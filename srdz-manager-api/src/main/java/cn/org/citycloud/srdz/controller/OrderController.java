package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.bean.OrderSearch;
import cn.org.citycloud.srdz.service.OrderService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 订单管理controller.
 *
 * @author demon
 * @Date 2016/4/25 11:14
 */
@RestController
@Api(tags = "订单管理")
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value="获取订单列表",notes="获取订单列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="stime",value="开始时间",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="etime",value="结束时间",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="orderStatus",value="订单状态：0(已取消)；10(默认):待支付；20:待发货； 40:已发货；50:待评价； 60:已完成",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="backOrderStatus",value="退款状态 （100:待退款  110:退款中 120:待汇款 130:拒绝退款 140:已退款）",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="orderType",value="订单类型(1.普通订单,2.分销订单)",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="memberName",value="用户名称",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="orderId",value="订单号",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="supplierName",value="供应商名称",required=false,dataType="string",paramType="query")
    })
    public Object getOrderList(@ApiIgnore OrderSearch orderSearch) {
        return orderService.getOrderList(orderSearch);
    }

    @RequestMapping(value="/{orderId}",method=RequestMethod.GET)
    @ApiOperation(value="获取订单详情",notes="获取订单详情",consumes="application/json",produces="application/json")
    public Object getOrderDetail(@ApiParam(name="orderId",value="订单Id",required=true) @PathVariable int orderId) {
        return orderService.getOrderDetail(orderId);
    }

    @RequestMapping(value = "/orderExcel", method = RequestMethod.GET)
    @ApiOperation(value="下载订单列表",notes="下载订单列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="stime",value="开始时间",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="etime",value="结束时间",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="orderStatus",value="订单状态：0(已取消)10(默认):未付款;20:已付款;30:已收货;40:已评价",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="orderType",value="订单类型   （1  普通订单   2  分销订单）",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="memberName",value="用户名称",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="orderId",value="订单号",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="supplierName",value="供应商名称",required=false,dataType="string",paramType="query")
    })
    public void downOrderExcel(@ApiIgnore OrderSearch orderSearch, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        orderService.downOrderExcel(orderSearch, os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(("订单列表.xls").getBytes(), "iso-8859-1"));
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
