<%-- 
    Document   : my_recipes_temp
    Created on : 05.12.2016, 22:50:57
    Author     : Aziza
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:choose>
    <c:when test="${not empty param.id}">
        <c:set var="profile" value="${dbBean.getUserById(param.id)}"></c:set>
    </c:when>
    <c:otherwise>
        <c:set var="profile" value="${user}"></c:set>
    </c:otherwise>
</c:choose>
<c:forEach items="${dbBean.getMyRecipes(profile.getId())}" var="recipe">
<div class="row recipe">
    <div class="col-md-4 recipe-img">
      <img src="images/recipes/${recipe.getImage()}">
    </div>
    <div class="col-md-8 recipe-info">
      <h4>${recipe.getTitle()}</h4>
      <p>${recipe.getDesc()}</p>
      <div class="recipe-info-btm">
          <!--<a href="#"><span class="typcn typcn-heart"></span>123</a>-->
          <!--<a href="#"><span class="typcn typcn-message"></span>comments</a>-->
          <a href="#"><span class="typcn typcn-user"></span>${recipe.getName()} ${recipe.getSurname()}</a>
      </div>
    </div>
</div>
</c:forEach>