<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/report_util.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/report_org_util.js"></script>

<script type="text/javascript">

	var setting = {
		view: {
			showLine: true,
			showIcon: true,
			selectedMulti: false,
			dblClickExpand: false,
			expandSpeed: "fast"
		},
		data: {
			simpleData: {
				enable: true
			}
			
		},
		callback: {
			onClick: zTreeOnClick
		}
	};

	var zNodes =[];
	
	<c:if test="${not empty requestScope.orgTreeStr}">
		zNodes = ${requestScope.orgTreeStr};
	</c:if>

	//页面初始化
	$(document).ready(function(){
		
	});
	
	//单击当前节点
	function zTreeOnClick(e, treeId, treeNode) {
		var objLeft = document.getElementById("left");
		removeLeftAll();
		if (treeNode.children) {
			var childNodes = treeNode.children;
			for (var i = 0; i < childNodes.length; i++) {
				var opt = new Option(childNodes[i].name, childNodes[i].id);
				objLeft.options.add(opt);
				opt.selected = false;
			}
			searchUserByDeptId(treeNode.id);
		} else {
			searchUserByDeptId(treeNode.id);
		}
	}
	
	
	//显示组织机构
	function showOrgDiv() {
		REPORT.Util.lockPage();
		var org_div = $("#org_tree_div");
		org_div.fadeIn("slow");
		document.getElementById("org_tree_div").style.left=(document.body.clientWidth)/2 - (document.getElementById("org_tree_div").offsetWidth)/2 + "px";    			
	   	document.getElementById("org_tree_div").style.top=(document.body.clientHeight)/2 - (document.getElementById("org_tree_div").offsetHeight)/2 + "px";
	   	initOrgTree();
	}
	
	//隐藏组织机构
	function hideOrgDiv() {
		REPORT.Util.unLockPage();
		var org_div = $("#org_tree_div");
		org_div.fadeOut("slow");
	}
	
	//初始化组织机构树
	function initOrgTree() {
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var rootNode = treeObj.getNodeByParam("name", "美的集团", null);
		treeObj.expandNode(rootNode, null, null, null, true);
		removeLeftAll();
		removeRightAll();
		$("#searchUserName").val("");
	}
	
	
	//根据部门id获取部门员工
	function searchUserByDeptId(deptId) {
		var searchURL = "";
		if (deptId == null || deptId == "") {
			return;
		} else {
			searchURL = "<%=request.getContextPath()%>/log/uservisit/searchUserByDeptId.do?deptId=" + deptId;
		}
		jQuery.ajax({
			type : "POST",
			url : searchURL,
			data : "sn=" + new Date(),
			dataType : "text",
			success : searchUserCallback
		});
	}
	
	//搜索员工
	function searchUser() {
		var username = $("#searchUserName").val();
		var searchURL = "";
		if (username == null || username == "") {
			alert("请输入员工姓名.");
			return;
		} else {
			removeLeftAll();
			searchURL = "<%=request.getContextPath()%>/log/uservisit/searchUser.do?username=" + encodeURI(encodeURI(username));
		}
		jQuery.ajax({
			type : "POST",
			url : searchURL,
			data : "sn=" + new Date(),
			dataType : "text",
			success : searchUserCallback
		});
	}
	
	////搜索员工回调
	function searchUserCallback(data) {
		if (data.toString() == "NULL") return;
		var userJson = eval("("+ data+ ")");
		var userList = userJson.userList;
		for (var i = 0; i < userList.length; i++) {
			document.getElementById("left").options.add(new Option(userList[i].userName, userList[i].fdID));			
		}
	}

</script>


  	<div id="org_tree_div" style="display:none;background:#ffffff;position: absolute; z-index: 51;border:1px solid #99bbe8;width:600px;height:430px;">
 		<div id="divTitle" style="background:#dfe8f6;width:100%;font:13px/22px Arial, Helvetica, sans-serif,'宋体';"><table width="100%"><tr><td style="width:20%">&nbsp;<strong>人员组织机构选择</strong></td><td style="width:76%">&nbsp;</td><td style="width:3%;cursor:pointer;"><span onclick="hideOrgDiv()">X</span></td></tr></table></div>
		<div id="divContent" style="font:13px/22px Arial, Helvetica, sans-serif,'宋体';background:#ffffff;width:100%;height:95%;">
			<table style="width:98%;height:100%">
				<tr>
					<td style="height:100%;vertical-align:top;border-right:1px solid #99bbe8;">
						<div class="zTreeDemoBackground left" style="width:200px;height:400px;overflow-x:auto;overflow-y:scroll;">
							<ul id="treeDemo" class="ztree"></ul>
						</div>
					</td>
					<td style="text-align:center;vertical-align:top">
					
			<table border="0" style="margin-left:10px;">
				<tr>
					<td>
						<fieldset>
							<legend>员工搜索</legend>
							<center>
								<table>
									<tr>
										<td style="font:13px/22px Arial, Helvetica, sans-serif,'宋体';">员工姓名&nbsp;&nbsp;</td>
										<td><input type="text" id="searchUserName" name="searchUserName" style="width:200px;" maxlength="20"/></td>
										<td>&nbsp;&nbsp;<INPUT TYPE="button" value="搜索" onclick="searchUser()"></td>
									</tr>
								</table>
							</center>
						</fieldset><br />
					</td>
				</tr>
				<tr>
					<td>
						<table style="margin-left:20px;">
							<tr>
								<td width="40%">
									<select multiple name="left" id="left" size="8" style='width:120px;height:250px;' 
									  ondblclick="moveToRightOption(document.getElementById('left'), document.getElementById('right'))">
									</select>
								</td>
								<td width="20%" align="center">
									<input type="button" value="增加" onclick="moveToRightOption(document.getElementById('left'),document.getElementById('right'))"><br><br>
									<input type="button" value="删除" onclick="moveRightOption(document.getElementById('right'))"><br><br>
									<input type="button" value="增加全部" onclick="moveAllToRightOption(document.getElementById('left'),document.getElementById('right'))"><br><br>
									<input type="button" value="删除全部" onclick="removeRightAll()">
								</td>
								<td width="40%">
									<select  multiple name="right" id="right" size="8" style='width:120px;height:250px;'>
									</select>
								</td>
							</tr>
						</table><br />
					</td>
			    </tr>
			    <tr>
					<td colspan="3">
						<CENTER>
							<INPUT TYPE="button" value="确定" onclick="setOrg()">&nbsp;&nbsp;&nbsp;&nbsp;
							<INPUT TYPE="button" value="取消" onclick="hideOrgDiv()">
						</CENTER>
					</td>
				</tr>
			</table>
					
					
						

					</td>
				</tr>
			</table>
		</div>
  	</div>

	<div id="lockDiv" style="position: absolute; z-index: 50; background: #E0E3E6; top: 0px; left: 0px;filter:alpha(opacity=70); display: none;"></div>
	
