package com.zrgj.util;



/**
 * 短信发送工具类
 */
public class SMSUtils {
	public static final String VALIDATE_CODE = "SMS_15171419790";//发送短信验证码
	public static final String ORDER_NOTICE = "SMS_15171419700";//体检预约成功通知

	public static void sendShortMessage(String templateCode,String phoneNumbers,String param) {
		// 设置超时时间-可自行调整
		Client client = new Client();
		client.setAppId("hw_10663");     //开发者ID，在【设置】-【开发设置】中获取
		client.setSecretKey("57efd6cbf88e4eaf4bf912de32c63700");    //开发者密钥，在【设置】-【开发设置】中获取
		client.setVersion("1.0");

		/**
		 *   json格式可在 bejson.com 进行校验
		 */
		Client.Request request = new Client.Request();
		request.setBizContent("{\"mobile\":[\""+phoneNumbers+"\"]," +
				"\"type\":0,\"template_id\":\"ST_2020101100000005\"," +
				"\"sign\":\"智慧教学云平台\"," +
           "\"send_time\":\"\",\"params\":{\"code\":"+param+"}}");
		// 这里是json字符串，send_time 为空时可以为null, params 为空时可以为null,短信签名填写审核后的签名本身，不需要填写签名id
		request.setMethod("sms.message.send");
		System.out.println( client.execute(request) );
	}
}
