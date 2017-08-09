/**
 * Asynchronous Execution
 */
package cn.liqiankun.hytrix.command;

import org.aspectj.lang.ProceedingJoinPoint;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @author liqiankun
 */
public class AsyncInvokeAopCommand extends HystrixCommand<Object> implements
		BaseCommand {
	private final ProceedingJoinPoint pjp;

	protected AsyncInvokeAopCommand(ProceedingJoinPoint pjp) {
		super(HystrixCommandGroupKey.Factory.asKey(AsyncInvokeAopCommand.class.getName()));
		this.pjp = pjp;
	}

	@Override
	protected Object run() throws Exception {
		Object reObj=null;
		try {
			//显示调用，确保被代理的方法被调用
			reObj = pjp.proceed();
		} catch (Throwable e) {
			throw new Exception(e);
		}
		return reObj;
	}

	@Override
	public Object doIt() throws Exception {
		//TODO
		System.out.println("AsyncInvokeAopCommand just do it");
		//注意这里返回的是java.util.concurrent.Future对象
		super.queue();
		return null;
	}
	
	
}
