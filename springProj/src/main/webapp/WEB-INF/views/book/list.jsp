<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<title>도서관리시스템</title>
</head>
<body>

<!-- 검색 시작 -->
<div>
	<!-- 
	action속성 및 그 값이 생략 시, 현재 URI(/list)를 재요청.
	method는 GET(form 태그의 기본 HTTP 메소드는 GET임)
	 -->
<!-- 	<form action="/list" method="get" > -->
	<form>
		<input type="text" name="keyword" value="${param.keyword}" 
			placeholder="검색어를 입력해주세요." />
		<!-- /list?keyword=ㄴㅇ -->
		<input type="submit" value="검색" />
	</form>
</div>

<div>
	<table border="1">
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>카테고리</th>
				<th>가격</th>
			</tr>
		</thead>
		<!-- 
		[rnum=1, bookId=37, title=제목30, category=카테고리30, price=10000, 
			insertDate=Wed Jan 25 11:45:07 KST 2023, content=내용30]
		
		forEach태그? 배열(String[][], int[][]), Collection(List, Set) 또는
			Map(HashTable, HashMap, SortedMap)에 저장되어 있는 값들을
			순차적으로 처리할 때 사용함. 자바의 for, do ~ while을 대신하여 사용
		
		data : mav.addObject("data", bookVOList)
		data : List<BookVO>
		var : 변수(variable)
		items : 아이템(배열, Collection, Map)
		varStatus : 루프 정보를 담은 객체
			- index : 루프(반복) 실행 시 현재 인덱스(0부터 시작)
			- count : 실행 회수(1부터 시작, 보통 행번호를 출력)
		-->
		<tbody>
			<c:forEach var="bookVO" items="${data.content}" varStatus="stat">
				<tr>
					<td>${bookVO.rnum}</td>
					<td><a href="/detail?bookId=${bookVO.bookId}">${bookVO.title}</a></td>
					<td>${bookVO.category}</td>
					<td><fmt:formatNumber type="number" maxFractionDigits="3" value="${bookVO.price}" /></td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="4">
					<div class="col-sm-12 col-md-7">
						<div class="dataTables_paginate paging_simple_numbers" id="dataTable_paginate">
							<ul class="pagination">
								<li class="paginate_button page-item previous 
									<c:if test="${data.startPage lt 6}">disabled</c:if>" id="dataTable_previous">
									<a href="/list?keyword=${param.keyword}&currentPage=${data.startPage -1}" aria-controls="dataTable" data-dt-idx="0" 
										tabindex="0" class="page-link">Previous</a>
								</li>
								<c:forEach var="bNo" begin="${data.startPage}" end="${data.endPage}">
								<li class="paginate_button page-item 
									<c:if test='${param.currentPage==bNo}'>active</c:if>">
									<a href="/list?keyword=${param.keyword}&currentPage=${bNo}" aria-controls="dataTable" data-dt-idx="1" 
										tabindex="0" class="page-link">${bNo}</a>
								</li>
								</c:forEach>
								<li class="paginate_button page-item next 
									<c:if test="${data.endPage ge data.totalPages}">disabled</c:if>" id="dataTable_next">
									<a href="/list?keyword=${param.keyword}&currentPage=${data.endPage +1}" aria-controls="dataTable" data-dt-idx="7"
										tabindex="0" class="page-link">Next</a>
								</li>
							</ul>
						</div>
					</div>
				</td>
			</tr>
		</tfoot>
	</table>
</div>
<p>
	<a href="/create">책 생성</a>
</p>
</body>
</html>