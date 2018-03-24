package com.bugjc.tx.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaoleilu.hutool.util.StrUtil;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class XmlUtils {
	

	public static String map2xmlBody(Map<String, Object> vo, String rootElement) {
		Document doc = DocumentHelper.createDocument();
		Element body = DocumentHelper.createElement(rootElement);
		doc.add(body);
		__buildMap2xmlBody(body, vo);
		return doc.asXML();
	}
	
	@SuppressWarnings("unchecked")
	private static void __buildMap2xmlBody(Element body, Map<String, Object> vo) {
		if (vo != null) {
			Iterator<String> it = vo.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (StrUtil.isNotEmpty(key)) {
					Object obj = vo.get(key);
					Element element = DocumentHelper.createElement(key);
					if (obj != null) {
						if (obj instanceof String) {
							element.setText((String) obj);
						} else {
							if (obj instanceof Character || obj instanceof Boolean || obj instanceof Number
									|| obj instanceof java.math.BigInteger || obj instanceof java.math.BigDecimal) {
								Attribute attr = DocumentHelper.createAttribute(element, "type", obj.getClass().getCanonicalName());
								element.add(attr);
								element.setText(String.valueOf(obj));
							} else if (obj instanceof Map) {
								Attribute attr = DocumentHelper.createAttribute(element, "type", Map.class.getCanonicalName());
								element.add(attr);
								__buildMap2xmlBody(element, (Map<String, Object>) obj);
							} else {
							}
						}
					}
					body.add(element);
				}
			}
		}
	}

	public static Map xmlBody2map(String xml, String rootElement) throws DocumentException {
		Document doc = DocumentHelper.parseText(xml);
		Element body = (Element) doc.selectSingleNode("/" + rootElement);
		Map vo = __buildXmlBody2map(body);
		return vo;
	}

	private static Map __buildXmlBody2map(Element body) {
		Map vo = new HashMap();
		if (body != null) {
			List<Element> elements = body.elements();
			for (Element element : elements) {
				String key = element.getName();
				if (StrUtil.isNotEmpty(key)) {
					String type = element.attributeValue("type", "java.lang.String");
					String text = element.getText().trim();
					Object value = null;
					if (String.class.getCanonicalName().equals(type)) {
						value = text;
					} else if (Character.class.getCanonicalName().equals(type)) {
						value = new Character(text.charAt(0));
					} else if (Boolean.class.getCanonicalName().equals(type)) {
						value = new Boolean(text);
					} else if (Short.class.getCanonicalName().equals(type)) {
						value = Short.parseShort(text);
					} else if (Integer.class.getCanonicalName().equals(type)) {
						value = Integer.parseInt(text);
					} else if (Long.class.getCanonicalName().equals(type)) {
						value = Long.parseLong(text);
					} else if (Float.class.getCanonicalName().equals(type)) {
						value = Float.parseFloat(text);
					} else if (Double.class.getCanonicalName().equals(type)) {
						value = Double.parseDouble(text);
					} else if (java.math.BigInteger.class.getCanonicalName().equals(type)) {
						value = new java.math.BigInteger(text);
					} else if (java.math.BigDecimal.class.getCanonicalName().equals(type)) {
						value = new java.math.BigDecimal(text);
					} else if (Map.class.getCanonicalName().equals(type)) {
						value = __buildXmlBody2map(element);
					} else {
					}
					vo.put(key, value);
				}
			}
		}
		return vo;
	}
	
	
	/**
     * xml转json
     * @param xmlStr
     * @return
     * @throws DocumentException
     */
    public static JSONObject xml2Json(String xmlStr) throws DocumentException{
        Document doc= DocumentHelper.parseText(xmlStr);
        JSONObject json=new JSONObject();
        dom4j2Json(doc.getRootElement(), json);
        return json;
    }

    /**
     * xml转json
     * @param element
     * @param json
     */
    @SuppressWarnings("unchecked")
	public static void dom4j2Json(Element element,JSONObject json){
        //如果是属性
        for(Object o:element.attributes()){
            Attribute attr=(Attribute)o;
            if(!StrUtil.isEmpty(attr.getValue())){
                json.put("@"+attr.getName(), attr.getValue());
            }
        }
        List<Element> chdEl=element.elements();
        if(chdEl.isEmpty()&&!StrUtil.isEmpty(element.getText())){//如果没有子元素,只有一个值
            json.put(element.getName(), element.getText());
        }

        for(Element e:chdEl){//有子元素
            if(!e.elements().isEmpty()){//子元素也有子元素
                JSONObject chdjson=new JSONObject();
                dom4j2Json(e,chdjson);
                Object o=json.get(e.getName());
                if(o!=null){
                    JSONArray jsona=null;
                    if(o instanceof JSONObject){//如果此元素已存在,则转为jsonArray
                        JSONObject jsono=(JSONObject)o;
                        json.remove(e.getName());
                        jsona=new JSONArray();
                        jsona.add(jsono);
                        jsona.add(chdjson);
                    }
                    if(o instanceof JSONArray){
                        jsona=(JSONArray)o;
                        jsona.add(chdjson);
                    }
                    json.put(e.getName(), jsona);
                }else{
                    if(!chdjson.isEmpty()){
                        json.put(e.getName(), chdjson);
                    }
                }


            }else{//子元素没有子元素
                for(Object o:element.attributes()){
                    Attribute attr=(Attribute)o;
                    if(!StrUtil.isEmpty(attr.getValue())){
                        json.put("@"+attr.getName(), attr.getValue());
                    }
                }
                if(!e.getText().isEmpty()){
                    json.put(e.getName(), e.getText());
                }
            }
        }
    }

    
    @SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		// 解析结果存储在HashMap
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList){
			map.put(e.getName(), e.getText());
		}

		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}
    
}
