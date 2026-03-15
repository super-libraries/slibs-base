package cn.slibs.base.ftp;

import cn.slibs.base.ftp.base.FTPMode;
import cn.slibs.base.ftp.base.FTPType;
import cn.slibs.base.ftp.client.SSLSessionReuseFTPSClient;
import cn.slibs.base.misc.Const;
import com.iofairy.top.S;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPSClient;

import javax.net.ssl.SSLContext;
import java.util.StringJoiner;

import static com.iofairy.validator.Preconditions.*;

/**
 * <b>SFTP/FTP/FTPS</b> 连接配置
 *
 * @since 0.3.0
 */
@Getter
public class FTPConfig {
    /*==================
     ===== 公共参数 =====
     ==================*/
    private final String ip;
    private final int port;
    private final String username;
    private final String password;
    private final FTPType ftpType;
    private final String serverInfo;
    /*==================
     ===== SFTP参数 =====
     ==================*/
    private final int timeout;                // 超时时间，单位：毫秒
    /*==================
     === FTP/FTPS参数 ===
     ==================*/
    private final FTPMode ftpMode;
    private final String displayEncoding;     // 客户端显示使用编码
    private final String controlEncoding;     // 服务器采用编码
    private final int fileTransferMode;
    private final int fileType;
    private final int connectTimeout;         // FTP连接超时时间，单位：毫秒
    private final long dataTimeout;           // FTP数据读取时间，单位：毫秒
    /** 为 FTP/FTPS 客户端添加协议级调试监听器，实时将客户端与服务器之间的原始协议交互（命令 + 响应）输出 */
    private final ProtocolCommandListener protocolCommandListener;
    /*==================
     ===== FTPS参数 =====
     ==================*/
    /** 是否隐式模式（默认为：false，即显式模式），新系统中应优先采用<b>“显式模式”</b>。（显式模式：explicit；隐式模式：implicit） */
    private final boolean isImplicit;
    /**
     * SSL/TLS。 <br>
     * <b>注：</b>{@code sslContext}不为空时，采用带 {@code sslContext} 的构造方法
     */
    private final String protocol;
    private final SSLContext sslContext;
    /** 用于设置系统参数{@code System.setProperty("jdk.tls.useExtendedMasterSecret", "false");}，如果为{@code null}，则不设置 */
    private final Boolean useExtendedMasterSecret;
    /**
     * 是否采用session重用的FTPS客户端。{@code false}采用 {@link FTPSClient}，否则采用{@link SSLSessionReuseFTPSClient} <br>
     * <b>注：</b>{@code useFtpsClient}不为空时，采用 {@code useFtpsClient} 作为FTPS客户端
     */
    private final boolean isSessionReuse;
    private final FTPSClient useFtpsClient;


    public FTPConfig(String ip,
                     int port,
                     String username,
                     String password,
                     FTPType ftpType,
                     Integer timeout,
                     FTPMode ftpMode,
                     String displayEncoding,
                     String controlEncoding,
                     Integer fileTransferMode,
                     Integer fileType,
                     Integer connectTimeout,
                     Long dataTimeout,
                     ProtocolCommandListener protocolCommandListener,
                     Boolean isImplicit,
                     String protocol,
                     SSLContext sslContext,
                     Boolean useExtendedMasterSecret,
                     Boolean isSessionReuse,
                     FTPSClient useFtpsClient) {
        checkHasBlank(args(ip, username, password), args("ip", "username", "password"));
        checkArgument(port <= 0, "port端口号必须大于0！");
        checkNullNPE(ftpType);

        /*
         默认值设定
         */
        if (timeout == null) timeout = 300000;
        if (ftpMode == null) ftpMode = FTPMode.DEFAULT;
        if (S.isBlank(displayEncoding)) displayEncoding = Const.GBK;
        if (S.isBlank(controlEncoding)) controlEncoding = FTP.DEFAULT_CONTROL_ENCODING;
        if (fileTransferMode == null) fileTransferMode = FTP.STREAM_TRANSFER_MODE;
        if (fileType == null) fileType = FTP.BINARY_FILE_TYPE;
        if (connectTimeout == null) connectTimeout = 60000;
        if (dataTimeout == null) dataTimeout = 120000L;
        if (isImplicit == null) isImplicit = false;
        if (S.isBlank(protocol)) protocol = "TLSv1.2";
        if (isSessionReuse == null) isSessionReuse = false;


        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.ftpType = ftpType;
        this.serverInfo = ftpType + "://" + username + "@" + ip + ":" + port;
        this.timeout = timeout;
        this.ftpMode = ftpMode;
        this.displayEncoding = displayEncoding;
        this.controlEncoding = controlEncoding;
        this.fileTransferMode = fileTransferMode;
        this.fileType = fileType;
        this.connectTimeout = connectTimeout;
        this.dataTimeout = dataTimeout;
        this.protocolCommandListener = protocolCommandListener;
        this.isImplicit = isImplicit;
        this.protocol = protocol;
        this.sslContext = sslContext;
        this.useExtendedMasterSecret = useExtendedMasterSecret;
        this.isSessionReuse = isSessionReuse;
        this.useFtpsClient = useFtpsClient;
    }

