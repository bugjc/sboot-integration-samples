package com.bugjc.web;

import com.bugjc.core.dto.Result;
import com.bugjc.core.dto.ResultGenerator;
import io.swagger.annotations.*;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* Created by aoki on 2018/01/25.
*/
@Api(value="TEST管理",tags={"TEST管理"})
@Scope("prototype")
@RestController
@RequestMapping("/behavior")
public class TestController {

    @ApiOperation(value="XXXXXX", notes="XXXXX")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",dataType = "String",name = "Signature",value = "RSA-SHA1签名",required = true),
            @ApiImplicitParam(paramType = "path",dataType = "Integer",name = "userId",value = "会员ID",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "businessId",value = "业务ID",required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "添加成功"),
            @ApiResponse(code = 400, message = "对象参数名错误或值格式错误"),
            @ApiResponse(code = 500, message = "接口异常")}
    )
    @PostMapping("/add")
    public Result add() {
        return ResultGenerator.genSuccessResult();
    }

}
