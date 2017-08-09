/**
 * 
 */
package cn.liqiankun.hytrix.command;

/**
 * @author liqiankun
 *
 */
public interface BaseCommand {

	/**
	 * 执行命令代理
	 * @return
	 * @throws Exception
	 */
	Object doIt() throws Exception ;
}
