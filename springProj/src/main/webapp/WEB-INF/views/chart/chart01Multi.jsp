<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/resources/css/bootstrap.min.css" />
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js" ></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
// 구글 차트 라이브러리를 로딩(메모리에 올림)
google.load("visualization", "1", {"packages": ["corechart"]})

// 불러오는 작업이 완료되어 로딩이 되었다면 drawChart() 함수를 호출하는 콜백이 일어남
// 1) 첫번째 차트
google.setOnLoadCallback(drawChart);

// 2) 두번째 차트
google.setOnLoadCallback(drawChart2);

// 1) 콜백함수
function drawChart() {
	// 아작났어유..피씨다타써
	// dataType : 응답 데이터의 형식
	// contentType : 보내는 데이터의 형식
	// sync : 동기 / async : 비동기
	let jsonData = $.ajax({
		url: "/resources/json/simpleData.json",
		dataType: "json",
		async: false
	}).responseText;
	
	console.log("jsonData : " + jsonData);
	
	// 1) 구글 차트용 데이터 테이블 생성(파싱-구문분석/의미분석)
	let data = new google.visualization.DataTable(jsonData);
	
	// 2) 어떤 차트 모양으로 출력할지 정해주자
	// LineChart, ColumnChart, PieChart
	let chart = new google.visualization.LineChart(
		document.getElementById("chart-div")
	);
	
	// 3) data 데이터를 가지고 LineChart 모양으로 출력해보자
	chart.draw(data, {
		title : "차트 예제",
		width : 500,
		height: 400
	});
}

// 2) 콜백함수
function drawChart2() {
	// 아작났어유..피씨다타써
	let jsonData = $.ajax({
		url: "/resources/json/simpleData2.json",
		dataType: "json",
		async: false
	}).responseText;
	
	console.log("jsonData : " + jsonData);
	
	// 1) 구글 차트용 데이터 테이블 생성(파싱-구문분석/의미분석)
	let data = new google.visualization.DataTable(jsonData);
	
	// 2) 어떤 차트 모양으로 출력할지 정해주자
	// LineChart, ColumnChart, PieChart
	let chart = new google.visualization.ColumnChart(
		document.getElementById("chart-div2")
	);
	
	// 3) data 데이터를 가지고 LineChart 모양으로 출력해보자
	chart.draw(data, {
		title : "차트 예제",
		width : 500,
		height: 400
	});
}
</script>
<div class="row">
	<div class="col-xl-8 col-lg-7">
		<!-- 1) Area Chart -->
		<div class="card shadow mb-4">
			<div class="card-header py-3">
				<h6 class="m-0 font-weight-bold text-primary">상품가격</h6>
			</div>
			<!-- 구글차트가 보여질 영역 -->
			<div id="chart-div"></div>
		</div>
		<!-- 2) Area Chart -->
		<div class="card shadow mb-4">
			<div class="card-header py-3">
				<h6 class="m-0 font-weight-bold text-primary">채소 가격</h6>
			</div>
			<!-- 구글차트가 보여질 영역 -->
			<div id="chart-div2"></div>
		</div>
	</div>
</div>