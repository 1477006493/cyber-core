package com.cyber.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cyber
 * @Description 文件相关
 * @date 2022/5/17 11:39
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 文件访问地址
     */
    @ApiModelProperty("文件访问地址")
    private String url;

    /**
     * 文件名称
     */
    @ApiModelProperty("文件名称")
    private String fileName;

    /**
     * 本地服务器存储文件的路径
     */
    @ApiModelProperty("本地服务器存储文件的路径")
    private String localPath;

    /**
     * 文件类型后缀
     */
    @ApiModelProperty("文件类型后缀")
    private String suffix;

}