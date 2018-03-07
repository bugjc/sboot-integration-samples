package com.bugjc.web;
import com.bugjc.core.dto.Result;
import com.bugjc.core.dto.ResultGenerator;
import com.bugjc.model.Test2;
import com.bugjc.service.Test2Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by aoki on 2017/11/06.
*/
@RestController
@RequestMapping("/test2")
public class Test2Controller {
    @Resource
    private Test2Service test2Service;

    @PostMapping("/add")
    public Result add(Test2 test2) {
        test2Service.save(test2);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        test2Service.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Test2 test2) {
        test2Service.update(test2);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Test2 test2 = test2Service.findById(id);
        return ResultGenerator.genSuccessResult(test2);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Test2> list = test2Service.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
