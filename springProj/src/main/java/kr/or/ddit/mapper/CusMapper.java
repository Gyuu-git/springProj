package kr.or.ddit.mapper;

import java.util.List;

import kr.or.ddit.vo.CarVO;
import kr.or.ddit.vo.CusVO;

public interface CusMapper {
	// 고객테이블(CUS)의 기본키 데이터 생성
	public String getCusNum();
	
	// 고객(CUS) 등록 
	// <insert id="createPost" parameterType="cusVO">
	public int createPost(CusVO cusVO);
	
	// 소유자동차(CAR) 등록. 다중 insert시 update 태그를 사용함
	// <update id="createPostCar" parameterType="java.util.List">
	public int createPostCar(List<CarVO> carVOList);

	// 상세보기
	public CusVO detail(CusVO cusVO);
}
