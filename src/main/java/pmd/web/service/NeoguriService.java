package pmd.web.service;

/**
 * 너구리(가칭) 앱에서 필요한 서비스 제공 인터페이스
 * @author Hankook
 *
 */
public interface NeoguriService {
	/**
	 * 사용자 일정 정보를 JSONArray String 형식으로 반환
	 * @param userId
	 * @return
	 */
	public String selectTasks(String userId);
	
	/**
	 * 일정 정보 추가
	 * @param userId
	 * @param taskName
	 * @param taskExpl
	 * @param setAlarm
	 * @param remindAlarm
	 * @param ontimeAlarm
	 */
	public void insertTask(String userId, String taskName, String taskExpl, String taskDttm, String setAlarm, String remindAlarm, String ontimeAlarm);
	
	/**
	 * 일정 정보 삭제
	 * @param userId
	 * @param taskName
	 * @param taskDttm
	 */
	public void deleteTask(String userId, String taskName, String taskDttm);
	
	/**
	 * 사용자에 속한 일정 정보 전부 삭제
	 * @param userId
	 */
	public void deleteTasks(String userId);
	
	/**
	 * 일정 정보 수정
	 * @param userId
	 * @param taskName
	 * @param taskExpl
	 * @param setAlarm
	 * @param remindAlarm
	 * @param ontimeAlarm
	 */
	public void modifyTask(String userId, String taskName, String taskExpl, String taskDttm, String setAlarm, String remindAlarm, String ontimeAlarm);
	
	/**
	 * 사용자 단일 조회, 로그인 등에 사용 (암호화된 비밀번호)
	 * JSONArray String 형식으로 반환됨
	 * @param userId
	 * @param userPw
	 * @return
	 */
	public String selectUser(String userId, String userPw);
	
	/**
	 * 사용자 전체 조회(아이디만), 아이디 중복체크 등에 사용 
	 * JSONArray String 형식으로 반환됨
	 * @return
	 */
	public String selectUsers();
	
	/**
	 * 사용자 추가 (회원가입)
	 * @param userId
	 * @param userPw
	 * @param userName
	 */
	public void insertUser(String userId, String userPw, String userName);
	
	/**
	 * 사용자 삭제 (탈퇴)
	 * @param userId
	 * @param userPw
	 */
	public void deleteUser(String userId, String userPw);
	
	/**
	 * 사용자 수정 (비밀번호 인증 필요)
	 * @param userId
	 * @param userPw
	 * @param userName
	 */
	public void modifyUser(String userId, String userPw, String userName);
	
}