package cn.ichano.judge.controller;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ichano.common.service.SequenceService;
import cn.ichano.common.util.ExecuteServiceUtil;

public class SequenceTest extends AbstractTest {

	private static Logger LOGGER = LoggerFactory.getLogger(SequenceTest.class);
	private static SequenceService sequenceService;

	static {
		sequenceService = re.getBean(SequenceService.class);
	}

	@Test
	public void seqGet() {
		for (int j = 0; j < 2000; j++) {
			ExecuteServiceUtil.getService().execute(new Runnable() {
				public void run() {
					for (int i = 0; i < 1000; i++) {
						long startTime = System.currentTimeMillis();
						Long value = sequenceService.getNext("table_a");
						long lastTime = System.currentTimeMillis();
						LOGGER.error("value:{}, cost:{}ms", value,
								(lastTime - startTime));
					}
				}

			});

		}
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
