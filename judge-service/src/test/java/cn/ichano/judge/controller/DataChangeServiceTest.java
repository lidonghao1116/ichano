package cn.ichano.judge.controller;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import cn.ichano.common.db.vo.DbNoticeMessage;
import cn.ichano.common.util.ExecuteServiceUtil;
import cn.ichano.common.util.JsonUtil;
import cn.ichano.judge.service.DataChangeService;

public class DataChangeServiceTest extends AbstractTest {

	final AtomicInteger counte = new AtomicInteger(0);
	
		
	@Test
	public void testApplyNotice() {
		final DataChangeService dcc = re.getBean(DataChangeService.class);
		for (int i = 0; i < 100; i++) {

			for (int j = 1; j < 1000; j++) {
				ExecuteServiceUtil.getService().execute(new Runnable(){
					@Override
					public void run(){
						DbNoticeMessage message = createNoticeMessage();
						dcc.applyNotice( message);
						counte.incrementAndGet();
					}
				});
				if(counte.get() %100 == 0){
					
					System.out.println("current:" + counte.get());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
			}
		}
	}

	public DbNoticeMessage createNoticeMessage() {
		String value = "{\"sql\":\"INSERT INTO t_cm_event(\\n\\t\\t\\t   eid\\n\\t\\t\\t   ,cid\\n\\t\\t\\t  ,type\\n\\t\\t\\t  ,create_time\\n\\t\\t\\t  ,camera_id\\n\\t\\t\\t  ,file_num\\n\\t\\t\\t  ,file_size\\n\\t\\t\\t  ,index_size\\n\\t\\t\\t  ,audio_type\\n\\t\\t\\t  ,video_type\\n\\t\\t\\t  ,channel\\n\\t\\t\\t  ,audio_depth\\n\\t\\t\\t  ,sample_rate\\n\\t\\t\\t  ,video_width\\n\\t\\t\\t  ,video_height\\n\\t\\t\\t  ,duration_time\\n\\t\\t\\t  ,hash\\n\\t\\t\\t  ,path\\n\\t\\t\\t  ,bucket\\n\\t\\t\\t  ,cloud_server\\n\\t\\t\\t) VALUES (\\n\\t\\t\\t:eid,\\n\\t\\t\\t:cid,\\n\\t\\t\\t:type,\\n\\t\\t\\t:create_time,\\n\\t\\t\\t:camara_id,\\n\\t\\t\\t:file_num,\\n\\t\\t\\t:file_size,\\n\\t\\t\\t:index_size,\\n\\t\\t\\t:audio_type,\\n\\t\\t\\t:video_type,\\n\\t\\t\\t:channel,\\n\\t\\t\\t:audio_depth,\\n\\t\\t\\t:sample_rate,\\n\\t\\t\\t:video_width,\\n\\t\\t\\t:video_height,\\n\\t\\t\\t:duration_time,\\n\\t\\t\\t:hash,\\n\\t\\t\\t:path,\\n\\t\\t\\t:bucket,\\n\\t\\t\\t:cloud_server\\n\\t\\t\\t)\",\"bean\":{\"eid\":\"201501211421829323517\",\"cid\":\"1234\",\"type\":1,\"create_time\":\"20141218110008\",\"camara_id\":1,\"file_num\":0,\"file_size\":98304,\"index_size\":0,\"duration_time\":0,\"audio_type\":1,\"video_type\":1,\"channel\":1,\"audio_depth\":16,\"sample_rate\":44100,\"video_width\":320,\"video_height\":240,\"hash\":\"f6fdffe48c908deb0f4c3bd36c032e72\",\"bucket\":\"athome-motionvideo\",\"cloud_server\":\"s3.cn-north-1.amazonaws.com.cn\",\"path\":\"/free/1234/201501211421829323517/\",\"finished\":\"false\"},\"objs\":null}";
		@SuppressWarnings("unchecked")
		Map<String, Object> params = JsonUtil.fromJsonString(value, Map.class);

		@SuppressWarnings("unchecked")
		Map<String, Object> bean = (Map<String, Object>) params.get("bean");
		bean.put("eid", "" + System.currentTimeMillis());

		DbNoticeMessage message = new DbNoticeMessage();
		message.setValue(JsonUtil.toJsonString(params));
		return message;
	}

}
