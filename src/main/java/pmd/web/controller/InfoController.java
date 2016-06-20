package pmd.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pmd.common.common.CommandMap;
import pmd.common.common.PMDUtil;
import pmd.common.vo.Chart1VO;
import pmd.common.vo.Chart2VO;
import pmd.common.vo.Chart3VO;
import pmd.common.vo.SoftwareInfoVO;
import pmd.common.vo.UserInfoVO;
import pmd.web.service.InfoService;
import pmd.web.service.MainService;

@Controller
public class InfoController {
     
	PMDUtil pmd= new PMDUtil();
	
    @Resource(name="infoService")
    private InfoService infoService;
    @Resource(name="mainService")
    private MainService mainService;
     
    /*******************************************************************************************************
     * 요약 페이지																											*
     * @param commandMap																								*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @RequestMapping(value="/web/info/summary.do")
    public ModelAndView openSummaryPage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/info/summary");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/    	
    	
    	/*----------------------------------------------*/
    	/*				세션 가져오기 --				*/
    	/*----------------------------------------------*/
    	HttpSession session= request.getSession();
    	/*----------------------------------------------*/
    	/*				-- 세션 가져오기				*/
    	/*----------------------------------------------*/    	
    	
    	/*------------------------------------------------------------*/
    	/*				현재 사용자 정보 가져오기 --			  */
    	/*------------------------------------------------------------*/
    	UserInfoVO userInfo= pmd.loginCheck(session);
    	/*------------------------------------------------------------*/
    	/*				-- 현재 사용자 정보 가져오기			  */
    	/*------------------------------------------------------------*/
    	
