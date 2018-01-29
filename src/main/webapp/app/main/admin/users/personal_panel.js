/**
 * 个人中心
 * 
 * @author:WangMingM
 * @date:2017.12.07
 */
$(function() {
	PersonalPanel.init();
});

var PersonalPanel = function() {
	'use strict';
	
	
	
	var _eidtPicture =function(){
		
		console.log(33);
		Dialog.dialog({
			showHeader: false,
			height : '600px',
			width : '500px',
			toggle : 'modal',
			moveable : true,
			iframe:basePath+"/sysuser/edit_pic.wmctl"
//			custom :function() {
//				return "";
//			}
		}).show();
		
	};

	// ==个人信息==//
	var _selfInfo = function() {

	};
	// ==修改个人信息==//
	var _updateSelf = function() {

	};

	// ==及时通信==//
	var _talk = function() {

	};

	// ==待办==//
	var _todo = function() {

	};
	// ==平台状况==//
	var _zk = function() {

	};
	// ==统计信息==//
	var _zk = function() {

	};

	return {

		init : function() {
				
				$('#dashboard').dashboard({draggable: true});
				
		},
		eidtPicture:function(){
			_eidtPicture();
		}
	}

}();
function dd(a,b){
	 
	



 
	if(a>b){
		document.getElementById("preview").width=300;
		document.getElementById("preview").height=b/a*300;
	}
	if(a<b){
		document.getElementById("preview").height=300;
		document.getElementById("preview").height=a/b*300;
	}
	if(a==b){
//		document.getElementById("preview").width='auto';
//		document.getElementById("preview").height='auto';
	}
	console.log(a);
	console.log(b);
}
function ddd(){
	$('#preview').remove();
	var im = document.createElement("img");
	im.setAttribute("id","preview"); 
	var c = document.getElementById("canvas");
	c.appendChild(im);
	
    
}



	function cut() {
		$("#imgCutter").imgCutter({
			fixedRatio : true,
			post: basePath+"/sysuser/edit_pic_sub.wmctl",
			before: function(){
				
				var ss = $("#canvas");
				
				console.log(ss);
			}
		});
		
		var f = $("#img-cutter-submit");
		var myImgCutter = $('#imgCutter').data('zui.imgCutter');
		console.log(myImgCutter);
	//	myImgCutter.$btn=f;
		var src = document.getElementById("preview").src;

		//调用 resetImg 方法
		myImgCutter.resetImage(src);
		
		
		 var a = document.getElementById("preview").width;
			
	 		var b = document.getElementById("preview").height;
	 		
	 		console.log(a);
	 		console.log(b);
	  
	}

	function imgPreview(fileDom) {
 		ddd();
		var reader =null;
		if (window.FileReader) {
			  reader = new FileReader();
		} else {
			alert("如需该功能请升级您的设备！");
		}

		var file = fileDom.files[0];
		var imageType = /^image\//;
		if (!imageType.test(file.type)) {
			alert("请选择图片！");
			return;
		}
		//读取完成

		reader.onload = function(e) {
			var img = document.getElementById("preview");
			img.src = e.target.result;
			
//			  width = e.target.naturalWidth;
//			  height = e.target.naturalHeight;
			  
				console.log(e);
				  

		         var a = document.getElementById("preview").width;
				
		 		var b = document.getElementById("preview").height;
		 		
		
		 		
			 cut();
		};
		reader.readAsDataURL(file);
		
		   
		
	}