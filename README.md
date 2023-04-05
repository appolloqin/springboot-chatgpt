# springboot-chatgpt
## Java H5 SpringBoot chatGPT
**如果喜欢这个项目，请给它一个Star；如果你发明了更好用的快捷键或函数插件，欢迎发issue或者pull requests（dev分支）**
If you like this project, please give it a Star. If you've come up with more useful academic shortcuts or functional plugins, feel free to open an issue or pull request （to `dev` branch）.

> **Note**
- 懒得看项目代码？输入任意用户名登录
<div align="center">
<img src="https://github.com/appolloqin/springboot-chatgpt/blob/main/docs/images/login.png" width="600" >
</div>
- 输入内容回车效果
<div align="center">
<img src="https://github.com/appolloqin/springboot-chatgpt/blob/main/docs/images/home.png" width="600" >
</div>

## 直接运行 (Windows, Linux or MacOS)

### 1. 下载项目
```sh
git clone https://github.com/appolloqin/springboot-chatgpt.git
cd springboot-chatgpt
```

### 2. 配置API KEY和代理设置

在`application.properties`中，配置 海外Proxy 和 OpenAI API KEY，说明如下
```
1. 如果你在国内，需要设置海外代理才能够顺利使用 OpenAI API，设置方法请仔细阅读application.properties（1.修改其中的proxies.useproxy为True; 2.按照说明修改其中的proxies.ip proxies.port）。
2. 配置 OpenAI API KEY。你需要在 OpenAI 官网上注册并获取 API KEY。一旦你拿到了 API KEY，在 application.properties 文件里配置好即可。
```
（P.S. 请保护好自己的OpenAI API KEY。API KEY优先级高于Token）

### 3. 安装依赖
```sh
# （选择）推荐
安装java环境和maven请自行搜索怎么安装。使用maven进行jar管理   

```
### 4. 编译
```sh
cd springboot-chatgpt
mvn clean package -DskipTests -e
```
### 5. 运行
```sh
cd target
java -jar springboot-chatgpt-0.0.1-SNAPSHOT.jar
```

