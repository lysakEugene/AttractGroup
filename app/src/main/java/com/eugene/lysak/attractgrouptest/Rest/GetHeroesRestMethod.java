package com.eugene.lysak.attractgrouptest.Rest;

import com.eugene.lysak.attractgrouptest.Models.Heroes;

import org.json.JSONArray;

import java.net.URI;


/**
 * Created by zeka on 05.04.16.
 */
public class GetHeroesRestMethod {


    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final URI HEROES_URI = URI.create("http://others.php-cd.attractgroup.com/test.json");


    public GetHeroesRestMethod(){

    }

    private Request buildRequest() {
        return new Request(HEROES_URI,null);
    }

    private Heroes parseResponseBody(String responseBody) throws Exception {

        JSONArray json = new JSONArray(responseBody);
        return new Heroes(json);

    }

    public RestMethodResult execute() {
        Request request = buildRequest();
        Response response = doRequest(request);
        return buildResult(response);
    }

    private Response doRequest(Request request) {
        RestClient client = new RestClient();
        return client.execute(request);
    }

    private RestMethodResult buildResult(Response response) {

        int status = response.status;
        String responseBody = null;
        Heroes resource = null;

        try {
            responseBody = new String(response.body, DEFAULT_ENCODING);
            resource = parseResponseBody(responseBody);
        } catch (Exception ex) {
            status = 512;
        }
        return new RestMethodResult(status, resource);
    }
}
