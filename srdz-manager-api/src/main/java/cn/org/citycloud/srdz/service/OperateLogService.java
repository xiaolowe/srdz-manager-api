package cn.org.citycloud.srdz.service;

import cn.org.citycloud.srdz.bean.OperateLogSearch;

/**
 * 日志service.
 *
 * @author demon
 * @Date 2016/5/14 17:01
 */
public interface OperateLogService {
    /**
     * 根据条件查询日志列表
     *
     * @param search
     * @return
     */
    public Object getLogList(OperateLogSearch search);
}
