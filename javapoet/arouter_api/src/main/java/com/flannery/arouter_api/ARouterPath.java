package com.flannery.arouter_api;

import com.flannery.arouter_annotation.bean.RouterBean;

import java.util.Map;

public interface ARouterPath {

    Map<String, RouterBean> getPathMap();

}
