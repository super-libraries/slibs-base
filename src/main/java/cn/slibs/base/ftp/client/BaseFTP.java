package cn.slibs.base.ftp.client;

import cn.slibs.base.ftp.FTPConfig;
import cn.slibs.base.ftp.UnifiedFTP;
import cn.slibs.base.ftp.base.FTPFileFilter;
import cn.slibs.base.ftp.base.FTPFileInfo;
import cn.slibs.base.ftp.base.FTPFileType;
import cn.slibs.base.ftp.base.FTPType;
import cn.slibs.base.misc.Const;
import cn.slibs.base.utils.FilesUtils;
import cn.slibs.base.utils.StringTool;
import com.iofairy.falcon.fs.FilePath;
import com.iofairy.falcon.fs.PathInfo;
import com.iofairy.falcon.io.MultiByteArrayInputStream;
import com.iofairy.falcon.io.MultiByteArrayOutputStream;
import com.iofairy.si.SI;
import com.iofairy.tcf.Close;
import com.iofairy.tcf.Try;
import com.iofairy.top.G;
import com.iofairy.top.S;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;

import static cn.slibs.base.ftp.base.FTPFileType.*;
import static com.iofairy.validator.Preconditions.*;

/**
 * FTP客户端基类
 *
 * @since 0.3.0
 */
@Slf4j
public abstract class BaseFTP implements UnifiedFTP {
    /* FTP配置信息 */
    protected FTPConfig ftpConfig;
    protected String serverInfo;
    /* 各FTP客户端 */
    protected ChannelSftp sftp;
    protected FTPClient ftp;
    protected FTPSClient ftps;

    @Override
    public boolean copy(String srcFilePath, String destFilePath, String ingSuff, String failSuff) throws Exception {
        // ftp文件是否存在判断
        FTPFileType ftpFileType = fileType(srcFilePath);

        checkArgument(ftpFileType == NON_EXIST || ftpFileType == DIR, serverInfo + "--" + srcFilePath + " >>> 此文件不存在或指向一个目录！");
        FTPFileType ftpFileType1 = fileType(destFilePath);
        checkArgument(ftpFileType1 != NON_EXIST, serverInfo + "--" + destFilePath + " >>> 已经存在此文件或目录，请先删除再复制！");

        mkParentDirs(destFilePath);
        if (ftpConfig.getFtpType() == FTPType.FTP || ftpConfig.getFtpType() == FTPType.FTPS) {
            /*
             下载文件流时，一定要 ftp.completePendingCommand();
             否则可能导致不可预料的结果，如：destFilePath 不存在，却被判定为存在
             */
            try (InputStream down = down(srcFilePath)) {
                upload(down, destFilePath, ingSuff, failSuff);
            }
        } else {
            MultiByteArrayOutputStream byteOs = new MultiByteArrayOutputStream();
            sftp.get(srcFilePath, byteOs);
            // try (InputStream down = sftp.get(srcFilePath)) {    // 使用此方法，会导致上传的时候卡住
            try (InputStream down = new MultiByteArrayInputStream(byteOs.toByteArrays())) {
                upload(down, destFilePath, ingSuff, failSuff);
            }
        }

        return true;
    }

    @Override
    public boolean completePendingCommand() throws IOException {
        switch (ftpConfig.getFtpType()) {
            case FTP:
                return ftp.completePendingCommand();
            case FTPS:
                return ftps.completePendingCommand();
            default:
                return true;
        }
    }

    /**
     * 获取各种FTP原始客户端。可实现更多定制化的FTP操作。<br>
     * {@link org.apache.commons.net.ftp.FTPClient}
     * 或{@link org.apache.commons.net.ftp.FTPSClient}
     * 或{@link com.jcraft.jsch.ChannelSftp}
     *
     * @return 获取各种FTP原始客户端
     */
    @Override
    public Object getRawClient() {
        switch (ftpConfig.getFtpType()) {
            case FTP:
                return ftp;
            case SFTP:
                return sftp;
            case FTPS:
                return ftps;
            default:
                return null;
        }
    }

    @Override
    public FTPConfig getFTPConfig() {
        return ftpConfig.clone();
    }

    @Override
    public String toString() {
        return serverInfo;
    }




