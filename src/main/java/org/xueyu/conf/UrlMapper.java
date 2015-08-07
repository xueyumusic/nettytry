package org.xueyu.conf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class UrlMapper implements BeanPostProcessor {
	private static Map<String, Object> urlmapper = new HashMap<String, Object>();
	private static Map<String, Method> methodMapper = new HashMap<String, Method>();
	static {
		urlmapper.put("goo", "org.xueyu.rmi.GooFunc.func");
		
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
				Method method = cs.getMethod(funcname, null);
				//Object obj = method.invoke(null);
				methodMapper.put(entry.getKey(), method);
			} catch (Exception ex) {
				
			}
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
	
	public String getName() {
		return "url mapper beans";
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		
		
	}
	
	
}
