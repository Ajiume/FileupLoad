var actualHeight = 0;
var sUserAgent = navigator.userAgent;
var isOpera = sUserAgent.indexOf("Opera") > -1;
var isIE = sUserAgent.indexOf("compatible") > -1 && sUserAgent.indexOf("MSIE") > -1 && !isOpera;
var isLtIE7 = false;
if (isIE) {
    var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
    reIE.test(sUserAgent);
    var fIEVersion = parseFloat(RegExp["$1"]);

    isLtIE7 = fIEVersion < 7.0;
}

var REPORT = {
	Util: {
		getWidth: function () {
            //var strWidth = document.body.clientWidth;  //可见区域宽度
            var strWidth = document.body.scrollWidth;    //实际整个网页宽度
            return strWidth;
        },

        getHeight: function () {
        	var strHeight = 0;
        	if (actualHeight == 0) {
        		strHeight = document.body.clientHeight;  //可见区域高度
        	} else if (actualHeight == 1) {
        		strHeight = document.body.scrollHeight;    //实际整个网页高度
        	}
            return strHeight;
        },
        
        lockPage: function () {
            var elements = jQuery("#lockDiv");
            //jQuery("select").hide();
            elements.css("width",REPORT.Util.getWidth());
            elements.css("height",REPORT.Util.getHeight());
            elements.show();
            elements.css("cursor","progress");
        },

        unLockPage: function () {
            var elements = document.getElementById("lockDiv");
            //jQuery("select").show();
            elements.style.display = "none";
            elements.style.cursor = "default";
        }
          
	}
};
