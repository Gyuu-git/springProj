<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
window.onload = function () {
	document.getElementById("sel").value = ${param.size};
}
</script>
<!-- Page Heading -->
<h1 class="h3 mb-2 text-gray-800">Tables</h1>
<p class="mb-4">
	DataTables is a third party plugin that is used to generate the demo
	table below. For more information about DataTables, please visit the 
	<a target="_blank" href="https://datatables.net">
		official DataTables documentation
	</a>
</p>

<!-- DataTales Example -->
<div class="card shadow mb-4">
	<div class="card-header py-3">
		<h6 class="m-0 font-weight-bold text-primary">DataTables Example</h6>
	</div>
	<div class="card-body">
		<div class="table-responsive">
			<div id="dataTable_wrapper" class="dataTables_wrapper dt-bootstrap4">
				<div class="row">
					<form id="frm" name="frm" style="width:100%;">
						<div class="col-sm-12 col-md-6" style="float:left;">
							<div class="dataTables_length" id="dataTable_length">
								<label>Show 
									<select id="sel" name="size" aria-controls="dataTable"
										class="custom-select custom-select-sm form-control form-control-sm"
										onchange="document.frm.submit();">
										<option value="10">10</option>
										<option value="25">25</option>
										<option value="50">50</option>
										<option value="100">100</option>
									</select>
									entries
								</label>
							</div>
						</div>
						<div class="col-sm-12 col-md-6" style="float:right;">
							<div id="dataTable_filter" class="dataTables_filter">
									<label>Search:
										<input type="search" name="keyword" value="${param.keyword}"
											class="form-control form-control-sm" 
											placeholder="" aria-controls="dataTable">
									</label>
<!-- 									<input type="submit" class="btn btn-primary" value="검색" /> -->
							</div>
						</div>
					</form>
				</div>
				<div class="row">
					<div class="col-sm-12">
						<table class="table table-bordered dataTable" id="dataTable"
								width="100%" cellspacing="0" role="grid"
								aria-describedby="dataTable_info" style="width: 100%;">
							<thead>
								<tr role="row">
									<th class="sorting sorting_asc" tabindex="0" 
										aria-label="순번: activate to sort column descending"
										aria-controls="dataTable" aria-sort="ascending"
										rowspan="1" colspan="1" style="width: 101px;">
										순번
									</th>
									<th class="sorting" tabindex="0" aria-controls="dataTable" 
										aria-label="상품분류 아이디: activate to sort column ascending"
										rowspan="1" colspan="1" style="width: 152px;">
										상품분류 아이디
									</th>
									<th class="sorting" tabindex="0" aria-controls="dataTable"
										aria-label="상품분류 구분: activate to sort column ascending"
										rowspan="1" colspan="1" style="width: 72px;">
										상품분류 구분
									</th>
									<th class="sorting" tabindex="0" aria-controls="dataTable"
										aria-label="상품분류 명: activate to sort column ascending"
										rowspan="1" colspan="1" style="width: 31px;">
										상품분류 명
									</th>
								</tr>
							</thead>
							<tbody>
							<!-- 
								1) 페이징 이전
								List<LprodVO> lprodVOList
								mav.addObject("data", lprodVOList);
								
								2) 페이징 이후
								mav.addObject("data", new ArticlePage<LprodVO>(total, currentPage, size, lprodVOList));
								
								ArticlePage에서 List<LprodVO> lprodVOList를 끄집어 내야함
								data는 ArticlePage이고
								data.content는 ArticlePage의 content멤버변수
								
								viv : 포이치 비브
								
								stat.count : 1부터 시작
								stat.index : 0부터 시작
							-->
								<c:forEach var="lprodVO" items="${data.content}" varStatus="stat" >
									<tr 
										<c:if test="${stat.count%2!=0}">class="odd"</c:if>
										<c:if test="${stat.count%2==0}">class="even"</c:if>
									>
										<td class="sorting_1">${lprodVO.rnum}</td>
										<td>${lprodVO.lprodId}</td>
										<td>${lprodVO.lprodGu}</td>
										<td><a href="/lprod/detail?lprodGu=${lprodVO.lprodGu}" >${lprodVO.lprodNm}</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12 col-md-5">
						<div class="dataTables_info" id="dataTable_info" 
								role="status" aria-live="polite">
							Showing 1 to 10 of 57 entries
						</div>
					</div>
					<!-- EL태그 정리
						== : eq(equal)
						!= : ne(not equal)
						<  : lt(less than)
						>  : gt(greater than)
						<= : le(less equal)
						>= : ge(greater equal)
					 -->
					<div class="col-sm-12 col-md-7">
						<div class="dataTables_paginate paging_simple_numbers" id="dataTable_paginate">
							<ul class="pagination">
								<li class="paginate_button page-item previous 
									<c:if test="${data.startPage lt 6}">disabled</c:if>" id="dataTable_previous">
									<a href="/lprod/list?currentPage=${data.startPage -1}&keyword=${param.keyword}&size=${param.size}" aria-controls="dataTable" data-dt-idx="0" 
										tabindex="0" class="page-link">Previous</a>
								</li>
								<c:forEach var="pNo" begin="${data.startPage}" end="${data.endPage}">
								<li class="paginate_button page-item 
									<c:if test='${param.currentPage==pNo}'>active</c:if>">
									<a href="/lprod/list?currentPage=${pNo}&keyword=${param.keyword}&size=${param.size}" aria-controls="dataTable" data-dt-idx="1" 
										tabindex="0" class="page-link">${pNo}</a>
								</li>
								</c:forEach>
								<li class="paginate_button page-item next 
									<c:if test="${data.endPage ge data.totalPages}">disabled</c:if>" id="dataTable_next">
									<a href="/lprod/list?currentPage=${data.endPage +1}&keyword=${param.keyword}&size=${param.size}" aria-controls="dataTable" data-dt-idx="7"
										tabindex="0" class="page-link">Next</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="row">
					<!-- 
						요청 URI : /prod/create
						방식 : GET	
					 -->
					<a href="/lprod/create" class="btn btn-primary btn-icon-split">
						<span class="icon text-white-50"><i class="fas fa-flag"></i></span>
						<span class="text">상품분류 추가</span>
					</a>
				</div>
			</div>
		</div>
	</div>
</div>
