package com.ldg.cloud.test;
//
//import com.ldg.cloud.entriy.Role;
//import com.ldg.cloud.entriy.User;
//import com.ldg.cloud.entriy.UserDto;
//import com.ldg.cloud.utils.OrikaMapperUtils;
//
//import java.util.*;
//
//
//public class OrikaMapperTest {
//    public static void main(String[] args) {
//        User userA = new User().setId("111").setName("张三").setAge(20);
//        userA.setUserList(Arrays.asList(new User[]{new User().setId("121").setName("张三1").setAge(21), new User().setId("122").setName("张三2").setAge(22)}));
//        userA.setRoles(Arrays.asList(new Role[]{new Role().setId(1).setName("老师"), new Role().setId(2).setName("班主任")}));
//        Map<String, String> map = new HashMap<String, String>() {
//            {
//                put("roles", "roleList");
//            }
//        };
//        UserDto userDto = OrikaMapperUtils.map(userA, UserDto.class, map);
//        System.out.println("无configMap的对象映射(源):" + userA);
//        System.out.println("无configMap的对象映射(目标):" + userDto);
//
//        Map<String, String> configMap = new HashMap<String, String>() {
//            {
//                put("name", "userName");
//            }
//        };
//        UserDto userDto2 = OrikaMapperUtils.map(userA, UserDto.class, configMap);
//        System.out.println("带configMap的对象映射(源):" + userA);
//        System.out.println("带configMap的对象映射(目标):" + userDto2);
//        //A对象
//        List<User> listA = Arrays.asList(new User().setId("123").setName("张三").setAge(20));
//        List<UserDto> userDtos = OrikaMapperUtils.mapAsList(listA, UserDto.class);
//        System.out.println("无configMap的list映射(源):" + listA);
//        System.out.println("无configMap的list映射(目标):" + userDtos);
//        List<UserDto> userDtos2 = OrikaMapperUtils.mapAsList(listA, UserDto.class, configMap);
//        System.out.println("带configMap的list映射(源):" + listA);
//        System.out.println("带configMap的list映射(目标):" + userDtos2);
//    }
//}