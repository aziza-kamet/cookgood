<%-- 
    Document   : index
    Created on : 06.11.2016, 18:53:23
    Author     : Aziza
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${empty dbBean}">
    <jsp:useBean id="dbBean" scope="application" class="com.mycompany.cookgood.beans.DBConnection">            
    </jsp:useBean>
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CookGood</title>
        <%@ include file="templates/cssLinks.html" %>
        <%@ include file="templates/jsSrcs.html" %>
    </head>
    <body>
        <c:choose>
            <c:when test="${!empty user}">
                <jsp:include page="templates/auth_menu.jsp" />
            </c:when>
            <c:otherwise>
                <jsp:include page="templates/menu.jsp" />
            </c:otherwise>
        </c:choose>
        
        <c:choose>
            <c:when test="${!empty param.page}">                
                <jsp:include page="pages/${param.page}.jsp" />
            </c:when>
            <c:otherwise>
                <jsp:include page="pages/home.jsp" />
            </c:otherwise>
        </c:choose>
        
        <%@ include file="templates/footer.html" %>
    </body>
</html>
