package eon.hg.fap.db.service;

import eon.hg.fap.db.model.primary.Permissions;

import java.util.List;
import java.util.Map;

public interface IPermissionsService {

	Permissions save(Permissions instance);

	Permissions getObjById(Long id);

	void delete(Long id);

	Permissions update(Permissions instance);
	
	List<Permissions> query(String query, Map params, int begin, int max);

	Permissions getObjByProperty(String construct, String propertyName, Object value);

}
