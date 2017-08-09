package cn.liqiankun.hytrix.web.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.liqiankun.hytrix.service.TestProxyService;

@RestController
public class IndexController {

	private AtomicInteger at = new AtomicInteger(1);
	
	@Autowired
	private TestProxyService testProxyService;
	
	@RequestMapping("index")
	public String index(){
		testProxyService.getId(""+at.incrementAndGet());
		return "hello world";
	}
}