    /*###################################################################################
     ************************************************************************************
     ------------------------------------------------------------------------------------
     ***********************          BaseFTP自己的成员方法          **********************
     ------------------------------------------------------------------------------------
     ************************************************************************************
     ###################################################################################*/

    boolean mkDirs(String dirPath, FTPType ftpType) throws Exception {
        PathInfo pathInfo = FilePath.info(dirPath);

        checkArgument(pathInfo == null || !pathInfo.isHasRoot(), serverInfo + "--" + dirPath + " >>> 不是有效的文件夹“绝对路径”……");

        dirPath = pathInfo.getFullPath();

        FTPFileType ftpFileType = fileType(dirPath);    // 获取当前路径类型
        if (ftpFileType == NON_EXIST) {
            String[] paths = pathInfo.getPaths();
            if (paths.length > 1) {
                String currentPath = "";
                for (int i = 1; i < paths.length; i++) {
                    currentPath = currentPath + Const.SLASH + paths[i];
                    if (!mkDir(currentPath, 1, ftpType)) {
                        return false;
                    }
                }
                return true;
            } else {
                return true;
            }
        } else return ftpFileType == DIR;
    }

    boolean mkDir(String dirPath, int retryCounts, FTPType ftpType) throws Exception {
        FTPFileType ftpFileType = fileType(dirPath);    // 获取当前路径类型
        if (ftpFileType == NON_EXIST) {
            boolean isMkdir;
            if (ftpType == FTPType.FTP || ftpType == FTPType.FTPS) {
                isMkdir = Try.tcf(() -> ftp.makeDirectory(encodeD2C(dirPath)), false, false);
            } else {
                Try.tcf(() -> sftp.mkdir(dirPath), false);
                isMkdir = cd(dirPath);
            }

            if (!isMkdir) {
                Try.sleep(50);      // 高并发有可能创建失败，但其实可能由其他线程创建完成，延迟一点时间，再次判断
                boolean cd = cd(dirPath);
                if (!cd && retryCounts > 0) {
                    log.warn(serverInfo + "--" + dirPath + ">>> 正在尝试重新创建文件夹(retry_mkdir_again)，剩余尝试次数：" + retryCounts);
                    return mkDir(dirPath, retryCounts - 1, ftpType);
                } else {
                    return cd;
                }
            } else {
                return true;
            }
        } else return ftpFileType == DIR;
    }


    long down(String ftpFilePath, String localFilePath, String ingSuff, String failSuff, FTPType ftpType) throws Exception {
        if (S.isBlank(ingSuff)) ingSuff = Const.ING;
        if (S.isBlank(failSuff)) failSuff = Const.FAIL;

        FTPFileType ftpFileType = fileType(ftpFilePath);
        checkArgument(ftpFileType == NON_EXIST, serverInfo + "--" + ftpFilePath + " >>> 文件不存在！");

        File file = new File(localFilePath);
        FilesUtils.mkParentDirs(file);

        String ingLocalPath = localFilePath + ingSuff;
        try (OutputStream os = Files.newOutputStream(Paths.get(ingLocalPath))) {
            if (ftpType == FTPType.FTP || ftpType == FTPType.FTPS) {
                ftp.retrieveFile(encodeD2C(ftpFilePath), os);
            } else {
                sftp.get(ftpFilePath, os);
            }
        } catch (Exception e) {
            String failLocalPath = localFilePath + failSuff;
            if (!ingSuff.equals(failSuff)) {
                FilesUtils.rename(ingLocalPath, failLocalPath);
            }
            throw new Exception(serverInfo + "--" + ftpFilePath + " >>> 下载失败！", e);
        }

        /*
         下载完成后，先获取文件信息，因为重命名完成后，有可能文件会被立即处理，出现找不到文件的错误
         */
        long fileSize = FilesUtils.fileSize(ingLocalPath);
        FilesUtils.rename(ingLocalPath, localFilePath);
        return fileSize;
    }

    long upload(String localFilePath, String ftpFilePath, String ingSuff, String failSuff, FTPType ftpType) throws Exception {
        // 本地文件是否存在判断
        File file = new File(localFilePath);
        checkArgument(!file.exists(), "${?} >>> 待上传的本地文件：[${?}]不存在！", serverInfo, localFilePath);

        InputStream is = null;
        try {
            is = Files.newInputStream(Paths.get(localFilePath));
            return upload(is, ftpFilePath, ingSuff, failSuff, ftpType);
        } catch (Exception e) {
            throw new RuntimeException(serverInfo + "--" + localFilePath + " >>> 上传失败！", e);
        } finally {
            Close.close(is);
        }
    }

