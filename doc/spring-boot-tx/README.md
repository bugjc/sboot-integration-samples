## 利用SPRING编程式事务处理多数据源事务

#### 主要的技术
- springboot
- mybatis

#### USE
- 运行resource/test-tx1和test-tx2 sql文件并配置数据库地址
- 启动AdminApplication.java
- 浏览器输入地址：IP：PORT

#### 开发建议
- 仅作为后台系统管理，不可放置业务功能代码；通过调用API服务项目接口来对接业务功能管理。
可选择前端js或后台controller调用的方式联结业务。  
- 通过使用AdminLTE-master.zip模版进行开发

**注意**
- 需开启跨域访问
- 系统管理权限被修改后需要登出在登录，也可增加对权限变更后刷新session
- redis限制登录密码错误次数


