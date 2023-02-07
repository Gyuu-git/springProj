package kr.or.ddit.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.service.EmpService;
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
			String zipCode, String address, String detAddress, Errors errors) {
		if(errors.hasErrors()) {
			// forwarding
			return "emp/create";
		}
		
		log.info("empVO : " + empVO);
		log.info("zipCode : " + zipCode + "address : " + address + ", detAddress" + detAddress);
		
		// 우편번호 + 주소 + 상세주소 => empVO의 empAddr 멤버변수에 setting하기
		String empAddr = zipCode + " " + address + " " + detAddress;
		empVO.setEmpAddr(empAddr);
		
		int result = this.empService.createPost(empVO);
		log.info("result : " + result);
		
		// 입력성공 : 상세보기로 redirect. 기본키 데이터를 파라미터로 보냄
		return "redirect:/emp/detail?empNum=" + empVO.getEmpNm();
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
	
	/* 컨트롤러 메서드 매개변수
	- Model
	- RedirectAttributes
	- 자바빈즈 클래스
	- MultipartFile
	- BindingResult
	- java.util.Locale
	- java.security.Principal
	*/
	// 요청 URI : /cus/detail?cusNum=CUS003
	@GetMapping("/detail")
	public String detail(Model model, RedirectAttributes ras, @ModelAttribute EmpVO empVO) {
		
		// forwarding
		return "emp/detail";
	}
}
