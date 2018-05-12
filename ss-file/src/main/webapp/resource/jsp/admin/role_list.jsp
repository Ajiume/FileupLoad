
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
	
<%@ include file="/resource/jsp/common_header.jsp"%>
<body class="list">
	<div class="bar">
		角色管理&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="<%=request.getContextPath()%>/role/list.do" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='<%=request.getContextPath()%>/role/add.do'" value="添加角色" hidefocus />
				&nbsp;&nbsp;
				<label>查找: </label>
				<select name="searchBy">
					<option value="name"<c:if test="${pager.searchBy == 'name'}"> selected</c:if>>
						角色名称
					</option>
				</select>
				<input type="text" name="keyword" value="${pager.keyword}" />
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				&nbsp;&nbsp;
				<label>每页显示: </label>
				<select name="pageSize" id="pageSize">
					<option value="10"<c:if test="${pager.pageSize == 10}"> selected</c:if>>
						10
					</option>
					<option value="20"<c:if test="${pager.pageSize == 20}"> selected</c:if>>
						20
					</option>
					<option value="50"<c:if test="${pager.pageSize == 50}"> selected</c:if>>
						50
					</option>
					<option value="100"<c:if test="${pager.pageSize == 100}"> selected</c:if>>
						100
					</option>
				</select>
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<a href="#" class="sort" name="name" hidefocus>角色名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="isSystem" hidefocus>系统内置</a>
					</th>
					<th>
						<a href="#" class="sort" name="description" hidefocus>描述</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>创建日期</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<c:forEach items="${pager.result}" var="role">
					<tr>
						<td>
							<input type="checkbox"<c:if test="${role.isSystem}"> disabled title='系统内置权限不允许删除!'</c:if><c:if test="${!role.isSystem}"> name='ids' value='${role.fdId}'</c:if> />
						</td>
						<td>
							${role.name}
						</td>
						<td>
							<span <c:if test="${role.isSystem}">class='trueIcon'</c:if><c:if test="${!role.isSystem}">class='falseIcon'</c:if>>&nbsp;</span>
						</td>
						<td>
							${role.description}
						</td>
						<td>
							<c:choose>
								<c:when test="${not empty role.createDate}">
									<fmt:formatDate value="${role.createDate}" pattern="yyyy-MM-dd HH:mm:ss" var="createDate" type="both"/>
									<span>${createDate}</span>
								</c:when>
								<c:otherwise>
									&nbsp;
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<a href="<%=request.getContextPath()%>/role/edit.do?id=${role.fdId}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<c:choose>
				<c:when test="${pager.result != null and fn:length(pager.result) > 0}">
					<div class="pagerBar">
						<div class="delete">
							<input type="button" id="deleteButton" class="formButton" url="<%=request.getContextPath()%>/role/delete.do" value="删 除" disabled hidefocus />
						</div>
						<div class="pager">
							<%@ include file="/resource/jsp/pager.jsp"%>
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