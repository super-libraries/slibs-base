package cn.slibs.base.ftp;

import cn.slibs.base.ftp.base.FTPFileInfo;
import cn.slibs.base.ftp.base.FTPFileType;
import cn.slibs.base.ftp.client.BaseFTP;
import cn.slibs.base.ftp.client.FTP;
import cn.slibs.base.ftp.client.SFTP;
import cn.slibs.base.misc.Const;
import cn.slibs.base.utils.FilesUtils;
import com.iofairy.falcon.fs.FilePath;
import com.iofairy.falcon.fs.PathInfo;
import com.iofairy.lambda.RT0;
import com.iofairy.tcf.Close;
import com.iofairy.tcf.Try;
import com.jcraft.jsch.ChannelSftp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;

import static cn.slibs.base.ftp.base.FTPFileType.FILE;

/**
 * FTP高级操作
 *
 * @since 0.3.0
 */
@Slf4j
public class FTPAdvanced {


    /**
     * 断点上传
     *
     * @param localFilePath             本地文件路径
     * @param ftpFilePath               远程FTP/SFTP文件路径
     * @param retryTimes                尝试次数
     * @param deleteWhenExist           如果远程FTP/SFTP文件已经存在，是否删除
     * @param deleteWithSuffixWhenExist 如果远程FTP/SFTP已经存在临时文件，是否删除
     * @param createClientAction        创建FTP/SFTP客户端的方法
     * @return 断点上传是否成功
     * @throws Exception ftp相关操作发生异常
     */
    public static boolean uploadResume(String localFilePath,
                                       String ftpFilePath,
                                       int retryTimes,
                                       boolean deleteWhenExist,
                                       boolean deleteWithSuffixWhenExist,
                                       RT0<UnifiedFTP, Exception> createClientAction) throws Exception {
        return uploadResume(localFilePath, ftpFilePath, retryTimes, deleteWhenExist, deleteWithSuffixWhenExist, Const.ING, Const.FAIL, createClientAction);
    }

