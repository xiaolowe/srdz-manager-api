package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.bean.*;
import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.entity.*;
import cn.org.citycloud.srdz.exception.BusinessErrorException;
import cn.org.citycloud.srdz.repository.*;
import cn.org.citycloud.srdz.service.GoodsService;
import cn.org.citycloud.srdz.utils.BeanUtil;
import cn.org.citycloud.srdz.utils.DateUtils;
import cn.org.citycloud.srdz.utils.SiteMsgUtils;
import cn.org.citycloud.srdz.websocket.WebSocketMsgsHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.*;

/**
 * 商品管理service接口实现.
 *
 * @author demon
 * @Date 2016/4/25 15:37
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private EvaluateGoodsDao evaluateGoodsDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private GoodsClassDao classDao;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private WebSocketMsgsHandler msgsHandler;

    @Override
    public Object getGoodsList(GoodsSearch goodsSearch) {
        int page = goodsSearch.getPageNo();
        int size = goodsSearch.getPageSize();
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        Specification<Goods> spec = new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                // 只查询普通的商品
                predicate = cb.and(predicate, cb.equal(root.get("discountFlg"), Constants.GOODS_TYPE_NORMAL));
                if (goodsSearch.getGoodsClassId() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("goodsClassId"), goodsSearch.getGoodsClassId()));
                }
                if (goodsSearch.getGoodsStatus() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("goodsStatus"), goodsSearch.getGoodsStatus()));
                }
                if (!StringUtils.isEmpty(goodsSearch.getSupplierName())) {
                    predicate = cb.and(predicate, cb.like(root.get("supplier").get("supplierName"), "%" + goodsSearch.getSupplierName() + "%"));
                }
                if (!StringUtils.isEmpty(goodsSearch.getGoodsName())) {
                    predicate = cb.and(predicate, cb.like(root.get("goodsName"), "%" + goodsSearch.getGoodsName() + "%"));
                }

                return query.where(predicate).getRestriction();
            }
        };
        return goodsDao.findAll(spec, pageable);
    }

    @Override
    public Object getGoodsDetail(int goodsId) {
        return goodsDao.findOne(goodsId);
    }

//    @Override
//    public Object unShelve(int goodsId) throws IOException {
//        // TODO 发送站内信通知相应的供应商
//        Goods goods = updateGoodsStatus(goodsId, Constants.GOODS_UNSHELVE);
//        Message message = SiteMsgUtils.getMessage(goods.getSupplier().getSupplierName(), goods.getSupplier().getSupplierId(),
//                goodsId, Constants.GOOD_UNSHELVE);
//        messageDao.save(message);
//        msgsHandler.sendMessageToUser(message.getReceiverId(), new TextMessage(message.getMessageContent()));
//        return goods;
//    }


    @Override
    public void forbidSales(int[] goodsIds) throws IOException {
        List<Goods> list = batchUpdateStatus(goodsIds, Constants.GOODS_AVOID_SALE);
        for (Goods goods : list) {
            Message message = SiteMsgUtils.getMessage(goods.getSupplier().getSupplierName(), goods.getSupplier().getSupplierId(),
                    goods.getGoodsId(), Constants.GOOD_FORBID);
            message.setPlatform(Constants.MSG_SUPPLIER);
            messageDao.save(message);
            msgsHandler.sendMessageToUser(message.getReceiverId(), new TextMessage(message.getMessageContent()));
        }
        // TODO 发送站内信通知相应的供应商
    }

    @Override
    public void cancelForbid(int[] goodsIds) throws IOException {
        List<Goods> list = batchUpdateStatus(goodsIds, Constants.GOODS_NORMAL);
        // TODO 发送站内信通知相应的供应商
        for (Goods goods : list) {
            Message message = SiteMsgUtils.getMessage(goods.getSupplier().getSupplierName(), goods.getSupplier().getSupplierId(),
                    goods.getGoodsId(), Constants.GOOD_CANCEL_FORBID);
            message.setPlatform(Constants.MSG_SUPPLIER);
            messageDao.save(message);
            msgsHandler.sendMessageToUser(message.getReceiverId(), new TextMessage(message.getMessageContent()));
        }
    }

    @Transactional
    public void setRecommended(int[] goodsIds) {
        batchUpdateIsRecommend(goodsIds, Constants.RECOMMEND);
    }

    @Transactional
    public void cancelRecommended(int[] goodsIds) {
        batchUpdateIsRecommend(goodsIds, Constants.UNRECOMMEND);
    }

    @Override
    public Object getGoodsCommentsList(EvaluateGoodsSearch evaluateGoodsSearch) {
        int page = evaluateGoodsSearch.getPageNo();
        int size = evaluateGoodsSearch.getPageSize();
        Sort sort = new Sort(Sort.Direction.DESC, "gevalAddtime");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        Specification<EvaluateGood> spec = new Specification<EvaluateGood>() {
            @Override
            public Predicate toPredicate(Root<EvaluateGood> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (!StringUtils.isEmpty(evaluateGoodsSearch.getStime())) {
                    String stime = evaluateGoodsSearch.getStime() + " 00:00:00";
                    predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("gevalAddtime"), DateUtils.parse(stime, DateUtils.DATE_PATTERN_1)));
                }
                if (!StringUtils.isEmpty(evaluateGoodsSearch.getEtime())) {
                    String etime = evaluateGoodsSearch.getEtime() + " 23:59:59";
                    predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("gevalAddtime"), DateUtils.parse(etime, DateUtils.DATE_PATTERN_1)));
                }
                if (!StringUtils.isEmpty(evaluateGoodsSearch.getGoodsName())) {
                    predicate = cb.and(predicate, cb.like(root.get("goodsName"), "%" + evaluateGoodsSearch.getGoodsName() + "%"));
                }
                if (evaluateGoodsSearch.getSupplierId() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("supplier").get("supplierId"), evaluateGoodsSearch.getSupplierId()));
                }
                return query.where(predicate).getRestriction();
            }
        };
        return evaluateGoodsDao.findAll(spec, pageable);
    }

    @Override
    public Object shieldComment(int evaluateId) {
        EvaluateGood evaluateGood = evaluateGoodsDao.findOne(evaluateId);
        evaluateGood.setGevalStatus(1);
        evaluateGoodsDao.save(evaluateGood);
        return evaluateGood;
    }

    @Override
    public Object cancelShieldComment(int evaluateId) {
        EvaluateGood evaluateGood = evaluateGoodsDao.findOne(evaluateId);
        evaluateGood.setGevalStatus(0);
        evaluateGoodsDao.save(evaluateGood);
        return evaluateGood;
    }

    @Override
    public Object getBrandList(BrandSearch brandSearch) {
        int page = brandSearch.getPageNo();
        int size = brandSearch.getPageSize();
        Sort sort = new Sort(Sort.Direction.ASC, "brandSort");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        Specification<Brand> spec = new Specification<Brand>() {

            @Override
            public Predicate toPredicate(Root<Brand> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (!StringUtils.isEmpty(brandSearch.getBrandName())) {
                    predicate = cb.and(predicate, cb.like(root.get("brandName"), "%" + brandSearch.getBrandName() + "%"));
                }
                return query.where(predicate).getRestriction();
            }
        };
        return brandDao.findAll(spec, pageable);
    }

    @Override
    public Object getBrandDetail(int brandId) {
        return brandDao.findOne(brandId);
    }

    @Override
    public Object addBrand(BrandBean brandBean) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandBean, brand);
        brand.setCreateTime(new Date());
        brand.setUpdateTime(new Date());
        brandDao.save(brand);
        return brand;
    }

    @Override
    public Object updateBrand(BrandBean brandBean, int brandId) {
        Brand brand = brandDao.findOne(brandId);
        BeanUtils.copyProperties(brandBean, brand);
        brand.setUpdateTime(new Date());
        brandDao.save(brand);
        return brand;
    }

    /**
     * 修改商品的状态
     *
     * @param goodsId
     * @param status
     * @return
     */
    private Goods updateGoodsStatus(int goodsId, int status) {
        Goods goods = goodsDao.findOne(goodsId);
        goods.setGoodsStatus(status);
        goods.setUpdateTime(new Date());
        goodsDao.save(goods);
        return goods;
    }

    /**
     * 批量修改商品状态
     *
     * @param goodsIds
     * @param status
     */
    private void batchUpdateIsRecommend(int[] goodsIds, int status) {
        List<Goods> list = new ArrayList<>();
        for (int goodsId : goodsIds) {
            Goods goods = goodsDao.findOne(goodsId);
            goods.setIsRecommend(status);
            goods.setUpdateTime(new Date());
            list.add(goods);
        }
        goodsDao.save(new Iterable<Goods>() {
            @Override
            public Iterator<Goods> iterator() {
                return list.iterator();
            }
        });
    }

    /**
     * 批量修改商品状态
     *
     * @param goodsIds
     * @param status
     */
    private List<Goods> batchUpdateStatus(int[] goodsIds, int status) {
        List<Goods> list = new ArrayList<>();
        for (int goodsId : goodsIds) {
            Goods goods = goodsDao.findOne(goodsId);
            goods.setGoodsStatus(status);
            goods.setUpdateTime(new Date());
            list.add(goods);
        }
        return goodsDao.save(new Iterable<Goods>() {
            @Override
            public Iterator<Goods> iterator() {
                return list.iterator();
            }
        });
    }

    @Override
    public Object getFirstLevelClass() {
        return classDao.findByParentId(0);
    }

    @Override
    public Object getGoodClassByPid(int pid) {
        return classDao.findByParentId(pid);
    }

    @Override
    public Object addGoodClass(GoodsCategoryBean classBean) throws BusinessErrorException {
        checkName(classBean.getGoodsClassName(), false, 0);
        Set<GoodsClassBanner> tempList = new HashSet<>();
        // 判断bannerSet的size，如果是三级分类的添加，则size必须大于1
        if (classBean.getIsThirdLevel() == 1) {
            if (classBean.getGoodsClassBannerSet() == null || classBean.getGoodsClassBannerSet().size() < 1) {
                throw new BusinessErrorException("014", "三级分类banner数量至少传一张");
            }
            if (classBean.getParentId() == -1) {
                throw new BusinessErrorException("015", "请选择父级分类");
            }
            for (GoodsClassBannerBean bean : classBean.getGoodsClassBannerSet()) {
                GoodsClassBanner goodsClassBanner = new GoodsClassBanner();
                goodsClassBanner.setBannerImage(bean.getBannerImage());
                tempList.add(goodsClassBanner);
            }
        }
        GoodsClass goodsClass = new GoodsClass();
        BeanUtils.copyProperties(classBean, goodsClass);
        goodsClass.setGoodsClassBannerSet(tempList);
        goodsClass.setCreateTime(new Date());
        goodsClass.setUpdateTime(new Date());
        classDao.save(goodsClass);
        return goodsClass;
    }

    @Transactional
    @Override
    public Object updateGoodClass(GoodsCategoryBean classBean, int classId) throws BusinessErrorException {
        checkName(classBean.getGoodsClassName(), true, classId);
        Set<GoodsClassBanner> tempList = new HashSet<>();
        // 判断bannerSet的size，如果是三级分类的修改，则size必须大于1
        if (classBean.getIsThirdLevel() == 1) {
            if (classBean.getGoodsClassBannerSet() == null || classBean.getGoodsClassBannerSet().size() < 1) {
                throw new BusinessErrorException("014", "三级分类banner数量至少传一张");
            }
            if (classBean.getParentId() == -1) {
                throw new BusinessErrorException("015", "请选择父级分类");
            }
            for (GoodsClassBannerBean bean : classBean.getGoodsClassBannerSet()) {
                GoodsClassBanner goodsClassBanner = new GoodsClassBanner();
                goodsClassBanner.setBannerImage(bean.getBannerImage());
                tempList.add(goodsClassBanner);
            }
        }
        GoodsClass goodsClass = classDao.findOne(classId);
        BeanUtils.copyProperties(classBean, goodsClass);
        goodsClass.setGoodsClassBannerSet(tempList);
        goodsClass.setUpdateTime(new Date());
        classDao.save(goodsClass);
        return goodsClass;
    }

    @Override
    public Object getGoodClassList() {
        StringBuffer sql = new StringBuffer("SELECT t1 FROM GoodsClass t1 WHERE t1.parentId = 0 and t1.delFlag = 0 order by t1.sort ");
        StringBuffer sql2 = new StringBuffer("SELECT t2 FROM GoodsClass t2 WHERE t2.delFlag = 0");
        sql2.append(" AND t2.parentId IN ( SELECT goodsClassId FROM GoodsClass WHERE parentId = 0 ) order by t2.sort ");
        Query query = em.createQuery(sql.toString(), GoodsClass.class);
        Query query2 = em.createQuery(sql2.toString(), GoodsClass.class);
        List<GoodsClass> list = query.getResultList();
        list.addAll(query2.getResultList());
        List<GoodsClassBean> paramList = new ArrayList<>();
        for (GoodsClass goodsClass : list) {
            GoodsClassBean goodsClassBean = new GoodsClassBean();
            BeanUtils.copyProperties(goodsClass, goodsClassBean);
            paramList.add(goodsClassBean);
        }
        List<GoodsClassBean> resultList = new ArrayList<>();
        for (GoodsClassBean goodsClassBean1 : paramList) {
            boolean mark = false;
            for (GoodsClassBean goodsClassBean2 : paramList) {
                if (goodsClassBean1.getParentId() == goodsClassBean2.getGoodsClassId()) {
                    mark = true;
                    if (goodsClassBean2.getChildren() == null) {
                        goodsClassBean2.setChildren(new ArrayList<GoodsClassBean>());
                    }
                    goodsClassBean2.getChildren().add(goodsClassBean1);
                    break;
                }
            }
            if (!mark) {
                resultList.add(goodsClassBean1);
            }
        }
        return resultList;
    }

    @Override
    public Object getGoodClassList(GoodsClassSearch classSearch) {
        StringBuffer sql = new StringBuffer("select t1.goods_class_id id1,t2.goods_class_id id2,t3.goods_class_id id3, t1.goods_class_name name1,t2.goods_class_name name2,t3.goods_class_name name3,t3.class_image, t3.goods_desc ");
        sql.append(" from goods_class t1");
        sql.append(" left join goods_class t2 on t1.goods_class_id = t2.parent_id ");
        sql.append(" inner join goods_class t3 on t3.parent_id = t2.goods_class_id where t1.parent_id = 0");
        if (classSearch.getPid() != -1) {
            sql.append(" and (t3.parent_id = " + classSearch.getPid() + " or t2.parent_id = " + classSearch.getPid() + ")");
        }
        if (!StringUtils.isEmpty(classSearch.getClassName())) {
            sql.append(" and t3.goods_class_name like '%" + classSearch.getClassName() + "%'");
        }
        int page = classSearch.getPageNo();
        int pageSize = classSearch.getPageSize();
        int start = (page - 1) * pageSize;
        sql.append(" order by t3.create_time desc limit " + start + "," + pageSize);
        Query query = em.createNativeQuery(sql.toString());
        List<Object[]> tempList = query.getResultList();
        List<GoodsClassListBean> content = new ArrayList<>();
        for (Object[] objArr : tempList) {
            if (objArr.length == 8) {
                GoodsClassListBean goodsClassListBean = new GoodsClassListBean();
                goodsClassListBean.setFirstClassId((Integer) objArr[0]);
                goodsClassListBean.setSecondClassId((Integer) objArr[1]);
                goodsClassListBean.setThirdClassId((Integer) objArr[2]);
                goodsClassListBean.setFirstClassName((String) objArr[3]);
                goodsClassListBean.setSecondClassName((String) objArr[4]);
                goodsClassListBean.setThirdClassName((String) objArr[5]);
                goodsClassListBean.setClassImage((String) objArr[6]);
                goodsClassListBean.setGoodsDesc((String) objArr[7]);
                content.add(goodsClassListBean);
            }
        }
        StringBuffer countSql = new StringBuffer("SELECT count(1) FROM goods_class t1 LEFT JOIN goods_class t2 ON t1.goods_class_id = t2.parent_id INNER JOIN goods_class t3 ON t3.parent_id = t2.goods_class_id WHERE t1.parent_id = 0");
        if (classSearch.getPid() != -1) {
            countSql.append(" and (t3.parent_id = " + classSearch.getPid() + " or t2.parent_id = " + classSearch.getPid() + ")");
        }
        if (!StringUtils.isEmpty(classSearch.getClassName())) {
            countSql.append(" and t3.goods_class_name like '%" + classSearch.getClassName() + "%'");
        }
        Query countQuery = em.createNativeQuery(countSql.toString());
        BigInteger total = (BigInteger) countQuery.getSingleResult();
        Pageable pageable = new PageRequest(page - 1, pageSize);
        Page<GoodsClassListBean> resultPage = new PageImpl<GoodsClassListBean>(content, pageable, total.longValue());
        return resultPage;
    }

    @Override
    public Object getGoodClassDetail(int classId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Map<String, Object> resultMap = BeanUtil.beanToMap(classDao.findOne(classId), GoodsClass.class);
        StringBuffer sql = new StringBuffer("select t1.goods_class_name name1,t2.goods_class_name name2 ");
        sql.append(" from goods_class t1");
        sql.append(" left join goods_class t2 on t1.goods_class_id = t2.parent_id ");
        sql.append(" inner join goods_class t3 on t3.parent_id = t2.goods_class_id where t3.goods_class_id = ");
        sql.append(classId);
        Query query = em.createNativeQuery(sql.toString());
        Object[] queryResult = (Object[]) query.getSingleResult();
        if (queryResult.length == 2) {
            resultMap.put("firstLevelName", queryResult[0]);
            resultMap.put("secondLevelName", queryResult[1]);
        }
        return resultMap;
    }

    @Override
    public void deleteGoodClass(int classId) throws BusinessErrorException {
        if (hasChildren(classId)) {
            throw new BusinessErrorException("005", "当前删除的分类有子分类，不能删除");
        } else {
            GoodsClass goodsClass = classDao.findOne(classId);
            goodsClass.setDelFlag(1);
            goodsClass.setUpdateTime(new Date());
            classDao.save(goodsClass);
        }
    }

    @Override
    public Object getThdClassListByName(String name) {
        StringBuffer sql = new StringBuffer("select t3.goods_class_name name3 ");
        sql.append(" from goods_class t1");
        sql.append(" left join goods_class t2 on t1.goods_class_id = t2.parent_id ");
        sql.append(" inner join goods_class t3 on t3.parent_id = t2.goods_class_id where t1.parent_id = 0");
        if (!StringUtils.isEmpty(name)) {
            sql.append(" and t3.goods_class_name like '%" + name + "%'");
        }
        sql.append(" order by t3.create_time desc limit 0, 10 ");
        Query query = em.createNativeQuery(sql.toString());
        return query.getResultList();
    }

    /**
     * 判断商品分类下是否有子节点
     *
     * @param classId
     * @return
     */
    private boolean hasChildren(int classId) {
        long total = classDao.count(new Specification<GoodsClass>() {
            @Override
            public Predicate toPredicate(Root<GoodsClass> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                predicate = cb.and(predicate, cb.equal(root.get("parentId"), classId));
                return query.where(predicate).getRestriction();
            }
        });
        if (total > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断三级分类名称是否存在
     *
     * @param className
     * @param classId
     * @param isUpdate
     * @throws BusinessErrorException
     */
    private void checkName(String className, boolean isUpdate, int classId) throws BusinessErrorException {
        StringBuffer sql = new StringBuffer("select t1.goods_class_id id1,t2.goods_class_id id2,t3.goods_class_id id3, t1.goods_class_name name1,t2.goods_class_name name2,t3.goods_class_name name3,t3.class_image, t3.goods_desc ");
        sql.append(" from goods_class t1");
        sql.append(" left join goods_class t2 on t1.goods_class_id = t2.parent_id ");
        sql.append(" inner join goods_class t3 on t3.parent_id = t2.goods_class_id where t1.parent_id = 0");
        sql.append(" and t3.goods_class_name = '" + className + "'");
        if (isUpdate) {
            GoodsClass goodsClass = classDao.findOne(classId);
            sql.append(" and t3.goods_class_name != '" + goodsClass.getGoodsClassName() + "'");
        }
        Query query = em.createNativeQuery(sql.toString());
        List<Object[]> tempList = query.getResultList();
        if (tempList != null && tempList.size() > 0) {
            throw new BusinessErrorException("017", "三级分类名称已经存在，请重新输入");
        }
    }
}
