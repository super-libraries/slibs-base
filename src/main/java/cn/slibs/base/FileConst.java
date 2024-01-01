package cn.slibs.base;

/**
 * 文件相关常量
 *
 * @since 0.0.1
 */
public class FileConst {
    /*
     * 目录分隔符
     */
    public final static String SLASH = "/";            // 斜杠
    public final static String BACKSLASH = "\\";           // 反斜杠
    /**
     * String "quote-comma-quote"（ {@code ","} ）
     */
    public static final String QCQ = "\",\"";
    public static final String QUOTE = "\"";
    public static final String TWO_QUOTE = "\"\"";
    public static final String QUOTE_BR = "\"\n";

    /*
     * Charset
     */
    public static final String UTF8 = "UTF-8";
    public static final String GB18030 = "GB18030";     // GB18030 > GBK > GB2312
    public static final String GBK = "GBK";
    public static final String GB2312 = "GB2312";
    public static final String ISO_8859_1 = "ISO-8859-1";
    public static final String ISO_8859_5 = "ISO-8859-5";

    /*
     * 文件名后缀（file suffix）
     */
    /**
     * 文件正在传输
     */
    public static final String ING = ".ing";
    /**
     * 文件传输失败
     */
    public static final String FAIL = ".fail";
    /**
     * 文件被跳过
     */
    public static final String SKIP = ".skip";
    /**
     * 文件被忽略
     */
    public static final String IGNORE = ".ignore";
    public static final String GZ = ".gz";
    public static final String TAR_GZ = ".tar.gz";
    public static final String ZIP = ".zip";
    public static final String TXT = ".txt";
    public static final String CSV = ".csv";
    public static final String CTR = ".ctr";

}
