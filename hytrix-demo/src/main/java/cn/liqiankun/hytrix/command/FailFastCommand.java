/**
 * 
 */
package cn.liqiankun.hytrix.command;

import java.lang.reflect.Method;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import cn.liqiankun.hytrix.exception.FailFastException;

/**
 * @author liqiankun
 *
 */
public class FailFastCommand extends HystrixCommand<Object> implements
		BaseCommand {
	private final Object targetObj;
	private final Method method;
	private final Object[] args;
	//failfast开关，届时可以从全局配置中心获取 TODO
	private boolean doFailFast = false;

	protected FailFastCommand(Object targetObj, Method method, Object[] args) {
		super(HystrixCommandGroupKey.Factory.asKey("FailFastCommandGroup"));
		this.targetObj = targetObj;
		this.method = method;
		this.args = args;
	}

	@Override
	protected Object run() throws Exception {
		if(doFailFast) {
			throw new FailFastException("fail fast active!");
		} else {
			Object reObj = method.invoke(targetObj, args);
			return reObj;
		}
	}

	@Override
	public Object doIt() throws Exception {
		//TODO 
		System.out.println("FailFastCommand just do it");
		return super.execute();
	}
	
	
}
