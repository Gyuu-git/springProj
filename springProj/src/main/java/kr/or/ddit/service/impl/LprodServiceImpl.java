package kr.or.ddit.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.dao.LprodDao;
import kr.or.ddit.service.LprodService;
import kr.or.ddit.vo.AttachVO;
import kr.or.ddit.vo.LprodVO;
import lombok.extern.slf4j.Slf4j;

// 서비스 클래스 : 비즈니스 로직
// 스프링 MVC 구조에서 Controller와 DAO를 연결하는 역할
/*
 스프링 프레임워크(디자인패턴 + 라이브러리)는 직접 클래스를 생성하는 것을 지양하고,
 인터페이스를 통해 접근하는 것을 권장하고 있기 때문(확장성)
 * 프링이는 인터페이스를 좋아해.
 그래서 서비스 레이어는 인터페이스(BookService)와 클래스(BookServiceImpl)를 함께 사용함
 
 Impl : implement의 약자
*/
// Service 어노테이션 : "프링아 이 클래스는 서비스 클래스야"라고 알려줌.
//			프링이가 자바빈으로 등록해줌
@Slf4j
@Service
public class LprodServiceImpl implements LprodService {
	
	@Autowired
	LprodDao lprodDao;

	@Override
	public List<LprodVO> list(Map<String, String> map) {
		return this.lprodDao.list(map);
	}

	// 상품분류 자동생성
	@Override
	public int getLprodId() {
		return this.lprodDao.getLprodId();
	}
	
	// 상품분류코드 자동 생성
	@Override
	public String getLprodGu() {
		return this.lprodDao.getLprodGu();
	}

	// 상품분류 정보를 입력
	@Override
	public int createPost(LprodVO lprodVO) {
		// LPROD테이블에 insert
		int result = this.lprodDao.createPost(lprodVO);
		
		// ATTACH테이블에 insert(다중 insert)
		List<AttachVO> attachVOList = lprodVO.getAttachVOList();
		result += this.lprodDao.createPostAttach(attachVOList);
		log.info("result : " + result);
		
		return result;
	}

	// 상품분류 상세보기
	@Override
	public LprodVO detail(LprodVO lprodVO) {
		return this.lprodDao.detail(lprodVO);
	}

	// 상품분류 정보변경 
	@Override
	public int updatePost(LprodVO lprodVO) {
		return this.lprodDao.updatePost(lprodVO);
	}

	// 상품분류 삭제
	@Override
	public int deletePost(LprodVO lprodVO) {
		return this.lprodDao.deletePost(lprodVO);
	}
	
	// 첨부파일 등록
	// <insert id="uploadFormAction" parameterType="attachVO">
	@Override
	public int uploadFormAction(AttachVO attachVO) {
		return this.lprodDao.uploadFormAction(attachVO);
	}
	
	// 전체 행의 수(total)
	@Override
	public int getTotal(Map<String, String> map) {
		return this.lprodDao.getTotal(map);
	}
}
