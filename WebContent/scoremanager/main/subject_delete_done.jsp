<%-- 学生変更完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="content">
		<div id="wrap_box">
			<div id="wrap_box">
				<p class="text-center" style="background-color:#8cc3a9">変更が完了しました</p>
				<br>
				<br>
				<br>
				<a href="StudentList.action">科目一覧</a>
			</div>
		</div>
	</c:param>
</c:import>