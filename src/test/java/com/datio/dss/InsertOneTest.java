package com.datio.dss;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.datiobd.daas.DaasMongoConnector;
import com.datiobd.daas.Parameters;
import com.datiobd.daas.conf.EnumOperation;
import com.datiobd.daas.model.DocumentWrapper;
import com.datiobd.daas.model.FindIterableWrapper;
import com.datiobd.daas.rsa.EncryptionUtil;

public class InsertOneTest {
    public static final int TOTAL_DOCS = 10;
    private static String COLLECTION_NAME = "test1";
    private static String DATABASE = "daas";
    private static String HOST = "127.0.0.1";
    private static String DATABASE_NAME = "daas";
    private static String USER_NAME = EncryptionUtil.getIntance().encryptWithCustomException("testUser");
    private static String PASSWORD = EncryptionUtil.getIntance().encryptWithCustomException("password");

    private static DaasMongoConnector instance = new DaasMongoConnector();

    private static Map<String, Object> baseConfiguration = new HashMap<String, Object>() {
        {
            put(Parameters.COLLECTION, COLLECTION_NAME);
            put(Parameters.DATABASE, DATABASE_NAME);
            put(Parameters.HOST, HOST);
            put(Parameters.USER, USER_NAME);
            put(Parameters.PASSWORD, PASSWORD);
            put(Parameters.API_OR_REST, Parameters.API);
        }
    };
    private static DocumentWrapper documentWrapper = DocumentWrapper.parse("{\"database\" : \"daas\",\"table\" : \"clients\",\"detail\" : {\"name\" : \"Miguel\", \"age\" : 28, \"isVip\" : \"true\"}}");
/*
{
	"database": "daas",
	"table": "clients",
	"detail": {
		"name": "Miguel",
		"age": 28,
		"isVip": "true"
	}
}
 */
    @Test
    public void test() throws Exception {
        System.out.println(documentWrapper.toJson());
        //Delete all documents
        deleteAllDocuments();
        //Insert One register
        insertOneRegister(documentWrapper);
        FindIterableWrapper<DocumentWrapper> documentWrappers = getAllDocuments();
        Assert.assertNotNull(documentWrappers);
        Assert.assertNotNull(documentWrappers.first());
        Assert.assertEquals(documentWrappers.first().getString("database"), documentWrapper.getString("database"));
        Assert.assertEquals(documentWrappers.first().getString("table"), documentWrapper.getString("table"));
        // Complete tests

    }

    private FindIterableWrapper<DocumentWrapper> getAllDocuments() throws Exception {
        baseConfiguration.put(Parameters.FILTER, null);
        return instance.executeWithReturn(EnumOperation.FIND, baseConfiguration);
    }


    private void deleteAllDocuments() throws Exception {
        baseConfiguration.put(Parameters.FILTER, new DocumentWrapper());
        instance.executeWithReturn(EnumOperation.DELETE_MANY, baseConfiguration);
    }

    private void insertOneRegister(DocumentWrapper documentWrapper) throws Exception {
        baseConfiguration.put(Parameters.FILTER, null);
        baseConfiguration.put(Parameters.DOCUMENT, documentWrapper);

        instance.executeWithNoReturn(EnumOperation.INSERT_ONE,
                baseConfiguration);
    }
}
