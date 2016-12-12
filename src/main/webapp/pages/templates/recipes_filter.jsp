<%-- 
    Document   : recipes_filter
    Created on : 26.11.2016, 22:05:19
    Author     : Aziza
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="recipe-filter">
    <div class="list-group" role="tablist">
        <a href="#collapseOne" class="list-group-item list-group-item-action" data-toggle="collapse" aria-expanded="true" aria-controls="collapseOne">
            Order by
        </a>
        <div id="collapseOne" class="collapse in" role="tabpanel">
            <div class="list-group-item">
                <div class="form-check">
                    <label class="form-check-label">
                      <input type="radio" class="form-check-input order-by" name="order_by" id="optionsRadios1" value="r_date" <c:if test="${param.order != 'popular'}">checked</c:if>>
                      Relevance
                    </label>
                </div>
                <div class="form-check">
                <label class="form-check-label">
                      <input type="radio" class="form-check-input order-by" name="order_by" id="optionsRadios2" value="likes" <c:if test="${param.order == 'popular'}">checked</c:if>>
                      Popularity
                    </label>
                </div>
            </div>
        </div>
        <a href="#collapseTwo" class="list-group-item list-group-item-action" data-toggle="collapse" aria-expanded="true" aria-controls="collapseOne">
            Categories
        </a>
        <div id="collapseTwo" class="collapse in" role="tabpanel">
            <div class="list-group-item">
                <div class="form-check">
                    <label class="form-check-label">
                      <input type="radio" class="form-check-input category" name="category" id="optionsRadios1" value="" <c:if test="${empty param.category}">checked</c:if>>
                      All categories
                    </label>
                </div>
                <c:forEach items="${dbBean.getCatalog('categories')}" var="category">
                    <div class="form-check">
                        <label class="form-check-label">
                          <input type="radio" class="form-check-input category" name="category" id="optionsRadios1" value="${category}" <c:if test="${param.category == category}">checked</c:if>>
                          ${category}
                        </label>
                    </div>
                </c:forEach>
            </div>    
        </div>
        <a href="#collapseThree" class="list-group-item list-group-item-action" data-toggle="collapse" aria-expanded="true" aria-controls="collapseOne">
            Cuisine
        </a>
        <div id="collapseThree" class="collapse in" role="tabpanel">
            <div class="list-group-item">
                <div class="form-check">
                    <label class="form-check-label">
                        <input type="radio" class="form-check-input cuisine" name="cuisine" id="optionsRadios1" value="" <c:if test="${empty param.cuisine}">checked</c:if>>
                      All cuisine
                    </label>
                </div>
                <c:forEach items="${dbBean.getCatalog('cuisines')}" var="cuisine">
                    <div class="form-check">
                        <label class="form-check-label">
                          <input type="radio" class="form-check-input cuisine" name="cuisine" id="optionsRadios1" value="${cuisine}" <c:if test="${param.cuisine == cuisine }">checked</c:if>>
                          ${cuisine}
                        </label>
                    </div>
                </c:forEach>
            </div>    
        </div>
    </div>
</div>
