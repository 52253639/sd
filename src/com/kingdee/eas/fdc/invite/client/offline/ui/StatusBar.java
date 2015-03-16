package com.kingdee.eas.fdc.invite.client.offline.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.ColorUIResource;

import com.kingdee.eas.fdc.invite.client.offline.util.ResourceHelper;

public class StatusBar {

	private JLabel leftInfo = new JLabel("",ResourceHelper.getIcon("state_cue_1.png"),10);

	private JLabel rightInfo = new JLabel("");

	Color[] flashColor = new Color[] { Color.YELLOW, new ColorUIResource(0x62, 0x93, 0xBB) };

	JPanel statusPane = new JPanel();
	{
		statusPane.setLayout(new BorderLayout());
		statusPane.setBorder(BorderFactory.createEmptyBorder());
		statusPane.add(leftInfo, BorderLayout.WEST);
		statusPane.add(rightInfo, BorderLayout.EAST);
	}

	public void showTip(String tip) {
		final JLabel statusTip = new JLabel(tip,ResourceHelper.getIcon("state_cue_1.png"),10);
		statusTip.setSize(200,20);
		statusPane.remove(leftInfo);
		statusPane.add(statusTip, BorderLayout.WEST);
		statusPane.repaint();
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			int x;

			public void run() {
				statusTip.setForeground(flashColor[x++ % 2]);
				if (x > 10) {
					timer.cancel();
					statusPane.remove(statusTip);
					statusPane.add(leftInfo,BorderLayout.WEST);
					statusPane.repaint();
				}
			}
		}, 0, 800);

	}

	public void setFlashColor(Color[] colors) {
		if (colors != null && colors.length > 1)
			flashColor = colors;
	}

	public void setLeftInfo(String info) {
		leftInfo.setText(info);
		statusPane.repaint();
	}

	public void setRightInfo(String info) {
		rightInfo.setText(info);
		statusPane.repaint();
	}

	public JPanel getStatusPane() {
		return statusPane;
	}

	public void setBackground(Color color) {
		statusPane.setBackground(color);
	}

	public void setFont(Font font) {
		leftInfo.setFont(font);
		rightInfo.setFont(font);
	}

	public void setForeground(Color color) {
		leftInfo.setForeground(color);
		rightInfo.setForeground(color);
	}

	public void setLeftBackground(Color color) {
		leftInfo.setBackground(color);
	}

	public void setRightBackground(Color color) {
		rightInfo.setBackground(color);
	}

	public void setLeftForeground(Color color) {
		leftInfo.setForeground(color);
	}

	public void setRightForeground(Color color) {
		rightInfo.setForeground(color);
	}

}
