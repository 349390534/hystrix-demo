/**
 * 
 */
package cn.liqiankun.hytrix.proxy;

import java.lang.reflect.Proxy;

import org.springframework.util.Assert;

import cn.liqiankun.hytrix.enums.HystrixTypeEnum;

/**
 * @author liqiankun
 * 代理工厂类
 */
public class JdkProxyFactory {

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> T getProxyInstance(final T obj) {
		Assert.notNull(obj, "the param obj is null");
		ProxyHandler handler = new ProxyHandler(obj);
		return getProxyInstance(obj, handler);
	}

	/**
	 * @param obj
	 * @param hystrixTypeEnum Command类型,参见枚举
	 *            {@link HystrixTypeEnum} 
	 * @return
	 */

	public static <T> T getProxyInstance(final T obj,
			HystrixTypeEnum hystrixTypeEnum) {
		Assert.notNull(obj, "the param obj is null");
		ProxyHandler handler = new ProxyHandler(obj, hystrixTypeEnum);
		return getProxyInstance(obj, handler);
	}

	@SuppressWarnings("unchecked")
	private static <T> T getProxyInstance(final T obj, ProxyHandler handler) {
		ClassLoader cl = obj.getClass().getClassLoader();
		Class<?>[] in = obj.getClass().getInterfaces();
		return (T) Proxy.newProxyInstance(cl, in, handler);
	}

}
