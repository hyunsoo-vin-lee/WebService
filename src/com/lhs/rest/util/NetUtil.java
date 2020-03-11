package com.lhs.rest.util;

import javax.ws.rs.core.Response;

public class NetUtil {

	public static Response getResponse(String outStr) {
		return Response.ok().header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS")
				.header("Access-Control-Max-Age", "3600")
				.header("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization")
				.entity(outStr)
				.build();
	}
}
