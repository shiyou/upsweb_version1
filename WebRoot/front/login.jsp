<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath %>" >
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Free HTML5 Bootstrap Admin Template</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The styles -->
    <link id="bs-css" href="<%=basePath %>common/charisma/css/bootstrap-cerulean.min.css" rel="stylesheet"><!-- 1 -->
    <link href="<%=basePath %>common/charisma/css/charisma-app.css" rel="stylesheet"> <!-- 2 -->
    <link href='<%=basePath %>common/charisma/bower_components/fullcalendar/dist/fullcalendar.css' rel='stylesheet'>
    <link href='<%=basePath %>common/charisma/bower_components/fullcalendar/dist/fullcalendar.print.css' rel='stylesheet' media='print'>
    <link href='<%=basePath %>common/charisma/bower_components/chosen/chosen.min.css' rel='stylesheet'>
    <link href='<%=basePath %>common/charisma/bower_components/colorbox/example3/colorbox.css' rel='stylesheet'>
    <link href='<%=basePath %>common/charisma/bower_components/responsive-tables/responsive-tables.css' rel='stylesheet'>
    <link href='<%=basePath %>common/charisma/bower_components/bootstrap-tour/build/css/bootstrap-tour.min.css' rel='stylesheet'>
    <link href='<%=basePath %>common/charisma/css/jquery.noty.css' rel='stylesheet'>
    <link href='<%=basePath %>common/charisma/css/noty_theme_default.css' rel='stylesheet'>
    <link href='<%=basePath %>common/charisma/css/elfinder.min.css' rel='stylesheet'>
    <link href='<%=basePath %>common/charisma/css/elfinder.theme.css' rel='stylesheet'>
    <link href='<%=basePath %>common/charisma/css/jquery.iphone.toggle.css' rel='stylesheet'>
    <link href='<%=basePath %>common/charisma/css/uploadify.css' rel='stylesheet'>
    <link href='<%=basePath %>common/charisma/css/animate.min.css' rel='stylesheet'>
    
    
    
    
    <script type="text/javascript" src="<%=basePath %>common/bootstrap/js/tooltip.js"></script>
	<script type="text/javascript" src="<%=basePath %>common/bootstrap/js/popover.js"></script>
	<script type="text/javascript" src="<%=basePath %>common/bootstrap/js/alert.js"></script></head>
	<script type="text/javascript" src="<%=basePath %>common/jquery/jquery-2.1.1.min.js"></script>
</head>

<body>
<div class="ch-container">
    <div class="row">
        
    <div class="row">
        <div class="col-md-12 center login-header">
            <h2>电信支付平台</h2>
        </div>
        <!--/span-->
    </div><!--/row-->

    <div class="row">
        <div class="well col-md-5 center login-box">
            <div class="alert alert-info">
                请输入用户名和密码
            </div>
            <form class="form-horizontal" action="" method="post" id="formId">
                <fieldset>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user red"></i></span>
                        <input type="text" id="userNo" name="userNo" class="form-control" placeholder="用户名" required="required">
                    </div>
                    <div class="clearfix"></div><br>

                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock red"></i></span>
                        <input type="password" id="password" name="password" class="form-control" placeholder="密码" required="required">
                    </div>
                    <div class="clearfix"></div><br/>
                    
  					 <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i><img src="<%=basePath %>index" id="verify" align="middle" title="看不清，换一张"  style="cursor:hand;"/></i></span>
                        <input type="text" id="verifyCode" name="verifyCode" class="form-control" placeholder="验证码" required="required">
                    </div>
                    <div class="clearfix"></div>
                <!--    
                    <div class="input-prepend">
                        <label class="remember" for="remember"><input type="checkbox" id="remember"> 记住我</label>
                    </div>
                    <div class="clearfix"></div>
 --> 
                    <p class="center col-md-5">
                        <button type="button" id="submitId"  class="btn btn-primary">登录</button>
                    </p>
                </fieldset>
            </form>
        </div>
        <!--/span-->
    </div><!--/row-->
</div><!--/fluid-row-->

</div><!--/.fluid-container-->

<script type="text/javascript">

	$(function(){
		$("#verify").click(function(){
			   $(this).attr("src","/upsweb/index?timestamp="+new Date().getTime());
		});	  
		
	});
	
	$(function(){
		$("#submitId").click(function(){ 
		 var userNo=$.trim($("#userNo").val());
		 var password=$.trim($("#password").val());
		 var verifyCode =$.trim($("#verifyCode").val());
		 var data={'userNo':userNo , 'password': password,'verifyCode':verifyCode};
		 var url="/upsweb/user/login"; 
		 $.ajax({
                url: url,
                type: "POST",
                cache: false,
                data: data,
               // dataType: "json",
                success:function (result) { 
			 		alert("登录成功"+result.toString());
                },
                error:function(){
                	alert("失败");
                }
            });
	 	});
 	});
	
</script>

</body>
</html>
