package eon.hg.fap.hessian.ibs;

import gov.gfmis.fap.util.XMLData;
import gov.gfmis.fap.util.SessionInValidException;

import java.util.List;
import java.util.Map;


/**
 * 与用户登录相关的接口
 *
 * @author swj
 * @version 1.0
 */
public interface ILogin {
    List queryBusiYear() throws Exception;

    List getUserNameByCode(String userCode);

    String getSystemNameById(String sysid) throws SessionInValidException;
    String getUserSysNameById(String userSysId) throws SessionInValidException;
    public String getUserSysLoginImgById(String userSysId)throws SessionInValidException;
    public List queryBookSet(int year) throws Exception;

    public String getSysLookAndFeel(String sysId);

    public String getSysPara(String paraCode, String setCode);

    List queryRolesByUser(String userCode, String year, String changedsysid);

    public String getUserIdbyCode(String userCode);

    public Map getLoinUser();

    public String getUserCodebyId(String userId);

    public Map findRoleMessageByUserCode(String userCode, String setYear,
                                         String sysId);

    /**
     * 根据用户id取得用户的区域代码
     *
     * @param user_Id
     *                String 用户id
     * @return String 返回用户区域编码
     * @author 黄节添加
     */
    public String getUserRgCodeById(String user_Id);

    /**
     * 获取用户所属用机构（BELONG_ORG）和用户所属机构类型（BELONG_TYPE）
     *
     * @param user_Id
     *                String用户id
     * @return XMLData （包含字段belong_org和belong_type），返回null表示没有数据
     * @author 黄节2007年7月12日添加
     */
    public XMLData getUserbelong(String user_Id);

    /**
     * 根据用户code获取用户信息
     *
     * @param userCode
     *                String 用户code
     * @return Map（字段符合表sys_usermanage)用户信息
     * @author 黄节2007年5月23日新增
     */
    public Map getUserInforByCode(String userCode);

    /**
     * 清除记录，清除sys_session表所有满足条件{login_date>当前时间-5天}的记录
     *
     * @author 黄节2007年9月15日添加
     */
    public void removeInvalidateSession();

    /**
     * 根据用户id取得用户的区域代码
     *
     * @param user_Id
     *            String 用户id
     * @param setYear  用户年度
     * @return String 返回用户区域编码
     * @author lindx添加
     */
    public String getUserRgCodeWithYear(String user_Id, int setYear);


    /**
     * 从年度库获取用户所属用机构（BELONG_ORG）和用户所属机构类型（BELONG_TYPE）
     *
     * @param user_Id
     *            String用户id
     * @param setYear  用户年度
     * @return XMLData （包含字段belong_org和belong_type），返回null表示没有数据
     * @author lindx
     */
    public XMLData getUserbelongWithYear(String user_Id, int setYear);
}
