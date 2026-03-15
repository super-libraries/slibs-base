package cn.slibs.base.ftp;

import cn.slibs.base.ftp.base.FTPFileFilter;
import cn.slibs.base.ftp.base.FTPFileInfo;
import cn.slibs.base.ftp.base.FTPFileType;
import cn.slibs.base.misc.Const;
import com.iofairy.falcon.fs.FilePath;
import com.iofairy.falcon.fs.PathInfo;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 统一{@code SFTP/FTP/FTPS}客户端
 *
 * @since 0.3.0
 */
public interface UnifiedFTP extends Closeable {

    /**
     * FTP登录操作
     *
     * @throws Exception ftp相关操作发生异常
     */
    void login() throws Exception;

    /**
     * 切换目录
     *
     * @param path 路径
     * @return 切换工作目录是否成功
     * @throws Exception ftp相关操作发生异常
     */
    boolean cd(String path) throws Exception;

    /**
     * 获取当前工作目录
     *
     * @return 当前工作目录
     * @throws Exception ftp相关操作发生异常
     */
    String pwd() throws Exception;

    /**
     * 获取指定路径的文件类型
     *
     * @param path 路径
     * @return FTP文件类型
     * @throws Exception ftp相关操作发生异常
     */
    FTPFileType fileType(String path) throws Exception;

    /**
     * 获取文件信息
     *
     * @param path 路径。<b>注：路径为空，则返回 null</b>
     * @return FTP文件信息
     * @throws Exception ftp相关操作发生异常
     */
    FTPFileInfo fileInfo(String path) throws Exception;

    /**
     * 遍历文件夹下面的文件，返回文件信息list，如果路径不存在，或不是文件夹，则返回 null
     *
     * @param path   文件夹路径。<b>注：如果不是文件夹，则返回 null</b>
     * @param filter 过滤条件
     * @return 返回 FtpFileInfo list，如果路径不存在，或不是文件夹，则返回 null
     * @throws Exception ftp相关操作发生异常
     */
    default List<FTPFileInfo> ls(String path, FTPFileFilter filter) throws Exception {
        return ls(path, 0, filter);
    }

    /**
     * 遍历文件夹下面的文件，返回文件信息list，如果路径不存在，或不是文件夹，则返回 null
     *
     * @param path           文件夹路径。<b>注：如果不是文件夹，则返回 null</b>
     * @param recursiveLevel 文件夹递归层数。负数：无限遍历；0：不遍历；正数：遍历层数
     * @param filter         过滤条件
     * @return 返回 FtpFileInfo list，如果路径不存在，或不是文件夹，则返回 null
     * @throws Exception ftp相关操作发生异常
     */
    List<FTPFileInfo> ls(String path, int recursiveLevel, FTPFileFilter filter) throws Exception;

    /**
     * 遍历文件夹下面的文件，返回文件名，如果路径不存在，或不是文件夹，则返回 null
     *
     * @param path   文件夹路径。<b>注：如果不是文件夹，则返回 null</b>
     * @param filter 过滤条件
     * @return 返回 文件名list，如果路径不存在，或不是文件夹，则返回 null
     * @throws Exception ftp相关操作发生异常
     */
    default List<String> lsName(String path, FTPFileFilter filter) throws Exception {
        return lsName(path, 0, filter);
    }

    /**
     * 遍历文件夹下面的文件，返回文件名，如果路径不存在，或不是文件夹，则返回 null
     *
     * @param path           文件夹路径。<b>注：如果不是文件夹，则返回 null</b>
     * @param recursiveLevel 文件夹递归层数。负数：无限遍历；0：不遍历；正数：遍历层数
     * @param filter         过滤条件
     * @return 返回 文件名list，如果路径不存在，或不是文件夹，则返回 null
     * @throws Exception ftp相关操作发生异常
     */
    List<String> lsName(String path, int recursiveLevel, FTPFileFilter filter) throws Exception;

    /**
     * 递归创建文件夹
     *
     * @param dirPath 目录
     * @return 是否创建目录成功
     * @throws Exception ftp相关操作发生异常
     */
    boolean mkDirs(String dirPath) throws Exception;

    /**
     * 创建指定路径的父目录
     *
     * @param path 文件路径
     * @return 是否创建父目录成功
     * @throws Exception ftp相关操作发生异常
     */
    default boolean mkParentDirs(String path) throws Exception {
        PathInfo pathInfo = FilePath.info(path);
        if (pathInfo == null || !pathInfo.isHasRoot()) return false;
        return mkDirs(pathInfo.getParentPath());
    }

