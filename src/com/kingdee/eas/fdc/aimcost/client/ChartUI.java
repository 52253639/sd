/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Rectangle;
import java.math.BigDecimal;

import com.kingdee.bos.ctrl.swing.KDChart;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.chart.Chart;
import com.kingdee.bos.ctrl.swing.chart.ChartType;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.ChartData;

/**
 * output class name
 */
public class ChartUI extends AbstractChartUI {
	public ChartUI() throws Exception {
		super();
	}

	private KDChart chart = new KDChart();

	/**
	 * 根据id显示窗体
	 */
	public static boolean showChart(IUIObject ui, ChartData data)
			throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("data", data);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWWIN)
				.create(ChartUI.class.getName(), uiContext, null, "View");
		uiWindow.show();
		return true;
	}

	/**
	 * @param args
	 * @throws UIException
	 */
	public static void main(String[] args) throws UIException {
		ChartData data = new ChartData(new String[] { "结算金额", "未结算金额" });
		data.addGroupData("一栋", new BigDecimal[] { new BigDecimal("1"),
				new BigDecimal("2") });
		data.addGroupData("二栋", new BigDecimal[] { new BigDecimal("2"),
				new BigDecimal("4") });
		ChartUI.showChart(null, data);
	}

	public void initUIContentLayout() {
		this.setBounds(new Rectangle(10, 10, 800, 600));
		this.setLayout(new KDLayout());
		this.putClientProperty("OriginalBounds",
				new Rectangle(10, 10, 780, 580));
		this.add(chart, new KDLayout.Constraints(11, 11, 760, 560,
				KDLayout.Constraints.ANCHOR_TOP
						| KDLayout.Constraints.ANCHOR_BOTTOM
						| KDLayout.Constraints.ANCHOR_LEFT
						| KDLayout.Constraints.ANCHOR_RIGHT));
		this.menuBar.setVisible(false);
	}

	public void onLoad() throws Exception {
		ChartData data = (ChartData) this.getUIContext().get("data");
		chart.setChartType(data.getChartType());
		chart.addChartData(data.getData());
		chart.getChartTitle().setText(data.getTitle());
		super.onLoad();
	}

	public void setTitle(String text) {
		this.chart.getChartTitle().setText(text);
	}
}