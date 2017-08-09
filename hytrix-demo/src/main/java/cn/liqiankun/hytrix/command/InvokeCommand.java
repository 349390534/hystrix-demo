/**
 * 
 */
package cn.liqiankun.hytrix.command;

import java.lang.reflect.Method;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @author liqiankun
 *
 */
public class InvokeCommand extends HystrixCommand<Object> implements
		BaseCommand {
	private final Object targetObj;
	private final Method method;
	private final Object[] args;

	protected InvokeCommand(Object targetObj, Method method, Object[] args) {
		super(HystrixCommandGroupKey.Factory.asKey("FailFastCommandGroup"));
		this.targetObj = targetObj;
		this.method = method;
		this.args = args;
	}

	@Override
	protected Object run() throws Exception {
		Object reObj = method.invoke(targetObj, args);
		return reObj;
	}

	@Override
	public Object doIt() throws Exception {
		//TODO 
		System.out.println("InvokeCommand just do it");
		return super.execute();
	}
	
	
}
