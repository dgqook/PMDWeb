package pmd.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pmd.common.vo.SoftwareInfoVO;

public interface InfoService {
	/**
	 * 보유 프로그램 목록 조회
	 * @param map
	 * @return
	 */
	ArrayList<SoftwareInfoVO> getOwnedSoftware(Map<String,Object> map);
	
	/**
	 * 설치된 프로그램 목록 조회
	 * @param map
	 * @return
	 */
	ArrayList<SoftwareInfoVO> getInstalledSoftware(Map<String,Object> map);
	
	/**
	 * 설치된 프로그램 목록 조회
	 * @param map
	 * @return
	 */
	ArrayList<SoftwareInfoVO> getInstalledSoftwareWithPcName(Map<String,Object> map);
	
	/**
	 * 유료 프로그램 목록 조회
	 * @param map
	 * @return
	 */
	ArrayList<SoftwareInfoVO> getChargedSoftware(Map<String,Object> map);
	
	/**
	 * 특정 사용자의 PC들에 깔려있는 소프트웨어 조회
	 * @param map
	 * @return
	 */
	ArrayList<SoftwareInfoVO> getUserPcList(Map<String,Object> map);
	
	/**
	 * 특정 사용자의 특정 PC에 깔려있는 소프트웨어 조회
	 * @param map
	 * @return
	 */
	ArrayList<SoftwareInfoVO> getUserPcListByPk(Map<String,Object> map);
	
	/**
	 * pc 소프트웨어 목록 업로드 by pmd client
	 * @param map
	 * @return
	 */
	void updateUserPcSwList(Map<String,Object> map);
	
	/**
	 * pc 소프트웨어 목록 갱신 by pmd client
	 * @param paramMap
	 */
	void modifyUserPcSwList(Map<String, Object> paramMap);

	/**
	 * 소프트웨어 등록 > 검색 결과 반환
	 * @param map
	 * @return
	 */
	ArrayList<SoftwareInfoVO> getChargedSoftwareByPk(Map<String, Object> map);

	/**
	 * 보유 소프트웨어 등록
	 * @param paramMap
	 */
	void doRegisterSoftware(Map<String, Object> paramMap);
	
	/**
	 * 보유 소프트웨어 등록 1
	 * @param paramMap
	 */
	void doRegisterASoftware(Map<String, Object> paramMap);

	/**
	 * 보유 소프트웨어 수량 추가
	 * @param paramMap
	 */
	void doUpdateSoftware(Map<String, Object> paramMap);

	/**
	 * 보유 소프트웨어 정보 삭제
	 * @param paramMap
	 */
	void doDeleteSoftware(Map<String, Object> paramMap);

	void deleteUserPcSwList(Map<String, Object> map);

	
	/**
	 * 수정할 보유 소프트웨어 정보를 가져온다
	 * @param paramMap
	 * @return
	 */
	SoftwareInfoVO getOwnSoftwareInfo(Map<String, Object> paramMap);

	/**
	 * 보유 소프트웨어 수량 수정
	 * @param paramMap
	 * @return
	 */
	void doModifyQuantity(Map<String, Object> paramMap);

	
	void setLegalSoftware(String userId, String sL_Code, String sL_PcName, String sL_SwName, String sL_SwFile);

	/**
	 * 정품 소프트웨어의 개수를 반환한다.
	 * @param sL_SwName
	 * @return
	 */
	int getNumberOfLegal(String userId, String sL_SwName);

	/**
	 * 체크된 정품 소프트웨어의 개수를 반환한다.
	 * @param sL_SwName
	 * @return
	 */
	int getNumberOfChecked(String userId, String sL_SwName, String sL_SwFile);

	/**
	 * 계정 - 소프트웨어 - 미등록개수 정보가 등록된 관리목록 불러오기
	 * @param paramMap
	 * @return
	 */
	ArrayList<SoftwareInfoVO> getManageList(Map<String, Object> paramMap);


	void doDeleteASoftware(Map<String, Object> paramMap);

	String getUserEmailAddress(HashMap<String, Object> paramMap);

	
	
	
}