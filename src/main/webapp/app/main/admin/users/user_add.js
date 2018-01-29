/**
 * 
 */
var UserAdd = function() {
	'use strict';
		var _html='<form id="IForm">'
						+'<div class=" add-div">'
							+'<div class="input-group">'
							  +'<span class="input-group-addon"><i class="icon icon-user"></i></span>'
							  +'<input type="text" id ="uAccount" name ="uAccount" class="form-control" placeholder="帐号">'
							+'</div> '
							+'<div class="input-group">'
							  +'<span class="input-group-addon"><i class="icon icon-file-text-o"></i></span>'
							  +'<input type="text" id ="uName" name ="uName" class="form-control" placeholder="姓名">'
							+'</div>'
							+'<div class="input-group">'
							  +'<span class="input-group-addon"><i class="icon icon-envelope-alt"></i></span>'
							  +'<input type="text" id ="uEmail" name ="uEmail" class="form-control" placeholder="邮箱">'
							+'</div>'
							+'<div class="input-group">'
							  +'<span class="input-group-addon"><i class="icon icon-phone"></i></span>'
							  +'<input type="text" id ="uTel" name ="uTel" class="form-control" placeholder="电话">'
							+'</div>'
							+'<div class ="add-btn">'
							+'<button class="btn " onclick="javascript:UserList.saveData();" type="button"><i class="icon icon-save"></i>保存</button>'
							+'<button class="btn " type="button"><i class="icon icon-undo"></i>重置</button>'
							+'</div>'
						+'</div>'
					+'</form>';
	
		//此方法废弃
	var _addUser =function(){
		Dialog.dialog({
			backdrop : 'static',
			iframe : basePath + '/sysuser/add_user.wmctl',
			toggle : 'modal',
			height : '400px',
			width : '700px'
		});
		
	};
 
	return {
		init : function() {

		},
		getAddUserHtml: function(){
			return _html;
		}
	}
}();