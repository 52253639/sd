package com.kingdee.eas.fdc.schedule.framework.util;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import javax.swing.JTextField;

/**
 * 显示文档的标签
 * <p>
 * 每个文档置于一个标签中，再按顺序放入单元格中
 * 
 */
public class DocLabel extends JTextField {
	// 业务对象
	Map data;
	boolean isSelected;

	public DocLabel() {
		super();
		this.setEditable(false);
		this.setBorder(null);
		Font f = new Font("SansSerif", Font.HANGING_BASELINE, 16);
		this.setFont(f);
		this.setBackground(getBizColor());
	}

	public DocLabel(String text, Map data) {
		super();
		this.data = data;
		this.setEditable(false);
		this.setBorder(null);
		this.setText(text);
		this.setBackground(Color.white);
		this.setForeground(getBizColor());
	}

	public void setData(Map data) {
		this.data = data;
	}

	public Map getData() {
		return this.data;
	}

	public boolean isSelected() {
		return this.isSelected;
	}

	public void setIsSelected(boolean b) {
		this.isSelected = false;
	}

	public Color getBizColor() {
		String status = (String) data.get(Config.STATUS_KEY);
		if (status == null)
			return Color.WHITE;
		if (status.equals(Config.STATUS_AUDITED)) {
			return Config.BG_AUDITED_COLOR;
		} else if (status.equals(Config.STATUS_AUDITING)) {
			return Config.BG_AUDITING_COLOR;
		} else if (status.equals(Config.STATUS_NOTSUBMIT)) {
			return Config.BG_NOTSUBMIT_COLOR;
		}
		return null;
	}

}