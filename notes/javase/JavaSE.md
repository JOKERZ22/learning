### 基本类型


1byte(字节)=8bit(比特)


| 基本类型 | 所占字节大小 | 范围大小                                   | 默认值 |
| -------- | ------------ | ------------------------------------------ | --- |
| byte     | 1            | -128 ~ 127                                 | 0 |
| short    | 2            | -32768 ~ 32767                             | 0 |
| int      | 4            | -2147483648 ~ 2147483647                   | 0 |
| long     | 8            | -9223372036854775808 ~ 9223372036854775807 | 0 |
| float    | 4            | 3.4x10<sup>38</sup> | 0.0 |
| double   | 8            | 1.79x10<sup>308</sup> | 0.0 |
| char     | 2            |                                            | "" |
| boolean     | 4 |                                            | false |


### 位运算


| 符号        | 规则                             |
| ----------- | -------------------------------- |
| &(与运算)   | 必须两个数同时为1，结果才为1     |
| \|(或运算)  | 只要任意一个为1，结果就为1       |
| ~(非运算)   | 0和1互换                         |
| ^(异或运算) | 如果两个数不同，结果为1，否则为0 |


### 运算优先级


从高到低
* `()`
* `! ~ ++ --`
* `*/ %`
* `+-`
* `<< >> >>>`
* `&`
* `|`
* `+= -= *= /=`


### 关系运算符优先级
* `!`
* `>，>=，<，<=`
* `==，!=`
* `&&`
* `||`


### final的作用
* final修饰的方法可以阻止被覆写；
* final修饰的class可以阻止被继承；
* final修饰的field必须在创建对象时初始化，随后不可修改；


### 抽象类与接口的区别


|            | 抽象类（abstract class） | 接口（interface）       |
| ---------- | ------------------------ | --------------------------- |
| 继承       | 只能extends一个class     | 可以implements多个interface |
| 字段       | 可以定义实例字段         | 不能定义实例字段            |
| 抽象方法   | 可以定义抽象方法         | 可以定义抽象方法            |
| 非抽象方法 | 可以定义非抽象方法       | 可以定义default方法         |


### 访问权限


| 修饰符    | 作用域                                                       |
| --------- | ------------------------------------------------------------ |
| public    | 定义为`public`的`class`、`interface`可以被其他任何类访问；定义为`public`的`field`、`method`可以被其他类访问，前提是首先有访问`class`的权限 |
| package   | 包作用域是指一个类允许访问同一个`package`的没有`public`、`private`修饰的`class`，以及没有`public`、`protected`、`private`修饰的字段和方法 |
| protected | `protected`作用于继承关系。定义为`protected`的字段和方法可以被子类访问，以及子类的子类 |
| private   | 定义为`private`的`field`、`method`无法被其他类访问；嵌套类拥有访问`private`的权限 |


### Java内部类


|                                   | 特点                                      |
| --------------------------------- | ----------------------------------------- |
| 内部类（Inner Class）             | 依附于外部类，可以访问外部类的private字段 |
| 匿名内部类（Anonymous Class）     | 同上，不用声明引用                        |
| 静态内部类（Static Nested Class） | 独立，可以访问外部类的静态字段            |


### 异常体系


