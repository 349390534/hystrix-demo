/**
 * 
 */
package cn.liqiankun.hytrix.proxy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
/*import org.aspectj.lang.annotation.AfterReturning;
 import org.aspectj.lang.annotation.AfterThrowing;
 import org.aspectj.lang.annotation.Around;
 import org.aspectj.lang.annotation.Aspect;
 import org.aspectj.lang.annotation.Before;
 import org.aspectj.lang.annotation.Pointcut;*/
import org.springframework.stereotype.Component;

import cn.liqiankun.hytrix.command.BaseCommand;
import cn.liqiankun.hytrix.command.CommandFactory;
import cn.liqiankun.hytrix.command.RequestCacheAopCommand;
import cn.liqiankun.hytrix.enums.HystrixTypeEnum;
import cn.liqiankun.hytrix.util.ArgUtil;


/**
 * @author liqiankun
 *
 */
@Aspect
@Component
public class ProxyCommandInterceptor {

	@Pointcut("execution (* cn.liqiankun.hytrix.service..*.*(..))")
	private void anyMethod() {
	}

	/*
	 * // 前置通知
	 * 
	 * @Before("anyMethod()") public void doBefore() {
	 * System.out.println("前置通知--"); }
	 * 
	 * // 声明后置通知
	 * 
	 * @AfterReturning(pointcut = "anyMethod()", returning = "result") public
	 * void doAfterReturning(String result) { System.out.println("后置通知");
	 * System.out.println("---" + result + "---"); }
	 * 
	 * // 声明例外通知
	 * 
	 * @AfterThrowing(pointcut = "anyMethod()", throwing = "e") public void
	 * doAfterThrowing(Exception e) { System.out.println("发生异常:" +
	 * e.getMessage()); }
	 * 
	 * // 声明最终通知
	 * 
	 * @After("anyMethod()") public void doAfter() { System.out.println("最终通知");
	 * }
	 */

	// 声明环绕通知
	@Around("anyMethod()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		/*System.out.println("进入方法---环绕通知");
		String kind = pjp.getKind();
		System.out.println("kind:" + kind);
		System.out.println("signature:" + pjp.getSignature().getName());
		System.out.println("targetClass:" + pjp.getTarget().getClass());
		System.out.println("this:" + pjp.getThis());*/
		// 方法对应的类名
		String typeClassName = pjp.getTarget().getClass().getTypeName();
		// 方法名
		String methodName = pjp.getSignature().getName();
		HystrixTypeEnum hystrixTypeEnum = ProxyMethodPatternConf
				.getTypeCommandFromConf(typeClassName, methodName);
		BaseCommand command = null;
		// 构造handler时给定command值以该值为准，否则以配置为准
		if (HystrixTypeEnum.WITH_FALL_BACK == hystrixTypeEnum) {
			// TODO
			System.out.println("WITH_FALL_BACK");
		} else {
			Object objTarget = null;
			if (null == hystrixTypeEnum)
				command = CommandFactory.getCommand(pjp);
			else {
				command = CommandFactory.getCommand(pjp, hystrixTypeEnum);
			}
			
			if(HystrixTypeEnum.REQUEST_CACHE == hystrixTypeEnum){
				if(command instanceof RequestCacheAopCommand){
					RequestCacheAopCommand rc = (RequestCacheAopCommand)command;
					//将参数转为string作为key
					Object[] args = pjp.getArgs();
					String cacheKey = ArgUtil.serializeArgs(args);
					rc.setCacheKey(cacheKey);
				} 
			}
			objTarget = command.doIt();
			//System.out.println("退出方法---环绕通知");
			return objTarget;
		}
		return null;
	}

}