    	/*------------------------------------------*/
    	/*				로그인 체크 -- 			*/
    	/*------------------------------------------*/
    	if(userInfo == null){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    	
    		// 쿼리 파라미터 설정
			Map<String,Object> paramMap= commandMap.getMap();
			paramMap.put("userId", userInfo.getUserId());
			// --쿼리 파라미터 설정
		
			// 목록 가져오기
			ArrayList<SoftwareInfoVO> rawInstalledList= infoService.getInstalledSoftware(paramMap);
			ArrayList<SoftwareInfoVO> ownedList= null;
			ArrayList<SoftwareInfoVO> installedList= null;
			// --목록 가져오기
			
			///// 유료 소프트웨어만 가지고 연산하도록 필터링 /////
			ArrayList<SoftwareInfoVO> chargedList= infoService.getChargedSoftware(paramMap);
			
			///// 보유 소프트웨어 리스트 및 설치 소프트웨어 리스트 필터링 ///
			ownedList= infoService.getOwnedSoftware(paramMap);
			installedList= pmd.includeSoftwareBySwName(rawInstalledList, chargedList);
    	
			// 차트 데이터 만들기(소프트웨어 사용 정보) -> 소프트웨어명, 보유정품수량, 복제수량, 재고
			Chart1VO addToChart1= null;
			ArrayList<Chart1VO> chart1= new ArrayList<Chart1VO>();
			
			// 차트 데이터 만들기(사용 중인 소프트웨어) -> 소프트웨어명, 사용 수량
			Chart2VO addToChart2= null;
			ArrayList<Chart2VO> chart2= new ArrayList<Chart2VO>();
			
			// 차트 데이터 만들기(도표) -> 컴퓨터명, IP주소, 운영체제, 소프트웨어명, 업데이트
			Chart3VO addToChart3= null;
			ArrayList<Chart3VO> chart3= new ArrayList<Chart3VO>();
			
			///// 보유 정품 소프트웨어 중복 제거 (다른 만료일자인 경우) /////
			ownedList= pmd.removeDuplicateBySwName(ownedList);
			
			///// 만료 제품 제거 /////
			ownedList= pmd.removeExpiredSoftware(ownedList);
			
			
			// 보유 정품 소프트웨어 이름 및 개수 파악
			for(SoftwareInfoVO o:ownedList){
				pmd.logging("[가공전]이름: "+o.getSwName()+", 보유개수: "+o.getOwnQuantity());
				addToChart1= new Chart1VO();
				addToChart1.setSwName(o.getSwName());
				addToChart1.setOwnQuantity(Integer.parseInt(o.getOwnQuantity().replace(" ", "")));
				chart1.add(addToChart1);
			}
			
			// 사용중인 프로그램 이름도 차트1에 추가, , 차트3 데이터 수집
			for(SoftwareInfoVO i:installedList){
				boolean needToAdd= true;
				for(Chart1VO c1:chart1){
					if(c1.getSwName().replaceAll(" ", "").equals(i.getSwName().replaceAll(" ", ""))){
						needToAdd=false;
					}
				}
				if(needToAdd){
					addToChart1= new Chart1VO();
					addToChart1.setSwName(i.getSwName());
					chart1.add(addToChart1);
				}
				needToAdd=false;
				
				addToChart3= new Chart3VO();
				addToChart3.setPcIp(i.getPcIp());
				addToChart3.setPcName(i.getPcName());
				addToChart3.setPcOs(i.getPcOs());
				addToChart3.setSwName(i.getSwName());
				addToChart3.setUpdateDate(i.getUpdateDate().substring(0, 19));
				chart3.add(addToChart3);
			}
			
			// 보유 정품 소프트웨어 중 사용중인 개수 파악 (정품, 복제)
			for(int n=0; n<chart1.size(); n++){
				for(SoftwareInfoVO i:installedList){
					if(chart1.get(n).getSwName().replaceAll(" ","").equals(i.getSwName().replaceAll(" ",""))){
						if(chart1.get(n).getCopyQuantity()==0){
							chart1.get(n).setCopyQuantity(1);
						}else{
							chart1.get(n).setCopyQuantity(chart1.get(n).getCopyQuantity()+1);
						}
					}
				}	
			}
			
			// 정품, 복제, 재고 소프트웨어 갯수 파악 및 등록
			for(int n=0; n<chart1.size(); n++){
				int temp= chart1.get(n).getOwnQuantity()-chart1.get(n).getCopyQuantity();
				pmd.logging("[처리전]파일: "+chart1.get(n).getSwName()+", 정품: "+chart1.get(n).getOwnQuantity());
				
				if(temp>0){ // 보유 개수가 사용 개수보다 많은 경우
					chart1.get(n).setOwnQuantity(chart1.get(n).getCopyQuantity());
					chart1.get(n).setCopyQuantity(0);
					chart1.get(n).setStockQuantity(temp);
				}else if(temp<0){//보유 개수가 사용 개수보다 적은 경우
					// 보유 소프트웨어 개수 그대로 진행
					chart1.get(n).setCopyQuantity(temp*(-1));
					chart1.get(n).setStockQuantity(0);
				}else{
					// 보유 소프트웨어 개수 그대로 진행
					chart1.get(n).setCopyQuantity(0);
					chart1.get(n).setStockQuantity(0);
				}
				
				// 사용 프로그램(정품/복제)가 1개 이상일 경우 -> 차트2에 활용
				pmd.logging("  [처리후]파일: "+chart1.get(n).getSwName()+", 정품: "+chart1.get(n).getOwnQuantity()+", 복제: "+chart1.get(n).getCopyQuantity()+", 재고: "+chart1.get(n).getStockQuantity());
				if(chart1.get(n).getCopyQuantity()>0 || chart1.get(n).getOwnQuantity()>0) {
					addToChart2= new Chart2VO();
					addToChart2.setSwName(chart1.get(n).getSwName());
					addToChart2.setUsingQuantity(chart1.get(n).getCopyQuantity()+chart1.get(n).getOwnQuantity());
					chart2.add(addToChart2);
					//log.debug("추가!");
				}
			}
			
	        // jsp를 사용해 출력하므로 모델뷰가 아닌 세션에 데이터를 저장한다.
			
			chart1.sort(new Comparator<Chart1VO>(){
				@Override
				public int compare(Chart1VO o1, Chart1VO o2) {
					int o1sum= o1.getOwnQuantity() + o1.getCopyQuantity() + o1.getStockQuantity();
					int o2sum= o2.getOwnQuantity() + o2.getCopyQuantity() + o2.getStockQuantity();
					if(o1sum > o2sum) return -1;
					else if(o1sum < o2sum) return 1;
					else return 0;
				}
			});
	        session.setAttribute("chart1",chart1);
	        if(chart1.size()<=3) session.setAttribute("chart1Count", 3);
	        else session.setAttribute("chart1Count", chart1.size());
	        
	        
	        chart2.sort(new Comparator<Chart2VO>() {
	        	@Override
	        	public int compare(Chart2VO o1, Chart2VO o2) {
	        		if(o1.getUsingQuantity() > o2.getUsingQuantity()) return -1;
	        		else if(o1.getUsingQuantity() < o2.getUsingQuantity()) return 1;
	        		else return 0;
	        	}
			});
	        session.setAttribute("chart2",chart2);
	        
	        chart3.sort(new Comparator<Chart3VO>(){
	        	@Override
	        	public int compare(Chart3VO o1, Chart3VO o2) {
	        		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        		Date o1date = null;
	        		Date o2date = null;
	        		try {
	        			o1date = transFormat.parse(o1.getUpdateDate());
	        			o2date = transFormat.parse(o2.getUpdateDate());
					} catch (ParseException e) {
						o1date = new Date();
						o2date = new Date();
					}
	        		if(o1date.before(o2date)) return 1;
	        		else return -1;
	        	}
	        });
	        session.setAttribute("chart3",chart3);
	        
	        	
	        /*--------------------------------------------------------------------------*/
    		/*						 	-- 기능 구현 부분							*/
    		/*--------------------------------------------------------------------------*/
    	}
    	/*----------------------------------------------*/
    	/*				-- 로그인 체크	 			*/
    	/*----------------------------------------------*/
    	
    	
    	return mv;
    }
    
    /*******************************************************************************************************
     * 현황 페이지																											*
     * @param commandMap																								*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @RequestMapping(value="/web/info/present.do")
    public ModelAndView openPresentPage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/info/present");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/    	
    	
    	/*----------------------------------------------*/
    	/*				세션 가져오기 --				*/
    	/*----------------------------------------------*/
    	HttpSession session= request.getSession();
    	/*----------------------------------------------*/
    	/*				-- 세션 가져오기				*/
    	/*----------------------------------------------*/    	
    	
    	/*------------------------------------------------------------*/
    	/*				현재 사용자 정보 가져오기 --			  */
    	/*------------------------------------------------------------*/
    	UserInfoVO userInfo= pmd.loginCheck(session);
    	/*------------------------------------------------------------*/
    	/*				-- 현재 사용자 정보 가져오기			  */
    	/*------------------------------------------------------------*/
    	
    	/*------------------------------------------*/
    	/*				로그인 체크 -- 			*/
    	/*------------------------------------------*/
    	if(userInfo == null){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    	
    		
    		
    		Map<String,Object> paramMap= new HashMap<String,Object>();
    		paramMap.put("userId", userInfo.getUserId());
    		
    		ArrayList<SoftwareInfoVO> table1= null;
    		ArrayList<SoftwareInfoVO> rawTable1= infoService.getUserPcList(paramMap);
    		ArrayList<SoftwareInfoVO> table2= null;
    		ArrayList<SoftwareInfoVO> rawTable2= null;
    		
    		ArrayList<String> nameList= new ArrayList<String>();
    		boolean exist= false;
    		
    		for(SoftwareInfoVO p:rawTable1){
    			for(String name:nameList){
    				if(p.getPcName().equals(name)) exist= true;
    			}
    			if(!exist) nameList.add(p.getPcName());
    			exist=false;
    		}
    		
    		String pcName= request.getParameter("pcName");
    		
    		if(pcName == null) pcName= "";
    		else{
    			if(pcName.equals("ALL")) pcName="";
    		}
    		
    		if(pcName.equals("")){
    			table1= new ArrayList<SoftwareInfoVO>();
    			
    			// 유료 소프트웨어만 가지고 연산하도록 필터링
    			ArrayList<SoftwareInfoVO> chargedList= infoService.getChargedSoftware(paramMap);
    			for(SoftwareInfoVO c: chargedList){
    				for(int i= 0; i<rawTable1.size(); i++){
    					if(c.getSwName().replaceAll(" ", "").equals(rawTable1.get(i).getSwName().replaceAll(" ", ""))){
    						table1.add(rawTable1.get(i));
    					}
    				}
    			}
    			
    			session.setAttribute("dataList", table1);
    		}else{
    			paramMap.put("pcName", pcName);
    			rawTable2= infoService.getUserPcListByPk(paramMap);
    			table2= new ArrayList<SoftwareInfoVO>();
    			
    			// 유료 소프트웨어만 가지고 연산하도록 필터링
    			ArrayList<SoftwareInfoVO> chargedList= infoService.getChargedSoftware(paramMap);
    			for(SoftwareInfoVO c: chargedList){
    				for(int i= 0; i<rawTable2.size(); i++){
    					if(c.getSwName().replaceAll(" ", "").equals(rawTable2.get(i).getSwName().replaceAll(" ", ""))){
    						table2.add(rawTable2.get(i));
    					}
    				}
    			}
    			
    			session.setAttribute("dataList", table2);
    		}
    		session.setAttribute("nameList", nameList);
    		
    		
    		/*--------------------------------------------------------------------------*/
    		/*						 	-- 기능 구현 부분							*/
    		/*--------------------------------------------------------------------------*/
    	}
    	/*----------------------------------------------*/
    	/*				-- 로그인 체크	 			*/
    	/*----------------------------------------------*/
    	
    	
    	
    	return mv;
    } 
    
    /************************************************************************************************
     * 소프트웨어 정보 자동 업로드 ( CoordyClient )															*
     * @param commandMap																						*
     * @return																										*
     * @throws Exception																							*
     ************************************************************************************************/
    @RequestMapping(value="/web/info/updateSwInfo.do")
    public void updateSwInfo(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	request. setCharacterEncoding("UTF-8");
    	
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	
    	String message= request.getParameter("message");
    	Map<String,Object> paramMap= new HashMap<String,Object>();
    	
    	String userId= null;
    	String pcIp= null;
    	String pcName= null;
    	String pcOs= null;
    	String updateDate= null;
    	String swFile= null;
    	String swName= null;
    	ArrayList<SoftwareInfoVO> messages= new ArrayList<SoftwareInfoVO>();
    	ArrayList<SoftwareInfoVO> installed= new ArrayList<SoftwareInfoVO>();
    	ArrayList<SoftwareInfoVO> installedNoDup= new ArrayList<SoftwareInfoVO>();
    	Map<String,Object> resultMap= null;
    	if(message!=null && !message.equals("")){
	        StringTokenizer st= new StringTokenizer(message,"*");
	        if(st.hasMoreTokens()) {
	        	userId= st.nextToken();
	        	paramMap.put("userId", userId);
	        	resultMap=mainService.doIdCheck(paramMap);
	        }
	        if(st.hasMoreTokens()) pcIp= st.nextToken();
	        if(st.hasMoreTokens()) pcName= st.nextToken().replaceAll("\'", "");
	        if(st.hasMoreTokens()) pcOs= st.nextToken();
	        if(st.hasMoreTokens()) updateDate= st.nextToken();
	        if(st.hasMoreTokens() && ((String)resultMap.get("checkSuccess")).equals("false")) {
		        int tiktok= 0;
		        String nextToken= "";
		        while(st.hasMoreTokens()){
		        	nextToken= st.nextToken();
		        	if(nextToken!=null && !nextToken.equals("")){
			        	if(tiktok%2==0){
			        		swFile= nextToken.replaceAll("\'", "");
			        	}else{
			        		swName= nextToken.replaceAll("\'", "");
			        		messages.add(new SoftwareInfoVO(swName,"",swFile,"",userId,pcName,pcIp,pcOs,updateDate));
			        	}
		        	}
		        	tiktok++;
		        }
		        ArrayList<SoftwareInfoVO> installList= infoService.getInstalledSoftware(paramMap);
		        ArrayList<SoftwareInfoVO> installListTemp= new ArrayList<SoftwareInfoVO>(installList);
		        int idx= 0;

		        for(int i=0; i<messages.size(); i++)
		        	if(!messages.get(i).equals("")) {
		        		installed.add(new SoftwareInfoVO(messages.get(i).getSwName(),messages.get(i).getSwFile(),userId,pcName,pcIp,pcOs,updateDate));
		        	}
		        boolean isExist= false; 
		        for(SoftwareInfoVO s:installListTemp) {
		        	for(SoftwareInfoVO d:installedNoDup) {			// 목록 내에 중복된 소프트웨어 이름 제거
		        		if(s.getSwName().replaceAll(" ", "").equals(d.getSwName().replaceAll(" ", ""))){
		        			isExist= true;
		        		}
		        	}
		        	for(SoftwareInfoVO i:installListTemp){				// 이미 등록된 소프트웨어와 동일한 이름인 경우 추가X
		        		if(s.getSwName().replaceAll(" ", "").equals(i.getSwName().replaceAll(" ", "")) && s.getPcName().equals(i.getPcName())){
		        			isExist= true;
		        		}
		        	}
		        	if(!isExist) installedNoDup.add(new SoftwareInfoVO(installList.get(idx).getSwName(),userId,pcName,pcIp,pcOs,updateDate));
		        	isExist= false;
		        	idx++;
		        }       		
		        
		        if(installedNoDup.size()>0){
			        paramMap.put("list", installedNoDup);
			        infoService.updateUserPcSwList(paramMap);
		        }
	        }
    	}
    }
    
    /************************************************************************************************
     * 관리 페이지																									*
     * @param commandMap																						*
     * @return																										*
     * @throws Exception																							*
     ************************************************************************************************/
    @RequestMapping(value="/web/info/manage.do")
    public ModelAndView openManagePage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/info/manage");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/    	
    	
    	/*----------------------------------------------*/
    	/*				세션 가져오기 --				*/
    	/*----------------------------------------------*/
    	HttpSession session= request.getSession();
    	/*----------------------------------------------*/
    	/*				-- 세션 가져오기				*/
    	/*----------------------------------------------*/    	
    	
    	/*------------------------------------------------------------*/
    	/*				현재 사용자 정보 가져오기 --			  */
    	/*------------------------------------------------------------*/
    	UserInfoVO userInfo= pmd.loginCheck(session);
    	/*------------------------------------------------------------*/
    	/*				-- 현재 사용자 정보 가져오기			  */
    	/*------------------------------------------------------------*/
    	
    	/*------------------------------------------*/
    	/*				로그인 체크 -- 			*/
    	/*------------------------------------------*/
    	if(userInfo == null){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    	
    	
    		
    		Map<String,Object> paramMap= new HashMap<String,Object>();
    		paramMap.put("userId", userInfo.getUserId());
    		ArrayList<SoftwareInfoVO> ownList= infoService.getOwnedSoftware(paramMap);
    		for(int i=0; i<ownList.size(); i++){
    			if(ownList.get(i).getOwnExpDate().equals("")) {
    				ownList.get(i).setOwnExpDate("<span style=color:gray;>영구 라이센스</span>");
    			} else {
    				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
    				Date expDate = transFormat.parse(ownList.get(i).getOwnExpDate());
    				Date nowDate = new Date();
    				
    				long diff = expDate.getTime() - nowDate.getTime();
    				Long longDiffDays= diff / (24 * 60 * 60 * 1000);
    			    int diffDays = longDiffDays.intValue();
    			    
    			    if(diffDays<0) {
    			    	ownList.get(i).setOwnExpDate("<span style=color:red;>기간만료("+ownList.get(i).getOwnExpDate()+")</span>");
    			    }else if(diffDays<=30){
    			    	ownList.get(i).setOwnExpDate("<span style=color:red;>"+diffDays+"일 ("+ownList.get(i).getOwnExpDate()+")</span>");
    			    }else{
    			    	ownList.get(i).setOwnExpDate("<span style=color:#a0cbf4;>"+diffDays+"일 ("+ownList.get(i).getOwnExpDate()+")</span>");
    			    }
    				
    			}
    		}
    		
    		session.setAttribute("ownList", ownList);
    		/*--------------------------------------------------------------------------*/
    		/*						 	-- 기능 구현 부분							*/
    		/*--------------------------------------------------------------------------*/
    	}
    	/*----------------------------------------------*/
    	/*				-- 로그인 체크	 			*/
    	/*----------------------------------------------*/
    	return mv;
    } 
    //------------------------------------------------------------------------------------------------------------------------------------------------
    // 신규 견적 요청 관련
    
    /*******************************************************************************************************
     * 관리 페이지 > 등록 페이지																							*
     * @param commandMap	                                           													*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @RequestMapping(value="/web/info/estimatePage.do")
    public ModelAndView goEstimatePage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception {
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/info/estimate");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/    	
    	
    	/*----------------------------------------------*/
    	/*				세션 가져오기 --				*/
    	/*----------------------------------------------*/
    	HttpSession session= request.getSession();
    	/*----------------------------------------------*/
    	/*				-- 세션 가져오기				*/
    	/*----------------------------------------------*/    	
    	
    	/*------------------------------------------------------------*/
    	/*				현재 사용자 정보 가져오기 --			  */
    	/*------------------------------------------------------------*/
    	UserInfoVO userInfo= pmd.loginCheck(session);
    	/*------------------------------------------------------------*/
    	/*				-- 현재 사용자 정보 가져오기			  */
    	/*------------------------------------------------------------*/
    	
    	/*------------------------------------------*/
    	/*				로그인 체크 -- 			*/
    	/*------------------------------------------*/
    	if(userInfo == null){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    	
    		Map<String, Object> paramMap= new HashMap<String,Object>();
    		String searchKeyword= request.getParameter("searchKeyword");
    		if(searchKeyword == null || searchKeyword.equals("")){
	    		ArrayList<SoftwareInfoVO> chargedList= new ArrayList<SoftwareInfoVO>();
	    		session.setAttribute("chargedList", chargedList);
    		} else {
    			paramMap.put("searchKeyword", searchKeyword);
    			ArrayList<SoftwareInfoVO> chargedList= infoService.getChargedSoftwareByPk(paramMap);
    			session.setAttribute("chargedList", chargedList);
    		}
    		
    		/*--------------------------------------------------------------------------*/
    		/*						 	-- 기능 구현 부분							*/
    		/*--------------------------------------------------------------------------*/
    	}
    	/*----------------------------------------------*/
    	/*				-- 로그인 체크	 			*/
    	/*----------------------------------------------*/
    	return mv;
    }
    
    
    /*******************************************************************************************************
     * 관리 페이지 > 등록 페이지																							*
     * @param commandMap	                                           													*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @RequestMapping(value="/web/info/estimate.do")
    public ModelAndView doEstimate(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception {
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/info/manage");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/    	
    	
    	/*----------------------------------------------*/
    	/*				세션 가져오기 --				*/
    	/*----------------------------------------------*/
    	HttpSession session= request.getSession();
    	/*----------------------------------------------*/
    	/*				-- 세션 가져오기				*/
    	/*----------------------------------------------*/    	
    	
    	/*------------------------------------------------------------*/
    	/*				현재 사용자 정보 가져오기 --			  */
    	/*------------------------------------------------------------*/
    	UserInfoVO userInfo= pmd.loginCheck(session);
    	/*------------------------------------------------------------*/
    	/*				-- 현재 사용자 정보 가져오기			  */
    	/*------------------------------------------------------------*/
    	
    	/*------------------------------------------*/
    	/*				로그인 체크 -- 			*/
    	/*------------------------------------------*/
    	if(userInfo == null){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    	
    		
    		 // 이메일 관련
        	
    		Date nowDate = new Date();
    		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		String nowDateString = transFormat.format(nowDate);
    		
    		String quantity= request.getParameter("quantity");
    		
    		String chk[] = request.getParameterValues("chk");
    		Map<String,Object> paramMap= new HashMap<String,Object>();
    		paramMap.put("userId", userInfo.getUserId());
    		ArrayList<SoftwareInfoVO> chargedList= infoService.getChargedSoftware(paramMap);
    		ArrayList<SoftwareInfoVO> requestList= new ArrayList<SoftwareInfoVO>();
    		
    		   		
    		// 매칭된 보유 소프트웨어와 동일한 유료 소프트웨어 정보를 다시 추린다.
    		for(String o:chk){
    			for(SoftwareInfoVO c:chargedList){
    				if(o.replaceAll(" ", "").equals(c.getSwName().replaceAll(" ", ""))){
    					c.setOwnQuantity(quantity);
    					requestList.add(c);
    				}
    			}
    		}
    		
    		 // 메일 관련 정보
	           String host = "smtp.worksmobile.com";
	           final String username = PMDUtil.REQUEST_MAIL_ID;
	           final String password = PMDUtil.REQUEST_MAIL_PW;		//TODO 웍스모바일 비밀번호
	           
	           
	           // 메일 내용
	           String subject = "[신규] PMD 견적 요청 - "+userInfo.getUserId()+" | "+nowDateString;
	           String body = 	"일시: "+nowDateString+"\n"+
			        		    "아이디: "+userInfo.getUserId()+"\n"+
			        		    "이메일: "+userInfo.getUserEmail()+"\n"+
			        		    "유선전화: "+userInfo.getUserTel()+"\n"+
			        		    "휴대전화: "+userInfo.getUserHp()+"\n"+
	        		   			"회사: "+userInfo.getUserCoName()+"\n"+
	        		   			"회사주소: ("+userInfo.getUserCoZip()+") "+userInfo.getUserCoAddr()+"\n\n"+
	        		   			"< 견적 내용 > ( [회사] 제품, 수량 )\n\n";
	           
	           for(SoftwareInfoVO s:requestList){
	        	   body+="[ "+s.getSwVendor()+" ] ";
	        	   body+=s.getSwName()+",   ";
	        	   body+=s.getOwnQuantity()+"\n";
	           }
	           body+="\n\n본문에 있는 메일 주소로 견적 내용 보내주시면 됩니다.";
	            
	           Properties props = System.getProperties();
	            
	           props.put("mail.smtp.user" , username);
	           props.put("mail.smtp.host", host);
	           props.put("mail.smtp.port", "465");
	           props.put("mail.smtp.starttls.enable", "true");
	           props.put("mail.smtp.auth", "true");
	           props.put("mail.smtp.debug", "true");
	           props.put("mail.smtp.socketFactory.port", "465");
	           props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	           props.put("mail.smtp.socketFactory.fallback", "false");


	           Session session2 = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
	               String un=username;
	               String pw=password;
	               protected PasswordAuthentication getPasswordAuthentication() {
	                   return new PasswordAuthentication(un, pw);
	               }
	           });
	           session2.setDebug(PMDUtil.LOG_ENABLE); //for debug
	           Message mimeMessage = new MimeMessage(session2);
	           mimeMessage.setFrom(new InternetAddress(PMDUtil.REQUEST_MAIL_ID));
	           //mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
	           
	           InternetAddress[] toAddr= new InternetAddress[3];
	           toAddr[0]= new InternetAddress(PMDUtil.REQUEST_MAIL_TO1);
	           toAddr[1]= new InternetAddress(PMDUtil.REQUEST_MAIL_TO2);
	           toAddr[2]= new InternetAddress(PMDUtil.REQUEST_MAIL_ID);
	           mimeMessage.setRecipients(Message.RecipientType.TO, toAddr);
	           
	           mimeMessage.setSubject(subject);
	           mimeMessage.setText(body);
	           Transport.send(mimeMessage);
	           
	       	// --이메일 관련
	           
	           
    		mv.addObject("servletMessage", "정상적으로 요청되었습니다. \n빠른 시일 내에 계정에 등록된 연락처 또는 이메일 주소로 답변 드리도록 하겠습니다. ");
    		/*--------------------------------------------------------------------------*/
    		/*						 	-- 기능 구현 부분							*/
    		/*--------------------------------------------------------------------------*/
    	}
    	/*----------------------------------------------*/
    	/*				-- 로그인 체크	 			*/
    	/*----------------------------------------------*/
    	return mv;
    }
    	
    //----------------------------------------------------------------------------------------------------------------------------------------------------
    
    /*******************************************************************************************************
     * 관리 페이지 > 등록 페이지																							*
     * @param commandMap	                                           													*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @RequestMapping(value="/web/info/registerPage.do")
    public ModelAndView doRegisterSwPage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/info/register");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/    	
    	
    	/*----------------------------------------------*/
    	/*				세션 가져오기 --				*/
    	/*----------------------------------------------*/
    	HttpSession session= request.getSession();
    	/*----------------------------------------------*/
    	/*				-- 세션 가져오기				*/
    	/*----------------------------------------------*/    	
    	
    	/*------------------------------------------------------------*/
    	/*				현재 사용자 정보 가져오기 --			  */
    	/*------------------------------------------------------------*/
    	UserInfoVO userInfo= pmd.loginCheck(session);
    	/*------------------------------------------------------------*/
    	/*				-- 현재 사용자 정보 가져오기			  */
    	/*------------------------------------------------------------*/
    	
    	/*------------------------------------------*/
    	/*				로그인 체크 -- 			*/
    	/*------------------------------------------*/
    	if(userInfo == null){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    	
    		
    		Map<String, Object> paramMap= new HashMap<String,Object>();
    		String searchKeyword= request.getParameter("searchKeyword");
    		if(searchKeyword == null || searchKeyword.equals("")){
	    		ArrayList<SoftwareInfoVO> chargedList= new ArrayList<SoftwareInfoVO>();
	    		session.setAttribute("chargedList", chargedList);
    		} else {
    			paramMap.put("searchKeyword", searchKeyword);
    			ArrayList<SoftwareInfoVO> chargedList= infoService.getChargedSoftwareByPk(paramMap);
    			session.setAttribute("chargedList", chargedList);
    		}
    		/*--------------------------------------------------------------------------*/
    		/*						 	-- 기능 구현 부분							*/
    		/*--------------------------------------------------------------------------*/
    	}
    	/*----------------------------------------------*/
    	/*				-- 로그인 체크	 			*/
    	/*----------------------------------------------*/
    	return mv;
    }
    
    /************************************************************************************************
     * 관리 페이지 > 등록 페이지 > 등록 요청																	*
     * @param commandMap																						*
     * @return																										*
     * @throws Exception																							*
     ************************************************************************************************/
    @RequestMapping(value="/web/info/register.do")
    public ModelAndView doRegisterSw(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/info/register");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/    	
    	
    	/*----------------------------------------------*/
    	/*				세션 가져오기 --				*/
    	/*----------------------------------------------*/
    	HttpSession session= request.getSession();
    	/*----------------------------------------------*/
    	/*				-- 세션 가져오기				*/
    	/*----------------------------------------------*/    	
    	
    	/*------------------------------------------------------------*/
    	/*				현재 사용자 정보 가져오기 --			  */
    	/*------------------------------------------------------------*/
    	UserInfoVO userInfo= pmd.loginCheck(session);
    	/*------------------------------------------------------------*/
    	/*				-- 현재 사용자 정보 가져오기			  */
    	/*------------------------------------------------------------*/
    	
    	/*------------------------------------------*/
    	/*				로그인 체크 -- 			*/
    	/*------------------------------------------*/
    	if(userInfo == null){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    	
    		
    		String[] chk= request.getParameterValues("chk");
    		String expiryDate= request.getParameter("expiryDate");
    		String quantity= request.getParameter("quantity");
    		if(expiryDate==null) expiryDate= "";
    		if(quantity==null) quantity= "";
    		
    		Map<String,Object> paramMap= new HashMap<String,Object>();
    		paramMap.put("userId", userInfo.getUserId());
    		ArrayList<SoftwareInfoVO> chargedList= infoService.getChargedSoftware(paramMap);
    		ArrayList<SoftwareInfoVO> ownedList= infoService.getOwnedSoftware(paramMap); // 현재 보유중인 소프트웨어 정보
    		ArrayList<SoftwareInfoVO> dupList= new ArrayList<SoftwareInfoVO>(); // 중복되는 소프트웨어 정보, UPDATE
    		
    		
    		ArrayList<SoftwareInfoVO> registerList= new ArrayList<SoftwareInfoVO>(); // INSERT
    		SoftwareInfoVO addToArray= new SoftwareInfoVO();
    		
    		// 파라미터로 읽어온 정보를 토대로 테이블에 추가할 리스트를 작성한다.
    		for(String s: chk){
    			addToArray= new SoftwareInfoVO();
    			for(SoftwareInfoVO c:chargedList){
    				if(s.replaceAll(" ", "").equals(c.getSwName().replaceAll(" ", ""))){
    					addToArray.setUserId(userInfo.getUserId());
    					addToArray.setSwName(s);
    					addToArray.setSwVendor(c.getSwVendor());
    					addToArray.setOwnQuantity(quantity);
    					addToArray.setOwnExpDate(expiryDate);
    				}
    			}
    			registerList.add(addToArray);
    		}
    		
    		
    		
    		for(int i=0; i<registerList.size(); i++){ // 이번에 추가할 것들 중에서
    			//log.debug("1");
    			for(int j=0; j<ownedList.size(); j++){	// 기존에 가지고 있던 것 들과
    				//log.debug("2");
    				if(registerList.get(i).getSwName().replaceAll(" ", "").equals(ownedList.get(j).getSwName().replaceAll(" ", ""))
    						&& registerList.get(i).getOwnExpDate().equals(ownedList.get(j).getOwnExpDate())){ // 겹치는 애들만 고름
    					//log.debug("3");
    					
    					// 기존 갯수가 null로 잡히는 애들은 0으로 초기화
    					if(ownedList.get(j).getOwnQuantity().equals("")) ownedList.get(j).setOwnQuantity("0");
    					
    					// 새로 만든 놈들 중에 겹치는 놈을 골라서 따로 빼내고
    					addToArray= registerList.get(i);
    					// 남짜 다시 체크
    					addToArray.setOwnExpDate(expiryDate);
    					// 기존 갯수 + 새로 추가된 갯수
    					addToArray.setOwnQuantity(
    							Integer.parseInt(quantity)+Integer.parseInt(ownedList.get(j).getOwnQuantity())+"");
    					// 따로 뺀 리스트에 추가
    					dupList.add(addToArray);
    					
    					// 원래 리스트에서 하나 뺌
    					registerList.remove(i);
    					if(registerList.size()<=0){
    						break;
    					}else{
    						i--;
    					}
    				}
    			}
    		}
    		if(registerList.size()!=0){
    			paramMap.put("regList", registerList);
    			infoService.doRegisterSoftware(paramMap);
    		}
    		if(dupList.size()!=0){
	    		paramMap.put("dupList", dupList);
	    		infoService.doUpdateSoftware(paramMap);
    		}
    		mv.addObject("servletMessage", "등록되었습니다. 다른 제품을 더 추가하시겠습니까?");
    		/*--------------------------------------------------------------------------*/
    		/*						 	-- 기능 구현 부분							*/
    		/*--------------------------------------------------------------------------*/
    	}
    	/*----------------------------------------------*/
    	/*				-- 로그인 체크	 			*/
    	/*----------------------------------------------*/
    	return mv;
    }
    
    /************************************************************************************************
     * 관리 페이지 > 삭제요청																						*
     * @param commandMap																						*
     * @return																										*
     * @throws Exception																							*
     ************************************************************************************************/
    @RequestMapping(value="/web/info/delete.do")
    public ModelAndView doDeleteSw(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/info/manage");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/    	
    	
    	/*----------------------------------------------*/
    	/*				세션 가져오기 --				*/
    	/*----------------------------------------------*/
    	HttpSession session= request.getSession();
    	/*----------------------------------------------*/
    	/*				-- 세션 가져오기				*/
    	/*----------------------------------------------*/    	
    	
    	/*------------------------------------------------------------*/
    	/*				현재 사용자 정보 가져오기 --			  */
    	/*------------------------------------------------------------*/
    	UserInfoVO userInfo= pmd.loginCheck(session);
    	/*------------------------------------------------------------*/
    	/*				-- 현재 사용자 정보 가져오기			  */
    	/*------------------------------------------------------------*/
    	
    	/*------------------------------------------*/
    	/*				로그인 체크 -- 			*/
    	/*------------------------------------------*/
    	if(userInfo == null){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/

    		
    		String chk[] = request.getParameterValues("chk");
    		Map<String,Object> paramMap= new HashMap<String,Object>();
    		paramMap.put("userId", userInfo.getUserId());
    		ArrayList<SoftwareInfoVO> checkedList= new ArrayList<SoftwareInfoVO>();
    		ArrayList<SoftwareInfoVO> ownedList= infoService.getOwnedSoftware(paramMap);
    		
    		// 요청 내용과 매칭되는 보유 소프트웨어 내용을 찾는다.
    		for(String s: chk){
    			for(SoftwareInfoVO o:ownedList){
    				if(s.equals(o.getOwnSer())){
    					checkedList.add(o);
    				}
    			}
    		}
    		
    		paramMap.put("list", checkedList);
    		infoService.doDeleteSoftware(paramMap);
    		
    		mv.addObject("정상적으로 삭제되었습니다.");
    		response.sendRedirect(PMDUtil.PMD_URL+"/web/info/manage.do");
    		/*--------------------------------------------------------------------------*/
    		/*						 	-- 기능 구현 부분							*/
    		/*--------------------------------------------------------------------------*/
    	}
    	/*----------------------------------------------*/
    	/*				-- 로그인 체크	 			*/
    	/*----------------------------------------------*/
    	return mv;
    }
    
    /*******************************************************************************************************
     * 관리 페이지 > 견적요청																								*
     * @param commandMap																								*
     * @return																												*
     * @throws Exception																									*
     *******************************************************************************************************/
    @RequestMapping(value="/web/info/request.do")
    public ModelAndView doRequestSw(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/info/manage");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/    	
    	
    	/*----------------------------------------------*/
    	/*				세션 가져오기 --				*/
    	/*----------------------------------------------*/
    	HttpSession session= request.getSession();
    	/*----------------------------------------------*/
    	/*				-- 세션 가져오기				*/
    	/*----------------------------------------------*/    	
    	
    	/*------------------------------------------------------------*/
    	/*				현재 사용자 정보 가져오기 --			  */
    	/*------------------------------------------------------------*/
    	UserInfoVO userInfo= pmd.loginCheck(session);
    	/*------------------------------------------------------------*/
    	/*				-- 현재 사용자 정보 가져오기			  */
    	/*------------------------------------------------------------*/
    	
    	/*------------------------------------------*/
    	/*				로그인 체크 -- 			*/
    	/*------------------------------------------*/
    	if(userInfo == null){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    	


    		
    		 // 이메일 관련
	        	
    		Date nowDate = new Date();
    		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		String nowDateString = transFormat.format(nowDate);
    		
    		String chk[] = request.getParameterValues("chk");
    		Map<String,Object> paramMap= new HashMap<String,Object>();
    		paramMap.put("userId", userInfo.getUserId());
    		ArrayList<SoftwareInfoVO> chargedList= infoService.getChargedSoftware(paramMap);
    		ArrayList<SoftwareInfoVO> ownedList= infoService.getOwnedSoftware(paramMap);
    		ArrayList<SoftwareInfoVO> checkedList= new ArrayList<SoftwareInfoVO>();
    		ArrayList<SoftwareInfoVO> requestList= new ArrayList<SoftwareInfoVO>();
    		
    		// 요청 내용과 매칭되는 보유 소프트웨어 내용을 찾는다.
    		for(String s: chk){
    			for(SoftwareInfoVO o:ownedList){
    				if(s.equals(o.getOwnSer())){
    					checkedList.add(o);
    				}
    			}
    		}
    		
    		// 매칭된 보유 소프트웨어와 동일한 유료 소프트웨어 정보를 다시 추린다.
    		for(SoftwareInfoVO o:checkedList){
    			for(SoftwareInfoVO c:chargedList){
    				if(o.getSwName().replaceAll(" ", "").equals(c.getSwName().replaceAll(" ", ""))){
    					c.setOwnExpDate(o.getOwnExpDate());
    					c.setOwnQuantity(o.getOwnQuantity());
    					requestList.add(c);
    				}
    			}
    		}
    		
	       	 // 메일 관련 정보
	           String host = "smtp.worksmobile.com";
	           final String username = PMDUtil.REQUEST_MAIL_ID;
	           final String password = PMDUtil.REQUEST_MAIL_PW;		//TODO 웍스모바일 비밀번호
	           
	           
	           // 메일 내용
	           String subject = "[연장] PMD 견적 요청 - "+userInfo.getUserId()+" | "+nowDateString;
	           String body = 	"일시: "+nowDateString+"\n"+
			        		    "아이디: "+userInfo.getUserId()+"\n"+
			        		    "이메일: "+userInfo.getUserEmail()+"\n"+
			        		    "유선전화: "+userInfo.getUserTel()+"\n"+
			        		    "휴대전화: "+userInfo.getUserHp()+"\n"+
	        		   			"회사: "+userInfo.getUserCoName()+"\n"+
	        		   			"회사주소: ("+userInfo.getUserCoZip()+") "+userInfo.getUserCoAddr()+"\n\n"+
	        		   			"< 견적 내용 > ( [회사] 제품, 버전, 만료일, 수량 )\n\n";
	           
	           for(SoftwareInfoVO s:requestList){
	        	   body+="[ "+s.getSwVendor()+" ] ";
	        	   body+=s.getSwName()+",   ";
	        	   body+=s.getSwVersion()+",   ";
	        	   body+=s.getOwnExpDate()+",   ";
	        	   body+=s.getOwnQuantity()+"\n";
	           }
	           body+="\n\n본문에 있는 메일 주소로 견적 내용 보내주시면 됩니다.";
	            
	           Properties props = System.getProperties();
	            
	           props.put("mail.smtp.user" , username);
	           props.put("mail.smtp.host", host);
	           props.put("mail.smtp.port", "465");
	           props.put("mail.smtp.starttls.enable", "true");
	           props.put("mail.smtp.auth", "true");
	           props.put("mail.smtp.debug", "true");
	           props.put("mail.smtp.socketFactory.port", "465");
	           props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	           props.put("mail.smtp.socketFactory.fallback", "false");
	             
	           pmd.logging("-1");
	           Session session2 = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
	               String un=username;
	               String pw=password;
	               protected PasswordAuthentication getPasswordAuthentication() {
	                   return new PasswordAuthentication(un, pw);
	               }
	           });
	           session2.setDebug(PMDUtil.LOG_ENABLE); //for debug
	           Message mimeMessage = new MimeMessage(session2);
	           mimeMessage.setFrom(new InternetAddress(PMDUtil.REQUEST_MAIL_ID));
	           //mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
	           
	           InternetAddress[] toAddr= new InternetAddress[3];
	           toAddr[0]= new InternetAddress(PMDUtil.REQUEST_MAIL_TO1);
	           toAddr[1]= new InternetAddress(PMDUtil.REQUEST_MAIL_TO2);
	           toAddr[2]= new InternetAddress(PMDUtil.REQUEST_MAIL_ID);
	           mimeMessage.setRecipients(Message.RecipientType.TO, toAddr);
	           
	           mimeMessage.setSubject(subject);
	           mimeMessage.setText(body);
	           Transport.send(mimeMessage);
	           
	       	// --이메일 관련
	       	
	       	mv.addObject("servletMessage","정상적으로 요청되었습니다. \n빠른 시일 내에 계정에 등록된 연락처 또는 이메일 주소로 답변 드리도록 하겠습니다. ");
    		
	       	/*--------------------------------------------------------------------------*/
    		/*						 	-- 기능 구현 부분							*/
    		/*--------------------------------------------------------------------------*/
    	}
    	/*----------------------------------------------*/
    	/*				-- 로그인 체크	 			*/
    	/*----------------------------------------------*/
    	return mv;
    }
    
    /*********************************************************************************************
     * 현황 페이지																								*
     * @param commandMap																					*
     * @return																									*		
     * @throws Exception																						*
     *********************************************************************************************/
    @RequestMapping(value="/web/info/search.do")
    public ModelAndView openSearchPage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/info/search");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*/    	
    	
    	/*------------------------------------------------------------------*/
    	/*							파라미터 체크 --			  			*/
    	/*------------------------------------------------------------------*/
    	if(PMDUtil.LOG_ENABLE) pmd.getParameterLog(commandMap);	
    	/*------------------------------------------------------------------*/
    	/*							-- 파라미터 체크			  			*/
    	/*------------------------------------------------------------------*/    	
    	
    	/*----------------------------------------------*/
    	/*				세션 가져오기 --				*/
    	/*----------------------------------------------*/
    	HttpSession session= request.getSession();
    	/*----------------------------------------------*/
    	/*				-- 세션 가져오기				*/
    	/*----------------------------------------------*/    	
    	
    	/*------------------------------------------------------------*/
    	/*				현재 사용자 정보 가져오기 --			  */
    	/*------------------------------------------------------------*/
    	UserInfoVO userInfo= pmd.loginCheck(session);
    	/*------------------------------------------------------------*/
    	/*				-- 현재 사용자 정보 가져오기			  */
    	/*------------------------------------------------------------*/
    	
    	/*------------------------------------------*/
    	/*				로그인 체크 -- 			*/
    	/*------------------------------------------*/
    	if(userInfo == null){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    	
    	
    		
    		Map<String, Object> paramMap= new HashMap<String,Object>();
    		String searchKeyword= request.getParameter("searchKeyword");
    		if(searchKeyword == null || searchKeyword.equals("") ){
	    		ArrayList<SoftwareInfoVO> chargedList= new ArrayList<SoftwareInfoVO>();
	    		session.setAttribute("chargedList", chargedList);
    		} else {
    			paramMap.put("searchKeyword", searchKeyword);
    			ArrayList<SoftwareInfoVO> chargedList= infoService.getChargedSoftwareByPk(paramMap);
    			session.setAttribute("chargedList", chargedList);
    		}
    		
    		/*--------------------------------------------------------------------------*/
    		/*						 	-- 기능 구현 부분							*/
    		/*--------------------------------------------------------------------------*/
    	}
    	/*----------------------------------------------*/
    	/*				-- 로그인 체크	 			*/
    	/*----------------------------------------------*/
    	return mv;
    } 
}