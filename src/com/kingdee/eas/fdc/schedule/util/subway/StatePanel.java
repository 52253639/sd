package com.kingdee.eas.fdc.schedule.util.subway;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDPanel;

public class StatePanel extends KDPanel {
	private static final long serialVersionUID = -5540592290581369462L;

	Logger logger = Logger.getLogger(StatePanel.class);

	public static String rsPath = "com.kingdee.eas.fdc.schedule.ScheduleResource";
	// 按时完成
	public static String WELLDONE = "按时完成";
	// 延时完成
	public static String DONE = "延时完成";
	// 延时且未完成
	public static String LATE = "延时且未完成";
	// 待确认
	public static String TODO = "未达到完成日期";
	// 空格
	public static String space = " ";
	// 冒号
	public static String colon = ",";

	// 勾
	public static String achieve = "√";
	// 圈
	public static String pending = "○";
	// 叉
	public static String fault = "×";
	// 待确认
	public static String unSure = "?";

	// 红
	public static Color RED = new Color(255, 0, 0);
	// 绿
	public static Color GREEN = new Color(0, 128, 10);
	// 黄
	public static Color ORANGE = new Color(255, 204, 0);
	// 粉
	public static Color PINK = new Color(255, 153, 204);
	// 蓝
	public static Color BLUE= new Color(0, 0, 255);

	public StatePanel() {
		initLayout();
		initDefualtPanel();
	}

	public StatePanel(String[] names, String[] values, Color[] colors, boolean[] isBords) {
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
		width += lbWD.getFontMetrics(lbWD.getFont()).stringWidth(lbWD.getText())+20;
		JLabel lbWDV = new JLabel(achieve);
		lbWDV.setForeground(GREEN);
		lbWDV.setFont(lbWDV.getFont().deriveFont(Font.BOLD));
		add(lbWDV);
		add(lbWD);
		width += lbWDV.getFontMetrics(lbWDV.getFont()).stringWidth(lbWDV.getText())+20;

//		String dn = space + DONE + colon;
//		JLabel lbDN = new JLabel(dn);
//		width += lbDN.getFontMetrics(lbDN.getFont()).stringWidth(lbDN.getText());
//		JLabel lbDNV = new JLabel(pending);
//		lbDNV.setForeground(ORANGE);
//		lbDNV.setFont(new Font("Gungsuh", Font.BOLD, 15));
//		add(lbDNV);
//		add(lbDN);
//		width += lbDNV.getFontMetrics(lbDNV.getFont()).stringWidth(lbDNV.getText())+20;
//		
		String dn_1 = space + DONE + colon;
		JLabel lbDN_1 = new JLabel(dn_1);
		width += lbDN_1.getFontMetrics(lbDN_1.getFont()).stringWidth(lbDN_1.getText());
		JLabel lbDNV_1 = new JLabel(pending);
		lbDNV_1.setForeground(ORANGE);
		lbDNV_1.setFont(new Font("Gungsuh", Font.BOLD, 15));
		add(lbDNV_1);
		add(lbDN_1);
		width += lbWDV.getFontMetrics(lbDNV_1.getFont()).stringWidth(lbDNV_1.getText())+20;
//		width += lbDNV.getFontMetrics(lbDNV_1.getFont()).stringWidth(lbDNV_1.getText())+20;

		String lt = space + LATE + colon;
		JLabel lbLT = new JLabel(lt);
		width += lbLT.getFontMetrics(lbLT.getFont()).stringWidth(lbLT.getText());
		JLabel lbLTV = new JLabel(fault);
		lbLTV.setForeground(RED);
		lbLTV.setFont(new Font("Gungsuh", Font.BOLD, 15));
		add(lbLTV);
		add(lbLT);
		width += lbLTV.getFontMetrics(lbLTV.getFont()).stringWidth(lbLTV.getText())+20;

		String td = space + TODO + colon;
		JLabel lbTD = new JLabel(td);
		width += lbTD.getFontMetrics(lbTD.getFont()).stringWidth(lbTD.getText());
		JLabel lbTDV = new JLabel(unSure);
		lbTDV.setForeground(BLUE);
		lbTDV.setFont(new Font("Gungsuh", Font.BOLD, 15));
		add(lbTDV);
		add(lbTD);
		width += lbTDV.getFontMetrics(lbTDV.getFont()).stringWidth(lbTDV.getText())+20;

		Dimension size = new Dimension(1500, 200);
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
