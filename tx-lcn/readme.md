# tx-lcn transaction project
### this is a tx-lcn transaction Spring Boot project repository

# tx-lcn transaction项目简介

### tx-lcn transaction Spring Boot的学习项目

```TX-LCN 主要有两个模块，Tx-Client(TC) Tx-Manager(TM). TC作为微服务下的依赖，TM是独立的服务。```


### tm maven依赖
```
 <dependency>
   <groupId>com.codingapi.txlcn</groupId>
   <artifactId>txlcn-tm</artifactId>
   <version>5.0.2.RELEASE</version>
</dependency>

```
+ 创建MySQL数据库, 名称为: tx-manager

+ 创建数据表
```
CREATE TABLE `t_tx_exception`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `unit_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mod_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `transaction_state` tinyint(4) NULL DEFAULT NULL,
  `registrar` tinyint(4) NULL DEFAULT NULL,
  `remark` varchar(4096) NULL DEFAULT  NULL,
  `ex_state` tinyint(4) NULL DEFAULT NULL COMMENT '0 未解决 1已解决',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
```


### tm配置信息
```
spring.application.name=tx-manager
server.port=7970

spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/tx-manager?characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=root

mybatis.configuration.map-underscore-to-camel-case=true
mybatis.configuration.use-generated-keys=true

#tx-lcn.logger.enabled=true
# TxManager Host Ip
#tx-lcn.manager.host=127.0.0.1
# TxClient连接请求端口
#tx-lcn.manager.port=8070
# 心跳检测时间(ms)
#tx-lcn.manager.heart-time=15000
# 分布式事务执行总时间
#tx-lcn.manager.dtx-time=30000
#参数延迟删除时间单位ms
#tx-lcn.message.netty.attr-delay-time=10000
#tx-lcn.manager.concurrent-level=128
# 开启日志
#tx-lcn.logger.enabled=true
#logging.level.com.codingapi=debug
#redis 主机
#spring.redis.host=127.0.0.1
#redis 端口
#spring.redis.port=6379
#redis 密码
#spring.redis.password=
```
### tc依赖
```
<dependency>
            <groupId>com.codingapi.txlcn</groupId>
            <artifactId>txlcn-tc</artifactId>
            <version>5.0.2.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>com.codingapi.txlcn</groupId>
            <artifactId>txlcn-txmsg-netty</artifactId>
            <version>5.0.2.RELEASE</version>
        </dependency>
```
### TC开启分布式事务注解```@EnableDistributedTransaction```
```
package com.nickbi.txlcn.apptwo;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDistributedTransaction
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.nickbi.txlcn.apptwo.dao")
public class AppTwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppTwoApplication.class, args);
	}

}

```
### TC TM配置
```
# 默认之配置为TM的本机默认端口
tx-lcn.client.manager-address=127.0.0.1:8070 
```
### 服务A
```
package com.nickbi.txlcn.appone.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.nickbi.txlcn.appone.entity.UserEntity;
import com.nickbi.txlcn.appone.service.OneService;
import com.nickbi.txlcn.appone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * package com.nickbi.txlcn.appone.service
 * description <p/>
 *
 * @author nickbi
 * create on 2019-10-11 18:18
 */
@Service
public class OneServiceImpl implements OneService {

	@Autowired
	private UserService userService;

	@Autowired
	private RestTemplate restTemplate;


	@LcnTransaction
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveInfo(String name) throws Exception {
		final Boolean twoResult = restTemplate.getForObject("http://127.0.0.1:8002/two?name=" + name, Boolean.class);
		if (!twoResult) {
			throw new Exception("transaction test");
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setName("one");
		userService.save(userEntity);

	}
}

```

### 服务B
```
package com.nickbi.txlcn.apptwo.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.nickbi.txlcn.apptwo.entity.UserEntity;
import com.nickbi.txlcn.apptwo.service.TwoService;
import com.nickbi.txlcn.apptwo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * package com.nickbi.txlcn.apptwo.service.impl
 * description <p/>
 *
 * @author wzfc-001
 * create on 2019-10-12 09:43
 */
@Service
public class TwoServiceImpl implements TwoService {

	private final UserService userService;

	public TwoServiceImpl(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 保存用户名
	 *
	 * @param name
	 * @return
	 */
	@LcnTransaction
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveInfo(String name) {
		UserEntity userEntity = new UserEntity();
		userEntity.setName(name);
		userService.save(userEntity);
		return !"ex".equals(name);
	}
}
```


### more articles please click [my blog](https://www.jianshu.com/u/1bb4b4eaef1e)

### of course you can send email to me [nickbi@nickbi.com](mailto:nickbi@nickbi.com)

### 更多博客，请访问[我的博客](https://www.jianshu.com/u/1bb4b4eaef1e)

### 如遇问题，请发送邮件到我的邮箱[nickbi@nickbi.com](mailto:nickbi@nickbi.com)
