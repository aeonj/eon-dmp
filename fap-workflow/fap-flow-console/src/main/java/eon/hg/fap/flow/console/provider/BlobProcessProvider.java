package eon.hg.fap.flow.console.provider;

import eon.hg.fap.db.model.primary.Accessory;
import eon.hg.fap.db.service.IAccessoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BlobProcessProvider implements ProcessProvider {
    private String prefix="blob:";
    @Autowired
    private IAccessoryService accessoryService;

    @Override
    public InputStream loadProcess(String fileName) {
        if(fileName.startsWith(prefix)){
            fileName=fileName.substring(prefix.length(),fileName.length());
        }
        Accessory accessory = accessoryService.getObjByProperty("acc_type",11,"name",fileName);
        InputStream is = new ByteArrayInputStream(accessory.getBlob_value());
        return is;
    }

    @Override
    public List<ProcessFile> loadAllProcesses() {
        List<Accessory> accessoryList = accessoryService.query("select obj from Accessory obj where acc_type=11",null,-1,-1);
        List<ProcessFile> pfs = new ArrayList<>();
        for (Accessory accessory : accessoryList) {
            ProcessFile pf = new ProcessFile(accessory.getName(),accessory.getAddTime());
            pfs.add(pf);
        }
        return pfs;
    }

    @Override
    public void saveProcess(String fileName, String content) {
        if(fileName.startsWith(prefix)){
            fileName=fileName.substring(prefix.length(),fileName.length());
        }
        Accessory vf = accessoryService.getObjByProperty("acc_type",11,"name",fileName);
        if (vf==null) {
            Accessory accessory = new Accessory();
            accessory.setName(fileName);
            accessory.setAcc_type(11);
            accessory.setReal_name(fileName);
            accessory.setInfo("工作流");
            try {
                accessory.setBlob_value(content.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            accessoryService.save(accessory);
        } else {
            Accessory accessory = vf;
            accessory.setName(fileName);
            accessory.setAcc_type(11);
            accessory.setReal_name(fileName);
            accessory.setInfo("工作流");
            try {
                accessory.setBlob_value(content.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            accessoryService.save(accessory);
        }
    }

    @Override
    public void deleteProcess(String fileName) {
        Accessory accessory = accessoryService.getObjByProperty("acc_type",11,"name",fileName);
        accessoryService.delete(accessory.getId());
    }

    @Override
    public String getName() {
        return "Blob存储";
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public boolean support(String fileName) {
        return fileName.startsWith(prefix);
    }

    @Override
    public boolean isDisabled() {
        return false;
    }
}
