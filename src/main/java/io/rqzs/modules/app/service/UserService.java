/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.rqzs.io
 *
 * 版权所有，侵权必究！
 */

package io.rqzs.modules.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.rqzs.modules.app.entity.UserEntity;
import io.rqzs.modules.app.form.LoginForm;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface UserService extends IService<UserEntity> {

	UserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回用户ID
	 */
	long login(LoginForm form);
}
