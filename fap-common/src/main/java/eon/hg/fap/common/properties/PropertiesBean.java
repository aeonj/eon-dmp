package eon.hg.fap.common.properties;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class PropertiesBean {

    public static String UPLOAD_FOLDER;

    @Value("${spring.cache.type}")
    private String cache_type;

    @Value("${eon.hg.file.upload_folder}")
    public void setUploadFolder(String uploadFolder) {
        if (StrUtil.isNotBlank(uploadFolder)) {
            if (!uploadFolder.endsWith(File.separator)) {
                uploadFolder = uploadFolder+File.separator;
            }
            PropertiesBean.UPLOAD_FOLDER = uploadFolder;
        } else {
            PropertiesBean.UPLOAD_FOLDER = "D:/";
        }
    }

    public String getCache_type() {
        return cache_type;
    }
}
