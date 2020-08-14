
# Docker

虚拟机技术与容器技术的对比：

- ![虚拟机技术与容器技术的对比](./图片/虚拟机技术与容器技术的对比.png)
- 容器技术是 APP 层面的隔离
- 虚拟化技术是物理资源层面的隔离

Docker 能干什么：

- 简化配置
- 整合服务器
- 代码流水线管理
- 调试能力
- 提高开发效率
- 多租户
- 隔离应用
- 快速部署

开发流程：

![开发流程](./图片/开发流程.png)

什么是容器？

- 对软件和其依赖的标准打包
- 应用之间相互隔离
- 共享同一个 OS Kernel (系统核心)
- 可以运行在很多主流操作系统上

</br>

## 安装

到 [官网](https://docs.docker.com/) 可以查看介绍以及下载。

### Mac & Windows

对于 [Mac](https://docs.docker.com/docker-for-mac/install/) 和 [Windows](https://docs.docker.com/docker-for-windows/install/) ，都是点击 "Download from Docker Hub" 按钮即可下载。

需要注意的是， Windows 的安装需求有点苛刻，详细请看官网。底层原理是利用虚拟技术，创建一个"小型Linux"系统，用于运行 Docker 。

之后，在控制台输入 `docker version` 即可验证 Docker 是否安装成功。






### CentOS

TODO:

1. 先卸载
2-6




### Vagrant + VirtualBox (推荐)





### Docker Machine

对于 Mac 和 Windows 系统，如果已经安装了对应版本的 Docker ， Docker Machine 也是包含其中。

使用 Docker Machine 可以快速创建一个包含 Docker 的虚拟机。命令如下：

``` shell
# 期间可能会下载包含 Docker 的 ISO 镜像文件
docker-machine create <name>
```

需要注意的是，提前安装 VirtualBox 软件，安装的虚拟机是在它上面运行。

安装完成后，就可以使用了。常用命令如下：

``` shell
# 连接虚拟机
docker-machine ssh <name>

# 查看所有命令
docker-machine


```

这里涉及到 Client 和 Server 端的使用。

- 默认安装之后，本地的 docker 命令就是 Client 端，是直接控制 Server 端。
- 在本地运行 `docker version` 命令，能够看到 Client 和 Server 端的版本信息。
- 假如 Server 端被停掉，运行 `docker version` 命令会只看到 Client 端信息。
- 如何添加本地的 docker 作为 Server 端：
  - 运行 `docker-machine start <name>` 启动包含 docker 的虚拟机
  - 运行 `docker-machine env <name>` 配置该机器的环境变量
  - 运行 `eval $(docker-machine env <name>)` 使本地 docker 命令映射到 Server 端






### Docker Playground

[Docker Playground](https://labs.play-with-docker.com/) 是一个网页版的 Docker ，无需安装，即可连接到远程的临时服务器。对于学习 Docker 是一个不错的选择。




</br>






</br>











</br>











</br>











</br>











</br>











</br>











</br>











</br>



