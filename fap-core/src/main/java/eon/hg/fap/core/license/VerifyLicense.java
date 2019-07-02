package eon.hg.fap.core.license;

import de.schlichtherle.license.*;
import eon.hg.fap.common.properties.PropertiesFactory;
import eon.hg.fap.common.properties.PropertiesHelper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.prefs.Preferences;

/**
 * Created by aeon on 2018/1/12.
 */
@Slf4j
public class VerifyLicense {
    public PropertiesHelper prop = PropertiesFactory.getPropertiesHelper("license");
    private LicenseManager licenseManager;
    private boolean isinstall = false;

    //common param
    private String PUBLICALIAS = "";
    private String STOREPWD = "";
    private String SUBJECT = "";
    private String licPath = "";
    private String pubPath = "";

    public VerifyLicense() {
        PUBLICALIAS = prop.getValue("PUBLICALIAS");
        STOREPWD = prop.getValue("STOREPWD");
        SUBJECT = prop.getValue("SUBJECT");
        licPath = prop.getValue("licPath");
        pubPath = prop.getValue("pubPath");
        this.licenseManager = LicenseManagerHolder
                .getLicenseManager(initLicenseParams());
    }

    public boolean isinstall() {
        return isinstall;
    }

    public boolean install(String path) {
        // 安装证书
        try {
            licenseManager.install(new File(path+licPath));
            log.info("客户端安装证书成功!");
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("客户端证书安装失败!");
            return false;
        }
        isinstall = true;
        return true;
    }

    public boolean verify() {
        if (isinstall) {
            // 验证证书
            try {
                licenseManager.verify();
                log.info("客户端验证证书成功!");
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("客户端证书验证失效!");
                return false;
            }
            return true;
        } else {
            System.out.println("客户端证书安装失败，请重启服务!");
            return false;
        }
    }

    // 返回验证证书需要的参数
    private LicenseParam initLicenseParams() {
        Preferences preference = Preferences
                .userNodeForPackage(VerifyLicense.class);
        CipherParam cipherParam = new DefaultCipherParam(STOREPWD);

        KeyStoreParam privateStoreParam = new DefaultKeyStoreParam(
                VerifyLicense.class, "/"+pubPath, PUBLICALIAS, STOREPWD, null);
        LicenseParam licenseParams = new DefaultLicenseParam(SUBJECT,
                preference, privateStoreParam, cipherParam);
        return licenseParams;
    }
}
