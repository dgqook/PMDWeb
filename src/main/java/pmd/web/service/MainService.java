package pmd.web.service;

import java.util.Map;

public interface MainService {
	/**
	 * 로그인
	 * @param map
	 * @return
	 */
	Map<String,Object> doLoginFunction(Map<String,Object> map);
	
	/**
	 * 자동 로그인
	 * @param map
	 * @return
	 */
	Map<String,Object> doAutoLoginFunction(Map<String,Object> map);
	
	/**
	 * 아이디 비밀번호 찾기
	 * @param map
	 * @return
	 */
	Map<String,Object> doFindAccount(Map<String,Object> map);
	
	/**
	 * 비밀번호 찾기 : 인증코드 조회
	 * @param map
	 * @return
	 */
	Map<String,Object> doAuthorization(Map<String,Object> map);
	
	/**
	 * 비밀번호 변경
	 * @param map
	 * @return
	 */
	void doChangePassword(Map<String,Object> map);
	
	/**
	 * 회원 등록
	 * @param map
	 * @return
	 */
	void doCreateAccount(Map<String,Object> map);
	
	/**
	 * 회원 등록 : 아이디 체크
	 * @param map
	 * @return
	 */
	Map<String,Object> doIdCheck(Map<String,Object> map);
	
	/**
	 * 회원 등록 : 이메일 체크
	 * @param map
	 * @return
	 */
	Map<String,Object> doEmailCheck(Map<String,Object> map);

	/**
	 * 정보 수정 : 비밀번호 체크
	 * @param map
	 * @return
	 */
	Map<String, Object> doPwCheck(Map<String, Object> paramMap);

	/**
	 * 정보 수정
	 * @param map
	 * @return
	 */
	void doModUserInfo(Map<String, Object> joinMap);
}