/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.chart.ChartFactory;
import com.kingdee.bos.ctrl.freechart.chart.ChartPanel;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardPieSectionLabelGenerator;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardPieToolTipGenerator;
import com.kingdee.bos.ctrl.freechart.chart.plot.PiePlot3D;
import com.kingdee.bos.ctrl.freechart.chart.title.LegendTitle;
import com.kingdee.bos.ctrl.freechart.chart.title.TextTitle;
import com.kingdee.bos.ctrl.freechart.data.general.Dataset;
import com.kingdee.bos.ctrl.freechart.data.general.DefaultPieDataset;
import com.kingdee.bos.ctrl.freechart.data.general.PieDataset;
import com.kingdee.bos.ctrl.freechart.ui.RectangleEdge;
import com.kingdee.bos.ctrl.freechart.util.Rotation;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskStateHelper;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryCollection;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportEntryInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportInfo;
import com.kingdee.eas.fdc.schedule.framework.client.KDTaskStatePanel;
import com.kingdee.eas.fdc.schedule.framework.client.ScheduleChartProvider;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ViewProjectWeekReportPrintUI extends AbstractViewProjectWeekReportPrintUI {
	private static final long serialVersionUID = -4155418224252894875L;
	private static final Logger logger = CoreUIObject.getLogger(ViewProjectWeekReportPrintUI.class);

	private ProjectSpecialInfo ps;
	private CurProjectInfo prj;
	public ViewProjectWeekReportPrintUI() throws Exception {
		super();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		this.setUITitle("项目报告打印界面");
		KDTaskStatePanel pnlDesc = new KDTaskStatePanel();
		kDthisWeek.getContentPane().add(pnlDesc, BorderLayout.SOUTH);
		this.kdtThisEntry.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		this.kdtThisEntry.getStyleAttributes().setLocked(true);
		this.kdtNextEntrys.getStyleAttributes().setLocked(true);
		onladDataOfTable();
		kdtThisEntry.getColumn("description").setWidth(200);//
		kdtNextEntrys.getColumn("respPerson").setWidth(300);
		kdtNextEntrys.getColumn("respDept").setWidth(370);
		this.kDSplitPane1.setDividerLocation(250);
		this.kDSplitPane2.setDividerLocation(225);
	}

	protected void initWorkButton() {
		btnExportExcel.setEnabled(true);
		btnExportExcel.setIcon(EASResource.getIcon("imgTbtn_output"));
		this.btnPrint.setEnabled(true);
		btnPrint.setIcon(EASResource.getIcon("imgTbtn_print"));
		this.btnPrintPreview.setEnabled(true);
		btnPrintPreview.setIcon(EASResource.getIcon("imgTbtn_preview"));
		super.initWorkButton();
	}

	/**
	 * @description 根据传过来的对象得到数据
	 * @author lijianbo
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */
	public void onladDataOfTable() throws Exception {
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof ProjectWeekReportEditUI) {
			// /* ProjectWeekReportEditUI projectWeekReport =
			// (ProjectWeekReportEditUI) getUIContext().get("Owner");
			// String editDataId = projectWeekReport.editDataId;
			// EntityViewInfo view = new EntityViewInfo();
			// view.getSelector().add(new SelectorItemInfo("*"));
			// view.getSelector().add(new SelectorItemInfo("project.*"));
			// view.getSelector().add(new SelectorItemInfo("entrys.*"));
			// view.getSelector().add(new
			// SelectorItemInfo("entrys.relateTask.*"));
			// view.getSelector().add(new
			// SelectorItemInfo("entrys.respPerson.*"));
			// view.getSelector().add(new
			// SelectorItemInfo("entrys.respDept.*"));
			// FilterInfo filter = new FilterInfo();
			// filter.getFilterItems().add(new FilterItemInfo("id",
			// editDataId));
			// view.setFilter(filter);
			// ProjectWeekReportCollection collection =
			// ProjectWeekReportFactory.
			// getRemoteInstance().getProjectWeekReportCollection(view);
			// if (collection.size() > 0) {*/
			ProjectWeekReportInfo projectWeekReportInfo = (ProjectWeekReportInfo) getUIContext().get("reportInfo");
			   if (projectWeekReportInfo.getProjectSpecial() != null) {
				ps = projectWeekReportInfo.getProjectSpecial();
				prj = projectWeekReportInfo.getProject();
			} else {
				prj = projectWeekReportInfo.getProject();
			}
				String state = projectWeekReportInfo.getState().toString();
				ProjectWeekReportEntryCollection entryCollection = projectWeekReportInfo.getEntrys();
				List thisWeek = new ArrayList();
				List nextWeek = new ArrayList();

				if (entryCollection.size() > 0) {
					for (int i = 0; i < entryCollection.size(); i++) {
						ProjectWeekReportEntryInfo entryInfo = entryCollection.get(i);
						if (entryInfo.isIsNext()) {
							nextWeek.add(entryInfo);
						} else {
							thisWeek.add(entryInfo);
						}
					}
				}
				loadThisTableFields(thisWeek, state);
				loadNextTableFields(nextWeek);
				initChart(thisWeek);
			}
		// }

	}
	
	protected void initChart(List thisWeek) throws Exception {
		kDPanelImage.add(createChartPanel(thisWeek), BorderLayout.CENTER);
	}

	/**
	 * @description 创建一个图表面板
	 * @author lijianbo
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */
	protected JPanel createChartPanel(List thisWeek) throws Exception {
		JFreeChart chart = createChart(createDataset(thisWeek));
		return new ChartPanel(chart);
	}

	/**
	 * @description 构建图表所需的数据源
	 * @author lijianbo
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */
	protected Dataset createDataset(List thisWeek) {
		DefaultPieDataset result = new DefaultPieDataset();
		if (thisWeek.size() > 0) {
			int finished = 0;// 完成数
			int delayed = 0;// 延时数
			int unfinished = 0;// 未完成数
			int excudeing = 0;// 执行中
			int affirm = 0;// 待确认
			for (int i = 0; i < kdtThisEntry.getRowCount(); i++) {
				Object value = kdtThisEntry.getRow(i).getCell("state").getUserObject();
				if (value == null) {
					excudeing++;
					continue;
				}

				int state = Integer.parseInt(value.toString());
				switch (state) {
				case 0:
					finished++;
					break;
				case 1:
					delayed++;
					break;
				case 2:
					affirm++;
					break;
				case 3:
					unfinished++;
					break;
				}

				}
			double fin = Double.parseDouble(String.valueOf(finished));
			double dely = Double.parseDouble(String.valueOf(delayed));
			double aff = Double.parseDouble(String.valueOf(affirm));
			double unfin = Double.parseDouble(String.valueOf(unfinished));
			double excude = Double.parseDouble(String.valueOf(excudeing));
			double total = fin + dely + aff + unfin + excude;
			if (finished == thisWeek.size()) {
				result.setValue("按时完成", total);
			} else {
				result.setValue("按时完成", fin);
			}
			if (delayed == thisWeek.size()) {
				result.setValue("延时完成", total);
			} else {
				result.setValue("延时完成", dely);
			}
			if (affirm == thisWeek.size()) {
				result.setValue("待确认", total);
			} else {
				result.setValue("待确认", aff);
			}
			if (unfinished == thisWeek.size()) {
				result.setValue("延迟未完成", total);
			} else {
				result.setValue("延迟未完成", unfin);
			}
			if (excude == thisWeek.size()) {
				result.setValue("执行中", total);
			} else {
				result.setValue("执行中", excude);
			}
		}
		return result;
	}

	/**
	 * 返回图片名称
	 * 
	 * @return
	 */
	public String getChartTitle() throws Exception {
		String content = "";
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof ProjectWeekReportEditUI) {
//			ProjectWeekReportEditUI projectWeekReport = (ProjectWeekReportEditUI) getUIContext().get("Owner");
//			String editDataId = projectWeekReport.editDataId;
//			EntityViewInfo view = new EntityViewInfo();
//			view.getSelector().add(new SelectorItemInfo("*"));
//			view.getSelector().add(new SelectorItemInfo("project.*"));
//			view.getSelector().add(new SelectorItemInfo("entrys.*"));
//			view.getSelector().add(new SelectorItemInfo("entrys.relateTask.*"));
//			view.getSelector().add(new SelectorItemInfo("entrys.respPerson.*"));
//			view.getSelector().add(new SelectorItemInfo("entrys.respDept.*"));
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("id", editDataId));
//			view.setFilter(filter);
			// ProjectWeekReportCollection collection =
			// ProjectWeekReportFactory.
			// getRemoteInstance().getProjectWeekReportCollection(view);
			// if (collection.size() > 0) {
			ProjectWeekReportInfo projectWeekReportInfo = (ProjectWeekReportInfo) getUIContext().get("reportInfo");
				ProjectWeekReportEntryCollection entryCollection = projectWeekReportInfo.getEntrys();
				List thisWeek = new ArrayList();
				List nextWeek = new ArrayList();

				if (entryCollection.size() > 0) {
					for (int i = 0; i < entryCollection.size(); i++) {

						ProjectWeekReportEntryInfo entryInfo = entryCollection.get(i);
						if (entryInfo.isIsNext()) {
							nextWeek.add(entryInfo);
						} else {
							thisWeek.add(entryInfo);
						}
					}
				}
				content = projectWeekReportInfo.getYear() + "年第" + projectWeekReportInfo.getWeek() + "周"
						+ projectWeekReportInfo.getProject().getName() + "项目周报";
				String title = projectWeekReportInfo.getYear() + "年第" + projectWeekReportInfo.getWeek() + "周";
				if (thisWeek.size() > 0 && thisWeek != null) {
					int finished = 0;// 完成数
					int delayed = 0;// 延时数
					int unfinished = 0;// 未完成数
					int excudeing = 0;// 执行中
					int affirm = 0;// 待确认
				for (int i = 0; i < kdtThisEntry.getRowCount(); i++) {
					Object value = kdtThisEntry.getRow(i).getCell("state").getUserObject();
					if (value == null) {
						excudeing++;
						continue;
					}

						int state = Integer.parseInt(value.toString());
						switch (state) {
						case 0:
							finished++;
							break;
						case 1:
							delayed++;
							break;
						case 2:
							affirm++;
							break;
						case 3:
							unfinished++;
							break;
						}

						}

					String str = title + "工作共" + thisWeek.size() + "项,按时完成" + finished + "项,延时完成" + delayed + "项,待确认" + affirm + "项,延时且未完成"
							+ unfinished + "项,执行中" + excudeing + "项。计划下周工作共有" + nextWeek.size() + "项,见如下饼状图：";
					Font font = new Font(str, Font.PLAIN, 12);
					content = content + "\n" + font.getName();
				} else {
					String str = title + "工作共" + thisWeek.size() + "项,按时完成0项,延时完成0项,待确认0项,延时且未完成0项,执行中0项。计划下周工作共有" + nextWeek.size() + "项," + "\n"
							+ "见如下饼状图：";
					Font font = new Font(str, Font.PLAIN, 12);
					content = content + "\n" + font.getName();
				}

			}

		return content;

	}

	/**
	 * 返回一个图表
	 * 
	 * @param dataset
	 * @return
	 */
	protected JFreeChart createChart(Dataset dataset) throws Exception {
		JFreeChart chart = ChartFactory.createPieChart3D(getChartTitle(), (PieDataset) dataset, false, true, false);
		chart.setBackgroundPaint(Color.white);
		LegendTitle legendtitle = new LegendTitle(chart.getPlot());
		legendtitle.setPosition(RectangleEdge.RIGHT);
		chart.addSubtitle(legendtitle);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		// 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
		plot
				.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}\r\n({2})", NumberFormat.getNumberInstance(), new DecimalFormat(
						"0.00%")));
		plot.setStartAngle(0);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setCircular(true);
		plot.setOutlineStroke(new BasicStroke(0));// 边框粗细
		plot.setOutlinePaint(Color.white);// 边框颜色
		plot.setLabelFont(new Font("黑体", Font.PLAIN, 10));
		plot.setForegroundAlpha(0.75f);
		plot.setBackgroundPaint(Color.white);
		 // 设置突出显示的数据块
		plot.setToolTipGenerator(new StandardPieToolTipGenerator("{0}:{1}\r\n({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
		// pie.setSectionLabelFont(new Font("黑体", Font.TRUETYPE_FONT, 12));
		// 设定背景透明度（0-1.0之间）
		plot.setBackgroundAlpha(0.6f);
		// 设定前景透明度（0-1.0之间）
		plot.setForegroundAlpha(0.9f);
		plot.setDepthFactor(0.08);
		plot.setNoDataMessage("未加载数据");
		plot.setSectionPaint("按时完成", Color.GREEN);
		plot.setSectionPaint("延时完成", Color.RED);
		plot.setSectionPaint("待确认", Color.ORANGE);
		plot.setSectionPaint("延迟未完成", new Color(139, 0, 180));
		plot.setSectionPaint("执行中", new Color(0, 82, 41));

		String[] titls = chart.getTitle().getText().split("\n");
		if (titls.length > 1) {
			chart.setTitle(new TextTitle(titls[0], new Font("黑体", Font.BOLD, 18)));
			chart.addSubtitle(new TextTitle(titls[1], new Font("黑体", Font.BOLD, 12)));
			chart.getSubtitle(1).setPadding(0, 55, 0, -10);
		}
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 12)); 
		return chart;
	}

	public void loadNextTableFields(List list) {
		kdtNextEntrys.removeRows(false);
		this.kdtNextEntrys.checkParsed();
		if (list.size() > 0) {// 集合大于0
			for (int i = 0; i < list.size(); i++) {// 循环将分录放入表格中
				ProjectWeekReportEntryInfo entryInfo = (ProjectWeekReportEntryInfo) list.get(i);
				IRow row = kdtNextEntrys.addRow();
				row.getCell("taskName").setValue(entryInfo.getTaskName());
				row.getCell("planStartDate").setValue(entryInfo.getPlanStartDate());
				row.getCell("planEndDate").setValue(entryInfo.getPlanEndDate());
				//row.getCell("relateTask").setValue(entryInfo.getRelateTask());
				row.getCell("respPerson").setValue(entryInfo.getRespPerson());
				row.getCell("respDept").setValue(entryInfo.getRespDept());
				row.getCell("entryID").setValue(entryInfo.getId());

			}
		}

	}

	/*
	 * 将对象中的数据，显示到本期表格中
	 * 
	 * @see
	 * com.kingdee.eas.fdc.schedule.client.OpReportBaseUI#loadThisTableFields()
	 */
	public void loadThisTableFields(List list, String state) throws BOSException, SQLException {
		kdtThisEntry.removeRows(false);
		this.kdtThisEntry.checkParsed();
		if (list.size() > 0) {// 集合大于0
			ProjectWeekReportEntryInfo entryInfo = null;
			entryInfo = (ProjectWeekReportEntryInfo) list.get(0);
			String costCenterId = null;
			
			String sql = "select fcostcenterid from t_fdc_curproject where fid =(select fprojectid from t_sch_fdcschedule where fid = (select fscheduleid from t_sch_fdcscheduletask where fid = ?))";
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql(sql);
			builder.addParam(entryInfo.getRelateTask().getId().toString());
			IRowSet rs = builder.executeQuery();
			while (rs.next()) {
				costCenterId = rs.getString(1);
			}
			
			Object obj = ps == null ? prj : ps;

			boolean isParamControl = ViewReportHelper.isStartingParamControl(costCenterId, obj);
			boolean isEvaluation = false;
			Set<String> evSet = null;
			if (isParamControl) {
				FDCScheduleTaskCollection cols = new FDCScheduleTaskCollection();
				for (int i = 0; i < list.size(); i++) {
					entryInfo = (ProjectWeekReportEntryInfo) list.get(i);
					cols.add(entryInfo.getRelateTask());
				}
				evSet = FDCScheduleTaskStateHelper.isEvaluationed(null, cols);
			}
			for (int i = 0; i < list.size(); i++) {// 循环将分录放入表格中
				isEvaluation = false;
				entryInfo = (ProjectWeekReportEntryInfo) list.get(i);
				IRow row = kdtThisEntry.addRow();
				row.getCell("taskName").setValue(entryInfo.getTaskName());
				row.getCell("planEndDate").setValue(entryInfo.getPlanEndDate());
				row.getCell("completePercent").setValue(new Integer(entryInfo.getCompletePrecent()));
				row.getCell("realEndDate").setValue(entryInfo.getRealEndDate());
				row.getCell("intendEndDate").setValue(entryInfo.getIntendEndDate());
				if (entryInfo.getDescription() != null && !"".equals(entryInfo.getDescription())) {
					row.getCell("description").setValue(entryInfo.getDescription());
				} else {
					row.getCell("description").setValue("");
				}
				row.getCell("respPerson").setValue(entryInfo.getRespPerson());
				row.getCell("respDept").setValue(entryInfo.getRespDept());

				row.getCell("entryID").setValue(entryInfo.getId());
				if (evSet != null && evSet.contains(entryInfo.getRelateTask().getSrcID())) {
					isEvaluation = true;
				}
				ViewReportHelper.initStateCell(isEvaluation, isParamControl, row, entryInfo);
			}
		}
	}

	/**
	 * @description 对于表格数据不允许修改
	 * @author lijianbo
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */

	protected void kdtNextEntrys_tableClicked(KDTMouseEvent e) throws Exception {
		if (this.kdtNextEntrys.getSelectManager().getActiveRowIndex() >= 0) {
			IRow row = this.kdtNextEntrys.getRow(this.kdtNextEntrys.getSelectManager().getActiveRowIndex());

			row.getCell("taskName").getStyleAttributes().setLocked(true);
			row.getCell("planStartDate").getStyleAttributes().setLocked(true);
			row.getCell("planEndDate").getStyleAttributes().setLocked(true);
			row.getCell("respPerson").getStyleAttributes().setLocked(true);
			row.getCell("respDept").getStyleAttributes().setLocked(true);
		} else {
			return;
		}
	}

	/**
	 * @description 对于表格数据不允许修改
	 * @author lijianbo
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */
	protected void kdtThisEntry_tableClicked(KDTMouseEvent e) throws Exception {
		IRow row = this.kdtThisEntry.getRow(this.kdtThisEntry.getSelectManager().getActiveRowIndex());
		if (null == row) {
			SysUtil.abort();
		}
		row.getCell("state").getStyleAttributes().setLocked(true);
		row.getCell("taskName").getStyleAttributes().setLocked(true);
		row.getCell("planEndDate").getStyleAttributes().setLocked(true);
		// row.getCell("isComplete").getStyleAttributes().setLocked(true);
		row.getCell("completePercent").getStyleAttributes().setLocked(true);
		row.getCell("realEndDate").getStyleAttributes().setLocked(true);
		row.getCell("intendEndDate").getStyleAttributes().setLocked(true);
		row.getCell("description").getStyleAttributes().setLocked(true);
		row.getCell("respPerson").getStyleAttributes().setLocked(true);
		row.getCell("respDept").getStyleAttributes().setLocked(true);

		super.kdtThisEntry_tableClicked(e);
	}

	public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception {
		List tables = new ArrayList();
		tables.add(new Object[] { "本周任务", kdtThisEntry });
		tables.add(new Object[] { "下周任务", kdtNextEntrys });
		FDCTableHelper.exportExcel(this, tables);
	}

	/**
	 * 返回一张图片
	 * <p>
	 * 通常是由界面生成，包括图表和表格上下两部分<br>
	 * 
	 * 建议子类重写此方法，否则导出的图片将与页面完全相同<br>
	 * 可能导致表格的某些行或者列看不到，且间隔处有黑条
	 * 
	 * @return
	 */
	protected BufferedImage getChartIMG() {
		// int height2 = kDPanelImage.getHeight();
		// int width2 = kDPanelImage.getWidth();
		// // kDPanelImage.add(KDTitle);
		//
		// BufferedImage img = new BufferedImage(width2, height2,
		// BufferedImage.TYPE_INT_RGB);
		// Graphics g = img.getGraphics();
		// kDPanelImage.paintComponents(g);
		// return img;

		// 打印前窄点
		Dimension size = kDPanelImage.getSize();
		int w1 = size.width;
		int h1 = size.height;
		size.setSize(w1 + 780, h1);
		kDPanelImage.setSize(size);
		int height2 = kDPanelImage.getHeight();
		int width2 = kDPanelImage.getWidth();
		// kDPanelImage.add(KDTitle);

		BufferedImage img = new BufferedImage(width2, height2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		kDPanelImage.paintComponents(g);
		// 改回去
		size.setSize(w1, h1);
		kDPanelImage.setSize(size);
		return img;
	}

	protected BufferedImage getImgAndTable() {
		// BufferedImage img1 = getChartIMG();
		// int w1 = img1.getWidth();
		// int h1 = img1.getHeight();
		// // 本周任务表格
		// KDTSelectBlock curSelect = kdtThisEntry.getSelectManager().get();
		// // 计算表格最合适宽度(2个像素的边界+序号列宽+所有列宽和)
		// int fitWidth = 2 + kdtThisEntry.getIndexColumn().getWidth() +
		// kdtThisEntry.getColumns().getWidth();
		// // 计算表格最合适高度(2个像素的边界+表头高+所有行高和)
		// int fitHeight = 2 + kdtThisEntry.getHead().getHeight() +
		// kdtThisEntry.getBody().getHeight();
		// // 清除选择
		// kdtThisEntry.getSelectManager().remove();
		// // 此句不可去掉，用于在生成图片前设置表格合适大小
		// kdtThisEntry.setSize(fitWidth, fitHeight);
		//
		// // 3、将表格导出为img2
		// int w2 = kdtThisEntry.getWidth() + 10;
		// int h2 = kdtThisEntry.getHeight() + 10;
		// BufferedImage img2 = new BufferedImage(w2, h2,
		// BufferedImage.TYPE_INT_RGB);
		// Graphics g = img2.getGraphics();
		// kdtThisEntry.paintAll(g);
		// g.dispose();
		// float scale = (float) w2 / w1;
		// w1 = w2;
		// h1 = (int) (h1 * scale);
		// BufferedImage img = new BufferedImage(w1, h1 + h2,
		// BufferedImage.TYPE_INT_RGB);
		// Image scaleImage = img1.getScaledInstance(w1, h1,
		// Image.SCALE_AREA_AVERAGING);
		// g = img.createGraphics();
		// g.drawImage(scaleImage, 0, 0, w1, h1, null);
		//
		// // 5、将img2画在img的下方
		// g.drawImage(img2, 0, h1, w2, h2, null);
		//
		// g.dispose();
		//
		// // 6、将表格高宽设回原样，并选择之前所选择的行
		// if (curSelect != null) {
		// kdtThisEntry.getSelectManager().select(curSelect);
		// }
		//
		// // 7、返回img
		// return img;
		BufferedImage img1 = getChartIMG();
		int w1 = img1.getWidth();
		int h1 = img1.getHeight();
		// 本周任务表格
		KDTSelectBlock curSelect = kdtThisEntry.getSelectManager().get();
		// 计算表格最合适宽度(2个像素的边界+序号列宽+所有列宽和)
		int fitWidth = 2 + kdtThisEntry.getIndexColumn().getWidth() + kdtThisEntry.getColumns().getWidth();
		// 计算表格最合适高度(2个像素的边界+表头高+所有行高和)
		int fitHeight = 2 + kdtThisEntry.getHead().getHeight() + kdtThisEntry.getBody().getHeight();
		// 清除选择
		kdtThisEntry.getSelectManager().remove();
		// 此句不可去掉，用于在生成图片前设置表格合适大小
		kdtThisEntry.setSize(fitWidth, fitHeight);

		// 3、将表格导出为img2
		// 打印钱窄点
		kdtThisEntry.getColumn("description").setWidth(148);
		kdtThisEntry.getColumn("taskName").setWidth(200);
		kdtThisEntry.getColumn("planEndDate").setWidth(100);
		kdtThisEntry.getColumn("completePercent").setWidth(50);
		kdtThisEntry.getColumn("realEndDate").setWidth(100);
		kdtThisEntry.getColumn("intendEndDate").setWidth(96);
		 int w2 = kdtThisEntry.getWidth() + 10 + 290;
		// int w2 = kdtThisEntry.getWidth() + 10 + 430;
		int h2 = kdtThisEntry.getHeight() + 10;
		BufferedImage img2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img2.getGraphics();
		kdtThisEntry.paintAll(g);
		g.dispose();
		float scale = (float) w2 / w1;
		w1 = w2;
		h1 = (int) (h1 * scale);
		BufferedImage img = new BufferedImage(w1, h1 + h2, BufferedImage.TYPE_INT_RGB);
		Image scaleImage = img1.getScaledInstance(w1, h1, Image.SCALE_AREA_AVERAGING);
		g = img.createGraphics();
		g.drawImage(scaleImage, 0, 0, w1, h1, null);

		// 5、将img2画在img的下方
		g.drawImage(img2, 0, h1, w2, h2, null);

		g.dispose();

		// 6、将表格高宽设回原样，并选择之前所选择的行
		if (curSelect != null) {
			kdtThisEntry.getSelectManager().select(curSelect);
		}
		// 打印后窄点
		kdtThisEntry.getColumn("description").setWidth(200);
		kdtThisEntry.getColumn("taskName").setWidth(300);
		kdtThisEntry.getColumn("planEndDate").setWidth(140);
		kdtThisEntry.getColumn("completePercent").setWidth(80);
		kdtThisEntry.getColumn("realEndDate").setWidth(140);
		kdtThisEntry.getColumn("intendEndDate").setWidth(140);
		// 7、返回img
		return img;
		
	}

	protected BufferedImage getUIIMG() {
		// // 1、将图表面板导出为img1
		// BufferedImage img1 = getImgAndTable();
		// int w1 = img1.getWidth();
		// int h1 = img1.getHeight();
		//
		// KDTSelectBlock curSelect = kdtNextEntrys.getSelectManager().get();
		// // 计算表格最合适宽度(2个像素的边界+序号列宽+所有列宽和)
		// int fitWidth = 2 + kdtNextEntrys.getIndexColumn().getWidth() +
		// kdtNextEntrys.getColumns().getWidth();
		// // 计算表格最合适高度(2个像素的边界+表头高+所有行高和)
		// int fitHeight = 2 + kdtNextEntrys.getHead().getHeight() +
		// kdtNextEntrys.getBody().getHeight();
		// // 清除选择
		// kdtNextEntrys.getSelectManager().remove();
		// // 此句不可去掉，用于在生成图片前设置表格合适大小
		// kdtNextEntrys.setSize(fitWidth, fitHeight);
		//
		// // 3、将表格导出为img2
		// int w2 = kdtNextEntrys.getWidth();
		// int h2 = kdtNextEntrys.getHeight();
		// BufferedImage img2 = new BufferedImage(w2, h2,
		// BufferedImage.TYPE_INT_RGB);
		// Graphics g = img2.getGraphics();
		// kdtNextEntrys.paintAll(g);
		// g.dispose();
		//
		// // 4、新建img，将img1等比缩放成宽度与img2一样，并画在img的上方
		// float scale = (float) w2 / w1;
		// w1 = w2;
		// h1 = (int) (h1 * scale);
		// BufferedImage img = new BufferedImage(w1, h1 + h2,
		// BufferedImage.TYPE_INT_RGB);
		// Image scaleImage = img1.getScaledInstance(w1, h1,
		// Image.SCALE_AREA_AVERAGING);
		// g = img.createGraphics();
		// g.drawImage(scaleImage, 0, 0, w1, h1, null);
		//
		// // 5、将img2画在img的下方
		// g.drawImage(img2, 0, h1, w2, h2, null);
		// g.dispose();
		// if (curSelect != null) {
		// kdtNextEntrys.getSelectManager().select(curSelect);
		// }
		//
		// // 7、返回img
		// return img;
		// 1、将图表面板导出为img1
		BufferedImage img1 = getImgAndTable();
		int w1 = img1.getWidth();
		int h1 = img1.getHeight();

		KDTSelectBlock curSelect = kdtNextEntrys.getSelectManager().get();
		// 计算表格最合适宽度(2个像素的边界+序号列宽+所有列宽和)
		int fitWidth = 2 + kdtNextEntrys.getIndexColumn().getWidth() + kdtNextEntrys.getColumns().getWidth();
		// 计算表格最合适高度(2个像素的边界+表头高+所有行高和)
		int fitHeight = 2 + kdtNextEntrys.getHead().getHeight() + kdtNextEntrys.getBody().getHeight();
		// 清除选择
		kdtNextEntrys.getSelectManager().remove();
		// 此句不可去掉，用于在生成图片前设置表格合适大小
		kdtNextEntrys.setSize(fitWidth, fitHeight);

		// 3、将表格导出为img2
		// 下面4行代码在设置了图片长度后 设置回去
		int printpre1 = kdtNextEntrys.getColumn(0).getWidth();
		int printpre2 = kdtNextEntrys.getColumn(1).getWidth();
		int printpre3 = kdtNextEntrys.getColumn(2).getWidth();
		int printpre4 = kdtNextEntrys.getColumn(3).getWidth();
		int printpre5 = kdtNextEntrys.getColumn(4).getWidth();
		kdtNextEntrys.getColumn(0).setWidth(180);
		kdtNextEntrys.getColumn(1).setWidth(180);
		kdtNextEntrys.getColumn(2).setWidth(180);
		kdtNextEntrys.getColumn(3).setWidth(180);
		kdtNextEntrys.getColumn(4).setWidth(200);
		int w2 = kdtNextEntrys.getWidth();
		int h2 = kdtNextEntrys.getHeight();
		BufferedImage img2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img2.getGraphics();
		kdtNextEntrys.paintAll(g);
		g.dispose();

		// 4、新建img，将img1等比缩放成宽度与img2一样，并画在img的上方
		float scale = (float) w2 / w1;
		w1 = w2;
		h1 = (int) (h1 * scale);
		BufferedImage img = new BufferedImage(w1 - 550, h1 + h2, BufferedImage.TYPE_INT_RGB);
		Image scaleImage = img1.getScaledInstance(w1, h1, Image.SCALE_AREA_AVERAGING);
		g = img.createGraphics();
		g.drawImage(scaleImage, 0, 0, w1, h1, null);

		// 5、将img2画在img的下方
		g.drawImage(img2, 0, h1, w2, h2, null);
		g.dispose();
		if (curSelect != null) {
			kdtNextEntrys.getSelectManager().select(curSelect);
		}
		// 下面4行代码在设置了图片长度后 设置回去
		kdtNextEntrys.getColumn(0).setWidth(printpre1);
		kdtNextEntrys.getColumn(1).setWidth(printpre2);
		kdtNextEntrys.getColumn(2).setWidth(printpre3);
		kdtNextEntrys.getColumn(3).setWidth(printpre4);
		kdtNextEntrys.getColumn(4).setWidth(printpre5);
		// 7、返回img
		return img;
	}

	/**
	 * 返回套打模板路径
	 * <p>
	 * 子类需重载
	 * 
	 * @return
	 */
	protected String getTDPath() {
		return "/bim/fdc/process/processProjectbill";
	}

	/**
	 * 打印
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ScheduleChartProvider dataPvd = new ScheduleChartProvider(getChartTitle(), getUIIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(getTDPath(), dataPvd, SwingUtilities.getWindowAncestor(this));
	}

	/**
	 * 打印预览
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		ScheduleChartProvider dataPvd = new ScheduleChartProvider(getChartTitle(), getUIIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview(getTDPath(), dataPvd, SwingUtilities.getWindowAncestor(this));
	}
}