package cn.ichano.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 序列生成器，适合单机,分布式请使用SequenceService接口
 * 
 * @author B6M0AB243123
 *
 */
public class SequenceUtil {

	private static AtomicLong eid = new AtomicLong(System.currentTimeMillis());
	
	/**
	 * 获取一个序列
	 * @return
	 */
	public static String getNextSequence() {
		
		String currentDate = TimeUtil.getCurrentTimeString(TimeUtil.YYYYMMDD);

		return currentDate + String.valueOf(eid.incrementAndGet());
	}
	
	/**
	 * 一次获取多个序列
	 * @param num
	 * @return
	 */
	public static List<String> getNextSequence(int num){
		
		String currentDate = TimeUtil.getCurrentTimeString(TimeUtil.YYYYMMDD);

		long current = eid.addAndGet(num);
		List<String> result = new ArrayList<String>(num);
		for(int i=num -1;i >= 0;i--){
			result.add(currentDate + String.valueOf(current - i));
		}
				
		return result;
	}
	
	
	 
}
