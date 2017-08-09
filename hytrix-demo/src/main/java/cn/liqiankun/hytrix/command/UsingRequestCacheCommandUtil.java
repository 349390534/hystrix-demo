/**
 * 
 */
package cn.liqiankun.hytrix.command;

import org.springframework.util.Assert;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * request scope cacheUtil,use it first invoke the startCache() and stopCache()
 * to stop it when the end Notice :The call of these methods need to be
 * performed in a thread
 * 
 * @author liqiankun
 */
public class UsingRequestCacheCommandUtil {

	/**
	 * get a instance
	 * 
	 * @return
	 */
	public static synchronized UsingRequestCacheCommandUtil get() {
		return new UsingRequestCacheCommandUtil();
	}

	public HystrixRequestContext startCache() {
		HystrixRequestContext context = HystrixRequestContext
				.initializeContext();
		return context;
	}

	public void stopCache(HystrixRequestContext context) {
		Assert.notNull(context);
		context.shutdown();
	}

	/*private String cacheKey;

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}

	public String getCacheKey() {
		return cacheKey;
	}*/

}
