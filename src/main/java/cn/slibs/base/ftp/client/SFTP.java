package cn.slibs.base.ftp.client;

import cn.slibs.base.ftp.FTPConfig;
import cn.slibs.base.ftp.base.FTPFileFilter;
import cn.slibs.base.ftp.base.FTPFileInfo;
import cn.slibs.base.ftp.base.FTPFileType;
import cn.slibs.base.ftp.base.FTPType;
import com.iofairy.falcon.fs.FilePath;
import com.iofairy.falcon.fs.PathInfo;
import com.iofairy.tcf.Try;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cn.slibs.base.ftp.base.FTPFileType.DIR;
import static cn.slibs.base.ftp.base.FTPFileType.NON_EXIST;
import static com.iofairy.validator.Preconditions.*;

/**
 * SFTP客户端
 *
 * @since 0.3.0
 */
public class SFTP extends BaseFTP {

    public SFTP(FTPConfig ftpConfig) {
        this(null, ftpConfig);
    }

    public SFTP(ChannelSftp loggedInFTP, FTPConfig ftpConfig) {
        checkNullNPE(ftpConfig);
        checkArgument(ftpConfig.getFtpType() != FTPType.SFTP, "构建的FTP客户端类型与`ftpConfig.getFtpType()`指定的类型不一致！");

        this.ftpConfig = ftpConfig;
        this.serverInfo = ftpConfig.getServerInfo();
        this.sftp = loggedInFTP;
    }

    @Override
    public void login() throws Exception {
        sftp = sftpLogin(ftpConfig);
    }

    @Override
    public boolean cd(String destPath) {
        if (sftp != null) {
            try {
                sftp.cd(destPath);
                return true;
            } catch (SftpException e) {
                if (e.getMessage().equalsIgnoreCase("no such file")) {
                    return false;
                } else {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    @Override
    public String pwd() throws Exception {
        return sftp.pwd();
    }

    @Override
    public FTPFileType fileType(String path) {
        return fileType(sftp, path);
    }

    @Override
    public FTPFileInfo fileInfo(String path) throws Exception {
        return fileInfo(sftp, path);
    }

    @Override
    public List<FTPFileInfo> ls(String path, int recursiveLevel, FTPFileFilter filter) throws Exception {
        FTPFileInfo info = fileInfo(path);
        if (info == null || info.getFtpFileType() != DIR) return null;
        String fileFullPath = info.getFileFullPath();

        List<FTPFileInfo> ftpFileInfos = new ArrayList<>();
        ls(sftp, fileFullPath, recursiveLevel, filter, ftpFileInfos);
        return ftpFileInfos;
    }

    @Override
    public List<String> lsName(String path, int recursiveLevel, FTPFileFilter filter) throws Exception {
        List<FTPFileInfo> ftpFileInfos = ls(path, recursiveLevel, filter);
        return ftpFileInfos == null ? null : ftpFileInfos.stream().map(FTPFileInfo::getFileFullPath).collect(Collectors.toList());
    }

    @Override
    public boolean mkDirs(String dirPath) throws Exception {
        return mkDirs(dirPath, FTPType.SFTP);
    }

    @Override
    public boolean delete(String ftpPath) throws Exception {
        boolean isDelete = delete(ftpPath, FTPType.SFTP);
        if (!isDelete) {
            Try.sleep(50);      // 删除失败，重试一次
            return delete(ftpPath, FTPType.SFTP);
        }
        return true;
    }

    @Override
    public boolean rename(String oldFilePath, String newFilePath) throws Exception {
        FTPFileType ftpFileType = fileType(oldFilePath);
        if (ftpFileType == NON_EXIST) return false;

        PathInfo info = FilePath.info(newFilePath);
        boolean mkdirs = mkDirs(info.getParentPath());
        if (mkdirs) {
            sftp.rename(oldFilePath, newFilePath);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void down(String filePath, OutputStream os) throws Exception {
        FTPFileType ftpFileType = fileType(filePath);
        if (ftpFileType == NON_EXIST || ftpFileType == DIR)
            throw new RuntimeException(ftpConfig.getServerInfo() + filePath + " >>> 路径不存在或不是一个文件！");

        sftp.get(filePath, os);
    }

    @Override
    public InputStream down(String filePath) throws Exception {
        FTPFileType ftpFileType = fileType(filePath);
        if (ftpFileType == NON_EXIST || ftpFileType == DIR)
            throw new RuntimeException(ftpConfig.getServerInfo() + filePath + " >>> 路径不存在或不是一个文件！");

        return sftp.get(filePath);
    }

    @Override
    public long downFile(String ftpFilePath, String localFilePath, String ingSuff, String failSuff) throws Exception {
        return down(ftpFilePath, localFilePath, ingSuff, failSuff, FTPType.SFTP);
    }

    @Override
    public long uploadFile(String localFilePath, String ftpFilePath, String ingSuff, String failSuff) throws Exception {
        return upload(localFilePath, ftpFilePath, ingSuff, failSuff, FTPType.SFTP);
    }

    @Override
    public long upload(InputStream is, String ftpFilePath, String ingSuff, String failSuff) throws Exception {
        return upload(is, ftpFilePath, ingSuff, failSuff, FTPType.SFTP);
    }

    @Override
    public boolean isConnected() {
        try {
            return sftp.isConnected();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void close() {
        close(sftp);
    }


}
