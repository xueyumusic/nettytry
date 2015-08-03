package org.xueyu.view;

import java.util.HashMap;
import java.util.Map;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;

public class Thymeleaf {
	private static TemplateEngine templateEngine;
	
	static {
		initializeTemplateEngine();
	}
	
	private static void initializeTemplateEngine() {
		//FileTemplateResolver templateResolver = new FileTemplateResolver();
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setTemplateMode("XHTML");
		templateResolver.setPrefix("templates/");
		templateResolver.setSuffix(".thy");
		templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
		templateResolver.setCacheable(true);
		
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
	}
	
	public String view() {
		//Map<String, String> cmap = new HashMap<String, String>();
		//cmap.put("par1", "hello yu, wo zai zhe");
		Context context = new Context();
		context.setVariable("par1", "ni hao ma");
		String ret = templateEngine.process("d", context);
		return ret;
		
	}
}
