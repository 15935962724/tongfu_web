package com.tongfu.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author wuyongao
 * @Description 系统信息配置 可添加配置参数get,set 方法
 * @Date 2022/08/15/18:08
 * @Version 1.0
 */
@Component
public class SystemPropertiesUtil {
    /**
     * 附件图片路径
     */
    private String fileWebPath;

    public String getFileWebPath() {
        return fileWebPath;
    }
    @Value("${file-web-path}")
    public void setFileWebPath(String fileWebPath) {
        this.fileWebPath = fileWebPath;
    }
}
