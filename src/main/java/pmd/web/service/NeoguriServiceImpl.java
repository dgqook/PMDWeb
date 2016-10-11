package pmd.web.service;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import pmd.common.common.PMDUtil;
import pmd.common.vo.NeoguriVO;
import pmd.web.dao.NeoguriDAO;

@Service("neoguriService")
public class NeoguriServiceImpl implements NeoguriService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="neoguriDAO")
	private NeoguriDAO neoguriDAO;
	
	PMDUtil pmd= new PMDUtil();

	@SuppressWarnings("unchecked")
	@Override
	public String selectTasks(String userId) {
		pmd.logging("NEOGURI SERVICE ACCESS - selectTasks");
		
		Map<String,Object> paramMap= new HashMap<String,Object>();
		paramMap.put("userId", userId);
		ArrayList<NeoguriVO> taskList= neoguriDAO.selectTasks(paramMap);
		
		JSONArray jsonArray= new JSONArray();
		JSONObject jsonObject;
		
		for(NeoguriVO n:taskList){
			jsonObject= new JSONObject();
			
			jsonObject.put("userId", n.getUserId());
			jsonObject.put("taskName", n.getTaskName());
			jsonObject.put("taskExpl", n.getTaskExpl());
			jsonObject.put("taskDttm", n.getTaskDttm());
			jsonObject.put("setAlarm", n.getSetAlarm());
			jsonObject.put("remindAlarm", n.getRemindAlarm());
			jsonObject.put("ontimeAlarm", n.getOntimeAlarm());
			
			jsonArray.add(jsonObject);
		}
		pmd.logging("NEOGURI SERVICE EXIT - selectTasks");
		return jsonArray.toJSONString();
	}

	@Override
	public void insertTask(String userId, String taskName, String taskExpl, String taskDttm, String setAlarm, String remindAlarm,
			String ontimeAlarm) {
		pmd.logging("NEOGURI SERVICE ACCESS - insertTask");
		
		Map<String,Object> paramMap= new HashMap<String,Object>();
		
		paramMap.put("userId", userId);
		paramMap.put("taskName", taskName);
		paramMap.put("taskExpl", taskExpl);
		paramMap.put("taskDttm", taskDttm);
		paramMap.put("setAlarm", setAlarm);
		paramMap.put("remindAlarm", remindAlarm);
		paramMap.put("ontimeAlarm", ontimeAlarm);
		
		neoguriDAO.insertTask(paramMap);
		pmd.logging("NEOGURI SERVICE EXIT - insertTask");
	}

	@Override
	public void deleteTask(String userId, String taskName, String taskDttm) {
		pmd.logging("NEOGURI SERVICE ACCESS - deleteTask");
		
		Map<String,Object> paramMap= new HashMap<String,Object>();
		
		paramMap.put("userId", userId);
		paramMap.put("taskName", taskName);
		paramMap.put("taskDttm", taskDttm);
		
		neoguriDAO.deleteTask(paramMap);
		pmd.logging("NEOGURI SERVICE EXIT - deleteTask");
	}
	
	@Override
	public void deleteTasks(String userId) {
		pmd.logging("NEOGURI SERVICE ACCESS - deleteTasks");
		
		Map<String,Object> paramMap= new HashMap<String,Object>();
		
		paramMap.put("userId", userId);
		
		neoguriDAO.deleteTasks(paramMap);
		pmd.logging("NEOGURI SERVICE EXIT - deleteTasks");
	}

	@Override
	public void modifyTask(String userId, String taskName, String taskExpl, String taskDttm, String setAlarm, String remindAlarm,
			String ontimeAlarm) {
		pmd.logging("NEOGURI SERVICE ACCESS - modifyTask");
		
		Map<String,Object> paramMap= new HashMap<String,Object>();
		
		paramMap.put("userId", userId);
		paramMap.put("taskName", taskName);
		paramMap.put("taskExpl", taskExpl);
		paramMap.put("taskDttm", taskDttm);
		paramMap.put("setAlarm", setAlarm);
		paramMap.put("remindAlarm", remindAlarm);
		paramMap.put("ontimeAlarm", ontimeAlarm);
		
		neoguriDAO.modifyTask(paramMap);
		pmd.logging("NEOGURI SERVICE EXIT - modifyTask");
	}

	@SuppressWarnings("unchecked")
	@Override
	public String selectUser(String userId, String userPw) {
		pmd.logging("NEOGURI SERVICE ACCESS - selectUser");
		
		Map<String,Object> paramMap= new HashMap<String,Object>();
		paramMap.put("userId", userId);
		paramMap.put("userPw", userPw);
		ArrayList<NeoguriVO> taskList= neoguriDAO.selectUser(paramMap);
		
		JSONArray jsonArray= new JSONArray();
		JSONObject jsonObject;
		
		for(NeoguriVO n:taskList){
			jsonObject= new JSONObject();
			
			jsonObject.put("userId", n.getUserId());
			jsonObject.put("userName", n.getUserName());
			
			jsonArray.add(jsonObject);
		}
		pmd.logging("NEOGURI SERVICE EXIT - selectUser");
		return jsonArray.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String selectUsers() {
		pmd.logging("NEOGURI SERVICE ACCESS - selectUsers");
		
		ArrayList<NeoguriVO> taskList= neoguriDAO.selectUsers();
		
		JSONArray jsonArray= new JSONArray();
		JSONObject jsonObject;
		
		for(NeoguriVO n:taskList){
			jsonObject= new JSONObject();
			
			jsonObject.put("userId", n.getUserId());
			
			jsonArray.add(jsonObject);
		}
		pmd.logging("NEOGURI SERVICE EXIT - selectUsers");
		return jsonArray.toJSONString();
	}

	@Override
	public void insertUser(String userId, String userPw, String userName) {
		pmd.logging("NEOGURI SERVICE ACCESS - insertUser");
		
		ArrayList<NeoguriVO> userList= neoguriDAO.selectUsers();
		boolean isDup= false;
		
		for(NeoguriVO n:userList){
			if(n.getUserId().equals(userId)){
				pmd.logging("중복!");
				isDup= true;
			}
		}
		
		if( ! isDup ){
			Map<String,Object> paramMap= new HashMap<String,Object>();
			
			paramMap.put("userId", userId);
			paramMap.put("userName", userName);
			paramMap.put("userPw", userPw);
			
			neoguriDAO.insertUser(paramMap);
		}
		pmd.logging("NEOGURI SERVICE EXIT - insertUser");
	}

	@Override
	public void deleteUser(String userId, String userPw) {
		pmd.logging("NEOGURI SERVICE ACCESS - deleteUser");
		
		Map<String,Object> paramMap= new HashMap<String,Object>();
		
		paramMap.put("userId", userId);
		paramMap.put("userPw", userPw);
		
		neoguriDAO.deleteUser(paramMap);
		pmd.logging("NEOGURI SERVICE EXIT - deleteUser");
	}

	@Override
	public void modifyUser(String userId, String userPw, String userName) {
		pmd.logging("NEOGURI SERVICE ACCESS - modifyUser");
		
		Map<String,Object> paramMap= new HashMap<String,Object>();
		
		paramMap.put("userId", userId);
		paramMap.put("userPw", userPw);
		paramMap.put("userName", userName);
		
		neoguriDAO.modifyUser(paramMap);
		pmd.logging("NEOGURI SERVICE EXIT - modifyUser");
	}
}