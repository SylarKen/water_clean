//课程类型
function courseTypeFormatter(value){
    if(value==1) return "线路课程";
    if(value==2) return "基地课程";
    if(value==3) return "项目课程";
}

//区域
function areaFormatter(value) {
    if (value==1) return "京津冀研学区";
    if (value==2) return "东北研学区";
    if (value==3) return "黄河中下游研学区";
    if (value==4) return "西北内蒙古研学区";
    if (value==5) return "长江中下游研学区";
    if (value==6) return "华南研学区";
    if (value==7) return "西南研学区";
    if (value==8) return "青藏研学区";
}

//审核状态
function statusFormatter(value){
    var text = "<font color=red>未审核</font>";
    if(value == 1){
        text = "<font color=green>已审核</font>"
    }
    return text;
}

/**
 * 日期格式化
 */
function dateFormatter(e) {
	if (typeof (e) == "string") {// 如果是string或number(毫秒数)类型
		// 表示通过.Net MVC提供的 return Json(result,
		// JsonRequestBehavior.AllowGet);方式返回的/Date(1332919782070)/日期格式
		if (e.indexOf("Date") > 0) {
			/* json格式时间转js时间格式 */
			var value = e.substr(1, e.length - 2);
			var obj = eval('(' + "{Date: new " + value + "}" + ')');
			var date = obj["Date"];
			if (date.getFullYear() < 1900) {
				return "";
			}			
			return date.format("yyyy-MM-dd");
		}	 
		// 表示其他字符串或数字格式日期
		var date = new Date(e);
		return date.format("yyyy-MM-dd");
	} else if (typeof (e) == "number") {
		var date = new Date(e);
		return date.format("yyyy-MM-dd");
	} else if (e) {// 如果是Date类型
		return e.format("yyyy-MM-dd");
	} else {
		return "";
	}
}

function timeFormatter(e) {
	if (typeof (e) == "string") {// 如果是string或number(毫秒数)类型
		// 表示通过.Net MVC提供的 return Json(result,
		// JsonRequestBehavior.AllowGet);方式返回的/Date(1332919782070)/日期格式
		if (e.indexOf("Date") > 0) {
			/* json格式时间转js时间格式 */
			var value = e.substr(1, e.length - 2);
			var obj = eval('(' + "{Date: new " + value + "}" + ')');
			var date = obj["Date"];
			if (date.getFullYear() < 1900) {
				return "";
			}
			return date.format("yyyy-MM-dd hh:mm:ss");
		}
		// 表示其他字符串格式日期
		var date = new Date(e);
		return date.format("yyyy-MM-dd hh:mm:ss");
	} else if (typeof (e) == "number") { // 表示数字格式日期
		var date = new Date(e);
		return date.format("yyyy-MM-dd hh:mm:ss");
	} else if (e) {// 如果是Date类型
		return e.format("yyyy-MM-dd hh:mm:ss");
	} else {
		return "";
	}
}

/**
 * 格式化数值
 * @param value 需要格式化的数值
 * @param precision 小数点后位数
 */
function numberFormatter(value, precision) {
	if(typeof(value) == "undefined") {
		return "0.00";
	}
	if(typeof(value) == "string") {
		return parseFloat(value).toFixed(precision);
	}
	if(typeof(value) == "number") {
		return value.toFixed(precision);
	}
	return "0.00";
}

/**
 * 格式化delete_flag
 */
function deleteFormatter(delete_flag, row){
	var delete_flag_text = "<font color=green>是</font>";
	if(Number(delete_flag) == 1){
		delete_flag_text = "<font color=red>否</font>"
	}
	return delete_flag_text;
}

function sexFormatter(value){
    if (value == 0) return '<span>男</span>';
    if (value == 1) return '<span>女</span>';
}

function formatCellTip(value){
    if(typeof value == "undefined" || value == null || value == ""){
        return "";
    }else{
        return '<a href="#" title="'+value+'" class="easyui-tooltip">'+value+'</a>';
    }
}