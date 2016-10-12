package pmd.web.controller;

import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pmd.common.common.CommandMap;
import pmd.common.common.PMDUtil;
import pmd.web.service.NeoguriService;

@Controller
public class NeoguriController{
	
	@Resource(name="neoguriService")
	NeoguriService nService;
	
	PMDUtil pmd= new PMDUtil();
	
	/**
     *  NEOGURI 안드로이드 어플리케이션 통신용 URL
     */
    @RequestMapping(value="/external/neoguri.do")
    public ModelAndView neoguri(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception {
    	pmd.logging("NEOGURI ACCESS");
    	ModelAndView mv= new ModelAndView("external/test");
    	
    	String cmd= null;
    	String param= null;
    	boolean paramCheck= false;
    	String result= "false";
    	
    	cmd= request.getParameter("cmd");
    	param= request.getParameter("param");
    	
    	if(cmd != null && !cmd.equals("")){
    		paramCheck= (param != null && !param.equals(""));
    		pmd.logging("param: "+param);
    		
    		@SuppressWarnings("unused")
			StringTokenizer stringTokenizer= null;
    		
    		if(cmd.equals("selectTasks") && paramCheck){			// 사용자에게 속한 일정 목록 조회 -> JSON Array 형식으로 반환
    			stringTokenizer= new StringTokenizer(param, ",,");
    			String userId= stringTokenizer.nextToken();
    			
    			result= nService.selectTasks(userId);
    			
    		}else if(cmd.equals("insertTask") && paramCheck){	// 일정 추가
    			stringTokenizer= new StringTokenizer(param, ",,");
    			String userId= stringTokenizer.nextToken();		pmd.logging("userId: "+userId);
    			String taskName= stringTokenizer.nextToken();	pmd.logging("taskName: "+taskName);
    			String taskExpl= stringTokenizer.nextToken();		pmd.logging("taskExpl: "+taskExpl);
    			String taskDttm= stringTokenizer.nextToken();		pmd.logging("taskDttm: "+taskDttm);
    			String setAlarm= stringTokenizer.nextToken();		pmd.logging("setAlarm: "+setAlarm);
    			String remindAlarm= "";
    			if(setAlarm.equals("true")){
    				remindAlarm= stringTokenizer.nextToken();	pmd.logging("remindAlarm: "+remindAlarm);
    			}
    			String ontimeAlarm= stringTokenizer.nextToken();	pmd.logging("ontimeAlarm: "+ontimeAlarm);
    			
    			nService.insertTask(userId, taskName, taskExpl, taskDttm, setAlarm, remindAlarm, ontimeAlarm);
    			result= "true";
    			
    		}else if(cmd.equals("deleteTask") && paramCheck){  // 일정 삭제
    			stringTokenizer= new StringTokenizer(param, ",,");
    			String userId= stringTokenizer.nextToken();
    			String taskName= stringTokenizer.nextToken();
    			String taskDttm= stringTokenizer.nextToken();	
    			
    			nService.deleteTask(userId, taskName, taskDttm);
    			result= "true";
    			
    		}else if(cmd.equals("deleteTasks") && paramCheck){	// 전체 일정 삭제
    			stringTokenizer= new StringTokenizer(param, ",,");
    			String userId= stringTokenizer.nextToken();
    			
    			nService.deleteTasks(userId);
    			result= "true";
		    			
    		}else if(cmd.equals("modifyTask") && paramCheck){	// 일정 정보 수정
    			stringTokenizer= new StringTokenizer(param, ",,");
    			String userId= stringTokenizer.nextToken();
    			String taskName= stringTokenizer.nextToken();
    			String taskExpl= stringTokenizer.nextToken();
    			String taskDttm= stringTokenizer.nextToken();
    			String setAlarm= stringTokenizer.nextToken();
    			String remindAlarm= stringTokenizer.nextToken();
    			String ontimeAlarm= stringTokenizer.nextToken();    		
    			
    			nService.modifyTask(userId, taskName, taskExpl, taskDttm, setAlarm, remindAlarm, ontimeAlarm);
    			result= "true";
    			
    		}else if(cmd.equals("selectUser") && paramCheck){	// 단일 사용자 조회 -> JSON Array 형식으로 반환
    			stringTokenizer= new StringTokenizer(param, ",,");
    			String userId= stringTokenizer.nextToken();
    			String userPw= stringTokenizer.nextToken();		
    			
    			result= nService.selectUser(userId, userPw);
    			
    		}else if(cmd.equals("selectUsers")){						// 전체 사용자 조회 -> JSON Array 형식으로 반환
    			result= nService.selectUsers();
    			
    		}else if(cmd.equals("insertUser") && paramCheck){	// 사용자 추가
    			stringTokenizer= new StringTokenizer(param, ",,");
    			String userId= stringTokenizer.nextToken();
    			String userPw= stringTokenizer.nextToken();
    			String userName= stringTokenizer.nextToken();		
    			
    			nService.insertUser(userId, userPw, userName);
    			result= "true";
    			
    		}else if(cmd.equals("deleteUser") && paramCheck){	// 사용자 삭제
    			stringTokenizer= new StringTokenizer(param, ",,");
    			String userId= stringTokenizer.nextToken();
    			String userPw= stringTokenizer.nextToken();
    			
    			nService.deleteUser(userId, userPw);
    			result= "true";
    			
    		}else if(cmd.equals("modifyUser") && paramCheck){	// 사용자 정보 수정
    			stringTokenizer= new StringTokenizer(param, ",,");
    			String userId= stringTokenizer.nextToken();
    			String userPw= stringTokenizer.nextToken();
    			String userName= stringTokenizer.nextToken();	
    			
    			nService.modifyUser(userId, userPw, userName);
    			result= "true";
    			
    		}else{ // 기타 입력
    			result="wrong command";
    		}
    	}else{
    		result="NEOGURI ☆★";
    	}
    	    	
    	mv.addObject("result", result);
    	return mv;
    }

}