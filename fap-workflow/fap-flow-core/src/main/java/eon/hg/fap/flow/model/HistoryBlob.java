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
package eon.hg.fap.flow.model;

import eon.hg.fap.core.constant.Globals;
import org.springframework.util.SerializationUtils;

import javax.persistence.*;

/**
 * @author Jacky.gao
 * @since 2013年9月27日
 */
@Entity
@Table(name= Globals.FLOW_TABLE_SUFFIX + "his_blob")
public class HistoryBlob {
	@Id
	@Column(name="ID_")
	private long id;
	
	@Lob
	@Column(name="BLOB_VALUE_",length=1024000)
	private byte[] blobValue;

	public HistoryBlob(){}
	public HistoryBlob(Object obj){
		byte[] b=SerializationUtils.serialize(obj);
		setBlobValue(b);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getBlobValue() {
		return blobValue;
	}

	public void setBlobValue(byte[] blobValue) {
		this.blobValue = blobValue;
	}
}
