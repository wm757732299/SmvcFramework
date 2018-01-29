/**
 * 左侧导航
 * @author:WangMingM
 * @date:2017.10
 */
var nthTabs = $("#editor-tabs").nthTabs();
$(function() {

	InitMenu.init();
	InitMenu.initMenu();
	Home.init();

});


var InitMenu = function() {
	'use strict';
	var _initMenu = function() {
		var menudata = [ {
			id : 1,
			text : '首页',
			url : '#dashboard',
			icon : 'icon-dashboard',
			active : true,
			children : []
		} ];
		var menu = $('#menu').navmenu();
		var url = basePath +"/menu/init_menu.wmctl";
		AjaxRequest.asyncAjax(url, null, function(result) {
			if (result.success == "true") {
				var json = result.data;
				menu.SetData(json);
			} else {
				menu.SetData(menudata);
			}
		});
	};
	var _initTab = function() {
		nthTabs.addTab({
			id : 'ONE-LEVEL-HOME-PAGE',
			title : '首页',
			content : '/main/home_index.wmctl',
			active : true,
			allowClose : false,
		});
	};

	return {
		init : function() {
			_initTab();
		},
		initMenu : function() {
			_initMenu();
		}
	}
}();

/**
 * 菜单监听事件
 */
var menuListener = function() {
	'use strict';
	var _addTab = function(a, b, c) {

		var tablist = nthTabs.getTabList();
		var fal = 0;
		$.each(tablist, function(index, value) {
			if (value.id == "#" + a) {
				nthTabs.setActTab(a);
				fal = 1;
				return;
			}
		});
		if (fal == 0) {
			var j = {
				id : a,
				title : c,
				content : b
			};
			nthTabs.addTab(j);
			nthTabs.setActTab(a);
		}

	};

	return {
		init : function() {

		},
		addTab : function(a, b, c) {
			_addTab(a, b, c);
		}
	};
}();

var Home = function() {
	'use strict';
	var _changeTheme = function(themestr) {
		var theme = document.getElementById("link_theme");
		if (theme) {
			theme.setAttribute("href", basePath+"/app/plugin/zui1.7.0/dist/css/"+themestr);
		}

	};

	return {
		init : function() {
			if(loginerAction){
				PermissionBtn.init(loginerAction);
			}
		},
		changeTheme : function(themestr) {
			_changeTheme(themestr);
		}
	}
}();
