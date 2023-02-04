<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 파일업로드
1) method는 꼭 post !
2) enctype="multipart/form-data"
3) <input type="file" name="uploadFile" .. /> name속성이 꼭 있어야 함!
-->
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript">
$(function() {
	// 이미지 미리보기 시작 ----------------------
	let sel_file = [];
	
	$("#input_imgs").on("change", handleImgFileSelect);
	$("#input_imgs2").on("change", handleImgFileSelect);
	// 파라미터 e : onchange 이벤트 객체
	function handleImgFileSelect(e) {
		$(".imgs_wrap").html("");
		// 이벤트가 발생된 타겟 안에 들어있는 이미지 파일들을 가져옴
		let files = e.target.files;
		// 이미지가 여러개가 있을 수 있으므로 이미지들을 분리해서 배열로 만듦
		let fileArr = Array.prototype.slice.call(files);
		
		// 파일 오브젝트의 배열 반복. f : 배열 안에 들어있는 각각의 이미지 파일 객체
		fileArr.forEach(function(f) {
			// 이미지 파일이 아닌 경우 이미지 미리보기 실패 처리(image/jpeg)
			if(!f.type.match("image.*")){
				alert("이미지 확장자만 가능합니다.");
				// 함수종료
				return;
			}
			// 이미지 객체(f)를 전역 배열타입 변수(sel_file)에 넣자
			sel_file.push(f);
			// 이미지 객체를 읽을 자바스크립트의 reader 객체 생성
			let reader = new FileReader();
			// e : reader객체가 이미지 객체를 읽는 이벤트
			reader.onload = function(e) {
				// e.target : 이미지 객체
				// e.target.result : reader가 이미지를 다 읽은 결과
				let img_html = "<p><img src='" + e.target.result + "' /></p>";
				// div 사이에 이미지가 렌더링되어 화면에 보임
				// 객체.append : 누적, .html : 새로고침, innerHTML : J/S
				$(".imgs_wrap").append(img_html);
			}
			// f : 이미지 파일 객체를 읽은 후 다음 이미지 파일(f)를 위해 초기화 함
			reader.readAsDataURL(f);
		}); // end forEach;
	}
	// 이미지 미리보기  끝  ----------------------
});
</script>
<!-- 
	요청 URL : /lprod/uploadFormAction
	요청 파라미터 : uploadFile이라는 이름의 파일객체
	요청 방식 : post
-->
<div class="imgs_wrap"></div>
<form action="/lprod/uploadFormAction" method="post" enctype="multipart/form-data">
	<input type="file" id="input_imgs" name="uploadFile" />
	<button type="submit">업로드</button>
</form>
<hr />
<!-- 
	요청 URL : /lprod/uploadFormMultiAction
	요청 파라미터 : uploadFile이라는 이름의 파일객체
	요청 방식 : post
 -->
<form action="/lprod/uploadFormMultiAction" method="post" enctype="multipart/form-data">
	<input type="file" id="input_imgs2" name="uploadFile" multiple />
	<button type="submit">다중 업로드</button>
</form>