package fh2229.com.util;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.tools.packager.Log;
import fh2229.com.config.*;
import fh2229.com.data.ChangeOddsData;
import fh2229.com.data.PayOrderData;
import fh2229.com.data.PeriodResultListData;
import fh2229.com.data.ValidateData;
import fh2229.com.enums.OddEvenEnum;
import fh2229.com.exception.*;
import fh2229.com.strategy.PayAmount1Strategy;
import fh2229.com.strategy.PayType1Strategy;
import fh2229.com.thread.GetNewPeriodThread;
import fh2229.com.util.test.RuoKuai;
import lombok.extern.log4j.Log4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static fh2229.com.config.GameConfig.LOGENABLE;

/**
 * 凤凰彩票相关APIs
 */
@Log4j
public class FHAPI {



	//保存了三个前记录
	//去重排序容器
	public   static UnrepeatedList unrepeatedList = new UnrepeatedList(3);

	private int [] currentPayAmount = GameConfig.payAmount1;

	//当前采用的下注数目策略
	private PayAmount1Strategy payAmountStrategy = new PayAmount1Strategy();

	//当前采用的下注类型策略
	private  static PayType1Strategy currentPayType1Strategy = new PayType1Strategy();

//	private OddEvenEnum currentPayType ;

	private OddEvenEnum nextPayType;

	private OddEvenEnum previousPayType;

	public static int payStep = 0 ;

	private int shouldPayAmount = GameConfig.payAmount6[0]; //应当支付金额为第一次的金额

	//上一期的结果
	public static String previousResult ;

	public static String NEWPERIODID ;

	//开始投注时间
	public static String STARTTIME ;

	//结束投注时间
	public static String CLOSETIME;

	//服务器时间
	public static String SERVERTIME ;



	static{
		//读取最新三次记录 只是一次
//		load3PeriodResult2Property();
	}

