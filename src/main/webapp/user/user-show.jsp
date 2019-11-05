<%@page pageEncoding="UTF-8" %>
<script>
    $(function () {
        $("#user-show-table").jqGrid({
            url : "${pageContext.request.contextPath}/user/selectAll",
            datatype : "json",
            colNames : [  '编号', '用户名', '昵称', '头像','电话', '性别','省份','城市','签名','创建时间' ],
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
                {name : "sign"},
                {name : "createDate"}
            ],
            styleUI:"Bootstrap",
            autowidth:true,
            rowNum : 3,
            rowList : [ 3, 5, 10 ],
            pager : '#user-page',
            viewrecords : true,
            caption : "用户数据展示",
            editurl : "someurl.php"
        }).navGrid("#user-page", {edit : false,add : false,del : false,search:false});
    })
</script>



<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#">所有用户</a></li>
    <li role="presentation"><a href="${pageContext.request.contextPath}/user/export" >导出用户数据</a></li>
</ul>
<table id="user-show-table"></table>
<div id="user-page" style="height: 40px"></div>
