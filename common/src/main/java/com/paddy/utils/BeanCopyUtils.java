package com.paddy.utils;

import com.paddy.domain.entity.Article;
import com.paddy.domain.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils() {

    }

    public static<V> V copyBean(Object source, Class<V> clazz) {
        // 创建目标对象
        V result;
        try {
            result = clazz.newInstance();
            // 实现属性
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 返回结果
        return result;
    }

    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> clazz){
        //不使用for循环，使用stream流进行转换
        return list.stream()
                .map(o -> copyBean(o, clazz))
                //把结果转换成集合
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("abc");

        HotArticleVo hotArticleVo = copyBean(article, HotArticleVo.class);
        System.out.println(hotArticleVo);
    }
}
