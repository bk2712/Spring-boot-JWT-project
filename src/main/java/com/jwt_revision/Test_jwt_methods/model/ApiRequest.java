package com.jwt_revision.Test_jwt_methods.model;

import lombok.Data;

import java.util.Map;

@Data
public class ApiRequest {

    private String url;
    private String method;
    private String body;
    private Map<String, String> headers;

    public ApiRequest(String url, String method, String body, Map<String, String> headers) {
        this.url = url;
        this.method = method;
        this.body = body;
        this.headers = headers;
    }

    public ApiRequest() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
