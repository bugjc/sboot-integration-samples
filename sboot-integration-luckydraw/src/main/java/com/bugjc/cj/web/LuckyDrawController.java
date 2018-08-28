package com.bugjc.cj.web;

import com.bugjc.cj.service.LogicService;
import com.bugjc.cj.core.dto.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther: qingyang
 * @Date: 2018/6/6 09:18
 * @Description:
 */
@RequestMapping("/lucky/draw")
@RestController
public class LuckyDrawController {

    @Resource
    private LogicService logicService;

    @GetMapping("{userId}")
    public Result luckyDraw(@PathVariable String userId){
        return logicService.luckyDraw(userId);
    }

    @GetMapping("query/{queryId}")
    public Result queryLuckDraw(@PathVariable String queryId){
        return logicService.queryLuckDraw(queryId);
    }

}
