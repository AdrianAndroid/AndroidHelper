package com.imooc.router.gradle;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Time:2021/7/22 19:21
 * Author:
 * Description:
 */
public class JsonRootBean {


    public static Type getType() {
        return new TypeToken<List<JsonRootBean>>() {
        }.getType();
    }

    private String url;
    private String description;
    private String realPath;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getRealPath() {
        return realPath;
    }

    @Override
    public String toString() {
        return "JsonRootBean{" +
                "url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", realPath='" + realPath + '\'' +
                '}';
    }
}