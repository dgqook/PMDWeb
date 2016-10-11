package pmd.app.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import pmd.app.dao.AppProtocolDAO;
import pmd.common.common.PMDUtil;
import pmd.common.vo.Chart1VO;
import pmd.common.vo.Chart2VO;
import pmd.common.vo.Chart3VO;
import pmd.common.vo.SoftwareInfoVO;
import pmd.common.vo.UserInfoVO;
import pmd.web.service.InfoService;
import pmd.web.service.MainService;

@Service("appProtocolService")
public class AppProtocolServiceImpl implements AppProtocolService {
	PMDUtil pmd= new PMDUtil();
	
	@Resource(name="mainService")
	private MainService mainService;
	
	@Resource(name="infoService")
    private InfoService infoService;
	
	@Resource(name="appProtocolDAO")
	private AppProtocolDAO dao;
	/*******************************************************************************************************
	 * 어플리케이션 - 회원가입																							*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@Override
	public String appJoin(HashMap<String, Object> paramMap) {
		pmd.logging("[APP] /app/join.do - service");
		
		// 반환할 JSONObject 객체 생성
		JSONObject result= new JSONObject();
		
		// 파라미터가 없는 지 체크
		if(paramMap.isEmpty()){
			result.put("success", false);
		}else{
			// 파라미터 값 가져오기
			String userId= (String) paramMap.get("userId");
			String userPw= (String) paramMap.get("userPw");
			String userPmss= (String) paramMap.get("userPmss");
			String userName= (String) paramMap.get("userName");
			String userEmail= (String) paramMap.get("userEmail");
			String userTel= (String) paramMap.get("userTel");
			String userHp= (String) paramMap.get("userHp");
			String userCoName= (String) paramMap.get("userCoName");
			String userCoZip= (String) paramMap.get("userCoZip");
			String userCoAddr= (String) paramMap.get("userCoAddr");
			String userCoAddrSys= (String) paramMap.get("userCoAddrSys");
			
			// 파라미터 유효성 체크
			if(pmd.checkNullBlank(userId, userPw, userPmss, userName, userEmail, userTel, userHp, userCoName, userCoZip, userCoAddr, userCoAddrSys)) result.put("success", false);
			else{
				try{ // 기능 구현
					
					boolean validate= true;
			        String message= null;
			        
			        //필터링부분, 하나라도 걸리면 validate변수가 false가 된다
			                
			        //비밀번호: 숫자/문자/일부특수문자(_@./#&+-)만 사용할 수 있으며 6-20자리를 만족해야 한다.
			        if(validate) {
			        	validate = (Pattern.matches("^[A-Za-z0-9_@./#&+-]*.{6,20}$", userPw))? (true) : (false) ;  
				    	if(!validate) {
				        	message= "비밀번호 입력에 문제가 있습니다.\n계속해서 문제가 발생하는 경우 hk@m-soft.co.kr로 문의해주세요.";
				    	}
			        }
			        
			        //이름: 알파벳 대/소문자 및 한글 입력만 가능하다.
			        if(validate) {
			        	validate = (Pattern.matches("^[a-zA-Z가-힣0-9]*$", userName))? (true) : (false) ;  
				        if(!validate) {
				        	message= "이름 입력에 문제가 있습니다.\n계속해서 문제가 발생하는 경우 hk@m-soft.co.kr로 문의해주세요.";
				        }
			        }
			        
			        
			        //회사명: 영문 한글 숫자 ()_ 만 사용 가능
			        if(validate) {
			        	validate = (Pattern.matches("^[a-zA-Z가-힣0-9()_]*$", userCoName))? (true) : (false) ; 
				        if(!validate) {
				        	message= "회사명 입력에 문제가 있습니다.\n계속해서 문제가 발생하는 경우 hk@m-soft.co.kr로 문의해주세요.";
				        }
			        }
			        
			        //유선전화: ['2,3자리'-'3,4자리'-'4자리'] 조건을 만족시켜야 하고 '-'을 제외한 모든 문자는 숫자로 이루어져야 한다.
			        if(validate) {
			        	validate = (Pattern.matches("(0(2|3(1|2|3)|4(1|2|3)|5(1|2|3|4|5)|6(1|2|3|4)))-(\\d{3,4})-(\\d{4})", userTel))? (true) : (false) ;  
				        if(!validate) {
				        	message= "유선 전화번호 입력에 문제가 있습니다.\n계속해서 문제가 발생하는 경우 hk@m-soft.co.kr로 문의해주세요.";
				        }
			        }
			        
			        //휴대전화: [0,1]로 시작하며 010,011,016-9만 앞에 올 수 있음.
			        //		 ['3자리'-'3,4자리'-'4자리'] 조건을 만족시켜야 하고 '-'을 제외한 모든 문자는 숫자로 이루어져야 한다.
			        if(validate) {
			        	validate = (Pattern.matches("(01[016789])-(\\d{3,4})-(\\d{4})", userHp))? (true) : (false) ;  
				        if(!validate) {
				        	message= "휴대폰 번호 입력에 문제가 있습니다.\n계속해서 문제가 발생하는 경우 hk@m-soft.co.kr로 문의해주세요.";
				        }
			        }
			        
			        if(validate){
			        	
			        	Map<String,Object> idCheck= mainService.doIdCheck(paramMap);
			        	String checkSuccess= (String)idCheck.get("checkSuccess");
			        	if(checkSuccess.equals("true")){
			        		mainService.doCreateAccount(paramMap);
			                result.put("success", true);
			                
			        	}else{
			        		message= "이미 가입된 계정입니다. \n비밀번호를 잊으셨다면 비밀번호 찾기 기능을 이용해주세요.";
			        		result.put("message", message);
				        	result.put("success", false);
			        	}
			        }else{
			        	result.put("message", message);
			        	result.put("success", false);
			        }
					
					
				}catch(Exception e){
					e.printStackTrace();
					result.put("success", false);
				}
			}
		}
		return result.toJSONString();
	}

	/*******************************************************************************************************
	 * 어플리케이션 - 로그인																								*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@Override
	public String appLogin(HashMap<String, Object> paramMap) {
		pmd.logging("[APP] /app/login.do - service");
		
		// 반환할 JSONObject 객체 생성
		JSONObject result= new JSONObject();
		
		// 파라미터가 없는 지 체크
		if(paramMap.isEmpty()){
			result.put("success", false);
		}else{
			// 파라미터 값 가져오기
			String userId= (String) paramMap.get("userId");
			String userPw= (String) paramMap.get("userPw");
			
			// 파라미터 유효성 체크
			if(pmd.checkNullBlank(userId, userPw)) result.put("success", false);
			else{
				try{ // 기능 구현
					
					Map<String,Object> map= mainService.doLoginFunction(paramMap);
					UserInfoVO user= (UserInfoVO) map.get("userInfo");
					String diffDays= (String) map.get("diffDays");
					if(user == null || user.getUserId().equals("")) result.put("success", false);
					else {
						result.put("success", true);
						result.put("expiryDay", diffDays);
						result.put("userId", user.getUserId());
						result.put("userName", user.getUserName());
					}
					
				}catch(Exception e){
					e.printStackTrace();
					result.put("success", false);
				}
			}
		}
		return result.toJSONString();
	}

	/*******************************************************************************************************
	 * 어플리케이션 - 아이디/비밀번호 찾기																				*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@Override
	public String appFindAccount(HashMap<String, Object> paramMap) {
		pmd.logging("[APP] /app/findAccount.do - service");
		
		// 반환할 JSONObject 객체 생성
		JSONObject result= new JSONObject();
		
		// 파라미터가 없는 지 체크
		if(paramMap.isEmpty()){
			result.put("success", false);
		}else{
			// 파라미터 값 가져오기
			String userEmail= (String) paramMap.get("userEmail");
			
			// 파라미터 유효성 체크
			if(pmd.checkNullBlank(userEmail)) result.put("success", false);
			else{
				try{ // 기능 구현
					
					Map<String, Object> map = mainService.doFindAccount(paramMap);
		        	
		        	if(map.containsKey("findSuccess") && map.get("findSuccess").equals("true")) {
			        	UserInfoVO userInfo= (UserInfoVO) map.get("userInfo");        	
			        	
			        	// 이메일 관련
			        	
			        	 // 메일 관련 정보
			            String host = "smtp.worksmobile.com";
			            final String username = PMDUtil.REQUEST_MAIL_ID;
			            final String password = PMDUtil.REQUEST_MAIL_PW;		
			            int port=465;
			             
			            // 메일 내용
			            String recipient = (String) userInfo.getUserEmail();
			            String subject = "PMD 계정 분실 관련 내용입니다.";
			            String body = 	"안녕하세요. "+userInfo.getUserName()+"님의 아이디는 "+
			            				userInfo.getUserId()+" 입니다.\n비밀번호 변경을 원하시면 아래 링크로 이동해주세요.\n "+
			            				"http://pmd.kr/web/main/changePasswordPage.do?userId="+userInfo.getUserId()+"&authCode="+map.get("authCode")+
			            				"\n이 링크는 30분 뒤에 만료됩니다.";
			             
			            Properties props = System.getProperties();
			             
			             
			            props.put("mail.smtp.host", host);
			            props.put("mail.smtp.port", port);
			            props.put("mail.smtp.auth", "true");
			            props.put("mail.smtp.ssl.enable", "true");
			            props.put("mail.smtp.ssl.trust", host);
			              
			            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			                String un=username;
			                String pw=password;
			                protected PasswordAuthentication getPasswordAuthentication() {
			                    return new PasswordAuthentication(un, pw);
			                }
			            });
			            session.setDebug(true); //for debug
			              
			            Message mimeMessage = new MimeMessage(session);
			            mimeMessage.setFrom(new InternetAddress(PMDUtil.REQUEST_MAIL_ID));
			            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			            mimeMessage.setSubject(subject);
			            mimeMessage.setText(body);
			            Transport.send(mimeMessage);
			        	
			            result.put("success", true);
			        	// --이메일 관련
		        	}					
					
				}catch(Exception e){
					e.printStackTrace();
					result.put("success", false);
				}
			}
		}
		return result.toJSONString();
	}

	/*******************************************************************************************************
	 * 어플리케이션 - 요약정보 (usingInfo, usedSoftware, updateHist)																							*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@Override
	public String appGraph(HashMap<String, Object> paramMap) {
		pmd.logging("[APP] /app/graph.do - service");
		
		// 반환할 JSONObject 객체 생성
		JSONObject result= new JSONObject();
		
		// 파라미터가 없는 지 체크
		if(paramMap.isEmpty()){
			//pmd.logging("[APP] /app/graph.do - service1");
			result.put("success", false);
		}else{
			// 파라미터 값 가져오기
			String userId= (String) paramMap.get("userId");
			pmd.logging("[APP] /app/graph.do - service - userId: "+userId);
			
			// 파라미터 유효성 체크
			if(pmd.checkNullBlank(userId)) result.put("success", false);
			else{
				try{ // 기능 구현
					//pmd.logging("[APP] /app/graph.do - service2");
					
					// 목록 가져오기
					ArrayList<SoftwareInfoVO> rawInstalledList= infoService.getInstalledSoftware(paramMap);
					ArrayList<SoftwareInfoVO> ownedList= null;
					ArrayList<SoftwareInfoVO> installedList= null;
					// --목록 가져오기
					
					///// 유료 소프트웨어만 가지고 연산하도록 필터링 /////
					ArrayList<SoftwareInfoVO> chargedList= infoService.getChargedSoftware(paramMap);
					
					///// 보유 소프트웨어 리스트 및 설치 소프트웨어 리스트 필터링 ///
					ownedList= infoService.getOwnedSoftware(paramMap);
					installedList= pmd.includeSoftwareBySwName(rawInstalledList, chargedList);
		    	
					// 차트 데이터 만들기(소프트웨어 사용 정보) -> 소프트웨어명, 보유정품수량, 복제수량, 재고
					Chart1VO addToChart1= null;
					ArrayList<Chart1VO> chart1= new ArrayList<Chart1VO>();
					
					// 차트 데이터 만들기(사용 중인 소프트웨어) -> 소프트웨어명, 사용 수량
					Chart2VO addToChart2= null;
					ArrayList<Chart2VO> chart2= new ArrayList<Chart2VO>();
					
					// 차트 데이터 만들기(도표) -> 컴퓨터명, IP주소, 운영체제, 소프트웨어명, 업데이트
					Chart3VO addToChart3= null;
					ArrayList<Chart3VO> chart3= new ArrayList<Chart3VO>();
					
					///// 보유 정품 소프트웨어 중복 제거 (다른 만료일자인 경우) /////
					ownedList= pmd.removeDuplicateBySwName(ownedList);
					
					///// 만료 제품 제거 /////
					ownedList= pmd.removeExpiredSoftware(ownedList);
					
					
					// 보유 정품 소프트웨어 이름 및 개수 파악
					for(SoftwareInfoVO o:ownedList){
						//pmd.logging("[가공전]이름: "+o.getSwName()+", 보유개수: "+o.getOwnQuantity());
						addToChart1= new Chart1VO();
						addToChart1.setSwName(o.getSwName());
						addToChart1.setOwnQuantity(Integer.parseInt(o.getOwnQuantity().replace(" ", "")));
						chart1.add(addToChart1);
					}
					
					// 사용중인 프로그램 이름도 차트1에 추가, , 차트3 데이터 수집
					for(SoftwareInfoVO i:installedList){
						boolean needToAdd= true;
						for(Chart1VO c1:chart1){
							if(c1.getSwName().replaceAll(" ", "").equals(i.getSwName().replaceAll(" ", ""))){
								needToAdd=false;
							}
						}
						if(needToAdd){
							addToChart1= new Chart1VO();
							addToChart1.setSwName(i.getSwName());
							chart1.add(addToChart1);
						}
						needToAdd=false;
						
						addToChart3= new Chart3VO();
						addToChart3.setPcIp(i.getPcIp());
						addToChart3.setPcName(i.getPcName());
						addToChart3.setPcOs(i.getPcOs());
						addToChart3.setSwName(i.getSwName());
						addToChart3.setUpdateDate(i.getUpdateDate().substring(0, 19));
						chart3.add(addToChart3);
					}
					
					// 보유 정품 소프트웨어 중 사용중인 개수 파악 (정품, 복제)
					for(int n=0; n<chart1.size(); n++){
						for(SoftwareInfoVO i:installedList){
							if(chart1.get(n).getSwName().replaceAll(" ","").equals(i.getSwName().replaceAll(" ",""))){
								if(chart1.get(n).getCopyQuantity()==0){
									chart1.get(n).setCopyQuantity(1);
								}else{
									chart1.get(n).setCopyQuantity(chart1.get(n).getCopyQuantity()+1);
								}
							}
						}	
					}
					
					// 정품, 복제, 재고 소프트웨어 갯수 파악 및 등록
					for(int n=0; n<chart1.size(); n++){
						int temp= chart1.get(n).getOwnQuantity()-chart1.get(n).getCopyQuantity();
						//pmd.logging("[처리전]파일: "+chart1.get(n).getSwName()+", 정품: "+chart1.get(n).getOwnQuantity());
						
						if(temp>0){ // 보유 개수가 사용 개수보다 많은 경우
							chart1.get(n).setOwnQuantity(chart1.get(n).getCopyQuantity());
							chart1.get(n).setCopyQuantity(0);
							chart1.get(n).setStockQuantity(temp);
						}else if(temp<0){//보유 개수가 사용 개수보다 적은 경우
							// 보유 소프트웨어 개수 그대로 진행
							chart1.get(n).setCopyQuantity(temp*(-1));
							chart1.get(n).setStockQuantity(0);
						}else{
							// 보유 소프트웨어 개수 그대로 진행
							chart1.get(n).setCopyQuantity(0);
							chart1.get(n).setStockQuantity(0);
						}
						
						// 사용 프로그램(정품/복제)가 1개 이상일 경우 -> 차트2에 활용
						//pmd.logging("  [처리후]파일: "+chart1.get(n).getSwName()+", 정품: "+chart1.get(n).getOwnQuantity()+", 복제: "+chart1.get(n).getCopyQuantity()+", 재고: "+chart1.get(n).getStockQuantity());
						if(chart1.get(n).getCopyQuantity()>0 || chart1.get(n).getOwnQuantity()>0) {
							addToChart2= new Chart2VO();
							addToChart2.setSwName(chart1.get(n).getSwName());
							addToChart2.setUsingQuantity(chart1.get(n).getCopyQuantity()+chart1.get(n).getOwnQuantity());
							chart2.add(addToChart2);
							//log.debug("추가!");
						}
					}
					
			        // jsp를 사용해 출력하므로 모델뷰가 아닌 세션에 데이터를 저장한다.
					
					chart1.sort(new Comparator<Chart1VO>(){
						@Override
						public int compare(Chart1VO o1, Chart1VO o2) {
							int o1sum= o1.getOwnQuantity() + o1.getCopyQuantity() + o1.getStockQuantity();
							int o2sum= o2.getOwnQuantity() + o2.getCopyQuantity() + o2.getStockQuantity();
							if(o1sum > o2sum) return -1;
							else if(o1sum < o2sum) return 1;
							else return 0;
						}
					});
			        
			        chart2.sort(new Comparator<Chart2VO>() {
			        	@Override
			        	public int compare(Chart2VO o1, Chart2VO o2) {
			        		if(o1.getUsingQuantity() > o2.getUsingQuantity()) return -1;
			        		else if(o1.getUsingQuantity() < o2.getUsingQuantity()) return 1;
			        		else return 0;
			        	}
					});
			        
			        // 정렬하기
			        chart3.sort(new Comparator<Chart3VO>(){
			        	@Override
			        	public int compare(Chart3VO o1, Chart3VO o2) {
			        		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        		Date o1date = null;
			        		Date o2date = null;
			        		try {
			        			o1date = transFormat.parse(o1.getUpdateDate());
			        			o2date = transFormat.parse(o2.getUpdateDate());
							} catch (ParseException e) {
								o1date = new Date();
								o2date = new Date();
							}
			        		if(o1date.before(o2date)) return 1;
			        		else return -1;
			        	}
			        });
			        
			        // JSON 형식으로 변환
			        
			        JSONArray usingInfo= new JSONArray();
			        JSONArray usedSoftware= new JSONArray();
			        JSONArray updateHist= new JSONArray();
			        JSONObject tmp= null;
			        
			        for(Chart1VO c1:chart1){
			        	tmp= new JSONObject();
			        	tmp.put("swName", c1.getSwName());
			        	tmp.put("owned", c1.getOwnQuantity());
			        	tmp.put("copy", c1.getCopyQuantity());
			        	tmp.put("stock", c1.getStockQuantity());
			        	usingInfo.add(tmp);
			        }
			        for(Chart2VO c2:chart2){
			        	tmp= new JSONObject();
			        	tmp.put("swName", c2.getSwName());
			        	tmp.put("count", c2.getUsingQuantity());
			        	usedSoftware.add(tmp);
			        }
			        for(Chart3VO c3:chart3){
			        	tmp= new JSONObject();
			        	tmp.put("pcName", c3.getPcName());
			        	tmp.put("pcOs", c3.getPcOs());
			        	tmp.put("pcIp", c3.getPcIp());
			        	tmp.put("swName", c3.getSwName());
			        	tmp.put("updateDate", c3.getUpdateDate());
			        	updateHist.add(tmp);
			        }
			        
			       result.put("usingInfo", usingInfo);
			       result.put("usedSoftware", usedSoftware);
			       result.put("updateHist", updateHist);
					
					result.put("success", true);
				}catch(Exception e){
					e.printStackTrace();
					result.put("success", false);
				}
			}
		}
		return result.toJSONString();
	}

	/*******************************************************************************************************
	 * 어플리케이션 - 사용자목록(pcList), 사용현황(installList)																				*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@Override
	public String appStatus(HashMap<String, Object> paramMap) {
		pmd.logging("[APP] /app/status.do - service");
		
		// 반환할 JSONObject 객체 생성
		JSONObject result= new JSONObject();
		
		// 파라미터가 없는 지 체크
		if(paramMap.isEmpty()){
			result.put("success", false);
		}else{
			// 파라미터 값 가져오기
			String userId= (String) paramMap.get("userId");
			String pcName= (String) paramMap.get("pcName");
			
			// 파라미터 유효성 체크
			if(pmd.checkNullBlank(userId)) result.put("success", false);
			else{
				try{ // 기능 구현
					
					ArrayList<SoftwareInfoVO> table1= null;
		    		ArrayList<SoftwareInfoVO> rawTable1= infoService.getUserPcList(paramMap); // 계정에 소속된 모든 소프트웨어/PC 정보 가져오기
		    		ArrayList<SoftwareInfoVO> table2= null;
		    		ArrayList<SoftwareInfoVO> rawTable2= null;
		    		
		    		// JSONArray에 객체들을 담기 위한 그릇
		    		JSONObject tmp= null;
		    		
		    		ArrayList<String> nameList= new ArrayList<String>();
		    		boolean exist= false;
		    		
		    		for(SoftwareInfoVO p:rawTable1){
		    			for(String name:nameList){
		    				if(p.getPcName().equals(name)) exist= true;
		    			}
		    			if(!exist) nameList.add(p.getPcName());
		    			exist=false;
		    		}		    		
		    		
		    		// 내용이 없거나 ALL이면 전체 PC 출력
		    		if(pcName == null) pcName= "";
		    		else{
		    			if(pcName.equals("ALL")) {
		    				pcName="";
		    			}
		    		}
		    		
		    		// 전체 PC 소프트웨어를 조회하는 경우
		    		if(pcName.equals("")){
		    			table1= new ArrayList<SoftwareInfoVO>(); ////////////////////////////////////////TODO WorkController 내용과 비교해서 다른부분 보정할 것. 
		    			
		    			// 유료 소프트웨어만 가지고 연산하도록 필터링
		    			ArrayList<SoftwareInfoVO> chargedList= infoService.getChargedSoftware(paramMap);
		    			
		    			table1= pmd.includeSoftwareBySwName(rawTable1,chargedList);
		    			
		    			// JSONArray 변환
		    			JSONArray jTable1= new JSONArray();
			    		for(SoftwareInfoVO s:table1){
			    			tmp= new JSONObject();
			    			if(s.getParam1() == null || s.getParam1().equals("")){
			    				tmp.put("code", "F");
			    			}else{
			    				tmp.put("code", s.getParam1());
			    			}
			    			tmp.put("pcName", s.getPcName());
			    			tmp.put("pcOs", s.getPcOs());
			    			tmp.put("swName", s.getSwName());
			    			tmp.put("swFile", s.getSwFile());
			    			tmp.put("updateDate", s.getUpdateDate());
			    			jTable1.add(tmp);
			    		}
		    			result.put("installList", jTable1);
		    		}else{
		    			
		    			paramMap.put("pcName", pcName);
		    			rawTable2= infoService.getUserPcListByPk(paramMap);
		    			table2= new ArrayList<SoftwareInfoVO>();
		    			
		    			// 유료 소프트웨어만 가지고 연산하도록 필터링
		    			ArrayList<SoftwareInfoVO> chargedList= infoService.getChargedSoftware(paramMap);
		    			
		    			table2= pmd.includeSoftwareBySwName(rawTable2,chargedList);
		    			
		    			
		    			
		    			// JSONArray 변환
		    			JSONArray jTable2= new JSONArray();
			    		for(SoftwareInfoVO s:table2){
			    			tmp= new JSONObject();
			    			if(s.getParam1() == null || s.getParam1().equals("")){
			    				tmp.put("code", "F");
			    			}else{
			    				tmp.put("code", s.getParam1());
			    			}
			    			tmp.put("pcName", s.getPcName());
			    			tmp.put("pcOs", s.getPcOs());
			    			tmp.put("swName", s.getSwName());
			    			tmp.put("swFile", s.getSwFile());
			    			tmp.put("updateDate", s.getUpdateDate());
			    			jTable2.add(tmp);
			    		}
		    			result.put("installList", jTable2);
		    		}
		    		
		    		// JSONArray 변환
		    		JSONArray jNameList= new JSONArray();
		    		for(String s:nameList){
		    			tmp= new JSONObject();
		    			tmp.put("pcName", s);
		    			jNameList.add(tmp);
		    		}
		    		result.put("pcList", jNameList);
					
					result.put("success", true);
				}catch(Exception e){
					e.printStackTrace();
					result.put("success", false);
				}
			}
		}
		return result.toJSONString();
	}

	/*******************************************************************************************************
	 * 어플리케이션 - 정품/복제 소프트웨어 토글 버튼																	*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@Override
	public String appToggleSoftware(HashMap<String, Object> paramMap) {
		pmd.logging("[APP] /app/toggleSoftware.do - service");
		
		// 반환할 JSONObject 객체 생성
		JSONObject result= new JSONObject();
		
		// 파라미터가 없는 지 체크
		if(paramMap.isEmpty()){
			result.put("success", false);
		}else{
			// 파라미터 값 가져오기
			String userId= (String) paramMap.get("userId");
			String pcName= (String) paramMap.get("pcName");
			String swName= (String) paramMap.get("swName");
			String swFile= (String) paramMap.get("swFile");
			String code= (String) paramMap.get("code");
			
			// 파라미터 유효성 체크
			if(pmd.checkNullBlank(userId, pcName, swName, code)) result.put("success", false);
			else{
				try{ // 기능 구현
					
					int stock= infoService.getNumberOfLegal(userId, swName);
		    		int checked= infoService.getNumberOfChecked(userId, swName, swFile); 
		    		
		    		pmd.logging("stock: "+stock+", checked: "+checked);
		    		 // 해당 상품의 재고가 체크한 정품 수량보다 많은 경우만 상태를 바꿀 수 있음 
			    		if(code != null && !code.equals("")){
			    			if(code.equals("T")) {
			    				infoService.setLegalSoftware(userId, "F", pcName, swName, swFile);
			    				pmd.logging(pcName+" > "+swName+" -> T to F");
			    				result.put("message", "정품 등록이 해제되었습니다.\n남은 개수 : "+(stock-(checked-1)));	    				
			    			}
			    			else {
			    				if(stock >checked) {
				    				infoService.setLegalSoftware(userId, "T", pcName, swName, swFile);
				    				pmd.logging(pcName+" > "+swName+" -> F to T");
				    				result.put("message", "정품 소프트웨어로 지정되었습니다.\n남은 개수 : "+(stock-(checked+1)));
			    				}
			    			}
			    		
		    		}else{
		    			if(code != null && !code.equals("")) result.put("message", "재고가 부족합니다.");
		    		}
					
					result.put("success", true);
				}catch(Exception e){
					e.printStackTrace();
					result.put("success", false);
				}
			}
		}
		return result.toJSONString();
	}

	/*******************************************************************************************************
	 * 어플리케이션 - 보유 소프트웨어 목록 (미구매 제품 포함)														*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@Override
	public String appProductList(HashMap<String, Object> paramMap) {
		pmd.logging("[APP] /app/productList.do - service");
		
		// 반환할 JSONObject 객체 생성
		JSONObject result= new JSONObject();
		
		// 파라미터가 없는 지 체크
		if(paramMap.isEmpty()){
			result.put("success", false);
		}else{
			// 파라미터 값 가져오기
			String userId= (String) paramMap.get("userId");
			
			// 파라미터 유효성 체크
			if(pmd.checkNullBlank(userId)) result.put("success", false);
			else{
				try{ // 기능 구현
					
					ArrayList<SoftwareInfoVO> ownList= infoService.getOwnedSoftware(paramMap);
		    		for(int i=0; i<ownList.size(); i++){
		    			if(ownList.get(i).getOwnExpDate().equals("")) {
		    				ownList.get(i).setOwnExpDate("");
		    			} else {
		    				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		    				Date expDate = transFormat.parse(ownList.get(i).getOwnExpDate());
		    				Date nowDate = new Date();
		    				
		    				long diff = expDate.getTime() - nowDate.getTime();
		    				Long longDiffDays= diff / (24 * 60 * 60 * 1000);
		    			    int diffDays = longDiffDays.intValue();
		    			    
		    			    ownList.get(i).setDiffDays(diffDays);
		    			    ownList.get(i).setOwnExpDate(ownList.get(i).getOwnExpDate());
		    				
		    			}
		    		}
		    		
		    		ArrayList<SoftwareInfoVO> manageList= infoService.getManageList(paramMap);
		    		ArrayList<SoftwareInfoVO> installList= infoService.getInstalledSoftware(paramMap); 	// DB에 입력된 설치 프로그램 목록 가져오기
		    		ArrayList<SoftwareInfoVO> chargeList= infoService.getChargedSoftware(paramMap);
		    		boolean isExist= false;
		    		String ins_swName;
		    		String ins_swFile;
		    		String chr_swName;
		    		String chr_swFile;
		    		String man_swName;
		    		//String man_swFile;
		    		
		    		pmd.logging("start filtering / manageSize: "+manageList.size()+", installSize: "+installList.size()+", chargeSize: "+chargeList.size() );
			        for(SoftwareInfoVO i:installList){
			        	// 유료소프트웨어 검증 후 추가
		    			for(SoftwareInfoVO c:chargeList){
		    				//pmd.logging("chargeName: "+c.getSwName()+", installName: "+i.getSwName());
		    				ins_swName= i.getSwName();
		    				ins_swFile= i.getSwFile();
		    				chr_swName= c.getSwName();
		    				chr_swFile= c.getSwFile();
		    				if(ins_swName.replaceAll(" ", "").equals(chr_swName.replaceAll(" ", "")) && ins_swFile.replaceAll(" ", "").equals(chr_swFile.replaceAll(" ", ""))){
		    					pmd.logging("match! - swName: "+c.getSwName());
		    					isExist= false;
		    					for(int m=0; m<manageList.size(); m++){
		    						man_swName= manageList.get(m).getSwName();
		    						//man_swFile= manageList.get(m).getSwFile();
		        	        		// 설치내역과 관리목록의 소프트웨어 명이 같은 경우 개수만 추가
		        	        		if(ins_swName.replaceAll(" ","").equals(man_swName.replaceAll(" ", ""))){
		        	        			//pmd.logging("already exist! - swName: "+i.getSwName());
		        	        			manageList.get(m).setOwnQuantity(  String.valueOf(  Integer.parseInt( manageList.get(m).getOwnQuantity() ) + 1  )  );
		        	        			isExist= true;
		        	        		}
		                		}
		    					
		    					if(!isExist){
		    						//pmd.logging("new Software! - swName: "+c.getSwName());
		    						SoftwareInfoVO newSw= new SoftwareInfoVO();
		        					newSw.setSwName(c.getSwName());
		        					newSw.setSwVendor(c.getSwVendor());
		        					newSw.setOwnQuantity(String.valueOf(1));
		    						manageList.add(newSw);
		    					}
		    					break;
		    					
		    				}
		    				
		    			}
		        	}
			        
			        
			        for(int i=manageList.size()-1; i>=0; i--){
			        	//pmd.logging("manageList - swName: "+manageList.get(i).getSwName()+", quantity: "+manageList.get(i).getOwnQuantity());
			        	for(SoftwareInfoVO o:ownList){
			        		if(manageList.get(i).getSwName().equals(o.getSwName())){
			        			manageList.remove(i);
			        		}
			        	}
			        }
			        	
			        	
			        if(manageList.size()>0){
				        for(SoftwareInfoVO s:manageList){
				        	s.setOwnSer("미구매");
				        	s.setOwnExpDate("미구매");
				        	ownList.add(s);
				        }
			        }
			        
			        // JSONArray 변환
			        JSONArray jOwnList= new JSONArray();
			        JSONObject tmp= null;
			        for(SoftwareInfoVO s:ownList){
			        	tmp= new JSONObject();
			        	tmp.put("ownSer", s.getOwnSer());
			        	tmp.put("swName", s.getSwName());
			        	tmp.put("swVendor", s.getSwVendor());
			        	tmp.put("ownQuantity", s.getOwnQuantity());
			        	tmp.put("ownExpDate", s.getOwnExpDate());
			        	tmp.put("diffDays", s.getDiffDays());
			        	jOwnList.add(tmp);
			        }
			        result.put("ownList", jOwnList);
					
					result.put("success", true);
				}catch(Exception e){
					e.printStackTrace();
					result.put("success", false);
				}
			}
		}
		return result.toJSONString();
	}

	/*******************************************************************************************************
	 * 어플리케이션 - 보유 소프트웨어 등록 (주어진 목록에서)														*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@Override
	public String appAddProduct(HashMap<String, Object> paramMap) { //TODO 여기부터 다시 구현
		pmd.logging("[APP] /app/addProduct.do - service");
		
		// 반환할 JSONObject 객체 생성
		JSONObject result= new JSONObject();
		
		// 파라미터가 없는 지 체크
		if(paramMap.isEmpty()){
			result.put("success", false);
		}else{
			// 파라미터 값 가져오기
			String userId= (String) paramMap.get("userId");
			String swName= (String) paramMap.get("swName");
			String swVendor= (String) paramMap.get("swVendor");
			String ownQuantity= (String) paramMap.get("ownQuantity");
			String ownExpDate= (String) paramMap.get("ownExpDate");
			
			// 파라미터 유효성 체크
			if(pmd.checkNullBlank(userId, swName, swVendor, ownQuantity, ownExpDate)) result.put("success", false);
			else{
				try{ // 기능 구현
					
	        		paramMap.put("swName", swName);
		    		paramMap.put("userId", userId);
		    		paramMap.put("swVendor", swVendor);
		    		paramMap.put("ownQuantity", ownQuantity);
		    		paramMap.put("ownExpDate", ownExpDate);
		    		
		    		infoService.doRegisterASoftware(paramMap);
					
					result.put("success", true);
				}catch(Exception e){
					e.printStackTrace();
					result.put("success", false);
				}
			}
		}
		return result.toJSONString();
	}

	/*******************************************************************************************************
	 * 어플리케이션 - 보유 소프트웨어 정보 수정																		*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@Override
	public String appModProduct(HashMap<String, Object> paramMap) {
		pmd.logging("[APP] /app/modProduct.do - service");
		
		// 반환할 JSONObject 객체 생성
		JSONObject result= new JSONObject();
		
		// 파라미터가 없는 지 체크
		if(paramMap.isEmpty()){
			result.put("success", false);
		}else{
			// 파라미터 값 가져오기
			String ownSer= (String) paramMap.get("ownSer");
			String ownQuantity= (String) paramMap.get("ownQuantity");
			String ownExpDate= (String) paramMap.get("ownExpDate");
			
			// 파라미터 유효성 체크
			if(pmd.checkNullBlank(ownSer, ownQuantity, ownExpDate)) result.put("success", false);
			else{
				try{ // 기능 구현
					
		    		paramMap.put("ownSer", ownSer);
		    		paramMap.put("ownExpDate", ownExpDate);
		    		paramMap.put("ownQuantity", ownQuantity);
		    		infoService.doModifyQuantity(paramMap);
					
					result.put("success", true);
				}catch(Exception e){
					e.printStackTrace();
					result.put("success", false);
				}
			}
		}
		return result.toJSONString();
	}

	/*******************************************************************************************************
	 * 어플리케이션 - 보유 소프트웨어 정보 삭제																		*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@Override
	public String appDelProduct(HashMap<String, Object> paramMap) {
		pmd.logging("[APP] /app/addProduct.do - service");
		
		// 반환할 JSONObject 객체 생성
		JSONObject result= new JSONObject();
		
		// 파라미터가 없는 지 체크
		if(paramMap.isEmpty()){
			result.put("success", false);
		}else{
			// 파라미터 값 가져오기
			String ownSer= (String) paramMap.get("ownSer");
			
			// 파라미터 유효성 체크
			if(pmd.checkNullBlank(ownSer)) result.put("success", false);
			else{
				try{ // 기능 구현
					
					paramMap.put("ownSer", ownSer);
		    		infoService.doDeleteASoftware(paramMap);
					
					result.put("success", true);
				}catch(Exception e){
					e.printStackTrace();
					result.put("success", false);
				}
			}
		}
		return result.toJSONString();
	}

	/*******************************************************************************************************
	 * 어플리케이션 - 여러 소프트웨어 견적 요청 (key: estimate)														*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@Override
	public String appEstimate(HashMap<String, Object> paramMap) {
		pmd.logging("[APP] /app/estimate.do - service");
		
		// 반환할 JSONObject 객체 생성
		JSONObject result= new JSONObject();
		
		// 파라미터가 없는 지 체크
		if(paramMap.isEmpty()){
			result.put("success", false);
		}else{
			// 파라미터 값 가져오기
			String estimate= (String) paramMap.get("estimate");
			
			// 파라미터 유효성 체크
			if(pmd.checkNullBlank(estimate)) result.put("success", false);
			else{
				try{ // 기능 구현
					JSONParser parser= new JSONParser();
					JSONArray estimateJArray= (JSONArray) parser.parse(estimate);
					if(estimateJArray.isEmpty()){
						result.put("success", false);
					}else{
						ArrayList<SoftwareInfoVO> estimateList= new ArrayList<SoftwareInfoVO>();
						
						SoftwareInfoVO tmp1= null;
						JSONObject tmp2= null;
						for(int i=0; i<estimateJArray.size(); i++){
							tmp2= (JSONObject) estimateJArray.get(i);
							tmp1= new SoftwareInfoVO();
							
							tmp1.setUserId((String)tmp2.get("userId"));
							tmp1.setSwName((String)tmp2.get("swName"));
							tmp1.setSwVendor((String)tmp2.get("swVendor"));
							tmp1.setOwnQuantity((String)tmp2.get("ownQuantity"));
							tmp1.setOwnExpDate((String)tmp2.get("ownExpDate"));
							estimateList.add(tmp1);
						}
						
						paramMap.put("estimateList", estimateList);
						paramMap.put("userId", tmp1.getUserId());
						String userEmail= infoService.getUserEmailAddress(paramMap);
						
						 // 이메일 관련
			    		Date nowDate = new Date();
			    		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    		String nowDateString = transFormat.format(nowDate);
			    		
			    		
				       	 // 메일 관련 정보
				           String host = "smtp.worksmobile.com";
				           final String username = PMDUtil.REQUEST_MAIL_ID;
				           final String password = PMDUtil.REQUEST_MAIL_PW;		//TODO 웍스모바일 비밀번호
				           
				           
				           // 메일 내용
				           String subject = "[연장] PMD 견적 요청 - "+tmp1.getUserId()+" | "+nowDateString;
				           String body = 	"일시: "+nowDateString+"\n"+
						        		    "아이디: "+tmp1.getUserId()+"\n"+
						        		    "이메일: "+userEmail+"\n"+
				        		   			"< 견적 내용 > ( [회사] 제품, 버전, 만료일, 수량 )\n\n";
				           
				           for(SoftwareInfoVO s:estimateList){
				        	   body+="[ "+s.getSwVendor()+" ] ";
				        	   body+=s.getSwName()+",   ";
				        	   body+=s.getSwVersion()+",   ";
				        	   body+=s.getOwnExpDate()+",   ";
				        	   body+=s.getOwnQuantity()+"\n";
				           }
				           body+="\n\n본문에 있는 메일 주소로 견적 부탁합니다.";
				            
				           Properties props = System.getProperties();
				            
				           props.put("mail.smtp.user" , username);
				           props.put("mail.smtp.host", host);
				           props.put("mail.smtp.port", "465");
				           props.put("mail.smtp.starttls.enable", "true");
				           props.put("mail.smtp.auth", "true");
				           props.put("mail.smtp.debug", "true");
				           props.put("mail.smtp.socketFactory.port", "465");
				           props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				           props.put("mail.smtp.socketFactory.fallback", "false");
				             
				           pmd.logging("-1");
				           Session session2 = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				               String un=username;
				               String pw=password;
				               protected PasswordAuthentication getPasswordAuthentication() {
				                   return new PasswordAuthentication(un, pw);
				               }
				           });
				           session2.setDebug(PMDUtil.LOG_ENABLE); //for debug
				           Message mimeMessage = new MimeMessage(session2);
				           mimeMessage.setFrom(new InternetAddress(PMDUtil.REQUEST_MAIL_ID));
				           //mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
				           
				           InternetAddress[] toAddr= new InternetAddress[3];
				           toAddr[0]= new InternetAddress(PMDUtil.REQUEST_MAIL_TO1);
				           toAddr[1]= new InternetAddress(PMDUtil.REQUEST_MAIL_TO2);
				           toAddr[2]= new InternetAddress(PMDUtil.REQUEST_MAIL_ID);
				           mimeMessage.setRecipients(Message.RecipientType.TO, toAddr);
				           
				           mimeMessage.setSubject(subject);
				           mimeMessage.setText(body);
				           Transport.send(mimeMessage);
				           
				       	// --이메일 관련
						
						result.put("success", true);
					}
				}catch(Exception e){
					e.printStackTrace();
					result.put("success", false);
				}
			}
		}
		return result.toJSONString();
	}

	/*******************************************************************************************************
	 * 어플리케이션 - 소프트웨어 검색																					*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@Override
	public String appSearch(HashMap<String, Object> paramMap) {
		pmd.logging("[APP] /app/search.do - service");
		
		// 반환할 JSONObject 객체 생성
		JSONObject result= new JSONObject();
		
		// 파라미터가 없는 지 체크
		if(paramMap.isEmpty()){
			result.put("success", false);
		}else{
			// 파라미터 값 가져오기
			String keyword= (String) paramMap.get("keyword");
			
			// 파라미터 유효성 체크
			if(pmd.checkNullBlank(keyword)) result.put("success", false);
			else{
				try{ // 기능 구현
					
					paramMap.put("searchKeyword", keyword);
	    			ArrayList<SoftwareInfoVO> chargedList= infoService.getChargedSoftwareByPk(paramMap);
	    			
	    			// JSONArray 변환
			        JSONArray jSearchList= new JSONArray();
			        JSONObject tmp= null;
			        for(SoftwareInfoVO s:chargedList){
			        	tmp= new JSONObject();
			        	tmp.put("swName", s.getSwName());
			        	tmp.put("swVendor", s.getSwVendor());
			        	tmp.put("swVersion", s.getSwVersion());
			        	jSearchList.add(tmp);
			        }
			        result.put("searchList", jSearchList);
					
					result.put("success", true);
				}catch(Exception e){
					e.printStackTrace();
					result.put("success", false);
				}
			}
		}
		return result.toJSONString();
	}

}
