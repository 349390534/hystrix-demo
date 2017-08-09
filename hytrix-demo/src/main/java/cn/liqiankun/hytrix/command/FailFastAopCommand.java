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
public class FailFastAopCommand extends HystrixCommand<Object> implements
		BaseCommand {
	private final ProceedingJoinPoint pjp;
	//failfast开关，届时可以从全局配置中心获取 TODO
	private boolean doFailFast = false;

	protected FailFastAopCommand(ProceedingJoinPoint pjp) {
		super(HystrixCommandGroupKey.Factory.asKey(FailFastAopCommand.class.getName()));
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
		System.out.println("FailFastAopCommand just do it");
		return super.execute();
	}
	
	
}
