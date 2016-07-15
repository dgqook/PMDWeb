package pmd.web.service;

import java.util.Map;

public interface ExternalService {

	
	// 연락처 등록
	Map<String, Object> registerContact(Map<String, Object> paramMap);
	// 연락처 목록 가져오기

	Map<String, Object> removeContact(Map<String, Object> paramMap);

	Map<String, Object> selectContact(Map<String, Object> paramMap);
	
	// 연락처 삭제
}
