define('static/libs/util/config', ['require', 'exports', 'module'], function(require, exports, module) {

  Date.prototype.Format = function(a) {
  	var c, b = {
  		"M+": this.getMonth() + 1,
  		"d+": this.getDate(),
  		"h+": this.getHours(),
  		"m+": this.getMinutes(),
  		"s+": this.getSeconds(),
  		"q+": Math.floor((this.getMonth() + 3) / 3),
  		S: this.getMilliseconds()
  	};
  	/(y+)/.test(a) && (a = a.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length)));
  	for (c in b) RegExp("(" + c + ")").test(a) && (a = a.replace(RegExp.$1, 1 == RegExp.$1.length ? b[c] : ("00" + b[c]).substr(("" + b[c]).length)));
  	return a
  };
  return {
  	base: "http://" + location.host + ns
  }

});