	private static void load3PeriodResult2Property() {

		String requestUrl = "http://fh2229.com/Result/GetLotteryResultList?gameID=75&pageSize=20&pageIndex=1&_=1518311883568";


		String result = RequestURLUtils.getUrl(requestUrl, AUTHUtil.buildCookieMapOnly());

		try {
			PeriodResultListData periodResultListData = JackSonUtils.objectMapper.readValue(result , PeriodResultListData.class);

			if(!CollectionUtils.isEmpty(Arrays.asList(periodResultListData.getList()))){
				if(Arrays.asList(periodResultListData.getList()).size() >= 3 ){

					String record0 = periodResultListData.getList()[0].getResult();
					String record1 = periodResultListData.getList()[1].getResult();
					String record2 = periodResultListData.getList()[2].getResult();


					String record0Id = periodResultListData.getList()[0].getPeriod();
					String record1Id = periodResultListData.getList()[1].getPeriod();
					String record2Id = periodResultListData.getList()[2].getPeriod();

					GetNewPeriodThread.loggedFid.add(record0Id+"");

					GetNewPeriodThread.loggedFid.add(record1Id+"");

					GetNewPeriodThread.loggedFid.add(record2Id+"");



					if(LOGENABLE){
						System.out.println("\n载入初始前三局QQ分分彩结果:");
						System.out.println(record0 + " ID: " + record0Id);

						System.out.println(record1 + " ID: " + record1Id);

						System.out.println(record2 + " ID: " + record2Id);
					}
					//0是倒数第三名 1是倒数第二名 2是倒数第一名
					unrepeatedList.set(0, record2 );
					unrepeatedList.set(1, record1);
					unrepeatedList.set(2, record0);
				}

				;
			}


		} catch (JsonParseException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}



	public static void login (String username, String password , String validateCode){
		try {


			//设置请求头
			Map<String,String> header = new HashMap<String, String>();


			//设置请求实体
			Map<String,Object> body = new HashMap<String, Object>();
			body.put("username",username);
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

	public  void login (){
		try {


			Map<String,String> cookieAndValidateCode = getResponseCookieAndValidateCode(URLConfig.validateCodeURL);

			//cookie
			String setCookieStr =null;

			//验证码
			String validateCode = null;
			if(cookieAndValidateCode != null
					&& cookieAndValidateCode.size() > 0){
				for(Map.Entry<String,String> entry : cookieAndValidateCode.entrySet()){
					setCookieStr = entry.getKey();

					validateCode = entry.getValue();
				}
			}

			//设置请求头
			Map<String,String> header = new HashMap<String, String>();
			header.put("Cookie",setCookieStr);


			//设置请求实体
			Map<String,Object> body = new HashMap<String, Object>();
			body.put("username","freebuf002");
			body.put("password","2ae4c2c4f3e070a335327533e3a95600");
			body.put("validateCode",validateCode);

			String requestUrl = URLConfig.loginURL;

			String result =  RequestURLUtils.postUrl(requestUrl, header , body);

			ObjectMapper objectMapper = new ObjectMapper();

			ResponseData responseData = objectMapper.readValue(result.toString() , ResponseData.class);


			if(responseData.getStatus().equalsIgnoreCase("false")){
				throw new BadIllegalResponseException(responseData.getInfo());
			}else{
				System.out.println("登录成功");
				return ;
			}



		} catch (IOException e) {

			e.printStackTrace();

			return ;
		} catch (BadIllegalResponseException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 根据给定验证码url返回 服务器返回的cookie 和 数字验证码
	 * @param urlStr
	 * @return
	 */
	public static Map<String,String> getResponseCookieAndValidateCode(String urlStr){

			CloseableHttpResponse response = CloseableHttpClientUtils.getResponse(urlStr);

			//Cookie
			String setCookie = CloseableHttpClientUtils.getCookie(response);

			byte[] buffer = CloseableHttpClientUtils.getByteFromResponse(response);

//			//保存图片 验证图片完整性
//			FileOutputStream outputStream = new FileOutputStream(new File("1.gif"));
//			outputStream.write(buffer);
//			outputStream.flush();
			//调用验证码识别image
			String validateInfoJson = RuoKuai.createByPost(RuoKuaiConfig.USERNAME
								,RuoKuaiConfig.PASSWORD
								,RuoKuaiConfig.TYPEID
								,"90"
								,RuoKuaiConfig.SOFTID
								,RuoKuaiConfig.SOFTKEY,buffer);

			Map result = MapUtils.buildEmptyMap();

			//验证码
			String validateCode = getValidateCodeFromJson(validateInfoJson);

			//键 放入 cookie
			//值 放入 validate
			result.put(setCookie.toString(), validateCode);

			return result;

	}

	private static String getValidateCodeFromJson(String validateCode) {

		try {
			return JackSonUtils.objectMapper.readValue(validateCode, ValidateData.class).getResult().toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return validateCode;
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
		header.put("Cookie", AUTHConfig.COOKIE);


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

//		String threadName = Thread.currentThread().getName();
//
//		System.out.println("下注线程名称为 : "+threadName);
		//QQ分分彩下单第一球请求地址

		//在投注允许时间内进行投注

		if(SERVERTIME != null
				&& STARTTIME != null
				&& CLOSETIME != null){
			Date serverDate = DateUtils.format(SERVERTIME);

			Date startDate = DateUtils.format(STARTTIME);

			Date closeDate = DateUtils.format(CLOSETIME);

			//应该下注的时间
			Date shouldPayDate = (Date)serverDate.clone();
			shouldPayDate.setSeconds(shouldPayDate.getSeconds()+30);

			if(shouldPayDate.compareTo(closeDate) < 0 && shouldPayDate.compareTo(startDate) > 0  ){

				StringBuffer message = new StringBuffer();

				message.append("服务器时间:"+serverDate.toString());

				message.append("\n下注开始时间:"+serverDate.toString());

				message.append("\n下注结束时间:"+serverDate.toString());

				throw new IllegalPayTimeException("不在可下注范围内!!!"+message.toString());
			}

		}

		Map<String,String> header = new HashMap<String, String>();
		header.put("Content-Type","application/x-www-form-urlencoded");
		header.put("Cookie",AUTHConfig.COOKIE);

		Map<String,Object> body = new HashMap<String, Object>();
		body.put("force",false);
		body.put("gameId",GameIdConfig.QQFenFenCaiGameID);
		body.put("MaxBack",8);



		int id = 0;
//		if(shouldPayAmount == 0 ){
//			System.out.println("没有投注金额 不投注");
//			return;
//		}


		int goal = 0;


		currentPayType1Strategy.setData(unrepeatedList);
		nextPayType = currentPayType1Strategy.doStrategy();

		//判断下注多少
		shouldPayAmount = payAmountStrategy.doStrategy0(previousPayType,payStep , previousResult ,  currentPayAmount)  ;


		id = goal =  nextPayType == null ? 707 : Integer.valueOf(nextPayType.getCode());
		String odds = GameConfig.QQFenFenCaiDanChangeOdds;
		String orderListFormat = String.format("[{\"id\":%d,\"amount\":\"%s\",\"goal\":%d,\"odds\":\"%s\"}]",id , shouldPayAmount , goal , odds );
		body.put("orderlist",orderListFormat);
		String periodId = NEWPERIODID;

		if(periodId == null){
			throw new GetNewPeriodException();
		}
		String changeoOdds = getChangeOdds(GameIdConfig.QQFenFenCaiGameID , periodId);

		if(changeoOdds.equals("[]")){
			// 倍率不变
		}else{
			throw new PayOrderException("倍率应该改变,看日志记录的");
		}

		body.put("periodID",periodId);
		body.put("selectBack",8);


//		List<Map<String,Object>> bodySortedMap = Collections.emptyList();
//		bodySortedMap.add(body);
		String result = RequestURLUtils.postUrl(URLConfig.payURL,header, body) ;

		try {
			PayOrderData payOrderData = JackSonUtils.objectMapper.readValue(result, PayOrderData.class);
			if(payOrderData.isStatus() ){
				//下注步数自增
				payStep++;

				previousPayType = nextPayType;

				System.out.println("\n购买期数:"+NEWPERIODID);
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




	/**
	 * 获取赔率变化
	 * @return
	 */
	public String getChangeOdds(String gameId , String periodId){

		//token头

		String rquestUrl = String.format(URLConfig.getChangeOddsURL  , periodId , gameId ) ;
		String result = RequestURLUtils.getUrl(rquestUrl , AUTHUtil.buildCookieMapOnly()  );

		//{"status":true,"info":"获取赔率变化成功！","data":"[]"}

		try {
			ChangeOddsData changeOddsData = JackSonUtils.objectMapper.readValue(result, ChangeOddsData.class);

			if(changeOddsData.isStatus()){
				Log.debug(changeOddsData.getInfo()+ changeOddsData.getData()+changeOddsData.isStatus());
				return changeOddsData.getData();
			}else{
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
