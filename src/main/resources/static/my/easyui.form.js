//函数:获取窗口尺寸  
function pageSize() {  
	var winWidth = 0;  
	var winHeight = 0; 
    // 获取窗口宽度  
    if (window.innerWidth)  
        winWidth = window.innerWidth;  
    else if ((document.body) && (document.body.clientWidth))  
        winWidth = document.body.clientWidth-4;  
    // 获取窗口高度  
    if (window.innerHeight)  
        winHeight = window.innerHeight;  
    else if ((document.body) && (document.body.clientHeight))  
        winHeight = document.body.clientHeight-7;     
    // 通过深入Document内部对body进行检测，获取窗口大小  
    if (document.documentElement  && document.documentElement.clientHeight && document.documentElement.clientWidth)  
    {  
        winHeight = document.documentElement.clientHeight;  
        winWidth = document.documentElement.clientWidth;  
    }  
    return {
		width : winWidth,
		height :winHeight
	}
}

//动态设置DataGrid控件的大小
function setDataGridSize(datagridId, delHeight, delWidth) {
	$(function() {
		var $datagrid = $("#" + datagridId);
		if (delHeight || delHeight == 0)
			$datagrid.datagrid('resize', {
				height : pageSize().height - delHeight
			});
		if (delWidth || delWidth == 0)
			$datagrid.datagrid('resize', {
				width : pageSize().width - delWidth
			});

		$(window).resize(function() {
			if (delHeight || delHeight == 0)
				$datagrid.datagrid('resize', {
					height : pageSize().height - delHeight
				});
			if (delWidth || delWidth == 0)
				$datagrid.datagrid('resize', {
					width : pageSize().width - delWidth
				});
		});

	});
}

// 动态设置TreeGrid控件的大小
function setTreeGridSize(datagridId, delHeight, delWidth) {
	var $datagrid = $("#" + datagridId);
	if (delHeight || delHeight == 0)
		$datagrid.treegrid('resize', {
			height : pageSize().height - delHeight
		});
	if (delWidth || delWidth == 0)
		$datagrid.treegrid('resize', {
			width : pageSize().width - delWidth
		});

	$(window).resize(function() {
		if (delHeight || delHeight == 0)
			$datagrid.treegrid('resize', {
				height : pageSize().height - delHeight
			});
		if (delWidth || delWidth == 0)
			$datagrid.treegrid('resize', {
				width : pageSize().width - delWidth
			});
	});
}

//将form表单中的参数值转换为json对象
function getFormJson(formId) {
	var o = {};
	var a = $("#" + formId).serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
}

function search_load(dateGridId, params, gridType) {
	if (gridType == "treegrid") {
		// 如果是string类型 表示为查询条件所在的form表单的id 这种情况下
		// 会利用getFormJson(formId)方法将这些参数封装成json对象
		if (typeof (params) == "string")
			$('#' + dateGridId).treegrid('load', getFormJson(params));
		else if (typeof (params) == "undefined" || params=="" || params== null) // 表示不传递参数
			$('#' + dateGridId).treegrid('load');
		else
			// 表示params本身就是参数的json对象
			$('#' + dateGridId).treegrid('load', params);
	} else {
		// 如果是string类型 表示为查询条件所在的form表单的id 这种情况下
		// 会利用getFormJson(formId)方法将这些参数封装成json对象
		if (typeof (params) == "string")
			$('#' + dateGridId).datagrid('load', getFormJson(params));
		else if (typeof (params) == "undefined") // 表示不传递参数
			$('#' + dateGridId).datagrid('load');
		else
			// 表示params本身就是参数的json对象
			$('#' + dateGridId).datagrid('load', params);
	}
}

/**
 * 表单提交方法
 * 并且Controller中统一返回com.domor.model.Result
 * @param formId 需要提交的表单ID
 * @param dialog 弹出框的ID或者jQuery对象（可以为空）
 * @param gridId 需要重载数据的easyui datagrid ID（可以为空）
 * @param gridFormId easyui datagrid 重载时的过滤参数的表单ID（可以为空）
 * @param subBtn 当前按钮的ID或对象（可以为空）
 * @param gridType grid 类型：treegrid or 其它（可以为空）
 */
