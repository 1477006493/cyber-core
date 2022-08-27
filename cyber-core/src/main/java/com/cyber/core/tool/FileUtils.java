package com.cyber.core.tool;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 文件处理工具类
 *
 * @author cyber
 */
public class FileUtils extends FileUtil {

    /**
     * 查询某个目录下的所有文件
     *
     * @param dir
     * @return
     * @throws IOException
     */
    public static List<File> searchAllFile(File dir) throws IOException {
        List<File> arrayList = new ArrayList<>();
        searchFiles(dir, arrayList);
        return arrayList;
    }


    /**
     * 递归获取某个目录下的所有文件
     *
     * @param dir
     * @param collector
     * @throws IOException
     */
    public static void searchFiles(File dir, List<File> collector) throws IOException {
        if (dir.isDirectory()) {
            File[] subFiles = dir.listFiles();
            for (int i = 0; i < Objects.requireNonNull(subFiles).length; i++) {
                searchFiles(subFiles[i], collector);
            }
        } else {
            collector.add(dir);
        }
    }


    /**
     * 创建文件
     *
     * @param dir
     * @param file
     * @return
     */
    public static File mkdir(String dir, String file) {
        if (dir == null) {
            throw new IllegalArgumentException("dir must be not null");
        }
        File result = new File(dir, file);
        if (result.getParentFile() != null) {
            result.getParentFile().mkdirs();
        }
        return result;
    }
}
