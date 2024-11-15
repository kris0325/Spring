package com.google.springboot.service;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import org.bson.Document;

/**
 * @author: kris
 * @date: 2024/06/12
 * @description: GeoHash 通过地理空间索引，实现地理空间数据存储和快速地理位置搜索
 **/
public class MongoDBService {
    public static void main(String[] args) {
        // 连接到MongoDB
        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("geodb");
            MongoCollection<Document> collection = database.getCollection("places");

            // 定义中心点和搜索半径
            Point center = new Point(new Position(-122.4194, 37.7749));
            double radiusInMeters = 500;

            // 创建地理空间查询
            FindIterable<Document> results = collection.find(Filters.geoWithinCenterSphere(
                    "location",
                    center.getPosition().getValues().get(0),
                    center.getPosition().getValues().get(1),
                    radiusInMeters / 6378100.0  // 将半径转换为弧度
            ));

            /*
            *  Filters.nearSphere
            * */
//            public List<Seed> getGeoNearSeed(String mongoTable, LocationBean locationBean) {
//                Bson filter = Filters.and(
//                        Filters.nearSphere("detail.location", new Point(new Position(locationBean.getLng(), locationBean.getLat())), 1.0 * Integer.valueOf(distance), 0.0),
//                        Filters.eq(SeedCodec.FIELD_FETCH_COUNT, SeedStatusEnum.INIT.getCode())
//                );
//                List<Seed> seedTaskList = new ArrayList<>();
//                mongoDB.swan.getCollection(mongoTable, Seed.class).find(filter
//                ).forEach((Consumer<? super Seed>) seedTaskList::add);
//
//                return seedTaskList;
//            }
//


            // 打印查询结果
            for (Document doc : results) {
                System.out.println(doc.toJson());
            }
        }
    }

}
