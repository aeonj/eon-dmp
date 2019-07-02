package eon.hg.fap.core.license;

import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

/**
 * Created by aeon on 2018/1/12.
 */
public class LicenseManagerHolder {

    private static MacLicenseManager licenseManager;

    public static synchronized LicenseManager getLicenseManager(LicenseParam licenseParams) {
        if (licenseManager == null) {
            licenseManager = new MacLicenseManager(licenseParams);
        }
        return licenseManager;
    }
}
