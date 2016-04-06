package com.eugene.lysak.attractgrouptest.Rest;

import java.net.URI;

/**
 * Created by zeka on 05.04.16.
 */
public class Request {

    private URI requestUri;
    private byte[] body;

    public Request( URI requestUri, byte[] body) {
        this.requestUri = requestUri;
        this.body = body;
    }


    public URI getRequestUri() {
        return requestUri;
    }


    public byte[] getBody() {
        return body;
    }



}
