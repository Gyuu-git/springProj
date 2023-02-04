<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
	<li>
	    <!-- Sidebar - Brand -->
	    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.html">
	        <span class="sidebar-brand-icon rotate-n-15">
	            <i class="fas fa-laugh-wink"></i>
	        </span>
	        <span class="sidebar-brand-text mx-3">SB Admin <sup>2</sup></span>
	    </a>
    </li>
	
	<li>
	    <!-- Divider -->
	    <hr class="sidebar-divider my-0">
	</li>
    <!-- Nav Item - Dashboard -->
    <li class="nav-item active">
        <a class="nav-link" href="index.html">
            <i class="fas fa-fw fa-tachometer-alt"></i>
            <span>Dashboard</span></a>
    </li>

	<li>
	    <!-- Divider -->
	    <hr class="sidebar-divider" />
	</li>
	
	<li>
	    <!-- Heading -->
	    <div class="sidebar-heading">
	        Interface
	    </div>
	</li>
	
    <!-- Nav Item - Pages Collapse Menu -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo"
            aria-expanded="true" aria-controls="collapseTwo">
            <i class="fas fa-fw fa-cog"></i>
            <span>상품분류관리</span>
        </a>
        <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <h6 class="collapse-header">상품분류관리:</h6>
                <a class="collapse-item" href="/lprod/create">상품분류등록</a>
                <a class="collapse-item" href="/lprod/list">상품분류목록</a>
            </div>
        </div>
    </li>

    <!-- Nav Item - Utilities Collapse Menu -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities"
            aria-expanded="true" aria-controls="collapseUtilities">
            <i class="fas fa-fw fa-wrench"></i>
            <span>도서관리</span>
        </a>
        <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities"
            data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <h6 class="collapse-header">도서관리:</h6>
                <a class="collapse-item" href="/create">도서등록</a>
                <a class="collapse-item" href="/list">도서목록</a>
            </div>
        </div>
    </li>

    <!-- 고객관리 -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseCus"
            aria-expanded="true" aria-controls="collapseCus">
            <i class="fas fa-fw fa-spinner"></i>
            <span>고객관리</span>
        </a>
        <div id="collapseCus" class="collapse" aria-labelledby="headingUtilities"
            data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <h6 class="collapse-header">고객정보관리:</h6>
                <a class="collapse-item" href="/cus/create">고객등록</a>
                <a class="collapse-item" href="/cus/list">고객목록</a>
            </div>
        </div>
    </li>

	<li>
	    <!-- Divider -->
	    <hr class="sidebar-divider">
	</li>
	<li>
	    <!-- Heading -->
	    <div class="sidebar-heading">
	        Addons
	    </div>
	</li>
	
    <!-- Nav Item - Pages Collapse Menu -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePages"
            aria-expanded="true" aria-controls="collapsePages">
            <i class="fas fa-fw fa-folder"></i>
            <span>Pages</span>
        </a>
        <div id="collapsePages" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <h6 class="collapse-header">Login Screens:</h6>
                <a class="collapse-item" href="login.html">Login</a>
                <a class="collapse-item" href="register.html">Register</a>
                <a class="collapse-item" href="forgot-password.html">Forgot Password</a>
                <div class="collapse-divider"></div>
                <h6 class="collapse-header">Other Pages:</h6>
                <a class="collapse-item" href="404.html">404 Page</a>
                <a class="collapse-item" href="blank.html">Blank Page</a>
            </div>
        </div>
    </li>

    <!-- Nav Item - Charts -->
    <li class="nav-item">
        <a class="nav-link" href="charts.html">
            <i class="fas fa-fw fa-chart-area"></i>
            <span>Charts</span></a>
    </li>

    <!-- Nav Item - Tables -->
    <li class="nav-item">
        <a class="nav-link" href="tables.html">
            <i class="fas fa-fw fa-table"></i>
            <span>Tables</span></a>
    </li>

	<li>
	    <!-- Divider -->
	    <hr class="sidebar-divider d-none d-md-block">
	</li>
	<li>
	    <!-- Sidebar Toggler (Sidebar) -->
	    <div class="text-center d-none d-md-inline">
	        <button class="rounded-circle border-0" id="sidebarToggle"></button>
	    </div>
	</li>
	<li>
	    <!-- Sidebar Message -->
	    <div class="sidebar-card d-none d-lg-flex">
	        <img class="sidebar-card-illustration mb-2" src="/resources/sbadmin2/img/undraw_rocket.svg" alt="...">
	        <p class="text-center mb-2"><strong>SB Admin Pro</strong> is packed with premium features, components, and more!</p>
	        <a class="btn btn-success btn-sm" href="https://startbootstrap.com/theme/sb-admin-pro">Upgrade to Pro!</a>
	    </div>
	</li>
</ul>