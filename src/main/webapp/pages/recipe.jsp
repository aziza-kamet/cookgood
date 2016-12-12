<%-- 
    Document   : recipe
    Created on : 28.11.2016, 1:27:03
    Author     : Aziza
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="recipe" value="${dbBean.getRecipe(param.id)}"></c:set>
<c:set var="recipe_user" value="${dbBean.getUserById(recipe.getUserID())}"></c:set>
<div class="container">
  <div class="row recipe-page">
    <div class="col-md-12">
      <table class="recipe-page-title">  
        <tr> 
          <td rowspan="2" class="recipe-page-img">
            <img src="images/recipes/${recipe.getImage()}">
          </td>
          <td class="recipe-page-details">            
            <h4>${recipe.getTitle()}</h4>
            <h5><a href="javascript:void(0)">
              <c:choose>
                  <c:when test="${dbBean.doesUserLike(user.getId(), param.id)}">
                      <span id="like_${param.id}" class="typcn typcn-heart" onclick="likePressed('like_${param.id}')"></span>
                  </c:when>
                  <c:otherwise>
                      <span id="like_${param.id}" class="typcn typcn-heart-outline" onclick="likePressed('like_${param.id}')"></span>
                  </c:otherwise>                     
              </c:choose>
            <c:choose>
                <c:when test="${dbBean.isInFav(user.getId(), param.id)}">
                    <a href="javascript:void(0)" data-toggle="tooltip" title="Remove from favourites"><span id="star_${param.id}" class="typcn typcn-star" style="float:right;"></span></a>
                </c:when>
                <c:otherwise>
                    <a href="javascript:void(0)" data-toggle="tooltip" title="Add to favourites"><span id="star_${param.id}" class="typcn typcn-star-outline" style="float:right;"></span></a>
                </c:otherwise>  
            </c:choose> 
              </a><span id="like_${param.id}_count">${recipe.getLikes()}</span></h5>
            <p>${recipe.getDesc()} </p>        
          </td>
        </tr>
        <tr>
          <td class="recipe-details-btm">            
            <span><img src="images/${recipe_user.getAvatar()}"></span>
            <span><h6>${recipe_user.getName()} ${recipe_user.getSurname()}</h6></span>            
          </td>
        </tr>
        
      </table>  
      <!--</div>  -->
      <div class="ingredients">
        <h4>Ingridients</h4>
        <table class="table">
        <tbody>   
            <c:forEach items="${recipe.getIngredients()}" var="ings"> 
                <tr>
                  <td>${ings.getIngredient()}</td>
                  <td>${ings.getAmount()} ${ings.getMeasure()}</td>
                </tr>
            </c:forEach>
        </tbody>
        </table>
      </div>
      
      <div class="steps">
        <h4>Step-by-step instructions</h4>
        <table class="table">
          <c:forEach items="${recipe.getSteps()}" var="step"> 
                <tr>
                    <td><img src="images/recipes/${step.getImage()}" style="width:200px"></td>
                    <td><p>${step.getDescription()}</p></td>
                </tr>
            </c:forEach>
        </table>
      </div>
      
      <div class="comments">
        <h4>Comments to recipe</h4>
          <div class="form-group">
            <textarea class="form-control" id="comment"  rows="3" placeholder="Add your comment here"></textarea>
          </div>
          <div class="form-group form-submit">
            <c:choose> 
                <c:when test="${not empty user.getId()}">
                    <input type="button" class="btn btn-default pull-xs-right" value="Submit" onclick="add_comment(${user.getId()}, ${param.id}, $('#comment').val())">
                </c:when>
                <c:otherwise>
                    <input type="button" class="btn btn-default pull-xs-right" value="Submit">                    
                </c:otherwise>    
            </c:choose> 
          </div>
        <div class="comments-content">          
            <jsp:include page="templates/comments_temp.jsp">
                <jsp:param name="rid" value="${param.id}"></jsp:param>
            </jsp:include>
        </div>
      </div>
  </div>
</div>
</div>
<script src="dist/js/functions.js"></script>
<script>    
  function add_comment(uid, rid, text){
      jQuery.ajax({
      url: "add_comment",
      data: "uid=" + uid + "&rid=" + rid +"&text=" + text,
      type: "GET",
      success: function(data){
        $("#comment").val("");
        $(".comments-content").html(data);
      },
      error: function(xh, text, error){
        alert("error" + error);
      }
    });
  }
  
<c:if test="${not empty user.getId()}">
  
  $(document).on('click', '.typcn-star-outline', function(){
    
    id = $(this).attr('id');
    fav("add", id.split("_")[1], id);
    
  });
  
  $(document).on('click', '.typcn-star', function(){
    
    id = $(this).attr('id');
    fav("remove", id.split("_")[1], id);
    
  });
  
</c:if> 
  
</script>