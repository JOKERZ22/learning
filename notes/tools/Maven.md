###  定义及主要功能


Maven是专门为Java项目打造的管理和构建工具，它的主要功能有：


* 提供了一套标准化的项目结构;
* 提供了一套标准化的构建流程（编译，测试，打包，发布……）;
* 提供了一套依赖管理机制;


### 项目结构


![项目结构](http://img.tuoluoleaf.com/img/maven.jpg)


| 目录               | 作用                 |
| ------------------ | -------------------- |
| a-maven-project    | 项目名               |
| pom.xml            | 项目描述文件         |
| src/main/java      | Java源码             |
| src/main/resources | 资源文件             |
| src/test/java      | 测试源码             |
| src/test/resources | 测试资源文件         |
| target             | 编译、打包生成的文件 |


### pom.xml


```xml
<project ...>
	<modelVersion>4.0.0</modelVersion>
	
	<groupId>com.itranswarp.learnjava</groupId>
	<artifactId>hello</artifactId>
	<version>1.0</version>
	
	<packaging>jar</packaging>
	
	<properties>
        ...
	</properties>
	
	<dependencies>
        <dependency>
            <groupId>commons-logging</groupId>
            <artifactId>commons-logging</artifactId>
            <version>1.2</version>
        </dependency>
	</dependencies>
	
</project>
```


**使用groupId，artifactId和version唯一定位一个依赖**


### 依赖关系


| scope    | 说明                                          | 示例            |
| -------- | --------------------------------------------- | --------------- |
| compile  | 编译时需要用到该jar包（默认）                 | commons-logging |
| test     | 编译test时需要用到该jar包                     | junit           |
| runtime  | 编译时不需要，但运行时需要用到                | mysql           |
| provided | 编译时需要用到，但运行时由JDK或某个服务器提供 | servlet-api     |


其中，默认的compile是最常用的，Maven会把这种类型的依赖直接放入classpath;


test依赖表示仅在测试时使用，正常运行时并不需要。最常用的test依赖就是JUnit：
```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.3.2</version>
    <scope>test</scope>
</dependency>
```


runtime依赖表示编译时不需要，但运行时需要。最典型的runtime依赖是JDBC驱动，例如MySQL驱动：
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.48</version>
    <scope>runtime</scope>
</dependency>
```


provided依赖表示编译时需要，但运行时不需要。最典型的provided依赖是Servlet API，编译的时候需要，但是运行时，Servlet服务器内置了相关的jar，所以运行期不需要：
```xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.0</version>
    <scope>provided</scope>
</dependency>
```


### Maven阿里云镜像仓库
settings.xml配置文件添加阿里云镜像节点
```xml
<settings>
    <mirrors>
        <mirror>
            <id>aliyun</id>
            <name>aliyun</name>
            <mirrorOf>central</mirrorOf>
            <!-- 国内推荐阿里云的Maven镜像 -->
            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
        </mirror>
    </mirrors>
</settings>
```


### 构建流程


#### Lifecycle(生命周期)和Phase(阶段)


Maven的生命周期由一系列阶段（phase）构成，以内置的生命周期default为例，它包含以下phase：
* validate
* initialize
* generate-sources
* process-sources
* generate-resources
* process-resources
* compile
* process-classes
* generate-test-sources
* process-test-sources
* generate-test-resources
* process-test-resources
* test-compile
* process-test-classes
* test
* prepare-package
* package
* pre-integration-test
* integration-test
* post-integration-test
* verify
* install
* deploy


| 命令        | 生命周期 | Phase                      |
| ----------- | -------- | -------------------------- |
| mvn package | default  | validate->package          |
| mvn compile | default  | validate->compile          |
| mvn clean   | clean    | pre-clean clean post-clean |


执行mvn命令，后面的参数是phase，Maven自动根据生命周期运行到指定的phase，也可以指定多个phase。
例如，运行mvn clean package，Maven先执行clean生命周期并运行到clean这个phase，然后执行default生命周期并运行到package这个phase，实际执行的phase如下：
pre-clean clean validate ... package


| 常用命令          | 作用效果                                                     |
| ----------------- | ------------------------------------------------------------ |
| mvn clean         | 清理所有生成的class和jar                                     |
| mvn clean compile | 先清理，再执行到compile                                      |
| mvn clean test    | 先清理，再执行到test，因为执行test前必须执行compile，所以这里不必指定compile |
| mvn clean package | 先清理，再执行到package                                      |

大多数phase在执行过程中，因为我们通常没有在pom.xml中配置相关的设置，所以这些phase什么事情都不做。

| 常用Phase | 作用效果 |
| --------- | -------- |
| clean     | 清理     |
| compile   | 编译     |
| test      | 运行测试 |
| package   | 打包     |


#### Goal


执行一个phase又会触发一个或多个goal：


| 执行的Phase | 对应执行的Goal                      |
| ----------- | ----------------------------------- |
| compile     | compiler:compile                    |
| test        | compiler:testCompile  surefire:test |


goal的命名总是`abc:xyz`这种形式。


* lifecycle相当于Java的package，它包含一个或多个phase；

* phase相当于Java的class，它包含一个或多个goal；

* goal相当于class的method，它其实才是真正干活的。


大多数情况，我们只要指定phase，就默认执行这些phase默认绑定的goal，只有少数情况，我们可以直接指定运行一个goal，例如，启动Tomcat服务器：


```shell
mvn tomcat:run
```

### 插件


使用Maven构建项目就是执行lifecycle，执行到指定的phase为止。每个phase会执行自己默认的一个或多个goal。goal是最小任务单元


执行每个phase，都是通过某个插件（plugin）来执行的，Maven本身其实并不知道如何执行compile，它只是负责找到对应的compiler插件，然后执行默认的compiler:compile这个goal来完成编译。
所以，使用Maven，实际上就是配置好需要使用的插件，然后通过phase调用它们。


Maven已经内置了一些常用的标准插件：

| 插件名称 | 对应执行的phase |
| -------- | --------------- |
| clean    | clean           |
| compiler | compile         |
| surefire | test            |
| jar      | package         |


使用自定义插件需要在pom.xml中声明并且一般都需要配置
例如：使用maven-shade-plugin可以创建一个可执行的jar


```xml
<project>
    ...
	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-shade-plugin</artifactId>
                <version>3.2.1</version>
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>shade</goal>
						</goals>
						<configuration>
                            ...
						</configuration>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
</project>

<configuration>
    <transformers>
        <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
            <mainClass>com.itranswarp.learnjava.Main</mainClass>
        </transformer>
    </transformers>
</configuration>
```


| 常用插件               | 作用效果                             |
| ---------------------- | ------------------------------------ |
| maven-shade-plugin     | 打包所有依赖包并生成可执行jar        |
| cobertura-maven-plugin | 生成单元测试覆盖率报告               |
| findbugs-maven-plugin  | 对Java源码进行静态分析以找出潜在问题 |


### 模块管理

抽取各模块共同部分作为parent，并设置<packaging>为pom，其他子模块从parent继承，项目结果如下:


![模块管理](http://img.tuoluoleaf.com/img/maven_models.jpg)


父工程parent pom.xml
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>top.shaonianyou</groupId>
    <artifactId>parent</artifactId>
    <version>1.0</version>
    <packaging>pom</packaging>

    <name>parent</name>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <java.version>8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.28</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.3</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>5.5.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

子工程模块A pom.xml
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>top.shaonianyou</groupId>
        <artifactId>parent</artifactId>
        <version>1.0</version>
        <relativePath>../parent/pom.xml</relativePath>
    </parent>

    <artifactId>module-a</artifactId>
    <packaging>jar</packaging>
    <name>module-a</name>
</project>
```

如果模块A依赖模块B，则模块A需要模块B的jar包才能正常编译，我们需要在模块A中引入模块B：
```xml
...
    <dependencies>
        <dependency>
            <groupId>top.shaonianyou</groupId>
            <artifactId>module-b</artifactId>
            <version>1.0</version>
        </dependency>
    </dependencies>
```

在编译的时候，需要在根目录创建一个pom.xml统一编译：
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

    <modelVersion>4.0.0</modelVersion>
    <groupId>top.shaonianyou</groupId>
    <artifactId>build</artifactId>
    <version>1.0</version>
    <packaging>pom</packaging>
    <name>build</name>

    <modules>
        <module>parent</module>
        <module>module-a</module>
        <module>module-b</module>
        <module>module-c</module>
    </modules>
</project>
```


### MVNW

mvnw是Maven Wrapper的缩写，可以负责给这个特定的项目安装指定版本的Maven，而其他项目不受影响。


#### 安装Maven Wrapper


安装Maven Wrapper最简单的方式是在项目的根目录（即pom.xml所在的目录）下运行安装命令：


```shell
mvn -N io.takari:maven:0.7.6:wrapper
```


会自动使用最新版本的Maven。注意0.7.6是Maven Wrapper的版本。最新的Maven Wrapper版本可以去[官方网站](https://github.com/takari/maven-wrapper)查看。


指定使用的Maven版本


```shell
mvn -N io.takari:maven:0.7.6:wrapper -Dmaven=3.3.3
```

安装后项目结构：
![mvnw项目结构](http://img.tuoluoleaf.com/img/maven_mvnw.jpg)


使用与mvn命令一致，替换成mvnw就行


Maven Wrapper的另一个作用是把项目的mvnw、mvnw.cmd和.mvn提交到版本库中，可以使所有开发人员使用统一的Maven版本。