package cn.slibs.base.ftp.base;

/**
 * 常用过滤器
 *
 * @since 0.3.0
 */
public class CommonFilter {
    /** 过滤出所有文件 */
    public static final FTPFileFilter FILE_FILTER = ftpFileInfo -> ftpFileInfo.getFtpFileType() == FTPFileType.FILE;
    /** 过滤出所有目录 */
    public static final FTPFileFilter DIR_FILTER = ftpFileInfo -> ftpFileInfo.getFtpFileType() == FTPFileType.DIR;
    /** 过滤出所有符号链接 */
    public static final FTPFileFilter SYMBOLIC_LINK_FILTER = ftpFileInfo -> ftpFileInfo.getFtpFileType() == FTPFileType.SYMBOLIC_LINK;

}
