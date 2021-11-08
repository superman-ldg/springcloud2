import org.apache.http.HttpHost;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class Test {

    public static void main(String[] args) throws IOException {
        //创建Es客户端
        RestHighLevelClient levelClient=new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );

//        CreateIndexRequest request=new CreateIndexRequest("user");
//        CreateIndexResponse createIndexResponse = levelClient.indices().create(request, RequestOptions.DEFAULT);
//
//        boolean acknowledged = createIndexResponse.isAcknowledged();
//        System.out.println("索引操作"+acknowledged);

        levelClient.close();

    }


}
