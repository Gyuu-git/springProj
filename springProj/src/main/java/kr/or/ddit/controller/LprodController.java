package kr.or.ddit.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.service.LprodService;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.vo.AttachVO;
import kr.or.ddit.vo.LprodVO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

/* Controller 어노테이션
 스프링 프레임워크에게 "이 클래스는 웹 브라우저(클라이언트)의 요청(request)을
 받아들이는 컨트롤러야"라고 알려주는 것임.
 스프링은 servlet-context.xml의 context:component-scan의 설정에 의해
 이 클래스를 자바빈 객체로 미리 등록(메모리에 바인딩)
*/
@RequestMapping("/lprod")
@Slf4j
@Controller
public class LprodController {
	@Autowired
	LprodService lprodService;
	
	// 요청 URI : /lprod/list?currentPage=2
	// 1) 요청 파라미터 : currentPage=2
	// 2) 요청 파라미터 : x
	// required : 필수니?
	// defaultValue : 없으면? defaultValue = "1" : 1페이지로 간주함
	// currentPage=2 파라미터의 타입은 String.. but, int 타입의 매개변수로 자동 형변환 가능
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(ModelAndView mav, 
			@RequestParam(value = "keyword", required = false) String keyword, 
			@RequestParam(value = "size", required = false, defaultValue = "10") int size, 
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage) {
		// 검색 조건
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("keyword", keyword);
		
		// 1) 전체 행의 수 구하기(total)
		int total = this.lprodService.getTotal(map);
		// 2) 한 페이지에 보여질 행의 수(size)
//		int size = 10;
		
		// map{"size":"10", "currentPage":"1"}
		map.put("size", size + "");
		map.put("currentPage", currentPage + "");
		log.info("map : " + map);
		
		List<LprodVO> lprodVOList = lprodService.list(map);
		
		// 페이징 처리 전.. data
//		mav.addObject("data", lprodVOList);
		// 페이징 처리 후..
		mav.addObject("data", new ArticlePage<LprodVO>(total, currentPage, size, 5, lprodVOList));
		
		// jsp경로
		// tiles-config.xml의
		// */*은 lprod/list와 {1}/{2}.jsp에 의해 lprod/list.jsp가 forwarding됨
		mav.setViewName("lprod/list");
		
		return mav;
	}
	
	// 요청 URI : /prod/create
	// 방식 : GET
	// @RequestMapping(value = "/create", method = RequestMethod.GET)
	@GetMapping("/create")
	public String create(Model model) {
		// 상품분류 아이디 자동생성
		int lprodId = lprodService.getLprodId();
		
		// lprodId 데이터를 model에 담아서 create.jsp로 보내줌
		model.addAttribute("lprodId", lprodId);
		
		// 상품대분류 등록 jsp를 forwarding
		// 1. 타일즈의 insex.jsp 우선 적용
		// 2. <definition name="*/*" extends="tiles-layout">
		//		/WEB-INF/views/lprod/create.jsp => tiles의 body로 include됨
		return "lprod/create";
	}
	
	// url: "/lprod/getLprodGu"
	// type: "post"
	// @RequestMapping(value = "/getLprodGu", method = RequestMethod.POST)
	// 비동기 방식 요청 처리 시 @ResponseBody를 사용함 => JSON데이터로 변환하여 리턴해줌
	@ResponseBody
	@PostMapping("/getLprodGu")
	public String getLprodGu(Model model) {
		String lprodGu = this.lprodService.getLprodGu();
		return lprodGu;
	}
	
