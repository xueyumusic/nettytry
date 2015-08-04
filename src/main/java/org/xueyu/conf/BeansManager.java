package org.xueyu.conf;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeansManager implements ApplicationContextAware {
	private static AnnotationConfigApplicationContext applicationContext = null;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		
		this.applicationContext = (AnnotationConfigApplicationContext)applicationContext;
	}

	public static AnnotationConfigApplicationContext getContext() {
		return applicationContext;
	}
}
