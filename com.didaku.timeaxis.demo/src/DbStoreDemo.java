import org.apache.commons.lang3.time.StopWatch;

public class DbStoreDemo {

	/** ����Log4j����־��� */
	private final static org.apache.logging.log4j.Logger _Logger = org.apache.logging.log4j.LogManager
			.getLogger(DbStoreDemo.class);

	private static StopWatch _Watch = new StopWatch();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		_Logger.info("����DbStoreDemo");
        _Logger.info("--����MongoDb���ݳ־û��������ܵ���ʾ��");
        _Logger.info("Mongo! Mongo!! Mongo!!! Mongo!!!! Mongo!!!!!");
        
        _Watch.reset();
        
	}

}
