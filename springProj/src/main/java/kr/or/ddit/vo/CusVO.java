package kr.or.ddit.vo;

import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

// 고객
/* Bean Validation이 제공하는 제약 어노테이션
 - NotNull : 빈 값 체크
 - NotBlank : null체크, trim(공백제거) 후 길이가 0인지 체크
 - Size : 글자 수 체크
 - Email : 이메일 주소 형식 체크
 - Post : 오늘보다 과거 날짜(ex. 생일)
 - Future : 미래 날짜 체크(ex. 예약일)
*/
@Data
public class CusVO {
	@NotBlank
	private String cusNum;	// 고객번호(필수=mandatory), null체크, trim 후 길이가 0인지 체크
	@NotBlank
	@Size(min = 2, max = 10, message = "2자 ~ 10자 이내로 입력해 주세요")
	private String cusNm;	// 고객명
	
	private String cusAddr;	// 주소
	@NotBlank
	private String cusPhe;	// 연락처
	
	private String postNo;	// 우편번호
	
	private String addrDet;	// 주소 상세
	
	// 고객(CUS) : 자동차(CAR) = 1 : N
	private List<CarVO> carVOList;
	// 고객(CUS) : 서비스(SER) = 1 : N
	private List<SerVO> serVOList;
}
