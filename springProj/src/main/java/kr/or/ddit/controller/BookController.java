package kr.or.ddit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.service.BookService;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.vo.BookVO;
import lombok.extern.slf4j.Slf4j;

/* Controller 어노테이션
 스프링 프레임워크에게 "이 클래스는 웹 브라우저(클라이언트)의 요청(request)을
 받아들이는 컨트롤러야"라고 알려주는 것임.
 스프링은 servlet-context.xml의 context:component-scan의 설정에 의해
 이 클래스를 자바빈 객체로 미리 등록(메모리에 바인딩)
*/
@Slf4j
@Controller
public class BookController {
	// 서비스를 호출하기 위해 의존성 주입(Dependency Injection-DI)
	@Autowired
	BookService bookService;
	
	// 요청 URI : /create
	// 방법 : get
	// 요청 -(매핑)- 메소드
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create() {
		/* ModelAndView
			1) Model : Controller가 반환할 데이터(String, int, List, Map, VO...)를 담당.
			2) View : 화면을 담당(뷰(View : jsp)의 경로). jsp파일의 위치
		*/
		ModelAndView mav = new ModelAndView();
		/*
		<beans:property name="prefix" value="/WEB-INF/views/" />
		<beans:property name="suffix" value=".jsp" />
		prefix + 뷰경로 + suffix 가 조립
		/WEB-INF/views/book/create.jsp
		*/
		// forwarding
		mav.setViewName("book/create");
		return mav;
	}
	
	/*
	요청 URI : /create?title=개똥이글로리&category=소설&price=10000
	요청 URL : /create
	요청 파라미터 : {title : 개똥이글로리, category : 소설, price : 10000}
	요청 방식 : post
	
	bookVO{bookId : null, title : 개똥이글로리, category : 소설, price : 10000, insertDate : null, 
			content : null, cont : 내용, uploadfile : 파일객체, attachVOList : null}
	*/
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public ModelAndView createPost(BookVO bookVO, ModelAndView mav) {
		log.info("bookVO : " + bookVO.toString());
		
		// cont -> content복사
		bookVO.setContent(bookVO.getCont());
		
		// <selectKey resultType="int" order="BEFORE" keyProperty="bookId">
		// 1 증가된 기본키 값을 받음
		int bookId = bookService.createPost(bookVO);
		log.info("bookId : " + bookId);
		
		if(bookId < 1) {	// 등록 실패
			// /create로 요청을 다시 함 => uri주소가 바뀜
			mav.setViewName("redirect:/create");
		}else {
			mav.setViewName("redirect:/detail?bookId=" + bookId);
		}
		
		return mav;
	}
	
	// 책 상세보기
	// 요청 URI : /detail?bookId=2
	// 요청 URL : /detail
	// 요청 파라미터(HTTP파라미터=QueryString) : bookId=2
	// bookVO => bookVO{bookId:2, title:null, category:null, 
	//					price:0, insertDate:null, content:null}
	@RequestMapping(value="/detail", method = RequestMethod.GET)
	public ModelAndView detail(BookVO bookVO, ModelAndView mav) {
		log.info("bookVO : " + bookVO);
		
		BookVO data = bookService.detail(bookVO);
		// bookVO{bookId:2, title:개똥이글로리, category:소설, 
		//			price:10000, insertDate:23/01/20, content:더글러리 후속작}
		log.info("data : " + data);
		
//		ModelAndView mav = new ModelAndView();
		// model : 데이터를 jsp로 넘겨줌
		mav.addObject("data", data);
		mav.addObject("bookId", data.getBookId());	// 기본키 값
		
		// forwarding => detail.jsp를 해석/컴파일 => html로 크롬에게 리턴. 데이터 전달 가능
		mav.setViewName("book/detail");
		return mav;
	}
	
