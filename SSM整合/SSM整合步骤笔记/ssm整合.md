# SSM整合

## 目录结构

SSM框架开发目录结构

~~~~java
├──main
|  ├──java
|  |  └──com
|  |     └──web
|  |        ├── core
|  |        |  ├── entity                       //POJO包
|  |        |  ├── controller                   //控制器包
|  |        |  ├── service                      //Service接口包
|  |        |  |  └── serviceImpl               //service实现类包
|  |        |  ├── mapper                       //Mapper接口包
|  |        |  |  └── StudentMapper.java        //Mapper接口文件
|  |        |  └── util                         //工具包
|  ├── resources                                //资源文件夹（配置文件）
|  |  | // ==============================Spring相关配置==============================
|  |  ├──applicationContext.xml                 //Spring配置文件
|  |  | // =============================SpringMVC相关配置=============================
|  |  ├──spring-mvc.xml                         //springMVC配置文件
|  |  | // ==========================数据库, MyBatis相关配置===========================
|  |  ├──jdbc.properties                        //数据库配置文件
|  |  ├──mybatis-config.xml                     //mybatis配置文件
|  |  ├──spring-mybatis.xml                     //spring-mybatis整合配置
|  |  └── mapper                                //mapper.xml文件夹
|  |  |  └── StudentMapper.xml
|  |  | // =================================其他配置==================================
|  |  ├──log4j.properties                       //log4j配置文件
|  └── webapp                                   ///web应用部署根目录
|     ├──pages                                  //如果是前后端不分离项目 在这里放置html，jsp类型的前端页面
|     |  └── studentList.jsp
|     ├──static                                 //静态资源文件夹
|     |  ├──css
|     |  |  └── login.css
|     |  ├──images
|     |  |  ├──login-img.png
|     |  |  └── login_logo.png
|     |  └── js
|     |     └── JQuery.js
|     └── WEB-INF                             //Java的WEB应用的安全目录 用户在地址栏输入路径无法访问到WEB-INF的内容 必须通过服务端请求才能访问
|        └── web.xml                          //用于配置JavaWeb应用程序的部署信息
└──test
| ├── java                                   //测试Java代码，遵循和main目录包名相同的原则
└──pom.xml                                   //Maven项目管理文件
~~~~



## SSM项目开发流程

根据上面的项目目录结构可知，SSM项目开发流程为：

* 配置pom.xml 导入依赖
* 编写Spring配置文件
* 编写SpringMVC配置文件
* 编写数据库，MyBatis相关配置文件
* 编写web.xml配置文件
* 编写其它配置文件（如log4j）
* 代码开发
  * 业务代码开发
  * 其它
    * 统一异常处理
    * 拦截器
    * 声明式事务
    * AOP



## 01 配置文件

### 1) pom.xml文件

~~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>ssm_project</artifactId>
    <version>1.0-SNAPSHOT</version>
    <!--打包方式为war包-->
    <packaging>war</packaging>

    <dependencies>
        <!--1.Spring相关依赖-->
        <!--1)Spring-context-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
        <!--2)AOP相关依赖-->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.8.13</version>
        </dependency>

        <!--2.SpringMVC相关依赖-->
        <!-- 1)servlet依赖 -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.1.0</version>
            <scope>provided</scope>
        </dependency>
        <!-- 2)SpringMVC依赖 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>

        <!--3.数据库, MyBatis相关依赖-->
        <!--1)mysql驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
        <!--2)spring-jdbc
            配置Spring框架对jdbc的支持
        -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
        <!--3)mybatis依赖-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.4</version>
        </dependency>
        <!--4)mybatis整合到Spring的整合包-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.4</version>
        </dependency>
        <!--5)分页查询，pagehelper-->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
            <version>4.0.0</version>
        </dependency>
        <!--6)druid数据库连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.16</version>
        </dependency>

        <!--4.junit依赖-->
        <!-- 1)junit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
        <!--2)spring整合junit的依赖-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>


        <!--5.其它依赖-->
        <!--log4j依赖，打印mybatis日志-->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
        <!--lombok依赖-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.12</version>
            <scope>provided</scope>
        </dependency>
        <!-- 3)jackson，帮助进行json转换
       将对象序列化为JSON格式，放入响应体
        -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.9.0</version>
        </dependency>
        <!--4)commons文件上传，如果需要文件上传功能，需要添加本依赖-->
        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.4</version>
        </dependency>
        <!--jsp依赖-->
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.1</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <!--配置JDK版本-->
    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <!--配置tomcat插件-->
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.tomcat.maven</groupId>
                <artifactId>tomcat7-maven-plugin</artifactId>
                <version>2.2</version>
                <configuration>
                    <!--端口-->
                    <port>80</port>
                    <!--项目路径-->
                    <path>/</path>
                    <!--解决get请求中文乱码-->
                    <uriEncoding>utf-8</uriEncoding>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>

