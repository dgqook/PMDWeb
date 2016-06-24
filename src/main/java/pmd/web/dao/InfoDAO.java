package pmd.web.dao;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Repository;

import pmd.common.dao.AbstractDAO;
import pmd.common.vo.SoftwareInfoVO;
 
@Repository("infoDAO")
public class InfoDAO extends AbstractDAO{
	
	/**
	 * 보유 소프트웨어 목록 조회
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  ArrayList<SoftwareInfoVO> getOwnedSoftware( Map<String, Object> map) {
		return ( ArrayList<SoftwareInfoVO>)selectList("info.getOwnedSoftware", map);
	}
	
	/**
	 * 등록된 PC 및 설치된 프로그램들 조회
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  ArrayList<SoftwareInfoVO> getInstalledSoftware( Map<String, Object> map) {
		return (ArrayList<SoftwareInfoVO>)selectList("info.getInstalledSoftware", map);
	}
	/**
	 * 등록된 PC 및 설치된 프로그램들 조회
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  ArrayList<SoftwareInfoVO> getInstalledSoftwareWithPcName( Map<String, Object> map) {
		return (ArrayList<SoftwareInfoVO>)selectList("info.getInstalledSoftwareWithPcName", map);
	}
	
	/**
	 * 등록된 PC 및 설치된 프로그램들 조회
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  ArrayList<SoftwareInfoVO> getChargedSoftware( Map<String, Object> map) {
		return (ArrayList<SoftwareInfoVO>)selectList("info.getChargedSoftware", map);
	}
	
	/**
	 * 특정 사용자의 PC들에 깔려있는 소프트웨어 목록 조회
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  ArrayList<SoftwareInfoVO> getUserPcList( Map<String, Object> map) {
		return (ArrayList<SoftwareInfoVO>)selectList("info.getUserPcList", map);
	} 
	/**
	 * 특정 사용자의 특정 PC에 깔려있는 소프트웨어 목록 조회
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked") 
	public  ArrayList<SoftwareInfoVO> getUserPcListByPk( Map<String, Object> map) {
		return (ArrayList<SoftwareInfoVO>)selectList("info.getUserPcListByPk", map);
	}
	
	/**
	 * pc 소프트웨어 목록 업로드 by pmd client
	 * @param map
	 * @return
	 */
	public  void updateUserPcSwList( Map<String, Object> map) {
		insert("info.updateUserPcSwList", map);
	}
	
	public void deleteUserPcSwList(Map<String, Object> map) {
		delete("info.deleteUserPcSwList", map);
	}

	/**
	 * 소프트웨어 등록 > 검색 결과 반환
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SoftwareInfoVO> getChargedSoftwareByPk(Map<String, Object> map) {
		return (ArrayList<SoftwareInfoVO>)selectList("info.getChargedSoftwareByPk", map);
	}

	/**
	 * 소프트웨어 등록 > 등록
	 * @param paramMap
	 */
	public void doRegisterSoftware(Map<String, Object> paramMap) {
		insert("info.doRegisterSoftware", paramMap);
	}

	/**
	 * 소프트웨어 등록 > 수량 추가
	 * @param paramMap
	 */
	public void doUpdateSoftware(Map<String, Object> paramMap) {
		update("info.doUpdateSoftware", paramMap);
	}

	/**
	 * 소프트웨어 관리 > 소프트웨어 정보 삭제
	 * @param paramMap
	 */
	public void doDeleteSoftware(Map<String, Object> paramMap) {
		delete("info.doDeleteSoftware", paramMap);
	}

	/**
	 * 소프트웨어 관리 > 보유 소프트웨어 정보 수정 > 정보 불러오기
	 * @param paramMap
	 */
	public SoftwareInfoVO getOwnSoftwareInfo(Map<String, Object> paramMap) {
		return (SoftwareInfoVO)selectOne("info.getOwnSoftwareInfo", paramMap);
	}

	
}