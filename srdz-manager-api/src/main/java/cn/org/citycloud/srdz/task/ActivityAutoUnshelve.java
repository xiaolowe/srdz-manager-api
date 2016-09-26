package cn.org.citycloud.srdz.task;

import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.entity.Activity;
import cn.org.citycloud.srdz.repository.ActivityDao;
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
 * 活动自动下架：结束时间小于等于当前时间.
 *
 * @author demon
 * @Date 2016/5/5 14:04
 */
public class ActivityAutoUnshelve {

    private final static Logger logger = LoggerFactory.getLogger(ActivityAutoUnshelve.class);

    @Autowired
    private ActivityDao activityDao;

    /**
     * 自动下架
     */
    public void activityUnshelve() {
        logger.info("---------------活动自动下架定时任务开始执行");
        Iterator<Activity> iterator = getActivityList().iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            activity.setUpdateDate(new Date());
            activity.setStatus(Constants.ACTIVITY_CLOSED);
            activityDao.save(activity);
            logger.info("活动自动下架，活动编号:{}", activity.getActivityId());
        }
        logger.info("---------------活动自动下架定时任务执行结束");
    }


    /**
     * 获取过期的活动
     *
     * @return
     */
    private List<Activity> getActivityList() {
        Specification<Activity> spec = new Specification<Activity>() {
            @Override
            public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                predicate = cb.and(predicate, cb.equal(root.get("status"), Constants.ACTIVITY_RUNNING));
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("endTime"), new Date()));
                return query.where(predicate).getRestriction();
            }
        };
        return activityDao.findAll(spec);
    }
}
