package io.rqzs.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.rqzs.common.utils.PageUtils;
import io.rqzs.modules.sys.entity.CameraParameterEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-12-25 14:51:46
 */
public interface CameraParameterService extends IService<CameraParameterEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

