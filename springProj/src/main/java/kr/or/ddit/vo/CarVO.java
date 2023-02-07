package kr.or.ddit.vo;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CarVO {
	@NotBlank
	private String carNum;	// 자동차 번호
	@NotBlank
	private String mnfNum;	// 제조 번호
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dt;		// 연식
	
	private int dist;		// 주행 거리
	@NotBlank
	private String cusNum;	// 고객 번호
	
	// 자동차(CAR) : 서비스(SER) = 1 : N
	private List<SerVO> serVOList;
}
