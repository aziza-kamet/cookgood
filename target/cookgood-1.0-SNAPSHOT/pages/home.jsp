<%-- 
    Document   : home
    Created on : 19.11.2016, 13:57:19
    Author     : Aziza
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
       

<div class="container-fluid">   
    <div class="row" id="search-bar">
      <div class="offset-sm-3 col-sm-6">
        <h3>Search recipes</h3>
        <div class="input-group">
          <input id="search-field" type="text" class="form-control" placeholder="Example: Chocolate cake">
          <div class="input-group-btn">
            <button class="btn btn-default" type="button" id="search-button"><span class="glyphicon glyphicon-search">Search</span></button>
          </div>
        </div>
      </div>
    </div>
    <div class="row categories">
        <c:forEach items="${dbBean.getCategories()}" var="category">
        <div class="col-xs-12 col-sm-12 col-xs-12 col-sm-12 col-md-3 text-xs-center">
          <a href="index.jsp?page=recipes&category=${category.getName()}" class="thumbnail">
            <img src="images/${category.getImage()}" alt="">
          </a>
          <p>${category.getName()}</p>
        </div>
      </c:forEach>
    </div>
    <div class="row cuisine">
      <h4>Сuisine</h4><br>
      <ul class="list-group list-group-horizontal">        
        <c:forEach items="${dbBean.getCatalog('cuisines')}" var="cuisine">
            <li class="list-group-item">
              <a href="?page=recipes&cuisine=${cuisine}" class="btn btn-outline-primary">${cuisine}</a>
            </li>
        </c:forEach>        
      </ul>
    </div>
    <div class="row new-recipes">
    <h4>New recipes</h4><br>
      <div class="card-deck-wrapper">
        <div class="card-deck">
            <c:forEach items="${dbBean.getNewRecipes()}" var="recipe">    
          <div class="card">
              <a href="index.jsp?page=recipe&id=${recipe.getRecipeID()}"><img class="card-img-top" src="images/recipes/${recipe.getImage()}" alt=""></a>
            <div class="card-block">
              <h4 class="card-title"><a class="card-link" href="?page=recipe&id=${recipe.getRecipeID()}">${recipe.getTitle()}</a><span style="float:right;">${recipe.getLikes()}</span></h4>
              <p class="card-text">${recipe.getDesc()}</p> 
            </div>            
          </div>  
        </c:forEach>
        </div>
      </div>
  </div>
  <div class="row best-authors">
    <h4>Top authors</h4><br>
    <c:forEach items="${dbBean.getTopAuthors()}" var="author">
    <div class="col-xs-6 col-md-3 text-xs-center">
      <a href="?page=user_profile&id=${author.getId()}">
        <img src="images/${author.getAvatar()}" alt="" class="img-circle" style="width:170px; height:170px;">
      </a>
      <p>${author.getName()} ${author.getSurname()}</p>
    </div>
    </c:forEach>
  </div>  
</div>
<script>
    $(document).on('click', '#search-button', function (){
        
        search_text = encodeURIComponent($('#search-field').val());
        window.location.replace(window.location + '?page=recipes#key_' + search_text);
        

    });
</script>