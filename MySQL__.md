
# MySQL

## 起步

### 安装

1. 到官网的 [**下载地址**](https://dev.mysql.com/downloads/mysql/) 。

2. 下载完成后，将该压缩包**解压**到合适目录。

3. 将解压后的文件夹下的 **`bin` 文件夹**配置到环境变量。

4. 创建**配置文件 `my.ini`**(Linux和Mac是`my.cnf`) ，配置文件放在软件目录的根目录下(如`C:\Program Files\mysql-8.0.20-winx64`)，若没有 `my.ini` 文件，可以通过记事本创建，命名为 `my.ini` ，然后填上如下内容：（**记得把两个地址的值修改**）

    ``` txt
    [mysqld]
    # 设置3306 端口
    port=3306
    # 设置mysql 的安装目录 （注意！修改成正确的地址）
    basedir=C:\\Program Files\\mysql-8.0.20-winx64
    # 设置mysql 数据库的数据的存放目录 （注意！修改成正确的地址）
    datadir=C:\\Program Files\\mysql-8.0.20-winx64\\data
    # 允许最大连接数
    max_connections=200
    # 允许连接失败的次数。这是为了防止有人从该主机试图攻击数据库系统
    max_connect_errors=10
    # 服务端使用的字符集默认为UTF8
    character-set-server=utf8
    # 创建新表时将使用的默认存储引擎
    default-storage-engine=INNODB
    # 默认使用“mysql_native_password”插件认证
    default_authentication_plugin=mysql_native_password
    [mysql]
    # 设置mysql 客户端默认字符集
    default-character-set=utf8
    [client]
    # 设置mysql 客户端连接服务端时默认使用的端口
    port=3306
    default-character-set=utf8
    ```

5. (**管理员身份**)控制台依次输入下面的命令：
    - **初始化**： `mysqld --initialize –-console`
    - 输入上面命令后，控制台会打印出一些信息，留意类似 `A temporary password is generated for root@localhost: Z1jA4LbvA6;,`
    - 其中 `Z1jA4LbvA6;,` 是 root 用户的默认密码
    - **安装服务**： `mysqld --install`
    - **启动服务**： `net start mysql`
    - 假如启动服务后，提示 `MySQL 服务无法启动。`，先执行如下几步，然后重复上面步骤：
    - 手动删除 `data` 文件夹（上面`my.ini`文件指定的数据存放目录）
    - 停止服务： `net stop mysql`
    - 删除服务： `sc delete mysql`

6. 连接数据库：
    - 控制台输入命令 `mysql –u root –p`
    - 会提示输入密码，输入上面记住的默认密码

7. 修改密码：
    - 控制台输入命令 `ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';`
    - 上面语句中，密码被修改为 `root` 。

8. 退出数据库连接： `exit` 或 `quit`

### 相关术语

- 数据库系统(Database System, DBS):
  - 数据库(Database, DB)
  - 数据库管理系统(Database Management System, DBMS): 用于定义/管理数据的小软件
  - 应用开发工具
  - 管理员及用户

- SQL(Structured Query Language)语言，是结构化查询语言，包括:
  - DDL: 数据定义语言，定义数据库、表、触发器等。
  - DML: 数据操作语言，包括增、删、改。
  - DQL: 数据查询语言，数据库的查询。
  - DCL: 数据控制语言，跟权限有关。

### 数据类型

整型：

| 数据类型      | 存储范围 (无符号关键字 `unsigned`)           | 字节 |
|:--------------|:---------------------------------------------|:----:|
| TINYINT       | 有符号： -2^7 到 2^7-1                       |  1   |
|               | 无符号： 0 到 2^8-1                          |  1   |
| SMALLINT      | 有符号： -2^15 到 2^15-1                     |  2   |
|               | 无符号： 0 到 2^16-1                         |  1   |
| MEDIUMINT     | 有符号： -2^23 到 2^23-1                     |  3   |
|               | 无符号： 0 到 2^24-1                         |  1   |
| INT           | 有符号： -2^31 到 2^31-1                     |  4   |
|               | 无符号： 0 到 2^32-1                         |  1   |
| BIGINT        | 有符号： -2^63 到 2^63-1                     |  8   |
|               | 无符号： 0 到 2^64-1                         |  1   |
| BOOL, BOOLEAN | 等价于 TINYINT(1) , 0 为 false ; 其他为 true |  1   |

浮点型：（`M`是数字总位数;`D`是小数点后面的位数）

| 数据类型         | 存储范围                                                | 字节 |
|:-----------------|:--------------------------------------------------------|:----:|
| `FLOAT[(M,D)]`   | -3.40E+38 到 -1.17E-38 、 0 、 1.175E-38 到 3.40E+38    |  4   |
| `DOUBLE[(M,D)]`  | -1.79E+308 到 -2.22E-308 、 0 、 2.22E-308 到 1.79E+308 |  8   |
| `DECIMAL[(M,D)]` | 和 DOUBLE 一样，内部以字符串形式存储数值                | M+2  |

字符型：

| 数据类型                    | 字节 (L是长度)          | 备注                                                |
|:----------------------------|:------------------------|:----------------------------------------------------|
| CHAR(M)                     | M (0<=M<=255)           | CHAR 存储时末尾会以空格补齐，检索时会删除。         |
|                             |                         | VARCHAR 存储时不会以空格补齐，末尾的空格也会保留。  |
|                             |                         | CHAR 是"空间换时间"；VARCHAR 是"时间换空间"。       |
|                             |                         | VARCHAR 需要额外的空间记录字符长度。                |
|                             |                         | CHAR 和 VARCHAR 都可以有默认值。                    |
| VARCHAR(M)                  | L+1 (L<=M且0<=M<=65535) |                                                     |
| TINYTEXT                    | L+1 (L<2^8)             | 4 种 "TEXT" 都不能有默认值。                        |
|                             |                         | "TEXT"检索时区分大小写，但 CHAR 和 VARCHAR 不区分。 |
| TEXT                        | L+2 (L<2^16)            |                                                     |
| MEDIUMTEXT                  | L+3 (L<2^24)            |                                                     |
| LONGTEXT                    | L+4 (L<2^32)            |                                                     |
| ENUM('value1','value2',...) | 1、2                    | 最多65535个成员                                     |
| SET('value1','value2',...)  | 1、2、3、4、8           | 最多64个成员                                        |

日期时间型：（开发中常用`int`类型代替时间）

| 数据类型  | 存储范围                                      | 字节 | 备注                                     |
|:----------|:----------------------------------------------|:----:|:-----------------------------------------|
| TIME      | -838:59:59 ~ 838:59:59                        |  3   | 兼容格式 `D HH:mm:ss` ，日期会转换成小时 |
| DATE      | 1000-01-01 ~ 9999-12-31                       |  3   |                                          |
| DATETIME  | 1000-01-01 00:00:00 ~ 9999-12-31 23:59:59     |  8   |                                          |
| TIMESTAMP | 1970-01-01 00:00:01 UTC ~ 2038-01-19 03:14:07 |  4   |                                          |
| YEAR      | 1901 ~ 2155                                   |  1   |                                          |

### 存储引擎

- **MyISAM** 在 5.5 版本之前默认使用的。
  - 每创建一个表都会生成三个文件：
    - `.frm`: 表结构文件
    - `.MYD`: 数据文件
    - `.MYI`: 索引文件
  - 可以在创建表的时候可以指定数据文件和索引文件的存储位置：
    - `DATA DIRECORY [=] 数据文件保存的绝对路径`
    - `INDEX DIRECORY [=] 索引文件保存的绝对路径`
    - 实际上的真实路径没有变化，只是在你的目标地址创建一个软链接
  - 单表**最大支持的数据量 2^64** 条记录
  - 单表**最多建立 64 个索引**
  - 如果是复合索引，最多包含 16 个字段，索引最大长度是 1000B
  - 存储格式：（定长或动态不需要指定，会根据字段类型自动选择）
    - 定长/静态 (FIXED): 是指表的字段中不包含 VARCHAR/TEXT/BLOB 类型的。**性能更好**。
    - 动态 (DYNAMIC): ...。占用空间少，但性能差，会产生"碎片"。
    - 压缩 (COMPERSSED): 需要专门的工具(myisampack)创建。
  - 修改表的存储格式： `CREATE TABLE tab_name(...)ENGINE=MyISAM ROW_FORMAT=FIXED;`

- **InnoDB** 在 5.5 版本之后默认使用的。
  - 遵循 **ACID 模型**，即原子性(Atomiocity)、一致性(Consistency)、隔离性(Isonlation)、持久性(Durability)。
  - 支持事务，具有从服务崩溃中恢复的能力，能够最大限度保护用户的数据。
  - 支持**行级锁**，无视表级锁。可以提升多用户并发时的读写性能。
  - 支持外键，保证数据的一致性和完整性。
  - 拥有自己独立的缓冲池，常用的数据和索引都在缓存中。
  - 对于 INSERT UPDATE DELETE ，它会使用一种 change buffering 的机制来自动优化：
    - 提供一致性的读
    - 缓冲变更的数据
    - 因此能减少磁盘 I/O ，提高性能
  - 每创建一个表都生成两个文件：
    - `.frm`: 表结构文件
    - `.ibd`: 表空间文件，包含数据和索引

### 其他

注释以 `#` 或 `--` 开头。

语法规范：

- 命令行结束符默认是 `;` 或 `\g` 。
- 常用 MySQL 的关键字需要大写，库名、表名、字段名等使用小写。
- SQL 语句支持折行操作，拆分的时候不能把完整的单词拆开。
- 数据库名、表名、字段名不要使用 MySQL 的保留字，如果必须要使用，需要用反引号("``")将其括起来。

</br>

## 数据库操作

| 命令                                                         | 说明               | 备注                                   |
|:-------------------------------------------------------------|:-------------------|:---------------------------------------|
| **`USE db_name;`**                                           | **打开**指定数据库 |                                        |
| **`CREATE DATABASE [IF NOT EXISTS] db_name [DEFAULT];`**     | "**增**"           | 末尾可追加 `CHARACTER SET 'UTF8'` 参数 |
| `DROP DATABASE db_name;`                                     | "删"               |                                        |
| `ALTER DATABASE db_name [DAFAULT] CHARACTER SET [=] 'UTF8';` | "改"               |                                        |
| **`SHOW CREATE DATABASE db_name`**                           | "**查**"           |                                        |
| `SHOW DATABASES;`                                            | "查"所有           |                                        |
|                                                              |                    |                                        |
|                                                              |                    |                                        |

注意：

- `DATABASE` 与 `SCHEMA` 等效替换。
- 一般命令都能跟上 `IF NOT EXISTS` 或 `IF EXISTS` 参数，决定改命令是否执行。如 `CREATE DATABASE [IF NOT EXISTS] db_name;` 。
- `information_schema` 、 `performance_schema` 、 `mysql` 这三个数据库不能删除。
- `CREATE DATABASE [IF NOT EXISTS] db_name [DEFAULT] CHARACTER SET 'UTF8';`

</br>

## 数据表操作

首先介绍**完整性约束条件**，又称为字段的"**属性**"，在创建表定义字段时使用，也可以组合使用：

- `COMMENT`: 为字段添加描述信息，不会影响功能，但方便维护。
  - 示例： `id INT UNSIGNED AUTO_INCREMENT KEY COMMENT '用户编号';`
- `UNSIGNED`: 无符号数值。
- `ZEROFILL`: "0" 填充，数据长度不足时，使用前补 "0" 的效果填充至指定长度。
- `NTONULL`: 非空约束，即该字段必须提供具体值。
- `DEFAULT`: 默认值，如果插入记录是没有给定值，则使用默认值。
  - 示例： `email DEFAULT '123@139.com';`
- `PRIMARY KEY`: 主键字段只能有一个，除了复合主键。
  - 唯一主键(写法1)： `id INT KEY;`
  - 唯一主键(写法2)： `PRIMARY KEY(field);`
  - 复合主键： `PRIMARY KEY(field1,field2,...);` 可以指定若干个字段共同作为主键
- `AUTO_INCREMENT`: 自动增长，只能用于数值列，且必须配置索引使用。默认从 1 开始，步长 1 。
  - 示例： `id INT AUTO_INCREMENT KEY;`
- `UNIQUE KEY`: 该字段的值是唯一的索引， NULL 除外，与主键不同，可以给多个字段指定。
- `FOREIGN KEY`: 见 [下文](#外键约束) 。

| 命令                                                                              | 说明                                                 |
|:----------------------------------------------------------------------------------|:-----------------------------------------------------|
| `ALTER TABLE tab_name ADD 字段名 字段类型 [完整性约束条件] [FIRST|AFTER 字段名];` | **添加字段**                                             |
| `ALTER TABLE tab_name DROP 字段名;`                                               | **删除字段**                                             |
| `SHOW CREATE TABLE tab_name;`                                                     | 查看指定表的详细信息                                 |
| `DELETE FROM test_tab;`                                                           | **清空**数据表（但`AUTO_INCREMENT`不会被还原）       |
| `TRUNCATE TABLE tab_name;`                                                        | **彻底清空**数据表（包括`AUTO_INCREMENT`也会被还原） |
| `ALTER TABLE test_tab AUTO_INCREMENT=1;`                                          | 将数据表的 `AUTO_INCREMENT` 设为 1                   |
|                                                                                   |                                                      |
|                                                                                   |                                                      |
|                                                                                   |                                                      |







- `ALTER TABLE tab_name ALTER 字段名 SET DEFAULT 默认值;` 添加/修改默认值
- `ALTER TABLE tab_name ALTER 字段名 DROP DEFAULT;` 删除默认值

- `ALTER TABLE tab_name MODIFY 字段名 字段类型 [完整性约束条件] [FIRST|AFTER 字段名称];` 修改字段类型、字段属性
- `ALTER TABLE tab_name CHANGE 原字段名 新字段名 字段类型 [完整性约束条件] [FIRST|AFTER 字段名称];` 修改字段名称、字段类型、字段属性

- `ALTER TABLE tab_name ADD PRIMARY KEY(字段名);` 添加主键
- `ALTER TABLE tab_name DROP PRIMARY KEY;` 删除主键（若字段有`AUTO_INCREMENT`属性，需要先删除该属性，再删除主键）

- `ALTER TABLE tab_name ADD UNIQUE KEY|INDEX(字段名);` 添加索引，以字段名作为索引名
- `ALTER TABLE tab_name ADD UNIQUE KEY|INDEX 索引名(字段名);` 添加索引，另外指定索引名
- `ALTER TABLE tab_name DROP UNIQUE KEY|INDEX [index_name] (字段名);` 删除索引（若`index_name`省略，则以字段名作为`index_name`）

- `ALTER TABLE tab_name RENAME [TO|AS] new_tab_name;` 或 `RENAME TABLE tab_name new_tab_name;` 修改数据表名称






</br>

## 常用函数

| 命令                   | 说明                   |
|:-----------------------|:-----------------------|
| `SELECT USER();`       | 得到登陆的用户信息     |
| `SELECT VERSION();`    | 得到 MySQL 的版本信息  |
| `SELECT NOW();`        | 得到当前日期时间       |
| `SELECTED DATABASE();` | 显示当前被打开的数据库 |
|                        |                        |
|                        |                        |
|                        |                        |
|                        |                        |





</br>

## 其他命令

| 命令 | 说明 |
|:-----|:-----|
|      |      |
|      |      |
|      |      |
|      |      |
|      |      |



- `SHOW WARNINGS;` 查看上一句代码的警告。




</br>

## 数据"增删改查"

| 命令                                                            | 说明             | 备注               |
|:----------------------------------------------------------------|:-----------------|:-------------------|
| `INSERT tab_name(field1,field2,...) VALUES(value1,value2,...);` | 对特定表插入数据 | 特定字段的单条数据 |
| `INSERT VALUES(value1,value2,...);`                             |                  | 所有字段的单条数据 |
| `INSERT VALUES(value1,value2,...), (value1,value2,...) ,...;`   |                  | 所有字段的多条数据 |
| `DESC tab_name;`                                                | 查看表结构       |                    |
| `DESCRIBE tab_name;`                                            |                  |                    |
| `SHOW COLUMNS FROM tab_name;`                                   |                  |                    |
| `SHOW [FULL] TABLES [{FROM|IN}] db_name [LIKE ];`               | 查看数据表列表   |                    |


`INSERT [INTO] tab_name[(field1,field2,...)] {VALUE|VALUES}(value1,value2,...)[(value1,value2,...),...];` 增
`INSERT tab_name SET field1=value1, field2=value2, ...;`

`DELETE FROM tab_name [WHERE 条件];` 删

`UPDATE tab_name SET field1=value1,field2=value2,... [WHERE 条件];` 改


查
`SELECT field1,field2,.... FROM tab_name [WHERE 条件] [GROUP BY {col_name|position} HAVING 二次筛选] [ORDER BY {col_name|position|expr} {ASC|DESC}] [LIMT 限制结果集的条数];`





































