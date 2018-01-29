/* ========================================================================
 * @author:WangMingM
 * ========================================================================
 *  ajax请求         2017.10.31
 * ======================================================================== */
'use strict';
var AjaxRequest = function() {

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
		}

	}
}();
