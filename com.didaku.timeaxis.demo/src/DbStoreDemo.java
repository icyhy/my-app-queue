import org.apache.commons.lang3.time.StopWatch;

public class DbStoreDemo {

	/** 来自Log4j的日志组件 */
	private final static org.apache.logging.log4j.Logger _Logger = org.apache.logging.log4j.LogManager
			.getLogger(DbStoreDemo.class);

	private static StopWatch _Watch = new StopWatch();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		_Logger.info("启动DbStoreDemo");
        _Logger.info("--采用MongoDb数据持久化，及性能的演示。");
        _Logger.info("Mongo! Mongo!! Mongo!!! Mongo!!!! Mongo!!!!!");
        
        _Watch.reset();
        
	}

}
