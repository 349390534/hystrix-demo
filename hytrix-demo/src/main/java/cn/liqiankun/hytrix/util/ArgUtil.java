/**
 * 
 */
package cn.liqiankun.hytrix.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liqiankun
 *
 */
public class ArgUtil {

	public static synchronized String serializeArgs(Object[] args) {
		if(args.length>=2)
			Arrays.sort(args);
		StringBuffer sb = new StringBuffer();
		for (Object obj : args) {
			Class<?> cls = obj.getClass();
			boolean isj = isJavaClass(cls);
			if(isj)
				sb.append(obj.toString());
			else
				sb.append(buildObj(obj));
		}
		return sb.toString();
	}
	public static synchronized StringBuffer buildObj(Object obj){
		StringBuffer sb =new StringBuffer();
		try {
			Map<String, String> m =BeanUtils.describe(obj);
			Set<Entry<String, String>> s = m.entrySet();
			for(Entry<String, String> en:s){
				//System.out.println(en.getKey()+":"+en.getValue());
				String pro = en.getKey();
				String value = en.getValue();
				if(StringUtils.isBlank(value))continue;
				if("class".equals(pro)){
					String c=value.split(" ")[1];
					sb.append(pro+":"+c).append(";");
				}else
					sb.append(pro+":"+value).append(";");
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return sb;
	}

	public static synchronized boolean isJavaClass(Class<?> clz) {
		return clz != null && clz.getClassLoader() == null;
	}
	
	
	/**
	 * getClassName
	 * @param obj
	 * @return
	 */
	public static synchronized String getClassName(Object obj){
		return obj.getClass().getName();
	}
}
