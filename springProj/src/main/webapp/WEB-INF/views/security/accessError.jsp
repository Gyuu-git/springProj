<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="text-center">
	<div class="error mx-auto" data-text="${msg}">${msg}</div>
	<p class="lead text-gray-800 mb-5">Server Error</p>
	<p class="text-gray-500 mb-0">
		${SPRING_SECURITY_403_EXCEPTION.getMessage()}
	</p>
	<a href="/lprod/list">← 처음으로</a>
</div>