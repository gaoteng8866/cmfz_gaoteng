<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>主页</title>
    <%--引入bootStrap样式--%>
    <link rel="stylesheet" href="boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <script src="boot/js/jquery-3.4.1.min.js"></script>
    <script src="boot/js/bootstrap.min.js"></script>
    <%--引入jqgrid--%>
    <script src="statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="statics/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <%--引入ajax--%>
    <script src="statics/jqgrid/js/ajaxfileupload.js"></script>
    <%--导入HTML编辑器--%>
    <script charset="utf-8" src="kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="kindeditor/lang/zh-CN.js"></script>
    <%--导入echarts.min.js--%>
    <script src="echarts/echarts.min.js"></script>
</head>
<body>
    <!--导航条-->
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <a class="navbar-brand" href="#">虚拟空间管理系统</a>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎：<font color="blue">${loginAdmin.username}</font></a></li>
                <li><a href="${pageContext.request.contextPath}/admin/execte">退出登录 <span class="glyphicon glyphicon-share"></span></a></li>
            </ul>
        </div>
    </nav>
    <%--中间栅格系统--%>
    <div class="row">
        <%--左侧手风琴--%>
        <div class="col-sm-2">
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title text-center">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                <h4>轮播图管理</h4>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body text-center">
                            <a href="javascript:$('#content-layout').load('${pageContext.request.contextPath}/banner/banner-show.jsp');" class="btn btn-default">所有轮播图</a>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title text-center">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                <h4>专辑管理</h4>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body text-center">
                            <a href="javascript:$('#content-layout').load('${pageContext.request.contextPath}/album/album-show.jsp')" class="btn btn-default">所有专辑</a>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingThree">
                        <h4 class="panel-title text-center">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                <h4>文章管理</h4>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                        <div class="panel-body text-center">
                            <a href="javascript:$('#content-layout').load('${pageContext.request.contextPath}/article/article-show.jsp')" class="btn btn-default">所有专辑</a>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingFour">
                        <h4 class="panel-title text-center">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                                <h4>用户管理</h4>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
                        <div class="panel-body text-center">
                            <a href="javascript:$('#content-layout').load('${pageContext.request.contextPath}/user/user-show.jsp');" class="btn btn-default">所有用户</a>
                        </div>
                        <div class="panel-body text-center">
                            <a href="javascript:$('#content-layout').load('${pageContext.request.contextPath}/user/user-graph.jsp');" class="btn btn-default">用户折线图</a>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingFive">
                        <h4 class="panel-title text-center">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                                <h4>明星管理</h4>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFive">
                        <div class="panel-body text-center">
                            <a href="javascript:$('#content-layout').load('star/star-show.jsp')" class="btn btn-default">所有明星</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-sm-10" id="content-layout">
            <!--套入一个大的div，进行单击按钮隐藏-->
                <!--巨幕-->
                <div class="jumbotron" style="padding-left: 200px;">
                    <h4>欢迎来到虚拟空间管理系统！</h4>
                </div>
                <img src="1.jpg" class="img-thumbnail" style="width:100%;">
            </div>
        </div>
      <div class="panel-body text-center">
          虚拟空间后台管理系统2019.10.27
      </div>

</body>
</html>