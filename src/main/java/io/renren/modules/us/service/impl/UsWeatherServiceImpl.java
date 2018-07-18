package io.renren.modules.us.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.renren.common.utils.R;
import io.renren.common.utils.RedisUtils;
import io.renren.modules.us.param.UsWeatherParam;
import io.renren.modules.us.service.UsWeatherService;
import io.renren.modules.us.util.UsOkHttpUtil;
import net.sf.json.JSONObject;
import okhttp3.Response;

@Service("usWeatherService")
public class UsWeatherServiceImpl implements UsWeatherService {
	
	private RedisUtils redisUtil;;
	@Override
	public R getWeather(UsWeatherParam weatherParam) {
		
		 String weather="";	
		 String WEATHER_KEY_WEATHER = "hm:weather:";
		 JSONObject jsonObject = null;
		 String cityinfo = redisUtil.get(WEATHER_KEY_WEATHER+weatherParam.getCity());
	     if (cityinfo==null) {
	    	 try {
	    		    String url = "http://api.avatardata.cn/Weather/Query?key=f2e558064d0f4d57a06c4984d7857a60&cityname="+weatherParam.getCity();
					Response response = UsOkHttpUtil.getInstance().getDataSync(url);
					  String result = response.body().string();
					  
					  JSONObject dataOfJson = JSONObject.fromObject(result);
					  String resultdata = dataOfJson.getString("result");
					  dataOfJson = JSONObject.fromObject(resultdata);
					  String realtimedata = dataOfJson.getString("realtime");
					  dataOfJson = JSONObject.fromObject(realtimedata);
					  String city_name=dataOfJson.getString("city_name");//城市
					  String datetime=dataOfJson.getString("date")+" "+dataOfJson.getString("time");//日期时间
					  
					  JSONObject dataOfJson1 = JSONObject.fromObject(result);
					  String resultdata1 = dataOfJson1.getString("result");
					  dataOfJson1 = JSONObject.fromObject(resultdata1);
					  String weatherdata = dataOfJson1.getString("weather");
					  dataOfJson1 = JSONObject.fromObject(weatherdata.substring(1,weatherdata.length()-1));
					  String infodata = dataOfJson1.getString("info");
					  dataOfJson1 = JSONObject.fromObject(infodata);
					  String dawndata = dataOfJson1.getString("dawn");
					  String[] down = dawndata.split(",");
					  String daydata = dataOfJson1.getString("day");
					  String[] day = daydata.split(",");
					  String tem=down[2]+"-"+day[2];
					  String temperature=tem.replace("\"", "");//温度
					  
					  JSONObject dataOfJson2 = JSONObject.fromObject(result);
					  String resultdata2 = dataOfJson2.getString("result");
					  dataOfJson2 = JSONObject.fromObject(resultdata2);
					  String realtimedata1 = dataOfJson2.getString("realtime");
					  dataOfJson2 = JSONObject.fromObject(realtimedata1);
					  String winddata=dataOfJson2.getString("wind");
					  dataOfJson2 = JSONObject.fromObject(winddata);
					  String directpower=dataOfJson2.getString("direct")+dataOfJson2.getString("power");//风向风力
					  
	                  JSONObject dataOfJson3 = JSONObject.fromObject(result);
					  String resultdata3 = dataOfJson3.getString("result");
					  dataOfJson3 = JSONObject.fromObject(resultdata3);
					  String realtimedata2 = dataOfJson3.getString("realtime");
					  dataOfJson3 = JSONObject.fromObject(realtimedata2);
					  String weatherdata1=dataOfJson3.getString("weather");
					  dataOfJson3 = JSONObject.fromObject(weatherdata1);
					  String humidity=dataOfJson3.getString("humidity");//湿度
					  String img=dataOfJson3.getString("img");//天气图片
					  String info=dataOfJson3.getString("info");//天气情况
	                  
					  //天气预报
	                  weather="{city_name:\""+city_name+"\",datetime:\""+datetime+"\",temperature:\""+temperature+"\",img:\""+img+"\",info:\""+info+"\",humidity:\""+humidity+"\",directpower:\""+directpower+"\"}";
	                  //redis保存
					  redisUtil.set(WEATHER_KEY_WEATHER+weatherParam.getCity(),weather);
					  jsonObject = JSONObject.fromObject(weather);//字符串转json
					  //System.out.println(jsonObject);
					  
				} catch (IOException e) {
					e.printStackTrace();
				}
			     
		}
	     if(cityinfo!=null){
	    	 String information = redisUtil.get(WEATHER_KEY_WEATHER+weatherParam.getCity());
	    	 JSONObject jsonObjectinfo = JSONObject.fromObject(information);//字符串转json
	    	 String datetimen=jsonObjectinfo.getString("datetime");
	    	 
	    	 Date dayn=new Date();    
	    	 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    	 try {
				Date time=df.parse(datetimen);
				long t1 = time.getTime();  
			    long t2 = dayn.getTime(); 
			    int hours = (int) ((t2 - t1) / (1000 * 60 * 60));
                if (hours>=1) {
					String url = "http://api.avatardata.cn/Weather/Query?key=f2e558064d0f4d57a06c4984d7857a60&cityname="+weatherParam.getCity();
					Response response;
					try {
						response = UsOkHttpUtil.getInstance().getDataSync(url);
						String result = response.body().string();
						  
						  JSONObject dataOfJson = JSONObject.fromObject(result);
						  String resultdata = dataOfJson.getString("result");
						  dataOfJson = JSONObject.fromObject(resultdata);
						  String realtimedata = dataOfJson.getString("realtime");
						  dataOfJson = JSONObject.fromObject(realtimedata);
						  String city_name=dataOfJson.getString("city_name");//城市
						  String datetime=dataOfJson.getString("date")+" "+dataOfJson.getString("time");//日期时间
						  
						  JSONObject dataOfJson1 = JSONObject.fromObject(result);
						  String resultdata1 = dataOfJson1.getString("result");
						  dataOfJson1 = JSONObject.fromObject(resultdata1);
						  String weatherdata = dataOfJson1.getString("weather");
						  dataOfJson1 = JSONObject.fromObject(weatherdata.substring(1,weatherdata.length()-1));
						  String infodata = dataOfJson1.getString("info");
						  dataOfJson1 = JSONObject.fromObject(infodata);
						  String dawndata = dataOfJson1.getString("dawn");
						  String[] down = dawndata.split(",");
						  String daydata = dataOfJson1.getString("day");
						  String[] day = daydata.split(",");
						  String tem=down[2]+"-"+day[2];
						  String temperature=tem.replace("\"", "");//温度
						  
						  JSONObject dataOfJson2 = JSONObject.fromObject(result);
						  String resultdata2 = dataOfJson2.getString("result");
						  dataOfJson2 = JSONObject.fromObject(resultdata2);
						  String realtimedata1 = dataOfJson2.getString("realtime");
						  dataOfJson2 = JSONObject.fromObject(realtimedata1);
						  String winddata=dataOfJson2.getString("wind");
						  dataOfJson2 = JSONObject.fromObject(winddata);
						  String directpower=dataOfJson2.getString("direct")+dataOfJson2.getString("power");//风向风力
						  
		                  JSONObject dataOfJson3 = JSONObject.fromObject(result);
						  String resultdata3 = dataOfJson3.getString("result");
						  dataOfJson3 = JSONObject.fromObject(resultdata3);
						  String realtimedata2 = dataOfJson3.getString("realtime");
						  dataOfJson3 = JSONObject.fromObject(realtimedata2);
						  String weatherdata1=dataOfJson3.getString("weather");
						  dataOfJson3 = JSONObject.fromObject(weatherdata1);
						  String humidity=dataOfJson3.getString("humidity");//湿度
						  String img=dataOfJson3.getString("img");//天气图片
						  String info=dataOfJson3.getString("info");//天气情况
		                  
						  //天气预报
		                  weather="{city_name:\""+city_name+"\",datetime:\""+datetime+"\",temperature:\""+temperature+"\",img:\""+img+"\",info:\""+info+"\",humidity:\""+humidity+"\",directpower:\""+directpower+"\"}";
		                  //redis保存
						  redisUtil.set(WEATHER_KEY_WEATHER+weatherParam.getCity(),weather);
						  jsonObject = JSONObject.fromObject(weather);//字符串转json
					} catch (IOException e) {
						e.printStackTrace();
					}
					  
				} else {
					weather=redisUtil.get(WEATHER_KEY_WEATHER+weatherParam.getCity());
					jsonObject = JSONObject.fromObject(weather);//字符串转json
				}
				} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	     Map<String, Object> map = new HashMap<>();
         map.put("weather", jsonObject);
         return R.ok(map);
	}
	   @Autowired
	    public void setRedisUtil(RedisUtils redisUtil) {
	        this.redisUtil = redisUtil;
	    }

}
