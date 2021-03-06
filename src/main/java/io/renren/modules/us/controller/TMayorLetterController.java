package io.renren.modules.us.controller;

import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.entity.*;
import io.renren.modules.us.param.*;
import io.renren.modules.us.service.*;
import io.renren.modules.us.util.UsSessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-18 16:06:20
 */
@RestController
@RequestMapping("/api/mayorLetter")
@Api("市长信箱")
public class TMayorLetterController {
    @Autowired
    private TMayorLetterService tMayorLetterService;
    @Autowired
    private UsUserService usUserService;
    @Autowired
    private TSDepartService tSDepartService;
    @Autowired
    private TSTypeService tSTypeService;
    @Autowired
    private TInfoService tInfoService;
    @Autowired
    private UsRegionsProService usRegionsProService;
    @Autowired
    private UsSessionUtil sessionUtil;


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("us:tmayorletter:list")
    public R list(@RequestParam Map<String, Object> params){

        PageUtils page = tMayorLetterService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("us:tmayorletter:info")
    public R info(@PathVariable("id") String id){
			TMayorLetterEntity tMayorLetter = tMayorLetterService.selectById(id);

        return R.ok().put("tMayorLetter", tMayorLetter);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("us:tmayorletter:save")
    public R save(@RequestBody TMayorLetterEntity tMayorLetter){
			tMayorLetterService.insert(tMayorLetter);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("us:tmayorletter:update")
    public R update(@RequestBody TMayorLetterEntity tMayorLetter){
			tMayorLetterService.updateById(tMayorLetter);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("us:tmayorletter:delete")
    public R delete(@RequestBody String[] ids){
			tMayorLetterService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    @PostMapping("sendLetter")
    @ApiOperation("发信")
    public R sendLetter(@RequestBody UsSendLetterParam form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        String userId = sessionUtil.getUserId(form.getSession());
        if (userId == null){
            return R.error("查询不到此用户");
        }

        UsUserEntity user = usUserService.selectById(userId);
        if(user == null){
            return R.error("查询不到此用户");
        }

        boolean isR = this.queryRel(user);
        if (isR == false){
            return R.error("用户未EID实名认证，请先实名认证再操作");
        }

        tMayorLetterService.sendLetter(user,form);
        return R.ok();
    }

    @PostMapping("myList")
    @ApiOperation("我发送的信件")
    public R myList(@RequestBody UsLetterListParam form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        String userId = sessionUtil.getUserId(form.getSession());
        if (userId == null){
            return R.error("查询不到此用户");
        }

        UsUserEntity user = usUserService.selectById(userId);
        if(user == null){
            return R.error("查询不到此用户");
        }

        boolean isR = this.queryRel(user);
        if (isR == false){
            return R.error("用户未EID实名认证，请先实名认证再操作");
        }

        Map<String, Object> params = new HashMap<>();

        String pageNo = (form.getPageNo() == null || "".equals(form.getPageNo())) ? "1" : form.getPageNo();
        String pageSize = (form.getPageSize() == null || "".equals(form.getPageSize())) ? "10" : form.getPageSize();

        params.put("limit",pageSize);
        params.put("page",pageNo);

        String senderId = user.getId();
        //参数
        params.put("senderId",senderId);

        PageUtils page = tMayorLetterService.queryPage(params);

        List list = page.getList();
        //赋值返回职业/工作单位/回复单位名称/信件状态
        for (int j = 0 ;j < list.size(); j++){
            TMayorLetterEntity tl = (TMayorLetterEntity)list.get(j);

            tl = this.queryName(tl);

        }

        return R.ok(page);

    }


    private boolean queryRel(UsUserEntity user) {
        return user.getEidLevel().equals(Constant.EidLevel.EID_LEVEL_3.getValue());
    }

    /**
     * 根据工作单位id获取名称等
     * @param
     * @return
     */
    public TMayorLetterEntity queryName(TMayorLetterEntity tl) {
        //工作单位
        if (null != tl.getuDepartid()  &&  !"".equals(tl.getuDepartid())){
            TSTypeEntity ts_ = tSTypeService.queryByCode(tl.getuDepartid(),"dep_list");
            if (ts_!=null){
                tl.setPersonDepartname(ts_.getTypename());
            }
        }
        //职业
        if (null != tl.getuJobid()  &&  !"".equals(tl.getuJobid())){
            TSTypeEntity ts = tSTypeService.queryByCode(tl.getuJobid(),"job_list");
            if (ts!=null){
                tl.setPersonJob(ts.getTypename());
            }
        }
        //回复单位
        if (null != tl.getReplyDepartid()  &&  !"".equals(tl.getReplyDepartid())){
            TSDepartEntity tSDepart_ =  tSDepartService.selectById(tl.getReplyDepartid());
            if (tSDepart_!=null){
                tl.setReplyDepartname(tSDepart_.getDepartname());
            }
        }

        //信件状态汉字
        if (null != tl.getBpmStatus()  &&  !"".equals(tl.getBpmStatus())){
            TSTypeEntity ts_0 = tSTypeService.queryByCode(tl.getBpmStatus(),"lettStatus");
            if (ts_0!=null){
                tl.setBpmStatus(ts_0.getTypename());
            }
        }

        return tl;
    }

    @PostMapping("queryLetterOtherList")
    @ApiOperation("查询其他人员信件")
    public R queryLetterOtherList(@RequestBody UsLetterListOtherParam form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        String userId = sessionUtil.getUserId(form.getSession());
        if (userId == null){
            return R.error("查询不到此用户");
        }

        UsUserEntity user = usUserService.selectById(userId);
        if(user == null){
            return R.error("查询不到此用户");
        }

        boolean isR = this.queryRel(user);
        if (isR == false){
            return R.error("用户未EID实名认证，请先实名认证再操作");
        }
        Map<String, Object> params = new HashMap<>();

        String pageNo = (form.getPageNo() == null || "".equals(form.getPageNo())) ? "1" : form.getPageNo();
        String pageSize = (form.getPageSize() == null || "".equals(form.getPageSize())) ? "10" : form.getPageSize();

        params.put("limit",pageSize);
        params.put("page",pageNo);

        //参数
        params.put("letterCode",form.getLetterCode());
        params.put("personIdCard",form.getPersonIdCard());

        PageUtils page = tMayorLetterService.queryPageOther(params);

        List list = page.getList();
        //赋值返回职业/工作单位/回复单位名称/信件状态
        for (int j = 0 ;j < list.size(); j++){
            TMayorLetterEntity tl = (TMayorLetterEntity)list.get(j);

            tl = this.queryName(tl);

        }

        return R.ok(page);

    }



    @PostMapping("letterDetail")
    @ApiOperation("信件明细")
    public R letterDetail(@RequestBody UsLetterDetailParam form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        String userId = sessionUtil.getUserId(form.getSession());
        if (userId == null){
            return R.error("查询不到此用户");
        }

        UsUserEntity user = usUserService.selectById(userId);
        if(user == null){
            return R.error("查询不到此用户");
        }

        boolean isR = this.queryRel(user);
        if (isR == false){
            return R.error("用户未EID实名认证，请先实名认证再操作");
        }

        TMayorLetterEntity tl = new TMayorLetterEntity();

        if (form.getLetterId()!=null){//信件id查询
            tl = tMayorLetterService.selectById(form.getLetterId());

        }else if(form.getLetterCode()!=null){//信件编码查询
            tl= tMayorLetterService.queryDetailByLetterCode(form.getLetterCode());//根据信件编号查询信件明细信息
        }else{
            return R.error("信件ID或信件编码未填写");
        }
        if (null != tl) {
            tl = this.queryName(tl);
        }

        return R.ok(tl);

    }


    @PostMapping("letterInfo")
    @ApiOperation("信件说明")
    public R letterInfo(@RequestBody UsBaseParam form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        TInfoEntity ti = tInfoService.queryByCode("szxx");//查询市长信箱说明

        return R.ok(ti);

    }


    @PostMapping("regionListPro")
    @ApiOperation("一二级属地")
    public R regionListPro(@RequestBody UsSessionParam form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        String userId = sessionUtil.getUserId(form.getSession());
        if (userId == null){
            return R.error("查询不到此用户");
        }

        UsUserEntity user = usUserService.selectById(userId);
        if(user == null){
            return R.error("查询不到此用户");
        }

        boolean isR = this.queryRel(user);
        if (isR == false){
            return R.error("用户未EID实名认证，请先实名认证再操作");
        }

        List<LinkedHashMap<Object, Object>>  list = usRegionsProService.queryList();

        return R.ok(list);

    }



}
