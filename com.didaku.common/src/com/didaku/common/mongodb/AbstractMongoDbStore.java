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
	 * <summary>���캯��������MongoDb��һ���������ʹ洢���ĳ���ʵ�� *?
	 */
	protected AbstractMongoDbStore(String connection, String database,
			String collection) {
		Connection = connection;
		Database = database;
		Collection = collection;
		try {
			_Mongo = new Mongo(connection); // ����һ��Mongo������
		} catch (UnknownHostException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} 
		_Database = _Mongo.getDB(Database); // ��ȡ���ݿ���ߴ������ݿ⣨�����ڵĻ�����
	}

}
