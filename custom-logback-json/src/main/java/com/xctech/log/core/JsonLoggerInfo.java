package com.xctech.log.core;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Map;

/**
 * 输出的日志内容
 *
 * @Author yanbo
 * @Date 2020/11/12
 */
public class JsonLoggerInfo {

    /**
     * 日志级别
     */
    private String level;
    /**
     * 日志所在的类
     */
    private String logger;
    /**
     * 日志分类
     */
    private String logEventType;
    /**
     * 当前线程
     */
    private String thread;
    /**
     * 日志信息
     */
    private String message;
    /**
     * 栈帧输出
     */
    private String stackTrace;
    /**
     * 请求信息
     */
    private Map<String, String> map;
    /**
     * 日志时间
     */
    private String app_timestamp;
    /**
     * 日志时间
     */
    private String parentId;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    /**
     * 日志时间
     */
    private String spanId;
    /**
     * 日志时间
     */
    private String traceId;

    public JsonLoggerInfo(String level, String logger, String logEventType, String thread, String message, Map<String, String> map, String stackTrace, long timeMillis, String p, String s ,String t) {
        this.level = level;
        this.logger = logger;
        this.logEventType = logEventType;
        this.thread = thread;
        this.message = message;
        this.map = map;
        this.stackTrace = stackTrace;
        this.app_timestamp = DateFormatUtils.format(timeMillis, "yyyy-MM-dd'T'HH:mm:ss.SSS");
        this.parentId = p;
        this.spanId = s;
        this.traceId = t;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

    public String getLogEventType() {
        return logEventType;
    }

    public void setLogEventType(String logEventType) {
        this.logEventType = logEventType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getApp_timestamp() {
        return app_timestamp;
    }

    public void setApp_timestamp(String app_timestamp) {
        this.app_timestamp = app_timestamp;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}