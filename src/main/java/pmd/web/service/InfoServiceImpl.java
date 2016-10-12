package pmd.web.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import pmd.common.vo.SoftwareInfoVO;
import pmd.web.dao.InfoDAO;
 
@Service("infoService")
public class InfoServiceImpl implements InfoService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="infoDAO")
	private InfoDAO infoDAO;

	/**
	 * 보유 소프트웨어 조회
	 */
	@Override
	public ArrayList<SoftwareInfoVO> getOwnedSoftware(Map<String, Object> map) {
		
		ArrayList<SoftwareInfoVO> resultList= infoDAO.getOwnedSoftware(map);
		return	resultList;
	}

	/**
	 * 설치된 소프트웨어 조회
	 */
	@Override
	public ArrayList<SoftwareInfoVO> getInstalledSoftware(Map<String, Object> map) {
		ArrayList<SoftwareInfoVO> resultList= infoDAO.getInstalledSoftware(map);
		return	resultList;
	}
	/**
	 * 설치된 소프트웨어 조회
	 */
	@Override
	public ArrayList<SoftwareInfoVO> getInstalledSoftwareWithPcName(Map<String, Object> map) {
		ArrayList<SoftwareInfoVO> resultList= infoDAO.getInstalledSoftwareWithPcName(map);
		return	resultList;
	}

	/**
	 * 유료 소프트웨어 목록 조회
	 */
	@Override
	public ArrayList<SoftwareInfoVO> getChargedSoftware(Map<String, Object> map) {
		ArrayList<SoftwareInfoVO> resultList= infoDAO.getChargedSoftware(map);
		return	resultList;
	}

	/**
	 * 특정 사용자의 PC들에 깔려있는 소프트웨어 조회
	 * @param map
	 * @return
	 */
	@Override
	public ArrayList<SoftwareInfoVO> getUserPcList(Map<String, Object> map) {
		ArrayList<SoftwareInfoVO> resultList= infoDAO.getUserPcList(map); 
		return resultList;
	}

	/**
	 * 특정 사용자의 특정 PC에 깔려있는 소프트웨어 조회
	 * @param map
	 * @return
	 */
	@Override
	public ArrayList<SoftwareInfoVO> getUserPcListByPk(Map<String, Object> map) {
		ArrayList<SoftwareInfoVO> resultList= infoDAO.getUserPcListByPk(map); 
		return resultList;
	}

	/**
	 * pc 소프트웨어 목록 업로드 by pmd client
	 * @param map
	 * @return
	 */
	@Override
	public void updateUserPcSwList(Map<String, Object> map) {
		infoDAO.updateUserPcSwList(map);
	}
	
	/**
	 * pc 소프트웨어 목록 갱신 by pmd client
	 */
	@Override
	public void modifyUserPcSwList(Map<String, Object> map) {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String nowDate= sdf.format(new Date());
		map.put("nowDate", nowDate);
		infoDAO.modifyUserPcSwList(map);
	}
	
	/**
	 * pc 소프트웨어 목록 업로드 (제거된 프로그램 DB에서 삭제) by pmd client
	 * @param map
	 * @return
	 */
	@Override
	public void deleteUserPcSwList(Map<String, Object> map) {
		infoDAO.deleteUserPcSwList(map);
	}

	/**
	 * 특정 키워드 정보를 갖는 유료 소프트웨어 목록 조회
	 */
	@Override
	public ArrayList<SoftwareInfoVO> getChargedSoftwareByPk(Map<String, Object> map) {
		ArrayList<SoftwareInfoVO> resultList= infoDAO.getChargedSoftwareByPk(map); 
		return resultList;
	}

	/**
	 * 보유 소프트웨어 등록
	 */
	@Override
	public void doRegisterSoftware(Map<String, Object> paramMap) {
		infoDAO.doRegisterSoftware(paramMap);
	}
	
	/**
	 * 보유 소프트웨어 등록 1
	 */
	@Override
	public void doRegisterASoftware(Map<String, Object> paramMap) {
		infoDAO.doRegisterASoftware(paramMap);
	}

	/**
	 * 보유 소프트웨어 수량 추가
	 */
	@Override
	public void doUpdateSoftware(Map<String, Object> paramMap) {
		infoDAO.doUpdateSoftware(paramMap);
	}

	/**
	 * 보유 소프트웨어 정보 삭제
	 */
	@Override 
	public void doDeleteSoftware(Map<String, Object> paramMap) {
		infoDAO.doDeleteSoftware(paramMap);
	}
	
	/**
	 * 보유 소프트웨어 정보 삭제1
	 */
	@Override 
	public void doDeleteASoftware(Map<String, Object> paramMap) {
		infoDAO.doDeleteASoftware(paramMap);
	}

	/**
	 * 수정할 보유 소프트웨어 정보를 가져온다.
	 */
	@Override
	public SoftwareInfoVO getOwnSoftwareInfo(Map<String, Object> paramMap) {
		
		return infoDAO.getOwnSoftwareInfo(paramMap);
	}

	/**
	 * 보유 소프트웨어 수량을 수정한다.
	 */
	@Override
	public void doModifyQuantity(Map<String, Object> paramMap) {
		infoDAO.doModifyQuantity(paramMap);
	}

	/**
	 * 해당 제품이 정품인지 아닌지 체크
	 * @param sL_Code
	 * @param sL_PcName 
	 * @param sL_SwName
	 */
	@Override
	public void setLegalSoftware(String userId, String sL_Code, String sL_PcName, String sL_SwName, String sL_SwFile) {
		infoDAO.setLegalSoftware(userId, sL_Code, sL_PcName, sL_SwName, sL_SwFile);
	}

	/**
	 *  해당 정품의 개수를 반환한다.
	 */
	@Override
	public int getNumberOfLegal(String userId, String sL_SwName) {
		Map<String, Object> paramMap= new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("swName", sL_SwName);
		return infoDAO.getNumberOfLegal(paramMap);
	}

	/**
	 *  해당 정품의 체크된 숫자를 반환한다.
	 */
	@Override
	public int getNumberOfChecked(String userId, String sL_SwName, String sL_SwFile) {
		Map<String, Object> paramMap= new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("swName", sL_SwName);
		paramMap.put("swFile", sL_SwFile);
		return infoDAO.getNumberOfChecked(paramMap);
	}

	/**
	 * 계정 - 소프트웨어 - 미등록 개수 정보를 가진 관리목록을 가져온다.
	 */
	@Override
	public ArrayList<SoftwareInfoVO> getManageList(Map<String, Object> paramMap) {
		return infoDAO.getManageList(paramMap);
	}

	@Override
	public String getUserEmailAddress(HashMap<String, Object> paramMap) {
		return infoDAO.getUserEmailAddress(paramMap);
	}

	
}