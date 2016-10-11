package pmd.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pmd.common.common.CommandMap;
import pmd.common.common.PMDUtil;
import pmd.common.vo.ContactInfoVO;
import pmd.web.service.ExternalService; 

@Controller
public class ExternalController {
	
	PMDUtil pmd= new PMDUtil();
	
    @Resource(name="externalService")
    private ExternalService externalService;
    
	/*******************************************************************************************************
     * 연결 테스트용																									*
     * @param commandMap																								*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @RequestMapping(value="/external/connectTest.do")
    public ModelAndView externalConnectTest(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	ModelAndView mv= new ModelAndView("external/test");
    	pmd.logging("[LOG] EXTERNAL CONNECT TEST");
    	
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
		String result = "";
	
		result="Connecting successful !";
		
        	
        /*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
		
		mv.addObject("result", result);
		return mv;
    }
    
    /*******************************************************************************************************
     * 연락처 추가																								*
     * @param commandMap																								*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/external/contactRegister.do")
    public ModelAndView contactRegister(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	
    	ModelAndView mv= new ModelAndView("external/test");
    	
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
    	String messageString= request.getParameter("message");
    	JSONObject resultJson= new JSONObject();
    	
    	pmd.logging("contactRegister - 시작 / 메시지 존재 여부: "+(messageString == null || messageString.equals("")));
    	
    	if(messageString == null || messageString.equals("")){
    		pmd.logging("contactRegister - 메세지 내용 없음");
    		
    		resultJson.put("data", null);
    		resultJson.put("success", "false");
    		resultJson.put("message", "비정상적인 접근입니다.");
    		
    	} else {
    		pmd.logging("contactRegister - 메세지 내용 있음");
    		
    		JSONParser parser= new JSONParser();
        	JSONObject message= (JSONObject) parser.parse(messageString);
        	
        	if(!message.containsKey("success") || !message.containsKey("data")) {	// 메시지 형식이 갖춰지지 않은 경우
        		pmd.logging("contactRegister - 메세지 형식 비정상");
        		
	    		resultJson.put("data", null);
	    		resultJson.put("success", "false");
	    		resultJson.put("message", "비정상적인 접근입니다.");
	    		
	    	} else {	// 메시지 형식이 잘 갖춰진 경우
	    		pmd.logging("contactRegister - 메세지 형식 정상");
	    		
	        	JSONObject data= (JSONObject) message.get("data");
	        	String success= String.valueOf( message.get("success") );
	        	
	        	// 다섯개 정보 전부 가져오기
	        	String name= String.valueOf( data.get("name") );
	        	String pnum= String.valueOf( data.get("pnum") );
	        	String unum= String.valueOf( data.get("unum") );
	        	String pkey= String.valueOf( data.get("pkey") );
	        	String messageReq= String.valueOf( data.get("messageReq") );
	        	
	        	pmd.logging("messageReq : "+messageReq);
	        	
	        	Map<String,Object> paramMap= new HashMap<String,Object>();
	        	
	        	
	        	if(success.equals("true")){ // 안드로이드에서 보내 온 내용이 정상적인 경우
	        		pmd.logging("contactRegister - 어플리케이션 TRUE");
	        		
	        		success= null;
	        		
	        		paramMap.put("name", name);
	            	paramMap.put("pnum", pnum);
	            	paramMap.put("unum",unum);
	            	paramMap.put("pkey", pkey);
	            	paramMap.put("messageReq",messageReq);
	            	pmd.logging("contactRegister - 추가할 연락처 이름 : "+name);
	            	pmd.logging("contactRegister - 추가할 연락처 번호 : "+pnum);
	            	pmd.logging("contactRegister - 제품 소유자 번호 : "+unum);
	            	pmd.logging("contactRegister - 제품 번호 : "+pkey);
	            	pmd.logging("contactRegister - 위급 상황 시 메시지 내용 : "+messageReq);
	            	
	            	// 중복체크 먼저 수행하고 아래 기능 수행
	            	pmd.logging("contactRegister - 비교용 연락처 목록 불러오기");
		        	ArrayList<ContactInfoVO> contactList= externalService.getContactList(paramMap);
		        	
		        	boolean duplicate= false;
		        	pmd.logging("contactRegister - 비교할 대상: ("+name+"/"+pnum+"/"+unum+"/"+pkey+"/"+messageReq+")");
		        	for(ContactInfoVO c: contactList){
		        		pmd.logging("contactRegister - 대조군: ("+name+"/"+pnum+"/"+unum+"/"+pkey+"/"+messageReq+")");
		        		
		        		if(c.getName().equals(name) && c.getPnum().equals(pnum) && c.getUnum().equals(unum) && c.getPkey().equals(pkey)){
		        			duplicate= true;
		        		}
		        	}
		        	
		        	if(!duplicate){		// 중복된 데이터가 존재하지 않는 경우
		        		pmd.logging("contactRegister - 중복 데이터 없음, 등록 가능");
		        		
		        		pmd.logging("contactRegister - 기존 연락처 수: "+contactList.size());
		        		
		        		contactList.add(new ContactInfoVO(name, pnum, unum, pkey,messageReq));
		        		paramMap.put("contactList",contactList);
		        		
		        		pmd.logging("contactRegister - 연락처 등록 서비스 실행");
		        		paramMap= externalService.registerContact(paramMap);
		            	success= String.valueOf(paramMap.get("success"));
		            	
		            	if(success.equals("true")){
		            		pmd.logging("contactRegister - 연락처 등록 성공");
		            		resultJson.put("data", null);
		            		resultJson.put("success", "true");
		            		resultJson.put("message", String.valueOf(paramMap.get("message")));
		            		
		            	} else {
		            		pmd.logging("contactRegister - 연락처 등록 실패");
		            		
		            		resultJson.put("data", null);
		            		resultJson.put("success", "false");
		            		resultJson.put("message", "연락처 추가에 실패했습니다.");
		            	}
		        	} else {		// 중복된 데이터가 존재하는 경우
		        		pmd.logging("contactRegister - 중복 데이터 존재");
		        		
		        		resultJson.put("data", null);
	            		resultJson.put("success", "false");
	            		resultJson.put("message", "이미 등록된 연락처입니다.");
		        	}
	        	} else {
	        		pmd.logging("contactRegister - 어플리케이션 FALSE");
	        		
	        		resultJson.put("data", null);
	        		resultJson.put("success", "false");
	        		resultJson.put("message", "비정상적인 접근입니다.");
	        	}
	    	}
    	}
    	
    	
        /*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
		
		mv.addObject("result", resultJson.toJSONString());
		return mv;
    }
    
    /*******************************************************************************************************
     * 연락처 제거																									*
     * @param commandMap																								*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/external/contactRemove.do")
    public ModelAndView contactRemove(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	ModelAndView mv= new ModelAndView("external/test");
    	
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
    	String messageString= request.getParameter("message");
    	JSONObject resultJson= new JSONObject();
    	
    	if(messageString == null || messageString.equals("")){ // 메세지가 없는 경우
    		resultJson.put("data", null);
    		resultJson.put("success", "false");
    		resultJson.put("message", "비정상적인 접근입니다.");
    		
    	} else {
	    	JSONParser parser= new JSONParser();
	    	JSONObject message= (JSONObject) parser.parse(messageString);
	    	
	    	if(!message.containsKey("success") || !message.containsKey("data")) {	// 메시지 형식이 갖춰지지 않은 경우
	    		resultJson.put("data", null);
	    		resultJson.put("success", "false");
	    		resultJson.put("message", "비정상적인 접근입니다.");
	    		
	    	} else {	// 메시지 형식이 잘 갖춰진 경우
		    	
		    	JSONObject data= (JSONObject) message.get("data");
		    	String success= String.valueOf( message.get("success") );
		    	
		    	String name= String.valueOf( data.get("name") );
		    	String pnum= String.valueOf( data.get("pnum") );
		    	String unum= String.valueOf( data.get("unum") );
		    	String pkey= String.valueOf( data.get("pkey") );
		    	
		    	
		    	Map<String,Object> paramMap= new HashMap<String,Object>();
		    	
		    	
		    	
		    	if(success.equals("true")){ // 안드로이드 측에서 성공적으로 보낸 메시지인 경우(?)
		    		success= null;
		    		
		    		paramMap.put("name", name);
		        	paramMap.put("pnum", pnum);
		        	paramMap.put("unum",unum);
		        	paramMap.put("pkey", pkey);
		        	ArrayList<ContactInfoVO> contactList= externalService.getContactList(paramMap);
		        	ArrayList<ContactInfoVO> updateList= new ArrayList<ContactInfoVO>();
		        	
		        	
		        	
			    	for(ContactInfoVO c:contactList){
			    		if(!c.getName().equals(name) && !c.getPnum().equals(pnum)) updateList.add(c);
			    	}
			    	
			    	// 하나도 정보가 없으면 서비스 구현부에서 정보를 알아낼 수 없으므로 임시로 하나 추가
			    	if(updateList.size() == 0) {
		        		updateList.add(new ContactInfoVO("", "", unum, pkey));	
		        	}
			    	paramMap.put("contactList",updateList);
			    	
			    	externalService.registerContact(paramMap);
			    	
		        	success= String.valueOf(paramMap.get("success"));
		        	
		        	if(success.equals("true")){
		        		resultJson.put("data", null);
		        		resultJson.put("success", "true");
		        		resultJson.put("message", "연락처가 삭제되었습니다.");
		        		
		        	} else {
		        		resultJson.put("data", null);
		        		resultJson.put("success", "false");
		        		resultJson.put("message", String.valueOf(paramMap.get("message")));
		        		
		        	}
		    	} else {
		    		resultJson.put("data", null);
		    		resultJson.put("success", "false");
		    		resultJson.put("message", "비정상적인 접근입니다.");
		    	}
	    	}
    	}
    	
        /*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
		
		mv.addObject("result", resultJson.toJSONString());
		return mv;
    }
    
    /*******************************************************************************************************
     * 연락처 목록 조회																								*
     * @param commandMap																								*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/external/contactSelect.do")
    public ModelAndView contactSelect(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	ModelAndView mv= new ModelAndView("external/test");
    	
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
    	String messageString= request.getParameter("message");
    	JSONObject resultJson= new JSONObject();
    	
    	if(messageString == null || messageString.equals("")){ // 메시지 내용이 없는 경우
    		resultJson.put("data", null);
    		resultJson.put("success", "false");
    		
    	} else {	// 메시지 내용이 있는 경우
	    	JSONParser parser= new JSONParser();
	    	JSONObject message= (JSONObject) parser.parse(messageString);
	    	
	    	if(!message.containsKey("success") || !message.containsKey("data")) {	// 메시지 형식이 갖춰지지 않은 경우
	    		resultJson.put("data", null);
	    		resultJson.put("success", "false");
	    		
	    	} else {	// 메시지 형식이 잘 갖춰진 경우
	    		JSONObject data= (JSONObject) message.get("data");
		    	String success= String.valueOf( message.get("success") );
		    	
		    	String unum= String.valueOf( data.get("unum"));
		    	String pkey= String.valueOf( data.get("pkey"));
		    	
		    	Map<String,Object> paramMap= new HashMap<String,Object>();
		    	JSONArray resultData= new JSONArray();
		    	JSONObject resultDataRow= null;
		    	
		    	
		    	
		    	if(success.equals("true")){	// 안드로이드 쪽에서 성공 메시지를 받은 경우
		    		success= null;
		    		
		    		paramMap.put("unum",unum);
			    	paramMap.put("pkey", pkey);
			    	
			    	paramMap= externalService.selectContact(paramMap);
			    	success= String.valueOf(paramMap.get("success"));
			    	
			    	if(success.equals("true")){	// 웹서버에서 성공 메시지를 보내는 경우
				    	ArrayList<ContactInfoVO> contactArray= (ArrayList<ContactInfoVO>) paramMap.get("data");
				    	
				    	if(contactArray.size()==0){
				    		resultJson.put("data", resultData);
					    	resultJson.put("success", "true");
				    	} else {
					    	for(ContactInfoVO c:contactArray){
					    		resultDataRow= new JSONObject();
					    		resultDataRow.put("name", c.getName());
					    		resultDataRow.put("pnum", c.getPnum());
					    		resultDataRow.put("unum", c.getUnum());
					    		resultDataRow.put("pkey", c.getPkey());
					    		
					    		resultData.add(resultDataRow);
					    	}
					    	
					    	resultJson.put("data", resultData);
					    	resultJson.put("success", "true");
				    	}
			    	} else {	// 웹서버에서 실패 메시지를 보내는 경우
			    		resultJson.put("data", null);
				    	resultJson.put("success", "false");
			    	}
		    	} else {	// 안드로이드 쪽에서 받은 메시지를 보낸 경우
		    		resultJson.put("data", "false");
		    		resultJson.put("success", "false");
		    	}
	    	}
    	}
    	
        /*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
		
		mv.addObject("result", resultJson.toJSONString());
		return mv;
    }

    /*******************************************************************************************************
     * 메시지 전송 체크																							*
     * @param commandMap																								*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/external/messageCheck.do")
    public ModelAndView messageCheck(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	ModelAndView mv= new ModelAndView("external/test");
    	
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
    	String messageString= request.getParameter("message");
    	JSONObject resultJson= new JSONObject();
    	
    	if(messageString == null || messageString.equals("")){ // 메시지 내용이 없는 경우
    		resultJson.put("data", null);
    		resultJson.put("success", "false");
    		
    	} else {	// 메시지 내용이 있는 경우
	    	JSONParser parser= new JSONParser();
	    	JSONObject message= (JSONObject) parser.parse(messageString);
	    	
	    	if(!message.containsKey("success") || !message.containsKey("data")) {	// 메시지 형식이 갖춰지지 않은 경우
	    		resultJson.put("data", null);
	    		resultJson.put("success", "false");
	    		
	    	} else {	// 메시지 형식이 잘 갖춰진 경우
	    		JSONObject data= (JSONObject) message.get("data");
		    	String success= String.valueOf( message.get("success") );
		    	
		    	String pkey= String.valueOf( data.get("pkey"));
		    	
		    	Map<String,Object> paramMap= new HashMap<String,Object>();
		    	
		    	if(success.equals("true")){	// 안드로이드 쪽에서 성공 메시지를 받은 경우
		    		success= null;
		    		
		    		paramMap.put("pkey", pkey);
			    	
			    	paramMap= externalService.messageCheck(paramMap);
			    	success= String.valueOf(paramMap.get("success"));
			    	
			    	if(success.equals("true")){	// 웹서버에서 성공 메시지를 보내는 경우
				    	String check= (String) paramMap.get("data");
				    	// data에 일반 String을 넣어서 메시지 전송이 가능한 상황이면 true, 아니면 false를 넣는다.
				    	if(check == null || check.equals("")){
				    		resultJson.put("data", "false");
					    	resultJson.put("success", "false");
				    	} else {
					    	resultJson.put("data", check);
					    	resultJson.put("success", "true");
				    	}
			    	} else {	// 웹서버에서 실패 메시지를 보내는 경우
			    		resultJson.put("data", null);
				    	resultJson.put("success", "false");
			    	}
		    	} else {	// 안드로이드 쪽에서 받은 메시지를 보낸 경우
		    		resultJson.put("data", "false");
		    		resultJson.put("success", "false");
		    	}
	    	}
    	}
    	
        /*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
		
		mv.addObject("result", resultJson.toJSONString());
		return mv;
    }
    
    /*******************************************************************************************************
     * 위치 전송 및 메시지 전송 여부 확인																				*
     * @param commandMap																								*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/external/locationUpload.do")
    public ModelAndView locationUpload(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	ModelAndView mv= new ModelAndView("external/test");
    	
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
    	String messageString= request.getParameter("message");
    	JSONObject resultJson= new JSONObject();
    	
    	if(messageString == null || messageString.equals("")){ // 메시지 내용이 없는 경우
    		resultJson.put("data", null);
    		resultJson.put("success", "false");
    		
    	} else {	// 메시지 내용이 있는 경우
	    	JSONParser parser= new JSONParser();
	    	JSONObject message= (JSONObject) parser.parse(messageString);
	    	
	    	if(!message.containsKey("success") || !message.containsKey("data")) {	// 메시지 형식이 갖춰지지 않은 경우
	    		resultJson.put("data", null);
	    		resultJson.put("success", "false");
	    		
	    	} else {	// 메시지 형식이 잘 갖춰진 경우
	    		JSONObject data= (JSONObject) message.get("data");
		    	String success= String.valueOf( message.get("success") );
		    	
		    	String pkey= String.valueOf( data.get("pkey"));
		    	String longitude= String.valueOf(  data.get("longitude") );
		    	String latitude= String.valueOf(  data.get("latitude") );
		    	String accuracy= String.valueOf(data.get("accuracy"));
		    	
		    	Map<String,Object> paramMap= new HashMap<String,Object>();
		    	
		    	
		    	if(success.equals("true")){	// 안드로이드 쪽에서 성공 메시지를 받은 경우
		    		success= null;
		    		
		    		paramMap.put("pkey", pkey);
		    		paramMap.put("longitude", longitude);
		    		paramMap.put("latitude", latitude);
		    		paramMap.put("accuracy", accuracy);
			    	
			    	paramMap= externalService.locationUpload(paramMap);
			    	success= String.valueOf(paramMap.get("success"));
			    	
			    	if(success.equals("true")){	// 웹서버에서 성공 메시지를 보내는 경우
			    		resultJson.put("success", "ture");
				    	resultJson.put("data", "true");
				    	
			    	} else {	// 웹서버에서 실패 메시지를 보내는 경우
			    		resultJson.put("data", null);
				    	resultJson.put("success", "false");
			    	}
		    	} else {	// 안드로이드 쪽에서 받은 메시지를 보낸 경우
		    		resultJson.put("data", "false");
		    		resultJson.put("success", "false");
		    	}
	    	}
    	}
    	
        /*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
		
		mv.addObject("result", resultJson.toJSONString());
		return mv;
    }
    
    /*******************************************************************************************************
     * 메시지 내용 조회																				*
     * @param commandMap																								*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/external/selectMessage.do")
    public ModelAndView selectMessage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	ModelAndView mv= new ModelAndView("external/test");
    	
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
    	String messageString= request.getParameter("message");
    	JSONObject resultJson= new JSONObject();
    	
    	if(messageString == null || messageString.equals("")){ // 메시지 내용이 없는 경우
    		resultJson.put("data", null);
    		resultJson.put("success", "false");
    		
    	} else {	// 메시지 내용이 있는 경우
	    	JSONParser parser= new JSONParser();
	    	JSONObject paramMessage= (JSONObject) parser.parse(messageString);
	    	
	    	if(!paramMessage.containsKey("success") || !paramMessage.containsKey("data")) {	// 메시지 형식이 갖춰지지 않은 경우
	    		resultJson.put("data", null);
	    		resultJson.put("success", "false");
	    		
	    	} else {	// 메시지 형식이 잘 갖춰진 경우
	    		JSONObject data= (JSONObject) paramMessage.get("data");
		    	String success= String.valueOf( paramMessage.get("success") );
		    	
		    	String pkey= String.valueOf( data.get("pkey"));
		    	String message= String.valueOf(  data.get("message") );
		    	
		    	Map<String,Object> paramMap= new HashMap<String,Object>();
		    	
		    	
		    	if(success.equals("true")){	// 안드로이드 쪽에서 성공 메시지를 받은 경우
		    		success= null;
		    		
		    		paramMap.put("pkey", pkey);
		    		paramMap.put("message", message);
			    	
			    	paramMap= externalService.selectMessage(paramMap);
			    	success= String.valueOf(paramMap.get("success"));
			    	
			    	if(success.equals("true")){	// 웹서버에서 성공 메시지를 보내는 경우
			    		resultJson.put("success", "ture");
				    	resultJson.put("data", paramMap.get("message"));
				    	
			    	} else {	// 웹서버에서 실패 메시지를 보내는 경우
			    		resultJson.put("data", null);
				    	resultJson.put("success", "false");
			    	}
		    	} else {	// 안드로이드 쪽에서 받은 메시지를 보낸 경우
		    		resultJson.put("data", "false");
		    		resultJson.put("success", "false");
		    	}
	    	}
    	}
    	
        /*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
		
		mv.addObject("result", resultJson.toJSONString());
		return mv;
    }
    
    /*******************************************************************************************************
     * 메시지 내용 수정																				*
     * @param commandMap																								*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/external/modifyMessage.do")
    public ModelAndView modifyMessage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	ModelAndView mv= new ModelAndView("external/test");
    	
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
    	String messageString= request.getParameter("message");
    	JSONObject resultJson= new JSONObject();
    	
    	if(messageString == null || messageString.equals("")){ // 메시지 내용이 없는 경우
    		resultJson.put("data", null);
    		resultJson.put("success", "false");
    		
    	} else {	// 메시지 내용이 있는 경우
	    	JSONParser parser= new JSONParser();
	    	JSONObject paramMessage= (JSONObject) parser.parse(messageString);
	    	
	    	if(!paramMessage.containsKey("success") || !paramMessage.containsKey("data")) {	// 메시지 형식이 갖춰지지 않은 경우
	    		resultJson.put("data", null);
	    		resultJson.put("success", "false");
	    		
	    	} else {	// 메시지 형식이 잘 갖춰진 경우
	    		JSONObject data= (JSONObject) paramMessage.get("data");
		    	String success= String.valueOf( paramMessage.get("success") );
		    	
		    	String pkey= String.valueOf( data.get("pkey"));
		    	String message= String.valueOf(  data.get("message") );
		    	
		    	Map<String,Object> paramMap= new HashMap<String,Object>();
		    	
		    	
		    	if(success.equals("true")){	// 안드로이드 쪽에서 성공 메시지를 받은 경우
		    		success= null;
		    		
		    		paramMap.put("pkey", pkey);
		    		paramMap.put("message", message);
			    	
			    	paramMap= externalService.modifyMessage(paramMap);
			    	success= String.valueOf(paramMap.get("success"));
			    	
			    	if(success.equals("true")){	// 웹서버에서 성공 메시지를 보내는 경우
			    		resultJson.put("success", "ture");
				    	resultJson.put("data", "true");
				    	
			    	} else {	// 웹서버에서 실패 메시지를 보내는 경우
			    		resultJson.put("data", null);
				    	resultJson.put("success", "false");
			    	}
		    	} else {	// 안드로이드 쪽에서 받은 메시지를 보낸 경우
		    		resultJson.put("data", "false");
		    		resultJson.put("success", "false");
		    	}
	    	}
    	}
    	
        /*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
		
		mv.addObject("result", resultJson.toJSONString());
		return mv;
    }

    
}
