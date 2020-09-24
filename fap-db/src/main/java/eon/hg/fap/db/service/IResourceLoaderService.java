package eon.hg.fap.db.service;

public interface IResourceLoaderService {

    public void initSysTable() throws Exception;
    public void upgrateSysTable() throws Exception;
}
