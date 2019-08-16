package eon.hg.fap.web.manage.action;

import cn.hutool.core.convert.Convert;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DESede;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.ip.ListNets;
import eon.hg.fap.common.properties.PropertiesFactory;
import eon.hg.fap.common.properties.PropertiesHelper;
import eon.hg.fap.core.body.ResultBody;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.common.util.tools.FileHandler;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.db.service.ISysConfigService;
import eon.hg.fap.db.service.IUserConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

@Controller
@RequestMapping("/manage")
public class LicenseAction {
    public PropertiesHelper propLicense = PropertiesFactory.getPropertiesHelper("license");
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private IUserConfigService userConfigService;

    @RequestMapping("/license.htm")
    public ModelAndView license(HttpServletRequest request,
                                HttpServletResponse response) throws SocketException, UnknownHostException {
        String mac = ListNets.getLocalMacAddress(request.getRemoteAddr());
        if (CommUtil.isEmpty(mac)) {
            mac = ListNets.getLocalMacAddress();
        }
        if (CommUtil.isEmpty(mac)) {
            //未能获取到网卡Mac地址
        }
        DESede desede = SecureUtil.desede(Convert.hexToBytes(Globals.DEFAULT_3DES_KEY));
        ModelAndView mv = new JModelAndView("license.html",
                configService.getSysConfig(),
                this.userConfigService.getUserConfig(), 0, request, response);
        mv.addObject("licensecode", desede.encryptHex(mac).toUpperCase());
        return mv;
    }

    @PostMapping("/license_upload.htm")
    public ResponseEntity<ResultBody> license_upload(@RequestParam("file") MultipartFile file,
                                                     RedirectAttributes redirectAttributes) {
        try {
            Dto dtoFile = FileHandler.saveFileToServer(file,"",propLicense.getValue("licPath"),"lic");
            if (CommUtil.isNotEmpty(dtoFile.get("fileName"))) {
                if (AeonConstants.VLicense.install(dtoFile.getString("filePath"))) {
                    Dto dto = new HashDto();
                    dto.put("redirect", "/manage/index.htm");
                    return new ResponseEntity(ResultBody.success().addObject(dto), HttpStatus.OK);
                } else {
                    return new ResponseEntity(ResultBody.failed("无效的授权文件，证书安装失败！"), HttpStatus.OK);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(ResultBody.failed(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
