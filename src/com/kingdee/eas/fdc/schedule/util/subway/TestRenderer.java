package com.kingdee.eas.fdc.schedule.util.subway;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class TestRenderer extends JLabel implements KDSubwayItemRenderer {

	public JComponent getSubwayRendererCompt(KDSubwayItemButton btnItem, Object detail) {
		setPreferredSize(new Dimension(220, 220));
		setOpaque(true);
		Map values = (Map) detail;

		String name = (String) values.get("name");
		String start = (String) values.get("start");
		String end = (String) values.get("end");
		String len = (String) values.get("natureTimes");
		String dept = (String) values.get("adminDept");
		String person = (String) values.get("adminPerson");
		String actualStartDate = (String) values.get("actualStartDate");
		String actualEndDate = (String) values.get("actualEndDate");
		// String admin = (String) values.get("admin");
		String checkDate = (String) values.get("checkDate");
		String complete = (String) values.get("complete");
		int stats = ((Integer) values.get("status")).intValue();
		String status = "";
		if (stats == 0) {
			status = "按时完成";
		} else if (stats == 1) {
			status = "延迟完成";
		} else if (stats == 2) {
			status = "未达到完成日期";
		} else {
			status = "延时未完成";
		}
		// String show1 = "<html>&nbsp;&nbsp;任务名称：" + name
		// + "<br>&nbsp;&nbsp;计划开始日期：" + start + "<br>&nbsp;&nbsp;标准工期："
		// + len + "<br>&nbsp;&nbsp;计划结束日期：" + end + "</html>";

		String show = "<html><body><font size=3><p>&nbsp;&nbsp;任务名称：" + name + "<br>&nbsp;&nbsp;计划开始日期：" + start + "<br>&nbsp;&nbsp;计划完成日期: " + end
				+ "<br>&nbsp;&nbsp;工期：" + len + "<br>&nbsp;&nbsp;责任部门：" + dept +"<br>&nbsp;&nbsp;责任人：" + person +"<br>&nbsp;&nbsp;实际开始日期：" + actualStartDate + "<br>&nbsp;&nbsp;实际完成日期：" + actualEndDate
				+ "<br>&nbsp;&nbsp;考核日期：" + checkDate + "<br>&nbsp;&nbsp;完成进度：" + complete + "<br>&nbsp;&nbsp;状态:" + status
				+ "</p></font></body></html>";
		setText(show);
		setBackground(new Color(221, 255, 221));
		setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		return this;
	}

}
