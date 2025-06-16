<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>科目管理</title>
</head>
<body>
<h2>得点管理システム</h2>
<!-- メニュー -->
<div>
	<ul>
		<li><a href="studentList.jsp">学生一覧</a></li>
		<li><a href="subjectList.jsp">科目管理</a></li>
		<li><a href="gradeList.jsp">成績一覧</a></li>
	</ul>
</div>
<!-- 科目管理画面 -->
<h3>科目管理</h3>
<a href="subjectRegister.jsp">新規登録</a>
<!-- 科目一覧テーブル -->
<table border="1">
	<thead>
		<tr>
			<th>科目コード</th>
			<th>科目名</th>
			<th colspan="2">操作</th>
		</tr>
	</thead>
	<tbody>
	<c:choose>
		<c:when test="${not empty subjectList}">
			<c:forEach var="subject" items="${subjectList}">
				<tr>
					<td>${subject.code}</td>
					<td>${subject.name}</td>
					<td><a href="subjectEdit.jsp?code=${subject.code}">編集</a></td>
					<td><a href="subjectDelete.jsp?code=${subject.code}">削除</a></td>
				</tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<tr>
				<td colspan="4">登録されている科目はありません。</td>
			</tr>
		</c:otherwise>
	</c:choose>
	</tbody>
</table>
<!-- ユーザー情報・ログアウト -->
<div style="text-align: right;">
   ${loginUser.name} さん <a href="logout.jsp">ログアウト</a>
</div>
</body>
</html>