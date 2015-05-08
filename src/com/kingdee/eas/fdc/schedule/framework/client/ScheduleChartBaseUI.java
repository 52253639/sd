/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.framework.client;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.freechart.chart.encoders.SunJPEGEncoderAdapter;
import com.kingdee.bos.ctrl.freechart.util.PngEncoder;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.util.FileNameExtensionFilter;
import com.kingdee.eas.util.client.EASResource;

/**
 * 进度图表报表基类
 * <p>
 * 定义了一个左树、右上图表、右下表格的框架<br>
 * 实现套打、保存图片等功能
 * 
 * @author emanon
 * 
 */
public class ScheduleChartBaseUI extends AbstractScheduleChartBaseUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ScheduleChartBaseUI.class);

	/**
	 * output class constructor
	 */
	public ScheduleChartBaseUI() throws Exception {
		super();
	}

	public void loadFields() {
		super.loadFields();
	}

	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initUIState();
	}

	public void onShow() throws Exception {
		super.onShow();
		sptTop.setDividerLocation(getChartHeight());
	}

	/**
	 * 打印
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ScheduleChartProvider dataPvd = new ScheduleChartProvider(getTDName(),
				getUIIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(getTDPath(), dataPvd, SwingUtilities
				.getWindowAncestor(this));
	}

	/**
	 * 打印预览
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		ScheduleChartProvider dataPvd = new ScheduleChartProvider(getTDName(),
				getUIIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview(getTDPath(), dataPvd, SwingUtilities
				.getWindowAncestor(this));
	}

	/**
	 * 导出图片至文件
	 * <p>
	 * 支持导出为jpg或者png<br>
	 * 当输入的文件名未包含后缀时，取选择的后缀<br>
	 * 
	 * 一般不需重写此方法，重写getUIIMG()返回图片即可
	 */
	public void actionExportIMG_actionPerformed(ActionEvent e) throws Exception {
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(KDFileChooser.FILES_ONLY);
		// 单选
		fc.setMultiSelectionEnabled(false);
		// 2种格式
		fc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("jpg",
				new String[] { "jpg" });
		FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("png",
				new String[] { "png" });
		fc.setFileFilter(jpgFilter);
		fc.setFileFilter(pngFilter);
		int retVal = fc.showSaveDialog(this);
		if (retVal == KDFileChooser.CANCEL_OPTION) {
			return;
		}
		File chooseFile = fc.getSelectedFile();
		// 得到图片
		BufferedImage img = getUIIMG();
		if (img == null) {
			return;
		}
		String imgName = chooseFile.getName().trim().toLowerCase();
		String newFile = chooseFile.getParent() + "\\" + imgName;
		// 判断选择了jpg或者png
		if (fc.getFileFilter().equals(jpgFilter)) {
			if (!imgName.endsWith("jpg")) {
				newFile += ".jpg";
			}
			File saveIMG = new File(newFile);
			// 写入jpg
			FileOutputStream fos = new FileOutputStream(saveIMG);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			new SunJPEGEncoderAdapter().encode(img, fos);
			bos.close();
		} else {
			if (!imgName.endsWith("png")) {
				newFile += ".png";
			}
			File saveIMG = new File(newFile);
			// 写入png
			FileOutputStream fos = new FileOutputStream(saveIMG);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			byte[] pngEncode = new PngEncoder(img).pngEncode();
			bos.write(pngEncode);
			bos.close();
		}
	}

	/**
	 * 返回套打标题
	 * <p>
	 * 子类需重载
	 * 
	 * @return
	 */
	protected String getTDName() {
		return "进度图表";
	}

	/**
	 * 返回套打模板路径
	 * <p>
	 * 子类需重载
	 * 
	 * @return
	 */
	protected String getTDPath() {
		return "/bim/fdc/process/rate";
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
	protected BufferedImage getUIIMG() {
		int height2 = sptTop.getHeight();
		int width2 = sptTop.getWidth();
		BufferedImage img = new BufferedImage(width2, height2,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		sptTop.paintComponents(g);
		return img;
	}

	/**
	 * 单独返回图表的图像
	 * <p>
	 * 有时候套打只需要打印图表，而下面的表格使用另外的数据源打印<br>
	 * 此时套打取图片使用此方法，而不用getUIIMG()
	 */
	protected BufferedImage getChartIMG() {
		int height2 = pnlChart.getHeight();
		int width2 = pnlChart.getWidth();
		BufferedImage img = new BufferedImage(width2, height2,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		pnlChart.paintComponents(g);
		return img;
	}

	/**
	 * 树选择改变
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {

	}

	/**
	 * 年份选择改变
	 */
	protected void cbYear_itemStateChanged(java.awt.event.ItemEvent e)
			throws Exception {
	}

	/**
	 * 设置界面状态
	 */
	protected void initUIState() {
		btnPrint.setIcon(EASResource.getIcon("imgTbtn_print"));
		menuItemPrint.setIcon(EASResource.getIcon("imgTbtn_print"));
		btnPrintPreview.setIcon(EASResource.getIcon("imgTbtn_preview"));
		menuItemPrintPre.setIcon(EASResource.getIcon("imgTbtn_preview"));
		btnExportIMG.setIcon(EASResource.getIcon("imgTbtn_chart"));
		menuItemExpIMG.setIcon(EASResource.getIcon("imgTbtn_chart"));

		actionPrint.setEnabled(true);
		actionPrintPreview.setEnabled(true);
		actionExportIMG.setEnabled(true);
	}

	/**
	 * 图表的高度<br>
	 * 一般是尽量高一点，使用当前页面高度减去下表格高度<br>
	 * 在onshow中调用，因为onload时，页面高度还没有确定，只是默认高度
	 * 
	 * @return
	 */
	protected int getChartHeight() {
		return getHeight() - 150;
	}

}