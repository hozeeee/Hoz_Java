
# 简介

Linux 分**内核版本**和**发行版本**。

内核版本的下载可以到 [https://www.kernel.org/](https://www.kernel.org/) ，下面简单说一下版本号的含义：

- 版本号分三部分：主版本号、次版本号、末版本号。与其他软件的版本号命名相同
- 次版本号是奇数的为开发版；偶数的为稳定版。 (2.6 之后取消这种规定)

发行版本，是其他厂商定制的，可能需要付费，下面说几个常用的：

- RedHat Enterprise Linux ，付费，提供技术支持，稳定
- Fedora ，免费，提供社区，比较新，稳定低于前者
- [**CentOS**](https://www.centos.org/) ，免费，基于 RedHat Enterprise Linux ，在它基础上去了商标，推荐使用。
- Debian ， Ubuntu ，提供图形界面

学习 Linux 建议使用 **VirtualBox** 虚拟机， [下载地址](https://www.virtualbox.org/wiki/Downloads) 。

- 在虚拟机中，按右边的 `Ctrl` 退出"鼠标独享"。

在命令行终端中，以 `$` 开头的是普通用户，以 `#` 开头的是管理员用户。

常见目录介绍：

- `/`: 根目录
- `/root`: root 用户的主目录
- `/home/<username>`: 普通用户的主目录
- `/etc`: 配置文件目录
- `/bin`: 存放可执行命令的目录
- `/sbin`: 存放管理命令的目录
- `/usr/bin`、`/usr/sbin`: 系统预装的其他命令

命令的需要多个参数，可以"连写"，如 `ls -l -a -R` 可以写成 `ls -laR` ，三个字母的顺序任意。

</br>
</br>

# 常用命令

## 帮助命令

### man

`man` 是 `manual` 的缩写。

帮助文档分 9 章，如果是查询第 1 章，`1` 可以忽略。

各章节的内容如下 (通过 `man 7 man` 获取更详细)：

1. Commands: 用户可从 shell 运行的命令
2. System calls: 必须由内核完成的功能
3. Library calls: 大多数 libc 函数，例如 `qsort(3)`
4. Special files: `/dev` 目录中的文件
5. File formats and conventions: `/etc/passwd` 等认类可读的文件的格式说明
6. Games
7. Macro packages and conventions: 文件系统标准描述，网络协议，ASCCII和其他字符集，还有你眼前这份文档
8. System management commands: 类似 `mount(8)` 等命令，大部分只能由 `root` 权限用户执行
9. Kernel routines: 废弃章节。

### help

**内部命令**使用 `help` 获取帮助，如 `# help cd` 。

内部命令就是， shell 自带的命令。

### info

`info` 比 `help` 更详细，可以作为 `help` 的补充。

示例： `# info ls` 。

</br>

## 文件的复制移动

### pwd

用于显示当前目录名称。

### cd

用于更改当前的操作目录。

`cd -` 可以回到前一个进入"当前目录"。

### ls

用于查看当前目录下的文件，常用参数如下

- `-l` 长格式显示
- `-a` 显示隐藏文件
- `-r` 倒序显示
- `-t` 按时间顺序显示
- `-R` 递归显示

支持通配符规则：（`*` 表示可有 0~n 个字符；`?` 表示必须有一个任意字符）

- 例如 `ls *.target` 会列出以 `target` 结尾的文件；
- 例如 `ls runlevel*.target` 会列出类似 `runlevel2.target` 的文件。
- 例如 `ls /dev/sda?` 会列出如 `/dev/sda1`|`/dev/sda2` 等的文件。

### mkdir

`mkdir ./a` 或 `mkdir a` 在当前目录下创建 a 文件夹。

`mkdir /a` 在根目录下创建 a 文件夹。

`mkdir -p a/b/c/d` 可以一次创建多级目录。

### rmdir

`rmdir a` 只能删除 a 文件夹（且 a 必须是空文件夹）

`rm -r a` 可以删除非空的文件夹和文件

### cp

`cp [options] [source_path] [target_path]` 复制文件到指定路径。

常用参数介绍：

- `-r`: 默认情况下， `cp` 只能复制文件，若需要复制目录，请使用此参数。
- `-v`: 默认情况下，复制没有进度条，此参数可以显示进度。
- `-p`: 默认情况下，复制后的文件的属性(如作者、修改时间)会发生改变，使用此参数可以保留源文件的用户、权限、时间等文件属性。
- `-a`: 是 `-p` 的"升级版"，等同于 `-dpR` ，保留源文件的所有属性。

### mv

`mv [options] [source_path] [target_path]` 移动目录或文件，若目标目录或文件名与源目录或文件不相同，则同时进行了重名名操作。

</br>

## 文本查看

### cat

在终端上显示文本内容

### head

查看本舰开头

### tail

查看文件结尾

-f 文件内容更新后，显示信息同步更新

### wc

统计文件内容信息

</br>

## 打包、压缩、解压缩

由于历史原因，打包和压缩是两个步骤。

打包： `tar cf [target_file] [source_dir]` 把 `[source_dir]` 目录的所有文件都压缩到 `[target_file]` 。

解包： `tar xzf [package_file] -C [target_dir]` 把压缩包文件解压到 `[target_dir]` ， `xjf` 同理。

- `c` 参数表示打包。
- `x` 参数表示解包。
- `f` 参数表示指定操作类型为文件。
- `tar` 命令的参数是不带 `-` 的，这点和大多数的命令有所区别。
- `z` 参数代表打包的时候就进行压缩。也可以单独使用 `gzip` 进行压缩，注意文件名以 `*.tar.gz` 命名。
- `j` 参数也代表打包的时候就进行压缩。也可以单独使用 `bzip2` 进行压缩，注意文件名以 `*.tar.bz2` 命名。压缩比例比 `gzip` 要高，所以压缩耗时也更长。

关于扩展名：

- `gzip` 的压缩包的扩展名，除了上面说的，还可能是 `.tgz` 。
- `bzip2` 的压缩包的扩展名，除了上面说的，还可能是 `.tar.bzip2` 、 `.tbz2` 。

</br>
</br>

# vi(m) 编辑器

vim 是基于 vi 的一个编辑器。

有 4 种输入模式：

- 正常模式：
  - 默认进入的就是正常模式，在其他模式下，任意模式下通过键盘的 `esc` 键也能回到此模式。
  - 光标移动操作：
    - 按 `h` 使光标向**左**移动；
    - 按 `j` 使光标向**下**移动；
    - 按 `k` 使光标向**上**移动；
    - 按 `l` 使光标向**右**移动。
    - 按 `^` 使光标移动到当前**行开头**；
    - 按 `$` 使光标移动到当前**行末尾**；
    - 按 `G` 使光标移动到**最后一行**；
    - 按 `<n>G` (即`<n> shift+g`) 可以把光标移动到**第`<n>`行**。
  - 复制：
    - 按 `yy` 复制光标所在的整行；
    - 按 `<n>yy` 表示从当前行开始，复制 `<n>` 行，如 `3yy` 表示复制三行；
    - 按 `y$` 表示复制从光标位置到该行末尾；
  - 剪切：
    - 按 `dd` 剪切光标坐在的整行；
    - 配合"方向键"(下面用`<orient>`表示)使用：
      - 按 `dh` 剪切光标的左边的单个字符；
      - 按 `dl` 剪切光标所在的字符；
      - 按 `dj` 剪切光标所在行和下一行的内容；
      - 按 `dk` 剪切光标所在行和上一行的内容；
    - 按 `d<orient>` 剪切光标到特定方向键位置的内容，如 `dl` 就是删除(`<orient>`是`h`/`j`/`k`/`l`中的一个)；
    - 按 `d$` 表示剪切从光标位置到改行末尾。
  - 按 `p` **粘贴**复制或剪切的内容。
  - 按 `x` **删除**光标所在的**单个**字符。
  - 按 `r` 后输入字符，可**替换**光标所在的**单个**字符。
  - 按 `u` **撤销**上一个命令。
  - 按 `Ctrl + r` **还原**上一个被撤销的命令。

- 插入模式：
  - 在插入模式下，可以正常输入文本。在正常模式下，有以下 6 种方式进入此模式：
    - `i` 表示在当前光标处进入插入模式；
    - `I` 表示在当前行开头进入插入模式；
    - `a` 表示在当前光标后(一个字符的位置)进入插入模式；
    - `A` 表示在当前行末尾进入插入模式；
    - `o` 表示在当前行的下一行插入一个新行，在新行的开头进入插入模式；
    - `O` 表示在当前行的上一行插入一个新行，在新行的开头进入插入模式；

- 命令模式(末行模式)：
  - 正常模式下按 `:` 接需要执行的命令。
    - 输入 `set nu` 后，显示每一行的行号； `set nonu` 则是关闭行号显示。
    - 输入 `w <filename>?` 后，将内容**保存**到`<filename>`(可以包含路径)；若通过打开文件的方式，`<filename>`则可以省略，表示保存到源文件中。
    - 输入 `q` 后，会**退出** vi(m) 编辑器，文件必须已保存或未修改。
    - 输入 `q!` 后，表示**不保存**退出。
    - 输入 `wq` 后，是 `w` 和 `q` 的组合，表示保存并退出。
    - 输入 `!<command>` 后，表示**暂时离开编辑器**，**并执行`<command>`命令**，如`!ifconfig`会查看ip地址，然后再回来继续编辑。
    - 关于文本**替换**：
      - 输入 `s/<souce_string>/<new_string>` 后，可以将**光标所在行**的**第一个** `<souce_string>` 的内容替换为 `<new_string>` 。
      - 输入 `%s/<souce_string>/<new_string>` 后，可以**全文替换**。
      - 输入 `%s/<souce_string>/<new_string>/g` 后，可以全文**替换所有**。
      - 输入 `<start_line>,<end_line>s/<souce_string>/<new_string>` 后，可以**针对第 `<start_line>` 行到 `<end_line>` 行**进行替换。
  - 正常模式下按 `/` 接需要**查找**的内容，如`/x`后按回车，就可以高亮显示`x`的内容。

- 可视模式：
  - 可视模式的作用是选中特定的区域，然后进行编辑操作。
  - 正常模式下按 `v` 进入**字符**可视模式，以字符为单位选中。
  - 正常模式下按 `V` 进入**行**可视模式，以行为单位选中。
  - 正常模式下按 `Ctrl + v` 进入**块**可视模式，以"矩形"的方式框选。
  - 可视模式下选中了区域，按上面介绍的命令就可以进行操作，如按`i`进入插入模式，就会对选中的多行输入相同的内容

## 其他

- `exit` 退出当前登陆的用户。

</br>
</br>

# 用户与权限管理

用户类型有两种： root 用户和普通用户。

- `useradd <username>` 新建 `<username>` 用户
- `userdel <user>?` 删除 `<user>` 用户 (建议带`-r`参数,表示连同删除`/home`目录下该用户的文件夹)
- `passwd <user>?` 修改 `<user>` 用户的密码；当省略 `<user>` 则是对当前用户修改密码 (输入命令后会提示输入新密码)
- `usermod <options> <user>` 修改 `<user>` 用户属性
- `chage <options> <user>` 修改 `<user>` 用户密码的过期时间

查看已有的用户：

- `id <username>` 确定是否存在 `<username>` 用户
- `cat /etc/shadow` 会包含已创建的用户
- `cat /etc/passwd` 也会包含已创建的用户

操作用户组：

- `groupadd <groupname>` 新建 `<groupname>` 用户组，然后通过 `usermode` 命令修改用户的所属用户组
  - 新建用户时，也可以添加选项 `-g <groupname>` 可以在创建用户的同时加入 `<groupname>` 用户组
- `groupdel <groupname>` 删除用户组

</br>
</br>

# 用户切换、命令权限

使用 `su` 命令可以临时切换用户，用于执行特定的命令。

- `su - <user>` 的 `-` 表示"完全切换"，否则可能身处无权限访问的目录下。
- 在"临时用户"下，可以使用该用户的所有权限。
- 一样是通过 `exit` 退出"临时用户"。
- root 用户可以无需密码就切换到普通用户，而普通用户切换其他用户则需要输入密码。

使用 `sudo` 以其他用户身份执行命令，但前提是该用户( root 用户)已经授权给本用户。

- 如 `sudo -u root shutdown -h 30` 中， `-u root` 表示以 root 用户身份执行命令，可以省略，默认就是以 root 身份执行； `shutdown -h 30` 是目标命令和参数。

管理员账号下，使用 `visudo` 可以给特定的用户或用户组添加某个命令的权限。

- 执行 `visudo` 后进入配置文件的编辑，文件内包含用例。
- 配置文件中以 `#` 开头的是注释内容。
- 简单说一下配置：
  - `%wheel localhost=/sbin/shutdown -h now`: `%wheel`表示被赋权限的用户组，也可以指定单个用户(省略`%`)；`localhost`是权限主机；`/sbin/shutdown -h now`是给用户(组)开放权限的命令，命令可以指定参数。
  - `user1 ALL=/sbin/mount /mnt/cdrom, /sbin/unmount /mnt/cdrom`: 可以同时指定多个开放权限的命令，用`,`分隔；`ALL`表示所有主机，包括远程主机。
  - `user2 ALL=(ALL) NOPASSWD: ALL`: `(ALL)`表示开放所有命令的权限；`NOPASSWD: ALL`是可选配置，表示不用密码，不建议开启。
- 若不知道**命令所在的位置**，执行 **`which <command>`** 即可获取。

</br>
</br>

# 用户和用户组的配置文件

`vim /etc/passwd`: 该文件会保存已创建的**用户**。会有多行，一行代表一个用户，有 7 个字段，用 `:` 分隔，如 `root:x:0:0:root:/root:/bin/bash` ：

1. 第一部分， `root` 表示**用户名**；
2. 第二部分， `x` 表示**需要使用密码登陆**，若不需要密码登陆，缺省即可；
3. 第三部分， `0` 表示 **uid** ，即用户 id ， Linux 中区分用户就是通过此 id ；
4. 第四部分， `0` 表示 **gid** ，即用户组 id ， Linux 中区分用户组就是通过此 id ；
5. 第五部分， `root` 表示**描述**，可以缺省；
6. 第六部分， `/root` 表示该用户的 **home 目录**；
7. 第七部分， `/bin/bash` 表示该用户的**命令解析器**，当设为 `/sbin/nologin` 表示**不允许**该用户**登陆**终端。

`vim /etc/shadow`: 该文件保存**用户密码**相关的配置。同样会有多行，一行代表一个用户，有 9 个字段，同样用 `:` 分隔，如 `user1:$6$....:17834:0:99999:7:::` ，下面只介绍前两部分：

1. 第一部分， `user1` 表示**用户名称**；
2. 第二部分， `$6$....` 表示加密过的密码，一般以 `$6$` 开头，需要注意，即使两个用户的密码相同，这里的值也不会相同。

`vim /etc/group`: 该文件表示已创建的**用户组**。还是多行，一行代表一个用户组，有 4 个字段，用 `:` 分隔，如 `root:x:0:rootuser` ：

1. 第一部分， `root` 表示**用户组名称**；
2. 第二部分， `x` 表示**需要使用密码登陆**，若不需要密码登陆，缺省即可；
3. 第三部分， `0` 表示 **gid** ，即用户组 id ， Linux 中区分用户组就是通过此 id ，与 `/etc/passwd` 文件对应；
4. 第四部分， `rootuser` 是一个用户，该用户的"其他用户组"就属于 `root` 用户组，即用户可以同时属于多个用户组，多个用 `,` 分隔。

</br>
</br>

# 关于文件的权限

`ls -l` 可以获取当前目录的文件的详细列表，其中包含文件的权限信息。示例：`-rw-r-xr-- 1 username groupname mtime filename` 。

``` txt
 -rw-rw-r--. 2 root root  4 6月  11 14:17 file1
```

1. 第 1 位，代表文件的类型，有如下几种：
    - `-` 表示普通文件
    - `d` 表示目录文件
    - `b` 表示块特殊文件，如硬盘设备
    - `c` 表示字符特殊文件，如 shell
    - `l` 表示符号链接
    - `f` 表示命名管道
    - `s` 表示套接字文件

2. 第 2~10 位，表示文件处于不同用户/用户组下的权限，
    - 前三位表示：文件属**用户**的权限
    - 中间三位表示：文件属**组**的权限
    - 后三位表示：**其他用户**的权限
    - 每一位出现的字符都是 `r`/`w`/`x`/`4`/`2`/`1`/`-` 中的一个。
    - 对于普通文件，字符的含义是：
      - `r` 表示可读，等效于 `4`
      - `w` 表示可写，等效于 `2`
      - `x` 表示可执行，等效于 `1`
      - `-` 表示空
    - 对于目录文件，字符的含义是：
      - `x` 表示可**进入**目录
      - `rx` 表示可**显示**目录内的文件名
      - `wx` 表示可**修改**目录内的文件名

修改文件的权限的命令如下：

- `chmod [u/g/o/a][+/-/=] <file>` 修改文件、目录权限。
  - `[u/g/o/a]` 的意思是值为 `u`/`g`/`o`/`a` 中的一个，其含义分别是：
    - `u` 表示设置"所属用户位"的权限
    - `g` 表示设置"所属组位"的权限
    - `o` 表示设置"其他用户位"的权限
    - `a` 表示所有的"权限位"一起设置
  - `[+/-/=]` 表示运算符，值为 ``/``/`` 中的一个，其含义分别是：
    - `+` 表示新增权限
    - `-` 表示减少权限
    - `=` 表示设置权限
  - 示例如下：(假设`testfile`文件的权限值为 `rw-r--r--`,例子间无联系)
    - `chmod u+x /testfile` 表示"所属用户位"增加"执行"权限，执行后的权限变为 `rwxr--r--`
    - `chmod 755 /testfile` 表示直接设置权限，执行后的权限变为 `rwxr-xr-x`
    - `chmod a-r /testfile` 表示所有的"权限位"都减少读权限，即执行后的权限变为 `-w-------`

- `chown <target_user>? :<group_name>? <file>` 更改所属用户、所属组。示例如下：
  - `chown user1 /test` 把 `/test` 目录的所属用户改为 `user1`
  - `chown :group1 /test` 把 `/test` 目录的所属组改为 `group1`

- `chgrp` 单独修改所属组，不常用

## facl

如果想针对某个文件，A 用户只读，B 用户可写，C 用户可执行，原始的文件权限控制不能做到。

`getfacl file1`: 查看 `file1` 的 facl 权限，输入如下内容：

  ``` txt
  # file: file1
  # owner: user1
  # group: user1
  user::rw-
  group::rw-
  other::r--
  ```

`setfacl -m u:user2:r file1`: 为用户 user2 增加对 `file1` 的 facl 读权限：

- `-m` 表示赋予权限；`-x` 表示回收权限。
- `u:` 表示对用户设置；也可以使用 `g:` 对用户组设置。
- `:r` 表示设置读权限，其他权限设置同理。

还有需要注意的，如果已经配置了 facl 权限，`ls -l` 查看到的权限的第 11 位会是一个 `+`。

</br>
</br>

# 网络管理

CentOS7 之前，网卡的命名都是以 `eth*` 的方式命名，好处是方便批量处理；而 CentOS7 及以后，网卡的命名规则如下：

- `eno1` 表示板载网卡
- `ens33` 表示 PCI-E 网卡
- `enp0s3` 表示 无法获取物理信息的 PCI-E 网卡

CentOS7 及以后的版本，也可以通过修改配置文件，实现以 `eth*` 的方式命名网卡，具体不细说了。

root 用户可以直接执行 `ifconfig` 查看网卡设备；普通用户则需要使用 `/sbin/ifconfig` 。

`mii-tool eth0` 可以查看 `eth0` 网卡的物理连接情况。

`route -n` 查看网关， `-n` 表示不解析主机名。

`ifup <网卡>` 和 `ifdown <网卡>` 是分别对网卡打开和关闭。

故障排除相关的命令：

- `ping www.baidu.com` 检测与该主机之间的链路是否为通，要考虑是否防火墙拦截了。
- `traceroute -w 1 www.baidu.com` 可以检测与目标主机之间的网络情况，其中 `-w 1` 表示超时时只等待 1 秒。
- `mtr` 可以动态(以表格的形式)显示本主机各个连接的具体情况。
- `nslookup www.baidu.com` 将域名解析成 IP 地址。
- `telnet www.baidu.com 80` 检测到目标主机的端口是否畅通。若提示"未找到命令"，可以通过 `yum install telnet` 安装。
- `tcpdump -i any -n host 10.0.0.1 and port 80 -w /tmp/filename` 抓包并保存到文件。
- `netstat -ntpl` 查看本机启动的服务。 `ss` 命令与此命令类似。

</br>
</br>

# 软件包管理

包管理有两个：

- CentOS、RedHat 使用 yum 包管理器，软件包的格式为 rpm ，下面只介绍这种；
- Debian、Ubuntu 使用 apt 包管理器，软件包的格式为 deb 。

rpm 包命名格式，以 `vim-common-7.4.10-5.el7.x86_64.rpm` 为例：

- `vim-common` 是软件名
- `7.4.10-5` 是软件版本
- `el7` 是系统版本
- `x86_64` 是平台

`rpm` 命令的常用参数：

- `-q` 查询软件包
- `-i` 安装软件包
- `-e` 卸载软件包软件包

`yum` 命令的常用参数：(建议改成国内镜像源)

- `install` 安装软件包
- `remove` 卸载软件包
- `list`/`grouplist` 查看软件包
- `update` 升级软件包

源代码编译方式安装：

1. `wget <url>` 下载压缩包。
2. `tar <file>` 解压缩包。
3. 进入解压后的目录， 执行 `configure` 文件，若没有，则查看 `README*` 文件。
4. 同样在该目录下，执行 `gmake -j2` 执行编译。完成后会在当前目录下生成 `build` 目录。
5. 还是在该目录下，执行 `make install` 进行安装。

内核升级：(有两种方式)

- 通过 `yum`
  - 默认的 `yum` 仓库能安装的内核版本都比较低，但可以通过 `yum install epel-release -y` 扩展仓库。
  - 通过上面的扩展之后，执行 `yum install kernal` 安装仓库内最新版本的内核；若需要安装指定版本，则执行如 `yum install kernal-3.10.0` 的命令。

- 通过源代码编译方式安装
  - 下载内核压缩包： [http://www.kernel.org/](http://www.kernel.org/) 。
  - 安装依赖： `yum install gcc gcc-c++ make ncurses-devel openssl-devel elfutils-libelf-devel` ，若之前安装了，可能就不需要再安装。
  - 解压： `tar vxf linux-5.1.14.tar.xz -C /usr/src/kernals/` 。
  - 定位到解压后的目录： `cd /usr/src/kernals/linux-5.1.14/` 。
  - 指定需要安装的配置项： `make menuconfig` ，执行后会进入一个图形界面，可以选择功能安装。结束后会回到命令行终端，且生成 `.config` 文件。
  - 若想使用原来的配置项，可以进入 `/boot` 目录，把 `config-....x86_64` 文件覆盖成上面说的 `.config` 文件，即执行如 `cp config-....x86_64 /usr/src/kernals/linux-5.1.14/.config` 。覆盖完成后需要再执行 `make menuconfig` 。
  - `lscpu` 查看硬件情况(看一下内核数)。
  - 编译： `make -j2 all` ，表示使用 2 个核心进行编译。至少要保留 10 GB 的可用空间。
  - 安装内核模块： `make modules_install` 。
  - 安装内核： `make install` 。
  - 重启 Linux ： `reboot` 。
  - 查看内核版本号： `uname -r` 。

</br>
</br>

# 进程管理

查看进程：

- `ps` 查看进程，常用参数是 `-eLf` 。
- `pstree` 以树状结构查看进程。
- `top` 动态显示系统及进程的情况，类似于 window 的"任务管理器"。

操作进程：

- 后台执行程序： `./program.sh &` ， `&` 就是表示在后台执行，前台的终端还能输入其他命令。
- 查看后台执行的程序： `jobs` 。
- 把后台的程序调回到前台： `fg 1` ，其中 `1` 是通过 `jobs` 获取到的序号。
- 挂起程序(即程序会被放到后台,且暂停执行)： 使用快捷键 `Ctrl + z` 。
- 若想把挂起程序继续执行，同样是 `jobs` 获取序号，然后使用 `bg 1` 把程序在后台执行；若想把程序在前台执行，则使用 `fg 1` 命令。

- 执行程序的时候，可以改变它的优先级，使用 `nice -n 10 ./program.sh` ，其中 `10` 就是优先级(越小优先级越大)。注意，"nice"的值范围是 -20 到 19 。
- 若想对已执行的程序修改其优先级，在使用 `renice -n 15 <pid>` ，其中 `<pid>` 是程序的 ID 。

- `kill -l` 可以查看相关的命令。
  - 如 `SIGINT` 是通知前台程序组终止进程，等效于快捷键 `Ctrl + c`
  - 如 `SIGKILL` 是立即结束程序，不能被阻塞和处理。示例： `kill -9 <pid>`

守护进程(daemon)：随系统开机的时候启动。

与守护进程类似的是 `nphup <program> &` 启动的程序，它会忽略 hangup(挂起) 的信号，即关闭终端也继续执行。

screen 环境：

- 使用 `screen` 命令进入 screen 环境，该环境下运行的程序，可以避免由于网络终端而导致的命令终端。
- 使用快捷键 `Ctrl + a` ，然后按 `d` 退出 screen 环境。(d 是 detached 的缩写)
- 若想再进去 screen 环境，可以使用 `screen -ls` 查看 screen 的会话，然后使用 `screen -r <sessionId>` 恢复会话。

服务管理工具 systemctl :

- 是 `service` 命令的“升级版”。
- 常用操作: `systemctl [start|stop|restart|reload|enable|disable|status] [服务名称]`
  - `enable` 和 `disable` 表示随开机是否一起启动。
  - `status` 查看服务状态。
- 软件包安装的服务单元 `/usr/lib/systemd/system/`
  - 在该目录下可以查看权限映射关系：
    - 通过 `runlevel*.target` 的几个文件去映射；
    - 例如 `runlevel0.target` 对应的是 `poweroff.target`，也就是关机
  - 通过 `systemctl get-default` 可以查看系统运行在哪个级别。
    - 会输出英文内容，如 `graphical-target`。
  - 通过 `systemctl set-default [*.target]` 可以设置运行级别（重启后生效）。
    - 如 `systemctl set-default nuti-user.target`。

</br>
</br>

# SELinux(Security-Enhanced Linux)

它是一个 Linux 内核模块，也是 Linux 的一个安全子系统。

主要作用就是最大限度地减小系统中服务进程可访问的资源（最小权限原则）。

例如，如果一个以 root 身份运行的网络服务存在 0day 漏洞，黑客就可以利用这个漏洞，以 root 的身份在您的服务器上为所欲为了。 SELinux 就是为了解决此问题。

简单了解：

- 运行 `getenforce` 查看 SELinux 状态。
  - 会有三个状态: `enforcing`、`permissive`、`disable`。
- 通过 `setenforce 0` 可以临时关闭 SELinux，重启后恢复。
- SELinux 的配置文件在 `/etc/selinux/config`。
- 通过 `ps -Z`、`id -Z`、`ls -Z` 分别查看进程、用户、文件的“标签”，这就是用于管理权限的。

</br>
</br>

# 内存与磁盘管理

## 内存查看

使用 `free` 会输出类似如下的结果：

``` txt
          total       used       free     shared    buff/cache    available
Mem:    1860720    1124539      87608      33864        648564       553772
Swap:   2097148     433655    1663492
```

- 参数说明：
  - `Mem` 行(第二行)是内存的使用情况。
  - `Swap` 行(第三行)是**交换分区**的使用情况。
  - `total` 列显示系统总的可用物理内存和交换分区大小。
  - `used` 列显示已经被使用的物理内存和交换分区。
  - `free` 列显示还有多少物理内存和交换分区可用使用。
  - `shared` 列显示被共享使用的物理内存大小。
  - `buff/cache` 列显示被 buffer 和 cache 使用的物理内存大小。
  - `available` 列显示还可以被应用程序使用的物理内存大小。
- free 与 available :
  - free 是真正尚未被使用的物理内存数量。
  - Linux 内核为了提升磁盘操作的性能，会消耗一部分内存去缓存磁盘数据，就是我们介绍的 buffer/cache；
  - 当应用程序需要内存时，如果没有足够的 free 内存可以用，内核就会从 buffer 和 cache 中回收内存来满足应用程序的请求。
  - 理论上 available = free + buffer + cache，实际中的数据往往有较大的误差。
- `Swap` 是“交换分区”，其实叫“虚拟内存”更合适，因为它表示使用了部分硬盘来作为内存使用。
  - 如果发现 `Swap` 的 `used` 比较多，那应该增加机器的内存空间，避免影响性能。
  - `Swap` 的空间可以调整；
  - 如果不使用 `Swap`，当内存超过了物理限制，Linux 会随机杀掉内存占用最大的进程。
- `free` 还可以携带参数 `-m` 或 `-g`，分别表示输出数值的单位是 Mb 或 Gb 。

实际使用 **`top`** 命令会更好，它能动态更新所有进程所占用的内存。

## 磁盘查看

运行 `fdisk -l` 命令会得到类似如下的输出：

``` txt
磁盘 /dev/sda：21.5 GB, 21474836480 字节，41943040 个扇区
Units = 扇区 of 1 * 512 = 512 bytes
扇区大小(逻辑/物理)：512 字节 / 512 字节
I/O 大小(最小/最佳)：512 字节 / 512 字节
磁盘标签类型：dos
磁盘标识符：0x000b3690

   设备  Boot      Start         End      Blocks   Id  System
/dev/sda1   *        2048     2099199     1048576   83  Linux
/dev/sda2         2099200    41943039    19921920   8e  Linux LVM

磁盘 /dev/mapper/centos-root：18.2 GB, 18249416704 字节，35643392 个扇区
Units = 扇区 of 1 * 512 = 512 bytes
扇区大小(逻辑/物理)：512 字节 / 512 字节
I/O 大小(最小/最佳)：512 字节 / 512 字节

磁盘 /dev/mapper/centos-swap：2147 MB, 2147483648 字节，4194304 个扇区
Units = 扇区 of 1 * 512 = 512 bytes
扇区大小(逻辑/物理)：512 字节 / 512 字节
I/O 大小(最小/最佳)：512 字节 / 512 字节
```

- 阅读上面的信息：
  - 虽然你使用的硬盘可能是固态硬盘，但 Linux 划分硬盘还是按照**扇区**。
  - `Units = 扇区 of 1 * 512 = 512 bytes` 说明了一个扇区的大小。
  - `Start` 和 `End` 分别表示起始和结束的扇区，相减后再乘以扇区大小，就得到分区的默认大小。
  - `Boot` 列标记了 `*` 号，表示系统在该分区中**启动**。

- 所有**磁盘当成文件对待**。

- 使用 `fdisk` 要小心，它既可以查看，也可以分区。

- 使用 `ls -l /dev/sda` 可以查看 `/dev/sda` 磁盘的信息。
  - 输出类似于 `brw-rw----. 1 root disk 8, 0 6月  11 10:09 /dev/sda` 的内容。
  - `b` 开头表示它是一个“块设备”。
  - 权限也不能随便修改。
  - 上面的 `8` 是**主设备号**，表示它的驱动程序。
  - 上面的 `0` 是**从设备号**，确定它的访问地址。
  - `sda` 其实就是一个分区，例如还有 `sda2` 那就是另一块分区。

其他查看磁盘信息的命令：

- `parted -l`，与 `fdisk -l` 类似：

    ``` txt
    Model: ATA VBOX HARDDISK (scsi)
    Disk /dev/sda: 21.5GB
    Sector size (logical/physical): 512B/512B
    Partition Table: msdos
    Disk Flags: 

    Number  Start   End     Size    Type     File system  标志
    1      1049kB  1075MB  1074MB  primary  xfs          启动
    2      1075MB  21.5GB  20.4GB  primary               lvm


    Model: Linux device-mapper (linear) (dm)
    Disk /dev/mapper/centos-swap: 2147MB
    Sector size (logical/physical): 512B/512B
    Partition Table: loop
    Disk Flags: 

    Number  Start  End     Size    File system     标志
    1      0.00B  2147MB  2147MB  linux-swap(v1)


    Model: Linux device-mapper (linear) (dm)
    Disk /dev/mapper/centos-root: 18.2GB
    Sector size (logical/physical): 512B/512B
    Partition Table: loop
    Disk Flags: 

    Number  Start  End     Size    File system  标志
    1      0.00B  18.2GB  18.2GB  xfs
    ```

- `df -h`，可以视为 `fdisk -l` 的补充：

    ``` txt
    文件系统                 容量  已用  可用 已用% 挂载点
    devtmpfs                 903M     0  903M    0% /dev
    tmpfs                    919M   16M  904M    2% /dev/shm
    tmpfs                    919M   11M  909M    2% /run
    tmpfs                    919M     0  919M    0% /sys/fs/cgroup
    /dev/mapper/centos-root   17G  5.8G   12G   34% /
    /dev/sda1               1014M  187M  828M   19% /boot
    tmpfs                    184M   44K  184M    1% /run/user/1000
    tmpfs                    184M     0  184M    0% /run/user/0
    ```

## 文件大小

先说结论，`ls -lh` 和 **`du -h`** 都能查看文件大小，但显示的文件大小未必相同。

`ls -lh` 显示的是文件**占用大小**；

`du -h` 显示的是文件**实际大小**。

出现着两者的差异的原因是，可能存在**空洞文件**。

### 如何创建空洞文件

使用 `dd if=/dev/zero bs=4M count=10 seek=20 of=myfile` 可以创建一个实际大小为 40M，占用大小为 120M 的文件。

- `dd` 命令用于指定大小的块拷贝一个文件，并在拷贝的同时进行指定的转换。
- `if=` 用于指定输入文件，就是 input file 的简写。
- `/dev/zero` 能够读出无穷多的 0，用来测试。
- `bs` 就是 block size，指定每次读取的块大小。
- `count` 指定块写入的次数。
- `seek` 指定从输出文件开头跳过若干个块后再开始复制。
- `of` 就是 output file。

## 常见的文件系统

Linux 支持多种文件系统，常见有：

- ext4 (centos6 及之前的版本使用)
- xfs (centos7 使用)
- NTFS（需安装额外软件支持） (Windows 使用)

ext4 文件系统基本结构:

- **超级块**，记录了整个文件系统的信息，如文件数量、文件大小等。
  - `df` 命令其实就是读取超级块的信息。
  - 当文件被修改的时候，超级块也会被自动更新。
- **超级块副本**，就是超级块的备份，且不止一份。
  - 其实文件恢复就是恢复超级块。
- **i 节点(inode)**，记录每一个文件的信息，如 ID、大小、文件名、权限等。
  - 注意，文件名与 ID 不是记录在同一个节点，文件名记录在当前文件所在的目录的节点上。
  - 使用 `ls -i` 命令可以查看 inode 信息。
  - 对于文件，“读”权限是对于 datablock 的读取权限；
  - 对于目录，“读”权限是对于 inode 里面的文件名的读取权限。
- **数据块(datablock)**，记录数据，跟在 inode 后面。
  - 数据块是有大小限制，如果一个数据块装不下，系统会创建一个新的数据块，挂载到前一个数据块后面。
  - 其实就是**链表**的形式。
  - `ls` 和 `du` 命令的差别也在此。前者统计包括空数据块的个数和大小；后者会忽略空数据块。
  - 在 ext4 或 xfs 系统中，默认的数据块的**最小尺寸是 4k**，即使文件只有一个字符。

## 操作

`touch file1`: 创建文件 `file1`

`vim file2`: 创建文件 `file2` 并打开编辑

`echo abc > file1`: 向 `file1` 文件末尾追加内容 `abc`

`mkdir dir1`: 创建目录 `dir1`

`cp filea fileb`: 将 `filea` 复制为 `fileb`

`mv filea fileb`: 将 `filea` 移动到 `fileb` (如果是同一个目录就是**重命名**；如果是不同路径，就是**剪切**)

`rm file2`: 将 `file2` 文件**删除**。

`ln fiel1 file2`: 创建名为 `file2` 的文件，**连接**到 `fiel1` 的 inode，不能跨磁盘，不能跨文件系统。称为“**硬连接**”。

`ln -s fiel1 file2`: 创建名为 `file2` 的文件，**软连接**到 `fiel1`，与上面不同，`file2` 的 inode 与 `file1` 不同。称为“**符号连接**”

说明：

- 如果在同一个文件系统(同一个分区)中操作， `mv` 命令不会改变 inode，即使移动到不同的目录。
- 使用 `vim` 编辑保存文件，会改变 inode；但使用 `echo` 增加内容只会把内容追加到 datablock。
- `vim` 的底层原理是，
  - 假设打开的文件时 `file1`；
  - 当它打开一个文件时，其实会创建一个 `.file1.swp` 的文件，其内容与 `file1` 相同；
  - 你编辑操作的也是对 `.file1.swp` 编写；
  - 好处时，意外宕机也能从 `.*.swp` 文件中恢复编辑的内容；使用 `file1` 也不会受你编辑的影响。
- `rm` 操作只是把 inode 与文件名的连接断开。
  - 文件恢复的原理大概是把所有的 inode 扫描出来，然后获取到后面的 datablock。
- `ln -s` 与 `ln` 不同，前者创建的文件的 datablock 存放目标文件的路径，inode 也和源文件不同。还需要注意：
  - 软连接文件的权限都是 777，所以对软连接文件做权限控制是无效的；
  - 它的类型的 `l`，通过 `ls -l` 可以查看到；

## 硬盘分区与挂载

步骤如下：

1. 插入物理硬盘（虚拟机就创建虚拟硬盘）。

2. 执行 `fdisk -l` 获取刚插入的硬盘信息，大概如下：（`/dev/sdb` 就是刚插入的硬盘）

    ``` txt
    磁盘 /dev/sda：21.5 GB, 21474836480 字节，41943040 个扇区
    Units = 扇区 of 1 * 512 = 512 bytes
    扇区大小(逻辑/物理)：512 字节 / 512 字节
    I/O 大小(最小/最佳)：512 字节 / 512 字节
    磁盘标签类型：dos
    磁盘标识符：0x000b3690

      设备 Boot      Start         End      Blocks   Id  System
    /dev/sda1   *        2048     2099199     1048576   83  Linux
    /dev/sda2         2099200    41943039    19921920   8e  Linux LVM

    磁盘 /dev/sdb：4294 MB, 4294967296 字节，8388608 个扇区
    Units = 扇区 of 1 * 512 = 512 bytes
    扇区大小(逻辑/物理)：512 字节 / 512 字节
    I/O 大小(最小/最佳)：512 字节 / 512 字节


    磁盘 /dev/mapper/centos-root：18.2 GB, 18249416704 字节，3564339
    2 个扇区
    Units = 扇区 of 1 * 512 = 512 bytes
    扇区大小(逻辑/物理)：512 字节 / 512 字节
    I/O 大小(最小/最佳)：512 字节 / 512 字节


    磁盘 /dev/mapper/centos-swap：2147 MB, 2147483648 字节，4194304
    个扇区
    Units = 扇区 of 1 * 512 = 512 bytes
    扇区大小(逻辑/物理)：512 字节 / 512 字节
    I/O 大小(最小/最佳)：512 字节 / 512 字节
    磁盘标签类型：dos
    磁盘标识符：0xeaf47753
    ```

3. 执行前必须确保硬盘没有数据，或数据已经备份，下面的操作会格式化硬盘。

4. `fdisk /dev/sdb` 进入**交互模式**。
    1. 输入 `m` 获取帮助信息。
    2. 输入 `m` **开始**创建分区。
    3. 输入 `p` 创建**主分区**；输入 `e` 创建**扩展分区**；输入 `l` 创建**逻辑分区**(只有创建了扩展分区才会出现的选项)。
        - **MSDOS 分区表**支持最多 4 个分区，即 `主分区 + 扩展分区 ≤ 4`；
        - 如果需要建立超过 4 个分区，则需要创建扩展分区，在扩展分区中建立逻辑分区；
        - 扩展分区只能创建 1 个，之后可以创建逻辑分区。
        - **GPT 分区表**则支持最多 128 个分区。
    4. 输入 1~4 的数字，确定**分区号**。
    5. 输入整数，确定**起始扇区**。
    6. 输入 `+扇区数` 或 `+size{K,M,G}`，确定**分区大小**。
        - 例如 `+1024` 表示分区大小为 1024 个扇区(512bytes/扇区)。
        - 例如 `+10G` 表示分区大小为 10G。
    7. 上面的步骤完成后，就已经完成了一个分区的创建。当前还处于**交互模式**下，重复上面的步骤可以继续创建分区。
    8. 输入 `p` 可以查看已创建的分区。信息大致如下：

        ``` txt
        磁盘 /dev/sdb：4294 MB, 4294967296 字节，8388608 个扇区
        Units = 扇区 of 1 * 512 = 512 bytes
        扇区大小(逻辑/物理)：512 字节 / 512 字节
        I/O 大小(最小/最佳)：512 字节 / 512 字节
        磁盘标签类型：dos
        磁盘标识符：0x46be6793

            设备 Boot      Start         End      Blocks   Id  System
        /dev/sdb1            2048     8388607     4193280   83  Linux
        ```

        - `/dev/sdb1` 就是刚才创建的分区。

    9. 到了这步，你可以选择保存配置；或者放弃配置；又或者删除某个分区的配置：
        - 输入 **`w`** 是**保存**分区配置，并退出交互模式；
        - 输入 **`q`** 则是**不保存**分区配置，并退出交互模式；
        - 输入 **`d`** 然后再输入数字(分区号) 可以**删除**该分区配置，但不会退出交互模式。

5. `mkfs.ext4 /dev/sdb1` 对分区**指定文件系统**。
    - **此命令会格式化**，请再三确定后执行，避免数据丢失。
    - `mkfs.` 后面跟的就是文件系统；
    - 输入 `mkfs.` 后连按两次 Tab 键可以查看可选命令。

6. `mkdir /mnt/sdb1` **创建目录**，该目录用于挂载文件系统。目录名称可以根据自己需要修改。

7. `mount /dev/sdb1 /mnt/sdb1` 将 `/dev/sdb1` 文件系统**挂载**到 `/mnt/sdb1` 目录。
    - 如果需要支持**用户磁盘配额**的功能，请把命令改为 `mount -o uquota,gquota /dev/sdb1 /mnt/sdb1`。
    - 注意，文件系统必须是 xfs，否则不支持用户磁盘配额。
    - 下一步的配置固化也需要把参数加上。

8. `mount` **查看挂载配置**。

    ``` txt
    sysfs on /sys type sysfs (rw,nosuid,nodev,noexec,relatime,seclabel)
    proc on /proc type proc (rw,nosuid,nodev,noexec,relatime)
    devtmpfs on /dev type devtmpfs (rw,nosuid,seclabel,size=923980k,nr_inodes=230995,mode=755)
    securityfs on /sys/kernel/security type securityfs (rw,nosuid,nodev,noexec,relatime)
    tmpfs on /dev/shm type tmpfs (rw,nosuid,nodev,seclabel)
    devpts on /dev/pts type devpts (rw,nosuid,noexec,relatime,seclabel,gid=5,mode=620,ptmxmode=000)
    tmpfs on /run type tmpfs (rw,nosuid,nodev,seclabel,mode=755)
    tmpfs on /sys/fs/cgroup type tmpfs (ro,nosuid,nodev,noexec,seclabel,mode=755)
    cgroup on /sys/fs/cgroup/systemd type cgroup (rw,nosuid,nodev,noexec,relatime,seclabel,xattr,release_agent=/usr/lib/systemd/systemd-cgroups-agentname=systemd)
    pstore on /sys/fs/pstore type pstore (rw,nosuid,nodev,noexec,relatime)
    cgroup on /sys/fs/cgroup/hugetlb type cgroup (rw,nosuid,nodev,noexec,relatime,seclabel,hugetlb)
    cgroup on /sys/fs/cgroup/blkio type cgroup (rw,nosuid,nodev,noexec,relatime,seclabel,blkio)
    cgroup on /sys/fs/cgroup/cpu,cpuacct type cgroup (rw,nosuid,nodev,noexec,relatime,seclabel,cpuacct,cpu)
    cgroup on /sys/fs/cgroup/pids type cgroup (rw,nosuid,nodev,noexec,relatime,seclabel,pids)
    cgroup on /sys/fs/cgroup/cpuset type cgroup (rw,nosuid,nodev,noexec,relatime,seclabel,cpuset)
    cgroup on /sys/fs/cgroup/devices type cgroup (rw,nosuid,nodev,noexec,relatime,seclabel,devices)
    cgroup on /sys/fs/cgroup/freezer type cgroup (rw,nosuid,nodev,noexec,relatime,seclabel,freezer)
    cgroup on /sys/fs/cgroup/perf_event type cgroup (rw,nosuid,nodev,noexec,relatime,seclabel,perf_event)
    cgroup on /sys/fs/cgroup/net_cls,net_prio type cgroup (rw,nosuid,nodev,noexec,relatime,seclabel,net_prio,net_cls)
    cgroup on /sys/fs/cgroup/memory type cgroup (rw,nosuid,nodev,noexec,relatime,seclabel,memory)
    configfs on /sys/kernel/config type configfs (rw,relatime)
    /dev/mapper/centos-root on / type xfs (rw,relatime,seclabel,attr2,inode64,noquota)
    selinuxfs on /sys/fs/selinux type selinuxfs (rw,relatime)
    systemd-1 on /proc/sys/fs/binfmt_misc type autofs (rw,relatime,fd=31,pgrp=1,timeout=0,minproto=5,maxproto=5,direct,pipe_ino=12878)
    mqueue on /dev/mqueue type mqueue (rw,relatime,seclabel)
    debugfs on /sys/kernel/debug type debugfs (rw,relatime)
    hugetlbfs on /dev/hugepages type hugetlbfs (rw,relatime,seclabel)
    fusectl on /sys/fs/fuse/connections type fusectl (rw,relatime)
    /dev/sda1 on /boot type xfs (rw,relatime,seclabel,attr2,inode64,noquota)
    sunrpc on /var/lib/nfs/rpc_pipefs type rpc_pipefs (rw,relatime)
    tmpfs on /run/user/1000 type tmpfs (rw,nosuid,nodev,relatime,seclabel,size=188212k,mode=700,uid=1000,gid=1000)
    gvfsd-fuse on /run/user/1000/gvfs type fuse.gvfsd-fuse (rw,nosuid,nodev,relatime,user_id=1000,group_id=1000)
    /dev/sdb1 on /mnt/sdb1 type ext4 (rw,relatime,seclabel,data=ordered)
    ```

    - 在最后一行可以看到刚才的挂载信息。
    - 如果使用用户磁盘配额功能，最后一行的信息会类似于：
      - `/dev/sdb1 on /mnt/sdb1 type xfs (rw,relatime,seclabel,attr2,inode64,logbufs=8,logbsize=32k,usrquota,grpquota)`

9. `vim /etc/fstab` 增加一行配置: `/dev/sdb1 /mnt/sdb1 ext4 defaults 0 0`。
    - 配置参数说明：
      - 第 1、2 个参数分别是分区和挂载目录；
      - 第 3 个参数是文件系统类型；
      - 第 3 个是操作权限，默认是可读写；
      - 第 4 个参数表示是否需要备份该分区，一般设为 0；
      - 第 5 个参数表示是否开机检查该分区，一般设为 0。
    - 保存配置后，下次启动时，系统会自动挂载分区。

10. 如果允许所有用户都能使用该分区，应该执行 `chmod 1777 /mnt/sdb1` **设置读写权限**。
    - `1777` 与 `777` 的区别在于，前者只有创建者和 root 可以删除。

11. 到上一步已经完成。下面主要针对配置用户磁盘配额。
    1. `xfs_quota -xc 'report -ugibh' /mnt/sdb1`: 表格形式**查看**用户磁盘配额的**使用情况**。
        - `-x` 表示"专家模式"，不会进入到"交互模式"，即 expert。
        - `-c` 表示执行的命令，`'report -ugibh'` 就是目标命令。
        - 参数 `u` 和 `g` 分别表示 user 和 group。
        - 参数 `i` 和 `b` 分别表示 inode 和 datablock。
        - 参数 `h` 表示 human readabled。
        - `/mnt/sdb1` 是被查询的目录。

        ``` txt
        User quota on /mnt/sdb1 (/dev/sdb1)
                                Blocks                            Inodes
        User ID      Used   Soft   Hard Warn/Grace     Used   Soft   Hard Warn/Grace
        ---------- --------------------------------- ---------------------------------
        root            0      0      0  00 [------]      3      0      0  00 [------]
        hozee           0     1G      0  00 [------]      5      2      5  00 [6 days]

        Group quota on /mnt/sdb2 (/dev/sdb1)
                                Blocks                            Inodes
        Group ID     Used   Soft   Hard Warn/Grace     Used   Soft   Hard Warn/Grace
        ---------- --------------------------------- ---------------------------------
        root            0      0      0  00 [------]      3      0      0  00 [------]
        hozee           0      0      0  00 [------]      5      0      0  00 [------]
        ```

        - 其中 `Grace` 表示了限期。

    2. `xfs_quota -xc 'limit -u isoft=5 ihard=10 bsoft=1G bhard=2G user1' /mnt/sdb1`: 对 `user1` 配置。
        - `-u` 表示对用户进行限制；也可以改为 `-g` 对用户组进行限制。
        - `isoft` `ihard` `bsoft` `bhard` 4 个都是可选参数。i 就是 inode；b 就是 datablock。
        - `soft` 表示**软限制**，超过限制只警告，但允许操作；`hard` 表示**硬限制**，超过限制的会被阻止。

假如硬盘容量大于 2T，请使用 **`parted`** 命令进行分区/格式化。其他的操作(挂载&固化挂载配置)相同：

``` shell
# 新建 /dev/sdb 的磁盘标签类型为 GPT
parted /dev/sdb mklabel gpt 
# 将 /dev/sdb 整个空间分给同一个分区
parted /dev/sdb mkpart primary 0 100%
# 忽略警告
ignore
# 对分区指定文件系统 (只能是 ext4)
mkfs -t ext4 /dev/sdb1
```

关于 MSDOS(MBR) 与 GPT 的区别：

....<!-- TODO: -->

使用经验：

- 对于 centos7 如果配置了硬盘后，又物理移除了该硬盘，启动不会进入图形界面。把 `/etc/fstab` 无效的配置删除，重启即可恢复。
- 仅 xfs 文件系统支持用户磁盘配额。
- 管理员不受“用户磁盘配额”的限制。

## 交换分区的扩充

**使用分区作为 Swap** :

1. 使用 `fdisk` 创建分区，具体参考上面的操作。假设创建了分区为 `/dev/sdb2`。
2. `mkswap /dev/sdb2` **创建**。
3. `swapon /dev/sdb2` **打开**。
4. `vim /etc/fstab` 增加一行配置 `/dev/sdb2 swap swap defaults 0 0`。
    - 第一个 `swap` 表示目标目录，用一个虚拟的 swap 代替。
    - 第二个 `swap` 表示文件系统的类型。
    - 剩下的参数，上面的[硬盘分区与挂载](#硬盘分区与挂载)的第 9 点有提到。

**使用文件作为 Swap** :

1. `dd if=/dev/zero bz=4M count=1024 of=/swapfile` **创建文件**。
    - `/swapfile` 是输出的文件名，可以根据自己需要改动。
2. `chmod 600 /swapfile` **修改文件权限**。
3. `mkswap /swapfile` **创建**。
4. `swapon /swapfile` **打开**。
5. `vim /etc/fstab` 增加一行配置 `/swapfile swap swap defaults 0 0`。

其他操作：

- `free -m` 查看。这时候发现 Swap 已经扩充。
- 如果想**关闭** Swap，使用 `swapoff /dev/sdb2` 命令。

## RAID(Redundant Arrays of Independent Disks, 独立磁盘冗余阵列)

常见级别及含义：

- **RAID 0**: 条带方式(striping)。提高单盘吞吐率。
  - 至少需要 2 块硬盘。
  - 把一份数据拆成两份，放到两块硬盘。
- RAID 1: 镜像方式(mirroring)。提高可靠性。
  - 至少需要 2 块硬盘。
  - 数据放到一块硬盘，然后拷贝一份到另一个硬盘。
  - 硬盘利用率会比较低，两块相同容量的硬盘可用率只有一块硬盘。
- RAID 5: RAID 0 + 奇偶校验。
  - 至少有 3 块硬盘。
  - 前两块硬盘写数据，第三块硬盘写前两块硬盘的奇偶校验。
  - 缺点是，损坏一块硬盘可以恢复数据，但损坏两块硬盘就无法恢复数据。
- RAID 10: RAID 1 + RAID 0 。
  - 至少需要 4 块硬盘。
  - 两块硬盘做 RAID 0 ；然后对做 RAID 0 的两块硬盘做 RAID 1 。

实际生成中，不会使用软件 RAID，因为会占用过多的 cpu 资源，通常使用 RAID 卡。

相关命令： `mdadm`。

## 逻辑卷管理

.... 53<!-- TODO: -->

</br>
</br>

# 系统综合状态查询

`sar`、`epel-release`、`iftop`。具体使用安装略。

</br>
</br>

# Shell

如何编写 shell 脚本：

- 约定 shell 脚本文件的**扩展名**是 **`.sh`**。
- 通常一行一个命令。
- 注释以 `#` 开头。
- `chmod u+x foo.sh` **增加**脚本文件**执行权限**。
- `bash foo.sh` **执行**脚本。

执行脚本的方式：

- `bash ./foo.sh` 会在当前目录生成一个 bash 进程，然后执行脚本内容。
- `./foo.sh` 同上。
  - 脚本内容的第一行必须加上**声明**内容 `#! /bin/bash`；
  - 如果以 `bash foo.sh` 的形式执行脚本，第一行不会被系统检测；
  - 如果以 `./foo.sh` 的形式执行脚本，第一行起的作用是，告诉系统用 `/bin/bash` 执行。
- `source ./foo.sh` 会在**当前进程**运行脚本。
  - 例如，脚本中包含了 `cd` 命令。
  - 使用 `source` 执行后，会导致当前环境的路径发生了变化；
  - 但使用 `bash` 执行后，不会影响当前环境的路径。
- `. foo.sh` 同上。`.` 就是 `source` 的简写。

## 内建命令(built-in)与外部命令(external)的区别

- 内建命令不需要创建子进程。
- 内建命令对当前 shell 生效。
- 外部命令则与之相反。

常见的 shell 内建命令：

- `source`: 在当前bash环境下读取并执行指定的命令
- `echo`: 输出指定的字符串或者变量
- `type`: 显示指定命令的类型
- `env`: 显示系统中已存在的环境变量
- `exec`: 调用并执行指定的命令
- `kill`: 删除执行中的程序或工作
- `alias`: 用来设置指令的别名
- `unalias`: 用来删除指令的别名
- `exit`: 退出当前的shell
- `jobs`: 显示linux的任务列表和状态
- `history`: 显示历史命令
- `logout`: 退出当前登录的shell
- `export`: 设置或者显示当前登录的shell
- `cd`: 切换工作目录

## 管道与重定向

管道：

- 符号是 `|`。
- 作用是使两个命令之间能够通信。其实就是把第一个命令的输出，作为第二个命令的输入。
- 管道也会创建一个子进程，用于连接两个命令。
- 管道符同样能连接两个以上的命令，同理是将前面的命令输出作为后面命令的输入。

重定向：

- 作用是，使命令的标准输出内容输出到一个文件中。
- 重定向分为输入和输出，输入输出以命令为"基准"，例如把文件输入到命令，那就是输入。
- **输入重定向**使用 `<` 符号。
  - 例如 `wc -l` 可以统计用户输入的行数(ctrl + d 退出输入)；
  - `wc -l < filea.txt` 可以统计 `filea.txt` 文件的行数。
- **输出重定向**有 6 种，`>`、`>>`、`2>`、`2>>`、`&>`、`&>>`。
  - `>` 表示把命令的**标准输出**输出到指定文件，文件内容会被输出内容**覆盖**；
  - `>>` 同上，但输出内容会**添加到**文件内容的**末尾**；
  - `2>` 表示把命令的**错误信息**输出到指定文件，文件内容会被输出内容**覆盖**；
  - `2>>` 同上，但输出内容会**添加到**文件内容的**末尾**；
  - `&>` 表示把命令的**所有输出**都输出到指定文件，文件内容会被输出内容**覆盖**；
  - `&>>` 同上，但输出内容会**添加到**文件内容的**末尾**。
- 输入重定向 + 输出重定向 的示例：(一般是在 shell 脚本生成另一个文件时用到)

  ``` shell
  cat > myfile <<EOF
  # 这里是 myfile 的内容...
  EOF
  ```

  - `<<EOF` 和 `EOF` 之间可以输入任意多行的内容。

## 变量

变量名的命名规则：

- 由字母、数字、下划线组成；
- 区分大小写；
- 不能以字母开头。

变量**声明**：(注意，**`=` 的两边都不能带空格**)

- `foo=123`: 将 123 赋值给变量 foo；
- `foo2="hello world"`: 将 hello world 赋值给变量 foo2；
  - 变量带有空格，需要用 双引号(`"`) 或 单引号(`'`)包裹。
- `let foo3=456+789`: 将 `456+789` 运算后的结果赋值给变量 foo3；
  - 可以运算，但尽量不要用，因为性能差。
- `myls=ls`: 将 ls 命令赋值给 myls；
  - 例如 `myls=ls` 声明后，`$myls` 等效于 `ls`。
- `foo4=$(ls -l /etc)`: 将 `ls -l /etc` 的执行结果赋值给变量 foo4；
  - `` `...` `` 与 `$(...)` 等效。

变量**作用域**：

- 默认只能在当前进程使用。
- 使用 `export` 命令可以把变量导出给子进程使用。
- 例如 `export my_var=1024`。
- 也可以先声明变量 `my_var=2048`，再导出变量 `export my_var`。

变量的**引用**：

- `${变量名}` 表示对变量的引用，一般可以省略 `{` `}`。
- `echo ${变量名}` 查看变量的值。

变量的**删除**：

- 使用 `unset 变量名` 即可删除变量。

系统环境变量：

- 通过 `set` 或 `env` 命令查看所有的环境变量。
- 常用的系统变量：
  - `$USER` 和 `$UID`: 查看当前用户的用户名和 ID。
  - `$PATH`: 可执行命令的路径。
    - 输出结果大概是 `/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/root/bin`，以 `:` 分割。
    - 需要修改可以执行 `PATH=$PATH:/foo/baz`
    - 例如 `ls` 命令就是在其中一个路径下。
  - `$PS1`: 当前提示的终端。
    - 通过修改它，可以使输出的显示更友好。例如包含执行时间、IP、完整路径信息等。
- 预定义变量：
  - `$?`: 上一条命令是否执行成功。如果是 0 则执行成功。
  - `$$`: 当前进程的 PID。
  - `$0`: 当前进程的名称。
- 位置参数变量：
  - 用于 shell 脚本内部，用于获取传入脚本的参数。
  - 例如，创建了 `myshell.sh` 脚本，执行 `./myshell.sh -a -l`，其中 `-a` 和 `-l` 就是位置参数。
  - `$1` ~ `${n}`: 用于获取各个位置的变量，例如 `./myshell.sh -a -l`，`$1` 的值就是 `-a`，`$2` 的值就是 `-l`。
  - 如果是超过 9 的，例如 11，需要这么写 `${11}`，大括号不能省略。
  - 位置参数可能为空，可以使用 `${2-_}` 的写法作为兼容。当位置 2 的参数为空时，它的值就是 `_`。
  - `$*` 或 `$@` 可以获取所有位置参数。
  - `$#` 获取位置参数的数量。
  - 脚本内使用 `shift` 关键字，可以把参数第一个移除。配合 `$#` 判断参数个数，逐个解析参数。

环境变量**配置文件**：

- 包括 `/etc/profile`、`~/.bash_profile`、`~/.bashrc`、`/etc/bashrc` 4 个文件；和 `/etc/profile.d/` 一个目录。
- `~` 表示用户的 home 目录， 保存用户特有的配置。
- `/etc/` 下的两个配置文件是所有用户都会加载。
- 加载的顺序是 `/etc/profile` -> `~/.bash_profile` -> `~/.bashrc` -> `/etc/bashrc`。顺序需要关注，因为同名变量会覆盖。
- 如果切换用户使用 `su username` 而非 `su - username`，只会加载 `~/.bashrc` 和 `/etc/bashrc`。
- 如果修改了配置，它不会立即生效，可以通过 `source 配置文件` 让配置文件重新加载。
- 配置文件的加载时间，是每次创建新的进程都会加载所有配置文件。
- `/etc/profile.d/` 针对不同的 shell 类型或版本，执行其中的某个脚本。

**数组**：

- `foo=(1 2 3 4)` 声明数组。元素之间用空格分割。
- `echo ${foo[@]}` 显示数组所有元素。
- `echo ${#foo[@]}` 显示数组长度。
- `echo ${foo[0]}` 显示数组中的第一个元素。其他同理。

**特殊字符**：

- `#`: 注释符号后面的内容。
- `;`: 表示一个命令的终结。
- `\`: 转义符号。需要显示特殊字符时则需要转义。
- `$`: 引用变量。
- `"`(双引号)、`'`(单引号)、`` ` ``(反引号)。
  - `"`(双引号) 内的内容会被解析。例如，`echo "$foo"` 等效于 `echo $foo`。我们称之为“不完全引用”。
  - `'`(单引号) 则不会被解析，包裹的内容直接被视为字符串。例如，`echo '$foo'` 会输出 `$foo` 的字符串。我们称之为“完全引用”。
  - `` ` ``(反引号) 是命令替换，即将其包裹的命令执行并保存输出内容。例如 `mylist=ls -l`，`mylist` 保存当前的文件列表，但如果新增了文件，`mylist` 的值还是原来的。
- `()`、`(())`、`$()` (圆括号):
  - 单圆括号会产生一个子 shell。例如 `(foo=123)`，在当前的进程一般是无法获取该变量的值。
  - 单圆括号还能定义数组。例如 `arr=(12 34 56)`。
  - 双圆括号用于计算。例如 `num=$((12+23))`。等效于 `let`。
  - `$()` 则用于得到内部命令执行结果的值，例如 `baz=$(ls -l)`。
- `[]`、`[[]]` (方括号):
  - 等效于 `test`。
  - 例如 `[2 -gt 3]` 等效与 `test 2 -gt 3`。(使用 `echo $?` 获取上一条命令的执行结果)
  - 双方括号就可以使用 `>`、`<`、`=` 比较运算符。即 `[[2 > 3]]` 等效于 `[2 -gt 3]`。
  - 更多关于 `test` 命令的信息，可以通过 `man test` 查看帮助。
- `<`、`>` (尖括号):
  - 输入/输出重定向。
- `{}` (花括号):
  - 指定范围。例如 `echo {0..9}` 会输出 0 到 9 的十个数字。
  - 利用这个特定，还能简写命令的参数。例如复制文件 `cp /foo/file{1,2}` 等效于 `cp /foo/file1 /foo/file2`。
- ....

**运算符**：

- `>`、`<`、`=`、`!=`: 比较运算符。
- `=`: 赋值运算符。
  - 使用 `unset` 删除变量。
- `+`、`-`、`*`、`/`、`**`、`%`: 基本运算符。
  - 其中 `**` 是次幂运算。`%` 是取余数。
  - 使用 `expr` 进行运算。例如 `expr 4 + 5`。注意，**不能进行浮点运算**，符号与数字之间需要空格。
- 数字可以用八进制或十六进制的形式表示。
  - 以 `0` 开头表示八进制。
  - 以 `0x` 开头表示十六进制。
  - 需要使用 `let` 关键字声明。例如 `let foo=0x22`，读取时会输出 34。
  - 双圆括号是 `let` 的语法糖。例如：
    - `((a=10))`
    - `((a++))`、`((a--))`
    - `echo $((11+10))`
    - `num=$((10+20))`
- `&&`、`||`、`!`: 逻辑运算符。
  - 下面的示例执行后通过 `echo $?` 获取执行结果，`0` 为执行成功，即 true，反之亦然。
  - `(( 5 > 4 && 4 > 5 ))` 结果为 1；
  - `(( 5 > 4 || 4 > 5 ))` 结果为 0；
  - `(( ! 5 > 4 ))` 结果为 1。

退出语句：(`exit`)

- 在 shell 中的 `exit` 命令表示退出，并返回上一条命令的 `$?` 的值。
- 也可以通过 `exit 10` 这样的形式，把 10 作为 shell 的自定义返回值。

**条件**语句：(`if`/`else`/`elif`/`fi`)

- `if [ 测试语句 ] then 执行响应的命令 fi`
  - 其中 `[ 测试语句 ]` 可以换成命令。`if` 判断的是其后面内容的返回值(`$?`) 是否为 0；
  - `then` 后面跟若干个执行命令，以 `fi` 结尾。
- `if ... then ... else ... fi`。就是 if-else 语句，不多赘述。
- 常见的写法如下：

  ``` shell
  # if...else...
  if [ $USER = root ] ; then
    echo " root user "
    echo $UID
  else
    echo " other user "
    echo $UID
  fi
  # if...else-if...else...
  if [ $foo > 10 ] ; then
    echo " greater than 10 "
  elif [ $foo = 10 ] ; then
    echo " equal to 10 "
  # 这里可以继续 elif
  else
    echo " less than 10 "
  fi
  # 嵌套条件
  if [ $baz != abc ] ; then
    echo " abc "
    if [ $baz = efg ] ; then
      echo " efg "
    else
      echo " other "
    fi
  else
    echo " abc "
  fi
  ```

**分支**语句：：(`case`/`in`/`"...")`/`*)`/`;;`/`esac`)

- 直接给示例代码：

  ``` shell
  case "$1" in
    "start"|"START")  # 匹配到值为 start 或 START
      echo $1" is start"
    ;;
    "stop")         # 可以有若干个判断值
      echo $1" is stop"
    ;;
    *)              # 相当于编程语言的 default
    ;;
  esac
  ```

**循环**语句：

- 循环语句内部都可以使用 **`break`** 和 **`continue`** 关键字。使用方法参考 `while` 的第二个示例。
  - 循环语句可以嵌套，`break` 和 `continue` 只会跳出当前循环。

- for 循环：

  ``` shell
  # 用其他命令得到数组，传给 for 循环执行
  for filename in `ls *.txt`
  do
    # basename 命令用于获取文件除了扩展名部分的名字
    # mv 命令用于重命名
    mv $filename $(basename $filename .txt)_new.txt
  done
  # 用自己定义的数组传给 for 循环执行
  for i in { 1, 2, 3, 4, 5 }
  do
    echo $i
  done
  # C 语言风格的 for 循环
  for (( i = 1 ; i <= 10 ; i++ ))
  do
    echo $i
  done
  ```

- while 循环：(`while`、`do`、`done`)

  ``` shell
  count=0
  while [ $count -lt 10 ]
  do
    echo $count
    ((count++))
  done
  # 嵌套判断语句、跳过一次循环、中止循环
  count2=0
  while [ $count2 -lt 10 ]
  do
    ((count2++))
    # 使用 break 跳出整个循环
    if [ $count2 -eq 8 ] ; then 
      break
    fi
    # 使用 continue 跳过一次循环
    if [ $(( $count2 % 2 )) = 1 ] ; then
      continue
    fi
    echo $count2
  done
  ```

- until 循环：(与 `while` 刚好相反)

  ``` shell
  count=0
  until [ $count -gt 10 ]
  do
    echo $count
    ((count++))
  done
  ```

**自定义函数**：(`function`、`local`、`return`)

- 直接给示例代码：

  ``` shell
  function cdls() {
    # 使用 local 声明变量，作用域只在当前函数内
    local lastPath=`pwd`
    # $1 获取第一个参数
    # -/ 表示，如果 $1 没有值，就用 / 作为默认值
    local path=${1-/}
    # 如果 cd 命令执行失败，退出函数
    if [[ $? != 0 ]] ; then return ; fi
    cd $path
    ls
    cd $lastPath
  }
  # 执行命令
  cdls /etc
  ```

- `function` 关键字不是必须的。也可以 `foo(){...}` 声明函数。
- `local` 声明的变量，表示其作用域仅在当前函数。
- 如果在 shell 声明的函数，需要使用 `source` 命令执行 shell，否则无法使用函数。
- 系统函数库在 `/etc/init.d/functions`

**脚本资源**控制：

- 使用 `nice` 和 `renice` 可以调整脚本的优先级。
- `ulimit -a` 可以查看当前终端的使用限制。会输出类似如下的内容：

  ``` txt
  [root@MiWiFi-RM1800-srv ~]# ulimit -a
  core file size          (blocks, -c) unlimited
  data seg size           (kbytes, -d) unlimited
  scheduling priority             (-e) 0
  file size               (blocks, -f) unlimited
  pending signals                 (-i) 7032
  max locked memory       (kbytes, -l) 64
  max memory size         (kbytes, -m) unlimited
  open files                      (-n) 1024
  pipe size            (512 bytes, -p) 8
  POSIX message queues     (bytes, -q) 819200
  real-time priority              (-r) 0
  stack size              (kbytes, -s) 8192
  cpu time               (seconds, -t) unlimited
  max user processes              (-u) 7032
  virtual memory          (kbytes, -v) unlimited
  file locks                      (-x) unlimited
  ```

  - 其中 `max user processes` 是用户最大可创建的进程数。
  - root 基本上是不会受限制的。

- “fork 炸弹”，会不断的创建进程。导致 cpu 被大量占用。
  - `function fn() { fn | fn &} ; fn` 可以创建并执行“fork 炸弹”。
  - 上面的函数其实就是一个递归函数。
  - `|` 就是管道符；`&` 可以让命令在后台执行。
  - 可以简写成 `.(){.|.&};.`。

**信号**捕获：

- 进程可以接收信号，然后默认会做一些事情。
- 例如 `kill -15 pid` 或 `kill pid` 其实就是向进程发送 **15 号**信号，程序收到会默认被杀死。
- 例如 `ctrl + c` 会向进程发送 **2 号**信号，程序收到会被关闭。
- 但 `kill -9 pid` 发送的 **9 号**信号不会被阻塞。
- 在 shell 脚本中写入如下内容，可以阻止信号的默认行为，并执行你的命令：

  ``` shell
  #! /bin/bash
  # 捕获 15 号信号，并执行 echo single-15 命令
  trap "echo single-15" 15
  # 捕获 2 号信号，并执行 ll 命令
  trap "ll" 2
  # 打印当前进程号
  echo $$
  # 死循环，避免程序结束
  while : ; do : ; done 
  ```

**计划任务**：

- `at 时间`: 可以创建**一次性**任务。
  - 运行示例：

    ``` txt
    [root@MiWiFi-RM1800-srv ~]# at 16:23
    warning: commands will be executed using /bin/sh
    at> echo hello > /tmp/hello.txt
    at> <EOT>
    job 3 at Tue Jun 15 16:23:00 2021
    ```

  - 除了 `at 16:23` 这种方式设置目标时间，还有其他方式，具体请查阅资料。
  - `<EOT>` 并不是手写的。通过 `ctrl + d` 保存并退出 at 编写模式。
  - 由于命令的标准输出并非在当前终端，所以输出到 `/tmp/hello.txt` 文件。
  - 通过查看 `/tmp/hello.txt` 的创建时间以及内容，可以确定命令是否执行成功。
  - 使用 **`atq` 查看**已设定的计划任务。
  - 使用 `at -d 计划号` 可以**删除**计划。

- `crontab -e`: 可以创建**周期性**任务。
  - 执行命令后，会进入 vim 编辑界面。编辑完成后，保存退出即可生效。
  - 下面给出脚本示例：

    ``` shell
    # 本示例用于输出日期到 /tmp/date_output.txt
    # 分  时  天  月  周  命令
      *   *   *   *   *   /usr/bin/date >> /tmp/date_output.txt
    # * 表示“任意”。上面全部都是 *，表示每一分钟都执行。
    # 周表示周几，例如 1 就是周一。
    # 除了 *，还能用具体数值表示，也能用范围数字。
    # 1,2 表示 1 和 2。
    # 1-10 表示 1 到 10。
    ```

  - 任务保存在 `/var/spool/cron/用户名` 的文件中。
    - 如果把该文件**删除**，周期性任务就会被删除。
  - 任务执行记录保存在 `/var/log/cron` 文件中。
  - 使用 `crontab -l` **查看**已创建的周期任务。

- 除了如何创建定时任务，任务的唯一性也是一个重要的事情。
  - 以 `flock -xn "/tmp/abc.lock" -c "/foo/baz.sh"` 执行 `/foo/baz.sh` 脚本，
  - 可以把为它加上“排他锁”，意思就是其他终端执行该脚本时会忽略，直到其他终端调用结束。

**文档查找**：

- **元字符**介绍：（与正则表达式的规则基本一致）
  - `.`: (非多行模式下)匹配除换行符意外的任意单个字符。
  - `*`: (跟在一个字符后)表示前一个字符的若干个(可以是零个)
  - `[]`: 匹配方括号中的字符的任意一个。
  - `^`: 匹配行开头。
  - `$`: 匹配行结尾。
  - `\`: 转义后面的特殊字符。
  - **扩展**元字符：（部分命令带上某参数后可以使用）
    - 不一一列出，上面列举的再加上扩展元字符，就是一个“完整”的正则。
- **`grep`**: **查找文本**当中，含有关键字的一行。
  - 例子 `grep pas* /root/anaconda-ks.cfg`
  - 使用命令时，建议把匹配值用 `""` 包裹，例如 `grep "pas*" /root/anaconda-ks.cfg`；
  - 原因是，某些字符可能会被命令“误解”。
- **`find`**: **查到文件**。
  - `find /etc -name passwd`: 在 `/etc` 目录下查找名字是 passwd 的文件。
  - `find /etc -regex pass.*`: 在 `/etc` 目录下以正则的方式查找 `pass.*` 的文件。
  - `find /etc -type f -regex pass.*`: 与上面类似。`-type f` 表示只查找文件类型的。
  - `find /etc -atime 8`: 查找“最后访问时间”在 8 小时以内的文件。类似的还有 `-ctime`(inode变化时间)、`-mtime`(内容变化时间)。
    - `stat filename`: 查看单个文件状态(即 `atime`/`ctime`/`mtime` )。
    - `LANG=c stat filename`: 以英文的形式执行 `stat` 命令。
    - 使用 `cat` 等查看命令，会导致文件的 atime 发生变化；
    - 使用 `touch` 等创建命令，会导致文件的 atime、ctime、mtime 都发生变化；
    - 使用 `chmod` 修改权限，会导致文件的 ctime 发生变化。
    - 使用 `echo >` 修改文件，会导致文件的 ctime、mtime 都发生变化；
  - `find *.txt -exec rm -v {} \;`:  找到 `*.txt` 的所有文件，并删除。

## sed

`sed` 与 `vim` 不同，是**行编辑器**，**非交互式**。

基本工作方式：

- 将文件以行为单位读取到内存（**模式空间**）。
- 使用 `sed` 的每个脚本对该行进行操作。
- 处理完成后输出该行。

**替换**(`s`)操作：

- `sed 's/aaa/bbb/' myfile`: 替换命令的基本用法。
  - **`s`** 表示**替换命令**。
  - 单引号(`''`) 包裹替换规则。用 `/` 分成三部分。
  - `aaa` 表示文件中原有的字符串；`bbb` 表示被替换的值。
  - `aaa` 和 `bbb` 都能使用**正则**表达式。
- `sed 's!/!bbb!' myfile`: 使用其他符号作为**分隔符**。
  - 上面的命令的意思是，把 `/` 替换成 `bbb`。
  - 也就是以 `!` 作为命令的分隔符。
  - 其实除了 `!`，我们可以使用任意字符作为分隔符。
- `sed -e 's/aaa/bbb/ -e 's/bbb/ccc/' myfile`: **链式替换**。
  - 命令的结果会将 `aaa` 替换成 `ccc`。
  - 命令可以简写成 `sed 's/aaa/bbb/;s/bbb/ccc/' myfile`。