    /**
     * 断点上传
     *
     * @param localFilePath             本地文件路径
     * @param ftpFilePath               远程FTP/SFTP文件路径
     * @param retryTimes                尝试次数
     * @param deleteWhenExist           如果远程FTP/SFTP文件已经存在，是否删除
     * @param deleteWithSuffixWhenExist 如果远程FTP/SFTP已经存在临时文件，是否删除
     * @param createClientAction        创建FTP/SFTP客户端的方法
     * @param ingSuff                   文件传输进行中的后缀名
     * @param failSuff                  文件传输失败的后续名
     * @return 断点上传是否成功
     * @throws Exception ftp相关操作发生异常
     */
    public static boolean uploadResume(String localFilePath,
                                       String ftpFilePath,
                                       int retryTimes,
                                       boolean deleteWhenExist,
                                       boolean deleteWithSuffixWhenExist,
                                       String ingSuff,
                                       String failSuff,
                                       RT0<UnifiedFTP, Exception> createClientAction) throws Exception {
        // 本地文件是否存在判断
        File file = new File(localFilePath);

        UnifiedFTP unifiedFTP = null;
        String ingFtpPath = ftpFilePath + ingSuff;
        String failFtpPath = ftpFilePath + failSuff;

        int deleteTryTimes = 3;     // 尝试删除文件次数
        Exception deleteException = null;

        /*
         1、续传前检查。检查文件是否存在，检查是否需要删除
         如果不删除，将 ftpFilePath 或 failFtpPath 重命名为 ingFtpPath
         */
        for (int i = 0; i < deleteTryTimes; i++) {
            try {
                unifiedFTP = createClientAction.$();
                String serverInfo = unifiedFTP.getFTPConfig().getServerInfo();

                if (!file.exists()) {
                    throw new RuntimeException(serverInfo + ">>> 待上传的本地文件：[" + localFilePath + "]不存在！");
                }

                if (deleteWithSuffixWhenExist) {
                    boolean delete1 = unifiedFTP.delete(ingFtpPath);
                    boolean delete2 = unifiedFTP.delete(failFtpPath);

                    if (!delete1) throw new RuntimeException(serverInfo + ">>> ftp文件：[" + ingFtpPath + "]删除失败！");
                    if (!delete2) throw new RuntimeException(serverInfo + ">>> ftp文件：[" + failFtpPath + "]删除失败！");
                } else {
                    // failFtpPath 在续传过程中再重命名
                    // FtpFileType ftpFileType = unifiedFTP.fileType(failFtpPath);
                    // if (ftpFileType == FILE) {
                    //     unifiedFTP.rename(failFtpPath, ingFtpPath);
                    // }
                }

                if (deleteWhenExist) {
                    boolean delete = unifiedFTP.delete(ftpFilePath);
                    if (!delete) throw new RuntimeException(serverInfo + ">>> ftp文件：[" + ftpFilePath + "]删除失败！");
                } else {
                    long length = file.length();    // 本地文件大小
                    FTPFileInfo info = unifiedFTP.fileInfo(ftpFilePath);

                    if (info != null && info.getFtpFileType() == FILE) {
                        /*
                         本地文件大小与FTP服务器文件大小一致，直接返回成功
                         */
                        if (info.getFileSize() == length) {
                            return true;
                        } else {
                            boolean rename = unifiedFTP.rename(ftpFilePath, ingFtpPath);
                            if (!rename) throw new RuntimeException(serverInfo + ">>> ftp文件：[" + ftpFilePath + "]重命名" + ingSuff + "失败！");
                        }
                    }
                }
                break;
            } catch (Exception e) {
                deleteException = e;
            } finally {
                Close.close(unifiedFTP);
            }
        }

        if (deleteException != null) throw deleteException;     // 删除出现异常


        /*
         2、续传文件
         retryTimes 为重试次数，加上 原来的一次，所以设置等于 0 时，还会跑一次
         */
        boolean success = false;
        while ((retryTimes--) >= 0 && !success) {
            FileInputStream inputStream = null;
            String serverInfo = "";
            try {
                unifiedFTP = createClientAction.$();
                serverInfo = unifiedFTP.getFTPConfig().getServerInfo();

                /*
                 .fail 文件重命令为 .ing 文件
                 */
                FTPFileType ftpFileType = unifiedFTP.fileType(failFtpPath);
                if (ftpFileType == FILE) {
                    boolean rename = unifiedFTP.rename(failFtpPath, ingFtpPath);
                    if (!rename) throw new RuntimeException(serverInfo + ">>> ftp文件：[" + failFtpPath + "]重命名" + ingSuff + "失败！");
                }

                // ftp上创建父路径
                PathInfo ftpPathInfo = FilePath.info(ingFtpPath);
                if (ftpPathInfo == null || !ftpPathInfo.isHasRoot())
                    throw new RuntimeException(serverInfo + "---" + ingFtpPath + " >>> 不是有效的“绝对路径”");
                boolean mkdirs = unifiedFTP.mkDirs(ftpPathInfo.getParentPath());
                if (!mkdirs) throw new RuntimeException(serverInfo + "---" + ingFtpPath + " >>> 创建此文件的父目录失败！");

                long ftpFileSize = 0;           // ftp文件大小

                // ftp文件是否存在判断
                FTPFileInfo ingInfo = unifiedFTP.fileInfo(ingFtpPath);
                if (ingInfo != null && ingInfo.getFtpFileType() == FILE) {
                    ftpFileSize = ingInfo.getFileSize();
                }

                inputStream = new FileInputStream(localFilePath);

                if (unifiedFTP instanceof FTP) {
                    FTP ftp = (FTP) unifiedFTP;
                    FTPClient ftpClient = (FTPClient) ftp.getRawClient();
                    ftpClient.setRestartOffset(ftpFileSize);
                    inputStream.skip(ftpFileSize);
                    try {
                        boolean appendFileSuccess = ftpClient.appendFile(((BaseFTP) unifiedFTP).encodeD2C(ingFtpPath), inputStream);
                        if (!appendFileSuccess) throw new RuntimeException();
                    } catch (Exception e) {
                        unifiedFTP.rename(ingFtpPath, failFtpPath);
                        throw new Exception(serverInfo + "---" + ftpFilePath + " >>> 上传失败！", e);
                    }
                } else {
                    SFTP sftp = (SFTP) unifiedFTP;
                    ChannelSftp sftpClient = (ChannelSftp) sftp.getRawClient();
                    // inputStream.skip(ftpFileSize);   // 内部已经实现跳过操作
                    try {
                        sftpClient.put(inputStream, ingFtpPath, ChannelSftp.RESUME);
                    } catch (Exception e) {
                        unifiedFTP.rename(ingFtpPath, failFtpPath);
                        throw new Exception(serverInfo + "---" + ftpFilePath + " >>> 上传失败！", e);
                    }
                }
                unifiedFTP.rename(ingFtpPath, ftpFilePath);

                success = true;
            } catch (Exception e) {
                Try.sleep(5000);
                log.warn("{} >>> 上传文件到FTP失败，正在尝试续传，剩余续传次数：{}", serverInfo, (retryTimes + 1));
                if (retryTimes < 0) {
                    throw e;
                }
            } finally {
                Close.close(inputStream);
                Close.close(unifiedFTP);
            }
        }

        return success;
    }

