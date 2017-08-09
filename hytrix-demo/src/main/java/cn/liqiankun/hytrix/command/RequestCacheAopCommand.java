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
public class RequestCacheAopCommand extends HystrixCommand<Object> implements
		BaseCommand {
	private final ProceedingJoinPoint pjp;

	private String cacheKey;
	
	protected RequestCacheAopCommand(ProceedingJoinPoint pjp) {
		super(HystrixCommandGroupKey.Factory.asKey(RequestCacheAopCommand.class.getName()));
		this.pjp = pjp;
	}
	
	public void setCacheKey(String cacheKey){
		this.cacheKey=cacheKey;
	}
	
	@Override
	protected Object run() throws Exception {
		Object reObj=null;
		try {
			reObj = pjp.proceed();
		} catch (Throwable e) {
			throw new Exception(e);
		}
		System.out.println("RequestCacheAopCommand run:"+cacheKey);
		return reObj;
	}

	@Override
	public Object doIt() throws Exception {
		//TODO 
		return super.execute();
	}

	@Override
	protected String getCacheKey() {
		System.out.println("getCacheKey "+cacheKey);
		return cacheKey;
	}
	
	
}
