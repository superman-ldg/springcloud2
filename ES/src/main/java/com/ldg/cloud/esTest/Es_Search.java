package com.ldg.cloud.esTest;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.io.IOException;

public class Es_Search {

    public static void main(String[] args) throws IOException {
        //创建Es客户端
        RestHighLevelClient levelClient=new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );

        GetIndexRequest request=new GetIndexRequest("user");
        GetIndexResponse get = levelClient.indices().get(request, RequestOptions.DEFAULT);

        System.out.println(get.getAliases());
        System.out.println(get.getMappings());
        System.out.println(get.getSettings());
        levelClient.close();

    }
}