~~~~



### 2) Spring配置文件

(applicationContext.xml)

~~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--开启组件扫描，以自动发现和注册Spring bean-->
    <context:component-scan base-package="com.sangeng">
        <!--要将Controller配置到SpringMVC容器中，因此这里将Controller排除-->
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"></context:exclude-filter>
    </context:component-scan>

    <!--启用基于注解的AOP功能-->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
</beans>
~~~~



### 3) SpringMVC配置文件

(spring-mvc.xml)

~~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!--开启组件扫描-->
    <context:component-scan base-package="com.sangeng.controller"/>

    <!--
        解决静态资源访问问题
        默认情况下，SpringMVC会拦截所有请求，导致静态资源无法被直接访问
        通过<mvc:default-servlet-handler/>的配置，可以启用默认的Servlet处理静态资源，而不是DispatcherServlet来处理
        即可以绕过SpringMVC的执行流程直接访问到静态资源，提高静态资源访问效率
     -->
    <mvc:default-servlet-handler/>

    <!--配置SpringMVC注解驱动-->
    <mvc:annotation-driven>
        <!--解决响应乱码 确保在处理HTTP请求和响应时，字符串的编码和解码都使用UTF-8字符集。-->
        <mvc:message-converters>
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg value="utf-8"/>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>

    <!--配置拦截器-->
    <!--<mvc:interceptors>-->
    <!--    <mvc:interceptor>-->
    <!--        &lt;!&ndash;配置需要拦截的路径&ndash;&gt;-->
    <!--        <mvc:mapping path="/**"/>-->
    <!--        &lt;!&ndash;配置排除拦截的路径&ndash;&gt;-->
    <!--        &lt;!&ndash;<mvc:exclude-mapping path="/"/>&ndash;&gt;-->
    <!--        &lt;!&ndash;配置拦截器对象注入容器&ndash;&gt;-->
    <!--        <bean class="com.sangeng.interceptor.MyHandlerInterceptor"></bean>-->
    <!--    </mvc:interceptor>-->
    <!--</mvc:interceptors>-->


    <!--配置视图解析器  用于前后端不分离的项目-->
    <!--<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="viewResolver">-->
    <!--    &lt;!&ndash;要求拼接的前缀&ndash;&gt;-->
    <!--    <property name="prefix" value="/WEB-INF/page/"></property>-->
    <!--    &lt;!&ndash;要拼接的后缀&ndash;&gt;-->
    <!--    <property name="suffix" value=".jsp"></property>-->
    <!--</bean>-->


    <!--
          文件上传解析器
          注意：id 必须为 multipartResolver
          如果需要上传文件时可以放开相应配置
    -->
    <!--<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">-->
    <!--&lt;!&ndash; 设置默认字符编码 &ndash;&gt;-->
    <!--<property name="defaultEncoding" value="utf-8"/>-->
    <!--&lt;!&ndash; 一次请求上传的文件的总大小的最大值，单位是字节&ndash;&gt;-->
    <!--<property name="maxUploadSize" value="#{1024*1024*100}"/>-->
    <!--&lt;!&ndash; 每个上传文件大小的最大值，单位是字节&ndash;&gt;-->
    <!--<property name="maxUploadSizePerFile" value="#{1024*1024*50}"/>-->
    <!--</bean>-->
</beans>
~~~~



### 4) 数据库 MyBatis相关配置文件

#### 4.1) jdbc信息配置文件

(jdbc.properties)

```properties
jdbc.url=jdbc:mysql://localhost:3306/mybatis_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
jdbc.driver=com.mysql.jdbc.Driver
jdbc.username=root
jdbc.password=yourpassword
```



#### 4.2) MyBatis配置文件

(mybatis-config.xml)

