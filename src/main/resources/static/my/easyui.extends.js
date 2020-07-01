/** 扩展easyui validate验证方法 begin */
$.extend($.fn.validatebox.defaults.rules, {
	minLength: { // 判断最小长度
		validator : function(value, param) {
			return value.length >= param[0];
		},
		message : '最少输入 {0} 个字符。'
	},
	length: {
		validator:function(value,param){
		var len=$.trim(value).length;
			return len>=param[0]&&len<=param[1];
		},
			message:"内容长度介于{0}和{1}之间."
	},
    CHS: {
        validator: function (value, param) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '请输入汉字'
    },
    english: {// 验证英语
		validator : function(value) {
			return /^[A-Za-z]+$/i.test(value);
		},
		message : '请输入英文'
	},
	unnormal: {// 验证是否包含空格和非法字符
		validator : function(value) {
			return /.+/i.test(value);
		},
		message : '输入值不能为空和包含其他非法字符'
	},
    postcode: {
        validator: function (value, param) {
            return /^[1-9]\d{5}$/.test(value);
        },
        message: '邮政编码不存在'
    },
    QQ: {
        validator: function (value, param) {
            return /^[1-9]\d{4,10}$/.test(value);
        },
        message: 'QQ号码不正确'
    },
    mobile: {
        validator: function (value, param) {
            return /^1[3|4|5|7|8|9]\d{9}$/.test(value);
        },
        message: '手机号码格式不正确(正确格式如：13450774432)'
    },
    loginName: {
        validator: function (value, param) {
            return /^[\u0391-\uFFE5\w]+$/.test(value);
        },
        message: '登录名称只允许汉字、英文字母、数字及下划线。'
    },
    username: {// 验证用户名
		validator : function(value) {
			return /^[a-zA-Z][a-zA-Z0-9_]{2,15}$/i.test(value);
		},
		message : '用户名不合法（字母开头，允许3-16字节，允许字母数字下划线）'
	},
	name: {// 验证姓名，可以是中文或英文
		validator : function(value) {
			return /^[\u0391-\uFFE5]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value);
		},
		message : '请输入姓名'
	},
    safepass: {
        validator: function (value, param) {
            return safePassword(value);
        },
        message: '密码由字母和数字组成，至少6位'
    },
    equalTo: {
        validator: function (value, param) {
            return value == $(param[0]).val();
        },
        message: '两次输入的字符不一至'
    },
    number: {
        validator: function (value, param) {
            return /^\d+$/.test(value);
        },
        message: '请输入数字'
    },
    floatOrInt: {// 验证是否为小数或整数
		validator : function(value) {
			return /^(\d{1,3}(,\d\d\d)*(\.\d{1,3}(,\d\d\d)*)?|\d+(\.\d+))?$/i.test(value);
		},
		message : '请输入数字，并保证格式正确'
	},
	integer: {// 验证整数
		validator : function(value) {
			return /^[+]?[1-9]+\d*$/i.test(value);
		},
		message : '请输入整数'
	},
	strNumber: {
		validator : function(value, param) {
			return /^[-]*[\d,]*$/.test(value) || /^[-]*[\d，]*$/.test(value);
		},
		message : '请输入正确数值字符串，以逗号分隔'
	},
    idcard: {
        validator: function (value, param) {
            return idCard(value);
        },
        message:'身份证号码格式不正确'
    }
});

/* 密码由字母和数字组成，至少6位 */
var safePassword = function (value) {
    return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));
}

var idCard = function (value) {
    if (value.length == 18 && 18 != value.length) return false;
    var number = value.toLowerCase();
    var d, sum = 0, v = '10x98765432', w = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
    var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
    if (re == null || a.indexOf(re[1]) < 0) return false;
    if (re[2].length == 9) {
        number = number.substr(0, 6) + '19' + number.substr(6);
        d = ['19' + re[4], re[5], re[6]].join('-');
    } else d = [re[9], re[10], re[11]].join('-');
    if (!isDateTime.call(d, 'yyyy-MM-dd')) return false;
    for (var i = 0; i < 17; i++) sum += number.charAt(i) * w[i];
    return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));
}

