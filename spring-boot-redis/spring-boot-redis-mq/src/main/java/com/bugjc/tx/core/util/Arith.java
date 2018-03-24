package com.bugjc.tx.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 浮点数运算
 * 默认小数点后2位且四舍五入
  * @author  作者 E-mail: qingmuyi@foxmail.com
  * @date 创建时间：2017年5月24日 上午10:31:03 
  * @version 1.0
 */
public class Arith {

	private Arith(){   
	}   
	
	/**
	 * 提供精确的四舍五入
	 * @param v1
	 * @return
	 */
	public static BigDecimal round(BigDecimal v1){
		return v1.setScale(2, RoundingMode.HALF_UP);
	}
	
	/**
	 * 参数四舍五入
	 * @param v1
	 * @return
	 */
	public static BigDecimal round(BigDecimal v1, int scale){
		return v1.setScale(scale, RoundingMode.HALF_UP);
	}
	
	/**  
	* 提供精确的加法运算。  
	* @param v1 被加数  
	* @param v2 加数  
	* @return 两个参数的和
	*/  
	public static BigDecimal add(BigDecimal v1,BigDecimal v2){   
		return v1.add(v2).setScale(2, RoundingMode.HALF_UP);   
	} 
	
	/**  
	* 提供精确的加法运算。  
	* @param v1 被加数  
	* @param v2 加数  
	* @return 两个参数的和
	*/  
	public static BigDecimal add(BigDecimal v1,BigDecimal v2,int scale){   
		return v1.add(v2).setScale(scale, RoundingMode.HALF_UP);   
	} 
	
	/**  
	* 提供精确的减法运算。  
	* @param v1 被减数  
	* @param v2 减数  
	* @return 两个参数的差  
	*/  
	public static BigDecimal sub(BigDecimal v1,BigDecimal v2){    
		return v1.subtract(v2).setScale(2, RoundingMode.HALF_UP);   
	} 
	
	/**  
	* 提供精确的减法运算。  
	* @param v1 被减数  
	* @param v2 减数  
	* @return 两个参数的差  
	*/  
	public static BigDecimal sub(BigDecimal v1,BigDecimal v2,int scale){    
		return v1.subtract(v2).setScale(scale, RoundingMode.HALF_UP);   
	} 
	
	/**  
	* 提供精确的乘法运算。  
	* @param v1 被乘数  
	* @param v2 乘数  
	* @return 两个参数的积  
	*/  
	public static BigDecimal mul(BigDecimal v1,BigDecimal v2){    
		BigDecimal bigDecimal =  v1.multiply(v2).setScale(2, RoundingMode.HALF_UP);
		return bigDecimal;   
	} 
	
	/**  
	* 提供精确的乘法运算。  
	* @param v1 被乘数  
	* @param v2 乘数  
	* @return 两个参数的积  
	*/  
	public static BigDecimal mul(BigDecimal v1,BigDecimal v2,int scale){    
		BigDecimal bigDecimal =  v1.multiply(v2).setScale(scale, RoundingMode.HALF_UP);
		return bigDecimal;   
	} 

	
	/**  
	* 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指  
	* 定精度，以后的数字四舍五入。  
	* @param v1 被除数  
	* @param v2 除数
	* @return 两个参数的商  
	*/  
	public static BigDecimal div(BigDecimal v1,BigDecimal v2){     
		return v1.divide(v2).setScale(2, RoundingMode.HALF_UP);   
	} 
	