~~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <!--指定使用log4j打印Mybatis日志-->
        <setting name="logImpl" value="LOG4J"/>
        <!--开启数据库列名和pojo类名的驼峰命名自动映射 例如car_num列名可以和carNum属性名自动映射-->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
    <!--
        配置别名包 别名默认为类名
        比如全限定类名为com.sangeng.pojo.User
        别名默认即为user(不区分大小写)
    -->
    <typeAliases>
        <package name="com.sangeng.pojo"></package>
    </typeAliases>
    <plugins>
        <!-- 注意：分页助手的插件，配置在通用mapper之前 -->
        <plugin interceptor="com.github.pagehelper.PageHelper">
            <!-- 指定方言 -->
            <!--数据库方言指的是特定数据库的语法，
            将dialect设为mysql是告诉mybatis生成的SQL语句要符合mysql语言的规范-->
            <property name="dialect" value="mysql"/>
        </plugin>
    </plugins>
</configuration>
~~~~



#### 4.3) MyBatis与Spring整合配置

(spring-mybatis.xml )

~~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <!--配置Spring整合Mybatis-->
    <!--1.配置数据库相关参数 读取jdbc.properties文件-->
    <context:property-placeholder location="classpath:jdbc.properties"></context:property-placeholder>
    
    <!--2.数据库连接池-->
    <bean class="com.alibaba.druid.pool.DruidDataSource" id="dataSource">
        <property name="url" value="${jdbc.url}"></property>
        <property name="username" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
        <property name="driverClassName" value="${jdbc.driver}"></property>
    </bean>
    
    <!--3.启用基于注解的事务管理-->
    <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
    <!--
        配置定义了一个名为transactionManager的事务管理器bean
    -->
    <bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager" id="transactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>

    <!--4.创建SqlSessionFactory的对象-->
    <bean class="org.mybatis.spring.SqlSessionFactoryBean" id="sessionFactory">
        <!--配置连接池-->
        <property name="dataSource" ref="dataSource"></property>
        <!--配置mybatis配置文件的路径-->
        <property name="configLocation" value="classpath:mybatis-config.xml"></property>
    </bean>

    <!--5.扫描com.sangeng.dao包下的接口，并为这些接口创建实现类对象-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer" id="mapperScannerConfigurer">
        <property name="basePackage" value="com.sangeng.mapper"></property>
    </bean>

</beans>

~~~~



#### 4.4) mapper文件夹创建

StudentMapper.xml

注意StudentMapper.java接口和StudentMapper.xml文件的路径要一致。都在com/sangeng/mapper目录下

