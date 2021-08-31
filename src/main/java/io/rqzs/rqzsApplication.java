/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.rqzs.io
 *
 * 版权所有，侵权必究！
 */

package io.rqzs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class rqzsApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类
		return builder.sources(rqzsApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(rqzsApplication.class, args);
	}

}