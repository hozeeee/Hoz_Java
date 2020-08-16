
# XML

XML(EXtensible Markup Language), 可扩展标记语言，与 HTML 非常类似，具有良好的人机可读性。

XML 与 HTML 的区别：

- XML 没有预定义标签，但 HTML 有。
- XML 重在保存和传输数据， HTML 用于显示信息。

XML 的常见用途：

- Java 程序的配置文件。
- 用于保存程序运行期间产生的数据。
- 网络间数据的传输。

XML 文档结构：

- 第一行必须是 "XML 声明"，用于说明文档的基本信息。
- **根节点**有且只有一个。（除了DTD）
- XML 标签的书写规则与 HTML 相同。

合法的标签名：

- 标签名要有意义。
- 标签名不要使用中文和特殊字符。
- 标签名必须是**小写**，多个单词之间用 **`-`** 分割。
- 建议多级标签之间不要存在重名的情况。

常见的 XML **转义字符**：

| 实体引用 | 对应符号 | 说明          |
|:---------|:---------|:--------------|
| `&lt;`   | `<`      | 小于/左尖括号 |
| `&gt;`   | `>`      | 大于/右尖括号 |
| `&amp;`  | `&`      | &             |
| `&apos;` | `'`      | 单引号        |
| `&quot;` | `"`      | 双引号        |

</br>

CDATA 标签：

- CDATA 标签用于存放不应由 XML 解析器进行解析的文本数据。
- 以 `<!CDATA[` 开始，以 `]]>` 结束，中间书写不被解析的文本信息。
- 示例： `<!CDATA[ <test>12<23></body> ]]>` 。

</br>

**DTD**(Document Type Definition, 文档类型定义)：

- DTD 是一种简单易用的语义约束方式，扩展名为 `.dtd` 。
- DTD 也是属于 XML 文档，首行也必须是 `<?xml version="1.0" encoding="UTF-8"?>` 。
- 下面给出简单几个例子：
  - `<!ELEMENT parent (sub)>` 用于描述父子节点的**包含及数量关系**：
    - `<!ELEMENT root-node (sub-node)>` 表示 `root-node` 节点中，只能有一个 `sub-node` 节点，其他部分是固定写法。
    - 把上面的 `(sub-node)` 改成 `(sub-node+)` 表示**至少有一个**。
    - 把上面的 `(sub-node)` 改成 `(sub-node*)` 表示**有零个或多个**。
    - `<!ELEMENT student (name, age, sex)>` 表示 `student` 节点依次包含 `name`, `age`, `sex` 节点。
    - `<!ELEMENT name (#PCDATA)>` 表示 `name` 节点是一个纯文本节点。
  - `<!ATTLIST>`
    - `<!ATTLIST my-node att1 CDATA "">` 表示 `my-node` 节点的 `att1` 属性类型是 `CDATA` ，其的默认值是空字符串。
  - `<!ATTLIST>` 和 `<!ELEMENT>` 都可以书写任意多行。
- 在 XML 中使用 `<!DOCTYPE>` 标签引用 DTD 文件：
  - `<!DOCTYPE root-node SYSTEM "my.dtd">`

</br>

**XML Schema**：

- XML Schema 比 DTD 更为复杂，但也提供了更多功能，如数据类型、格式限定、数据范围等特性。它也是 W3C 的标准。扩展名为 `.xsd` 。
- XML Schema 也是属于 XML 文档，首行也必须是 `<?xml version="1.0" encoding="UTF-8"?>` 。
- 根节点必须是 `<schema xmlns="http://www.w3.org/2001/XMLSchema"></schema>` 。
- 下面给出简单的示例代码：

  ``` xml
  <?xml version="1.0" encoding="UTF-8"?>
  <!-- xmlns="http://www.w3.org/2001/XMLSchema" 是固定需要写的 -->
  <schema xmlns="http://www.w3.org/2001/XMLSchema">
    <!-- 描述根节点名为 student -->
    <element name="student">
      <!-- complexType 表示 student 内部是复杂节点，即包含子节点是必须使用此标签包裹 -->
      <complexType>
        <!-- sequence 标签表示子标签的 顺序 是严格按照下面的定义 -->
        <sequence>
          <!-- 描述 student 标签内的 name, age, sex 子标签。重点是，可以指定数据类型 -->
          <element name="name" type="string"></element>
          <element name="age">
            <!-- simpleType 用于描述简单类型的子节点，一般用于对值范围的限定 -->
            <simpleType>
              <!-- 下面表示只能是 18~65 的整数类型 -->
              <restriction base="integer">
                <minInclusive value="18"></minInclusive>
                <maxInclusive value="65"></maxInclusive>
              </restriction>
            </simpleType>
          </element>
          <element name="sex" type="integer"></element>
          <!-- 可选属性 minOccurs 和 maxOccurs 用于描述节点可以出现的数量 -->
          <element name="addr" type="string" minOccurs="1" maxOccurs="10"></element>
          <!-- 如果还有嵌套的子节点，继续包裹 complexType 标签即可，这里就不演示了 -->
        </sequence>
        <!-- attribute 用于描述 student 的属性 desc ，其数据类型为字符串， use="required" 表示该属性是必填的 -->
        <attribute name="desc" type="string" use="required">
        </attribute>
      </complexType>
    </element>
  </schema>
  ```

- 在 XML 中使用 `<!DOCTYPE>` 标签引用 XML Schema 文件，需要在根节点的属性上指定：

  ``` xml
  <my-root-node
    xmls:xis="http://www.w3c.org/2001/XMLSchema-instance"
    xsi:noNamespaceSchemaLocation="my.xsd"
  >
    ...
  </my-root-node>
  ```

</br>

Dom4j 是一个用于解析 XML 的 Java 库。具体使用略。
