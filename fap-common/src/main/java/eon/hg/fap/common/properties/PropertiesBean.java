package eon.hg.fap.common.properties;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesBean {

    public static String UPLOAD_FOLDER;

    @Value("${eon.hg.file.upload_folder}")
    public void setUploadFolder(String uploadFolder) {
        if (StrUtil.isNotBlank(uploadFolder)) {
            PropertiesBean.UPLOAD_FOLDER = uploadFolder;
        } else {
            PropertiesBean.UPLOAD_FOLDER = "D:/";
        }
    }
}