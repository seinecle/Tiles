package Utils;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package net.clementlevallois.net.servicing.utils;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// *
// * @author LEVALLOIS
// */
//public class PropertiesParser {
//
//    public static Set<String> parse(String propertiesParam, GraphDatabaseService db) {
//        if (propertiesParam == null || propertiesParam.isEmpty()) {
//            return new HashSet();
//        }
//        Set<String> properties;
//        try (Transaction tx = db.beginTx()) {
//
//            properties = new HashSet(Arrays.asList(propertiesParam.split("_")));
//            Set<String> propertiesToRemove = new HashSet();
//            ResourceIterable<Label> allLabelsInUse = db.getAllLabelsInUse();
//            for (Label label : allLabelsInUse) {
//                if (!properties.contains(label.name())) {
//                    propertiesToRemove.add(label.name());
//                }
//            }
//            properties.removeAll(propertiesToRemove);
//            tx.success();
//        }
//        return properties;
//    }
//
//}
