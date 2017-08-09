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
public class FailSilentAopCommand extends HystrixCommand<Object> implements
		BaseCommand {
	private final ProceedingJoinPoint pjp;

	protected FailSilentAopCommand(ProceedingJoinPoint pjp) {
		super(HystrixCommandGroupKey.Factory.asKey(FailSilentAopCommand.class.getName()));
		this.pjp = pjp;
	}

	@Override
	protected Object run() throws Exception {
		try {
			return pjp.proceed();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	@Override
    protected Object getFallback() {
		System.out.println("fallback");
		return null;
	}
	
	@Override
	public Object doIt() throws Exception {
		//TODO 
		System.out.println("FailSilentAopCommand just do it");
		return super.execute();
	}
	
	
}
