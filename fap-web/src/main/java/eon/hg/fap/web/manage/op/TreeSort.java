package eon.hg.fap.web.manage.op;

import eon.hg.fap.common.util.metatype.Dto;

import java.util.Collections;
import java.util.List;

public abstract class TreeSort {

    protected void sortMenuList(List<Dto> child) {
        Collections.sort(child, (arg0, arg1) -> {
            int hits0 = arg0.getInteger("sequence");
            int hits1 = arg1.getInteger("sequence");
            if (hits1 < hits0) {
                return 1;
            } else if (hits1 == hits0) {
                return 0;
            } else {
                return -1;
            }
        });
    }

}