    /**
     * 删除文件或目录--注意：删除目录将删除目录下的所有文件及文件夹 <br>
     * 注：<br>
     * 删除的目的是让文件消失，所以只要 文件或目录 不存在，则表示 删除成功
     *
     * @param ftpPath 文件或目录路径
     * @return 是否删除成功
     * @throws Exception ftp相关操作发生异常
     */
    boolean delete(String ftpPath) throws Exception;

    /**
     * 重命名文件
     *
     * @param dirPath 文件所在目录
     * @param oldName 原始文件名
     * @param newName 新文件名
     * @return 是否重命名成功
     * @throws Exception ftp相关操作发生异常
     */
    default boolean rename(String dirPath, String oldName, String newName) throws Exception {
        return rename(FilePath.path(dirPath, oldName), FilePath.path(dirPath, newName));
    }

    /**
     * 移动文件
     *
     * @param srcDirPath  原目录
     * @param destDirPath 目标目录
     * @param fileName    移动的文件名
     * @return 是否移动成功
     * @throws Exception ftp相关操作发生异常
     */
    default boolean move(String srcDirPath, String destDirPath, String fileName) throws Exception {
        return rename(FilePath.path(srcDirPath, fileName), FilePath.path(destDirPath, fileName));
    }

    /**
     * 移动且重命名
     *
     * @param oldFilePath 原始的文件路径
     * @param newFilePath 新的文件路径
     * @return 是否移动且重命名成功
     * @throws Exception ftp相关操作发生异常
     */
    boolean rename(String oldFilePath, String newFilePath) throws Exception;


    /**
     * 下载文件
     *
     * @param filePath 文件路径
     * @param os       文件输出流
     * @throws Exception ftp相关操作发生异常
     */
    void down(String filePath, OutputStream os) throws Exception;

    /**
     * 下载文件并转成输入流（下载到本地，本地文件在传输过程会重命名为 .ing，传输失败会命名为 .fail后缀）
     *
     * @param filePath 文件完整路径
     * @return 文件输入流
     * @throws Exception ftp相关操作发生异常
     */
    InputStream down(String filePath) throws Exception;

    /**
     * 从 ftp 下载文件到本地目录（下载到本地，本地文件在传输过程会重命名为 .ing，传输失败会命名为 .fail后缀）
     *
     * @param ftpDirPath   ftp 目录
     * @param localDirPath 本地目录
     * @param fileName     文件名
     * @return 文件大小（单位：{@code Bytes}）
     * @throws Exception ftp相关操作发生异常
     */
    default long down(String ftpDirPath, String localDirPath, String fileName) throws Exception {
        return down(FilePath.path(ftpDirPath, fileName), FilePath.pathAuto(localDirPath, fileName));
    }

    /**
     * 从 ftp 下载文件到本地目录（下载到本地，本地文件在传输过程会重命名为 .ing，传输失败会命名为 .fail后缀）
     *
     * @param ftpDirPath   ftp 目录
     * @param localDirPath 本地目录
     * @param fileName     文件名
     * @param ingSuff      文件传输进行中的后缀名
     * @param failSuff     文件传输失败的后续名
     * @return 文件大小（单位：{@code Bytes}）
     * @throws Exception ftp相关操作发生异常
     */
    default long down(String ftpDirPath, String localDirPath, String fileName, String ingSuff, String failSuff) throws Exception {
        return downFile(FilePath.path(ftpDirPath, fileName), FilePath.pathAuto(localDirPath, fileName), ingSuff, failSuff);
    }

    /**
     * 从 ftp 下载文件到本地目录，并重命名（下载到本地，本地文件在传输过程会重命名为 .ing，传输失败会命名为 .fail后缀）
     *
     * @param ftpDirPath   ftp 目录
     * @param localDirPath 本地目录
     * @param oldName      旧文件名
     * @param newName      新文件名
     * @return 文件大小（单位：{@code Bytes}）
     * @throws Exception ftp相关操作发生异常
     */
    default long down(String ftpDirPath, String localDirPath, String oldName, String newName) throws Exception {
        return down(FilePath.path(ftpDirPath, oldName), FilePath.pathAuto(localDirPath, newName));
    }

