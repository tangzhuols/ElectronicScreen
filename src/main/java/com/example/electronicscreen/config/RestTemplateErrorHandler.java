package com.example.electronicscreen.config;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * Created by：xiaosui
 * Company：Kengic
 * Date：2021/4/23
 * Time：13:08
 * description: 对于服务器端的异常不处理->重写 restTemplate对服务器错误的处理,此处不处理,全部放到body中
 */
public class RestTemplateErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {

    }
}