	// 요청 URI : /update?bookId=2
	// 요청 URL : /update
	// 요청 파라미터 : bookId=2
	@RequestMapping(value="/update", method = RequestMethod.GET)
	public ModelAndView update(@ModelAttribute BookVO bookVO, ModelAndView mav) {
		// bookVO => bookVO{bookId:2, title:null, category:null, 
		//					price:0, insertDate:null, content:null}
		log.info("bookVO : " + bookVO);
		
		// 책 수정 화면 = 책 입력 화면 + 책 상세 데이터
		// 책 입력 화면 양식을 그대로 따라가고, 빈 칸을 데이터로 채워주면 됨
		BookVO data = bookService.detail(bookVO);
		// bookVO{bookId:2, title:개똥이글로리, category:소설, 
		//			price:10000, insertDate:23/01/20, content:더글러리 후속작}
		log.info("data : " + data);
		
//		ModelAndView mav = new ModelAndView();
		// model : 데이터를 jsp로 넘겨줌
		mav.addObject("data", data);
		
		// View : jsp의 경로
		mav.setViewName("book/update");
		return mav;
	}
	
	// 요청 URI : /update
	// 요청 파라미터 : {bookId:2, title:개똥이글로리2, category:소설2, price:13000}
	// 요청 방식 : post
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public ModelAndView updatePost(BookVO bookVO, ModelAndView mav) {
		// bookVO {bookId:2, title:개똥이글로리2, category:소설2, price:13000}
		log.info("bookVO(updatePost) : " + bookVO);

		// result는 update문에 반영된 행의 수
		int result = this.bookService.updatePost(bookVO);
		
		if(result > 0) {	// 업데이트 성공 -> 책 상세페이지(/detail)로 이동
			mav.setViewName("redirect:/detail?bookId=" + bookVO.getBookId());
		}else {	// 업데이트 실패 -> 업데이트 뷰페이지(/update)로 이동
			mav.setViewName("redirect:/update?bookId=" + bookVO.getBookId());
		}
		
		return mav;
	}
	
	// 요청 URI : /delete
	// 요청 파라미터 : {"bookId" : "2"}
	// 요청 방식 : post
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView deletePost(@ModelAttribute BookVO bookVO, ModelAndView mav) {
		// BookVO [bookId=2, title=null, category=null, price=0, 
		// 		insertDate=null, content=null]
		log.info("bookVO : " + bookVO);
		
		// 삭제 SQL 실행 후 반영된 행의 수
		int result = this.bookService.deletePost(bookVO);
		
		if(result > 0) {	// 삭제 성공 -> 목록으로 요청 이동(상세페이지가 없으므로..)
			mav.setViewName("redirect:/list");
		}else {	// 삭제 실패 -> 상세페이지로 이동
			mav.setViewName("redirect:/detail?bookId=" + bookVO.getBookId());
		}
		
		return mav;
	}
	
	// 1) 요청 URI : /list
	// 		요청 파라미터 : {}
	// 2) 요청 URI : /list?keyword=ㄴㅇ
	//		요청 파라미터 : {"keyword" : "ㄴㅇ"}
	// 요청 방식 : get
	// 스프링에서 요청파라미터를 매개변수로 받을 수 있음
	// @RequestParam
	//		- value : 키값 설정 / value값 설정시 매개변수명을 내 마음대로 정해도 되지만 
	//								설정하지 않을 시 요청 파라미터의 키값과 동일하게 작성해야 한다.
	//		- required : false > 파라미터가 있을수도 없을수도 있다.
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(ModelAndView mav, 
			@RequestParam(value="keyword", required = false) String keyword,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage) {
		Map<String, String> map = new HashMap<>();
		map.put("keyword", keyword);
		
		int total = this.bookService.getTotal(map);
		int size = 10;
		
		map.put("size", size + "");
		map.put("currentPage", currentPage + "");
		
		List<BookVO> bookVOList = this.bookService.list(map);
		
		log.info("bookVOList : " + bookVOList);
		
		// 데이터
		// 1) 페이징 전
//		mav.addObject("data", bookVOList);
		// 2) 페이징 후
		mav.addObject("data", new ArticlePage<BookVO>(total, currentPage, size, 5, bookVOList));
		// jsp(뷰) : book폴더에 있는 list.jsp를 forwarding(jsp를 해석, 컴파일하여 html로 리턴)
		mav.setViewName("book/list");
		
		return mav;
	}
}
