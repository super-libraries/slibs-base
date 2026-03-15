package cn.slibs.base.ftp.client;

import cn.slibs.base.ftp.FTPConfig;
import cn.slibs.base.ftp.base.FTPType;
import com.iofairy.tcf.Try;
import org.apache.commons.net.ftp.FTPSClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static com.iofairy.validator.Preconditions.*;

/**
 * FTPS客户端
 *
 * @since 0.3.0
 */
public class FTPS extends FTP {
    /**
     * 默认的SSLContext实例
     */
    public static final SSLContext DEFAULT_SSL_CONTEXT = Try.tcf(() -> createDefaultSSLContext("TLSv1.2"), false);

    public FTPS(FTPConfig ftpConfig) {
        this(null, ftpConfig);
    }

    public FTPS(FTPSClient loggedInFTP, FTPConfig ftpConfig) {
        super(loggedInFTP, ftpConfig);

        checkNullNPE(ftpConfig);
        checkArgument(ftpConfig.getFtpType() != FTPType.FTPS, "构建的FTP客户端类型与`ftpConfig.getFtpType()`指定的类型不一致！");

        this.ftpConfig = ftpConfig;
        this.serverInfo = ftpConfig.getServerInfo();
        this.ftps = loggedInFTP;
    }

    /*###################################################################################
     ************************************************************************************
     ------------------------------------------------------------------------------------
     **********************               静态类与函数               **********************
     ------------------------------------------------------------------------------------
     ************************************************************************************
     ###################################################################################*/

    public static SSLContext createDefaultSSLContext(String protocol) throws Exception {
        SSLContext sslContext = SSLContext.getInstance(protocol);
        sslContext.init(null, new TrustManager[]{new TrustAllTrustManager()}, new java.security.SecureRandom());
        return sslContext;
    }


    private static class TrustAllTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

}
