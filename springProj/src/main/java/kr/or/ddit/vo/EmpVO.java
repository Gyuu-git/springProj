package kr.or.ddit.vo;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

// 직원
@Data
public class EmpVO {
	@NotBlank
	private String empNum;	// 직원 번호
	
	private String empAddr;	// 주소
	@NotBlank
	private String empPne;	// 연락처
	@NotBlank
	private String empNm;	// 직원 명
	@NotBlank
	private int empPay;		// 급여
	
	// 직원(EMP) : 서비스(SER) = 1 : N
	private List<SerVO> serVOList;
}
