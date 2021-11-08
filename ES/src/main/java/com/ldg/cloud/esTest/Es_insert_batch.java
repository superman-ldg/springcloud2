package com.ldg.cloud.esTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldg.cloud.pojo.User;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class Es_insert_batch {

    public static void main(String[] args) throws IOException {
        //创建Es客户端
        RestHighLevelClient levelClient=new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );
        BulkRequest bulkRequest = new BulkRequest();
        User user = new User();user.setName("梁登光3");user.setPassword("123456");user.setId(1);
        User user2 = new User();user.setName("梁登光4");user.setPassword("123456");user.setId(1);
        User user3 = new User();user.setName("梁登光5");user.setPassword("123456");user.setId(1);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(user);
        String s2 = objectMapper.writeValueAsString(user2);
        String s3 = objectMapper.writeValueAsString(user3);



        IndexRequest source1 = new IndexRequest().index("user").type("ldg2").id("1002").source(s,XContentType.JSON);
        IndexRequest source2 = new IndexRequest().index("user").type("ldg3").id("1003").source(s2,XContentType.JSON);
        IndexRequest source3 = new IndexRequest().index("user").type("ldg4").id("1004").source(s3,XContentType.JSON);

        bulkRequest.add(source1).add(source3).add(source2);
        BulkResponse bulk = levelClient.bulk(bulkRequest,RequestOptions.DEFAULT);

        System.out.println(bulk.getTook());
        System.out.println(bulk.getItems());

        levelClient.close();

    }
}
