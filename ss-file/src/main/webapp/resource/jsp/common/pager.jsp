<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
	
<script type="text/javascript">
$().ready( function() {
	
	var $pager = $("#pager");
	
	$pager.pager({
		pagenumber: ${pager.pageId},
		pagecount: ${pager.pageCount},
		buttonClickCallback: $.gotoPage
	});

})
</script>
<span id="pager"></span>
<input type="hidden" name="pageNumber" id="pageNumber" value="${pager.pageId}" />
<input type="hidden" name="orderBy" id="orderBy" value="${pager.orderField}" />
<input type="hidden" name="order" id="order" value="${pager.order}" />

