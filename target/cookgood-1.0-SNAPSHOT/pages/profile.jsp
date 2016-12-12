<%-- 
    Document   : profile
    Created on : 19.11.2016, 19:13:02
    Author     : Aziza
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container-fluid">
  <div class="row profile-block">
    <div class="col-md-4">
      <div class="card">
        <img class="card-img-top text-xs-xenter" src="images/${user.getAvatar()}" alt="${user.getName()} ${user.getSurname()} avatar">
        <div class="card-block">
          <h4 class="card-title">${user.getName()} ${user.getSurname()}</h4>
        </div>
        <ul class="list-group list-group-flush">
            <li class="list-group-item list-group-item-action <c:if test="${empty param.tab || (not empty param.tab && param.tab == 'my_recipes')}">active</c:if>"><a href="index.jsp?page=profile&tab=my_recipes" class="card-link">My recipes</a></li>
          <li class="list-group-item list-group-item-action <c:if test="${not empty param.tab && param.tab == 'favors'}">active</c:if>"><a href="index.jsp?page=profile&tab=favors" class="card-link">Favourites</a></li>
        </ul>
      </div>              
    </div>
    <div class="col-md-8 profile-content">
        <c:choose>
            <c:when test="${not empty param.tab && param.tab == 'favors'}">
                <jsp:include page="templates/favors_temp.jsp"></jsp:include>
            </c:when>
            <c:otherwise>
                <jsp:include page="templates/my_recipes_temp.jsp"></jsp:include>                
            </c:otherwise>
        </c:choose>
    </div>
  </div>
</div>