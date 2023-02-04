package kr.or.ddit.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.AttachVO;
import kr.or.ddit.vo.BookVO;
import lombok.extern.slf4j.Slf4j;

/*
 매퍼XML(book_SQL.xml)을 실행시키는 DAO(Data Access Object) 클래스
 Repository 어노테이션 : 데이터에 접근하는 클래스
 => 스프링이 데이터를 관리하는 클래스라고 인지하여 자바빈 객체로 등록하여 관리함
*/
@Slf4j
@Repository
public class BookDao {
	/* DI(Dependency Injection) : 의존성 주입
	 new 키워드를 통해 개발자가 직접 생성하지 않고,
	  스프링이 미리 만들어 놓은(톰캣서버 실행 시 스프링이 미리 객체를 인스턴스화 해놓음)
	 sqlSessionTemplate 타입 객체를 BookDao 객체에 주입함
	 
	 IoC(Inversion of Control) : 제어의 역전
	*/
	
	// sqlSessionTemplate : 모더나 / BookDao : 개똥이 => 모더나를 개똥이 팔에 주입
	/*
	 root-context.xml <bean id="sqlSessionTemplate" ...
	 	데이터베이스에 개별적으로 쿼리를 실행시키는 객체
	 	이 객체를 통해 query를 실행함
	*/
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	// 도서 테이블(BOOK)에 입력
	// <insert id="createPost" parameterType="bookVO">
	public int createPost(BookVO bookVO) {
		// .insert("namespace값.id값", 파라미터)
		// book_SQL.xml 파일의 namespace가 book이고, id가 insert인
		// 태그를 찾아 그 안에 들어있는 sql을 실행함
		
		log.info("bookVO : " + bookVO.toString());
		// insert, update, delete는 반영된 행수가 return됨
		// insert성공 : 1이상, 실패면 0
		int result = this.sqlSessionTemplate.insert("book.createPost", bookVO);
		int bookId = 0;
		if(result > 0) {	// insert 성공
			bookId = bookVO.getBookId();	// 0? 1(1증가된 값이 들어있음. selectKey에 의해 생성됨)?
		}else {	// insert 실패
			bookId = 0;
		}
		return bookId;
	}
	
	// 책 상세보기
	public BookVO detail(BookVO bookVO) {
		// selectOne() 메소드 : 1행을 가져올 때 사용
		// selectList() 메소드 : 결과 집합 목록 반환(다중행)
		// 결과 행 수가 0이면? null을 반환
		// 결과 행 수가 2 이상이면? TooManyResultException 예외 발생
		// .selectOne("namespace명.id명", 파라미터);
		return this.sqlSessionTemplate.selectOne("book.detail", bookVO);
	}
	
	// 책 수정하기
	// parameterType="bookVO"
	public int updatePost(BookVO bookVO) {
		// .update("namespace.id", 파라미터)
		// 반환타입 int : update구문이 반영된 행의 수
		return this.sqlSessionTemplate.update("book.updatePost", bookVO);
	}
	
	// 책 삭제하기
	// <delete id="deletePost" parameterType="bookVO">
	public int deletePost(BookVO bookVO) {
		// .delete("namespace.id", 파라미터)
		// 반환타입 int : delete구문이 반영된 행의 수
		return this.sqlSessionTemplate.delete("book.deletePost", bookVO);
	}
	
	// 책 목록
	// 리턴타입 : List<BookVO>
	public List<BookVO> list(Map<String, String> map){
		// .selectOne()  : 1행(상세보기)
		// .selectList() : 여러행(목록)
		return this.sqlSessionTemplate.selectList("book.list", map);
	}
	
	// ATTACH 테이블에 insert all
	public int createPostAttach(List<AttachVO> attachVOList) {
		return this.sqlSessionTemplate.update("book.createPostAttach", attachVOList);
	}

	// 전체 행의 수(total)
	public int getTotal(Map<String, String> map) {
		return this.sqlSessionTemplate.selectOne("book.getTotal", map);
	}
}
