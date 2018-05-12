<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ include file="/resource/jsp/common/common_header.jsp"%>
<body class="list">
	<div class="bar">
		FTP配置信息列表&nbsp;总记录数: ${pager.rowCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="<%=request.getContextPath()%>/sys/model/dataList" method="post">
		<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />&nbsp;
		<input type="button" class="formButton" onclick="location.href='<%=request.getContextPath()%>/sys/model/add'" value="新增" hidefocus />&nbsp;
		<input type="button" id="deleteButton" class="formButton" url="<%=request.getContextPath()%>/sys/model/delete" value="删 除" hidefocus />
			<div class="listBar">
				<label>模块名称: </label>
				<input type="text" name="modelName" value="${param.modelName}" />&nbsp;
				<label>模块目录: </label>
				<input type="text" name="modelPath" value="${param.modelPath}" />&nbsp;
				<label>备注信息: </label>
				<input type="text" name="remark" value="${param.remark}" />&nbsp;
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
						<a href="#" class="sort" name="model_id" hidefocus>ID</a>
					</th>
					<th>
						<a href="#" class="sort" name="model_name" hidefocus>模块名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="model_path" hidefocus>模块目录</a>
					</th>
					<th>
						<a href="#" class="sort" name="remark" hidefocus>备注信息</a>
					</th>
					<th>
						<a>操作</a>
					</th>
				</tr>
				<c:forEach items="${pager.dataList}" var="sysModel">
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${sysModel.modelId}" />
						</td>
						<td>
							${sysModel.modelId}
						</td>
						<td>
							${sysModel.modelName}
						</td>
						<td>
							${sysModel.modelPath}
						</td>
						<td>
							${sysModel.remark}
						</td>
						<td>
							<a href="<%=request.getContextPath()%>/sys/model/edit?modelId=${sysModel.modelId}" title="修改">[修改]</a>&nbsp;
						</td>
					</tr>
				</c:forEach>
			</table>
			<c:choose>
				<c:when test="${pager.dataList != null and fn:length(pager.dataList) > 0}">
					<div class="pagerBar">
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