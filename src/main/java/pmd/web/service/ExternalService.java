package pmd.web.service;

import java.util.ArrayList;
import java.util.Map;

import pmd.common.vo.ContactInfoVO;

public interface ExternalService {

	
	// 연락처 등록
	Map<String, Object> registerContact(Map<String, Object> paramMap);
	
	// 연락처 삭제
	Map<String, Object> removeContact(Map<String, Object> paramMap);

	// 반환용 연락처 목록 가져오기
	Map<String, Object> selectContact(Map<String, Object> paramMap);
	
	// 중복체크용 연락처 목록 가져오기
	ArrayList<ContactInfoVO> getContactList(Map<String, Object> paramMap);

	// 연락처 전부 삭제
	Map<String, Object> removeAllContact(Map<String, Object> paramMap);
	
	
}
