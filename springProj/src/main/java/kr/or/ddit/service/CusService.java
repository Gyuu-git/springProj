package kr.or.ddit.service;

import kr.or.ddit.vo.CusVO;

public interface CusService {
	// 고객테이블(CUS)의 기본키 데이터 생성
	public String getCusNum();

	// 고객(CUS) 등록 
	public int createPost(CusVO cusVO);

	// 상세보기
	public CusVO detail(CusVO cusVO);
}
