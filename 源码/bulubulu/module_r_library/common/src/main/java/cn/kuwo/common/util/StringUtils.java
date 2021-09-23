/*
 * Have a nice day.
 * @author YangSong
 * @mail song.yang@kuwo.cn
 */
package cn.kuwo.common.util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author 李建衡：jianheng.li@kuwo.cn
 */

public class StringUtils {
    private static final String TAG = "StringUtils";

    /**
     * 字符解析器
     */
    public interface CharSpeller {
        String spell(char c);
    }

    // private static CharSpeller speller = null;
    //
    // public static void registerSpeller(CharSpeller cs) {
    // speller = cs;
    // }

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f"};

    /**
     * 将指定字节转换成16进制字符
     *
     * @param b 待转换字节
     * @return 返回转换后的字符串
     */
    public static String byteToHexDigits(byte b) {
        int n = b;
        if (n < 0)
            n += 256;

        int d1 = n / 16;
        int d2 = n % 16;

        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 将指定字节数组转换成16进制字符串
     *
     * @param bytes 待转换的字节数组
     * @return 返回转换后的字符串
     */
    public static String bytesToHexes(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(byteToHexDigits(bytes[i]));
        }
        return sb.toString();
    }

    /**
     * 验证邮箱格式.
     *
     * @param email email
     * @return 是否正确
     */
    public static boolean isEmail(String email) {
        // String str =
        // "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        String str = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.find();
    }

    /**
     * 比较两个字符串是否相等。
     *
     * @param s1 字符串1
     * @param s2 字符串2
     * @return
     */
    public static boolean equalsIgnoreCase(final String s1, final String s2) {
        // 两者为空，相同
        if (s1 != null && s2 != null) {
            return s1.equals(s2);
            /*
             * String s3 = s1.toLowerCase(); String s4 = s2.toLowerCase();
             * return s3.equals(s4);
             */
        }
        return false;
    }

    /**
     * 如果string为null,返回""
     */
    public static String getNotNullString(String string) {
        if (null == string) {
            return "";
        }
        return string;
    }

    public static boolean isLetter(int ch) {
        return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
    }

    public static boolean isDigit(int ch) {
        return ch >= '0' && ch <= '9';
    }

    public static boolean isNotEmpty(String str) {
        return (str != null && str.length() > 0 && !"null".equals(str));
    }

