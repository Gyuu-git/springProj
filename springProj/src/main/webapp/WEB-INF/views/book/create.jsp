<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="/resources/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<title>책 등록하기</title>
<script type="text/javascript">
$(function() {
	// 이미지 미리보기 시작 -------------------
	$("#input_imgs").on("change", handleImgFileSelect);
	// e : change이벤트
	function handleImgFileSelect(e) {
		// 파일객체에 파일들
		let files = e.target.files;
		// 이미지 배열
		let fileArr = Array.prototype.slice.call(files);
		// fileArr에서 하나 꺼내면 f(파일객체 1개)
		fileArr.forEach(function(f) {
			if(!f.type.match("image.*")){
				alert("이미지 확장자만 가능합니다.");
				return;
			}
			// 이미지를 읽을 객체
			let reader = new FileReader();
			// reader.readAsDataURL(f);의 이벤트
			reader.onload = function(e) {
				let img_html = "<img src='" + e.target.result + "' style='width:30%;' />";
				
				$(".imgs_wrap").append(img_html);
			}
			
			// 이미지를 읽자
			reader.readAsDataURL(f);
		});// end forEach
	}
	// 이미지 미리보기  끝  -------------------
});
</script>
</head>
<body>

<h1>책 등록</h1>

<!-- 폼 페이지 -->
<!-- 
요청 URI : /create?title=개똥이글로리&category=소설&price=10000
요청 파라미터 : title=개똥이글로리&category=소설&price=10000
요청 방식 : post
 -->
<form action="/create" method="post" enctype="multipart/form-data">
	<!-- 폼 데이터 -->
	<div class="imgs_wrap"></div><br />
	<p>제목 : <input type="text" name="title" required /></p>
	<p>카테고리 : <input type="text" name="category" required /></p>
	<p>가격 : <input type="number" name="price" required /></p>
	<p>내용 : <textarea name="cont" rows="5" cols="30"></textarea>
	<p>
		책표지 : <input type="file" id="input_imgs" name="uploadfile" multiple />
	</p>
	<p>
		<input type="submit" value="저장" />
		<input type="button" value="목록" />
	</p>
</form>

<script type="text/javascript">
CKEDITOR.replace('cont');
</script>
</body>
</html>