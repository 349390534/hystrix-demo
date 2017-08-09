/**
 * 
 */
package cn.liqiankun.hytrix.enums;

/**
 * hystrixcommand 类型枚举
 * @author liqiankun
 *
 */
public enum HystrixTypeEnum {
	/**
	 * INVOKE
	 */
	INVOKE,
	/**
	 * FAIL_FAST
	 */
	FAIL_FAST,
	/**
	 * FAIL_SILENT
	 */
	FAIL_SILENT,
	/**
	 * ASYNC_INVOKE
	 */
	ASYNC_INVOKE,
	/**
	 * ASYNC_FAIL_FAST
	 */
	ASYNC_FAIL_FAST,
	/**
	 * ASYNC_FAIL_SILENT
	 */
	ASYNC_FAIL_SILENT,
	/**
	 * WITH_FALL_BACK
	 */
	WITH_FALL_BACK,
	/**
	 * REQUEST_CACHE
	 */
	REQUEST_CACHE,
	
	;
	
	
	
}
