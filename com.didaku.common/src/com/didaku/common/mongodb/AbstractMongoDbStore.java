package com.didaku.common.mongodb;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.Mongo;

public abstract class AbstractMongoDbStore<T> implements
		IMongoDbStore<T> {

	protected String Connection;
	protected String Database;
	protected String Collection;
	
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
			_Mongo = new Mongo(connection); // 建立一个Mongo的连接
		} catch (UnknownHostException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
		_Database = _Mongo.getDB(Database); // 获取数据库或者创建数据库（不存在的话）。
	}

}
