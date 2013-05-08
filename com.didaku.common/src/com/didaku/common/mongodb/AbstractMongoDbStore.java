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

	/** 来自Log4j的日志组件 */
	private final static org.apache.logging.log4j.Logger _Logger = org.apache.logging.log4j.LogManager
			.getLogger(AbstractMongoDbStore.class); 
	
	protected String Connection;
	protected String Database;
	protected String Collection;
	private DBCollection _Collection;
	
	private Mongo _Mongo = null;
	private DB _Database;
	/*
	 * <summary>构造函数：基于MongoDb的一个数据类型存储器的抽象实现 *?
	 */
	protected AbstractMongoDbStore(String connection, String database,
			String collection) {
		Connection = connection;
		Database = database;
		Collection = collection;
		try {
			_Mongo =connection.isEmpty() ? new Mongo(): new Mongo(connection); // 建立一个Mongo的连接
		} catch (UnknownHostException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
		_Database = _Mongo.getDB(Database); // 获取数据库或者创建数据库（不存在的话）。
		_Collection = _Database.getCollection(Collection);
	}

	public String getConnection() {
		// TODO 自动生成的方法存根
		return Connection;
	}

	public String getDatabase() {
		// TODO 自动生成的方法存根
		return Database;
	}

	public String getCollection() {
		// TODO 自动生成的方法存根
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
			_Logger.error("异常：" + e.getMessage());
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
     * 查找集合所有对象 
     * @param collection 
     */  
     public List<com.mongodb.DBObject> findAll() {  
          return _Collection.find().toArray();  
     }
}
