package com.lhs.rest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.lhs.rest.util.DBUtil;
import com.lhs.rest.util.DataProcessingUtil;
import com.lhs.rest.util.NetUtil;
import com.matrixone.apps.domain.util.FrameworkUtil;
import com.matrixone.json.JSONArray;

import matrix.db.Context;
import matrix.db.JPO;
import matrix.util.MatrixException;
import matrix.util.StringList;

@Path("/rest")
@SuppressWarnings({ "deprecation", "rawtypes" })
public class RestHandler{
	
	@javax.ws.rs.core.Context
	private HttpServletResponse _response;
	 
	@javax.ws.rs.core.Context
	private HttpServletRequest _request;
	
	@GET
	@Path("/jpo")
	public Response invokeGET(@QueryParam("program") String programExpr, @QueryParam("returnType") String returnType) {
		return invokeCommon(programExpr, returnType);
	}
	
	@POST
	@Path("/jpo")
	public Response invokePOST(@FormParam("program") String program, @FormParam("returnType") String returnType) throws MatrixException {
		return invokeCommon(program, returnType);
	}
	
	private Response invokeCommon(String programExpr, String returnType) {
		String outStr = "";
		try {
			Context context = DBUtil.getContext(_request);
			
			if ( StringUtils.isEmpty(programExpr) )
			{
				outStr = "Check program parameter. program parameter is empty.";
			}
			else
			{
				returnType = StringUtils.isEmpty(returnType) ? "MapList" : returnType;
				
				Map paramMap = _request.getParameterMap();
				paramMap.remove("program");
				paramMap.remove("returnType");
				
				StringList slProgramExpr = FrameworkUtil.splitString(programExpr, ",");
				String[] sProgramExprArr = null;
				String sTempProgramExpr = null;
				
				JSONArray jsonArr = new JSONArray();
				for (int k = 0; k < slProgramExpr.size(); k++)
				{
					sTempProgramExpr = (String) slProgramExpr.get(k);
					sProgramExprArr = sTempProgramExpr.split(":");
					
					if ( sProgramExprArr != null && sProgramExprArr.length >= 2 )
					{
						Object result = JPO.invoke(context, sProgramExprArr[0], null, sProgramExprArr[0], JPO.packArgs(paramMap), Class.forName(returnType));
						jsonArr.put(DataProcessingUtil.convertObject2JSONArray(result));
					}
					else
					{
						outStr = "Check program parameter. program parameter is " + sTempProgramExpr;
						break;
					}
					
				}
				
				outStr = jsonArr.toString();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			outStr = e.getMessage();
		}
		return NetUtil.getResponse(outStr);
	}
	
}
