package pmd.web.service;

import java.util.ArrayList;
import java.util.Map;

import pmd.common.vo.ExpiryManageVO;
import pmd.common.vo.SoftwareInfoVO;
import pmd.common.vo.UserInfoVO;
import pmd.common.vo.WorkDataVO;

public interface WorkService {
	/**
	 * 보유 프로그램 목록 조회
	 * @param map
	 * @return
	 */
	ArrayList<WorkDataVO> getSearchResult(Map<String, Object> paramMap);

	/**
	 * 엑셀 데이터 업데이트
	 * @param paramMap
	 */
	void insertExcelData(Map<String, Object> paramMap);

	/**
	 * 전체 사용자 목록 조회
	 * @param paramMap
	 * @return
	 */
	ArrayList<UserInfoVO> getUserList();
	
	/**
	 * 검색 사용자 목록 조회
	 * @param paramMap
	 * @return
	 */
	ArrayList<UserInfoVO> getSearchUserList(Map<String, Object> paramMap);

	/**
	 * 최근 설치된 소프트웨어 목록 조회
	 * @param paramMap
	 * @return
	 */
	ArrayList<SoftwareInfoVO> getRecentInstalledSw(Map<String, Object> paramMap);

	/**
	 * 무료 소프트웨어 목록 조회
	 * @param paramMap
	 * @return
	 */
	ArrayList<SoftwareInfoVO> getFreeSoftwareList(Map<String, Object> paramMap);

	/**
	 * 무료 소프트웨어 추가
	 * @param paramMap
	 */
	void addFreeSoftware(Map<String, Object> paramMap);

	/**
	 * 유료 소프트웨어 추가
	 * @param paramMap
	 */
	void addChargedSoftware(Map<String, Object> paramMap);

	/**
	 * 전체 소유목록 조회
	 * @param paramMap
	 * @return
	 */
	ArrayList<ExpiryManageVO> getOwnedSoftware(Map<String, Object> paramMap);

	/**
	 * 직원 계정 전환
	 * @param paramMap
	 */
	void setMsoftAccount(Map<String, Object> paramMap);

	/**
	 * 사용자들의 코디 이용기간 조회
	 * @param paramMap
	 * @return
	 */
	ArrayList<UserInfoVO> getUserExpList();

	/**
	 * 코디 이용기간 연장
	 * @param paramMap
	 */
	void setCoordyExpiryDate(Map<String, Object> paramMap);

	/**
	 * 연장 기록 작성
	 * @param paramMap
	 */
	void insertCoordyExpHist(Map<String, Object> paramMap);
}