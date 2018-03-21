package com.bugjc.web;
import com.bugjc.core.dto.Result;
import com.bugjc.core.dto.ResultGenerator;
import com.bugjc.model.Order;
import com.bugjc.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author aoki
 * @create 2018/03/20.
 */
@Api(value="多数据源事务测试",tags={"多数据源事务测试"})
@RestController
@RequestMapping("/tx")
public class TxController {
    @Resource
    private OrderService orderService;

    @ApiOperation(value="测试事务回滚", notes="测试事务回滚")
    @GetMapping("/testRollback")
    public Result testRollback() {
        orderService.testRollback();
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="测试循环事务回滚", notes="测试循环事务回滚")
    @GetMapping("/testLoopRollback")
    public Result testLoopRollback() {
        orderService.testLoopRollback(0);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="测试事务提交", notes="测试事务提交")
    @GetMapping("/testCommit")
    public Result testCommit() {
        orderService.testCommit();
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="测试循环事务提交", notes="测试循环事务提交")
    @GetMapping("/testLoopCommit")
    public Result testLoopCommit() {
        orderService.testLoopCommit(0);
        return ResultGenerator.genSuccessResult();
    }
}
