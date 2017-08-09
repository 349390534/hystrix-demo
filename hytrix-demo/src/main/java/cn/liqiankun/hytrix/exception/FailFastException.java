/**
 * 
 */
package cn.liqiankun.hytrix.exception;

/**
 * @author liqiankun
 *
 */
public class FailFastException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1013630945820292910L;

	public FailFastException() {
		super();
	}
	public FailFastException(String message) {
		super(message);
	}
	
	
}
