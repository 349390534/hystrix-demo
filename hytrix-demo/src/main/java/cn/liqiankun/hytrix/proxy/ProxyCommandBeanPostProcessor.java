/**
 * 
 */
package cn.liqiankun.hytrix.proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author liqiankun
 *
 */
//@Component
public class ProxyCommandBeanPostProcessor implements BeanPostProcessor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#
	 * postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		/*if(bean instanceof IProductService){
			System.out.println("=======beanProsserAfter:"+bean.getClass() +"--"+beanName);
			IProductService ip=(IProductService)bean;
	    	IProductService productServiceProxy = JdkProxyFactory.getProxyInstance(ip);
	    	//bean=productServiceProxy;
		}*/
		return bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#
	 * postProcessBeforeInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		//System.out.println("postProcessBeforeInitialization:"+bean.getClass() +"--"+beanName);
		return bean;
	}

}
