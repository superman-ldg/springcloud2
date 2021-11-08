package com.ldg.cloud.esTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldg.cloud.pojo.User;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class Es_insert {

    public static void main(String[] args) throws IOException {
        //创建Es客户端
        RestHighLevelClient levelClient=new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );

        IndexRequest insertRequest= new IndexRequest();
        insertRequest.index("user").id("1001");
        insertRequest.type("ldg");

        User user = new User();
        user.setName("梁登光");
        user.setPassword("123456");
        user.setId(1);

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(user);

        insertRequest.source(s, XContentType.JSON);
        System.out.println(s);
        IndexResponse index = levelClient.index(insertRequest, RequestOptions.DEFAULT);
        System.out.println("索引是:"+index.getIndex());


        levelClient.close();

    }
}
