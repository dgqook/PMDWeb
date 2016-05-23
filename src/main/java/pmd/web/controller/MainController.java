package pmd.web.controller;

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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pmd.common.common.CommandMap;
import pmd.common.common.PMDUtil;
import pmd.common.common.VerifyRecaptcha;
import pmd.common.vo.UserInfoVO;
import pmd.web.service.MainService;

@Controller
public class MainController {
	PMDUtil pmd= new PMDUtil();
     
    @Resource(name="mainService")
    private MainService mainService;
    
    @RequestMapping(value="/web/main/test.do")
    public ModelAndView pageUiTest(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	ModelAndView mv= new ModelAndView("/info/_summary");
    	//mv.addObject("servletMessage","안녕");
    	return mv;
    }
    
    
     
    /*****************************************************************************************************************
     * 코디 웹사이트 접속 페이지																										*
     * @param commandMap																											*
     * @return																															*
     * @throws Exception																												*
     *****************************************************************************************************************/
    @RequestMapping(value="/web/main/index.do")
    public ModelAndView openLoginPage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/main/login");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/    
    	
    	/*--------------------------------------------------------------------------*/
		/*						 	기능 구현 부분 --							*/
		/*--------------------------------------------------------------------------*/
    	
    	String userId= null;
        String userPw= null;
        String autoLogin= null;
        String saveId= null;
        
        // 쿠키가 있는지 탐색, 있다면 코디 자동로그인 관련 내용이 있는지 탐색
        // 자동 로그인 관련 내용이 있다면 정보를 가져오고 해당 쿠키 내용을 다시 갱신 -> 쿠키 갱신은 로그인 완료 후 처리하도록 조정
        // request에 자동 로그인 정보를 담아 로그인 화면으로 보내줌 (자동 로그인은 로그인 화면에서 처리)
        // 로그아웃 시 쿠키를 삭제하도록 조치를 취해야 함
        // 아이디: CoordyAutoLoginId
        // 비밀번호: CoordyAutoLoginPw
        Cookie cookie[] = request.getCookies();
        if(cookie != null) {
        	for(int i=0; i<cookie.length; i++){
    			if(cookie[i].getName().equals("PMDAutoLoginId")){
    				userId= cookie[i].getValue();
    				//log.debug("FIND COOKIE: "+cookie[i].getName()+", "+ cookie[i].getValue());
        		}
    			if(cookie[i].getName().equals("PMDAutoLoginPw")){
					userPw= cookie[i].getValue();
					//log.debug("FIND COOKIE: "+cookie[i].getName()+", "+ cookie[i].getValue());
				}
    			if(cookie[i].getName().equals("PMDAutoLogin")){
    				autoLogin= cookie[i].getValue();
					//log.debug("FIND COOKIE: "+cookie[i].getName()+", "+ cookie[i].getValue());
				}
    			if(cookie[i].getName().equals("PMDSaveId")){
    				saveId= cookie[i].getValue();
					//log.debug("FIND COOKIE: "+cookie[i].getName()+", "+ cookie[i].getValue());
				}
        	}
        }
        
        pmd.logging("\nPMDAutoLoginId : "+userId+"\nPMDAutoLoginPw : "+userPw+"\nPMDAutoLogin : "+autoLogin +"\nPMDSaveId : "+saveId);
        
        // 자동로그인 설정이 되어 있는 경우 아이디와 비밀번호를 모델뷰에 넣어줌
        
        if(userId!=null ){
        	if(saveId.equals("on")) {
        		mv.addObject("userId", userId);
        		mv.addObject("saveIdChecked","checked");
        		mv.addObject("autoLogin", "man");
        	}
        	if(userPw!=null && autoLogin != null && autoLogin.equals("auto") ) {
	        	mv.addObject("userId", userId);
	        	mv.addObject("userPw", userPw);
	        	mv.addObject("autoLogin", autoLogin);
        	} 
        } else {
        	mv.addObject("userId", "");
        	mv.addObject("userPw", "");
        	mv.addObject("autoLogin", "man");
        	mv.addObject("saveIdChecked","");
        }
        
        /*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
    	return mv;
    }
    
    /**************************************************************************************************************
     * 비밀번호 찾기 페이지																										*
     * @param commandMap																										*
     * @return																														*
     * @throws Exception																											*
     **************************************************************************************************************/
    @RequestMapping(value="/web/main/findPassword.do")
    public ModelAndView findPasswordPage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv= new ModelAndView("/main/findPassword");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/ 
    	
