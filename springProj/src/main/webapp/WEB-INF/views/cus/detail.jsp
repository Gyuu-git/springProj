<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="/resources/css/bootstrap.min.css" />
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js" ></script>
<script type="text/javascript" >
	// 다음 우편번호 검색
	function openHomeSearch() {
		new daum.Postcode({
			// 다음 창에서 검색이 완료되면 콜백함수에 의해 결과 데이터가 data 객체로 들어옴
			oncomplete: function(data) {
				// 우편 번호
				$("#postNo").val(data.zonecode);
				$("#cusAddr").val(data.address);
				$("#addrDet").val(data.buildingName);
			}
		}).open();
	}
</script>

<nav class="navbar bg-body-tertiary">
	<div class="container-fluid">
		<a class="navbar-brand" href="#">고객관리</a>
	</div>
</nav>

<!-- 폼페이지
요청 URI : /cus/createPost
요청 파라미터 : {cusNum=12345, cusNm=개똥이, postNo=33233..}
요청 방식 : post
-->
<form:form modelAttribute="cusVO" action="/cus/createPost" method="post">
	<div class="bd-example">
		<div class="mb-3">
			<label for="cusNum" class="form-label">고객번호</label>
<!-- 			<input type="text" class="form-control" id="cusNum"  -->
<!-- 					name="cusNum" placeholder="고객번호를 입력해주세요" /> -->
		<!-- 꺽새form콜론input에는 type="text"랑 id="cusNum이랑 name="cusNum"이 내장 -->
			<form:input path="cusNum" class="form-control" />
			<!-- path : cusVO객체의 cusNum 멤버변수 -->
			<form:errors path="cusNum" style="color: red;" />
		</div>
		<div class="mb-3">
			<label for="cusNm" class="form-label">고객명</label>
<!-- 			<input type="text" class="form-control" id="cusNm"  -->
<!-- 					name="cusNm" placeholder="고객명를 입력해주세요" /> -->
			<form:input path="cusNm" class="form-control" />
			<form:errors path="cusNm" style="color: red;" />
		</div>
		<div class="mb-3">
			<div>
				<label for="postNo" class="form-label">우편번호</label>
			</div>
<!-- 			<input type="text" class="form-control" id="postNo"  -->
<!-- 					name="postNo" placeholder="우편번호를 입력해주세요" -->
<!-- 					style="width:80%; float:left;" /> -->
			<form:input path="postNo" class="form-control" style="width:80%; float:left;" />
			<input class="btn btn-info" type="button" value="우편번호 검색" 
					style="width:20%; float:right;" onclick="openHomeSearch()" />
		</div>
		<div class="mb-3">
			<label for="cusAddr" class="form-label">주소</label>
<!-- 			<input type="text" class="form-control" id="cusAddr"  -->
<!-- 					name="cusAddr" placeholder="주소를 입력해주세요"> -->
			<form:input path="cusAddr" class="form-control" />
		</div>
		<div class="mb-3">
			<label for="addrDet" class="form-label">상세 주소</label>
<!-- 			<input type="text" class="form-control" id="addrDet"  -->
<!-- 					name="addrDet" placeholder="상세 주소를 입력해주세요"> -->
			<form:input path="addrDet" class="form-control" />
		</div>
		<div class="mb-3">
			<label for="cusPhe" class="form-label">연락처</label>
<!-- 			<input type="text" class="form-control" id="cusPhe"  -->
<!-- 					name="cusPhe" placeholder="연락처를 입력해주세요"> -->
			<form:input path="cusPhe" class="form-control" />
			<form:errors path="cusPhe" style="color: red;" />
		</div>
		<div class="mb-3">
			<label for="cusBir" class="form-label">생일</label>
<!-- 			<input type="date" class="form-control" id="cusBir"  -->
<!-- 					name="cusBir" placeholder="생일을 입력해주세요"> -->
			<form:input path="cusBir" class="form-control" />
			<form:errors path="cusBir" style="color: red;" />
		</div>
		<div class="mb-3">
			<label for="cusBir" class="form-label">취미</label>
			<form:checkbox path="hobbyList" value="Music" label="Music" />
			<form:checkbox path="hobbyList" value="Movie" label="Movie" />
			<form:checkbox path="hobbyList" value="Sports" label="Sports" />
		</div>
		<div class="mb-3">
			<label for="gender" class="form-label">성별</label>
			<form:radiobutton path="gender" value="male" label="male" />
			<form:radiobutton path="gender" value="female" label="female" />
			<form:radiobutton path="gender" value="others" label="others" />
		</div>
		<div class="mb-3">
			<label for="nationality" class="form-label">국적</label>
			<form:select path="nationality" items="${nationalityMap}" />
		</div>
		<div class="mb-3">
			<!-- 고객 : 소유 자동차(List<CarVO> carVOList)  = 1 : N -->
			<div class="card" style="width: 100%;">
				<div class="card-header">
					소유자동차
				</div>
				<ul class="list-group list-group-flush">
					<li class="list-group-item">
						<input type="hidden" name="carVOList[0].cusNum" value="${cusNum}" />
						<input type="text" class="form-control" name="carVOList[0].carNum"
								style="width:25%;float:left;" placeholder="자동차번호" required />
			            <input type="text" class="form-control" name="carVOList[0].mnfNum"
			            		style="width:25%;float:left;" placeholder="제조 번호" required />
			            <input type="date" class="form-control" name="carVOList[0].dt"
			            		style="width:25%;float:left;" placeholder="연식" required />
			            <input type="text" class="form-control" name="carVOList[0].dist"
			            		style="width:25%;float:left;" placeholder="주행거리" required />
					</li>
					<li class="list-group-item">
						<input type="hidden" name="carVOList[1].cusNum" value="${cusNum}" />
						<input type="text" class="form-control" name="carVOList[1].carNum"
								style="width:25%;float:left;" placeholder="자동차번호" required />
			            <input type="text" class="form-control" name="carVOList[1].mnfNum"
			            		style="width:25%;float:left;" placeholder="제조 번호" required />
			            <input type="date" class="form-control" name="carVOList[1].dt"
			            		style="width:25%;float:left;" placeholder="연식" required />
			            <input type="text" class="form-control" name="carVOList[1].dist"
			            		style="width:25%;float:left;" placeholder="주행거리" required />
					</li>
				</ul>
			</div>
		</div>
		<div class="mb-3">
			<button type="submit" class="btn btn-primary btn-lg">등록</button>
		</div>
	</div>
</form:form>