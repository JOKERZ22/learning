### ETL

&emsp;&emsp;Extract-Transform-Load的缩写，即数据抽取、转换、装载的过程

* 抽取(Extract)

&emsp;&emsp;将数据从各种原始的业务系统中读取出来，以便为随后的步骤提供数据

* 转换(Transform)

&emsp;&emsp;任何对数据的处理过程都是转换。这些处理过程通常包括（但不限于）下面一些操作：

&emsp;&emsp;1. 数据清洗（过滤不完整的数据、错误的数据、重复的数据）；

&emsp;&emsp;2. 根据规则验证数据；

&emsp;&emsp;3. 根据处理后的数据计算派生值和聚集值；

* 加载(Load)

&emsp;&emsp;将数据加载到目标系统的所有操作

### 介绍

&emsp;&emsp;Kettle(Pentaho Data Integration)是一款开源的Java编写的ETL工具，可以在Window、Linux、Unix上运行，绿色无需安装，数据抽取高效稳定。支持100%无编码、拖拽方式开发ETL数据管道；可对接包括传统数据库、文件、大数据平台、接口、流数据等数据源；支持ETL数据管道加入机器学习算法

&emsp;&emsp;Kettle 中文名称叫水壶，该项目的主程序员MATT 希望把各种数据放到一个壶里，然后以一种指定的格式流出


#### 下载&安装

