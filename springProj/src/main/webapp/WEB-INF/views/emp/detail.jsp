<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="/resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="/resources/css/sweetalert2.min.css" />
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="/resources/js/sweetalert2.min.js"></script>
<script type="text/javascript" >
// 다음 우편번호 검색
function openHomeSearch() {
	new daum.Postcode({
		// 다음 창에서 검색이 완료되면 콜백함수에 의해 결과 데이터가 data 객체로 들어옴
		oncomplete: function(data) {
			// 우편 번호
			$("#zipCode").val(data.zonecode);
			$("#address").val(data.address);
			$("#detAddress").val(data.buildingName);
		}
	}).open();
}

$(function() {
	// 직원번호 자동등록
	// 아작났어유..피씨다타써
	// 피 : processType:false는 파일 업로드 시 사용(let formData = new formData())
	// 씨 : contentType : "application/json;charset:utf-8"(보내는 타입)
	// 다 : data : JSON.stringify(data);
	// 타 : type : "post"
	// 써 : success: function(){}
// 	$.ajax({
// 		url: "/emp/getEmpNum",
// 		type: "post",
// 		success: function(result) {
// 			console.log("result: " + result);
// 			$("#empNumAjax").val(result);
// 		}
// 	});
	
	// 매니저 선택하기
	$("#btnEmpMjNum").on("click", function() {
		$.ajax({
			url: "/emp/getEmpAll",
			type: "post",
			dataType: "json",
			success: function (result) {
				// result : List<EmpVO> empVOList
				var html = "";
				$.each(result, function(index, empVO) {
					html += '<tr class="trSelect">'
						  + '	<th scope="row">' + (index + 1) + '</th>'
						  + '	<td>' + empVO.empNum + '</td>'
						  + '	<td>' + empVO.empNm + '</td>'
						  + '</tr>';
				});
				$("tbody").html(html);
			}
		});
	});
	
	// 동적으로 생성된 요소의 이벤트 처리
	// $(".trSelect").on("click", function () {})은 작동을 안할것임
	$(document).on("click", ".trSelect", function () {
		// tr이 여러개인데 그 중 클릭한 바로 그 tr
		let objArr = $(this).children("td");
		let empMjNum = objArr[0].innerText;
		let empMjNm = objArr[1].innerText;
		$("#empMjNum").val(empMjNum);
		$("#empMjNm").val(empMjNm);
		$(".btn-close").click();
		
		/*
		let empNum = $(this).find("td").html();
		let empNm = $(this).find("td").next().html();
		$("#empMjNum").val(empNum);
		$("#empMjNm").val(empNm);
		$(".btn-close").click();
		*/
	});
	
	$(".link").on("click", function() {
		// toggleClass() 메서드는 해당 클래스를 토글함
// 		$(".link").toggleClass("active");
		
		$(".link").attr("class", "nav-link link");
		$(".link").removeAttr("aria-current");
		$(this).attr("class", "nav-link link active");
		$(this).attr("aria-current", "page");
		
		let id = $(this).data("id");
		if(id == "employee"){
			$("#employee").css("display", "block");
			$("#manager").css("display", "none");
		}else{
			$("#manager").css("display", "block");
			$("#employee").css("display", "none");
		}
		
		Toast.fire({
			icon: 'success',
			title: 'miee'
		});
	});
	
	let Toast = Swal.mixin({
		toast:true,
		position: 'top-end',
		showConfirmButton: false,
		timer: 3000
	});
	
	// 수정버튼 클릭 -> spn1:none / spn2: block
	$('#edit').on("click", function() {
		$("#spn1").css("display", "none");
		$("#spn2").css("display", "block");
		// 읽기전용 해제
		$(".form-control").removeAttr("readonly");
		// 검색버튼 사용
		$("#btnPostno").removeAttr("disabled");
		$("#btnEmpMjNum").removeAttr("disabled");
	});
	// 취소버튼 클릭 -> sp1: block / spn2: none
	$('#cancel').on("click", function() {
		$("#spn1").css("display", "block");
		$("#spn2").css("display", "none");
		// 읽기전용
		$(".form-control").attr("readonly", true);
		// 검색버튼 비활성
		$("#btnPostno").attr("disabled", true);
		$("#btnEmpMjNum").attr("disabled", true);
	});
	
	// 삭제버튼 클릭
	$("#delete").on("click", function() {
		// 파라미터를 javascript 변수에 저장하고자 한다면...
		let empNumJs = "${param.empNum}";	// 이렇게는 안쓸것임(보안에 취약할 수 있음)
		let empNum = $("#employeeEmpNum").val();
		// 세션스토리지에 변수값을 넣고자 한다면..
		sessionStorage.setItem("empNum", empNum);
		
		console.log("empNumJs : " + empNumJs, ", empNum : " + empNum);
		
		// true(1) / false(0)
		let cfm = confirm("삭제하시겠습니까?");
		console.log("cfm : " + cfm);
		
		if(cfm > 0){	// 삭제 수행
			// json 오브젝트
			let data = {"empNum" : empNum};
			
			$.ajax({
				url: "/emp/deletePost",
				contentType: "application/json;charset=utf-8",
				data: JSON.stringify(data),
				dataType: "json",
				type: "post",
				success: function(result) {
					// controller에서 map.put("result", 1+""); / ResponseBody
					console.log("result : " + JSON.stringify(result));
					// result : {"result":"1"}
					let str = result.result;	// 1 or 0
					// str이 0보다 크면 성공, 아니면 실패
					// 성공 시 /emp/list로 이동 / 실패 시 실패 메시지를 보여줌
					if(str > 0){
						location.href = "/emp/list";
					}else{
						Toast.fire({
							icon: "success",
							title: "삭제 실패ㅠㅠㅠ"
						});
					}
				}
			});
		}else{	// 삭제 취소
			Toast.fire({
				icon: "success",
				title: "삭제가 취소되었씁니다."
			});
		}
	});
});
</script>
<!-- 
	요청 URI : /emp/createPost
	요청 파라미터 : {empNum=EMP001, zipCode=1234, address=대전...}
	요청 방식 : post
