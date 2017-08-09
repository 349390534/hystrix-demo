/**
 * 
 */
package cn.liqiankun.hytrix.command;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @author liqiankun
 *
 */
public class FailSilentCommand extends HystrixCommand<Object> implements
		BaseCommand {
	private final Object targetObj;
	private final Method method;
	private final Object[] args;

	protected FailSilentCommand(Object targetObj, Method method, Object[] args) {
		super(HystrixCommandGroupKey.Factory.asKey("FailSilentCommandGroup"));
		this.targetObj = targetObj;
		this.method = method;
		this.args = args;
	}

	@Override
	protected Object run() throws Exception {
		Object reObj = null;
		reObj = method.invoke(targetObj, args);
		System.out.println(method.getName() + " invoke obj "
				+ targetObj.getClass() + ",the args is " + args);
		return reObj;
	}

	
	@Override
    protected Object getFallback() {
		System.out.println("fallback");
		Type returnType = method.getGenericReturnType();
		String clsn = returnType.getTypeName();
		System.out.println("" + clsn);
		if (returnType instanceof ParameterizedType){
			/**如果是泛型类型 */
			Type[] types = ((ParameterizedType) returnType)
					.getActualTypeArguments();//泛型类型列表
			for (Type tp : types) {
				System.out.println("" + tp.getTypeName());
			}
		} else {
//			try {
//				return Class.forName(clsn).newInstance();
//			} catch (InstantiationException e) {
//			} catch (IllegalAccessException e) {
//			} catch (ClassNotFoundException e) {
//			}
		}
		return null;
	}
	
	@Override
	public Object doIt() throws Exception {
		//TODO 
		System.out.println("FailSilentCommand just do it");
		return super.execute();
	}
	
	
}
