/**
 * 
 */
'use strict';
$(document).ready(function() {

	getComt();
	
					$("div").on("click", ".comt-reply", function(e) {
						if (this === e.target) {
							var ckEle=$(this);//被点击jq对象
							
							var cId = ckEle.data("cid");
							var rId = ckEle.data("rid");
							ckEle.removeClass("comt-reply");
							ckEle.addClass("comt-cancel-reply");
							ckEle.text("取消回复");
							if (rId) {
								reply(rId, cId, ckEle);//回复的回复
							} else {
								reply(cId, null, ckEle);//评论的回复
							}

						}
						// 阻止事件冒泡到父元素
						e.stopPropagation();
					});
					$("div").on("click", ".comt-cancel-reply", function(e) {
						if (this === e.target) {
							var ckEle=$(this);//被点击jq对象
							
							var cId = ckEle.data("cid");
							var rId = ckEle.data("rid");
							ckEle.removeClass("comt-cancel-reply");
							ckEle.addClass("comt-reply");
							ckEle.text("回复");
							if (rId) {
								cancelReply(rId, ckEle);
							} else {
								cancelReply(cId, ckEle);
							}

						}
						// 阻止事件冒泡到父元素
						e.stopPropagation();
					});

					$("div").on("click", ".icon-comment-alt", function(e) {
						if (this === e.target) {
							var ckEle=$(this);//被点击jq对象
							
							var cId = $(this).data("cid");

							codeEff(1, function() {
								console.log($(this).data("cid"));
							});

						}
						// 阻止事件冒泡到父元素
						e.stopPropagation();
					});

					$("div").on("click",".thumbs-up-btn .icon-thumbs-o-up",function(e) {
										if (this === e.target) {
											var ckEle=$(this);//被点击jq对象
											
											var cId = ckEle.data("cid");
											//处理thumbs-down-btn
										    var nextNode = ckEle.parent(".thumbs-up-btn").next();
										    nextNode.removeClass("thumbs-down-btn");
										    nextNode.addClass("thumbs-btn-disable");
										    nextNode.addClass("disabled");
										    var f = cacheData(cId,"video_comt_thumbs_up_localstorage",true);
											if (f) {
												thumbsUp(cId);
												var span = $(this.children[0]);
												var voteNum = span.text();
												voteNum = Number(voteNum) + 1;
												span.text(voteNum);
											}
											ckEle.removeClass("icon-thumbs-o-up");
											ckEle.addClass("icon-thumbs-up");
										}
										e.stopPropagation();
									});
					$("div").on("click",".thumbs-up-btn .icon-thumbs-up",function(e) {
										if (this === e.target) {
											var ckEle=$(this);//被点击jq对象
											
											var cId = ckEle.data("cid");
											var f = cacheData(cId,"video_comt_thumbs_up_localstorage",false);
											ckEle.removeClass("icon-thumbs-up");
											ckEle.addClass("icon-thumbs-o-up");
											var span = $(this.children[0]);
											var voteNum = span.text();
											voteNum = Number(voteNum) - 1;
											span.text(voteNum);
											//处理thumbs-down-btn
											var nextNode = ckEle.parent(".thumbs-up-btn").next();
											nextNode.removeClass("thumbs-btn-disable");
										    nextNode.removeClass("disabled");
										    nextNode.addClass("thumbs-down-btn");
											
											cancelVote(cId);
										}
										e.stopPropagation();
									});

					$("div").on("click",".thumbs-down-btn .icon-thumbs-o-down",function(e) {
										if (this === e.target) {
											var ckEle=$(this);//被点击jq对象
											
											var cId = ckEle.data("cid");
											//处理thumbs-up-btn
											var nextNode = ckEle.parent(".thumbs-down-btn").prev();
											nextNode.addClass("thumbs-btn-disable");
										    nextNode.addClass("disabled");
										    nextNode.removeClass("thumbs-up-btn");
										    
											var f = cacheData(cId,"video_comt_thumbs_down_localstorage",true);
											if (f) {
												var span = $(this.children[0]);
												var voteNum = span.text();
												voteNum = Number(voteNum) + 1;
												span.text(voteNum);
												thumbsDown(cId);
											}
											ckEle.removeClass("icon-thumbs-o-down");
											ckEle.addClass("icon-thumbs-down");
										}
										// 阻止事件冒泡到父元素
										e.stopPropagation();
									});

					$("div").on("click",".thumbs-down-btn .icon-thumbs-down",function(e) {
										if (this === e.target) {
											var ckEle=$(this);//被点击jq对象
											
											var cId = ckEle.data("cid");
											var f = cacheData(cId,"video_comt_thumbs_down_localstorage",false);
											var span = $(this.children[0]);
											var voteNum = span.text();
											voteNum = Number(voteNum) - 1;
											span.text(voteNum);
											ckEle.removeClass("icon-thumbs-down");
											ckEle.addClass("icon-thumbs-o-down");
											
											//处理thumbs-up-btn
											var nextNode = ckEle.parent(".thumbs-down-btn").prev();
											nextNode.removeClass("thumbs-btn-disable");
										    nextNode.removeClass("disabled");
										    nextNode.addClass("thumbs-up-btn");
											
											cancelVote(cId);
										}
										e.stopPropagation();
									});

					$("div").on("click", ".icon-ellipsis-h", function(e) {

						clearCache();
					});
				});

