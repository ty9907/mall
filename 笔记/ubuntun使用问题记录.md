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
sudo apt-get install apt-transport-https ca-certificates curl software-properties-common

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



## 4、为普通用户添加docker使用权限

```shell
sudo groupadd docker #添加docker用户组
sudo gpasswd -a $USER docker #将登陆用户加入到docker用户组中
newgrp docker #更新用户组

#或者使用以下命令
sudo chmod a+rw /var/run/docker.sock
```

原文链接：[https://blog.csdn.net/u011337602/article/details/104541261](https://blog.csdn.net/u011337602/article/details/104541261)



## 5、docker启动mysql指令

```shell
docker run -p 3306:3306 --name mysql \
-v /mydata/mysql/log:/var/log/mysql \
-v /mydata/mysql/data:/var/lib/mysql \
-v /mydata/mysql/conf:/etc/mysql -e MYSQL_ROOT_PASSWORD=root \
-d mysql:5.7
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



## 6、docker启动redis指令

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

##  7、docker设置容器自动重启

```shell
docker update mysql --restart=always
docker update redis --restart=always
```



## 8、 Docker安装ElasticSearch

```shell
docker pull elasticsearch:7.4.2
docker pull kibana:7.4.2

mkdir -p /mydata/elasticsearch/config
mkdir -p /mydata/elasticsearch/data
echo "http.host: 0000">>/mydata/elasticsearch/config/elasticsearch.yml  #创建配置文件

docker run --name elasticsearch -p 9200:9200 -p 9300:9300 \
-e "discovery.type=single-node" \
-e ES_JAVA_OPTS="-Xms256m -Xmx512m" \
-v /mydata/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
-v /mydata/elasticsearch/data:/usr/share/elasticsearch/data \
-v /mydata/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
-d elasticsearch:7.4.2

#运行docker镜像时出现以下错误
docker: Error response from daemon: OCI runtime create failed: container_linux.go:380: starting container process caused: process_linux.go:545: container init caused: rootfs_linux.go:76: mounting "/mydata/elasticsearch/config/elasticsearch.yml" to rootfs at "/usr/share/elasticsearch/config/elasticsearch.yml" caused: mount through procfd: not a directory: unknown: Are you trying to mount a directory onto a file (or vice-versa)? Check if the specified host path exists and is the expected type.
#原因是elasticsearch.yml被我建成了目录；删除目录，重新生成文件即可运行镜像
rm -r /mydata/elasticsearch/configelasticsearch.yml/
touch /mydata/elasticsearch/config/elasticsearch.yml

#使用docker ps -a 发现elastixsearch 状态为退出，原因是 /mydata/elasticsearch 访问失败，为目录添加权限，
chmod -R 777 /mydata/elasticsearch
#再次启动elasticsearch
docker start {imageid}
```

启动kibana

```shell
docker run --name kibana -e ELASTICSEARCH_HOSTS=http://192.168.159.128:9200 -p 5601:5601 \
-d kibana:7.4.2
```



## 9、使用vi方向键变为ABCD

由于ubuntu预安装的是tiny版本，所以会导致我们在使用上的产生上述的不便。但是，我们安装了vim的full版本之后，键盘的所有键在vi下就很正常了。

首先，要先卸掉旧版的vi，输入以下命令：

```shell
sudo apt-get remove vim-common
```

然后安装full版的vim，输入命令：

```shell
sudo apt-get install vim
```

这样安装好了之后，我们在编辑文件的时候依然是使用“vi”命令来启动新装的vim，但是操作起来比tiny更加方便了。

原文链接：[https://www.awaimai.com/262.html](https://www.awaimai.com/262.html)

## 10、ElasticSearch安装ik分词器

```shell
#分词器版本需要与elasticsearch相同，我的是7.4.2版本

#进入easticsearch目录下的plugin目录，新建ik目录，使用下面指令下载插件，或者使用ftp等方式将下载后的插件上传到ik目录，然后解压
wget https://github-releases.githubusercontent.com/2993595/19827980-fef3-11e9-8cda-384bc0d9396c?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIWNJYAX4CSVEH53A%2F20211117%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20211117T141929Z&X-Amz-Expires=300&X-Amz-Signature=ad3470f451ba6c521e9b43ca619ee441d1ecbaadf750791e33ae74dfc763f217&X-Amz-SignedHeaders=host&actor_id=53524785&key_id=0&repo_id=2993595&response-content-disposition=attachment%3B%20filename%3Delasticsearch-analysis-ik-7.4.2.zip&response-content-type=application%2Foctet-stream
```

```shell
#进入容器内部bin目录下，查看插件，出现ik说明插件安装成功
docker exec -it ${imageid} /bin/bash
cd bin/
elasticsearch-plugin list
```

## 11、docker启动nginx

````shell
docker run -p 80:80 --name nginx \
-v /mydata/nginx/html:/usr/share/nginx/html \
-v /mydata/nginx//logs:/var/log/nginx \
-v /mydata/nginx/conf:/etc/nginx \
-d nginx
````

