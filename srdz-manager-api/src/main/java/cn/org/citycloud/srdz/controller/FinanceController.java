package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.bean.CashBean;
import cn.org.citycloud.srdz.bean.CashSearch;
import cn.org.citycloud.srdz.bean.PayInfoSearch;
import cn.org.citycloud.srdz.bean.RejectedBean;
import cn.org.citycloud.srdz.core.BaseController;
import cn.org.citycloud.srdz.exception.BusinessErrorException;
import cn.org.citycloud.srdz.service.FinanceService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * 财务管理controller接口.
 *
 * @author demon
 * @Date 2016/4/21 16:04
 */
@RestController
@Api(tags = "财务管理")
@RequestMapping("/finance")
public class FinanceController extends BaseController {

    @Autowired
    private FinanceService financeService;

    @RequestMapping(value = "/cash", method = RequestMethod.GET)
    @ApiOperation(value="获取提现列表",notes="获取提现列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="stime",value="开始时间 2016-04-20",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="etime",value="结束时间 2016-04-20",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="confirmStatus",value="审核状态 ( 0 未审核  1 审核通过  2 已打款  3 已驳回",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="cashId",value="支付单号",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="companyName",value="申请单位",required=false,dataType="string",paramType="query")
    })
    public Object getCashList(@ApiIgnore CashSearch cashSearch) {
        cashSearch.setRoleCode(getUserToken().getRoleCode());
        return financeService.getCashList(cashSearch);
    }

    @RequestMapping(value = "/cash/{cashId}", method = RequestMethod.GET)
    @ApiOperation(value="获取提现记录详情",notes="获取提现记录详情",consumes="application/json",produces="application/json")
    public Object getCashDetail(@ApiParam(name="cashId",value="提现记录id",required=true) @PathVariable int cashId) {
        return financeService.getCashDetail(cashId);
    }

    @RequestMapping(value = "/cash/history", method = RequestMethod.GET)
    @ApiOperation(value="获取历史打款记录",notes="获取提现记录详情",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="applyerId",value="申请人Id",required=false,dataType="int",paramType="query"),
    })
    public Object getHisCashList(int applyerId) {
        return financeService.getHisCashList(applyerId);
    }

    @RequestMapping(value="/cash/{cashId}/passed",method=RequestMethod.PUT)
    @ApiOperation(value="提现申请审核通过",notes="提现申请审核通过",consumes="application/json",produces="application/json")
    public Object passedCash(@ApiParam(name="cashId",value="提现记录id",required=true) @PathVariable int cashId) throws BusinessErrorException {
        return financeService.passedCash(cashId, getUserToken());
    }

    @RequestMapping(value="/cash/{cashId}/rejected",method=RequestMethod.PUT)
    @ApiOperation(value="提现申请审核驳回",notes="提现申请审核驳回",consumes="application/json",produces="application/json")
    public Object rejectedCash(@ApiParam(name="cashId",value="提现记录id",required=true) @PathVariable int cashId, @RequestBody @Valid RejectedBean rejectedBean) throws IOException, BusinessErrorException {
        return financeService.rejectedCash(cashId, rejectedBean.getReason(), getUserToken());
    }

    @RequestMapping(value="/cash",method=RequestMethod.PUT)
    @ApiOperation(value="提现申请确认打款",notes="提现申请确认打款",consumes="application/json",produces="application/json")
    public Object ensureCash(@RequestBody @Valid CashBean cashBean) throws IOException {
        return financeService.ensureCash(cashBean, getUserToken());
    }

    @RequestMapping(value = "/payInfo", method = RequestMethod.GET)
    @ApiOperation(value="获取支付单列表",notes="获取支付单列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="stime",value="开始时间 2016-04-20",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="etime",value="结束时间 2016-04-20",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="payStyle",value="支付方式代码 1 银联;2 支付宝;3 微信",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="payId",value="支付单号",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="companyName",value="收款单位",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="payMemberName",value="付款单位",required=false,dataType="string",paramType="query")
    })
    public Object getPayInfoList(@ApiIgnore PayInfoSearch payInfoSearch) {
        return financeService.getPayInfoList(payInfoSearch);
    }

    @RequestMapping(value="/payInfo/{payId}",method=RequestMethod.GET)
    @ApiOperation(value="获取支付单详情",notes="获取支付单详情",consumes="application/json",produces="application/json")
    public Object getPayInfoDetail(@ApiParam(name="payId",value="支付单id",required=true) @PathVariable int payId) {
        return financeService.getPayInfoDetail(payId);
    }

    @RequestMapping(value = "/downCash", method = RequestMethod.GET)
    @ApiOperation(value="下载支出报表",notes="下载支出报表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="stime",value="开始时间 2016-04-20",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="etime",value="结束时间 2016-04-20",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="confirmStatus",value="审核状态 ( 0 未审核  1 审核通过  2 已打款  3 已驳回",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="cashId",value="支付单号",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="companyName",value="申请单位",required=false,dataType="string",paramType="query")
    })
    public void downCashExcel(@ApiIgnore CashSearch cashSearch, HttpServletResponse response) throws InvocationTargetException, NoSuchMethodException, IOException, IllegalAccessException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        financeService.downCashExcel(cashSearch, os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(("支出报表.xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        byte[] buff = new byte[2048];
        int bytesRead;
        // Simple read/write loop.
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bos.close();
    }

    @RequestMapping(value = "/downPay", method = RequestMethod.GET)
    @ApiOperation(value="下载收入报表",notes="下载收入报表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="stime",value="开始时间 2016-04-20",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="etime",value="结束时间 2016-04-20",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="payStyle",value="支付方式代码 1 银联;2 支付宝;3 微信",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="payId",value="支付单号",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="companyName",value="收款单位",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="payMemberName",value="付款单位",required=false,dataType="string",paramType="query")
    })
    public void downPayInfoExcel(@ApiIgnore PayInfoSearch payInfoSearch, HttpServletResponse response) throws InvocationTargetException, NoSuchMethodException, IOException, IllegalAccessException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        financeService.downPayInfoExcel(payInfoSearch, os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(("收入报表.xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        byte[] buff = new byte[2048];
        int bytesRead;
        // Simple read/write loop.
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bos.close();
    }
}
