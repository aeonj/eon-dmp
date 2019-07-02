package eon.hg.fap.core.domain.entity;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by aeon on 2018/4/18.
 */
@MappedSuperclass
public class BaseEntity extends HashMap implements Serializable {

}
