package com.kingdee.eas.fdc.schedule.framework.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.eas.util.client.EASResource;

public class KDTaskStatePanel extends KDPanel {
	private static final long serialVersionUID = -5540592290581369462L;

	Logger logger = Logger.getLogger(KDTaskStatePanel.class);

	public static String rsPath = "com.kingdee.eas.fdc.schedule.ScheduleResource";
	// 按时完成
	public static String WELLDONE = EASResource.getString(rsPath, "welldone");
	// 延时完成
	public static String DONE = EASResource.getString(rsPath, "done");
	// 延时且未完成
	public static String LATE = EASResource.getString(rsPath, "late");
	// 待确认
	public static String TODO = EASResource.getString(rsPath, "todo");
	// 空格
	public static String space = " ";
	// 冒号
	public static String colon = "：";

	// 勾
	public static String achieve = EASResource.getString(rsPath, "achieve");
	// 圈
	public static String pending = EASResource.getString(rsPath, "pending");

	// 红
	public static Color RED = new Color(245, 0, 0);
	// 绿
	public static Color GREEN = new Color(10, 220, 10);
	// 橙
	public static Color ORANGE = new Color(220, 180, 0);

	public KDTaskStatePanel() {
		initLayout();
		initDefualtPanel();
	}

	public KDTaskStatePanel(String[] names, String[] values, Color[] colors, boolean[] isBords) {
		initLayout();
		initPanel(names, values, colors, isBords);
	}

	/**
	 * 水平间距为0
	 */
	private void initLayout() {
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		layout.setHgap(3);
		setLayout(layout);
	}

	/**
	 * 默认面板，包括 按时完成、延时完成、延时且未完成、待确认
	 */
	protected void initDefualtPanel() {
		int width = 0;

		String wd = space + WELLDONE + colon;
		JLabel lbWD = new JLabel(wd);
		width += lbWD.getFontMetrics(lbWD.getFont()).stringWidth(lbWD.getText());
		add(lbWD);
		JLabel lbWDV = new JLabel(achieve);
		lbWDV.setForeground(GREEN);
		lbWDV.setFont(lbWDV.getFont().deriveFont(Font.BOLD));
		add(lbWDV);
		width += lbWDV.getFontMetrics(lbWDV.getFont()).stringWidth(lbWDV.getText());

		String dn = space + DONE + colon;
		JLabel lbDN = new JLabel(dn);
		add(lbDN);
		width += lbDN.getFontMetrics(lbDN.getFont()).stringWidth(lbDN.getText());
		JLabel lbDNV = new JLabel(achieve);
		lbDNV.setForeground(RED);
		lbDNV.setFont(lbDNV.getFont().deriveFont(Font.BOLD));
		add(lbDNV);
		width += lbDNV.getFontMetrics(lbDNV.getFont()).stringWidth(lbDNV.getText());

		String lt = space + LATE + colon;
		JLabel lbLT = new JLabel(lt);
		add(lbLT);
		width += lbLT.getFontMetrics(lbLT.getFont()).stringWidth(lbLT.getText());
		JLabel lbLTV = new JLabel(pending);
		lbLTV.setForeground(RED);
		add(lbLTV);
		width += lbLTV.getFontMetrics(lbLTV.getFont()).stringWidth(lbLTV.getText());

		String td = space + TODO + colon;
		JLabel lbTD = new JLabel(td);
		add(lbTD);
		width += lbTD.getFontMetrics(lbTD.getFont()).stringWidth(lbTD.getText());
		JLabel lbTDV = new JLabel(pending);
		lbTDV.setForeground(ORANGE);
		add(lbTDV);
		width += lbTDV.getFontMetrics(lbTDV.getFont()).stringWidth(lbTDV.getText());

		Dimension size = new Dimension(1000, 20);
		setPreferredSize(size);
		setSize(size);
	}

	/**
	 * 初始化说明面板
	 * 
	 * @param names
	 *            名称
	 * @param values
	 *            表示字符
	 * @param colors
	 *            字符颜色
	 * @param isBords
	 *            是否粗体
	 */
	private void initPanel(String[] names, String[] values, Color[] colors, boolean[] isBords) {
		if (names == null || values == null || colors == null) {
			logger.error("null params");
			return;
		} else {
			int nlen = names.length;
			int vlen = values.length;
			int clen = colors.length;
			if (nlen == 0 || vlen == 0 || clen == 0) {
				logger.error("null params");
				return;
			} else if (nlen != vlen || nlen != clen) {
				logger.error("error params length");
				return;
			} else {
				int width = 0;
				for (int i = 0; i < nlen; i++) {
					JLabel lbName = new JLabel(space + names[i] + colon);
					add(lbName);
					width += lbName.getFontMetrics(lbName.getFont()).stringWidth(lbName.getText());
					JLabel lbValue = new JLabel(values[i]);
					lbValue.setForeground(colors[i]);
					if (isBords != null && isBords.length > i && isBords[i]) {
						Font font = lbValue.getFont().deriveFont(Font.BOLD);
						lbValue.setFont(font);
					}
					add(lbValue);
					width += lbValue.getFontMetrics(lbValue.getFont()).stringWidth(lbValue.getText());
				}
				Dimension size = new Dimension(width, 20);
				setPreferredSize(size);
				setSize(size);
			}
		}
	}
}
