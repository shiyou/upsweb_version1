<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<script type="text/javascript" src="../common/jquery/jquery-2.1.1.min.js"></script>
	</head>
	<body>
	<input id="user" type="button" value="****" />
	<h6><a href="addUser.jsp">添加用户</a></h6>  
		<table border="1">
			<tbody>
				<tr>
					<th>
						姓名
					</th>
					<th>
						年龄
					</th>
					<th>
						操作
					</th>
				</tr>
				<c:if test="${!empty userList }">
					<c:forEach items="${userList }" var="user">
						<tr>
							<td>
								${user.name}
							</td>
							<td>
								${user.age }
							</td>
							<td>
								<a href="/test_ssh/user/getUser?id=${user.id }">编辑</a>
								<a href="javascript:del('${user.id }')">删除</a>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</body>
	<script type="text/javascript">
	
	$(document).ready(function(){
		$("#user").click(function(){
			alert("hhhhhhhh");
		});
	});
	</script>
	<script type="text/javascript">
	 function del(id){
        $.get("/upsweb/user/delUser?id=" + id,function(data){
            if("success" == data.result){  
                alert("删除成功");  
                window.location.reload();  
            }else{  
                alert("删除失败");  
            }  
        });  
    }  
		
	</script>
		
</html>