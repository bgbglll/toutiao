
$(function(){
        //按钮单击时执行
        $("#testAjax").click(function(){

              //Ajax调用处理
            $.ajax({
               type: "GET",
               url: "/page",
               data:"curPage=0",
               dataType:"html",
               success: function(data){
               alert("123");
                        $("#myDiv").append(data);
                  }
            });

         });

    });
var newsPages;
$(function(){

$.ajax({
      type: "POST",
      url: "/news/totalPages",
      dataType:"json",
      async: false,
      cache:false,
      success: function(data){
      //alert(data);
      newsPages = data;
      }
});
});
 $(function(){
             var element = $('#news-page');
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
                 totalPages: newsPages,
                 pageUrl: function(type, page, current){
                        return "/?curPage="+page;

                  }
             }
             element.bootstrapPaginator(options);
  });