![img](file:///C:\Users\86176\Desktop\ssm整合.assets\StudentMapper.xml文件路径.png)



~~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--
    注意：namespace要填写UserMapper.java接口的全类名
    这样MyBatis才能为该接口创建对应的实体类对象
-->
<mapper namespace="com.sangeng.mapper.UserMapper">
</mapper>
~~~~



### 5) web.xml配置文件

web.xml

~~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--1.指定spring的配置文件路径-->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml, classpath:spring-mybatis.xml</param-value>
    </context-param>
    <!--
        2.配置监听器：监听器用于在应用程序启动和关闭时执行一些操作
        ContextLoaderListener会在web项目启动时创建spring容器
    -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <!--3.配置DispatcherServlet-->
    <servlet>
        <servlet-name>DispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--
            为DispatcherServlet提供初始化参数的
            设置springmvc配置文件的路径
                name是固定的，必须是contextConfigLocation
                value指的是SpringMVC配置文件的位置
         -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring-mvc.xml</param-value>
        </init-param>
        <!--
            指定项目启动就初始化DispatcherServlet
         -->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>DispatcherServlet</servlet-name>
        <!--
             /           表示当前servlet映射除jsp之外的所有请求（包含静态资源）
             *.do        表示.do结尾的请求路径才能被SpringMVC处理(老项目会出现)
             /*          表示当前servlet映射所有请求（包含静态资源,jsp），不应该使用其配置DispatcherServlet
         -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>


    <!--4.定义乱码处理过滤器，由SpringMVC提供-->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <!-- name固定不变，value值根据需要设置 -->
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <!-- 所有请求都设置utf-8的编码 -->
        <url-pattern>/*</url-pattern>
    </filter-mapping>
</web-app>
~~~~





### 6) 其它配置文件

#### 6.1) log4j配置文件

log4j.properties

~~~~properties
### direct log messages to stdout ###
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{ABSOLUTE} %5p %c{1}:%L - %m%n

### direct messages to file mylog.log ###
log4j.appender.file=org.apache.log4j.FileAppender
log4j.appender.file.File=c:/mylog.log
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{ABSOLUTE} %5p %c{1}:%L - %m%n

### set log levels - for more verbose logging change 'info' to 'debug' ###

log4j.rootLogger=debug, stdout
~~~~





## 02 代码开发

### 1) 数据库配置

数据库初始化语句

~~~~mysql
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mybatis_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mybatis_db`;
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
insert  into `user`(`id`,`username`,`age`,`address`) values (1,'UZI',19,'上海'),(2,'PDD',25,'上海');
~~~~



![据库表初始状](C:\Users\86176\Desktop\ssm整合.assets\数据库表初始状态.png)



### 2) 测试配置成功

测试上述的配置成功

需求：访问localhost:80/user/1能够查到id为1的数据

UserController.java

~~~~ java
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    @ResponseBody
    public User getById(@PathVariable("id") Integer id) {
        User user = userService.getById(id);
        return user;
    }
}

~~~~

UserService.java

~~~~java
public interface UserService {
    User getById(Integer id);
}
~~~~

UserServiceImpl.java

~~~~java
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }
}

~~~~

UserMapper.java

~~~~java
public interface UserMapper {
    User getById(Integer id);
}![试成功界](C:\Users\86176\Desktop\ssm整合.assets\测试成功界面.png)![试成功界](C:\Users\86176\Desktop\ssm整合.assets\测试成功界面.png)
~~~~

UserMapper.xml

~~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.sangeng.mapper.UserMapper">
    <!--parameterType属性可以不写，因为MyBatis有自动推断机制，可以根据接口定义的方法的参数列表的类型，来确定传入的参数的类型-->
    <select id="getById" resultType="com.sangeng.pojo.User">
        select * from user where id = #{id}
    </select>
</mapper>
~~~~



测试结果

![试成功界](C:\Users\86176\Desktop\ssm整合.assets\测试成功界面.png)



### 3) 案例

需求：实现对用户数据的增删改查

#### 3.1 业务代码开发

##### 3.1.1 统一响应格式

~~~~java
package com.sangeng.common;

import lombok.Data;

/**
 * 统一响应格式
 * 所有响应给前端的数据都统一成这个格式进行返回
 * 便于前端对取到的数据进行统一处理
 * @param <T> 响应的数据类型
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // JSON字符串中只包含不为null的值
public class ResponseResult<T> {
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 响应的数据
     * 由于响应的数据类型可能有很多种，可能是User类, Car类, List类...
     * 因此使用泛型
     */
    private T data;

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
~~~~



测试案例中Controller中的handler方法修改为：

~~~~java
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    @ResponseBody
    public ResponseResult getById(@PathVariable("id") Integer id) {
        User user = userService.getById(id);
        return new ResponseResult(200, "操作成功", user);
    }
}

~~~~



![试成功界面(统一响应结果](C:\Users\86176\Desktop\ssm整合.assets\测试成功界面(统一响应结果).png)



##### 3.1.2 查询所有用户

UserController.java

~~~~java
@Controller
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;
    
    /**
     * 查询所有用户信息
     * @return 所有用户信息
     */
    @GetMapping("/user")
    public ResponseResult getAll() {
        List<User> users = userService.getAll();
        return new ResponseResult(200, "操作成功", users);
    }
}
~~~~

UserService.java

~~~~java
public interface UserService {
    List<User> getAll();
}
~~~~

UserServiceImpl.java

~~~~java
package com.sangeng.service.impl;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询所有用户信息
     * @return 所有用户信息
     */
    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }
}
~~~~

UserMapper.xml

~~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.sangeng.mapper.UserMapper">
    <select id="getAll" resultType="com.sangeng.pojo.User">
        select * from user
    </select>
</mapper>
~~~~

![etAll查询结](C:\Users\86176\Desktop\ssm整合.assets\getAll查询结果.png)

##### 3.1.3 分页查询所有用户

封装分页信息的类PageInfoResult.java