var isDateTime = function (format, reObj) {
    format = format || 'yyyy-MM-dd';
    var input = this, o = {}, d = new Date();
    var f1 = format.split(/[^a-z]+/gi), f2 = input.split(/\D+/g), f3 = format.split(/[a-z]+/gi), f4 = input.split(/\d+/g);
    var len = f1.length, len1 = f3.length;
    if (len != f2.length || len1 != f4.length) return false;
    for (var i = 0; i < len1; i++) if (f3[i] != f4[i]) return false;
    for (var i = 0; i < len; i++) o[f1[i]] = f2[i];
    o.yyyy = s(o.yyyy, o.yy, d.getFullYear(), 9999, 4);
    o.MM = s(o.MM, o.M, d.getMonth() + 1, 12);
    o.dd = s(o.dd, o.d, d.getDate(), 31);
    o.hh = s(o.hh, o.h, d.getHours(), 24);
    o.mm = s(o.mm, o.m, d.getMinutes());
    o.ss = s(o.ss, o.s, d.getSeconds());
    o.ms = s(o.ms, o.ms, d.getMilliseconds(), 999, 3);
    if (o.yyyy + o.MM + o.dd + o.hh + o.mm + o.ss + o.ms < 0) return false;
    if (o.yyyy < 100) o.yyyy += (o.yyyy > 30 ? 1900 : 2000);
    d = new Date(o.yyyy, o.MM - 1, o.dd, o.hh, o.mm, o.ss, o.ms);
    var reVal = d.getFullYear() == o.yyyy && d.getMonth() + 1 == o.MM && d.getDate() == o.dd && d.getHours() == o.hh && d.getMinutes() == o.mm && d.getSeconds() == o.ss && d.getMilliseconds() == o.ms;
    return reVal && reObj ? d : reVal;
    function s(s1, s2, s3, s4, s5) {
        s4 = s4 || 60, s5 = s5 || 2;
        var reVal = s3;
        if (s1 != undefined && s1 != '' || !isNaN(s1)) reVal = s1 * 1;
        if (s2 != undefined && s2 != '' && !isNaN(s2)) reVal = s2 * 1;
        return (reVal == s1 && s1.length != s5 || reVal > s4) ? -10000 : reVal;
    }
};
/** 扩展easyui validate验证方法 end */


/** 扩展easyui datagrid begin */
$.extend($.fn.datagrid.methods, {  
	addToolbarItem: function(jq, items){  
      return jq.each(function(){  
          var toolbar = $(this).parent().prev("div.datagrid-toolbar");
          for(var i = 0;i<items.length;i++){
              var item = items[i];
              if(item === "-"){
                  toolbar.append('<div class="datagrid-btn-separator"></div>');
              }else{
                  var btn=$("<a href=\"javascript:void(0)\"></a>");
                  btn[0].onclick=eval(item.handler||function(){});
                  btn.css("float","left").appendTo(toolbar).linkbutton($.extend({},item,{plain:true}));
              }
          }
          toolbar = null;
      });  
    },
    removeToolbarItem: function(jq, param){  
      return jq.each(function(){  
          var btns = $(this).parent().prev("div.datagrid-toolbar").find("a");
          var cbtn = null;
          if(typeof param == "number"){
              cbtn = btns.eq(param);
          }else if(typeof param == "string"){
              var text = null;                
              btns.each(function(){
                  text = $(this).data().linkbutton.options.text;
                  console.log(text)
                  if(text == param){
                      cbtn = $(this);
                      text = null;
                      return;
                  }
              });
          } 
          if(cbtn){
          	
              var prev = cbtn.prev()[0];
              var next = cbtn.next()[0];
              if(prev && next && prev.nodeName == "DIV" && prev.nodeName == next.nodeName){
                  $(prev).remove();
              }else if(next && next.nodeName == "DIV"){
                  $(next).remove();
              }else if(prev && prev.nodeName == "DIV"){
                  $(prev).remove();
              }
              cbtn.remove();    
              cbtn= null;
          }                        
      });  
    }                 
});

