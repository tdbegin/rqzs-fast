package io.rqzs.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.rqzs.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.rqzs.modules.sys.entity.CameraParameterEntity;
import io.rqzs.modules.sys.service.CameraParameterService;
import io.rqzs.common.utils.PageUtils;
import io.rqzs.common.utils.R;



/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-12-25 14:51:46
 */
@RestController
@RequestMapping("sys/cameraparameter")
public class CameraParameterController {
    @Autowired
    private CameraParameterService cameraParameterService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:cameraparameter:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cameraParameterService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("findAll")
    @RequiresPermissions("sys:cameraparameter:list")
    public R list(){
        List<CameraParameterEntity> list = cameraParameterService.list();
        return R.ok().put("list",list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:cameraparameter:info")
    public R info(@PathVariable("id") Long id){
        CameraParameterEntity cameraParameter = cameraParameterService.getById(id);

        return R.ok().put("cameraParameter", cameraParameter);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:cameraparameter:save")
    public R save(@RequestBody CameraParameterEntity cameraParameter){
        cameraParameterService.save(cameraParameter);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:cameraparameter:update")
    public R update(@RequestBody CameraParameterEntity cameraParameter){
        ValidatorUtils.validateEntity(cameraParameter);
        cameraParameterService.updateById(cameraParameter);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:cameraparameter:delete")
    public R delete(@RequestBody Long[] ids){
        cameraParameterService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping("/savestart")
    public void saveCam(@RequestBody Map map){
        String id = String.valueOf(map.get("cameraId"));
        String inputFile = "rtsp://192.168.0."+ id +":8554/0";
        // Decodes-encodes
        String outputFile = "recordesave.mp4";
//        frameRecord(inputFile, outputFile,1);

        boolean isStart=true;//该变量建议设置为全局控制变量，用于控制录制结束
        // 获取视频源
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
        // 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
        //1280,960    640,480
//        grabber.setOption("rtsp_transport", "udp");
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, 640, 480, 1);
        // 开始取视频源
//        av_dict_set(null, "buffer_size", "1024000", 0);
        try {
            recordByFrame(grabber, recorder, isStart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void recordByFrame(FFmpegFrameGrabber grabber, FFmpegFrameRecorder recorder, Boolean status)
            throws Exception {
        try {//建议在线程中使用该方法
            grabber.start();
            recorder.start();
            Frame frame = null;

            long currentTime = System.currentTimeMillis();
            long currentTimes = currentTime + 10 * 60 * 1000;
            while (status&& (frame = grabber.grabFrame()) != null && currentTimes>System.currentTimeMillis()) {
                recorder.record(frame);
            }
            recorder.stop();
            grabber.stop();
        } finally {
            if (grabber != null) {
                grabber.stop();
            }
        }
    }

    @RequestMapping("/tortmp")
    public void rtspTortmp(@RequestBody Map map) throws Exception {
        String id = String.valueOf(map.get("cameraId"));
        String rtmpId = "rtmp://127.0.0.1/live/play";
        String rtspId =  "rtsp://192.168.0."+ id +":8554/0";
        int audioOpen =0;
        push(rtmpId,rtspId,audioOpen);
    }
    public static void push(String rtmpPath,String rtspPath,int audioOpen ) throws Exception  {
        boolean quit  = false;
        int width = 640,height = 480;
        FFmpegFrameGrabber grabber = FFmpegFrameGrabber.createDefault(rtspPath);
        grabber.setOption("rtsp_transport", "udp");
        grabber.setImageWidth(width);
        grabber.setImageHeight(height);
        System.out.println("grabber start");
        grabber.start();
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(rtmpPath,width,height, audioOpen);
        recorder.setInterleaved(true);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setFormat("flv"); // rtmp的类型
        recorder.setFrameRate(25);
        recorder.setImageWidth(width);recorder.setImageHeight(height);
        recorder.setPixelFormat(0); // yuv420p
        System.out.println("recorder start");
        recorder.start();
        //
        OpenCVFrameConverter.ToIplImage conveter = new OpenCVFrameConverter.ToIplImage();
        System.out.println("all start!!");
        int count = 0;
        while(!quit){
            count++;
            Frame frame = grabber.grabImage();
            if(frame == null){
                continue;
            }
            if(count % 100 == 0){
                System.out.println("count="+count);
            }
            recorder.record(frame);
        }

        grabber.stop();
        grabber.release();
        recorder.stop();
        recorder.release();
    }

}
