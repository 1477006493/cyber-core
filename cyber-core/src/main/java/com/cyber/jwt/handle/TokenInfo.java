package com.cyber.jwt.handle;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cyber
 * @date 2022年7月7日
 * @Version 1.0
 */
@Data
@ApiModel(
        description = "登录成功后的认证信息"
)
public class TokenInfo implements Serializable {
    @ApiModelProperty("令牌")
    private String accessToken;

    @ApiModelProperty("令牌类型")
    private String tokenType;

    @ApiModelProperty("刷新令牌")
    private String refreshToken;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("用户ID")
    @JsonSerialize(
            using = ToStringSerializer.class
    )
    private Long userId;

    @ApiModelProperty("过期时间")
    private Long expire;
}
