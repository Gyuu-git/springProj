package kr.or.ddit.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

// 자바빈 클래스
@Data
public class MemVO {
	private int userNo;
	private String userId;
	private String userPw;
	private String userName;
	private int coin;
	private Date regDate;
	private Date updDate;
	private String enabled;
	
	// MemVO : MemAuthVO = 1 : N
	private List<MemAuthVO> memAuthVOList;
}
