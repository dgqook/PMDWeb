package pmd.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pmd.common.common.CommandMap;
import pmd.common.common.PMDUtil;
import pmd.web.service.ExternalService;

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
    public void openSummaryPage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
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
        	
		
		PMDUtil.sendResponse(response, result);
        /*--------------------------------------------------------------------------*/
		/*						 	-- 기능 구현 부분							*/
		/*--------------------------------------------------------------------------*/
    	
    }
}
