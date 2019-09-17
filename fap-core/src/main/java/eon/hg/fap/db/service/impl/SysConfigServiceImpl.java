package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.cache.AbstractCacheOperator;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.db.dao.primary.SysConfigDao;
import eon.hg.fap.db.model.primary.Accessory;
import eon.hg.fap.db.model.primary.SysConfig;
import eon.hg.fap.db.service.ISysConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class SysConfigServiceImpl extends AbstractCacheOperator implements ISysConfigService {
	@Resource
	private SysConfigDao sysConfigDao;

	public SysConfig save(SysConfig sysConfig) {
		this.reset();
		return this.sysConfigDao.save(sysConfig);
	}

	public SysConfig getObjById(Long id) {
		SysConfig sysConfig = this.sysConfigDao.get(id);
		if (sysConfig != null) {
			return sysConfig;
		}
		return null;
	}

	public void delete(Long id) {
		this.reset();
		this.sysConfigDao.remove(id);
	}

	public SysConfig update(SysConfig sysConfig) {
		this.reset();
		return this.sysConfigDao.update( sysConfig);
	}
	
	@Transactional(readOnly = true)
	public SysConfig getSysConfig() {
		try {
			SysConfig sc = this.getCache();
			return sc;
		} catch (Exception e) {
			return getObject();
		}
	}

	@Override
	public String getCacheId(Object... params) {
		return "sys_config";
	}

	@Override
	public String getKey(Object... params) {
		return "config";
	}

	@Override
	public SysConfig getObject(Object... params) {
		List<SysConfig> configs = this.sysConfigDao.findAll();
		if (configs != null && configs.size() > 0) {
			SysConfig sc = configs.get(0);
			if (sc.getUploadFilePath() == null) {
				sc.setUploadFilePath(Globals.UPLOAD_FILE_PATH);
			}
			if (sc.getSysLanguage() == null) {
				sc.setSysLanguage(Globals.DEFAULT_SYSTEM_LANGUAGE);
			}
			if (sc.getWebsiteName() == null || sc.getWebsiteName().equals("")) {
				sc.setWebsiteName(Globals.DEFAULT_WBESITE_NAME);
			}
			if (sc.getCloseReason() == null || sc.getCloseReason().equals("")) {
				sc.setCloseReason(Globals.DEFAULT_CLOSE_REASON);
			}
			if (sc.getTitle() == null || sc.getTitle().equals("")) {
				sc.setTitle(Globals.DEFAULT_SYSTEM_TITLE);
			}
			if (sc.getImageSaveType() == null
					|| sc.getImageSaveType().equals("")) {
				sc.setImageSaveType(Globals.DEFAULT_IMAGESAVETYPE);
			}
			if (sc.getImageFilesize() == 0) {
				sc.setImageFilesize(Globals.DEFAULT_IMAGE_SIZE);
			}
			if (sc.getSmallWidth() == 0) {
				sc.setSmallWidth(Globals.DEFAULT_IMAGE_SMALL_WIDTH);
			}
			if (sc.getSmallHeight() == 0) {
				sc.setSmallHeight(Globals.DEFAULT_IMAGE_SMALL_HEIGH);
			}
			if (sc.getMiddleWidth() == 0) {
				sc.setMiddleWidth(Globals.DEFAULT_IMAGE_MIDDLE_WIDTH);
			}
			if (sc.getMiddleHeight() == 0) {
				sc.setMiddleHeight(Globals.DEFAULT_IMAGE_MIDDLE_HEIGH);
			}
			if (sc.getBigHeight() == 0) {
				sc.setBigHeight(Globals.DEFAULT_IMAGE_BIG_HEIGH);
			}
			if (sc.getBigWidth() == 0) {
				sc.setBigWidth(Globals.DEFAULT_IMAGE_BIG_WIDTH);
			}
			if (sc.getImageSuffix() == null || sc.getImageSuffix().equals("")) {
				sc.setImageSuffix(Globals.DEFAULT_IMAGE_SUFFIX);
			}
			if (sc.getSecurityCodeType() == null
					|| sc.getSecurityCodeType().equals("")) {
				sc.setSecurityCodeType(Globals.SECURITY_CODE_TYPE);
			}
			if (sc.getSmsURL() == null || sc.getSmsURL().equals("")) {
				sc.setSmsURL(Globals.DEFAULT_SMS_URL);
			}
			if (sc.getMemberIcon() == null) {
				Accessory memberIcon = new Accessory();
				memberIcon.setPath("resources/style/common/images");
				memberIcon.setName("member.jpg");
				sc.setMemberIcon(memberIcon);
			}
			if (sc.getWebsiteLogo() == null) {
				Accessory logoIcon = new Accessory();
				logoIcon.setPath("resources/style/common/images");
				logoIcon.setName("logo.ico");
				sc.setWebsiteLogo(logoIcon);
			}
			return sc;
		} else {
			SysConfig sc = new SysConfig();
			sc.setUploadFilePath(Globals.UPLOAD_FILE_PATH);
			sc.setWebsiteName(Globals.DEFAULT_WBESITE_NAME);
			sc.setSysLanguage(Globals.DEFAULT_SYSTEM_LANGUAGE);
			sc.setTitle(Globals.DEFAULT_SYSTEM_TITLE);
			sc.setSecurityCodeType(Globals.SECURITY_CODE_TYPE);
			sc.setEmailEnable(Globals.EAMIL_ENABLE);
			sc.setCloseReason(Globals.DEFAULT_CLOSE_REASON);
			sc.setImageSaveType(Globals.DEFAULT_IMAGESAVETYPE);
			sc.setImageFilesize(Globals.DEFAULT_IMAGE_SIZE);
			sc.setSmallWidth(Globals.DEFAULT_IMAGE_SMALL_WIDTH);
			sc.setSmallHeight(Globals.DEFAULT_IMAGE_SMALL_HEIGH);
			sc.setMiddleHeight(Globals.DEFAULT_IMAGE_MIDDLE_HEIGH);
			sc.setMiddleWidth(Globals.DEFAULT_IMAGE_MIDDLE_WIDTH);
			sc.setBigHeight(Globals.DEFAULT_IMAGE_BIG_HEIGH);
			sc.setBigWidth(Globals.DEFAULT_IMAGE_BIG_WIDTH);
			sc.setImageSuffix(Globals.DEFAULT_IMAGE_SUFFIX);
			sc.setSmsURL(Globals.DEFAULT_SMS_URL);
            Accessory memberIcon = new Accessory();
            memberIcon.setPath("resources/style/common/images");
            memberIcon.setName("member.jpg");
            sc.setMemberIcon(memberIcon);
            Accessory logoIcon = new Accessory();
            logoIcon.setPath("resources/style/common/images");
            logoIcon.setName("logo.ico");
            sc.setWebsiteLogo(logoIcon);
			sc.setDb_reset(true);
			this.save(sc);
			return sc;
		}
	}
}
