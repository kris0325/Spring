package com.google.springboot.service.cache;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author kris
 * @Create 2024-10-31 18:32
 * @Description
 */
public class LocalCacheMechanism {
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
        SimpleCache<String, ApiResponse> apiCache = new SimpleCache<>(LocalCacheMechanism::fetchFromApi);
        //method2
        SimpleCache<String, ApiResponse> apiResponseSimpleCache = new SimpleCache<>(key -> fetchFromApi(key));

        ApiResponse response1 = apiCache.get("key1");
        ApiResponse response2 = apiCache.get("key1");
        System.out.println("First call: " + response1.getData());
        System.out.println("Second call (cached): " + response2.getData());
    }

    public static void main(String[] args) {
        testSolution14();
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
        System.out.println(" fetch Data by call fetchFromApi " + UUID.randomUUID());
        return new ApiResponse("Data for " + key);
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

}
