package cn.ichano.judge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.ichano.common.entity.message.NoticeMessage;
import cn.ichano.common.service.NoticeService;
import cn.ichano.judge.service.DataChangeService;

/**
 * 远程通知服务
 * 
 * @author wenjp
 *
 */
public class NoticeServiceImpl implements NoticeService {

	private Logger LOGGER = LoggerFactory.getLogger(NoticeServiceImpl.class);
	
	@Autowired
	DataChangeService dataChangeService;
	
//	private ContentType ct = ContentType.create("application/json","UTF-8");
	/* (non-Javadoc)
	 * @see cn.ichano.common.message.impl.NoticeService#notice(cn.ichano.common.message.vo.NoticeMessage)
	 */
	@Override
	public String notice(NoticeMessage n) {
		
		return String.valueOf(dataChangeService.applyNotice(n));
//		if(Env.getConfig().getBoolean("common.notice))
//		String url = n.getUrl();
//		try {
//			return HttpUtil.commonPostMethod(url,ct, JsonUtil.toJsonBytes(n));
//		} catch (ClientProtocolException e) {
//			LOGGER.debug("sync call remote error. url:{}, notice:{}",url,n);
//			throw new RuntimeException("execute remote error:", e);
//		} catch (IOException e) {
//			LOGGER.debug("sync call remote error. url:{}, notice:{}",url,n);
//			throw new RuntimeException("execute remote error:", e);
//		}

	}

}
