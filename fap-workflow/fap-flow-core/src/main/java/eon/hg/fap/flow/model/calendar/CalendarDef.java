/*******************************************************************************
 * Copyright 2017 Bstek
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package eon.hg.fap.flow.model.calendar;

import eon.hg.fap.core.constant.Globals;

import javax.persistence.*;
import java.util.List;


/**
 * @author Jacky.gao
 * @since 2013年9月23日
 */
@Entity
@Table(name= Globals.FLOW_TABLE_SUFFIX + "calendar")
public class CalendarDef implements java.io.Serializable{
	private static final long serialVersionUID = 2579525564734297159L;
	@Id
	@Column(name="ID_")
	private long id;
	@Column(name="CATEGORY_ID_",length=60)
	private String categoryId;
	@Column(name="NAME_",length=60)
	private String name;
	@Column(name="DESC_",length=120)
	private String desc;
	@Column(name="TYPE_",length=12)
	@Enumerated(EnumType.STRING)
	private CalendarType type;
	
	@Transient
	private List<CalendarDate> calendarDates;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public CalendarType getType() {
		return type;
	}
	public void setType(CalendarType type) {
		this.type = type;
	}
	public List<CalendarDate> getCalendarDates() {
		return calendarDates;
	}
	public void setCalendarDates(List<CalendarDate> calendarDates) {
		this.calendarDates = calendarDates;
	}
}
