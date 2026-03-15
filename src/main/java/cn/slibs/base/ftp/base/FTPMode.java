package cn.slibs.base.ftp.base;

/**
 * FTP/FTPS连接模式
 *
 * @since 0.3.0
 */
public enum FTPMode {
    /** FTP主动模式（Active Mode） */
    PORT,
    /** FTP被动模式（Passive Mode） */
    PASV,
    /** 默认模式 */
    DEFAULT,

}