    long upload(InputStream is, String ftpFilePath, String ingSuff, String failSuff, FTPType ftpType) throws Exception {
        if (S.isBlank(ingSuff)) ingSuff = Const.ING;
        if (S.isBlank(failSuff)) failSuff = Const.FAIL;

        // ftp文件是否存在判断
        FTPFileType ftpFileType = fileType(ftpFilePath);

        checkArgument(ftpFileType != NON_EXIST, serverInfo + "--" + ftpFilePath + " >>> 此文件或目录不存在！");

        // ftp上创建父路径
        PathInfo ftpPathInfo = FilePath.info(ftpFilePath);
        checkArgument(ftpPathInfo == null || !ftpPathInfo.isHasRoot(), serverInfo + "--" + ftpFilePath + " >>> 不是有效的“绝对路径”");
        boolean mkdirs = mkDirs(ftpPathInfo.getParentPath());
        checkState(!mkdirs, serverInfo + "--" + ftpFilePath + " >>> 创建此文件的父目录失败！");

        String ingFtpPath = ftpFilePath + ingSuff;
        String failFtpPath = ftpFilePath + failSuff;

        try {
            if (ftpType == FTPType.FTP || ftpType == FTPType.FTPS) {
                ftp.storeFile(encodeD2C(ingFtpPath), is);
            } else {
                sftp.put(is, ingFtpPath);
            }
        } catch (Exception e) {
            Close.close(is);    // 重命名之前先关闭流，否则无法重命名
            if (!ingSuff.equals(failSuff)) {
                rename(ingFtpPath, failFtpPath);
            }

            throw new Exception(serverInfo + "--" + ftpFilePath + " >>> 上传失败！", e);
        }
        Close.close(is);

        /*
         上传完成后，先获取文件信息，因为重命名完成后，有可能文件会被立即处理，出现找不到文件的错误
         */
        FTPFileInfo info = fileInfo(ingFtpPath);
        long fileSize = info.getFileSize();
        boolean rename = rename(ingFtpPath, ftpFilePath);
        if (rename) {
            return fileSize;
        } else {
            return -1;
        }
    }


    boolean delete(final String ftpPath, final FTPType ftpType) throws Exception {
        FTPFileType fileType = fileType(ftpPath);
        if (fileType == null || fileType == NON_EXIST) return true;
        if (fileType == DIR) {
            List<FTPFileInfo> fileInfos = ls(ftpPath, null);
            for (FTPFileInfo fileInfo : fileInfos) {
                final String fileFullPath = fileInfo.getFileFullPath();

                if (fileInfo.getFtpFileType() == DIR) {
                    delete(fileFullPath, ftpType);
                } else {
                    delete(fileFullPath, ftpType, false);
                }
            }
            return delete(ftpPath, ftpType, true);
        } else {
            return delete(ftpPath, ftpType, false);
        }
    }

    boolean delete(final String ftpPath, FTPType ftpType, boolean isDir) throws Exception {
        try {
            if (ftpType == FTPType.FTP || ftpType == FTPType.FTPS) {
                String encodePath = encodeD2C(ftpPath);
                return isDir ? ftp.removeDirectory(encodePath) : ftp.deleteFile(encodePath);
            } else {
                if (isDir) {
                    sftp.rmdir(ftpPath);
                } else {
                    sftp.rm(ftpPath);
                }
                return fileType(ftpPath) == NON_EXIST;
            }
        } catch (Exception e) {
            // 有时候即使报错了，也删除成功了，所以判断是否还存在文件，如果存在，再抛异常
            if (fileType(ftpPath) != NON_EXIST) throw new RuntimeException(e);
            return true;
        }
    }

    /**
     * 客户端程序使用的编码转换成ftp服务端编码(displayEncoding to controlEncoding)
     *
     * @param path 待转换编码的路径
     * @return 转换后的路径
     */
    public String encodeD2C(String path) {
        return StringTool.convertEncode(path, ftpConfig.getDisplayEncoding(), ftpConfig.getControlEncoding());
    }

