package kr.or.ddit.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.service.CusService;
import kr.or.ddit.vo.CusVO;
import lombok.extern.slf4j.Slf4j;

// 프링아 이거 컨트롤러야. 자바빈으로 등록해서 관리해줘
// "value = "는 속성이 1개일 땐 속성명 생략 가능
@Slf4j
@RequestMapping("/cus")
@Controller
public class CusController {
	
	@Autowired
	CusService cusService;
	
	// void로 응답
	// 요청 URI : /cus/create
	@GetMapping("/create")
	public void create() {
		log.info("create()에 왔다");
		// forwarding
//		return "cus/create";
	}
	
	// String으로 응답***************
	/*
	요청 URI : /cus/createPost
	요청 파라미터 : {cusNum=12345, cusNm=개똥이, postNo=33233..}
	요청 방식 : post
	*/
	// String, int, Map.. => @RequestParam
	// VO => ModelAttribute
	// @Valid는 CusVO의 validation 체크를 해주는 어노테이션
	// 문제발생 시 Errors errors객체에 오류 정보를 담아서 꼭!!! forwarding해주면 됨
	@PostMapping("/createPost")
	public String createPost(@Valid @ModelAttribute CusVO cusVO, Errors errors) {
		
		// errors.hasErrors() : 문제 발생 시 true
		if(errors.hasErrors()) {
			return "cus/create";
		}
		
		// 고객번호는 CUS001, CUS002...
		// 자동으로 다음 고객번호를 생성시켜보자
		String cusNum = cusService.makeCusNum();
		log.info("cusNum : " + cusNum);
		cusVO.setCusNum(cusNum);
		
		// 입력성공 : 상세보기로 redirect. 기본키 데이터를 파라미터로 보냄
		return "redirect:/cus/detail?cusNum=" + cusVO.getCusNum();
	}
}
