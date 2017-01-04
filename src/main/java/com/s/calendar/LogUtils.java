package com.s.calendar;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/08.
 */
public class LogUtils {

	private static boolean bl = true;
	private static DateFormat formatter     = new SimpleDateFormat("yyyyMMddHH");
	private static DateFormat formatterinfo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final String LOG         = "/mnt/sdcard/com.electrocardio/log/";
	private static final String PACKETLOSS  = "/mnt/sdcard/com.electrocardio/packetloss/";

	public static void e(Exception e) {
		if (bl)
			Log.e("输出时间 ----" + formatterinfo.format(new Date()) + "----", "err: ", e);
	}

	public static void i(String str) {
		if (bl)
			Log.e("输出时间 ----" + formatterinfo.format(new Date()) + "----" +
					"", str);
	}


	/**
	 * log 输出
	 */
	public static void log(String cache) {

		out(cache, LOG);

	}

	/**
	 * packetLoss  丢包
	 */
	public static void packetLoss(String cache) {

		out(cache, PACKETLOSS);

	}

	/**
	 * 将内容写入指定文件
	 * 需求文件写入权限
	 */
	public static void out(String cache, String what) {

		if (bl) {

			cache = formatterinfo.format(new Date()) + " :  " + cache + "\n";
			FileOutputStream fos = null;

			try {

				String time = formatter.format(new Date());
				String fileName = "log-1.txt";     //如果满足不了  log-1 log-2。。。。

				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

					String path = what + time + "/";
					File dir = new File(path);

					if (!dir.exists()) {

						dir.mkdirs();

					}

					fos = new FileOutputStream(path + fileName, true);
					fos.write(cache.getBytes());

				}

			} catch (Exception e) {

				e(e);

			} finally {

				if (fos != null)

					try {

						fos.close();

					} catch (IOException e) {

						e.printStackTrace();

					}

			}

		}

	}

}
