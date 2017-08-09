/**
 * 
 */
package cn.liqiankun.hytrix.proxy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.liqiankun.hytrix.enums.HystrixTypeEnum;

/**
 * @author liqiankun
 * 代理方法模式配置
 */
public class ProxyMethodPatternConf {

	/**
	 * key=classname value=command:add*|del*
	 * ex: key=cn.liqiankun.hystrix.A value=FAIL_FAST:add*|del*;ASYNC_FAIL_FAST:add*|del*;
	 */
	private static Map<String,Map<String,Set<String>>> _patternMap = new HashMap<String, Map<String,Set<String>>>(0);
	
	static{
		//读取配置 TODO
		//Test 数据
		Map<String,Set<String>> pm= new HashMap<String,Set<String>>();
		HashSet<String> patternList = new HashSet<String>();
		patternList.add("add*");
		patternList.add("query*");
		//pm.put(HystrixTypeEnum.FAIL_FAST.toString(), patternList);
		pm.put(HystrixTypeEnum.REQUEST_CACHE.toString(), patternList);
		_patternMap.put("cn.liqiankun.hytrix.service.TestProxyService", pm);
		
		Map<String,Set<String>> pm1= new HashMap<String,Set<String>>();
		HashSet<String> patternList1 = new HashSet<String>();
		patternList1.add("query*");
		pm1.put(HystrixTypeEnum.ASYNC_FAIL_FAST.toString(), patternList1);
		
		HashSet<String> patternListCache = new HashSet<String>();
		patternListCache.add("get*");
		pm1.put(HystrixTypeEnum.WITH_FALL_BACK.toString(), patternListCache);
		
		HashSet<String> patternListCache2 = new HashSet<String>();
		patternListCache2.add("list*");
		pm1.put(HystrixTypeEnum.REQUEST_CACHE.toString(), patternListCache2);
		
		_patternMap.put("cn.liqiankun.hytrix.service.impl.TestProxyServiceImpl", pm1);
	}
	
	private static final String RP ="\\*";
	/**
	 * 获取对应类方法名配置的command
	 * @param typeClassName class全路径名称
	 * @param methodName 方法名称
	 * @return
	 */
	public static HystrixTypeEnum getTypeCommandFromConf(String typeClassName,String methodName){
		HystrixTypeEnum htype = null;
		Map<String,Set<String>> pm = _patternMap.get(typeClassName);
		if(null==pm)
			return htype;
		
		HystrixTypeEnum[] hs=HystrixTypeEnum.values();
		for(HystrixTypeEnum h :hs){
			String hn=h.toString();
			Set<String> st = pm.get(hn);
			if(null == st)continue;
			for(String mp :st){
				if(mp.startsWith("*") && !mp.endsWith("*")){
					//*method
					 if(methodName.endsWith(mp.replaceAll(RP, "")))
						 return h;
				}else if(!mp.startsWith("*") && mp.endsWith("*")){
					//method*
					 if(methodName.startsWith(mp.replaceAll(RP, "")))
						 return h;
				} else if(!mp.startsWith("*") && !mp.endsWith("*")){
					//*method*
					if(methodName.contains(mp.replaceAll(RP, "")))
						 return h;
				}
			}
		}
		return htype;
	}
}
