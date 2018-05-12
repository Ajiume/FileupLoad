<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ include file="/resource/jsp/common/common_header.jsp"%>
<body class="list">
	<div class="bar">
		FTP配置信息列表&nbsp;总记录数: ${pager.rowCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="<%=request.getContextPath()%>/ftp/conf/dataList" method="post">
		<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />&nbsp;
		<input type="button" class="formButton" onclick="location.href='<%=request.getContextPath()%>/ftp/conf/add'" value="新增" hidefocus />&nbsp;
		<input type="button" id="deleteButton" class="formButton" url="<%=request.getContextPath()%>/ftp/conf/delete" value="删 除" hidefocus />
			<div class="listBar">
				<label>ftp地址: </label>
				<input type="text" name="ftpAddress" value="${param.ftpAddress}" />&nbsp;
				<label>ftp端口: </label>
				<input type="text" name="ftpPort" value="${param.ftpPort}" />&nbsp;
				<label>用户名: </label>
				<input type="text" name="ftpUsername" value="${param.ftpUsername}" />&nbsp;
				<label>每页显示: </label>
				<select name="pageSize" id="pageSize">
				    <option value="1"<c:if test="${pager.pageSize == 1}"> selected</c:if>>
						1
					</option>
					<option value="5"<c:if test="${pager.pageSize == 5}"> selected</c:if>>
						5
					</option>
					<option value="10"<c:if test="${pager.pageSize == 10}"> selected</c:if>>
						10
					</option>
					<option value="20"<c:if test="${pager.pageSize == 20}"> selected</c:if>>
						20
					</option>
					<option value="50"<c:if test="${pager.pageSize == 50}"> selected</c:if>>
						50
					</option>
				</select><br/>
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<a href="#" class="sort" name="conf_id" hidefocus>ID</a>
					</th>
					<th>
						<a href="#" class="sort" name="ftp_address" hidefocus>ftp地址</a>
					</th>
					<th>
						<a href="#" class="sort" name="ftp_port" hidefocus>ftp端口号</a>
					</th>
					<th>
						<a href="#" class="sort" name="ftp_username" hidefocus>用户名</a>
					</th>
					<th>
						<a href="#" class="sort" name="ftp_base_path" hidefocus>基准路径</a>
					</th>
					<th>
						<a href="#" class="sort" name="ftp_url" hidefocus>url</a>
					</th>
					<th>
						<a href="#" class="sort" name="remark" hidefocus>备注</a>
					</th>
					<th>
						<a>操作</a>
					</th>
				</tr>
				<c:forEach items="${pager.dataList}" var="ftpConf">
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${ftpConf.confId}" />
						</td>
						<td>
							${ftpConf.confId}
						</td>
						<td>
							${ftpConf.ftpAddress}
						</td>
						<td>
							${ftpConf.ftpPort}
						</td>
						<td>
							${ftpConf.ftpUsername}
						</td>
						<td>
							${ftpConf.ftpBasePath}
						</td>
						<td>
							${ftpConf.ftpUrl}
						</td>
						<td>
							${ftpConf.remark}
						</td>
						<td>
							<a href="<%=request.getContextPath()%>/ftp/conf/edit?confId=${ftpConf.confId}" title="修改">[修改]</a>&nbsp;
							<a href="<%=request.getContextPath()%>/ftp/conf/detail?confId=${ftpConf.confId}" title="详情">[详情]</a>&nbsp;
						</td>
					</tr>
				</c:forEach>
			</table>
			<c:choose>
				<c:when test="${pager.dataList != null and fn:length(pager.dataList) > 0}">
					<div class="pagerBar">
<!-- 				  		<div class="delete"> -->
<!--  							<input type="button" id="deleteButton" class="formButton" url="<%=request.getContextPath()%>/ftp/conf/delete" value="删 除" hidefocus /> -->
<!-- 						</div> -->
						<div class="pager">
							<%@ include file="/resource/jsp/common/pager.jsp"%>
						</div>
					<div>
				</c:when>
				<c:otherwise>
					<div class="noRecord">没有找到任何记录!</div>
				</c:otherwise>
			</c:choose>
		</form>
	</div>
	
</body>
</html>