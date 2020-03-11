package com.lhs.rest.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.matrixone.apps.domain.util.MapList;
import com.matrixone.json.JSONArray;
import com.matrixone.json.JSONObject;

import matrix.util.MatrixException;
import matrix.util.StringList;

@SuppressWarnings({"rawtypes","unchecked","deprecation"})
public class DataProcessingUtil {

	public static Map extractBarChartDataFromMapList(MapList mapList, String selectCategory, StringList slCategory, String selectData, String name) {
		try {
			if ( slCategory == null )
			{
				slCategory = new StringList();
			}
			
			Map map = null;
			String sCategory = null;
			String sFixedCategory = null;
			String sData = null;
			StringList slData = new StringList();
			
			if ( slCategory.size() > 0 )
			{
				for (int k = 0; k < slCategory.size(); k++)
				{
					sFixedCategory = (String) slCategory.get(k);
					
					for (int m = 0; m < mapList.size(); m++)
					{
						map = (Map) mapList.get(m);
						sCategory = (String) map.get(selectCategory);
						
						if ( sCategory.equals(sFixedCategory) )
						{
							sData = (String) map.get(selectData);
							slData.add(sData);
							
							break;
						}
					}
				}
			}
			else
			{
				for (int k = 0; k < mapList.size(); k++)
				{
					map = (Map) mapList.get(k);
					sCategory = (String) map.get(selectCategory);
					
					slCategory.add(sCategory);
					
					sData = (String) map.get(selectData);
					slData.add(sData);
				}
			}
			
			Map mReturn = new HashMap();
			mReturn.put("name", name);
			mReturn.put("data", slData);
			mReturn.put("category", slCategory);
			
			return mReturn;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static JSONArray convertObject2JSONArray(Object obj) throws MatrixException {
		JSONArray jsonArr = new JSONArray();
		JSONObject json = null;
		if ( obj instanceof MapList )
		{
			MapList mapList = (MapList) obj;
			Map map = null;
			for (int k = 0; k < mapList.size(); k++)
			{
				map = (Map) mapList.get(k);
				jsonArr.put( new JSONObject(map) );
			}
		}
		else if ( obj instanceof Map )
		{
			json = new JSONObject((Map) obj);
			jsonArr.put(json);
		}
		else
		{
			json = new JSONObject();
			json.put("value", obj);
			jsonArr.put(json);
		}
		return jsonArr;
	}
	
	public static Map getSumByGroup(MapList mapList, String selectGroup, String selectValue) throws Exception{
		try {
			Map<String,Double> mSumByGroup = new HashMap<String,Double>();
			Map map = null;
			String sGroup = null;
			String sValue = null;
			Double dValue = 0d;
			Double dSum = 0d;
			
			for (int k = 0; k < mapList.size(); k++)
			{
				map = (Map) mapList.get(k);
				sGroup = (String) map.get(selectGroup);
				sValue = (String) map.get(selectValue);
				dValue = parseNumber(sValue);
				
				if ( mSumByGroup.containsKey(sGroup) )
				{
					dSum = mSumByGroup.get(sGroup) + dValue;
					mSumByGroup.put(sGroup, dSum);
				}
				else
				{
					mSumByGroup.put(sGroup, dValue);
				}
			}
			return mSumByGroup;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static MapList convertMap2MapList(Map map, String keyExpr, String valueExpr) {
		try {
			Iterator iter = map.keySet().iterator();
			
			MapList mlReturn = new MapList();
			String sKey = null;
			Double dValue = null;
			Map mTemp = null;
			
			while ( iter.hasNext() )
			{
				sKey = (String) iter.next();
				dValue = (Double) map.get(sKey);
				
				mTemp = new HashMap();
				mTemp.put(keyExpr, sKey);
				mTemp.put(valueExpr, dValue);
				
				mlReturn.add(mTemp);
			}
			
			return mlReturn;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static double parseNumber(String str) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			System.err.println("ERROR : DataProcessingUtil : parseNumber : str - " + str);
			return 0;
		}
	}
}
