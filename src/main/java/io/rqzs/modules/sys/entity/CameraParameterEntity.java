package io.rqzs.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-12-25 14:51:46
 */
@Data
@TableName("camera_parameter")
public class CameraParameterEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 摄像头id
	 */
	private Integer cameraId;
	/**
	 * 地址值
	 */
	private String cameraAddress;
	/**
	 * 名称
	 */
	private String cameraName;
	/**
	 * 协议
	 */
	private String cameraProtocol;
	/**
	 * 状态 0：禁用 1：正常
	 */
	private Integer cameraStatus;
	/**
	 * 保存时长
	 */
	private Date saveDay;
	/**
	 * 
	 */
	private String username;
	/**
	 * 
	 */
	private String password;

}