~~~~java
package com.sangeng.common;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoResult<T> {
    /**
     * 页码
     */
    private int pageNum;

    /**
     * 一页包含多少条数据
     */
    private int pageSize;

    /**
     * 总共一共有多少条数据
     */
    private int total;

    /**
     * 查询到的数据
     * 由于可能查询不同类型的数据
     * 因此这里使用泛型
     */
    private List<T> Data;
}

~~~~

UserController.java

~~~~java
package com.sangeng.controller;

@Controller
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{pageNum}/{pageSize}")
    public ResponseResult getByPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        PageInfoResult pageInfoResult = userService.getByPage(pageNum, pageSize);
        return new ResponseResult(200, "操作成功", pageInfoResult);
    }
}
~~~~

UserService.java

~~~~java
package com.sangeng.service;

public interface UserService {
    PageInfoResult getByPage(int pageNum, int pageSize);
}

~~~~

UserServiceImpl.java

~~~~java
package com.sangeng.service.impl;

@Service
public class UserServiceImpl implements UserService {
    /**
     * 分页查询数据
     * @param pageNum 页码
     * @param pageSize 一页包含多少条数据
     * @return 这一页的分页信息以及查询到的数据封装成的PageInfoResult对象
     */
    @Override
    public PageInfoResult getByPage(int pageNum, int pageSize) {
        // 开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        // 查询数据
        // 由于开启了分页查询 下面mybatis会查询返回对应页的数据
        List<User> users = userMapper.getAll();
        // 获取分页信息
        PageInfo pageInfo = new PageInfo(users);
        // 封装PageInfoResult结果
        PageInfoResult pageInfoResult = new PageInfoResult(pageInfo.getPageNum(), pageInfo.getPageSize(), (int) pageInfo.getTotal(), users);
        return pageInfoResult;
    }
}

~~~~

![etByPage查询结](C:\Users\86176\Desktop\ssm整合.assets\getByPage查询结果.png)

##### 3.1.4 插入用户

UserController.java

~~~~java
package com.sangeng.controller;

@Controller
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody User user) {
        int count = userService.addUser(user);
        return new ResponseResult(200, "成功插入" + count + "条数据");
    }
}

~~~~

UserService.java

~~~~java
package com.sangeng.service;

public interface UserService {
    int addUser(User user);
}

~~~~

UserServiceImpl.java

~~~~java
package com.sangeng.service.impl;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }
}
~~~~

UserMapper.java

```java
package com.sangeng.mapper;

import com.sangeng.pojo.User;

public interface UserMapper {
    int addUser(User user);
}
```

UserMapper.xml

~~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.sangeng.mapper.UserMapper">
    <insert id="addUser">
        insert into user values (null, #{username}, #{age}, #{address})
    </insert>
</mapper>
~~~~

测试结果：

（1）响应结果：

![ddUser响应结](C:\Users\86176\Desktop\ssm整合.assets\addUser响应结果.png)

（2）数据库结果：

![ddUser数据库结](C:\Users\86176\Desktop\ssm整合.assets\addUser数据库结果.png)



##### 3.1.5 删除用户

UserController.java

```java
package com.sangeng.controller;

@Controller
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;

    @DeleteMapping("/user/{id}")
    public ResponseResult deleteById(@PathVariable("id") Integer id) {
        int count = userService.deleteById(id);
        return new ResponseResult(200, "成功删除" + count + "条数据");
    }
}
```

UserService.java

```java
package com.sangeng.service;

public interface UserService {
    int deleteById(Integer id);
}
```

UserServiceImpl.java

```java
package com.sangeng.service.impl;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int deleteById(Integer id) {
        return userMapper.deleteById(id);
    }
}
```

UserMapper.java

```java
package com.sangeng.mapper;

public interface UserMapper {
    int deleteById(Integer id);
}

```

UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.sangeng.mapper.UserMapper">
    <delete id="deleteById">
        delete from user where id = #{id}
    </delete>
</mapper>
```

测试结果：

（1）响应结果：

![eleteUser响应结](C:\Users\86176\Desktop\ssm整合.assets\deleteUser响应结果.png)

（2）删除前：

![eleteUser删除](C:\Users\86176\Desktop\ssm整合.assets\deleteUser删除前.png)

（3）删除后：

![eleteUser删除](C:\Users\86176\Desktop\ssm整合.assets\deleteUser删除后.png)

##### 3.1.6 更新用户

UserController.java

```java
package com.sangeng.controller;

@Controller
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;
	
    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody User user) {
        int count = userService.updateUser(user);
        return new ResponseResult(200, "成功更新" + count + "条数据");
    }
}
```

UserService.java

```java
package com.sangeng.service;

