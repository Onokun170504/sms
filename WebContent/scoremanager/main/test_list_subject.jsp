<%-- 学生一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >
<c:param name="title">得点管理システム</c:param>

<c:param name="scripts"></c:param>

<c:param name="content">
<section class="me=4">

<%-----------------------------------------------------------------------------------------------%>

<%-- ①画面タイトル(h2) --%>
<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績一覧 (科目)</h2>

<div class="row border mx-3 mb-3 py-2 rounded">

<%-----------------------------------------------------------------------------------------------%>
	<%--(上部分)--%>
<%-----------------------------------------------------------------------------------------------%>

<%-- ⑮科目情報識別コード(input)--%>
<form action="TestListSubjectExecute.action" method="get">
<div class="row align-items-center  pb-2 mb-3 border-bottom " id="filter">
</div>


<%-- ②科目情報(label) --%>
<div class="col-2 text-center ">科目情報</div>
<div class="col-2">

<%-- ③入学年度(label) --%>
<label class="form-label" for="f1">入学年度</label>

<%-- ⑥入学年度selectボックス(f1) --%>
<select class="form-select" id="f1" name="f1">

<option value="0">--------</option>

<c:forEach var="year" items="${ent_year_set }">
<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
<option value="${year }" <c:if test="${year==f1 }">selected</c:if>>${year }</option>

</c:forEach>
</select>
</div>


<div class="col-2">


<%-- ④クラス(label) --%>
<label class="form-label" for="f2">クラス</label>

<%-- ⑦クラスselectボックス(f2) --%>
<select class="form-select" id="f2" name="f2">
<option value="0">--------</option>
<c:forEach var="num" items="${class_num_set }">
<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
<option value="${num }" <c:if test="${num==f2 }">selected</c:if>>${num }</option>
</c:forEach>
</select>
</div>


<div class="col-4">


<%-- ⑤科目(label) --%>
<label class="form-label " for="f3" >科目</label>

<%-- ⑧科目selectボックス(f3) --%>
<select class="form-select" id="f3" name="f3">
<option value="0">--------</option>
<c:forEach var="subject" items="${subjects}">
<%-- 現在のsubject.cdと選択されていたf3が一致していた場合selectedを追記 --%>
<option value="${subject.cd}" <c:if test="${subject.cd == f3 }">selected</c:if>>${subject.name}</option>
</c:forEach>
</select>
</div>


<%-- ⑨検索ボタン(button) --%>
<div class="col-2 mt-3 text-center">
<button class="btn btn-secondary" id="filter-button">検索</button>
</div>
</form>

<%-----------------------------------------------------------------------------------------------%>
	<%--(下部分)--%>
<%-----------------------------------------------------------------------------------------------%>

<%-- ⑯学生情報識別コード --%>
<form action="TestListStudentExecute.action" method="get">
<div class="row align-items-center" id="filter">

<%-- ⑩学生情報 --%>
<div class="col-2 text-center">学生情報</div>
<div class="col-4">

<%-- ⑪学生番号 --%>
<label class="form-label" for="f4">学生番号</label>

<%-- ⑫学生番号入力テキスト --%>
<input class="form-control" type="text" id="f4" name="f4"
 placeholder="学生番号を入力してください" required></div>

<%-- ⑬検索ボタン --%>
<div class="col-2 text-center">
<button class="btn btn-secondary" id="filter-button">検索</button>
</div>
</div>
</form>
</div>

<%-----------------------------------------------------------------------------------------------%>
<%-- 1番下の部分 --%>
<%-----------------------------------------------------------------------------------------------%>

<%-- ⑭利用方法案内メッセージ(label) --%>



			<c:choose>
				<c:when test="${students.size()>0 }">
					<div>検索結果：${students.size() }件</div>
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>1回</th>
							<th>2回</th>
						</tr>
						<c:forEach var="student" items="${students }">
							<tr>
								<td>${student.entYear }</td>
								<td>${student.classNum }</td>
								<td>${student.name }</td>
								<td>${student.getPoint(1) }</td>
								<td>
									<c:choose>
										<c:when test="${student.getPoint(2) != -1}">
											${student.getPoint(2)}
										</c:when>
										<c:otherwise>
											-
										</c:otherwise>
									</c:choose>
								</td>

							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<div>学生情報が存在しませんでした。</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>