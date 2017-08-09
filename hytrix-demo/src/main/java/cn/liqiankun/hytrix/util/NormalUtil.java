package cn.liqiankun.hytrix.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 通用工具类
 * @author wangdefeng
 *
 */
public class NormalUtil {

	/**
	 * 向jsonStr中添加一条key-value数据
	 * @param key
	 * @param value
	 * @param jsonStr
	 * @return
	 */
	public static String addUserMeta(String key, String value, String jsonStr){
		JSONObject json = null;
		if(null == jsonStr || jsonStr.length() == 0){
			json = new JSONObject();
		}else{
			json = JSONObject.parseObject(jsonStr);
		}
		json.put(key, value);
		return json.toJSONString();
	}
	
	/**
	 * 将key-value生成一条jsonStr
	 * @param key
	 * @param value
	 * @param jsonStr
	 * @return
	 */
	public static String addUserMeta(String key, String value){
		return addUserMeta(key, value , null);
	}
	
	/**
	 * 将Map添加到jsonStr中
	 * @param map
	 * @param jsonStr
	 * @return
	 */
	public static String putUserMeta(Map<String, String> map, String jsonStr){
		JSONObject json = null;
		if(null == jsonStr || jsonStr.length() == 0){
			json = new JSONObject();
		}else{
			json = JSONObject.parseObject(jsonStr);
		}
		json.putAll(map);
		return json.toJSONString();
	}
	
	/**
	 * 将Map转换为jsonStr
	 * @param map
	 * @return
	 */
	public static String putUserMeta(Map<String, String> map){
		return putUserMeta(map, null);
	}
	
	/**
	 * 序列化对象
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static byte[] serialize(Object object) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(object);
		byte[] bytes = baos.toByteArray();
		return bytes;
	}
	
	/**
	 * 反序列化对象
	 * @param bytes
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object unserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		return ois.readObject();
	}
	
}