-->
<!-- data : List<EmpVO> empVOList
	stat.index : 0부터
	stat.count : 1부터
	
	scope : page(동일 jsp), request(동일 요청), session(동일 웹브라우저), application(웹브라우저)
-->
<c:forEach var="empVO" items="${data}" varStatus="stat">
	<c:choose>
		<c:when test="${empVO.empNum == param.empNum}"><!-- 직원 -->
			<c:set var="employee" value="${empVO}" scope="page" />
		</c:when>
		<c:otherwise><!-- 관리자 -->
			<c:set var="manager" value="${empVO}" scope="page" />
		</c:otherwise>
	</c:choose>
</c:forEach>
<ul class="nav nav-tabs">
	<li class="nav-item">
		<a class="nav-link link active" data-id="employee" aria-current="page" href="#">직원</a>
	</li>
	<li class="nav-item">
		<a class="nav-link link" data-id="manager" href="#">관리자</a>
	</li>
</ul>

<div id="employee" class="bd.example">
<form:form modelAttribute="empVO" action="/emp/createPost" method="post" class="row g-3">
	<input type="hidden" name="mode" id="mode" value="update" />
	<!-- 직원번호 시작 -->
	<div class="col-md-6">
		<label for="empNum" class="form-label">직원번호</label>
<!--     	<input type="text" name="empNum" class="form-control" id="empNum" -->
<%--     			value="${empNum}" placeholder="직원번호를 검색해주세요" /> --%>
<%-- 		<form:input path="empNum" class="form-control" /> --%>
		<input type="text" name="empNum" class="form-control" id="employeeEmpNum"
    			value="${employee.empNum}" placeholder="우편번호를 검색해주세요" readOnly />
	</div>
	<!-- 직원번호 끝 -->
	<!-- 주소 시작 -->
	<div class="col-md-2">
    	<label for="zipCode" class="form-label">우편번호</label>
    	<input type="text" name="zipCode" class="form-control" id="zipCode"
    			placeholder="우편번호를 검색해주세요" readonly />
    	<button type="button" id="btnPostno" class="btn btn-primary" 
    			onclick="openHomeSearch()" disabled>검색</button>
	</div>
	<div class="col-12">
		<label for="address" class="form-label">주소</label>
		<input type="text" name="address" class="form-control" id="address" 
				placeholder="주소를 검색해주세요" readonly />
	</div>
	<div class="col-12">
		<label for="detAddress" class="form-label">상세주소</label>
		<input type="text" name="detAddress" class="form-control" id="detAddress" 
				placeholder="상세주소를 입력해주세요" value="${employee.empAddr}" readonly />
	</div>
	<!-- 주소 끝 -->
	<!-- 연락처 시작 -->
	<div class="col-md-6">
		<label for="empPne" class="form-label">연락처</label>
		<input type="text" name="empPne" class="form-control" id="empPne"
				placeholder="연락처를 입력해주세요" value="${employee.empPne}" readonly required />
	</div>
	<!-- 연락처 끝 -->
	<!-- 직원명 시작 -->
	<div class="col-md-6">
		<label for="empNm" class="form-label">직원명</label>
		<input type="text" name="empNm" class="form-control" id="empNm"
				placeholder="직원명을 입력해주세요" value="${employee.empNm}" readonly required />
	</div>
	<!-- 직원명 끝 -->
	<!-- 급여 시작 -->
	<div class="col-md-6">
		<label for="empPay" class="form-label">급여</label>
		<input type="number" name="empPay" class="form-control" id="empPay"
				placeholder="급여를 입력해주세요" value="${employee.empPay}" readonly required />
	</div>
	<!-- 급여 끝 -->
	<!-- 매니저 등록 시작 -->
	<div class="col-md-6">
		<label for="empMjNm" class="form-label">매니저명</label>
		<input type="text" name="empMjNum" class="form-control" 
				id="empMjNum" value="${employee.empMjNum}" readOnly  />
		<input type="text" class="form-control" id="empMjNm"
				placeholder="직원명을 입력해주세요" value="${manager.empNm}" readOnly />
		<button type="button" class="btn btn-primary" id="btnEmpMjNum" 
				data-toggle="modal" data-target="#exampleModal" disabled>검색</button>
	</div>
	<!-- 매니저 등록 끝 -->
	<div class="col-12">
		<!-- 일반모드 시작 -->
		<span id="spn1">
			<button type="button" id="edit" class="btn btn-primary">수정</button>
			<button type="button" id="delete" class="btn btn-danger">삭제</button>
		</span>
		<!-- 수정모드 시작 -->
		<span id="spn2" style="display: none;">
			<button type="submit" id="ok" class="btn btn-success">확인</button>
			<button type="button" id="cancel" class="btn btn-warning">취소</button>
		</span>
		<a href="/emp/list" class="btn btn-info">목록</a>
	</div>
