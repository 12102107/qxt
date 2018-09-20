package io.renren.modules.us.controller;

/**
 * @author gaoxipeng
 * @Date 2018/9/7 14:09
 * @Description 查询公交线路规划
 */
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.param.UsBusRouteDetilsParam;
import io.renren.modules.us.param.UsBusRouteParam;
import io.renren.modules.us.util.BusRouteUtils;
import io.renren.modules.us.util.UsOkHttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;


/**
 * 1.这个需要客户申请个高德API上面的key
 * */
@RestController
@RequestMapping("/api/bus")
@Api("公交路线查询")
public class BusRoutePlanController {
    private Logger logger = LoggerFactory.getLogger(BusRoutePlanController.class);

    @Value("${us.bus.key}")
    private String key;

    @Value("${us.bus.output}")
    private String output;

    @Value("${us.bus.city}")
    private String city;

    @Value("${us.bus.busUrl}")
    private String busUrl;

    @Value("${us.bus.geocodeUrl}")
    private String geocodeUrl;

    @Value("${us.bus.distanceUrl}")
    private String distanceUrl;

    private static String s = null;


    /**
     * @Author gaoxp
     * @Description   公交线路了查询
     * @Date 16:13 2018/9/7
     * @Param [form]
     * @return io.renren.common.utils.R
     **/
    @PostMapping("/busRouteList")
    @ApiOperation("公交路线查询")
    public String busrRouteList(@RequestBody UsBusRouteParam form) throws IOException {
        //表单校验
        ValidatorUtils.validateEntity(form);

        String start = form.getOrigin();
        String end = form.getDestination();
        //起点经纬度
        String startLonLat = getLonLat(start);
        //目的地经纬度
        String endLonLat =getLonLat(end);
        //公交换乘策略
        String strategy= form.getStrategy();
        //出发日期
        String date=form.getDate();
        //出发时间
        String time=form.getTime();
        //System.out.println("起始地："+start+",经纬度："+startLonLat);
        //System.out.println("终点："+end+",经纬度："+endLonLat);
        Long dis = getDistance(startLonLat,endLonLat);
        logger.info(start+" 到 "+end+"的距离为："+dis+"米");

        //公交线路
        String queryUrl =busUrl+"origin="+startLonLat+"&destination="+endLonLat+"&strategy="+strategy+"&date="+date+"&time="+time+"&city="+city+"&output="+output+"&key="+key+"";
        String response = getResponse(queryUrl);
        //解析从高德返回的json数据返回前端
        s = BusRouteUtils.busTaints(response,form.getStrategy());
        //线路查询去除多余参数segments
        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONObject route = jsonObject.getJSONObject("route");
        JSONArray transits1 = route.getJSONArray("transits");
        String busString = null;
        for (int i = 0; i <transits1.size() ; i++) {
            JSONObject jsonObject1 = transits1.getJSONObject(i);
            jsonObject1.remove("segments");
            jsonObject1.remove("distance");
            jsonObject1.remove("missed");
            jsonObject1.remove("nightflag");
        }
        busString = jsonObject.toString();
        logger.info("线路查询json:"+busString);

        return busString;

    }
    /**
     * @Author gaoxp
     * @Description   公交路线详情接口
     * @Date 10:32 2018/9/14
     * @Param [form]
     * @return io.renren.common.utils.R
     **/
    @PostMapping("/busRouteDetils")
    @ApiOperation("公交路线详情")
    public String busrRouteDetils(@RequestBody UsBusRouteDetilsParam form ){
        //表单校验
        ValidatorUtils.validateEntity(form);
        //解析从高德返回的json数据返回前端
        String detils = BusRouteUtils.busTaintDetils(s,form.getId());
        logger.info("线路详情json:"+detils);

        return detils;
    }


   /**
    * @Author gaoxp
    * @Description   地址转换为经纬度
    * @Date 10:36 2018/9/10
    * @Param [address]
    * @return java.lang.String
    **/
    private  String getLonLat(String address) throws IOException {
        //返回输入地址address的经纬度信息, 格式是 经度,纬度
        String queryUrl = geocodeUrl+"key="+key+"&city="+city+"&address="+address;
        String queryResult = getResponse(queryUrl);  //高德接口返回的是JSON格式的字符串
        //System.out.println("gao:"+queryResult);
        JSONObject job = JSONObject.parseObject(queryResult);
        JSONObject jobJSON = JSONObject.parseObject(job.get("geocodes").toString().substring(1, job.get("geocodes").toString().length()-1));
        String DZ = jobJSON.get("location").toString();
        return DZ;
    }
    /**
     * @Author gaoxp
     * @Description //经纬度算出两点间距离
     * @Date 11:06 2018/9/10
     * @Param [startLonLat, endLonLat]
     * @return long
     **/
    private  long getDistance(String startLonLat, String endLonLat) throws IOException {
        //返回起始地startAddr与目的地endAddr之间的距离，单位：米
        Long result = new Long(0);
        String queryUrl = distanceUrl+"key="+key+"&city="+city+"&origins="+startLonLat+"&destination="+endLonLat;
        String queryResult = getResponse(queryUrl);
        JSONObject job = JSONObject.parseObject(queryResult);
        JSONArray ja = job.getJSONArray("results");
        JSONObject jobO = JSONObject.parseObject(ja.getString(0));
        result = Long.parseLong(jobO.get("distance").toString());
        return result;
    }



    /**
     * @Author gaoxp
     * @Description   发送请求
     * @Date 10:50 2018/9/10
     * @Param [serverUrl]
     * @return java.lang.String
     **/
    private  String getResponse(String serverUrl) throws IOException {
        UsOkHttpUtil okHttpUtil = UsOkHttpUtil.getInstance();
        Response response = okHttpUtil.getDataSync(serverUrl);
        return response.body().string();
    }

}


