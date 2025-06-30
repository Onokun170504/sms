<%-- 科目変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="scripts"></c:param>

  <c:param name="content">
    <section>
      <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>

      <form action="SubjectUpdateExecute.action" method="get">
        <!-- 科目コード -->
        <div class="mx-auto py-2">
          <label for="subjectCode">科目コード</label><br>
          <input class="form-control" type="text" id="subjectCode" name="subjectCode" value="${subjectCode}" readonly />
        </div>

        <!-- 科目名 -->
        <div class="mx-auto py-2">
          <label for="subjectName">科目名</label><br>
          <input class="form-control" type="text" id="subjectName" name="subjectName" value="${subjectName}" required maxlength="30" />
        </div>

        <!-- 送信 -->
        <div class="mx-auto py-2">
          <input class="btn btn-primary" type="submit" value="変更" />
        </div>
      </form>

      <a href="SubjectList.action">戻る</a>
    </section>
  </c:param>
</c:import>
