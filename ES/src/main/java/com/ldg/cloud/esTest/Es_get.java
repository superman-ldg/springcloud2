package com.ldg.cloud.esTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldg.cloud.pojo.User;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class Es_get {

    public static void main(String[] args) throws IOException {
        //创建Es客户端
        RestHighLevelClient levelClient=new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );
        GetRequest getRequest = new GetRequest();
        getRequest.index("user").id("1001").type("ldg");
        GetResponse documentFields = levelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(documentFields.getSourceAsString());
        levelClient.close();

    }
}
