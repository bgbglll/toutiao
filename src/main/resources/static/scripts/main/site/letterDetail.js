$(function(){
        //按钮单击时执行
        $("[id*='del-link']").click(function(){
            var that = this;
            var id = $(that).attr("value");;
            alert("值为：" + id);
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