package com.ten31f.app;

import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.ten31f.tools.Scrapper;

public class App {

	public static final String FORMAT = "youtube-dl \"%s\"";

	private static Logger LOGGER = Logger.getLogger(App.class.getName());

	static {
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new SimpleFormatter() {
			private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

			@Override
			public String formatMessage(LogRecord record) {
				return String.format(format, new Date(record.getMillis()), record.getLevel().getLocalizedName(),
						record.getMessage());
			}
		});
		LOGGER.setUseParentHandlers(false);
		LOGGER.addHandler(handler);
	}

	public static void main(String[] args) {		

		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");

		Scrapper scrapper = new Scrapper(args[0]);

		scrapper.setTagFilter(true);

		scrapper.scrap();

	}

}
