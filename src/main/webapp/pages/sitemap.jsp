<%-- 
    Document   : sitemap
    Created on : 06.12.2016, 17:06:11
    Author     : Aziza
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container">
    <br><br><br>
    <div class="row">
        <div class="col-sm-12">
            <h3>Sitemap:</h3>
            <ul>
                <li><a href="index.jsp"><strong>Home</strong></a></li> 
                <li><strong>Categories</strong></li>
                    <ul>
                        <c:forEach items="${dbBean.getCatalog('categories')}" var="category">
                            <li><a href="?page=recipes&category=${category}">${category}</a></li>
                        </c:forEach>
                    </ul>
                <li><strong>Cuisine</strong></li>
                    <ul>
                        <c:forEach items="${dbBean.getCatalog('cuisines')}" var="cuisine">
                            <li><a href="?page=recipes&cuisine=${cuisine}">${cuisine}</a></li>
                        </c:forEach>
                    </ul>
                <li><a href="?page=recipes"><strong>All recipes</strong></a></li>
                <li><a href="?page=recipes&order=popular"><strong>Popular</strong></a></li>
            </ul> 
        </div>
    </div>
</div>