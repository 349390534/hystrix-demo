/**
 * 
 */
package cn.liqiankun.hytrix.command;

import org.springframework.util.Assert;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * 
 * 
 * @author liqiankun
 */
public class UsingWithFallBackCommandUtil {

	private Invoke invoke;
	private CallBack callBack;

	public interface Invoke {
		Object process();
	}

	public interface CallBack {
		Object callBack();
	}

	public static synchronized UsingWithFallBackCommandUtil init() {
		return new UsingWithFallBackCommandUtil();
	}

	public UsingWithFallBackCommandUtil setInvoke(Invoke invoke) {
		Assert.notNull(invoke);
		this.invoke = invoke;
		return this;
	}

	public UsingWithFallBackCommandUtil setCallBack(CallBack callBack) {
		Assert.notNull(callBack);
		this.callBack = callBack;
		return this;
	}

	/**
	 * call this method
	 * @return
	 */
	public Object execute() {
		if (null != invoke) {
			//Will be implemented method wrapped
			InvokeCommand command = new InvokeCommand(invoke, callBack);
			return command.execute();
		}
		return null;
	}

	/**
	 * 内部执行类
	 * @author liqiankun
	 *
	 */
	private static class InvokeCommand extends HystrixCommand<Object> {

		private Invoke invoke;
		private CallBack callBack;

		protected InvokeCommand(Invoke invoke, CallBack callBack) {
			super(HystrixCommandGroupKey.Factory.asKey(InvokeCommand.class
					.getName()));
			this.invoke = invoke;
			this.callBack = callBack;
		}

		@Override
		protected Object getFallback() {
			return new WithFallbackCommand(callBack).execute();
		}

		@Override
		protected Object run() throws Exception {
			return invoke.process();
		}

	}
	private static class WithFallbackCommand extends HystrixCommand<Object> {
		
		private CallBack callBack;
		
		protected WithFallbackCommand(CallBack callBack) {
			super(HystrixCommandGroupKey.Factory.asKey(InvokeCommand.class
					.getName()));
			this.callBack = callBack;
		}
		
		@Override
		protected Object getFallback() {
			return null;
		}
		
		@Override
		protected Object run() throws Exception {
			Assert.notNull(callBack);
			return callBack.callBack();
		}
		
	}
}
