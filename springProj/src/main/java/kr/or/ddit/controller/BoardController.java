package kr.or.ddit.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/board")
@Controller
public class BoardController {
	
	// 요청 URI : /board/list : 모두가 접근 가능
	// Authentication : 인증(로그인)
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list")
	public String list() {
		// forwarding
		return "board/list";
	}
	
	// 요청 URI : /board/register : 로그인한 회원만 접근 가능
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	@GetMapping("/register")
	public String register() {
		// forwarding
		return "board/register";
	}
}
