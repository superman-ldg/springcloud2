package com.ldg.cloud.utils;

import com.ldg.cloud.pojo.Person;
import com.ldg.cloud.pojo.Student;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Administrator
 *
 * MapperFactory
 *
 *
 */

public class MyOrikaUtils {

//    static {
//        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
//        mapperFactory.classMap(Student.class,Person.class)
//                .field("name", "name")
//                .field("age", "age")
//                .field("card","card")
//                .byDefault()
//                .register();
//        MapperFacade mapper = mapperFactory.getMapperFacade();
//
//
//    }

    @Autowired
    private static MapperFactory mapperFactory;

    @Test
    public static void main(String []args) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Student.class,Person.class)
                .field("name", "name")
                .field("age", "age")
                .field("card","card")
                .byDefault()
                .register();
        BoundMapperFacade<Student, Person> mapper = (BoundMapperFacade<Student, Person>) mapperFactory.getMapperFacade();

        Student student = new Student();
        student.setAge(10).setName("老大哥").setCard("11057").setId(1);
        Person map = mapper.map(student);
        System.out.println(map);

    }
}
