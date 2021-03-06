package eon.hg.fap.web.manage.action;

import cn.hutool.core.util.StrUtil;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.common.util.tools.StringUtils;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.tools.JsonHandler;
import lombok.val;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BizAction {
	
	/**
	 * 输出响应
	 * 
	 * @param str
	 * @throws IOException
	 */
	protected void write(String str, HttpServletResponse response) {
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.write(str);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 交易成功提示信息
	 * 
	 * @param pMsg
	 *            提示信息
	 * @param response
	 * @return
	 * @throws IOException
	 * @deprecated user {@link eon.hg.fap.core.body.ResultBody}
	 */
	@Deprecated
	protected void setOkTipMsg(String pMsg, HttpServletResponse response) {
		Dto outDto = new HashDto();
		outDto.put("success",AeonConstants.TRUE);
		outDto.put("msg", pMsg);
		write(JsonHandler.toJson(outDto), response);
	}

	@Deprecated
	protected Dto OkTipMsg(String pMsg) {
		Dto outDto = new HashDto();
		outDto.put("success",AeonConstants.TRUE);
		outDto.put("msg", pMsg);
		return outDto;
	}
	/**
	 * 
	 * 交易失败提示信息(特指：业务交易失败并不是请求失败)<br>
	 * 和Form的submit中的failur回调对应,Ajax.request中的failur回调是指请求失败
	 * 
	 * @param pMsg
	 *            提示信息
	 * @param response
	 * @return
	 * @throws IOException
	 * @deprecated user {@link eon.hg.fap.core.body.ResultBody}
	 */
	@Deprecated
	protected void setErrTipMsg(String pMsg, HttpServletResponse response) {
		Dto outDto = new HashDto();
		outDto.put("success",AeonConstants.FALSE);
		outDto.put("msg", pMsg);
		write(JsonHandler.toJson(outDto), response);
	}

	@Deprecated
	protected Dto ErrTipMsg(String pMsg) {
		Dto outDto = new HashDto();
		outDto.put("success",AeonConstants.FALSE);
		outDto.put("msg", pMsg);
		return outDto;
	}

	public boolean isAjaxRequest(HttpServletRequest request) {
		if(request.getHeader("x-requested-with")!=null && "xmlhttprequest".equalsIgnoreCase(request.getHeader("x-requested-with"))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 获取树形节点单击事件传到后台的节点唯一标识号 对应JS节点对象的ID属性,其Key值为:"node"
	 * 
	 * @param request
	 * @return
	 */
	public String getTreeNodeUID4Clicked(HttpServletRequest request) {
		return request.getParameter("node");
	}

	/**
	 * 获取Grid新增和编辑过的脏数据
	 * 
	 * @return
	 */
	public List getGridDirtyData(HttpServletRequest request) {
		List list = new ArrayList();
		String dirtyData = request.getParameter("dirtydata");
		if (CommUtil.isEmpty(dirtyData)) {
			return list;
		}
		dirtyData = dirtyData.substring(1, dirtyData.length() - 1);
		String[] dirtyDatas = dirtyData.split("},");
		for (int i = 0; i < dirtyDatas.length; i++) {
			if (i != dirtyDatas.length - 1) {
				dirtyDatas[i] += "}";
			}
			Dto dto = JsonHandler.parseDto(dirtyDatas[i]);
			list.add(dto);
		}
		return list;
	}
	
	public void jqGridFilter(HttpServletRequest request, QueryObject qo) {
		if (qo!=null) {
			if (CommUtil.null2Boolean(request.getParameter("_search"))) {
				Dto dtoFilter = JsonHandler.parseDto(request.getParameter("filters"));
				List lstField = (List) dtoFilter.get("rules");
				String group = dtoFilter.getString("groupOp");
				String query = "";
				Map<String,String> Q2Oper = jqGridOp();
				for (int i=0; i<lstField.size(); i++) {
					Map dto = (Map) lstField.get(i);
                    String field = CommUtil.null2String(dto.get("field"));  
                    String op = CommUtil.null2String(dto.get("op"));  
                    String data = CommUtil.null2String(dto.get("data"));  
                    if (!op.isEmpty() && !data.isEmpty()) {  
                        if(i==0) 
                        	qo.addQuery(field,"q_"+field,jqGridData(op,data),Q2Oper.get(op));
                        else  
                        	qo.addQuery(field,"q_"+field,jqGridData(op,data),Q2Oper.get(op),group);
                    }  
				}
			}
		}
	}

	/**
	 * 获取前端查询组件的条件，组装jpa条件语句
	 * @param jsonOp
	 * @param qo
	 * @deprecated use {@link #extGridJpa(String,QueryObject)}
	 */
	@Deprecated
	public QueryObject extGridFilter(String jsonOp, QueryObject qo) {
		return extGridJpa(jsonOp,qo);
	}

	/**
	 * 获取前端查询组件的条件，组装jpa条件语句
	 * @param jsonOp 从前端获取的json查询条件字符串，请参考前端控件QueryPanel
	 * @param qo 查询条件对象
	 * @version 2.0.3+
	 */
	public QueryObject extGridJpa(String jsonOp, QueryObject qo) {
		if (qo!=null && CommUtil.isNotEmpty(jsonOp)) {
			List<Dto> lstField = JsonHandler.parseList(jsonOp);
			Map<String,String> Q2Oper = jqGridOp();
			for (Dto dto : lstField) {
				String field = CommUtil.null2String(dto.get("field"));
				String op = CommUtil.null2String(dto.get("op"));
				String data = CommUtil.null2String(dto.get("data"));
				if (!op.isEmpty() && !data.isEmpty()) {
					String alias = qo.getAlias();
				    if (CommUtil.isNotEmpty(alias)) {
                        qo.addQuery(alias+"."+field, "e_" + field, jqGridData(op, data), Q2Oper.get(op));
                    } else {
                        qo.addQuery(field, "e_" + field, jqGridData(op, data), Q2Oper.get(op));
                    }
				}
			}
		}
		return qo;
	}

	/**
	 * 获取前端查询组件的条件，用于组装不带参数的原生sql条件
	 * @param jsonOp
	 * @param qo
	 * @deprecated use {@link #extGridSql(String,QueryObject)}
	 */
	@Deprecated
	public QueryObject extGridWhere(String jsonOp, QueryObject qo) {
		return extGridSql(jsonOp,qo);
	}

	/**
	 * 获取前端查询组件的条件，用于组装不带参数的原生sql条件
	 * @param jsonOp 从前端获取的json查询条件字符串，请参考前端控件QueryPanel
	 * @param qo 查询条件对象
	 * @version 2.0.3+
	 */
	public QueryObject extGridSql(String jsonOp, QueryObject qo) {
		if (qo!=null && CommUtil.isNotEmpty(jsonOp)) {
			List<Dto> lstField = JsonHandler.parseList(jsonOp);
			Map<String,String> Q2Oper = jqGridOp();
			for (Dto dto : lstField) {
				String field = CommUtil.null2String(dto.get("field"));
				String op = CommUtil.null2String(dto.get("op"));
				String type = CommUtil.null2String(dto.get("type"));
				String data = CommUtil.null2String(dto.get("data"));
				if (!op.isEmpty() && !data.isEmpty()) {
					qo.and(field, Q2Oper.get(op), extTypeConvert(type,jqGridData(op, data)));
				}
			}
		}
		return qo;
	}

	private String extTypeConvert(String type, String val) {
		if ("string".equals(type)) {
			if (StrUtil.indexOf(val,'(')>0) {
				return val.replace(" ("," ('").replace(") ","') ").replaceAll(",","','");
			} else {
				return StringUtils.quote(val);
			}
		} else if ("date".equals(type)) {
			return StringUtils.quote(val);
		}
		return val;
	}

	private String jqGridData(String op, String val) {
		if(op.equals("bw") || op.equals("bn")) return val + "%";  
        else if (op.equals("ew") || op.equals("en")) return "%"+val;  
        else if (op.equals("cn") || op.equals("nc")) return "%" +val+ "%";  
        else if (op.equals("in") || op.equals("ni")) return " (" +val+ ") ";  
        else return val; 
	}

	private Map<String,String> jqGridOp() {
		Map<String,String> Q2Oper = new HashMap();
        //['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']  
        Q2Oper.put("eq", " = ");  
        Q2Oper.put("ne", " <> ");  
        Q2Oper.put("lt", " < ");  
        Q2Oper.put("le", " <= ");  
        Q2Oper.put("gt", " > ");  
        Q2Oper.put("ge", " >= ");  
        Q2Oper.put("bw", " LIKE ");  
        Q2Oper.put("bn", " NOT LIKE ");  
        Q2Oper.put("in", " IN ");  
        Q2Oper.put("ni", " NOT IN ");  
        Q2Oper.put("ew", " LIKE ");  
        Q2Oper.put("en", " NOT LIKE ");  
        Q2Oper.put("cn", " LIKE ");  
        Q2Oper.put("nc", " NOT LIKE ");  
        return Q2Oper;
	}

}
