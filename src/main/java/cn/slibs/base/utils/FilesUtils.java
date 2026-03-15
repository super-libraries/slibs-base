package cn.slibs.base.utils;

import com.iofairy.tcf.Try;

import java.io.File;
import java.io.IOException;

import static com.iofairy.validator.Preconditions.checkNullNPE;

/**
 * 文件操作类
 *
 * @since 0.3.0
 */
public class FilesUtils {

    public static void mkParentDirs(String path) {
        checkNullNPE(path);
        mkParentDirs(new File(path));
    }

    public static void mkParentDirs(File file) {
        checkNullNPE(file);

        try {
            File parent = file.getCanonicalFile().getParentFile();
            if (parent == null) {
                return;
            }

            parent.mkdirs();

            if (!parent.isDirectory()) {
                throw new IOException("无法创建父目录：" + file);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取文件大小（单位：字节），如果文件不存在，则返回 {@code -1}
     *
     * @param path 文件路径
     * @return 返回文件大小
     */
    public static long fileSize(String path) {
        return fileSize(path == null ? null : new File(path));
    }

    /**
     * 获取文件大小（单位：字节），如果文件不存在，则返回 {@code -1}
     *
     * @param file 文件路径
     * @return 返回文件大小
     */
    public static long fileSize(File file) {
        return file != null && file.exists() ? file.length() : -1L;
    }

    /**
     * 重命名文件
     *
     * @param oldPath 旧路径
     * @param newPath 新路径
     * @return 是否重命名成功
     */
    public static boolean rename(String oldPath, String newPath) {
        return new File(oldPath).renameTo(new File(newPath));
    }

    public static boolean delete(String filePath) throws Exception {
        return delete(new File(filePath));
    }

    public static boolean delete(File file) throws Exception {
        boolean exists = file.exists();
        if (exists) {
            file.delete();
            exists = file.exists();
            if (exists) {
                Try.sleep(50);      // 删除失败，重试一次
                return file.delete();
            }
        }

        return true;
    }

}
