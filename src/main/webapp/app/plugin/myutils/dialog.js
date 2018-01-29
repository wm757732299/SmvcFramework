/* ========================================================================
 * @author:WangMingM
 * ========================================================================
 *  自定义弹出框       2017.10
 * ======================================================================== */
'use strict';
var Dialog = function() {

	var __del="";
	var _del = function(prompt,btn_id) {
		return new $.zui.ModalTrigger(
				{
					showFooter : true,
					title : '确认',
					icon : 'icon icon-exclamation-sign',
					backdrop : 'static',
					width : '270px',
					height : '176px',
					moveable : true,
					custom : function() {
						var html = '<div style="height:40px; margin-top: 10px; text-align: center"><i class="icon icon-question-sign"></i> '+(prompt?prompt:'您确定要删除吗？')+'</div>'
								+ '<div class="modal-footer"  style="text-align: center;">'
								+ '<button id="'
								+ btn_id
								+ '" type="button" class="btn btn-primary" >确&nbsp;&nbsp;&nbsp;认</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
								+ '<button type="button" class="btn btn-default" data-dismiss="modal">取&nbsp;&nbsp;&nbsp;消</button>'
								+ '</div>';

						return html;
					}
				});

	};
	var _delDialog = function(callback) {
		var id = $.zui.uuid();
		__del =_del(null,id);
		__del.show();
		if (typeof callback == "function") {
			$('#' + id).click(callback);
		}
	};
	var _delProDialog = function(prompt,callback) {
		var id = $.zui.uuid();
		__del =_del(prompt,id);
		__del.show();
		if (typeof callback == "function") {
			$('#' + id).click(callback);
		}
	};
	
	
	var _dialog = function(option){
		return new $.zui.ModalTrigger(option);
	};

	return {
		init : function() {

		},
		delDialog : function(callback) {
			_delDialog(callback);
		},
		delProDialog : function(prompt,callback) {
			_delProDialog(prompt,callback);
		},
		confirmDialog : function(prompt,callback) {
			_delProDialog(prompt,callback);
		},
		getConfirmDialog: function(){
			return __del;
		},
		getDelDialog: function(){
			return __del;
		},
		dialog : function(option){
			return _dialog(option);
		}
	}
}();
