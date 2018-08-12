/**
 *  共用正则表达式校验工具库
 */
Reg={
		/**
		 * 校验通用
		 * @param exp
		 * @param str
		 * @returns
		 */
		regEnter:function(exp,str){
			if(Reg.isNull(exp)){
				//layer.msg("校验表达式不能为空！", {icon: 2});
				return false;
			}else if(Reg.isNull(str)){
				//layer.msg("被校验的字符串不能为空！", {icon: 2});
				return false;
			}
			var reg=eval(exp);
			return reg.test(str);
		},
		/**
		 * 空的校验
		 * @param param
		 * @returns {Boolean}
		 */
		isNull:function(param){
			if(typeof(param)=="undefined"||param==null||param=="null"||param==""){
				return true;
			}else{
				return false;
			}
		},
		/**
		 * 空的校验
		 */
		isEmptyValue : function(value) {
            var type;
            if(value == null) { // 等同于 value === undefined || value === null
                return true;
            }
            type = Object.prototype.toString.call(value).slice(8, -1);
            switch(type) {
            case 'String':
                return !$.trim(value);
            case 'Array':
                return !value.length;
            case 'Object':
                return $.isEmptyObject(value);
            default:
                return false;
            }
        },
		/**
		 * 整数或者小数
		 * @param str
		 * @returns
		 */
		intOrFloat:function(str){
			var exp=/^[0-9]+\.{0,1}[0-9]{0,2}$/;
			return Reg.regEnter(exp,str);
		},
		/**
		 * 只能是数字
		 * @param str
		 * @returns
		 */
		onlyInt:function(str){
			var exp=/^[0-9]*$/;
			return Reg.regEnter(exp,str);
		},
		/**
		 * 只能输入n位的数字
		 * @param str
		 * @param n
		 * @returns
		 */
		onlyNNumInt:function(str,n){
			if(Reg.isNull(n)){
				alert("需要校验的数字位数不能为空！");
				return false;
			}
			var exp=/^\d{"+n+"}$/;
			return Reg.regEnter(exp,str);
		},
		/**
		 * 至少n位数字
		 * @param str
		 * @param n
		 * @returns
		 */
		nNumInt:function(str,n){
			if(Reg.isNull(n)){
				alert("需要校验的数字位数不能为空！");
				return false;
			}
			var exp=/^\d{"+n+",}$/;
			return Reg.regEnter(exp,str);
		},
		/**
		 * n到m位数字
		 * @param str
		 * @param n
		 * @param m
		 * @returns
		 */
		n_mNumInt:function(str,n,m){
			if(Reg.isNull(n)||Reg.isNull(m)){
				alert("需要校验的数字位数不能为空！");
				return false;
			}
			var exp=/^\d{"+n+","+m+"}$/;
			return Reg.regEnter(exp,str);
		},
		/**
		 * 只能输入非零开头的数字
		 * @param str
		 * @returns
		 */
		zeroOrNoZeroStart:function(str){
			var exp=/^([1-9][0-9]*)$/;
			return Reg.regEnter(exp,str);
		},
		/**
		 * 只能输入有n位小数的正实数
		 * @param str
		 * @param n
		 * @returns
		 */
		nDecimals:function(str,n){
			var exp=/^[0-9]+(.[0-9]{"+n+"})?$/;
			return Reg.regEnter(exp,str);
		},
		/**
		 * 只能输入有n~m位小数的正实数
		 * @param str
		 * @param n
		 * @param m
		 * @returns
		 */
		n_mDecimals:function(str,n,m){
			var exp=/^[0-9]+(.[0-9]{"+n+","+m+"})?$/;
			return Reg.regEnter(exp,str);
		},
		/**
		 * 非零正整数
		 * @param str
		 * @returns
		 */
		noZeroInt:function(str){
			var exp=/^\+?[1-9][0-9]*$/;
			return Reg.regEnter(exp,str);
		},
		/**
		 * 菲零负整数
		 * @param str
		 * @returns
		 */
		noZeroNegaInt:function(str){
			var exp=/^\-[1-9][0-9]*$/;
			return Reg.regEnter(exp,str);
		},
		/**
		 * 长度为n的字符串
		 * @param str
		 * @returns
		 */
		length_n_str:function(str,n){
			var exp=/^.{"+n+"}$/;
			return Reg.regEnter(exp,str);
		},
		/**
		 * 由26个英文字母组成的字符串
		 * @param str
		 * @param aorA,大写或小写类型，A表示大写，a表示小写，不指定或其他置顶表示不限制大小写
		 * @returns
		 */
		letter_str:function(str,aorA){
			var exp;
			if(Reg.isNull(aorA)){
				exp=/^[A-Za-z]+$/;
			}else if(aorA=="A"){
				exp=/^[A-Z]+$/;
			}else if(aorA=="a"){
				exp=/^[a-z]+$/;
			}else{
				exp=/^[A-Za-z]+$/;
			}
			return Reg.regEnter(exp,str);
		},
		/**
		 * 由数字、26个英文字母或者下划线组成的字符串
		 * @param str
		 * @returns
		 */
		letter_int_str:function(str){
			var exp=/^\w+$/;
			return Reg.regEnter(exp, str);
		},
		/**
		 * 以字母开头，长度在n~m之间，只能包含字符、数字和下划线
		 * @param str
		 * @param n 最短长度
		 * @param m 最长长度
		 * @returns
		 */
		nm_letter_int_str:function(str,n,m){
			var exp=/^[a-zA-Z]\w{"+n+","+m+"}$/;
			return Reg.regEnter(exp, str);
		},
		/**
		 * 验证是否含有^%&',;=?$\"等特殊字符
		 * @param str
		 * @returns
		 */
		isSpecialStr:function(str){
			var exp=/[^%&',;=?$\x22]+/;
			return Reg.regEnter(exp, str);
		},
		/**
		 * 只能输入汉字
		 * @param str
		 * @returns
		 */
		chinese:function(str){
			var exp=/^[\u4e00-\u9fa5]{0,}$/;
			return Reg.regEnter(exp, str);
		},
		/**
		 * 验证Email地址
		 * @param str
		 * @returns
		 */
		email:function(str){
			var exp=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
			return Reg.regEnter(exp, str);
		},
		/**
		 * 验证InternetURL地址
		 * @param str
		 * @returns
		 */
		internetUrl:function(str){
			var exp="/^http://([\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$/";
			return Reg.regEnter(exp, str);
		},
		/**
		 * 验证电话号码,正确格式为："XXX-XXXXXXX"、"XXXX-XXXXXXXX"、"XXX-XXXXXXX"、"XXX-XXXXXXXX"、"XXXXXXX"和"XXXXXXXX"
		 * @param str
		 * @returns
		 */
		tel:function(str){
			var exp="/^(\(\d{3,4}-)|\d{3.4}-)?\d{7,8}$/";
			return Reg.regEnter(exp, str);
		},
		/**
		 * 匹配手机号
		 * @param str
		 * @returns
		 */
		Mobile:function(str){
			var exp=/^((\(\d{3}\))|(\d{3}\-))?13\d{9}$/;
			return Reg.regEnter(exp, str);
		},
		/**
		 * 验证身份证号（15位或18位数字）
		 * @param str
		 * @returns
		 */
		IdCard:function(str){
			var exp=/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
			return Reg.regEnter(exp, str);
		},
		/**
		 * 货币输入校验
		 * @param str
		 * @returns
		 */
		money:function(str){
			var exp=/^\d+\.\d{2}$/;
			return Reg.regEnter(exp, str);
		},
		/**
		 * 验证一年的12月，正确格式为："01"～"09"和"1"～"12"。
		 * @param str
		 * @returns
		 */
		month:function(str){
			var exp=/^(0?[1-9]|1[0-2])$/;
			return Reg.regEnter(exp, str);
		},
		/**
		 * 验证一个月的31天,正确格式为；"01"～"09"和"1"～"31"。
		 * @param str
		 * @returns
		 */
		day:function(str){
			var exp=/^((0?[1-9])|((1|2)[0-9])|30|31)$/;
			return Reg.regEnter(exp, str);
		},
		/**
		 * 匹配html标签的正则表达式
		 * @param str
		 * @returns
		 */
		html:function(str){
			var exp=/<(.*)>(.*)<\/(.*)>|<(.*)\/>/;
			return Reg.regEnter(exp, str);
		},
		/**
		 * 匹配空行的正则表达式
		 * @param str
		 * @returns
		 */
		space:function(str){
			var exp="/\n[\s| ]*\r/";
			return Reg.regEnter(exp, str);
		},
		/**
		 * 匹配首尾空格的正则表达式
		 * @param str
		 * @returns
		 */
		start_end_space:function(str){
			var exp=/^(^\s*)|(\s*$)$/;
			return Reg.regEnter(exp, str);
		},
		/**
		 * 匹配QQ号
		 * @param str
		 * @returns
		 */
		QQ:function(str){
			var exp=/^[1-9][0-9]{4,}$/;
			return Reg.regEnter(exp, str);
		},
		/**
		 * 匹配邮编
		 * @param str
		 * @returns
		 */
		ZipCode:function(str){
			var exp=/^[\\d]{6}/;
			return Reg.regEnter(exp, str);
		},
		/**
		 * 匹配双字节字符(包括汉字在内)
		 * @param str
		 */
		other:function(str){
			var exp=/[^\x00-\xff]/;
			return Reg.regEnter(exp, str);
		},
		//---------------------------------------------------相关函数部分----------------------------------------------
		/**
		 * 类似java中的trim函数
		 */
		trim:function(str){
			return str.replace(/(^\s*)|(\s*$)/g, "");
		}
}