<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en" class>

<head>
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
    <title>SB Admin 2 - Dashboard</title>

    <!-- Custom fonts for this template-->
    <link href="/resources/sbadmin2/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="/resources/sbadmin2/css/sb-admin-2.min.css" rel="stylesheet">
</head>

<body id="page-top" class="sidebar-mini sidebar-closed sidebar-collapse">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Sidebar //////// aside 시작 ////////
        /WEB-INF/views/tiles/aside.jsp
        -->
		<tiles:insertAttribute name="aside" />
        <!-- End of Sidebar //////// aside 끝 //////// -->

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <!-- Topbar //////// header 시작 //////// 
                /WEB-INF/views/tiles/header.jsp를 include
                -->
                <tiles:insertAttribute name="header" />
                <!-- End of Topbar //////// header 끝 //////// -->

                <!-- Begin Page Content //////// body 시작 //////// -->
                <div class="container-fluid">
					<tiles:insertAttribute name="body" />
                </div>
                <!-- /.container-fluid //////// body  끝  //////// -->

            </div>
            <!-- End of Main Content -->

            <!-- Footer //////// footer 시작 ////////
            /WEB-INF/views/tiles/footer.jsp
            -->
            <tiles:insertAttribute name="footer" />
            <!-- End of Footer //////// footer 끝 //////// -->

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <!-- Logout Modal-->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">로그아웃 하시겠습니까?</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">세션을 종료하려면 아래의 로그아웃 버튼을 클릭해주세요.</div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">취소</button>
                    <form action="/logout" method="post">
                    	<button type="submit" class="btn btn-primary">로그아웃</button>
                    	<sec:csrfInput />
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap core JavaScript-->
    <script src="/resources/sbadmin2/vendor/jquery/jquery.min.js"></script>
    <script src="/resources/sbadmin2/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="/resources/sbadmin2/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="/resources/sbadmin2/js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="/resources/sbadmin2/vendor/chart.js/Chart.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="/resources/sbadmin2/js/demo/chart-area-demo.js"></script>
    <script src="/resources/sbadmin2/js/demo/chart-pie-demo.js"></script>

</body>
</html>