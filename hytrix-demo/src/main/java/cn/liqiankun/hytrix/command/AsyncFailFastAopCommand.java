/**
 * 
 */
package cn.liqiankun.hytrix.command;

import org.aspectj.lang.ProceedingJoinPoint;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import cn.liqiankun.hytrix.exception.FailFastException;

/**
 * @author liqiankun
 *
 */
public class AsyncFailFastAopCommand extends HystrixCommand<Object> implements
		BaseCommand {
	private final ProceedingJoinPoint pjp;
	//failfast开关，届时可以从全局配置中心获取 TODO
	private boolean doFailFast = false;

	protected AsyncFailFastAopCommand(ProceedingJoinPoint pjp) {
		super(HystrixCommandGroupKey.Factory.asKey(AsyncFailFastAopCommand.class.getName()));
		this.pjp = pjp;
	}

	@Override
	protected Object run() throws Exception {
		if(doFailFast) {
			throw new FailFastException("fail fast active!");
		} else {
			try {
				return pjp.proceed();
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public Object doIt() throws Exception {
		//TODO 
		System.out.println("AsyncFailFastAopCommand just do it");
		super.queue();
		return null;
	}
	
	
}
