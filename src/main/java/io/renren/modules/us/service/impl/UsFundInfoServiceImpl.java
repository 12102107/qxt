package io.renren.modules.us.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.R;
import io.renren.modules.us.dao.UsFundInfoDao;
import io.renren.modules.us.entity.UsFundInfoEntity;
import io.renren.modules.us.param.UsFundInfoParam;
import io.renren.modules.us.service.UsFundInfoService;
import io.renren.modules.us.util.UsOkHttpUtil;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;


/**
 * @author Li
 */
@Service("usFundInfoService")
public class UsFundInfoServiceImpl extends ServiceImpl<UsFundInfoDao, UsFundInfoEntity> implements UsFundInfoService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public R info(UsFundInfoParam fundInfoParam) {
        String url = "http://10.66.68.15:8087/gateway/api/open/zggjjgrjbxx_hmrhggfwxm/1.0" +
                "?api_key=32303137303831363134353832353130373730302361306539646562622d376238342d343132622d623830652d6539a1f70ba3a3fd01c86ccb";
        url += "&ZGXM=" + fundInfoParam.getZGXM();
        url += "&SFZH=" + fundInfoParam.getSFZH();
        UsOkHttpUtil okHttpUtil = UsOkHttpUtil.getInstance();
        try {
            Response response = okHttpUtil.getDataSync(url);
            String result = response.body().string();
            JSONObject object = JSONObject.parseObject(result);
            if (!object.get("code").toString().equals("200")) {
                return R.error(object.get("msg").toString());
            } else {
                return R.ok(object.get("data"));
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return R.error();
        }
    }

}
