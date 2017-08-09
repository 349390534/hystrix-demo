/**
 * 
 */
package cn.liqiankun.hytrix.command;

import java.lang.reflect.Method;
//import com.zhongan.life.face.app.enums.HystrixTypeEnum;

import org.aspectj.lang.ProceedingJoinPoint;

import cn.liqiankun.hytrix.enums.HystrixTypeEnum;

/**
 * @author liqiankun
 *
 */
public class CommandFactory {

	/**
	 * 默认获取invoke命令实例，正常调用
	 * 
	 * @param targetObj
	 * @param method
	 * @param args
	 * @return
	 */
	public static BaseCommand getCommand(Object targetObj, Method method,
			Object[] args) {
		return getCommand(targetObj, method, args, HystrixTypeEnum.INVOKE);
	}

	/**
	 * 根据类型获取command实例
	 * 
	 * @param targetObj
	 * @param method
	 * @param args
	 * @param hystrixType
	 *            {@link HystrixTypeEnum}
	 * @return
	 */
	public static BaseCommand getCommand(Object targetObj, Method method,
			Object[] args, HystrixTypeEnum hystrixType) {
		BaseCommand command = null;
		switch (hystrixType) {
		case INVOKE:
			command = new InvokeCommand(targetObj, method, args);
			break;
		case FAIL_FAST:
			command = new FailFastCommand(targetObj, method, args);
			break;
		case FAIL_SILENT:
			command = new FailSilentCommand(targetObj, method, args);
			break;

		default:
			command = new InvokeCommand(targetObj, method, args);
			break;
		}
		return command;
	}

	
	public static BaseCommand getCommand(ProceedingJoinPoint pjp) {
		return getCommand(pjp, HystrixTypeEnum.INVOKE);
	}
	
	public static BaseCommand getCommand(ProceedingJoinPoint pjp,
			HystrixTypeEnum hystrixType) {

		BaseCommand command = null;
		switch (hystrixType) {
		case INVOKE:
			command = new InvokeAopCommand(pjp);
			break;
		case FAIL_FAST:
			command = new FailFastAopCommand(pjp);
			break;
		case FAIL_SILENT:
			command = new FailSilentAopCommand(pjp);
			break;
		case ASYNC_INVOKE:
			command = new AsyncInvokeAopCommand(pjp);
			break;
		case ASYNC_FAIL_FAST:
			command = new AsyncFailFastAopCommand(pjp);
			break;
		case ASYNC_FAIL_SILENT:
			command = new AsyncFailSilentAopCommand(pjp);
			break;
		case REQUEST_CACHE:
			command = new RequestCacheAopCommand(pjp);
			break;

		default:
			command =  new InvokeAopCommand(pjp);
			break;
		}
		return command;
	
	}

	
}
