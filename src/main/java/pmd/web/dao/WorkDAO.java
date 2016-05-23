package pmd.web.dao;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Repository;

import pmd.common.dao.AbstractDAO;
import pmd.common.vo.ExpiryManageVO;
import pmd.common.vo.SoftwareInfoVO;
import pmd.common.vo.UserInfoVO;
import pmd.common.vo.WorkDataVO;
 
@Repository("workDAO")
public class WorkDAO extends AbstractDAO{

	/**
	 * 필터 검색 결과 반환
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<WorkDataVO> getSearchResult(Map<String, Object> paramMap) {
		return (ArrayList<WorkDataVO>)selectList("work.getSearchResult",paramMap);
	}
	
	/**
	 * 필터 검색 결과 반환 - 업체명
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<WorkDataVO> getSearchResultByCompany(Map<String, Object> paramMap) {
		return (ArrayList<WorkDataVO>)selectList("work.getSearchResultByCompany",paramMap);
	}
	
	/**
	 * 필터 검색 결과 반환 - 대표자
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<WorkDataVO> getSearchResultByOwner(Map<String, Object> paramMap) {
		return (ArrayList<WorkDataVO>)selectList("work.getSearchResultByOwner",paramMap);
	}

	/**
	 * 엑셀 데이터 업로드
	 * @param paramMap
	 */
	public void insertExcelData(Map<String, Object> paramMap) {
		insert("work.insertExcelData", paramMap);
	}

	/**
	 * 사용자 리스트 조회 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<UserInfoVO> getUserList() {
		return (ArrayList<UserInfoVO>)selectList("work.getUserList");
	}
	
	/**
	 * 사용자 리스트 조회 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<UserInfoVO> getSearchUserList(Map<String, Object> paramMap) {
		return (ArrayList<UserInfoVO>)selectList("work.getSearchUserList",paramMap);
	}

	/**
	 * 최근 설치된 소프트웨어 목록 조회
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SoftwareInfoVO> getRecentInstalledSw(Map<String, Object> paramMap) {
		return (ArrayList<SoftwareInfoVO>)selectList("work.getRecentInstalledSw",paramMap);
	}

	/**
	 * 무료 소프트웨어 목록 조회
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SoftwareInfoVO> getFreeSoftwareList(Map<String, Object> paramMap) {
		return (ArrayList<SoftwareInfoVO>)selectList("work.getFreeSoftwareList",paramMap);
	}

	/**
	 * 무료 소프트웨어 추가
	 * @param paramMap
	 */
	public void addFreeSoftware(Map<String, Object> paramMap) {
		insert("work.addFreeSoftware",paramMap);
	}

	/**
	 * 유료 소프트웨어 추가
	 * @param paramMap
	 */
	public void addChargedSoftware(Map<String, Object> paramMap) {
		insert("work.addChargedSoftware",paramMap);
	}

	/**
	 * 전체 소유목록 조회
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<ExpiryManageVO> getOwnedSoftware(Map<String, Object> paramMap) {
		return (ArrayList<ExpiryManageVO>)selectList("work.getOwnedSoftware",paramMap);
	}

	/**
	 * 직원 계정 전환
	 * @param paramMap
	 */
	public void setMsoftAccount(Map<String, Object> paramMap) {
		update("work.setMsoftAccount",paramMap);
	}

	/**
	 * 사용자들의 코디 이용기간 조회
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<UserInfoVO> getUserExpList() {
		return (ArrayList<UserInfoVO>)selectList("work.getUserExpList");
	}

	/**
	 * 코디 이용기간 연장
	 * @param paramMap
	 */
	public void setCoordyExpiryDate(Map<String, Object> paramMap) {
		update("work.setCoordyExpiryDate",paramMap);
	}

	/**
	 * 연장 기록 작성
	 * @param paramMap
	 */
	public void insertCoordyExpHist(Map<String, Object> paramMap) {
		insert("work.insertCoordyExpHist",paramMap);
	}

	
	
	
	
}