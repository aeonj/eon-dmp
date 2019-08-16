package eon.hg.fap.core.domain.entity;

import eon.hg.fap.core.annotation.Lock;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统域模型基类，该类包含3个常用字段，其中id为自增长类型，该类实现序列化，只有序列化后才可以实现tomcat集群配置session共享
 * @author AEON
 *
 */
@Data
@MappedSuperclass
public class ChrIdEntity implements Serializable {
	/**
	 * 序列化接口，自动生成序列号
	 */
	private static final long serialVersionUID = 6462432775809482807L;
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(unique = true, nullable = false)
	protected String chr_id;// 域模型id，这里为自增类型
	protected Date addTime;// 添加时间，这里为长时间格式
	@Lock
	protected boolean is_deleted;// 是否删除,默认为0未删除，-1表示删除状态
	protected Date lastTime;// 添加时间，这里为长时间格式
	@Column(length=60)
	protected String lastUser; //最后修改用户

	public ChrIdEntity(String chr_id, Date addTime) {
		super();
		this.chr_id = chr_id;
		this.addTime = addTime;
	}

	public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ChrIdEntity))
            return false;
        final ChrIdEntity other = (ChrIdEntity) obj;
         if(!this.getChr_id().equals(other.getChr_id()))
            return false;
        return true;
   } 
	
}
