# logback
logback custom
自定义Json输出日志格式
##v1.0.0.RELEASE
    完成Json日志格式改造
##v1.1.0.RELEASE
    整合Spring-cloud 实现分布式链路追踪，且保存分布式下服务链路的请求信息
```json
    {
      "app_timestamp": "2020-12-19T22:41:37.738",
      "level": "INFO",
      "logEventType": "accessLog",
      "logger": "com.alex.logback.provider.controller.ProviderController",
      "map":{
        "http_accept": "*/*",
        "http_content_length": "-1",
        "http_elapsed_time": "-1",
        "http_location": "zh_CN",
        "http_method": "GET",
        "http_path": "/provider",
        "http_remote_host": "192.168.74.1",
        "http_session": "59B1DA17A8B5B44C86016D511DFFCA87",
        "http_status_code": "200",
        "http_user_agent": "Java/1.8.0_202",
        "http_version": "HTTP/1.1"
      },
      "message": "[INFO] aaa",
      "parentId": "f90d87d4989d8da4",
      "spanId": "20e3c486545a2f3e",
      "thread": "http-nio-8001-exec-1",
      "traceId": "f90d87d4989d8da4"
    }
```
