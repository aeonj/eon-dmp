package eon.hg.fap.core.tools;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import eon.hg.fap.common.properties.PropertiesBean;
import eon.hg.fap.core.security.SecurityUserHolder;

import java.io.File;

public class FileProperty {

    /**
     * 根据关键字生成基于eon.hg.file.upload_folder属性配置的相对路径
     * @param key
     * @param isCreate
     * @return
     */
    public static String getUploadPath(String key, boolean isCreate) {
        StringBuilder paths = new StringBuilder();
        if (StrUtil.isNotBlank(key)) {
            paths.append(key).append(File.separator);
        }
        if (SecurityUserHolder.getOnlineUser()!=null) {
            paths.append(SecurityUserHolder.getOnlineUser().getUserid()).append(File.separator);
        } else {
            paths.append("no_login").append(File.separator);
        }
        paths.append(DateUtil.today()).append(File.separator);
        if (isCreate) {
            FileUtil.touch(PropertiesBean.UPLOAD_FOLDER+paths.toString());
        }
        return paths.toString();
    }

    /**
     * 根据关键字生成基于eon.hg.file.upload_folder属性配置的绝对路径
     * @param key
     * @param isCreate
     * @return
     */
    public static String getAbsUploadPath(String key, boolean isCreate) {
        return PropertiesBean.UPLOAD_FOLDER+getUploadPath(key,isCreate);
    }

}
