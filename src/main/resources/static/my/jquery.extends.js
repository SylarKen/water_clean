Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};

Array.prototype.indexOfObj = function(idName, idValue) { 
    for (var i = 0; i < this.length; i++) {  
        if (eval('this['+i+'].'+idName) == idValue) return i;  
    }  
    return -1;  
};

Array.prototype.getItem = function(idName, idValue) { 
    for (var i = 0; i < this.length; i++) {  
        if (eval('this['+i+'].'+idName) == idValue) return this[i];  
    }  
    return null;  
};

Array.prototype.removeObj = function(idName, idValue) {  
    var index = this.indexOfObj(idName,idValue);  
    if (index > -1) {  
        this.splice(index, 1);  
    }  
};

Array.prototype.toStringObj = function(idName) { 
	var str='';
    for (var i = 0; i < this.length; i++) {  
        if(str=='')
        	str = eval('this['+i+'].'+idName);
  	    else 
  	    	str += ','+eval('this['+i+'].'+idName);
    }  
    return str;  
};

String.prototype.startWith=function(str){     
  var reg=new RegExp("^"+str);     
  return reg.test(this);        
}  

String.prototype.endWith=function(str){     
  var reg=new RegExp(str+"$");     
  return reg.test(this);        
}