- `sed 's/aaa/bbb/;s/bbb/ccc/' myfile myfile2 myfile3`: 对**多个文件**执行相同的替换。
- `sed -i 's/aaa/bbb/;s/bbb/ccc/' myfile myfile2 myfile3`: 替换操作**写入**到文件中。
  - 如果不带 `-i` 参数，替换的内容只是在终端输出。
- `sed -r`: 加上 **`-r`** 参数可以使用**扩展元字符**。下面给出一些示例:
  - `sed -r 's/(aaa)(bbb)/\2:\1'`: 将 `aaabbb` 替换成 `bbb:aaa`。
  - `sed -r 's/(aaa)|(bbb)/ccc'`: 将 `aaa` 或 `bbb` 替换成 `ccc`。
  - `?` 和 `+` 分别表示前面的字符出现“一次或零次”和“至少一次”。
  - ....
- **标志位**：
  - `sed 's/aaa/bbb/g' myfile`: **全局替换**匹配到的值。
    - 关键是带上 `g`。
    - 默认情况下，只会替换第一次匹配到的值。
    - 如果把 `g` 换成数字，则会替换第 n 个。
  - `sed -n 's/aaa/bbb/p' myfile`: 取消默认输出，只输出被替换的行。
    - `-n` 表示阻止默认输出。
    - `p` 表示只输出被替换的行。
  - `sed -n 's/aaa/bbb/w /tmp/foo.txt' myfile`: 与上面类似，但会输出到文件。
    - `w` 后面就是跟着输出的目标文件，即 `/tmp/foo.txt`。
