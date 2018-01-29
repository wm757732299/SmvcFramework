/**
 * 新增编辑弹框
 */
var MenuAdd = function() {
	'use strict';
	var _html='<form id="IForm">'
					+'<input type="hidden" id="id" name="id" placeholder="帐号" readonly="readonly" >'
					+'<div class=" add-div">'
						+'<div class="input-group">'
						  +'<span class="input-group-addon">名称</span>'
						  +'<input type="text" id ="menuName" name ="menuName" class="form-control" placeholder="名称">'
						+'</div> '
						+'<div class="input-group">'
						  +'<span class="input-group-addon">图标</span>'
						  +'<input type="text" id ="menuIcon" name ="menuIcon" class="form-control" value="icon-node" placeholder="图标">'
						+'</div>'
						+'<div class="input-group">'
						  +'<span class="input-group-addon">地址</span>'
						  +'<input type="text" id ="menuUrl" name ="menuUrl" class="form-control" placeholder="地址">'
						+'</div>'
						+'<div class="input-group">'
						  +'<span class="input-group-addon">类型</span>'
						  +'<select id ="nodeType" name ="nodeType" class="form-control">'
						  +'<option value="">请选择节点类型</option>'
						  +'<option value="0">叶子节点</option>'
						  +'<option value="1">父节点、分支节点</option>'
						 +'</select>'
						+'</div>'
						+'<div class="input-group">'
						  +'<span class="input-group-addon">序号</span>'
						  +'<input type="text" id ="menuSort" name ="menuSort" class="form-control" placeholder="序号">'
						+'</div>'
						 +'<input type="hidden" id ="menuParent" name ="menuParent" class="form-control" placeholder="父节点">'
						+'<div class ="add-btn">'
						+'<button class="btn " id="saveBtn"  type="button"><i class="icon icon-save"></i>保存</button>'
						+'<button class="btn " type="button"><i class="icon icon-undo"></i>取消</button>'
						+'</div>'
					+'</div>'
				+'</form>';

	return {
//		onclick="javascript:MenuHome.addSaveData();"
		getAddMenuHtml : function() {
			return _html;
		}
	}
}();