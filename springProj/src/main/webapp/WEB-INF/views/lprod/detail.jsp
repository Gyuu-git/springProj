<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/resources/css/bootstrap.min.css" />
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript">
// document의 요소들이 모두 로딩된 후 실행
$(function() {
	$('.btn-icon-split').on("click", function() {
		// 상품분류코드 자동생성
		// 아작났어유..피씨다타써
		/*
		url : 요청 경로
		contentType : 보내는 데이터의 타입
		dataType : 응답 데이터 타입
		data : 요청시 전송할 데이터
		type : 요청방식. get, post, put
		*/
		$.ajax({
			url: "/lprod/getLprodGu",
			type: "post",
			success:function(res){
				$('input[name="lprodGu"]').val(res);
			}
		});
	});
	
	$("#btnRegist").on("click", function() {
		let lprodGu = $("#lprodGu").val();
		let lprodNm = $("#lprodNm").val();
		
		if(jQuery.trim(lprodGu) == ""){
			alert("상품분류코드를 작성해주세요");
			$('#lprodGu').focus();
			return false;
		}
		if(jQuery.trim(lprodNm) == ""){
			alert("상품분류명을 작성해주세요");
			$('#lprodNm').focus();
			return false;
		}
		
		// [submit]을 실행
// 		$("#frm").submit(function() { alert("등록을 진행합니다."); });
		/*
		요청 URI : /lprod/createPost
		요청 파라미터 : {"lprodId" : "10", "lprodGu" : "P404", "lprodNm" : "간식류"}
		요청 방식 : post
		*/
		$("form").submit();
	});
	
	// 수정버튼 클릭 -> 수정모드로 전환
	$("#edit").on("click", function() {
		// 모드 변경
		$("#spn1").css("display", "none");
		$("#spn2").css("display", "block");
		
		// 2) 입력란 활성화
		$("#lprodNm").removeAttr("readonly");
		
		// 4) form의 action속성의 속성값을 updatePost로 변경
		$('#frm').attr("action", "/lprod/updatePost");
	});
	
	// 삭제버튼 클릭
	$("#delete").on("click", function() {
		// from의 action속성의 속성값을 /lprod/deletePost로 변경
		$("#frm").attr("action", "/lprod/deletePost");
		// 삭제여부 재확인
		// true(1) / false(0)
		let result = confirm("삭제하시겠습니까?");
		// submit
		if(result > 0){	// 삭제 실행
			$("#frm").submit();
		}else{	// 삭제 안함
			alert("삭제가 취소되었습니다.");
		}
	});
	
	$(".btn-outline-primary").on("click", function() {
		let filename = $(this).data("filename");
		console.log(filename);
		
		/* sessionStorage
		- 세션 스토리지는 각각의 URL에 대해 독립적인 저장 공간을 제공함
		- 저장 공간이 쿠키보다 큼
		- session은 JSP 내장 객체 vs sessionStorage는 클라이언트 객체(쿠키처럼..)
		*/
		// session.setAttribute("filename", filename);
		sessionStorage.setItem("filename", filename);
		
		$(".bg-register-image").html("<img style='width:100%;' src='/resources/upload" + filename + "' alt='" + filename + "' />");
		$(".modal-body").html("<img style='width:100%;' src='/resources/upload" + filename + "' alt='" + filename + "' />");
	});
	
	// 파일 다운로드 하기
	$("#btnDownload").on("click", function() {
		let fnm = sessionStorage.getItem("filename");
		console.log("fnm : " + fnm);
		
		// <iframe id="ifrm" name="ifrm" style="display:none;" >
		let vIfrm = document.getElementById("ifrm");
// 		vIfrm.src = "/download?fileName=" + fnm;
		vIfrm.href = "/download?fileName=" + fnm;
		vIfrm.click();
	});
})
</script>
<div class="container">
	<div class="card o-hidden border-0 shadow-lg my-5">
		<div class="card-body p-0">
			<!-- Nested Row within Card Body -->
			<div class="row">
				<div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
				<div class="col-lg-7">
					<div class="p-5">
						<div class="text-center">
							<h1 class="h4 text-gray-900 mb-4">Create an Account!</h1>
						</div>
						<form id="frm" class="user" action="/lprod/createPost" method="post">
							<div class="form-group row">
								<div class="col-sm-6 mb-3 mb-sm-0">
								<!-- 변경 제약
									disabled : 값이 전달 안됨
									readonly : 파라미터로 값이 전달됨
								 -->
									<input type="text" class="form-control form-control-user"
										id="lprodId" name="lprodId" value="${lprodVO.lprodId}" readonly>
								</div>
								<div class="col-sm-6">
									<button type="button" class="btn btn-info btn-icon-split" disabled>
                                        <span class="icon text-white-50">
                                            <i class="fas fa-info-circle"></i>
                                        </span>
                                        <span class="text">분류코드 자동생성</span>
                                    </button>
								</div>
							</div>
							<div class="form-group">
								<input type="text" class="form-control form-control-user"
									id="lprodGu" name="lprodGu" placeholder="상품분류 코드" 
									value="${lprodVO.lprodGu}" readonly required />
							</div>
							<div class="form-group">
								<input type="text" class="form-control form-control-user"
									id="lprodNm" name="lprodNm" placeholder="상품분류 명" 
									value="${lprodVO.lprodNm}" readonly required />
							</div>
							<!-- 일반모드 시작 -->
							<span id="spn1">
								<button type="button" id="edit" class="btn btn-warning btn-user btn-block"
									style="width:50%; float:left;">
									수정
								</button> 
								<button type="button" id="delete" class="btn btn-danger btn-user btn-block"
									style="width:50%;">
									삭제
								</button>
							</span>
							<!-- 일반모드  끝  -->
							
							<!-- 수정모드 시작 -->
							<span id="spn2" style="display: none;">
								<button type="submit" class="btn btn-warning btn-user btn-block"
									style="width:50%; float:left;">
									확인
								</button> 
								<!-- lprodGu=P406 : ${param} -->
								<button type="button" id="delete" class="btn btn-danger btn-user btn-block" style="width:50%;"
									onclick="javascript:location.href='/lprod/detail?lprodGu=${param.lprodGu}'">
									취소
								</button>
							</span>
							<!-- 수정모드  끝  -->
							<hr />
