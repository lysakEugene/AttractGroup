package com.eugene.lysak.attractgrouptest.Rest;

import com.eugene.lysak.attractgrouptest.Models.Heroes;

/**
 * Created by zeka on 05.04.16.
 */
public class RestMethodResult {

    private int statusCode = 0;
    private Heroes resource;

    public RestMethodResult(int statusCode, Heroes resource) {
        this.statusCode = statusCode;
        this.resource = resource;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Heroes getResource() {
        return resource;
    }

}
