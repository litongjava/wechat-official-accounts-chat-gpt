## wechat-official-accounts-chat-gpt

### 简介

将ChatGPT接入到微信公众号

- 本项目没有连接数据库undertow.txt中的数据库的数据配置可以忽略

- Java语言开发,基于Jfinal

- 依赖com.github.plexpt:chatgpt

- 默认使用chatgpt 3.5 模型,使用体验没有直接使用官网的效果好

  

### 部署

#### 修改配置文件

src\main\resources\undertow.txt

```
weixin.app.id=
weixin.app.secret=
weixin.app.token=
weixin.app.encoding_aes_key=
weixin.app.encryptMessage=true

#支持多个key使用,分割
chatgpt.keys=
chatgpt.proxy.enable=false
chatgpt.proxy.type=http
chatgpt.proxy.http.host=
chatgpt.proxy.http.port=
#chatgpt.proxy.type=socks5
chatgpt.proxy.socks5.host=
chatgpt.proxy.socks5.port=
```

#### 获取微信公众号配置
1)登录https://mp.weixin.qq.com/  
2)进入微信公众号后台-->设置与开发-->基本配置   
获取appId,appSecret,token,encodingAESKey  
修改配置配置文件中的下列配置
```
weixin.app.id
weixin.app.secret
weixin.app.token
weixin.app.encoding_aes_key
```

#### 获取ChatGPT配置
略,然后配置key,支持多个key使用,分割
```
chatgpt.keys=
```

#### 搭建代理服务器
如果在中国境内,需要通过搭理代理服务器访问ChatGPT,目前支持http代理和socks5
```
chatgpt.proxy.enable=false
chatgpt.proxy.type=http
chatgpt.proxy.http.host=
chatgpt.proxy.http.port=
#chatgpt.proxy.type=socks5
chatgpt.proxy.socks5.host=
chatgpt.proxy.socks5.port=
```


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

### 测试是否启动成功
访问下面的接口测试
```
http://localhost:8080/l/wechat/msg/status
```
消息接口
```
http://localhost:8080/l/wechat/msg
```

#### 微信公众号超时时间
ChatGPT的响应时间比较长,可能会导致出现超时的情况
微信服务器连接公众号开发者服务器时发生超时,超时时间为5秒,超时这个时间限制微信公众平台就采用了这个处理方式，这个是客户端无法改变的。除非找到微信平台有决策权的.
