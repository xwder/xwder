package com.xwder.app.utils;

import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字符串工具类
 *
 * @author xwder
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final Map<String, String> CAMEL_CACHE = new ConcurrentHashMap<>();

    /**
     * <p> A String for carriage return CR ("\r").</p>
     */
    public static final String CR = org.apache.commons.lang3.StringUtils.CR;
    /**
     * <p> A String for linefeed LF ("\n").</p>
     */
    public static final String LF = org.apache.commons.lang3.StringUtils.LF;
    /**
     * <p>
     * On UNIX systems, it returns {@code "\n"};
     * On Windows systems it returns {@code "\r\n"}.
     * </p>
     */
    public static final String LINE_SEPARATOR = System.lineSeparator();
    /**
     * <p> The empty String {@code ""}.</p>
     */
    public static final String EMPTY = org.apache.commons.lang3.StringUtils.EMPTY;
    /**
     * <p> A String for a space character.</p>
     */
    public static final String SPACE = org.apache.commons.lang3.StringUtils.SPACE;
    /**
     * <p> A String for a underline character.</p>
     */
    public static final String UNDERLINE = "_";
    /**
     * <p> A String for a dash character.</p>
     */
    public static final String DASH = "-";
    /**
     * <p> A String for a dot character.</p>
     */
    public static final String DOT = ".";
    /**
     * <p> A String for a comma character.</p>
     */
    public static final String COMMA = ",";
    /**
     * <p> A String for a single quote character.</p>
     */
    public static final String SINGLE_QUOTE = "\'";
    /**
     * <p> A String for a double quote character.</p>
     */
    public static final String DOUBLE_QUOTE = "\"";
    /**
     * <p> A String for a forward slash character.</p>
     */
    public static final String SLASH_FORWARD = "/";
    /**
     * <p> A String for a back slash character.</p>
     */
    public static final String SLASH_BACK = "\\";
    /**
     * <p> The {@code file.separator} System Property.</p>
     */
    public static final String FILE_SEPARATOR = File.separator;
    /**
     * <p> A String for "true".</p>
     */
    public static final String TRUE = "true";
    /**
     * <p> A String for "false".</p>
     */
    public static final String FALSE = "false";
    /**
     * <p> A String for time format "HH:mm:ss".</p>
     */
    public static final String TIME_FORMAT = "HH:mm:ss";
    /**
     * <p> A String for date format "yyyy-MM-dd".</p>
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * <p> A String for datetime format "yyyy-MM-dd HH:mm:ss".</p>
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * <p> A String for iso datetime format "yyyy-MM-ddTHH:mm:ss".</p>
     */
    public static final String ISO8601_DATETIME_FORMAT = "yyyy-MM-dd\'T\'HH:mm:ss";
    /**
     * <p> UTC Timezone String "UTC".</p>
     */
    public static final String UTC = "UTC";
    /**
     * <p> The {@code user.home} System Property. User's home directory. </p>
     */
    public static final String USER_HOME = SystemUtils.USER_HOME;
    /**
     * <p> Gets the host name from an environment variable.</p>
     */
    public static final String HOST_NAME = SystemUtils.getHostName();
    /**
     * <p> The {@code java.io.tmpdir} System Property. Default temp file path.</p>
     */
    public static final String TMP_DIR = SystemUtils.JAVA_IO_TMPDIR;
    /**
     * <p> The {@code file.encoding} System Property.</p>
     */
    public static final String FILE_ENCODING = SystemUtils.FILE_ENCODING;
    /**
     * <p> A String UTF-8 file encoding.</p>
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 空字符串
     */
    private static final String NULLSTR = "";

    /**
     * 下划线
     */
    private static final char SEPARATOR = '_';

    /**
     * 获取参数不为空值
     *
     * @param value defaultValue 要判断的value
     * @return value 返回值
     */
    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * * 判断一个Collection是否为空， 包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * 判断一个Collection是否非空，包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * * 判断一个对象数组是否为空
     *
     * @param objects 要判断的对象数组
     *                * @return true：为空 false：非空
     */
    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * * 判断一个对象数组是否非空
     *
     * @param objects 要判断的对象数组
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * * 判断一个字符串是否为空串
     *
     * @param str String
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || NULLSTR.equals(str.trim());
    }

    /**
     * * 判断一个字符串是否为非空串
     *
     * @param str String
     * @return true：非空串 false：空串
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * * 判断一个对象是否是数组类型（Java基本型别的数组）
     *
     * @param object 对象
     * @return true：是数组 false：不是数组
     */
    public static boolean isArray(Object object) {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * 去空格
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @return 结果
     */
    public static String substring(final String str, int start) {
        if (str == null) {
            return NULLSTR;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return NULLSTR;
        }

        return str.substring(start);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @param end   结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return NULLSTR;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return NULLSTR;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 下划线转驼峰命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(SEPARATOR);
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * 驼峰式命名法 例如：user_name->userName
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * <p>Remove underLine "_" in words and change the character to uppercase after it.
     * </p>
     * <pre>
     * StringUtil.camel(null)  = null
     * StringUtil.camel("")  = ""
     * StringUtil.camel(" ")  = " "
     * StringUtil.camel("ab_cd")  = "abCd"
     * StringUtil.camel("ab_cd_ef")  = "abCdEf"
     * StringUtil.camel("_ab_cd_ef")  = "AbCdEf"
     * StringUtil.camel("ab_cd_ef_")  = "abCdEf"
     * </pre>
     *
     * @param str
     * @return
     */
    public static String camel(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        } else if (str.startsWith(StringUtils.UNDERLINE) || str.startsWith(StringUtils.DASH)) {
            //非驼峰命名不做转换，比如分页查询时，sqlServer会自动添加__hibernate_row_nr__ 字段，该字段需要忽略
            return str;
        } else {
            if (CAMEL_CACHE.containsKey(str)) {
                return CAMEL_CACHE.get(str);
            }

            String[] strs = str.toLowerCase().split(StringUtils.UNDERLINE);
            String column = strs[0];
            for (int i = 1; i < strs.length; i++) {
                column += String.valueOf(strs[i].charAt(0)).toUpperCase() + strs[i].substring(1, strs[i].length());
            }

            CAMEL_CACHE.put(str, column);
            return column;
        }
    }

    /**
     * 字符串首字母小写
     * <p>
     * StringUtils.getFirstUp("IFFlag")        = iFFlag
     * StringUtils.getFirstUp("LForeignPort")  = lForeignPort
     *
     * @param string
     * @return
     */
    public static String getFirstLower(String string) {
        if (isEmpty(string)) {
            return string;
        }
        return String.valueOf(string.charAt(0)).toLowerCase() + string.substring(1);
    }
}