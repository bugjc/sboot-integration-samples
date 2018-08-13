package com.bugjc.jetcache.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员表
 * @author qingyang
 * @date 2018/8/12 07:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements Serializable {
    private int memberId;
    private String nickname;
    private int age;
    private Date createDate;
}
