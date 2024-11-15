package com.google.springboot.utils;
//import org.apache.http.client.utils.URIBuilder;

/**
 * @author: yangwenkang
 * @date: 2021/12/29
 * @description:
 **/
public class WebhookUtils {

//    public void relay(){
//        URIBuilder uri = null;
//        try {
//            uri = new URIBuilder(url);
//        } catch (URISyntaxException e) {
//            logger.error("", e);
//        }
//
//        uri.setParameters(qparams);
//
//        try (
//                CloseableHttpClient closeableHttpClient = HttpClients.createDefault()
//        ) {
//            HttpPost httpPost = new HttpPost(uri.build());
//            httpPost.addHeader("Content-type", "application/json;charset=utf-8");
//
//            StringEntity alertStringEntity = new StringEntity(mapper.writeValueAsString(alertCenterStructs), "UTF-8");
//            httpPost.setEntity(alertStringEntity);
//
//            try (
//                    CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpPost)
//            ) {
//                HttpEntity entity = httpResponse.getEntity();
//                System.out.println(entity.getContent());
//            } catch (Exception e) {
//                logger.error("send msg to alert center failed", e);
//            }
//        } catch (Exception e) {
//            logger.error("send msg to alert center failed", e);
//        }
//
//    }
}
