function frameResize() {
    var wh =  document.documentElement.clientHeight;
    document.body.style.height = (wh-30)+'px';
}

//清空表单数据
function resetForm(id){
	$("#"+id).form('clear');
}

//数字转汉字
function transfer(input) {
	input = input+"";
	var danwei = Array("", "十", "百", "千", "万", "十", "百", "千", "亿");
	var l = input.length;
	var a = new Array(l);
	var b = new Array(l);
	var result = "";
	for (var i = 0; i < l; i++) {
		a[i] = input.substr(i, 1);
		b[i] = getchinese(a[i]);
		result += b[i] + danwei[l - i - 1];
	}
	return result;
}

function getchinese(input) {
	if (input == "0")
		return "零";
	else if (input == "1")
		return "一";
	else if (input == "2")
		return "二";
	else if (input == "3")
		return "三";
	else if (input == "4")
		return "四";
	else if (input == "5")
		return "五";
	else if (input == "6")
		return "六";
	else if (input == "7")
		return "七";
	else if (input == "8")
		return "八";
	else if (input == "9")
		return "九";
}
 
//把datebox设置成月份选择
function setDateBox(id){
	$('#'+id).datebox({
        onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
            span.trigger('click'); //触发click事件弹出月份层
            if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                tds = p.find('div.calendar-menu-month-inner td');
                tds.click(function (e) {
                    e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                    var year = /\d{4}/.exec(span.html())[0]//得到年份
                    , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                    $('#'+id).datebox('hidePanel')//隐藏日期对象
                    .datebox('setValue', year + '-' + month); //设置日期的值
                });
            }, 0);
            yearIpt.unbind();//解绑年份输入框中任何事件
        },
        parser: function (s) {
            if (!s) return new Date();
            var arr = s.split('-');
            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
        },
        formatter: function (d) { return d.getFullYear() + '-' + ((d.getMonth() + 1)>=10?(d.getMonth() + 1):('0'+(d.getMonth() + 1))); }
    });
	var p = $('#'+id).datebox('panel'), //日期选择对象
    tds = false, //日期选择对象中月份
    yearIpt = p.find('input.calendar-menu-year'),//年份输入框
    span = p.find('span.calendar-text'); //显示月份层的触发控件
}

