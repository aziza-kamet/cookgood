<%-- 
    Document   : recipes
    Created on : 20.11.2016, 13:02:16
    Author     : Aziza
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
        
<!-- Begin page content -->
<div class="container-fluid" >
  <div class="row" id="recipes-search-bar" style="background-color: darkseagreen;">
    <div class="offset-sm-3 col-sm-6">
      <div class="input-group">
        <input id="search-field" type="text" class="form-control" placeholder="Example: Chocolate cake">
        <div class="input-group-btn">
          <button id="search-button" class="btn btn-default" type="button"><span class="glyphicon glyphicon-search">Search</span></button>
        </div>
      </div>
    </div>
  </div>
  <div class="recipes">
     
    <div class="col-sm-3">
        <jsp:include page="templates/recipes_filter.jsp"/>
    </div>  
    <div class="col-sm-6">
        <div class="recipes_temp">
        </div>    
    </div>  
          
  </div>
</div>
        
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js" integrity="sha384-THPy051/pYDQGanwU6poAc/hOdQxjnOEXzbT+OuUAFqNqFjL+4IGLBgCJC3ZOShY" crossorigin="anonymous"></script> 
<script src="dist/js/functions.js"></script>
<script>
  
  var key_word = "";
  var order = $('input[name=order_by]:checked').val();
  var category = $('input[name=category]:checked').val();
  var cuisine = $('input[name=cuisine]:checked').val();
    
  $(document).ready(function(){
//    $('[data-toggle="tooltip"]').tooltip();
    if(location.hash){
      var url = window.location.hash.substr(1).split("&");  
      
      for(var i = 0; i < url.length; i++){
          
          var key = url[i].substr(0,url[i].indexOf('_'));
          var value = url[i].substr(url[i].indexOf('_')+1);
          if(key === "key"){
              key_word = value;
            searchRecipe(value, order, category, cuisine);
          }
          
      }
      
    } else {
        searchRecipe("", order, category, cuisine);
    }
  });

  $(document).on('click', '#search-button', function(){
    
    key_word = encodeURIComponent($('#search-field').val());
    searchRecipe(key_word, order, category, cuisine);
    
  });
  
  $(document).on('click', '.order-by', function(){
    
    order = $(this).val();
    searchRecipe(key_word, order, category, cuisine);
    
  });
  
  $(document).on('click', '.category', function(){
    
    category = $(this).val();
    searchRecipe(key_word, order, category, cuisine);
    
  });
  
  $(document).on('click', '.cuisine', function(){
    
    cuisine = $(this).val();
    searchRecipe(key_word, order, category, cuisine);
        
  });
  
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