- **寻址**：
  - `sed '1 s/aaa/bbb/g' myfile`: **指定行号**进行规则替换。`1` 就是行号。
    - `2,$` 表示第二行到最后一行。
  - `sed '/ccc/ s/aaa/bbb/g' myfile`: 对符合指定查询**正则规则**的行进行替换。`/ccc/` 就是指定查询的正则规则。
  - 行号与正则可以混用。
    - `sed '/ccc/,2 s/aaa/bbb/g' myfile` 表示从符合 `/ccc/` 的开始算，共 2 行，都进行替换。
    - `sed '2,/ccc/ s/aaa/bbb/g' myfile` 表示从第 2 行开始，直到符合 `/ccc/` 规则的那行。如果没有符合 `/ccc/`，则等效 `$`。
  - 无论是正则还是数字，后面都能跟 `!` 表示“**除了**”的意思。
    - 例如 `1! ...` 表示除了第一行，其他都执行 `...` 操作。
    - 例如 `3,$! ...` 表示除了第三行到最后一行，其他都执行 `...` 操作。
    - 例如 `/aaa/! ...` 表示除了匹配 `/aaa/` 的行，其他都执行 `...` 操作。
- **分组**：
  - 其实就是配合多条替换规则使用。
  - 例如 `sed '2,/ccc/ {s/aaa/bbb/g';s/bbb/ddd/g'} myfile`。
  - 终点是用大括号(`{}`)包裹多条匹配规则。
