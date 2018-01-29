/**
 * 代码生成
 * @author:WangMingM
 * @date:2017.11.22
 */
$(function() {
	CodeTool.init();
});
var CodeTool = function() {
	'use strict';
	var _tables = null;
	// ==已选的表名==//
	var _selectTables = null;
	// ==已经选的tab==//
	var _alreadySeleTab = [];
	// ==当前选中的tab==//
	var _currTab = null;

	//==查询数据库表信心==//
	var _connDataBase = function() {
		var html = "";
		var url = basePath + "/code/create_code.wmctl";
		AjaxRequest.asyncAjaxPost(url, null, function(result) {
			if (result.success = "true") {
				$("#databset_tables").empty();
				_tables = result.data;
				for (var i = 0; i < _tables.length; i++) {
					html += '<li><a href="javascript:CodeTool.selectTable(\''
							+ _tables[i].tableName
							+ '\');"><i class="icon icon-th"></i> '
							+ _tables[i].tableName + '</a></li>';
				}
				$("#databset_tables").append(html);
			}
		});

	};
	//==选择数据表==//
	var _selectTable = function(tableName) {
		_selectTables = tableName;
		_alreadySeleTab = [];
		_generatCode(tableName, _currTab);
	};
	//==生成代码==//
	var _generatCode = function(tableName, codeType) {
		var type = codeType ? (codeType) : "MapperXml";
		_alreadySeleTab.push(type);
		if (_tables) {
			var url = basePath + "/code/generat_code.wmctl";
			var data = null;
			for (var i = 0; i < _tables.length; i++) {
				if (tableName && _tables[i].tableName == tableName) {
					data = _tables[i];
				}
			}
			if (data) {
				data.codeType = type;
				AjaxRequest.asyncAjaxPost(url, data, function(result) {
					if (result.success = "true") {
						$("#" + type).empty();
						$("#" + type).append(result.data);
						$('pre code').each(function(i, block) {
							hljs.highlightBlock(block);
						});
					}
				});
			} else {
				alert("请选择数据表！");
			}

		} else {
			alert("error!!!");
			return false;
		}

	};
	//==改变代码风格==//
	var _changeStyle = function() {
		var styleArr = ["agate.css","androidstudio.css","arduino-light.css","arta.css","ascetic.css","atelier-cave-dark.css","atelier-cave-light.css","atelier-dune-dark.css","atelier-dune-light.css","atelier-estuary-dark.css","atelier-estuary-light.css","atelier-forest-dark.css","atelier-forest-light.css","atelier-heath-dark.css","atelier-heath-light.css","atelier-lakeside-dark.css","atelier-lakeside-light.css","atelier-plateau-dark.css","atelier-plateau-light.css","atelier-savanna-dark.css","atelier-savanna-light.css","atelier-seaside-dark.css","atelier-seaside-light.css","atelier-sulphurpool-dark.css","atelier-sulphurpool-light.css","brown-paper.css","codepen-embed.css","color-brewer.css","dark.css","darkula.css","default.css","docco.css","dracula.css","far.css","foundation.css","github.css","github-gist.css","googlecode.css","grayscale.css","gruvbox-dark.css","gruvbox-light.css","hopscotch.css","hybrid.css","idea.css","ir-black.css","kimbie.dark.css","kimbie.light.css","magula.css","mono-blue.css","monokai.css","monokai-sublime.css","obsidian.css","paraiso-dark.css","paraiso-light.css","pojoaque.css","purebasic.css","qtcreator_dark.css","qtcreator_light.css","railscasts.css","rainbow.css","school-book.css","solarized-dark.css","solarized-light.css","sunburst.css","tomorrow.css","tomorrow-night.css","tomorrow-night-blue.css","tomorrow-night-bright.css","tomorrow-night-eighties.css","vs.css","xcode.css","xt256.css","zenburn.css"];  
		var selectHtml = [];
		selectHtml.push('<select id="changeStyle">');
		for ( var i in styleArr) {
			var OptionValue = styleArr[i];
			selectHtml.push('<option value="' + OptionValue + '" >'
					+ OptionValue + '</option>');
		}
		selectHtml.push('</select>');
		var selectHtmlString = selectHtml.join("");
		document.getElementById('changeStyleSelect').innerHTML = selectHtmlString;
		var obj = document.getElementById('changeStyle');
		obj.addEventListener("change", function(event) {
			var value = this.options[this.options.selectedIndex].value;
			var l = document.createElement('link');
			l.setAttribute('href', basePath + '/app/plugin/highlight/styles/'+ value);
			l.setAttribute('rel', 'stylesheet');
			document.head.appendChild(l);
		});

	};
	
	var _btnsListener = function(){
		console.log(999);
		$('.btn-copy').click(function(e) {
			var code = $(this).data("codeid");
			console.log($("#"+code).val());
		});
 
		
	};
	//==选择tab页==//
	var _changeTab = function() {
		$('[data-tab]').on('shown.zui.tab', function(e) {
			_currTab = e.target.innerText;
			var fla = true;
			for (var i = 0; i < _alreadySeleTab.length; i++) {
				if (_alreadySeleTab[i] == _currTab) {
					fla = false;
				}
			}
			if (fla) {
				_generatCode(_selectTables, _currTab);
			}
		});
	};
	return {
		init : function() {
			_connDataBase();
			hljs.initHighlightingOnLoad();
			_changeStyle();
			_changeTab();
//			_btnsListener();
		},
		selectTable : function(tableName) {
			_selectTable(tableName);
		}
	}
}();