    public static boolean isNumeric(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                if (i != 0 || str.charAt(i) != '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

    // 字符串转换成int，遇到str=""则设置默认值
    public static int String2Int(String str, int ret) {
        if (isNotEmpty(str)) {
            try {
                ret = Integer.parseInt(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static String getValueFromSetString(String setString) {
        String pair[] = setString.split(":");
        return (pair.length == 2 ? pair[1] : "");
    }

    public static String encodeUrl(String str, String charsetName) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        // java会把空格编码成+ 不符合通用标准 这里特殊处理下
        return URLEncoder.encode(str, charsetName).replaceAll("\\+", "%20");
    }

    public static String decodeUrl(String str, String charsetName) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        // java会把空格编码成+ 不符合通用标准 这里特殊处理下
        return URLDecoder.decode(str.replaceAll("%20", "+"), charsetName);
    }

    static final int UPPER_LOWER_SPAN = 'A' - 'a';
    static final int LOWER_UPPER_SPAN = -UPPER_LOWER_SPAN;

    /**
     * 将作为文件名的字符串的特殊字符"\*?:$/'",`^<>+"替换成"_"，以便文件顺利创建成功
     *
     * @param path 原待创建的文件名
     * @return 返回处理后的文件名
     */
    public static String filterForFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return "";
        }

        String need = path.replaceAll("\\\\|\\*|\\?|\\:|\\$|\\/|'|\"|,|`|\\^|<|>|\\+", "_");
        return need;
    }

    /*
     * 将字符串转换为UTF-8编码
     */
    public static String toUtf8(String str) {
        if (str == null)
            return null;
        String tem = null;
        try {
            tem = new String(str.getBytes(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return tem;
    }

    public static String[] split(String str, char separatorChar) {
        return splitWorker(str, separatorChar, false);
    }

    private static String[] splitWorker(String str, char separatorChar, boolean preserveAllTokens) {
        if (str == null) {
            return null;
        }
        int len = str.length();
        if (len == 0) {
            return new String[0];
        }
        List<String> list = new ArrayList<String>();
        int i = 0, start = 0;
        boolean match = false;
        boolean lastMatch = false;
        while (i < len) {
            if (str.charAt(i) == separatorChar) {
                if (match || preserveAllTokens) {
                    list.add(str.substring(start, i));
                    lastMatch = true;
                }
                start = ++i;
                continue;
            }
            lastMatch = false;
            match = true;
            i++;
        }
        if (match || (preserveAllTokens && lastMatch)) {
            list.add(str.substring(start, i));
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    // 将List转成字符，每一行用'\n'结尾
    public static <T extends Object> String listToString(List<T> list) {
        if (list == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer();

        for (T info : list) {
            if (info != null) {
                sb.append(info.toString());
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    // 将字符串按照行的方式，组成一个List
    public static List<String> stringToList(String data, List<String> list) {
        if (TextUtils.isEmpty(data) || list == null) {
            return null;
        }

        String[] lines = data.split("\\n");

        for (String line : lines) {
            list.add(line);
        }

        return list;
    }

    // 纪广兴增加------------------------------------------------------

    public static final String EMPTY_STRING = "";

    // \u3000 is the double-byte space character in UTF-8
    // \u00A0 is the non-breaking space character (&nbsp;)
    // \u2007 is the figure space character (&#8199;)
    // \u202F is the narrow non-breaking space character (&#8239;)
    public static final String WHITE_SPACES = " \r\n\t\u3000\u00A0\u2007\u202F";

    public static final String LINE_BREAKS = "\r\n";

    /**
     * This is a both way strip
     *
     * @param str   the string to strip
     * @param left  strip from left
     * @param right strip from right
     * @param what  character(s) to strip
     * @return the stripped string
     */
    public static String megastrip(String str, boolean left, boolean right, String what) {
        if (str == null) {
            return null;
        }

        int limitLeft = 0;
        int limitRight = str.length() - 1;

        while (left && limitLeft <= limitRight && what.indexOf(str.charAt(limitLeft)) >= 0) {
            limitLeft++;
        }
        while (right && limitRight >= limitLeft && what.indexOf(str.charAt(limitRight)) >= 0) {
            limitRight--;
        }

        return str.substring(limitLeft, limitRight + 1);
    }

    /**
     * lstrip - strips spaces from left
     *
     * @param str what to strip
     * @return String the striped string
     */
    public static String lstrip(String str) {
        return megastrip(str, true, false, WHITE_SPACES);
    }

    /**
     * rstrip - strips spaces from right
     *
     * @param str what to strip
     * @return String the striped string
     */
    public static String rstrip(String str) {
        return megastrip(str, false, true, WHITE_SPACES);
    }

    /**
     * strip - strips both ways
     *
     * @param str what to strip
     * @return String the striped string
     */
    public static String strip(String str) {
        return megastrip(str, true, true, WHITE_SPACES);
    }

    // ---------------------------------------------------------------------------

    // 秀场部分
    public static String join(List<?> arr, char join) {
        if (arr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Object item : arr) {
            sb.append(item.toString()).append(join);
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

	/*public static String filterEmoji(String source) {
		if(source != null)
		{
			Pattern emoji = Pattern.compile("[ud83cudc00-ud83cudfff]|[ud83dudc00-ud83dudfff]|[u2600-u27ff]",Pattern.UNICODE_CASE| Pattern.CASE_INSENSITIVE);
//			Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern . CASE_INSENSITIVE ) ;
			Matcher emojiMatcher = emoji.matcher(source);
			if ( emojiMatcher.find())
			{
				source = emojiMatcher.replaceAll("*");
				return source ;
			}
			return source;
		}
		return source;
	}*/


    //判断是否为汉字
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    //判断是否有表情
    public static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }


    public static String getStringFromJson(org.json.JSONObject obj, String key) {
        if (obj == null) {
            return "";
        }
        try {
            String strVal = obj.getString(key);
            if (TextUtils.isEmpty(strVal)) {
                return "";
            }

            return strVal;
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    //限制字符中文16個英文32
    public static void setEtCharacter(final EditText editText) {

        TextWatcher textWatcher = new TextWatcher() {
            private int editStart;
            private int editEnd;
            private int maxLen = 32;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("TextChanged", "---->beforeTextChanged : start = " + start + " count = " + count + " after = " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = editText.getSelectionStart();
                editEnd = editText.getSelectionEnd();
                // 先去掉监听器，否则会出现栈溢出
                editText.removeTextChangedListener(this);
                if (!TextUtils.isEmpty(editText.getText())) {
                    String etstring = editText.getText().toString().trim();
                    while (calculateLength(s.toString()) > maxLen) {
                        s.delete(editStart - 1, editEnd);
                        editStart--;
                        editEnd--;
                        Log.d("TextChanged", "editStart = " + editStart + " editEnd = " + editEnd);
                    }
                }

                editText.setText(s);
                editText.setSelection(editStart);

                // 恢复监听器
                editText.addTextChangedListener(this);

            }

            private int calculateLength(String etstring) {
                char[] ch = etstring.toCharArray();

                int varlength = 0;
                for (int i = 0; i < ch.length; i++) {
                    if ((ch[i] >= 0x2E80 && ch[i] <= 0xFE4F) || (ch[i] >= 0xA13F && ch[i] <= 0xAA40) || ch[i] >= 0x80) { // 中文字符范围0x4e00 0x9fbb
                        varlength = varlength + 2;
                    } else {
                        varlength++;
                    }
                }
                Log.d("TextChanged", "varlength = " + varlength);
                // 这里也可以使用getBytes,更准确嘛
                // varlength = etstring.getBytes(CharSet.forName("GBK")).lenght;// 编码根据自己的需求，注意u8中文占3个字节...
                return varlength;
            }
        };
        editText.addTextChangedListener(textWatcher);
    }

    public static String formatFloat(float value) {
        if (value == 0f) {
            return "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(value);//format 返回的是字符串
    }

    public static void main(String[] args) {
        System.out.printf(formatFloat(4));
    }
}
