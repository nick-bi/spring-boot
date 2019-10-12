# Activiti
### this is a Activiti Spring Boot project repository

# Activiti项目简介
### 这是一个Activiti Spring Boot的学习项目

### maven依赖
````
 <dependency>
      <groupId>org.activiti</groupId>
      <artifactId>activiti-spring-boot-starter-basic</artifactId>
       <version>6.0.0</version>
  </dependency>
  ````
  ### jdbc配置
  #### spring2.0 开始默认使用的是hikari为数据源连接，具体druid和harike的对比，请移步[harike官网](https://github.com/brettwooldridge/HikariCP)
  ````
spring:
   datasource:
    driver-class-name: com.mysql.jdbc.Driver
    type: com.zaxxer.hikari.HikariDataSource
    url: jdbc:mysql://localhost:3306/activiti_test?useUnicode=true&characterEncoding=utf-8
    username: root
    password: 111111
    hikari:
      minimum-idle: 5
      maximum-pool-size: 15
      auto-commit: true
      idle-timeout: 30000
      pool-name: DatebookHikariCP
      max-lifetime: 1800000
      connection-timeout: 30000
      connection-test-query: SELECT 1

  activiti:
    check-process-definitions: true # activti是否自动部署
    db-identity-used: true #是否使用activti自带的用户体系
    database-schema-update: true #是否每次都更新数据库
    #    check-process-definitions: false # activti是否自动部署
    #    db-identity-used: false #是否使用activti自带的用户体系
    #    database-schema-update: false #是否每次都更新数据库
````
### application配置
#### 这里我们将禁用activiti的安全配置，如果需要使用acitiviti rest api，请按需要进行安全配置
````
@SpringBootApplication(exclude = {org.activiti.spring.boot.SecurityAutoConfiguration.class})
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }

}
````
### 创建bpmn文件
#### 1.使用acitiviti的 admin及web项目创建bpmn文件
#### 2.下载安装 actiBPM插件
![image](https://github.com/bijialin/spring-boot/raw/master/activiti/rdimages/actiBPMWindow.png)
##### 在resources下创建个processes目录，在这个目录下创建bpmn文件，deploy的时候直接使用文件名部署即可.
##### 例如test.bpmn
![image](https://github.com/bijialin/spring-boot/raw/master/activiti/rdimages/testbpm.png)

### activiti service
|服务 | 服务名称 |
| ------ | ------ | 
| RuntimeService | 工作流运行服务 |
| TaskService | 工作流任务服务 |
| HistoryService | 工作流历史数据服务 |
| IdentityService | 用户服务 |
| RepositoryService | 部署服务 |


##### 本项目对acitiviti的service 进行了封装，如需扩展服务，请查询activiti相应service进行开发。 
````
  /**
     * 启动流程
     *
     * @param processDefinitionKey 流程定义id
     * @param paramsMap            参数
     * @return
     */
    ProcessInstance startProcess(String processDefinitionKey, Map<String, Object>
            paramsMap) throws Exception;

    /**
     * 完成任务
     *
     * @param taskId    任务id
     * @param paramsMap 任务携带变量
     */
    void complete(String taskId, Map<String, Object> paramsMap);

    /**
     * 完成任务
     *
     * @param taskId
     */
    public void complete(String taskId);

    /**
     * 通过流程id 查询任务
     *
     * @param processInstanceId
     * @return
     */
    Task queryTaskByProcessId(String processInstanceId);

    /**
     * 通过任务id，查询任务信息
     *
     * @param taskId
     * @return
     */
    Task queryTaskById(String taskId);

    /**
     * 设置任务认领组
     *
     * @param taskId
     * @param groupId
     */
    void addCandidateGroup(String taskId, String groupId);


    /**
     * 认领任务
     *
     * @param taskId 任务id
     * @param userId 认领人id
     */
    void claim(String taskId, String userId);

    /**
     * 设置认证用户，用于定义流程启动人
     *
     * @param userId
     */
    void setAuthUser(String userId);

    /**
     * 查看定义的流程图
     *
     * @param processDefinitionId
     * @return
     */
    byte[] definitionImage(String processDefinitionId) throws IOException;

    /**
     * 查看流程进度图
     *
     * @param pProcessInstanceId
     * @return
     * @throws Exception
     */
    byte[] getProcessImage(String pProcessInstanceId) throws Exception;


    /**
     * 通过任务和变量名称获取变量
     *
     * @param taskId
     * @param varName
     * @return
     */
    public Object queryVariables(String taskId, String varName);


    /**
     * 通过流程id 查询流程
     *
     * @param processId
     * @return
     */
    HistoricProcessInstanceEntity queryProcessInstance(String processId);


    /**
     * 删除流程
     *
     * @param processInstanceId
     * @param deleteReason
     * @return
     * @throws Exception
     */
    void deleteProcessInstance(String processInstanceId, String deleteReason) throws Exception;

    /**
     * 流程部署
     * 此方法为手动部署，传入/resource/processes目录下的流程问卷名称即可
     *
     * @param fileName 文件名
     * @param category 流程分类
     */
    Deployment deploy(String fileName, String category);

    /**
     * 删除部署的流程，级联删除流程实例
     *
     * @param deploymentId 流程部署ID
     */
    void deleteDeployment(String deploymentId);

    /**
     * 流程部署列表
     *
     * @return
     */
    List<Deployment> deployList();

    /**
     * 获取部署流程列表
     *
     * @return
     */
    List<ProcessDefinition> getProcessList() throws Exception;

    /**
     * 挂起流程
     *
     * @param processDefinitionId
     * @throws Exception
     */
    void suspendProcess(String processDefinitionId) throws Exception;

    /**
     * 激活流程
     *
     * @param processDefinitionId
     * @throws Exception
     */
    void activateProcess(String processDefinitionId) throws Exception;

    /**
     * 任务回退
     *
     * @param taskId  当前任务id
     * @param userId  用户id
     * @param reason  理由
     * @param groupId 分组id
     * @param backNum 退回数
     * @throws Exception
     */
    public void rollBackTask(String taskId, String userId, String reason, String groupId, int backNum) throws
            Exception;
````


### more articles please click [my blog](https://www.jianshu.com/u/1bb4b4eaef1e)
### of course you can send email to me [nickbi@nickbi.com](mailto:nickbi@nickbi.com)

### 更多博客，请访问[我的博客](https://www.jianshu.com/u/1bb4b4eaef1e)
### 如遇问题，请发送邮件到我的邮箱[nickbi@nickbi.com](mailto:nickbi@nickbi.com)
