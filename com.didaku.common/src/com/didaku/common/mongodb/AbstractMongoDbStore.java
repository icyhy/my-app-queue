package com.didaku.common.mongodb;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public abstract class AbstractMongoDbStore<DBObject> implements
		IMongoDbStore<com.mongodb.DBObject> {

	/** ����Log4j����־��� */
	private final static org.apache.logging.log4j.Logger _Logger = org.apache.logging.log4j.LogManager
			.getLogger(AbstractMongoDbStore.class); 
	
	protected String Connection;
	protected String Database;
	protected String Collection;
	private DBCollection _Collection;
	
	private Mongo _Mongo = null;
	private DB _Database;
	/*
	 * <summary>���캯��������MongoDb��һ���������ʹ洢���ĳ���ʵ�� *?
	 */
	protected AbstractMongoDbStore(String connection, String database,
			String collection) {
		Connection = connection;
		Database = database;
		Collection = collection;
		try {
			_Mongo =connection.isEmpty() ? new Mongo(): new Mongo(connection); // ����һ��Mongo������
		} catch (UnknownHostException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} 
		_Database = _Mongo.getDB(Database); // ��ȡ���ݿ���ߴ������ݿ⣨�����ڵĻ�����
		_Collection = _Database.getCollection(Collection);
	}

	public String getConnection() {
		// TODO �Զ����ɵķ������
		return Connection;
	}

	public String getDatabase() {
		// TODO �Զ����ɵķ������
		return Database;
	}

	public String getCollection() {
		// TODO �Զ����ɵķ������
		return Collection;
	}

	public boolean add(List<com.mongodb.DBObject> entities){
		if (null == entities)
            return true;
		try{
		_Collection.insert(entities);
		}
		catch(MongoException e)
		{
			_Logger.error("�쳣��" + e.getMessage());
		}
		return true;
	}
	
	public boolean delete(com.mongodb.DBObject query) {
		_Collection.remove(query);
		return true;
	}

	public boolean clear(){
		 List<com.mongodb.DBObject> rs = findAll();  
         if (rs != null && !rs.isEmpty()) {  
              for (int i = 0; i < rs.size(); i++) {  
                   _Collection.remove(rs.get(i));  
              }  
         } 
		return true;
	}
	
	public long count(com.mongodb.DBObject mongoQuery) {
		if(mongoQuery == null)
			return _Collection.count();
		else
			return _Collection.count(mongoQuery);
	}
	
    public long count(){
    	return _Collection.count();
    }

	public List<com.mongodb.DBObject> find(com.mongodb.DBObject mongoQuery) {
		return _Collection.find(mongoQuery).toArray();
	}
	
	 /** 
     * ���Ҽ������ж��� 
     * @param collection 
     */  
     public List<com.mongodb.DBObject> findAll() {  
          return _Collection.find().toArray();  
     }
}
