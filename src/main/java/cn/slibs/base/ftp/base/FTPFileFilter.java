package cn.slibs.base.ftp.base;

/**
 * FTP文件过滤器
 *
 * @since 0.3.0
 */
@FunctionalInterface
public interface FTPFileFilter {
    /**
     * 过滤ftp文件的方法
     *
     * @param ftpFileInfo ftp文件信息
     * @return true表示通过，false表示不通过
     */
    boolean accept(FTPFileInfo ftpFileInfo);

}
