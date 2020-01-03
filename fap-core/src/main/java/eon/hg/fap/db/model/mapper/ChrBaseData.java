/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年3月1日
 * @author cxj
 */

package eon.hg.fap.db.model.mapper;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author AEON
 *
 */
@MappedSuperclass
@Data
public abstract class ChrBaseData extends BaseData implements Serializable {

	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(unique = true, nullable = false, length = 38)
	private String chr_id;
	@Column(length = 38)
	private String parent_chr_id;

}
