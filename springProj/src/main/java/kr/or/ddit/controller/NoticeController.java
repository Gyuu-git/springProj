package kr.or.ddit.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/notice")
@Controller
public class NoticeController {

	// 요청 URI : /notice/list : 모두가 접근 가능
	@GetMapping("/list")
	public String list() {
		// forwarding
		return "notice/list";
	}
	
	// 요청 URI : /notice/register
	// 로그인한 관리자(ROLE_ADMIN)나 회원(ROLE_MEMBER) 권한을 가진 사용자만 접근 가능
	// authentication : 인증(로그인) / authorization : 인가(권한)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
	@GetMapping("/register")
	public String register() {
		// forwarding
		return "notice/register";
	}
}
