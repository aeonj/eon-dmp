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
	@Column(name = "chr_id", unique = true, nullable = false, length = 38)
	protected String id;// 域模型id，这里为自增类型
	@Column(name = "add_time")
	protected Date addTime;// 添加时间，这里为长时间格式
	@Lock
	protected boolean is_deleted;// 是否删除,默认为0未删除，-1表示删除状态
	@Column(name = "last_time")
	protected Date lastTime;// 添加时间，这里为长时间格式
	@Column(name = "last_user", length=60)
	protected String lastUser; //最后修改用户

	public ChrIdEntity(String id, Date addTime) {
		super();
		this.id = id;
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
         if(!this.getId().equals(other.getId()))
            return false;
        return true;
   } 
	
}
