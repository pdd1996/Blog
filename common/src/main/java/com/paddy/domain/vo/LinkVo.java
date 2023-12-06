package com.paddy.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_link")
public class LinkVo {
    private Long id;

    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;
}
