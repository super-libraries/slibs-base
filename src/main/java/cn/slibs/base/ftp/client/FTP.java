package cn.slibs.base.ftp.client;

import cn.slibs.base.ftp.FTPConfig;
import cn.slibs.base.ftp.base.FTPFileFilter;
import cn.slibs.base.ftp.base.FTPFileInfo;
import cn.slibs.base.ftp.base.FTPFileType;
import cn.slibs.base.ftp.base.FTPType;
import com.iofairy.falcon.fs.FilePath;
import com.iofairy.falcon.fs.PathInfo;
import com.iofairy.falcon.io.MultiByteArrayInputStream;
import com.iofairy.falcon.io.MultiByteArrayOutputStream;
import com.iofairy.tcf.Try;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cn.slibs.base.ftp.base.FTPFileType.DIR;
import static cn.slibs.base.ftp.base.FTPFileType.NON_EXIST;
import static com.iofairy.validator.Preconditions.*;

/**
 * FTP客户端
 *
 * @since 0.3.0
 */
public class FTP extends BaseFTP {

    public FTP(FTPConfig ftpConfig) {
        this(null, ftpConfig);
    }

    public FTP(FTPClient loggedInFTP, FTPConfig ftpConfig) {
        checkNullNPE(ftpConfig);
        FTPType ftpType = ftpConfig.getFtpType();
        checkArgument(ftpType != FTPType.FTP && ftpType != FTPType.FTPS, "构建的FTP客户端类型与`ftpConfig.getFtpType()`指定的类型不一致！");

        this.ftpConfig = ftpConfig;
        this.serverInfo = ftpConfig.getServerInfo();
        this.ftp = loggedInFTP;
    }

    @Override
    public void login() throws Exception {
        if (ftpConfig.getFtpType() == FTPType.FTP) {
            ftp = ftpLogin(ftpConfig);
        } else {
            ftps = ftpsLogin(ftpConfig);
            ftp = ftps;
        }
    }

    @Override
    public boolean cd(String destPath) throws IOException {
        return null != ftp && ftp.changeWorkingDirectory(encodeD2C(destPath));
    }

    @Override
    public String pwd() throws Exception {
        return encodeC2D(ftp.printWorkingDirectory());
    }

    @Override
    public FTPFileType fileType(String path) throws Exception {
        return fileType(ftp, path, ftpConfig);
    }

    @Override
    public FTPFileInfo fileInfo(String path) throws Exception {
        return fileInfo(ftp, path, ftpConfig);
    }

    @Override
    public List<FTPFileInfo> ls(String path, int recursiveLevel, FTPFileFilter filter) throws Exception {
        FTPFileInfo info = fileInfo(path);
        if (info == null || info.getFtpFileType() != DIR) return null;
        String fileFullPath = info.getFileFullPath();
        String encodeFileFullPath = encodeD2C(fileFullPath);

        List<FTPFileInfo> ftpFileInfos = new ArrayList<>();
        ls(ftp, fileFullPath, encodeFileFullPath, recursiveLevel, filter, ftpFileInfos, ftpConfig);
        return ftpFileInfos;
    }

    @Override
    public List<String> lsName(String path, int recursiveLevel, FTPFileFilter filter) throws Exception {
        List<FTPFileInfo> ftpFileInfos = ls(path, recursiveLevel, filter);
        return ftpFileInfos == null ? null : ftpFileInfos.stream().map(FTPFileInfo::getFileFullPath).collect(Collectors.toList());
    }

    @Override
    public boolean mkDirs(String dirPath) throws Exception {
        return mkDirs(dirPath, FTPType.FTP);
    }

    @Override
    public boolean delete(String ftpPath) throws Exception {
        boolean isDelete = delete(ftpPath, FTPType.FTP);
        if (!isDelete) {
            Try.sleep(50);      // 删除失败，重试一次
            return delete(ftpPath, FTPType.FTP);
        }
        return true;
    }

    @Override
    public boolean rename(String oldFilePath, String newFilePath) throws Exception {
        FTPFileType ftpFileType = fileType(oldFilePath);
        if (ftpFileType == NON_EXIST) return false;

        String encodeOldPath = encodeD2C(oldFilePath);
        PathInfo info = FilePath.info(newFilePath);
        boolean mkdirs = mkDirs(info.getParentPath());
        if (mkdirs) {
            String encodeNewPath = encodeD2C(newFilePath);
            return ftp.rename(encodeOldPath, encodeNewPath);
        } else {
            return false;
        }
    }

    @Override
    public void down(String filePath, OutputStream os) throws Exception {
        FTPFileType ftpFileType = fileType(filePath);
        if (ftpFileType == NON_EXIST || ftpFileType == DIR)
            throw new RuntimeException(ftpConfig.getServerInfo() + "--" + filePath + " >>> 路径不存在或不是一个文件！");

        ftp.retrieveFile(encodeD2C(filePath), os);
    }


    @Override
    public InputStream down(String filePath) throws Exception {
        FTPFileType ftpFileType = fileType(filePath);
        if (ftpFileType == NON_EXIST || ftpFileType == DIR)
            throw new RuntimeException(ftpConfig.getServerInfo() + "--" + filePath + " >>> 路径不存在或不是一个文件！");

        MultiByteArrayOutputStream byteOs = new MultiByteArrayOutputStream();
        if (!ftp.retrieveFile(encodeD2C(filePath), byteOs)) {
            throw new IOException(filePath + " retrieveFile fail.");
        }
        return new MultiByteArrayInputStream(byteOs.toByteArrays());
    }

    @Override
    public long downFile(String ftpFilePath, String localFilePath, String ingSuff, String failSuff) throws Exception {
        return down(ftpFilePath, localFilePath, ingSuff, failSuff, FTPType.FTP);
    }

    @Override
    public long uploadFile(String localFilePath, String ftpFilePath, String ingSuff, String failSuff) throws Exception {
        return upload(localFilePath, ftpFilePath, ingSuff, failSuff, FTPType.FTP);
    }

    @Override
    public long upload(InputStream is, String ftpFilePath, String ingSuff, String failSuff) throws Exception {
        return upload(is, ftpFilePath, ingSuff, failSuff, FTPType.FTP);
    }

    @Override
    public boolean isConnected() {
        try {
            return ftp.isConnected();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void close() {
        close(ftp);
        close(ftps);
    }


}
