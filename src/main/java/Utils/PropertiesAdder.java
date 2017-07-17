package Utils;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package net.clementlevallois.net.servicing.utils;
//
//import java.util.Set;
//
///**
// *
// * @author LEVALLOIS
// */
//public class PropertiesAdder {
//
//    public static JSONObject addNodePropertiesToJson(Node next, Set<String> properties, JSONObject nodeJson) throws JSONException {
//        if (properties.contains("name")) {
//            nodeJson.put("name", (next.hasProperty("name")) ? next.getProperty("name") : "");
//        }
//        if (properties.contains("screenName")) {
//            nodeJson.put("screenName", (next.hasProperty("screenName")) ? next.getProperty("screenName") : "");
//        }
//        if (properties.contains("description")) {
//            nodeJson.put("description", (next.hasProperty("description")) ? next.getProperty("description") : "");
//        }
//        if (properties.contains("location")) {
//            nodeJson.put("location", (next.hasProperty("location")) ? next.getProperty("location") : "");
//        }
//        if (properties.contains("followersCount")) {
//            nodeJson.put("followersCount", (next.hasProperty("followersCount")) ? next.getProperty("followersCount") : "");
//        }
//        if (properties.contains("friendsCount")) {
//            nodeJson.put("friendsCount", (next.hasProperty("friendsCount")) ? next.getProperty("friendsCount") : "");
//        }
//        if (properties.contains("listedCount")) {
//            nodeJson.put("listedCount", (next.hasProperty("listedCount")) ? next.getProperty("listedCount") : "");
//        }
//
//        if (properties.contains("lang")) {
//            nodeJson.put("lang", (next.hasProperty("lang")) ? next.getProperty("lang") : "");
//        }
//
//        return nodeJson;
//    }
//
//}
