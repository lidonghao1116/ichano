package cn.ichano.judge.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ichano.common.db.vo.DbNoticeMessage;
import cn.ichano.common.util.JsonUtil;
import cn.ichano.judge.service.DataChangeService;

@Controller
@RequestMapping(value = "/change")
public class DataChangeController {
	private Logger LOGGER = LoggerFactory.getLogger(DataChangeController.class);



	ReentrantLock lock = new ReentrantLock();

	@Autowired
	private DataChangeService dataChangeService;

	@RequestMapping(method = RequestMethod.POST, value = "/single/{position}/@db")
	@ResponseBody
	public String singleChangeNotice(@PathVariable("position") String position,
			@RequestBody DbNoticeMessage notice) {
		LOGGER.debug("receive single data change. position:{} , message:{}",
				position, notice);

		// Position p = Position.createPosition(position);

		int result = dataChangeService.applyNotice(notice);

		return String.valueOf(result);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/reload/{file}/@db")
	@ResponseBody
	public String reloadSql(@PathVariable("file") String file,
			HttpServletRequest request) {

		String sqlLogFile = getSqlFile(file);

		int from = getFrom(request);

		int to = getTo(request);
		boolean result = false;
		if (lock.isLocked()) {
			return "reload is running...";
		} else {
			try {
				lock.tryLock(10, TimeUnit.SECONDS);
				result = reload(sqlLogFile, from, to);
			} catch (InterruptedException e) {
				return "cant't get lock.";
			} finally {
				lock.unlock();
			}

		}

		return String.valueOf(result);
	}

	private int getFrom(HttpServletRequest request) {
		String from = request.getParameter("from");
		int result = 0;
		if (!StringUtils.isEmpty(from)) {
			result = Integer.valueOf(from);
		}
		return result;
	}

	private int getTo(HttpServletRequest request) {
		String to = request.getParameter("to");
		int result = Integer.MAX_VALUE;
		if (!StringUtils.isEmpty(to)) {
			result = Integer.valueOf(to);
		}
		return result;
	}

	private boolean reload(String sqlLogFile, int from, int to) {
		File file = new File(sqlLogFile);
		if (!file.exists()) {
			LOGGER.error("can't find sql file:", sqlLogFile);
			return false;
		}
		StringBuffer sb = new StringBuffer();
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			isr = new InputStreamReader(new FileInputStream(file), "utf-8");
			br = new BufferedReader(isr);

			String temp = null;

			int i = 0;
			while ((temp = br.readLine()) != null) {
				i++;
				if (i < from) {
					continue;
				} else if (i > to) {
					break;
				}
				int noticePostion = temp.indexOf("notice:");
				if (noticePostion != -1) {
					String noticeMessage = temp.substring(noticePostion + 7);
					DbNoticeMessage notice = JsonUtil.fromJsonString(
							noticeMessage, DbNoticeMessage.class);
					dataChangeService.applyNotice(notice);
				}

				// 定时休眠下，避免瞬时压力太大
				if (i % 100 == 0) {
					LOGGER.info("loading noticemessage , current:{}", i);
					Thread.sleep(5000);
				}
			}
			LOGGER.info("finish loading noticemessage , total:{}", (i - from));
		} catch (Exception e) {
			LOGGER.error("readFileError,fileName:{}", file.getName(), e);
			return false;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return true;

	}

	private String getSqlFile(String file) {
		// TODO Auto-generated method stub
		return "../log/" + file;
	}

}
