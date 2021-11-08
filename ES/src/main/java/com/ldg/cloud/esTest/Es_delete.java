package com.ldg.cloud.esTest;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.io.IOException;

public class Es_delete {

    public static void main(String[] args) throws IOException {
        //创建Es客户端
        RestHighLevelClient levelClient=new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );

        DeleteIndexRequest request=new DeleteIndexRequest("user");
        AcknowledgedResponse delete = levelClient.indices().delete(request, RequestOptions.DEFAULT);
        levelClient.close();

    }
}
