package kr.or.ddit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.service.EmpService;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.vo.EmpVO;
import lombok.extern.slf4j.Slf4j;

// 프링아 이거 컨트롤러야. 자바빈으로 등록해서 관리해줘
// "value = "는 속성이 1개일 땐 속성명 생략 가능
@Slf4j
@RequestMapping("/emp")
@Controller
public class EmpController {
	
	@Autowired
	EmpService empService;
	
	// void로 응답
	// 요청 URI : /emp/create
	// 요청 방식 : post
	@GetMapping("/create")
	public void create(@ModelAttribute EmpVO empVO, Model model) {
		log.info("create()에 왔다");
		
		String empNum = this.empService.getEmpNum();
		empVO.setEmpNum(empNum);
		
		model.addAttribute("empNum", empNum);
		
		// forwarding
//		return "emp/create";
	}
	
	// 신규 직원번호 가져오기(Ajax)
	// 요청 URI : /emp/getEmpNum
	// 요청 방법: post
	// ResponseBody : JSON데이터로 리턴
	@ResponseBody
	@PostMapping("/getEmpNum")
	public String getEmpNum() {
		String empNum = this.empService.getEmpNum();
		
		return empNum;
	}
	
	// 신규 직원 등록하기
	/*
	요청 URI : /emp/createPost
	요청 파라미터 : {empNum=EMP001, zipCode=1234, address=대전...}
	요청 방식 : post
	*/
	@PostMapping("/createPost")
	public String createPost(@Valid @ModelAttribute EmpVO empVO,
			String zipCode, String address, String detAddress, 
			@RequestParam(required=false,defaultValue="new") String mode, 
			Errors errors) {
		if(errors.hasErrors()) {
			// forwarding
			return "emp/create";
		}
		
		log.info("empVO : " + empVO);
		log.info("zipCode : " + zipCode + "address : " + address + ", detAddress" + detAddress);
		//mode
		//- create에서 오면 new
		//- detail에서 오면 update
		log.info("mode : " + mode);
		
		String oldEmpNum = "";
		if(mode.equals("update")) {
			oldEmpNum = empVO.getEmpNum(); //기본키 데이터 백업
		}
		
		if(errors.hasErrors()) {
			//forwarding
			return "emp/create";
		}
		
		// 우편번호 + 주소 + 상세주소 => empVO의 empAddr 멤버변수에 setting하기
		String empAddr = zipCode + " " + address + " " + detAddress;
		empVO.setEmpAddr(empAddr);
		
		int result = this.empService.createPost(empVO);
		log.info("result : " + result);
		
		// 입력성공 : 상세보기로 redirect. 기본키 데이터를 파라미터로 보냄
//		return "redirect:/emp/detail?empNum=" + empVO.getEmpNum();
		// update
		if(mode.equals("update")) {
			return "redirect:/emp/detail?empNum="+oldEmpNum;
		}else {
			//insert
			return "redirect:/emp/detail?empNum="+empVO.getEmpNum();
		}
	}
	
	/*
	 요청 URI : /emp/getEmpAll
	 요청방식 : post
	 dataType : json
	*/
	@ResponseBody
	@PostMapping("/getEmpAll")
	public List<EmpVO> getEmpAll(){
		// 모든 직원 정보 가져오기
		List<EmpVO> empVOList = this.empService.getEmpAll();
		
		log.info("empVOList : " + empVOList);
		
		return empVOList;
	}
	
	// 요청 URI : /emp/detail?empNum=EMP003
	// 요청 파라미터 : empNum=EMP003
	// 요청 방식 : get
	@GetMapping("/detail")
	public String detail(@RequestParam String empNum, @ModelAttribute EmpVO empVO, 
				Model model) {
		log.info("empNum : " + empNum);
		log.info("empVO : " + empVO);
		
		// 상세화면은 등록화면과 동일 + empVO데이터로 채우면 됨
		List<EmpVO> empVOList = this.empService.detail(empVO);
		log.info("empVOList : " + empVOList);
		
		model.addAttribute("data", empVOList);
		
		// emp폴더의 detail.jsp를 forwarding
		return "emp/detail";
	}
	
	/*
	요청 URI : /emp/deletePost
	요청 파라미터 : let data = {"empNum" : empNum}
	요청 방식 : post 
	응답 데이터 타입 : json
	응답 데이터 : {"result" : 1}
	*/
	@ResponseBody
	@PostMapping("/deletePost")
	public Map<String, Integer> deletePost(@RequestBody EmpVO empVO) {
		log.info("empVO : " + empVO);
		
		int result = this.empService.deletePost(empVO);
		
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("result", result);
		
		return resultMap; 
	}
	
	/*
	요청 URI : /emp/list
	요청 방식 : get
	요청 파라미터 : show=10&keyword=팬더
	
	map은	RequestParam
	vo는	ModelAttribute
	json은	RequestBody
	*/
	@GetMapping("/list")
	public String list(@RequestParam(required = false) Map<String, String> map, 
				@RequestParam(required = false, defaultValue = "1") int currentPage,
				@RequestParam(value = "show", required = false, defaultValue = "10") int size,
				Model model){
		log.info("map : " + map);
		map.put("currentPage", currentPage + "");
		
		if(map.get("show") == null || map.get("show").length() < 1) {
			map.put("show", "10");
		}
		
		// Map으로 파라미터를 받아서 매퍼xml에서 검색 조건으로 사용
		List<EmpVO> empVOList = this.empService.list(map);
		log.info("empVOList : " + empVOList);
		
		// empVOList 객체를 페이징 처리해보자
		// new ArticlePage<EmpVO>(total, currentPage, size, blockSize, content)
		int total = this.empService.getTotal(map);
		
		ArticlePage<EmpVO> articlePage 
			= new ArticlePage<EmpVO>(total, currentPage, size, 3, empVOList);
		
		model.addAttribute("data", articlePage);
		
		// forwarding
		return "emp/list";
	}
	
	/* 직원 1명의 정보를 리턴
	요청 URI : /emp/showMj
	요청 파라미터 : let empNum = $(this).data("num");
	요청방식 : post
	응답 데이터 : json
	*/
	@ResponseBody
	@PostMapping("/showMj")
	public EmpVO showMj(@RequestBody EmpVO empVO) {
		log.info("empVO : " + empVO);
		EmpVO emp = this.empService.showMj(empVO);
		
		return emp;
	}
}
