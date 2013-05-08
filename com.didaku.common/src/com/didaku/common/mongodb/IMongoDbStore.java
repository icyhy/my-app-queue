package com.didaku.common.mongodb;

import java.util.List;

import com.mongodb.DBObject;

public interface IMongoDbStore<DBObject> {
    /// <summary>�����ַ���
    /// </summary>
    String getConnection();

    /// <summary>���ݿ���
    /// </summary>
    String getDatabase();

    /// <summary>���ݼ�������
    /// </summary>
    String getCollection();

    /// <summary>ɾ��ָ�������ļ�¼
    /// </summary>
    /// <param name="query"></param>
    /// <returns></returns>
    boolean delete(DBObject  query);

    /// <summary>��ָ���Ĳ�ѯ������ȡʵ������
    /// </summary>
    long count(DBObject mongoQuery);

    /*
     * ��������ʵ������
     */
    long count();

    List<DBObject> find(DBObject mongoQuery);


}
