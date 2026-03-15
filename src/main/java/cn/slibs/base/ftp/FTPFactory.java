package cn.slibs.base.ftp;

import cn.slibs.base.ftp.client.FTP;
import cn.slibs.base.ftp.client.FTPS;
import cn.slibs.base.ftp.client.SFTP;
import com.jcraft.jsch.ChannelSftp;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;

import static com.iofairy.validator.Preconditions.checkNullNPE;

/**
 * 工厂类：创建<b>已登录</b>的FTP客户端
 *
 * @since 0.3.0
 */
public class FTPFactory {

    private FTPFactory() {

    }

    /**
     * 构建<b>已登录</b>的FTP对象
     *
     * @param ftpConfig ftp配置信息
     * @return 已登录的FTP对象
     * @throws Exception ftp相关操作发生异常
     */
    public static UnifiedFTP buildLoggedInFTP(FTPConfig ftpConfig) throws Exception {
        return buildLoggedInFTP(null, ftpConfig);
    }

    /**
     * 构建<b>已登录</b>的FTP对象
     *
     * @param loggedInFTP 如果是 sftp 则传入 {@link ChannelSftp} 对象；
     *                    如果是ftp，则传入 {@link FTPClient} 对象；
     *                    如果是ftps，则传入 {@link FTPSClient} 对象；
     * @param ftpConfig   ftp配置信息
     * @return 已登录的FTP对象
     * @throws Exception ftp相关操作发生异常
     */
    public static UnifiedFTP buildLoggedInFTP(Object loggedInFTP, FTPConfig ftpConfig) throws Exception {
        checkNullNPE(ftpConfig);

        UnifiedFTP ftp = null;
        if (loggedInFTP == null) {
            switch (ftpConfig.getFtpType()) {
                case SFTP:
                    ftp = new SFTP(ftpConfig);
                    break;
                case FTP:
                    ftp = new FTP(ftpConfig);
                    break;
                case FTPS:
                    ftp = new FTPS(ftpConfig);
                    break;
                default:
                    throw new IllegalArgumentException("不支持的FTP类型：" + ftpConfig.getFtpType());
            }

            ftp.login();    /* 此处已执行【登录】操作 */

        } else {
            if (loggedInFTP instanceof ChannelSftp) {
                ftp = new SFTP((ChannelSftp) loggedInFTP, ftpConfig);
            } else if (loggedInFTP instanceof FTPSClient) {
                ftp = new FTPS((FTPSClient) loggedInFTP, ftpConfig);
            } else if (loggedInFTP instanceof FTPClient) {
                ftp = new FTP((FTPClient) loggedInFTP, ftpConfig);
            } else {
                throw new IllegalArgumentException("不支持的FTP客户端类型：" + loggedInFTP.getClass().getName());
            }
        }

        return ftp;
    }

}
