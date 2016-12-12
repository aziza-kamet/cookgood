<%-- 
    Document   : auth_menu
    Created on : 19.11.2016, 19:16:30
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
          <li class="nav-item <c:if test="${param.page == 'recipes' && empty param.order}">active</c:if>">
            <a class="nav-link" href="?page=recipes">All recipes</a>
          </li>
          <li class="nav-item <c:if test="${param.order == 'popular'}">active</c:if>">
            <a class="nav-link" href="?page=recipes&order=popular">Popular</a>              
          </li> 
        </ul>
        <ul class="nav navbar-nav pull-sm-right pull-md-right">
          <li class="nav-item"><a class="nav-link" href="?page=profile"><span class="typcn typcn-user-outline"></span> Profile</a></li>
          <li class="nav-item"><a class="nav-link" href="logout"><span class="typcn typcn-key-outline"></span> Logout</a></li>
          <li class="nav-item" id="add-recipe"><a class="nav-link" href="javascript:void(0)" data-toggle="modal" data-target="#addRecModal"><span class="typcn typcn-document-add"></span> Add recipe</a></li>
        </ul>
      </div>
    </div>
  </nav>  
</div>
<div class="modal fade" id="addRecModal" role="dialog">
  <div class="modal-dialog">
      <!-- Modal content-->
      <form action="add_recipe" method="POST" enctype="multipart/form-data">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">&times;</button>
            <h4 class="modal-title">Add your recipe</h4>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <label for="usr">Title:</label>
              <input type="text" class="form-control" name="title" id="name" placeholder="write your title"><p id="placeErrorTitle" style="color:red"></p>
            </div>
            <div class="form-group">
              <label for="usr">Description:</label>
              <input type="text" class="form-control" name="desc" id="desc" placeholder="write description"><p id="placeErrorDescr" style="color:red"></p>
            </div>
            <div class="form-group">
              <label for="usr">Photo:</label>
              <input type="file" class="form-control" name="image" id="image">
            </div>
            <div class="form-group">
              <label for="usr">Category:</label>
              <select name="categories" class="form-control">
                    <c:forEach items="${dbBean.getCatalog('categories')}" var="category">
                        <option>${category}</option>
                    </c:forEach>
              </select> 
            </div>
            <div class="form-group">
              <label for="usr">Cuisine:</label>
              <select name="cuisines" class="form-control">
                    <c:forEach items="${dbBean.getCatalog('cuisines')}" var="cuisine">
                        <option>${cuisine}</option>
                    </c:forEach>
              </select>
            </div>
            <div class="row">
              <div class="input_fields_wrap">
              <table class="table borderless" id="input_fields_wrap">
                <tr>
                  <td>Ingredients:</td>
                  <td>Amounts:</td>
                  <td>Measure:</td>
                </tr>
                <tr>
                  <td><input type="text" name="ingredients" class="form-control" id="userInput" placeholder="for example apple"/></td>
                  <td><input type="text" name="amount" class="form-control" id="userInput" placeholder="for example 3"/></td>
                  <td>
                    <select name="measures" class="form-control" id="sel1">
                        <c:forEach items="${dbBean.getCatalog('measures')}" var="measure">
                            <option>${measure}</option>
                        </c:forEach>
                    </select>
                  </td>
                </tr>
              </table>
              </div>
            </div>
           <button type="button" class="btn btn-primary" id="add_field_button">+ Add ingredient</button><br>
            <div class="row">
              <div class="step_fields_wrap">
              <table class="table borderless" id="step_fields_wrap">
                <tr>
                  <td  style="width: 40%;">Step photo:</td>
                  <td>Description:</td>
                </tr>
                <tr>
                  <td><input type="file" name="step_images"/></td>
                  <td><textarea name="step_desc" class="form-control" rows="3"></textarea></td>                  
                </tr>
              </table>
              </div>
            </div>  
            <button type="button" class="btn btn-primary" id="add_step_button">+ Add step</button><br>
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-success" style="float:right">Add recipe</button>
          </div>
        </div>
    </form>    
  </div>
</div>
<script>
  $(document).ready(function() {
    var max_fields = 10; //maximum input boxes allowed
    var wrapper = $("#input_fields_wrap"); //Fields wrapper
    var add_button = $("#add_field_button"); //Add button ID
    var step_wrapper = $("#step_fields_wrap");
    var step_add_button = $("#add_step_button");
    
    var x = 1; 
    $(add_button).click(function(e){ 
        e.preventDefault();
        if(x < max_fields){ 
            x++;
            text = '<tr><td><input type="text" name="ingredients" class="form-control" /></td><td><input type="text" name="amount" class="form-control" id="userInput"/></td><td><select name="measures" class="form-control" id="sel1">';
            <c:forEach items="${dbBean.getCatalog('measures')}" var="measure">
                text += '<option>${measure}</option>'
            </c:forEach>;
            text += '</select></td><td><a href="#" class="remove_field">Remove</a></td></tr>';
            $(wrapper).append(text);
        }
    });
    //udalenie
    $(wrapper).on("click",".remove_field", function(e){ 
        e.preventDefault(); $(this).parent('td').parent('tr').remove(); x--;
    });
    
    var y = 1; 
    $(step_add_button).click(function(e){ 
        e.preventDefault();
        if(y < max_fields){ 
            y++;
            text = '<tr><td><input type="file" name="step_images"/></td>';
            text += '<td><textarea name="step_desc" class="form-control" rows="3"></textarea></td>';
            text += '<td><a href="#" class="remove_field">Remove</a></td></tr>';
            $(step_wrapper).append(text);
        }
    });
    //udalenie
    $(step_wrapper).on("click",".remove_field", function(e){ 
        e.preventDefault(); $(this).parent('td').parent('tr').remove(); y--;
    });
    
  });
  
  function checkField(){
    var x = document.getElementById("name").value;
    var y = document.getElementById("desc").value;
    if(x==""){
      document.getElementById("placeErrorTitle").innerHTML = "You should write your title";
    }
    if(y==""){
      document.getElementById("placeErrorDescr").innerHTML = "You should write your description";
    }
  }
</script>