    public static FTPConfigBuilder builder() {
        return new FTPConfigBuilder();
    }

    public static FTPConfigBuilder builder(String ip, int port, String username, String password, FTPType ftpType) {
        return new FTPConfigBuilder(ip, port, username, password, ftpType);
    }


    @Data
    @Accessors(fluent = true)
    @NoArgsConstructor
    public static class FTPConfigBuilder {
        private String ip;
        private int port;
        private String username;
        private String password;
        private FTPType ftpType;
        private Integer timeout;                // 超时时间，单位：毫秒
        private FTPMode ftpMode;
        private String displayEncoding;         // 客户端显示使用编码
        private String controlEncoding;         // 服务器采用编码
        private Integer fileTransferMode;
        private Integer fileType;
        private Integer connectTimeout;         // FTP连接超时时间，单位：毫秒
        private Long dataTimeout;               // FTP数据读取时间，单位：毫秒
        private ProtocolCommandListener protocolCommandListener;
        private Boolean isImplicit;
        private String protocol;
        private SSLContext sslContext;
        private Boolean useExtendedMasterSecret;
        private Boolean isSessionReuse;
        private FTPSClient useFtpsClient;

        public FTPConfigBuilder(String ip, int port, String username, String password, FTPType ftpType) {
            this.ip = ip;
            this.port = port;
            this.username = username;
            this.password = password;
            this.ftpType = ftpType;
        }

        public FTPConfig build() {
            return new FTPConfig(ip, port, username, password, ftpType, timeout, ftpMode, displayEncoding,
                    controlEncoding, fileTransferMode, fileType, connectTimeout, dataTimeout, protocolCommandListener,
                    isImplicit, protocol, sslContext, useExtendedMasterSecret, isSessionReuse, useFtpsClient);
        }
    }


    @Override
    public FTPConfig clone() {
        return new FTPConfig(ip, port, username, password, ftpType, timeout, ftpMode, displayEncoding,
                controlEncoding, fileTransferMode, fileType, connectTimeout, dataTimeout, protocolCommandListener,
                isImplicit, protocol, sslContext, useExtendedMasterSecret, isSessionReuse, useFtpsClient);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", serverInfo + " >>> 配置信息：[", "]");
        joiner.add("size(password)='" + password.length() + "'");

        switch (ftpType) {
            case SFTP:
                return joiner.add("timeout=" + timeout).toString();
            case FTP:
            case FTPS:
                joiner
                        .add("ftpMode=" + ftpMode)
                        .add("displayEncoding='" + displayEncoding + "'")
                        .add("controlEncoding='" + controlEncoding + "'")
                        .add("fileTransferMode=" + fileTransferMode)
                        .add("fileType=" + fileType)
                        .add("connectTimeout=" + connectTimeout)
                        .add("dataTimeout=" + dataTimeout)
                        .add("protocolCommandListener=" + getClassName(protocolCommandListener));

                if (ftpType == FTPType.FTPS) {
                    joiner
                            .add("isImplicit=" + isImplicit)
                            .add("protocol='" + protocol + "'")
                            .add("sslContext=" + getClassName(sslContext))
                            .add("useExtendedMasterSecret=" + useExtendedMasterSecret)
                            .add("isSessionReuse=" + isSessionReuse)
                            .add("useFtpsClient=" + getClassName(useFtpsClient));
                }

                return joiner.toString();

            default:
                return joiner.toString();
        }
    }

    private static String getClassName(Object o) {
        return o == null ? null : o.getClass().getName();
    }

}
