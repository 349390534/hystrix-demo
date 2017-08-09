/**
 * 
 */
package cn.liqiankun.hytrix.command;

import org.aspectj.lang.ProceedingJoinPoint;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @author liqiankun
 *
 */
public class InvokeAopCommand extends HystrixCommand<Object> implements
		BaseCommand {
	private final ProceedingJoinPoint pjp;

	protected InvokeAopCommand(ProceedingJoinPoint pjp) {
		super(HystrixCommandGroupKey.Factory.asKey(InvokeAopCommand.class.getName()));
		this.pjp = pjp;
	}

	@Override
	protected Object run() throws Exception {
		Object reObj=null;
		try {
			reObj = pjp.proceed();
		} catch (Throwable e) {
			throw new Exception(e);
		}
		return reObj;
	}

	@Override
	public Object doIt() throws Exception {
		//TODO 
		System.out.println("InvokeAopCommand just do it");
		return super.execute();
	}
	
	
}
