package eon.hg.fap.db.model.mapper;

import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Data
public abstract class Ccid extends IdEntity implements Serializable {
    @Column(length = 128)
    private String hash_code;
    private Long en_id;
    @Column(length = 42)
    private String en_code;
    private String en_name;
    private Long mb_id;
    @Column(length = 42)
    private String mb_code;
    private String mb_name;
    private Long hold1_id;
    private Long hold2_id;
    private Long hold3_id;
    private Long hold4_id;
    private Long hold5_id;
    private Long hold6_id;
    private Long hold7_id;
    private Long hold8_id;
    private Long hold9_id;
    private Long hold10_id;
    private Long hold11_id;
    private Long hold12_id;
    private Long hold13_id;
    private Long hold14_id;
    private Long hold15_id;
    @Column(length = 42)
    private String hold2_code;
    @Column(length = 42)
    private String hold3_code;
    @Column(length = 42)
    private String hold4_code;
    @Column(length = 42)
    private String hold5_code;
    @Column(length = 42)
    private String hold6_code;
    @Column(length = 42)
    private String hold7_code;
    @Column(length = 42)
    private String hold8_code;
    @Column(length = 42)
    private String hold9_code;
    @Column(length = 42)
    private String hold10_code;
    @Column(length = 42)
    private String hold11_code;
    @Column(length = 42)
    private String hold12_code;
    @Column(length = 42)
    private String hold13_code;
    @Column(length = 42)
    private String hold14_code;
    @Column(length = 42)
    private String hold15_code;
    private String hold1_name;
    private String hold2_name;
    private String hold3_name;
    private String hold4_name;
    private String hold5_name;
    private String hold6_name;
    private String hold7_name;
    private String hold8_name;
    private String hold9_name;
    private String hold10_name;
    private String hold11_name;
    private String hold12_name;
    private String hold13_name;
    private String hold14_name;
    private String hold15_name;

}
