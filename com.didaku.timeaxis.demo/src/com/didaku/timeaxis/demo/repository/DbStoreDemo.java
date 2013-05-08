package com.didaku.timeaxis.demo.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.time.StopWatch;
import org.nutz.lang.Strings;

import com.didaku.timeaxis.repository.MongoStore;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class DbStoreDemo {

	private final static org.apache.logging.log4j.Logger _Logger = org.apache.logging.log4j.LogManager
			.getLogger(DbStoreDemo.class); 
	
	private static StopWatch _Watch = new StopWatch();
	private static MongoStore _Store = new MongoStore("localhost","TransactionStore","TransactionCollection");
	
	private static final int DEMO_SIZE = 5 * 1000;
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {

		_Logger.info("启动DbStoreDemo");
		_Logger.info("--采用MongoDb数据持久化，及性能的演示。");
		_Logger.info("Mongo! Mongo!! Mongo!!! Mongo!!!! Mongo!!!!!");
        
        _Watch.reset();
        
        _Watch.start();
        long count1 = _Store.count();
        _Watch.stop();
        _Logger.info(String.format("[0]集合中原数据个数:%d, 计时:%d", count1, _Watch.getTime()));
        
        _Watch.reset();
        _Watch.start();
        _Store.clear(); //清除数据
        _Watch.stop();
        _Logger.info(String.format("[0.1]集合清理完成。计时:%d", _Watch.getTime()));
        
        _Watch.reset();
        DbStoreDemo demo = new DbStoreDemo();
        DbStoreDemo.ThreadStore threadStore = demo.new ThreadStore();
        _Watch.start();
        for (int i = 0; i < 50; i++)
        {
            new Thread(threadStore).start();//多线程进行存储
        }
        _Watch.stop();
        _Logger.info("等待60秒钟......");
        Thread.sleep(1000*60);
        long dataCount = _Store.count();
        _Logger.info(String.format("[0.1.9]当前数据量:%d", dataCount));
        _Logger.info(String.format("[0.2]创建测试数据%d完成。计时:%d", DEMO_SIZE*50, _Watch.getTime()));
	}


	class ThreadStore implements Runnable{
	
		@Override
		public void run() {
	        _Logger.info(String.format("[0.1.1]生成数据"));
	        List<com.mongodb.DBObject> trans = GetEntities(DEMO_SIZE);
	        _Logger.info(String.format("[0.1.2]生成数据完成，准备存储"));
	        _Store.add(trans);
	        _Logger.info(String.format("[0.1.3]存储完成。"));
	        long count = _Store.count();
	        _Logger.info(String.format("[0.1.4]集合中数据个数:%d", count));
		}
		
        private List<com.mongodb.DBObject> GetEntities(int size)
        {
        	List<com.mongodb.DBObject> array = new ArrayList<com.mongodb.DBObject>(size);
            Random random = new Random();
        	for (int i = 0; i < size; i++)
            {
                UUID id = java.util.UUID.randomUUID();
                Calendar initDate = Calendar.getInstance();
                initDate.add(Calendar.YEAR, -10);
                initDate.add(Calendar.MONTH,  random.nextInt((10 - 1) * 12));
                initDate.add(Calendar.DAY_OF_YEAR, random.nextInt(365));
                initDate.add(Calendar.HOUR_OF_DAY, random.nextInt(24));
                initDate.add(Calendar.MINUTE, random.nextInt(60));
                initDate.add(Calendar.SECOND, random.nextInt(60));
                
                SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
                String dateTimeStr = dateFm.format(initDate.getTime());
                DBObject tran = new BasicDBObject();
                
                tran.put("Identifier", String.format("%c%s", 'R', Strings.alignRight(i, 3, '0')));
                tran.put("Owner", 1000 + random.nextInt(2000));
                tran.put("User", id.toString().substring(8, 15).toUpperCase());
                tran.put("Queue", id.toString().substring(0, 5)); 
                tran.put("Time", dateTimeStr);
                array.add(tran);
            }
            return array;
        }
	}
}