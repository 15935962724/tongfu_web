/*
 * Copyright 2005-2013 shenzhou.net. All rights reserved.
 * Support: http://www.shenzhou.net
 * License: http://www.shenzhou.net/license
 */
package com.tongfu.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utils - Web
 * 
 * @author HaoKangHu Team
 * @version 1.0
 */
public final class HtmlSpiritUtil {


		public static String delHTMLTag(String htmlStr){
			String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
			String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
			String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

			Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
			Matcher m_script=p_script.matcher(htmlStr);
			htmlStr=m_script.replaceAll(""); //过滤script标签

			Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
			Matcher m_style=p_style.matcher(htmlStr);
			htmlStr=m_style.replaceAll(""); //过滤style标签

			Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
			Matcher m_html=p_html.matcher(htmlStr);
			htmlStr=m_html.replaceAll(""); //过滤html标签
			if(htmlStr.length()>35){
				htmlStr = htmlStr.substring(0,35);
			}
			return htmlStr.trim()+"..."; //返回文本字符串
		}

}