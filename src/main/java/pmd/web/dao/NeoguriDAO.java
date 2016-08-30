package pmd.web.dao;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Repository;

import pmd.common.dao.AbstractDAO;
import pmd.common.vo.NeoguriVO;

@Repository("neoguriDAO")
public class NeoguriDAO extends AbstractDAO{

	@SuppressWarnings("unchecked")
	public ArrayList<NeoguriVO> selectTasks(Map<String, Object> paramMap) {
		return (ArrayList<NeoguriVO>)selectList("neoguri.selectTasks", paramMap);
	}

	public void insertTask(Map<String, Object> paramMap) {
		insert("neoguri.insertTask", paramMap);
	}

	public void deleteTask(Map<String, Object> paramMap) {
		delete("neoguri.deleteTask", paramMap);
	}

	public void deleteTasks(Map<String, Object> paramMap) {
		delete("neoguri.deleteTasks", paramMap);
	}

	public void modifyTask(Map<String, Object> paramMap) {
		update("neoguri.modifyTask", paramMap);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<NeoguriVO> selectUser(Map<String, Object> paramMap) {
		return (ArrayList<NeoguriVO>)selectList("neoguri.selectUser", paramMap);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<NeoguriVO> selectUsers() {
		return (ArrayList<NeoguriVO>)selectList("neoguri.selectUsers");
	}

	public void insertUser(Map<String, Object> paramMap) {
		insert("neoguri.insertUser", paramMap);
	}

	public void deleteUser(Map<String, Object> paramMap) {
		delete("neoguri.deleteUser", paramMap);
	}

	public void modifyUser(Map<String, Object> paramMap) {
		update("neoguri.modifyUser", paramMap);
	}
}
