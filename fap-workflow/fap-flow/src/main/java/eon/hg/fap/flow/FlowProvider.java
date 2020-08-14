package eon.hg.fap.flow;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.flow.meta.*;

import java.util.List;

public interface FlowProvider {

    /**
     * 基于node的当前工作节点和业务状态获取工作流条件语句
     * 本接口与待办任务进行join连接查询，返回结果需要自行处理
     * @param node 当前节点对象，包括流程名称，节点名称，节点状态，用户ID
     * @param alias 业务表别名
     * @param id_field 业务表ID
     * @return sql连接对象
     */
    SqlRelation getTaskSqlCondition(CurrentNode node, String alias, String id_field);

    /**
     * 组装当前列表查询条件对象
     * @param menu_id 当前菜单功能
     * @param state 当前状态
     * @param qo 条件对象
     * @return
     */
    IQueryObject getTaskHqlCondition(Long menu_id, NodeState state, IQueryObject qo);

    /**
     * 组装当前列表条件对象
     * @param node 当前节点对象
     * @param qo 条件对象
     * @return
     */
    IQueryObject getTaskHqlCondition(CurrentNode node, IQueryObject qo);

    /**
     * 通用流程处理
     * @param menu_id 当前菜单ID
     * @param state 当前状态
     * @param actionType 操作类型
     * @param advice 流程处理意见
     * @param records 业务记录列表
     */
    void doWorkFlowByBusiness(Long menu_id, NodeState state, ActionType actionType, String advice,
                              List records);

    /**
     * 通用流程处理
     * @param menu_id 当前菜单ID
     * @param state 当前状态
     * @param actionType 操作类型
     * @param advice 流程处理意见
     * @param records 业务记录列表
     * @param toNodeName 只用于转发和退回到指定节点
     */
    void doWorkFlowByBusiness(Long menu_id, NodeState state, ActionType actionType, String advice,
                              List records, String toNodeName);

    /**
     * 通用流程处理
     * @param node 流程节点信息
     * @param actionType 操作类型
     * @param advice 流程处理意见
     * @param records 业务记录列表
     */
    void doWorkFlowByBusiness(CurrentNode node, ActionType actionType, String advice,
                              List records);

    /**
     * 通用流程处理
     * @param node 流程节点信息
     * @param actionType 操作类型
     * @param advice 流程处理意见
     * @param records 业务记录列表
     * @param toNodeName 只用于转发和退回到指定节点
     */
    void doWorkFlowByBusiness(CurrentNode node, ActionType actionType, String advice,
                              List records, String toNodeName);

    /**
     * 通用流程处理
     * @param node 流程节点信息
     * @param actionType 操作类型
     * @param advice 流程处理意见
     * @param records 业务记录列表
     * @param toNodeName 只用于转发和退回到指定节点
     * @param task_field 业务记录中对应的任务ID字段名
     * @param business_field 业务记录中对应的业务ID字段名，仅records记录集中不存在task_field字段才有用
     * @param bUpdateVariants 是否更新业务记录对应的流程变量
     */
    void doWorkFlowByBusiness(CurrentNode node, ActionType actionType, String advice,
                              List records, String toNodeName, String task_field, String business_field, boolean bUpdateVariants);

    /**
     * 获取操作日志
     * @param menu_id 菜单ID
     * @param business_id 业务ID
     * @return
     */
    List<TaskVO> loadTaskLogList(Long menu_id, String business_id);

    /**
     * 获取操作日志
     * @param menu_id 菜单ID
     * @param business_id 业务ID
     * @param page 当前页
     * @param limit 页记录数
     * @return
     */
    IPageList loadTaskLogList(Long menu_id, String business_id, int page, int limit);

    /**
     * 获取当前登录用户对应的任务列表
     * @param menu_id 菜单ID
     * @param business_id 业务ID
     * @return
     */
    List<TaskVO> loadTaskInfoList(Long menu_id, String business_id);

    /**
     * 获取当前登录用户对应的任务列表
     * @param menu_id 菜单ID
     * @param business_id 业务ID
     * @param page 当前页
     * @param limit 页记录数
     * @return
     */
    IPageList loadTaskInfoList(Long menu_id, String business_id, int page, int limit);
}
