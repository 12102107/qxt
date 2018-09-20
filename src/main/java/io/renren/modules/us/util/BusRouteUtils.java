package io.renren.modules.us.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gaoxipeng
 * @Date 2018/9/10 17:08
 * @Description 解析从高德返回的复杂json，以更友好的json格式返回客户端。查询下路接口调用的方法
 */
public class BusRouteUtils {

    private   static final int SECOND=3600;

    private   static Logger logger = LoggerFactory.getLogger(BusRouteUtils.class);


    public static String busTaints(String response,String strategy){
        String s ="";
        try {
            JSONObject jsonObject = JSONObject.parseObject(response);
            JSONObject route = jsonObject.getJSONObject("route");
            JSONArray transits = route.getJSONArray("transits");
            for (int i = 0; i <transits.size() ; i++) {
                JSONObject info = transits.getJSONObject(i);

                //高德返回walking_distance的value值为空的时候是数组形式，这里换乘字符串返回客服端
                if(info.getString("walking_distance").length()<=2){
                    info.put("walking_distance", "");
                }
                info.put("programme", "");
                String cost = info.getString("cost");
                String s1;
                if(cost.length()>2) {
                    int i2 = cost.indexOf(".");
                    String cost1 = cost.substring(0, i2);
                    StringBuffer stringBuffer3 = new StringBuffer();
                    stringBuffer3.append(cost1).append("元");
                    s1 = stringBuffer3.toString();
                }else {
                    s1 ="";
                }
                String duration = info.getString("duration");
                //将秒转化为分钟、小时的格式返回
                info.put("duration", getTimes(duration));
                //System.out.println("总时间:"+info.getString("duration"));
                JSONArray segments = info.getJSONArray("segments");
                //按条件查询去第出行方案，第一条即为最快或换乘少或步行少

                JSONObject jsonObject3 = transits.getJSONObject(0);
                if ("0".equals(strategy)) {
                    jsonObject3.put("programme", "最快");
                }else if ("2".equals(strategy)){
                    jsonObject3.put("programme", "换乘少");
                } else if ("3".equals(strategy)){
                    jsonObject3.put("programme", "步行少");
                }
                String departure_stop = "";  //在哪上车
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                for (int j = 0; j < segments.size() ; j++) {
                    JSONObject jsonObject1 = segments.getJSONObject(j);
                    //以下是乘坐地铁的相关字段，不必要返回前端
                    jsonObject1.remove("exit");
                    jsonObject1.remove("taxi");
                    jsonObject1.remove("railway");
                    jsonObject1.remove("entrance");
                        //防止walking数据为空报异常
                        if(jsonObject1.getString("walking").length()>2) {
                            JSONObject walking = jsonObject1.getJSONObject("walking");
                            if (walking.size() != 0) {
                                String duration2 = walking.getString("duration");
                                walking.put("duration", getTimes(duration2));
                                String duration3 = walking.getString("duration");
                                //System.out.println("步行时间:" + duration3);
                            }
                        }
                        JSONObject bus = jsonObject1.getJSONObject("bus");
                        if(bus.getString("buslines").length()>2) {
                        JSONArray buslines = bus.getJSONArray("buslines");
                        Map<String, Object> map = new HashMap<>();
                        StringBuffer stringBuffer = new StringBuffer();
                        for (int k = 0; k < buslines.size(); k++) {
                            JSONObject jsonOne = buslines.getJSONObject(k);
                            if (jsonOne.size() != 0) {
                                String duration3 = jsonOne.getString("duration");
                                jsonOne.put("duration", getTimes(duration3));
                                String duration4 = jsonOne.getString("duration");
                                //System.out.println("坐公交时间:" + duration4);

                                //在transits层添加具体换乘的所有公交线路
                                String name = jsonOne.getString("name");
                                int i1 = name.indexOf("(");
                                String substring = name.substring(0,i1);
                                stringBuffer.append(substring).append("/ ");
                            }

                            //将乘车起始站点departure_stop返回到transits层以便前端更好的读取
                            JSONObject One = segments.getJSONObject(0);
                            JSONObject bus1 = One.getJSONObject("bus");
                            JSONArray buslines1 = bus1.getJSONArray("buslines");
                            for (int l = 0; l < buslines1.size() ; l++) {
                                JSONObject jsonObject2 = buslines1.getJSONObject(0);
                                JSONObject departure_stop1 = jsonObject2.getJSONObject("departure_stop");
                                departure_stop = departure_stop1.getString("name");
                            }
                        }
                            stringBuffer.deleteCharAt(stringBuffer.length() - 2);
                            map.put("line", stringBuffer.toString().trim());
                            list.add(map);

                    }
                }
                //将list转化为jsonArray拼接到原有json中
                net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(list);
                //拼接起乘站点到json
                info.put("departure_stop", departure_stop);
                //拼接换乘路线到json
                info.put("bus_line", jsonArray);
                //拼接具体乘车路线id返回客户端，以便线路详情接口针对性的查询
                info.put("id", i + "");
                //eg:4.0,改成4元
                info.put("cost", s1);
            }
           ///线路查询去除多余参数segments
            JSONArray transits1 = route.getJSONArray("transits");
            for (int i = 0; i <transits1.size() ; i++) {
                JSONObject jsonObject1 = transits1.getJSONObject(i);
                jsonObject1.remove("distance");
                jsonObject1.remove("missed");
                jsonObject1.remove("nightflag");
            }
            s = jsonObject.toString();
            //生产环境此日志建议注释
            //logger.info("格式化后的json:"+s);

        }catch (RuntimeException e) {
            e.printStackTrace();
        }

        return s;

    }
    /**
     * @author gaoxipeng
     * @Date 2018/9/14 13:08
     * @Description 解析从高德返回的复杂json，以更友好的json格式返回客户端  线路详情接口调用的方法
     */
    public static String busTaintDetils(String s, String id) {
        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONObject route = jsonObject.getJSONObject("route");
        JSONArray transits = route.getJSONArray("transits");
        //id为多少证明就是查询该条线路详情
        JSONObject jsonObject1 = transits.getJSONObject(Integer.parseInt(id));

        //删除多余乘坐方案
        route.remove("transits");

        //添加查询方案乘坐详情
        route.put("transits", jsonObject1);

        //取新的transitsNew值
        JSONObject transitsNew = route.getJSONObject("transits");
        JSONArray segments = transitsNew.getJSONArray("segments");
        for (int i = 0; i < segments.size(); i++) {
            JSONObject jsonObject2 = segments.getJSONObject(i);

            JSONObject walking = jsonObject2.getJSONObject("walking");
            String distance = walking.getString("distance");
            //将步行distance放到segments对象中
            jsonObject2.put("distance", distance);
            //拼接换乘起始站
            JSONObject bus = jsonObject2.getJSONObject("bus");

            String line = ""; //具体乘坐的公交
            String departure_stop = ""; //起始站
            String arrival_stop = ""; //下车站
            String direction = ""; //此路公交开往方向
            String next_station = ""; //下一站
            String start_time = ""; //首班车时间
            String end_time = ""; //末班车时间
            String via_num = ""; //此路公交需要乘坐的战数
            String duration = ""; //乘坐此路公交需要话费的时间
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

            if(bus.getString("buslines").length()>2) {
                //取换乘线路value
                JSONArray bus_line = transitsNew.getJSONArray("bus_line");
                for (int j = 0; j < bus_line.size(); j++) {
                    JSONObject jsonObject3 = bus_line.getJSONObject(i);
                    line = jsonObject3.getString("line");
                    if (line.contains(" ")) {
                        int i1 = line.indexOf(" ");
                        line = line.substring(0, i1-1);
                    }

                }
            }

            JSONArray buslines1 = bus.getJSONArray("buslines");
            for (int l = 0; l < buslines1.size() ; l++) {
                JSONObject jsonObject3 = buslines1.getJSONObject(0);
                //上车地点
                JSONObject departure_stop1 = jsonObject3.getJSONObject("departure_stop");
                departure_stop = departure_stop1.getString("name");
                //此路公交的下车站
                JSONObject arrival_stop1 = jsonObject3.getJSONObject("arrival_stop");
                arrival_stop = arrival_stop1.getString("name");
                //开往哪个方向
                String name = jsonObject3.getString("name");
                int i1 = name.lastIndexOf("-");
                //删除")"
                String substring = name.substring(i1 + 1);
                direction = substring.substring(0, substring.length() - 1);
                //取下一站字段
                JSONArray via_stop = jsonObject3.getJSONArray("via_stops");
                for (int j = 0; j < via_stop.size(); j++) {
                    JSONObject jsonObject4 = via_stop.getJSONObject(0);
                    next_station = jsonObject4.getString("name");
                }
                //首班车时间
                String result = jsonObject3.getString("start_time");
                start_time = getString(result);
                //末班车时间
                String result1 = jsonObject3.getString("end_time");
                end_time = getString(result1);
                //此段途经公交站点列表
                JSONArray via_stops1 = jsonObject3.getJSONArray("via_stops");
                for (int j = 0; j < via_stops1.size(); j++) {
                    Map<String, Object> map = new HashMap<>();
                    JSONObject jsonObject4 = via_stops1.getJSONObject(j);
                    String name1 = jsonObject4.getString("name");
                    map.put("name", name1);
                    list.add(map);
                }

                //此路公交需要乘坐的站数(高德返回的站数需要加1才是正确的战数)
                int num = 1;
                via_num = jsonObject3.getString("via_num");
                int i2 = Integer.parseInt(via_num) + num;
                via_num = i2 + "站";

                //此路公交乘坐花费的时间
                duration = jsonObject3.getString("duration");

            }
            net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(list);
            jsonObject2.put("departure_stop", departure_stop);
            jsonObject2.put("arrival_stop", arrival_stop);
            jsonObject2.put("line", line);
            jsonObject2.put("direction",direction);
            jsonObject2.put("next_station",next_station);
            jsonObject2.put("start_time",start_time);
            jsonObject2.put("end_time",end_time);
            jsonObject2.put("via_stops",jsonArray);
            jsonObject2.put("via_num",via_num);
            jsonObject2.put("duration",duration);
            jsonObject2.put("open", false);
        }
        transitsNew.remove("departure_stop");
        transitsNew.remove("id");
        transitsNew.remove("programme");
        JSONArray segments1 = transitsNew.getJSONArray("segments");
        for (int i = 0; i < segments1.size(); i++) {
            JSONObject jsonObject2 = segments1.getJSONObject(i);
            jsonObject2.remove("bus");
            jsonObject2.remove("walking");
        }
        return jsonObject.toString();
    }


    /**
     * @Author gaoxp
     * @Description // 高德返回首末班车时间格式转换 eg:0530-->05:30
     * @Date 13:50 2018/9/19
     * @Param [result]
     * @return java.lang.String
     **/
    private static String getString(String result) {
        String star_end_time;
        if (result.length()>2) {
            String substring5 = result.substring(0, 2);
            String substring6 = result.substring(2);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(substring5).append(":").append(substring6);
            star_end_time = stringBuffer.toString();
        }else {
            star_end_time = "";
        }
        return star_end_time;
    }


    /**
     * @Author gaoxp
     * @Description 时间秒转化为分钟或者小时
     * @Date 15:14 2018/9/10
     * @Param  time
     * @return
     **/
    private   static  String getTimes(String time){
        if(Integer.parseInt(time)>=0 && Integer.parseInt(time)<SECOND){
            int newTime=Integer.parseInt(time)/60;
            String s = String.valueOf(newTime);
            return s+"分钟";
        }else{
            int hour,minute,second;
            hour = Integer.parseInt(time)/3600;
            minute = (Integer.parseInt(time) - hour*3600)/60;
            String hourOld=hour+"";
            String minuteOld=minute+"";
            return hourOld+"小时"+minuteOld+"分钟";
        }

    }


}
