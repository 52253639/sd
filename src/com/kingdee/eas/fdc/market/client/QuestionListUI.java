/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.insider.EncouragementTypeEnum;
import com.kingdee.eas.fdc.market.AskQuestionFactory;
import com.kingdee.eas.fdc.market.FillBlankQuestionFactory;
import com.kingdee.eas.fdc.market.JudgeQuestionFactory;
import com.kingdee.eas.fdc.market.QuestionBaseCollection;
import com.kingdee.eas.fdc.market.QuestionStyle;
import com.kingdee.eas.fdc.market.SelectQuestionFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.KDTableUtil;

/**
 * output class name
 */
public class QuestionListUI extends AbstractQuestionListUI {
	private static final Logger logger = CoreUIObject.getLogger(QuestionListUI.class);

	private String editUIName;
	private ICoreBase bizInterface;

	public QuestionBaseCollection questionCollection = new QuestionBaseCollection(); // 该UI作为选择试题的弹出框时使用

	/**
	 * output class constructor
	 */
	public QuestionListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		// 第一次进入此页面 默认是简答题
		mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.market.app", "AskQuestionQuery");
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("questionStyle", QuestionStyle.ASKQUESTION_VALUE, CompareType.EQUALS));
		this.mainQuery = new EntityViewInfo();
		this.mainQuery.setFilter(filterInfo);
		this.setEditUIName(AskQuestionEditUI.class.getName());
		this.setBizInterface(com.kingdee.eas.fdc.market.AskQuestionFactory.getRemoteInstance());
		getUIContext().put("selectedIndex", "0");// 新增试题时使用
		// 判断是否是作为弹出选择窗口
		String isPrompt = getUIContext().get("isPrompt") == null ? "" : getUIContext().get("isPrompt").toString();
		// 作为弹出选择试题窗口使用
		if (isPrompt != null && isPrompt.equalsIgnoreCase("true")) {
			this.submitButton.setVisible(true);
			// 屏蔽工具栏
			this.toolBar.setVisible(false);
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		// 判断是否是作为弹出选择窗口
		String isPrompt = getUIContext().get("isPrompt") == null ? "" : getUIContext().get("isPrompt").toString();
		// 作为弹出选择试题窗口使用
		if (isPrompt != null && isPrompt.equalsIgnoreCase("true")) {
			return;

		}

		super.tblMain_tableClicked(e);
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * output questionStyle_itemStateChanged method
	 */
	protected void questionStyle_itemStateChanged(java.awt.event.ItemEvent e) throws Exception {
		super.questionStyle_itemStateChanged(e);
		QuestionStyle selectedStyle = (QuestionStyle) questionStyle.getItemAt(questionStyle.getSelectedIndex());
		getUIContext().put("selectedIndex", questionStyle.getSelectedIndex() + "");// 新增试题时使用
		// 简答题
		if (selectedStyle.getValue().equals(QuestionStyle.ASKQUESTION_VALUE)) {
			this.setEditUIName(AskQuestionEditUI.class.getName());
			this.setBizInterface(com.kingdee.eas.fdc.market.AskQuestionFactory.getRemoteInstance());

			mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.market.app", "AskQuestionQuery");

			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("questionStyle", QuestionStyle.ASKQUESTION_VALUE, CompareType.EQUALS));

			this.mainQuery = new EntityViewInfo();
			this.mainQuery.setFilter(filterInfo);
		}
		// 判断题
		else if (selectedStyle.getValue().equals(QuestionStyle.JUDGEQUESTION_VALUE)) {
			this.setEditUIName(JudgeQuestionEditUI.class.getName());
			this.setBizInterface(com.kingdee.eas.fdc.market.JudgeQuestionFactory.getRemoteInstance());
			mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.market.app", "JudgeQuestionQuery");
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("questionStyle", QuestionStyle.JUDGEQUESTION_VALUE, CompareType.EQUALS));
			this.mainQuery = new EntityViewInfo();
			this.mainQuery.setFilter(filterInfo);
		}

		// 填空题
		else if (selectedStyle.getValue().equals(QuestionStyle.FILLBLANKQUESTION_VALUE)) {
			this.setEditUIName(FillBlankQuestionEditUI.class.getName());
			this.setBizInterface(com.kingdee.eas.fdc.market.FillBlankQuestionFactory.getRemoteInstance());
			mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.market.app", "FillBlankQuestionQuery");
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("questionStyle", QuestionStyle.FILLBLANKQUESTION_VALUE, CompareType.EQUALS));
			this.mainQuery = new EntityViewInfo();
			this.mainQuery.setFilter(filterInfo);
		}

		// 单选题
		else if (selectedStyle.getValue().equals(QuestionStyle.SINGLESELECTQUESTION_VALUE)) {
			getUIContext().put("questionStyle", QuestionStyle.SINGlESELECTQUESTION);
			this.setEditUIName(SelectQuestionEditUI.class.getName());
			this.setBizInterface(com.kingdee.eas.fdc.market.SelectQuestionFactory.getRemoteInstance());
			mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.market.app", "SelectQuestionQuery");
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("questionStyle", QuestionStyle.SINGLESELECTQUESTION_VALUE, CompareType.EQUALS));
			this.mainQuery = new EntityViewInfo();
			this.mainQuery.setFilter(filterInfo);
		}

		// 多选题
		else if (selectedStyle.getValue().equals(QuestionStyle.MUTISELECTQUESTION_VALUE)) {
			getUIContext().put("questionStyle", QuestionStyle.MUTISELECTQUESTION);
			this.setEditUIName(SelectQuestionEditUI.class.getName());
			this.setBizInterface(com.kingdee.eas.fdc.market.SelectQuestionFactory.getRemoteInstance());
			mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.market.app", "SelectQuestionQuery");
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("questionStyle", QuestionStyle.MUTISELECTQUESTION_VALUE, CompareType.EQUALS));
			this.mainQuery = new EntityViewInfo();
			this.mainQuery.setFilter(filterInfo);
		}
		this.tblMain.removeRows();
		tblMain.requestFocusInWindow();

	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionView_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}

	/**
	 * output actionImportData_actionPerformed
	 */
	public void actionImportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionImportData_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionExportData_actionPerformed
	 */
	public void actionExportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportData_actionPerformed(e);
	}

	/**
	 * output actionToExcel_actionPerformed
	 */
	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		super.actionToExcel_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionPublishReport_actionPerformed
	 */
	public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception {
		super.actionPublishReport_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancelCancel_actionPerformed(e);
	}

	/**
	 * output actionQueryScheme_actionPerformed
	 */
	public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception {
		super.actionQueryScheme_actionPerformed(e);
	}

	protected String getEditUIName() {
		// TODO 自动生成方法存根
		return editUIName;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return bizInterface;
	}

	protected void setBizInterface(ICoreBase bizInterface) {
		this.bizInterface = bizInterface;
	}

	protected void setEditUIName(String editUIName) {
		this.editUIName = editUIName;
	}

	/**
	 * output submitButton_mouseClicked method
	 */
	protected void submitButton_mouseClicked(java.awt.event.MouseEvent e) throws Exception {
		int[] rowID = KDTableUtil.getSelectedRows(this.tblMain);
		Set rowSet = new HashSet();

		for (int i = 0; rowID != null && i < rowID.length; i++) {

			String strID = tblMain.getRow(rowID[i]).getCell("id").getValue().toString();

			rowSet.add(strID);
		}

		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo fi = new FilterInfo();
		fi.getFilterItems().add(new FilterItemInfo("id", rowSet, CompareType.INCLUDE));
		viewInfo.setFilter(fi);

		// 查询选择的集合
		QuestionStyle selectedStyle = (QuestionStyle) questionStyle.getItemAt(questionStyle.getSelectedIndex());

		// 简答题
		if (selectedStyle.getValue().equals(QuestionStyle.ASKQUESTION_VALUE)) {
			questionCollection = AskQuestionFactory.getRemoteInstance().getQuestionBaseCollection(viewInfo);
		}
		// 判断题
		else if (selectedStyle.getValue().equals(QuestionStyle.JUDGEQUESTION_VALUE)) {
			questionCollection = JudgeQuestionFactory.getRemoteInstance().getQuestionBaseCollection(viewInfo);
		}

		// 填空题
		else if (selectedStyle.getValue().equals(QuestionStyle.FILLBLANKQUESTION_VALUE)) {
			questionCollection = FillBlankQuestionFactory.getRemoteInstance().getQuestionBaseCollection(viewInfo);
		}

		// 单选题
		else if (selectedStyle.getValue().equals(QuestionStyle.SINGLESELECTQUESTION_VALUE)) {
			questionCollection = SelectQuestionFactory.getRemoteInstance().getQuestionBaseCollection(viewInfo);

		}

		// 多选题
		else if (selectedStyle.getValue().equals(QuestionStyle.MUTISELECTQUESTION_VALUE)) {
			questionCollection = SelectQuestionFactory.getRemoteInstance().getQuestionBaseCollection(viewInfo);
		}

		this.getUIWindow().close();
	}

}