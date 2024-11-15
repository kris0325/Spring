package com.google.springboot.service.xgboost;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
/**
 * @Author kris
 * @Create 2024-06-19 23:54
 * @Description
 */


public class GenerateDataset {
    public static void main(String[] args) {
        int nProducts = 1000;
        Random random = new Random(0);

        try (FileWriter writer = new FileWriter("/Users/kris/Downloads/xgboost_dedup_dataset.csv")) {
            writer.append("product_id,name,description,price\n");
            for (int i = 1; i <= nProducts; i++) {
                String productId = String.valueOf(i);
                String name = "Product " + i;
                String description = "Description of Product " + i;
                double price = 10 + (990 * random.nextDouble());
                writer.append(productId)
                        .append(",")
                        .append(name)
                        .append(",")
                        .append(description)
                        .append(",")
                        .append(String.format("%.2f", price))
                        .append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

