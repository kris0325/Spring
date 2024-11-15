package com.google.springboot.utils.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.springboot.model.postgresql.Employee;
import com.google.springboot.utils.TaskScheduleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author kris
 * @Create 2024-10-27 21:50
 * @Description Java8新特性Stream API详解
 * https://juejin.cn/post/6990525607188627486
 * <p>
 * https://juejin.cn/post/7129334698500685837
 */
public class JavaStreamAPIUtils {
    private static final Logger log = LoggerFactory.getLogger(JavaStreamAPIUtils.class);


    /**
     * the ways to create Stream
     */
    /**
     * 2.1 通过集合
     */
    public static List<Employee> createEmployeesStreamStreamsByCollection() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "張三", 23, "swe", 30d, 1));
        employees.add(new Employee(2L, "李四", 24, "swe", 40d, 2));
        employees.add(new Employee(3L, "王五", 25, "senior swe", 50d, 1));
        employees.add(new Employee(4L, "王六", 31, "senior swe", 10d, 4));
        employees.add(new Employee(5L, "李奧納多", 23, "senior swe", 50d, 2));
        employees.add(new Employee(6L, "麥可喬丹", 35, "senior swe", 60d, 4));

        Stream<Employee> stream;
        // 返回一个顺序流 （按照集合顺序获取）
        stream = employees.stream();
        // 返回一个并行流 （类似于线程去获取数据，无序）
        stream = employees.parallelStream();
        return employees;
    }

    /**
     * 2.2 通过數組
     */
    public static Stream<Employee> createEmployeesStreamStreamsByArray() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6};
        IntStream intStream = Arrays.stream(arr);
        Employee e1 = (new Employee(1L, "張三", 20, "swe", 10000d, 1));
        Employee e2 = (new Employee(2L, "李四", 24, "swe", 20000d, 2));
        Employee[] employees = new Employee[]{e1, e2};
        return Arrays.stream(employees);
    }

    /**
     * 2.3 通过Stream的of方法
     */
    public static Stream<Integer> createEmployeesStreamStreamsByStreamOf() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6);
        return integerStream;
    }


    /**
     * 2.4 通过无限流
     */
    public static void createEmployeesStreamByStream(String[] args) {
        // 生成偶数
        Stream.iterate(0, t -> t + 2).limit(10).forEach(System.out::println);
        // 10个随机数
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }

    /**
     * 三、Stream的API方法
     */
    /**
     * 3.1 filter
     */
    public static List<Employee> filterEmployees(List<Employee> allEmployees) {
        //筛选工资大于20000的员工：
        List<Employee> filterEmployees = allEmployees.stream().filter(e -> e.getSalary() > 20000d).collect(Collectors.toList());
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(filterEmployees);
            log.info("filter employees: {}", json);
        } catch (Exception e) {
            log.error("filter employees: {}", e.getMessage());
        }
        return filterEmployees;
    }

    /**
     * //3.2 limit
     */
    public static List<Employee> limitEmployees(List<Employee> allEmployees) {
        //输出集合元素数量：
        List<Employee> limitEmployees = allEmployees.stream().limit(2).collect(Collectors.toList());
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(limitEmployees);
            log.info("limitEmployees employees: {}", json);
        } catch (Exception e) {
            log.error("limitEmployees: {}", e.getMessage());
        }
        return limitEmployees;
    }

    /**
     * 3.3 skip 跳過前面的2个元素
     */
    public static List<Employee> skipEmployees(List<Employee> allEmployees) {
        //输出集合元素数量：
        List<Employee> limitEmployees = allEmployees.stream().skip(2).collect(Collectors.toList());

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(limitEmployees);
            log.info("skipEmployees employees: {}", json);
        } catch (Exception e) {
            log.error("skipEmployees: {}", e.getMessage());
        }
        return limitEmployees;
    }

    /**
     * 3.3 distinct
     */
    public static List<Employee> distinct(List<Employee> allEmployees) {
        //输出集合元素数量：
        List<Employee> distinct = allEmployees.stream().distinct().collect(Collectors.toList());
        printEmployees(distinct);

        return distinct;
    }

    /**
     * 3.5 map
     * 操作元素，比如大小写转换
     */
    public static List<Employee> map(List<Employee> allEmployees) {
        //输出集合元素数量：
        List<Employee> employees = allEmployees.stream().map(e -> {
            e.setSalary(e.getSalary() + 10000d);
            return e;
//                            e.getSalary()*2
        }).collect(Collectors.toList());
        printEmployees(employees);

        List<String> names = allEmployees.stream().map(Employee::getName).filter(name -> name.length() > 3).collect(Collectors.toList());
        System.out.println(names);

        List<String> list = Arrays.asList("a", "b", "c", "d");
        List<String> newList = list.stream().map(String::toUpperCase).collect(Collectors.toList());
        return employees;
    }

    /**
     * 3.6 排序
     */
    public static List<Employee> sorted(List<Employee> allEmployees) {

        //自定義比較器：按照年齡升序排序，如果年齡相同，則安全薪資升序排序
        List<Employee> sortedByAgeThenBySalary = allEmployees.stream().sorted((e1, e2) -> {
            int age = Integer.compare(e1.getAge(), e2.getAge());
            if (age != 0) {
                return age;
            } else {
                return Double.compare(e1.getSalary(), e2.getSalary());
            }
        }).collect(Collectors.toList());

        //直接使用jdk的 Comparator 按照年齡升序排序，如果年齡相同，則安全薪資升序排序
        List<Employee> sortedByAgeThenBySalary2 = allEmployees.stream()
                .sorted(Comparator.comparingInt(Employee::getAge).thenComparingDouble(Employee::getSalary))
                .collect(Collectors.toList());


        printEmployees(sortedByAgeThenBySalary);
        return sortedByAgeThenBySalary;
    }

    /**
     * 3.7 匹配与查找
     * @param args
     */
    /**
     *
     */
    public static List<Employee> findAndMatch(List<Employee> allEmployees) {
        boolean res;
        //allMatch：检查是否匹配所有元素：
        boolean allMatch = allEmployees.stream()
                .allMatch(e -> e.getAge() > 18);
        System.out.println(allMatch);

        //anyMatch：检查是否至少匹配一个元素
        boolean any = allEmployees.stream()
                .anyMatch(e -> e.getSalary() > 60000d);
        System.out.println(any);

        //anyMatch：检查是否至少匹配一个元素
        res = allEmployees.stream()
                .noneMatch(e -> e.getName().startsWith("張"));
        System.out.println(res);

        //findFirst：返回第一个元素
        List<Employee> employees = new ArrayList<>();
        Optional<Employee> first = employees.stream()
                .findFirst();
        Employee employee = new Employee();
        employee.setName(" Optional<Employee>");
//        printEmployee(first.orElse(employee));
        // java.util.NoSuchElementException: No value present
//        printEmployee(first.get());


        /**
         * findAny：返回当前流中的任意元素
         */
        Optional<Employee> employee1 = allEmployees.stream().findAny();
        printEmployee(employee1.orElse(employee));

        /**
         * count：返回流中元素的总个数
         */
        long count = allEmployees.stream()
                .filter(e -> e.getAge() > 25)
                .count();
        System.out.println(count);

        /**
         * max：返回流中的最大值
         */
        Optional<Double> maxSalary = allEmployees.stream()
                .map(Employee::getSalary)
                .max(Double::compare);
        System.out.println(maxSalary.orElse(00000d));

        /**
         * 8、min：返回流中的最小值
         */
        Optional<Employee> minSalary = allEmployees.stream()
                .min((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        printEmployee(minSalary.orElse(new Employee()));
        return allEmployees;
    }

    /**
     * 3.8 归约
     * <p>
     * reduce 方法的主要作用是将流中的元素聚合成一个单一的结果。它可以用于各种聚合操作，如求和、求积、查找最大值或最小值等12.
     * 适用场景
     * 数值计算：求和、求积、求最大值、最小值等。
     * 字符串拼接：将多个字符串合并成一个。
     * 复杂对象的聚合：例如，计算多个用户的平均评分。
     * 并行处理：在并行流中进行高效的聚合操作。
     * <p>
     * 注意事项
     * 1.性能考虑：对于简单的数值计算，专门的方法（如 sum()、max()、min()）可能比 reduce 更高效
     * 2.并行流：在使用并行流时，确保累加器和组合器是无状态且线程安全的
     * 3.类型转换：当需要在 reduce 操作中改变类型时，使用第三种重载形式
     * 结论
     * reduce 方法是 Stream API 中一个非常灵活和强大的工具，适用于各种聚合场景。它允许开发者以声明式的方式编写复杂的聚合逻辑，同时保持代码的简洁性和可读性。在处理大量数据或需要并行计算时，reduce 方法尤其有用
     *
     * @param args
     */
    public static List<Employee> reduce(List<Employee> allEmployees) {
        Optional<Double> min = allEmployees.stream()
                .map(Employee::getSalary)
                .reduce(Double::min);
        System.out.printf("min:" + min);

        Optional<Double> max = allEmployees.stream()
                .map(Employee::getSalary)
                .reduce(Double::max);
        System.out.printf("max:" + max);


        Optional<Double> reduce2Sum = allEmployees.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);

        Double sum = allEmployees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();
        //大数据集
        double sum2 = allEmployees.parallelStream()
                .mapToDouble(Employee::getSalary)
                .sum();
        double sum3 = allEmployees.parallelStream()
                .map(Employee::getSalary)
                .reduce(0.0, Double::sum);

        System.out.printf("reduce2Sum:" + sum);

        System.out.printf("reduce2Sum:" + reduce2Sum.orElse(0d));

        return allEmployees;
    }

    public static void parallelStreamThreadUnSafe(List<Employee> allEmployees) {
        // 错误示范：使用外部可变状态
        double[] total = {0.0};
        for (int i = 0; i < 100; i++) {
            allEmployees.parallelStream().forEach(e -> {
                total[0] += e.getSalary(); // 非线程安全操作
            });
        }

        System.out.println("Total salary (incorrect): " + total[0]);
    }

    /**
     * 3.9 收集 collect
     *
     * @param args
     */
    public static void collect(List<Employee> allEmployees) {
        List<Employee> collect = allEmployees.stream()
                .filter(e -> e.getSalary() > 20d)
                .collect(Collectors.toList());
        printEmployees(collect);
    }

    /**
     * 3.10 分组方法
     * 如果你想对数据进行分类，但是你指定的key是可以重复的，那么你应该使用groupingBy 而不是toMap。
     *
     * @param args
     */
    public static void groupingBy(List<Employee> allEmployees) {
        Map<Integer, List<Employee>> employee2DepartmentId = allEmployees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartmentId));
        printEmployees(employee2DepartmentId);
    }

    /**
     * 3.11 Collectors.toMap 构造映射
     *
     * @param args 使用 Collectors.toMap() 而不是 Collectors.groupingBy()。
     *             第一个参数 Employee::getName 是 key mapper，它指定了如何从 Employee 对象获取 map 的 key。
     *             第二个参数 employee -> employee 是 value mapper，它指定了如何从 Employee 对象获取 map 的 value。在这里，我们直接使用整个 Employee 对象作为 value。
     *             第三个参数 (existing, replacement) -> existing 是一个 merge function。它用于处理当有重复的 key 时应该如何处理。在这个例子中，我们选择保留现有的 entry。
     *             结果将是一个 Map<String, Employee>，其中 key 是员工的名字，value 是对应的 Employee 对象。
     *             注意：如果你确定所有的 Employee 名字都是唯一的，你可以简化这个调用，省略 merge function：
     */
    public static void name2Employee(List<Employee> allEmployees) {
        /**
         * stream().collect(Collectors.toMap(key, value), key重复时会报错
         * * java.lang.IllegalStateException: Duplicate key 王五
         */
        Map<String, Employee> name2Employee2 = allEmployees.stream()
                .collect(Collectors.toMap(Employee::getName, employee -> employee));

        Map<String, Employee> name2Employee = allEmployees.stream()
                .collect(Collectors.toMap(Employee::getName, employee -> employee, (existing, replacement) -> existing));

        printName2Employee(name2Employee);
    }


    public static void main(String[] args) {
        List<Employee> allEmployees = createEmployeesStreamStreamsByCollection();

        name2Employee(allEmployees);
    }

    public static void printName2Employee(Map<String, Employee> name2Employee) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(name2Employee);
            log.info(" name2Employee: {}", json);
        } catch (Exception e) {
            log.error("name2Employee: {}", e.getMessage());
        }
    }

    public static void printEmployees(List<Employee> employees) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(employees);
            log.info(" employees: {}", json);
        } catch (Exception e) {
            log.error("employees: {}", e.getMessage());
        }
    }

    public static void printEmployees(Map<Integer, List<Employee>> employee2DepartmentId) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(employee2DepartmentId);
            log.info(" employee2DepartmentId: {}", json);
        } catch (Exception e) {
            log.error("employee2DepartmentId: {}", e.getMessage());
        }
    }

    public static void print(List<String> employees) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(employees);
            log.info(" employees: {}", json);
        } catch (Exception e) {
            log.error("employees: {}", e.getMessage());
        }
    }

    public static void printEmployee(Employee employee) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(employee);
            log.info(" employee: {}", json);
        } catch (Exception e) {
            log.error("employee: {}", e.getMessage());
        }
    }


    public static void mapReduce() {
        /**
         * 您的理解是正确的。Java Stream 的 map 和 reduce 操作确实借鉴了 Google 的 MapReduce 编程模型的思想。这两个操作在概念上和用途上与 Google MapReduce 模型有很多相似之处：
         *
         * 1. Map 操作:
         *    - Google MapReduce: 用于将输入数据转换为键值对。
         *    - Java Stream map: 用于转换流中的每个元素。
         *
         * 2. Reduce 操作:
         *    - Google MapReduce: 用于合并所有具有相同键的值。
         *    - Java Stream reduce: 用于将流中的元素组合成一个单一的结果。
         *
         * 主要相似点:
         *
         * 1. 数据转换:
         *    - 两者都使用 map 来转换或处理数据。
         *
         * 2. 数据聚合:
         *    - 两者都使用 reduce 来聚合或合并数据。
         *
         * 3. 并行处理:
         *    - Google MapReduce 设计用于分布式系统中的并行处理。
         *    - Java Stream 可以通过 parallel() 方法支持并行处理。
         *
         * 4. 函数式编程思想:
         *    - 两者都采用了函数式编程的思想，将操作抽象为函数。
         *
         * 主要区别:
         *
         * 1. 规模和用途:
         *    - Google MapReduce 主要用于大规模分布式数据处理。
         *    - Java Stream 主要用于单机环境下的内存数据处理。
         *
         * 2. 实现复杂度:
         *    - Google MapReduce 是一个复杂的分布式系统框架。
         *    - Java Stream 是语言级别的 API，使用相对简单。
         *
         * 3. 数据源:
         *    - Google MapReduce 通常处理存储在分布式文件系统中的数据。
         *    - Java Stream 通常处理内存中的集合或其他数据源。
         *
         * 总的来说，Java Stream 的 map 和 reduce 操作确实在概念上借鉴了 Google MapReduce 的思想，但进行了简化和适应，使其更适合在单机 Java 程序中使用。这种设计让 Java 开发者可以用类似 MapReduce 的方式来处理数据，但无需处理分布式系统的复杂性。
         *
         */
        List<String> words = Arrays.asList("hello", "world", "java", "stream");

        String result = words.stream()
                // 使用map将每个单词转换为大写
                .map(String::toUpperCase)
                // 使用reduce连接所有单词,用空格分隔
                .reduce("", (a, b) -> a.isEmpty() ? b : a + " " + b);

        System.out.println("结果: " + result);

    }

    public static void ImmutableCollections() {
        List<String> list = List.of("a", "b", "c");
        list.forEach(System.out::println);
//        list.add("1");
//        list.remove(0);
    }

    /**
     * 惰性求值确实能够提高效率,主要有以下几个原因:
     * 1避免不必要的计算：
     * 在惰性求值中,中间操作不会立即执行,而是等到真正需要结果的终止操作时才执行。这意味着如果最终不需要某些中间结果,那么相关的计算就完全不会发生,从而避免了不必要的计算13。
     * 2合并操作：
     * 惰性求值允许Stream API在内部优化操作链。它可以将多个操作合并在一起,减少迭代次数。例如,多个filter操作可能会被合并成一个单一的过滤步骤1。
     * 3短路求值：
     * 某些操作(如findFirst()或limit())可能不需要处理整个流就能得到结果。惰性求值使得这些操作可以在找到满足条件的元素后立即停止处理,而不是处理整个数据集2。
     * 4并行处理优化：
     * Lazy Evaluation 惰性求值为并行处理提供了更多优化机会。由于操作链在终止操作之前不会执行,Stream API可以更好地规划如何在多个线程之间分配工作4。
     * 5内存效率：
     * 对于大型数据集,惰性求值可以减少中间结果的内存占用。因为中间结果不需要全部存储,只在需要时才生成3。
     * 举个例子:
     * <p>
     * 在这个例子中,如果是立即求值,Stream会先对所有元素进行filter操作,然后对所有偶数进行map操作,最后才找到第一个元素。但是通过惰性求值,Stream只需要处理到第一个偶数(2),对其进行平方操作后就可以立即返回结果,无需处理后续元素,大大提高了效率12。
     * 总之,惰性求值通过推迟计算、优化操作顺序和减少不必要的处理,在很多情况下可以显著提高Stream操作的效率。
     */
    public static void lazyEvaluation() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int result = numbers.stream().filter(n -> n % 2 == 0).map(n -> n * n).findFirst().orElse(0);
    }

    public static void peekAndForeach(){
        /**
         * 代码说明
         * 使用 peek()：
         *
         * 流过滤出以 "C" 开头的名称，并使用 peek() 打印每个过滤后的名称。这表明 peek() 允许检查，同时仍然允许进一步处理。
         * 最后调用 forEach() 打印转换后的名称。
         * 使用 forEach()：
         *
         * 创建一个新流，过滤出以 "D" 开头的名称，并使用 forEach() 打印每个找到的名称。
         * 在这里，没有中间操作进行观察，直接消费流。
         * 总结
         * 总之，使用 peek() 可以在不消耗流的情况下检查或调试流中的元素，而使用 forEach() 则是对流中的元素执行最终操作，完全消耗流。
         */
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        // 使用 peek() 进行调试
        System.out.println("使用 peek():");
        names.stream()
                .filter(name -> name.startsWith("C"))
                .peek(name -> System.out.println("过滤后的名称: " + name)) // 调试操作
                .map(String::toUpperCase) // 转换名称
                .forEach(name -> System.out.println("最终名称: " + name)); // 消费流

        System.out.println("\n使用 forEach():");
        // 直接使用 forEach()
        names.stream()
                .filter(name -> name.startsWith("D"))
                .forEach(name -> System.out.println("找到的名称: " + name)); // 消费流
    }
}
