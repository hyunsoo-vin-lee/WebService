package com.lhs.rest.test;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.lhs.rest.util.DataProcessingUtil;
import com.lhs.rest.util.NetUtil;
import com.matrixone.apps.domain.util.MapList;
import com.matrixone.json.JSONArray;

import matrix.util.MatrixException;
import matrix.util.StringList;

@Path("/test")
@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
public class RestHandlerTest {

	@javax.ws.rs.core.Context
	private HttpServletResponse _response;
	 
	@javax.ws.rs.core.Context
	private HttpServletRequest _request;
	
	@GET
	@Path("/stringlist")
	public Response getStringList() {
		StringList slReturn = new StringList();
		slReturn.add("Ross");
		slReturn.add("Chandler");
		slReturn.add("Joey");
		slReturn.add("Rachel");
		slReturn.add("Phoeby");
		slReturn.add("Monica");
		
		return NetUtil.getResponse(slReturn.toString());
	}
	
	@GET
	@Path("/barChartData")
	public Response getBarChartData() throws MatrixException {
		MapList mapList = new MapList();
		Map map = new HashMap();
		map.put("name", "Ross");
		map.put("age", "26");
		map.put("body-age", "36");
		mapList.add(map);
		map = new HashMap();
		map.put("name", "Chandler");
		map.put("age", "26");
		map.put("body-age", "50");
		mapList.add(map);
		map = new HashMap();
		map.put("name", "Joey");
		map.put("age", "25");
		map.put("body-age", "20");
		mapList.add(map);
		map = new HashMap();
		map.put("name", "Rachel");
		map.put("age", "24");
		map.put("body-age", "30");
		mapList.add(map);
		map = new HashMap();
		map.put("name", "Monica");
		map.put("age", "24");
		map.put("body-age", "18");
		mapList.add(map);
		map = new HashMap();
		map.put("name", "Phoeby");
		map.put("age", "25");
		map.put("body-age", "46");
		mapList.add(map);
		
		StringList slCategory = new StringList();
		MapList mlReturn = new MapList();
		mlReturn.add( DataProcessingUtil.extractBarChartDataFromMapList(mapList, "name", slCategory, "age", "Age") );
		mlReturn.add( DataProcessingUtil.extractBarChartDataFromMapList(mapList, "name", slCategory, "body-age", "Body Age") );
		
		JSONArray jsonArray = DataProcessingUtil.convertObject2JSONArray(mlReturn);
		
		return NetUtil.getResponse(jsonArray.toString());
	}
	
	@GET
	@Path("/circleChartData")
	public Response getCircleChartData() throws Exception {
		MapList mapList = new MapList();
		Map map = new HashMap();
		map.put("name", "Ross");
		map.put("age", "26");
		map.put("body-age", "36");
		map.put("room-no", "401");
		map.put("income", "4000");
		mapList.add(map);
		map = new HashMap();
		map.put("name", "Chandler");
		map.put("age", "26");
		map.put("body-age", "50");
		map.put("room-no", "302");
		map.put("income", "6000");
		mapList.add(map);
		map = new HashMap();
		map.put("name", "Joey");
		map.put("age", "25");
		map.put("body-age", "20");
		map.put("room-no", "302");
		map.put("income", "1500");
		mapList.add(map);
		map = new HashMap();
		map.put("name", "Rachel");
		map.put("age", "24");
		map.put("body-age", "30");
		map.put("room-no", "301");
		map.put("income", "1800");
		mapList.add(map);
		map = new HashMap();
		map.put("name", "Monica");
		map.put("age", "24");
		map.put("body-age", "18");
		map.put("room-no", "301");
		map.put("income", "3000");
		mapList.add(map);
		map = new HashMap();
		map.put("name", "Phoeby");
		map.put("age", "25");
		map.put("body-age", "46");
		map.put("room-no", "201");
		map.put("income", "2300");
		mapList.add(map);
		
		Map mSumByGroup = DataProcessingUtil.getSumByGroup(mapList, "room-no", "income");
		MapList mlReturn = DataProcessingUtil.convertMap2MapList(mSumByGroup, "name", "y");
		
		JSONArray jsonArray = DataProcessingUtil.convertObject2JSONArray(mlReturn);
		
		return NetUtil.getResponse(jsonArray.toString());
	}
}
