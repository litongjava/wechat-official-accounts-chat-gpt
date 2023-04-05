## wechat-official-accounts-chat-gpt

### 简介

将ChatGPT接入到微信公众号

### 安装

#### 开发启动
使用mvn和spring-boot插件启动即可
```
set JAVA_HOME=D:\\dev_program\\java\\jdk1.8.0_121
mvn clean package -DskipTests spring-boot:run
```
#### 编译

```
set JAVA_HOME=D:\\dev_program\\java\\jdk1.8.0_121
mvnd clean install -DskipTests
````
#### 启动
```
java -jar target\wechat-official-accounts-chat-gpt-1.0.jar
```
#### 微信公众号超时时间
ChatGPT的响应时间比较长,可能会导致出现超时的情况
微信服务器连接公众号开发者服务器时发生超时,超时时间为5秒,超时这个时间限制微信公众平台就采用了这个处理方式，这个是客户端无法改变的。除非找到微信平台有决策权的.
