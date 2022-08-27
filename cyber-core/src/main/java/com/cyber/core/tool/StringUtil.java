package com.cyber.core.tool;


import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 自定义的字符串工具类
 *
 * @author cyber
 */
public class StringUtil extends StringUtils {

    private final static Pattern PATTERN = Pattern.compile("_([a-z0-9])");

    private final static String SPLIT = ",";


    /**
     * 高亮关键字
     *
     * @param str      需要高亮的文本
     * @param keywords 关键字
     * @return 高亮关键字
     */
    public static String highlight(String str, String keywords) {
        if (str == null || "".equals(str)) {
            return "";
        }
        if (keywords == null || "".equals(keywords)) {
            return "";
        }
        return str.replace(keywords, "<strong style='color:red'>" + keywords + "</strong>");
    }

    /**
     * 首字母小写
     * @param s String
     * @return String
     */
    public static String firstCharLowerCase(String s) {
        if (s == null || "".equals(s)) {
            return ("");
        }
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

    /**
     * <b>首字母大写</b><br>
     * @param s String
     * @return String
     */
    public static String firstCharUpperCase(String s) {
        if (s == null || "".equals(s)) {
            return ("");
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }


    /**
     * 字符串替换<br/>
     * @param text 原始字符串
     * @param repl 想被替换的内容
     * @param with 替换后的内容
     * @return
     */
    public static String replace(String text, String repl, String with) {
        if (text == null || repl == null || with == null || repl.length() == 0) {
            return text;
        }

        StringBuffer buf = new StringBuffer(text.length());
        int searchFrom = 0;
        while (true) {
            int foundAt = text.indexOf(repl, searchFrom);
            if (foundAt == -1) {
                break;
            }

            buf.append(text.substring(searchFrom, foundAt)).append(with);
            searchFrom = foundAt + repl.length();
        }
        buf.append(text.substring(searchFrom));

        return buf.toString();
    }


    /**
     * 将List<?>的类型转为String,多个对象以逗号隔开
     */
    public static String listToString(List<?> list) {
        if (CollectionUtil.isEmpty(list)) {
            return "";
        }
        return join(list, SPLIT);
    }

    /**
     * 将List<?>的类型转为String,多个对象以逗号隔开
     */
    public static List<String> stringToList(String str) {
        return Arrays.stream(str.split(SPLIT)).map(s -> new String(s.trim())).collect(Collectors.toList());
    }


    /**
     * 转化为String对象,如果为空则返回"",否则返回原对象的字符串
     * @return boolean 返回结果
     */
    public static String toString(Object str) {
        return str == null ? "" : str.toString();
    }

    /**
     * 将ids转为List<Long>
     *
     * @param ids 字符串类型的ids
     * @return 将ids转为List<Long>
     */
    public static List<Long> getIds(String ids) {
        return Arrays.stream(ids.split(SPLIT)).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
    }

    /**
     * 将数据库表按小驼峰划分
     * goods_type_test -> goodsTypeTest
     *
     * @param target 目标字符串
     * @return 将数据库表按小驼峰划分
     */
    public static String toFirstUpper(String target) {
        if (target == null || "".equals(target)) {
            return null;
        }
        String[] split = target.toLowerCase().split("_");
        StringBuilder result = new StringBuilder(split[0]);
        if (split.length == 1) {
            return result.substring(0, 1).toUpperCase() + result.substring(1);
        }
        for (int i = 0; i < split.length; i++) {
            if (i == 0) {
                continue;
            }
            String temp = split[i];
            temp = temp.substring(0, 1).toUpperCase() + temp.substring(1);
            result.append(temp);
        }
        return result.toString();
    }

    /**
     * 将包路径 com.smoky.modules.system.entity转换为文件路径
     *
     * @param str 包路径
     * @return com\smoky\modules\system\entity
     */
    public static String convertToPath(String str) {
        if (str == null || "".equals(str)) {
            return null;
        }
        return str.trim().replace(".", "\\");
    }




    /**
     * 将Throwable的堆栈信息打印成字符串
     * @param throwable throwable
     * @return str
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter printWriter = new PrintWriter(sw)) {
            throwable.printStackTrace(printWriter);
            return sw.toString();
        }
    }



}
