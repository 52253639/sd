package com.kingdee.eas.fdc.schedule.util.subway;

import javax.swing.JComponent;

/**
 * 显示地铁图节点的详细信息渲染器抽象
 * <p>
 * 建议子类继承JLabel<br>
 * 设置背景色为草绿，setBackground(new Color(221, 255, 221));<br>
 * 设置边框为线条，setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));<br>
 * 
 * 具体示例见TestRenderer
 * 
 * @author emanon
 * 
 */
public interface KDSubwayItemRenderer {

	/**
	 * 返回一个面板，上面为需要显示的信息
	 * 
	 * @param btnItem
	 *            地铁图节点按钮
	 * @param detail
	 * @return
	 */
	public abstract JComponent getSubwayRendererCompt(
			KDSubwayItemButton btnItem, Object detail);

}
