package pmd.common.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pmd.common.vo.SoftwareInfoVO;
import pmd.common.vo.UserInfoVO;

/************************************************************************************************
 * 코디 개발 유틸, 자주 반복되는 작업 등을 함수형태로 제공												*
 * @author HK																									*
 ************************************************************************************************/
public class PMDUtil {
	Logger log = Logger.getLogger(this.getClass());
	
	//-------------------------------------------------------------------------------------
	
	public static final String PMD_URL= "";//"http://localhost:8080/pmd";
	//public static final String REQUEST_MAIL_TO1= "dgqook@gmail.com";
	//public static final String REQUEST_MAIL_TO2= "dfgr234@naver.com";
	public static final String REQUEST_MAIL_TO1= "kjs@m-soft.co.kr";
	public static final String REQUEST_MAIL_TO2= "ar@m-soft.co.kr";
	public static final String REQUEST_MAIL_ID= "hk@m-soft.co.kr";
	public static final String REQUEST_MAIL_PW= "a0597063@";
	public static final boolean LOG_ENABLE= true;

	
	//-------------------------------------------------------------------------------------
	/*********************************************************************************************
	 * 문자열을 MD-5 방식으로 암호화																		*
	 * @param txt 암호화 하려하는 문자열																	*
	 * @return String																							*
	 * @throws Exception																						*
	 *********************************************************************************************/
	public static String encMD5(String str){
		String MD5 = ""; 
		try{
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(str.getBytes()); 
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			MD5 = sb.toString();
		}catch( NoSuchAlgorithmException e){
			e.printStackTrace(); 
			MD5 = null; 
		}
		return MD5;
	}
	//-------------------------------------------------------------------------------------
	/*********************************************************************************************
	 * ajax할때 쓰는거																							*
	 * @param response																						*
	 * @param message																						*
	 *********************************************************************************************/
	public static void sendResponse(HttpServletResponse response, String message){
		PrintWriter writer = null;
		try{
			writer= response.getWriter();
			writer.write(message);
			writer.flush();
		} catch (IOException e){
			throw new RuntimeException(e.getMessage(),e);
		}
		finally{
			if(writer != null){
				writer.close();
			}
		}
	}
	//-------------------------------------------------------------------------------------
	/*********************************************************************************************
	 * 파라미터로 어느 값이 들어왔는지 확인해주는 함수													*
	 * @param commandMap																					*
	 *********************************************************************************************/
	public void getParameterLog(CommandMap commandMap){
		if(commandMap.isEmpty() == false){
			Iterator<Entry<String,Object>> iterator= commandMap.getMap().entrySet().iterator();
			Entry<String,Object> entry= null;
			while(iterator.hasNext()){
				entry= iterator.next();
				log.debug("key : "+entry.getKey()+", value : "+entry.getValue());
			}
		}
	}
	
