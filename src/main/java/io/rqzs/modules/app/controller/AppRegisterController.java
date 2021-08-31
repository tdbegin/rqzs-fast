/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.rqzs.io
 *
 * 版权所有，侵权必究！
 */

package io.rqzs.modules.app.controller;


import io.rqzs.common.utils.R;
import io.rqzs.common.validator.ValidatorUtils;
import io.rqzs.modules.app.entity.UserEntity;
import io.rqzs.modules.app.form.RegisterForm;
import io.rqzs.modules.app.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 注册
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/app")
@Api("APP注册接口")
public class AppRegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("register")
    @ApiOperation("注册")
    public R register(@RequestBody RegisterForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        UserEntity user = new UserEntity();
        user.setMobile(form.getMobile());
        user.setUsername(form.getMobile());
        user.setPassword(DigestUtils.sha256Hex(form.getPassword()));
        user.setCreateTime(new Date());
        userService.save(user);

        return R.ok();
    }
}
