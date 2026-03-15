package cn.slibs.base.ftp.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;

/**
 * FTP日志监听器
 *
 * @since 0.3.0
 */
@Slf4j
public class LogProtocolCommandListener implements ProtocolCommandListener {

    @Override
    public void protocolCommandSent(ProtocolCommandEvent e) {
        if (log.isDebugEnabled()) {
            String cmd = e.getMessage().trim();
            // 主动跳过 USER/PASS 命令（双重保险）
            if (cmd.startsWith("USER ") || cmd.startsWith("PASS ")) {
                return; // 完全不记录登录命令行
            }

            debug(">>> {}", cmd);
        }
    }

    @Override
    public void protocolReplyReceived(ProtocolCommandEvent e) {
        if (log.isDebugEnabled()) {
            debug("<<< {}", e.getMessage().trim());
        }
    }

    public static void debug(String format, Object... arguments) {
        log.debug(format, arguments);
    }

}