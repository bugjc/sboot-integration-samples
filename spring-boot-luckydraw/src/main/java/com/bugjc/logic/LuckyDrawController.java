package com.bugjc.logic;

import com.bugjc.logic.service.LogicService;
import com.bugjc.util.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @Auther: qingyang
 * @Date: 2018/6/6 09:18
 * @Description:
 */
@RequestMapping("/lucky/draw")
@RestController
public class LuckyDrawController {

    @Autowired
    private LogicService logicService;

    @PostMapping
    public Result luckyDraw(@Valid @NotBlank(message = "用户编号不能为空") String userId){
        return logicService.luckyDraw(userId);
    }

    @GetMapping("query/{queryId}")
    public Result queryLuckDraw(@PathVariable String queryId){
        return logicService.queryLuckDraw(queryId);
    }

}
