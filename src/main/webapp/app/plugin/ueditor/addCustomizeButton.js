UE.registerUI('发表评论',function(editor,uiName){
    var btn = new UE.ui.Button({
        name:uiName,
        title:uiName,
        cssRules :'background-position: -480px -20px;',
        onclick:function () {
          console.log("dd");
        }
    });

    editor.addListener('selectionchange', function () {
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
}/*index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮*/);