// 方法执行效率测试
function codeEff(i, fn, para) {
	var start = new Date().getTime();// 起始时间
	for (var int = 0; int < i; int++) {
		fn(para);
	}
	var end = new Date().getTime();// 接受时间
	console.log("=======代码执行 " + i + "次耗时:" + (end - start) + "毫秒=======");
}

function clearCache() {
	window.localStorage.clear();
}

// 缓存管理 flag:true点赞点踩，false取消赞、踩
// return: false已操作过，true未操作过
function cacheData(cId, sign, flag) {
	// sign : video_comt_thumbs_up_localstorage
	// sign : video_comt_thumbs_down_localstorage
	var myLocalStorage = window.localStorage;
	var localVoteStr = myLocalStorage.getItem(sign);
	var localVoteArr = null;
	var f = true;
	if (localVoteStr) {
		localVoteArr = localVoteStr.split(",");
		if (localVoteArr.length > 100) {
			localVoteArr.shift();
		}
		var index = localVoteArr.lastIndexOf(cId);
		if (index >= 0) {
			f = false;
			if (!flag) {
				localVoteArr.splice(index, 1);
			}
			// else {
			// localVoteArr.push(cId);
			// }
			localVoteStr = localVoteArr.join(",");
			myLocalStorage.setItem(sign, localVoteStr);
		} else {
			if (flag) {
				localVoteArr.push(cId);
			}
			localVoteStr = localVoteArr.join(",");
			myLocalStorage.setItem(sign, localVoteStr);
		}
	} else {
		localVoteArr = [];
		if (flag) {
			localVoteArr.push(cId);
			localVoteStr = localVoteArr.join(",");
			myLocalStorage.setItem(sign, localVoteStr);
		}
	}
	return f;
}

function getComt() {
	var data = {};
	$.ajax({
		type : 'post',
		url : basePath + "/videoComment/web/get_comt.wmctl",
		data : data,
		async : false,
		success : function(result) {
			if (result.success == "true") {
				var d = result.data;
				var h = buildComtModel(d);
				$("#video_comment").append(h);
			} else {
				alert("请登录");
			}
		}
	});
	// $(".icon-thumbs-o-down").click(function(e) {
	//	     
	// console.log(3333);
	// // e.stopPropagation();
	// });
}

