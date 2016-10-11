package pmd.app.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pmd.app.service.AppProtocolService;
import pmd.common.common.CommandMap;
import pmd.common.common.PMDUtil;

@Controller
public class AppProtocolController {
	
	PMDUtil pmd= new PMDUtil();
		
	@Resource(name="appProtocolService")
	private AppProtocolService appProtocolService;
	
	/*******************************************************************************************************
	 * 어플리케이션 - 회원가입																							*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@RequestMapping(value="/app/join.do")
	public ModelAndView appJoin(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
		// 도입 로그
		pmd.logging("[APP] /app/join.do");
		
		// 반환 페이지 설정
		ModelAndView mv= new ModelAndView("external/test");
		
		// 파라미터 체크
		HashMap<String,Object> paramMap= pmd.getParameter(commandMap);
		
		// 서비스로부터 데이터를 받고 그대로 반환 (JSON 문자열 형태로 옴)
		String result= appProtocolService.appJoin(paramMap);
		
		// 반환 페이지에 데이터 삽입
		mv.addObject("result", result);
		return mv;
	}
	
	/*******************************************************************************************************
	 * 어플리케이션 - 로그인																								*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@RequestMapping(value="/app/login.do")
	public ModelAndView appLogin(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
		// 도입 로그
		pmd.logging("[APP] /app/login.do");
		
		// 반환 페이지 설정
		ModelAndView mv= new ModelAndView("external/test");
		
		// 파라미터 체크
		HashMap<String,Object> paramMap= pmd.getParameter(commandMap);
		
		// 서비스로부터 데이터를 받고 그대로 반환 (JSON 문자열 형태로 옴)
		String result= appProtocolService.appLogin(paramMap);
		
		// 반환 페이지에 데이터 삽입
		mv.addObject("result", result);
		return mv;
	}
	
	/*******************************************************************************************************
	 * 어플리케이션 - 아이디/비밀번호 찾기																				*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@RequestMapping(value="/app/findAccount.do")
	public ModelAndView appFindAccount(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
		// 도입 로그
		pmd.logging("[APP] /app/findAccount.do");
		
		// 반환 페이지 설정
		ModelAndView mv= new ModelAndView("external/test");
		
		// 파라미터 체크
		HashMap<String,Object> paramMap= pmd.getParameter(commandMap);
		
		// 서비스로부터 데이터를 받고 그대로 반환 (JSON 문자열 형태로 옴)
		String result= appProtocolService.appFindAccount(paramMap);
		
		// 반환 페이지에 데이터 삽입
		mv.addObject("result", result);
		return mv;
	}
	
	/*******************************************************************************************************
	 * 어플리케이션 - 요약정보																							*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@RequestMapping(value="/app/graph.do")
	public ModelAndView appGraph(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
		// 도입 로그
		pmd.logging("[APP] /app/graph.do");
		
		// 반환 페이지 설정
		ModelAndView mv= new ModelAndView("external/test");
		
		// 파라미터 체크
		HashMap<String,Object> paramMap= pmd.getParameter(commandMap);
		
		// 서비스로부터 데이터를 받고 그대로 반환 (JSON 문자열 형태로 옴)
		String result= appProtocolService.appGraph(paramMap);
		
		// 반환 페이지에 데이터 삽입
		mv.addObject("result", result);
		return mv;
	}
	
	/*******************************************************************************************************
	 * 어플리케이션 - 사용자목록, 사용현황																				*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@RequestMapping(value="/app/status.do")
	public ModelAndView appStatus(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
		// 도입 로그
		pmd.logging("[APP] /app/status.do");
		
		// 반환 페이지 설정
		ModelAndView mv= new ModelAndView("external/test");
		
		// 파라미터 체크
		HashMap<String,Object> paramMap= pmd.getParameter(commandMap);
		
		// 서비스로부터 데이터를 받고 그대로 반환 (JSON 문자열 형태로 옴)
		String result= appProtocolService.appStatus(paramMap);
		
		// 반환 페이지에 데이터 삽입
		mv.addObject("result", result);
		return mv;
	}
	
	/*******************************************************************************************************
	 * 어플리케이션 - 정품/복제 소프트웨어 토글 버튼																	*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@RequestMapping(value="/app/toggleSoftware.do")
	public ModelAndView appToggleSoftware(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
		// 도입 로그
		pmd.logging("[APP] /app/toggleSoftware.do");
		
		// 반환 페이지 설정
		ModelAndView mv= new ModelAndView("external/test");
		
		// 파라미터 체크
		HashMap<String,Object> paramMap= pmd.getParameter(commandMap);
		
		// 서비스로부터 데이터를 받고 그대로 반환 (JSON 문자열 형태로 옴)
		String result= appProtocolService.appToggleSoftware(paramMap);
		
		// 반환 페이지에 데이터 삽입
		mv.addObject("result", result);
		return mv;
	}

	/*******************************************************************************************************
	 * 어플리케이션 - 보유 소프트웨어 목록 (미구매 제품 포함)														*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@RequestMapping(value="/app/productList.do")
	public ModelAndView appProductList(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
		// 도입 로그
		pmd.logging("[APP] /app/productList.do");
		
		// 반환 페이지 설정
		ModelAndView mv= new ModelAndView("external/test");
		
		// 파라미터 체크
		HashMap<String,Object> paramMap= pmd.getParameter(commandMap);
		
		// 서비스로부터 데이터를 받고 그대로 반환 (JSON 문자열 형태로 옴)
		String result= appProtocolService.appProductList(paramMap);
		
		// 반환 페이지에 데이터 삽입
		mv.addObject("result", result);
		return mv;
	}
	
	/*******************************************************************************************************
	 * 어플리케이션 - 보유 소프트웨어 등록 (주어진 목록에서)														*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@RequestMapping(value="/app/addProduct.do")
	public ModelAndView appAddProduct(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
		// 도입 로그
		pmd.logging("[APP] /app/addProduct.do");
		
		// 반환 페이지 설정
		ModelAndView mv= new ModelAndView("external/test");
		
		// 파라미터 체크
		HashMap<String,Object> paramMap= pmd.getParameter(commandMap);
		
		// 서비스로부터 데이터를 받고 그대로 반환 (JSON 문자열 형태로 옴)
		String result= appProtocolService.appAddProduct(paramMap);
		
		// 반환 페이지에 데이터 삽입
		mv.addObject("result", result);
		return mv;
	}
	
	/*******************************************************************************************************
	 * 어플리케이션 - 보유 소프트웨어 정보 수정																		*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@RequestMapping(value="/app/modProduct.do")
	public ModelAndView appModProduct(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
		// 도입 로그
		pmd.logging("[APP] /app/modProduct.do");
		
		// 반환 페이지 설정
		ModelAndView mv= new ModelAndView("external/test");
		
		// 파라미터 체크
		HashMap<String,Object> paramMap= pmd.getParameter(commandMap);
		
		// 서비스로부터 데이터를 받고 그대로 반환 (JSON 문자열 형태로 옴)
		String result= appProtocolService.appModProduct(paramMap);
		
		// 반환 페이지에 데이터 삽입
		mv.addObject("result", result);
		return mv;
	}
	
	/*******************************************************************************************************
	 * 어플리케이션 - 보유 소프트웨어 정보 삭제																		*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@RequestMapping(value="/app/delProduct.do")
	public ModelAndView appDelProduct(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
		// 도입 로그
		pmd.logging("[APP] /app/delProduct.do");
		
		// 반환 페이지 설정
		ModelAndView mv= new ModelAndView("external/test");
		
		// 파라미터 체크
		HashMap<String,Object> paramMap= pmd.getParameter(commandMap);
		
		// 서비스로부터 데이터를 받고 그대로 반환 (JSON 문자열 형태로 옴)
		String result= appProtocolService.appDelProduct(paramMap);
		
		// 반환 페이지에 데이터 삽입
		mv.addObject("result", result);
		return mv;
	}
	
	/*******************************************************************************************************
	 * 어플리케이션 - 여러 소프트웨어 견적 요청 (key: estimate)														*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@RequestMapping(value="/app/estimate.do")
	public ModelAndView appEstimate(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
		// 도입 로그
		pmd.logging("[APP] /app/estimate.do");
		
		// 반환 페이지 설정
		ModelAndView mv= new ModelAndView("external/test");
		
		// 파라미터 체크
		HashMap<String,Object> paramMap= pmd.getParameter(commandMap);
		
		// 서비스로부터 데이터를 받고 그대로 반환 (JSON 문자열 형태로 옴)
		String result= appProtocolService.appEstimate(paramMap);
		
		// 반환 페이지에 데이터 삽입
		mv.addObject("result", result);
		return mv;
	}
	
	/*******************************************************************************************************
	 * 어플리케이션 - 소프트웨어 검색																					*
	 * @param commandMap																								*
	 * @return																												*
	 * @throws Exception																									*
	 *******************************************************************************************************/
	@RequestMapping(value="/app/search.do")
	public ModelAndView appSearch(HttpServletRequest request, HttpServletResponse response, CommandMap commandMap) throws Exception{
		// 도입 로그
		pmd.logging("[APP] /app/search.do");
		
		// 반환 페이지 설정
		ModelAndView mv= new ModelAndView("external/test");
		
		// 파라미터 체크
		HashMap<String,Object> paramMap= pmd.getParameter(commandMap);
		
		// 서비스로부터 데이터를 받고 그대로 반환 (JSON 문자열 형태로 옴)
		String result= appProtocolService.appSearch(paramMap);
		
		// 반환 페이지에 데이터 삽입
		mv.addObject("result", result);
		return mv;
	}
}