- **脚本文件**：
  - 对于比较复杂，且能服用的 sed 命令，可以编写到一个文件中。
  - 使用 `sed -f mysedscriptfile myfile` 执行 `mysedscriptfile` 文件。
  - `mysedscriptfile` 直接书写如 `2,/ccc/ s/aaa/bbb/g` 的内容即可。

**删除**(`d`)操作：

- 原理是，删除**模式空间**的内容，改变脚本的控制流，读取新的输入行。
- 例如 `sed '/aaa/d' myfile`，会删除所有匹配 `/aaa/` 的行。
- “改变脚本的控制流”的意思是：
  - 例如 `sed '/aaa/d;s/bbb/ccc/' myfile` 的 `s/bbb/ccc/` 不会被执行。
  - 假如该行没有匹配 `/aaa/`，那也不会执行后面的指令。

**插入**(`i`)、**追加**(`a`)、**更改**(`c`)操作：

- 插入: `sed '/aaa/i hello' myfile`，表示在匹配到 `/aaa/` 的**前一行插入** `hello`。
- 追加: `sed '/bbb/a world' myfile`，表示在匹配到 `/bbb/` 的**后一行插入** `world`。
- 更改: `sed '/ccc/i hello world' myfile`，表示在匹配到 `/ccc/` 的**整行更改**为 `hello world`。

