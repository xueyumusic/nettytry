package org.xueyu.conf;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class UrlMapperPostBean implements BeanPostProcessor {
	private Map<String, Object> beanmap = new HashMap<String, Object>();
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		System.out.println("####in post bean:"+beanName);
		Class<?> cls = bean.getClass();
		System.out.println("####bean class name:"+cls.getName());
		beanmap.put(cls.getName(), bean);
		return bean;
	}
	
	public Object findBean(String clsName) {
		Object bean = beanmap.get(clsName);
		return bean;
	}

}
