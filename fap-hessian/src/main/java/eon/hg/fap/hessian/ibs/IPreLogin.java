package eon.hg.fap.hessian.ibs;

import gov.gfmis.fap.util.SessionInValidException;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 登录前得取系统信息操作
 */
public interface IPreLogin {
    List queryBusiYear() throws Exception;

    List getUserNameByCode(String userCode) throws SessionInValidException;

    String getSystemNameById(String sysid) throws SessionInValidException;
    public String getUserSysLoginImgById(String userSysId)throws SessionInValidException;
    public String getSysLookAndFeel(String sysId);

    public String getSysPara(String paraCode, String setCode);

    List queryRolesByUser(String userCode, String year)
            throws SessionInValidException;

    public String getUserIdbyCode(String userCode);

    public List getOffLinePara(String sysid);

    public byte[] getFileAtServer(String url) throws IOException;

    public int getIsDstoreBySysId(String sysId);

    public Map getValueFromSysapp(String sysid);

    /**
     * 先获取总帐的bean注入，如果为空，取全部帐套
     *
     * @param userId
     *                用户id
     * @param year
     *                选择的年份
     * @return List（XMLData字段符合表ele_book_set） 返回帐套信息
     * @throws Exception
     *                 抛出sql错误
     * @author 黄节2007年5月21日新增
     */
    public List getBookSetByUserId(String userId, int year) throws Exception;

    /**
     * 先获取总帐的bean注入，如果为空，去全部帐套，否则先返回没有数据的list
     *
     * @param year
     *                传入的年份，取全部帐套用
     * @return List（XMLData字段符合表ele_book_set） 返回帐套信息
     * @throws Exception
     *                 抛出sql错误
     * @author 黄节2007年5月21日修改
     */
    public List queryBookSet(int year) throws Exception;
}