**读取**(`r`)、**写入**(`w`)操作：

- 读取: `sed '/aaa/r foo.txt' myfile`，表示匹配到 `/aaa/` 的**该行下面**，插入 `foo.txt` 文件内容。
- 写入: `sed '/bbb/w baz.txt' myfile`，表示匹配到 `/bbb/` 的**所有行**内容，都**覆盖**写入到 `baz.txt` 文件。

**下一行**(`n`)操作：

- `sed '/age/n;s/aaa/bbb/' myfile`，表示匹配到 `/age/` 的一行会被**忽略**，然后继续对下一行操作。

**打印行号**(`=`)操作：

- `sed -n '/aaa/=' myfile`，表示只输出匹配到 `/aaa/` 的行号。

**打印**(`p`)操作：

- `sed -n '/aaa/p' myfile`，表示只输出匹配到 `/aaa/` 的行。
- 注意，默认不带 `-n` 的情况下，`sed` 命令会默认输出完整被处理的结果，"`p`" 的输出结果紧跟后面。
- 所以 "`p`" 一般和 `-n` 一起使用。

**退出**(`q`)操作：

- `sed '/aaa/q' myfile`，表示如果匹配到 `/aaa/` **之后**退出命令。
- 也可以根据行号 `sed '10q' myfile`，即读取到第 10 行之后就退出。
- 它的作用在于：
  - `sed` 的原理的逐行读取，
  - 而如果文件行数较多，达到某个条件之后的内容是不需要的，
  - 为了节省处理时间，那就可以使用此操作。

