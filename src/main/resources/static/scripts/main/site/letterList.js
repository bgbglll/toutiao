$(function(){
        //按钮单击时执行
        $("[id*='sns-action-del']").click(function(){
            var that = this;
            var id = $(that).attr("value");
            //alert("值为：" + id);
              //Ajax调用处理
            $.ajax({
               type: "POST",
               url: "/msg/deleteList",
               data: {conversationId:id},
               dataType:"json",
               async: false,
               ache:false,
               success: function(data){


                        $(that).parent().parent().parent().remove();
                        //$("#msg-item-4009123").append(data);
                  }
            });

         });


    });

    var msgListPages;
    //alert(id);
      $(function(){
      $.ajax({
            type: "POST",
            url: "/msg/list/totalPages",
            dataType:"json",
            async: false,
            cache:false,
            success: function(data){
            //alert(data);
            msgListPages = data;
            }
      });
      });
       $(function(){
                   var element = $('#letter-list-page');
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
                       totalPages: msgListPages,
                       pageUrl: function(type, page, current){
                              //alert(123);
                              return "/msg/list?curPage=" + page;
                        }
                   }
                   element.bootstrapPaginator(options);
        });