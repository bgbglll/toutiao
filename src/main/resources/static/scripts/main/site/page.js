$(function(){
        //按钮单击时执行
        $("#testAjax").click(function(){

              //Ajax调用处理
            $.ajax({
               type: "POST",
               url: "test.php",
               data: "name=garfield&age=18",
               success: function(data){
                        $("#myDiv").html('<h2>'+data+'</h2>');
                  }
            });

         });
    });