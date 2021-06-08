# ubuntun使用问题记录

## 1、E: 无法获得锁 /var/lib/dpkg/lock-frontend。锁正由进程 4860（unattended-upgr）持有，N: 请注意，直接移除锁文件不一定是合适的解决方案，且可能损坏您的系统。E: 无法获取 dpkg 前端锁 (/var/lib/dpkg/lock-frontend)，是否有其他进程正占用它？

解决方法：

``` shell
#强制解锁：
sudo rm /var/lib/dpkg/lock-frontend
sudo rm /var/cache/apt/archives/lock  
sudo rm /var/lib/dpkg/lock
```

## 2、docker安装记录（系统版本：Ubuntu 20.04 LTS ）

````shell
1、更新 apt-get 源
sudo apt-get update`

2、安装包允许apt-get 通过http使用仓库
sudo apt-get install apt-transport-https ca-certificates
curl software-properties-common

3、添加docker官方 GPG key
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

4、设置docker稳定版仓库
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"

5、设置仓库完毕，重新更新数据源
sudo apt-get update

6、安装最新docker
sudo apt-get install docker-ce
````

设置docker开机自启

```shell
sudo systemctl enable docker
```

在上述第五步更新数据源时可能会出现如下错误（我安装时未遇到）

```shell
获取:1 http://security.ubuntu.com/ubuntu focal-security InRelease [107 kB]                                                                                                   
忽略:2 https://download.docker.com/linux/ubuntu focal InRelease                                                                                                                          
错误:3 https://download.docker.com/linux/ubuntu focal Release                                                                                              
  404  Not Found [IP: 13.225.103.65 443]
命中:4 http://mirrors.163.com/ubuntu focal InRelease                                                                                  
命中:5 http://mirrors.163.com/ubuntu focal-updates InRelease                                                                          
命中:6 http://mirrors.163.com/ubuntu focal-backports InRelease                                                 
忽略:7 http://dl.google.com/linux/chrome/deb stable InRelease                                     
命中:8 http://dl.google.com/linux/chrome/deb stable Release                  
命中:9 http://archive.ubuntukylin.com:10006/ubuntukylin trusty InRelease
正在读取软件包列表... 完成                                                                                                  
E: 仓库 “https://download.docker.com/linux/ubuntu focal Release” 没有 Release 文件。
N: 无法安全地用该源进行更新，所以默认禁用该源。
N: 参见 apt-secure(8) 手册以了解仓库创建和用户配置方面的细节。
```

处理方法

```shell
在 /etc/apt/source.list 文件中增加如下配置
deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable
```

然后再次更新源

``` she
sudo apt-get update		
```

可能或出现以下错误

```shell
获取:1 http://security.ubuntu.com/ubuntu focal-security InRelease [107 kB]                                                                                                   
获取:2 https://download.docker.com/linux/ubuntu bionic InRelease [64.4 kB]                                                                                                               
命中:3 http://mirrors.163.com/ubuntu focal InRelease                                                               
命中:4 http://mirrors.163.com/ubuntu focal-updates InRelease                                                                          
命中:5 http://mirrors.163.com/ubuntu focal-backports InRelease                                                                      
忽略:6 http://dl.google.com/linux/chrome/deb stable InRelease  
命中:7 http://dl.google.com/linux/chrome/deb stable Release                                       
命中:8 http://archive.ubuntukylin.com:10006/ubuntukylin trusty InRelease                          
忽略:10 https://download.docker.com/linux/ubuntu focal InRelease             
获取:11 https://download.docker.com/linux/ubuntu bionic/stable amd64 Packages [11.0 kB]
错误:12 https://download.docker.com/linux/ubuntu focal Release                  
  404  Not Found [IP: 13.225.103.32 443]
正在读取软件包列表... 完成                                             
E: 仓库 “https://download.docker.com/linux/ubuntu focal Release” 没有 Release 文件。
N: 无法安全地用该源进行更新，所以默认禁用该源。
N: 参见 apt-secure(8) 手册以了解仓库创建和用户配置方面的细节。
```

可以看到的是，上面的 11 已经是成功的，12 是错误的，这个时候，我们只需要其 /etc/apt/source.list 中删除掉报错对应的内容，然后再次更新源。

原文链接；https://www.cnblogs.com/gaofangye/p/12812486.html

## 3、docker配置加速

```shell
1、在/etc/docker文件夹下创建daemon.json文件，默认此文件夹是不存在的
vi /etc/docker/daemon.json

2、然后将官方加速器（下边键值）加入到daemon.json中。
{
  "registry-mirrors": ["https://registry.docker-cn.com"]
}

3、重启docker服务
service docker restart
```

```shell
国内镜像地址
#网易
http://hub-mirror.c.163.com
#ustc
https://docker.mirrors.ustc.edu.cn
```

原文链接：[https://www.jianshu.com/p/9072f99218d1](https://www.jianshu.com/p/9072f99218d1)



## 4|、docker启动mysql指令

```shell
docker run -p 3306:3306 --name mysql -v /mydata/mysql/log:/var/log/mysql -v /mydata/mysql/data:/var/lib/mysql -v /mydata/mysql/conf:/etc/mysql -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7
```

启动后修改mysql编码为utf-8

```shell
#默认没有这个文件
vi /mydata/mysql/conf/my.cnf

#将下面内容复制到mysql.cnf中
[client]
dafault-character-set=utf8

[mysql]
dafault-character-set=utf8

[mysqld]
init_connect='SET collation_connection = utf8_unicode_ci'
init_connect='SET NAMES utf8'
character-set-server=utf8
collation-server=utf8_unicode_ci
skip-character-set-client-handshake
skip-name-resolve

#查询表明忽略大小写
lower_case_table_names=1
```

然后重启mysql镜像

``` shell
docker restart mysql
```



## 5、docker启动redis指令

```shell
#默认没有redis.conf文件，直接执行docker run会创建redis.conf文件夹
mkdir -p /mydata/redis/conf
touch /mydata/redis/conf/redis.conf

docker run -p 6379:6379 --name redis \
-v /mydata/redis/data:/data \
-v /mydata/redis/conf/redis.conf:/etc/redis/redis.conf \
-d redis redis-server /etc/redis/redis.conf

#启动后在redis.conf中加入下列配置
appendonly yes

#进入docker中redis客户端控制台（最新版redis已经默认支持持久化，不需要添加下列配置）
docker exec -it redis redis-cli
```

##  6、docker设置容器自动重启

```shell
docker update mysql --restart=always
docker update redis --restart=always
```





