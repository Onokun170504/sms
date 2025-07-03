<%-- 科目変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="scripts"></c:param>

  <c:param name="content">
    <section>
      <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>
<div class="my-2 text-end px-4">

				<a href="          ">ログアウト</a>

			</div>
      <form action="SubjectDeleteExecute.action" method="get">
     <!-- 科目コード -->
<div class="mx-auto py-2">
  <label for="subjectdelete">「Javaプログラミング基礎(F02)」を削除してもよろしいですか</label><br>
</div>

		<!-- 送信 -->
		<div class="mx-auto py-2">
		  <input class="btn btn-danger" type="submit" value="削除" />
		</div>
		</form>


      <a href="SubjectList.action">戻る</a>
    </section>
  </c:param>
</c:import>
