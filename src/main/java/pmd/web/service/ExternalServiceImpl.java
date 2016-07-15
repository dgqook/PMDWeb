package pmd.web.service;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pmd.common.vo.ContactInfoVO;
import pmd.web.dao.ExternalDAO;

@Service("externalService")
public class ExternalServiceImpl implements ExternalService{

	@Resource(name="externalDAO")
	private ExternalDAO externalDAO; 
	
	@Override
	public Map<String, Object> registerContact(Map<String, Object> paramMap) {
		try{
			externalDAO.registerContact(paramMap);
			paramMap.put("success", "true");
			paramMap.put("data", null);
		}catch(Exception e){
			paramMap.put("success", "false");
			paramMap.put("data", null);
		}
		return paramMap;
	}

	@Override
	public Map<String, Object> removeContact(Map<String, Object> paramMap) {
		try{
			externalDAO.removeContact(paramMap);
			paramMap.put("success", "true");
			paramMap.put("data", null);
		}catch(Exception e){
			paramMap.put("success", "false");
			paramMap.put("data", null);
		}
		return paramMap;
	}

	@Override
	public Map<String, Object> selectContact(Map<String, Object> paramMap) {
		try{
			ArrayList<ContactInfoVO> data= externalDAO.selectContactList(paramMap);
			paramMap.put("success", "true");
			paramMap.put("data", data);
		}catch(Exception e){
			paramMap.put("success", "false");
			paramMap.put("data", null);
		}
		return paramMap;
	}
	
	// 연결 테스트
	
	// 연락처 목록 가져오기
	// 연락처 등록
	// 연락처 삭제
	
}
