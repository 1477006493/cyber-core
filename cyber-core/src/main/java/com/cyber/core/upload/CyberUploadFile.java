package com.cyber.core.upload;

import com.cyber.core.dto.FileDTO;
import com.cyber.core.enums.Code;
import com.cyber.core.exception.BaseException;
import com.cyber.core.properties.FileProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author cyber
 * 使用springmvc(springboot)文件上传的工具类
 */
@Slf4j
@Component
public class CyberUploadFile {

    @Resource
    private FileProperties fileProperties;

    /**
     * 文件上传
     *
     * @param file file
     * @return FileDTO
     */
    public FileDTO uploadFileToLocal(MultipartFile file) {
        String directory = getDirectory();
        //创建存储路径的文件夹
        File dir = new File(fileProperties.getLocalFilePath() + directory);
        //如果该路径不存在，就创建给路径的文件夹
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //获取上传文件的s后缀格式-即截取该类型文件最后一个.之后的内容
        String filename = file.getOriginalFilename();

        String suffix1 = ".";
        if (filename == null || !filename.contains(suffix1)) {
            throw new BaseException(Code.FILE_UPLOAD_FAIL);
        }
        String suffix = filename.substring(filename.lastIndexOf("."));
        String fileRealName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(suffix1));

        //给需要上传的文件设定唯一的名称--newFileName为上传的文件名
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
        //创建该文件
        File newFile = new File(fileProperties.getLocalFilePath() + directory + newFileName);
        log.info("上传的文件路径:{}", fileProperties.getLocalFilePath() + directory + newFileName);

        try {
            //将上传的文件内容复制到newFile
            file.transferTo(newFile);
            //得到上传该文件的访问路径
            String url = fileProperties.getBaseUrl() + directory + newFileName;
            //将信息封装
            FileDTO fileDTO = new FileDTO();
            fileDTO.setUrl(url);
            fileDTO.setFileName(fileRealName);
            fileDTO.setLocalPath(fileProperties.getLocalFilePath() + directory + newFileName);
            return fileDTO;
        } catch (Exception e) {
            log.warn("上传失败", e);
            throw new BaseException(Code.ERROR);
        }
    }

    /**
     * 写入文件
     *
     * @param content 文件内容
     * @param suffix  后缀
     * @return FileDTO
     */
    public FileDTO createFileAndWrite(String content, String suffix) {
        //设定上传的文件路径，使用时间格式
        String directory = getDirectory();
        //创建存储路径的文件夹
        File dir = new File(fileProperties.getLocalFilePath() + directory);
        //如果该路径不存在，就创建给路径的文件夹
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //给需要上传的文件设定唯一的名称--newFileName为上传的文件名
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
        //创建该文件
        File newFile = new File(fileProperties.getLocalFilePath() + directory + newFileName);
        //写文件
        try {
            BufferedWriter fWriter = new BufferedWriter
                    (new OutputStreamWriter
                            (new FileOutputStream(newFile, true), StandardCharsets.UTF_8));
            //FileWriter fWriter = new FileWriter(newFile, true);
            fWriter.write(content + "\r\n");
            fWriter.flush();
            fWriter.close();
        } catch (Exception e) {
            throw new BaseException(Code.FILE_UPLOAD_FAIL);
        }
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileName(newFileName);
        fileDTO.setLocalPath(directory + newFileName);
        fileDTO.setUrl(fileProperties.getBaseUrl() + directory + newFileName);
        return fileDTO;
    }

    /**
     * 读取本地文件内容
     *
     * @param localPath 本地文件路径
     * @return 读取到的文件内容
     */
    public String readeLocalFileContent(String localPath) {
        File file = new File(localPath);
        StringBuilder result = new StringBuilder();
        try {
            // 构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            // 使用readLine方法，一次读一行
            while ((s = br.readLine()) != null) {
                result.append(System.lineSeparator()).append(s);
            }
            br.close();
        } catch (Exception e) {
            throw new BaseException(Code.FILE_UPLOAD_FAIL);
        }
        return result.toString();
    }


    /**
     * 文件上传
     *
     * @param file 需要上传的文件
     * @return Map<String, Object>
     */
    @Deprecated
    public Map<String, Object> uploadFile(MultipartFile file) {
        String directory = getDirectory();
        //创建存储路径的文件夹
        File dir = new File(fileProperties.getLocalFilePath() + directory);
        //如果该路径不存在，就创建给路径的文件夹
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //获取上传文件的后缀格式-即截取该类型文件最后一个.之后的内容
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isEmpty(originalFilename)){
            throw new BaseException(Code.FILE_UPLOAD_FAIL);
        }
        String suffix = originalFilename.substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = originalFilename.substring(0, file.getOriginalFilename().lastIndexOf("."));
        //给需要上传的文件设定唯一的名称--newFileName为上传的文件名
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
        //创建该文件
        File newFile = new File(fileProperties.getLocalFilePath() + directory + newFileName);
        log.info("上传的文件路径:{}", fileProperties.getLocalFilePath() + directory + newFileName);

        try {
            //将上传的文件内容复制到newFile
            file.transferTo(newFile);
            //得到上传该文件的访问路径
            String url = fileProperties.getBaseUrl() + directory + newFileName;
            //将信息封装
            Map<String, Object> map = new HashMap<>(16);
            map.put("url", url);
            map.put("fileName", fileName);
            //localPath 为本地服务器图片路径
            map.put("localPath", fileProperties.getLocalFilePath() + directory + newFileName);
            return map;
        } catch (Exception e) {
            log.warn("上传失败，{}", e.getMessage());
            //出错了就返回null
            return null;
        }
    }

    /**
     * 获取时间路径 ，格式为 DateConstant.PATTERN_NO_HH_MM_SS
     *
     * @return 获取时间路径
     */
    private String getDirectory() {
        //时间格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //设定上传的文件路径，使用时间格式
        return simpleDateFormat.format(new Date());
    }
}
