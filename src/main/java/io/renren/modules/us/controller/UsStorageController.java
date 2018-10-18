package io.renren.modules.us.controller;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.exception.RRException;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.param.UsSessionParam;
import io.renren.modules.us.service.UsUserService;
import io.renren.modules.us.util.UsOkHttpUtil;
import io.renren.modules.us.util.UsSessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Api("网盘接口")
public class UsStorageController {

    private UsUserService userService;

    private UsSessionUtil sessionUtil;

    private UsUserEntity getUser(String session) {
        String userId = sessionUtil.getUserId(session);
        if (userId == null) {
            return null;
        }
        UsUserEntity user = userService.selectById(userId);
        if (user == null) {
            return null;
        } else {
            return user;
        }
    }

    private String getToken(String username, String password) throws IOException {
        OkHttpClient okHttpClient = UsOkHttpUtil.getInstance().getOkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password).build();
        Request request = new Request.Builder().post(body).url("http://42.159.5.20/api2/auth-token/").build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        String str = response.body().string();
        int status = response.code();
        if (status == 200) {
            return str;
        } else {
            throw new RRException(status + str);
        }
    }

    private String getSpace(String username, String token) throws IOException {
        OkHttpClient okHttpClient = UsOkHttpUtil.getInstance().getOkHttpClient();
        RequestBody body = new FormBody.Builder().build();
        //必须使用管理账号token
        Request request = new Request.Builder().post(body).url("http://42.159.5.20/api2/accounts/" + username)
                .header("Authorization", "Token " + token)
                .header("Accept", "application/json; indent=4")
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        String str = response.body().string();
        int status = response.code();
        if (status == 200) {
            return str;
        } else {
            throw new RRException(status + str);
        }
    }

    @PostMapping("/api/storage/token")
    @ApiOperation("获取token")
    public R token(@org.springframework.web.bind.annotation.RequestBody UsSessionParam param) throws IOException {
        //表单校验
        ValidatorUtils.validateEntity(param);
        UsUserEntity user = getUser(param.getSession());
        if (user == null) {
            return R.error("查询不到此用户");
        }
        String token = getToken(user.getMobilePhone() + "@139.com", user.getMobilePhone() + "@139.com");
        return R.ok(JSONObject.parseObject(token));
    }

    @PostMapping("/api/storage/space")
    @ApiOperation("获取用户使用空间")
    public R space(@org.springframework.web.bind.annotation.RequestBody UsSessionParam param) throws IOException {
        //表单校验
        ValidatorUtils.validateEntity(param);
        UsUserEntity user = getUser(param.getSession());
        if (user == null) {
            return R.error("查询不到此用户");
        }
        String jsonToken = getToken("data@upp.com", "data@upp.com");
        JSONObject object = JSONObject.parseObject(jsonToken);
        String token = object.getString("token");
        String data = getSpace(user.getMobilePhone() + "@139.com", token);
        return R.ok(JSONObject.parseObject(data));
    }

    @PostMapping("/api/storage/create")
    @ApiOperation("创建用户空间")
    public R create(@org.springframework.web.bind.annotation.RequestBody UsSessionParam param) throws IOException {
        //表单校验
        ValidatorUtils.validateEntity(param);
        UsUserEntity user = getUser(param.getSession());
        if (user == null) {
            return R.error("查询不到此用户");
        }
        String jsonToken = getToken("data@upp.com", "data@upp.com");
        JSONObject object = JSONObject.parseObject(jsonToken);
        String token = object.getString("token");
        //创建空间
        OkHttpClient okHttpClient = UsOkHttpUtil.getInstance().getOkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("password", user.getMobilePhone() + "@139.com")
                .build();
        //必须使用管理账号token
        Request request = new Request.Builder().put(body).url("http://42.159.5.20/api2/accounts/" + user.getMobilePhone() + "@139.com" + "/")
                .header("Authorization", "Token " + token)
                .header("Accept", "application/json; indent=4")
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        String str = response.body().string();
        int status = response.code();
        //第一次创建返回201 第二次创建返回200
        if (status == 201 || status == 200) {
            return R.ok(JSONObject.parseObject(str));
        } else {
            throw new RRException(status + str);
        }
    }

    @Autowired
    public void setUserService(UsUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSessionUtil(UsSessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }
}