function submitForm(formId, dialog, gridId, gridFormId,subBtn,gridType) {
	var form = $("#" + formId);
	var action = form.attr("action");
	if (typeof(subBtn)=='string') {
		subBtn = $("#"+subBtn);
	}else{
		subBtn = $(subBtn);
	}
	
	if (typeof(dialog)=='string') {
		dialog = $("#"+dialog);
	}else{
		dialog = $(dialog);
	}
	 
	if (form.form("validate")) {
		if(subBtn) subBtn.linkbutton("disable");
		$.post(action, getFormJson(formId), function(data) {
            if(data.status){
            	$.messager.alert("温馨提示", "保存成功!");
            	if(dialog) dialog.dialog('close');
            	if(gridId) search_load(gridId, gridFormId, gridType);
            }else{
            	$.messager.alert("温馨提示", data.message);
			}
		},'json');
		if(subBtn) subBtn.linkbutton("enable");
	}
}

/**
 * 表单提交方法
 * 并且Controller中统一返回com.domor.model.Result
 * @param formId 需要提交的表单ID
 * @param dialog1 弹出框的ID或者jQuery对象（可以为空）
 * @param dialog2 弹出框的ID或者jQuery对象（可以为空）
 * @param gridId 需要重载数据的easyui datagrid ID（可以为空）
 * @param gridFormId easyui datagrid 重载时的过滤参数的表单ID（可以为空）
 * @param subBtn 当前按钮的ID或对象（可以为空）
 * @param gridType grid 类型：treegrid or 其它（可以为空）
 */
function submitForm2(formId, dialog1, dialog2, gridId, gridFormId,subBtn,gridType) {
	var form = $("#" + formId);
	var action = form.attr("action");
	if (typeof(subBtn)=='string') {
		subBtn = $("#"+subBtn);
	}else{
		subBtn = $(subBtn);
	}
	
	if (typeof(dialog1)=='string') {
		dialog1 = $("#"+dialog1);
	}else{
		dialog1 = $(dialog1);
	}
	if (typeof(dialog2)=='string') {
		dialog2 = $("#"+dialog2);
	}else{
		dialog2 = $(dialog2);
	}
	 
	if (form.form("validate")) {
		if(subBtn) subBtn.linkbutton("disable");
		$.post(action, getFormJson(formId), function(data) {
            if(data.status){
            	$.messager.alert("温馨提示", "保存成功!");
            	if(dialog1) dialog1.dialog('close');
            	if(dialog2) dialog2.dialog('close');
            	if(gridId) search_load(gridId, gridFormId, gridType);
            }else{
            	$.messager.alert("温馨提示", data.message);
			}
		},'json');
		if(subBtn) subBtn.linkbutton("enable");
	}
}

/**
 * 表格工具格按钮控制（datagrid treegrid）
 * @param gridId
 * @param gridType
 */
function actionButtonCtr(gridId, gridType) {
	var toolbar;
	if (gridType == "treegrid")
		toolbar = $('#' + gridId).treegrid('options').toolbar;
	else
		toolbar = $('#' + gridId).datagrid('options').toolbar;

	$.getJSON('/getActionsByUser', {}, function(data) {
		for ( var i = 0; i < toolbar.length; i++) {
			var tool = toolbar[i];
			var hasAction = false;
			for ( var j = 0; j < data.length; j++) {
				var action = data[j];
				if (action.menuUrl == tool.url && action.menuType == 2) {
					hasAction = true;
					break;
				}
			}

			if (!hasAction) {
			  if(tool.text) 
		         $('#'+gridId).datagrid("removeToolbarItem",tool.text);
			}
		}
	});
}

/**
 * 窗口按钮控制（dialog）
 * @param btns
 * @param status
 * @author liyy
 */
function dialogButtonCtr(dialogId, btns, status){
	return new Promise(function(resolve) {
		var buttons = new Array();
		$.getJSON('/getButtonAuth', {status: status}, function(data) {
			for ( var i = 0; i < btns.length; i++) {
				var btn = btns[i];
				for ( var j = 0; j < data.length; j++) {
					var action = data[j];
					if (action.menuUrl == btn.id && action.menuType == 2) {
						buttons.push(btn);
						break;
					}
				}
			}
			var cbtn = {
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() { $("#"+dialogId).dialog('close'); }
			}
			buttons.push(cbtn);
			resolve(buttons);
		});
	})
}
