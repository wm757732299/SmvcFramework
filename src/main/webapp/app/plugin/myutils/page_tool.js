/* ========================================================================
 * @author:WangMingM
 * ========================================================================
 *  分页工具      2017.10
 * ======================================================================== */
'use strict';
var PageTool = function(){
	var _cols=[], _url="";
	
	var totalRows=0;// 总记录数
	var totalPage=0;// 总页数
	var pageSize=10;//每页条数
	var curPage=1;//当前页码
	
	var _list =Function;
	
	function _createPageBar(pageInfo) {
		if(pageInfo){
			 totalRows = pageInfo.totalRows; // 总记录数
			 totalPage = pageInfo.totalPage; // 总页数
			 pageSize = pageInfo.pageSize;// 每页条数
			 curPage = pageInfo.curPage; // 当前页
			 
			 if(curPage>=totalPage){
				 curPage=totalPage;
			 }
		}
		var page = "<ul id='pager' class='pager pager-loose'>";
		
			page+="<li class='first "+(curPage==1 ? "disabled":"")+"'><a href='javascript:PageTool.first()'>首页</a></li>"
				+"<li class='previous "+(curPage==1 ? "disabled":"")+"'><a href='javascript:PageTool.prevPage()'>上一页</a></li>";
		
		var i = 1;
		$("#pages").empty();
		if (totalPage > 0 && totalPage <= 10) {
			for (i = 1; i <= totalPage; i++) {
				page += "<li "+(i==curPage ? "class='active'":"")+"'><a href='javascript:PageTool.pageNo("+i+");'>" + i + "</a></li>";
			}

		} else if (totalPage > 10) {
			if (curPage > 0 && curPage <= 6) {
				for (i = 1; i <= 10; i++) {
					page +="<li "+(i==curPage ? "class='active'":"")+"'><a href='javascript:PageTool.pageNo("+i+");'>" + i + "</a></li>";
				}
			} else if (curPage > 6 ) {
				if(curPage + 4 <= totalPage){
					for (i = curPage - 5; i <= curPage + 4; i++) {//-3
						page +="<li "+(i==curPage ? "class='active'":"") +"'><a href='javascript:PageTool.pageNo("+i+");'>" + i + "</a></li>";
					}
				}else if(curPage <= totalPage && curPage + 4 > totalPage){
					for (i = totalPage - 9; i <= totalPage; i++) {//-4
						page +="<li "+(i==curPage ? "class='active'":"")+"'><a href='javascript:PageTool.pageNo("+i+");'>" + i + "</a></li>";
					}
				}
			} 
		}
			page+="<li class='next "+(curPage>=totalPage ? "disabled":"")+"'><a href='javascript:PageTool.nextPage()'>下一页</a></li>"
				+"<li class='last "+(curPage>=totalPage ? "disabled":"")+"'><a href='javascript:PageTool.last()'>尾页</a></li></ul>";
		$("#pages").append(page);
	};
	
	// 上一页
	var _prevPage = function () {
		 totalPage = parseInt((totalRows + pageSize - 1) / pageSize); // 总页数
		if (curPage > 1 && curPage <= totalPage) {
			curPage -= 1;
			_list.reLoadData({curPage:curPage,pageSize:pageSize});
		}
		
	};
	// 下一页
	var _nextPage =function () {
		  totalPage = parseInt((totalRows + pageSize - 1) / pageSize); // 总页数
		if (curPage > 0 && curPage < totalPage) {
			curPage += 1;
			_list.reLoadData({curPage:curPage,pageSize:pageSize});
		}
//		_createPageBar("");
		
	};
	// 第N页
	var _pageNo = function (obj) {
		 curPage = obj;// 当前页
		//var pageSize = $("#jumpMenu1").val();// 每页显示条数
		_list.reLoadData({curPage:curPage,pageSize:pageSize});
	};
	var _first = function(){
		curPage=1;
		_list.reLoadData({curPage:curPage,pageSize:pageSize});
	};
	var _last = function(){
		curPage=totalPage;
		_list.reLoadData({curPage:curPage,pageSize:pageSize});
	};
	// 设置每页条数
	function MM_jumpMenu(obj) {
		var pageSize = $("#" + obj.id).val();// $("#jumpMenu1").val();//每页显示条数
		var curPage = 1;// 当前页
		//searchByPage(pageSize, curPage);
	};
	
	return {
		init : function(obj){
			_list =obj;
//			  _cols = cols; _url =url;
//			 if(typeof fn =='function'){
//				 _searchByPage=fn;
//			 }
			return {curPage:curPage,pageSize:pageSize};
		},
		createPageBar:function(data){
			_createPageBar(data);
		},
		prevPage :function(){
			_prevPage();
		},
		nextPage:function(){
			_nextPage();
		},
		pageNo:function(obj){
			_pageNo(obj);
		},
		first :function(){
			_first();
		},
		last: function(){
			_last();
		}
	}
}();