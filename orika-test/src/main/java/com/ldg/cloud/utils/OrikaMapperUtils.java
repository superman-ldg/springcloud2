package com.ldg.cloud.utils;
//
//import com.google.common.cache.Cache;
//import com.google.common.cache.CacheBuilder;
//import ma.glasnost.orika.MapperFacade;
//import ma.glasnost.orika.MapperFactory;
//import ma.glasnost.orika.impl.DefaultMapperFactory;
//import ma.glasnost.orika.metadata.ClassMapBuilder;
//
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//
///**
// * 基于Orika的属性映射工具类 - Orika是一个简单、快速的JavaBean拷贝框架，它能够递归地将数据从一个JavaBean复制到另一个JavaBean,Orika底层采用了javassist类库生成Bean映射的字节码
// * <p>
// * 1. 支持模糊类型映射(如:Integer <-> Long, Integer <-> String)
// * 2. 支持对象中的list映射
// * <p>
// * 性能：手动拷贝 > cglib beanCopier > orika mapper > spring beanUtils > apache commons beanUtils
// *
// */
//public class OrikaMapperUtils {
//
//    /**
//     * 默认字段工厂
//     */
//    private static MapperFactory MAPPER_FACTORY =new DefaultMapperFactory.Builder().build();
//
//    /**
//     * 默认字段实例
//     */
//    private static MapperFacade MAPPER_FACADE =MAPPER_FACTORY.getMapperFacade();
//
//
//    /**
//     * 实例缓存
//     */
//    private final static Cache<Object, Object> CACHE_MAPPER_FACADE_MAP = CacheBuilder.newBuilder().maximumSize(100)
//            .concurrencyLevel(10)
//            .expireAfterWrite(60L, TimeUnit.SECONDS)
//            .recordStats()
//            .build();
//
//    /**
//     * 映射实体（默认字段）
//     *
//     * @param data    源数据（对象）
//     * @param toClass 映射类对象
//     * @return 映射类对象
//     */
//    public static <E, T> E map(T data, Class<E> toClass) {
//        return MAPPER_FACADE.map(data, toClass);
//    }
//
//    /**
//     * 映射实体（自定义配置）
//     *
//     * @param data      源数据（对象）
//     * @param toClass   映射类对象
//     * @param configMap 自定义配置
//     *                  - 用于处理源对象与目标对象字段名不匹配的情况 - 格式:{key=源对象中的字段名 : value=目标对象中的字段名}
//     * @return 映射类对象
//     */
//    public static <E, T> E map(T data, Class<E> toClass, Map<String, String> configMap) {
//        MapperFacade mapperFacade = getMapperFacade(toClass, data.getClass(), configMap);
//        return mapperFacade.map(data, toClass);
//    }
//
//    /**
//     * 映射集合（默认字段）
//     *
//     * @param data    源数据（集合）
//     * @param toClass 映射类对象
//     * @return 映射类对象(集合)
//     */
//    public static <E, T> List<E> mapAsList(Collection<T> data, Class<E> toClass) {
//        if (Objects.isNull(data)) {
//            return Collections.emptyList();
//        } else {
//            return MAPPER_FACADE.mapAsList(data, toClass);
//        }
//    }
//
//
//    /**
//     * 映射集合（自定义配置）
//     *
//     * @param data      源数据（集合）
//     * @param toClass   映射类
//     * @param configMap 自定义配置
//     *                  - 用于处理源对象与目标对象字段名不匹配的情况 - 格式:{key=源对象中的字段名 : value=目标对象中的字段名}
//     * @return 映射类对象(集合)
//     */
//    public static <E, T> List<E> mapAsList(Collection<T> data, Class<E> toClass, Map<String, String> configMap) {
//        if (Objects.isNull(data)) {
//            return Collections.emptyList();
//        }
//        Optional<T> first = data.stream().findFirst();
//        if (first.isPresent()) {
//            MapperFacade mapperFacade = getMapperFacade(toClass, first.get().getClass(), configMap);
//            return mapperFacade.mapAsList(data, toClass);
//        } else {
//            return Collections.emptyList();
//        }
//    }
//
//    /**
//     * 获取自定义映射
//     *
//     * @param toClass   映射类
//     * @param dataClass 数据映射类
//     * @param configMap 自定义配置
//     * @return 映射类对象
//     */
//    private static <E, T> MapperFacade getMapperFacade(Class<E> toClass, Class<T> dataClass, Map<String, String> configMap) {
//        String mapKey = dataClass.getCanonicalName() + "_" + toClass.getCanonicalName();
//        Object o = CACHE_MAPPER_FACADE_MAP.getIfPresent(mapKey);
//        MapperFacade mapperFacade;
//        if (Objects.isNull(o)) {
//            MapperFactory factory = new DefaultMapperFactory.Builder().build();
//            ClassMapBuilder classMapBuilder = factory.classMap(dataClass, toClass);
//            configMap.forEach(classMapBuilder::field);
//            classMapBuilder.byDefault().register();
//            mapperFacade = factory.getMapperFacade();
//            CACHE_MAPPER_FACADE_MAP.put(mapKey, mapperFacade);
//        } else {
//            mapperFacade = (MapperFacade) o;
//        }
//        return mapperFacade;
//    }
//
//}