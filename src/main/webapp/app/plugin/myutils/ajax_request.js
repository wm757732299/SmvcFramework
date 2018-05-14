/* ========================================================================
 * @author:WangMingM
 * ========================================================================
 *  ajax请求         2017.10.31
 * ======================================================================== */
'use strict';
var AjaxRequest = function() {
	
	/*方法执行效率测试
	 * i 执行次数
	 * 测试函数
	 * 测试函数参数
	 */
	var  _codeEff = function(i, fn, para) {
		var start = new Date().getTime();// 起始时间
		for (var int = 0; int < i; int++) {
			fn(para);
		}
		var end = new Date().getTime();// 接受时间
		console.log("=======代码执行 " + i + "次耗时:" + (end - start) + "毫秒=======");
	}
	

	//异步
	var _asyncAjax = function(url, data, callback) {
		$.ajax({
			type : 'GET',
			url : url,
			data : data,
			async : true,
			success : callback
		});
	};
	
	var _asyncAjaxPost = function(url, data, callback) {
		$.ajax({
			type : 'post',
			url : url,
			data : data,
//            contentType:'application/json;charset=UTF-8', 
			async : true,
			success : callback
		});
	};
	//同步
	var _syncAjax = function(url, data, callback) {
		$.ajax({
			type : 'GET',
			url : url,
			data : data,
			async : false,
			success : callback
		});
	};
	var _syncAjaxPost = function(url, data, callback) {
		$.ajax({
			type : 'post',
			url : url,
			data : data,
			async : false,
			success : callback
		});
	};
	return {
		init : function() {

		},
		asyncAjax : function(url, data, callback) {
			_asyncAjax(url, data, callback);
		},
		asyncAjaxPost : function(url, data, callback){
			_asyncAjaxPost(url, data, callback);
		},
		syncAjax : function(url, data, callback) {
			_syncAjax(url, data, callback);
		},
		syncAjaxPost : function(url, data, callback){
			_syncAjaxPost(url, data, callback);
		},
		codeEff :function(i, fn, para){
			_codeEff(i, fn, para);
		}

	}
}();
