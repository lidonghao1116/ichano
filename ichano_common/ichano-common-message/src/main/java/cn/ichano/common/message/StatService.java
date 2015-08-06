package cn.ichano.common.message;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class StatService {

	private EmailStat emailStat = new EmailStat();

	private PushStat pushStat = new PushStat();

	private static StatService statService = new StatService();

	private StatService() {

	}

	public static StatService getInstance() {
		return statService;
	}

	public EmailStat getEmailStat() {
		return emailStat;
	}

	public PushStat getPushStat() {
		return pushStat;
	}

	public enum Type {
		NEED, REAL
	}

	public enum Plat {
		ANDROID, IOS, BOTH
	}

	private static class CountEntity {
		private AtomicLong realCount;

		private AtomicLong needCount;

		private CountEntity() {
			realCount = new AtomicLong(0);
			needCount = new AtomicLong(0);
		}

		public void addTotal(Type type) {
			addTotal(type, 1);

		}

		public void addTotal(Type type, int count) {
			if (type.equals(Type.NEED)) {
				needCount.addAndGet(count);
			} else {
				realCount.addAndGet(count);
			}

		}

		public Long getTotal(Type type) {
			if (type.equals(Type.NEED)) {
				return needCount.get();
			} else {
				return realCount.get();
			}
		}

		public void clear() {
			realCount.set(0);
			needCount.set(0);
		}

		@Override
		public String toString() {
			return "{need:" + needCount.get() + ",real:" + realCount.get()
					+ "}";
		}
	}

	public static class PushStat {
		private Logger LOGGER = LoggerFactory.getLogger(PushStat.class);
		private CountEntity total;

		private CountEntity ios;

		private CountEntity android;

		private PushStat() {
			total = new CountEntity();

			ios = new CountEntity();

			android = new CountEntity();
		}

		public void addTotal(Type type, Plat plat) {
			addTotal(type, plat, 1);
		}

		public void addTotal(Type type, Plat plat, int count) {
			if (plat.equals(Plat.IOS)) {
				ios.addTotal(type, count);
				total.addTotal(type, count);
				return;
			}
			if (plat.equals(Plat.ANDROID)) {
				android.addTotal(type, count);
				total.addTotal(type, count);
				return;
			}
			LOGGER.error(
					"add total don't need call PLAT.TOTAL:plat:{}, count:{}",
					plat, count);
		}

		public Long getTotal(Type type, Plat plat) {
			if (plat.equals(Plat.IOS)) {
				return ios.getTotal(type);
			} else if (plat.equals(Plat.ANDROID)) {
				return android.getTotal(type);
			} else {
				return total.getTotal(type);
			}

		}

		public void clear() {
			total.clear();
			ios.clear();
			android.clear();
		}

		@Override
		public String toString() {
			return "push:{total:" + total.toString() + ",ios:" + ios.toString()
					+ ",android:" + android.toString() + "}";
		}

	}

	public static class EmailStat {

		private CountEntity total;

		private EmailStat() {
			total = new CountEntity();
		}

		public void addTotal(Type type) {
			total.addTotal(type);

		}

		public Long getTotal(Type type) {
			return total.getTotal(type);
		}

		public void clear() {
			total.clear();
		}

		@Override
		public String toString() {
			return "email:" + total.toString();
		}

	}

}
