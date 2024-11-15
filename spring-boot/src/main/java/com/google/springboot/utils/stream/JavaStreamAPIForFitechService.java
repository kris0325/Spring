package com.google.springboot.utils.stream;

import java.time.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author kris
 * @Create 2024-10-30 21:20
 * @Description
 */
public class JavaStreamAPIForFitechService {

    // 静态内部类
    static class Transaction {
        private String id;
        private double amount;
        private Long timestamp;
        private TransactionType type;
        private Customer customer;

        public Transaction(String id, double amount, TransactionType type, Customer customer) {
            this.id = id;
            this.amount = amount;
            this.type = type;
            this.customer = customer;
        }
        public Long getTimestamp() {
            return timestamp;
        }
        public void setAmount(double amount) {
            this.amount = amount;
        }

        public void setType(TransactionType type) {
            this.type = type;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public String getId() {
            return id;
        }

        public double getAmount() {
            return amount;
        }

        public TransactionType getType() {
            return type;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void process() {
            System.out.println("Processing transaction: " + id);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Transaction that = (Transaction) o;
            return Double.compare(that.amount, amount) == 0 &&
                    Objects.equals(id, that.id) &&
                    type == that.type &&
                    Objects.equals(customer, that.customer);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, amount, type, customer);
        }
    }

    static class Customer {
        private String name;

        public Customer(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    static class BankAccount {
        private double balance;

        public BankAccount(double balance) {
            this.balance = balance;
        }

        public double getBalance() {
            return balance;
        }
    }

    static class AccountInfo {
        private String accountId;

        public AccountInfo(String accountId) {
            this.accountId = accountId;
        }

        public String getAccountId() {
            return accountId;
        }
    }

    enum TransactionType {
        DEBIT, CREDIT
    }

    static class ApiResponse {
        private String data;

        public ApiResponse(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }
    }

    // 自定义异常
    static class TransactionException extends Exception {
        public TransactionException(String message) {
            super(message);
        }
    }

    // 函数式接口
    @FunctionalInterface
    interface TransactionProcessor {
        void process(Transaction transaction) throws TransactionException;

        default void safeProcess(Transaction transaction) {
            try {
                process(transaction);
            } catch (TransactionException e) {
                System.out.println("Error processing transaction: " + e.getMessage());
            }
        }
    }

    // 测试方法
    public static void main(String[] args) {
        // 创建测试数据
        List<Transaction> transactions = createTestTransactions();
//        double a = transactions.stream()
////                .map(Transaction::getAmount)
//                .collect(Collectors.averagingDouble(Transaction::getAmount));
//        System.out.println(a);
        List<BankAccount> accounts = createTestBankAccounts();

        // 测试解决方案1
//        testSolution1(transactions);

//        // 测试解决方案2
//        testSolution2();
//
//        // 测试解决方案3
//        testSolution3(transactions);
//
//        // 测试解决方案4
//        testSolution4(transactions);
//
//        // 测试解决方案5
//        testSolution5(accounts);
//
//        // 测试解决方案6
//        testSolution6(transactions);
//
//        // 测试解决方案7
//        testSolution7(transactions);
//
//        // 测试解决方案8
//        testSolution8(transactions);
//
//        // 测试解决方案9
//        testSolution9();
//
//        // 测试解决方案10
//        testSolution10();
//
//        // 测试解决方案11
//        testSolution11();
//
//        // 测试解决方案12
//        testSolution12(transactions);
//
//        // 测试解决方案13
//        testSolution13(transactions);
//
//        // 测试解决方案14
//        testSolution14();
//
//        // 测试解决方案15
//        testSolution15(transactions);

//        // 测试解决方案16
//        testSolution16();
//
//        // 测试解决方案17
        testSolution17();
//
//        // 测试解决方案18
//        testSolution18();
//
//        // 测试解决方案19
//        testSolution19(transactions);
//
//        // 测试解决方案20
//        testSolution20(transactions);
    }

    // 创建测试数据
    private static List<Transaction> createTestTransactions() {
        Customer c1 = new Customer("Alice");
        Customer c2 = new Customer("Bob");
        Customer c3 = new Customer("Charlie");

        return Arrays.asList(
                new Transaction("T1", 100.0, TransactionType.DEBIT, c1),
                new Transaction("T2", 200.0, TransactionType.CREDIT, c2),
                new Transaction("T3", 150.0, TransactionType.DEBIT, c3),
                new Transaction("T4", 300.0, TransactionType.CREDIT, c1),
                new Transaction("T5", 50.0, TransactionType.DEBIT, c2)
        );
    }

    private static List<BankAccount> createTestBankAccounts() {
        return Arrays.asList(
                new BankAccount(1000.0),
                new BankAccount(2000.0),
                new BankAccount(3000.0)
        );
    }

    /**
     * 1 Question: How would you use Java Stream API to filter a list of transactions and sum the amounts?
     *
     * @param transactions
     */
    private static void testSolution1(List<Transaction> transactions) {
        System.out.println("Testing Solution 1:");
        double sum = transactions.stream()
                .filter(t -> t.getType() == TransactionType.DEBIT)
                .mapToDouble(Transaction::getAmount)
                .sum();
        System.out.println("Sum of DEBIT transactions: " + sum);
    }

    private static void testSolution2() {
        System.out.println("\nTesting Solution 2:");
        System.out.println("Intermediate operations: filter, map");
        System.out.println("Terminal operations: forEach, collect");
    }

    //3. Question: How would you use parallel streams to improve performance when processing large datasets?
    private static void testSolution3(List<Transaction> transactions) {
        System.out.println("\nTesting Solution 3:");
        double sum = transactions.parallelStream()
                .filter(t -> t.getAmount() > 100)
                .mapToDouble(Transaction::getAmount)
                .sum();
        System.out.println("Sum of transactions > 100: " + sum);
    }

    //4. Question: Implement a method to find the top 3 customers by transaction volume using Stream API.
    private static List<Customer> testSolution4(List<Transaction> transactions) {
        System.out.println("\nTesting Solution 4:");
        List<Customer> top3Customers = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCustomer,
                        Collectors.summingDouble(Transaction::getAmount)))
                .entrySet().stream()
                .sorted(Map.Entry.<Customer, Double>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

//        List<Customer> top3Customers = getTop3Customers(transactions);
        System.out.println("Top 3 customers: " + top3Customers.stream().map(Customer::getName).collect(Collectors.joining(", ")));
        return top3Customers;
    }