    	 /*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/    
    	
    	/*--------------------------------------------------------------------------*/
		/*						 	기능 구현 부분 --							*/
		/*--------------------------------------------------------------------------*/
        /*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
    	
    	return mv;
    }
    
    /**************************************************************************************************************
     * 코디 클라이언트 다운로드 링크																							*
     * @param commandMap																										*
     * @return																														*
     * @throws Exception																											*
     **************************************************************************************************************/
    @RequestMapping(value="/web/main/clientDownload.do")
    public ModelAndView clientDownload(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/main/clientDownload");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/ 
        
        /*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/    
    	
    	/*--------------------------------------------------------------------------*/
		/*						 	기능 구현 부분 --							*/
		/*--------------------------------------------------------------------------*/
        mv.addObject("map", null);
        /*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
        return mv;
    }
    
    /*****************************************************************************************************************
     * 로그인 기능																														*
     * @param commandMap																											*
     * @return																															*
     * @throws Exception																												*
     *****************************************************************************************************************/
    @RequestMapping(value="web/main/login.do")
    public ModelAndView doLoginFunction(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = null;
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/ 
    	
    	/*----------------------------------------------*/
    	/*				세션 가져오기 --				*/
    	/*----------------------------------------------*/
    	HttpSession session= request.getSession();
    	/*----------------------------------------------*/
    	/*				-- 세션 가져오기				*/
    	/*----------------------------------------------*/
    	
    	/*--------------------------------------------------------------------------*/
		/*						 	기능 구현 부분 --							*/
		/*--------------------------------------------------------------------------*/
        
    	
        
     // 사용자 정보를 가져와서 어느 사용자인지에 따라 뷰를 나눈다.
        String autoLogin= request.getParameter("autoLogin");
        String autoLoginChk= request.getParameter("autoLoginChk");
        String saveId= request.getParameter("saveId");
        
        Map<String,Object> map= null;
        
        
        //log.debug("autoLogin: "+autoLogin);
        if(autoLogin == null) autoLogin= "";
        if(autoLoginChk == null) autoLoginChk= "";
        
        
        if(autoLoginChk.equals("on")) autoLogin= "man";
        
        // 로그인/자동로그인 쿼리 나누어 실행
        if(autoLogin.equals("auto")) {	// 자동로그인 상태인 경우
	        if(autoLoginChk.equals("on")){ // 첫 자동 로그인
	        	map= mainService.doLoginFunction(commandMap.getMap());
	        	//log.debug("autoLogin on, userPw: "+((UserInfoVO)map.get("userInfo")).getUserPw());
	        	
	        } else {
	        	map= mainService.doAutoLoginFunction(commandMap.getMap());
	        }
        } else {
        	map= mainService.doLoginFunction(commandMap.getMap());
        }
         
        UserInfoVO userInfo = null;
        String restOfExpiry= (String)map.get("restOfExpiry");
        
        //pmd.logging( ""+map.containsKey("loginSuccess") );
       //pmd.logging( ""+map.get("loginSuccess").equals("true") );
       // pmd.logging( ""+(restOfExpiry != null) );
        pmd.logging("saveId : "+saveId);
        
        
        if(map.containsKey("loginSuccess") && map.get("loginSuccess").equals("true")
        		&& restOfExpiry != null && !restOfExpiry.equals("false")){
        	
        	session.setMaxInactiveInterval(180*60);
        	
        	// 자동 로그인 체크 작업을 거쳐 로그인 기능으로 접근한 경우에만 아래 조건문 실행 -> 처음으로 자동로그인 체크
        	userInfo= (UserInfoVO) map.get("userInfo");
        	
        	if(autoLogin.equals("auto")||autoLoginChk.equals("on")){ // 자동로그인인 경우 쿠키 데이터 갱신
        		Cookie newCookie0= new Cookie("PMDAutoLogin", "auto");
        		newCookie0.setMaxAge(60*60*24*30);            // 쿠키 유지 기간 갱신 - 1달
        		newCookie0.setPath("/");                      // 모든 경로에서 접근 가능하도록 
        		response.addCookie(newCookie0);
        		
        		Cookie newCookie1= new Cookie("PMDAutoLoginId", userInfo.getUserId());
        		newCookie1.setMaxAge(60*60*24*30);            // 쿠키 유지 기간 갱신 - 1달
        		newCookie1.setPath("/");                      // 모든 경로에서 접근 가능하도록 
        		response.addCookie(newCookie1);
        		
        		Cookie newCookie2= new Cookie("PMDAutoLoginPw", userInfo.getUserPw());	//
        		newCookie2.setMaxAge(60*60*24*30);            // 쿠키 유지 기간 갱신 - 1달
        		newCookie2.setPath("/");                      // 모든 경로에서 접근 가능하도록
        		response.addCookie(newCookie2);
        		
        		Cookie newCookie3= new Cookie("PMDSaveId", saveId);	//
        		newCookie3.setMaxAge(60*60*24*30);            // 쿠키 유지 기간 갱신 - 1달
        		newCookie3.setPath("/");                      // 모든 경로에서 접근 가능하도록
        		response.addCookie(newCookie3);
        	}
        	if(saveId != null && saveId.equals("on")){
        		Cookie newCookie1= new Cookie("PMDAutoLoginId", userInfo.getUserId());
        		newCookie1.setMaxAge(60*60*24*30);            // 쿠키 유지 기간 갱신 - 1달
        		newCookie1.setPath("/");                      // 모든 경로에서 접근 가능하도록 
        		response.addCookie(newCookie1);
        		
        		Cookie newCookie3= new Cookie("PMDSaveId", saveId);	//
        		newCookie3.setMaxAge(60*60*24*30);            // 쿠키 유지 기간 갱신 - 1달
        		newCookie3.setPath("/");                      // 모든 경로에서 접근 가능하도록
        		response.addCookie(newCookie3);
        	}
        	
        	if(userInfo.getUserPmss().equals("M")){
        		mv = new ModelAndView("/work/general");
        		response.sendRedirect(PMDUtil.PMD_URL+"/web/work/generalFilter.do");
        	} else {
        		mv = new ModelAndView("/info/summary");
        		response.sendRedirect(PMDUtil.PMD_URL+"/web/info/summary.do");
        	}

        	// 모델뷰에 사용자 정보 추가 (UserInfoVO 형식)
        	// 로그인여부는 loginSuccess속성이 true인지를 항상 비교하여 알아볼 수 있도록 한다. -> 로그인은 세션으로 한다...
        	session.setAttribute("userInfo", userInfo);
        	session.setAttribute("restOfExpiry", restOfExpiry);
        	
        	if(!userInfo.getUserPmss().equals("M")&&restOfExpiry.equals("false")){
        		mv.setViewName("main/login");
        		mv.addObject("servletMessage", "사용 기간이 만료되었습니다.");
        	}
        	
        	mv.addObject("userInfo", map.get("userInfo"));
        	mv.addObject("restOfExpiry", map.get("restOfExpiry"));
        } else {
        	// 로그인 실패
        	
        	
        	Cookie newCookie0= new Cookie("PMDAutoLogin", "auto");
    		newCookie0.setMaxAge(0);            // 쿠키 유지 기간 갱신 - 1달
    		newCookie0.setPath("/");                      // 모든 경로에서 접근 가능하도록 
    		response.addCookie(newCookie0);
        	// 쿠키를 강제로 만료시킨다.
            Cookie newCookie1= new Cookie("PMDAutoLoginId", "");
    		newCookie1.setMaxAge(0);            // 쿠키 유지 기간 갱신 - 1달
    		newCookie1.setPath("/");                      // 모든 경로에서 접근 가능하도록 
    		response.addCookie(newCookie1);
    		
    		Cookie newCookie2= new Cookie("PMDAutoLoginPw", "");
    		newCookie2.setMaxAge(0);            // 쿠키 유지 기간 갱신 - 1달
    		newCookie2.setPath("/");                      // 모든 경로에서 접근 가능하도록
    		response.addCookie(newCookie2);
    		
    		Cookie newCookie3= new Cookie("PMDSaveId", "");
    		newCookie2.setMaxAge(0);            // 쿠키 유지 기간 갱신 - 1달
    		newCookie2.setPath("/");                      // 모든 경로에서 접근 가능하도록
    		response.addCookie(newCookie3);
        	
    		// 기본 데이터 초기화
    		
    		commandMap.clear();
    		commandMap.put("userId", "");
        	commandMap.put("userPw", "");
        	commandMap.put("autoLogin", "man"); 
        	commandMap.put("saveId", "");
        	
    		mv = new ModelAndView("/main/login");
    		mv.addObject("servletMessage", "로그인에 실패했습니다. 아이디와 비밀번호를 다시 확인해 주세요.");
        }
        /*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
        return mv;
    }
    
    /**************************************************************************************************************
     * 로그아웃																														*
     * @param commandMap																										*
     * @return																														*
     * @throws Exception																											*
     **************************************************************************************************************/
    @RequestMapping(value="/web/main/logout.do")
    public ModelAndView doLogoutFunction(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/main/login");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/    
        
    	/*--------------------------------------------------------------------------*/
		/*						 	기능 구현 부분 --							*/
		/*--------------------------------------------------------------------------*/
        // 쿠키를 강제로 만료시킨다.
        Cookie newCookie1= new Cookie("PMDAutoLoginId", "");
		newCookie1.setMaxAge(0);            // 쿠키 유지 기간 갱신 - 1달
		newCookie1.setPath("/");                      // 모든 경로에서 접근 가능하도록 
		response.addCookie(newCookie1);
		
		Cookie newCookie2= new Cookie("PMDAutoLoginPw", "");
		newCookie2.setMaxAge(0);            // 쿠키 유지 기간 갱신 - 1달
		newCookie2.setPath("/");                      // 모든 경로에서 접근 가능하도록
		response.addCookie(newCookie2);
		Cookie newCookie3= new Cookie("PMDAutoLogin", "");
		newCookie3.setMaxAge(0);            // 쿠키 유지 기간 갱신 - 1달
		newCookie3.setPath("/");                      // 모든 경로에서 접근 가능하도록 
		response.addCookie(newCookie3);
		
		Cookie newCookie4= new Cookie("PMDSaveId", "");
		newCookie4.setMaxAge(0);            // 쿠키 유지 기간 갱신 - 1달
		newCookie4.setPath("/");                      // 모든 경로에서 접근 가능하도록 
		response.addCookie(newCookie4);
        
		// 기본 데이터 초기화
		
		commandMap.clear();
		commandMap.put("userId", "");
    	commandMap.put("userPw", "");
    	commandMap.put("autoLogin", "man");
    	commandMap.put("saveId", "");
		
    	/*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
        return mv;
    }
    
    
    
    /**************************************************************************************************************
     * 아이디 비밀번호 찾기																										*
     * @param commandMap																										*
     * @return																														*
     * @throws Exception																											*
     **************************************************************************************************************/
    @RequestMapping(value="/web/main/findAccount.do")
    public ModelAndView doFindAccountFunction(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv= null;
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/
    	
    	/*--------------------------------------------------------------------------*/
		/*						 	기능 구현 부분 --							*/
		/*--------------------------------------------------------------------------*/
        
        // get reCAPTCHA request param
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
        UserInfoVO userInfo= null;
        
        
        Map<String,Object> map= null;
        
        if(verify){
        	map = mainService.doFindAccount(commandMap.getMap());
        	
        	if(map.containsKey("findSuccess") && map.get("findSuccess").equals("true")) {
	        	userInfo= (UserInfoVO) map.get("userInfo");        	
	        	
	        	// 이메일 관련
	        	
	        	 // 메일 관련 정보
	            String host = "smtp.worksmobile.com";
	            final String username = "hk@m-soft.co.kr";
	            final String password = "a0597063!";		
	            int port=465;
	             
	            // 메일 내용
	            String recipient = (String) userInfo.getUserEmail();
	            String subject = "Coordy 계정 분실 관련 내용입니다.";
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
	            mimeMessage.setFrom(new InternetAddress("hk@m-soft.co.kr"));
	            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
	            mimeMessage.setSubject(subject);
	            mimeMessage.setText(body);
	            Transport.send(mimeMessage);
	        	
	        	// --이메일 관련
	        	
	        	mv = new ModelAndView("/main/login");
	        	mv.addObject("servletMessage","이메일을 발송했습니다.");
        	} else {
        		mv = new ModelAndView("/main/findPassword");
            	mv.addObject("servletMessage","인증에 실패했거나 존재하지 않는 계정 입니다..");
        	}
        } else {
        	mv = new ModelAndView("/main/findPassword");
        	mv.addObject("servletMessage","인증에 실패했거나 존재하지 않는 계정 입니다..");
        }
        /*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
        return mv;
    }
 
     
    /*******************************************************************************************************
     * 패스워드 변경 화면																									*
     * @param commandMap																								*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @RequestMapping(value="/web/main/changePasswordPage.do")
    public ModelAndView openChangePasswordPage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = null;
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/    	
    	
    	/*--------------------------------------------------------------------------*/
		/*						 	기능 구현 부분 --							*/
		/*--------------------------------------------------------------------------*/
        
        // 파라이터로 온 인증코드 비교
        Map<String,Object> authMap= mainService.doAuthorization(commandMap.getMap());
        
        if(((String)authMap.get("authSuccess")).equals("true")){ // 인증 성공여부
        	mv = new ModelAndView("/main/changePassword");
        	mv.addObject("userId", authMap.get("userId"));
        } else {
        	mv = new ModelAndView("/main/login");
        	mv.addObject("servletMessage","인증코드가 만료되었거나 비정상적인 접근입니다.");
        }
		
        // 기본 데이터 초기화
		commandMap.clear();
		
		/*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
        return mv;
    }
    
    /****************************************************************************************************
     * 패스워드 변경 기능 수행																						*
     * @param commandMap																							*
     * @return																											*
     * @throws Exception																								*
     ****************************************************************************************************/
    @RequestMapping(value="/web/main/changePassword.do")
    public ModelAndView doChangePassword(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
        
        /*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/main/changePassword");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/  
        
    	/*--------------------------------------------------------------------------*/
		/*						 	기능 구현 부분 --							*/
		/*--------------------------------------------------------------------------*/
        // 이전 접속 경로 확인, 비밀번호 변경 화면이 아니면 접근 불가능 하도록 설정 
        if(request.getHeader("referer")!=null && 
    		request.getHeader("referer").startsWith(PMDUtil.PMD_URL+"/web/main/changePasswordPage.do")){
        	Map<String,Object> changeInfo= new HashMap<String,Object>();
        	
        	changeInfo.put("userId", commandMap.get("userId"));
        	changeInfo.put("userPw", commandMap.get("userPw"));
        	
        	mainService.doChangePassword(changeInfo);
        	
        	mv.addObject("servletMessage","정상적으로 변경되었습니다.");
        } else{
        	mv.addObject("servletMessage","비정상적인 접근입니다.");
        }
        // --이전 접속 경로 확인, 비밀번호 변경 화면이 아니면 접근 불가능 하도록 설정
        
        /*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
        return mv;
    }
    
    /****************************************************************************************************
     * 회원가입 약관																										*
     * @param commandMap																							*
     * @return																											*
     * @throws Exception																								*
     ****************************************************************************************************/
    @RequestMapping(value="/web/main/joinPage.do")
    public ModelAndView openjoinPage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv =  new ModelAndView("/main/joinPage");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/    
        
        return mv;
    }
    
    /*******************************************************************************************************
     * 회원가입 페이지																										*
     * @param commandMap																								*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @RequestMapping(value="/web/main/createAccountPage.do")
    public ModelAndView openCreateAccountPage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/main/createAccount");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/   
        return mv;
    }
    
    /*******************************************************************************************************
     * 회원가입 기능																											*
     * @param commandMap																								*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @RequestMapping(value="/web/main/createAccount.do")
    public ModelAndView doCreateAccount(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = null;
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/   
    	
    	/*--------------------------------------------------------------------------*/
		/*						 	기능 구현 부분 --							*/
		/*--------------------------------------------------------------------------*/
        
        String userId= null;
        String userPw= null;
        String userName= null;
        String userEmail= null;
        String userCoName= null;
        String userCoZip= null;
        String userCoAddr= null;
        String userCoAddrSys= null;
        String userTel= null;
        String userHp= null;
        
        userId=(String) commandMap.get("userId");
        userPw=(String) commandMap.get("userPw");
        userName=(String) commandMap.get("userName");
        userEmail=(String) commandMap.get("userEmail");
        userCoName=(String) commandMap.get("userCoName");
        userCoZip=(String) commandMap.get("userCoZip");
        userCoAddr=((String) commandMap.get("userCoAddr1"))+" "+((String)commandMap.get("userCoAddr2"));
        userCoAddrSys=(String) commandMap.get("userCoAddrSys");
        userCoAddrSys= (userCoAddrSys.equals("R"))? ("N"):("O");
        userTel=(String) commandMap.get("userTel");
        userHp=(String) commandMap.get("userHp");
        
        Map<String,Object> joinMap= new HashMap<String,Object>();
        joinMap.put("userId", userId);			// 정규식 필터링
        joinMap.put("userPw", userPw);			// 정규식 필터링
        joinMap.put("userName", userName);		// 특수문자 불가
        joinMap.put("userEmail", userEmail);	// 정규식 필터링
        joinMap.put("userCoName", userCoName);	// 특수문자 불가
        joinMap.put("userCoZip", userCoZip);	// 필요없음
        joinMap.put("userCoAddr", userCoAddr);	// 필요없음
        joinMap.put("userCoAddrSys", userCoAddrSys); //필요없음
        joinMap.put("userTel", userTel);		// 정규식 필터링
        joinMap.put("userHp", userHp);			// 정규식 필터링
        
        boolean validate= true;
        String message= null;
        
        //필터링부분, 하나라도 걸리면 validate변수가 false가 된다
                
        //비밀번호: 숫자/문자/일부특수문자(_@./#&+-)만 사용할 수 있으며 6-20자리를 만족해야 한다.
        if(validate) {
        	validate = (Pattern.matches("^[A-Za-z0-9_@./#&+-]*.{6,20}$", userPw))? (true) : (false) ;  
	    	if(!validate) {
	        	message= "비밀번호 입력에 문제가 있습니다.\n계속해서 문제가 발생하는 경우 hk@m-soft.co.kr로 문의해주세요.";
	        	mv = new ModelAndView("/main/createAccount");
	            mv.addObject("servletMessage", message);
	    	}
        }
        
        //이름: 알파벳 대/소문자 및 한글 입력만 가능하다.
        if(validate) {
        	validate = (Pattern.matches("^[a-zA-Z가-힣]*$", userName))? (true) : (false) ;  
	        if(!validate) {
	        	message= "이름 입력에 문제가 있습니다.\n계속해서 문제가 발생하는 경우 hk@m-soft.co.kr로 문의해주세요.";
	        	mv = new ModelAndView("/main/createAccount");
	            mv.addObject("servletMessage", message);
	        }
        }
        
        
        
        //회사명: 영문 한글 숫자 ()_ 만 사용 가능
        if(validate) {
        	validate = (Pattern.matches("^[a-zA-Z가-힣0-9()_]*$", userCoName))? (true) : (false) ; 
	        if(!validate) {
	        	message= "회사명 입력에 문제가 있습니다.\n계속해서 문제가 발생하는 경우 hk@m-soft.co.kr로 문의해주세요.";
	        	mv = new ModelAndView("/main/createAccount");
	            mv.addObject("servletMessage", message);
	        }
        }
        
        //유선전화: ['2,3자리'-'3,4자리'-'4자리'] 조건을 만족시켜야 하고 '-'을 제외한 모든 문자는 숫자로 이루어져야 한다.
        if(validate) {
        	validate = (Pattern.matches("(0(2|3(1|2|3)|4(1|2|3)|5(1|2|3|4|5)|6(1|2|3|4)))-(\\d{3,4})-(\\d{4})", userTel))? (true) : (false) ;  
	        if(!validate) {
	        	message= "유선 전화번호 입력에 문제가 있습니다.\n계속해서 문제가 발생하는 경우 hk@m-soft.co.kr로 문의해주세요.";
	        	mv = new ModelAndView("/main/createAccount");
	            mv.addObject("servletMessage", message);
	        }
        }
        
        //휴대전화: [0,1]로 시작하며 010,011,016-9만 앞에 올 수 있음.
        //		 ['3자리'-'3,4자리'-'4자리'] 조건을 만족시켜야 하고 '-'을 제외한 모든 문자는 숫자로 이루어져야 한다.
        if(validate) {
        	validate = (Pattern.matches("(01[016789])-(\\d{3,4})-(\\d{4})", userHp))? (true) : (false) ;  
	        if(!validate) {
	        	message= "휴대폰 번호 입력에 문제가 있습니다.\n계속해서 문제가 발생하는 경우 hk@m-soft.co.kr로 문의해주세요.";
	        	mv = new ModelAndView("/main/createAccount");
	            mv.addObject("servletMessage", message);
	        }
        }
        
        if(validate){
        	mainService.doCreateAccount(joinMap);
        	mv = new ModelAndView("/main/login");
            mv.addObject("servletMessage", "정상적으로 가입되었습니다.");
        }else{
        	mv = new ModelAndView("/main/joinPage");
            mv.addObject("servletMessage", message);
        }
        //--필터링부분
        
        /*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
        return mv;
    }
    
    /****************************************************************************************************
     * 회원가입 : 아이디 중복체크 AJAX																				*
     * @param commandMap																							*
     * @return																											*
     * @throws Exception																								*
     ****************************************************************************************************/
    @RequestMapping(value="/web/main/idCheck.do")
    public void doIdCheck(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/   
    	
    	/*--------------------------------------------------------------------------*/
		/*						 	기능 구현 부분 --							*/
		/*--------------------------------------------------------------------------*/
    	
        Map<String,Object> resultMap= mainService.doIdCheck(commandMap.getMap());
        
        boolean validate= true;
        validate = (Pattern.matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$", 
        				(String)commandMap.get("userId")))? (true) : (false) ;  
		if(!validate) resultMap.put("checkSuccess", "false");
		
        PMDUtil.sendResponse(response, (String)resultMap.get("checkSuccess"));
        /*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
    }
    
    /*******************************************************************************************************
     * 회원가입 : 이메일 중복체크 AJAX																					*
     * @param commandMap																								*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @RequestMapping(value="/web/main/emailCheck.do")
    public void doEmailCheck(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/  
        
        /*--------------------------------------------------------------------------*/
		/*						 	기능 구현 부분 --							*/
		/*--------------------------------------------------------------------------*/
        Map<String,Object> resultMap= mainService.doEmailCheck(commandMap.getMap());
        
        boolean validate= true;
        validate = (Pattern.matches("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", 
        				(String)commandMap.get("userEmail")))? (true) : (false) ;  
		if(!validate) resultMap.put("checkSuccess", "false");
		
        PMDUtil.sendResponse(response, (String)resultMap.get("checkSuccess"));
        /*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
    }
    	
	/*******************************************************************************************************
     * 정보수정 페이지 > 비밀번호 확인																					*
     * @param commandMap																								*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @RequestMapping(value="/web/main/myPage.do")
    public ModelAndView openMyPage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("main/pwCheck");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/
    	
    	/*----------------------------------------------*/
    	/*				세션 가져오기 --				*/
    	/*----------------------------------------------*/
    	HttpSession session= request.getSession();
    	/*----------------------------------------------*/
    	/*				-- 세션 가져오기				*/
    	/*----------------------------------------------*/    	
    	
    	/*------------------------------------------------------------*/
    	/*				현재 사용자 정보 가져오기 --			  */
    	/*------------------------------------------------------------*/
    	UserInfoVO userInfo= pmd.loginCheck(session);
    	/*------------------------------------------------------------*/
    	/*				-- 현재 사용자 정보 가져오기			  */
    	/*------------------------------------------------------------*/
    	
    	/*------------------------------------------*/
    	/*				로그인 체크 -- 			*/
    	/*------------------------------------------*/
    	if(userInfo == null){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    		
	        return mv;
	        /*--------------------------------------------------------------------------*/
    		/*						 	-- 기능 구현 부분							*/
    		/*--------------------------------------------------------------------------*/
    	}
    	/*----------------------------------------------*/
    	/*				-- 로그인 체크	 			*/
    	/*----------------------------------------------*/
    	return mv;
    } 
    
    /**********************************************************************************************************
     * 정보수정 페이지 > 비밀번호 확인																						*
     * @param commandMap																									*
     * @return																													*
     * @throws Exception																										*
     **********************************************************************************************************/
    @RequestMapping(value="/web/main/modUserInfoPage.do")
    public ModelAndView openModUserPage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("main/modUserInfo");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/    	
    	
    	/*----------------------------------------------*/
    	/*				세션 가져오기 --				*/
    	/*----------------------------------------------*/
    	HttpSession session= request.getSession();
    	/*----------------------------------------------*/
    	/*				-- 세션 가져오기				*/
    	/*----------------------------------------------*/    	
    	
    	/*------------------------------------------------------------*/
    	/*				현재 사용자 정보 가져오기 --			  */
    	/*------------------------------------------------------------*/
    	UserInfoVO userInfo= pmd.loginCheck(session);
    	/*------------------------------------------------------------*/
    	/*				-- 현재 사용자 정보 가져오기			  */
    	/*------------------------------------------------------------*/
    	
    	/*------------------------------------------*/
    	/*				로그인 체크 -- 			*/
    	/*------------------------------------------*/
    	if(userInfo == null){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    		
    		String userPw= request.getParameter("userPw");
    		
    		Map<String,Object> paramMap= new HashMap<String,Object>();
    		paramMap.put("userId", userInfo.getUserId());
    		paramMap.put("userPw", userPw);
    		Map<String, Object> resultMap= mainService.doPwCheck(paramMap);
    		
    		String result= (String)resultMap.get("pwCheckSuccess");
    		
    		if(result.equals("true")){
    			mv.addObject("servletMessage", "인증에 성공했습니다.");
    		} else {
    			mv.setViewName("main/pwCheck");
    			mv.addObject("servletMessage", "비밀번호가 일치하지 않습니다.");
    		}
	        return mv;
	        /*--------------------------------------------------------------------------*/
    		/*						 	-- 기능 구현 부분							*/
    		/*--------------------------------------------------------------------------*/
    	}
    	/*----------------------------------------------*/
    	/*				-- 로그인 체크	 			*/
    	/*----------------------------------------------*/
    	return mv;
    } 
    
    /**************************************************************************************************************
     * 정보수정 페이지 > 정보 수정																								*
     * @param commandMap																										*
     * @return																														*
     * @throws Exception																											*
     **************************************************************************************************************/
    @RequestMapping(value="/web/main/modUserInfo.do")
    public ModelAndView doModUserInfo(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("main/modUserInfo");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/    	
    	
    	/*----------------------------------------------*/
    	/*				세션 가져오기 --				*/
    	/*----------------------------------------------*/
    	HttpSession session= request.getSession();
    	/*----------------------------------------------*/
    	/*				-- 세션 가져오기				*/
    	/*----------------------------------------------*/    	
    	
    	/*------------------------------------------------------------*/
    	/*				현재 사용자 정보 가져오기 --			  */
    	/*------------------------------------------------------------*/
    	UserInfoVO userInfo= pmd.loginCheck(session);
    	/*------------------------------------------------------------*/
    	/*				-- 현재 사용자 정보 가져오기			  */
    	/*------------------------------------------------------------*/
    	
    	/*------------------------------------------*/
    	/*				로그인 체크 -- 			*/
    	/*------------------------------------------*/
    	if(userInfo == null){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    		
            String userPw= null;
            String userName= null;
            String userCoName= null;
            String userCoZip= null;
            String userCoAddr= null;
            String userCoAddrSys= null;
            String userTel= null;
            String userHp= null;
            
            userPw=(String) commandMap.get("userPw");
            userName=(String) commandMap.get("userName");
            userCoName=(String) commandMap.get("userCoName");
            userCoZip=(String) commandMap.get("userCoZip");
            userCoAddr=((String) commandMap.get("userCoAddr1"))+" "+((String)commandMap.get("userCoAddr2"));
            userCoAddrSys=(String) commandMap.get("userCoAddrSys");
            userCoAddrSys= (userCoAddrSys.equals("R"))? ("N"):("O");
            userTel=(String) commandMap.get("userTel");
            userHp=(String) commandMap.get("userHp");
            
            Map<String,Object> joinMap= new HashMap<String,Object>();
            joinMap.put("userId",userInfo.getUserId());
            joinMap.put("userEmail", userInfo.getUserEmail());
            joinMap.put("userPw", userPw);			// 정규식 필터링
            joinMap.put("userName", userName);		// 특수문자 불가
            joinMap.put("userCoName", userCoName);	// 특수문자 불가
            joinMap.put("userCoZip", userCoZip);	// 필요없음
            joinMap.put("userCoAddr", userCoAddr);	// 필요없음
            joinMap.put("userCoAddrSys", userCoAddrSys); //필요없음
            joinMap.put("userTel", userTel);		// 정규식 필터링
            joinMap.put("userHp", userHp);			// 정규식 필터링
            
            boolean validate= true;
            String message= null;
            
            //필터링부분, 하나라도 걸리면 validate변수가 false가 된다
                    
            //비밀번호: 숫자/문자/일부특수문자(_@./#&+-)만 사용할 수 있으며 6-20자리를 만족해야 한다.
            if(validate) validate = (Pattern.matches("^[A-Za-z0-9_@./#&+-]*.{6,20}$", userPw))? (true) : (false) ;  
            if(!validate) {
            	message= "비밀번호 입력에 문제가 있습니다.\n계속해서 문제가 발생하는 경우 hk@m-soft.co.kr로 문의해주세요.";
            	mv = new ModelAndView("/main/modUserInfo");
            	mv.addObject("servletMessage", message);
            }
            
            //이름: 알파벳 대/소문자 및 한글 입력만 가능하다.
            if(validate) validate = (Pattern.matches("^[a-zA-Z가-힣]*$", userName))? (true) : (false) ;  
            if(!validate) {
            	message= "이름 입력에 문제가 있습니다.\n계속해서 문제가 발생하는 경우 hk@m-soft.co.kr로 문의해주세요.";
            	mv = new ModelAndView("/main/modUserInfo");
            	mv.addObject("servletMessage", message);
            }
            
            //회사명: 영문 한글 숫자 ()_ 만 사용 가능
            if(validate) validate = (Pattern.matches("^[a-zA-Z가-힣0-9()_]*$", userCoName))? (true) : (false) ; 
            if(!validate) {
            	message= "회사명 입력에 문제가 있습니다.\n계속해서 문제가 발생하는 경우 hk@m-soft.co.kr로 문의해주세요.";
            	mv = new ModelAndView("/main/modUserInfo");
            	mv.addObject("servletMessage", message);
            }
            
            //유선전화: ['2,3자리'-'3,4자리'-'4자리'] 조건을 만족시켜야 하고 '-'을 제외한 모든 문자는 숫자로 이루어져야 한다.
            if(validate) validate = (Pattern.matches("(0(2|3(1|2|3)|4(1|2|3)|5(1|2|3|4|5)|6(1|2|3|4)))-(\\d{3,4})-(\\d{4})", userTel))? (true) : (false) ;  
            if(!validate) {
            	message= "유선 전화번호 입력에 문제가 있습니다.\n계속해서 문제가 발생하는 경우 hk@m-soft.co.kr로 문의해주세요.";
            	mv = new ModelAndView("/main/modUserInfo");
            	mv.addObject("servletMessage", message);
            }
            
            //휴대전화: [0,1]로 시작하며 010,011,016-9만 앞에 올 수 있음.
            //		 ['3자리'-'3,4자리'-'4자리'] 조건을 만족시켜야 하고 '-'을 제외한 모든 문자는 숫자로 이루어져야 한다.
            if(validate) validate = (Pattern.matches("(01[016789])-(\\d{3,4})-(\\d{4})", userHp))? (true) : (false) ;  
            if(!validate) {
            	message= "휴대폰 번호 입력에 문제가 있습니다.\n계속해서 문제가 발생하는 경우 hk@m-soft.co.kr로 문의해주세요.";
            	mv = new ModelAndView("/main/modUserInfo");
            	mv.addObject("servletMessage", message);
            }
            
            if(validate){
            	mainService.doModUserInfo(joinMap);
            	mv = new ModelAndView("/info/summary");
                mv.addObject("servletMessage", "정상적으로 수정되었습니다.");
            }else{
            	mv = new ModelAndView("/main/modUserInfo");
            	mv.addObject("servletMessage", message);
            }
            //--필터링부분
    		
    		
	        return mv;
	        /*--------------------------------------------------------------------------*/
    		/*						 	-- 기능 구현 부분							*/
    		/*--------------------------------------------------------------------------*/
    	}
    	/*----------------------------------------------*/
    	/*				-- 로그인 체크	 			*/
    	/*----------------------------------------------*/
    	return mv;

    } 
}