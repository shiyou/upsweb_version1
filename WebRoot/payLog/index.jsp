<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title></title>
		<!-- Bootstrap core CSS -->
		<link href="<%=basePath%>/common/bootstrap/css/bootstrap.min.css"
			rel="stylesheet">
		<!-- Bootstrap theme -->
		<link
			href="<%=basePath%>/common/bootstrap/css/bootstrap-theme.min.css"
			rel="stylesheet">
	</head>

	<body role="document">
		<!-- Fixed navbar -->
		<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">Bootstrap theme</a>
				</div>
				<div class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						<li class="active">
							<a href="payLog/listPayLog">支付记录查询</a>
						</li>
						<li>
							<a href="#about">About</a>
						</li>
						<li>
							<a href="#contact">Contact</a>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu" role="menu">
								<li>
									<a href="#">支付记录查询</a>
								</li>
								<li>
									<a href="#">Another action</a>
								</li>
								<li>
									<a href="#">Something else here</a>
								</li>
								<li class="divider"></li>
								<li class="dropdown-header">
									Nav header
								</li>
								<li>
									<a href="#">Separated link</a>
								</li>
								<li>
									<a href="#">One more separated link</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>


		<div class="container theme-showcase" role="main">
			<div class="page-header"></div>
			<div class="page-header">
				<h1>
					支付查询
				</h1>
			</div>
			<div class="row">
				<div>
					<form id="formId" action="payLog/listPayLog" method="post">
						<table>
							<tr>
								<th>
									按条件查询
								</th>
							</tr>
							<tr>
								<td>
									系统流水号
								</td>
								<td>
									<input type="input" id="payLogId" name="payLogId" value="" />
								</td>
								<td>
									支付流水号
								</td>
								<td>
									<input type="input" id="payId" name="payId" value="" />
								</td>
								<td>
									订单流水号
								</td>
								<td>
									<input type="input" id="orderId" name="orderId" value="" />
								</td>

							</tr>
							<tr>
								<td>
								</td>
							</tr>
							<tr>
								<td>
									支付时间
								</td>
								<td>
									<input type="input" id="payDate" name="payDate" value="" />
								</td>
								<td>
									支付账号
								</td>
								<td>
									<input type="input" id="payAcctNo" name="payAcctNo" value="" />
								</td>
								<td>
									<input type="button" id="selectId" value="查 询" />
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div class="col-md-6 ">
					<table class="table table-bordered table-hover table-condensed">
						<thead>
							<tr class="info">
								<th>
									系统流水号
								</th>
								<th>
									支付流水号
								</th>
								<th>
									订单流水号
								</th>
								<th>
									支付时间
								</th>
								<th>
									支付账号
								</th>
								<th>
									支付金额(RMB)
								</th>
								<th>
									商户订单号
								</th>
								<th>
									商户
								</th>
								<th>
									产品服务
								</th>
								<th>
									支付机构
								</th>
								<th>
									支付扣费状态
								</th>
								<th>
									结算时间
								</th>
								<th>
									对账状态
								</th>
							</tr>
						</thead>
						<tbody>
						<!-- 
							<div>
								<span><a href="javascript:void(0)" id="topPage">首页</a>
								</span>
								<c:if test="${page.pageNumber >1}">
									<span><a
										href="payLog/listPayLog?pageNumber=${page.pageNumber-1}">上一页</a>
									</span>
								</c:if>
								<c:if test="${page.pageNumber < totalCount/page.pageSize}">
									<span><a
										href="payLog/listPayLog?pageNumber=${page.pageNumber+1}">下一页</a>
									</span>
								</c:if>

								<!-- <span><a href="payLog/listPayLog?pageNumber=${lastPageNumber }">末页    </a></span> 
								<span><a href="javascript:void(0)" id="lastPage">末页 </a>
								</span>
								<span>总数统计:${totalCount}</span>
							</div> -->
							<c:forEach var="pager" items="${pagers}" varStatus="vars">
								<tr>
									<td>
										${pager.payLogId }
									</td>
									<td>
										${pager.payId }
									</td>
									<td>
										${pager.orderId }
									</td>
									<!-- <td>${pager.payDate }</td> -->
									<td>
										<fmt:formatDate value="${pager.payDate }" var="formattedDate"
											type="date" pattern="yyyyMMdd HH:mm:ss" />
										${formattedDate}
									</td>
									<td>
										${pager.payAcctNo }
									</td>
									<td>
										${pager.payCharge}
									</td>
									<td>
										${pager.spOrderNo }
									</td>
									<td>
										${pager.sp.spid }
									</td>
									<td>
										${pager.service.serviceId }
									</td>
									<td>
										${pager.payDepartment.departmentId }
									</td>
									<td>
										${pager.state }
									</td>
									<td>
										${pager.settleDate}
									</td>
									<td>
										${pager.payCheckState }
									</td>
								</tr>
							</c:forEach>
						</tbody>

					</table>
				</div>
			</div>

			<pg:pager url="payLog/listPayLog" items="${totalCount}" maxPageItems="${page.pageSize}" maxIndexPages="10" export="currentPageNumber=pageNumber">
				<pg:param name="pageSize"/>
				<pg:param name="payLogId" />
				<pg:param name="payId"/>
				<pg:param name="orderId"/>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" height="35" style="margin-top: 10px; border-top: #ddd dashed 1px;"">
					<tr>${pageSize}
						<td width="10%"></td>
						<td width="24%">
							<div align="left">
								<span class="STYLE22">
			                       	页次：${currentPageNumber }/<pg:last>${pageNumber }</pg:last> 总数:${totalCount }    
			                   </span>
							</div>
						</td>
						<td width="40%" align=right vAlign="center" noWrap>
							<pg:first>
								<a id="firstPage" href="${pageUrl }">首页</a>
							</pg:first>
							<pg:prev>
								<a href="${pageUrl}">前页</a>
							</pg:prev>
							<pg:pages>
							<c:choose>
								<c:when test="${currentPageNumber eq pageNumber}">
									<font color="red">${pageNumber }</font>
								</c:when>
								<c:otherwise>
									<a href="${pageUrl }">${pageNumber}</a>
								</c:otherwise>
							</c:choose>
							</pg:pages>
							<pg:next>
								<a href="${pageUrl}">下页</a>
							</pg:next>
							<pg:last>
								<a href="${pageUrl}">尾页</a>
							</pg:last>
							
						</td>
						<!-- 每页显示
						<td width="16%">
		                  <select name="pageSize" onchange="selectPagesize(this)" >
							<c:forEach begin="10" end="20" step="10" var="i">
								<option value="${i }" <c:if test="${i eq page.pageSize }">selected</c:if> >${i }</option>
							</c:forEach>
						  </select>
			            <td width="10%"></td>    -->     
					</tr>
				</table>
			</pg:pager>


		</div>
		<!-- /container -->


		<!-- Bootstrap core JavaScript
    ================================================== -->
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="<%=basePath%>/common/jquery/jquery-2.1.1.js">
</script>
		<script src="<%=basePath%>/common/bootstrap/js/bootstrap.min.js">
</script>
		<!-- JavaScript  defined ============================================ here -->
	<script type="text/javascript">
		$(function(){
	    		$("#selectId").click(function(){  
	    			$("#formId").submit();
	    		});
	    		 $("#topPage").click(function(){
	    		//	var payLogId =${payLog.payLogId }//$("#payLogId").val(); 
					window.self.location="payLog/listPayLog?pageNumber=1&payLogId="+payLogId;    			
	    		});
	    		
	    		$("#lastPage").click(function(){
	    		//	var payLogId = ${payLog.payLogId};
	    			window.location="payLog/listPayLog?pageNumber="+${lastPageNumber }+"&payLogId="+payLogId;
	    		});
	    	
	    });
		
			function selectPagesize(field) {
				window.location = document.getElementById("firstPage").href + "&pageSize=" + field.value;
			}
    </script>
	</body>
</html>
