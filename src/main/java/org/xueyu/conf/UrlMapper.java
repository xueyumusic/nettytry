package org.xueyu.conf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class UrlMapper {
	private static Map<String, Object> urlmapper = new HashMap<String, Object>();
	static {
		urlmapper.put("goo", "org.xueyu.rmi.GooFunc.func");
	}
	
	public static Object findAndInvokeFunc(String reqname) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String name = (String)urlmapper.get(reqname);
		int sep = name.lastIndexOf('.');
		String fullclassname = name.substring(0, sep);
		String funcname = name.substring(sep+1);
		Class cs = Class.forName(fullclassname);
		Method method = cs.getMethod(funcname, null);
		Object obj = method.invoke(null);
		return obj;
	}
	
	public String getName() {
		return "url mapper beans";
	}
	
	
}
