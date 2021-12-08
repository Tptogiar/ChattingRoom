



# 呱呱聊天室

基于socket实现的局域网聊天室



# 服务端

### 1.服务端配置

```
guaguaUser：服务端登录用户名
guaguaPassword=服务端登录密码
port：服务端的端口号

driverClass：数据库驱动
dataBaseUrl：数据库连接
dataBaseUser：数据库用户名
dataBasePassword：数据库密码
dataBaseName：数据库名称
```

在这里需配置数据库连接，用户名，密码，以及本机Mysql数据库版本对应的驱动即可（程序中有数据库初始化，只需获取数据库连接即可）

```
guaguaUser=root
guaguaPassword=123
port=555

driverClass=com.mysql.jdbc.Driver
dataBaseUrl=jdbc:mysql://localhost:3306/?characterEncoding=utf-8
dataBaseUser=root
dataBasePassword=********

dataBaseName=guaguaChatting
```

![image](https://user-images.githubusercontent.com/79641956/119363321-428a7b00-bce0-11eb-984f-a40302225e71.png)

ps : 原数据库驱动版本为：（GuaGuaChatting\Code\lib目录下）

![image](https://user-images.githubusercontent.com/79641956/119358199-fd177f00-bcda-11eb-878d-540d03b899fc.png)
![image](https://user-images.githubusercontent.com/79641956/119360905-ac555580-bcdd-11eb-89e9-1575eeec7af8.png)
![image](https://user-images.githubusercontent.com/79641956/119360906-ad868280-bcdd-11eb-8d2d-4d14c07c886a.png)





### 2.功能介绍

#### 服务端

##### 1.登录界面
![image](https://user-images.githubusercontent.com/79641956/119358573-4ff13680-bcdb-11eb-9ab8-c97e68a3ce56.png)
![image](https://user-images.githubusercontent.com/79641956/119358575-51226380-bcdb-11eb-98e1-7cdc5711c45f.png)



##### 2.主界面

![image](https://user-images.githubusercontent.com/79641956/119358772-87f87980-bcdb-11eb-90d9-2dcc06d67523.png)

![image](https://user-images.githubusercontent.com/79641956/119358808-9050b480-bcdb-11eb-9461-d0d86565a8ac.png)

![image](https://user-images.githubusercontent.com/79641956/119358829-96469580-bcdb-11eb-967e-349836c6f338.png)
![image](https://user-images.githubusercontent.com/79641956/119359002-bd9d6280-bcdb-11eb-8366-6cdbdd21eb5c.png)
![image](https://user-images.githubusercontent.com/79641956/119359036-c4c47080-bcdb-11eb-83f6-ec160eb32f87.png)
![image](https://user-images.githubusercontent.com/79641956/119359054-ca21bb00-bcdb-11eb-9836-8c72ea048b73.png)
![image](https://user-images.githubusercontent.com/79641956/119359073-cf7f0580-bcdb-11eb-9a6f-dc2aa3c8e35c.png)
![image](https://user-images.githubusercontent.com/79641956/119359996-be82c400-bcdc-11eb-8b7b-71427fda283d.png)

#### 客户端

##### 1.登录注册界面
![image](https://user-images.githubusercontent.com/79641956/122634089-cfbdc580-d10e-11eb-9036-2816743c6725.png)
![image](https://user-images.githubusercontent.com/79641956/119359543-546a1f00-bcdc-11eb-858a-0748cee7914b.png)
![image](https://user-images.githubusercontent.com/79641956/119359548-5633e280-bcdc-11eb-8ff6-aa1d20c31fe5.png)
![image](https://user-images.githubusercontent.com/79641956/119359557-57650f80-bcdc-11eb-8f82-65d8e9085f4c.png)
![image](https://user-images.githubusercontent.com/79641956/119359561-58963c80-bcdc-11eb-9775-2c67e44eb071.png)
![image](https://user-images.githubusercontent.com/79641956/119360076-d78b7500-bcdc-11eb-9614-e819029ed39d.png)
![image](https://user-images.githubusercontent.com/79641956/119360077-d8240b80-bcdc-11eb-9c22-415075f24011.png)
![image](https://user-images.githubusercontent.com/79641956/119360080-d8240b80-bcdc-11eb-8966-d736558489f4.png)




##### 2.主界面


![image](https://user-images.githubusercontent.com/79641956/119359908-aca12100-bcdc-11eb-82f6-ceb8d51333cd.png)
![image](https://user-images.githubusercontent.com/79641956/119359912-add24e00-bcdc-11eb-9e6f-108bf23eaadc.png)
![image](https://user-images.githubusercontent.com/79641956/119359915-ae6ae480-bcdc-11eb-8f14-660baa1a4938.png)
![image](https://user-images.githubusercontent.com/79641956/119359919-af037b00-bcdc-11eb-9cf9-c62e1fb50c42.png)
![image](https://user-images.githubusercontent.com/79641956/119359923-af9c1180-bcdc-11eb-8163-b36877daf97f.png)
![image](https://user-images.githubusercontent.com/79641956/119359925-b034a800-bcdc-11eb-836c-9a226e392999.png)
![image](https://user-images.githubusercontent.com/79641956/119360028-c9d5ef80-bcdc-11eb-869c-3def61fa6ee9.png)
![image](https://user-images.githubusercontent.com/79641956/119360030-ca6e8600-bcdc-11eb-8281-56b7442716bf.png)
![image](https://user-images.githubusercontent.com/79641956/119360033-cb071c80-bcdc-11eb-93ac-1bb061cf43dc.png)
![image](https://user-images.githubusercontent.com/79641956/119360051-d0646700-bcdc-11eb-8655-8559ac4c134e.png)
![image](https://user-images.githubusercontent.com/79641956/119360122-e2460a00-bcdc-11eb-9533-728c6225374d.png)
![image](https://user-images.githubusercontent.com/79641956/119360520-48cb2800-bcdd-11eb-9bac-4da426d524c3.png)
![image](https://user-images.githubusercontent.com/79641956/119360531-4b2d8200-bcdd-11eb-9f94-72bf70f128a5.png)
![image](https://user-images.githubusercontent.com/79641956/119360214-f7229d80-bcdc-11eb-8ef8-43ecf01bf6ad.png)
![image](https://user-images.githubusercontent.com/79641956/119360227-fa1d8e00-bcdc-11eb-9ec5-8a794c1f5a94.png)








