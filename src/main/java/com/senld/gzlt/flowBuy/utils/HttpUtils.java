package com.senld.gzlt.flowBuy.utils;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.senld.gzlt.flowBuy.base.HttpCode;
import com.senld.gzlt.flowBuy.config.SystemConfig;
import com.senld.gzlt.flowBuy.entity.ErrorLog;
import com.senld.gzlt.flowBuy.exception.SenldException;
import com.senld.gzlt.flowBuy.service.ErrorLogService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author YoonaLt
 */
@Component
@Slf4j
public class HttpUtils {

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private ErrorLogService errorLogService;

	public Map<String, Object> get(String url, String name, String jsonStr) throws Exception {
		String result = getHandle(url, name, jsonStr);
		return JsonUtil.fromJson(result);
	}

	private String getHandle(String url, String name, String jsonStr) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建uri
		URIBuilder builder = new URIBuilder(systemConfig.getPrefixUrl() + url);
		URI uri = builder.build();
		// 创建http GET请求
		HttpGet httpGet = new HttpGet(uri);
		String apiKey = systemConfig.getKey();
		String apiSecret = systemConfig.getSecret();
		String prodId = systemConfig.getProdId();
		String nonce = UUID.randomUUID().toString();
		Long time = new Date().getTime();
		HashMap<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("X-V-Key", apiKey);
		paramsMap.put("X-V-Nonce", nonce);
		paramsMap.put("X-V-Time", String.valueOf(time));
		String sign = sign(paramsMap, apiSecret);
		// checkSignature(paramsMap, apiSecret,
		// "Gordf7eun2jqlNEm8SEsihp+2/lwQP4wtepLCVRAKcw=");
		log.info("signResult:" + sign);
		httpGet.setHeader("X-V-Key", apiKey);
		httpGet.setHeader("X-V-Nonce", nonce);
		httpGet.setHeader("X-V-Time", String.valueOf(time));
		httpGet.setHeader("X-V-ProdId", prodId);
		httpGet.setHeader("X-V-Signature", sign);
		// 执行http的post请求
		CloseableHttpResponse response = httpclient.execute(httpGet);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		if (response.getStatusLine().getStatusCode() != 200) {
			Map<String, Object> map = JsonUtil.fromJson(result);
			// 记录错误操作日志
			saveErrorLog(name, systemConfig.getPrefixUrl() + url, jsonStr, "GET", map);
			log.error("远程调用异常：code={},error_code={},message={}", (String) map.get("code"), (String) map.get("error_code"), (String) map.get("message"));
			throw new SenldException("服务器运行异常", HttpCode.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getForList(String url, String name, String jsonStr) throws Exception {
		String result = getHandle(url, name, jsonStr);
		return JsonUtil.fromJson(result, List.class);
	}

	private String posthandle(String url, String name, String jsonStr) throws Exception {
		// 创建httpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建post请求方式实例
		HttpPost httpPost = new HttpPost(systemConfig.getPrefixUrl() + url);

		// 设置请求头 发送的是json数据格式
		httpPost.setHeader("Content-type", "application/json;charset=utf-8");
		httpPost.setHeader("Connection", "Close");
		String apiKey = systemConfig.getKey();
		String apiSecret = systemConfig.getSecret();
		String prodId = systemConfig.getProdId();
		String nonce = UUID.randomUUID().toString();
		Long time = new Date().getTime();
		HashMap<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("X-V-Key", apiKey);
		paramsMap.put("X-V-Nonce", nonce);
		paramsMap.put("X-V-Time", String.valueOf(time));
		String sign = sign(paramsMap, apiSecret);
		// checkSignature(paramsMap, apiSecret,
		// "Gordf7eun2jqlNEm8SEsihp+2/lwQP4wtepLCVRAKcw=");
		// LOGGER.info("signResult:" + sign);
		httpPost.setHeader("X-V-Key", apiKey);
		httpPost.setHeader("X-V-Nonce", nonce);
		httpPost.setHeader("X-V-Time", String.valueOf(time));
		httpPost.setHeader("X-V-ProdId", prodId);
		httpPost.setHeader("X-V-Signature", sign);
		// 设置参数---设置消息实体 也就是携带的数据
		StringEntity entity = new StringEntity(jsonStr, Charset.forName("UTF-8"));
		// 设置编码格式
		entity.setContentEncoding("UTF-8");
		// 发送Json格式的数据请求
		entity.setContentType("application/json");
		// 把请求消息实体塞进去
		httpPost.setEntity(entity);

		// 执行http的post请求
		CloseableHttpResponse response = httpClient.execute(httpPost);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		if (response.getStatusLine().getStatusCode() != 200) {
			Map<String, Object> map = JsonUtil.fromJson(result);
			// 记录错误操作日志
			log.error("远程调用异常：code={},error_code={},message={}", (String) map.get("code"), (String) map.get("error_code"), (String) map.get("message"));
			throw new SenldException("服务器运行异常", HttpCode.INTERNAL_SERVER_ERROR.value());
		}
		return result;
	}

	/**
	 * 发送 post 请求
	 *
	 * @param url
	 *            请求地址
	 * @param jsonStr
	 *            Form表单json字符串
	 * @return 请求结果
	 */
	public Map<String, Object> post(String url, String name, String jsonStr) throws Exception {
		String result = posthandle(url, name, jsonStr);
		Map<String, Object> map = JsonUtil.fromJson(result);
		return map;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> postForList(String url, String name, String jsonStr) throws Exception {
		String result = posthandle(url, name, jsonStr);
		List<Map<String, Object>> map = JsonUtil.fromJson(result, List.class);
		return map;
	}

	private void saveErrorLog(String name, String url, String jsonStr, String type, Map<String, Object> map) {
		ErrorLog errorLog = new ErrorLog();
		errorLog.setName(name);
		errorLog.setUrl(url);
		errorLog.setParam(jsonStr);
		errorLog.setType("POST");
		errorLog.setCode((String) map.get("code"));
		errorLog.setMessage((String) map.get("message"));
		errorLog.setErrorCode((String) map.get("error_code"));
		errorLog.setCreateTime(new Date());
		errorLogService.save(errorLog);
	}

	/**
	 * 获取请求ip地址
	 */
	public static String getRequestIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

	/**
	 * 签名计算
	 * 
	 * @param paramsMap
	 * @param key
	 * @return
	 */

	protected static String sign(Map<String, String> paramsMap, String key) {
		String unSign = orderParamsMap(paramsMap);
		// LOGGER.warn("***待签名字符串[" + unSign + "]");
		String sign = null;
		try {
			sign = AbstractCoder.encryptBASE64(AbstractCoder.encryptHMAC(unSign.getBytes(), key.getBytes()));
		} catch (Exception e) {
			log.warn("*****************************计算签名异常*************************", e);
		}
		return sign;
	}

	protected static boolean checkSignature(HashMap<String, String> paramsMap, String appSecret, String xCaSignature) {
		String sign = sign(paramsMap, appSecret);
		if (StringUtils.equalsIgnoreCase(sign, xCaSignature)) {
			// LOGGER.info("*****************************请求验签通过!************************");
			return true;
		} else {
			log.warn("*****************************请求验签不通过!请检查相应的签名内容*************************");
			log.warn("***签名方法一结果sign:[" + sign + "],实际传入的xCaSignature[" + xCaSignature + "]");
		}
		return false;
	}

	public static String orderParamsMap(Map<String, String> paramsMap) {
		List<String> keys = new ArrayList<>(paramsMap.keySet());
		Collections.sort(keys);
		StringBuilder paramStringBuffer = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = paramsMap.get(key);
			// 过滤value为空的参数键值对
			paramStringBuffer.append(key).append("=").append(value).append("&");
		}
		// 去掉请求字符串末尾的最后一个&号
		if (paramStringBuffer.length() - 1 == paramStringBuffer.lastIndexOf("&")) {
			paramStringBuffer.deleteCharAt(paramStringBuffer.lastIndexOf("&"));
		}
		return paramStringBuffer.toString();
	}

}
