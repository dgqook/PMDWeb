package pmd.web.service;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pmd.common.vo.ContactInfoVO;
import pmd.common.vo.ExContactInfoVO;
import pmd.web.dao.ExternalDAO;

@Service("externalService")
public class ExternalServiceImpl implements ExternalService{

	@Resource(name="externalDAO")
	private ExternalDAO externalDAO; 
	
	// 연락처 등록
	@Override
	public Map<String, Object> registerContact(Map<String, Object> paramMap) {
		try{
			externalDAO.registerContact(paramMap);
			
			// 중복
			@SuppressWarnings("unchecked")
			ArrayList<ContactInfoVO> contactList= (ArrayList<ContactInfoVO>) paramMap.get("contactList");
			
			switch(contactList.size()){
			case 1:	// 기존: 0
				paramMap.put("unum",contactList.get(0).getUnum());
				paramMap.put("pkey",contactList.get(0).getPkey());
				
				removeAllContact(paramMap);
				
				paramMap.put("name1",contactList.get(0).getName());
				paramMap.put("pnum1",contactList.get(0).getPnum());
				
				paramMap.put("success", "true");
				paramMap.put("data", null);
				paramMap.put("message", "등록되었습니다.");
				break;
				
			case 2:	// 기존: 1
				paramMap.put("unum",contactList.get(0).getUnum());
				paramMap.put("pkey",contactList.get(0).getPkey());
				
				removeAllContact(paramMap);
				
				paramMap.put("name1",contactList.get(0).getName());
				paramMap.put("pnum1",contactList.get(0).getPnum());
				paramMap.put("name2",contactList.get(1).getName());
				paramMap.put("pnum2",contactList.get(1).getPnum());
				
				paramMap.put("success", "true");
				paramMap.put("data", null);
				paramMap.put("message", "등록되었습니다.");
				break;
				
			case 3:	// 기존: 2
				paramMap.put("unum",contactList.get(0).getUnum());
				paramMap.put("pkey",contactList.get(0).getPkey());
				
				removeAllContact(paramMap);
				
				paramMap.put("name1",contactList.get(0).getName());
				paramMap.put("pnum1",contactList.get(0).getPnum());
				paramMap.put("name2",contactList.get(1).getName());
				paramMap.put("pnum2",contactList.get(1).getPnum());
				paramMap.put("name3",contactList.get(2).getName());
				paramMap.put("pnum3",contactList.get(2).getPnum());
				
				paramMap.put("success", "true");
				paramMap.put("data", null);
				paramMap.put("message", "등록되었습니다.");
				break;
				
			case 4:	// 기존: 3
				paramMap.put("unum",contactList.get(0).getUnum());
				paramMap.put("pkey",contactList.get(0).getPkey());
				
				removeAllContact(paramMap);
				
				paramMap.put("name1",contactList.get(0).getName());
				paramMap.put("pnum1",contactList.get(0).getPnum());
				paramMap.put("name2",contactList.get(1).getName());
				paramMap.put("pnum2",contactList.get(1).getPnum());
				paramMap.put("name3",contactList.get(2).getName());
				paramMap.put("pnum3",contactList.get(2).getPnum());
				paramMap.put("name4",contactList.get(3).getName());
				paramMap.put("pnum4",contactList.get(3).getPnum());
				
				paramMap.put("success", "true");
				paramMap.put("data", null);
				paramMap.put("message", "등록되었습니다.");
				break;
				
			case 5:	// 기존: 4
				paramMap.put("unum",contactList.get(0).getUnum());
				paramMap.put("pkey",contactList.get(0).getPkey());
				
				removeAllContact(paramMap);
				
				paramMap.put("name1",contactList.get(0).getName());
				paramMap.put("pnum1",contactList.get(0).getPnum());
				paramMap.put("name2",contactList.get(1).getName());
				paramMap.put("pnum2",contactList.get(1).getPnum());
				paramMap.put("name3",contactList.get(2).getName());
				paramMap.put("pnum3",contactList.get(2).getPnum());
				paramMap.put("name4",contactList.get(3).getName());
				paramMap.put("pnum4",contactList.get(3).getPnum());
				paramMap.put("name5",contactList.get(4).getName());
				paramMap.put("pnum5",contactList.get(4).getPnum());
				
				paramMap.put("success", "true");
				paramMap.put("data", null);
				paramMap.put("message", "등록되었습니다.");
				break;
				
			case 6:	// 기존: 5
				paramMap.put("success", "true");
				paramMap.put("data", null);
				paramMap.put("message", "연락처는 5개 까지 저장 가능합니다.");
				break;
				
			default:
				paramMap.put("message", "비정상적인 접근입니다.");
				paramMap.put("success", "false");
				paramMap.put("data", null);
				break;
					
			}
			
		}catch(Exception e){
			paramMap.put("message", "오류가 발생했습니다..");
			paramMap.put("success", "false");
			paramMap.put("data", null);
		}
		return paramMap;
	}

