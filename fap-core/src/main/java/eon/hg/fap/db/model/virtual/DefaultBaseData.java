package eon.hg.fap.db.model.virtual;

import eon.hg.fap.db.model.mapper.BaseData;
import lombok.Data;

@Data
public class DefaultBaseData extends BaseData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 697783040320351132L;
	private Long parent_id;
	
	public DefaultBaseData() {

	}

	@Override
	public BaseData getParent() {
		// TODO Auto-generated method stub
		return null;
	}


}
