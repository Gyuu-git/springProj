package kr.or.ddit.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.or.ddit.mapper.MemMapper;
import kr.or.ddit.vo.MemVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	// DI(의존성 주입)
	@Autowired
	MemMapper memMapper;
	
	// 요청 파라미터 : <input type="text" name="username"...
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 파라미터 준비 (public MemVO memLogin(MemVO memVO))
		MemVO memVO = new MemVO();
		// WHERE A.USER_ID = #{userId}
		memVO.setUserId(username);
		
		memVO = memMapper.memLogin(memVO);
		log.info("memVO : " + memVO);
		
		log.warn("MemMapper에 의해 쿼리를 실행할 것임 : " + memVO);
		
		// 3항 연산자. memVO가 null이면 null을 리턴하고, null이 아니면 USER를 리턴
		return memVO == null ? null: new CustomUser(memVO);
	}

}
