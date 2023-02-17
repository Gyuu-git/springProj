package kr.or.ddit.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import kr.or.ddit.vo.MemAuthVO;
import kr.or.ddit.vo.MemVO;

// User : 스프링 시큐리티가 제공하고 있는 사용자 정보 클래스
public class CustomUser extends User {
	// 이 memVO 객체는 JSP에서 사용할 수 있음.
	private MemVO memVO;
	
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	// 생성자
	// memVO : select한 사용자 및 권한 정보가 들어있음
	// MemVO 타입의 객체 memVO를 스프링 시큐리티에서 제공해주고 있는 UsersDetails 타입으로 변환
	// 스프링 시큐리티야, 회원정보를 보내줄테니 이제부터 니가 좀 관리해줘
	public CustomUser(MemVO memVO) {
		// 1) username / 2) password / 3)authorities
		// memVO.getMemAuthVOList() :
		//	2	ROLE_MEMBER
		//	2	ROLE_ADMIN
		// memVO.getMemAuthVOList().stream() (직렬화) : 
		//	2	ROLE_MEMBER		2	ROLE_ADMIN
		super(memVO.getUserId(), memVO.getUserPw(), 
				memVO.getMemAuthVOList().stream()
				.map(auth->new SimpleGrantedAuthority(auth.getAuth()))
				.collect(Collectors.toList()));
		this.memVO = memVO;
	}
	
	public static List<SimpleGrantedAuthority> getCollect(MemVO memVO){
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		
		List<MemAuthVO> memAuthVOList = memVO.getMemAuthVOList();
		for(MemAuthVO memAuthVO : memAuthVOList) {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(memAuthVO.getAuth());
			authorities.add(authority);
		}
		
		return authorities;
	}

	public MemVO getMemVO() {
		return memVO;
	}
	public void setMemVO(MemVO memVO) {
		this.memVO = memVO;
	}
	
}
