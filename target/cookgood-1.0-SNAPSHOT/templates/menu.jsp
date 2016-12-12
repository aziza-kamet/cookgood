<%-- 
    Document   : menu
    Created on : 14.11.2016, 1:36:28
    Author     : Aziza
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="pos-f-t">
  
  <div class="collapse" id="navbar-header">
    <div class="container bg-inverse p-a-1">
      
    </div>
  </div>
  
  <nav class="navbar navbar-light navbar-static-top bg-faded">
    <div class="container">
      <button class="navbar-toggler hidden-sm-up" type="button" data-toggle="collapse" data-target="#exCollapsingNavbar2">
        &#9776;
      </button>
      <div class="collapse navbar-toggleable-xs" id="exCollapsingNavbar2">
        <a class="navbar-brand" href="index.jsp">Cook good</a>
        <ul class="nav navbar-nav">
          <li class="nav-item <c:if test="${empty param.page || param.page == 'home'}">active</c:if>">
            <a class="nav-link" href="index.jsp">Home</a>
          </li>
          <li class="nav-item <c:if test="${param.page == 'recipes' && (empty param.order || empty param.category || empty param.cuisine)}">active</c:if>">
            <a class="nav-link" href="?page=recipes">All recipes</a>
          </li>
          <li class="nav-item <c:if test="${param.order == 'popular'}">active</c:if>">
            <a class="nav-link" href="?page=recipes&order=popular">Popular</a>              
          </li>
        </ul>
        <ul class="nav navbar-nav pull-sm-right pull-md-right">
          <li class="nav-item"><a class="nav-link" href="javascript:void(0)" data-toggle="modal" data-target="#regModal"><span class="typcn typcn-user"></span> Sign Up</a></li>
          <li class="nav-item"><a class="nav-link" href="javascript:void(0)" data-toggle="modal" data-target="#loginModal"><span class="typcn typcn-key"></span> Login</a></li>
          <li class="nav-item" id="add-recipe"><a class="nav-link" href="javascript:void(0)" data-toggle="modal" data-target="#loginModal"><span class="typcn typcn-document-add"></span> Add recipe</a></li>
        </ul>
      </div>
    </div>
  </nav>  
</div>
<%@ include file="menu_modals.html" %>