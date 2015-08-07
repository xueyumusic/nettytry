package org.xueyu.conf;

import io.netty.handler.codec.http.FullHttpRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class UrlMapper {
	private static Map<String, Object> urlmapper = new HashMap<String, Object>();
	private static Map<String, Method> methodMapper = new HashMap<String, Method>();
	private static Map<String, String> clsMapper = new HashMap<String, String>();
	static {
		//urlmapper.put("goo", "org.xueyu.rmi.GooFunc.func");
		urlmapper.put("/testc", "org.xueyu.controller.ControllerTest.test");
		
		initUrlMapper();
	}
	
	private static void initUrlMapper() {
		for (Map.Entry<String, Object> entry: urlmapper.entrySet()) {
			//String value = (String)entry.getValue();
			String name = (String)urlmapper.get(entry.getKey());
			int sep = name.lastIndexOf('.');
			String fullclassname = name.substring(0, sep);
			String funcname = name.substring(sep+1);
			try {
				System.out.println("##full:"+fullclassname+"  funcname:"+funcname);
				Class<?> cs = Class.forName(fullclassname);
				Method method = cs.getMethod(funcname, FullHttpRequest.class);
				//Object obj = method.invoke(null);
				methodMapper.put(entry.getKey(), method);
				clsMapper.put(entry.getKey(), fullclassname);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		for (Map.Entry<String, Method> me: methodMapper.entrySet()) {
			System.out.println("##in mehtod mapper:"+me.getKey()+"  method:"+me.getValue().getName());
		}
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
	
	public static Method findMethod(String uri) {
		Method method = methodMapper.get(uri);
		return method;
	}
	
	public static String findClassName(String uri) {
		String clsname = clsMapper.get(uri);
		return clsname;
	}
	
	public String getName() {
		return "url mapper beans";
	}

	
	
}
