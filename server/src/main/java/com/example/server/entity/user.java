package com.example.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/*
Lombok插件的使用：
    @Setter 注解在类或字段，注解在类时为所有字段生成setter方法，注解在字段上时只为该字段生成setter方法。
    @Getter 使用方法同上，区别在于生成的是getter方法。
    @ToString 注解在类，添加toString方法。
    @EqualsAndHashCode 注解在类，生成hashCode和equals方法。
    @NoArgsConstructor 注解在类，生成无参的构造方法。
    @RequiredArgsConstructor 注解在类，为类中需要特殊处理的字段生成构造方法，比如final和被@NonNull注解的字段。
    @AllArgsConstructor 注解在类，生成包含类中所有字段的构造方法。
    @Data 注解在类，生成setter/getter、equals、canEqual、hashCode、toString方法，如为final属性，则不会为该属性生成setter方法。
    @Slf4j 注解在类，生成log变量，严格意义来说是常量。private static final Logger log = LoggerFactory.getLogger(UserController.class);
*/

@Data //
@NoArgsConstructor  //无参构造
@AllArgsConstructor //有参构造
public class user {
    private int id;
    private String username;
    private String password;
    private String profilePhoto;//头像地址
    private String contact;//联系人列表

}
