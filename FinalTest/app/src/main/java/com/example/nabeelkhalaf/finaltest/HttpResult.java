package com.example.nabeelkhalaf.finaltest;

public class HttpResult {
    private int _statusCode;
    private String _responseBody;

    public HttpResult(final int statusCode, String responseBody) {
        _statusCode = statusCode;
        _responseBody = responseBody;
    }

    public int getStatusCode() {
        return _statusCode;
    }

    public String getResponseBody() {
        return _responseBody;
    }
}
