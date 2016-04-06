package com.eugene.lysak.attractgrouptest.Rest;


/**
 * Created by zeka on 05.04.16.
 */
public class Response {

     //HTTP стату код
    public int status;
    //тело
    public byte[] body;

    protected Response(int status, byte[] body) {
        this.status = status;
        this.body = body;
    }

}
