/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;

import java.util.HashSet;
import java.util.Iterator;

import java.util.TreeSet;


import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IBlock;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;

import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;

import com.kingdee.eas.fdc.market.CycleEnum;
import com.kingdee.eas.fdc.market.MarketTypeFactory;
import com.kingdee.eas.fdc.market.MarketTypeInfo;
import com.kingdee.eas.fdc.market.MovementPlanE5Collection;
import com.kingdee.eas.fdc.market.MovementPlanE5Factory;
import com.kingdee.eas.fdc.market.MovementPlanE5Info;

import com.kingdee.eas.fdc.market.MovementPlanInfo;
import com.kingdee.eas.fdc.market.PlanEntryCollection;
import com.kingdee.eas.fdc.market.PlanEntryInfo;
import com.kingdee.eas.fdc.market.PlanInfo;
import com.kingdee.eas.fdc.market.SchemeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;

import com.kingdee.eas.framework.client.multiDetail.DetailPanel;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class PlanEditUI extends AbstractPlanEditUI {

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel = super.getSelectors();
		sel.add("*");
		sel.add("sellProject.number");
		sel.add("sellProject.name");
		sel.add("sellProject.id");
		sel.add("entrys.*");
		sel.add("entrys.MovementPlan.*");
		sel.add("entrys.MovementPlan.media.*");
		sel.add("entrys.MovementPlan.mmType.*");
		sel.add("entrys.MovementPlan.sellProject.*");
		sel.add("entrys.E3.*");
		return sel;
	}

	public boolean checkBeforeWindowClosing() {
		return true;
	}



	private SellProjectInfo projectInfo = null;
	private CycleEnum cycleType = null;





	protected KDPanel controlPanel;

	DetailPanel detailPanel;

	/**
	 * output class constructor
	 */
	public PlanEditUI() throws Exception {
		super();
	}

	protected void verifyInput(ActionEvent e) throws Exception {

	}

	/**
	 * output initUIContentLayout method
	 */
	public void initUIContentLayout() {
		super.initUIContentLayout();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initToolBar();
		// setAddColum();
		this.kDBelongSysComboBox.setRequired(true);
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		SHEHelper.setTextFormat(this.txtPlanMoney);
		this.kDTextArea1.setEnabled(true);
		this.kDTextArea1.setVisible(true);
		this.actionWorkFlowG.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		
	}

	private void initToolBar() throws Exception {
		// 设置按钮图标
		this.btnAddLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		// 设置按钮为可用
		this.btnAddInsider.setEnabled(true);
		this.btnDelInsider.setEnabled(true);
		// 设置按钮图标
		this.btnDelInsider.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		this.btnDelInsider.setToolTipText("删除方案");
		this.btnDelInsider.setText("删除方案");
		this.btnDelInsider.setSize(82, 19);
		this.btnAddInsider.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.btnAddInsider.setToolTipText("新增方案");
		this.btnAddInsider.setText("新增方案");
		this.btnAddInsider.setSize(82, 19);
		kdtEntrys.getStyleAttributes().setLocked(true);

		// kdtE3_detailPanel.setTitle("计划效果");
		// kdtE3_detailPanel.getAddNewLineButton().setVisible(false);
		// kdtE3_detailPanel.getInsertLineButton().setVisible(false);
		// kdtE3_detailPanel.getRemoveLinesButton().setVisible(false);
		kDSplitPane1.setDividerLocation(280);
		kdtEntrys.setBounds(new Rectangle(0, 30, 1000, 220));

		this.kdtEntrys.getColumn("predictCost").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.kdtEntrys.getColumn("predictCost").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		this.kdtE3.getColumn("colValue").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(0));
		this.kdtE3.getColumn("colValue").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtE3.getColumn("remark").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(0));
		this.kdtE3.getColumn("remark").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

	}

	/**
	 * 描述： 增加活动方案按钮事件
	 * 
	 * @author:hjx
	 * @see com.kingdee.eas.fdc.insider.client.AbstractIntegralManageEditUI#actionAddInsider_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionAddMovement_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddMovement_actionPerformed(e);
		HashSet idSet = new HashSet();
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			IRow row = this.kdtEntrys.getRow(i);
			if (row != null && row.getCell("number") != null && row.getCell("number").getValue() != null) {
				idSet.add(row.getCell("number").getValue());
			}
		}
		KDCommonPromptDialog dlg = new KDCommonPromptDialog();
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.fdc.market.app.f7MovementPlanQuery")));
		EntityViewInfo v = new EntityViewInfo();
		FilterInfo f = new FilterInfo();
		f.getFilterItems().add(new FilterItemInfo("schemeType", new Integer(SchemeTypeEnum.PLANSCHEME_VALUE)));
		SellProjectInfo sellproject = (SellProjectInfo) this.kDMarketProjectPromptBox.getValue();
		if (sellproject != null) {
			f.getFilterItems().add(new FilterItemInfo("sellProject.id", sellproject.getId().toString()));
		}
		if (idSet.size() > 0) {
			f.getFilterItems().add(new FilterItemInfo("number", idSet, CompareType.NOTINCLUDE));
		}
		v.setFilter(f);
		dlg.setEntityViewInfo(v);
		dlg.setEnabledMultiSelection(true);
		dlg.show();
		Object[] object = (Object[]) dlg.getData();
		if (object != null && object.length > 0) {
			IRow row = null;
			for (int j = 0; j < object.length; j++) {
				MovementPlanInfo movementPlanInfo = (MovementPlanInfo) object[j];
				this.kdtEntrys.checkParsed();
				row = this.kdtEntrys.addRow(j);
				showTblPlan(row, movementPlanInfo);
			}
		}
		txtPlanMoneySetSum();

	}

	/**
	 * 
	 * 描述：<方法描述>给分录赋值
	 * 
	 * @param <参数描述>
	 * @return <返回值描述>
	 * 
	 * @author:hjx <p>
	 * @throws BOSException
	 * @throws EASBizException
	 * 
	 * @see <相关的类>
	 */
	private void showTblPlan(IRow row, MovementPlanInfo movementPlanInfo) throws EASBizException, BOSException {
		// 这里设置活动类型属性
		ObjectUuidPK pkMarket = new ObjectUuidPK(movementPlanInfo.getMmType().getId());
		if (movementPlanInfo.getMmType() != null) {
			MarketTypeInfo marketinfo;
			marketinfo = MarketTypeFactory.getRemoteInstance().getMarketTypeInfo(pkMarket);
			row.getCell("mmType").setValue(marketinfo.getName());
		}
		ObjectUuidPK pkProject = new ObjectUuidPK(movementPlanInfo.getSellProject().getId());
		if (movementPlanInfo.getSellProject() != null) {
			SellProjectInfo projectinfo;
			projectinfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo(pkProject);
			row.getCell("sellProject").setValue(projectinfo.getName());
		}
		row.getCell("id").setValue(movementPlanInfo);
		row.getCell("number").setValue(movementPlanInfo.getNumber());
		row.getCell("number").setUserObject(movementPlanInfo);
		row.getCell("name").setValue(movementPlanInfo.getName());
		row.getCell("startTime").setValue(movementPlanInfo.getBeginDate());
		row.getCell("endTime").setValue(movementPlanInfo.getEndDate());
		row.getCell("predictCost").setValue(movementPlanInfo.getTotalMoney());
	}

	/**
	 * 描述： 删除行
	 * 
	 * @author:Administrator
	 * @see com.kingdee.eas.fdc.insider.client.AbstractTestInsiderF7EditUI#actionDelPerson_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionDelMovement_actionPerformed(ActionEvent e) throws Exception {
		super.actionDelMovement_actionPerformed(e);

		if (kdtEntrys.getRowCount() <= 0)
			return;
		int[] selectRows = getSelectedRows(kdtEntrys);
		kdtEntrys.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		for (int i = selectRows.length - 1; i >= 0; i--) {
			kdtEntrys.removeRow(selectRows[i]);
		}

		if (this.kdtEntrys.getRowCount() >= 0) {
			this.kdtEntrys.getSelectManager().select(0, 0);
		}
		if (this.kdtE3.getRowCount() > 0) {
			this.kdtE3.removeRows();
			this.kdtE3.checkParsed();
		}
	}

	public static int[] getSelectedRows(KDTable table) {
		if (table.getSelectManager().size() == 0) {
			return new int[0];
		} else {
			TreeSet set = new TreeSet();
			for (int i = 0; i < table.getSelectManager().size(); i++) {
				// KDTSelectBlock block = table.getSelectManager().get(i);
				IBlock block = KDTBlock.change(table, table.getSelectManager().get(i));
				for (int j = block.getTop(); j <= block.getBottom(); j++) {
					set.add(new Integer(j));
				}
			}

			int[] rows = new int[set.size()];
			Iterator iter = set.iterator();
			int k = 0;
			while (iter.hasNext()) {
				rows[k++] = ((Integer) iter.next()).intValue();
			}

			return rows;
		}
	}

	/**
	 * output loadFields method
	 */
	public void loadFields() {
		super.loadFields();
		SHEHelper.setNumberEnabled(editData, this.getOprtState(), this.txtNumber);// 配置启用编码规则

		setTableToSumField(kdtEntrys, new String[] { "predictCost" });
		if (txtauditingState.getSelectedIndex() > 0) {
			FDCBillStateEnum selected = (FDCBillStateEnum) txtauditingState.getItemAt(txtauditingState.getSelectedIndex());
			if (selected.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)) {
				this.actionEdit.setEnabled(false);
				this.actionAddNew.setEnabled(false);
				this.actionSave.setEnabled(false);
				this.actionSubmit.setEnabled(false);
				this.actionRemove.setEnabled(false);
			}
		}
		if (editData != null && editData.getSellProject() != null) {
			if (editData.getCycleType().getValue() == "1") {
				this.kDPlanMonthComboBox.setEnabled(false);
			}
			BOSUuid projId = null;
			SellProjectInfo sellInfo = null;
			try {
				if (STATUS_ADDNEW.equals(getOprtState())) {
					sellInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(projectInfo.getId()));
				} else {
					sellInfo = (SellProjectInfo) kDMarketProjectPromptBox.getData();
				}
				projId = sellInfo.getOrgUnit().getId();
				FullOrgUnitInfo costOrg = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(projId));
				this.txtOrgName.setText(costOrg.getDisplayName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		PlanEntryCollection peCol = this.editData.getEntrys();
		this.kdtEntrys.removeRows();
		this.kdtEntrys.checkParsed();
		for (int i = 0; i < peCol.size(); i++) {
			PlanEntryInfo intryfo = peCol.get(i);
			if (intryfo != null) {
				IRow row = this.kdtEntrys.addRow();
				row.getCell("id").setValue(intryfo.getId().toString());
				row.setUserObject(intryfo);
				MovementPlanInfo mpInfo = intryfo.getMovementPlan();
				if (mpInfo != null) {
					row.getCell("number").setValue(mpInfo.getNumber());
					row.getCell("number").setUserObject(mpInfo);
					row.getCell("name").setValue(mpInfo.getName());
					row.getCell("startTime").setValue(mpInfo.getBeginDate());
					row.getCell("endTime").setValue(mpInfo.getEndDate());
					row.getCell("predictCost").setValue(mpInfo.getTotalMoney());
					// MediaInfo media = mpInfo.getMedia();
					// if (media != null) {
					// row.getCell("media").setValue(media.getName());
					// }
					MarketTypeInfo marketinfo = mpInfo.getMmType();
					if (marketinfo != null) {
						row.getCell("mmType").setValue(marketinfo.getName());
					}
					SellProjectInfo project = mpInfo.getSellProject();
					if (project != null) {
						row.getCell("sellProject").setValue(project.getName());
					}
				}
			}
		}

	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		PlanInfo info = this.editData;
		if (info != null) {
			info.setOrgUnit(SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo());
			PlanEntryCollection entryCol = new PlanEntryCollection();
			for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
				IRow row = this.kdtEntrys.getRow(i);
				PlanEntryInfo entryInfo = null;
				if (row.getUserObject() instanceof PlanEntryInfo) {
					entryInfo = (PlanEntryInfo) row.getUserObject();
				} else {
					entryInfo = new PlanEntryInfo();
					if (row.getCell("number").getUserObject() instanceof MovementPlanInfo) {
						MovementPlanInfo movePlanInfo = (MovementPlanInfo) row.getCell("number").getUserObject();
						if (movePlanInfo != null) {
							entryInfo.setMovementPlan(movePlanInfo);
						}
					}
				}
				entryInfo.setParent(info);
				entryCol.add(entryInfo);
			}
			info.getEntrys().clear();
			info.getEntrys().addCollection(entryCol);
		}
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
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (kDBelongSysComboBox.getSelectedItem() == null) {
			MsgBox.showInfo("所属系统不能为空！");
			return;
		}
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable() && StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("计划编号不能为空！");
			this.abort();
		}
		String name = this.txtName.getText().trim();
		if (name == null || name == "" || "".equals(name) || " ".equals(name)) {
			MsgBox.showInfo("计划名称不能为空！");
			return;
		}
		// txtPlanMoneySetSum();
		super.actionSave_actionPerformed(e);
	}

	private void txtPlanMoneySetSum() {
		BigDecimal totalMoney = new BigDecimal(0.00);
		for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
			IRow row = this.kdtEntrys.getRow(i);
			BigDecimal predictCost = FDCHelper.ZERO;
			if (row.getCell("predictCost") != null && row.getCell("predictCost").getValue() != null) {
				predictCost = FDCHelper.toBigDecimal(row.getCell("predictCost").getValue());
				totalMoney = totalMoney.add(predictCost);
			}
		}
		txtPlanMoney.setValue(totalMoney);
		SHEHelper.setTextFormat(this.txtPlanMoney);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {

		if (kDBelongSysComboBox.getSelectedItem() == null) {
			MsgBox.showInfo("所属系统不能为空！");
			return;
		}
		if (this.txtNumber.isEnabled() && this.txtNumber.isEditable() && StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("计划编号不能为空！");
			this.abort();
		}
		String name = this.txtName.getText().trim();
		if (name == null || name == "" || "".equals(name) || " ".equals(name)) {
			MsgBox.showInfo("计划名称不能为空！");
			return;
		}
		// txtPlanMoneySetSum();
		super.actionSubmit_actionPerformed(e);
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
	 * output actionFirst_actionPerformed
	 */
	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		super.actionFirst_actionPerformed(e);
	}

	/**
	 * output actionPre_actionPerformed
	 */
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		super.actionPre_actionPerformed(e);
	}

	/**
	 * output actionNext_actionPerformed
	 */
	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		super.actionNext_actionPerformed(e);
	}

	/**
	 * output actionLast_actionPerformed
	 */
	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		super.actionLast_actionPerformed(e);
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
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionSubmitOption_actionPerformed
	 */
	public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmitOption_actionPerformed(e);
	}

	/**
	 * output actionReset_actionPerformed
	 */
	public void actionReset_actionPerformed(ActionEvent e) throws Exception {
		super.actionReset_actionPerformed(e);
	}

	/**
	 * output actionAddLine_actionPerformed
	 */
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
	}

	/**
	 * output actionInsertLine_actionPerformed
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionInsertLine_actionPerformed(e);
	}

	/**
	 * output actionRemoveLine_actionPerformed
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemoveLine_actionPerformed(e);
	}

	/**
	 * output actionCreateFrom_actionPerformed
	 */
	public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception {
		super.actionCreateFrom_actionPerformed(e);
	}

	/**
	 * output actionCopyFrom_actionPerformed
	 */
	public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopyFrom_actionPerformed(e);
	}

	/**
	 * output actionAuditResult_actionPerformed
	 */
	public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception {
		super.actionAuditResult_actionPerformed(e);
	}

	/**
	 * output actionTraceUp_actionPerformed
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceUp_actionPerformed(e);
	}

	/**
	 * output actionTraceDown_actionPerformed
	 */
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		super.actionTraceDown_actionPerformed(e);
	}

	/**
	 * output actionViewSubmitProccess_actionPerformed
	 */
	public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception {
		super.actionViewSubmitProccess_actionPerformed(e);
	}

	/**
	 * output actionViewDoProccess_actionPerformed
	 */
	public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception {
		super.actionViewDoProccess_actionPerformed(e);
	}

	/**
	 * output actionMultiapprove_actionPerformed
	 */
	public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception {
		super.actionMultiapprove_actionPerformed(e);
	}

	/**
	 * output actionNextPerson_actionPerformed
	 */
	public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception {
		super.actionNextPerson_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionVoucher_actionPerformed
	 */
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		super.actionVoucher_actionPerformed(e);
	}

	/**
	 * output actionDelVoucher_actionPerformed
	 */
	public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception {
		super.actionDelVoucher_actionPerformed(e);
	}

	/**
	 * output actionWorkFlowG_actionPerformed
	 */
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		super.actionWorkFlowG_actionPerformed(e);
	}

	/**
	 * output actionCreateTo_actionPerformed
	 */
	public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCreateTo_actionPerformed(e);
	}

	/**
	 * output actionSendingMessage_actionPerformed
	 */
	public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendingMessage_actionPerformed(e);
	}

	/**
	 * output actionSignature_actionPerformed
	 */
	public void actionSignature_actionPerformed(ActionEvent e) throws Exception {
		super.actionSignature_actionPerformed(e);
	}

	/**
	 * output actionWorkflowList_actionPerformed
	 */
	public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception {
		super.actionWorkflowList_actionPerformed(e);
	}

	/**
	 * output actionViewSignature_actionPerformed
	 */
	public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception {
		super.actionViewSignature_actionPerformed(e);
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.market.PlanFactory.getRemoteInstance();
	}

	/**
	 * output getDetailTable method
	 */
	protected KDTable getDetailTable() {
		return this.kdtEntrys;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		return new com.kingdee.eas.fdc.market.PlanEntryInfo();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.PlanInfo objectValue = new com.kingdee.eas.fdc.market.PlanInfo();
		objectValue.setOrgUnit(SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo());
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setCreateTime(new Timestamp(System.currentTimeMillis()));
		objectValue.setBizDate(SHEHelper.getServerDate2());
		projectInfo = (SellProjectInfo) getUIContext().get("project");
		cycleType = (CycleEnum) getUIContext().get("cycleType");
		if (projectInfo != null) {
			objectValue.setSellProject(projectInfo);
		}
		if (cycleType != null) {
			objectValue.setCycleType(cycleType);
		}
		// 计划日期,默认当前日期,必录,可以修改.
		objectValue.setPlanYear(FDCDateHelper.formatDate(SHEHelper.getServerDate2()).substring(0, 4));
		//this.txtPlanMonth.setText(String.valueOf(SCMClientUtils.getServerDate2
		// ()).substring(0,4));
		// this.kDPlanMonthComboBox
		return objectValue;
	}

	public void kdtEntrys_Changed(int rowIndex, int colIndex) throws Exception {

	}

	/**
	 * output kdtEntrys_tableClicked method
	 */
	protected void kdtEntrys_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		if (this.kdtEntrys.getRowCount() < 1) {
			return;
		}
		int rowIndex = e.getRowIndex();
		if (rowIndex < 0) {
			return;
		}
		IRow row = kdtEntrys.getRow(rowIndex);
		if (row.getCell("number").getUserObject() instanceof MovementPlanInfo) {
			MovementPlanInfo movementPlanInfo = (MovementPlanInfo) row.getCell("number").getUserObject();
			if (movementPlanInfo != null) {
				this.kdtE3.removeRows();
				this.kdtE3.checkParsed();
				EntityViewInfo view = new EntityViewInfo();
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add("*");
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("parent.id", movementPlanInfo.getId().toString()));
				view.setSelector(sel);
				view.setFilter(filter);
				view.getSorter().add(new SorterItemInfo("seq"));
				MovementPlanE5Collection coll = MovementPlanE5Factory.getRemoteInstance().getMovementPlanE5Collection(view);
				for (int i = 0; i < coll.size(); i++) {
					MovementPlanE5Info e5Info = coll.get(i);
					IRow irow = kdtE3.addRow();
					irow.getCell("planName").setValue(e5Info.getPredictName());
					irow.getCell("colValue").setValue(String.valueOf(e5Info.getPredictValue()));
					irow.getCell("remark").setValue(FDCHelper.ZERO);
				}
			}
		}
	}
    protected OrgType getMainBizOrgType()
    {
        return OrgType.Sale;
    }   	
}