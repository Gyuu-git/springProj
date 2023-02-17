package kr.or.ddit.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {
	
	// 요청 URI : /login
	// 요청방식 : get
	// 오류 메시지와 로그아웃 메시지를 파라미터로 사용해보자(없을 수도 있음)
	@GetMapping("/login")
	public String loginForm(String error, String logout, Model model) {
		log.info("error : " + error);
		if(error != null) {
			log.info("error길이 : " + error.length());
		}
		log.info("logout : " + logout);
		
		if(error != null) {
			model.addAttribute("error", "Login Error");
		}
		
		if(logout != null) {
			model.addAttribute("logout", "Logout!!");
		}
		
		// forwarding
		return "security/loginForm";
	}
}
