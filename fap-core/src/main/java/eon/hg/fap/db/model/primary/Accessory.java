package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 系统附件管理类，用来管理系统所有附件信息，包括图片附件、rar附件等等
 * @author AEON
 *
 */
@Data
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "accessory", indexes = {
		@Index(name = "idx_accessory", columnList = "acc_type")
})
public class Accessory extends IdEntity {

	private String name;// 附件名称
	private String path;// 存放路径
	@Column(name = "asize",precision = 12, scale = 2)
	private BigDecimal size;// 附件大小
	private int width;// 宽度
	private int height;// 高度
	private String ext;// 扩展名，不包括.
	private String info;// 附件说明
	private String real_name;//文件上传的真实名称
	//acc_type 等于11时代表工作流模板，记录blob字段
	@Column(length = 2, columnDefinition = "int default 1")
	private int acc_type = 1;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] blob_value;

	public Accessory(Long id, Date addTime) {
		super(id, addTime);
		// TODO Auto-generated constructor stub
	}

	public Accessory(Long id) {
		super.setId(id);
		// TODO Auto-generated constructor stub
	}

	public Accessory() {
		super();
		// TODO Auto-generated constructor stub
	}


}
