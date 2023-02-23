# 𬭊蚀刻器 Dubnium (Db) Sculptor

一个用于在项目启动时自动检测并创建数据库表结构的简易工具.
依赖 [MVCIntrospector](https://github.com/FirokOtaku/MVCIntrospector) 库 (`[17.6.0,)`).

----

cmd

```bash
git clone https://github.com/FirokOtaku/DubniumSculptor.git
cd DubniumSculptor
mvn install
```

maven

```xml
<dependency>
    <groupId>firok.spring</groupId>
    <artifactId>dbsculptor</artifactId>
    <version>{VRESION}</version>
</dependency>
```

application.yml

```yaml
firok:
  spring:
    dbsculptor:
      enable: true
    mvci:
      runtime:
        enable-mapper-config: true
```

Java 17+

```java
import firok.spring.dbsculptor.DirectMapper;
import firok.spring.mvci.runtime.CurrentMappers;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

// 使相关组件可以被扫描
@MapperScans({
    @MapperScan("firok.spring.dbsculptor"),
})
@ComponentScans({
    @ComponentScan("firok.spring.mvci.runtime"),
    @ComponentScan("firok.spring.dbsculptor"),
})
public class AnySpringApplication
{
    // 保证项目里有这两个实例,
    // 且名为 currentMappers 和 directMapper
    @Autowired
    CurrentMappers currentMappers;
    @Autowired
    DirectMapper directMapper;
}
```

```java
import firok.spring.dbsculptor.Dubnium;
import firok.spring.mvci.MVCIntrospective;

// 在实体类标注 MVCI 和 DBS 的注解
@MVCIntrospective
@Dubnium(sculpturalScript = """
    # 在这里编写自定义 SQL 脚本
    create table d_user (
        username varchar(64),
        password varchar(64),
    );
    """)
public class UserBean
{
    String username;
    String password;
}
```
