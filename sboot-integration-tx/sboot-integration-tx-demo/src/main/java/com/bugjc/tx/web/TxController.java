package com.bugjc.tx.web;

import com.bugjc.ha.task.RetryStrategyTimerUtil;
import com.bugjc.tx.core.dto.Result;
import com.bugjc.tx.core.dto.ResultGenerator;
import com.bugjc.tx.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
    @ApiImplicitParam(paramType = "query",name = "loopCount", value = "循环次数", required = true, dataType = "Integer",defaultValue = "0")
    @GetMapping("/testLoopRollback")
    public Result testLoopRollback(int loopCount) {
        orderService.testLoopRollback(loopCount);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="测试异步事务回滚", notes="测试异步事务回滚")
    @GetMapping("/testAsyncRollback")
    public Result testAsyncRollback() {
        Map<String,Object> map = new HashMap<>();
        map.put("targetService",orderService);
        map.put("targetMethodParam",new HashMap(){{
            put("type", "testRollback");
        }});
        RetryStrategyTimerUtil.addRetryStrategyTimer(map);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="测试异步循环事务回滚", notes="测试异步循环事务回滚")
    @ApiImplicitParam(paramType = "query",name = "loopCount", value = "循环次数", required = true, dataType = "Integer",defaultValue = "0")
    @GetMapping("/testAsyncLoopRollback")
    public Result testAsyncLoopRollback(int loopCount) {
        Map<String,Object> map = new HashMap<>();
        map.put("targetService",orderService);
        map.put("targetMethodParam",new HashMap(){{
            put("type", "testLoopRollback");
            put("loopCount", loopCount);
        }});
        RetryStrategyTimerUtil.addRetryStrategyTimer(map);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="测试事务提交", notes="测试事务提交")
    @GetMapping("/testCommit")
    public Result testCommit() {
        orderService.testCommit();
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="测试循环事务提交", notes="测试循环事务提交")
    @ApiImplicitParam(paramType = "query",name = "loopCount", value = "循环次数", required = true, dataType = "Integer",defaultValue = "0")
    @GetMapping("/testLoopCommit")
    public Result testLoopCommit(int loopCount) {
        orderService.testLoopCommit(loopCount);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="测试异步事务提交", notes="测试异步事务提交")
    @GetMapping("/testAsyncCommit")
    public Result testAsyncCommit() {
        Map<String,Object> map = new HashMap<>();
        map.put("targetService",orderService);
        map.put("targetMethodParam",new HashMap(){{
            put("type", "testCommit");
        }});
        RetryStrategyTimerUtil.addRetryStrategyTimer(map);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="测试异步循环事务提交", notes="测试异步循环事务提交")
    @ApiImplicitParam(paramType = "query",name = "loopCount", value = "循环次数", required = true, dataType = "Integer",defaultValue = "0")
    @GetMapping("/testAsyncLoopCommit")
    public Result testAsyncLoopCommit(int loopCount) {
        Map<String,Object> map = new HashMap<>();
        map.put("targetService",orderService);
        map.put("targetMethodParam",new HashMap(){{
            put("type", "testLoopCommit");
            put("loopCount", loopCount);
        }});
        RetryStrategyTimerUtil.addRetryStrategyTimer(map);
        return ResultGenerator.genSuccessResult();
    }

}