	// 연락처 삭제
		@Override
		public Map<String, Object> removeContact(Map<String, Object> paramMap) {
			try{
				@SuppressWarnings("unchecked")
				ArrayList<ContactInfoVO> contactList= (ArrayList<ContactInfoVO>) paramMap.get("contactList");
				ContactInfoVO target= new ContactInfoVO((String)paramMap.get("unum"), (String)paramMap.get("pkey"), (String)paramMap.get("name"), (String)paramMap.get("pnum"));
				for(int i=0; i<contactList.size(); i++){
					if(contactList.get(i).getName().equals(target.getName()) && contactList.get(i).getUnum().equals(target.getUnum()) 
							&& contactList.get(i).getPkey().equals(target.getPkey()) && contactList.get(i).getPnum().equals(target.getPnum())){
						contactList.remove(i);
						break;
					}
				}
				paramMap.put("contactList", contactList);
				externalDAO.removeContact(paramMap);
				registerContact(paramMap);
				
				paramMap.put("success", "true");
				paramMap.put("data", null);
				paramMap.put("message", "삭제가 완료되었습니다.");
			}catch(Exception e){
				paramMap.put("success", "false");
				paramMap.put("data", null);
				paramMap.put("message", "삭제에 실패했습니다..");
			}
			return paramMap;
		}
		
		// 연락처 삭제
		@Override
		public Map<String, Object> removeAllContact(Map<String, Object> paramMap) {
			try{
				externalDAO.removeContact(paramMap);
				paramMap.put("success", "true");
				paramMap.put("data", null);
			}catch(Exception e){
				paramMap.put("success", "false");
				paramMap.put("data", null);
			}
			return paramMap;
		}

	// 반환용 연락처 목록 가져오기
	@Override
	public Map<String, Object> selectContact(Map<String, Object> paramMap) {
		try{
			ExContactInfoVO data= externalDAO.selectContactList(paramMap);
			ArrayList<ContactInfoVO> result= new ArrayList<ContactInfoVO>();
			
			if(data.getName1()!=null && !data.getName1().equals("")){
				result.add(new ContactInfoVO(data.getName1(), data.getPnum1(), data.getUnum(), data.getPkey()));
			}
			if(data.getName2()!=null && !data.getName2().equals("")){
				result.add(new ContactInfoVO(data.getName2(), data.getPnum2(), data.getUnum(), data.getPkey()));
			}
			if(data.getName3()!=null && !data.getName3().equals("")){
				result.add(new ContactInfoVO(data.getName3(), data.getPnum3(), data.getUnum(), data.getPkey()));
			}
			if(data.getName4()!=null && !data.getName4().equals("")){
				result.add(new ContactInfoVO(data.getName4(), data.getPnum4(), data.getUnum(), data.getPkey()));
			}
			if(data.getName5()!=null && !data.getName5().equals("")){
				result.add(new ContactInfoVO(data.getName5(), data.getPnum5(), data.getUnum(), data.getPkey()));
			}
			
			paramMap.put("success", "true");
			paramMap.put("data", data);
		}catch(Exception e){
			paramMap.put("success", "false");
			paramMap.put("data", null);
		}
		return paramMap;
	}

	// 중복체크용 연락처 목록 가져오기
	@Override
	public ArrayList<ContactInfoVO> getContactList(Map<String, Object> paramMap) {
		
		ExContactInfoVO data= externalDAO.selectContactList(paramMap);
		ArrayList<ContactInfoVO> result= new ArrayList<ContactInfoVO>();
		
		if(data.getName1()!=null && !data.getName1().equals("")){
			result.add(new ContactInfoVO(data.getName1(), data.getPnum1(), data.getUnum(), data.getPkey()));
		}
		if(data.getName2()!=null && !data.getName2().equals("")){
			result.add(new ContactInfoVO(data.getName2(), data.getPnum2(), data.getUnum(), data.getPkey()));
		}
		if(data.getName3()!=null && !data.getName3().equals("")){
			result.add(new ContactInfoVO(data.getName3(), data.getPnum3(), data.getUnum(), data.getPkey()));
		}
		if(data.getName4()!=null && !data.getName4().equals("")){
			result.add(new ContactInfoVO(data.getName4(), data.getPnum4(), data.getUnum(), data.getPkey()));
		}
		if(data.getName5()!=null && !data.getName5().equals("")){
			result.add(new ContactInfoVO(data.getName5(), data.getPnum5(), data.getUnum(), data.getPkey()));
		}
		
		return result;
	}
}
