package com.kingdee.eas.fdc.invite.client.offline;

import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.kingdee.eas.fdc.invite.client.offline.ui.MainFrame;
import com.kingdee.eas.fdc.invite.client.offline.ui.WaitWindow;

public class Main {
	static {
		Properties properties = new Properties();
		try {
			properties.load(Main.class.getResourceAsStream("log4j.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PropertyConfigurator.configure(properties);
	}

	private static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		if(MainFrame.currentFile!=null){
			return;
		}
		WaitWindow wait = null;
		Preferences preferences = Preferences.userRoot();
		if (preferences.getBoolean("isShow", true)) {
			wait = new WaitWindow();
		}
		try {
			UIManager.setLookAndFeel("com.kingdee.bos.ctrl.swing.plaf.KingdeeLookAndFeel");
		} catch (ClassNotFoundException e) {
			logger.error(e);
		} catch (InstantiationException e) {
			logger.error(e);
		} catch (IllegalAccessException e) {
			logger.error(e);
		} catch (UnsupportedLookAndFeelException e) {
			logger.error(e);
		}
		JFrame frame = new MainFrame("离线报价客户端");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Application().run();
			}
		});

		if (wait != null)
			wait.dispose();
	}

}