<!-- 							<button type="button" class="btn btn-primary" -->
<!-- 								data-toggle="modal" data-target="#modal-default"> -->
<!-- 								Default Modal 띄우기 -->
<!-- 							</button> -->
<!-- 							<hr /> -->
<!-- 							<a data-toggle="modal" href="#modal-default">Default Modal 띄우기2</a> -->
<!-- 							<hr /> -->
<!-- 							<p data-toggle="modal" data-target="#modal-default">Default Modal 띄우기3</p> -->
<!-- 							<hr /> -->
							<div class="accordion" id="accordionExample">
							<!-- lprodVO.attachVOList : LisT<AttachVO> -->
							<!-- 모달을 띄우는 방법
							1. button으로 띄우기
								<button type="button" class="btn btn-default"
									data-toggle="modal" data-target="#modal-default">
									Default Modal 띄우기
								</button>
							2. a 태그로 띄우기
								<a data-toggle="modal" href="#modal-default"
							3. 기타 요소로 띄우기
								<p data-toggle="modal" data-target="#modal-default"
							-->
							<c:forEach var="attachVO" items="${lprodVO.attachVOList}" varStatus="status">
								<div class="accordion-item">
									<h2 class="accordion-header" id="headingOne">
										<button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne${status.index}" aria-expanded="true" aria-controls="collapseOne">
											첨부파일${status.count}
										</button>
									</h2>
									<div id="collapseOne${status.index}" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
										<div class="accordion-body">
											<div class="card" style="width: 100px;">
												<img src="/resources/upload${attachVO.thumbnail}" class="card-img-top" 
													title="${attachVO.thumbnail}" alt="${attachVO.thumbnail}">
												<div class="card-body">
													<h5 class="card-title"></h5>
													<p class="card-text">파일크기 : ${attachVO.filesize}</p>
													<a data-toggle="modal" href="#modal-default" 
														data-filename="${attachVO.filename}" class="btn btn-outline-primary">
														보기
													</a>
												</div>
											</div>
										</div>
									</div>
								</div>
							</c:forEach>
							</div>
							<a href="/lprod/list" class="btn btn-primary btn-user btn-block">
								<i class="fab fa-google fa-fw"></i> 목록보기
							</a>
						</form>
						<hr />
						
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="modal-default" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
				<button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
			</div>                                                                                     
			<div class="modal-body">
			</div>
			<div class="modal-footer">
				<button type="button" id="btnDownload" class="btn btn-primary">다운로드</button>
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<a id="ifrm" name="ifrm" style="display:none;" ></a>