    /**
     * ftp服务端编码转换成客户端程序使用的编码(controlEncoding to displayEncoding)
     *
     * @param path 待转换编码的路径
     * @return 转换后的路径
     */
    public String encodeC2D(String path) {
        return StringTool.convertEncode(path, ftpConfig.getControlEncoding(), ftpConfig.getDisplayEncoding());
    }


    /*###################################################################################
     ************************************************************************************
     ------------------------------------------------------------------------------------
     ***********************            BaseFTP静态方法            ***********************
     ------------------------------------------------------------------------------------
     ************************************************************************************
     ###################################################################################*/

    static FTPClient ftpLogin(FTPConfig ftpConfig) throws Exception {
        String errMsg = SI.$("${?}--与服务器建立连接失败，请检查用户名和密码是否正确！", ftpConfig.getServerInfo());
        FTPClient ftp = null;
        try {
            ftp = new FTPClient();
            ftp.setConnectTimeout(ftpConfig.getConnectTimeout());
            ftp.setDataTimeout(Duration.ofMillis((long) ftpConfig.getDataTimeout()));
            ftp.connect(ftpConfig.getIp(), ftpConfig.getPort());
            ftp.login(ftpConfig.getUsername(), ftpConfig.getPassword());
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                close(ftp);
                throw new RuntimeException(errMsg);
            }

            switch (ftpConfig.getFtpMode()) {
                case PORT:
                    ftp.enterLocalActiveMode();
                case PASV:
                    ftp.enterLocalPassiveMode();
            }

            ftp.setFileTransferMode(ftpConfig.getFileTransferMode());
            ftp.setFileType(ftpConfig.getFileType());
            ftp.setControlEncoding(ftpConfig.getControlEncoding());
            if (ftpConfig.getProtocolCommandListener() != null) {
                ftp.addProtocolCommandListener(ftpConfig.getProtocolCommandListener());
            }

            return ftp;
        } catch (Exception e) {
            close(ftp);
            throw new RuntimeException(errMsg, e);
        }
    }

    static FTPSClient ftpsLogin(FTPConfig ftpConfig) throws Exception {
        String errMsg = SI.$("${?}--与服务器建立连接失败，请检查用户名和密码是否正确！", ftpConfig.getServerInfo());
        FTPSClient ftps = ftpConfig.getUseFtpsClient();
        try {
            Boolean useExtendedMasterSecret = ftpConfig.getUseExtendedMasterSecret();
            if (useExtendedMasterSecret != null) {
                System.setProperty("jdk.tls.useExtendedMasterSecret", useExtendedMasterSecret.toString());
            }


            if (ftps == null) {
                SSLContext sslContext = ftpConfig.getSslContext();
                String protocol = ftpConfig.getProtocol();
                if (ftpConfig.isSessionReuse()) {
                    ftps = sslContext == null
                            ? new SSLSessionReuseFTPSClient(protocol, ftpConfig.isImplicit())
                            : new SSLSessionReuseFTPSClient(ftpConfig.isImplicit(), sslContext);
                } else {
                    ftps = sslContext == null
                            ? new FTPSClient(protocol, ftpConfig.isImplicit())
                            : new FTPSClient(ftpConfig.isImplicit(), sslContext);
                }
            }

            ftps.setConnectTimeout(ftpConfig.getConnectTimeout());
            ftps.setDataTimeout(Duration.ofMillis((long) ftpConfig.getDataTimeout()));
            ftps.connect(ftpConfig.getIp(), ftpConfig.getPort());
            ftps.login(ftpConfig.getUsername(), ftpConfig.getPassword());
            int reply = ftps.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                close(ftps);
                throw new RuntimeException(errMsg);
            }

            ftps.execPBSZ(0); // 设置保护缓冲区大小
            ftps.execPROT("P"); // 设置数据通道保护级别：P=私有（加密）

            switch (ftpConfig.getFtpMode()) {
                case PORT:
                    ftps.enterLocalActiveMode();
                case PASV:
                    ftps.enterLocalPassiveMode();
            }

            ftps.setFileTransferMode(ftpConfig.getFileTransferMode());
            ftps.setFileType(ftpConfig.getFileType());
            ftps.setControlEncoding(ftpConfig.getControlEncoding());
            if (ftpConfig.getProtocolCommandListener() != null) {
                ftps.addProtocolCommandListener(ftpConfig.getProtocolCommandListener());
            }

            return ftps;
        } catch (Exception e) {
            close(ftps);
            throw new RuntimeException(errMsg, e);
        }
    }

    static ChannelSftp sftpLogin(FTPConfig ftpConfig) throws Exception {
        String errMsg = SI.$("${?}--与服务器建立连接失败，请检查用户名和密码是否正确！", ftpConfig.getServerInfo());

        Session session = null;
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(ftpConfig.getUsername(), ftpConfig.getIp(), ftpConfig.getPort());
            session.setPassword(ftpConfig.getPassword());
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);
            // 设置超时时间
            session.setTimeout(ftpConfig.getTimeout());
            session.connect();
            sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();

            return sftp;
        } catch (Exception e) {
            try {
                session.disconnect();
            } catch (Exception e1) {
            }
            close(sftp);
            throw new RuntimeException(errMsg, e);
        }
    }

    static FTPFileType fileType(FTPClient ftp, String path, FTPConfig ftpConfig) throws Exception {
        FTPFileInfo ftpFileInfo = fileInfo(ftp, path, ftpConfig);
        return ftpFileInfo == null ? NON_EXIST : ftpFileInfo.getFtpFileType();
    }

    static FTPFileType fileType(ChannelSftp sftp, String path) {
        try {
            SftpATTRS sftpATTRS = sftp.lstat(path);
            return sftpATTRS.isDir() ? DIR : (sftpATTRS.isLink() ? SYMBOLIC_LINK : FILE);
        } catch (Exception e) {
            String message = e.getMessage() == null ? "" : e.getMessage().toLowerCase();
            String stackTrace = G.stackTrace(e);
            String string = e.toString() == null ? "" : e.toString().toLowerCase();
            if (message.contains("no such file")
                    || (string.startsWith("2: " + FilePath.path(path.toLowerCase())) && stackTrace.contains("com.jcraft.jsch.ChannelSftp.throwStatusError"))) {
                return NON_EXIST;
            }

            throw new RuntimeException(e);
        }
    }


    static FTPFileInfo fileInfo(ChannelSftp sftp, String path) throws Exception {
        PathInfo pathInfo = FilePath.info(path);
        if (pathInfo == null) return null;

        String fullPath = pathInfo.getFullPath();
        String fileName = pathInfo.getFileName().name;
        String parentPath = pathInfo.getParentPath();

        try {
            SftpATTRS sftpATTRS = sftp.lstat(fullPath);

            Date modifyTime = new Date((long) sftpATTRS.getMTime() * 1000L);
            return new FTPFileInfo(parentPath, fileName, sftpATTRS.isDir() ? -1 : sftpATTRS.getSize(),
                    modifyTime, sftpATTRS.isDir() ? DIR : (sftpATTRS.isLink() ? SYMBOLIC_LINK : FILE));
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("no such file")) {
                return null;
            }

            throw new RuntimeException(e);
        }
    }

    static FTPFileInfo fileInfo(FTPClient ftp, String path, FTPConfig ftpConfig) throws Exception {
        PathInfo pathInfo = FilePath.info(path);
        if (pathInfo == null || !pathInfo.isHasRoot()) return null;

        String fullPath = pathInfo.getFullPath();
        String fileName = pathInfo.getFileName().name;
        String parentPath = pathInfo.getParentPath();

        String encodeFullPath = encodeD2C(fullPath, ftpConfig);
        String encodeParentPath = encodeD2C(parentPath, ftpConfig);

        boolean cd = ftp.changeWorkingDirectory(encodeFullPath);

        if (cd) {   // 是文件夹
            FTPFile ftpFile = ftp.mlistFile(encodeFullPath);
            if (ftpFile == null) {
                if (fullPath.equals(FilePath.ROOT))
                    return new FTPFileInfo(parentPath, fileName, -1, null, DIR);

                FTPFile[] ftpFiles = ftp.listFiles(encodeParentPath);
                for (FTPFile ftpFile1 : ftpFiles) {
                    String ftpFileName = encodeC2D(ftpFile1.getName(), ftpConfig);
                    if (ftpFileName.equals(fileName)) {
                        return new FTPFileInfo(parentPath, fileName, -1, getDate(ftpFile1.getTimestamp()), DIR);
                    }
                }
                return new FTPFileInfo(parentPath, fileName, -1, null, DIR);
            } else {
                return new FTPFileInfo(parentPath, fileName, -1, getDate(ftpFile.getTimestamp()), DIR);
            }
        } else {    // 可能是文件或不存在
            FTPFile ftpFile = ftp.mlistFile(encodeFullPath);
            if (ftpFile == null) {
                FTPFile[] ftpFiles = ftp.listFiles(encodeFullPath);
                if (ftpFiles.length == 1) {
                    FTPFile ftpFile1 = ftpFiles[0];
                    return new FTPFileInfo(parentPath, fileName, ftpFile1.getSize(), getDate(ftpFile1.getTimestamp()), ftpFile1.isFile() ? FILE : SYMBOLIC_LINK);
                } else {
                    // 如果文件中存在一些特殊字符如：英文括号()，会导致 listFiles指定文件时，无法获取文件的Bug
                    FTPFile[] ftpFilesInDir = ftp.mlistDir(encodeParentPath);
                    if (G.isEmpty(ftpFilesInDir)) {
                        ftpFilesInDir = ftp.listFiles(encodeParentPath);
                    }

                    if (!G.isEmpty(ftpFilesInDir)) {
                        for (FTPFile ftpFile2 : ftpFilesInDir) {
                            String ftpFileName = encodeC2D(ftpFile2.getName(), ftpConfig);
                            if (ftpFileName.equals(fileName)) {
                                return new FTPFileInfo(parentPath, fileName, ftpFile2.getSize(), getDate(ftpFile2.getTimestamp()), ftpFile2.isFile() ? FILE : SYMBOLIC_LINK);
                            }
                        }
                    }
                }
            } else {
                return new FTPFileInfo(parentPath, fileName, ftpFile.getSize(), getDate(ftpFile.getTimestamp()), ftpFile.isFile() ? FILE : SYMBOLIC_LINK);
            }
        }

        return null;
    }

    /**
     * 递归遍历FTP文件夹
     *
     * @param ftpClient      ftp客户端
     * @param path           文件夹路径
     * @param encodePath     编码后的文件夹路径
     * @param recursiveLevel 文件夹递归层数。负数：无限遍历；0：不遍历；正数：遍历层数
     * @param filter         过滤条件
     * @param ftpFileInfos   文件信息列表
     * @param ftpConfig      ftp配置
     * @throws Exception ftp相关操作发生异常
     */
    static void ls(FTPClient ftpClient,
                   String path,
                   String encodePath,
                   int recursiveLevel,
                   FTPFileFilter filter,
                   List<FTPFileInfo> ftpFileInfos,
                   FTPConfig ftpConfig) throws Exception {

        FTPFile[] ftpFiles = ftpClient.mlistDir(encodePath);
        if (G.isEmpty(ftpFiles)) {
            FTPFile[] ftpFiles1 = ftpClient.listFiles(encodePath);
            if (!G.isEmpty(ftpFiles1)) {
                ftpFile2FileInfo(ftpClient, path, recursiveLevel, filter, ftpFileInfos, ftpFiles1, ftpConfig);
            }
        } else {
            ftpFile2FileInfo(ftpClient, path, recursiveLevel, filter, ftpFileInfos, ftpFiles, ftpConfig);
        }
    }

    /**
     * ftp文件对象转FTP文件信息对象
     *
     * @param ftpClient      ftp客户端
     * @param path           文件夹路径
     * @param recursiveLevel 文件夹递归层数。负数：无限遍历；0：不遍历；正数：遍历层数
     * @param filter         过滤条件
     * @param ftpFileInfos   文件信息列表
     * @param ftpFiles       ftp文件列表
     * @param ftpConfig      ftp配置
     * @throws Exception ftp相关操作发生异常
     */
    static void ftpFile2FileInfo(FTPClient ftpClient,
                                 String path,
                                 int recursiveLevel,
                                 FTPFileFilter filter,
                                 List<FTPFileInfo> ftpFileInfos,
                                 FTPFile[] ftpFiles,
                                 FTPConfig ftpConfig) throws Exception {

        for (FTPFile ftpFile : ftpFiles) {
            String ftpFileName = encodeC2D(ftpFile.getName(), ftpConfig);
            Date modifyDate = getDate(ftpFile.getTimestamp());
            boolean isDir = ftpFile.isDirectory();
            FTPFileInfo info = isDir ? new FTPFileInfo(path, ftpFileName, -1, modifyDate, DIR)
                    : new FTPFileInfo(path, ftpFileName, ftpFile.getSize(), modifyDate, ftpFile.isFile() ? FILE : SYMBOLIC_LINK);

            if (filter == null || filter.accept(info)) {
                ftpFileInfos.add(info);
            }

            if (isDir) {
                String fileFullPath = info.getFileFullPath();
                String encodeFileFullPath = encodeD2C(fileFullPath, ftpConfig);
                if (recursiveLevel > 0) {
                    ls(ftpClient, fileFullPath, encodeFileFullPath, recursiveLevel - 1, filter, ftpFileInfos, ftpConfig);
                } else if (recursiveLevel < 0) {
                    ls(ftpClient, fileFullPath, encodeFileFullPath, recursiveLevel, filter, ftpFileInfos, ftpConfig);
                }
            }
        }
    }

    /**
     * 递归遍历SFTP文件夹
     *
     * @param sftpClient     sftp客户端
     * @param path           文件夹路径
     * @param recursiveLevel 文件夹递归层数。负数：无限遍历；0：不遍历；正数：遍历层数
     * @param filter         过滤条件
     * @param ftpFileInfos   文件信息列表
     * @throws Exception ftp相关操作发生异常
     */
    static void ls(ChannelSftp sftpClient,
                   String path,
                   int recursiveLevel,
                   FTPFileFilter filter,
                   List<FTPFileInfo> ftpFileInfos) throws Exception {
        Vector<ChannelSftp.LsEntry> entrys = sftpClient.ls(path);

        for (ChannelSftp.LsEntry entry : entrys) {
            SftpATTRS attrs = entry.getAttrs();
            Date modifyTime = new Date((long) attrs.getMTime() * 1000L);
            boolean isDir = attrs.isDir();
            FTPFileInfo info = new FTPFileInfo(path, entry.getFilename(), isDir ? -1 : attrs.getSize(),
                    modifyTime, isDir ? DIR : (attrs.isLink() ? SYMBOLIC_LINK : FILE));

            String fileName = info.getFileName();
            boolean isDot = fileName.equals(".") || fileName.equals("..");
            if (!isDot) {
                if (filter == null || filter.accept(info)) {
                    ftpFileInfos.add(info);
                }
            }

            if (isDir && !isDot) {
                String fileFullPath = info.getFileFullPath();
                if (recursiveLevel > 0) {
                    ls(sftpClient, fileFullPath, recursiveLevel - 1, filter, ftpFileInfos);
                } else if (recursiveLevel < 0) {
                    ls(sftpClient, fileFullPath, recursiveLevel, filter, ftpFileInfos);
                }
            }

        }
    }

    static Date getDate(Calendar calendar) {
        return calendar == null ? null : calendar.getTime();
    }


    static String encodeD2C(String path, FTPConfig ftpConfig) {
        return StringTool.convertEncode(path, ftpConfig.getDisplayEncoding(), ftpConfig.getControlEncoding());
    }

    static String encodeC2D(String path, FTPConfig ftpConfig) {
        return StringTool.convertEncode(path, ftpConfig.getControlEncoding(), ftpConfig.getDisplayEncoding());
    }

    /**
     * 关闭ftp
     *
     * @param ftp ftp或ftps客户端
     */
    static void close(FTPClient ftp) {
        if (null != ftp && ftp.isConnected()) {
            Try.tcf(() -> ftp.logout(), false);
            Try.tcf(() -> ftp.disconnect(), false);
        }
    }

    /**
     * 关闭sftp
     *
     * @param sftp sftp客户端
     */
    static void close(ChannelSftp sftp) {
        if (sftp != null) {
            final Session session = Try.tcf(() -> sftp.getSession(), false);
            Try.tcf(() -> session.disconnect(), false);
            if (sftp.isConnected()) {
                Try.tcf(() -> sftp.disconnect(), false);
            }
        }
    }

}