**多行模式**(`N`、`P`、`D`)操作：

- 对于 XML 或 JSON 这种格式的文件，可能需要多行模式。
- `N`: 将下一行加入到模式空间。
- `D`: 删除模式空间中的第一行。
- `P`: 打印模式空间中的第一行。
- 示例：`sed 'N;s/\n//;s/hello world/hello sed/g;P;D' myfile` 表示以**两行**为单位读取。
<!-- TODO: 其实上面的示例有问题 -->

使用**保持空间**(`h`、`H`、`g`、`G`、`x`)操作：

- `h`: 将模式空间的内容**覆盖存放**到保持空间。
- `H`: 将模式空间的内容**追加存放**到保持空间。
- `g`: 将保持空间的内容**覆盖取出**到模式空间。
- `G`: 将保持空间的内容**追加取出**到模式空间。
- `x`:  **交换**模式空间和保持空间的内容。
- 保持空间默认值是一个换行符。
- 示例：`cat -n /etc/passwd | head -6 | sed -n '1h;2,$x;H;$x;$p'`。
  - 示例代码的作用是把所有行倒序。
  - `cat -n /etc/passwd | head -6` 为了随便获取多行内容，可以忽略。
  - `1h` 把第一行覆盖到保持空间；
  - `1!x` 把除了第一行，每次都交换保持空间和模式空间的内容；
  - `H` 然后把模式空间的内容追加到交换空间；
  - `$x` 最后一行就把所有内容取出；
  - `-n` 和 `$p` 配合，只输出最后一次的内容。

