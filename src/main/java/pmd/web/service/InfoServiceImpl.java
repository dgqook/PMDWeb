package pmd.web.service;

import java.util.ArrayList;
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
}