
# MySQL

## 给自己备忘的速查

- 数据库
  - 建库：`CREATE DATABASE db_name;`
  - 选库：`USE db_name;`
  - 删库：`DROP DATABASE db_name;`
  - 已有库：`SHOW DATABASES;`
  - 当前库：`SHOW DATABASE();`
- 数据表
  - 建表：`CREATE TABLE tab_name(id INT AUTO_INCREMENT, ...);`
  - 改字段：`ALTER TABLE tab_name CHANGE old_filed_name new_filed_name 字段类型 [完整性约束条件];`
  - 增删字段：`ALTER TABLE tab_name {ADD|DROP} field 字段类型 [完整性约束条件];` (只有添加才需要"字段类型"和"属性")
- 数据
  - 增：`INSERT tab_name VALUES(val1, val2, ....);`
  - 删：`DELETE FROM tab_name WHERE ...;`
  - 改：`UPDATE tab_name field1=value1, field2=value2, ...;`
  - 查：`SELECT field1, field2, .... FROM tab_name [WHERE 条件] [GROUP BY {field|position} HAVING 二次筛选] [ORDER BY {field|position|expr} {ASC|DESC}] [LIMIT 限制结果集的条数];`
- 查询条件
  - WHERE：比较运算符号(`>`/`<`/`=`等)、范围查询(`BETWEEN ... AND`)、包含于集合(`IN(val1,val2,...)`)、逻辑运算符(`OR`/`AND`)、模糊查询(`LIKE`)
  - GROUP BY
  - ORDER BY
  - LIMIT
- 多表联查
  - 内连接：`SELECT field1, field2, ... FROM tab_name1 INNER JOIN tab_name2 ON 连接条件 [INNER JOIN tab_name2 ON 连接条件2 ....];`
  - 外连接：`SELECT field1, field2, ... FROM tab_name1 OUTER {LEFT|RIGHT} JOIN tab_name2 ON 条件 [OUTER {LEFT|RIGHT} JOIN tab_name2 ON 条件2 ...];`
  - 外键约束：
    - 删除外键： `ALTER TABLE tab_name DORP FOREIGN KEY fk_name;`
    - 创建外键： `ALTER TABLE tab_name ADD FOREIGN KEY(fk_name) REFERENCES fk_tab_name(prim_key);`
- 子查询：`SELECT * FROM student_tab WHERE score >= (SELECT score FROM leave_tab WHERE id=1);`

</br>

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

| 命令                                                                          | 说明               |
|:------------------------------------------------------------------------------|:-------------------|
| **`USE db_name;`**                                                            | **打开**指定数据库 |
| **`CREATE DATABASE [IF NOT EXISTS] db_name [DEFAULT] CHARACTER SET 'UTF8';`** | "**增**"           |
| `DROP DATABASE db_name;`                                                      | "删"               |
| `ALTER DATABASE db_name [DAFAULT] CHARACTER SET [=] 'UTF8';`                  | "改"               |
| **`SHOW CREATE DATABASE db_name`**                                            | "**查**"           |
| `SHOW DATABASES;`                                                             | "查"所有           |

注意：

- `DATABASE` 与 `SCHEMA` 等效替换。
- 一般命令都能跟上 `IF NOT EXISTS` 或 `IF EXISTS` 参数，决定改命令是否执行。如 `CREATE DATABASE [IF NOT EXISTS] db_name;` 。
- `information_schema` 、 `performance_schema` 、 `mysql` 这三个数据库不能删除。

</br>

## 数据表操作

首先介绍**完整性约束条件(Constraints)**，又称为字段的"**属性**"，在创建/修改表时，用于定义字段的属性，多个属性也可以组合使用：

- `COMMENT`: 为字段添加描述信息，不会影响功能，但方便维护。
  - 示例： `id INT UNSIGNED AUTO_INCREMENT KEY COMMENT '用户编号';`

- `UNSIGNED`: 无符号数值。

- `ZEROFILL`: "0" 填充，数据长度不足时，使用前补 "0" 的效果填充至指定长度。

- **`NOT NULL`**: 非空约束，即该字段必须提供具体值。

- `DEFAULT`: 默认值，如果插入记录是没有给定值，则使用默认值。
  - 示例： `email DEFAULT '123@139.com';`

