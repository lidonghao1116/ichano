package cn.ichano.common.service;

import cn.ichano.common.entity.message.NoticeMessage;

/**
 * 通用通知服务
 * @author wenjp
 *
 */
public interface NoticeService {

	public abstract String notice(NoticeMessage n);

}