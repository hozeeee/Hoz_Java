
# IntelliJ IDEA

## 常用快捷键

代码补全：

- **`Alt + Enter`**:      快速添加 `try...catch` 或 `throws` 。
- **`Alt + Ins`**:        快速生成面板，包括 Getter 、 Setter 、构造函数、重写 `equals()` 、 `hasCode()` 、 `toString()` 。
- `Ctrl + Shift + Enter`: 自动完成当前行。例如自动添加 `;` 或者自动换行。

查找替换：

- **`Ctrl + Shift + F/R`**: **全局**查找/替换。
- `Ctrl + F/R`:             当前文件查找/替换。
- **`Ctrl + N`**:           搜索类。
- `Ctrl + Shift + N`:       文件查找面板。
- **`Ctrl + Shift + A`**:   Find Action 。搜索功能及快捷键。

还原/撤销：

- `Ctrl + Z`:         撤销。
- `Ctrl + Shift + Z`: 还原撤销。

注释：

- `Ctrl [+ Shift] + /`:     行注释，如果加上Shift表示仅对光标选中部分注释。
- `Ctrl + Alt + Shift + J`: 进入列操作。自动选中多行的相同位置进行同时修改。

代码快速定位：

- `Ctrl [+ Shift] + E`: 最近访问(编辑)的文件列表。
- `Ctrl + Shift + 1~9`: 创建书签。就是对指定行添加标签，以便于快速访问。
- `Shift + F11`:        查看书签。
- `Ctrl + 1~9`:         快速切换书签。
- `Alt + ←/→`:          切换 tab 标签。
- `Ctrl + G`:           跳转到指定行。

运行与调试：

- `Shift + F9`:        调试。
- `Shift + F10`:       运行。
- `F8`:                单步运行。
- `F9`:                恢复运行至下一个断点。
- `Shift + Ctrl + F8`: 查看所有断点。

其他：

- `Ctrl + Alt + L`: 格式化代码。
- **`Shift + F6`**: 重命名。

</br>

## Live Templates

常用的有：

Live Templates 指的是，输入一定的简写内容，然后按 Tab 键后，自动补全代码。

- `psvm`: `public static void main()`
- `sout`: `System.out.printIn()`
- `psfs`: `public static final String`
- `fori`: for(int i) 循环
- `itli`: List 迭代

添加自定义模板：

1. 点击顶部菜单栏 File –> 点击 Setting 打开面板
2. 选择 Editor -> Live Templates
3. 然后自由发挥

</br>

## 操作备忘

- 修改快捷键：

  1. 菜单栏 File -> Settings
  2. 选择 Keymap
  3. 搜索 `reformat code`(格式化代码)、`redo`(还原撤销)
  4. 双击或右键选择添加快捷键

- 为 Maven 全局指定阿里仓库：

  1. 找到 IDEA 的安装目录，打开 `IntelliJ IDEA 2020.1.1/plugins/maven/lib/` 文件夹
  2. 该文件夹下 `maven2` 或 `maven3` ，打开其中的 `conf/settings.xml`
  3. 在 `<mirrors>` 标签中添加如下代码：

      ``` xml
      <mirror>  
        <id>alimaven</id>  
        <name>aliyun maven</name>  
        <url>http://maven.aliyun.com/nexus/content/groups/public/</url>  
        <mirrorOf>central</mirrorOf>
      </mirror>
      ```

  4. 在 IDEA 的菜单栏 File -> Settings
  5. 搜索 Maven ，修改 `Maven home directory` 的地址为刚才修改的 Maven 的目录

- 使用自带的 Database 客户端面板：

  1. 打开项目后，默认在界面的最右边有 "Database" 面板，点击展开面板
  2. 在面板的左上角有 "+" 号，选择 "Data Source" -> "MySQL" (其他数据库同理)
  3. 填写数据库连接的信息即可。
  4. 需要注意的是，如果第一次登陆这类数据库，底部会有 "Download missing driver files" 的提醒，需点击下载驱动。
  5. 右键 "schemas" ，可以选择 "Run SQL Script..." 可以执行指定 SQL 文件。
