package com.cyber.core.tool;

import com.cyber.core.filter.SensitiveFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文字过滤等字符串工具类
 * @date 2022年8月19日
 * @author cyber
 */
public class StringFilterUtil {

    /**
     * 过滤反动、色情、暴力等非法字符串
     * @param str 需要过滤的字符串
     * @return 已经过滤的字符串
     */
    public static String filterIllegal(String str){
        SensitiveFilter sensitiveFilter = SensitiveFilter.DEFAULT;
        return sensitiveFilter.filter(str);
    }

    /**
     * 过滤特殊字符
     *
     */
    public static String filterStr(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 过滤emoji表情，将表情替换成*
     *
     */
    public static String filterEmoji(String source) {
        if (source != null) {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]|" +
                            "[\ud83e\udd00-\ud83e\uddff]|[\u2300-\u23ff]|[\u2500-\u25ff]|[\u2100-\u21ff]|[\u00a0-\u0fff]|[\u2b00-\u2bff]|[\u2d06]|[\u3030]"
                    , Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                source = emojiMatcher.replaceAll("*");
                return source;
            }
            return source;
        }
        return source;
    }

    /**
     * 过滤各种特殊字符、html、emoji
     */
    public static String filterSpecialStr(String str) {
        if (StringUtil.isBlank(str)) {
            return "";
        }
        //过滤html
        String filterHtmlString = filterHtmlString(str);
        //过滤特殊字符
        String filterStr = filterStr(filterHtmlString);
        //过滤emoji
        return filterEmoji(filterStr);
    }

    /**
     * 去除HTML字符串
     *
     * @param htmlStr 需要去除的字符串
     * @return 去除HTML字符串
     */
    public synchronized static String filterHtmlString(String htmlStr) {
        if (htmlStr == null || "".equals(htmlStr)) {
            return "";
        }
        //定义script的正则表达式
        String regExScript = "<script[^>]*?>[\\s\\S]*?<\\/script>";
        //定义style的正则表达式
        String regExStyle = "<style[^>]*?>[\\s\\S]*?<\\/style>";
        //定义HTML标签的正则表达式
        String regExHtml = "<[^>]+>";
        Pattern pScript = Pattern.compile(regExScript, Pattern.CASE_INSENSITIVE);
        Matcher mScript = pScript.matcher(htmlStr);
        //过滤script标签
        htmlStr = mScript.replaceAll("");
        Pattern pStyle = Pattern.compile(regExStyle, Pattern.CASE_INSENSITIVE);
        Matcher mStyle = pStyle.matcher(htmlStr);
        //过滤style标签
        htmlStr = mStyle.replaceAll("");
        Pattern pHtml = Pattern.compile(regExHtml, Pattern.CASE_INSENSITIVE);
        Matcher mHtml = pHtml.matcher(htmlStr);
        //过滤html标签
        htmlStr = mHtml.replaceAll("");
        //返回文本字符串
        return htmlStr.trim();
    }
}
