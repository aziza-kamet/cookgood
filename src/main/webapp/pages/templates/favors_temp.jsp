<%-- 
    Document   : favors_temp
    Created on : 06.12.2016, 0:44:05
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
<c:forEach items="${dbBean.getFavor(profile.getId())}" var="recipe">
<div class="row recipe">
    <div class="col-md-4 recipe-img">
      <img src="images/recipes/${recipe.getImage()}">
    </div>
    <div class="col-md-8 recipe-info">
      <table class="recipe-info-top">
        <tr>
          <td class="info-left">
            <h4><a href="index.jsp?page=recipe&id=${recipe.getRecipeID()}" style="color: cornflowerblue;">${recipe.getTitle()}</a></h4>
            <p>${recipe.getDesc()} <a href="index.jsp?page=recipe&id=${recipe.getRecipeID()}">read more</a></p>
          </td>
          <td class="info-right">
            <c:choose>
                <c:when test="${dbBean.isInFav(user.getId(), recipe.getRecipeID())}">
                    <a href="javascript:void(0)" data-toggle="tooltip" title="Add to favourites"><span id="star_${recipe.getRecipeID()}" class="typcn typcn-star"></span></a>
                </c:when>
                <c:otherwise>
                    <a href="javascript:void(0)" data-toggle="tooltip" title="Remove from favourites"><span id="star_${recipe.getRecipeID()}" class="typcn typcn-star-outline"></span></a>
                </c:otherwise>  
            </c:choose>    
              
          </td>
        </tr>
      </table>
      <div class="recipe-info-btm">
          <!--<a href="#"><span class="typcn typcn-heart"></span>123</a>-->
          <!--<a href="#"><span class="typcn typcn-message"></span>comments</a>-->
          <a href="#"><span class="typcn typcn-user"></span>${recipe.getName()} ${recipe.getSurname()}</a>
      </div>
    </div>
</div>
</c:forEach>