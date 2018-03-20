package com.bugjc.web;
import com.bugjc.core.dto.Result;
import com.bugjc.core.dto.ResultGenerator;
import com.bugjc.model.Order;
import com.bugjc.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author aoki
 * @create 2018/03/20.
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    @GetMapping("/testRollback")
    public Result testRollback() {
        orderService.testRollback();
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/testLoopRollback")
    public Result testLoopRollback() {
        orderService.testLoopRollback(0);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/testCommit")
    public Result testCommit() {
        orderService.testCommit();
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/testLoopCommit")
    public Result testLoopCommit() {
        orderService.testLoopCommit(0);
        return ResultGenerator.genSuccessResult();
    }
}
