package cn.slibs.base.ftp.client;

import org.apache.commons.net.ftp.FTPSClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Locale;

/**
 * Session可重用的FTPS客户端。<br>
 * 解决连接FTPS出现的425问题：{@code 425 Cannot secure data connection - TLS session resumption required.}
 *
 * @since 0.3.0
 */
public class SSLSessionReuseFTPSClient extends FTPSClient {

    public SSLSessionReuseFTPSClient() {
    }

    public SSLSessionReuseFTPSClient(boolean isImplicit) {
        super(isImplicit);
    }

    public SSLSessionReuseFTPSClient(boolean isImplicit, SSLContext context) {
        super(isImplicit, context);
    }

    public SSLSessionReuseFTPSClient(SSLContext context) {
        super(context);
    }

    public SSLSessionReuseFTPSClient(String protocol) {
        super(protocol);
    }

    public SSLSessionReuseFTPSClient(String protocol, boolean isImplicit) {
        super(protocol, isImplicit);
    }

    /*
    // Source - https://stackoverflow.com/a/32404418
    // Posted by Martin Prikryl, modified by community. See post 'Timeline' for change history
    // License - CC BY-SA 4.0
    // Source - https://github.com/iterate-ch/cyberduck/blob/master/ftp/src/main/java/ch/cyberduck/core/ftp/FTPClient.java
    */
    @Override
    protected void _prepareDataSocket_(final Socket socket) throws IOException {
        if (socket instanceof SSLSocket) {
            // Control socket is SSL
            final SSLSession session = ((SSLSocket) _socket_).getSession();
            if (session.isValid()) {
                final SSLSessionContext context = session.getSessionContext();
                try {
                    final Field sessionHostPortCache = context.getClass().getDeclaredField("sessionHostPortCache");
                    sessionHostPortCache.setAccessible(true);
                    final Object cache = sessionHostPortCache.get(context);
                    final Method putMethod = cache.getClass().getDeclaredMethod("put", Object.class, Object.class);
                    putMethod.setAccessible(true);
                    Method getHostMethod;
                    try {
                        getHostMethod = socket.getClass().getMethod("getPeerHost");
                    } catch (NoSuchMethodException e) {
                        // Running in IKVM
                        getHostMethod = socket.getClass().getDeclaredMethod("getHost");
                    }
                    getHostMethod.setAccessible(true);
                    Object peerHost = getHostMethod.invoke(socket);
                    String arg1 = String.format("%s:%s", peerHost, socket.getPort()).toLowerCase(Locale.ROOT);
                    putMethod.invoke(cache, arg1, session);
                } catch (Exception e) {
                    throw new IOException(e);
                }
            } else {
                throw new IOException("Invalid SSL Session");
            }
        }
    }

}