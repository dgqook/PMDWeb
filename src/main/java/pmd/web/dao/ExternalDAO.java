package pmd.web.dao;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Repository;

import pmd.common.dao.AbstractDAO;
import pmd.common.vo.ContactInfoVO;
import pmd.common.vo.ExContactInfoVO;

@Repository("externalDAO")
public class ExternalDAO extends AbstractDAO{

	public  void registerContact( Map<String, Object> map) {
		insert("external.registerContact", map);
	}
	
	public  void removeContact( Map<String, Object> map) {
		delete("external.removeContact", map);
	}
	
	public ExContactInfoVO selectContactList(Map<String, Object> map) {
		return (ExContactInfoVO)selectOne("external.selectContactList", map);
	}

}
