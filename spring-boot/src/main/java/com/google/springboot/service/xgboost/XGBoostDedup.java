//package com.google.springboot.service.xgboost;
//
//import ml.dmlc.xgboost4j.java.*;
//import ml.dmlc.xgboost4j.java.XGBoost;
//import ml.dmlc.xgboost4j.java.DMatrix;
//
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVParser;
//import org.apache.commons.csv.CSVRecord;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * @Author kris
// * @Create 2024-06-20 00:02
// * @Description
// *
// * 参数详细解释
// * objective: 目标函数
// *
// * "binary:logistic"：表示二分类任务，输出是概率。
// * 适用于二分类问题，例如分类是否是重复商品。
// * 其他可能的目标函数包括回归、排名等。
// * max_depth: 树的最大深度
// *
// * 值越大，树的深度越深，模型越复杂。
// * 增大树的深度可能会提高训练集上的性能，但同时也会增加过拟合的风险。
// * 通常需要根据具体数据集进行调参，找到合适的深度。
// * eta: 学习率（步长）
// *
// * 学习率决定每次更新的步长，值越小，步长越小，模型学习速度越慢，但可能更稳定。
// * 学习率越小，通常需要更多的迭代次数（树的数量）来达到同样的效果。
// * 常见值范围是0.01到0.3之间。
// * eval_metric: 评价指标
// *
// * "logloss"：对数损失函数（log loss），主要用于二分类任务。
// * 对数损失函数可以度量模型预测概率与真实标签之间的差异。
// * 其他可能的评价指标包括错误率、AUC（ROC曲线下的面积）、均方误差等。
// * 可以根据任务选择合适的评价指标来评估模型性能。
// * 去重原理的具体分析
// * 商品去重的主要目标是识别出数据集中是否存在重复的商品记录。以下是本代码中商品去重的具体原理和步骤：
// *
// * 数据加载：
// *
// * 从CSV文件中读取商品数据，包括商品ID、名称、描述和价格等信息。
// * 文本特征提取：
// *
// * 使用TF-IDF（词频-逆文档频率）方法对商品名称和描述进行文本特征提取。
// * 生成每个商品的TF-IDF向量，这些向量表示商品的文本特征。
// * 相似度计算：
// *
// * 计算每对商品之间的余弦相似度，得到相似度矩阵。
// * 余弦相似度衡量两个商品文本特征向量之间的相似程度，相似度越高表示两个商品越相似。
// * 特征构建：
// *
// * 对每对商品计算相似度和价格差异，将这些特征作为XGBoost模型的输入。
// * 标签（是否重复）由商品对是否都是重复商品决定。
// * 模型训练和预测：
// *
// * 使用XGBoost训练模型，目标是预测商品对是否重复。
// * 模型通过学习相似度、价格差异等特征与标签之间的关系来进行分类。
// * 模型评价：
// *
// * 使用测试集评估模型性能，计算预测的准确性。
// * 准确性衡量模型正确预测重复商品对的比例。
// * 通过以上步骤，可以有效识别数据集中重复的商品记录，提高数据质量和后续数据分析的准确性。
// */
//
//
//public class XGBoostDedup {
//    public static void main(String[] args) {
//        // 数据集文件路径
//        String datasetPath = "xgboost_dedup_dataset.csv";
//        // 加载数据集到产品列表中
//        List<Product> products = loadDataset(datasetPath);
//
//        // 生成TF-IDF特征和余弦相似度矩阵
//        Map<String, Integer> wordIndex = new HashMap<>();
//        List<List<Integer>> tfidfVectors = generateTfidfVectors(products, wordIndex);
//        double[][] cosineSimilarities = computeCosineSimilarities(tfidfVectors);
//
//        // 创建相似度数据框
//        List<Similarity> similarities = new ArrayList<>();
//        for (int i = 0; i < cosineSimilarities.length; i++) {
//            for (int j = 0; j < cosineSimilarities.length; j++) {
//                if (i != j) {
//                    similarities.add(new Similarity(products.get(i).productId, products.get(j).productId, cosineSimilarities[i][j]));
//                }
//            }
//        }
//
//        // 为XGBoost生成数据集
//        List<Float> labels = new ArrayList<>();
//        List<float[]> features = new ArrayList<>();
//        for (Similarity sim : similarities) {
//            Product p1 = products.stream().filter(p -> p.productId.equals(sim.productId1)).findFirst().get();
//            Product p2 = products.stream().filter(p -> p.productId.equals(sim.productId2)).findFirst().get();
//            labels.add((float) (p1.isDuplicate && p2.isDuplicate ? 1.0 : 0.0));
//            features.add(new float[]{
//                    p1.price, p2.price, (float) Math.abs(p1.price - p2.price), (float) sim.similarity
//            });
//        }
//
//        // 数据集拆分为训练集和测试集
//        int trainSize = (int) (features.size() * 0.8);
//        DMatrix trainData = new DMatrix(features.subList(0, trainSize), trainSize, 4, 0.0f);
//        trainData.setLabel(labels.subList(0, trainSize));
//
//        DMatrix testData = new DMatrix(features.subList(trainSize, features.size()), features.size() - trainSize, 4, 0.0f);
//        testData.setLabel(labels.subList(trainSize, labels.size()));
//
//        // 训练XGBoost模型
//        HashMap<String, Object> params = new HashMap<>();
//
//// 设置目标函数，binary:logistic表示二分类任务，输出是概率
//        params.put("objective", "binary:logistic");
//
//// 设置树的最大深度，值越大，模型越复杂，可能导致过拟合
//        params.put("max_depth", 5);
//
//// 设置学习率（步长），控制每次迭代更新的步幅大小，值越小，模型学习越慢，需要更多迭代次数
//        params.put("eta", 0.1);
//
//// 设置评价指标，这里使用对数损失函数(log loss)评估模型性能
//        params.put("eval_metric", "logloss");
//
//// 定义训练和测试数据集的映射
//        HashMap<String, DMatrix> watches = new HashMap<>();
//        watches.put("train", trainData);  // 训练数据集
//        watches.put("test", testData);    // 测试数据集
//
//        try {
//            Booster booster = XGBoost.train(trainData, params, 100, watches, null, null);
//
//            // 预测并评估
//            float[][] predictions = booster.predict(testData);
//            List<Float> predLabels = Arrays.stream(predictions).map(p -> p[0] > 0.5 ? 1.0f : 0.0f).collect(Collectors.toList());
//            float accuracy = computeAccuracy(labels.subList(trainSize, labels.size()), predLabels);
//            System.out.println("Accuracy: " + accuracy);
//        } catch (XGBoostError e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 加载数据集
//    private static List<Product> loadDataset(String path) {
//        List<Product> products = new ArrayList<>();
//        try (FileReader reader = new FileReader(path);
//             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
//            for (CSVRecord record : csvParser) {
//                products.add(new Product(
//                        record.get("product_id"),
//                        record.get("name"),
//                        record.get("description"),
//                        Float.parseFloat(record.get("price")),
//                        false
//                ));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return products;
//    }
//
//    // 生成TF-IDF向量
//    private static List<List<Integer>> generateTfidfVectors(List<Product> products, Map<String, Integer> wordIndex) {
//        List<List<Integer>> tfidfVectors = new ArrayList<>();
//        int index = 0;
//        for (Product product : products) {
//            List<Integer> tfidfVector = new ArrayList<>();
//            String[] words = (product.name + " " + product.description).split(" ");
//            for (String word : words) {
//                if (!wordIndex.containsKey(word)) {
//                    wordIndex.put(word, index++);
//                }
//                tfidfVector.add(wordIndex.get(word));
//            }
//            tfidfVectors.add(tfidfVector);
//        }
//        return tfidfVectors;
//    }
//
//    // 计算余弦相似度
//    private static double[][] computeCosineSimilarities(List<List<Integer>> tfidfVectors) {
//        int size = tfidfVectors.size();
//        double[][] similarities = new double[size][size];
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                similarities[i][j] = computeCosineSimilarity(tfidfVectors.get(i), tfidfVectors.get(j));
//            }
//        }
//        return similarities;
//    }
//
//    // 计算两个向量的余弦相似度
//    private static double computeCosineSimilarity(List<Integer> vector1, List<Integer> vector2) {
//        Set<Integer> intersection = new HashSet<>(vector1);
//        intersection.retainAll(vector2);
//        return intersection.size() / Math.sqrt(vector1.size() * vector2.size());
//    }
//
//    // 计算预测准确性
//    private static float computeAccuracy(List<Float> labels, List<Float> predLabels) {
//        int correct = 0;
//        for (int i = 0; i < labels.size(); i++) {
//            if (labels.get(i).equals(predLabels.get(i))) {
//                correct++;
//            }
//        }
//        return (float) correct / labels.size();
//    }
//
//    // 商品类
//    static class Product {
//        String productId;
//        String name;
//        String description;
//        float price;
//        boolean isDuplicate;
//
//        public Product(String productId, String name, String description, float price, boolean isDuplicate) {
//            this.productId = productId;
//            this.name = name;
//            this.description = description;
//            this.price = price;
//            this.isDuplicate = isDuplicate;
//        }
//    }
//
//    // 相似度类
//    static class Similarity {
//        String productId1;
//        String productId2;
//        double similarity;
//
//        public Similarity(String productId1, String productId2, double similarity) {
//            this.productId1 = productId1;
//            this.productId2 = productId2;
//            this.similarity = similarity;
//        }
//    }
//}
