package cn.liqiankun.hytrix.service.impl;

import java.util.Random;

import org.springframework.stereotype.Service;

import cn.liqiankun.hytrix.service.TestProxyService;

@Service
public class TestProxyServiceImpl implements TestProxyService {

	@Override
	public String getId(String id) {
			return new Random().nextInt()+"-"+id;
	}

	
}
