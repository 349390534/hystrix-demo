/**
 * 
 */
package cn.liqiankun.hytrix.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.springframework.util.Assert;

import cn.liqiankun.hytrix.command.BaseCommand;
import cn.liqiankun.hytrix.command.CommandFactory;
import cn.liqiankun.hytrix.enums.HystrixTypeEnum;

/**
 * jdk proxy InvocationHandler
 * @author liqiankun
 *
 */
public class ProxyHandler implements InvocationHandler {

	private Object obj;

	private HystrixTypeEnum hystrixTypeEnum;

	public ProxyHandler(Object obj) {
		this.obj = obj;
	}

	public ProxyHandler(Object obj, HystrixTypeEnum hystrixTypeEnum) {
		this.obj = obj;
		this.hystrixTypeEnum = hystrixTypeEnum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 * java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Assert.notNull(proxy);
		Assert.notNull(method);
		Assert.notEmpty(args);
		// 方法对应的类名
		String typeClassName = method.getDeclaringClass().getTypeName();
		// 方法名
		String methodName = method.getName();
		HystrixTypeEnum hystrixTypeEnumConf = ProxyMethodPatternConf
				.getTypeCommandFromConf(typeClassName, methodName);
		BaseCommand command = null;
		//构造handler时给定command值以该值为准，否则以配置为准
		if (null == hystrixTypeEnum && null == hystrixTypeEnumConf)
			command = CommandFactory.getCommand(obj, method, args);
		else {
			if (null == hystrixTypeEnumConf && null != hystrixTypeEnum) {
				command = CommandFactory.getCommand(obj, method, args,
						hystrixTypeEnum);
			} else if (null != hystrixTypeEnumConf && null == hystrixTypeEnum)
				command = CommandFactory.getCommand(obj, method, args,
						hystrixTypeEnumConf);
		}
		Object objTarget = command.doIt();
		return objTarget;
	}
}
