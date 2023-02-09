<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="/resources/css/sweetalert2.min.css" />
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="/resources/js/sweetalert2.min.js"></script>

<div class="card shadow mb-4">
	<div class="card-header py-3">
		<h6 class="m-0 font-weight-bold text-primary">직원목록</h6>
	</div>
	<div class="card-body">
		<div class="table-responsive">
			<div id="dataTable_wrapper" class="dataTables_wrapper dt-bootstrap4">
				<!-- 검색 영역 시작 
					action을 생략하면 현재의 URI, method를 생략하면 기본이 get 
				-->
				<form name="frm" id="frm" action="/emp/list" method="get">
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<div class="dataTables_length" id="dataTable_length">
								<label>
									Show
									<select id="show" name="show" aria-controls="dataTable"
										class="custom-select custom-select-sm form-control form-control-sm" 
										onchange="document.frm.submit();">
										<option value="10"
											<c:if test="${param.show == '10'}">selected</c:if>>10</option>
										<option value="25"
											<c:if test="${param.show == '25'}">selected</c:if>>25</option>
										<option value="50"
											<c:if test="${param.show == '50'}">selected</c:if>>50</option>
										<option value="100"
											<c:if test="${param.show == '100'}">selected</c:if>>100</option>
									</select>
									entries
								</label>
							</div>
						</div>
						<div class="col-sm-12 col-md-6">
							<div id="dataTable_filter" class="dataTables_filter">
								<label>
									Search:
									<input type="search" name="keyword" class="form-control form-control-sm" 
										placeholder="검색어를 입력해 주세요" aria-controls="dataTable"
										value="${param.keyword}">
								</label>
								<label>
									<button type="submit" class="btn btn-primary btn-icon-split btn-sm">
										<span class="icon text-white-50">
											<i class="fas fa-flag"></i>
										</span>
										<span class="text">검색</span>
									</button>
								</label>
							</div>
						</div>
					</div>
				</form>
				<!-- 검색 영역  끝  -->
				<div class="row">
					<div class="col-sm-12">
						<table class="table table-bordered dataTable" id="dataTable"
							width="100%" cellspacing="0" role="grid"
							aria-describedby="dataTable_info" style="width: 100%;">
							<thead>
								<tr role="row">
									<th class="sorting sorting_asc" tabindex="0"
										aria-controls="dataTable" rowspan="1" colspan="1"
										aria-sort="ascending"
										aria-label="Name: activate to sort column descending"
										style="width: 57px;">번호</th>
									<th class="sorting" tabindex="0" aria-controls="dataTable"
										rowspan="1" colspan="1"
										aria-label="Position: activate to sort column ascending"
										style="width: 61px;">직원번호</th>
									<th class="sorting" tabindex="0" aria-controls="dataTable"
										rowspan="1" colspan="1"
										aria-label="Office: activate to sort column ascending"
										style="width: 49px;">직원명</th>
									<th class="sorting" tabindex="0" aria-controls="dataTable"
										rowspan="1" colspan="1"
										aria-label="Age: activate to sort column ascending"
										style="width: 31px;">급여</th>
									<th class="sorting" tabindex="0" aria-controls="dataTable"
										rowspan="1" colspan="1"
										aria-label="Start date: activate to sort column ascending"
										style="width: 68px;">매니저</th>
								</tr>
							</thead>
							<tbody>
								<!-- data : List<EmpVO> empVOList
									empVO : EmpVO
								stat.index : 0부터 시작
								stat.count : 1부터 시작
								-->
								<c:forEach var="empVO" items="${data.content}" varStatus="stat">
									<tr <c:if test="${empVO.rnum % 2 == 0}">class="even"</c:if>
										<c:if test="${empVO.rnum % 2 == 1 }">class="odd"</c:if>
										>
										<td class="sorting_1">${empVO.rnum}</td>
										<td><a href="/emp/detail?empNum=${empVO.empNum}" >${empVO.empNum}</a></td>
										<td>${empVO.empNm}</td>
										<td>${empVO.empPay}</td>
										<td>
											<a class="showMj" data-num="${empVO.empMjNum}" 
												data-bs-toggle="modal" data-bs-target="#exampleModal" >
												${empVO.empMjNm}
											</a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12 col-md-5">
						<div class="dataTables_info" id="dataTable_info" role="status"
							aria-live="polite">
							<c:if test="${param.show==null}">
								<c:set var="show" value="10" />
							</c:if>
							<c:if test="${param.show!=null}">
								<c:set var="show" value="${param.show}" />
							</c:if>
							<!-- scope(공유영역) : page(기본), request, session, application -->
							<!-- 종료행 : currentPage * show -->
							<c:set var="endRow" value="${data.currentPage * show}" />                     
							<!-- 시작행 : 종료행 - (size-1) -->
							<c:set var="startRow" value="${endRow - (show-1)}" />
							<!-- 전체행수 : total -->
							<c:if test="${endRow > data.total}">
								<c:set var="endRow" value="${data.total}"/>
							</c:if>
							Showing ${startRow} to ${endRow} of ${data.total} entries
						</div>
					</div>
					<div class="col-sm-12 col-md-7">
						<div class="dataTables_paginate paging_simple_numbers"
							id="dataTable_paginate">
							<ul class="pagination"> 
								<li class="paginate_button page-item previous 
									<c:if test="${data.startPage lt 4}">disabled</c:if>" id="dataTable_previous">
									<a href="/emp/list?currentPage=${data.startPage -1}&keyword=${param.keyword}&show=${param.show}" aria-controls="dataTable" data-dt-idx="0" 
										tabindex="0" class="page-link">Previous</a>
								</li>
								<c:forEach var="pNo" begin="${data.startPage}" end="${data.endPage}">
								<li class="paginate_button page-item 
									<c:if test='${param.currentPage==pNo}'>active</c:if>">
									<a href="/emp/list?currentPage=${pNo}&keyword=${param.keyword}&show=${param.show}" aria-controls="dataTable" data-dt-idx="1" 
										tabindex="0" class="page-link">${pNo}</a>
								</li>
								</c:forEach>
								<li class="paginate_button page-item next 
									<c:if test="${data.endPage ge data.totalPages}">disabled</c:if>" id="dataTable_next">
									<a href="/emp/list?currentPage=${data.endPage +1}&keyword=${param.keyword}&show=${param.show}" aria-controls="dataTable" data-dt-idx="7"
										tabindex="0" class="page-link">Next</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="exampleModalLabel">매니저 정보</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<!-- 내용 시작 -->
				<div class="mb-3 row">
					<label for="staticEmail" class="col-sm-3 col-form-label">직원번호</label>
					<div class="col-sm-10">
						<input type="text" readonly class="form-control-plaintext" id="empNum" value="">
					</div>
				</div>
				 <div class="mb-3 row">
					<label for="staticEmail" class="col-sm-3 col-form-label">직원 명</label>
					<div class="col-sm-10">
						<input type="text" readonly class="form-control-plaintext" id="empNm" value="">
					</div>
				</div>
				<div class="mb-3 row">
					<label for="staticEmail" class="col-sm-3 col-form-label">연락처</label>
					<div class="col-sm-10">
						<input type="text" readonly class="form-control-plaintext" id="empPne" value="">
					</div>
				</div>
				<div class="mb-3 row">
					<label for="staticEmail" class="col-sm-3 col-form-label">주소</label>
					<div class="col-sm-10">
						<input type="text" readonly class="form-control-plaintext" id="empAddr" value="">
					</div>
				</div>
				<div class="mb-3 row">
					<label for="staticEmail" class="col-sm-3 col-form-label">급여</label>
					<div class="col-sm-10">
						<input type="text" readonly class="form-control-plaintext" id="empPay" value="">
					</div>
				</div>
				<div class="mb-3 row">
					<label for="staticEmail" class="col-sm-3 col-form-label">매니저</label>
					<div class="col-sm-10">
						<input type="text" readonly class="form-control-plaintext" id="empMjNm" value="">
					</div>
				</div>
        		<!-- 내용 끝 -->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(function () {
// 	$("#show").val("${param.show}");
	
	$(document).on("click", ".showMj", function() {
		console.log("ajax로 매니저 정보 가져와보자");
		console.log("요청 URI : /emp/showMj");
		console.log("요청 파라미터(json) : {'empNum' : 'EMP006'}");
		console.log("요청방식 : post");
		
		let empNum = $(this).data("num");
		data = {"empNum" : empNum};
		
		$.ajax({
			url: "/emp/showMj",
			contentType: "application/json;charset=utf-8",
			data : JSON.stringify(data),
			dataType: "json",
			type: "post",
			success: function (result) {
				console.log("result : " + JSON.stringify(result));
				
				$("#empNum").val(result.empNum);
				$("#empNm").val(result.empNm);
				$("#empPne").val(result.empPne);
				$("#empAddr").val(result.empAddr);
				$("#empPay").val(result.empPay);
				$("#empMjNm").val(result.empMjNum);
			}
		});
	});
})
</script>