## AWK

AWK 更像是脚本语言，用于“比较规范”的文本处理，用于统计数量并输出指定字段。

如果熟悉编程语言，使用 AWK 会更舒服。

一般用于对文本内容进行统计、按需要的格式进行输出。

简单示例 `awk -F ',' '{print $1, $2, $3}' filename`：

- `-F ','` 是可选参数，表示每行参数的分隔符是 `,`，默认是空白字符(`\t`/`\s`等)。
- `'{print $1, $2, $3}'` 是处理的执行代码，一般把内容写入到某个文件，通过 `-f *.awk` 的方式使用。

假设我们有一个 `kpi.txt` 文件需要处理，内容如下：

``` txt
姓名    语文    英语    数学    理综    文综
std1    121     99      112     78      89
std2    89      145     129     33      55
std3    99      99      99      99      99
std4    150     149     249     98      98
```

我们可以创建 `kpi.awk` 文件，用于得到 `kpi.txt` 的平均成绩等统计数据：

``` shell
# 声明函数，可以在下面任意位置使用
function printStart(){
  print "start --->"
}

# 输入数据前例程，一般用于获取参数
BEGIN{
  printStart()
  allSum=0
  stdNum=0
  courseNum=0
  # ARGC、ARGV 分别表示参数的个数和参数数组
  # 例如 # awk -f kpi.awk kpi.txt
  # ARGC 是 2；ARGV 是 [awk, kpi.txt]
  print "命令共有", ARGC, "个参数，分别是: "
  for(x=0; x<ARGC; x++){
    print ARGV[x]
  }
  print ""
}
# 主输入循环，这里会执行多次，每行一次
{
  # FNR 表示文件行号
  # NF 表示该行的参数数量
  if(FNR > 1 && NF >= 1) {
    sum=0
    # for 循环
    for(i=1; i<NF; i++){
      sum+=$"i+1"
      _i=i+1
      sum+=$_i
    }
    # $1 是每行的第一个参数，如此类推
    print $1, "的总分:", sum
    print $1, "的平均分", sum/(NF-1)
    allSum+=sum
    stdNum++
  }
  # 条件语句
  if(courseNum==0) courseNum=NF-1
}
# 完成例程，一般用于最后的输出。如果读取多个文件，则是读取完所有文件才会被执行
END{
  print "所有同学平均分: ", allSum/stdNum/courseNum
}
```

执行 `awk -f kpi.awk kpi.txt` 会得到如下内容：

``` txt
start --->
命令共有 2 个参数，分别是:
awk
kpi.txt

std1 的总分: 499
std1 的平均分 99.8
std2 的总分: 451
std2 的平均分 90.2
std3 的总分: 495
std3 的平均分 99
std4 的总分: 744
std4 的平均分 148.8
所有同学平均分:  109.45
```

可以看出，`*.awk` 的内容与编程语言比较像。下面简单介绍：

- AWK 的操作也是逐行读取，每行都会被**分隔符**分割成多个**字段**。
  - 分隔符默认是**空白字符**(空格/制表符)。
  - 通过 `$1`、`$2` ... `$n` 获取每个字段的值。
  - 命令参数可以通过，如 `-F ','` 指定 `,` 为分隔符。
  - 如果在 `*.awk` 中，通过修改**全局变量**实现：
    - `FS` 和 `OFS` 字符分隔符；
    - **`FS`** 是输入的字段分隔符；
    - **`OFS`** 表示输出的字段分隔符。

- 数据过滤：
  - 在 `{}`(主输入循环) 前可以加上正则，表示特定的行才会进入主输入循环被处理。
  - 例如 `awk '/^if/{print $0}' /boot/grub2/grub.cfg` 只打印以 `if` 开头的行。

- 赋值操作符：
  - 就是 `=`(等号)。
  - 例如 `var=1`。

- 算术运算符：
  - 熟悉编程语言的其实都知道。
  - `+`、`-`、`*`、`/`、`%`、`^` ...
  - `++`、`--`；
  - `+=`、`-=`、`*=`、`/=`、`%=`、`^=`(异或运算赋值) ...

- 关系操作符： `>`、`<`、`>=`、`<=`、`==`、`!=`、`~`、`!~`

- 布尔操作符： `&&`、`||`、`!` ...

- **系统变量**：
  - FS 和 0FS 字符分隔符，FS 是输入....；0FS 表示输出的字段分隔符
  - RS 记录分隔符，默认是 \n
  - NR 和 FNR 行数，当只有一个文件作为输入，两者一致；当有多个文件作为输入时，FNR 会重新计数
  - NF 字段数量，最后一个字段内容可以用 $NF 取出

- 条件语句： `if(...){ ... }`

- 循环语句：
  - `while(...){ ... }`
  - `do{ ... }while(...){ ... }`
  - `for(;;;){ ... }`
  - `for(* in *){ ... }`
  - 循环内能使用 `break` 和 `continue` 关键字。

- 数组：
  - 赋值: `数组名[下标]=变量`
  - 删除: `delete 数组名[下标]`
  - 遍历: `for(变量 in 数组名){ ... }`

- awk 参数获取：
  - 在 `BEGIN{}` 中读取。
  - `ARGC` 是**参数数量**。
  - `ARGV` 是**参数数组**。
  - 例如 `awk -f foo.awk afile.txt bfile.txt`
    - `ARGC` 是 `3`；
    - `ARGV` 是 `[awk, afile.txt, bfile.txt]`；

- 系统函数：
  - 三角函数: `sin()`、`cos()` ...
  - 随机数: `rand()`、`srand()`
    - `rand()` 会得到相同的值，直到掉用 `srand()` 后，下一个 `rand()` 才会变化。
  - 字符串处理:
    - `gsub(r,s,t)`
    - `index(s,t)`
    - `length(s)`
    - `match(s,r)`
    - `split(s,a,sep)`
    - `sub(r,s,t)`
    - `substr(s.p,n)`
    - ...
  - 这里只列出一部分，通过 `man awk` 获取更多系统函数。

- 自定义函数：
  - 一般在最外城定义函数，`BEGIN{}`、`{}`、`END{}` 都能使用。
  - `function 函数名(参数){ ... }`
  - 函数内部使用 `return` 返回值。

</br>
</br>

# 防火墙

## iptables

下面只介绍 filter 和 nat 表，这两个是最常用的。其他表可以通过 `man iptables` 查看。

### filter 表

新添加规则之后，执行 `iptables -t filter -L` 可能会很慢，因为系统会把 ip 解析成域名，可以使用 `-nL` 参数取代 `-L`；`-vL` 会更详细。
**查看**命令 `iptables -t filter -vnL`。

- 参数说明：
  - `-n` 表示**不会** ip 解析成域名。
  - `-v` 就是 verbose，表示详细模式。
  - `-L` 就是查询列表。

- 命令输入类似如下：

  ``` txt
  Chain INPUT (policy ACCEPT 241K packets, 382M bytes)
  pkts bytes target     prot opt in     out     source               destination
  241K  382M LIBVIRT_INP  all  --  *      *       0.0.0.0/0            0.0.0.0/0

  Chain FORWARD (policy ACCEPT 0 packets, 0 bytes)
  pkts bytes target     prot opt in     out     source               destination
      0     0 LIBVIRT_FWX  all  --  *      *       0.0.0.0/0            0.0.0.0/0
      0     0 LIBVIRT_FWI  all  --  *      *       0.0.0.0/0            0.0.0.0/0
      0     0 LIBVIRT_FWO  all  --  *      *       0.0.0.0/0            0.0.0.0/0

  Chain OUTPUT (policy ACCEPT 97471 packets, 20M bytes)
  pkts bytes target     prot opt in     out     source               destination
  97457   20M LIBVIRT_OUT  all  --  *      *       0.0.0.0/0            0.0.0.0/0
  ```

- `policy` 一行表示默认规则。即该条规则下面的具体规则都没有匹配到，就会走默认规则。
- `iptables -P INPUT DROP` 就可以把 `INPUT` 的默认规则改为 `DROP`。
- `in` 和 `out` 都是指定网卡。
- `prot` 表示允许的协议，默认是全部。其实还包括端口号的设置。
  - `-p` 参数可以指定允许的协议，如 `-p tcp`。
  - `--dport 80` 可以指定访问的端口为 `80`。

**添加**规则使用 `iptables -t filter -A INPUT -s 10.0.0.1 -j ACCEPT`。

- `-A` 表示添加，在最后一行新增。类似的还有 `-I`，表示插入，插入到第一行。
  - `-D` 是**删除**操作，使用方式与 `-A` 相同。
- `INPUT` 表示对**输入**规则进行操作。类似的还有 `OUTPUT`(**输出**) 和 `FORWARD`(**转发**)。
- `-s 10.0.0.1` 的 `s` 就是 source，指定源 IP 地址。
- `-j ACCEPT` 表示动作是**允许**。类似的还有 `DROP`，表示**禁止**。
- **规则的顺序很重要**，**前者会覆盖后者**。例如，第一条规则被匹配了，即使后面的也匹配到，也不会被执行。

**清除**自定义规则 `iptables -t filter -F`。

- 但需要注意，它**不能清除默认规则**。
- `-F` 后面可以携带"chain"，例如 `INPUT`。表示对单个 chain 清空。

更多用法可以通过 `man iptalbes` 查阅。

### nat 表

**网络地址转换**(Network Address Translation)，就是指在一个组织网络内部，根据需要将 IP 地址转换。包括两部分：

- `PREROUTING`(**目的地址转换**): 即**服务器代理**。通俗说就是，局域网以外的机器访问此电脑的某个端口时，把内容转发到特定的内网 IP。
  - 示例：`iptables -t nat -A PREROUTING -i eth0 -d 114.115.116.117 -p tcp --dport 8080 -j DNAT --to-destination 192.168.31.2`。
  - `-t nat` 就是修改 NAT 表；
  - `-A PREROUTING` 就是“目的地址转换”；
  - `-i eth0` 指定输入的网卡；
  - `-d 114.115.116.117` 指定目标地址；
  - `-p tcp` 指定协议；
  - `--dport 8080` 指定端口号；
  - `-j DNAT` 就是动作；
  - `--to-destination 192.168.31.2` 指定转发到的目标地址。
  - 即外网机器通过 tcp 协议访问本机的 `114.115.116.117:8080` 时，会被转发到内网的 `192.168.31.2` 机器上。

- `POSTROUTING`(**源地址转换**): 即**本地代理**。通俗说就是，局域网内的机器通过当前配置的机器去访问外部网络的机器。
  - 示例：`iptables -t nat -A POSTROUTING -s 192.168.31.2/24 -o eth0 -j SNAT --to-source 114.115.116.117`。
  - `-t nat`、`-A POSTROUTING`、`-j SNAT`、`-o eth0` 不再赘述；
  - `-s 192.168.31.8/24` 指定内网机器的源地址；
  - `--to-source 114.115.116.117` 指定伪装成的目标地址。注意，`eth0` 网卡必须有 `114.115.116.117` 这个 IP。

### 保存配置

如果不保存到配置文件，重启后就会失效。

配置文件在 `/etc/sysconfig/iptables`。

通过命令 `service iptables save|start|stop|restart` 可以保存配置。

注意，如果不包含 `service` 命令，可以通过 `yum install iptables-services` 安装。

也可以使用 `iptables-save > /etc/sysconfig/iptalbes` 将配置内容保存到配置文件。

通过 `iptables-restore < /etc/sysconfig/iptables` 将文件**还原**到配置。

## firewallD 服务

它支持**区域(zone)**概念。

firewallD 可能未被启用，需要使用 `systemctl start firewalld.service` 开启服务。

- 类似的命令还有 `stop`、`enable`、`disable`。

使用 `systemctl status firewalld.service` 可以查看 firewalld 的状态。

