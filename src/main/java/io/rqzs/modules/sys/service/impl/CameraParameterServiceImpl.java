package io.rqzs.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rqzs.common.utils.PageUtils;
import io.rqzs.common.utils.Query;

import io.rqzs.modules.sys.dao.CameraParameterDao;
import io.rqzs.modules.sys.entity.CameraParameterEntity;
import io.rqzs.modules.sys.service.CameraParameterService;


@Service("cameraParameterService")
public class CameraParameterServiceImpl extends ServiceImpl<CameraParameterDao, CameraParameterEntity> implements CameraParameterService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CameraParameterEntity> page = this.page(
                new Query<CameraParameterEntity>().getPage(params),
                new QueryWrapper<CameraParameterEntity>()
        );

        return new PageUtils(page);
    }

}