$.extend($.fn.datagrid.methods, {  
	  getRowByFieldValue: function(jq, param){
		  var row;
		   jq.each(function(){  
			 var rows = $(this).datagrid("getRows");
			 for(var i=0;i<rows.length;i++){
				 if(rows[i][param.fieldName]==param.fieldValue){
					 row = rows[i];
					 return false;
				 }
			 }
		  });
		  return row;
	  },
	  selectRowByFieldValue:function(jq, param){
		   return jq.each(function(){  
			 var row =   $(this).datagrid("getRowByFieldValue",param);
			 var rowIndex = $(this).datagrid("getRowIndex",row);	
			 $(this).datagrid("checkRow",rowIndex);
			 
		  });	  
	  },
	  unSelectRowByFieldValue:function(jq, param){
		   return jq.each(function(){  
			 var row =   $(this).datagrid("getRowByFieldValue",param);
			 var rowIndex = $(this).datagrid("getRowIndex",row);	
			 $(this).datagrid("uncheckRow",rowIndex);
			 
		  });	  
	  }
});

$.extend($.fn.datagrid.methods, {
	editCell: function(jq,param){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field){
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
          var ed = $(this).datagrid('getEditor', param);
          if (ed){
              if ($(ed.target).hasClass('textbox-f')){
                  $(ed.target).textbox('textbox').focus();
              } else {
                  $(ed.target).focus();
              }
          }
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	},
  enableCellEditing: function(jq){
      return jq.each(function(){
          var dg = $(this);
          var opts = dg.datagrid('options');
          opts.oldOnClickCell = opts.onClickCell;
          opts.onClickCell = function(index, field){
              if (opts.editIndex != undefined){
                  if (dg.datagrid('validateRow', opts.editIndex)){
                      dg.datagrid('endEdit', opts.editIndex);
                      opts.editIndex = undefined;
                  } else {
                      return;
                  }
              }
              dg.datagrid('selectRow', index).datagrid('editCell', {
                  index: index,
                  field: field
              });
              opts.editIndex = index;
              opts.oldOnClickCell.call(this, index, field);
          }
      });
  },
  disableCellEditing: function(jq){
      return jq.each(function(){
          var dg = $(this);
          var opts = dg.datagrid('options');
          opts.oldOnClickCell = opts.onClickCell;
          opts.onClickCell = function(){};
      });
  }
});

$.extend($.fn.datagrid.methods, {  
	//打开消息提示功能
	doCellTip: function(jq, params){ 
		if(!params){
			params = {};
		}

		function showTip(data, td, e){  
			if ($(td).text() == "")   
				return;  
			data.tooltip.text($(td).text()).css({  
				top: (e.pageY + 10) + 'px',  
				left: (e.pageX + 20) + 'px',  
				'z-index': $.fn.window.defaults.zIndex,  
				display: 'block'  
			});  
		};  
		return jq.each(function(){  
			var grid = $(this);  
			var options = $(this).data('datagrid');  
			if (!options.tooltip) {  
				var panel = grid.datagrid('getPanel').panel('panel');  
				var defaultCls = {  
					'font': 'normal 14px "Microsoft YaHei","Arial","宋体"',
					'border': '1px solid #333',  
					'padding': '2px',  
					'color': '#333',  
					'background': '#f7f5d1',  
					'position': 'absolute',  
					'max-width': '500px',  
					'border-radius' : '4px',  
					'-moz-border-radius' : '4px',  
					'-webkit-border-radius' : '4px',  
					'display': 'none'  
				}  
				var tooltip = $("<div id='celltip'></div>").appendTo('body');  
				tooltip.css($.extend({}, defaultCls, params.cls));  
				options.tooltip = tooltip;  
				panel.find('.datagrid-body').each(function(){  
					var delegateEle = $(this).find('> div.datagrid-body-inner').length ? $(this).find('> div.datagrid-body-inner')[0] : this;  
					$(delegateEle).undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove').delegate('td', {  
						'mouseover': function(e){  
							if (params.delay) {  
								if (options.tipDelayTime)   
									clearTimeout(options.tipDelayTime);  
								var that = this;  
								options.tipDelayTime = setTimeout(function(){  
									showTip(options, that, e);  
								}, params.delay);  
							}  
							else {  
								showTip(options, this, e);  
							}  

						},  
						'mouseout': function(e){  
							if (options.tipDelayTime)   
								clearTimeout(options.tipDelayTime);  
							options.tooltip.css({  
								'display': 'none'  
							});  
						},  
						'mousemove': function(e){  
							var that = this;  
							if (options.tipDelayTime)   
								clearTimeout(options.tipDelayTime);  
							options.tipDelayTime = setTimeout(function(){  
								showTip(options, that, e);  
							}, params.delay);  
						}  
					});  
				});  

			}  

		});  
	},  
	//关闭消息提示功能
	cancelCellTip: function(jq){  
		return jq.each(function(){  
			var data = $(this).data('datagrid');  
			if (data.tooltip) {  
				data.tooltip.remove();  
				data.tooltip = null;  
				var panel = $(this).datagrid('getPanel').panel('panel');  
				panel.find('.datagrid-body').undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove')  
			}  
			if (data.tipDelayTime) {  
				clearTimeout(data.tipDelayTime);  
				data.tipDelayTime = null;  
			}  
		});  
	}  
});
/** 扩展easyui datagrid end */


