<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html lang="en"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
 <link href="./common/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="./common/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="./common/css/demo.css">
  <link rel="stylesheet" type="text/css" href="./common/css/style.css">
<link rel="stylesheet" type="text/css" href="./common/css/animate-custom.css">
<link rel="stylesheet" href="./common/bootstrap/css/bootstrap.css" type="text/css"></link>
<script type="text/javascript" src="./common/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./common/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="./common/bootstrap/js/tooltip.js"></script>
<script type="text/javascript" src="./common/bootstrap/js/popover.js"></script>


<script type="text/javascript" src="./common/bootstrap/js/alert.js"></script></head>
<body>
        <div class="container">
            <header> </header>
                <div id="container_demo">
                    <div id="wrapper">
                        <div id="login" class="animate form">
                            <form action="" onsubmit="check();"> 
                                <h1>中国电信融合支付平台</h1> 
                                <p> 
                                    <label for="username" class="uname" data-icon="u"> 用户名 </label>
                                    <input id="username" name="username" required="required" type="text" placeholder="myusername or mymail@mail.com">
                                </p>
                                <p> 
                                    <label for="password" class="youpasswd" data-icon="p"> 密码 </label>
                                    <input id="password" name="password" required="required" type="password" placeholder="eg. X8df!90EO"> 
                                </p>
								 <p> 
                                    <label for="verification" class="verification" data-icon="v"> 验证  </label>
                                     <img src="index" align="middle" title="看不清，请点我"  onclick="javascript:refresh(this);" onmouseover="mouseover(this)"/><br/>
                                    <input id="verification" name="verification" required="required" type="verification" placeholder="eg. 3">
                                </p>
                                <p class="keeplogin"> 
									<input type="checkbox" name="loginkeeping" id="loginkeeping" value="loginkeeping"> 
									<label for="loginkeeping">保持登录</label>
								</p>
                                <p class="login button"> 
                                    <input type="submit" value="登录"> 
                                    <input id="dd" type="text" class="" data-toggle="popover"  title="Popover title" data-content="It's very engaging. Right?">
								</p>
								<button type="button" class="btn btn-default" data-container="body" data-toggle="popover" data-placement="left" data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus.">
  Popover on left
</button>
                            </form>
                        </div>
                        <button id="jg" type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                       <div class="alert alert-block hide" id="warning-block">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <h4>Warning!</h4>Best check yo self, you're not...
</div>
</body>

	<script type="text/javascript">
		function check(){
				 $("#jg").alert();
		}
		
		$(function ()
			{ $("#dd").popover();
		});
	</script>
</html>