package eon.hg.fap.core.license;

import cn.hutool.core.convert.Convert;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DESede;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseContentException;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;
import eon.hg.fap.common.ip.ListNets;
import eon.hg.fap.core.constant.Globals;

import java.net.SocketException;

/**
 * Created by aeon on 2018/1/12.
 */
public class MacLicenseManager extends LicenseManager{

    public MacLicenseManager(LicenseParam licenseParam) {
        super(licenseParam);
    }

    protected synchronized void validate(LicenseContent licenseParams) throws LicenseContentException {
        System.out.println("授权认证信息："+ licenseParams.getInfo());
        System.out.println("认证有效期至："+licenseParams.getNotAfter());
        super.validate(licenseParams);
        String licensecode = licenseParams.getExtra().toString();

        try {
            DESede desede = SecureUtil.desede(Convert.hexToBytes(Globals.DEFAULT_3DES_KEY));
            String macAddress = desede.decryptStr(licensecode);
            if (!ListNets.validateMacAddress(macAddress)) {
                throw new LicenseContentException("MAC address verification failed");
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            throw new LicenseContentException("MAC address verification failed");
        }
    }


}