public interface UserService {
    int updateUser(User user);
}
```

UserServiceImpl.java

```java
package com.sangeng.service.impl;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }
}
```

UserMapper.java

```java
package com.sangeng.mapper;

import com.sangeng.pojo.User;

import java.util.List;

public interface UserMapper {
    int updateUser(User user);
}

```

UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.sangeng.mapper.UserMapper">
    <update id="updateUser">
        update user set username = #{username}, age = #{age}, address = #{address} where id = #{id}
    </update>
</mapper>
```

测试结果：

（1）响应结果：

![pdateUser响应结](C:\Users\86176\Desktop\ssm整合.assets\updateUser响应结果.png)

（2）更新前：

![pdateUser更新](C:\Users\86176\Desktop\ssm整合.assets\updateUser更新前.png)

（3）更新后：

![pdateUser更新](C:\Users\86176\Desktop\ssm整合.assets\updateUser更新后.png)

#### 3.2 其它

##### 3.2.1 统一异常处理

当Controller层抛出异常，可以使用@ControllerAdvice对Controller层出现的异常进行统一处理

无需在每一个handler方法中使用try..catch..语句来单独处理。

【对于Mapper层，Service层出现的异常，我们可以将异常抛到调用该层的上层去处理。

也就是说，Mapper层的异常抛到Service层，Service层的异常抛到Controller层。

最终对Controller层抛出的异常进行统一处理】



使用步骤

1. 创建类加上@ControllerAdvice注解进行标识
2. 定义异常处理方法，使用@ExceptionHandler标识进行处理的异常。
3. 将异常处理类注入Bean容器

~~~~java
package com.sangeng.exception;

// 1.创建类加上@ControllerAdvice注解进行标识
@ControllerAdvice
// 3.将异常处理类注入Bean容器
// 实际上@ControllerAdvice注解本身就包含@Component的语义 因此这里的@Component可以省略
@Component
// @ResponseBody注解表明该类中所有方法的返回值直接作为响应体，而不是视图名。
// 确保异常处理方法返回的对象会被转换为响应体，而不是被视图解析器解析成视图。
@ResponseBody
public class MyControllerAdvice {
    // 2.定义异常处理方法，使用@ExceptionHandler标识进行处理的异常。
    @ExceptionHandler({Exception.class})
    public ResponseResult exceptionHandler(Exception ex) {
        return new ResponseResult(500, ex.getMessage());
    }
}
~~~~



测试：

在UserController.java这个handler方法添加异常

~~~~java
@GetMapping("/user")
public ResponseResult getAll() {
    List<User> users = userService.getAll();
    // 新添加的异常
    System.out.println(1/0);
    return new ResponseResult(200, "操作成功", users);
}
~~~~

测试结果：

![试统一异常处](C:\Users\86176\Desktop\ssm整合.assets\测试统一异常处理.png)



##### 3.2.2 拦截器

创建并配置分为三步

- 创建类实现HandlerInterceptor接口
- 重写HandlerInterceptor接口的方法
- 将拦截器配置到SpringMVC中



~~~~java
package com.sangeng.interceptor;

