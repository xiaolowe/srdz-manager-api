package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.bean.*;
import cn.org.citycloud.srdz.core.BaseController;
import cn.org.citycloud.srdz.exception.BusinessErrorException;
import cn.org.citycloud.srdz.service.GoodsService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 商品管理controller.
 *
 * @author demon
 * @Date 2016/4/25 15:54
 */
@RestController
@Api(tags = "商品管理")
@RequestMapping("/goods")
public class GoodsController extends BaseController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value="获取商品列表",notes="获取商品列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="goodsClassId",value="商品一级分类",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="goodsStatus",value="商品状态（ 0 正常  1 上架   2  下架  10 禁售）",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="supplierName",value="供应商名称",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="goodsName",value="商品名称",required=false,dataType="string",paramType="query")
    })
    public Object getGoodsList(@ApiIgnore GoodsSearch goodsSearch) {
        return goodsService.getGoodsList(goodsSearch);
    }

    @RequestMapping(value = "/{goodsId}", method = RequestMethod.GET)
    @ApiOperation(value="获取商品详情",notes="获取商品详情",consumes="application/json",produces="application/json")
    public Object getGoodsDetail(@ApiParam(name="goodsId",value="商品Id",required=true) @PathVariable int goodsId) {
        return goodsService.getGoodsDetail(goodsId);
    }

    @RequestMapping(value="/forbid",method=RequestMethod.PUT)
    @ApiOperation(value="批量商品禁售",notes="批量商品禁售",consumes="application/json",produces="application/json")
    public void forbidSales(@RequestBody @Valid int[] goodsIds) throws IOException {
        goodsService.forbidSales(goodsIds);
    }

    @RequestMapping(value="/cancelForbid",method=RequestMethod.PUT)
    @ApiOperation(value="批量取消商品禁售",notes="批量取消商品禁售",consumes="application/json",produces="application/json")
    public void cancelForbid(@RequestBody @Valid int[] goodsIds) throws IOException {
        goodsService.cancelForbid(goodsIds);
    }

    @RequestMapping(value="/recommend",method=RequestMethod.PUT)
    @ApiOperation(value="设为推荐",notes="设为推荐",consumes="application/json",produces="application/json")
    public void setRecommended(@RequestBody @Valid int[] goodsIds) {
        goodsService.setRecommended(goodsIds);
    }

    @RequestMapping(value="/cancelRecommend",method=RequestMethod.PUT)
    @ApiOperation(value="取消推荐",notes="取消推荐",consumes="application/json",produces="application/json")
    public void cancelRecommended(@RequestBody @Valid int[] goodsIds) {
        goodsService.cancelRecommended(goodsIds);
    }

    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    @ApiOperation(value="获取商品评价列表",notes="获取商品评价列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="stime",value="开始时间",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="etime",value="结束时间",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="supplierId",value="供应商id",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="goodsName",value="商品名称",required=false,dataType="string",paramType="query")
    })
    public Object getGoodsCommentsList(@ApiIgnore EvaluateGoodsSearch evaluateGoodsSearch) {
        return goodsService.getGoodsCommentsList(evaluateGoodsSearch);
    }

    @RequestMapping(value="/comments/{evaluateId}/shield",method=RequestMethod.PUT)
    @ApiOperation(value="屏蔽评论",notes="屏蔽评论",consumes="application/json",produces="application/json")
    public Object shieldComment(@ApiParam(name="evaluateId",value="评价Id",required=true) @PathVariable int evaluateId) {
        return goodsService.shieldComment(evaluateId);
    }

    @RequestMapping(value="/comments/{evaluateId}/cancelShield",method=RequestMethod.PUT)
    @ApiOperation(value="取消屏蔽评论",notes="取消屏蔽评论",consumes="application/json",produces="application/json")
    public Object cancelShieldComment(@ApiParam(name="evaluateId",value="评价Id",required=true) @PathVariable int evaluateId) {
        return goodsService.cancelShieldComment(evaluateId);
    }

    @RequestMapping(value = "/brands", method = RequestMethod.GET)
    @ApiOperation(value="获取品牌列表",notes="获取品牌列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="brandName",value="品牌名称",required=false,dataType="string",paramType="query")
    })
    public Object getBrandList(@ApiIgnore BrandSearch brandSearch) {
        return goodsService.getBrandList(brandSearch);
    }

    @RequestMapping(value = "/brands/{brandId}", method = RequestMethod.GET)
    @ApiOperation(value="获取品牌详情",notes="获取品牌详情",consumes="application/json",produces="application/json")
    public Object getBrandDetail(@ApiParam(name="brandId",value="品牌Id",required=true) @PathVariable int brandId) {
        return goodsService.getBrandDetail(brandId);
    }

    @RequestMapping(value="/brands",method=RequestMethod.POST)
    @ApiOperation(value="添加品牌",notes="添加品牌",consumes="application/json",produces="application/json")
    public Object addBrand(@RequestBody @Valid BrandBean brandBean) {
        return goodsService.addBrand(brandBean);
    }

    @RequestMapping(value="/brands/{brandId}",method=RequestMethod.PUT)
    @ApiOperation(value="修改品牌",notes="修改品牌",consumes="application/json",produces="application/json")
    public Object updateBrand(@RequestBody @Valid BrandBean brandBean, @ApiParam(name="brandId",value="品牌Id",required=true) @PathVariable int brandId) {
        return goodsService.updateBrand(brandBean, brandId);
    }

    @RequestMapping(value = "/class/firstLevel", method = RequestMethod.GET)
    @ApiOperation(value="获取商品一级分类",notes="获取商品一级分类",consumes="application/json",produces="application/json")
    public Object getFirstLevelClass() {
        return goodsService.getFirstLevelClass();
    }

    @RequestMapping(value = "/class/{pid}", method = RequestMethod.GET)
    @ApiOperation(value="根据父节点获取商品分类",notes="根据父节点获取商品分类",consumes="application/json",produces="application/json")
    public Object getGoodClassByPid(@ApiParam(name="pid",value="父节点Id",required=true) @PathVariable int pid) {
        return goodsService.getGoodClassByPid(pid);
    }

    @RequestMapping(value = "/class", method = RequestMethod.GET)
    @ApiOperation(value="获取分类树形结构数据",notes="获取分类树形结构数据",consumes="application/json",produces="application/json")
    public Object getGoodClassList() {
        return goodsService.getGoodClassList();
    }

    @RequestMapping(value = "/class/list", method = RequestMethod.GET)
    @ApiOperation(value="获取一~三级分类列表",notes="获取一~三级分类列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pid",value="父分类id",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="className",value="三级分类名称",required=false,dataType="string",paramType="query")
    })
    public Object getGoodClassList(@ApiIgnore GoodsClassSearch classSearch) {
        return goodsService.getGoodClassList(classSearch);
    }

    @RequestMapping(value = "/class/detail/{classId}", method = RequestMethod.GET)
    @ApiOperation(value="获取分类详情",notes="获取分类详情",consumes="application/json",produces="application/json")
    public Object getGoodsClassDetail(@ApiParam(name="classId",value="分类Id",required=true) @PathVariable int classId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return goodsService.getGoodClassDetail(classId);
    }

    @RequestMapping(value="/class",method=RequestMethod.POST)
    @ApiOperation(value="添加分类",notes="添加分类",consumes="application/json",produces="application/json")
    public Object addGoodsClass(@RequestBody @Valid GoodsCategoryBean classBean) throws BusinessErrorException {
        return goodsService.addGoodClass(classBean);
    }

    @RequestMapping(value="/class/{classId}",method=RequestMethod.PUT)
    @ApiOperation(value="修改分类",notes="修改分类",consumes="application/json",produces="application/json")
    public Object updateGoodsClass(@RequestBody @Valid GoodsCategoryBean classBean, @ApiParam(name="classId",value="分类Id",required=true) @PathVariable int classId) throws BusinessErrorException {
        return goodsService.updateGoodClass(classBean, classId);
    }

    @RequestMapping(value="/class/{classId}",method=RequestMethod.DELETE)
    @ApiOperation(value="删除分类",notes="删除分类",consumes="application/json",produces="application/json")
    public void deleteGoodClass(@ApiParam(name="classId",value="分类Id",required=true) @PathVariable int classId) throws BusinessErrorException {
        goodsService.deleteGoodClass(classId);
    }

    @RequestMapping(value = "/class/name/", method = RequestMethod.GET)
    @ApiOperation(value="按照三级分类名称模糊查询分类列表",notes="按照三级分类名称模糊查询分类列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="name",value="三级分类名称",required=false,dataType="string",paramType="query")
    })
    public Object getThdClassListByName(@ApiIgnore String name) {
        return goodsService.getThdClassListByName(name);
    }
}