	/**  
	* 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指  
	* 定精度，以后的数字四舍五入。  
	* @param v1 被除数  
	* @param v2 除数  
	* @param scale 表示表示需要精确到小数点以后几位。  
	* @return 两个参数的商  
	*/  
	public static BigDecimal div(BigDecimal v1,BigDecimal v2,int scale){     
		return v1.divide(v2,scale,BigDecimal.ROUND_HALF_UP);
	} 
	
	
	 /**  
     *   
     * 功能描述：金额字符串转换：单位分转成单元  
        
     * @param o 传入需要转换的金额字符串
     * @return 转换后的金额字符串  
     */   
    public static String fenToYuan(Object o) {  
        if(o == null) {
            return "0.00";
        }

        String s = o.toString();  
        int len = -1;     
        StringBuilder sb = new StringBuilder();  
        if (s != null && s.trim().length()>0 && !s.equalsIgnoreCase("null")){  
            s = removeZero(s);  
            if (s != null && s.trim().length()>0 && !s.equalsIgnoreCase("null")){  
                len = s.length();  
                int tmp = s.indexOf("-");  
                if(tmp>=0){  
                    if(len==2){  
                        sb.append("-0.0").append(s.substring(1));  
                    }else if(len==3){  
                        sb.append("-0.").append(s.substring(1));  
                    }else{  
                        sb.append(s.substring(0, len-2)).append(".").append(s.substring(len-2));                  
                    }                         
                }else{  
                    if(len==1){  
                        sb.append("0.0").append(s);  
                    }else if(len==2){  
                        sb.append("0.").append(s);  
                    }else{  
                        sb.append(s.substring(0, len-2)).append(".").append(s.substring(len-2));                  
                    }                     
                }  
            }else{  
                sb.append("0.00");  
            }  
        }else{  
            sb.append("0.00");  
        }  
        return sb.toString();         
    }  
    /**  
     *   
     * 功能描述：金额字符串转换：单位元转成单分  
       
     * @param o 传入需要转换的金额字符串
     * @return 转换后的金额字符串  
     */       
    public static String yuanToFen(Object o) {  
        if(o == null) {
            return "0";
        }

        String s = o.toString();  
        int posIndex = -1;  
        String str = "";  
        StringBuilder sb = new StringBuilder();  
        if (s != null && s.trim().length()>0 && !s.equalsIgnoreCase("null")){  
            posIndex = s.indexOf(".");  
            if(posIndex>0){  
                int len = s.length();  
                if(len == posIndex+1){  
                    str = s.substring(0,posIndex);  
                    if(str == "0"){  
                        str = "";  
                    }  
                    sb.append(str).append("00");  
                }else if(len == posIndex+2){  
                    str = s.substring(0,posIndex);  
                    if(str == "0"){  
                        str = "";  
                    }  
                    sb.append(str).append(s.substring(posIndex+1,posIndex+2)).append("0");  
                }else if(len == posIndex+3){  
                    str = s.substring(0,posIndex);  
                    if(str == "0"){  
                        str = "";  
                    }  
                    sb.append(str).append(s.substring(posIndex+1,posIndex+3));  
                }else{  
                    str = s.substring(0,posIndex);  
                    if(str == "0"){  
                        str = "";  
                    }  
                    sb.append(str).append(s.substring(posIndex+1,posIndex+3));  
                }  
            }else{  
                sb.append(s).append("00");  
            }  
        }else{  
            sb.append("0");  
        }  
        str = removeZero(sb.toString());  
        if(str != null && str.trim().length()>0 && !str.trim().equalsIgnoreCase("null")){  
            return str;  
        }else{  
            return "0";  
        }  
    }  
    
    /**  
     *   
     * 功能描述：去除字符串首部为"0"字符  
        
     * @param str 传入需要转换的字符串  
     * @return 转换后的字符串  
     */  
    public static String removeZero(String str){     
        char  ch;    
        String result = "";  
        if(str != null && str.trim().length()>0 && !str.trim().equalsIgnoreCase("null")){                  
            try{              
                for(int i=0;i<str.length();i++){  
                    ch = str.charAt(i);  
                    if(ch != '0'){                        
                        result = str.substring(i);  
                        break;  
                    }  
                }  
            }catch(Exception e){  
                result = "";  
            }     
        }else{  
            result = "";  
        }  
        return result;  
              
    }

    /**
     * 去除无用的0
     * @param amt
     * @return
     */
    public static BigDecimal removeFutilityZero(String amt){
        if(amt.indexOf(".") > 0){
            amt = amt.replaceAll("0+?$", "");//去掉后面无用的零
            amt = amt.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return new BigDecimal(amt);
    }
    
    public static void main(String[] args) {
		BigDecimal b = new BigDecimal(1.2533355);
		BigDecimal a = new BigDecimal(1.2533355);
		System.out.println(removeFutilityZero("152500"));
	}
	
}
