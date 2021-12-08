import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldg.cloud.MainApplication;
import com.ldg.cloud.dao.UserMapper;
import com.ldg.cloud.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class MybatisPlusApplicationTests {
    // 继承了BaseMapper，所有的方法都来自己父类
// 我们也可以编写自己的扩展方法！
    @Autowired
    private UserMapper userMapper;
    @Test
    public void contextLoads() {
// 参数是一个 Wrapper ，条件构造器，这里我们先不用 null
// 查询全部用户
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }
    @Test
    public void testInsert(){
        User user=new User();
        user.setName("梁登光2");
        user.setAge(88);
        user.setEmail("1126458841@qq.com");
        int insert = userMapper.insert(user);
        //insert受影响的行数
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void updata(){
        User user=new User();
        user.setName("梁登光吃屎把你");
        user.setId(1465194495600377857L);
        user.setAge(8888);
        user.setEmail("1126458841@qq.com");
        userMapper.updateById(user);
        //insert受影响的行数
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }
    // 测试乐观锁成功！多线程下
    @Test
    public void testOptimisticLocker(){
// 1、查询用户信息
        User user = userMapper.selectById(1465194495600377857L);
// 2、修改用户信息
        user.setName("量的呃呃");
        user.setEmail("1126458841@qq.com");
// 3、执行更新操作
        userMapper.updateById(user);
    }
    // 测试乐观锁失败！多线程下
    @Test
    public void testOptimisticLocker2(){
// 线程 1
        User user = userMapper.selectById(1465194495600377857L);
        user.setName("梁灯光卡很久");
        user.setEmail("1126458841@qq.com");
// 模拟另外一个线程执行了插队操作
        User user2 = userMapper.selectById(1L);
        user2.setName("梁灯光卡很久获得更加符合");
        user2.setEmail("1126458841@qq.com");
        userMapper.updateById(user2);
// 自旋锁来多次尝试提交！
        userMapper.updateById(user); // 如果没有乐观锁就会覆盖插队线程的值！
    }


    @Test
    public void page(){
        //当前页，每页的条数
        Page<User> page= new Page<>(1,2);
        Page<User> userPage = userMapper.selectPage(page, null);
        List<User> records = userPage.getRecords();
        records.forEach(System.out::println);
    }
    @Test
    public void delete(){
        userMapper.deleteById(1L);

    }

    @Test
    void contextLoads1() {
// 查询name不为空的用户，并且邮箱不为空的用户，年龄大于等于12
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .isNotNull("name")
                .isNotNull("email")
                .ge("age",12);
        userMapper.selectList(wrapper).forEach(System.out::println); // 和我们刚才学习的map对比一下
    }

    @Test
    void test2(){
// 查询名字狂神说
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name","狂神说");
        User user = userMapper.selectOne(wrapper); // 查询一个数据，出现多个结果使用List或者 Map
        System.out.println(user);
    }

    @Test
    void test3(){
  // 查询年龄在 20 ~ 30 岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age",20,30); // 区间
        Integer count = userMapper.selectCount(wrapper);// 查询结果数
        System.out.println(count);
    }

    // 模糊查询
    @Test
    void test4(){
// 查询年龄在 20 ~ 30 岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
// 左和右 t%
        wrapper
                .notLike("name","e")
                .likeRight("email","t");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    // 模糊查询
    @Test
    void test5(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
// id 在子查询中查出来
        wrapper.inSql("id","select id from user where id<3");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }

    //测试六
    @Test
    void test6(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
// 通过id进行排序
        wrapper.orderByAsc("id");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }


}

