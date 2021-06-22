# mysql archive 安装

1、下载mysql 压缩包 ，下载地址：[https://dev.mysql.com/downloads/mysql/](https://dev.mysql.com/downloads/mysql/)。解压至安装目录。

2、不要在安装目录下新建data文件夹，初始化会自动新建，在mysql安装目录下新建 my.ini 文件，输入以下内容：

```sql
[mysqld]
 
# 设置3306端口
 
port=3306
 
# 设置mysql的安装目录
 
basedir=D:/softwares/mysql/mysql-8.0.25-winx64
# 设置mysql数据库的数据的存放目录
 
datadir=D:/softwares/mysql/mysql-8.0.25-winx64/data
 
# 允许最大连接数
 
max_connections=200
 
# 允许连接失败的次数。
 
max_connect_errors=10
 
# 服务端使用的字符集默认为UTF8
 
character-set-server=utf8
 
# 创建新表时将使用的默认存储引擎
 
default-storage-engine=INNODB
 
# 默认使用“mysql_native_password”插件认证
 
#mysql_native_password
 
default_authentication_plugin=mysql_native_password
 
default-time-zone='+08:00'
#sql_mode="STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION"
 
[mysql]
 
# 设置mysql客户端默认字符集
 
default-character-set=utf8
 
[client]
 
# 设置mysql客户端连接服务端时默认使用的端口
 
port=3306
default-character-set=utf8
```

3、以管理员的方式进入到解压的\bin目录下

4、执行：

```
mysqld install
```

5、执行：

```
mysqld --initialize --console
```

出现以下内容，注意此时出现的临时密码，后面修改密码要用；

![mysql](D:\documents\chrome\mysql.png)

6、执行

```sql
net start mysql
```

![runstart](D:\documents\chrome\runstart.png)

7、配置MySQL环境变量：右键此电脑 -> 属性 -> 高级系统设置 -> 环境变量 ；

在系统变量下面新建，MYSQL_HOME 作为键，你解压的MySQL路径为值，例如我的路径是：D:\softwares\mysql\mysql-8.0.25-winx64

8、配置Path，在系统变量下找到Path，双击，新建，输入%MYSQL_HOME%\bin

9、修改密码；cmd执行

```
mysql -uroot -p
```

![](D:\documents\chrome\pass.png)

​	

输入刚才的临时密码，Enter，如下显示已经成功登录了MySQL8

![passchange](D:\documents\chrome\passchange.png)

接下来修改密码：

```shell
ALTER USER 'root'@'localhost' identified with mysql_native_password by 'root';
```

如下图修改成功：

![change](D:\documents\chrome\change.png)





```
启动MYSQL：net start mysql
停止MYSLQ：net stop mysql
移除mysql mysqld remove
```

