package cn.org.citycloud.srdz.task;

import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.entity.Advertisement;
import cn.org.citycloud.srdz.repository.AdvertisementDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 广告自动下架，下架条件：当前日期小于等于结束时间.
 *
 * @author demon
 * @Date 2016/5/5 13:07
 */
public class AdAutomaticUnshelve {

    private final static Logger logger = LoggerFactory.getLogger(AdAutomaticUnshelve.class);

    @Autowired
    private AdvertisementDao adDao;

    /**
     * 自动下架
     */
    public void adUnshelve() {
        logger.info("---------------广告自动下架定时任务开始执行");
        Iterator<Advertisement> iterator = getExpiredAdList().iterator();
        while (iterator.hasNext()) {
            Advertisement ad = iterator.next();
            ad.setStatus(Constants.AD_OUT_DATE);
            ad.setUpdateDate(new Date());
            adDao.save(ad);
            logger.info("广告自动下架，广告编号:{}", ad.getAdvertisementId());
        }
        logger.info("---------------广告自动下架定时任务执行结束");
    }

    /**
     * 获取过期广告列表
     *
     * @return
     */
    private List<Advertisement> getExpiredAdList() {
        Specification<Advertisement> spec = new Specification<Advertisement>() {
            @Override
            public Predicate toPredicate(Root<Advertisement> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("endTime"), new Date()));
                predicate = cb.and(predicate, cb.equal(root.get("status"), Constants.AD_NOMAL));
                return query.where(predicate).getRestriction();
            }
        };
        return adDao.findAll(spec);
    }
}
