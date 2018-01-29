/**
 * 
 */
var RoleAdd = function() {
	'use strict';
		var _html='<form id="IForm">'
				+'<input type="hidden" id="id" name="id"   readonly="readonly" >'
						+'<div class=" add-div">'
							+'<div class="input-group">'
							  +'<span class="input-group-addon">角色</span>'
							  +'<input type="text" id ="roleName" name ="roleName" class="form-control" placeholder="角色名称">'
							+'</div> '
							+'<div class="input-group">'
							  +'<span class="input-group-addon">级别</span>'
							  +'<select id ="roleLevel" name ="roleLevel" class="form-control">'
							  +'<option value="">请选择</option>'
							  +'<option value=1>一级</option>'
							  +'<option value=2>二级</option>'
							  +'<option value=3>三级</option>'
							  +'<option value=4>四级</option>'
							  +'<option value=5>五级</option>'
							  +'<option value=6>六级</option>'
							 +'</select>'
							+'</div>'
							+'<div class="input-group">'
							  +'<span class="input-group-addon">备注</span>'
							  +'<textarea id ="remarks" name ="remarks" class="form-control" rows="3" placeholder="200字以内"></textarea>'
							+'</div>'
							 
							+'<div class ="add-btn">'
							+'<button class="btn " id="saveBtn"  type="button"><i class="icon icon-save"></i>保存</button>'
							+'<button class="btn " type="button"><i class="icon icon-undo"></i>重置</button>'
							+'</div>'
						+'</div>'
					+'</form>';
		
		var _initSelect = function(n){
				if(typeof(n) == "number"){
					var opt ="";
					for (var i =(n+1); i <=6; i++) {
						opt+='<option value='+i+'>'+_formart(i)+'</option>';
					}
					
					var html='<form id="IForm">'
						+'<input type="hidden" id="id" name="id"   readonly="readonly" >'
								+'<div class=" add-div">'
									+'<div class="input-group">'
									  +'<span class="input-group-addon">角色</span>'
									  +'<input type="text" id ="roleName" name ="roleName" class="form-control" placeholder="角色名称">'
									+'</div> '
									+'<div class="input-group">'
									  +'<span class="input-group-addon">级别</span>'
									  +'<select id ="roleLevel" name ="roleLevel" class="form-control">'
									  +'<option value="">请选择</option>'
									  +opt
									 +'</select>'
									+'</div>'
									+'<div class="input-group">'
									  +'<span class="input-group-addon">备注</span>'
									  +'<textarea id ="remarks" name ="remarks" class="form-control" rows="3" placeholder="200字以内"></textarea>'
									+'</div>'
									 
									+'<div class ="add-btn">'
									+'<button class="btn " id="saveBtn"  type="button"><i class="icon icon-save"></i>保存</button>'
									+'<button class="btn " type="button"><i class="icon icon-undo"></i>重置</button>'
									+'</div>'
								+'</div>'
							+'</form>';
					
					return html;
				}
				return "";
		};
		
		var _formart= function(n){
			if(n==1){
				return"一级";
				
			}else if(n==2){
				return"二级";
				
			}else if(n==3){
				return"三级";
				
			}else if(n==4){
				return"四级";
				
			}else if(n==5){
				return"五级";
				
			}else if(n==6){
				return"六级";
				
			}else{
				return"--";
			}
		};
	return {
		init : function() {
			
		},
		initSelect : function(n){
			return _initSelect(n);
		},
		getAddRoleHtml: function(){
			return _html;
		}
	}
}();