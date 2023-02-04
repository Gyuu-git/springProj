package kr.or.ddit.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.BookVO;

// 서비스 interface : 비즈니스 로직(비즈니스 레이어)
public interface BookService {
	// 메소드 시그니처
	// 도서 등록
	public int createPost(BookVO bookVO);

	// 도서 상세보기
	public BookVO detail(BookVO bookVO);

	// 도서 수정하기
	public int updatePost(BookVO bookVO);
	
	// 도서 삭제하기
	public int deletePost(BookVO bookVO);

	// 도서 목록보기
	public List<BookVO> list(Map<String, String> map);

	// 전체 행의 수(total)
	public int getTotal(Map<String, String> map);
}