	/*
	요청 URI : /lprod/createPost
	요청 파라미터 : {"lprodId" : "10", "lprodGu" : "P404", "lprodNm" : "간식류"}
	요청 방식 : post
	*/
	@PostMapping("/createPost")
	public String createPost(@ModelAttribute LprodVO lprodVO, Model model) {
		String uploadFolder = "C:\\eclipse-jee-2020-06\\workspace\\springProj\\src\\main\\webapp\\resources\\upload";
		
		// make folder 시작------------------
		File uploadPath = new File(uploadFolder, getFolder());
		log.info("uploadPath : " + uploadPath);
		
		// 만약 연/월/일 해당 폴더가 없으면 생성
		if(!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		
		// make folder   끝------------------
		
		// lprodVO : LprodVO [lprodId=10, lprodGu=P404, lprodNm=dd]
		//					uploadFile:null, attachVOList:null]
		log.info("lprodVO : " + lprodVO);
		
		MultipartFile[] multipartFiles = lprodVO.getUploadFile();
		log.info("multipartFile : " + multipartFiles);
		
		List<AttachVO> voList = new ArrayList<AttachVO>();
		int seq = 1;
		
		// uploadFile 정보를 통해서 attatchVOList에 값들을 setting해줘야 함
		// 배열로부터 하나씩 파일을 꺼내오자
		for(MultipartFile multipartFile : multipartFiles) {
			AttachVO vo = new AttachVO();
			// 실제 파일명
			String uploadFileName = multipartFile.getOriginalFilename();
			
			log.info("-----------------");
			log.info("fileName : " + uploadFileName);
			log.info("fileSize : " + multipartFile.getSize());
			log.info("contentType : " + multipartFile.getContentType());	// MIME타입
			
			//------------- 같은 날 같은 이미지를 업로드 시 파일 중복 방지 시작 -------------
			// java.util.UUID => 랜덤값 생성
			UUID uuid = UUID.randomUUID();	// 임의의 값을 생성
			// 원래의 파일 이름과 구분하기 위해 _를 붙임
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			//------------- 같은 날 같은 이미지를 업로드 시 파일 중복 방지  끝  -------------
			
			// 파일 객체 설계(복사할 대상 경로, 파일명)
			File saveFile = new File(uploadPath, uploadFileName);
			
			try {
				// 파일 복사가 일어남
				multipartFile.transferTo(saveFile);
				
				// 썸네일 처리 시작 -----------------
				if(checkImageType(saveFile)) {	// 이미지라면 실행
					FileOutputStream thumbnail = new FileOutputStream(
											new File(uploadPath, "s_" + uploadFileName));
					// 썸네일 생성(원본파일, 대상, 가로크기, 세로크기)
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}
				// 썸네일 처리   끝 -----------------
			} catch (IllegalStateException e) {
				log.error(e.getMessage());
				return "0";
			} catch (IOException e) {
				log.error(e.getMessage());
				return "0";
			}
			
			vo.setSeq(seq++);
			String filename = "/" + getFolder().replace("\\", "/") + "/" + uploadFileName;
			vo.setFilename(filename);
			vo.setFilesize((int) multipartFile.getSize());
			String thumbFileName = "/" + getFolder().replace("\\", "/") + "/s_" + uploadFileName;
			vo.setThumbnail(thumbFileName);
			vo.setEtpId(lprodVO.getLprodGu());
			voList.add(vo);
		}
		// lprodVO : LprodVO [lprodId=10, lprodGu=P404, lprodNm=dd, 
		//					uploadFile:있음, attachVOList:있음]
		lprodVO.setAttachVOList(voList);
		
		log.info("(after) lprodVO : " + lprodVO);
		
		int result = this.lprodService.createPost(lprodVO);
		
		if(result > 0) {	// 입력성공
			// /lprod/detail
			return "redirect:/lprod/detail?lprodGu=" + lprodVO.getLprodGu();
		}else {		// 입력실패
			// /lprod/create => create.jsp에 데이터가 입력되어있기
			model.addAttribute("lprodId", lprodVO.getLprodId());
			model.addAttribute("lprodVO", lprodVO);
			return "lprod/create";
		}
	}
	
	// 요청 URI : /lprod/detail?lprodGu=P404
	// 요청 파라미터 : lprodGu=P404
	// 요청 방식 : GET
	@GetMapping("/detail")
	public String detail(@ModelAttribute("LprodVO") LprodVO lprodVO, Model model) {
		// lprodVO : LprodVO [lprodId=0, lprodGu=P404, lprodNm=null]
		log.info("lprodVO : " + lprodVO);
		
		// lprodGu의 값이 P404인 상품분류 정보 1행을 가져오자.
		lprodVO = this.lprodService.detail(lprodVO);
		model.addAttribute("lprodVO", lprodVO);
		
		// forwarding
		// detail.jsp는 create.jsp를 기본 폼으로 하고, data를 받아서 정보를 화면에 출력
		return "lprod/detail";
	}
	
	// 상품분류 정보 변경
	// 요청 URL : /lprod/updatePost
	// 요청 파라미터 : {"lprod : "10", "lprodGu" : "P404", "lprodNm" : "간식류변경"}
	// 요청 방식 : post
	@PostMapping("/updatePost")
	public String updatePost(@ModelAttribute LprodVO lprodVO, Model model) {
		log.info("lprodVO : " + lprodVO);
		
		// 정보 변경
		int result = this.lprodService.updatePost(lprodVO);
		log.info("result : " + result);
		
		if(result > 0) {	// 정보변경 성공
			// detail로 되돌아오기
			return "redirect:/lprod/detail?lprodGu=" + lprodVO.getLprodGu();
		}else {		// 정보변경 실패 => lprodVO가 넘어온 그대로 detail.jsp로 넘겨주자
			// LprodVO [lprodId=11, lprodGu=P405, lprodNm=dfdf수정]
			model.addAttribute("lprodVO", lprodVO);
			// forwarding
			// detail.jsp는 create.jsp를 기본 폼으로 하고, data를 받아서 정보를 화면에 출력
			return "lprod/detail";
		}
	}
	
	// 상품분류 정보 삭제
	// 요청 URI : /lprod/deletePost
	// 요청 파라미터 : {"lprodId":"10", "lprodGu":"P404", "lprodNm":"간식류변경"}
	// 요청 방식 : GET
	@PostMapping("/deletePost")
	public String deletePost(@ModelAttribute LprodVO lprodVO, Model model) {
		int result = this.lprodService.deletePost(lprodVO);
		
		if(result > 0) {
			return "redirect:/lprod/list";
		}else {
			return "redirect:/lprod/detail?lprodGu=" + lprodVO.getLprodGu();
		}
	}

	// 요청 URL : /lprod/uploadForm
	// 요청 방식 : GET
	@GetMapping("/uploadForm")
	public String uploadForm() {
		// forwarding
		return "lprod/uploadForm";
	}
	
	/*
	요청 URL : /lprod/uploadFormAction
	요청 파라미터 : uploadFile이라는 이름의 파일객체
	요청 방식 : post
	*/
	@PostMapping("/uploadFormAction")
	public String uploadFormAction(MultipartFile uploadFile) {
		// MultipartFile : 스프링에서 제공해주는 타입
		/*
		String, getOriginalFileName() : 업로드 되는 파일의 실제 파일명
		boolean, isEmpty() : 파일이 없다면 true
		long, getSize() : 업로드되는 파일의 크기
		transferTo(File file) : 파일을 저장
		*/
		// 파일이 저장되는 경로
		String uploadFolder = "c:\\upload";
		
		// make foler 시작------------------------
		// c:\\upload\\2023\\01\\27
		File uploadPath = new File(uploadFolder, getFolder());
		log.info("upload Path : " + uploadPath);
		
		// 만약 연/월/일 해당 폴더가 없다면 생성
		if(!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		// make foler   끝------------------------
		
		// 파일 명
		String uploadFileName = uploadFile.getOriginalFilename();
		
		log.info("------------");
		log.info("이미지 명 : " + uploadFileName);
		log.info("파일 크기 : " + uploadFile.getSize());
		
		//------------- 파일명 중복 방지 시작 -------------
		// java.util.UUID => 랜덤값을 생성
		UUID uuid = UUID.randomUUID();
		// ERASDKLDAMDQWAS_개똥이.jpg
		uploadFileName = uuid.toString() + "_" + uploadFileName;
		//------------- 파일명 중복 방지  끝  -------------

		// (어디에, 무엇을)
		// 계획을 세움
		File saveFile = new File(uploadPath, uploadFileName);
		
		try {
			// 계획 실행. 파일 복사됨(클라이언트의 파일을 서버의 공간으로 복사)
			uploadFile.transferTo(saveFile);
			
			AttachVO attachVO = new AttachVO();
			
			// 1) filename : /2023/01/27/2314532b02_nullpointer.jpg
			String filename = "/" + getFolder().replace("\\", "/") + "/" + uploadFileName;
			attachVO.setFilename(filename);
			
			// 2) filesize
			attachVO.setFilesize((int) uploadFile.getSize());
			
			// 3)thumbnail : /2023/01/27/s_2314532b02_nullpointer.jpg
			String thumb = "/" + getFolder().replace("\\", "/") + "/s_" + uploadFileName;
			attachVO.setThumbnail(thumb);
			
			log.info("attatchVO : " + attachVO);
			
			// 이미지인지 체킹
			// MIME 타입을 가져옴. images/jpeg
			String contentType = Files.probeContentType(saveFile.toPath());
			log.info("contentType : " + contentType);
			// MIME 타입 정보가 images로 시작하는지 여부
			if(contentType.startsWith("image")) {	// 이미지가 맞다면 true
				FileOutputStream thumbnail = new FileOutputStream(
												new File(uploadPath, "s_" + uploadFileName));
				// 썸네일 생성
				Thumbnailator.createThumbnail(uploadFile.getInputStream(), thumbnail, 100, 100);
				thumbnail.close();
			}
			// ATTACH 테이블에 insert
			int result = this.lprodService.uploadFormAction(attachVO);
			log.info("result : " + result);
		} catch (IllegalStateException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return "redirect:/lprod/uploadForm";
	}
	
	/*
	요청 URL : /lprod/uploadFormMultiAction
	요청 파라미터 : uploadFile이라는 이름의 파일객체
	요청 방식 : post
	*/
	@PostMapping("/uploadFormMultiAction")
	public String uploadFormMultiAction(MultipartFile[] uploadFile) {
		// 파일이 저장되는 경로
		String uploadFolder = "c:\\upload";
		
		// make foler 시작------------------------
		// c:\\upload\\2023\\01\\27
		File uploadPath = new File(uploadFolder, getFolder());
		log.info("upload Path : " + uploadPath);
		
		// 만약 연/월/일 해당 폴더가 없다면 생성
		if(!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		// make foler   끝------------------------
				
		for(MultipartFile mf : uploadFile) {
			// 파일 명
			String uploadFileName = mf.getOriginalFilename();
			
			log.info("------------");
			log.info("이미지 명 : " + uploadFileName);
			log.info("파일 크기 : " + mf.getSize());
			
			//------------- 파일명 중복 방지 시작 -------------
			// java.util.UUID => 랜덤값을 생성
			UUID uuid = UUID.randomUUID();
			// ERASDKLDAMDQWAS_개똥이.jpg
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			//------------- 파일명 중복 방지  끝  -------------

			// (어디에, 무엇을)
			// 계획을 세움
			File saveFile = new File(uploadPath, uploadFileName);
			
			try {
				// 계획 실행. 파일 복사됨(클라이언트의 파일을 서버의 공간으로 복사)
				mf.transferTo(saveFile);
				
				// 이미지인지 체킹
				// MIME 타입을 가져옴. images/jpeg
				String contentType = Files.probeContentType(saveFile.toPath());
				log.info("contentType : " + contentType);
				// MIME 타입 정보가 images로 시작하는지 여부
				if(contentType.startsWith("image")) {	// 이미지가 맞다면 true
					FileOutputStream thumbnail = new FileOutputStream(
													new File(uploadPath, "s_" + uploadFileName));
					// 썸네일 생성
					Thumbnailator.createThumbnail(mf.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}
			} catch (IllegalStateException e) {
				log.error(e.getMessage());
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
		
		// redirect
		return "redirect:/lprod/uploadForm";
	}
	
	public static String getFolder() {
		// 2023-01-27형식(format) 지정
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		// 날짜 객체 생성(java.util 패키지)
		Date date = new Date();
		// 2023-01-27
		String str = sdf.format(date);
		// 단순 날짜 문자를 File객체의 폴더 타입으로 바꾸기
		// c:\\upload\\2023\\01\\27
		return str.replace("-", File.separator);
	}
	
	// *썸네일 처리 전 이미지 파일의 판단
	// 썸네일 처리
	// 용량이 큰 파일을 썸네일 처리를 하지 않으면
	// 모바일과 같은 환경에서 많은 데이터를 소비해야 하므로
	// 이미지의 경우 특별한 경우가 아니면 썸네일을 제작해야 함
	// 썸네일은 이미지만 가능함
	public static boolean checkImageType(File file) {
		/*
		 .jpeg / .jpg(JPEG)의 MIME 타입 : image/jpeg
		*/
		// MIME 타입을 통해 이미지 여부 확인
		try {
			// file.toPath() : 파일 객체를 path객체로 변환
			String contentType = Files.probeContentType(file.toPath());
			log.info("contentType : " + contentType);
			// MIME 타입 정보가 image로 시작하는지 여부를 return
			return contentType.startsWith("image");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		
		// 이 파일이 이미지가 아닐 경우
		return false;
	}
}