[官网](http://community.pentaho.com/projects/data-integration/)
[GitHub](https://github.com/pentaho/pentaho-kettle)
[第三方](https://sourceforge.net/projects/pentaho/files/)

&emsp;&emsp;Java开发，依赖JDK，注意Kettle版本与JDK版本对应关系！ 
&emsp;&emsp;本文环境：Windows&emsp;&emsp; JDK1.8.0_291&emsp;&emsp; pdi-ce-9.1.0.0-324

#### 脚本

* 转换（Transform，.ktr）：完成针对数据的基础转换；

* 作业（Job，.kjb）：完成整个工作流的控制；

##### 区别 ：

&emsp;&emsp;1. 作业是步骤流，转换是数据流；

&emsp;&emsp;2. 作业的每一个步骤，必须等到前面的步骤跑完，后面的步骤才会执行；而转换会一次性把所有控件全部先启动（一个控件对应一个线程），然后数据流会从第一个控件开始，一条记录、一条记录地流向最后的控件；

#### 核心组件

* 勺子（Spoon.bat/spoon.sh）：一个图形化界面，可以让开发人员用图形化的方式开发转换和作业；
* 煎锅（Pan.bat/pan.sh）：用命令行的方式调用转换；
* 厨房（Kitchen.bat/kitchen.sh）：用命令行的方式调用作业；
* 菜单（Carte.bat/carte.sh）：一个轻量级的Web容器，用于建立专用、远程的ETL Server；

#### 目录结构

![Kettle目录](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525091359.png)

![Kettle文件](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525091444.png)

#### 概念模型

![Kettle概念模型](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525090916.png)

#### 核心概念

* 转换（Transaformation）

&emsp;&emsp;转换(transaformation)是ETL解决方案中最主要的部分，它处理抽取、转换、加载各种对数据行的操作

&emsp;&emsp;转换包含一个或多个步骤(step)，如读取文件、过滤数据行、数据清洗或将数据加载到数据库

&emsp;&emsp;转换里的步骤通过跳(hop)来连接，跳定义一个单向通道，允许数据从一个步骤向另一个步骤流动

&emsp;&emsp;在Kettle里，数据的单位是行，数据流就是数据行从一个步骤到另一个步骤的移动

&emsp;&emsp;数据流有的时候也被称之为记录流

* 步骤（Step）

&emsp;&emsp;步骤（控件）是转换里的基本的组成部分

&emsp;&emsp;一个步骤有如下几个关键特性：

&emsp;&emsp;① 步骤需要有一个名字，这个名字在转换范围内唯一；

&emsp;&emsp;② 每个步骤都会读、写数据行(唯一例外是“生成记录”步骤，该步骤只写数据)；

&emsp;&emsp;③ 步骤将数据写到与之相连的一个或多个输出跳，再传送到跳的另一端的步骤；

&emsp;&emsp;④ 大多数的步骤都可以有多个输出跳。一个步骤的数据发送可以被被设置为分发和复制，分发是目标步骤轮流接收记录，复制是所有的记录被同时发送到所有的目标步骤；

* 跳（Hop）

&emsp;&emsp;跳就是步骤之间带箭头的连线，跳定义了步骤之间的数据通路

&emsp;&emsp;跳实际上是两个步骤之间的被称之为行集的数据行缓存（行集的大小可以在转换的设置里定义）

&emsp;&emsp;当行集满了，向行集写数据的步骤将停止写入，直到行集里又有了空间

&emsp;&emsp;当行集空了，从行集读取数据的步骤停止读取，直到行集里又有可读的数据行

* 数据行

&emsp;&emsp;数据类型

&emsp;&emsp;&emsp;&emsp;数据以数据行的形式沿着步骤移动。一个数据行是零到多个字段的集合，字段包含下面几种数据类型：

&emsp;&emsp;&emsp;&emsp;① String:字符类型数据；

&emsp;&emsp;&emsp;&emsp;② Number:双精度浮点数；

&emsp;&emsp;&emsp;&emsp;③ Integer:带符号长整型（64位）；

&emsp;&emsp;&emsp;&emsp;④ BigNumber:任意精度数据；

&emsp;&emsp;&emsp;&emsp;⑤ Date:带毫秒精度的日期时间值；

&emsp;&emsp;&emsp;&emsp;⑥ Boolean:取值为true和false的布尔值；

&emsp;&emsp;&emsp;&emsp;⑦ Binary:二进制字段可以包含图像、声音、视频及其他类型的二进制数据；

&emsp;&emsp;元数据

&emsp;&emsp;&emsp;&emsp;每个步骤在输出数据行时都有对字段的描述，这种描述就是数据行的元数据

&emsp;&emsp;&emsp;&emsp;通常包含下面一些信息：

&emsp;&emsp;&emsp;&emsp;① 名称：行里的字段名应用是唯一的；

&emsp;&emsp;&emsp;&emsp;② 数据类型：字段的数据类型；

&emsp;&emsp;&emsp;&emsp;③ 格式：数据显示的方式，如Integer的#、0.00；

&emsp;&emsp;&emsp;&emsp;④ 长度：字符串的长度或者BigNumber类型的长度；

&emsp;&emsp;&emsp;&emsp;⑤ 精度：BigNumber数据类型的十进制精度；

&emsp;&emsp;&emsp;&emsp;⑥ 货币符号：￥；

&emsp;&emsp;&emsp;&emsp;⑦ 小数点符号：十进制数据的小数点格式。不同文化背景下小数点符号是不同的，一般是点（.）或逗号（，）；

&emsp;&emsp;&emsp;&emsp;⑧ 分组符号：数值类型数据的分组符号，不同文化背景下数字里的分组符号也是不同的，一般是点（.）或逗号（，）或单引号（’）；

* 并行

&emsp;&emsp;跳的这种基于行集缓存的规则允许每个步骤都是由一个独立的线程运行，这样并发程度最高。这一规则也允许数据以最小消耗内存的数据流的方式来处理。在数据仓库里，我们经常要处理大量数据，所以这种并发低消耗内存的方式也是ETL工具的核心需求

&emsp;&emsp;对于kettle的转换，不可能定义一个执行顺序，因为所有步骤都以并发方式执行：当转换启动后，所有步骤都同时启动，从它们的输入跳中读取数据，并把处理过的数据写到输入跳，直到输入跳里不再有数据，就中止步骤的运行。当所有的步骤都中止了，整个转换就中止了（要与数据流向区分开）

&emsp;&emsp;如果你想要一个任务沿着指定的顺序执行，那么就要使用后面所讲的“作业”！

![核心概念](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525101907.png)

#### 集群搭建

[kettle集群搭建](https://www.cnblogs.com/liuruilongdn/p/13809657.html)

### 使用

#### 资源库

&emsp;&emsp;在实际的团队开发过程中，不可能将自己的转换、作业和调度等配置存放在自己的电脑中。而Kettle的资源库正可以将我们的转换、作业等存储下来，构成一种协作平台

Kettle支持连接的资源库类型有三种：

&emsp;&emsp;1. Pentaho Repository：使用的是Kettle公司的服务器，一般公司不会使用；

&emsp;&emsp;2. Database Repository：保存在数据库的资源库，通过用户名密码访问；

&emsp;&emsp;3. File Repository：保存在硬盘的资源库，不用密码就可以使用；

##### 使用数据库资源库

[Kettle数据库资源库](https://www.staroon.dev/2018/07/18/KettleDoc-4/)


#### 输入控件

&emsp;&emsp;输入：就是用来抽取数据或生成数据

&emsp;&emsp;是ETL操作的E

##### 文件输入（CSV/文本/Excel）



###### CSV

![CSV输入](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525112610.png)

###### 文本

![文本输入1](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525133736.png)

![文本输入2](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525133845.png)

![文本输入3](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525133925.png)

###### Excel

![Excel输入1](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525134246.png)

![Excel输入2](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525134653.png)

###### 多文件合并

![多文件合并](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525135324.png)

##### Get data from XML

###### XML概念

&emsp;&emsp;XML 指可扩展标记语言（EXtensible Markup Language）, XML 被设计用来传输和存储数据

&emsp;&emsp;XPath即为XML路径语言（XML Path Language），它是一种用来确定XML文档中某部分位置的语言

&emsp;&emsp;XPath基于XML的树状结构，提供在数据结构树中找寻节点的能力

&emsp;&emsp;XPath-语法路径表达式

| 表达式   | 描述                                                     |
| -------- | -------------------------------------------------------- |
| nodename | 选取此节点的所有子节点                                   |
| /        | 从根节点选取                                             |
| //       | 从匹配选择的当前节点选择文档中的节点，而不考虑它们的位置 |
| .        | 选择当前节点                                             |
| ..       | 选择当前节点的父节点                                     |
| @        | 选取属性                                                 |

&emsp;&emsp;XPath-语法路径表达式示例

| 路径表达式      | 结果                                                         |
| --------------- | ------------------------------------------------------------ |
| bookstore       | 选取bookstore元素的所有子节点                                |
| /bookstore      | 选取根元素boolstore（假如路径起始于正斜杠“/”，则此路径始终代表到某元素的绝对路径） |
| bookstore/book  | 选取属于bookstore的子元素的所有book元素                      |
| //book          | 选取所有book子元素，而不管它们在文档中的位置                 |
| bookstore//book | 选取属于bookstore元素的后代的所有book元素，而不管它们位于bookstore之下的什么位置 |
| //@lang         | 选取名为lang的所有属性                                       |

###### 使用示例

![XML输入1](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525142126.png)

![XML输入1](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525142223.png)

![XML输入3](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525142349.png)

##### JSON Input

###### JSON概念

&emsp;&emsp;JSON(JavaScript Object Notation, JS 对象简谱) 是一种轻量级的数据交换格式

&emsp;&emsp;JSON核心概念：数组、对象、属性

&emsp;&emsp;数组：[ ]

&emsp;&emsp;对象：{ }

&emsp;&emsp;属性：key:value

![JSON](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525144703.png)

&emsp;&emsp;JSONPath类似于XPath在xml文档中的定位，JsonPath表达式通常是用来路径检索或设置Json的

&emsp;&emsp;其表达式可以接受“dot–notation”（点记法）和“bracket–notation”（括号记法）格式

&emsp;&emsp;点记法：``` $.store.book[0].title```

&emsp;&emsp;括号记法：``` $[‘store’][‘book’][0][‘title’] ```

&emsp;&emsp;JSONPath-操作符

![JSONPath-操作符](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525145030.png)

&emsp;&emsp;JSONPath-示例

![JSONPath-示例](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525145208.png)

###### 使用示例

![JSON输入1](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525152217.png)

![JSON输入2](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525152332.png)

##### 生成记录

![生成记录](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525152756.png)

##### 表输入

###### 下载驱动包

&emsp;&emsp;下载数据库对应的驱动包（JDBC jar包）放在Kettle的lib目录下

![数据库驱动](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525154253.png)

###### 新建DB连接

![新建DB连接](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525154102.png)

###### 编写SQL抽取数据

![抽取数据](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525154557.png)

#### 输出控件

&emsp;&emsp;输出是转换里面的第二个分类

&emsp;&emsp;输出属于ETL的L，L就是Load加载

##### 文件输出（Excel/文本/SQL）

###### Excel

&emsp;&emsp;Excel输出 ：2007年之前老版本（.xls）

![Excel2003](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525163701.png)

&emsp;&emsp;Microsoft Excel 输出：2007年之后新版本（.xlsx）

![Excel2007](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525163827.png)

###### 文本

![文本输出](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525164207.png)

###### SQL

&emsp;&emsp;可导出表结构和数据输出为SQL文件

![SQL输出](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525164644.png)

##### 表输出

&emsp;&emsp;表输出就是把数据写入到指定的表

![表输出](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525170027.png)

##### 更新

&emsp;&emsp;更新就是把数据库已经存在的记录与数据流里面的记录进行比对，如果不同就进行更新

&emsp;&emsp;如果记录不存在，则会出现错误

![更新](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525171612.png)

##### 插入/更新

&emsp;&emsp;插入更新就是把数据库已经存在的记录与数据流里面的记录进行比对，如果不同就进行更新

&emsp;&emsp;如果记录不存在，则会插入数据

![插入/更新](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525172047.png)

##### 删除

&emsp;&emsp;删除就是删除数据库表中指定条件的数据

![删除](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525173140.png)

#### 转换控件

&emsp;&emsp;转换是转换里面的第四个分类

&emsp;&emsp;转换属于ETL的T，T就是Transform清洗、转换

##### Concat fields

&emsp;&emsp;Concat fields就是多个字段连接起来形成一个新的字段

![Concatfields](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525174408.png)

##### 值映射

&emsp;&emsp;值映射就是把字段的一个值映射成其他的值

&emsp;&emsp;在数据质量规范上使用非常多，比如很多系统对应性别gender字段的定义不同

&emsp;&emsp;系统1：1 男、2女

&emsp;&emsp;系统2：f 男、m 女

&emsp;&emsp;数据仓库统一为：female 男、male女

![值映射](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525175510.png)

##### 增加常量

&emsp;&emsp;增加常量就是在本身的数据流里面添加一列数据，该列的数据都是相同的值

![增加常量](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525175950.png)

##### 增加序列

&emsp;&emsp;增加序列是给数据流添加一个序列字段

![增加序列](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525180749.png)

##### 字段选择

&emsp;&emsp;字段选择是从数据流中选择字段、改变名称、修改数据类型

![字段选择1](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525181404.png)

![字段选择2](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525181440.png)

![字段选择3](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525181506.png)

##### 计算器

&emsp;&emsp;计算器是一个函数集合来创建新的字段，还可以设置字段是否移除（临时字段）

![计算器](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525182214.png)

##### 字符串-剪切-替换-操作

###### 剪切

&emsp;&emsp;剪切字符串是指定输入流字段裁剪的位置剪切出新的字段

![剪切](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525183607.png)

###### 替换

&emsp;&emsp;字符串替换是指定搜索内容和替换内容，如果输入流的字段匹配上搜索内容就进行替换生成新字段

![替换](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525183629.png)

###### 操作

![操作](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525183649.png)

&emsp;&emsp;字符串操作是去除字符串两端的空格和大小写切换，并生成新的字段

##### 排序记录+去除重复记录

###### 排序记录

&emsp;&emsp;排序记录是按照指定的字段的升序或降序对数据流排序

![排序](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525184420.png)

###### 去除重复记录

&emsp;&emsp;去除重复记录是去除数据流里面相同的数据行

&emsp;&emsp;注意：必须先对数据流进行排序

![去重](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525184543.png)

##### 唯一行（哈希值）

&emsp;&emsp;唯一行（哈希值）就是删除数据流重复的行

&emsp;&emsp;注意：唯一行（哈希值）和（排序记录+去除重复记录）效果一样的，但是实现的原理不同

&emsp;&emsp;唯一行（哈希值）执行的效率会高一些

![唯一行（哈希值）](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525185027.png)

##### 拆分字段

&emsp;&emsp;拆分字段是把字段按照分隔符拆分成两个或多个字段

&emsp;&emsp;注意：拆分字段后，原字段就不存在于数据流中

![拆分字段](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525185557.png)

##### 列拆分为多行

&emsp;&emsp;列拆分为多行就是把指定分隔符的字段进行拆分为多行

![列拆分1](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525185837.png)

![列拆分2](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525185847.png)

![列拆分为多行](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525190211.png)

##### 列转行

&emsp;&emsp;列转行就是如果数据一列有相同的值，按照指定的字段，把多行数据转换为一行数据

&emsp;&emsp;去除一些原来的列名，把一列数据变为字段

&emsp;&emsp;注意：列转行之前数据流必须进行排序

![列转行1](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525190410.png)

![列转行2](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525190425.png)

![列转行3](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525192331.png)

##### 行转列

&emsp;&emsp;行转列就是把数据字段的字段名转换为一列，把数据行变为数据列

![行转列1](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525223208.png)

![行转列2](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525223235.png)

![行转列3](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525223828.png)

##### 行扁平化

&emsp;&emsp;行扁平化就是把同一组的多行数据合并成为一行

&emsp;&emsp;注意：

&emsp;&emsp;只有数据流的同类数据数据行记录一致的情况才可使用

&emsp;&emsp;数据流必须进行排序，否则结果会不正确

![行扁平化1](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525224044.png)

![行扁平化2](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525224106.png)

![行扁平化3](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525224345.png)

#### 应用控件

&emsp;&emsp;应用是转换里面的第五个分类

&emsp;&emsp;应用都是一些工具类

##### 替换NULL值

&emsp;&emsp;替换NULL值就是把null转换为其它的值

&emsp;&emsp;NULL值不好进行数据分析

![替换NULL值](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525231050.png)

##### 写日志

&emsp;&emsp;写日志主要是在调试的时候使用，把日志信息打印到日志窗口

![写日志1](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525231611.png)

![写日志2](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525231713.png)

##### 发送邮件

&emsp;&emsp;发送邮件就是执行成功、失败、其它某种情景给相关人员发送邮件

#### 流程控件

&emsp;&emsp;流程是转换里面的第六个分类

&emsp;&emsp;流程主要用来控制数据流程和数据流向

##### Switch/case

&emsp;&emsp;Switch/case让数据流从一路到多路

![Switch/case1](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525235405.png)

![Switch/case2](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525234820.png)

##### 过滤记录

&emsp;&emsp;过滤记录让数据流从一路到两路

![过滤记录1](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525235538.png)

![过滤记录2](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210525235940.png)

##### 空操作

&emsp;&emsp;空操作一般作为数据流的终点，实际开发中很少使用

![空操作](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526000653.png)

##### 中止

&emsp;&emsp;中止是数据流的终点，如果有数据到这里，将会报错

&emsp;&emsp;用来校验数据的时候使用

![中止](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526001038.png)

#### 查询控件

&emsp;&emsp;查询是转换里面的第七个分类

&emsp;&emsp;查询是用来查询数据源里的数据并合并到主数据流中

##### HTTP client

![HTTP client](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526063209.png)

##### 数据库查询

&emsp;&emsp;数据库查询就是数据库里面的左连接

![数据库查询](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526064214.png)

##### 数据库连接

&emsp;&emsp;数据库连接可以执行两个数据库的查询，和单参数的表输入

![数据库连接](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526101529.png)

##### 流查询

&emsp;&emsp;流查询在查询前把数据都加载到内存中，并且只能进行等值查询

![流查询](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526101623.png)

#### 连接控件

&emsp;&emsp;连接是转换里面的第八个分类

&emsp;&emsp;连接是结果集通过关键字进行连接

##### 合并记录

&emsp;&emsp;合并记录是用于将两个不同来源的数据合并，这两个来源的数据分别为旧数据和新数据，该步骤将旧数据和新数据按照指定的关键字匹配、比较、合并

&emsp;&emsp;需要设置的参数：

&emsp;&emsp;旧数据来源：旧数据来源的步骤

&emsp;&emsp;新数据来源：新数据来源的步骤

&emsp;&emsp;标志字段：设置标志字段的名称，标志字段用于保存比较的结果，比较结果有下列几种：

&emsp;&emsp;1. “identical” – 旧数据和新数据一样；

&emsp;&emsp;2. “changed” – 数据发生了变化；

&emsp;&emsp;3. “new” – 新数据中有而旧数据中没有的记录；

&emsp;&emsp;4. “deleted” –旧数据中有而新数据中没有的记录；

&emsp;&emsp;关键字段：用于定位两个数据源中的同一条记录

&emsp;&emsp;比较字段：对于两个数据源中的同一条记录中，指定需要比较的字段

&emsp;&emsp;合并后的数据将包括旧数据来源和新数据来源里的所有数据，对于变化的数据，使用新数据代替旧数据，同时在结果里用一个标示字段，来指定新旧数据的比较结果

&emsp;&emsp;注意：

&emsp;&emsp;旧数据和新数据需要事先按照关键字段排序

&emsp;&emsp;旧数据和新数据要有相同的字段名称

![合并记录](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526102031.png)

##### 记录关联（笛卡尔积）

&emsp;&emsp;记录关联就是对两个数据流进行笛卡尔积操作

![记录关联（笛卡尔积）1](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526102124.png)

![记录关联（笛卡尔积）2](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526102240.png)

##### 记录集连接

&emsp;&emsp;记录集连接就像数据库的左连接、右连接、内连接、外连接

&emsp;&emsp;注意：在进行记录集连接之前，应该要对记录集进行排序

![记录集连接1](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526102337.png)

![记录集连接2](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526102403.png)

#### 统计控件

&emsp;&emsp;统计是转换里面的第十三个分类

&emsp;&emsp;统计是提供数据的采样和统计功能

##### 分组

&emsp;&emsp;分组是按照某一个或某几个进行分组，同时可以将其余字段按照某种规则进行合并

&emsp;&emsp;注意：分组之前数据应该进行排序

![分组](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526102602.png)

#### 映射控件

&emsp;&emsp;映射是转换里面的第十八个分类

&emsp;&emsp;映射是用来定义子转换，便于封装和重用

##### 映射（子转换）

&emsp;&emsp;映射（子转换）是用来配置子转换，对子转换进行调用的一个步骤

##### 映射输入规范

&emsp;&emsp;映射输入规范是输入字段，由调用的转换输入

##### 映射输出规范

&emsp;&emsp;映射输出规范是向调用的转换输出所有列，不做任何处理

![映射（子转换）](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526103919.png)

#### 脚本控件

&emsp;&emsp;脚本是转换里面的第七个分类

&emsp;&emsp;脚本就是直接通过程序代码完成一些复杂的操作

##### JavaScript代码

![JavaScript脚本](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526110620.png)

##### Java 代码

![Java脚本](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526110650.png)

##### 执行SQL脚本

![执行SQL脚本](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526110715.png)

#### 作业和参数

##### 作业

&emsp;&emsp;大多数ETL项目都需要完成各种各样的维护工作

&emsp;&emsp;例如，如何传送文件；验证数据库表是否存在，等等。而这些操作都是按照一定顺序完成。因为转换以并行方式执行，就需要一个可以串行执行的作业来处理这些操作

&emsp;&emsp;一个作业包含一个或者多个作业项，这些作业项以某种顺序来执行。作业执行顺序由作业项之间的跳（job hop）和每个作业项的执行结果来决定

![作业1](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526110943.png)

###### 作业项

&emsp;&emsp;作业项是作业的基本构成部分。如同转换的步骤，作业项也可以使用图标的方式图形化展示

&emsp;&emsp;但是，如果你再仔细观察，还是会发现作业项有一些地方不同于步骤：
在作业项之间可以传递一个结果对象（result object）。这个结果对象里面包含了数据行，它们不是以数据流的方式来传递的。而是等待一个作业项执行完了，再传递个下一个作业项

&emsp;&emsp;因为作业顺序执行作业项，所以必须定义一个起点。有一个叫“开始”的作业项就定义了这个点。一个作业只能定一个开始作业项

###### 作业跳

&emsp;&emsp;作业的跳是作业项之间的连接线，他定义了作业的执行路径。作业里每个作业项的不同运行结果决定了做作业的不同执行路径

&emsp;&emsp;①无条件执行：不论上一个作业项执行成功还是失败，下一个作业项都会执行。这是一种蓝色的连接线，上面有一个锁的图标

&emsp;&emsp;②当运行结果为真时执行：当上一个作业项的执行结果为真时，执行下一个作业项。通常在需要无错误执行的情况下使用。这是一种绿色的连接线，上面有一个对钩号的图标

&emsp;&emsp;③当运行结果为假时执行：当上一个作业项的执行结果为假或者没有成功执行是，执行下一个作业项。这是一种红色的连接线，上面有一个红色的停止图标

&emsp;&emsp;在图标上单击就可以对跳进行设置

![作业2](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526111250.png)

##### 参数

&emsp;&emsp;对于ETL参数传递是一个很重要的环节，因为参数的传递会涉及到业务数据是如何抽取

&emsp;&emsp;参数分为两种：全局参数和局部参数

###### 全局参数

&emsp;&emsp;全局参数定义是通过当前用户下.kettle文件夹中的kettle.properties文件来定义

&emsp;&emsp;定义方式是采用键=值对方式来定，如：start_date=20130101

&emsp;&emsp;注：在配置全局变量时需要重启Kettle才会生效

###### 局部参数

&emsp;&emsp;局部参数变量是通过“Set Variables”与“Get Variables”方式来设置

&emsp;&emsp;注：在“Set Variables”时在当前转换当中是不能马上使用，需要在作业中的下一步骤中使用

###### 参数的使用

&emsp;&emsp;Kettle中参数使用方法有两种：一种是%%变量名%%，一种是${变量名}

&emsp;&emsp;注：在SQL中使用变量时需要把“是否替换变量”勾选上，否则无法使变量生效

##### 表输入参数传递-常量传递

&emsp;&emsp;常量传递就是先自定义常量数据，在表输入的SQL语句里面使用？来替换

&emsp;&emsp;？号的替换顺序就是常量定义的顺序

##### 表输入参数传递-变量传递-转换命名参数

&emsp;&emsp;转换命名参数就是在转换内部定义的变量，作用范围是在转换内部

&emsp;&emsp;在转换的空白处右键，选择转换设置就可以看见

![转换命名参数](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526112422.png)

##### 表输入参数传递-变量传递-转换内设置变量和获取变量

&emsp;&emsp;在转换里面有一个作业分类，里面有设置变量和获取变量的步骤

&emsp;&emsp;注意：“获取变量”时在当前转换当中是不能马上使用，需要在作业中的下一步骤中使用！

![转换内设置变量](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526113520.png)

![转换内获取变量](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526113555.png)

##### 表输入参数传递-变量传递-作业里设置变量

&emsp;&emsp;变量可以在转换里面设置，也可以在作业里面设置

![作业里设置变量](https://cdn.jsdelivr.net/gh/JOKERZ22/image@master/20210526112945.png)

##### 发送邮件

### Java调用

[Java调用Kettle](https://blog.csdn.net/lizhiqiang1217/article/details/90027277)

### Linux使用

[Linux使用kettle](https://blog.csdn.net/zzq900503/article/details/79110810)

### 调优

[kettle性能优化](https://www.kettle.net.cn/2367.html)
