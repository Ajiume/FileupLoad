<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ include file="/resource/jsp/common/common_header.jsp"%>
<%@ taglib uri="/tags" prefix="size"%>
<body class="list">
	<div class="bar">
		FTP配置信息列表&nbsp;总记录数: ${pager.rowCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="<%=request.getContextPath()%>/ftp/file/dataList" method="post">
		<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />&nbsp;
		<input type="button" class="formButton" onclick="location.href='<%=request.getContextPath()%>/ftp/file/uploadPage'" value="上传文件" hidefocus />&nbsp;
 		<input type="button" id="deleteButton" class="formButton" url="<%=request.getContextPath()%>/ftp/file/delete" value="删 除" display="none" hidefocus />
			<div class="listBar">
				<label>模块名称: ${param.fdModelName}</label>
				<select name="fdModelName" readonly style="width:160px; height:22px">
					<option value="">全部</option>
					<c:forEach items="${models }" var="model">
						<option value="${model.modelName }" <c:if test="${param.fdModelName == model.modelName }"> selected</c:if>>
							${model.remark }
						</option>
					</c:forEach>
				</select>
				<label>内容类型: </label>
				<%-- <input type="text" name="fdContentType" value="${param.fdContentType}" />&nbsp; --%>
				<select name="fdContentDescribe" readonly style="width:160px; height:22px">
					<option value="">全部</option>
					<c:forEach items="${types }" var="type">
						<option value="${type }" <c:if test="${param.fdContentDescribe == type }"> selected</c:if>>
							${type }
						</option>
					</c:forEach>
				</select>
				<label>原始文件名称: </label>
				<input type="text" name="fdSrcName" value="${param.fdSrcName}" /><br/>
				<label>存储文件名称: </label>
				<input type="text" name="fdDestName" value="${param.fdDestName}" style="margin-top: 5px;"/>&nbsp;
				<label>每页显示: </label>
				<select name="pageSize" id="pageSize" style="margin-top: 5px;">
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
						<a href="#" class="sort" name="fd_model_name" hidefocus>模块名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="fd_src_name" hidefocus>原始文件名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="fd_dest_name" hidefocus>存储文件名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="fd_content_type" hidefocus>内容类型</a>
					</th>
					<th>
						<a href="#" class="sort" name="fd_size" hidefocus>文件大小</a>
					</th>
					<th>
						<a href="#" class="sort" name="fd_att_location" hidefocus>ip地址</a>
					</th>
					<th>
						<a href="#" class="sort" name="create_time" hidefocus>上传时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="download_sum" hidefocus>下载次数</a>
					</th>
					<th>
						<a>操作</a>
					</th>
				</tr>
				<c:forEach items="${pager.dataList}" var="fileMain">
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${fileMain.fdId}" />
						</td>
						<td>
							${fileMain.fdModelName}
						</td>
						<td>
							${fileMain.fdSrcName}
						</td>
						<td>
							${fileMain.fdDestName}
						</td>
						<td>
							${fileMain.fdContentDescribe}
						</td>
						<td>
							<size:size value ="${fileMain.fdSize} "/>
						</td>
						
						<td>
							${fileMain.fdAttLocation}
						</td>
						<td>
							<fmt:formatDate value="${fileMain.createTime}" pattern="yyyy-MM-dd HH:mm:ss" var="createTime" type="both"/>
							<span>${createTime}</span>
						</td>
						<td>
							${fileMain.downloadSum}
						</td>
						<td>
							<%-- <a href="${preUrl}${fileMain.fdFilePath}/${fileMain.fdDestName}" title="下载">[下载]</a>&nbsp; --%>
							<a href="<%=request.getContextPath()%>/ftp/file/download/file/${fileMain.fdId}" title="下载">[下载]</a>&nbsp;
							<a id="deleteById" href="javascript:void(0);"  onclick="deleteById(${fileMain.fdId})" title="下载">[删除]</a>&nbsp;
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
	
<script type="text/javascript">

function deleteById(fileId) {
	var url = "<%=request.getContextPath()%>/ftp/file/delete";
	$.dialog({type: "warn", content: "您确定要删除吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: batchDelete});
	function batchDelete() {
		$.ajax({
			url: url,
			data: {"ids": fileId},
			type: "POST",
			dataType: "json",
			cache: false,
			success: function(data) {
				debugger
				if (data.status == "success") {
					/* $idsCheckedCheck.parent().parent().remove(); */
				}
				$.message({type: data.status, content: data.message});
				setTimeout(function(){
				    $("#listForm").submit();
				},2000);
			}
		});
	}
}

</script>
</body>
</html>