	/*********************************************************************************************
	 * 임의의 로그 기록 함수																					*
	 * @param commandMap																					*
	 *********************************************************************************************/
	public void logging(String string){
		if(string != null && LOG_ENABLE){
				log.debug("[CUSTOM LOG] "+string);
		}
	}
	//-------------------------------------------------------------------------------------
	/*********************************************************************************************
	 * 세션에 유저 정보가 있는지 확인하고, 있다면 유저 정보를 객체로 반환, 없다면 null값 반환	*
	 * @param session																							*
	 * @return																									*
	 *********************************************************************************************/
	public UserInfoVO loginCheck(HttpSession session){
		UserInfoVO userInfo= (UserInfoVO) session.getAttribute("userInfo");
		if(userInfo != null && !userInfo.getUserId().equals("")){
			return userInfo;
		}else{
			return null;
		}
	}
	//-------------------------------------------------------------------------------------
	/**********************************************************************************************************
	 * Date 객체를 'yyyy-MM-dd' 형식의 String으로 바꿔준다.															*
	 * 매개변수가 없는 경우 현재 날짜를 반환한다.																			*
	 * @return																													*
	 **********************************************************************************************************/
	public String dateToString(Date date){
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		if(date == null) date= new Date();
		return sdf.format(date);
	}
	/**********************************************************************************************************
	 * Date 객체를 'yyyy-MM-dd' 형식의 String으로 바꿔준다.															*
	 * 매개변수가 없는 경우 현재 날짜를 반환한다.																			*
	 * @return																													*
	 **********************************************************************************************************/
	public String dateToString(){
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	/**********************************************************************************************************
	 * 'yyyy-MM-dd 형식의 String 객체를 Date 객체로 바꿔 반환한다												.	*
	 * 예외가 발생하면 null값을 반환한다.																					*
	 * @param str																												*
	 * @return																													*
	 **********************************************************************************************************/
	public Date stringToDate(String str){
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	//-------------------------------------------------------------------------------------
	/*****************************************************************************************************************
	 * 필터 리스트와 타겟 리스트 소프트웨어 이름이 매칭되지 않는 경우, 반환 리스트에 항목을 추가하는 함수			*
	 * @param targetList																												*
	 * @param filterList																													*
	 * @return																															*
	 *****************************************************************************************************************/
	public ArrayList<SoftwareInfoVO> excludeSoftwareBySwName(ArrayList<SoftwareInfoVO> targetList, ArrayList<SoftwareInfoVO> filterList){
		ArrayList<SoftwareInfoVO> result= new ArrayList<SoftwareInfoVO>();
		boolean isExist= false;
		String temp1= "";
		String temp2= "";
		
		for(int i=0; i<targetList.size(); i++){ 
			for(SoftwareInfoVO f: filterList){ 
				temp1= targetList.get(i).getSwName();
				temp2= f.getSwName();
				if(temp1.replaceAll(" ", "").equals(temp2.replaceAll(" ", ""))){
					temp1= targetList.get(i).getSwFile();
					temp2= f.getSwFile();
					if(temp1.replaceAll(" ", "").equals(temp2.replaceAll(" ", ""))){
						isExist= true;
					}
				}
			}
			if(!isExist){
				result.add(targetList.get(i));
			}
			isExist=false;
		}
		return result;
	}
	//-------------------------------------------------------------------------------------
	/*****************************************************************************************************************
	 * 필터 리스트와 타겟 리스트 소프트웨어 이름이 매칭되는 경우, 반환 리스트에 그 항목만 추가해주는 함수			*
	 * @param targetList																												*
	 * @param filterList																													*
	 * @return																															*
	 *****************************************************************************************************************/
	public ArrayList<SoftwareInfoVO> includeSoftwareBySwName(ArrayList<SoftwareInfoVO> targetList, ArrayList<SoftwareInfoVO> filterList){
		ArrayList<SoftwareInfoVO> result= new ArrayList<SoftwareInfoVO>();
		String temp1= "";
		String temp2= "";
		for(SoftwareInfoVO c: filterList){
			for(int i= 0; i<targetList.size(); i++){
				temp1= c.getSwName();
				temp2= targetList.get(i).getSwName();
				if(temp1.replaceAll(" ", "").equals(temp2.replaceAll(" ", ""))){
					temp1= c.getSwFile();
					temp2= targetList.get(i).getSwFile();
					if(temp1.replaceAll(" ", "").equals(temp2.replaceAll(" ", ""))){
						result.add(targetList.get(i));
					}
				}
			}
		}
		return result;
	}
	//-------------------------------------------------------------------------------------
	/*****************************************************************************************************************
	 * 소프트웨어 이름을 비교해서 중복이 있는 경우 수량을 합쳐준다. 또한 수량 값이 없는 경우 0으로 처리한다.		*
	 * @param targetList																												*
	 * @return																															*
	 *****************************************************************************************************************/
	public ArrayList<SoftwareInfoVO> removeDuplicateBySwName(ArrayList<SoftwareInfoVO> targetList){
		
		
		for(int i=0; i<targetList.size(); i++){
			for(int j=0; j<targetList.size(); j++){
				if(targetList.size()<=1) break; // 수량이 한개 이하이면 중복이 나올 수 없으므로 중단
				
				// 널값 예외처리
				if(targetList.get(i).getOwnQuantity().equals("")) targetList.get(i).setOwnQuantity("0"); 
				// 서로 다른 인덱스에서 같은 이름이 있으면 수량을 합친다.
				if(i!=j && targetList.get(i).getSwName().replaceAll(" ", "").equals(targetList.get(j).getSwName())){
					targetList.get(i).setOwnQuantity(
							Integer.parseInt(targetList.get(i).getOwnQuantity())+
							Integer.parseInt(targetList.get(j).getOwnQuantity())+"");
					targetList.remove(j);
				}
			}
		}
		
		
		
		return targetList;
	}
	//-------------------------------------------------------------------------------------
	/*****************************************************************************************************************
	 * 만료된 소프트웨어를 삭제한다.																									*
	 * @param targetList																												*
	 * @return																															*
	 * @throws ParseException																										*
	 *****************************************************************************************************************/
	public ArrayList<SoftwareInfoVO> removeExpiredSoftware(ArrayList<SoftwareInfoVO> targetList) throws ParseException{
		Date nowDate = new Date();
		Date expDate= null;
		
		long diff= 0;
		Long longDiffDays;
		int diffDays= 0;
		
		for(SoftwareInfoVO s:targetList){
			logging("[만료제거전] 이름: "+s.getSwName()+", 보유개수: "+s.getOwnQuantity());
		}
		
		// 만료 제품 제거
		for(int i=0; i<targetList.size(); i++){
			if(!targetList.get(i).getOwnExpDate().equals("")){ // 영구 라이센스 제품은 필터링 제외
				expDate = stringToDate(targetList.get(i).getOwnExpDate());
				if(expDate == null) expDate= new Date();
				diff = expDate.getTime() - nowDate.getTime();
			    longDiffDays = diff / (24 * 60 * 60 * 1000);
			    diffDays= longDiffDays.intValue();
			    
				if(diffDays<0){
					if(targetList.size()<=1) {
						targetList.remove(i);
						break;
					}else{
						targetList.remove(i);
						i--;
					}
				}
			}
		}
		
		for(SoftwareInfoVO s:targetList){
			logging("  [만료제거후] 이름: "+s.getSwName()+", 보유개수: "+s.getOwnQuantity());
		}
		return targetList;
	}
	//-------------------------------------------------------------------------------------
	
}
