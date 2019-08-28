package eon.hg.fap.web.manage.op;

import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.db.model.primary.Menu;
import eon.hg.fap.db.model.primary.Operate;
import eon.hg.fap.db.service.IOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OperateOP {
    @Autowired
    private IOperateService operateService;

    public List<Dto> getOperateTreeList(Menu menu) {
        List<Dto> lstTree = new ArrayList<>();
        for (Operate operate : menu.getOperates()) {
            lstTree.add(OperateData2Dto(operate));
        }
        return lstTree;
    }

    private Dto OperateData2Dto(Operate node) {
        Dto dto = new HashDto();
        dto.put("id", node.getId());
        dto.put("code", node.getName());
        dto.put("name", node.getName());
        dto.put("text", node.getName());
        dto.put("children", new ArrayList<>());
        dto.put("leaf", true);
        dto.put("parentid",null);
        dto.put("scrollbar","");
        dto.put("height",0);
        dto.put("width",0);
        dto.put("sequence", node.getSequence());
        return dto;
    }
}
