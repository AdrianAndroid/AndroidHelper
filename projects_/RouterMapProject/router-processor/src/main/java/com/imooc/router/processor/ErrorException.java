package com.imooc.router.processor;

import java.lang.annotation.Annotation;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

/**
 * Time:2021/7/23 14:05
 * Author:
 * Description:
 */
public class ErrorException implements SupportedSourceVersion {
    final String v;

    public ErrorException(String v) {
        this.v = v;
    }

    @Override
    public SourceVersion value() {
        return SourceVersion.RELEASE_6;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return ErrorException.class;
    }
}
