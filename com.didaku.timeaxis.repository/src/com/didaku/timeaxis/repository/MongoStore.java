package com.didaku.timeaxis.repository;

import java.util.List;

import com.didaku.common.mongodb.AbstractMongoDbStore;
import com.mongodb.DBObject;

public class MongoStore extends AbstractMongoDbStore{

	protected MongoStore(String connection, String database, String collection) {
		super(connection, database, collection);
		// TODO �Զ����ɵĹ��캯�����
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������

	}

	@Override
	public String getConnection() {
		// TODO �Զ����ɵķ������
		return Connection;
	}

	@Override
	public String getDatabase() {
		// TODO �Զ����ɵķ������
		return Database;
	}

	@Override
	public String getCollection() {
		// TODO �Զ����ɵķ������
		return Collection;
	}

	@Override
	public boolean Delete(DBObject query) {
		// TODO �Զ����ɵķ������
		return false;
	}

	@Override
	public long Count(DBObject mongoQuery) {
		// TODO �Զ����ɵķ������
		return 0;
	}

	@Override
	public List Find(DBObject mongoQuery) {
		// TODO �Զ����ɵķ������
		return null;
	}

}
