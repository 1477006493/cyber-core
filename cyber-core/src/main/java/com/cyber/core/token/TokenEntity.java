package com.cyber.core.token;

import lombok.Data;

/**
 * 返回Token映射的实体
 * @author smy
 * @date 2022年5月13日11:24:59
 */
@Data
public class TokenEntity {
    private Long id;
    private String roles;
}