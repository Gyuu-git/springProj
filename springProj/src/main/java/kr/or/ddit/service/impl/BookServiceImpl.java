package kr.or.ddit.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.controller.LprodController;
import kr.or.ddit.dao.BookDao;
import kr.or.ddit.service.BookService;
import kr.or.ddit.vo.AttachVO;
import kr.or.ddit.vo.BookVO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

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
public class BookServiceImpl implements BookService {
	
	// 데이터 베이스에 접근하기 위해 BookDao 인스턴스를 주입받자
	@Autowired
	BookDao bookDao;
	
	// 도서 테이블(BOOK)에 입력
	// <insert id="createPost" parameterType="bookVO">
	@Override
	public int createPost(BookVO bookVO) {
		// 1) BOOK 테이블에 insert. bookId를 리턴받음.
		//		bookVO의 bookId 멤버변수에 새로운 값이 들어있다.
		int result = bookDao.createPost(bookVO);

		// cont -> content복사
		bookVO.setContent(bookVO.getCont());
		
		String uploadFolder = "C:\\eclipse-jee-2020-06\\workspace\\springProj\\src\\main\\webapp\\resources\\upload";
		
		// make folder 시작 ---------------
		File uploadPath = new File(uploadFolder, LprodController.getFolder());
		log.info("upload Path : " + uploadPath);
		
		// 만약 연/월/일 해당 폴더가 없다면 생성
		if(!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		// make folder  끝  ---------------
		
		// List<AttachVO> attachVOList에 데이터 넣기 시작 ----------------
		List<AttachVO> voList = new ArrayList<AttachVO>();
		
		MultipartFile[] multipartFiles = bookVO.getUploadfile();
		for(MultipartFile multipartFile : multipartFiles) {
			AttachVO vo = new AttachVO();
			// 실제 파일명
			String uploadFileName = multipartFile.getOriginalFilename();
			
			log.info("----------------------");
			log.info("filename : " + uploadFileName);
			log.info("filesize : " + multipartFile.getSize());
			log.info("contentType : " + multipartFile.getContentType()); // MIME
			
			// UUID 처리 시작 -----------
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			// UUID 처리  끝  -----------
			
			// File 객체 설계(복사할 경로, 파일명)
			File saveFile = new File(uploadPath, uploadFileName);
			
			try {
				// 파일 복사. 파일객체를 복사한다(대상경로 및 파일명으로)
				multipartFile.transferTo(saveFile);
				
				// 이미지인가?
				if(LprodController.checkImageType(saveFile)) {
					// 썸네일 파일 설계
					FileOutputStream thumbnail = new FileOutputStream(
											new File(uploadPath, "s_" + uploadFileName));
					// 썸네일 생성
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}
			} catch (IllegalStateException e) {
				log.error(e.getMessage());
			} catch (IOException e) {
				log.error(e.getMessage());
			}
			
			String filename = "/" + LprodController.getFolder().replace("\\", "/") + "/" + uploadFileName;
			vo.setFilename(filename);
			vo.setFilesize((int) multipartFile.getSize());
			String thumbFilename = "/" + LprodController.getFolder().replace("\\", "/") + "/s_" + uploadFileName;
			vo.setThumbnail(thumbFilename);
			// 전사적 아이디
			vo.setEtpId(bookVO.getBookId() + "");
			
			log.info("saveFile.getPath() : " + saveFile.getPath());
			log.info("filename : " + filename);
			
			voList.add(vo);
		}
		
		bookVO.setAttachVOList(voList);
		// List<AttachVO> attachVOList에 데이터 넣기  끝  ----------------
		
		// 2) BookDAO의 createPostAttach()메소드 호출
		this.bookDao.createPostAttach(bookVO.getAttachVOList());
		
		return result;
	}
	
	// 책 상세보기
	@Override
	public BookVO detail(BookVO bookVO) {
		return bookDao.detail(bookVO);
	}
	
	// 책 수정하기
	@Override
	public int updatePost(BookVO bookVO) {
		return bookDao.updatePost(bookVO);
	}
	
	// 책 삭제하기
	@Override
	public int deletePost(BookVO bookVO) {
		return bookDao.deletePost(bookVO);
	}
	
	// 책 목록
	@Override
	public List<BookVO> list(Map<String, String> map){
		return bookDao.list(map);
	}

	// 전체 행의 수(total)
	@Override
	public int getTotal(Map<String, String> map) {
		return bookDao.getTotal(map);
	}
}
