function searchRecipe(key, order, category, cuisine){
  $(".recipes_temp").html("<center><div class='loader'></div></center>");
  jQuery.ajax({
    url: "search",
    data: "key_word=" + key + "&order=" + order + "&category=" + category + "&cuisine=" + cuisine,
    type: "GET",
    success: function(data){
      if(data != ""){
        $(".recipes_temp").html(data);
      }
    },
    error: function(xh, text, error){
//        alert("error" + error);
    }
  });
}

function starPressed(id){
  if($("#"+id).hasClass("typcn-star-outline")){
    $("#"+id).attr("class", "typcn typcn-star");
  } else {
    $("#"+id).attr("class", "typcn typcn-star-outline");
  }
}

function likePressed(id){
  if($("#"+id).hasClass("typcn-heart-outline")){      
      like("add", id.split("_")[1], id);
  } else {
      like("remove", id.split("_")[1], id);
  } 
}

function like(type, id, full_id){  
  jQuery.ajax({
    url: "like",
    data: "type=" + type + "&id=" + id,
    type: "GET",
    success: function(data){
      if(data != "" && data != "-1"){

          if(type == "add"){
              $("#"+full_id).attr("class", "typcn typcn-heart");
          } else {
              $("#"+full_id).attr("class", "typcn typcn-heart-outline");
          }
          $("#"+full_id+"_count").text(data);
      }
    },
    error: function(xh, text, error){
//        alert("error" + error);
    }
  });
}

function fav(type, rid, full_id){  
  jQuery.ajax({
    url: "favorite",
    data: "type=" + type + "&rid=" + rid,
    type: "GET",
    success: function(data){
      if(data != "" && data != "-1"){
          if(type == "add"){
              $("#"+full_id).attr("class", "typcn typcn-star");
          } else {
              $("#"+full_id).attr("class", "typcn typcn-star-outline");
          }
      }
    },
    error: function(xh, text, error){
      alert("error" + error);
    }
  });  
}