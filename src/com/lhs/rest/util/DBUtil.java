package com.lhs.rest.util;

import javax.servlet.http.HttpServletRequest;

import com.matrixone.servlet.Framework;

import matrix.db.Context;

public class DBUtil {

	public static Context getContext(HttpServletRequest request) throws Exception{
		try {
        	Context context = Framework.getContext(request); 
            return context;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