</form:form>
</div>
	
<div id="manager" class="bd.example" style="display: none;">
<c:if test="${manager == null}"><!-- 이 직원은 매니저가 없음 -->
	<div class="text-center">
		<div class="error mx-auto" data-text="관리자가 없습니다."
			style="font-size: 23px;">관리자가 없습니다.</div>
	</div>
</c:if>
<c:if test="${manager != null}">
<form:form modelAttribute="empVO" action="/emp/createPost" method="post" class="row g-3">
	<!-- 직원번호 시작 -->
	<div class="col-md-6">
		<label for="empNum" class="form-label">직원번호</label>
<!--     	<input type="text" name="empNum" class="form-control" id="empNum" -->
<%--     			value="${empNum}" placeholder="직원번호를 검색해주세요" /> --%>
<%-- 		<form:input path="empNum" class="form-control" /> --%>
		<input type="text" name="empNum" class="form-control" id="empNumAjax"
    			value="${manager.empNum}" placeholder="우편번호를 검색해주세요" readonly />
	</div>
	<!-- 직원번호 끝 -->
	<!-- 주소 시작 -->
	<div class="col-md-2">
    	<label for="zipCode" class="form-label">우편번호</label>
    	<input type="text" name="zipCode" class="form-control" id="zipCode"
    			placeholder="우편번호를 검색해주세요" readonly />
    	<button type="button" class="btn btn-primary" onclick="openHomeSearch()">검색</button>
	</div>
	<div class="col-12">
		<label for="address" class="form-label">주소</label>
		<input type="text" name="address" class="form-control" id="address" 
				placeholder="주소를 검색해주세요" readonly />
	</div>
	<div class="col-12">
		<label for="detAddress" class="form-label">상세주소</label>
		<input type="text" name="detAddress" class="form-control" id="detAddress" 
				placeholder="상세주소를 입력해주세요" value="${manager.empAddr}" />
	</div>
	<!-- 주소 끝 -->
	<!-- 연락처 시작 -->
	<div class="col-md-6">
		<label for="empPne" class="form-label">연락처</label>
		<input type="text" name="empPne" class="form-control" id="empPne"
				placeholder="연락처를 입력해주세요" value="${manager.empPne}" required />
	</div>
	<!-- 연락처 끝 -->
	<!-- 직원명 시작 -->
	<div class="col-md-6">
		<label for="empNm" class="form-label">직원명</label>
		<input type="text" name="empNm" class="form-control" id="empNm"
				placeholder="직원명을 입력해주세요" value="${manager.empNm}" required />
	</div>
	<!-- 직원명 끝 -->
	<!-- 급여 시작 -->
	<div class="col-md-6">
		<label for="empPay" class="form-label">급여</label>
		<input type="number" name="empPay" class="form-control" id="empPay"
				placeholder="급여를 입력해주세요" value="${manager.empPay}" required />
	</div>
	<!-- 급여 끝 -->
	<!-- 매니저 등록 시작 -->
	<div class="col-md-6">
		<label for="empMjNm" class="form-label">매니저명</label>
		<input type="text" name="empMjNum" class="form-control" 
				id="empMjNum" value="${manager.empMjNum}" readonly />
		<input type="text" class="form-control" id="empMjNm"
				placeholder="직원명을 입력해주세요" readonly />
		<button type="button" class="btn btn-primary" id="btnEmpMjNum" 
				data-toggle="modal" data-target="#exampleModal" >검색</button>
	</div>
	<!-- 매니저 등록 끝 -->
	<div class="col-12">
		<button type="submit" class="btn btn-primary">Sign in</button>
	</div>
</form:form>
</c:if>
</div>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
				<button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<!-- 직원 목록 시작 -->
				<div class="bd-example">
					<table class="table table-hover">
						<thead>
							<tr>
								<th scope="col">#</th>
								<th scope="col">직원번호</th>
								<th scope="col">이름</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				<!-- 직원 목록  끝  -->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary">Save changes</button>
			</div>
		</div>
	</div>
</div>