package com.xctech.log.core;

import brave.Tracer;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;
import com.alibaba.fastjson.JSON;
import com.xctech.log.core.constant.LogConstant;
import com.xctech.log.core.constant.RequestMappingConstant;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;


@Component
public class CustomJsonLogbackLayout extends LayoutBase<ILoggingEvent> implements ApplicationContextAware {
    private Tracer tracer;

    @Override
    public String doLayout(ILoggingEvent event) {
        Map<String, String> mdcPropertyMap = event.getMDCPropertyMap();
        Map<String, String> map = new LinkedHashMap<>();

        map.put(RequestMappingConstant.HTTP_ACCEPT, mdcPropertyMap.get(RequestMappingConstant.HTTP_ACCEPT));
        map.put(RequestMappingConstant.HTTP_CONTENT_LENGTH, mdcPropertyMap.get(RequestMappingConstant.HTTP_CONTENT_LENGTH));
        map.put(RequestMappingConstant.HTTP_CONTENT_TYPE, mdcPropertyMap.get(RequestMappingConstant.HTTP_CONTENT_TYPE));
        map.put(RequestMappingConstant.HTTP_ELAPSED_TIME, mdcPropertyMap.get(RequestMappingConstant.HTTP_ELAPSED_TIME));
        map.put(RequestMappingConstant.HTTP_LOCATION, mdcPropertyMap.get(RequestMappingConstant.HTTP_LOCATION));
        map.put(RequestMappingConstant.HTTP_METHOD, mdcPropertyMap.get(RequestMappingConstant.HTTP_METHOD));
        map.put(RequestMappingConstant.HTTP_PATH, mdcPropertyMap.get(RequestMappingConstant.HTTP_PATH));
        map.put(RequestMappingConstant.HTTP_QUERY, mdcPropertyMap.get(RequestMappingConstant.HTTP_QUERY));
        map.put(RequestMappingConstant.HTTP_REMOTE_HOST, mdcPropertyMap.get(RequestMappingConstant.HTTP_REMOTE_HOST));
        map.put(RequestMappingConstant.HTTP_SESSION, mdcPropertyMap.get(RequestMappingConstant.HTTP_SESSION));
        map.put(RequestMappingConstant.HTTP_STATUS_CODE, mdcPropertyMap.get(RequestMappingConstant.HTTP_STATUS_CODE));
        map.put(RequestMappingConstant.HTTP_USER_AGENT, mdcPropertyMap.get(RequestMappingConstant.HTTP_USER_AGENT));
        map.put(RequestMappingConstant.HTTP_VERSION, mdcPropertyMap.get(RequestMappingConstant.HTTP_VERSION));
        String parentIdString = null;
        String spanIdString = null;
        String traceIdString = null;
        if (tracer!=null) {
            parentIdString = tracer.currentSpan().context().parentIdString();
            traceIdString = tracer.currentSpan().context().traceIdString();
            spanIdString = tracer.currentSpan().context().spanIdString();
        }
        JsonLoggerInfo jsonLoggerInfo = new JsonLoggerInfo(
                event.getLevel().levelStr,
                event.getLoggerName(),
                mdcPropertyMap.getOrDefault(LogConstant.LOG_EVENT_TYPE,"appLog"),
                event.getThreadName(),
                event.toString(),
                map,
                event.getThrowableProxy() != null ? event.getThrowableProxy().getMessage() : null,
                event.getTimeStamp(),
                mdcPropertyMap.get("X-B3-ParentSpandId"),
                mdcPropertyMap.get("spanId"),
                mdcPropertyMap.get("traceId")
        );
        String prettyFastJsonStr = getPrettyFastJsonStr(jsonLoggerInfo);
        return prettyFastJsonStr + "\n";
    }

    private String getPrettyFastJsonStr(Object object) {
        String data = JSON.toJSONString(object, true);
        data = data.replace("\":\"","\": \"");
        data = data.replace("\t","  ");
        return data;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.tracer = applicationContext.getBean(Tracer.class);
    }
}
