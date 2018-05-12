<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
	
<%@ include file="/resource/jsp/common_header.jsp"%>
<body class="list">
	<div class="bar">
		管理员列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="<%=request.getContextPath()%>/admin/list.do" method="post">
			<div class="listBar">
				<!--  <input type="button" class="formButton" onclick="location.href='<%=request.getContextPath()%>/admin/add.do'" value="添加用户" hidefocus />-->
				<select name="searchBy">
					<option value="username"<c:if test="${pager.searchBy == 'username'}"> selected</c:if>>
						用户名
					</option>
					<option value="name"<c:if test="${pager.searchBy == 'name'}"> selected</c:if>>
						姓名
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
						<a href="#" class="sort" name="username" hidefocus>用户名</a>
					</th>
					<th>
						<a href="#" class="sort" name="email" hidefocus>E-mail</a>
					</th>
					<th>
						<a href="#" class="sort" name="name" hidefocus>姓名</a>
					</th>
					<th>
						<a href="#" class="sort" name="department" hidefocus>所属部门</a>
					</th>
					<th>
						<a href="#" class="sort" name="loginDate" hidefocus>最后登录时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="loginIp" hidefocus>最后登录IP</a>
					</th>
					<th>
						<span>状态</span>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>创建日期</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<c:forEach items="${pager.result}" var="admin">
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${admin.fdId}" />
						</td>
						<td>
							${admin.username}
						</td>
						<td>
							${admin.email}
						</td>
						<td>
							<c:choose>
								<c:when test="${not empty admin.name}">
									${admin.name}
								</c:when>
								<c:otherwise>
									&nbsp;
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${not empty admin.department}">
									${admin.department}
								</c:when>
								<c:otherwise>
									&nbsp;
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${not empty admin.loginDate}">
									<fmt:formatDate value="${admin.loginDate}" pattern="yyyy-MM-dd HH:mm:ss" var="loginDate" type="both"/>
									<span>${loginDate}</span>
								</c:when>
								<c:otherwise>
									&nbsp;
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${not empty admin.loginIp}">
									${admin.loginIp}
								</c:when>
								<c:otherwise>
									&nbsp;
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${admin.isAccountEnabled and !admin.isAccountLocked and !admin.isAccountExpired and !admin.isCredentialsExpired}">
									<span class="green">正常</span>
								</c:when>
								<c:when test="${!admin.isAccountEnabled}">
									<span class="red"> 未启用 </span>
								</c:when>
								<c:when test="${admin.isAccountLocked}">
									<span class="red"> 已锁定 </span>
								</c:when>
								<c:when test="${admin.isAccountExpired}">
									<span class="red"> 已过期 </span>
								</c:when>
								<c:otherwise>
									<span class="red"> 凭证过期 </span>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${not empty admin.createDate}">
									<fmt:formatDate value="${admin.createDate}" pattern="yyyy-MM-dd HH:mm:ss" var="createDate" type="both"/>
									<span>${createDate}</span>
								</c:when>
								<c:otherwise>
									&nbsp;
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<a href="<%=request.getContextPath()%>/admin/edit.do?id=${admin.fdId}" title="编辑">[查看]</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<c:choose>
				<c:when test="${pager.result != null and fn:length(pager.result) > 0}">
					<div class="pagerBar">
				<!--  		<div class="delete">
							<input type="button" id="deleteButton" class="formButton" url="<%=request.getContextPath()%>/admin/delete.do" value="删 除" disabled hidefocus />
						</div>-->
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