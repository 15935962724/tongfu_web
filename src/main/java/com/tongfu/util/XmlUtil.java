package com.tongfu.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.*;

//import org.jdom.Element;
//import org.jdom.JDOMException;
//import org.jdom.input.SAXBuilder;

/**
 * 解析xml
 * @date 2018-5-16 14:13:19
 * @author wsr
 *
 */
public class XmlUtil {
	
	public static Map getParameterMap(HttpServletRequest request) {  
	    // 参数Map  
	    Map properties = request.getParameterMap();  
	    // 返回值Map  
	    Map returnMap = new HashMap();  
	    Iterator entries = properties.entrySet().iterator();  
	    Map.Entry entry;  
	    String name = "";  
	    String value = "";  
	    while (entries.hasNext()) {  
	        entry = (Map.Entry) entries.next();  
	        name = (String) entry.getKey();  
	        Object valueObj = entry.getValue();  
	        if(null == valueObj){  
	            value = "";  
	        }else if(valueObj instanceof String[]){  
	            String[] values = (String[])valueObj;  
	            for(int i=0;i<values.length;i++){  
	                value = values[i] + ",";  
	            }  
	            value = value.substring(0, value.length()-1);  
	        }else{  
	            value = valueObj.toString();  
	        }  
	        returnMap.put(name, value);  
	    }  
	    return returnMap;  
	}  
	
	 /**  
     * Map转换成Xml  
     * @param map  
     * @return  
     */
     public static String mapToXml(Map<String, String> data) throws Exception {
         DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
         org.w3c.dom.Document document = documentBuilder.newDocument();
         org.w3c.dom.Element root = document.createElement("xml");
         document.appendChild(root);
         for (String key: data.keySet()) {
             String value = data.get(key);
             if (value == null) {
                 value = "";
             }
             value = value.trim();
             org.w3c.dom.Element filed = document.createElement(key);
             filed.appendChild(document.createTextNode(value));
             root.appendChild(filed);
         }
         TransformerFactory tf = TransformerFactory.newInstance();
         Transformer transformer = tf.newTransformer();
         DOMSource source = new DOMSource(document);
         transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         StringWriter writer = new StringWriter();
         StreamResult result = new StreamResult(writer);
         transformer.transform(source, result);
         String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
         try {
             writer.close();
         }
         catch (Exception ex) {
         }
         return output;
     }
	
    /**
     * 将xml转换为Map
     * @param xml
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getXmlToMap(String xml) throws Exception {
        Document xmlDoc = DocumentHelper.parseText(xml);
        Map<String, Object> map = new HashMap<String, Object>();
        if (xmlDoc == null) {
            return map;
        }
        Element root = xmlDoc.getRootElement();
        for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
            Element e = (Element) iterator.next();
            List list = e.elements();
            if (list.size() > 0) {
                map.put(e.getName(), Dom2Map(e,map));
            } else {
                map.put(e.getName(), e.getText());
            }
        }
        return map;
    }
	
    
    
    private static Map Dom2Map(Element e,Map map){
        List list = e.elements();
        if(list.size() > 0){
            for (int i = 0;i < list.size(); i++) {
                Element iter = (Element) list.get(i);
                List mapList = new ArrayList();
                if (iter.elements().size() > 0) {
                    Map m = Dom2Map(iter,map);
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(m);
                        }
                        if (obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = (List) obj;
                            mapList.add(m);
                        }
                        map.put(iter.getName(), mapList);
                    } else {
                        map.putAll(m);
                    }
                } else {
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(iter.getText());
                        }
                        if (obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = (List) obj;
                            mapList.add(iter.getText());
                        }
                        map.put(iter.getName(), mapList);
                    } else {
                        map.put(iter.getName(), iter.getText());
                    }
                }
            }
        } else {
            map.put(e.getName(), e.getText());
        }
        return map;
    }

}