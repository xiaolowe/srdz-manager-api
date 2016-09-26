package cn.org.citycloud.srdz.service;

import cn.org.citycloud.srdz.bean.ReportSearch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 报表service接口.
 *
 * @author demon
 * @Date 2016/4/22 14:33
 */
public interface ReportService {
    /**
     * 获取报表统计信息
     *
     * @param reportSearch
     * @return
     */
    public Object getReport(ReportSearch reportSearch);

    /**
     * 获取报表详情
     *
     * @param reportSearch
     * @return
     */
    public Object getItemList(ReportSearch reportSearch);

    /**
     * 下载报表
     *
     * @param reportSearch
     * @param os
     * @return
     */
    public void downReportExcel(ReportSearch reportSearch, ByteArrayOutputStream os) throws IOException;
}
