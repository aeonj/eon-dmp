package eon.hg.fap.core.domain.entity;

import eon.hg.fap.core.annotation.Lock;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统域模型基类，该类包含3个常用字段，其中id为自增长类型，该类实现序列化，只有序列化后才可以实现tomcat集群配置session共享
 * @author AEON
 *
 */
@MappedSuperclass
public class IdEntity implements Serializable {
	/**
	 * 序列化接口，自动生成序列号
	 */
	private static final long serialVersionUID = 6462432775809482807L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	protected Long id;// 域模型id，这里为自增类型
	protected Date addTime;// 添加时间，这里为长时间格式
	@Lock
	protected boolean is_deleted;// 是否删除,默认为0未删除，-1表示删除状态
	protected Date lastTime;// 添加时间，这里为长时间格式
	@Column(length=60)
	protected String lastUser; //最后修改用户

	public IdEntity() {
		super();
	}

	public IdEntity(Long id, Date addTime) {
		super();
		this.id = id;
		this.addTime = addTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	/**
	 * @return the lastTime
	 */
	public Date getLastTime() {
		return lastTime;
	}

	/**
	 * @param lastTime the lastTime to set
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * @return the lastUser
	 */
	public String getLastUser() {
		return lastUser;
	}

	/**
	 * @param lastUser the lastUser to set
	 */
	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof IdEntity))
            return false;
        final IdEntity other = (IdEntity) obj;
         if(!this.getId().equals(other.getId()))
            return false;
        return true;
   } 
	
}
