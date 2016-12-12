<%-- 
    Document   : recipes
    Created on : 26.11.2016, 20:53:13
    Author     : Aziza
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:forEach items="${dbBean.searchRecipes(param.key_word, param.order, param.category, param.cuisine)}" var="recipe">
<div class="row recipe">
    <div class="col-md-4 recipe-img">
        <a href="index.jsp?page=recipe&id=${recipe.getRecipeID()}"><img src="images/recipes/${recipe.getImage()}"></a>
    </div>
    <div class="col-md-8 recipe-info">
      <table class="recipe-info-top">
        <tr>
          <td class="info-left">
            <h4><a href="index.jsp?page=recipe&id=${recipe.getRecipeID()}" style="color: cornflowerblue;">${recipe.getTitle()}</a></h4>
            <p>${recipe.getDesc()} <a href="index.jsp?page=recipe&id=${recipe.getRecipeID()}">... read more</a></p>
          </td>
          <td class="info-right">
            <c:choose>
                <c:when test="${dbBean.isInFav(user.getId(), recipe.getRecipeID())}">
                    <a href="javascript:void(0)" data-toggle="tooltip" title="Remove from favourites"><span id="star_${recipe.getRecipeID()}" class="typcn typcn-star"></span></a>
                </c:when>
                <c:otherwise>
                    <a href="javascript:void(0)" data-toggle="tooltip" title="Add to favourites"><span id="star_${recipe.getRecipeID()}" class="typcn typcn-star-outline"></span></a>
                </c:otherwise>  
            </c:choose>    
              
          </td>
        </tr>
      </table>
      <div class="recipe-info-btm">
          <a href="javascript:void(0)">
              <c:choose>
                  <c:when test="${dbBean.doesUserLike(user.getId(), recipe.getRecipeID())}">
                      <span class="typcn typcn-heart" id="like_${recipe.getRecipeID()}" onclick="likePressed('like_${recipe.getRecipeID()}')"></span>
                  </c:when>
                  <c:otherwise>
                      <span class="typcn typcn-heart-outline" id="like_${recipe.getRecipeID()}" onclick="likePressed('like_${recipe.getRecipeID()}')"></span>
                  </c:otherwise>        
               
              </c:choose>
          </a>
          <span id="like_${recipe.getRecipeID()}_count">${recipe.getLikes()}</span>
          <span class="typcn typcn-user">${recipe.getName()} ${recipe.getSurname()}</span>
      </div>
    </div>
</div>
</c:forEach>