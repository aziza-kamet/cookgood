<%-- 
    Document   : comments_temp
    Created on : 05.12.2016, 12:40:19
    Author     : Aziza
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:forEach items="${dbBean.getComments(param.rid)}" var="comment">
<c:set var="comment_user" value="${dbBean.getUserById(comment.getUserID())}"></c:set>
<div class="row">
    <div class="col-md-3">
      <img src="images/${comment_user.getAvatar()}">
      <div>
      <h6>${comment_user.getName()} ${comment_user.getSurname()}</h6>
      <small></small>
      </div>
    </div>
    <div class="col-md-9">
      <p>${comment.getText()}</p>
    </div>
</div>
</c:forEach>