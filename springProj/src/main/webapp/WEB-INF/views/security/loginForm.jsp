<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="row justify-content-center">
	<div class="col-xl-10 col-lg-12 col-md-9">
		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">
				<!-- Nested Row within Card Body -->
				<div class="row">
					<div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
					<div class="col-lg-6">
						<div class="p-5">
							<div class="text-center">
								<h1 class="h4 text-gray-900 mb-4">Welcome Back!</h1>
								<h2>${error}</h2>
								<h2>${logout}</h2>
							</div>
							<form class="user" action="/login" method="post">
								<div class="form-group">
									<input type="text" class="form-control form-control-user" 
										id="username" name="username" aria-describedby="username" 
										placeholder="아이디를 입력해주세요" required />
								</div>
								<div class="form-group">
									<input type="password" class="form-control form-control-user" 
										id="password" name="password" placeholder="비밀번호를 입력해주세요" required/>
								</div>
								<div class="form-group">
									<div class="custom-control custom-checkbox small">
										<input type="checkbox" name="remember-me" class="custom-control-input" id="customCheck">
										<label class="custom-control-label" for="customCheck">Remember Me</label>
									</div>
								</div>
								<button type="submit" class="btn btn-primary btn-user btn-block">
									Login
								</button>
								<hr />
								<!-- 
								CSRF(Cross-site request forgery)
								크로스 사이트 요청 위조는 웹 사이트 취약점 공격의 하나로,
								사용자가 자신의 의지와는 무관하게 공격자가 의도한 행위
								(수정, 삭제, 등록)를 특정 웹사이트에 요청하게 하는 공격
								-->
								<sec:csrfInput/>
							</form>
							<div class="text-center">
								<a class="small" href="register.html">Create an Account!</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>