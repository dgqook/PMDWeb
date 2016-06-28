package pmd.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import pmd.common.common.CommandMap;
import pmd.common.common.PMDUtil;
import pmd.common.vo.ExpiryManageVO;
import pmd.common.vo.PcInfoVO;
import pmd.common.vo.SoftwareInfoVO;
import pmd.common.vo.UserInfoVO;
import pmd.common.vo.WorkDataVO;
import pmd.web.service.InfoService;
import pmd.web.service.WorkService;

@Controller
public class WorkController {
	PMDUtil pmd= new PMDUtil(); 
	
    @Resource(name="workService")
    private WorkService workService;
    @Resource(name="infoService")
    private InfoService infoService;
    
    
     
    /******************************************************************************************************************************************************
     * 필터 페이지																																									*
     * @param commandMap																																						*
     * @return																																										*
     * @throws Exception																																							*
     ******************************************************************************************************************************************************/
    @RequestMapping(value="/web/work/generalFilter.do")
    public ModelAndView openGeneralPage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{

    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/work/general");
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
    	if(userInfo == null || !userInfo.getUserPmss().equals("M")){
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    		
    		///// 날짜 정보 가져오기 /////
    		Date nowDate= new Date();
    		String nowDateString = pmd.dateToString(nowDate);
    		Calendar cal = Calendar.getInstance();
    	    cal.setTime(nowDate);
    	    int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;
    	    String[] whatDay= {"(일)","(월)","(화)","(수)","(목)","(금)","(토)"};
    	    nowDateString+= whatDay[(dayNum-1)];
    	    session.setAttribute("nowDate", nowDateString);
    		
    		///// 키워드 정보 가져오기 /////
    		ArrayList<WorkDataVO> workDataList= null;
    		String searchKeyword= request.getParameter("searchKeyword");
    		String searchType= request.getParameter("searchType");
    		if(searchKeyword==null || searchKeyword.equals("") || searchType == null || searchType.equals("")){
    			
    			///// 키워드가 없는 경우 /////
    			workDataList= new ArrayList<WorkDataVO>();
    			WorkDataVO temp= new WorkDataVO();
    			temp.setProductname("    검색 결과가 없습니다.    ");
    			workDataList.add(temp);
    	
    		}else{
    	
    			///// 키워드가 있는 경우 /////
    			Map<String,Object> paramMap= new HashMap<String,Object>();
    			paramMap.put("searchKeyword", searchKeyword);
    			paramMap.put("searchType", searchType);
    			workDataList= workService.getSearchResult(paramMap); 
    		}
    		
    		///// 검색 결과 등록 /////
    		session.setAttribute("workDataList", workDataList);
    		
    		if(searchType!=null && searchType.equals("company")){
    			mv.addObject("companyKeyword",searchKeyword);
    			mv.addObject("ownerKeyword","");
    		} else if(searchType!=null && searchType.equals("owner")){
    			mv.addObject("companyKeyword","");
    			mv.addObject("ownerKeyword",searchKeyword);
    		} else{
    			mv.addObject("companyKeyword","");
    			mv.addObject("ownerKeyword","");
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
    
    /***********************************************************************************************************************************************
     * 엑셀파일 업로드																																						*
     * @param commandMap																																				*
     * @return																																								*
     * @throws Exception																																					*
     ***********************************************************************************************************************************************/
    @RequestMapping(value="/web/work/excelUpload.do")
    public void excelUpload(MultipartHttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	try{
    		MultipartFile file = request.getFile("excelFile");
    		ArrayList<WorkDataVO> workDataList= new ArrayList<WorkDataVO>();
    		if (file != null && file.getSize() > 0) {
    			Workbook wb = null;
    			int pos = file.getName().lastIndexOf( "." );
    			String ext = file.getName().substring( pos + 1 );
    			if(ext.equals("xls")){
    				wb= new HSSFWorkbook(file.getInputStream());
    			} else {
    				wb= new XSSFWorkbook(file.getInputStream());
    			}
    			Sheet sheet = wb.getSheetAt(0);
    			int last = sheet.getLastRowNum();
    			for(int i=0; i<=last; i++){
    				Row row = sheet.getRow(i);
    				WorkDataVO workData = new WorkDataVO();
    				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
    				String workDataType = row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
    				if( workDataType != null){
    					workData.setType(workDataType);
    					if(row.getCell(1, Row.CREATE_NULL_AS_BLANK) != null
    							&& (row.getCell(1, Row.CREATE_NULL_AS_BLANK).toString() != null
	    							&& !row.getCell(1, Row.CREATE_NULL_AS_BLANK).toString().equals("N")
	    							&& !row.getCell(1, Row.CREATE_NULL_AS_BLANK).toString().equals("")
    								)
    							) {
								workData.setDate(sdf.format(row.getCell(1, Row.CREATE_NULL_AS_BLANK).getDateCellValue()));
    					} else {
    						workData.setDate(sdf.format(new Date()));
    					}
    					workData.setCompany(row.getCell(2, Row.CREATE_NULL_AS_BLANK).toString());
    					workData.setOwner(row.getCell(3, Row.CREATE_NULL_AS_BLANK).toString());
    					workData.setAddress(row.getCell(4, Row.CREATE_NULL_AS_BLANK).toString());
    					workData.setProductname(row.getCell(5, Row.CREATE_NULL_AS_BLANK).toString());
    					workData.setVersion(row.getCell(6, Row.CREATE_NULL_AS_BLANK).toString());
    					if(row.getCell(7, Row.CREATE_NULL_AS_BLANK).toString() != null 
							&& !row.getCell(7, Row.CREATE_NULL_AS_BLANK).toString().equals("")){
								if(!row.getCell(7, Row.CREATE_NULL_AS_BLANK).toString().equals("N")){
									workData.setNumber(""+(int)Double.parseDouble(row.getCell(7, Row.CREATE_NULL_AS_BLANK).toString()));
								}
		    					else {
		    						workData.setNumber(row.getCell(7, Row.CREATE_NULL_AS_BLANK).toString());
		    					}
    					}else {
    						workData.setNumber("N");
    					}
    					workData.setLicense(row.getCell(8, Row.CREATE_NULL_AS_BLANK).toString());
    					workData.setSeller(row.getCell(9, Row.CREATE_NULL_AS_BLANK).toString());
    					workData.setSerial(row.getCell(10, Row.CREATE_NULL_AS_BLANK).toString());
    					
    					workData.setCompany(
    							workData.getCompany().replaceAll("\n", "").replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("'", "`"));
    					workData.setOwner(
    							workData.getOwner().replaceAll("\n", "").replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("'", "`"));
    					workData.setAddress(
    							workData.getAddress().replaceAll("\n", "").replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("'", "`"));
    					workData.setProductname(
    							workData.getProductname().replaceAll("\n", "").replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("'", "`"));
    					
    					workDataList.add(workData);
    				}					
    			}				
    		}
    		Map<String,Object> paramMap= new HashMap<String,Object>();
    		paramMap.put("list", workDataList);
    		workService.insertExcelData(paramMap);
			PMDUtil.sendResponse(response, "true");
		}catch(Exception e){
			PMDUtil.sendResponse(response, "false");
			e.printStackTrace();
		}
    }	
    
    /*********************************************************************************************************************************************************
     * 업체정보관리 페이지																																								*
     * @param commandMap																																							*
     * @return																																											*
     * @throws Exception																																								*
     *********************************************************************************************************************************************************/
    @RequestMapping(value="/web/work/companiesInfo.do")
    public ModelAndView openCompaniesPage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/work/companies");
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
    	if(userInfo == null || !userInfo.getUserPmss().equals("M")){
    		////////// 로그인 실패 //////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{
    		////////// 로그인 성공 //////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    		String userKeyword= request.getParameter("userKeyword");
    		Map<String,Object> paramMap= new HashMap<String,Object>();
    		
    		///// 사용자 목록 /////
    		ArrayList<UserInfoVO> userList= null;
    		if(userKeyword != null && userKeyword.equals("")){ // 검색어가 있다면 검색결과를, 없다면 전체결과를 반환
    			userList= workService.getUserList();
    			if(userList==null || userList.size()==0){
        			userList= new ArrayList<UserInfoVO>();
        			UserInfoVO temp= new UserInfoVO();
        			temp.setUserCoName("    데이터가 없습니다.    ");
        			userList.add(temp);
        		}
    		} else {
    			paramMap.put("userKeyword", userKeyword);
    			userList= workService.getSearchUserList(paramMap);
    			if(userList==null || userList.size()==0){
        			userList= new ArrayList<UserInfoVO>();
        			UserInfoVO temp= new UserInfoVO();
        			temp.setUserCoName("    검색결과가 없습니다.    ");
        			userList.add(temp);
        		}
    		}
    		session.setAttribute("userList", userList);
    		
    		///// 미분류 소프트웨어 목록 /////
    		ArrayList<SoftwareInfoVO> swList= workService.getRecentInstalledSw(paramMap); // 이미 유료/무료 등록된 소프트웨어는 제외, 무료 소프트웨어는 소프트웨어 명만 적는걸로.
    		ArrayList<SoftwareInfoVO> freeSwList= workService.getFreeSoftwareList(paramMap);
    		ArrayList<SoftwareInfoVO> chargedList= infoService.getChargedSoftware(paramMap);
    		
    		swList= pmd.excludeSoftwareBySwName(swList, freeSwList);
    		swList= pmd.excludeSoftwareBySwName(swList, chargedList);
    		    		
    		session.setAttribute("swList", swList);
    		/*--------------------------------------------------------------------------*/
    		/*						 	-- 기능 구현 부분							*/
    		/*--------------------------------------------------------------------------*/
    	} 
    	/*----------------------------------------------*/
    	/*				-- 로그인 체크	 			*/
    	/*----------------------------------------------*/
        return mv;
    } 
    
    /******************************************************************************************************************************************************
     * 업체정보관리 페이지 > 특정 업체 조회																																	*
     * @param commandMap																																						*
     * @return																																										*
     * @throws Exception																																							*
     ******************************************************************************************************************************************************/
    @RequestMapping(value="/web/work/companyInfo.do")
    public ModelAndView openCompanyPage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/work/company");
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
    	if(userInfo == null || !userInfo.getUserPmss().equals("M")){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    		///// 파라미터 맵 생성 /////
    		Map<String,Object> paramMap= new HashMap<String,Object>();
    		
    		///// 세션에서 companyId 속성을 가져온 후 초기화 /////
    		String companyId= request.getParameter("companyId");
    		
    		if(companyId==null || companyId.equals("")) {
    			///// companyId 속성이 없는 경우 /////
    			companyId= (String)session.getAttribute("companyId");
    		}
    			
    		if(companyId!=null && !companyId.equals("")){
    			///// companyId 속성이 있는 경우 /////
	    		session.setAttribute("companyId", companyId);
    			paramMap.put("userId", companyId);
	    		
	    		///// PC 이름 목록을 위해 미리 선언 후 사용 /////
	    		ArrayList<SoftwareInfoVO> allPcInfo= infoService.getUserPcList(paramMap);
	    		
	    		///// PC 이름 목록 작성 /////
	    		ArrayList<String> nameList= new ArrayList<String>();
	    		boolean exist= false;
	    		for(SoftwareInfoVO p:allPcInfo){
	    			for(String name:nameList){
	    				if(p.getPcName().equals(name)) exist= true;
	    			}
	    			if(!exist) nameList.add(p.getPcName());
	    			exist=false;
	    		}
	    		
	    		///// pcName 파라미터가 넘어왔는지, 무슨값으로 넘어왔는지 확인 /////
	    		String pcName= request.getParameter("pcName");
	    		
	    		///// pcName 값이 null이거나 ALL이라면 빈칸으로 바꿔줌 /////
	    		if(pcName == null) pcName= "";
	    		else if(pcName.equals("ALL")) pcName="";
	    		
	    		///// 전체 pc 목록 조회하기 /////
	    		ArrayList<SoftwareInfoVO> chargedList= infoService.getChargedSoftware(paramMap);
	    		ArrayList<SoftwareInfoVO> pcInfoList= null;
	    		
	    		if(!pcName.equals("")){
	    			///// pcName 값이 있는 경우 -> 특정 PC의 소프트웨어 목록 /////
	    			paramMap.put("pcName", pcName);
	    			allPcInfo= infoService.getUserPcListByPk(paramMap);
	    		}
	    		
	    		///// 유료 프로그램만 보이도록 필터링 /////
    			pcInfoList= pmd.includeSoftwareBySwName(allPcInfo, chargedList);
    			
    			///// 세션에 데이터 등록 /////
    			session.setAttribute("dataList", pcInfoList);
	    		session.setAttribute("nameList", nameList);
	    		
    		} else {
    			///// companyId 속성이 없는 경우 /////
    			
    			///// PC 설치 소프트웨어 목록 /////
    			ArrayList<PcInfoVO> noData1= new ArrayList<PcInfoVO>();
    			PcInfoVO temp= new PcInfoVO();
    			temp.setPcName("데이터 없음");
    			noData1.add(temp);
    			
    			///// PC 이름 목록 /////
    			ArrayList<String> noData2= new ArrayList<String>();
    			noData2.add("데이터 없음");
    			
    			///// 세션에 데이터 등록 /////
    			session.setAttribute("dataList", noData1);
    			session.setAttribute("nameList", noData2);
    			
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
    
    /*****************************************************************************************************************
     * 업체정보관리 페이지 > 유료 소프트웨어 등록 > 등록페이지																*
     * @param commandMap																											*
     * @return																															*
     * @throws Exception																												*
     *****************************************************************************************************************/	
    @RequestMapping(value="/web/work/regChargedSwPage.do")
    public ModelAndView openRegChargedSwPage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/work/regChargedSw");
    	/*----------------------------------------------------------------*/
    	/*					-- 기본 반환 페이지 설정				  */
    	/*----------------------------------------------------------------*
    	
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
    	if(userInfo == null || !userInfo.getUserPmss().equals("M")){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    		
    		///// 파라미터 정보 가져오기 /////
    		String swName= request.getParameter("swName");
    		String swFile= request.getParameter("swFile");
    		String manual= request.getParameter("manual");
    		if(manual == null) manual= "";
    		
    		if( manual.equals("true") || ( swName != null && !swName.equals("") ) ){
    			///// swName 값이 넘어온 경우 /////
    			if( manual.equals("true") ){
    				mv.setViewName("/work/regChargedSw2");
    			} else {
    				mv.addObject("swName", swName);
	    			mv.addObject("swFile", swFile);
    			}
    		}else{
    			///// swName 값이 넘어오지 않은 경우 /////
    			mv.addObject("servletMessage","비정상적인 접근입니다.");
    			mv.setViewName("/work/companies");
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
     * 업체정보관리 페이지 > 유료 소프트웨어 등록 > 등록페이지 > 등록									*
     * @param commandMap																						*
     * @return																										*
     * @throws Exception																							*
     ************************************************************************************************/
    @RequestMapping(value="/web/work/regChargedSw.do")
    public ModelAndView openRegChargedSw(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/work/companies");
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
    	if(userInfo == null || !userInfo.getUserPmss().equals("M")){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    	
    		///// 파라미터에서 데이터 가져오기 /////
    		String swName= request.getParameter("swName");
    		String swVendor= request.getParameter("swVendor");
    		String swFile= request.getParameter("swFile");
    		String swVersion= request.getParameter("swVersion");
    		String swVendorKr= request.getParameter("swVendorKr");
    		
    		///// 파라미터 맵 작성 /////
    		Map<String,Object> paramMap= new HashMap<String,Object>();
    		
    		if(swName!=null && !swName.equals("")){
    			///// swName 값이 있다면 다른 값들도 들어왔다고 판단, 파라미터 맵에 정보 추가 /////
    			paramMap.put("swName", swName);
    			paramMap.put("swVendor", swVendor);
    			paramMap.put("swFile", swFile);
    			paramMap.put("swVersion", swVersion);
    			paramMap.put("swVendorKr", swVendorKr);
    			
    			///// 유료 소프트웨어 정보 등록 /////
    			workService.addChargedSoftware(paramMap);
    			response.sendRedirect(PMDUtil.PMD_URL+"/web/work/companiesInfo.do");
    			
    		}else{
    			///// swName 값이 없다면 다른 값들도 없다고 판단, 예외처리 /////
    			
    			mv.addObject("servletMessage","비정상적인 접근입니다.");
    			mv.setViewName("/work/companies");
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
     * 업체정보관리 페이지 > 무료 소프트웨어 등록															*
     * @param commandMap																						*
     * @return																										*
     * @throws Exception																							*
     ************************************************************************************************/
    @RequestMapping(value="/web/work/regFreeSw.do")
    public ModelAndView openRegFreeSw(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/work/companies");
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
    	if(userInfo == null || !userInfo.getUserPmss().equals("M")){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    	
    		///// 파라미터 정보 가져오기 /////
    		String swName= request.getParameter("swName");
    		
    		if(swName!=null && !swName.equals("")){
    			///// swName 값이 있는 경우 아래 작업 진행 /////
    			
    			Map<String,Object> paramMap= new HashMap<String,Object>();
    			paramMap.put("swName", swName);
    			
    			workService.addFreeSoftware(paramMap);
    			response.sendRedirect(PMDUtil.PMD_URL+"/web/work/companiesInfo.do");
    			
    		}else{
    			///// swName 값이 없는 경우 예외처리 /////
    			mv.addObject("servletMessage","비정상적인 접근입니다.");
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
    
    
    /****************************************************************************************************
     * 만료임박제품 관리 페이지																						*
     * @param commandMap																							*
     * @return																											*
     * @throws Exception																								*
     ****************************************************************************************************/
    @RequestMapping(value="/web/work/expiryManage.do")
    public ModelAndView expiryManage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/work/expiryManage");
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
    	if(userInfo == null || !userInfo.getUserPmss().equals("M")){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    	
    		Map<String,Object> paramMap= new HashMap<String,Object>();
    		ArrayList<ExpiryManageVO> ownedList= workService.getOwnedSoftware(paramMap);
    		ArrayList<ExpiryManageVO> expiryList= new ArrayList<ExpiryManageVO>();
    		
    		///// 만료 및 만료 임박 제품 필터링 /////
    		Date nowDate= new Date();
    		Date expiryDate= null;
    		Long longDiffDays= 0L;
    		int diffDays= 0;
    		
    		for(ExpiryManageVO s: ownedList){
    			if(!s.getOwnExpDate().equals("")){
	    			expiryDate= pmd.stringToDate(s.getOwnExpDate());
	    			longDiffDays= ((expiryDate.getTime()-nowDate.getTime())/(24*60*60*1000));
	    			diffDays= longDiffDays.intValue();
	    			if( diffDays<30 ){
	    				if(diffDays<0){
	    					s.setOwnExpDate("기간 만료 ("+s.getOwnExpDate()+")");
	    				} else {
	    					s.setOwnExpDate(diffDays+"일 ("+s.getOwnExpDate()+")");
	    				}
	    				expiryList.add(s);
	    			}
    			}
    		}
    		
    		///// 데이터가 없는 경우 /////
    		if(expiryList.size()<=0){
    			ExpiryManageVO temp= new ExpiryManageVO();
    			temp.setSwName("데이터가 없습니다.");
    			expiryList.add(temp);
    		}
    		
    		session.setAttribute("expiryList", expiryList);
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
     * 직원 계정 전환 페이지																						*
     * @param commandMap																						*
     * @return																										*
     * @throws Exception																							*
     ************************************************************************************************/
    @RequestMapping(value="/web/work/regWorkerPage.do")
    public ModelAndView regWorkerPage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/work/regWorker");
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
    	if(userInfo == null || !userInfo.getUserPmss().equals("M")){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/

    		///// 전체 사용자 목록 조회 /////
    		ArrayList<UserInfoVO> userList= workService.getUserList();

    		///// 데이터가 없는 경우 예외처리 /////
    		if(userList.size()<=0){
    			UserInfoVO temp= new UserInfoVO();
    			temp.setUserName("데이터가 없습니다.");
    			userList.add(temp);
    		}
    		
    		///// 세션에 사용자 목록 넣기 /////
    		session.setAttribute("userList", userList);
    		
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
     * 직원 계정 전환																								*
     * @param commandMap																						*
     * @return																										*
     * @throws Exception																							*
     ************************************************************************************************/
    @RequestMapping(value="/web/work/regWorker.do")
    public ModelAndView regWorker(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/work/regWorker");
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
    	if(userInfo == null || !userInfo.getUserPmss().equals("M")){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/

    		///// userId 값을 파라미터에서 가져옴 /////
    		String userId= request.getParameter("userId");
    		
    		///// 파라미터 맵 생성 후 userId 등록 /////
    		Map<String,Object> paramMap= new HashMap<String,Object>();
    		paramMap.put("userId", userId);
    		
    		workService.setMsoftAccount(paramMap);
    		
    		///// 새로운 목록을 보여주기 위해 userList를 다시 받음 /////
    		ArrayList<UserInfoVO> userList= workService.getUserList();
    		
    		mv.addObject("servletMessage",userId+"계정이 직원 계정으로 전환되었습니다.");
    		session.setAttribute("userList", userList);
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
     * 코디 이용기간 관리 페이지																				*
     * @param commandMap																					*
     * @return																									*
     * @throws Exception																						*
     *********************************************************************************************/
    @RequestMapping(value="/web/work/pmdManage.do")
    public ModelAndView pmdManage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/work/pmdManage");
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
    	if(userInfo == null || !userInfo.getUserPmss().equals("M")){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/

    		///// 전체 사용자들의 코디 만료일자 조회 /////
    		ArrayList<UserInfoVO> rawUserList= workService.getUserExpList();
    		
    		///// 페이지로 반환할 리스트 생성 /////
    		ArrayList<UserInfoVO> userList= new ArrayList<UserInfoVO>();
    		
    		Date nowDate= new Date();
    		Date expiryDate= null;
    		Long longDiffDays= 0L;
    		int diffDays= 0;
    		
    		///// 페이지로 반환할 리스트 채우기 /////
    		for(UserInfoVO u: rawUserList){
    			
    			///// 영구 라이선스 제외 /////
    			if(!u.getUserExpiryDate().equals("")){
	    			expiryDate= pmd.stringToDate(u.getUserExpiryDate());
	    			longDiffDays= ((expiryDate.getTime()-nowDate.getTime())/(24*60*60*1000));
	    			diffDays= longDiffDays.intValue();

	    			///// 기간 만료 / 잔여기간 30일 미만 / 잔여기간 30일 이상 3가지로 나누어 표시 /////
	    			if( diffDays<30 ){
	    				if(diffDays<0){
	    					///// 기간 만료 /////
	    					u.setUserRegDate(u.getUserExpiryDate());
	    					u.setUserExpiryDate("<span style=\"color:red;\">기간 만료 ("+u.getUserExpiryDate()+")</span>");
	    				} else {
	    					///// 잔여기간 30일 미만 /////
	    					u.setUserRegDate(u.getUserExpiryDate());
	    					u.setUserExpiryDate("<span style=\"color:red;\">"+diffDays+"일 ("+u.getUserExpiryDate()+")</span>");
	    				}
	    			} else {
	    				///// 잔여기간 30일 이상 /////
	    				u.setUserRegDate(u.getUserExpiryDate());
    					u.setUserExpiryDate("<span style=\"color:white;\">"+diffDays+"일 ("+u.getUserExpiryDate()+")</span>");
	    			}
	    			userList.add(u);
    			}
    		}
    		
    		session.setAttribute("userList", userList);
    		/*--------------------------------------------------------------------------*/
    		/*						 	-- 기능 구현 부분							*/
    		/*--------------------------------------------------------------------------*/
    	}
    	/*----------------------------------------------*/
    	/*				-- 로그인 체크	 			*/
    	/*----------------------------------------------*/
    	return mv;
    } 
    
    /****************************************************************************************************
     * 코디 이용기간 관리 페이지 > 연장 페이지																	*
     * @param commandMap																							*
     * @return																											*
     * @throws Exception																								*
     ****************************************************************************************************/
    @RequestMapping(value="/web/work/extendPage.do")
    public ModelAndView extendPage(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/work/extendPage");
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
    	if(userInfo == null || !userInfo.getUserPmss().equals("M")){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    	
    		///// 파라미터에서 userId, userExpiryDate 정보 가져오기 /////
    		String userId= request.getParameter("userId");
    		String userExpiryDate= request.getParameter("userExpiryDate");
    		
    		///// 세션에 파라미터에서 받아온 정보 담기 /////
    		session.setAttribute("userId", userId);
    		session.setAttribute("userExpiryDate", userExpiryDate);
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
     * 코디 이용기간 관리 페이지 > 연장 페이지 > 연장														*
     * @param commandMap																						*
     * @return																										*
     * @throws Exception																							*
     ************************************************************************************************/
    @RequestMapping(value="/web/work/pmdExtend.do")
    public ModelAndView pmdExtend(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
    	/*----------------------------------------------------------------*/
    	/*					기본 반환 페이지 설정 --				  */
    	/*----------------------------------------------------------------*/
    	ModelAndView mv = new ModelAndView("/work/pmdManage");
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
    	if(userInfo == null || !userInfo.getUserPmss().equals("M")){	
    		////////////// 로그인실패 //////////////
    		mv.setViewName("/main/login");
    		response.sendRedirect(PMDUtil.PMD_URL);
    		
    	}else{	
    		////////////// 로그인 성공 //////////////
    		/*--------------------------------------------------------------------------*/
    		/*						 	기능 구현 부분 --							*/
    		/*--------------------------------------------------------------------------*/
    	
    		///// 파라미터에서 정보 가져오기 /////
    		String userId= request.getParameter("userId");
    		String fromExpiryDate= request.getParameter("fromExpiryDate");
    		String toExpiryDate= request.getParameter("toExpiryDate");
    		
    		///// 쿼리에 넣을 파라미터 맵 생성 후 정보 추가 /////
    		Map<String,Object> paramMap= new HashMap<String,Object>();
    		paramMap.put("userId", userId);
    		paramMap.put("workerId", userInfo.getUserId());
    		paramMap.put("fromExpiryDate", fromExpiryDate);
    		paramMap.put("toExpiryDate", toExpiryDate);
    		
    		///// 작업 수행 /////
    		workService.setCoordyExpiryDate(paramMap);
    		workService.insertCoordyExpHist(paramMap);
    		
    		mv.addObject("servletMessage", "연장되었습니다.");
    		response.sendRedirect(PMDUtil.PMD_URL+"/web/work/pmdManage.do");
    	} // --로그인 체크
        return mv;
    }
}