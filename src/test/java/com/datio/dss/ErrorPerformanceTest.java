package com.datio.dss;

import com.datiobd.daas.DaasMongoConnector;
import com.datiobd.daas.Parameters;
import com.datiobd.daas.model.DocumentWrapper;
import com.datiobd.daas.rsa.EncryptionUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class ErrorPerformanceTest {

  public static final int TOTAL_DOCS = 10;
  private static String COLLECTION_NAME = "test";
  private static String DATABASE = "daas";
  private static String HOST = "127.0.0.1";
  private static Integer PORT = 27017;
  private static String DATABASE_NAME = "daas";
  private static String USER_NAME = EncryptionUtil.getIntance()
      .encryptWithCustomException("testUser");
  private static String PASSWORD = EncryptionUtil.getIntance()
      .encryptWithCustomException("password");

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
  private String cannotChangedFields;

  protected void doUpsert(String collectionName, List<Map<String, Object>> docs, String keyId)
      throws Exception {

    MongoUtils utils = new MongoUtils();

    for (Map<String, Object> doc : docs) {
      Map<String, Object> key = buildKey(keyId, doc);
      if (cannotChangedFields != null) {
        String[] fields = cannotChangedFields.split(",");
        for (String field : fields) {
          doc.remove(field);
        }
      }

      // TODO findOneAndReplace con el upsert a true
      DocumentWrapper result = utils
          .findOneAndReplaceMongo(fillBaseParameters(collectionName), key, doc, true);
    }
  }

  private Map<String, Object> buildKey(String key, Map<String, Object> doc) {
    String[] keyParts = key.split(",");
    Map<String, Object> queryPart = new HashMap<String, Object>();
    for (String part : keyParts) {
      queryPart.put(part, doc.get(part));
    }
    return queryPart;
  }

  private Map<String, Object> fillBaseParameters(String colection) {
    
    Map<String, Object> base = new HashMap<String, Object>();

    base.put(Parameters.DATABASE, DATABASE_NAME);
    base.put(Parameters.API_OR_REST, Parameters.API);
    base.put(Parameters.COLLECTION, colection);

    base.put(Parameters.USER, USER_NAME);
    base.put(Parameters.PASSWORD, PASSWORD);
    base.put(Parameters.HOST, HOST);
    base.put(Parameters.PORT, PORT);

    return base;
  }


  private MongoUtils utils = new MongoUtils();

  @Test
  public void test() throws Exception {

    DocumentWrapper documentWrapper = DocumentWrapper
        .parse("{\"name\" : \"Sagrario\", \"money\" : \"95\", \"single\" : \"false\" }\n");

    List<Map<String, Object>> docs = new ArrayList<>();
    for (int i = 1; i <= 5000; i++) {
      docs.add(documentWrapper);
    }

    doUpsert(COLLECTION_NAME, docs, "name");
  }


}
