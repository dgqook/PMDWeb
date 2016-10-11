package pmd.web.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pmd.common.common.PMDUtil;
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
			
			
			// 중복
			@SuppressWarnings("unchecked")
			ArrayList<ContactInfoVO> contactList= (ArrayList<ContactInfoVO>) paramMap.get("contactList");
			PMDUtil pmd= new PMDUtil();
			pmd.logging("SERVICE IMPL - messageReq : "+(String)paramMap.get("messageReq"));
			
			switch(contactList.size()){
			case 0:	// 기존: 1 -> 1 삭제
				paramMap.put("unum",contactList.get(0).getUnum());
				paramMap.put("pkey",contactList.get(0).getPkey());
				
				if(contactList.get(0).getPkey().equals("")) contactList.remove(0);
				
				removeAllContact(paramMap);
				
				paramMap.put("messageReq", (String)paramMap.get("messageReq"));
				
				externalDAO.registerContact(paramMap);
				
				paramMap.put("success", "true");
				paramMap.put("data", null);
				paramMap.put("message", "등록되었습니다.");
				break;
				
			case 1:	// 기존: 0
				paramMap.put("unum",contactList.get(0).getUnum());
				paramMap.put("pkey",contactList.get(0).getPkey());
				
				if(contactList.get(0).getPkey().equals("")) contactList.remove(0);
				
				removeAllContact(paramMap);
				
				paramMap.put("name1",contactList.get(0).getName());
				paramMap.put("pnum1",contactList.get(0).getPnum());
				paramMap.put("messageReq", (String)paramMap.get("messageReq"));
				
				externalDAO.registerContact(paramMap);
				
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
				paramMap.put("messageReq", (String)paramMap.get("messageReq"));
				externalDAO.registerContact(paramMap);
				
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
				paramMap.put("messageReq", (String)paramMap.get("messageReq"));
				externalDAO.registerContact(paramMap);
				
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
				paramMap.put("messageReq", (String)paramMap.get("messageReq"));
				externalDAO.registerContact(paramMap);
				
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
				paramMap.put("messageReq", (String)paramMap.get("messageReq"));
				externalDAO.registerContact(paramMap);
				
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
			
			e.printStackTrace();
		}
		return paramMap;
	}

	
	// 연락처 삭제
		@Override
		public Map<String, Object> removeContact(Map<String, Object> paramMap) {
			try{
				@SuppressWarnings("unchecked")
				ArrayList<ContactInfoVO> contactList= (ArrayList<ContactInfoVO>) paramMap.get("contactList");
				String messageReq= contactList.get(0).getMessageReq();
				ContactInfoVO target= new ContactInfoVO((String)paramMap.get("unum"), (String)paramMap.get("pkey"), (String)paramMap.get("name"), (String)paramMap.get("pnum"));
				for(int i=0; i<contactList.size(); i++){
					if(contactList.get(i).getName().equals(target.getName()) && contactList.get(i).getUnum().equals(target.getUnum()) 
							&& contactList.get(i).getPkey().equals(target.getPkey()) && contactList.get(i).getPnum().equals(target.getPnum())){
						contactList.remove(i);
						break;
					}
				}
				paramMap.put("messageReq", messageReq);
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
			PMDUtil pmd= new PMDUtil();
			pmd.logging("[CUSTOM] 전체삭제");
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
				result.add(new ContactInfoVO(data.getName1(), data.getPnum1(), data.getUnum(), data.getPkey(), data.getMessageReq()));
			}
			if(data.getName2()!=null && !data.getName2().equals("")){
				result.add(new ContactInfoVO(data.getName2(), data.getPnum2(), data.getUnum(), data.getPkey(), data.getMessageReq()));
			}
			if(data.getName3()!=null && !data.getName3().equals("")){
				result.add(new ContactInfoVO(data.getName3(), data.getPnum3(), data.getUnum(), data.getPkey(), data.getMessageReq()));
			}
			if(data.getName4()!=null && !data.getName4().equals("")){
				result.add(new ContactInfoVO(data.getName4(), data.getPnum4(), data.getUnum(), data.getPkey(), data.getMessageReq()));
			}
			if(data.getName5()!=null && !data.getName5().equals("")){
				result.add(new ContactInfoVO(data.getName5(), data.getPnum5(), data.getUnum(), data.getPkey(), data.getMessageReq()));
			}
			
			paramMap.put("success", "true");
			paramMap.put("data", result);
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
		if(data!=null){
			if(data.getName1()!=null && !data.getName1().equals("")){
				result.add(new ContactInfoVO(data.getName1(), data.getPnum1(), data.getUnum(), data.getPkey(), data.getMessageReq()));
			}
			if(data.getName2()!=null && !data.getName2().equals("")){
				result.add(new ContactInfoVO(data.getName2(), data.getPnum2(), data.getUnum(), data.getPkey(), data.getMessageReq()));
			}
			if(data.getName3()!=null && !data.getName3().equals("")){
				result.add(new ContactInfoVO(data.getName3(), data.getPnum3(), data.getUnum(), data.getPkey(), data.getMessageReq()));
			}
			if(data.getName4()!=null && !data.getName4().equals("")){
				result.add(new ContactInfoVO(data.getName4(), data.getPnum4(), data.getUnum(), data.getPkey(), data.getMessageReq()));
			}
			if(data.getName5()!=null && !data.getName5().equals("")){
				result.add(new ContactInfoVO(data.getName5(), data.getPnum5(), data.getUnum(), data.getPkey(), data.getMessageReq()));
			}
		}
		return result;
	}

	// 메시지 전송 가능 여부 체크
	@Override
	public Map<String, Object> messageCheck(Map<String, Object> paramMap) {
		ExContactInfoVO tmp= externalDAO.messageCheck(paramMap);
		String enabledTime= tmp.getEnabledTime();
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String nowTime= dateFormat.format(calendar.getTime());
		paramMap.put("enabledTime", nowTime); 
		
		// 한번도 메시지를 보내지 않았을 때
		if(enabledTime.equals("Never used")){
			externalDAO.messageReCheck(paramMap);
			paramMap.put("success", "true");
			paramMap.put("data", "true");
			
		// 한번이상 메시지를 보냈을 때
		} else {
			Date nowDate= null;
			Date enabledDate= null;
			try {
				nowDate= dateFormat.parse(nowTime);
				enabledDate = dateFormat.parse(enabledTime);
			} catch (ParseException e) {
				nowDate= new Date();
				enabledDate= new Date();
				e.printStackTrace();
			}
			
			// 최근 메시지 전송 시각이 5분 이내인 경우
			if( (nowDate.getTime() - enabledDate.getTime())< 300000){
				paramMap.put("success", "true");
				paramMap.put("data", "false");
				
			// 최근 메시지 전송 이력이 없는 경우
			} else {
				externalDAO.messageReCheck(paramMap);
				paramMap.put("success", "true");
				paramMap.put("data", "true");
			}
		}
		
		return paramMap;
	}

	// 위치 정보 업로드
	@Override
	public Map<String, Object> locationUpload(Map<String, Object> paramMap) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String nowTime= dateFormat.format(calendar.getTime());
		paramMap.put("detectionTime", nowTime);
		
		externalDAO.locationUpload(paramMap);
		paramMap.put("success", "true");
		return paramMap;
	}
	
	// 발송할 메시지 정보 조회
	@Override
	public Map<String, Object> selectMessage(Map<String, Object> paramMap) {
		ExContactInfoVO messageInfo;
		
		try{
			messageInfo= externalDAO.selectMessage(paramMap);
			paramMap.put("message", messageInfo.getMessageReq());
			paramMap.put("success", "true");
		}catch(Exception e){
			paramMap.put("success", "false");
		}

		return paramMap;
	}

	// 발송할 메세지 정보 수정
	@Override
	public Map<String, Object> modifyMessage(Map<String, Object> paramMap) {
		externalDAO.modifyMessage(paramMap);
		paramMap.put("success", "true");
		return paramMap;
	}

	
}