    /**
     * 从 ftp 下载文件到本地目录，并重命名（下载到本地，本地文件在传输过程会重命名为 .ing，传输失败会命名为 .fail后缀）
     *
     * @param ftpDirPath   ftp 目录
     * @param localDirPath 本地目录
     * @param oldName      旧文件名
     * @param newName      新文件名
     * @param ingSuff      文件传输进行中的后缀名
     * @param failSuff     文件传输失败的后续名
     * @return 文件大小（单位：{@code Bytes}）
     * @throws Exception ftp相关操作发生异常
     */
    default long down(String ftpDirPath, String localDirPath, String oldName, String newName, String ingSuff, String failSuff) throws Exception {
        return downFile(FilePath.path(ftpDirPath, oldName), FilePath.pathAuto(localDirPath, newName), ingSuff, failSuff);
    }

    /**
     * 从 ftp 下载文件到本地（下载到本地，本地文件在传输过程会重命名为 .ing，传输失败会命名为 .fail后缀）
     *
     * @param ftpFilePath   ftp文件路径
     * @param localFilePath 本地文件路径
     * @return 文件大小（单位：{@code Bytes}）
     * @throws Exception ftp相关操作发生异常
     */
    default long down(String ftpFilePath, String localFilePath) throws Exception {
        return downFile(ftpFilePath, localFilePath, Const.ING, Const.FAIL);
    }

    /**
     * 从 ftp 下载文件到本地（下载到本地，本地文件在传输过程会重命名为 .ing，传输失败会命名为 .fail后缀）
     *
     * @param ftpFilePath   ftp文件路径
     * @param localFilePath 本地文件路径
     * @param ingSuff       文件传输进行中的后缀名
     * @param failSuff      文件传输失败的后续名
     * @return 文件大小（单位：{@code Bytes}）
     * @throws Exception ftp相关操作发生异常
     */
    long downFile(String ftpFilePath, String localFilePath, String ingSuff, String failSuff) throws Exception;

    /**
     * 上传指定文件到ftp服务器（上传到服务器，服务器的文件在传输过程会重命名为 .ing，传输失败会命名为 .fail后缀）
     *
     * @param localDirPath 本地目录
     * @param ftpDirPath   上传到FTP的目录
     * @param fileName     文件名
     * @return 文件大小（单位：{@code Bytes}）
     * @throws Exception ftp相关操作发生异常
     */
    default long upload(String localDirPath, String ftpDirPath, String fileName) throws Exception {
        return uploadFile(FilePath.pathAuto(localDirPath, fileName), FilePath.path(ftpDirPath, fileName), Const.ING, Const.FAIL);
    }

    /**
     * 上传指定文件到ftp服务器（上传到服务器，服务器的文件在传输过程会重命名为 .ing，传输失败会命名为 .fail后缀）
     *
     * @param localDirPath 本地目录
     * @param ftpDirPath   上传到FTP的目录
     * @param fileName     文件名
     * @param ingSuff      文件传输进行中的后缀名
     * @param failSuff     文件传输失败的后续名
     * @return 文件大小（单位：{@code Bytes}）
     * @throws Exception ftp相关操作发生异常
     */
    default long upload(String localDirPath, String ftpDirPath, String fileName, String ingSuff, String failSuff) throws Exception {
        return uploadFile(FilePath.pathAuto(localDirPath, fileName), FilePath.path(ftpDirPath, fileName), ingSuff, failSuff);
    }

    /**
     * 上传指定文件到ftp服务器，并重命名（上传到服务器，服务器的文件在传输过程会重命名为 .ing，传输失败会命名为 .fail后缀）
     *
     * @param localDirPath 本地目录
     * @param ftpDirPath   上传到FTP的目录
     * @param oldName      旧文件名
     * @param newName      新文件名
     * @return 文件大小（单位：{@code Bytes}）
     * @throws Exception ftp相关操作发生异常
     */
    default long upload(String localDirPath, String ftpDirPath, String oldName, String newName) throws Exception {
        return uploadFile(FilePath.pathAuto(localDirPath, oldName), FilePath.path(ftpDirPath, newName), Const.ING, Const.FAIL);
    }

    /**
     * 上传指定文件到ftp服务器，并重命名（上传到服务器，服务器的文件在传输过程会重命名为 .ing，传输失败会命名为 .fail后缀）
     *
     * @param localDirPath 本地目录
     * @param ftpDirPath   上传到FTP的目录
     * @param oldName      旧文件名
     * @param newName      新文件名
     * @param ingSuff      文件传输进行中的后缀名
     * @param failSuff     文件传输失败的后续名
     * @return 文件大小（单位：{@code Bytes}）
     * @throws Exception ftp相关操作发生异常
     */
    default long upload(String localDirPath, String ftpDirPath, String oldName, String newName, String ingSuff, String failSuff) throws Exception {
        return uploadFile(FilePath.pathAuto(localDirPath, oldName), FilePath.path(ftpDirPath, newName), ingSuff, failSuff);
    }

