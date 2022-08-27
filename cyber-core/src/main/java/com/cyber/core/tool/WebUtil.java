package com.cyber.core.tool;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import com.cyber.log.bo.ReqLogBO;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

/**
 * @author cyber
 * @Description web相关工具类
 * @date 2022/5/18
 * @Version 1.0
 */
@Slf4j
public class WebUtil {
    public static final String USER = "user";

    public static final String USER_TOKEN = "user_token";

    private static String serverPort;

    public static String IP_REGION = "/ip2region.db";

    private static final String[] IP_HEADER_NAMES =
            new String[]{"X-Real-IP", "X-Forwarded-For"
                    , "Proxy-Client-IP", "WL-Proxy-Client-IP"};

    @Value("${server.port}")
    public void setServerPort(String serverPort) {
        WebUtil.serverPort = serverPort;
    }


    /**
     * 获取request对象
     *
     * @return request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        return attributes.getRequest();
    }

    /**
     * 项目的真实路径
     *
     * @return String
     */
    public static String getPjoPath() {
        HttpServletRequest request = getRequest();
        if (request == null || request.getServletContext() == null
                || StringUtils.isEmpty(request.getServletContext().getContextPath())) {
            return "";
        }
        // 项目的真实路径
        return StringUtils.replace(request.getServletContext().getContextPath(), "/", "\\");
    }


    /**
     * 获取客户端请求的路径名，如：/object/delObject
     *
     * @return String
     */
    public static String getServletPath() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return "";
        }
        // 项目的真实路径
        return request.getServletPath();
    }

    /**
     * 获取请求URI
     *
     * @return String
     */
    public static String getURI() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return "";
        }
        // 项目的真实路径
        return request.getRequestURI();
    }

    public static String getMethodType() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return "";
        }
        return request.getMethod();
    }

    /**
     * 获取服务器地址，如：localhost
     *
     * @return String
     */
    public static String getServerName() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return "";
        }
        return request.getServerName();
    }

    /**
     * 获取服务器端口，如8080
     *
     * @return String
     */
    public static String getServerPort() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return serverPort;
        }
        return request.getServerPort() + "";
    }

    /**
     * 获取用户ip，如：127.0.0.1
     *
     * @return String
     */
    public static String getRemoteAdr() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return "";
        }
        String remoteAddr = getRequest().getHeader(IP_HEADER_NAMES[0]);
        if (StringUtils.isNotBlank(remoteAddr)) {
            remoteAddr = getRequest().getHeader(IP_HEADER_NAMES[1]);
        } else if (StringUtils.isNotBlank(remoteAddr)) {
            remoteAddr = getRequest().getHeader(IP_HEADER_NAMES[2]);
        } else if (StringUtils.isNotBlank(remoteAddr)) {
            remoteAddr = getRequest().getHeader(IP_HEADER_NAMES[3]);
        }
        //0:0:0:0:0:0:0:1
        String ip = remoteAddr != null ? remoteAddr : request.getRemoteAddr();
        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            // 根据网卡取本机配置的IP
            try {
                InetAddress inet = InetAddress.getLocalHost();
                ip = inet.getHostAddress();
            } catch (UnknownHostException e) {
                ip = "127.0.0.1";
                log.error("根据网卡获取本机配置的IP异常", e);
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照','分割
        if (ip != null && ip.indexOf(",") > 0) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    /**
     * 获取项目的访问路径，如： localhost:8080/xx
     *
     * @return String
     */
    public static String getRequestUrl() {
        return getServerName() + ":" + getServerPort() + getServletPath();
    }

    /**
     * 获取客户端当前系统类型
     *
     * @return String
     */
    public static String getOsType() {
        UserAgent userAgent = getUserAgent();
        if (userAgent == null) {
            return "";
        }
        return userAgent.getOperatingSystem().getName();
    }

    private static UserAgent getUserAgent() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return UserAgent.parseUserAgentString(request.getHeader("user-agent"));
    }

    /**
     * 获取客户端当前系统类型
     *
     * @return String
     */
    public static String getBrowserType() {
        UserAgent userAgent = getUserAgent();
        if (userAgent == null) {
            return "";
        }
        return userAgent.getBrowser().toString();
    }

    /**
     * 封装WEB请求相关操作信息
     *
     * @param logBO logBO
     */
    public static void setBaseInfoLog(ReqLogBO logBO) {
        logBO.setStartTime(LocalDateTime.now());
        logBO.setRequestUri(getURI());
        logBO.setIp(getRemoteAdr());
        logBO.setUa(getBrowserType());
        logBO.setOsType(getOsType());
        logBO.setMethodType(getMethodType());
        HttpServletRequest request = getRequest();
        if (request != null && request.getAttribute(USER_TOKEN) != null) {
            Claims claims = (Claims) request.getAttribute(USER_TOKEN);
            String uid = claims.getId();
            logBO.setOptUser(uid);
        }
        logBO.setRequestPort(getServerPort());
        logBO.setRequestUrl(getRequestUrl());
    }

    /**
     * 根据IP地址获取城市
     *
     * @param ip ip地址
     * @return ip归属地
     */
    public static String getCityInfo(String ip) throws Exception {
        final String UNKNOWN = "未知";
        if (StringUtil.isBlank(ip)) {
            return UNKNOWN;
        }
        if (ip.startsWith("192.168") || "127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)){
            return "本地内网";
        }
        //读取jar包内的配置文件信息
        DbSearcher searcher = null;
        Method method = null;
        InputStream inputStream = null;
        try {
            ClassPathResource resource = new ClassPathResource(IP_REGION);
            inputStream = resource.getInputStream();
            byte[] dbBinStr = IoUtil.readBytes(inputStream);
            DbConfig config = new DbConfig();
            searcher = new DbSearcher(config, dbBinStr);
            // 查询算法memory，采用二进制方式初始化DBSearcher需要使用MEMORY_ALGORITYM，
            method = searcher.getClass().getMethod("memorySearch", String.class);
        } catch (IOException | DbMakerConfigException | IORuntimeException | NoSuchMethodException | SecurityException e) {
            log.error("getCityInfo is error", e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        if (method == null){
            return UNKNOWN;
        }
        return ((DataBlock) method.invoke(searcher, ip)).getRegion();
    }

}

