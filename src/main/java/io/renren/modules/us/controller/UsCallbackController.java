package io.renren.modules.us.controller;

import io.renren.common.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 *
 * @author gaoxipeng
 * @email
 * @date 2018-07-31 13:47:28
 */
@RestController
@RequestMapping("/api/meth")
@Api("回调函数接口")
public class UsCallbackController {
    @Autowired
    private RedisUtils redisUtils;
    
    @GetMapping("callback")
    @ApiOperation("回调函数接口")
    public void getUrl(HttpServletRequest request) {
        try {
            InputStream sin = new BufferedInputStream(request.getInputStream());
            ByteArrayOutputStream sout = new ByteArrayOutputStream();
            int b=0;
            while((b=sin.read())!=-1)
            {
                sout.write(b);
            }
            byte[] temp = sout.toByteArray();
            String s_ok = new String(temp,"UTF-8");
            //获取业务eid
            String eid = request.getParameter("eid");
            //将业务id和回调结果存到redis中
            redisUtils.setTimes(eid, s_ok);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
