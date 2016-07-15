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
	@RequestMapping(value="/external/register.do")
    public ModelAndView contactRegister(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
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
    	String messageString= request.getParameter("message");
    	JSONObject resultJson= new JSONObject();
    	
    	if(messageString == null || messageString.equals("")){
    		resultJson.put("data", null);
    		resultJson.put("success", "false");
    		
    	} else {
    		JSONParser parser= new JSONParser();
        	JSONObject message= (JSONObject) parser.parse(messageString);
        	
        	if(!message.containsKey("success") || !message.containsKey("data")) {	// 메시지 형식이 갖춰지지 않은 경우
	    		resultJson.put("data", null);
	    		resultJson.put("success", "false");
	    		
	    	} else {	// 메시지 형식이 잘 갖춰진 경우
	        	JSONObject data= (JSONObject) message.get("data");
	        	String success= String.valueOf( message.get("success") );
	        	
	        	String name= String.valueOf( data.get("name") );
	        	String pnum= String.valueOf( data.get("pnum") );
	        	String skey= String.valueOf( data.get("skey") );
	        	String akey= String.valueOf( data.get("akey") );
	        	
	        	Map<String,Object> paramMap= new HashMap<String,Object>();
	        	
	        	
	        	if(success.equals("true")){
	        		success= null;
	        		
	        		paramMap.put("name", name);
	            	paramMap.put("pnum", pnum);
	            	paramMap.put("skey",skey);
	            	paramMap.put("akey", akey);
	            	
	            	paramMap= externalService.registerContact(paramMap);
	            	success= String.valueOf(paramMap.get("success"));
	            	
	            	if(success.equals("true")){
	            		resultJson.put("data", null);
	            		resultJson.put("success", "true");
	            		
	            	} else {
	            		resultJson.put("data", null);
	            		resultJson.put("success", "false");
	            		
	            	}
	        	} else {
	        		resultJson.put("data", null);
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
     * 연락처 제거																									*
     * @param commandMap																								*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/external/remove.do")
    public ModelAndView contactRemove(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
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
    	String messageString= request.getParameter("message");
    	JSONObject resultJson= new JSONObject();
    	
    	if(messageString == null || messageString.equals("")){
    		resultJson.put("data", null);
    		resultJson.put("success", "false");
    		
    	} else {
	    	JSONParser parser= new JSONParser();
	    	JSONObject message= (JSONObject) parser.parse(messageString);
	    	
	    	if(!message.containsKey("success") || !message.containsKey("data")) {	// 메시지 형식이 갖춰지지 않은 경우
	    		resultJson.put("data", null);
	    		resultJson.put("success", "false");
	    		
	    	} else {	// 메시지 형식이 잘 갖춰진 경우
		    	
		    	JSONObject data= (JSONObject) message.get("data");
		    	String success= String.valueOf( message.get("success") );
		    	
		    	String name= String.valueOf( data.get("name") );
		    	String pnum= String.valueOf( data.get("pnum") );
		    	String skey= String.valueOf( data.get("skey") );
		    	String akey= String.valueOf( data.get("akey") );
		    	
		    	Map<String,Object> paramMap= new HashMap<String,Object>();
		    	
		    	if(success.equals("true")){
		    		success= null;
		    		
		    		paramMap.put("name", name);
		        	paramMap.put("pnum", pnum);
		        	paramMap.put("skey",skey);
		        	paramMap.put("akey", akey);
		        	
		        	paramMap= externalService.removeContact(paramMap);
		        	success= String.valueOf(paramMap.get("success"));
		        	
		        	if(success.equals("true")){
		        		resultJson.put("data", null);
		        		resultJson.put("success", "true");
		        		
		        	} else {
		        		resultJson.put("data", null);
		        		resultJson.put("success", "false");
		        		
		        	}
		    	} else {
		    		resultJson.put("data", null);
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
     * 연락처 목록 조회																								*
     * @param commandMap																								*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/external/select.do")
    public ModelAndView contactSelect(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
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
		    	
		    	String skey= String.valueOf( data.get("skey"));
		    	String akey= String.valueOf( data.get("akey"));
		    	
		    	Map<String,Object> paramMap= new HashMap<String,Object>();
		    	JSONArray resultData= new JSONArray();
		    	JSONObject resultDataRow= null;
		    	
		    	
		    	if(success.equals("true")){	// 안드로이드 쪽에서 성공 메시지를 받은 경우
		    		success= null;
		    		
		    		paramMap.put("skey",skey);
			    	paramMap.put("akey", akey);
			    	
			    	paramMap= externalService.selectContact(paramMap);
			    	success= String.valueOf(paramMap.get("success"));
			    	
			    	if(success.equals("true")){	// 웹서버에서 성공 메시지를 보내는 경우
				    	ArrayList<ContactInfoVO> contactArray= (ArrayList<ContactInfoVO>) paramMap.get("data");
				    	for(ContactInfoVO c:contactArray){
				    		resultDataRow= new JSONObject();
				    		resultDataRow.put("name", c.getName());
				    		resultDataRow.put("pnum", c.getPnum());
				    		resultDataRow.put("akey", c.getAkey());
				    		resultDataRow.put("skey", c.getSkey());
				    		
				    		resultData.add(resultDataRow);
				    	}
				    	
				    	resultJson.put("data", resultData);
				    	resultJson.put("success", "true");
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