function comtHtml(c) {
	var html = '';
	if (c) {
		var cid = c.id;
		html += '<hr style="margin-top: 8px; margin-bottom: 8px;"><a href="###" class="avatar"><img src="'
				+ basePath
				+ '/app/images/deft-head.ico"></a>'
				+ '<div class="content">'
				+ '<div class="pull-right text-muted">'
				+ moment(c.createTime).format("YYYY-MM-DD HH:mm")
				+ '</div>'
				+ '	<div>'
				+ '	<a href="###"><strong>'
				+ c.comtUname
				+ '</strong></a>'
				+ '	</div>'
				+ '	<div class="text">'
				+ c.content
				+ '</div>'
				+ '	<div class="actions">'
				+ '	<a class="comt-reply" data-cid='
				+ cid
				+ ' data-userid='
				+ c.fromUid
				+ ' data-nme='
				+ c.comtUname
				+ ' data-pic='
				+ c.comtUpic
				+ ' href="javascript:;">回复</a>'
				+ '<div class="pull-right text-muted"><a class="reply-list-btn" href="javascript:;"><i class="icon icon-comment-alt" data-cid='
				+ cid
				+ '>评论</i></a><a class="thumbs-up-btn" href="javascript:;"><i class="icon icon-thumbs-o-up" data-cid='
				+ cid
				+ '>赞<span class="thumbs-up-count">'
				+ c.likeCount
				+ '</span></i></a><a class="thumbs-down-btn" href="javascript:;"><i class="icon icon-thumbs-o-down" data-cid='
				+ cid
				+ '>踩<span class="thumbs-down-count">'
				+ c.dislikeCount
				+ '</span></i></a><a href="javascript:;"><i class="icon icon-ellipsis-h"></i></a></div>'
				+ '	</div>'
				+ '<div id="'
				+ cid
				+ '_div'
				+ '"></div>'
				+ '</div>';
	}
	return html;
}
function replyHtml(r, cId) {
	var html = '';
	if (r) {
		html += '<div class="comment reply">' + '<a href="###" class="avatar">'
				+ '<img style="    height: 30px; width: 30px;" src="'
				+ basePath
				+ '/app/images/deft-head.ico">'
				+ '</a>'
				+ '<div class="content">'
				+ '<div class="pull-right text-muted">'
				+ moment(r.createTime).format("YYYY-MM-DD HH:mm")
				+ '</div>'
				+ '<div>'
				+ '<a href="###"><strong>'
				+ r.comtUname
				+'</strong></a> <span class="text-muted">回复</span> <a href="###">'
				+ r.repliedUname
				+'</a>'
				+ '</div>'
				+ '<div class="text">'
				+ r.content
				+ '</div>'
				+ '<div class="actions">'
				+ '	<a class="comt-reply" data-rid='
				+ r.id
				+ ' data-cid='
				+ cId
				+ ' data-userid='
				+ r.replyUid
				+ ' data-nme='
				+ r.comtUname
				+ ' data-pic='
				+ r.comtUpic
				+ ' href="javascript:;">回复</a>'
				+ '</div>' + '	</div>'

				+ '<div id="' + r.id + '_div' + '"></div>'

				+ '	</div>';
	}

	return html;
}
function buildComtModel(data) {
	var commentsList = '';
	if (data) {
		for (var int = 0; int < data.length; int++) {
			var c = data[int];
			commentsList += comtHtml(c);
			var replys = c.replyList;
			var replyList = '<div id="' + c.id
					+ '_reply_list" class="comments-list">';
			if (replys && replys.length > 0) {
				for (var i = 0; i < replys.length; i++) {
					var re = replys[i];
					replyList += replyHtml(re, c.id);
				}
			}
			replyList += '</div>';
			commentsList += replyList;
		}
	}
	return commentsList;
}

function comment() {
	// var url=basePath+"/web/checklogin.wmctl";
	// $.ajax({
	// type : 'post',
	// url : url,
	// data : null,
	// async : false,
	// success : function(result){
	// if(result.success=="true"){
	//				
	// }else{
	// alert("请登录");
	// }
	// }
	// });

	var content = editor.getContent();
	var data = {
		topicId : videoId,
		fromUid : "2e7a8ec1-c39b-11e7-9786-d017c298b1bf",//评论人的id:后台自动获得此处不用填
		replyUid : "",
		content : content,
		replyGroup : "",
		vcType : "PL"
	}
	$.ajax({
		type : 'post',
		url : basePath + "/videoComment/save_comt.wmctl",
		data : data,
		async : false,
		success : function(result) {
			if (result.success == "true") {
				var c = result.data;
				var cc = comtHtml(c);
				cc += '<div id="' + c.id
						+ '_reply_list" class="comments-list"></div>';
				$("#video_comment").prepend(cc);
				editor.execCommand('cleardoc');
			} else {
				alert("请登录");
			}
		}
	});

}

