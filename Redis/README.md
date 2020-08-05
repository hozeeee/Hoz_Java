
# Redis

Redis 是 Key-Value 型 NoSQL 数据库；它将数据存储在内存中，同时也能持久化到硬盘；它常用于缓存，利用内存的高效，提升程序的处理速度。

Redis 的特点是，速度快、有广泛的语言支持、提供持久化、支持多种数据结构、多服务器的主从复制、分布式与高可用。

[官网地址](https://redis.io/) 。

</br>

## 安装&启动

需要注意的是，官方默认不提供 Windows 系统的版本，只能在 Linux 下使用。

Redis 的默认启动端口为 **6379** ，但不建议直接使用，避免黑客攻击。

### Linux

Linux 的安装命令如下：（分别是，下载、解压、定位、编译安装）

(执行下面命令前，需要安装 gcc ，它是 Linux 的编译包，可以对程序的源代码编译， `make` 命令基于 `gcc` ： `yum install gcc` 。)

``` shell
$ wget http://download.redis.io/releases/redis-6.0.6.tar.gz
$ tar xzf redis-6.0.6.tar.gz
$ cd redis-6.0.6
$ make
```

在解压文件夹的根目录下， `redis.conf` 就是配置文件，在 `src` 目录下的 `redis-server` 就是启动服务器的文件，而 `redis-cli` 就用于开启客户端连接。

在解压文件夹的根目录下，执行如下命令启动 Redis :

``` shell
$ src/redis-server redis.conf
```

### Windows

此 [GitHub 项目](https://github.com/MicrosoftArchive/redis) 是微软提供的 Redis 版本，但此项目已经很久没有维护了，只有 3.2.100 版本，远落后于官方版本。但作为学习练练手是可以的，因为其命令没有太大变化。

在控制台中，使用 `redis-server.exe` 加载配置文件 `redis.windows.conf` 即可启动服务，即 `redis-server.exe redis.windows.conf`。

</br>

## 常用配置项

配置项都在 `redis.conf` 中修改。修改完成后，记得重启服务才能生效。

| 配置项      | 示例              | 说明                               | 备注                                            |
|:------------|:------------------|:-----------------------------------|:------------------------------------------------|
| daemonize   | daemonize yes     | 是否启用后台运行，默认 no          | 以守护进程方式启动                              |
| port        | port 6380         | 设置端口号为 6380 ，默认 6379      | 建议修改，降低黑客的成功率                      |
| logfile     | logfile redis.log | 设置日志文件为 redis.log ，默认 "" |                                                 |
| databases   | database 255      | 设置数据库总量，默认 16            | 以编号形式获取数据库(数据库没有名字)，从 0 开始 |
| dir         | dir ./            | 数据存放的目录，默认 ./            | 不建议修改，保存在名为 dump.rdb 的文件中        |
| requirepass | requirepass root  | 设置密码为 root ，默认没有         |                                                 |

使用 `redis-cli -p 6379` 可以进入 Redis 客户端的命令行界面，若端口号使用默认的 6379 ，则 `-p 6379` 参数可以省略。在该界面下，有如下常用命令：

- `ping`: 若返回 `PONG` ，则说明 Redis 服务正常使用。
- `auth my_pass_word`: 当配置了 `requirepass` 后，需要使用此命令通过 Redis 的身份认证。
- `select 0`: 选择第 0 号数据库。记住， Redis 的数据库是没有名字，只有编号。

当我们配置了 `daemonize` 为 `yes` 后，关闭 Redis 有两个方法：

- 使用 Linux 的 `kill` 把进程杀死
- (推荐)使用 `redis-cli -p 6379 shutdown` 优雅关闭 Redis

</br>

## 常用命令

| 命令   | 示例           | 说明                              |
|:-------|:---------------|:----------------------------------|
| seletc | select 0       | 选择 0 号数据库                   |
| set    | set name kitty | 设置键值对 [name, kitty]          |
| get    | get name       | 获得键名为 name 的值              |
| del    | del name       | 删除键名为 name 的值              |
| keys   | keys n*        | 根据 Pattern 表达式擦汗寻符合的键 |
| dbsize | dbsize         | 返回键值对的总数(即数据总量)      |
| exists | exists name    | 检查是否存在键名 name             |
| expire | exprie name 20 | 设置键名为 name 在 20 秒后过期    |
| ttl    | ttl name       | 查看键名为 name 的过期剩余时间    |

</br>

## 数据类型

使用频率最高的是 String ，其次是 Hash 。

### String 类型

最大 512 MB，建议单个键值对不超过 100 KB。

常用命令：

| 命令   | 示例                  | 说明                    |
|:-------|:----------------------|:------------------------|
| get    | get name              | 获取键名为 name 的值    |
| set    | set name lisi         | 设置键值对 [name, lisi] |
| mset   | mset name lisi age 12 | 一次性设置多个键值对    |
| mget   | mget name age         | 一次性读取多个键值对    |
| del    | del name              | 删除指定键名的值        |
| incr   | incr age              | 指定键名自增 1          |
| decr   | decr age              | 指定键名自减 1          |
| incrby | incrby age 2          | 自增指定步长            |
| decrby | decrby age 2          | 自减指定步长            |

### Hash 类型

用于**存储结构化数据**。

| 命令(都以"h"开头) | 示例                           | 说明                              |
|:------------------|:-------------------------------|:----------------------------------|
| hget              | hget human:1 name              | 获取 hash 中键名为 name 的值      |
| hset              | hset human:1 name lisi         | 设置 hash 中的键值对 [name, lisi] |
| hmset             | hmset human:1 name lisi age 12 | 同时设置 hash 中多个键值对        |
| hmget             | human human:1 name age         | 同时获取 hash 中多个键的值        |
| hgetall           | hgetall human:1                | 获取 hash 中所有键值对            |
| hdel              | hdel human:1 age               | 删除 hash 中的指定键值对          |
| hexists           | hexists human:1 height         | 检查 hash 中是否存在该键名        |
| hlen              | hlen human:1                   | 获取 hash 的长度                  |

上面示例的 `human` 表示一类数据， `:1` 表示第 1 个。

### List 列表/数组类型

是一些列字符串的"数组"，且按插入时的顺序排列。其最大长度为 2^32 - 1 。

| 命令   | 示例                         | 说明                                     |
|:-------|:-----------------------------|:-----------------------------------------|
| rpush  | rpush nameList zhangsan lisi | 在 nameList 的右边依次插入 zhangsan lisi |
| lpush  | ....                         | 在 nameList 的左....                     |
| rpop   | rpop nameList                | 将 nameList 的右边第一个字符串弹出       |
| lpop   | ...                          | 将 nameList 的左.....                    |
| lrange | lrange nameList 0 -1         | 查询数组所有元素(0为第一个,-1为最后一个) |

### 集合类型（ set 和 Zset ）

集合类型的**成员是唯一的**，但 **Zset** 是**有序**的，而 **set** 是**无序**的。

| 命令          | 示例                             | 说明                                                  |
|:--------------|:---------------------------------|:------------------------------------------------------|
| sadd          | sadd classSet A B D              | 在 classSet 集合中添加元素 A B D                      |
| smembers      | smembers classSet                | 获取 classSet 的所有成员                              |
| sinter        | sinter classSet classSet2        | 获取 classSet 与 classSet2 的**交集**                 |
| sunion        | sunion classSet classSet2        | 获取 classSet 与 classSet2 的**并集**                 |
| sdiff         | sdiff classSet classSet2         | 获取 classSet 与 classSet2 的**差集**（前者减去后者） |
|               |                                  |                                                       |
| zadd          | sadd classZset 100 A 99 B 102 D  | 在 classZset 集合中添加元素 A B D （B->A->C）         |
| zrange        | zrange classZset 0 -1            | 获取 classZset 的所有成员                             |
| zrange        | zrange classZset 0 -1 withscores | 获取 classZset 的所有成员，且带有顺序编号             |
| zrangebyscore | zrangebyscore classZset 99 100   | 获取 classZset 的分数在 99~100 之间的成员             |

其实从命令可以看出， Zset 是基于数组，根据分数(score)排序。

</br>
</br>

# Jedis

Jedis 是 Java 语言开发的 Redis 客户端工具包，它只是对 Redis 命令的封装。

相关的 `redis.conf` 配置项：

- `mode` 配置项默认是 `yes` ，表示开启保护模式，在开发环境下可以先设为 `no` 。
- `bind` 配置默认是 `127.0.0.1` ，表示只能本机访问，在开发环境下可以先设为 `0.0.0.0` ，表示允许所有主机访问。

Linux 开放防火墙：（命令行执行）

- `firewall-cmd --zone=public --add-port=6379/tcp --permanent`
  - `--zone=public` 表示设置公用区域的防火墙
  - `--add-port=6379/tcp` 表示开放端口号为 6379 的 tcp 协议连接
  - `--permanent` 表示永久生效，若不设置， Linux 重启就会恢复防火墙设置
- `firewall-cmd --reload` 重启防火墙，才能对上面配置生效

[GitHub 地址](https://github.com/xetorthio/jedis) ，建议使用最广泛的 2.9.0 版本。

</br>

## 简单应用

具体代码可以查看 [redis_demo](./redis_demo) 的 `JedisTester` 类。

添加依赖：

``` xml
<dependency>
  <groupId>redis.clients</groupId>
  <artifactId>jedis</artifactId>
  <version>3.2.0</version>
  <type>jar</type>
  <scope>compile</scope>
</dependency>
```

创建 Jedis 实例对象，然后连接和关闭：

``` java
// 实例化 （若端口为默认的6379,则第二个参数可以省略）
Jedis jedis = new Jedis("localhost", 6379);
try {
  // 授权密码
  jedis.auth("root");
  // 使用第 2 个数据库
  jedis.select(2);
  System.out.println("Redis连接成功");
} catch (Exception e) {
  e.printStackTrace();
} finally {
  jedis.close();
}
```

jedis 类中提供了与 Redis 同名的方法，所以用法很简单直接：

``` java
// 字符串
jedis.set("num", "100");
String num = jedis.get("num");
System.out.println(num); // 100
jedis.mset("myStr1", "foo1", "myStr2", "foo2", "myStr3", "foo3");
List<String> myStrList = jedis.mget("myStr1", "myStr2", "myStr3");
System.out.println(myStrList);  // [foo1, foo2, foo3]
Long num2 = jedis.incr("num");
System.out.println(num2);  // 101

// hash
jedis.hset("student:666", "name", "zhangsan");
String studName = jedis.hget("student:666", "name");
System.out.println(studName); // zhangsan
Map<String, String> studMap = new HashMap();
studMap.put("name", "lisi");
studMap.put("age", "12");
jedis.hmset("student:999", studMap);
Map<String, String> studMapGet = jedis.hgetAll("student:999");
System.out.println(studMapGet); // {age=12, name=lisi}

// list
jedis.del("letter");
jedis.rpush("letter", "A", "B", "C");
String popStr = jedis.rpop("letter"); // C
System.out.println(popStr);
List<String> list = jedis.lrange("letter", 0, -1);
System.out.println(list);  // [A, B]
```

</br>

## 对对象的存储与提取

思路是，将对象解析成 JSON 字符串，然后以键值对的形式存储到 Redis ；同理，取出数据时根据键名获取 JSON 字符串，然后解析成对象。

JSON 与对象之间的转换，使用 [**fastjson**](https://github.com/alibaba/fastjson) 库：

``` xml
<dependency>
  <groupId>com.alibaba</groupId>
  <artifactId>fastjson</artifactId>
  <version>1.2.70</version>
</dependency>
```

下面以存储 `Student` 类为例。

创建 `Student` 类：

``` java
public class Student {
  private Integer id;
  private String name;
  private Integer age;
  private Float weight;
  // 快速生成 Getter 、 Setter 、 构造函数 ...
}
```

测试代码在 `CacheSimple` 文件中：

``` java
// 先实例化几个对象作为数据
List<Student> studList = new ArrayList<Student>();
studList.add(new Student(1, "zhangsan", 12, 65.32f));
studList.add(new Student(2, "lisi", 11, 88.88f));
studList.add(new Student(3, "zhaowu", 16, 55.65f));

// 将数据格式化成 JSON ，并存到 Redis
for (Student stud : studList) {
  String studJson = JSON.toJSONString(stud);
  System.out.println(studJson);
  // 存到 Redis
  String key = "student:" + stud.getId();
  jedis.set(key, studJson);
}
System.out.println("-----------------------------");

// 获取 JSON ，并转成 对象
jedis.select(3);  // 假设此处的jedis与前面不同,则需要重新选择数据
String inputId = "2"; // 假设以id查询,输入id的步骤省略
String key = "student:" + inputId;
if (jedis.exists(key)) {
  String json = jedis.get(key);
  Student stud = JSON.parseObject(json, Student.class);
  System.out.println("查询到学生：" + stud.getName());
} else {
  System.out.println("不存在该编号的学生");
}
```
