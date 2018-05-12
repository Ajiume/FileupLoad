	//移动到右边
	function moveToRightOption(obj1, obj2) {
		 var objSize = obj2.options.length;
		 if (objSize == 5 || objSize > 5) {
		 	 //alert("最多只能引用5个代码;");
		 	 //return;
		 }
		 var opt;
		 for(var i = obj1.options.length - 1 ; i >= 0 ; i--) {
			 if(obj1.options[i].selected) {
				 opt = new Option(obj1.options[i].text,obj1.options[i].value);
				 break;
			 }
		 }
		 for (var j = 0; j<obj2.options.length; j++) {
			 if(opt.value == obj2.options[j].value) {
				 alert("该对象已经存在;");
				 return;
			 }
		 }
		 for(var i = obj1.options.length - 1 ; i >= 0 ; i--) {
			 if(obj1.options[i].selected) {
				 opt = new Option(obj1.options[i].text,obj1.options[i].value);
				 opt.selected = false;
				 obj2.options.add(opt);
				 //obj1.remove(i);
				 break;
			 }
		 }
	}
	
	//删除右边指定的引用代码
	function moveRightOption(right) {
		for(var i = right.options.length - 1 ; i >= 0 ; i--) {
			if(right.options[i].selected) {
				right.remove(i);
				break;
			}
		}
	}
	
	//移动到左边
	function moveToLeftOption(obj1, obj2) {
		 for(var i = obj1.options.length - 1 ; i >= 0 ; i--) {
			 if(obj1.options[i].selected) {
				var opt = new Option(obj1.options[i].text,obj1.options[i].value);
				opt.selected = false;
				obj2.options.add(opt);
				obj1.remove(i);
			}
		 }
	}

	//删除全部选项
	function removeAll() {
		var objRight = document.getElementById("right");
		var objLeft = document.getElementById("left");
		if (objRight.options.length == 0) return;
		for (var i=0; i<objRight.options.length; i++) {
			var opt = new Option(objRight.options[i].text,objRight.options[i].value);
			objLeft.options.add(opt);
			objRight.remove(i);
			i = i - 1;
		}
	}
	
	//全部移动到右边
	function moveAllToRightOption(obj1, obj2) {
		var objRight = document.getElementById("right");
		var objLeft = document.getElementById("left");
		if (objLeft.options.length == 0) return;
		
		for (var i=0; i<objLeft.options.length; i++) {
			var opt = new Option(objLeft.options[i].text,objLeft.options[i].value);
			for (var j = 0; j<objRight.options.length; j++) {
				 if(opt.value == objRight.options[j].value) {
					 alert("[" + opt.text + "] 该对象已经存在;");
					 return;
				 }
			 }
		}
		
		for (var i=0; i<objLeft.options.length; i++) {
			var opt = new Option(objLeft.options[i].text,objLeft.options[i].value);
			objRight.options.add(opt);
			opt.selected = false;
		}
	}
	
	//删除左边全部选项
	function removeLeftAll() {
		var objLeft = document.getElementById("left");
		objLeft.options.length=0;
	}
	
	//删除右边全部选项
	function removeRightAll() {
		var objRight = document.getElementById("right");
		objRight.options.length = 0;
	}
	
	//搜索引用代码插入到左边选择框
	function searchCodeList() {		
		var codeSubject = document.getElementById("searchCodeSubject").value;
		var upgradeId = document.getElementsByName("upgradeId")[0].value;
	   	var upgradeName = document.getElementsByName("upgradeName")[0].value;
	   	initLeftCode(codeSubject);
	}
	
	//初始化引用代码左边选择框
	function initialSelect(codeSubject, upgradeId, upgradeName) {
		if (codeSubject == null || codeSubject == "") {
			initLeftCode(null);
		} else {
			initLeftCode(codeSubject);
		}
		initRightCode(upgradeId, upgradeName);
	}
	
	//初始化引用代码右边选择框
	function initRightCode(upgradeId, upgradeName) {
		removeRightAll();
		if (upgradeId != null && upgradeId != "") {
			var upgradeIds = upgradeId.split(",");
			var upgradeNames = upgradeName.split(",");
			for (var i=0; i<upgradeIds.length; i++) {
				document.getElementById("right").options.add(new Option(upgradeNames[i], upgradeIds[i]));
			}
		}
	}
	
	//设置引用ID和名称
	function setOrg() {
		var objLeft = document.getElementById("left");
		var objRight = document.getElementById("right");
		var values = "";
		var texts ="";
		if (objLeft.options.length == 0) {
			document.getElementsByName("orgId")[0].value = "";
		   	document.getElementsByName("orgName")[0].value = "";
		   	return;
		}
		if (objRight.options.length == 0) {
			document.getElementsByName("orgId")[0].value = "";
		   	document.getElementsByName("orgName")[0].value = "";
		   	return;
		}
		if (objRight.options.length == 1) {
			values = values + objRight.options[0].value;
			texts = texts + objRight.options[0].text;
			document.getElementsByName("orgId")[0].value = values;
			document.getElementsByName("orgName")[0].value = texts;
		} else {
			for (var i=0; i<objRight.options.length; i++) {
				if (i > 0) {
					values = values + "," + objRight.options[i].value;
					texts = texts + "," + objRight.options[i].text;
				} else {
					values = objRight.options[i].value;
					texts = objRight.options[i].text;
				}
			}
			document.getElementsByName("orgId")[0].value = values;
			document.getElementsByName("orgName")[0].value = texts;
		}
		hideOrgDiv();
	}
	
	
	
	