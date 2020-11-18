package com.xctech.log.core;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomJsonLogbackLayout extends LayoutBase<ILoggingEvent> {
    @Override
    public String doLayout(ILoggingEvent event) {
        Map<String, String> mdcPropertyMap = event.getMDCPropertyMap();
        Map<String, String> map = new LinkedHashMap<>(mdcPropertyMap);
        return new JsonLoggerInfo(
                event.getLevel().levelStr,
                event.getLoggerName(),
                null,
                event.getThreadName(),
                event.toString(),
                map,
                event.getThrowableProxy() != null ? event.getThrowableProxy().getMessage() : null,
                event.getTimeStamp()
        ).toString() + "\n";
    }
}
