package pmd.web.service;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import pmd.common.common.PMDUtil;
import pmd.common.vo.ExpiryManageVO;
import pmd.common.vo.SoftwareInfoVO;
import pmd.common.vo.UserInfoVO;
import pmd.common.vo.WorkDataVO;
import pmd.web.dao.WorkDAO;
 
@Service("workService")
public class WorkServiceImpl implements WorkService{
	Logger log = Logger.getLogger(this.getClass());
	PMDUtil pmd= new PMDUtil();
	
	@Resource(name="workDAO")
	private WorkDAO workDAO;

	/**
	 * 보유 소프트웨어 조회
	 */
	@Override
	public ArrayList<WorkDataVO> getSearchResult(Map<String, Object> paramMap) {
		String type= (String)paramMap.get("searchType");
		if(type != null && !type.equals("")){
			if(type.equals("company")){
				pmd.logging("company search");
				return workDAO.getSearchResultByCompany(paramMap);
			}else if(type.equals("owner")){
				pmd.logging("owner search");
				return workDAO.getSearchResultByOwner(paramMap);
			}else{
				pmd.logging("fit search");
				return workDAO.getSearchResultByFit(paramMap);
			}
		}else {
			pmd.logging("no search");
			return workDAO.getSearchResult(paramMap);
		}
	}

	/**
	 * 엑셀 데이터 업데이트
	 */
	@Override
	public void insertExcelData(Map<String, Object> paramMap) {
		workDAO.insertExcelData(paramMap);
	}

	/**
	 * 사용자 목록 조회 
	 */
	@Override
	public ArrayList<UserInfoVO> getUserList() {
		return workDAO.getUserList();
	}
	
	/**
	 * 검색 사용자 목록 조회 
	 */
	@Override
	public ArrayList<UserInfoVO> getSearchUserList(Map<String, Object> paramMap) {
		return workDAO.getSearchUserList(paramMap);
	}

	/**
	 * 최근 설치된 소프트웨어 목록 조회
	 */
	@Override
	public ArrayList<SoftwareInfoVO> getRecentInstalledSw(Map<String, Object> paramMap) {
		return workDAO.getRecentInstalledSw(paramMap);
	}

	/**
	 * 무료 소프트웨어 목록 조회
	 */
	@Override
	public ArrayList<SoftwareInfoVO> getFreeSoftwareList(Map<String, Object> paramMap) {
		return workDAO.getFreeSoftwareList(paramMap);
	}

	/**
	 * 무료 소프트웨어 추가
	 */
	@Override
	public void addFreeSoftware(Map<String, Object> paramMap) {
		workDAO.addFreeSoftware(paramMap);
	}

	/**
	 * 유료 소프트웨어 추가
	 */
	@Override
	public void addChargedSoftware(Map<String, Object> paramMap) {
		workDAO.addChargedSoftware(paramMap);
	}

	/**
	 * 전체 소유목록 조회
	 */
	@Override
	public ArrayList<ExpiryManageVO> getOwnedSoftware(Map<String, Object> paramMap) {
		return workDAO.getOwnedSoftware(paramMap);
	}

	/**
	 * 직원 계정 전환
	 */
	@Override
	public void setMsoftAccount(Map<String, Object> paramMap) {
		workDAO.setMsoftAccount(paramMap);
	}

	/**
	 * 사용자들의 코디 이용기간 조회
	 */
	@Override
	public ArrayList<UserInfoVO> getUserExpList() {
		return workDAO.getUserExpList();
	}

	/**
	 * 코디 이용기간 연장
	 */
	@Override
	public void setCoordyExpiryDate(Map<String, Object> paramMap) {
		workDAO.setCoordyExpiryDate(paramMap);
	}

	/**
	 * 연장 기록 작성
	 */
	@Override
	public void insertCoordyExpHist(Map<String, Object> paramMap) {
		workDAO.insertCoordyExpHist(paramMap);
	}

	
	@Override
	public void addFreeSoftwareList(Map<String, Object> paramMap) {
		workDAO.addFreeSoftwareList(paramMap);
	}

	
	
}