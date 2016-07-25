$(function(){
        //按钮单击时执行
        $("[id*='del-link']").click(function(){
            var that = this;
            var id = $(that).attr("value");
            //alert("值为：" + id);
              //Ajax调用处理
            $.ajax({
               type: "POST",
               url: "/msg/delete",
               data: {msgId:id},
               dataType:"json",
               async: false,
               ache:false,
               success: function(data){


                        $(that).parent().parent().parent().parent().remove();
                        //$("#msg-item-4009123").append(data);
                  }
            });

         });


    });

var msgDetailPages;
var id = $("#conversationId").attr("value");
//alert(id);
  $(function(){

  $.ajax({
        type: "POST",
        url: "/msg/detail/totalPages",
        dataType:"json",
        async: false,
        cache:false,
        data:{conversationId : id},
        success: function(data){
        //alert(data);
        msgDetailPages = data;
        }
  });
  });
   $(function(){
               var element = $('#letter-detail-page');
               var reg = new RegExp("(^|&)curPage=([^&]*)(&|$)", "i");
               var curPage=1;
               var r = window.location.search.substr(1).match(reg);
               //alert(r);
               if (r != null) {
                   curPage = unescape(r[2]);
               }
               var options = {
                   bootstrapMajorVersion:3,
                   currentPage: curPage,
                   numberOfPages: 5,
                   totalPages: msgDetailPages,
                   pageUrl: function(type, page, current){
                          return "/msg/detail?conversationId=" + id +"&curPage=" + page;
                    }
               }
               element.bootstrapPaginator(options);
    });