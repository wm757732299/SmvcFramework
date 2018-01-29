/* ========================================================================
 * @author:WangMingM
 * ========================================================================
 *  针对zui.datatable组件 封装数据模型 依赖jQuery       2017.10.31
 * ======================================================================== */
'use strict';
var DataModel = function() {

	/* 对record要求： 每行数据必须有唯一标识"id"*/
	var _buildDataModel = function(cols, record,rowSet) {
		var obj = new Object();
		var rows = [];
		$.each(record, function(index, value) {
			var rowId = value["id"];
			if(!rowId){console.error('Each line of data must have a unique identifier ID');return false;}
			
			var item = new Object();
			if(rowSet && typeof rowSet == "object" ){
				for ( var property in rowSet) {
					var p = property;
					item[p] = rowSet[p];
				}
			}
			var data = [];
			$.each(cols, function(i, v) {
				var colname = v["colName"];

				if (colname == undefined || colname == '') {
					data.push('');
				} else {
					var formatter = v["formatter"];
					if(formatter){
						var str = "";
						try {
							for ( var index in formatter) {
								var fname = formatter[index].name;
								var fn = formatter[index];
								if (fname) {
									str += fn(value[fname],rowId) ? fn(value[fname],rowId):'';
								} else {
									str += fn(value[colname]) ? fn(value[colname]) : '';
								}
							}
						} catch (e) {
							str = "";
					//		console.error(e);
						}
						data.push(str);
					}else{
						data.push(value[colname] == null ? '' : value[colname]);
					}
				}
			});
			item["id"] = rowId;
			item["checked"] = false;
			item["data"] = data;
			rows.push(item);
		});
		obj.cols = cols;
		obj.rows = rows;
		return obj;
	};
	return {
		init : function() {

		},
		buildDataModel : function(cols, record,rowSet) {
			return _buildDataModel(cols, record,rowSet);
		}
	}
}();