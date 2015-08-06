package cn.ichano.common.util.test;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import cn.ichano.common.util.TimeUtil;

public class TimeUtilTest {
	@Test
	public void testGetPreMonth() {

		Calendar c = Calendar.getInstance();
		for (int i = 0; i < 12; i++) {
			String month = c.get(Calendar.YEAR)
					+ StringUtils.leftPad(
							String.valueOf(c.get(Calendar.MONTH) + 1), 2, '0');

			
			String methodMonth = TimeUtil.getPreMonth(month, TimeUtil.YYYYMM);
			c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
			String realMonth = c.get(Calendar.YEAR)
					+ StringUtils.leftPad(
							String.valueOf(c.get(Calendar.MONTH) + 1), 2, '0');
			Assert.assertEquals(realMonth, methodMonth);

		}

		Assert.assertEquals("201412",
				TimeUtil.getPreMonth("201501", TimeUtil.YYYYMM));

		Assert.assertEquals("201501",
				TimeUtil.getNextMonth("201412", TimeUtil.YYYYMM));

	}

	@Test
	public void testIsPreMonth() {
		Assert.assertTrue(TimeUtil.isPreMonth("201412", TimeUtil.YYYYMM));
		Assert.assertTrue(TimeUtil.isPreMonth("201502", TimeUtil.YYYYMM));
	}

	@Test
	public void testSubString() {

		Assert.assertTrue("201412112".substring(0, 6).equals("201412"));
		Assert.assertTrue("201412112".substring(6, 8).equals("11"));
	}

	@Test
	public void testGetLastDay() {

		Assert.assertEquals(
				TimeUtil.getMaxDayOfMonth("201401", TimeUtil.YYYYMM), 31);
		Assert.assertEquals(
				TimeUtil.getMaxDayOfMonth("201402", TimeUtil.YYYYMM), 28);
		Assert.assertEquals(
				TimeUtil.getMaxDayOfMonth("201403", TimeUtil.YYYYMM), 31);
		Assert.assertEquals(
				TimeUtil.getMaxDayOfMonth("201404", TimeUtil.YYYYMM), 30);
		Assert.assertEquals(
				TimeUtil.getMaxDayOfMonth("201405", TimeUtil.YYYYMM), 31);
		Assert.assertEquals(
				TimeUtil.getMaxDayOfMonth("201406", TimeUtil.YYYYMM), 30);
		Assert.assertEquals(
				TimeUtil.getMaxDayOfMonth("201407", TimeUtil.YYYYMM), 31);
		Assert.assertEquals(
				TimeUtil.getMaxDayOfMonth("201408", TimeUtil.YYYYMM), 31);
		Assert.assertEquals(
				TimeUtil.getMaxDayOfMonth("201409", TimeUtil.YYYYMM), 30);
		Assert.assertEquals(
				TimeUtil.getMaxDayOfMonth("201410", TimeUtil.YYYYMM), 31);

		Assert.assertEquals(
				TimeUtil.getMaxDayOfMonth("201411", TimeUtil.YYYYMM), 30);

		Assert.assertEquals(
				TimeUtil.getMaxDayOfMonth("201412", TimeUtil.YYYYMM), 31);


	}
}