    /**
     * 上传指定文件到ftp服务器（上传到服务器，服务器的文件在传输过程会重命名为 .ing，传输失败会命名为 .fail后缀）
     *
     * @param localFilePath 本地文件路径
     * @param ftpFilePath   上传后FTP文件路径
     * @return 文件大小（单位：{@code Bytes}）
     * @throws Exception ftp相关操作发生异常
     */
    default long upload(String localFilePath, String ftpFilePath) throws Exception {
        return uploadFile(localFilePath, ftpFilePath, Const.ING, Const.FAIL);
    }

    /**
     * 上传指定文件到ftp服务器（上传到服务器，服务器的文件在传输过程会重命名为 .ing，传输失败会命名为 .fail后缀）
     *
     * @param localFilePath 本地文件路径
     * @param ftpFilePath   上传后FTP文件路径
     * @param ingSuff       文件传输进行中的后缀名
     * @param failSuff      文件传输失败的后续名
     * @return 文件大小（单位：{@code Bytes}）
     * @throws Exception ftp相关操作发生异常
     */
    long uploadFile(String localFilePath, String ftpFilePath, String ingSuff, String failSuff) throws Exception;

    /**
     * 上传 InputStream 到 ftp服务器（上传到服务器，服务器的文件在传输过程会重命名为 .ing，传输失败会命名为 .fail后缀）
     *
     * @param is          文件输入流
     * @param ftpFilePath 上传到ftp的完整文件路径
     * @return 文件大小（单位：{@code Bytes}）
     * @throws Exception ftp相关操作发生异常
     */
    default long upload(InputStream is, String ftpFilePath) throws Exception {
        return upload(is, ftpFilePath, Const.ING, Const.FAIL);
    }

    /**
     * 上传 InputStream 到 ftp服务器（上传到服务器，服务器的文件在传输过程会重命名为 .ing，传输失败会命名为 .fail后缀）
     *
     * @param is          文件输入流
     * @param ftpFilePath 上传到ftp的完整文件路径
     * @param ingSuff     文件传输进行中的后缀名
     * @param failSuff    文件传输失败的后续名
     * @return 文件大小（单位：{@code Bytes}）
     * @throws Exception ftp相关操作发生异常
     */
    long upload(InputStream is, String ftpFilePath, String ingSuff, String failSuff) throws Exception;

    /**
     * 在ftp上复制一个文件到另一个路径
     *
     * @param srcFilePath  原始“文件”路径，不能是文件夹
     * @param destFilePath 复制完以后的“文件”路径，不能是文件夹
     * @return 复制操作是否成功
     * @throws Exception ftp相关操作发生异常
     */
    default boolean copy(String srcFilePath, String destFilePath) throws Exception {
        return copy(srcFilePath, destFilePath, Const.ING, Const.FAIL);
    }

    /**
     * 在ftp上复制一个文件到另一个路径
     *
     * @param srcFilePath  原始“文件”路径，不能是文件夹
     * @param destFilePath 复制完以后的“文件”路径，不能是文件夹
     * @param ingSuff      文件传输进行中的后缀名
     * @param failSuff     文件传输失败的后续名
     * @return 复制操作是否成功
     * @throws Exception ftp相关操作发生异常
     */
    boolean copy(String srcFilePath, String destFilePath, String ingSuff, String failSuff) throws Exception;

    /**
     * 完成挂起的 FTP 命令并等待服务器响应
     *
     * @return 操作是否成功
     * @throws IOException ftp相关操作发生异常
     */
    boolean completePendingCommand() throws IOException;

    /**
     * 是否连接
     *
     * @return true表示连接，否则未连接
     */
    boolean isConnected();

    /**
     * 获取各种FTP原始客户端。可实现更多定制化的FTP操作。<br>
     * {@link org.apache.commons.net.ftp.FTPClient}
     * 或{@link org.apache.commons.net.ftp.FTPSClient}
     * 或{@link com.jcraft.jsch.ChannelSftp}
     *
     * @return 获取各种FTP原始客户端
     */
    Object getRawClient();

    /**
     * 获取FTP配置
     *
     * @return FTP配置
     */
    FTPConfig getFTPConfig();

}