![异常体系](http://img.tuoluoleaf.com/img/se_exception_tree.jpg)


### 函数式编程


#### FunctionalInterface


我们把只定义了单方法的接口称之为FunctionalInterface，用注解@FunctionalInterface标记。例如，Callable接口：
```java
@FunctionalInterface
public interface Callable<V> {
    V call() throws Exception;
}
```
再来看Comparator接口：
```java
@FunctionalInterface
public interface Comparator<T> {

    int compare(T o1, T o2);

    boolean equals(Object obj);

    default Comparator<T> reversed() {
        return Collections.reverseOrder(this);
    }

    default Comparator<T> thenComparing(Comparator<? super T> other) {
        ...
    }
    ...
}
```
虽然Comparator接口有很多方法，但只有一个抽象方法int compare(T o1, T o2)，其他的方法都是default方法或static方法。另外注意到boolean equals(Object obj)是Object定义的方法，不算在接口方法内。因此，Comparator也是一个FunctionalInterface。


#### Lambda表达式


函数式编程（Functional Programming）是把函数作为基本运算单元，函数可以作为变量，可以接收函数，还可以返回函数。历史上研究函数式编程的理论是Lambda演算，所以我们经常把支持函数式编程的编码风格称为Lambda表达式。


接收FunctionalInterface作为参数的时候，可以把实例化的匿名类改写为Lambda表达式，能大大简化代码。如：
以Comparator为例，我们想要调用Arrays.sort()时，可以传入一个Comparator实例，以匿名类方式编写如下：
```java
String[] array = ...
Arrays.sort(array, new Comparator<String>() {
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }
});
```


用Lambda改写如下：
```java
public class Main {
    public static void main(String[] args) {
        String[] array = new String[] { "Apple", "Orange", "Banana", "Lemon" };
        Arrays.sort(array, (s1, s2) -> {
            return s1.compareTo(s2);
        });
        System.out.println(String.join(", ", array));
    }
}
```


Lambda表达式的参数和返回值均可由编译器自动推断。


Lambda表达式的写法:
```java
(s1, s2) -> {
    return s1.compareTo(s2);
}
```
如果只有一行return xxx的代码，完全可以用更简单的写法：
```java
(s1, s2) -> s1.compareTo(s2)
```


#### 方法引用


FunctionalInterface不强制继承关系，不需要方法名称相同，只要求方法参数（类型和数量）与方法返回类型相同，即认为方法签名相同。


FunctionalInterface允许传入：
|                        | 特点                             | 例子                         |
| ---------------------- | -------------------------------- | ---------------------------- |
| 接口的实现类           | 传统写法，代码较繁琐             |                              |
| Lambda表达式           | 只需列出参数名，由编译器推断类型 | (s1, s2) -> s1.compareTo(s2) |
| 符合方法签名的静态方法 |                                  | String.compareTo()           |
| 符合方法签名的实例方法 | 实例类型被看做第一个参数类型     | Main::cmp                    |
| 符合方法签名的构造方法 | 实例类型被看做返回类型           | Person::new                  |


### Stream


Stream API与IO Stream的区别


|      | java.io | java.util.stream |
| ---- | ------- | -------------------- |
| 存储 | 顺序读写的`byte`或`char` | 顺序输出的任意Java对象实例 |
| 用途 | 序列化至文件或网络 | 内存计算／业务逻辑 |


Stream API与List的区别


|      | java.util.List           | java.util.stream     |
| ---- | ------------------------ | -------------------- |
| 元素 | 已分配并存储在内存       | 可能未分配，实时计算 |
| 用途 | 操作一组已存在的Java对象 | 惰性计算             |


特点：


* Stream API提供了一套新的流式处理的抽象序列；
* Stream API支持函数式编程和链式操作；
* Stream可以表示无限序列，并且大多数情况下是惰性求值的。


#### 创建Stream


**Stream.of()**
创建Stream最简单的方式是直接用Stream.of()静态方法，传入可变参数即创建了一个能输出确定元素的Stream：
```java
public class Main {
    public static void main(String[] args) {
        Stream<String> stream = Stream.of("A", "B", "C", "D");
        // forEach()方法相当于内部循环调用，
        // 可传入符合Consumer接口的void accept(T t)的方法引用：
        stream.forEach(System.out::println);
    }
}
```


**基于数组或Collection**
第二种创建Stream的方法是基于一个数组或者Collection，这样该Stream输出的元素就是数组或者Collection持有的元素：
```java
public class Main {
    public static void main(String[] args) {
        Stream<String> stream1 = Arrays.stream(new String[] { "A", "B", "C" });
        Stream<String> stream2 = List.of("X", "Y", "Z").stream();
        stream1.forEach(System.out::println);
        stream2.forEach(System.out::println);
    }
}

```


**基于Supplier**
创建Stream还可以通过Stream.generate()方法，它需要传入一个Supplier对象：
```java
Stream<String> s = Stream.generate(Supplier<String> sp);
```


基于Supplier创建的Stream会不断调用Supplier.get()方法来不断产生下一个元素，这种Stream保存的不是元素，而是算法，它可以用来表示无限序列。

例如，我们编写一个能不断生成自然数的Supplier，它的代码非常简单，每次调用get()方法，就生成下一个自然数：
```java
public class Main {
    public static void main(String[] args) {
        Stream<Integer> natual = Stream.generate(new NatualSupplier());
        // 注意：无限序列必须先变成有限序列再打印:
        natual.limit(20).forEach(System.out::println);
    }
}

class NatualSupplier implements Supplier<Integer> {
    int n = 0;
    public Integer get() {
        n++;
        return n;
    }
}

```


**其他方法**
创建Stream的第三种方法是通过一些API提供的接口，直接获得Stream。


例如，Files类的lines()方法可以把一个文件变成一个Stream，每个元素代表文件的一行内容：
```java
try (Stream<String> lines = Files.lines(Paths.get("/path/to/file.txt"))) {
    ...
}
```


另外，正则表达式的Pattern对象有一个splitAsStream()方法，可以直接把一个长字符串分割成Stream序列而不是数组：
```java
Pattern p = Pattern.compile("\\s+");
Stream<String> s = p.splitAsStream("The quick brown fox jumps over the lazy dog");
s.forEach(System.out::println);
```


**基本类型**


因为Java的范型不支持基本类型，所以我们无法用Stream<int>这样的类型，会发生编译错误。为了保存int，只能使用Stream<Integer>，但这样会产生频繁的装箱、拆箱操作。为了提高效率，Java标准库提供了IntStream、LongStream和DoubleStream这三种使用基本类型的Stream，它们的使用方法和范型Stream没有大的区别，设计这三个Stream的目的是提高运行效率：
```java
// 将int[]数组变为IntStream:
IntStream is = Arrays.stream(new int[] { 1, 2, 3 });
// 将Stream<String>转换为LongStream:
LongStream ls = List.of("1", "2", "3").stream().mapToLong(Long::parseLong);
```


#### 使用map


Stream.map()是Stream最常用的一个转换方法，它把一个Stream转换为另一个Stream。
所谓map操作，就是把一种操作运算，映射到一个序列的每一个元素上；可以将一种元素类型转换成另一种元素类型。


查看Stream的源码，会发现map()方法接收的对象是Function接口对象，它定义了一个apply()方法，负责把一个T类型转换成R类型：
```java
<R> Stream<R> map(Function<? super T, ? extends R> mapper);
```
其中，Function的定义是：
```java
@FunctionalInterface
public interface Function<T, R> {
    // 将T类型转换为R:
    R apply(T t);
}
```


利用map()，不但能完成数学计算，对于字符串操作，以及任何Java对象都是非常有用的。例如：
```java
public class Main {
    public static void main(String[] args) {
        List.of("  Apple ", " pear ", " ORANGE", " BaNaNa ")
                .stream()
                .map(String::trim) // 去空格
                .map(String::toLowerCase) // 变小写
                .forEach(System.out::println); // 打印
    }
}
```


#### 使用filter


filter()操作，就是对一个Stream的所有元素一一进行测试，不满足条件的就被“滤掉”了，剩下的满足条件的元素就构成了一个新的Stream。
例如，我们对1，2，3，4，5这个Stream调用filter()，传入的测试函数f(x) = x % 2 != 0用来判断元素是否是奇数，这样就过滤掉偶数，只剩下奇数，因此我们得到了另一个序列1，3，5：
```java
public class Main {
    public static void main(String[] args) {
        IntStream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .filter(n -> n % 2 != 0)
                .forEach(System.out::println);
    }
}
```

filter()方法接收的对象是Predicate接口对象，它定义了一个test()方法，负责判断元素是否符合条件：
```java
@FunctionalInterface
public interface Predicate<T> {
    // 判断元素t是否符合条件:
    boolean test(T t);
}
```

filter()除了常用于数值外，也可应用于任何Java对象。例如，从一组给定的LocalDate中过滤掉工作日，以便得到休息日：
```java
public class Main {
    public static void main(String[] args) {
        Stream.generate(new LocalDateSupplier())
                .limit(31)
                .filter(ldt -> ldt.getDayOfWeek() == DayOfWeek.SATURDAY || ldt.getDayOfWeek() == DayOfWeek.SUNDAY)
                .forEach(System.out::println);
    }
}

class LocalDateSupplier implements Supplier<LocalDate> {
    LocalDate start = LocalDate.of(2020, 1, 1);
    int n = -1;
    public LocalDate get() {
        n++;
        return start.plusDays(n);
    }
}
```


#### 使用reduce


map()和filter()都是Stream的转换方法，而Stream.reduce()则是Stream的一个聚合方法，它可以把一个Stream的所有元素按照聚合函数聚合成一个结果
```java
public class Main {
    public static void main(String[] args) {
        int sum = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).reduce(0, (acc, n) -> acc + n);
        System.out.println(sum); // 45
    }
}
```


reduce()方法传入的对象是BinaryOperator接口，它定义了一个apply()方法，负责把上次累加的结果和本次的元素 进行运算，并返回累加的结果：
```java
@FunctionalInterface
public interface BinaryOperator<T> {
    // Bi操作：两个输入，一个输出
    T apply(T t, T u);
}
```
上述代码看上去不好理解，但我们用for循环改写一下，就容易理解了:
```java
Stream<Integer> stream = ...
int sum = 0;
for (n : stream) {
    sum = (sum, n) -> sum + n;
}
```


除了可以对数值进行累积计算外，灵活运用reduce()也可以对Java对象进行操作。下面的代码演示了如何将配置文件的每一行配置通过map()和reduce()操作聚合成一个Map<String, String>：
```java
public class Main {
    public static void main(String[] args) {
        // 按行读取配置文件:
        List<String> props = List.of("profile=native", "debug=true", "logging=warn", "interval=500");
        Map<String, String> map = props.stream()
                // 把k=v转换为Map[k]=v:
                .map(kv -> {
                    String[] ss = kv.split("\\=", 2);
                    return Map.of(ss[0], ss[1]);
                })
                // 把所有Map聚合到一个Map:
                .reduce(new HashMap<String, String>(), (m, kv) -> {
                    m.putAll(kv);
                    return m;
                });
        // 打印结果:
        map.forEach((k, v) -> {
            System.out.println(k + " = " + v);
        });
    }
}
```


reduce()方法将一个Stream的每个元素依次作用于BinaryOperator，并将结果合并。
reduce()是聚合方法，聚合方法会立刻对Stream进行计算。


**对于`Stream`来说，对其进行转换操作并不会触发任何计算！聚合操作会立刻促使`Stream`输出它的每一个元素，并依次纳入计算，以获得最终结果。**


#### 输出集合


##### 输出为List


因为List的元素是确定的Java对象，因此，把Stream变为List不是一个转换操作，而是一个聚合操作，它会强制Stream输出每个元素。


下面的代码演示了如何将一组String先过滤掉空字符串，然后把非空字符串保存到List中：
```java
public class Main {
    public static void main(String[] args) {
        Stream<String> stream = Stream.of("Apple", "", null, "Pear", "  ", "Orange");
        List<String> list = stream.filter(s -> s != null && !s.isBlank()).collect(Collectors.toList());
        System.out.println(list);
    }
}
```


把Stream的每个元素收集到List的方法是调用collect()并传入Collectors.toList()对象，它实际上是一个Collector实例，通过类似reduce()的操作，把每个元素添加到一个收集器中（实际上是ArrayList）。
类似的，collect(Collectors.toSet())可以把Stream的每个元素收集到Set中。


##### 输出为数组


把Stream的元素输出为数组和输出为List类似，我们只需要调用toArray()方法，并传入数组的“构造方法”：
```java
List<String> list = List.of("Apple", "Banana", "Orange");
String[] array = list.stream().toArray(String[]::new);
```
注意到传入的“构造方法”是String[]::new，它的签名实际上是IntFunction<String[]>定义的String[] apply(int)，即传入int参数，获得String[]数组的返回值。


##### 输出为Map


如果我们要把Stream的元素收集到Map中，就稍微麻烦一点。因为对于每个元素，添加到Map时需要key和value，因此，我们要指定两个映射函数，分别把元素映射为key和value：
```java
public class Main {
    public static void main(String[] args) {
        Stream<String> stream = Stream.of("APPL:Apple", "MSFT:Microsoft");
        Map<String, String> map = stream
                .collect(Collectors.toMap(
                        // 把元素s映射为key:
                        s -> s.substring(0, s.indexOf(':')),
                        // 把元素s映射为value:
                        s -> s.substring(s.indexOf(':') + 1)));
        System.out.println(map);
    }
}
```


##### 分组输出


Stream还有一个强大的分组功能，可以按组输出。我们看下面的例子：
```java
public class Main {
    public static void main(String[] args) {
        List<String> list = List.of("Apple", "Banana", "Blackberry", "Coconut", "Avocado", "Cherry", "Apricots");
        Map<String, List<String>> groups = list.stream()
                .collect(Collectors.groupingBy(s -> s.substring(0, 1), Collectors.toList()));
        System.out.println(groups);
    }
}
```
分组输出使用Collectors.groupingBy()，它需要提供两个函数：一个是分组的key，这里使用s -> s.substring(0, 1)，表示只要首字母相同的String分到一组，第二个是分组的value，这里直接使用Collectors.toList()，表示输出为List，上述代码运行结果如下：
```java
{
    A=[Apple, Avocado, Apricots],
    B=[Banana, Blackberry],
    C=[Coconut, Cherry]
}
```


#### 其他操作


##### 排序


sorted()只是一个转换操作，它会返回一个新的Stream。
```java
public class Main {
    public static void main(String[] args) {
        List<String> list = List.of("Orange", "apple", "Banana")
            .stream()
            .sorted()
            .collect(Collectors.toList());
        System.out.println(list);
    }
}
```
此方法要求Stream的每个元素必须实现Comparable接口。如果要自定义排序，传入指定的Comparator即可：
```java
List<String> list = List.of("Orange", "apple", "Banana")
    .stream()
    .sorted(String::compareToIgnoreCase)
    .collect(Collectors.toList());
```


##### 去重


```java
List.of("A", "B", "A", "C", "B", "D")
    .stream()
    .distinct()
    .collect(Collectors.toList()); // [A, B, C, D]
```


##### 截取


截取操作也是一个转换操作，将返回新的Stream。截取操作常用于把一个无限的Stream转换成有限的Stream，skip()用于跳过当前Stream的前N个元素，limit()用于截取当前Stream最多前N个元素：
```java
List.of("A", "B", "C", "D", "E", "F")
    .stream()
    .skip(2) // 跳过A, B
    .limit(3) // 截取C, D, E
    .collect(Collectors.toList()); // [C, D, E]
```


##### 合并


将两个Stream合并为一个Stream可以使用Stream的静态方法concat()：
```java
Stream<String> s1 = List.of("A", "B", "C").stream();
Stream<String> s2 = List.of("D", "E").stream();
// 合并:
Stream<String> s = Stream.concat(s1, s2);
System.out.println(s.collect(Collectors.toList())); // [A, B, C, D, E]
```


##### flatMap


flatMap()，是指把Stream的每个元素（这里是List）映射为Stream，然后合并成一个新的Stream。
```java
Stream<List<Integer>> s = Stream.of(
        Arrays.asList(1, 2, 3),
        Arrays.asList(4, 5, 6),
        Arrays.asList(7, 8, 9));
        
Stream<Integer> i = s.flatMap(list -> list.stream());
```


##### 并行


常情况下，对Stream的元素进行处理是单线程的，即一个一个元素进行处理。但是很多时候，我们希望可以并行处理Stream的元素，因为在元素数量非常大的情况，并行处理可以大大加快处理速度。
把一个普通Stream转换为可以并行处理的Stream非常简单，只需要用parallel()进行转换：
```java
Stream<String> s = ...
String[] result = s.parallel() // 变成一个可以并行处理的Stream
                   .sorted() // 可以进行并行排序
                   .toArray(String[]::new);
```
经过parallel()转换后的Stream只要可能，就会对后续操作进行并行处理。我们不需要编写任何多线程代码就可以享受到并行处理带来的执行效率的提升。


##### 其他聚合方法


除了reduce()和collect()外，Stream还有一些常用的聚合方法：
* count()：用于返回元素个数；
* max(Comparator<? super T> cp)：找出最大元素；
* min(Comparator<? super T> cp)：找出最小元素。


针对IntStream、LongStream和DoubleStream，还额外提供了以下聚合方法：
* sum()：对所有元素求和；
* average()：对所有元素求平均数。


还有一些方法，用来测试Stream的元素是否满足以下条件：
* boolean allMatch(Predicate<? super T>)：测试是否所有元素均满足测试条件；
* boolean anyMatch(Predicate<? super T>)：测试是否至少有一个元素满足测试条件。


最后一个常用的方法是forEach()，它可以循环处理Stream的每个元素，我们经常传入System.out::println来打印Stream的元素：
```java
Stream<String> s = ...
s.forEach(str -> {
    System.out.println("Hello, " + str);
});
```

Stream常用操作

| 类型     | 操作                                                         |
| -------- | ------------------------------------------------------------ |
| 转换操作 | `map()`，`filter()`，`sorted()`，`distinct()`；              |
| 合并操作 | `concat()`，`flatMap()`；                                    |
| 并行处理 | `parallel()`；                                               |
| 聚合操作 | `reduce()`，`collect()`，`count()`，`max()`，`min()`，`sum()`，`average()`； |
| 其他操作 | allMatch()`, `anyMatch()`, `forEach()；                      |

