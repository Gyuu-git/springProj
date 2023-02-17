package kr.or.ddit.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public void create(@ModelAttribute CusVO cusVO, Model model) {
		log.info("create()에 왔다");
		
		// 기본키 데이터 생성
		String cusNum = this.cusService.getCusNum();
		cusVO.setCusNum(cusNum);
		cusVO.setCusNm("팬더");
		
		// 취미 등록
		List<String> hobbyList = new ArrayList<>();
		hobbyList.add("Music");
		hobbyList.add("Movie");
		hobbyList.add("Sports");
		cusVO.setHobbyList(hobbyList);
		
		cusVO.setGender("female");
		
		// 국적(한개 선택) -> select 박스
		Map<String, String> nationalityMap = new HashMap<>();
		nationalityMap.put("Korea", "Korea");
		nationalityMap.put("America", "America");
		nationalityMap.put("Germany", "Germany");
		
		// 생성된 고객번호를 model에 넣음
		model.addAttribute("cusNum", cusNum);
		model.addAttribute("nationalityMap", nationalityMap);
		
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
	public String createPost(@Valid @ModelAttribute CusVO cusVO, 
            String cusNum, String cusNm, String postNo, String cusAddr, String addrDet,
            @RequestParam("cusPhe") String phone, 
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date cusBir,
            String gender, String nationality, 
            Errors errors) {
		
		log.info("cusVO : " + cusVO);
		log.info("cusNum : " + cusNum + ", cusNm : " + cusNm + ", postNo : " + postNo
				+ ", cusAddr : " + cusAddr + ", addrDet : " + addrDet + ", cusPhe : " + phone 
				+ ", cusBir : " + cusBir);
		
		String hobby = "";
		for(String str : cusVO.getHobbyList()) {
			hobby += str + ",";
		}
		cusVO.setHobby(hobby);
		
		// errors.hasErrors() : 문제 발생 시 true
		if(errors.hasErrors()) {
			return "cus/create";
		}
		
		// CUS 테이블에 insert
		int result = this.cusService.createPost(cusVO);
		log.info("result : " + result);
		
		// 입력성공 : 상세보기로 redirect. 기본키 데이터를 파라미터로 보냄
		return "redirect:/cus/detail?cusNum=" + cusVO.getCusNum();
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
	public String detail(Model model, RedirectAttributes ras, @ModelAttribute CusVO cusVO) {
		
		// 상세보기
		cusVO = this.cusService.detail(cusVO);
		log.info("cusVO : " + cusVO);
		
		// Muslic, Sports,
		String hobby = cusVO.getHobby();
		String[] hobbyArray = hobby.split(",");
		List<String> hobbyList = new ArrayList<String>();
		for(int i = 0; i < hobbyArray.length; i++) {
			hobbyList.add(hobbyArray[i]);
		}
		cusVO.setHobbyList(hobbyList);
		
		// 국적(한개 선택) -> select 박스
		Map<String, String> nationalityMap = new HashMap<>();
		nationalityMap.put("Korea", "Korea");
		nationalityMap.put("America", "America");
		nationalityMap.put("Germany", "Germany");
		
		model.addAttribute("nationalityMap", nationalityMap);
		model.addAttribute("cusVO", cusVO);
		
		// forwarding
		return "cus/detail";
	}
}
