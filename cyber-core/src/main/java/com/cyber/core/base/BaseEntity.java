package com.cyber.core.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 基础的类，所有的entity都继承该类
 *
 * @author smy
 * @date 2022年5月13日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseEntity extends BaseMethod implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("创建人")
    private Long createUser;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("更新人")
    private Long updateUser;

    @ApiModelProperty("业务状态,默认为1")
    @TableField(fill = FieldFill.INSERT)
    private Integer status;

    @TableLogic(value="0",delval="1")
    @ApiModelProperty("是否已删除")
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;
}