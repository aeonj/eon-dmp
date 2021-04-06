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
package eon.hg.fap.flow.command.impl;

import eon.hg.fap.flow.command.Command;
import eon.hg.fap.flow.env.Context;
import eon.hg.fap.flow.model.Blob;
import eon.hg.fap.flow.utils.IDGenerator;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Jacky.gao
 * @since 2013年8月4日
 */
public class DeployProcessResourceCommand implements Command<Blob> {
	private byte[] processRes;
	private String name;
	private long processId;
	private boolean update=false;
	public DeployProcessResourceCommand(byte[] processRes,String name,long processId,boolean update){
		this.processRes=processRes;
		this.name=name;
		this.processId=processId;
		this.update=update;
	}
	public Blob execute(Context context) {
		Session session=context.getSession();
		if(update){
			Query query=session.createQuery("delete from "+Blob.class.getName()+" where processId=:processId");
			query.setLong("processId", processId);
			query.executeUpdate();
		}
		Blob lob=new Blob();
		lob.setId(IDGenerator.getInstance().nextId());
		lob.setBlobValue(processRes);
		lob.setName(name);
		lob.setProcessId(processId);
		session.save(lob);
		return lob;
	}

}
