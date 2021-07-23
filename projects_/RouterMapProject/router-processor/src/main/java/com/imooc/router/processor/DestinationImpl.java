package com.imooc.router.processor;

/**
 * Time:2021/7/23 16:17
 * Author: flannery
 * Description: Destination保存
 */
public class DestinationImpl {
    final String url;
    final String description;
    final String realPath;

    public DestinationImpl(String url, String description, String realPath) {
        this.url = url;
        this.description = description;
        this.realPath = realPath;
    }

    @Override
    public String toString() {
        return "DestinationImpl{" +
            "url='" + url + '\'' +
            ", description='" + description + '\'' +
            ", realPath='" + realPath + '\'' +
            '}';
    }
}
