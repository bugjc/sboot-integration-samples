package com.bugjc.core.util;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.DocumentException;

import java.util.Arrays;


public class StrSort {


    public static void main(String[] args) throws DocumentException {
        /**
         * 将字符串中的字符进行排序
         * 1.将字符串转成数组
         * 2.对数组进行排序
         * 3.将数组转成字符串
         * */
        String str = "alkfldsjfjnuiiwnnkj12315481askdlka{dasdasjkfjfmkmkl}";
        System.out.println(str);
        String sortStr = sortString(str);
        System.out.println(sortStr);
        String xmlStr = "<SLResponseOfAccessTokenY5ifgfiC xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.datacontract.org/2004/07/SLCS.BarCodePay.Core\"><Code>-301</Code><Data i:nil=\"true\" /><Message>获取访问令牌失败!非法请求</Message><Data i:nil=\"true\" /></SLResponseOfAccessTokenY5ifgfiC>";
        JSONObject result = XmlUtils.xml2Json(xmlStr);
        System.out.println(result.get("Code"));
    }

    /**
     * 对字符串中的字符进行排序，然后返回排好的字符串
     * @param str
     * @return
     */
    public static String sortString(String str) {
        char[] chs = stringToArray(str);
        sort(chs);
        String ch = arrayToString(chs);
        return ch;
    }

    /**
     * 将数组转成字符串
     * @param chs
     * @return
     */
    private static String arrayToString(char[] chs) {
        return String.valueOf(chs);
    }


    /**
     * 对数组进行排序
     * @param chs
     */
    private static void sort(char[] chs) {
        Arrays.sort(chs);
    }

    /**
     * 将字符串转成数组
     * @param str
     * @return
     */
    private static char[] stringToArray(String str) {
        return str.toCharArray();
    }


}

