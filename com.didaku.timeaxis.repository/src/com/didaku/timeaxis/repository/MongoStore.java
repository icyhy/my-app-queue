package com.didaku.timeaxis.repository;

import java.util.List;

import com.didaku.common.mongodb.AbstractMongoDbStore;
import com.mongodb.DBObject;

public class MongoStore extends AbstractMongoDbStore{

	protected MongoStore(String connection, String database, String collection) {
		super(connection, database, collection);
		// TODO 自动生成的构造函数存根
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根

	}

	@Override
	public String getConnection() {
		// TODO 自动生成的方法存根
		return Connection;
	}

	@Override
	public String getDatabase() {
		// TODO 自动生成的方法存根
		return Database;
	}

	@Override
	public String getCollection() {
		// TODO 自动生成的方法存根
		return Collection;
	}

	@Override
	public boolean Delete(DBObject query) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public long Count(DBObject mongoQuery) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public List Find(DBObject mongoQuery) {
		// TODO 自动生成的方法存根
		return null;
	}

}
