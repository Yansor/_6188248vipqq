package www6188248.vip.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import fh2229.com.config.AUTHConfig;
import fh2229.com.config.URLConfig;
import www6188248.vip.data.NewPeriodData;
import www6188248.vip.data.PayOrderData;
import www6188248.vip.exception.*;
import www6188248.vip.util.test.RuoKuai;
import www6188248.vip.util.test.ValidateData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 彩票相关APIs
 */
public class CPAPI {

	/*识别图片服务器地址*/
	private static String requestUrl = "http://api.ruokuai.com/create.json";

	private static String USERNAME = "freebuf";

	private static String PASSWORD = "abc895686843";

	private static String TYPEID = "1040";

	private static String SOFTID = "96958";

	private static String SOFTKEY = "9103161cf447464aac13c1f0be345698";

	private static String COOKIE = "ValidateToken=d78be816787a6d79bb2fadeff2009010; ASP.NET_SessionId=14sz3yl3yi1ktpkh5im1f1ab; CurrentSkin=h085; kangle_runat=1; LoginSessionID=fc6d06b2275534f5d3593d99b12bf37f; GAMEID=75; XYHandicap=0; multiSelect=False";


	public static String shoBiePic(String imageUrl){

		return RuoKuai.createByUrl(USERNAME,PASSWORD, TYPEID,"90",SOFTID,SOFTKEY,imageUrl);
	}

	public static void login (String validateCode){
		try {

			//设置请求头
			Map<String,String> header = new HashMap<String, String>();


			//设置请求实体
			Map<String,Object> body = new HashMap<String, Object>();
			body.put("username","freebuf002");
			body.put("password","2ae4c2c4f3e070a335327533e3a95600");
			body.put("validateCode",validateCode);

			String requestUrl = "http://6188248.vip/Login";

//			List<Map<String,Object>> bodySortedMap = Collections.emptyList();
//			bodySortedMap.add(body);

			String result =  RequestURLUtils.postUrl(requestUrl, header , body);



			ObjectMapper objectMapper = new ObjectMapper();

			ResponseData responseData = objectMapper.readValue(result.toString() , ResponseData.class);


			if(responseData.getStatus().equalsIgnoreCase("false")){
				throw new BadIllegalResponseException(responseData.getInfo());
			}
			return ;


		} catch (IOException e) {

			e.printStackTrace();

			return ;
		} catch (BadIllegalResponseException e) {
			e.printStackTrace();
		}

	}

	public void start (){
//		String validateCode = splitResult(shoBiePic("http://6188248.vip/Home/ValidateCode"));
//
//
//		//登陆成功
//		login(validateCode);

		//获取金额
//		getMoney();

		//下单1元
		try {
			while(true){
				//30秒下一次注
				Thread.sleep(30000);
				payOrder();
			}
		} catch (PayOrderException e) {
			System.out.print(e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
		}


	}

	private String splitResult(String json) {
		ObjectMapper objectMapper = new ObjectMapper();

		json = removeEnter(json);
		ValidateData validateData = null;
//		try {
//			validateData = objectMapper.readValue(json, ValidateData.class);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

//		return validateData.getResult();

		return json.substring(11,15);

	}

	private String removeEnter(String json) {


		if(Character.isSpace(json.charAt(json.length()-1))){
			return json.substring(0,json.length()-1);
		}
		return null;
	}



	public String getMoney(){

		//设置token头
		Map<String,String> header = new HashMap<String, String>();
		header.put("Cookie",
				COOKIE);


		String result = RequestURLUtils.getUrl(URLConfig.yuEURL,header);

		Element moneyEle = Jsoup.parse(result).getElementById("AvailableBalance");

		String money = null;
		if(moneyEle != null){
			money = moneyEle.childNode(0).toString();
		}

		return money;
	}

	/**
	 * 下注
	 */
	public void payOrder()throws PayOrderException,Exception {
		//QQ分分彩下单第一球请求地址
		String requestUrl = "http://6188248.vip/AddOrders/OtherOrder";

		Map<String,String> header = new HashMap<String, String>();
		header.put("Content-Type","application/x-www-form-urlencoded");
		header.put("Cookie", AUTHConfig.COOKIE);

		Map<String,Object> body = new HashMap<String, Object>();
		body.put("force",false);
		body.put("gameId",75);
		body.put("MaxBack",8);

		int id = 707;
		String amount = "5";//下注金额
		int goal = 707;
		String odds = "1.96";
		String orderListFormat = String.format("[{\"id\":%d,\"amount\":\"%s\",\"goal\":%d,\"odds\":\"%s\"}]",id , amount , goal , odds );
		body.put("orderlist",orderListFormat);
		String periodId = getNewPeriodID();

		if(periodId == null){
			throw new Exception("获取下注期数失败!!");
		}

		body.put("periodID",periodId);
		body.put("selectBack",8);


//		List<Map<String,Object>> bodySortedMap = Collections.emptyList();
//		bodySortedMap.add(body);
		String result = RequestURLUtils.postUrl(requestUrl,header, body) ;

		try {
			PayOrderData payOrderData = JackSonUtils.objectMapper.readValue(result, PayOrderData.class);
			if(payOrderData.isStatus() ){
                System.out.print("\n"+payOrderData.getInfo());

            }else{

				if(payOrderData.getInfo().contains("投注参数错误，请刷新页面重新投注")){
					throw new RequestParamMissingException();
				}else if(payOrderData.getInfo().contains("参数错误，请检查提交的订单是否正确！")){
					throw new RequestParamValueWrongException();
				}else if(payOrderData.getInfo().contains("您下注的期数，不是开盘中，请刷新页面")){
					throw new PeriodIdWrongException();
				}else if(payOrderData.getInfo().contains("您的操作过于频繁，请稍后提交")){
					throw new OperationTooMuchException();
				}
				throw new PayOrderException(payOrderData.getInfo());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}

	private String getNewPeriodID() {

		String requestUrl = "http://6188248.vip/Shared/GetNewPeriod?gameID=75";

		Map<String,String> header = new HashMap<String, String>();

		String result = RequestURLUtils.getUrl(requestUrl, header);

		try {
			NewPeriodData newPeriodData = JackSonUtils.objectMapper.readValue(result, NewPeriodData.class);

			return newPeriodData.getFid();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}
