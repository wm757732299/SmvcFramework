/**
 * @author:WangMingM
 * @date:2018.01
 */
$(function() {
	ActionList.init();
});

var ActionList = function() {
	'use strict';
	var _cols = [];
	
	var _url = basePath + "/sysAction/action_list_data.wmctl";
	var _param=null;
	var _data = null;
	var _loginRoleLevel=null;
	var _zTreeId="ztree";
	var _treeObj=null;
	// =========================定义方法===============================//
	var _searchData = function(url) {
		AjaxRequest.asyncAjax(url,null, function(result) {
			var record = result.data;
			var data = result.data;
			var html="";
			for (var int = 0; int < data.length; int++) {
				html+='<div class="col-md"><button class="btn btn-sm btn-warning btn-droppable zIndex" type="button" data-id="'+data[int].id+'"><span class="btn-droppable-id">'+data[int].actName+'</span></button></div>';
			}
			$('.actionBtnList').append(html);
		});
	};

	var _reLoadData = function(param) {
		
	};
	
	var _saveData =function(){
		var saveUrl = basePath + "/sysAction/save_act.wmctl";
		var formData = $("#addForm").serialize();
		// var shuju = decodeURIComponent(data,true); //将数据解码
		AjaxRequest.asyncAjaxPost(saveUrl, formData, function(result) {
			// 释放按钮
			if (result.success == "true") {
				var data= result.data;
				var html ='<div class="col-md"><button class="btn btn-sm btn-warning btn-droppable zIndex" type="button" data-id="'+data.id+'"><span class="btn-droppable-id">'+data.actName+'</span></button></div>';
				$('.actionBtnList').append(html);
			} 
			$.zui.messager.show(result.msg);
		});
	};
	
	var _resetForm= function(){
		//$('#').reset();
	};
	
	var _droppable = function(){
		$('#actionBtn').droppable({
		    selector: '.btn-droppable', // 定义允许拖放的元素
		    target: '.droppable-target',
		    start: function() {
		        $('#actionBtn .droppable-target').removeClass('panel-warning').removeClass('panel-success').find('.panel-heading').text('拖动到这里吗？');
		    },
		    drop: function(event) {
		        if(event.target) {
		        	var url =basePath+"/sysAction/del_action.wmctl";
		        	var id=event.element.data("id");
		        	var data={actionId:id};
		    		AjaxRequest.asyncAjax(url,data, function(result) {
		    			var record = result.data;
		    			var data = result.data;
		    			if(result.success=="true"){
		    				event.element.remove();
		    			}
		    			$.zui.messager.show(result.msg);
		    		});
		        }
		    },
		    drag: function(event) {
		        $('#actionBtn .droppable-target').removeClass('panel-success').removeClass('panel-warning');
		        if(event.target) event.target.addClass('panel-warning');
		    }
		});
		
	};
	
	// ==获得导航树数据==//
	var _getTree = function() {
		var url = basePath + "/menu/init_menu.wmctl";
		AjaxRequest.asyncAjax(url, null, function(result) {
			if (result.success == "true") {
				var json = result.data;
				var dropdown=_dropdown(json);
				$("#add_dropdown").append(dropdown);
			} else {
				alert("导航树获取失败!");
			}
		});
	};
	// ==根据航树数据初始化下拉菜单==//
	var _dropdown = function(arr) {
		var html='<ul class="dropdown-menu">';
		for (var i = 0; i < arr.length; i++) {
			if(arr[i].nodeType==1){
				html+='<li class="dropdown-submenu">'
					+'<a href="###">'+arr[i].text+'</a>';
				if(arr[i].children !=null){
					html+=_dropdown(arr[i].children);
				}
				html+=' </li>';
			}else if(arr[i].nodeType==0){
				html+='<li><a href="javascript:;" onclick="ActionList.choose(this)" data-id="'+arr[i].id+'" data-text="'+arr[i].text+'">'+arr[i].text+'</a></li>';
			}
		}
		html+='</ul>';
		return html;
	};
	var _choose = function(obj){
		var id = $(obj).data("id");
		var text = $(obj).data("text");
		$("#menuId").val(id);
		$("#ubiety").val(text);
	};
	return {
		init : function() {
			_searchData(_url);
			_droppable();
			_getTree();
		},
		searchData : function() {
			_searchData(_cols, _url);
		},
		saveData : function() {
			_saveData();
		},
		getTree: function(){
			_getTree();
		},
		choose:function(obj){
			_choose(obj);
		}
	}
}();