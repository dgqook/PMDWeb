package pmd.web.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import pmd.common.dao.AbstractDAO;
 
@Repository("mainDAO")
public class MainDAO extends AbstractDAO{
	
	/**
	 * 로그인 수행
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  Map<String, Object> doLoginFunction( Map<String, Object> map) {
		return ( Map<String, Object>)selectOne("main.doLoginFunction", map);
	}
	
	/**
	 * 자동 로그인 수행
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  Map<String, Object> doAutoLoginFunction( Map<String, Object> map) {
		return ( Map<String, Object>)selectOne("main.doAutoLoginFunction", map);
	}
	
	/**
	 * 아이디 비밀번호 찾기 수행
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  Map<String, Object> doFindAccount( Map<String, Object> map) {
		return ( Map<String, Object>)selectOne("main.doFindAccount", map);
	}
	
	/**
	 * 인증코드 등록
	 * @param map
	 * @return
	 */
	public  void doIssueAuthCode( Map<String, Object> map) {
		insert("main.doIssueAuthCode", map);
	}
	
	/**
	 * 인증코드 조회
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  Map<String, Object> doGetAuthCode( Map<String, Object> map) {
		return ( Map<String, Object>)selectOne("main.doGetAuthCode", map);
	}
	
	/**
	 * 비밀번호 변경 수행
	 * @param map
	 * @return
	 */
	public  void doChangePassword( Map<String, Object> map) {
		update("main.doChangePassword", map);
	}
	
	/**
	 * 회원 등록
	 * @param map
	 * @return
	 */
	public  void doCreateAccount( Map<String, Object> map) {
		insert("main.doCreateAccount", map);
	}
	
	/**
	 * 회원 등록 : 아이디 체크
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  Map<String, Object> doIdCheck( Map<String, Object> map) {
		return ( Map<String, Object>)selectOne("main.doIdCheck", map);
	}
	
	/**
	 * 회원 등록 : 이메일 체크
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  Map<String, Object> doEmailCheck( Map<String, Object> map) {
		return ( Map<String, Object>)selectOne("main.doEmailCheck", map);
	}

	/**
	 * 정보 수정 : 비밀번호 체크
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> doPwCheck(Map<String, Object> paramMap) {
		return ( Map<String, Object>)selectOne("main.doPwCheck", paramMap);
	}

	/**
	 * 정보 수정
	 * @param map
	 * @return
	 */
	public void doModUserInfo(Map<String, Object> joinMap) {
		update("main.doModUserInfo", joinMap);
	}
}