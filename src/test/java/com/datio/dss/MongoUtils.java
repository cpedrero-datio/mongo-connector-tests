package com.datio.dss;

import com.datiobd.daas.DaasMongoConnector;
import com.datiobd.daas.Parameters;
import com.datiobd.daas.conf.EnumOperation;
import com.datiobd.daas.model.DocumentWrapper;
import com.datiobd.daas.model.FindOneAndReplaceOptionsWrapper;
import com.datiobd.daas.model.FindOneAndReplaceOptionsWrapperImp;
import java.util.Map;

//import org.bson.BsonDocument;
//import org.bson.conversions.Bson;

public class MongoUtils {

  /** FIND ONE AND REPLACE
   *
   * @collection Coleccion sobre la que se hara la busqueda
   * @mapQuery Query en formato HashMap<String, Object>
   * @mapReplace Objecto en formato HashMap<String, Object> que sera insertado
   */
  public DocumentWrapper findOneAndReplaceMongo(Map<String, Object> baseParameters, Map<String, Object> mapQuery, Map<String, Object> replace, boolean upsert){

    DaasMongoConnector instance = new DaasMongoConnector();
    FindOneAndReplaceOptionsWrapper findOneAndReplaceOptions = new FindOneAndReplaceOptionsWrapperImp();

    findOneAndReplaceOptions.upsert(upsert);

    // Metemos el filtro
    //baseParameters.put(Parameters.FILTER, bsonGenerator(mapQuery));
    baseParameters.put(Parameters.FILTER, new DocumentWrapper(mapQuery));

    // Aniadimos el replace
    baseParameters.put(Parameters.DOCUMENT, documentGenerator(replace));

    // Aniadimos las options
    baseParameters.put(Parameters.FIND_ONE_AND_REPLACE_OPTIONS, findOneAndReplaceOptions);


    DocumentWrapper result = instance.executeWithReturn(EnumOperation.FIND_ONE_AND_REPLACE, baseParameters);

    return result;

  }

  /**
   * @map Map<String, Object> Mapa a convertir
   * @return Se devolvera un DocumentWrapper a partir del mapa de entrada.
   */
  public DocumentWrapper documentGenerator(Map<String, Object> map){

    DocumentWrapper doc = new DocumentWrapper(map);

    return doc;
  }

}