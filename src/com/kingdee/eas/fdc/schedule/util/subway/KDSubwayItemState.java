package com.kingdee.eas.fdc.schedule.util.subway;

import java.awt.Color;

/**
 * 节点状态<br>
 * 每个地铁图节点都有自己的状态，可以以此确定节点的颜色<br>
 * 默认有4中颜色，建议：<br>
 * 完成-绿色；进行中-橙色；未完成-红色；未开始-白色。<br>
 * 地铁图构建的时候，会根据状态集合生成说明面板，用以指示每个节点的状态
 * 
 * @author emanon
 * 
 */
public class KDSubwayItemState {

	// 默认四种颜色，纯绿、土黄、大红
	public static Color DONE = Color.GREEN;
	public static Color DOING = Color.ORANGE;
	public static Color UNDO = Color.RED;
	public static Color TODO = Color.WHITE;

	public KDSubwayItemState(String name, Color color) {
		this.statusName = name;
		this.statusColor = color;
	}

	/**
	 * 节点状态的名称，显示在地铁图的下方说明行中
	 */
	private String statusName;

	/**
	 * 节点状态的颜色，用于地铁图中线条以及节点上色
	 */
	private Color statusColor;

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Color getStatusColor() {
		return statusColor;
	}

	public void setStatusColor(Color statusColor) {
		this.statusColor = statusColor;
	}

}