- 输入内容大致如下：

  ``` txt
  ● firewalld.service - firewalld - dynamic firewall daemon
    Loaded: loaded (/usr/lib/systemd/system/firewalld.service; enabled; vendor preset: enabled)
    Active: active (running) since Sun 2021-06-13 05:52:52 EDT; 3 days ago
      Docs: man:firewalld(1)
  Main PID: 953 (firewalld)
      Tasks: 2 (limit: 11251)
    Memory: 2.4M
    CGroup: /system.slice/firewalld.service
            └─953 /usr/libexec/platform-python -s /usr/sbin/firewalld --nofork --nopid

  Warning: Journal has been rotated since unit was started. Log output is incomplete or unavailable.
  ```

- `Active` 行可以看到运行状态，如果是未被启动，会显示 `incative (dead)`。当前是处于运行中的状态。
  - 运行状态也可以使用 `firewall-cmd --state`。如果是运行中就会返回 `running`。
- 与 iptables 同时使用，可能会产生冲突，使用 `service iptables stop` 可以将 iptables 服务停用。

`firewall-cmd`:

- `firewall-cmd --list-all` 查看所有区域。输入类似如下信息：

  ``` txt
  public (active)
  target: default
  icmp-block-inversion: no
  interfaces: enp0s3
  sources: 1.2.3.4
  services: cockpit dhcpv6-client ssh
  ports:
  protocols:
  masquerade: no
  forward-ports:
  source-ports:
  icmp-blocks:
  rich rules:
  ```

  - `public` 就是区域，也是默认区域。
  - `firewall-cmd --list-services` 可以单独查看 `services` 项，其他同理。

- `firewall-cmd --get-zones` 查看所有区域。
- `firewall-cmd --get-default-zone` 查看默认区域。
- `firewall-cmd --get-active-zone` 查看活动区域。

- 使用 `firewall-cmd --add-service=https` **添加** `https` 到 `service` 规则。
  - 添加其他的规则同理，例如 `firewall-cmd --add-port=8080/tcp`。
  - 如果规则需要**永久保存**，增加参数 `--permanent`。

- 使用 `firewall-cmd --remove-source=1.2.3.4` 删除 `source` 中 `1.2.3.4` 的规则。
  - 删除其他规则同理。

</br>
</br>

# FTP 服务

ftp 支持所有操作系统。

FTP 协议有主动模式和被动模式。主动/被动是对于服务器来说，“主动”就是服务器主动向服务端发起请求；反之则是服务端等待客户端发起请求。

**安装**: `yum install vsftpd ftp`。`vsftpd` 是服务端；`ftp` 客户端。

**启动**服务: `systemctl start vsftpd.service`。

**连接** ftp 服务: `ftp localhost` 连接到本地的 ftp 服务，之后会提示输入账号密码。连接外部 ftp 服务同理。

- 如果 `ftp 1.2.3.4` 提示 `ftp: connect: 没有到主机的路由`，其实是被防火墙拦截了。
  - `firewall-cmd --add-service ftp --permanent` 添加服务白名单。
- 命令成功后，会进入到 ftp 交互界面：

  ``` shell
  # 查看本地文件列表
  ftp> !ls
  # 上传文件
  ftp> put foo.txt
  # 下载文件
  ftp> get baz.txt
  ```

配置不允许登录 ftp 的用户: `/etc/vsftp/ftpusers`。（一般是权限较高的用户，避免类似于 password 相关的高危文件被下载）

用户的**黑/白名单**配置文件: `/etc/vsftp/user_list`。是黑是白，取决于主配置的 `userlist_deny`。

**主配置**文件: **`/etc/vsftp/vsftpd.conf`**。常用配置介绍：

- 值有两个 `YES` 和 `NO`，都需要大写。
- `anonymous_enable=YES`: 是否支持匿名账号的功能。即不需要账号密码。
- `local_enable=YES`: 是否支持本地用户。如果 SELinux 是启用状态，还需要检查 SE bool 的 ftp_home_dir 的布尔值。
  - `setsebool -P ftp_home_dir 1` 设置 `ftp_home_dir` 的值为 `true`。下面有介绍 SE bool 的查询与设置。
- `write_enables=YES`: 本地用户是否有写权限。
- `connect_from_port_20=YES`: 是否开启 ftp 的主动模式。（用主动还是被动模式，由客户端决定）
- `userlist_enable=YES`: 是否启用用户的黑/白名单。
- `userlist_deny=YES`: 是否把 userlist 作为黑名单。（仅当 userlist_enable=YES 时生效）
- 通过 `man 5 vsftpd.conf` 获取配置的帮助。

查看/设置 SE bool ：

- 查询 SE bool 的值，并过滤包含 ftpd 关键字的内容: `getsebool -a | grep ftpd`。

  ``` txt
  ftpd_anon_write --> off
  ftpd_connect_all_unreserved --> off
  ftpd_connect_db --> off
  ftpd_full_access --> off
  ftpd_use_cifs --> off
  ftpd_use_fusefs --> off
  ftpd_use_nfs --> off
  ftpd_use_passive_mode --> off
  ```

- `ftpd_use_cifs` 开启后，既可以给 widows 文件共享，也可以给 ftp 用。
- 示例: `setsebool -P ftpd_use_nfs 1`。
  - 命令是把 `ftpd_use_nfs` 的值设为 `on`，`-P` 表示 permanent。
  - 其他布尔值的设置同理。

创建**虚拟用户**：

1. 创建真实用户: `useradd vuser -d /data/ftp -s /sbin/nologin`。
    - `-d /data/ftp` 设定该目录为 ftp 的根目录。
    - `-s /sbin/nologin` 指定 shell 的根目录。
    - 虚拟用户必须映射到一个真实用户。
    - 多个虚拟用户可以映射到同一个真实用户。

2. 创建虚拟用户:
    - 在 `/etc/vsftpd/` 目录创建 `vuser.temp` 文件，写法类似如下：

      ``` txt
      vuser1
      12345
      vuser2
      13579
      vuser3
      02468
      ```

    - `db_load -T -t hash -f /etc/vsftpd/vuser.temp /etc/vsftpd/vuser.db` 转化成 db 文件。
    - `chmod 600 /etc/vsftpd/vuser.db` 修改 db 文件的权限为 600，即仅 root 用户才有权限修改此文件。

3. 指定账号密码的文件：
    - 默认使用 `/etc/pam.d/vsftpd`，默认读取系统的 password 文件，使用其中的账号密码作为 ftp 服务的账号密码。
    - 编写 `/etc/pam.d/vsftpd.vuser` 文件，写入如下内容：

      ``` txt
      auth sufficient /lib64/secuity/pam_userdb.so db=/etc/vsftpd/vuser
      account sufficient /lib64/secuity/pam_userdb.so db=/etc/vsftpd/vuser
      ```

4. 创建 `/etc/vsftpd/vuserconfig` 目录，用于设置虚拟用户的权限：
    - 该目录不一定存在，没有则自己创建。
    - 在该目录下，创建与虚拟用户同名的文件。例如，需要对 vuser1 设置权限，则创建 `vuser1` 文件。
    - 在文件内编写类似如下内容（常用配置）：

      ``` shell
      local_root=/data/ftp        # 用户可以访问的根目录
      write_enable=YES            # 读权限
      anon_umask=022
      anon_world_readable_only=NO # 只读权限
      anon_upload_enable=YES      # 上传权限
      anon_mkdir_write_enable=YES # 允许创建目录
      anon_other_write_enable=YES
      download_enable=YES         # 下载权限
      ```

5. 修改 ftp 的主配置文件（`/etc/vsftpd/vsftpd.conf`）：
    - 修改如下配置：

      ``` shell
      # 开启如下配置
      guest_enable=YES                        # 开启后支持虚拟用户
      guest_username=vuser                    # 指定虚拟用户的映射到的真实用户
      user_config_dir=/etc/vsftpd/vuserconfig # 配置各个虚拟用户的权限
      allow_writeable_chroot=YES              # 允许虚拟用户写权限
      pam_service_name=vsftpd.vuser           # pam 服务的配置（设置虚拟用户的账号密码）
      # pam_service_name=vsftpd               # 需要把原来默认的配置关掉，不再支持本地用户登录 ftp 服务（pam 服务只允许一个配置）
      ```

6. 重启 ftp 服务: `systemctl restart vsftpd.service`。

</br>
</br>

# samba 服务

samba 用于模拟 Windows 的文件共享。

**安装**: `yum install samba`。

**主配置文件**: `/etc/samba/smb.conf`。具体介绍请通过 `man 5 samba` 查阅。

**用户**的设置：

- `smbpasswd -a user1` **添加** user1 用户到 samba 系统中。
  - 上命令执行成功后，会提示你设置密码，该密码应用于 samba 系统。
  - user1 必须在 Linux 系统已经创建。
  - samba 系统是参考 Linux 中该用户的权限。
- `smbpasswd -x user1` 从 samba 系统中**删除** user1 用户。
- `pdbedit -L` **查看**所有 samba 用户。

**启动**服务: `systemctl start smb.service`。停止则是 `stop`。

Linux **客户端**: `mount -t cifs -o username=user1 //127.0.0.1/user1 /mnt`

- `-t cifs` 指定文件系统的类型。此参数可以省略，由系统自动判断。
- `-o` 表示指定源。
- `username=user1` 设置登录用户名为 user1。
- `//127.0.0.1/user1` 就是源的地址，其中 `user1` 与用户同名，是该用户的根目录。
- `/mnt` 是本机被挂载的目录。
- 命令执行后，会提示输入密码。密码正确则挂载成功。
- 如果需要解除挂载，使用 `unmount /mnt`。

Windows 客户端:

- 资源管理访问共享
- 映射网络驱动器

<!-- TODO: 未实践 -->

</br>
</br>

# NFS 服务

nfs 服务是 Linux 自带的，不需要额外安装。通过 `systemctl start nfs.service` **启动**服务。

主配置文件: `/etc/exports`。编写说明：

- 一行一条规则。
- 示例: `/data/share 10.0.0.11(ro) 10.0.0.22(rw)`。
- `10.0.0.11` 即使可访问到共享文件夹的主机地址，如果不限制，可以指定为 `*`。
- `(rw)` 配置功能限制。多个功能用 `,` 分隔。常用配置：
  - `rw`: 读写。
  - `ro`: 只读。
  - `sync`: 读到内存中。
  - `all_squash`: 将远程访问的所有普通用户及所属组都映射为匿名用户或用户组(nfsnobody)。
- 注意 `10.0.0.11(ro)`，ip 与配置`()`之间不能有空格。
- `/data/share` 是共享目录。建议将该文件夹的访问权限设置为 `nfsnobody`。

**查看**可用的共享服务: `showmount -e localhost`。`localhost` 是主机地址。

**客户端**挂载: `mount -t nfs localhost:/data/share /mnt`。

在 Windows 的 nfs 客户端是收费软件。

</br>
</br>

# Nginx

Nginx(engine X) 是一个高性能的 Web 和反向代理服务器。它支持 HTTP、HTTPS 和邮件代理协议。

OpenResty 是基于 Nginx 和 Lua 实现的 Web 应用网关，集成了大量的第三方模块。

OpenResty 的**安装**：

- `yum-config-manager --add-repo https://openresty.org/package/centos/openresty.repo` 将 repo 加载到 yum 中。其他第三方的 repo 都是用此命令加载。
- `yum install openresty` 运行安装。

OpenResty **主配置**文件: `usr/local/openresty/nginx/conf/nginx.conf`。

- 配置内容简介：

  ``` shell
  # 运行时占用的进程数。如果并发量较大，需要增加数量，建议与 CPU 核心数相同。
  worker_processes 1;
  events {
    # 单个 worker 支持的最大并发连接数。超过则会返回 503。如果核心主频较高，也可以把此值调高。
    worker_connections 1024;
  }
  # http 服务配置。里面可以包含多个 server，即多个 http 服务。
  http {
    # 配置日志格式。main 是格式别名。"...." 是格式内容，具体不列举。
    log_format main ....
    # 设置访问日志的路径和格式配置(main)。
    access_log logs/access.log main
    .....
    # 单个服务相关配置。外层配置所有服务的共同配置。
    server {
      ......
    }
  }
  ```

- Nginx 的配置文件路径则是没有 "`/openresty`"。

OpenResty **启动**服务: `service openresty start`。还有 `stop`、`restart`、`reload`。

在 `/usr/local/openresty/nginx` 目录下：

- `/logs` 目录存放日志文件。一般是查看错误日志。
- `/html` 目录存放 Web 静态资源。

Nginx 功能非常强大，内容也很多，请自行学习。

</br>
</br>

# DNS 服务

DNS(Domain Name System, 域名系统) 服务的作用是将域名解析成 IP 地址。

例如 `www.baidu.com`:

- `www.baidu.com` 整个就是 FQDN(Full Qualified Domain Name, 完全限定域名)。同时带有主机名和域名的名称。
- `www` 是主机名。
- `baidu.com` 是域名。
- `.com` 就是顶级域(Top-level Domain, TLD)。
  - 其实还有更高一级，就是根域，主要用来管理互联网的主目录，最早是 IPV4，全球只有 13 台。

**查询方式**有两种：

- **递归**。即当前 DNS 服务器包含目标域名，通过查询服务器的列表返回结果。
- **迭代**。即当前 DNS 服务器不包含目标域名，需要再请求别的 DNS 服务器。

**解析方式**有两种：

- 如果是**把域名解析成 IP**，就是**正向解析**。
- 如果把 IP 解析成域名，那就是**反向解析**。

**安装**: `yum install bind bind-utils`。

**启动**: `systemctl start named.service`。

**主配置**文件: `/etc/named.conf`。

检查配置是否有误: `named-checkconf`。

<!-- TODO: 实践做一个 DNS 服务器 -->

本机可以通过修改 `/etc/host` 的配置指定某个域名与 IP 的映射关系。