- **`PRIMARY KEY`**: 主键字段只能有一个，除了复合主键。
  - 唯一主键(写法1)： `id INT KEY;`
  - 唯一主键(写法2)： `PRIMARY KEY(field);`
  - 复合主键： `PRIMARY KEY(field1,field2,...);` 可以指定若干个字段共同作为主键

- **`AUTO_INCREMENT`**: 自动增长，只能用于数值列，且必须配置索引使用。默认从 1 开始，步长 1 。
  - 示例： `id INT AUTO_INCREMENT KEY;`

- `UNIQUE KEY`: 该字段的值是唯一的索引， NULL 除外，与主键不同，可以给多个字段指定。

- **`FOREIGN KEY`**: 见 [下文](#外键约束) 。

### 创建表，并设置字段及其属性

`CREATE TABLE [IF NOT EXISTS] tab_name(field1 type1 [constraints1], field2 type2 [constraints2], ...]) [ENGINE=INNODB] [CHARSET=UTF8];`

关键字 **`CREATE TABLE`** ，示例：

``` sql
CREATE TABLE IF NOT EXISTS user(
  `id` INT AUTO_INCREMENT KEY   COMMENT '唯一ID',  # 用整型保存
  `username` VARCHAR(20)        COMMENT '用户名',  # 最长20位字符
  `password` CHAR(32)           COMMENT '密码',    # 最长32位字符
  `age` TINYINT(3) UNSIGNED     COMMENT '年龄',    # 位数最多3位，无符号
  `salary` FLOAT(8,2) ZEROFILL  COMMENT ' 薪资',   # 浮点数，总长8位，2位小数
  `married` TINYINT(1)          COMMENT '是否已婚', # 布尔类型
  `sex` ENUM('男','女','保密')   COMMENT '性别'     # 枚举类型
) ENGINE=INNODB CHARSET=UTF8;
```

上面的例子中，字段名都用 `` ` ``(反引号) 包裹，作用是避免字段名与 MySQL 的关键字冲突。

如果想复制某个表的所有字段（不包括内容），可以使用：

- `CREATE TABLE tab_name LIKE tab_name2;` ，示例的含义是，创建 `tab_name` ，并将 `tab_name2` 的字段复制到 `tab_name` ，但不包括内容

### 修改，字段的名称/数据类型/属性/默认值/位置、表的主键/索引

修改表都是用 **`ALTER TABLE`** 命令。

1. `ALTER TABLE tab_name CHANGE 原字段名 新字段名 字段类型 [完整性约束条件] [FIRST |AFTER 字段名称];`

    关键字 **`CHANGE`** 。此方法可以同时修改字段的**名称**/**数据类型**/**属性**/**位置**。示例如下：

    ``` sql
    # 将 user 表的 age 名称改为 userAge ，设置其默认值为 18 ，并将字段的位置放在第一列
    ALTER TABLE user CHANGE age userAge DEFAULT 18 FIRST;
    ```

2. `ALTER TABLE tab_name MODIFY 字段名 字段类型 [完整性约束条件] [FIRST |AFTER 字段名称];`

    关键字 **`MODIFY`** 。与上方法相比，**不能修改**字段**名称**。

3. `ALTER TABLE tab_name ALTER 字段名 SET DEFAULT 默认值;`

    关键字 **`ALTER ... SET DEFAULT`** 。修改(或添加)字段的**默认值**。

4. `ALTER TABLE tab_name ALTER 字段名 DROP DEFAULT;`

    关键字 **`ALTER ... DROP DEFAULT`** 。删除字段的**默认值**。

5. `ALTER TABLE tab_name ADD PRIMARY KEY(字段名);`

    关键字 **`ADD PRIMARY KEY`** 。添加**主键**。

6. `ALTER TABLE tab_name DROP PRIMARY KEY;`

    关键字 **`DROP PRIMARY KEY`** 。删除**主键**。

    注意：
    - 若该字段有 `AUTO_INCREMENT` 属性，需要先删除该属性。
    - 若表格原本就有主键，必须先把原来的主键删除。
    - 被移除主键的字段，默认保留 `NOT NULL` 属性。

7. `ALTER TABLE tab_name ADD UNIQUE KEY|INDEX [索引名](字段名[, 字段名2[, ....]]);`

    关键字 **`ADD UNIQUE INDEX`** 。添加**索引**，若忽略"索引名"参数，就以字段名作为索引名。示例如下：

    ``` sql
    # 在 user 表中，以字段 id 创建索引，索引名默认取 id
    ALTER TABLE user ADD UNIQUE INDEX (id);
    # 在 user 表中，以字段 id 创建名为 index_id 的索引
    ALTER TABLE user ADD UNIQUE INDEX index_id(id);
    # 在 user 表中，以字段 id 和 name 组合创建索引，索引名默认取 index_id2
    ALTER TABLE user ADD UNIQUE INDEX index_id2(id, name);
    ```

8. `ALTER TABLE tab_name DROP KEY|INDEX 索引名;`

    关键字 **`DROP INDEX`** 。删除**索引**。

### 其他数据表相关命令

| 命令                                                                               | 说明                                                                               |
|:-----------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------|
| `ALTER TABLE tab_name ADD 字段名 字段类型 [完整性约束条件] [FIRST |AFTER 字段名];` | **添加字段**                                                                       |
| `ALTER TABLE tab_name DROP 字段名;`                                                | **删除字段**                                                                       |
| `SHOW CREATE TABLE tab_name;`                                                      | 查看指定表的详细信息（如存储引擎、编码格式等）                                     |
| `DESC tab_name;`                                                                   | **查看表结构**（以表格形式显示所有字段的信息）                                     |
| `DESCRIBE tab_name;`                                                               |                                                                                    |
| `SHOW COLUMNS FROM tab_name;`                                                      |                                                                                    |
| `SHOW [FULL] TABLES [{FROM|IN} db_name] [LIKE ...];`                               | 模糊查询数据表名字                                                                 |
| `DELETE FROM tab_name;`                                                            | **清空**数据表（但`AUTO_INCREMENT`不会被还原）                                     |
| `TRUNCATE TABLE tab_name;`                                                         | **彻底清空**数据表（包括`AUTO_INCREMENT`也会被还原）                               |
| `DROP TABLE tab_name;`                                                             | **删除**数据表                                                                     |
| `ALTER TABLE tab_name AUTO_INCREMENT=1;`                                           | 将数据表的 `AUTO_INCREMENT` 设为 1                                                 |
| `ALTER TABLE tab_name RENAME [TO|AS] new_tab_name;`                                | 修改数据**表名称**                                                                 |
| `RENAME TABLE tab_name new_tab_name;`                                              | (`FIRST|AFTER` 表示在第一位(或某个字段后)插入该字段，省略此参数则在末尾添加字段。) |

</br>

## 数据的"增删改"

1. `INSERT [INTO] tab_name[(field1,field2,...)] VALUES(value1_1,value1_2,...)[, (value2_1,value2_2,...)[, ....]];`

    关键字 **`INSERT ... VALUE|VALUES`** 。对指定的数据表插入若干条数据。示例代码如下：

    ``` sql
    # 只插入一条记录。其中，第一个参数是 NULL 的原因是，该字段设置了 AUTO_INCREMENT
    INSERT user VALUE(null, "张三", "12345", 18 ,18000 , 1, "男");
    # 指定字段的插入方式
    INSERT user(password, username, age, salary, married, sex) VALUES("696969", "小乔", 19 ,12000 , 0, "女");
    # 同时插入多条记录
    INSERT user VALUES(null, "刘备", "666666", 22 ,21000 , 1, "男"), (null, "孙权", "ssssss", 22 ,21000 , 1, "男");
    ```

    注意：
    - `tab_name` 后面的字段可以省略，但后面的 `VALUE` 必须跟表格列一一对应。
    - 使用 `VALUES` 后面可以跟多条记录，用 `,`(逗号) 分隔。
    - 如果只插入一条记录， `VALUES` 可以替换成 `VALUE` 。

    插入单条数据的另一种写法： `INSERT tab_name SET field1=value1, field2=value2, ...;` 。示例代码如下：

    ``` sql
    INSERT user SET username="大乔", password="xxxxxx", age=18, salary=0, married=1, sex="女";
    ```

2. `DELETE FROM tab_name [WHERE 条件];`

    关键字 **`DELETE FROM`** 。其中 `WHERE` 是跟[查询条件](#数据的"查")有关。示例代码如下：

    ``` sql
    # 把 user 中 name 为 "小乔" 的记录都删除
    DELETE FROM user WHERE name="张三";
    ```

    注意，如果不加查询条件，会删除表的所有数据。

3. `UPDATE tab_name SET field1=value1[[, field2=value2], ...] [WHERE 条件];`

    关键字 **`UPDATE ... SET`** 。其中 `WHERE` 是跟[查询条件](#数据的"查")有关。示例代码如下：

    ``` sql
    # 把 user 中 id 为 5 的记录，修改指定字段的值
    UPDATE user SET name="大乔", salary=15000, married=0 WHERE id=5;
    ```

    注意，如果不加查询条件，会修改表的所有数据。

</br>

## 数据的"查"

`SELECT field1,field2,.... FROM tab_name [WHERE 条件] [GROUP BY {field|position} HAVING 二次筛选] [ORDER BY {field|position|expr} {ASC|DESC}] [LIMIT 限制结果集的条数];`

- `[WHERE 条件]`: 决定查询条件。
- `[GROUP BY {field|position} [HAVING 二次筛选]]`: 决定分组后的二次筛选。
- `[ORDER BY {field|position|expr} {ASC|DESC}]`: 决定排序。
- `[LIMIT 限制结果集的条数]`: 对筛选结果的截取（分页）。

### 别名

在一些复杂查询中，例如联表查询，不同表之间的字段可能相同，表的名字可能过长。这时我们可以指定别名：

- 给字段起别名： `SELECT field [AS] 别名, field2 [AS] 别名2, ... FROM db_name.tab_name;`
- 给数据表起别名： `SELECT field1,field2,... FROM db_name.tab_name [AS] 表别名;`

示例：

``` sql
# 字段起别名
SELECT id AS '编号', username AS '用户名' FROM test_db.test.tab;
# 表起别名 （对于多表联查很有用）
SELECT id,username FROM test_tab AS us;
```

关于[多表联查的内容](#多表联查)。

### WHERE

`WHERE` 后面可以跟 比较运算符号(`>`/`<`/`=`等)、范围查询(`BETWEEN ... AND`)、包含于集合(`IN(val1,val2,...)`)、逻辑运算符(`OR`/`AND`)、模糊查询(`LIKE`) 。

具体讲解：

- **比较运算符号**，使用 `>`、`>=`、`<`、`<=`、`=`、`!=`、`<>`、`<=>` 。

  示例：

  ``` sql
  SELECT username FROM test_tab WHERE id>=2;
  # 检测 NULL 值 （注意，"="不能检测NULL）
  SELECT username FROM test_tab WHERE desc<=>NULL;
  # 检测 NULL 值，方法二
  SELECT username FROM test_tab WHERE desc IS NULL;
  # 检测不为 NULL 的值
  SELECT username FROM test_tab WHERE desc IS NOT NULL;
  ```

- **范围查询**，使用 **`BETWEEN ... AND`** 。

  示例：

  ``` sql
  # 查询年龄在 18-20 岁的用户记录
  SELECT * FROM test_tab WHERE 18 AND 20;
  ```

- **包含于集合**的某个元素，使用 **`IN(value1,value2,....)`** 。

  示例：

  ``` sql
  # 查询 id 为 1，3，5 的记录
  SELECT * FROM test_tab WHERE id IN(1,3,5);
  ```

- **逻辑运算符**，使用 **`AND`** 、 **`OR`** 。简单理解就是将多个逻辑组合。

  示例：

  ``` sql
  # 查询用户名为 zhangsan 或 lisi ，且年龄为 18 岁
  SELECT * FROM test_tab WHERE username IN('zhangsan','lisi') AND age=18;
  ```

- **模糊查询**，使用 **`LIKE`** ，需要配合通配符使用，包括 **`%` 表示任意多个字符**； **`_` 表示任意一个字符**。

  示例：

  ``` sql
  # 查询用户名 "姓 zhang" 的记录
  SELECT * FROM test_tab WHERE username LIKE 'zhang%';
  # 查询用户名是三个字符的记录
  SELECT * FROM test_tab WHERE username LIKE '___';
  ```

  除了用于查询，还可以用于创建数据表：
  - `CREATE TABLE tab_name LIKE tab_name2;`： 创建 `tab_name` ，并将 `tab_name2` 的字段复制到 `tab_name` ，但不包括内容。

### GROUP BY

用于把值相同的放到一个组中，最终查询的结果只会显示组中的一条。**一般用于统计"某种分组"下的数量**。

``` sql
# 按年龄分组，统计每个年龄的数量
SELECT age ,COUNT(*) FROM test_tab GROUP BY age;
```

如果想把组的某个字段的值全部列出来显示，使用 `GROUP_CONCAT` 包括该字段，示例如下：

``` sql
# username 字段会全部列出，以逗号分隔
SELECT GROUP_CONCAT(username),age,desc FROM test_tab GROUP BY age;
```

**聚合函数**有 `COUNT()`(统计数量)、`SUM()`(求和)、`MIN()`(求最小值)、`MAX()`(求最大值)、`AVG()`(求平均) 。

用法都是类似的，下面以 `COUNT()` 为例：（注意，`COUNT()`统计某个字段时， `NULL` 值不被统计）

``` sql
# 获取查询到的记录的条数，并命名为 total 字段
SELECT COUNT(*) AS total FROM test_tab WHERE id=1;
# 被统计的数量，是不包括 NULL 值的
SELECT COUNT(desc) FROM test_tab GROUP BY age;
```

配合 `WITH ROLLUP` 使用，会在记录末尾添加一条记录，是上面所有记录的总和。

使用 `HAVING` 对查询结果进行二次筛选：

``` sql
# 对查询结果中， total 大于 3 的记录
SELECT age,COUNT(*) AS total
FROM test_tab
GROUP BY age
HAVING total>=3;
```

### ORDER BY

`ORDER BY field [{ASC|DESC}]` 对结果进行排序，**默认是 `ASX`(升序)** 。

示例：

``` sql
# 按照年龄进行降序排序
SELECT * FROM test_tab ORDER BY age DESC;
```

如果想随机排序，可以配合使用 `RAND()` ：

``` sql
# 每次执行这语句得到的顺序都不一定相同
SELECT * FROM test_tab ORDER BY RAND();
```

### LIMIT

`LIMIT n` 只显示结果的前 n 条记录；

`LIMIT offset,row_count` 显示从 `offset+1` 开始，共显示 `row_count` 条记录。

常用于"**分页**"的查询。

示例：

``` sql
# 显示结果的前 3 条
SELECT * FROM test_tab LIMIT 3;
# 显示从第 4 条开始，一共显示 3 条
SELECT * FROM test_tab LIMIT 3,3;
```

`LIMIT` 同样能用于 `UPDATE` 中。

</br>

# 多表联查

## 笛卡尔积

用的比较少。效果就是多个表之间相互组合，例如联查表A、B，分别有 3 和 4 条记录，最后查询的结果会有 3*4 条记录。

`SELECT tab_name1.id, tab_name1.username, tab_name2.desc, ... FROM tab_name1, tab_name2, ...;`

## 内连接

用的比较多。查询两个表中，符合连接条件的记录。使用关键字 **`INNER JOIN ... ON ...`** 。

注意，连表不会超过 3 个，否则效率会很低。

`SELECT field1,field2,... FROM tab_name1 INNER JOIN tab_name2 ON 连接条件 [INNER JOIN tab_name2 ON 连接条件2 ....];`

示例：

``` sql
# test_tab 表有 innerID 字段用于连接 test_tab2
SELECT a.id,a.username,b.desc
FROM test_tab AS a
INNER JOIN test_tab2 AS b
ON a.innerID=b.id;
```

需要注意的是，内连接查询，**会查询到符合连接条件的记录**，简单说就是，求**交集**。

例如，对于 A 表中的 id 在 B 表中不存在的情况，记录是不会被查询到的。

## 外连接

分为左外连接和右外连接。

`SELECT field1,field2,... FROM tab_name1 OUTER {LEFT|RIGHT} JOIN tab_name2 ON 条件 [OUTER {LEFT|RIGHT} JOIN tab_name2 ON 条件 ...];`

对于左外连接，会先查询左边中的全部记录，然后再去右表中查询符合的记录，不符合的以 `NULL` 填充。而右外连接刚好相反。

示例：

``` sql
# 左外连接， test_tab 的记录会全部被查到，只会将符合条件的 test_tab 的数据填充到 test_tab 中
SELECT a.id,a.username,b.desc
FROM test_tab AS a
OUTER LEFT JOIN test_tab2 AS b
ON a.innerID=b.id;
```

## 外键约束

只有 InnoDB 存储引擎支持**外键(`FOREIGN KEY`)**约束。

外键是为了保证多个表之间的数据的一致性和完整性。

下面描述中，存放外键字段的表称为"子表"；被外键参照的表称为"父表"。

- 建表时创建外键：

  ``` sql
  CREATE TABLE tab_name(
    ...
    # 这个外键也不是必须创建，FOREIGN KEY会自动创建
    cateId TINYINT UNSIGNED NOT NULL,
    # 指定外键（参照cate_tab表的主键id）
    FOREIGN KEY(cateId) REFERENCES cate_tab(id);
  ) ...;
  ```

- 动态删除外键： `ALTER TABLE tab_name DORP FOREIGN KEY fk_name;` 。

- 动态创建外键： `ALTER TABLE tab_name ADD FOREIGN KEY(fk_name) REFERENCES fk_tab_name(prim_key);` 。注意，假如外键字段已经存在，但数据的值存在"脏值"，是不会创建成功的。

注意：

- 父表提供给外键的字段 必须是该表的**主键**。
- 父表必须先创建。
- 子表的外键字段 与 父表的主键字段 **类型必须相同**，如果是数值型，无符号也要一致；如果字符型，长度可以不同。
- 子表的外键字段必定是索引字段。
- 父表中"被使用"的记录不能删除或修改。

外键约束的参照操作，语法是 `ON {DELETA|UPDATE} [下面的条件之一]` ：

- `CASCADE`: 级联操作，从父表删除或更新时，子表也会删除或更新对应的行。
- `SET NULL`: 从父表删除或更新记录，会将子表的外键设为 `NULL` （前提需要把外键设置为可空）。
- `NO ACTION|RESTRICT`: 默认值，拒绝对父表做删除或更新操作。
- 示例：

  ``` sql
  ALTER TABLE test_tab
  ADD FOREIGN KEY (fk_name) REFERENCES fk_tab_name(prim_key)
  ON DELETE CASCADE # 删除时会进行级联操作
  ON UPDATE CASCADE;
  ```

</br>

# 特殊形式的查询

## 子查询

简单说就是，查询语句嵌套在另一个查询语句中，内层查询的结果作为外层查询的数据。

内层查询用 `()` 包裹。

- 使用 **`IN`** 的子查询示例： （**`NOT IN`**则与之相反）

    ``` sql
    # 从tab_name2表冲查询到所有id的集合
    # 然后查询tab_name的tab_id包含于"集合"的记录
    SELECT * FROM tab_name
    WHERE tab_id IN
    # 下面查询到的结果类似 (id1,id2,id3,...)
    (SELECT tab2_id FROM tab_name2);
    ```

- 使用比较运算符的子查询示例：

    ``` sql
    # 从"等级"表查询等级 1 的分数是多少
    # 然后从"学生"表查询符合"等级1"的学生记录
    SELECT * FROM student_tab
    WHERE score >=
    (SELECT score FROM leave_tab WHERE id=1);
    ```

- 使用 `EXISTS` 的子查询示例： （`EXISTS`后面跟一个布尔类型的值，决定它前面语句是否执行）

    ``` sql
    # 当tab_name2包含id=666的记录
    # 则查询tab_name1的所有记录
    SELECT * FROM tab_name1
    WHERE EXISTS
    # 当此语句没有查询到结果，会返回 false
    (SELECT * FROM tab_name2 WHERE id=666);
    ```

- 比较运算符与 `ANY`/`SOME`、`ALL` 配合： （`ANY`是一样效果`SOME`）

    ``` sql
    # 从 leave_tab 查询所有分数
    # 再从 student_tab 查询分数 至少大于上面集合的最小值 的记录 （就是只有一个符合即可）
    SELECT * FROM student_tab WHERE score>=ANY(SELECT score FROM leave_tab);
    # (student_tab 查询的分数必须 大于集合的最大值)
    SELECT * FROM student_tab WHERE score>=ALL(SELECT score FROM leave_tab);
    ```

| 运算符     | ANY    | SOME   | ALL    |
|:-----------|:-------|:-------|:-------|
| `>`、`>=`  | 最小值 | 最小值 | 最大值 |
| `<`、`<=`  | 最大值 | 最大值 | 最小值 |
| `=`        | 任意值 | 任意值 |        |
| `<>`、`!=` |        |        | 任意值 |

两个简单示例：

- `INSERT tab_name(field1,field2,...) SELECT field1,field2,... FROM tab_name2;`： 查询 `tab_name2` 中的特定字段数据，并将其插入到 `tab_name2` 的相同字段中。
- `CREATE TABLE tab_name(field1,field2,...) SELECT field1,field2,... FROM tab_name2;`： 创建 `tab_name` ，并将 `tab_name2` 的特定字段查询并复制到 `tab_name` 的相同字段中。

## 查询合并

使用的是 `UNION` 或 `UNION ALL` ，表示将两个或以上的记录合并在一起。

``` sql
# 将 user_tab 和 user_tab2 查询到的 username 去重显示
SELECT username FROM user_tab
UNION
SELECT username FROM user_tab2;

# 而 UNION ALL 不会去重，就只是把两个查询结果合并在一起
SELECT username FROM user_tab
UNION ALL
SELECT username FROM user_tab2;
```

## 自身连接查询

无限级分类的实现形式。就是表自身查询连接自身。

观察如下数据表：（表名是 `cate` ）

| id |  cateName  | pId |
|:--:|:----------:|:---:|
| 1  |    服装    |  0  |
| 2  |    数码    |  0  |
| 3  |    玩具    |  0  |
| 4  |    男装    |  1  |
| 5  |    女装    |  1  |
| 6  |    内衣    |  1  |
| 7  |    电视    |  2  |
| 8  |    冰箱    |  2  |
| 9  |   洗衣机   |  2  |
| 10 |   爱马仕   |  3  |
| 11 |     LV     |  3  |
| 12 |   GUCCI    |  3  |
| 13 |    夹克    |  4  |
| 14 |    衬衫    |  4  |
| 15 |    裤子    |  4  |
| 16 |  液晶电视  |  7  |
| 17 | 等离子电视 |  7  |
| 18 |  背投电视  |  7  |

表中包含了多种分类，而分类之间也有包含关系，例如"男装"、"女装"、"内衣"都属于父级分类 id 为 1 的分类，即服装。而"服装"、"数码"、"玩具"都是顶级分类。

如下示例展示了，查询当前分类的父类：

``` sql
SELECT p.id,p.cateName AS pCateName, c.cateName AS cCateName
# 子表是自身，命名为 c
FROM cate AS c
# 主表也是自身，命名为 p
OUTER LEFT JOIN cate AS p
# 查询子表的 pId 与 父表的 id 相同的数据
ON c.pId=p.id;
```

</br>

## 常用函数

| 分类     | 命令                                                                  | 说明                      | 备注                                         |
|:---------|:----------------------------------------------------------------------|:--------------------------|:---------------------------------------------|
| 数学函数 | `SELECT AVG(field);`                                                  | 平均值                    | `field` 表示被计算的字段                     |
|          | `SELECT COUNT(field);`                                                | 行数                      | 同上                                         |
|          | `SELECT MAX(field);`                                                  | 最大值                    | 同上                                         |
|          | `SELECT MIN(field);`                                                  | 最小值                    | 同上                                         |
|          | `SELECT SUM(field);`                                                  | 总和                      | 同上                                         |
| 字符串   | `SELECT CHAR_LENGTH("你好啊");            # 3`                        | 获取字符串长度            |                                              |
|          | `SELECT LENGTH("你好啊");                # 9`                         | 获取字节长度              |                                              |
|          | `SELECT CONCAT("你","好","啊");           # "你好啊"`                 | 拼接字符串                | 若参数含有`NULL`,结果一定是`NULL`            |
|          | `SELECT CONCAT("你",NULL,"啊");          # NULL`                      |                           |                                              |
|          | `SELECT CONCAT_WS("、","你","好","啊");   # "你、好、啊"`             | 以特定分隔符拼接字符串    | 第一个参数是分隔符                           |
|          | `SELECT UPPER("HellO");                  # "HELLO"`                   | 转换成大写                |                                              |
|          | `SELECT UCASE("HellO");                  # "HELLO"`                   |                           |                                              |
|          | `SELECT LOWER("HellO");                  # "hello"`                   | 转换成小写                |                                              |
|          | `SELECT LCASE("HellO");                  # "hello"`                   |                           |                                              |
|          | `SELECT REVERSE("ABCD");                 # "DCBA"`                    | 反转字符串                |                                              |
|          | `SELECT LEFT("hello",2);                 # "he"`                      | 返回前/后的 n 个字符      |                                              |
|          | `SELECT RIGHT("hello",2);                # "lo"`                      |                           |                                              |
|          | `SELECT LPAD("abc",5,"0");               # "00abc"`                   | 字符串补全到特定长度      |                                              |
|          | `SELECT RPAD("abc",5,"0");               # "abc00"`                   |                           |                                              |
|          | `SELECT TRIM("  ABC ");                  # "ABC"`                     | 去掉字符串的空格          | 两端                                         |
|          | `SELECT LTRIM("  ABC ");                 # "ABC "`                    |                           | 左端                                         |
|          | `SELECT RTRIM("  ABC ");                 # "  ABC"`                   |                           | 右端                                         |
|          | `SELECT REPEAT("nb",3);                  # "nbnbnb"`                  | 将字符串重复 n 次         |                                              |
|          | `SELECT REPLACE("hello!", "hel", "Hel"); # "Hello!"`                  | 字符串替换                |                                              |
|          | `SELECT SUBSTRING("ABCDEFG", 2, 3);      # "BCD"`                     | 截取部分字符串            | 参数分别是：字符串，从第几个开始，截取多少位 |
|          | `SELECT STRCMP("A","B");                 # -1`                        | 比较字符串                | 结果可能是 `-1` 、 `0` 、 `1`                |
| 日期     | `SELECT CURDATE();`/`SELECT CURRENT_DATE();  # "2020-07-11"`          | 日期                      |                                              |
|          | `SELECT CURTIME();`/`SELECT CURRENT_TIME();  # "23:31:33"`            | 时间                      |                                              |
|          | `SELECT NOW();`/`SELECT SYSDATE();           # "2020-07-11 23:31:33"` | 日期时间                  |                                              |
|          | `SELECT CURRENT_TIMESTAMP();`                                         |                           |                                              |
|          | `SELECT MONTH("2020-07-11");                 # "7"`                   | 月份                      |                                              |
|          | `SELECT MONTHNAME("2020-07-11");             # "July"`                | 月份名称                  |                                              |
|          | `SELECT DAYOFWEEK("2020-07-11");             # "7"`                   | 星期几的数值              | 从周日开始，值为 1                           |
|          | `SELECT DAYNAME("2020-07-11");               # "Saturday"`            | 星期几的名称              |                                              |
|          | `SELECT WEEK("2020-07-11");                  # "27"`                  | 第几个周                  |                                              |
|          | `SELECT DATEDIFF("2020-07-11","2020-07-10"); # "1"`                   | 两个日期相差的天数        |                                              |
|          | `SELECT YEAR(NOW()), MONTH(NOW()), DAY(NOW());`                       | 年、月、日                |                                              |
|          | `SELECT HOUR(NOW()), MINUTE(NOW()), SECOND(NOW());`                   | 时、分、秒                |                                              |
| 其他     | `SELECT VERSION();           #  8.0.20`                               | 数据库版本号              |                                              |
|          | `SELECT CONNECTION_ID();     # 11`                                    | 当前连接数据库的 ID       |                                              |
|          | `SELECT USER();`/`SELECT CURRENT_USER();     # root@loaclhost`        | 当前登录的用户            |                                              |
|          | `SELECT SYSTEM_USER();`/`SELECT SESSION_USER();`                      |                           |                                              |
|          | `SELECT SCHEMA();`/`SELECT DATABASE();       # test_db`               | 当前打开的数据库          |                                              |
|          | `SELECT LAST_INSERT_ID();    # 1`                                     | `AUTO_INCREMENT` 最后的值 |                                              |
|          | `SELECT MD5("HELLO");        # eb61eead90e3b899c6bcbe27ac581660`      | 生成 md5 加密后的的字符串 | 32位                                         |
|          | `SELECT FIRST(field);`                                                | 第一个记录的值            | `field` 表示被统计的字段                     |
|          | `SELECT LAST(field);`                                                 | 最后一个记录的值          | 同上                                         |

</br>

## 其他命令

| 命令             | 说明                 |
|:-----------------|:---------------------|
| `SHOW WARNINGS;` | 查看上一句代码的警告 |
|                  |                      |

</br>

# Workbench(图形化管理工具)

[下载地址](https://dev.mysql.com/downloads/workbench/) 。

如果连接时出现关于时区的错误，请设置连接参数 `serverTimezone=UTC` 。