// 1. 创建类实现HandlerInterceptor接口
public class MyHandlerInterceptor implements HandlerInterceptor {
    // 2. 重写HandlerInterceptor接口的方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");
        return true;
    }

    /**
     * postHandle方法是在handler方法执行结束之后执行
     * 如果handler方法执行过程中报错没有执行完成 就不会执行postHandle方法 直接执行afterCompletion方法
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
    }
}
~~~~



spring-mvc.xml（将下列配置加入spring-mvc.xml文件中）

~~~~xml
<!--配置拦截器-->
<mvc:interceptors>
    <mvc:interceptor>
        <!--配置需要拦截的路径-->
        <!--/**表示拦截所有路径-->
        <mvc:mapping path="/**"/>
        <!--配置排除拦截的路径-->
        <!--<mvc:exclude-mapping path="/"/>-->
        <!--配置拦截器对象注入容器 填写自定义的拦截器的全限定类名-->
        <bean class="com.sangeng.interceptor.MyHandlerInterceptor"></bean>
    </mvc:interceptor>
</mvc:interceptors>
~~~~



测试：

访问任意一个handler方法

测试结果：

![截器测试结](C:\Users\86176\Desktop\ssm整合.assets\拦截器测试结果.png)



##### 3.2.3 声明式事务

注意：事务的注解通常放在Service层上。因为Service层通常包含业务逻辑，而业务逻辑的执行可能涉及到多个数据操作，需要保证这些操作在一个事务中。

使用事务分为两步：

* 在Mybatis与Spring整合配置文件spring-mybatis.xml中配置事务
* 在类/方法上使用@Transactional注解



spring-mybatis.xml

~~~~xml
...
<!--启用基于注解的事务管理-->
<tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
<!--
    配置定义了一个名为transactionManager的事务管理器bean
-->
<bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager" id="transactionManager">
    <property name="dataSource" ref="dataSource"></property>
</bean>
<!--数据库连接池-->
<bean class="com.alibaba.druid.pool.DruidDataSource" id="dataSource">
    <property name="url" value="${jdbc.url}"></property>
    <property name="username" value="${jdbc.username}"></property>
    <property name="password" value="${jdbc.password}"></property>
    <property name="driverClassName" value="${jdbc.driver}"></property>
</bean>
...
~~~~

UserController.java

~~~~java
package com.sangeng.controller;

@Controller
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;
	
    @GetMapping("/testTransaction")
    public ResponseResult testTransaction() {
        userService.testTransaction();
        return new ResponseResult(200, "操作成功");
    }
}
~~~~

UserService.java

~~~~java
package com.sangeng.service;

public interface UserService {
    void testTransaction();
}

~~~~

UserServiceImpl.java

~~~~java
package com.sangeng.service.impl;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional // 开启事务
    @Override
    public void testTransaction() {
        userMapper.addUser(new User(null, "test1", 1, "testAAA"));
        // 制造异常 如果事务生效，两条插入语句都不生效，数据库无新增数据
        System.out.println(1 / 0);
        userMapper.addUser(new User(null, "test2", 2, "testBBB"));
    }
}
~~~~



测试结果：

（1）执行事务前：

![行事务](C:\Users\86176\Desktop\ssm整合.assets\执行事务前.png)

（2）执行事务后：

![行事务](C:\Users\86176\Desktop\ssm整合.assets\执行事务后.png)

说明事务配置生效



##### 3.2.4 AOP

配置AOP分为五步：

* 导入aspectj依赖
* Spring配置文件中启用AOP
* 定义切面类 添加@Aspect注解
* 编写切点
* 编写增强逻辑



1. 导入aspectj依赖

   pom.xml

   ~~~~xml
   <dependency>
       <groupId>org.aspectj</groupId>
       <artifactId>aspectjweaver</artifactId>
       <version>1.8.13</version>
   </dependency>
   ~~~~

2. Spring配置文件中启用AOP

   applicationContext.xml

   ~~~~xml
   <!--启用基于注解的AOP功能-->
   <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
   ~~~~

3. 定义切面类 添加@Aspect注解

4. 编写切点

5. 编写增强逻辑

   ~~~~java
   package com.sangeng.aspect;

   // 1. 定义切面类 添加@Aspect注解
   @Aspect
   @Component
   public class MyAspect {
       // 2. 编写切点
       /**
        * 切点
        * execution(* com.sangeng.service..*.*(..))
        * 表示第一个*表示任意返回值
        * com.sangeng.service..*.*(..)表示对com.sangeng.service包及其子包下的所有类的所有方法都生效
        * (..)表示任意参数
        */
       @Pointcut("execution(* com.sangeng.service..*.*(..))")
       private void pointcut() {

       }

       // 3. 编写增强逻辑
       /**
        * 增强逻辑
        * 对pointcut()配置的所有满足条件的方法 在执行之前都输出before
        */
       @Before("pointcut()")
       private void before() {
           System.out.println("before");
       }
   }
   ~~~~

   ​

   测试：

   访问/user路径，查询所有用户

   测试结果：

   ![OP测试结](C:\Users\86176\Desktop\ssm整合.assets\AOP测试结果.png)