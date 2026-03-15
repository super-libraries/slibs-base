package cn.slibs.base.ftp.base;

import com.iofairy.falcon.fs.FilePath;
import com.iofairy.time.DateTime;
import com.iofairy.time.DateTimes;
import com.iofairy.top.S;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * FTP文件信息
 *
 * @since 0.3.0
 */
@Data
@NoArgsConstructor
public class FTPFileInfo {
    private String parentPath;          // 父目录
    private String fileName;            // 文件名
    private String fileFullPath;        // 完整路径（父目录+文件名）
    private long fileSize;              // 文件大小，文件夹为：-1
    private Date modifyTime;            // 修改时间
    private String modifyTimeStr;       // 修改时间字符串（yyyy-MM-dd HH:mm:ss）
    private FTPFileType ftpFileType;    // 文件类型

    public FTPFileInfo(String parentPath, String fileName, long fileSize, Date modifyTime, FTPFileType ftpFileType) {
        this(parentPath, fileName, FilePath.path(S.isBlank(parentPath) ? "/" : parentPath, S.nullToEmpty(fileName)), fileSize, modifyTime, ftpFileType);
    }

    public FTPFileInfo(String parentPath, String fileName, String fileFullPath, long fileSize, Date modifyTime, FTPFileType ftpFileType) {
        this.parentPath = parentPath;
        this.fileName = fileName;
        this.fileFullPath = fileFullPath;
        this.fileSize = fileSize;
        this.modifyTime = modifyTime;
        this.modifyTimeStr = this.modifyTime == null ? null : DateTime.of(modifyTime).format(DateTimes.DTF_STD);
        this.ftpFileType = ftpFileType;
    }

}
