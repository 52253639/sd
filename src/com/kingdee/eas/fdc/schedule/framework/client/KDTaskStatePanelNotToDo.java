/**
 * @(#)		    KDTaskStatePanelNotToDo.java
 * 版权：	    金蝶国际软件集团有限公司版权所有
 * 描述：	
 * @author      王维强
 * @version     EAS7.0
 * @createDate  2011-12-16
 * @see
 */
package com.kingdee.eas.fdc.schedule.framework.client;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

/**
 * @(#)		    KDTaskStatePanelNotToDo.java 版权： 金蝶国际软件集团有限公司版权所有 描述：取消待确认状态图标
 * @author duhongming
 * @version EAS7.0
 * @createDate 2011-12-16
 * @see
 */
public class KDTaskStatePanelNotToDo extends KDTaskStatePanel {

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

		Dimension size = new Dimension(1000, 20);
		setPreferredSize(size);
		setSize(size);
	}
}
