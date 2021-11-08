package com.ldg.cloud.esTest;

import org.apache.http.HttpHost;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class Es_update {


    public static void main(String[] args) throws IOException {
        //创建Es客户端
        RestHighLevelClient levelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("user").id("1001").type("ldg");
        updateRequest.doc(XContentType.JSON,"name","梁登光2");
        UpdateResponse update = levelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(update.getIndex());
        levelClient.close();

    }
}
