package pmd.web.service;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import pmd.common.vo.NeoguriVO;
import pmd.web.dao.NeoguriDAO;

@Service("workService")
public class NeoguriServiceImpl implements NeoguriService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="neoguriDAO")
	private NeoguriDAO neoguriDAO;

	@SuppressWarnings("unchecked")
	@Override
	public String selectTasks(String userId) {
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
		return jsonArray.toJSONString();
	}

	@Override
	public void insertTask(String userId, String taskName, String taskExpl, String taskDttm, String setAlarm, String remindAlarm,
			String ontimeAlarm) {
		Map<String,Object> paramMap= new HashMap<String,Object>();
		
		paramMap.put("userId", userId);
		paramMap.put("taskName", taskName);
		paramMap.put("taskExpl", taskExpl);
		paramMap.put("taskDttm", taskDttm);
		paramMap.put("setAlarm", setAlarm);
		paramMap.put("remindAlarm", remindAlarm);
		paramMap.put("ontimeAlarm", ontimeAlarm);
		
		neoguriDAO.insertTask(paramMap);
		
	}

	@Override
	public void deleteTask(String userId, String taskName, String taskDttm) {
		Map<String,Object> paramMap= new HashMap<String,Object>();
		
		paramMap.put("userId", userId);
		paramMap.put("taskName", taskName);
		paramMap.put("taskDttm", taskDttm);
		
		neoguriDAO.deleteTask(paramMap);
	}
	
	@Override
	public void deleteTasks(String userId) {
		Map<String,Object> paramMap= new HashMap<String,Object>();
		
		paramMap.put("userId", userId);
		
		neoguriDAO.deleteTasks(paramMap);
	}

	@Override
	public void modifyTask(String userId, String taskName, String taskExpl, String taskDttm, String setAlarm, String remindAlarm,
			String ontimeAlarm) {
		Map<String,Object> paramMap= new HashMap<String,Object>();
		
		paramMap.put("userId", userId);
		paramMap.put("taskName", taskName);
		paramMap.put("taskExpl", taskExpl);
		paramMap.put("taskDttm", taskDttm);
		paramMap.put("setAlarm", setAlarm);
		paramMap.put("remindAlarm", remindAlarm);
		paramMap.put("ontimeAlarm", ontimeAlarm);
		
		neoguriDAO.modifyTask(paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String selectUser(String userId, String userPw) {
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
		return jsonArray.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String selectUsers() {
		ArrayList<NeoguriVO> taskList= neoguriDAO.selectUsers();
		
		JSONArray jsonArray= new JSONArray();
		JSONObject jsonObject;
		
		for(NeoguriVO n:taskList){
			jsonObject= new JSONObject();
			
			jsonObject.put("userId", n.getUserId());
			
			jsonArray.add(jsonObject);
		}
		return jsonArray.toJSONString();
	}

	@Override
	public void insertUser(String userId, String userPw, String userName) {
		Map<String,Object> paramMap= new HashMap<String,Object>();
		
		paramMap.put("userId", userId);
		paramMap.put("userName", userName);
		paramMap.put("userPw", userPw);
		
		neoguriDAO.insertUser(paramMap);
	}

	@Override
	public void deleteUser(String userId, String userPw) {
		Map<String,Object> paramMap= new HashMap<String,Object>();
		
		paramMap.put("userId", userId);
		paramMap.put("userPw", userPw);
		
		neoguriDAO.deleteUser(paramMap);
	}

	@Override
	public void modifyUser(String userId, String userPw, String userName) {
		Map<String,Object> paramMap= new HashMap<String,Object>();
		
		paramMap.put("userId", userId);
		paramMap.put("userPw", userPw);
		paramMap.put("userName", userName);
		
		neoguriDAO.modifyUser(paramMap);
	}
}