/**
 * 扩展树表格treegrid级联勾选方法：级联选择
 * @param {Object} target
 * @param {Object} param param包括两个参数: id:勾选的节点ID deepCascade:是否深度级联
 * @return {TypeName}
 */
$.extend($.fn.treegrid.methods, {
	cascadeCheck : function(target, param) {
		var opts = $.data(target[0], "treegrid").options;
		if (opts.singleSelect) {
			return;
		}
		var idField = opts.idField;// 这里的idField其实就是API里方法的id参数
		var status = false;// 用来标记当前节点的状态，true:勾选，false:未勾选
		var selectNodes = $(target).treegrid('getSelections');// 获取当前选中项
		for ( var i = 0; i < selectNodes.length; i++) {
			if (selectNodes[i][idField] == param.id)
				status = true;
		}
		// 级联选择父节点
		selectParent(target[0], param.id, idField, status);
		selectChildren(target[0], param.id, idField, param.deepCascade, status);
		
		//级联选择父节点
		function selectParent(target, id, idField, status) {
			var parent = $(target).treegrid('getParent', id);
			if (parent) {
				var parentId = parent[idField];
				if (status)
					$(target).treegrid('select', parentId);
				else
					$(target).treegrid('unselect', parentId);
				selectParent(target, parentId, idField, status);
			}
		}
		//级联选择子节点
		function selectChildren(target, id, idField, deepCascade, status) {
			// 深度级联时先展开节点
			if (!status && deepCascade)
				$(target).treegrid('expand', id);
			// 根据ID获取下层孩子节点
			var children = $(target).treegrid('getChildren', id);
			for ( var i = 0; i < children.length; i++) {
				var childId = children[i][idField];
				if (status)
					$(target).treegrid('select', childId);
				else
					$(target).treegrid('unselect', childId);
				selectChildren(target, childId, idField, deepCascade, status);// 递归选择子节点
			}
		}
	}
});
/** 扩展树表格级联勾选方法：级联选择 end */

$.extend($.fn.datagrid.defaults.editors, {
	combotree_single : {
		init : function(container, options) {
			var box = $('<input />').appendTo(container);
			box.combotree(options);
			return box;
		},
		getValue : function(target) {
			var t = $(target).combotree('tree', target);
			var n = t.tree('getSelected');
			if (n != null)
				return n.text;
			else
				return $(target).combotree('getValue');
		},
		setValue : function(target, value) {
			if (value) {
				if (value.id) {
					$(target).combotree('setValue', value.id);
				} else {
					$(target).combotree('setValue', value);
				}
			}
		},
		resize : function(target, width) {
			var box = $(target);
			box.combotree('resize', width);
		},
		destroy : function(target) {
			$(target).combotree('destroy');
		}
	}
});

// datagrid的自定义editor控件 弹出dialog
$.extend($.fn.textbox.methods, {
	addClearBtn: function(jq, iconCls){
		return jq.each(function(){
			var t = $(this);
			var opts = t.textbox('options');
			opts.icons = opts.icons || [];
			
			if(t.textbox('getIcon',0).length>0)
				return;
			
			 
			opts.icons.unshift({
				iconCls: iconCls,
				handler: function(e){
					$(e.data.target).textbox('clear').textbox('textbox').focus();
					$(this).css('visibility','hidden');
				}
			});
			t.textbox();
			if (!t.textbox('getText')){
				t.textbox('getIcon',0).css('visibility','hidden');
			}
			t.textbox('textbox').bind('keyup', function(){
				var icon = t.textbox('getIcon',0);
				if ($(this).val()){
					icon.css('visibility','visible');
				} else {
					icon.css('visibility','hidden');
				}
			});
		});
	}
});