    /**
     * 断点下载
     *
     * @param ftpFilePath               远程FTP/SFTP文件路径
     * @param localFilePath             本地文件路径
     * @param retryTimes                尝试次数
     * @param deleteWhenExist           如果远程FTP/SFTP文件已经存在，是否删除
     * @param deleteWithSuffixWhenExist 如果远程FTP/SFTP已经存在临时文件，是否删除
     * @param createClientAction        创建FTP/SFTP客户端的方法
     * @return 断点下载是否成功
     * @throws Exception ftp相关操作发生异常
     */
    public static boolean downResume(String ftpFilePath,
                                     String localFilePath,
                                     int retryTimes,
                                     boolean deleteWhenExist,
                                     boolean deleteWithSuffixWhenExist,
                                     RT0<UnifiedFTP, Exception> createClientAction) throws Exception {
        return downResume(ftpFilePath, localFilePath, retryTimes, deleteWhenExist, deleteWithSuffixWhenExist, Const.ING, Const.FAIL, createClientAction);
    }

    /**
     * 断点下载
     *
     * @param ftpFilePath               远程FTP/SFTP文件路径
     * @param localFilePath             本地文件路径
     * @param retryTimes                尝试次数
     * @param deleteWhenExist           如果远程FTP/SFTP文件已经存在，是否删除
     * @param deleteWithSuffixWhenExist 如果远程FTP/SFTP已经存在临时文件，是否删除
     * @param ingSuff                   文件传输进行中的后缀名
     * @param failSuff                  文件传输失败的后续名
     * @param createClientAction        创建FTP/SFTP客户端的方法
     * @return 断点下载是否成功
     * @throws Exception ftp相关操作发生异常
     */
    public static boolean downResume(String ftpFilePath,
                                     String localFilePath,
                                     int retryTimes,
                                     boolean deleteWhenExist,
                                     boolean deleteWithSuffixWhenExist,
                                     String ingSuff,
                                     String failSuff,
                                     RT0<UnifiedFTP, Exception> createClientAction) throws Exception {

        UnifiedFTP unifiedFTP = null;

        String ingLocalPath = localFilePath + ingSuff;
        String failLocalPath = localFilePath + failSuff;

        File localFile = new File(localFilePath);
        File failFile = new File(failLocalPath);
        File ingFile = new File(ingLocalPath);

        int deleteTryTimes = 3;     // 尝试删除文件次数
        Exception deleteException = null;

        long ftpLength = 0;
        /*
         1、续传前检查。检查文件是否存在，检查是否需要删除
         如果不删除，将 localFilePath 或 failLocalPath 重命名为 ingLocalPath
         */
        for (int i = 0; i < deleteTryTimes; i++) {
            try {
                unifiedFTP = createClientAction.$();
                String serverInfo = unifiedFTP.getFTPConfig().getServerInfo();

                if (deleteWithSuffixWhenExist) {
                    boolean delete1 = FilesUtils.delete(ingLocalPath);
                    boolean delete2 = FilesUtils.delete(failLocalPath);

                    if (!delete1) throw new RuntimeException(serverInfo + ">>> 本地文件：[" + ingLocalPath + "]删除失败！");
                    if (!delete2) throw new RuntimeException(serverInfo + ">>> 本地文件：[" + failLocalPath + "]删除失败！");
                }

                if (deleteWhenExist) {
                    boolean delete = FilesUtils.delete(localFilePath);
                    if (!delete) throw new RuntimeException(serverInfo + ">>> 本地文件：[" + localFilePath + "]删除失败！");
                } else {
                    FTPFileInfo info = unifiedFTP.fileInfo(ftpFilePath);

                    if (info != null && info.getFtpFileType() == FILE) {
                        ftpLength = info.getFileSize();    // 远程文件大小
                        long localLength = 0;
                        if (localFile.exists()) {
                            localLength = localFile.length();
                        }
                        /*
                         本地文件大小与FTP服务器文件大小一致，直接返回成功
                         */
                        if (localLength == ftpLength) {
                            return true;
                        } else {
                            boolean rename = localFile.renameTo(ingFile);
                            if (!rename) throw new RuntimeException(serverInfo + ">>> 本地文件：[" + localFilePath + "]重命名" + ingSuff + "失败！");
                        }
                    } else {
                        deleteException = new RuntimeException(serverInfo + ">>> ftp文件：[" + ftpFilePath + "]不存在或不是文件类型！");
                    }
                }
                break;
            } catch (Exception e) {
                deleteException = e;
            } finally {
                Close.close(unifiedFTP);
            }
        }

        if (deleteException != null) throw deleteException;     // 删除出现异常


        /*
         2、续传文件
         retryTimes 为重试次数，加上 原来的一次，所以设置等于 0 时，还会跑一次
         */
        boolean success = false;
        while ((retryTimes--) >= 0 && !success) {
            OutputStream outputStream = null;
            String serverInfo = "";
            try {
                unifiedFTP = createClientAction.$();
                serverInfo = unifiedFTP.getFTPConfig().getServerInfo();

                /*
                 .fail 文件重命令为 .ing 文件
                 */
                if (failFile.exists() && failFile.isFile()) {
                    boolean rename = failFile.renameTo(ingFile);
                    if (!rename) throw new RuntimeException(serverInfo + ">>> 本地文件：[" + failLocalPath + "]重命名" + ingSuff + "失败！");
                }

                FilesUtils.mkParentDirs(ingFile);

                long localLength = 0;           // ftp文件大小

                if (ingFile.exists()) {
                    localLength = ingFile.length();
                }

                outputStream = new FileOutputStream(ingLocalPath, true);

                if (unifiedFTP instanceof FTP) {
                    FTP ftp = (FTP) unifiedFTP;
                    FTPClient ftpClient = (FTPClient) ftp.getRawClient();
                    ftpClient.setRestartOffset(localLength);
                    try {
                        if (!ftpClient.retrieveFile(((BaseFTP) unifiedFTP).encodeD2C(ftpFilePath), outputStream)) {
                            throw new IOException(ftpFilePath + " retrieveFile fail.");
                        }
                    } catch (Exception e) {
                        Close.close(outputStream);
                        ingFile.renameTo(new File(failLocalPath));
                        throw new Exception(serverInfo + "---" + ftpFilePath + " >>> 下载失败！", e);
                    }
                } else {
                    SFTP sftp = (SFTP) unifiedFTP;
                    ChannelSftp sftpClient = (ChannelSftp) sftp.getRawClient();
                    try {
                        sftpClient.get(ftpFilePath, outputStream, null, ChannelSftp.RESUME, localLength);
                    } catch (Exception e) {
                        Close.close(outputStream);
                        ingFile.renameTo(failFile);
                        throw new Exception(serverInfo + "---" + ftpFilePath + " >>> 上传失败！", e);
                    }
                }

                success = true;
            } catch (Exception e) {
                Try.sleep(5000);
                log.warn("{} >>> 下载文件到本地失败，正在尝试续传，剩余续传次数：{}", serverInfo, (retryTimes + 1));
                if (retryTimes < 0) {
                    throw e;
                }
            } finally {
                Close.close(outputStream);
                Close.close(unifiedFTP);
                if (success) {
                    boolean renameSuccess = ingFile.renameTo(localFile);
                    if (!renameSuccess) throw new RuntimeException(serverInfo + ">>> 本地文件：[" + ingLocalPath + "]重命名失败！");
                }
            }
        }

        return success;
    }

}
