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
	
	// 연락처 등록
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

	// 연락처 삭제
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

	// 반환용 연락처 목록 가져오기
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

	// 중복체크용 연락처 목록 가져오기
	@Override
	public ArrayList<ContactInfoVO> getContactList(Map<String, Object> paramMap) {
		return (ArrayList<ContactInfoVO>) externalDAO.selectContactList(paramMap);
	}
}
