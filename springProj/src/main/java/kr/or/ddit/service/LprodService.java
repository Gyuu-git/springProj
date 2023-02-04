package kr.or.ddit.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.AttachVO;
import kr.or.ddit.vo.LprodVO;

// 서비스 interface : 비즈니스 로직(비즈니스 레이어)
public interface LprodService {
	// 메소드 시그니처

	public List<LprodVO> list(Map<String, String> map);
	
	// 상품분류 자동생성
	public int getLprodId();

	// 상품분류코드 자동 생성
	public String getLprodGu();

	// 상품분류 정보를 입력
	public int createPost(LprodVO lprodVO);
	
	// 상품분류 상세보기
	public LprodVO detail(LprodVO lprodVO);
	
	// 상품분류 정보변경 
	public int updatePost(LprodVO lprodVO);

	// 상품분류 삭제
	public int deletePost(LprodVO lprodVO);

	// 첨부파일 등록
	public int uploadFormAction(AttachVO attachVO);

	// 전체 행의 수(total)
	public int getTotal(Map<String, String> map);
}
