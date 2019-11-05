<%@page pageEncoding="UTF-8" %>
<script>
    $(function () {
          $("#star-show-table").jqGrid({
              url : '${pageContext.request.contextPath}/star/selectAll',
              datatype : "json",
              height : 300,
              colNames : [ '编号', '艺名', '真名','照片', '性别', '生日' ],
              colModel : [
                  {name : 'id',hidden:true,editable:false},
                  {name : 'nickname',editable:true},
                  {name : 'realname',editable:true},
                  {name : 'photo',editable:true,edittype:"file",formatter:function (value,option,rows){
                          return"<img style='width: 100px;height: 70px' src='${pageContext.request.contextPath}/star/img/"+rows.photo+"'>";
                      }},
                  {name : 'sex',editable:true},
                  {name : 'bir',editable:true ,edittype: "date"}
              ],
              //设置表格样式
              styleUI:"Bootstrap",
              //根据相应的宽度自动调整
              autowidth:true,
              rowNum : 3,
              rowList : [ 3, 5, 10,],
              pager : '#star-page',
              viewrecords : true,
              subGrid :true,
              //标题
              caption : "所有明星列表",
              editurl : "${pageContext.request.contextPath}/star/edit",
              subGridRowExpanded : function(subgrid_id, id) {
                  // we pass two parameters
                  // 我们传递两个参数
                  // subgrid_id is a id of the div tag created whitin a table data
                  //subgrid_id是在表数据中创建的div标记的id
                  // the id of this elemenet is a combination of the "sg_" + id of the row
                  //这个元素集的id是行的“sg_”+ id的组合
                  // the row_id is the id of the row
                  //行id是行的id
                  // If we wan to pass additinal parameters to the url we can use
                  //如果我们想要传递额外的参数到我们可以使用的url
                  // a method getRowData(row_id) - which returns associative array in type name-value
                  //方法getRowData(row_id)——它返回类型为name-value的关联数组
                  // here we can easy construct the flowing
                  //在这里，我们可以很容易地构造流动
                  var subgrid_table_id, pager_id;
                  //初始化参数
                  subgrid_table_id = subgrid_id + "_t";
                  pager_id = "p_" + subgrid_table_id;
                  //拿第一个subgrid_id创造第一个html标签
                  $("#" + subgrid_id).html(
                      "<table id='" + subgrid_table_id  +"' class='scroll'></table>" +
                          //分页的div
                      "<div id='" + pager_id + "' class='scroll'></div>");
                  //初始化上面成为一个jqGrid的
                  $("#" + subgrid_table_id).jqGrid(
                      {
                          //下方是子表格
                          url : "${pageContext.request.contextPath}/user/selectUsersByStarId?starId=" + id,
                          datatype : "json",
                          colNames : [ '编号', '用户名', '昵称', '头像','电话', '性别','省份','城市','签名' ],
                          colModel : [
                              {name : "id"},
                              {name : "username"},
                              {name : "nickname"},
                              {name : 'photo',formatter:function (value,option,rows){
                                      return"<img style='width: 100px;height: 70px' src='${pageContext.request.contextPath}/star/img/"+rows.photo+"'>";
                                  }},
                              {name : "phone"},
                              {name : "sex"},
                              {name : "province"},
                              {name : "city"},
                              {name : "sign"}
                          ],
                          styleUI:"Bootstrap",
                          rowNum : 3,
                          pager : pager_id,
                          autowidth:true,
                          height : '100%'
                      });
                  jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                      "#" + pager_id, {
                          edit : false,
                          add : false,
                          del : false,
                          search:false
                      });
              },
        }).jqGrid('navGrid', '#star-page', { add : true,edit : false,del : false,search:false},{},
              {
                  //控制添加
                  closeAfterAdd:true,
                  afterSubmit:function (data) {
                      console.log(data);
                      var status = data.responseJSON.status;
                      var id = data.responseJSON.message;
                      if(status){
                          $.ajaxFileUpload({
                              url:"${pageContext.request.contextPath}/star/upload",
                              type:"post",
                              fileElementId:"photo",
                              data:{id:id},
                              success:function (response) {
                                  //自动刷新jqgrid表格
                                  $("#banner-show-table").trigger("reloadGrid");
                              }
                          });
                      }
                      return "123";
                  }
              });
    })
</script>
<div class="panel page-header">
    <h3>展示所有的明星</h3>
</div>
<%--下面是表格的id   star-show-table--%>
<table id="star-show-table"></table>
<%-- 下面是div的id   star-page--%>
<div id="star-page" style="height: 40px;"></div>