package com.bugjc.logic.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.bugjc.grocery.service.impl.AwardSinkComponent;
import com.bugjc.logic.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: qingyang
 * @Date: 2018/6/6 18:03
 * @Description:
 */
@Service
public class AwardServiceImpl implements AwardService {

    @Autowired
    private AwardSinkComponent awardSinkComponent;

    //奖品列表
    static List<JSONObject> list = new ArrayList(){{
        add(new JSONObject(){{
            put("awardName","现金100元");
        }});
        add(new JSONObject(){{
            put("awardName","现金15元");
        }});
        add(new JSONObject(){{
            put("awardName","100元优惠券");
        }});
        add(new JSONObject(){{
            put("awardName","10点积分");
        }});
        add(new JSONObject(){{
            put("awardName","20点积分");
        }});
    }};

    @Override
    public List<JSONObject> findAwardList() {
        return list;
    }

    @Override
    public JSONObject drawAward() {

        int total = awardSinkComponent.decrementAndGet();
        if (total < 0){
            return null;
        }

        //随机抽奖，抽到即从奖品列表中移除
        List<JSONObject> awardList = this.findAwardList();
        if (awardList.isEmpty()){
            return null;
        }

        int index = RandomUtil.randomInt(0,awardList.size());
        JSONObject jsonObject = awardList.get(index);
        list.remove(index);
        return jsonObject;
    }

}