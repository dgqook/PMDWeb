package pmd.web.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import pmd.common.vo.UserInfoVO;
import pmd.web.dao.MainDAO;
 
@Service("mainService")
public class MainServiceImpl implements MainService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="mainDAO")
	private MainDAO mainDAO;
	
	/**
	 * 로그인
	 */
	@Override
	public Map<String,Object> doLoginFunction(Map<String,Object> map) {
		Map<String,Object> resultMap= mainDAO.doLoginFunction(map);
		
		if(resultMap == null) {
			resultMap= new HashMap<String,Object>();
		}
		
		UserInfoVO userInfo= null;
		
		if(resultMap.containsKey("USER_PMSS")) {
			resultMap.put("loginSuccess", "true");
			
			userInfo= new UserInfoVO();
			userInfo.setUserId((String)resultMap.get("USER_ID"));
			userInfo.setUserPw((String)resultMap.get("USER_PW"));
			userInfo.setUserPmss((String)resultMap.get("USER_PMSS"));
			userInfo.setUserName((String)resultMap.get("USER_NAME"));
			userInfo.setUserEmail((String)resultMap.get("USER_EMAIL"));
			userInfo.setUserCoName((String)resultMap.get("USER_CO_NAME"));
			userInfo.setUserCoZip((String)resultMap.get("USER_CO_ZIP"));
			userInfo.setUserCoAddrSys((String)resultMap.get("USER_CO_ADDR_SYS"));
			userInfo.setUserCoAddr((String)resultMap.get("USER_CO_ADDR"));
			userInfo.setUserTel((String)resultMap.get("USER_TEL"));
			userInfo.setUserHp((String)resultMap.get("USER_HP"));
			userInfo.setUserRegDate(((java.sql.Date)resultMap.get("USER_REG_DATE")).toString());
			userInfo.setUserRecentConn(((java.sql.Date)resultMap.get("USER_RECENT_CONN")).toString());
			userInfo.setUserExpiryDate(((java.sql.Date)resultMap.get("USER_EXPIRY_DATE")).toString());
			userInfo.setUserQuestion((String)resultMap.get("USER_QUESTION"));
			userInfo.setUserAnswer((String)resultMap.get("USER_ANSWER"));
			
			resultMap.put("userInfo", userInfo);
			
			// 만료까지 남은 날짜 구하기
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
		    Date endDate= null;
			try {
				if(userInfo.getUserExpiryDate() == null || userInfo.getUserExpiryDate().equals("")) {
					endDate= new Date();
				} else {
					endDate = formatter.parse(userInfo.getUserExpiryDate());	
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Date beginDate = new Date();
		    
		    long diff = endDate.getTime() - beginDate.getTime();
		    //int diffDays = toIntExact(diff / (24 * 60 * 60 * 1000));
		    Long longDiffDays= diff / (24 * 60 * 60 * 1000);
		    int diffDays= longDiffDays.intValue();
		    
		    if(endDate.getTime() >= beginDate.getTime()){	// 사용 기간이 남은 경우
				if(diffDays<30){
					resultMap.put("restOfExpiry", "사용기간이 <span style='color:red;'>"+diffDays+"</span>일 남았습니다.");
				}else{
					resultMap.put("restOfExpiry", "사용기간이 <span style='color:white;'>"+diffDays+"</span>일 남았습니다.");
				}
			} else {			// 사용 기간이 끝난 경우
				if(userInfo.getUserPmss().equals("M")){
					resultMap.put("restOfExpiry", "true");
				}else{
					resultMap.put("restOfExpiry", "false");
				}
			}
			// -- 만료까지 남은 날짜 구하기
			
			
			
		} else {
			resultMap.put("userInfo", null);
			resultMap.put("loginSuccess", "false");
		}
		
		return resultMap;
	}

	
	/**
	 * 자동 로그인
	 */
	@Override
	public Map<String, Object> doAutoLoginFunction(Map<String, Object> map) {
		//log.debug("1");
		Map<String,Object>	resultMap= mainDAO.doAutoLoginFunction(map);
		UserInfoVO userInfo= null;
		//log.debug("2");
		if(resultMap == null) {
			resultMap= new HashMap<String,Object>();
			//log.debug("3");
		}
		
		// 파라미터 값 검사 구문
        if(resultMap.isEmpty() == false){ 
        	//log.debug("4");
            Iterator<Entry<String,Object>> iterator = resultMap.entrySet().iterator();
            Entry<String,Object> entry = null;
            while(iterator.hasNext()){
                entry = iterator.next();
                log.debug("key : "+entry.getKey()+", value : "+entry.getValue());
            }
        }
        // --파라미터 값 검사 구문
		
       // log.debug("5");
		if(resultMap.containsKey("USER_PMSS")) {
			//log.debug("6");
			resultMap.put("loginSuccess", "true");
						
			userInfo= new UserInfoVO();
			userInfo.setUserId((String)resultMap.get("USER_ID"));
			userInfo.setUserPw((String)resultMap.get("USER_PW"));
			userInfo.setUserPmss((String)resultMap.get("USER_PMSS"));
			userInfo.setUserName((String)resultMap.get("USER_NAME"));
			userInfo.setUserEmail((String)resultMap.get("USER_EMAIL"));
			userInfo.setUserCoName((String)resultMap.get("USER_CO_NAME"));
			userInfo.setUserCoZip((String)resultMap.get("USER_CO_ZIP"));
			userInfo.setUserCoAddrSys((String)resultMap.get("USER_CO_ADDR_SYS"));
			userInfo.setUserCoAddr((String)resultMap.get("USER_CO_ADDR"));
			userInfo.setUserTel((String)resultMap.get("USER_TEL"));
			userInfo.setUserHp((String)resultMap.get("USER_HP"));
			userInfo.setUserRegDate(((java.sql.Date)resultMap.get("USER_REG_DATE")).toString());
			userInfo.setUserRecentConn(((java.sql.Date)resultMap.get("USER_RECENT_CONN")).toString());
			userInfo.setUserExpiryDate(((java.sql.Date)resultMap.get("USER_EXPIRY_DATE")).toString());
			userInfo.setUserQuestion((String)resultMap.get("USER_QUESTION"));
			userInfo.setUserAnswer((String)resultMap.get("USER_ANSWER"));
			
			//log.debug("userPw: "+userInfo.getUserPw());
			
			resultMap.put("userInfo", userInfo);
			
			
			// 만료까지 남은 날짜 구하기
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			Date endDate= null;
			try {
				if(userInfo.getUserExpiryDate() == null || userInfo.getUserExpiryDate().equals("")) {
					endDate= new Date();
				} else {
					endDate = formatter.parse(userInfo.getUserExpiryDate());	
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Date beginDate = new Date();
		    
		    long diff = endDate.getTime() - beginDate.getTime();
		    //int diffDays = java.lang.Math.toIntExact(diff / (24 * 60 * 60 * 1000));
		    Long longDiffDays= diff / (24 * 60 * 60 * 1000);
		    int diffDays= longDiffDays.intValue();
		    
		    if(endDate.getTime() >= beginDate.getTime()){	// 사용 기간이 남은 경우
				if(diffDays<30){
					resultMap.put("restOfExpiry", "사용기간이 <span style='color:red;'>"+diffDays+"</span>일 남았습니다.");
				}else{
					resultMap.put("restOfExpiry", "사용기간이 <span style='color:white;'>"+diffDays+"</span>일 남았습니다.");
				}
			} else {			// 사용 기간이 끝난 경우
				if(userInfo.getUserPmss().equals("M")){
					resultMap.put("restOfExpiry", "true");
				}else{
					resultMap.put("restOfExpiry", "false");
				}
			}
			// -- 만료까지 남은 날짜 구하기
		} else {
			resultMap.put("userInfo", null);
			resultMap.put("loginSuccess", "false");
		}
		
		
		return resultMap;
	}
	
	/**
	 * 아이디 비밀번호 찾기
	 */
	@Override
	public Map<String, Object> doFindAccount(Map<String, Object> map) {
		Map<String,Object>	resultMap= mainDAO.doFindAccount(map);
		UserInfoVO userInfo= null;
		
		if(resultMap == null) {
			resultMap= new HashMap<String,Object>();
		}
		
		if(resultMap.containsKey("USER_ID")) {
			resultMap.put("findSuccess", "true");
			
			userInfo= new UserInfoVO();
			userInfo.setUserId((String)resultMap.get("USER_ID"));
			userInfo.setUserName((String)resultMap.get("USER_NAME"));
			userInfo.setUserEmail((String)resultMap.get("USER_EMAIL"));
						
			resultMap.put("userInfo", userInfo);
			
			// 현재시간 조회
			java.util.Date dt = new java.util.Date();
			java.text.SimpleDateFormat sdf = 
			new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// --현재시간 조회
						
			String nowDttm = sdf.format(dt);
			
			Map<String,Object> authMap= new HashMap<String,Object>();
			authMap.put("userId", userInfo.getUserId());
			authMap.put("userEmail", userInfo.getUserEmail());
			authMap.put("nowDttm", nowDttm);
			
			mainDAO.doIssueAuthCode(authMap);
			authMap=mainDAO.doGetAuthCode(authMap);
			
			resultMap.put("authCode", authMap.get("AUTH_CODE"));
		} else {
			resultMap.put("userInfo", null);
			resultMap.put("findSuccess", "false");
		}
		
		return resultMap;
	}
	
	/**
	 * 비밀번호 변경 : 인증코드 확인
	 */
	@Override
	public Map<String, Object> doAuthorization(Map<String, Object> map) {
		String userId= (String)map.get("userId");
		String userAuthCode= (String)map.get("authCode");
		String serverAuthCode= null;
		Date expiryDttm= null;
		Date nowDttm= null;
		
		Map<String, Object> resultMap= new HashMap<String, Object>();
		resultMap.put("userId", userId);
		
		map= mainDAO.doGetAuthCode(map);
		serverAuthCode= (String) map.get("AUTH_CODE");
		expiryDttm= new Date(((Timestamp)map.get("EXPIRY_DTTM")).getTime());
		
		// 현재시간 조회
		/*
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		nowDttm = sdf.format(dt);
		*/
		long now = System.currentTimeMillis();
		nowDttm = new Date(now);
		// --현재시간 조회
		
		// 문자열 정보를 전부 날짜 정보로 변환
		/*
		String from = null;
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now= null, exp= null;
		
		try {
			// 만료시간 변환
			from= expiryDttm;
			exp = transFormat.parse(from);
			// 현재시간 변환
			from= nowDttm;
			now = transFormat.parse(from);
		} catch (ParseException e) {
			resultMap.put("authSuccess", "false");
			e.printStackTrace();
			return resultMap;
		}
		*/
		// --문자열 정보를 전부 날짜 정보로 변환
		
		if(nowDttm.before(expiryDttm) && userAuthCode.equals(serverAuthCode)){	// 만료된 인증 코드인지 비교 & 코드 값이 서로 일치하는지 비교
			resultMap.put("authSuccess", "true");
		} else {
			resultMap.put("authSuccess", "false");
		}
		
		
		
		return resultMap;
	}

	/**
	 * 비밀번호 변경
	 */
	@Override
	public void doChangePassword(Map<String, Object> map) {
		mainDAO.doChangePassword(map);
	}

	@Override
	public void doCreateAccount(Map<String, Object> map) {
		mainDAO.doCreateAccount(map);
	}
	
	/**
	 * 회원가입 : 아이디 체크
	 */
	@Override
	public Map<String,Object> doIdCheck(Map<String,Object> map) {
		Map<String,Object> resultMap= mainDAO.doIdCheck(map);
		
		if(resultMap == null) {
			resultMap= new HashMap<String,Object>();
		}
				
		if(resultMap.containsKey("USER_ID")&&!resultMap.get("USER_ID").equals("")) {
			resultMap.put("checkSuccess", "false");
		} else {
			resultMap.put("checkSuccess", "true");
		}
		
		return resultMap;
	}
	
	/**
	 * 회원가입 : 이메일 체크
	 */
	@Override
	public Map<String,Object> doEmailCheck(Map<String,Object> map) {
		Map<String,Object> resultMap= mainDAO.doEmailCheck(map);
		
		if(resultMap == null) {
			resultMap= new HashMap<String,Object>();
		}
				
		if(resultMap.containsKey("USER_EMAIL")&&!resultMap.get("USER_EMAIL").equals("")) {
			resultMap.put("checkSuccess", "false");
		} else {
			resultMap.put("checkSuccess", "true");
		}
		
		return resultMap;
	}

	/**
	 * 정보 수정 : 비밀번호 체크
	 */
	@Override
	public Map<String, Object> doPwCheck(Map<String, Object> paramMap) {
		Map<String,Object> resultMap= mainDAO.doPwCheck(paramMap);
		
		if(resultMap == null) {
			resultMap= new HashMap<String,Object>();
		}
				
		if(resultMap.containsKey("USER_ID")&&!resultMap.get("USER_ID").equals("")) {
			resultMap.put("pwCheckSuccess", "true");
		} else {
			resultMap.put("pwCheckSuccess", "false");
		}
		
		return resultMap;
	}

	@Override
	public void doModUserInfo(Map<String, Object> joinMap) {
		mainDAO.doModUserInfo(joinMap);
	}
 
}