    private static List<Customer> getTop3Customers(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCustomer,
                        Collectors.summingDouble(Transaction::getAmount)))
                .entrySet().stream()
                .sorted(Map.Entry.<Customer, Double>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    //5. Question: How would you use Optional to handle null values in a stream of bank accounts?
    private static void testSolution5(List<BankAccount> accounts) {
        System.out.println("\nTesting Solution 5:");
        Optional<BankAccount> highestBalance = accounts.stream()
                .max(Comparator.comparing(BankAccount::getBalance));

        Optional<Double> ba = accounts.stream()
                .map(BankAccount::getBalance)
                .max(Double::compare);

        highestBalance.ifPresent(account -> System.out.println("Highest balance: " + account.getBalance()));
    }

    /**
     * 6. Question: Explain the concept of method references and provide an example in the context of financial transactions.
     *
     * Solution: Method references are shorthand notations of lambda expressions to call methods.
     * @param transactions
     */
    private static void testSolution6(List<Transaction> transactions) {
        System.out.println("\nTesting Solution 6:");
        transactions.forEach(Transaction::process);
    }

    /**
     * 7. Question: How would you use Stream API to group transactions by type and calculate the total amount for each type?
     * @param transactions
     */
    private static void testSolution7(List<Transaction> transactions) {
        System.out.println("\nTesting Solution 7:");
        Map<TransactionType, DoubleSummaryStatistics> amountSummaryStatistics2Type = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getType
                        ,Collectors.summarizingDouble(Transaction::getAmount)));
        amountSummaryStatistics2Type.get(TransactionType.CREDIT).getSum();
        amountSummaryStatistics2Type.get(TransactionType.CREDIT).getAverage();
        amountSummaryStatistics2Type.get(TransactionType.CREDIT).getCount();
        amountSummaryStatistics2Type.get(TransactionType.CREDIT).getMax();
        Map<TransactionType, Double> totalsByType = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getType,
                        Collectors.summingDouble(Transaction::getAmount)));
        System.out.println("Totals by type: " + totalsByType);
    }

    /**
     * 8. Question: Implement a custom collector to calculate the average transaction amount while filtering out outliers.
     * @param transactions
     */
    private static void testSolution8(List<Transaction> transactions) {
        System.out.println("\nTesting Solution 8:");
        // Implement AverageWithoutOutliersCollector here
       Double averagingDouble  = transactions.stream().filter(transaction -> "kris".equals(transaction.getCustomer().getName()))
                        .collect(Collectors.averagingDouble(Transaction::getAmount));
        Double averagingDouble2  = transactions.stream().filter(transaction -> "kris".equals(transaction.getCustomer().getName()))
                .mapToDouble(Transaction::getAmount)
                .average().getAsDouble();
        System.out.println("Average without outliers: Not implemented");
    }

    /**
     * 9. Question: How would you use CompletableFuture to process multiple API calls concurrently?
     * CompletableFuture 来并发处理多个 API 调用。这是现代应用程序中的一个常见场景,尤其是在需要同时从多个源获取数据的情况下。
     * 解决方案解释
     * 创建并发 API 调用
     * 创建了两个 CompletableFuture 对象:
     * accountFuture: 异步获取账户信息。
     * transactionsFuture: 异步获取交易信息。
     * 这两个 future 都使用 CompletableFuture.supplyAsync() 在单独的线程上执行各自的 API 调用（fetchAccountInfo 和 fetchTransactions）。
     * 组合 Futures
     * 使用 CompletableFuture.allOf() 创建一个新的 CompletableFuture (allOf),它会在 accountFuture 和 transactionsFuture 都完成时完成。
     * 处理结果
     * 使用 thenRun() 方法链接到 allOf,指定两个 future 都完成后应该执行的操作。
     * 在 thenRun() 内部,调用两个 future 的 join() 方法来获取它们的结果。
     * 然后处理并打印结果,显示账户 ID 和交易数量。
     * 等待完成
     * 最后,在整个链上调用 join() 以等待整个过程完成。
     * 关键概念
     * 异步执行: API 调用是并发进行的,提高了效率。
     * 组合 Futures: 使用 allOf() 等待多个 future 完成。
     * 非阻塞: 主线程在等待 API 调用完成时不会被阻塞。
     * 结果处理: 只有在所有 future 都完成后才处理结果。
     * 这个解决方案有效地展示了如何使用 CompletableFuture 来处理多个并发 API 调用,组合它们的结果,并高效地处理这些结果。这在需要从不同来源获取相关数据并一起处理的场景中特别有用。
     */
    private static void testSolution9() {
        System.out.println("\nTesting Solution 9:");
        CompletableFuture<AccountInfo> accountFuture = CompletableFuture
                .supplyAsync(() -> fetchAccountInfo("123"));
        CompletableFuture<List<Transaction>> transactionsFuture = CompletableFuture
                .supplyAsync(() -> fetchTransactions("123"));

        CompletableFuture<Void> allOf = CompletableFuture.allOf(accountFuture, transactionsFuture);
        allOf.thenRun(() -> {
            AccountInfo account = accountFuture.join();
            List<Transaction> transactions = transactionsFuture.join();
            System.out.println("Processed account: " + account.getAccountId() + " with " + transactions.size() + " transactions");
        }).join();
    }

    private static AccountInfo fetchAccountInfo(String accountId) {
        return new AccountInfo(accountId);
    }

    private static List<Transaction> fetchTransactions(String accountId) {
        return createTestTransactions();
    }

    /**
     *10. Question: Explain how you would implement rate limiting for API calls using Java 8 features.
     */
    private static void testSolution10() {
        System.out.println("\nTesting Solution 10:");
        RateLimiter rateLimiter = new RateLimiter();
        for (int i = 0; i < 110; i++) {
            boolean allowed = rateLimiter.allowRequest("api1");
            if (i % 10 == 0) {
                System.out.println("Request " + i + " allowed: " + allowed);
            }
        }
    }

    static class RateLimiter {
        private final ConcurrentHashMap<String, List<Long>> requestTimestamps = new ConcurrentHashMap<>();

        public boolean allowRequest(String apiKey) {
            long now = System.currentTimeMillis();
            requestTimestamps.compute(apiKey, (k, v) -> {
                if (v == null) {
                    v = new ArrayList<>();
                }
                v.add(now);
                v = v.stream().filter(timestamp -> now - timestamp < 60000).collect(Collectors.toList());
                return v;
            });
            return requestTimestamps.get(apiKey).size() <= 100; // 100 requests per minute
        }
    }

    /**
     * 11. Question: How would you use Java 8 Date/Time API to calculate the number of business days between two dates?
     */
    private static void testSolution11() {
        LocalDate now = LocalDate.now();
        System.out.println("当前日期: " + now);

        LocalTime nowTime = LocalTime.now();
        System.out.println("当前日期nowTime: " + nowTime);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("当前日期localDateTime: " + localDateTime);

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.printf("当前日期zonedDateTime: " + zonedDateTime);

        Instant instant = zonedDateTime.toInstant();
        System.out.printf("当前instant: " + instant);

        long toEpochMilli  = instant.toEpochMilli();
        System.out.printf("当前instant: " + toEpochMilli);

        LocalTime time1 = LocalTime.of(10, 30, 0);
        LocalTime time2 = LocalTime.of(13, 35, 0);
        Duration duration = Duration.between(time1, time2);
        System.out.printf("当前duration: " + duration.getSeconds());

        // Period示例：计算两个LocalDate之间的差距
        LocalDate date1 = LocalDate.of(2023, 1, 1);
        LocalDate date2 = LocalDate.of(2024, 3, 15);

        Period period = Period.between(date1, date2);
        System.out.printf("当前period: " + period.getDays());

        System.out.println("\nTesting Solution 11:");
        LocalDate start = LocalDate.of(2023, 10, 1);
        LocalDate end = LocalDate.of(2023, 10, 31);
        long businessDays = getBusinessDaysBetween(start, end);
        System.out.println("Business days between " + start + " and " + end + ": " + businessDays);
    }

    public static long getBusinessDaysBetween(LocalDate start, LocalDate end) {
        return start.datesUntil(end)
                .filter(date -> date.getDayOfWeek() != DayOfWeek.SATURDAY
                        && date.getDayOfWeek() != DayOfWeek.SUNDAY)
                .count();
    }

    /**
     * 12. Question: Implement a custom functional interface for processing financial transactions with error handling.
     * @param transactions
     */
    private static void testSolution12(List<Transaction> transactions) {
        System.out.println("\nTesting Solution 12:");
        TransactionProcessor processor = t -> {
            if (t.getAmount() < 0) {
                throw new TransactionException("Negative amount");
            }
            System.out.println("Processed transaction: " + t.getId());
        };
        transactions.forEach(processor::safeProcess);
    }

    /**
     * 13. Question: How would you use Stream API to implement pagination for a large dataset of transactions?
     * @param transactions
     * 对于大多数用例，特别是处理大型数据集时，skip 和 limit 方法通常更高效，是 Java 流中进行分页的推荐方法。
     */
    private static void testSolution13(List<Transaction> transactions) {
        System.out.println("\nTesting Solution 13:");
        List<Transaction> pagedTransactions = getPagedTransactions(transactions, 2, 1);
        System.out.println("Paged transactions: " + pagedTransactions.stream().map(Transaction::getId).collect(Collectors.joining(", ")));
    }

    public static List<Transaction> getPagedTransactions(List<Transaction> allTransactions, int pageSize, int pageNumber) {
        return allTransactions.stream()
                .skip((long) (pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    /**
     * 利用了Java 8的特性 实现了一个简单的API响应缓存机制,
     * 适用场景 : 这种缓存机制适用于需要频繁访问相同API数据的场景。
     *14. Question: Explain how you would use Java 8 features to implement a simple caching mechanism for API responses.
     *
     * 减少重复的API调用,降低服务器负载。
     * 1 提高应用响应速度,特别是对于频繁查询API的key。
     * 2 在API暂时不可用时,仍能提供缓存的数据。
     * 3 通过这种方式,应用可以在保持数据相对新鲜的同时,显著提升用户体验和系统性能
     *
     * SimpleCache的实现确实利用了一些函数式编程的概念,主要体现在以下几个方面:
     *
     * 1. **不可变性和纯函数**:
     *    SimpleCache的核心逻辑是通过纯函数实现的。`get`方法不会修改外部状态,只依赖输入参数返回结果,这符合纯函数的定义[1]。
     *
     * 2. **高阶函数**:
     *    构造函数接受一个`Function`对象作为参数,这是高阶函数的典型应用。它允许我们将行为参数化,提高了代码的灵活性和可复用性[3]。
     *
     * 3. **惰性求值**:
     *    通过`computeIfAbsent`方法实现了惰性求值。只有在需要时才会计算和缓存值,避免了不必要的计算[1]。
     *
     * 4. **函数组合**:
     *    SimpleCache将缓存逻辑和实际的计算逻辑(由外部提供的函数)组合在一起,体现了函数组合的思想。
     *
     * 使用函数式编程实现SimpleCache的好处包括:
     *
     * 1. **代码简洁性**:
     *    函数式的实现通常更加简洁和表达力强,减少了样板代码[3]。
     *
     * 2. **易于理解和维护**:
     *    纯函数和不可变性使得代码更容易理解和推理,降低了维护难度[1]。
     *
     * 3. **并发安全**:
     *    不可变性和纯函数的特性使得代码天然具有线程安全性,特别适合并发环境[1]。
     *
     * 4. **可测试性**:
     *    纯函数易于单元测试,因为它们没有副作用,输入输出关系明确[1]。
     *
     * 5. **灵活性和可扩展性**:
     *    高阶函数的使用使得缓存机制更加灵活,可以轻松适应不同的计算逻辑[3]。
     *
     * 6. **性能优化**:
     *    通过惰性求值和缓存,可以避免重复计算,提高程序性能[1][3]。
     *
     * 7. **更好的抽象**:
     *    函数式编程提供了更高层次的抽象,使得代码更加模块化和可复用[3]。
     *
     * 总的来说,SimpleCache的函数式实现结合了函数式编程的多个优点,使得代码更加简洁、可靠、高效,同时保持了良好的可维护性和可扩展性。这种实现方式特别适合处理计算密集型或需要频繁访问的操作,能够显著提升应用程序的性能和响应速度。
     *
     * Citations:
     * [1] https://www.cnblogs.com/xtt321/p/14220813.html
     * [2] https://course.ece.cmu.edu/~ece740/gem5/part2/simplecache.html
     * [3] https://www.cnblogs.com/star91/p/han-shu-shi-bian-cheng--han-shu-huan-cun.html
     * [4] https://llh911001.gitbooks.io/mostly-adequate-guide-chinese/content/ch3.html
     * [5] https://blog.csdn.net/niceHack/article/details/128486824
     * [6] https://github.com/samonxian/blog/issues/4
     * [7] https://111hunter.github.io/2020-09-02-js-functional/
     * [8] https://zh.wikipedia.org/zh-hans/%E5%87%BD%E6%95%B0%E5%BC%8F%E7%BC%96%E7%A8%8B
     */
    private static void testSolution14() {
        System.out.println("\nTesting Solution 14:");
        //method1
        SimpleCache<String, ApiResponse> apiCache = new SimpleCache<>(JavaStreamAPIForFitechService::fetchFromApi);
        //method2
        SimpleCache<String, ApiResponse> apiResponseSimpleCache = new SimpleCache<>(key -> fetchFromApi(key));

        ApiResponse response1 = apiCache.get("key1");
        ApiResponse response2 = apiCache.get("key1");
        System.out.println("First call: " + response1.getData());
        System.out.println("Second call (cached): " + response2.getData());
    }

    static class SimpleCache<K, V> {
        private final Map<K, V> cache = new ConcurrentHashMap<>();
        private final Function<K, V> computeFunction;

        public SimpleCache(Function<K, V> computeFunction) {
            this.computeFunction = computeFunction;
        }

        public V get(K key) {
            return cache.computeIfAbsent(key, computeFunction);
        }
    }

    private static ApiResponse fetchFromApi(String key) {
        return new ApiResponse("Data for " + key);
    }

    /**
     * 15. Question: How would you use Stream API to implement a basic fraud detection system that flags suspicious transactions?
     */
    private static void testSolution15(List<Transaction> transactions) {
        System.out.println("\nTesting Solution 15:");
        List<Transaction> suspiciousTransactions = detectSuspiciousTransactions(transactions);
        System.out.println("Suspicious transactions: " + suspiciousTransactions.stream().map(Transaction::getId).collect(Collectors.joining(", ")));
    }

    public static List<Transaction> detectSuspiciousTransactions(List<Transaction> transactions) {
        double averageAmount = transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .average()
                .orElse(0.0);

        double a = transactions.stream()
//                .map(Transaction::getAmount)
                .collect(Collectors.averagingDouble(Transaction::getAmount));

        double threshold = averageAmount * 3; // Flag transactions 3 times above average

        return transactions.stream().filter(t -> t.getAmount() > threshold)
                .collect(Collectors.toList());
    }

    /**
     * 16. Question: Implement a method to calculate compound interest using Java 8 features.
     */
    private static void testSolution16() {
        System.out.println("\nTesting Solution 16:");
        double compoundInterest = calculateCompoundInterest(1000, 0.05, 5);
        System.out.println("Compound interest: " + compoundInterest);
    }

    public static double calculateCompoundInterest(double principal, double rate, int years) {
        return IntStream.range(0, years)
                .mapToDouble(year -> principal * Math.pow(1 + rate, year))
                .sum();
    }

    /**
     * 17. Question: How would you use Java 8 features to implement a simple event-driven system for processing financial transactions?
     */
    private static void testSolution17() {
        System.out.println("\nTesting Solution 17:");
        TransactionEventSystem eventSystem = new TransactionEventSystem();
        eventSystem.addEventListener("DEBIT", t -> System.out.println("Debit transaction processed: " + t.getId()));
        eventSystem.addEventListener("CREDIT", t -> System.out.println("Credit transaction processed: " + t.getId()));

        Transaction debitTx = new Transaction("D1", 100, TransactionType.DEBIT, new Customer("Alice"));
        Transaction creditTx = new Transaction("C1", 200, TransactionType.CREDIT, new Customer("Bob"));

        eventSystem.processTransaction(debitTx);
        eventSystem.processTransaction(creditTx);
    }

    static class TransactionEventSystem {
        private final Map<String, List<Consumer<Transaction>>> eventListeners = new ConcurrentHashMap<>();

        public void addEventListener(String eventType, Consumer<Transaction> listener) {
            eventListeners.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(listener);
        }

        public void processTransaction(Transaction transaction) {
            eventListeners.getOrDefault(transaction.getType().toString(), Collections.emptyList())
                    .forEach(listener -> listener.accept(transaction));
        }
    }

    /**
     * 18. Question: Explain how you would use Java 8 features to implement a basic retry mechanism for API calls.
     */
    private static void testSolution18() {
        System.out.println("\nTesting Solution 18:");
        try {
            ApiResponse response = retryOperation(() -> callApi(), 3);
            System.out.println("API response: " + response.getData());
        } catch (RuntimeException e) {
            System.out.println("API call failed after retries: " + e.getMessage());
        }
    }

    public static <T> T retryOperation(Supplier<T> operation, int maxRetries) {
        return IntStream.range(0, maxRetries)
                .mapToObj(i -> {
                    try {
                        return operation.get();
                    } catch (Exception e) {
                        if (i == maxRetries - 1) throw new RuntimeException("Max retries reached", e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Operation failed after max retries"));
    }

    private static ApiResponse callApi() {
        // Simulate API call with random failure
        if (Math.random() < 0.7) {
            throw new RuntimeException("API call failed");
        }
        return new ApiResponse("Success");
    }

    /**
     * 19. Question: How would you use Stream API to implement a basic reconciliation system that matches transactions from two different sources?
     * @param transactions
     */
    private static void testSolution19(List<Transaction> transactions) {
        System.out.println("\nTesting Solution 19:");
        List<Transaction> source1 = transactions.subList(0, 3);
        List<Transaction> source2 = new ArrayList<>(transactions.subList(1, 4));
        source2.get(1)
                .setAmount(source2.get(1).getAmount() + 10); // Introduce a discrepancy

        List<Transaction> unmatched = reconcileTransactions(source1, source2);
        System.out.println("Unmatched transactions: "
                + unmatched.stream()
                .map(Transaction::getId)
                .collect(Collectors.joining(", ")));
    }

    public static List<Transaction> reconcileTransactions(List<Transaction> source1, List<Transaction> source2) {
        Map<String, Transaction> source2Map = source2.stream()
                .collect(Collectors.toMap(Transaction::getId, t -> t));

        return source1.stream()
                .filter(t1 -> {
                    Transaction t2 = source2Map.get(t1.getId());
                    return t2 == null || !t1.equals(t2);
                })
                .collect(Collectors.toList());
    }

    /**
     * 20. Question: Implement a method to calculate the running balance of an account given a list of transactions using Stream API.
     * @param transactions
     * 這個方法接收一個交易列表和初始餘額，然後計算並返回每次交易後的賬戶餘額。
     */
    private static void testSolution20(List<Transaction> transactions) {
        System.out.println("\nTesting Solution 20:");
        List<Double> runningBalances = calculateRunningBalance(transactions, 1000);
        System.out.println("Running balances: " + runningBalances);
    }

    public static List<Double> calculateRunningBalance(List<Transaction> transactions, double initialBalance) {
        /**
         * 您的理解是正確的。一般來說,不建議在 lambda 表達式中修改外部變量。這是基於以下幾個原因:
         *
         * 1. Java 語言規範的要求:
         *    Lambda 表達式中使用的外部局部變量必須是 final 或 effectively final 的[1][3]。這是 Java 語言設計的一個限制。
         *
         * 2. 線程安全性考慮:
         *    不允許修改外部變量可以避免潛在的並發問題,特別是在使用並行流(parallel streams)時[2]。
         *
         * 3. 函數式編程理念:
         *    Lambda 表達式源於函數式編程範式,該範式強調不可變性和無副作用[3]。
         *
         * 4. 代碼可讀性:
         *    允許修改外部變量可能導致代碼難以理解和維護,因為變量的值可能在不同地方被改變[3]。
         *
         * 5. 一致性:
         *    保持 lambda 表達式的行為與匿名內部類一致,後者也不允許修改外部局部變量[1]。
         *
         * 然而,在某些情況下,確實需要在 lambda 中"修改"外部狀態。這就是為什麼使用 `AtomicReference` 或類似的方法成為一種變通方案:
         *
         * 1. `AtomicReference` 允許我們在技術上遵守規則的同時,實現對外部狀態的"修改"[4]。
         *
         * 2. 這種方法實際上是創建了一個對象的引用,而不是直接修改變量,從而滿足了 effectively final 的要求[4]。
         *
         * 3. 使用 `AtomicReference` 還能確保在並發環境中的線程安全性[2]。
         *
         * 總的來說,雖然 `AtomicReference` 提供了一種在 lambda 中"修改"外部狀態的方法,但它應該謹慎使用。在大多數情況下,設計不需要修改外部狀態的 lambda 表達式是更好的做法,這樣可以保持代碼的簡潔性、可讀性和函數式編程的原則。
         */
        //使用 AtomicReference：這允許在 lambda 表達式中安全地修改外部變量。
        AtomicReference<Double> balance = new AtomicReference<>(initialBalance);
        //先按照交易時間排序
        return transactions.stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp))
                .map(t -> {
                    double newBalance = balance.get() + (t.getType() == TransactionType.CREDIT ? t.getAmount() : -t.getAmount());
                    balance.set(newBalance);
                    return newBalance;
                })
                .collect(Collectors.toList());
    }
}
