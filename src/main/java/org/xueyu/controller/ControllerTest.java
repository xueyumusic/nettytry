package org.xueyu.controller;

import io.netty.handler.codec.http.FullHttpRequest;

import org.springframework.stereotype.Component;
import org.xueyu.view.Thymeleaf;


@Component
public class ControllerTest {

	public String test(FullHttpRequest origreq) {
		//FullHttpRequest req = origreq.copy();
		
		Thymeleaf thymeleaf = new Thymeleaf();
		String respstr = thymeleaf.view();
		System.out.println("##controller test return:"+respstr);
		return respstr;
	}
	
	public String testPost(FullHttpRequest origreq) {
		return "post test";
	}
}
