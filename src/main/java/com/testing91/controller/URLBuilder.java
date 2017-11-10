package com.testing91.controller;

/**
 * Created by huzhiwei on 16/5/6.
 */

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tesla on 15/12/30.
 */
public class URLBuilder {
    private String site = "";
    private String target = "";
    private String fromUrl = "";
    private Map<String, Object> params = new HashMap<String, Object>();

    public URLBuilder(String site) {
        site = StringUtils.trimToEmpty(site);
        if (StringUtils.isBlank(site)) {
            this.site = "";
        }
    }

    public URLBuilder() {
        this.site = "";
    }

    public URLBuilder setTarget(String target) {
        this.target = StringUtils.removeEnd(target, "?");
        this.params = new HashMap<String, Object>();
        return this;
    }

    public URLBuilder fromRequest(HttpServletRequest request) {
        this.fromUrl = request.getRequestURL().toString();

        this.target = request.getServletPath();
        Enumeration parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement().toString();
            this.params.put(key, StringUtils.trimToEmpty(request.getParameter(key)));
        }
        return this;
    }

    public String getTarget() {
        return target;
    }

    public Object getParam(String key) {
        return this.params.get(key);
    }

    public URLBuilder addParam(String key, Object value) {
        if (StringUtils.isNotBlank(key) && value != null) {
            this.params.put(StringUtils.trim(key), value);
        }
        return this;
    }

    public URLBuilder removeParam(String key) {
        this.params.remove(StringUtils.trim(key));
        return this;
    }

    public URLBuilder removeAllParams() {
        this.params = new HashMap<String, Object>();
        return this;
    }

    //返回site和Path信息，无任何参数
    public String getUrlClean() {
//        StringBuilder builder = new StringBuilder(site);
//        if (!StringUtils.startsWith(target, "/")) {
//            builder.append("/");
//        }
//        builder.append(target);
//        if (!StringUtils.endsWith(target, "?")) {
//            builder.append("?");
//        }
//        return builder.toString();
        return fromUrl;
    }

    //返回带参数的完整
    public String getUrl() {
        StringBuilder builder = new StringBuilder(site);
        if (!StringUtils.startsWith(target, "/")) {
            builder.append("/");
        }
        builder.append(target);
        if (!StringUtils.endsWith(target, "?")) {
            builder.append("?");
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("&");
        }
        return builder.toString();
    }
}