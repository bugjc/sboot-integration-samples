package com.bugjc.web;

import com.bugjc.core.dto.Result;
import com.bugjc.core.dto.ResultGenerator;
import com.bugjc.core.util.HashUtil;
import com.bugjc.service.PublisherService;
import com.xiaoleilu.hutool.util.StrUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(value="redis发布/订阅相关接口",tags={"redis发布/订阅相关接口"})
@RestController
@RequestMapping
public class PublisherController {

    @Autowired
    private PublisherService publisherService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value="注册主题", notes="注册主题")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "channel", value = "主题（非中文）", required = true, dataType = "String")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "userId不能为空"),
            @ApiResponse(code = 500, message = "接口异常")}
    )
    @GetMapping(value = "register")
    public Result register(String channel){

        if (StrUtil.isEmpty(channel)){
            return ResultGenerator.genSuccessResult("无效的主题名称！");
        }

        if (!stringRedisTemplate.hasKey(genChannelKey(channel))){
            try {
                Long channelSize = stringRedisTemplate.boundValueOps(genChannelKey("channelSize")).increment(1);
                if (channelSize > 50){
                    return ResultGenerator.genFailResult("主题已超过50过！不允许注册了");
                }
                stringRedisTemplate.opsForSet().add(genChannelKey(channel), channel);
                return ResultGenerator.genSuccessResult("注册主题成功！请妥善保存好您的主题");
            }catch (Exception ex){
                stringRedisTemplate.boundValueOps(HashUtil.hashKeyForDisk("channelSize")).increment(-1);
                return ResultGenerator.genFailResult("注册主题失败！请妥善保存好您的主题");
            }
        }

        return ResultGenerator.genFailResult("主题已存在！");
    }

    /**
     * 生成主题通道key
     * @param channel
     * @return
     */
    @ApiIgnore
    public String genChannelKey(String channel){
        return channel+"-"+HashUtil.hashKeyForDisk(channel);
    }


    @ApiOperation(value="发送消息到指定主题", notes="发送消息到指定主题")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "channel", value = "主题（非中文）", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query",name = "msg", value = "消息", required = true, dataType = "String")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "userId不能为空"),
            @ApiResponse(code = 500, message = "接口异常")}
    )
    @PostMapping(value = "pub")
    public Result pubMsg(String channel, String msg){
        if (!stringRedisTemplate.hasKey(genChannelKey(channel))){
            return ResultGenerator.genFailResult("该主题还没有注册！请先调用注册接口");
        }
        publisherService.sendMsg(channel,msg);
        return ResultGenerator.genSuccessResult();
    }
}
