package com.didaku.common.mongodb;

import java.util.List;

import com.mongodb.DBObject;

public interface IMongoDbStore<T> {
    /// <summary>连接字符串
    /// </summary>
    String getConnection();

    /// <summary>数据库名
    /// </summary>
    String getDatabase();

    /// <summary>数据集合名称
    /// </summary>
    String getCollection();

    /// <summary>删除指定条件的记录
    /// </summary>
    /// <param name="query"></param>
    /// <returns></returns>
    boolean Delete(DBObject  query);

    /// <summary>按指定的查询条件获取实体数量
    /// </summary>
    long Count(DBObject mongoQuery);


    List<T> Find(DBObject mongoQuery);


}
