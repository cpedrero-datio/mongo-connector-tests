package com.datio.dss;

import static com.datiobd.daas.model.json.FiltersWrapper.eq;

import java.util.HashMap;
import java.util.Map;

import com.datiobd.daas.DaasMongoConnector;
import com.datiobd.daas.Parameters;
import com.datiobd.daas.conf.EnumOperation;
import com.datiobd.daas.model.DocumentWrapper;
import com.datiobd.daas.model.UpdateResultWrapper;

//public class UpsertTest {
//    private static String collect = "test";
//    private static String database = "daas";
//    private static String host = "127.0.0.1";
//
//    private static Map<String, Object> baseParameters = new HashMap<String, Object>() {
//        {
//            put(Parameters.COLLECTION, collect);
//            put(Parameters.DATABASE, database);
//            put(Parameters.HOST, host);
//        }
//    };
//    private static String json = "{'database' : 'daas','table' : 'clients', 'type' : 'manager'" +
//            "'detail' : {'name' : 'Miguel', 'age' : 28, 'isVip' : 'true'}}";
//
//    private static String newJson = "{'database' : 'daas','table' : 'newTable', 'type' : 'manager'" +
//            "'detail' : {'name' : 'Miguel', 'age' : 28, 'isVip' : 'true'}}";
//
//    public static void main(String args[]){
//        try {
//        //Delete all documents
//            deleteAllDocuments();
//        //Insert One register
//            insertOneRegister();
//
//        //Update with insert
//            updateWithInsert();
//
//            Iterable<DocumentWrapper> documentWrappers = getAllDocuments();
//
//            System.out.println("=====UPSERT with insert======");
//            documentWrappers.forEach(x->System.out.println(x));
//
//            deleteAllDocuments();
//            //Insert One register
//            insertOneRegister();
//            UpdateResultWrapper updateResultWrapper = updateOneUpsertWithModification();
//            documentWrappers = getAllDocuments();
//            System.out.println("=====UPSERT with update======");
//            documentWrappers.forEach(x->System.out.println(x));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static Iterable<DocumentWrapper> getAllDocuments() throws Exception {
//        baseParameters.put(Parameters.FILTER,
//                null);
//        Iterable<DocumentWrapper> documentWrappers = (Iterable<DocumentWrapper>) DaasMongoConnector
//                .getInstance().executeOperationWithReturn(EnumOperation.FIND, baseParameters);
//        DaasMongoConnector
//                .getInstance().executeOperation(EnumOperation.CLOSE, baseParameters);
//        return documentWrappers;
//    }
//
//    private static void deleteAllDocuments() throws Exception {
//        baseParameters.put(Parameters.FILTER, new DocumentWrapper().toBsonDocument());
//        DaasMongoConnector
//                .getInstance().executeOperationWithReturn(EnumOperation.DELETE_MANY, baseParameters);
//    }
//
//    private static void insertOneRegister() throws Exception {
//        baseParameters.put(Parameters.FILTER, null);
//        baseParameters.put(Parameters.DOCUMENT, DocumentWrapper.parse(json));
//
//        DaasMongoConnector.getInstance().executeOperation(EnumOperation.INSERT_ONE,
//                baseParameters);
//    }
//
//    private static UpdateResultWrapper updateWithInsert() throws Exception {
//        baseParameters.put(Parameters.FILTER,
//                eq("table", "fakeTable"));
//        baseParameters.put(Parameters.UPDATE_ONE,
//                new DocumentWrapper("$set", DocumentWrapper.parse(newJson).toBsonDocument())
//                        .toBsonDocument());
//        baseParameters.put(Parameters.UPSERT, true);
//
//        return (UpdateResultWrapper) DaasMongoConnector.getInstance()
//                    .executeOperationWithReturn(EnumOperation.UPDATE_ONE, baseParameters);
//    }
//    public static UpdateResultWrapper updateOneUpsertWithModification() throws Exception{
//        baseParameters.put(Parameters.FILTER,
//                eq("type", "manager"));
//        baseParameters.put(Parameters.UPDATE_ONE,
//                new DocumentWrapper("$set", new DocumentWrapper("type", "user").toBsonDocument()).toBsonDocument());
//        baseParameters.put(Parameters.UPSERT, false);
//
//        return (UpdateResultWrapper) DaasMongoConnector.getInstance()
//                    .executeOperationWithReturn(EnumOperation.UPDATE_ONE, baseParameters);
//
//    }
//}