function reply(cId, comtId, afalg) {
	var ume = cId + "_editor";
	var edi = '<div><script type="text/plain" id="' + ume
			+ '" style="width: 100%; height: 60px;"></script></div>';
	$("#" + cId + '_div').append(edi);

	UE.registerUI('确认回复', function(editor, uiName) {
		var btn = new UE.ui.Button({
			name : uiName,
			title : uiName,
			cssRules : 'background-position: -480px -20px;',
			onclick : function() {
				submitReply(cId, comtId, afalg);
				cancelReply(cId, afalg);
			}
		});

		editor.addListener('selectionchange', function() {
			var state = editor.queryCommandState(uiName);
			if (state == -1) {
				btn.setDisabled(true);
				btn.setChecked(false);
			} else {
				btn.setDisabled(false);
				btn.setChecked(state);
			}
		});

		return btn;
	}/*
		 * index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId
		 * 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮
		 */);

	var editor = UE.getEditor(ume, {
		toolbars : [ [ 'removeformat', 'selectall', 'cleardoc', 'emotion'
		// ,'fullscreen'
		] ],
		wordCount : false
	});
}
function cancelReply(cId, afalg) {
	afalg.removeClass("comt-cancel-reply");
	afalg.addClass("comt-reply");
	afalg.text("回复");

	var ume = cId + "_editor";
	UE.getEditor(ume).destroy();
	$("#" + cId + "_div").empty();
}
function submitReply(cId, comtId,afalg) {
	var ume = cId + "_editor";
	var userid = afalg.data("userid");
	var username = afalg.data("nme");
	var pic=afalg.data("pic");
	var content = UE.getEditor(ume).getContent();
	var data = {
		//id : comtId ? comtId : cId,
		topicId : videoId,
		fromUid : userid,//被回复人的id
		replyUid : "11ea8ae5-c44c-11e7-a1be-d017c298b1bf",//回复人的id:后台自动获得此处不用填
		content : content,
		replyGroup : comtId ? comtId : cId,//评论id
		replyedId : cId,
		repliedUname : username,
		repliedUpic : pic,
		vcType : "HF"
	};

	$.ajax({
		type : 'post',
		url : basePath + "/videoComment/save_comt.wmctl",
		data : data,
		async : false,
		success : function(result) {
			if (result.success == "true") {
				var re = result.data;
				var rep = (comtId ? comtId : cId) + '_reply_list';
				var repHtml = replyHtml(re, comtId ? comtId : cId);
				$("#" + rep).prepend(repHtml);
				// if(comtId){
				// cancelReply(cId);
				// }
			} else {
				alert("请登录");
				return false;
			}
		}
	});
}

// 点赞
function thumbsUp(cId) {
	$.ajax({
		type : 'get',
		url : basePath + "/videoComment/vote.wmctl",
		data : {
			cId : cId,
			vote : "thumbs_up"
		},
		async : false,
		success : function(result) {
			if (result.success == "true") {

				// if(this === e.target){
				// this.style.backgroundColor = "#00f";
				// }

			} else {
				alert("请登录");
			}
		}
	});
}
// 点踩
function thumbsDown(cId) {
	$.ajax({
		type : 'get',
		url : basePath + "/videoComment/vote.wmctl",
		data : {
			cId : cId,
			vote : "thumbs_down"
		},
		async : false,
		success : function(result) {
			if (result.success == "true") {

			} else {
				alert("请登录");
			}
		}
	});
}

// 取消点赞或点踩
function cancelVote(cId) {
	$.ajax({
		type : 'get',
		url : basePath + "/videoComment/vote.wmctl",
		data : {
			cId : cId,
			vote : "thumbs_up_cancel"
		},
		async : false,
		success : function(result) {
			if (result.success == "true") {

			} else {
				alert("请登录");
			}
		}
	});
}