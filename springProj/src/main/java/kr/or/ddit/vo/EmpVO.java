package kr.or.ddit.vo;

import java.util.List;

import javax.validation.constraints.NotNull;

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
	@NotNull
	private int empPay;		// 급여
	
	private String empMjNum;// 매니저

	private String empMjNm;	// 매니저 명
	
	private int rnum;		// 행 번호
	
	// 직원(EMP) : 서비스(SER) = 1 : N
	private List<SerVO> serVOList;
}
