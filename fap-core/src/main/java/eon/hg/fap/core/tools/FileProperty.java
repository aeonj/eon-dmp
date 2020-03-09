package eon.hg.fap.core.tools;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import eon.hg.fap.common.properties.PropertiesBean;
import eon.hg.fap.core.security.SecurityUserHolder;

import java.io.File;

public class FileProperty {

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
}
