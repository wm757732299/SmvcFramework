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
	var _searchData = function(param) {
		AjaxRequest.asyncAjax(_url,param, function(result) {
			var record = result.data;
			var data = result.data;
			var html="";
			for (var int = 0; int < data.length; int++) {
				html+='<button class="btn btn-sm btn-warning act-btn" type="button" data-menu="'+data[int].menuName+'" data-url="'+data[int].actUrl+'" data-id="'+data[int].id+'">'+data[int].actName+'</button>';
			}
			$('.actionBtnList').append(html);
		});
	};

	var _reLoadData = function() {
		$('.actionBtnList').empty();
		var param ={
				formParam:$("#searchForm").serialize()
		};
		_searchData(param);
	};
	
	var _saveData =function(){
		var saveUrl = basePath + "/sysAction/save_act.wmctl?actId=4444444444444444";
		var formData = $("#addForm").serialize();
		// var shuju = decodeURIComponent(data,true); //将数据解码
		AjaxRequest.asyncAjaxPost(saveUrl, formData, function(result) {
			// 释放按钮
			if (result.success == "true") {
				var data= result.data;
				var html ='<button class="btn btn-sm btn-warning act-btn" type="button" data-menu="'+data.menuName+'" data-url="'+data.actUrl+'" data-id="'+data.id+'">'+data.actName+'</button>';
				$('.actionBtnList').append(html);
			} 
				$.zui.messager.show(result.msg);
		});
	};
	
	var _editData = function(thiz){
		console.log(thiz);
		var saveUrl = basePath + "/sysAction/edit_act.wmctl";
		var formData = $("#editForm").serialize();
		AjaxRequest.asyncAjaxPost(saveUrl, formData, function(result) {
			// 释放按钮
			if (result.success == "true") {
				var data= result.data;
				
			} 
				$.zui.messager.show(result.msg);
		});
	};
	
	var _resetForm= function(){
		// $('#').reset();
	};
	
	var _droppable = function(){
		$('#actionBtn').droppable({
		    selector: '.act-btn', // 定义允许拖放的元素
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
		    				//清空编辑框
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
				//var dropdown=_dropdown(json);
				$("#add_dropdown").append(_dropdown(json,"choose"));
				$("#sear_dropdown").append(_dropdown(json,"searChoose"));
			} else {
				alert("导航树获取失败!");
			}
		});
	};
	// ==根据航树数据初始化下拉菜单==//
	var _dropdown = function(arr,fname) {
		var html='<ul class="dropdown-menu">';
		for (var i = 0; i < arr.length; i++) {
			if(arr[i].nodeType==1){
				html+='<li class="dropdown-submenu">'
					+'<a href="###">'+arr[i].text+'</a>';
				if(arr[i].children !=null){
					html+=_dropdown(arr[i].children,fname);
				}
				html+=' </li>';
			}else if(arr[i].nodeType==0){
				html+='<li><a href="javascript:;" onclick="ActionList.'+fname+'(this)" data-id="'+arr[i].id+'" data-text="'+arr[i].text+'">'+arr[i].text+'</a></li>';
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
	var _searChoose = function(obj){
		var id = $(obj).data("id");
		var text = $(obj).data("text");
		$("#sear_menuId").val(id);
		$("#sear_menuName").val(text);
	};
	
	var _event= function(){
		
		
		$("div").on("click", ".act-btn", function(e) {
			if (this === e.target) {
				var ckEle=$(this);//被点击jq对象
				
				var id = ckEle.data("id");
				var url = ckEle.data("url");
				var menu = ckEle.data("menu");
				console.log(url);
				
				$("#edit_catName").val(ckEle.html());
				$("#edit_id").val(id);
				$("#edit_url").val(url);
				$("#edit_menu").val(menu);
			}
			// 阻止事件冒泡到父元素
			e.stopPropagation();
		});
		
	}
	
	return {
		init : function() {
			_event();
			_searchData(null);
			_droppable();
			_getTree();
		},
		reLoadData : function() {
			_reLoadData();
		},
		saveData : function() {
			_saveData();
		},
		editData :function(thiz){
			_editData(thiz)
		},
		getTree: function(){
			_getTree();
		},
		choose:function(obj){
			_choose(obj);
		},
		searChoose:function(obj){
			_searChoose(obj);
		}
	}
}();