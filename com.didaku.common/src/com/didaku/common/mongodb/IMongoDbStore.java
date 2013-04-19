package com.didaku.common.mongodb;

import java.util.List;

import com.mongodb.DBObject;

public interface IMongoDbStore<T> {
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
    boolean Delete(DBObject  query);

    /// <summary>��ָ���Ĳ�ѯ������ȡʵ������
    /// </summary>
    long Count(DBObject mongoQuery);


    List<T> Find(DBObject mongoQuery);


}
