package com.bugjc.web;
import com.bugjc.core.dto.Result;
import com.bugjc.core.dto.ResultGenerator;
import com.bugjc.model.Test1;
import com.bugjc.service.Test1Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by aoki on 2017/11/06.
*/
@RestController
@RequestMapping("/test1")
@Api(value="test",tags={"test"})
public class Test1Controller {
    @Resource
    private Test1Service test1Service;

    @GetMapping("/test")
    public Result get() {
        test1Service.test();
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/add")
    public Result add(Test1 test1) {
        test1Service.save(test1);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        test1Service.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Test1 test1) {
        test1Service.update(test1);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Test1 test1 = test1Service.findById(id);
        return ResultGenerator.genSuccessResult(test1);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Test1> list = test1Service.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
