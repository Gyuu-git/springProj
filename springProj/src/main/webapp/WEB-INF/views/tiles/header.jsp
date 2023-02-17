<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

    <!-- Sidebar Toggle (Topbar) -->
    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
        <i class="fa fa-bars"></i>
    </button>

    <!-- Topbar Search -->
    <form
        class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
        <div class="input-group">
            <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..."
                aria-label="Search" aria-describedby="basic-addon2">
            <div class="input-group-append">
                <button class="btn btn-primary" type="button">
                    <i class="fas fa-search fa-sm"></i>
                </button>
            </div>
        </div>
    </form>

    <!-- Topbar Navbar -->
    <ul class="navbar-nav ml-auto">

        <!-- Nav Item - Search Dropdown (Visible Only XS) -->
        <li class="nav-item dropdown no-arrow d-sm-none">
            <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button"
                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-search fa-fw"></i>
            </a>
            <!-- Dropdown - Messages -->
            <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
                aria-labelledby="searchDropdown">
                <form class="form-inline mr-auto w-100 navbar-search">
                    <div class="input-group">
                        <input type="text" class="form-control bg-light border-0 small"
                            placeholder="Search for..." aria-label="Search"
                            aria-describedby="basic-addon2">
                        <div class="input-group-append">
                            <button class="btn btn-primary" type="button">
                                <i class="fas fa-search fa-sm"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </li>

        <!-- Nav Item - Alerts -->
        <li class="nav-item dropdown no-arrow mx-1">
            <a class="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button"
                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-bell fa-fw"></i>
                <!-- Counter - Alerts -->
                <span class="badge badge-danger badge-counter">3+</span>
            </a>
            <!-- Dropdown - Alerts -->
            <div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in"
                aria-labelledby="alertsDropdown">
                <h6 class="dropdown-header">
                    Alerts Center
                </h6>
                <a class="dropdown-item d-flex align-items-center" href="#">
                    <div class="mr-3">
                        <div class="icon-circle bg-primary">
                            <i class="fas fa-file-alt text-white"></i>
                        </div>
                    </div>
                    <div>
                        <div class="small text-gray-500">December 12, 2019</div>
                        <span class="font-weight-bold">A new monthly report is ready to download!</span>
                    </div>
                </a>
                <a class="dropdown-item d-flex align-items-center" href="#">
                    <div class="mr-3">
                        <div class="icon-circle bg-success">
                            <i class="fas fa-donate text-white"></i>
                        </div>
                    </div>
                    <div>
                        <div class="small text-gray-500">December 7, 2019</div>
                        $290.29 has been deposited into your account!
                    </div>
                </a>
                <a class="dropdown-item d-flex align-items-center" href="#">
                    <div class="mr-3">
                        <div class="icon-circle bg-warning">
                            <i class="fas fa-exclamation-triangle text-white"></i>
                        </div>
                    </div>
                    <div>
                        <div class="small text-gray-500">December 2, 2019</div>
                        Spending Alert: We've noticed unusually high spending for your account.
                    </div>
                </a>
                <a class="dropdown-item text-center small text-gray-500" href="#">Show All Alerts</a>
            </div>
        </li>

        <!-- Nav Item - Messages -->
        <li class="nav-item dropdown no-arrow mx-1">
            <a class="nav-link dropdown-toggle" href="#" id="messagesDropdown" role="button"
                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-envelope fa-fw"></i>
                <!-- Counter - Messages -->
                <span class="badge badge-danger badge-counter">7</span>
            </a>
            <!-- Dropdown - Messages -->
            <div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in"
                aria-labelledby="messagesDropdown">
                <h6 class="dropdown-header">
                    Message Center
                </h6>
                <a class="dropdown-item d-flex align-items-center" href="#">
                    <div class="dropdown-list-image mr-3">
                        <img class="rounded-circle" src="/resources/sbadmin2/img/undraw_profile_1.svg"
                            alt="...">
                        <div class="status-indicator bg-success"></div>
                    </div>
                    <div class="font-weight-bold">
                        <div class="text-truncate">Hi there! I am wondering if you can help me with a
                            problem I've been having.</div>
                        <div class="small text-gray-500">Emily Fowler · 58m</div>
                    </div>
                </a>
                <a class="dropdown-item d-flex align-items-center" href="#">
                    <div class="dropdown-list-image mr-3">
                        <img class="rounded-circle" src="/resources/sbadmin2/img/undraw_profile_2.svg"
                            alt="...">
                        <div class="status-indicator"></div>
                    </div>
                    <div>
                        <div class="text-truncate">I have the photos that you ordered last month, how
                            would you like them sent to you?</div>
                        <div class="small text-gray-500">Jae Chun · 1d</div>
                    </div>
                </a>
                <a class="dropdown-item d-flex align-items-center" href="#">
                    <div class="dropdown-list-image mr-3">
                        <img class="rounded-circle" src="/resources/sbadmin2/img/undraw_profile_3.svg"
                            alt="...">
                        <div class="status-indicator bg-warning"></div>
                    </div>
                    <div>
                        <div class="text-truncate">Last month's report looks great, I am very happy with
                            the progress so far, keep up the good work!</div>
                        <div class="small text-gray-500">Morgan Alvarez · 2d</div>
                    </div>
                </a>
                <a class="dropdown-item d-flex align-items-center" href="#">
                    <div class="dropdown-list-image mr-3">
                        <img class="rounded-circle" src="https://source.unsplash.com/Mv9hjnEUHR4/60x60"
                            alt="...">
                        <div class="status-indicator bg-success"></div>
                    </div>
                    <div>
                        <div class="text-truncate">Am I a good boy? The reason I ask is because someone
                            told me that people say this to all dogs, even if they aren't good...</div>
                        <div class="small text-gray-500">Chicken the Dog · 2w</div>
                    </div>
                </a>
                <a class="dropdown-item text-center small text-gray-500" href="#">Read More Messages</a>
            </div>
        </li>

        <div class="topbar-divider d-none d-sm-block"></div>
		
		<!-- 스프링 시큐리티 표현식
		인증 및 권한 정보에 따라 화면을 동적으로 구성할 수 있고, 로그인 한 사용자 정보를 보여줄 수도 있음
		 - hasRole("ROLE_MEMBER") : ROLE_MEMBER 권한이 있으면 true
		 - hasAnyRole("ROLE_MEMBER", "ROLE_ADMIN") : 여러 권한 중 하나라도 해당하는 권한이 있으면 true
		 - principal : 인증된 사용자의 사용자 정보
		 			(UserDetails 인터페이스를 구현한 클래스(customUser)의 객체를 의미)
		 - authentication : 인증된 사용자의 인증 정보
		 - permitAll : 모든 사용자에게 허용
		 - denyAll : 모든 사용자를 거부
		 - isAnonymous() : 로그인 하지 않은 경우 해당
		 - isAuthenticated() : 로그인 한 경우 true
		 - isFullyAuthenticated() : 
		 	Remember-me(로그인 저장)로 인증된 것이 아닌 일반적인 방법으로 인증된 사용자의 경우 true
		-->
		
		<!-- --------------------- 로그인 함 시작 --------------------- -->
		<sec:authorize access="isAuthenticated()">
		<!-- CustomUser.java에서 private MemVO memVO 멤버변수를 principal객체를 통해 사용 가능 -->
		<sec:authentication property="principal.memVO" var="memVO" />
	        <!-- Nav Item - User Information -->
	        <li class="nav-item dropdown no-arrow">
	            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
	                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                <span class="mr-2 d-none d-lg-inline text-gray-600 small">${memVO.userName}</span>
	                <img class="img-profile rounded-circle"
	                    src="/resources/images/miee.png">
	            </a>
	            <!-- Dropdown - User Information -->
	            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
	                aria-labelledby="userDropdown">
	                <a class="dropdown-item" href="#">
	                    <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
	                    Profile
	                </a>
	                <a class="dropdown-item" href="#">
	                    <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
	                    Settings
	                </a>
	                <a class="dropdown-item" href="#">
	                    <i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
	                    Activity Log
	                </a>
	                <div class="dropdown-divider"></div>
	                <a class="dropdown-item" href="/logout" data-toggle="modal" data-target="#logoutModal">
	                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
	                    Logout
	                </a> 
	            </div>
	        </li>
        </sec:authorize>
		<!-- --------------------- 로그인 함  끝  --------------------- -->
		<!-- --------------------- 로그인 안함 시작 --------------------- -->
		<sec:authorize access="isAnonymous()">
			<li class="nav-item dropdown no-arrow">
	            <a class="nav-link dropdown-toggle" href="/login" id="userDropdown" role="button"
	                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                <span class="mr-2 d-none d-lg-inline text-gray-600 small">로그인 해주세요</span>
	                <img class="img-profile rounded-circle"
	                    src="/resources/images/login.jpeg">
	            </a>
	        </li>
        </sec:authorize>
		<!-- --------------------- 로그인 안함  끝  --------------------- -->

    </ul>

</nav>