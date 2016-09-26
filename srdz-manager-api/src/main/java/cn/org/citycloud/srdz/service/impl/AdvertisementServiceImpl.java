package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.bean.AdvertisementBean;
import cn.org.citycloud.srdz.bean.AdvertisementSearch;
import cn.org.citycloud.srdz.entity.Advertisement;
import cn.org.citycloud.srdz.entity.GoodsClassBanner;
import cn.org.citycloud.srdz.repository.AdvertisementDao;
import cn.org.citycloud.srdz.repository.GoodsClassBannerDao;
import cn.org.citycloud.srdz.repository.GoodsClassDao;
import cn.org.citycloud.srdz.service.AdvertisementService;
import cn.org.citycloud.srdz.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * 广告service接口实现.
 *
 * @author demon
 * @Date 2016/4/25 14:28
 */
@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private AdvertisementDao adDao;

    @Autowired
    private GoodsClassDao classDao;

    @Autowired
    private GoodsClassBannerDao bannerDao;

    @Override
    public Object getAdvertisementList(AdvertisementSearch adSearch) {
        Sort sort = new Sort(Sort.Direction.DESC, "advertisementId");
        Specification<Advertisement> spec = new Specification<Advertisement>() {

            @Override
            public Predicate toPredicate(Root<Advertisement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (!StringUtils.isEmpty(adSearch.getStime())) {
                    String stime = adSearch.getStime() + " 00:00:00";
                    predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("startTime"), DateUtils.parse(stime, DateUtils.DATE_PATTERN_1)));
                }
                if (!StringUtils.isEmpty(adSearch.getEtime())) {
                    String etime = adSearch.getEtime() + " 23:59:59";
                    predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("endTime"), DateUtils.parse(etime, DateUtils.DATE_PATTERN_1)));
                }
                if (!StringUtils.isEmpty(adSearch.getAdvertisementName())) {
                    predicate = cb.and(predicate, cb.like(root.get("advertisementName"), "%" + adSearch.getAdvertisementName() + "%"));
                }
                return query.where(predicate).getRestriction();
            }
        };
        List<Advertisement> result = adDao.findAll(spec, sort);
        for (Advertisement ad : result) {
            // 如果小于1则表示过期了,-1小于，0等于，1大于
            if (ad.getEndTime().compareTo(new Date()) < 1) {
                if (ad.getStatus() == 1) {
                    ad.setStatus(10); // 正常过期
                } else {
                    ad.setStatus(11); // 关闭过期
                }
            }
            if (ad.getEndTime().compareTo(new Date()) > 0) {
                if (ad.getStatus() == 1) {
                    ad.setStatus(20); // 正常未过期
                } else {
                    ad.setStatus(21); // 关闭未过期
                }
            }
        }
        return result;
    }

    @Override
    public Object getAdDetail(int adId) {
        return adDao.findOne(adId);
    }

    @Override
    public Object addAd(AdvertisementBean adBean) {
        Advertisement advertisement = new Advertisement();
        BeanUtils.copyProperties(adBean, advertisement);
        advertisement.setCreateDate(new Date());
        advertisement.setUpdateDate(new Date());
        adDao.save(advertisement);
        return advertisement;
    }

    @Override
    public Object updateAd(AdvertisementBean adBean, int adId) {
        Advertisement advertisement = adDao.findOne(adId);
        BeanUtils.copyProperties(adBean, advertisement);
        advertisement.setUpdateDate(new Date());
        adDao.save(advertisement);
        return advertisement;
    }

    @Override
    public Object getGoodsClassList() {
        return classDao.getGoodsClassList();
    }

    @Override
    public Object updateGoodsClass(String classImage, int classId) {
        List<GoodsClassBanner> bannerList = bannerDao.findByGoodsClassId(classId);
        if (bannerList != null && bannerList.size() == 1) {
            GoodsClassBanner banner = bannerList.get(0);
            banner.setBannerImage(classImage);
            bannerDao.save(banner);
            return banner;
        } else {
            return null;
        }
    }

    @Override
    public Object getGoodsClassDetail(int classId) {
        return classDao.findOne(classId);
    }
}
