package com.paddy.constants;

/**
 * @author 35238
 * @date 2023/7/19 0019 19:14
 */
//字面值(代码中的固定值)处理，把字面值都在这里定义成常量
public class SystemCanstants {

    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;

    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * 文章列表当前查询页数
     */
    public static final int ARTICLE_STATUS_CURRENT = 1;

    /**
     * 文章列表每页显示的数据条数
     */
    public static final int ARTICLE_STATUS_SIZE = 10;

    public static final String STATUS_NORMAL = "0";
    /**
     * 友链审核通过
     */
    public static final String LINK_STATUS_NORMAL = "0";

    public static final String ARTICLE_COMMENT = "0";
    /**
     * 评论类型为：文章评论
     */
    public static final String LINK_COMMENT = "1";
    /**
     * 评论类型为：友链评论
     */
}