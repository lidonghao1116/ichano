package cn.ichano.judge.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.ichano.common.service.SequenceService;

@Service
public class Task {

	private static final Logger LOGGER = LoggerFactory.getLogger(Task.class);

	@Autowired
	SequenceService sequenceService;

	@Scheduled(fixedRate = 1000 * 10000)
	public void load() {
		// for (int j = 0; j < 2; j++) {
		// ExecuteServiceUtil.getService().execute(new Runnable() {
		// public void run() {
		// for (int i = 0; i < 300; i++) {
		// long startTime = System.currentTimeMillis();
		// Long value = sequenceService.getNext("tb_221");
		//
		// long lastTime = System.currentTimeMillis();
		// if (lastTime - startTime > 2) {
		// LOGGER.error("value:{}, cost:{}ms", value,
		//
		// (lastTime - startTime));
		// }
		// try {
		// Thread.sleep(100);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }
		// });
		//
		// try {
		// Thread.sleep(10000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// try {
		// Thread.sleep(100000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

}
