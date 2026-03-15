package cn.slibs.base.ftp.base;

/**
 * FTP/SFTP/FTPS 中的文件类型
 *
 * @since 0.3.0
 */
public enum FTPFileType {
    /** 文件 */
    FILE,
    /** 目录 */
    DIR,
    /** 符号链接 */
    SYMBOLIC_LINK,
    /** 其他 */
    OTHER,
    /** 不存在 */
    NON_EXIST,
}
