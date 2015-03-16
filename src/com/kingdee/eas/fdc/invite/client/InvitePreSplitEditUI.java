/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.FullDynamicCostMap;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.CostSplitAcctUI;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.InvitePreSplitCollection;
import com.kingdee.eas.fdc.invite.InvitePreSplitCostAmountFacadeFactory;
import com.kingdee.eas.fdc.invite.InvitePreSplitEntryCollection;
import com.kingdee.eas.fdc.invite.InvitePreSplitEntryInfo;
import com.kingdee.eas.fdc.invite.InvitePreSplitFactory;
import com.kingdee.eas.fdc.invite.InvitePreSplitInfo;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InviteProjectStateEnum;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class InvitePreSplitEditUI extends AbstractInvitePreSplitEditUI
{
	private static final Logger logger = CoreUIObject.getLogger(InvitePreSplitEditUI.class);

	private final String COL_CURPROJECT = "curProject";
	private final String COL_COSTACCOUNT = "costAccount";
	private final String COL_PRESPLITAMOUNT = "preSplitAmount";

	private final String COL_AIMCOST = "aimCost";
	private final String COL_OCCUREDCOST = "occuredCost";
	private final String COL_SPLITEDCOST = "splitedCost";

	private final String COL_DYNAMICCOST = "dynamicCost";
	private final String COL_SUB_AMOUNT = "substractAmount";
	private final String COL_ID = "id";

	private final String COL_COSTACCOUNT_NAME = "costAccountName";
	private IUIWindow acctUI=null;

	//动态成本
	private Map dyCostMap = new HashMap();
	//目标成本
	private Map aimCostMap = new HashMap();

	private FullOrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo();
	
	/**
	 * output class constructor
	 */
	public InvitePreSplitEditUI() throws Exception
	{
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		
		super.storeFields();
		
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception
	{
		
		super.actionSave_actionPerformed(e);
        
		reloadCostAccountName();
	}

	private void reloadCostAccountName()
	{
		for(int i = 0; i < kdtEntry.getRowCount(); ++i)
		{
			IRow row = kdtEntry.getRow(i);

			if(row.getCell(this.COL_COSTACCOUNT).getValue() != null)
			{
				CostAccountInfo tmpAcctInfo = (CostAccountInfo)(row.getCell(this.COL_COSTACCOUNT).getValue());
				if(tmpAcctInfo != null)
				{
					row.getCell(this.COL_COSTACCOUNT_NAME).setValue(tmpAcctInfo.getName());
				}
			}
		}
	}
	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionSubmit_actionPerformed(e);
		
		if(OprtState.ADDNEW.equals(getOprtState()))
		{
			this.prmtCurProject.setValue(null);
			if(kdtEntry.getFootRow(0)!= null)
			{
				kdtEntry.getFootRow(0).getCell(this.COL_PRESPLITAMOUNT).setValue(BigDecimal.ZERO);
			}
		}
		else
		{
			reloadCostAccountName();
		}
		
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionEdit_actionPerformed(e);
		this.actionImportCostAccount.setEnabled(true);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionRemoveLine_actionPerformed
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionRemoveLine_actionPerformed(e);

		BigDecimal sum = new BigDecimal("0");
		for(int i = 0; i < kdtEntry.getRowCount(); ++i)
		{
			IRow tmpRow = kdtEntry.getRow(i);
			if(tmpRow.getCell(this.COL_PRESPLITAMOUNT).getValue() != null)
			{
				BigDecimal tmp = new BigDecimal(tmpRow.getCell(this.COL_PRESPLITAMOUNT).getValue().toString());
				sum = sum.add(tmp);
			}
		}

		if(kdtEntry.getFootRow(0)!= null)
		{
			kdtEntry.getFootRow(0).getCell(this.COL_PRESPLITAMOUNT).setValue(sum);
		}
	}

	/**
	 * output actionAudit_actionPerformed
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionAudit_actionPerformed(e);
	}

	/**
	 * output actionUnAudit_actionPerformed
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionUnAudit_actionPerformed(e);
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected ICoreBase getBizInterface() throws Exception {
		return InvitePreSplitFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return this.kdtEntry;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected IObjectValue createNewData() {
		InvitePreSplitInfo info = new InvitePreSplitInfo();
		info.setState(FDCBillStateEnum.SAVED);
		info.setOrgUnit(orgUnitInfo);
		
		if(getUIContext().get("inviteProject") != null)
		{
			InviteProjectInfo projectInfo = (InviteProjectInfo)getUIContext().get("inviteProject");
			info.setInviteProject(projectInfo);
		}

		return info;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		InvitePreSplitEntryInfo entryInfo = new InvitePreSplitEntryInfo();
		return entryInfo;
	}

	public void onLoad() throws Exception {
		super.onLoad();

		this.kdtEntry.checkParsed();

		initHeadStyle();

		initTableStyle();

		this.actionAuditResult.setEnabled(false);
		this.actionAuditResult.setVisible(false);
/*
		if(editData != null && editData.getInviteProject() != null)
		{
			prmtCurProject.setValue(editData.getInviteProject().getProject());
		}*/
		reloadCostAccountName();
		if(getOprtState().equals(OprtState.VIEW))
		{
			if(!checkCanOperate())
			{
				this.actionAddNew.setEnabled(false);
				this.actionEdit.setEnabled(false);
				this.actionSubmit.setEnabled(false);
				this.actionRemove.setEnabled(false);

				this.actionImportCostAccount.setEnabled(false);
				this.actionRemoveLine.setEnabled(false);
			}
			else
			{
				if(editData.getState().equals(FDCBillStateEnum.SUBMITTED))
				{
					this.actionAddNew.setEnabled(true);
					this.actionEdit.setEnabled(true);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(true);
				}
				else if(editData.getState().equals(FDCBillStateEnum.AUDITTED))
				{
					this.actionAddNew.setEnabled(false);
					this.actionEdit.setEnabled(false);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(false);
				}
				else
				{
					this.actionAddNew.setEnabled(true);
					this.actionEdit.setEnabled(true);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(false);
				}
				
				this.actionRemoveLine.setEnabled(false);
				this.actionImportCostAccount.setEnabled(false);
			}
		}
		
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		
		this.actionCopyFrom.setVisible(false);
		this.menuWorkflow.setVisible(false);
		this.actionSubmitOption.setVisible(false);
		
		this.menuBiz.setVisible(false);
		this.menuView.setVisible(false);
		this.actionInviteExecuteInfo.setEnabled(true);
	}

	protected void initHeadStyle()
	{
		this.txtNumber.setRequired(true);

		this.btnImportCostAccount.setIcon(EASResource.getIcon("imgTbtn_evaluatecortrol"));

		this.prmtInviteProject.setDisplayFormat("$name$");
		this.prmtInviteProject.setEditFormat("$name$");
		this.prmtInviteProject.setCommitFormat("$name$");

		this.prmtInviteProject.setRequired(true);
		this.prmtInviteProject.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteProjectQuery");

		prmtInviteProject.addSelectorListener(new SelectorListener()
		{
			public void willShow(SelectorEvent e) 
			{
				try {
					setInviteProjectFilter(e);
				} catch (BOSException e1) {
					e1.printStackTrace();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		});

		prmtInviteProject.addDataChangeListener(new DataChangeListener()
		{
			public void dataChanged(DataChangeEvent eventObj) 
			{
				try {
					setInvProjectRelationValues(eventObj);
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		});

		this.prmtCurProject.setDisplayFormat("$name$");
		this.prmtCurProject.setEditFormat("$name$");
		this.prmtCurProject.setCommitFormat("$name$");

		this.contCurProject.setEnabled(false);
		this.prmtCurProject.setEnabled(false);
		this.prmtCurProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");

		this.txtPreSplitAmount.setRequired(true);
		txtPreSplitAmount.setPrecision(2);
		txtPreSplitAmount.setNegatived(false);
		txtPreSplitAmount.setSupportedEmpty(false);

		this.kDContainer1.setEnableActive(false);
		this.kDContainer1.setTitle("预拆分分录");

		this.chkIsAssContract.setEnabled(false);
		this.chkIsContractSplit.setEnabled(false);

		
		//add by david_yang PT043562 2011.04.02 (扩充name到255个字符)
		this.txtNumber.setMaxLength(255);
	}
	private class ProjectRender implements IDataFormat
	{
		public String format(Object o)
		{
			if(o == null)
				return null;
			if(o instanceof CurProjectInfo)
			{
				return ((CurProjectInfo)o).getLongNumber().replace('!', '.');
			}
			else if(o instanceof Map && ((Map)o).containsKey("longNumber")){
				return String.valueOf(((Map)o).get("longNumber")).replace('!', '.');
			}
			else
				return String.valueOf(o);
		}
	}

	private class CostAccountRender implements IDataFormat
	{
		public String format(Object o)
		{
			if(o == null)
				return null;
			if(o instanceof CostAccountInfo)
			{
				String longNumber = ((CostAccountInfo)o).getLongNumber();
				String codingNumber = longNumber.replace('!', '.');
				return codingNumber;
			}
			else if(o instanceof Map && ((Map)o).containsKey("longNumber")){

				String longNumber = String.valueOf(((Map)o).get("longNumber"));
				String codingNumber = longNumber.replace('!', '.');
				return codingNumber;
			}
			else
				return String.valueOf(o);
		}
	}
	protected void initTableStyle()
	{

		this.actionAddLine.setEnabled(false);
		this.actionAddLine.setVisible(false);

		this.actionInsertLine.setEnabled(false);
		this.actionInsertLine.setVisible(false);

		remove(btnRemoveLine);

		this.toolBar.add(btnRemoveLine);

		kdtEntry.getColumn(this.COL_ID).getStyleAttributes().setHided(true);

		IColumn curProjectCol = kdtEntry.getColumn(this.COL_CURPROJECT);
		curProjectCol.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		curProjectCol.getStyleAttributes().setLocked(true);

		curProjectCol.getStyleAttributes().setBackground(FDCTableHelper.lockColor);

		ObjectValueRender projectRender = new ObjectValueRender();
		projectRender.setFormat(new ProjectRender());
		curProjectCol.setRenderer(projectRender);

		IColumn costAccountCol = kdtEntry.getColumn(this.COL_COSTACCOUNT);
		costAccountCol.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		costAccountCol.getStyleAttributes().setLocked(true);
		costAccountCol.getStyleAttributes().setBackground(FDCTableHelper.lockColor);

		ObjectValueRender accountRender = new ObjectValueRender();
		accountRender.setFormat(new CostAccountRender());
		costAccountCol.setRenderer(accountRender);
		
		//设置权重列为正数且两位有效数字
		KDFormattedTextField preSplitAmountField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		preSplitAmountField.setPrecision(2);
		preSplitAmountField.setNegatived(false);
		preSplitAmountField.setSupportedEmpty(false);
		
		KDTDefaultCellEditor editorPreSplitAmount = new KDTDefaultCellEditor(preSplitAmountField);
		this.kdtEntry.getColumn(this.COL_PRESPLITAMOUNT).setEditor(editorPreSplitAmount);

		FDCHelper.formatTableNumber(kdtEntry, this.COL_PRESPLITAMOUNT);
		this.kdtEntry.getColumn(this.COL_PRESPLITAMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		FDCHelper.formatTableNumber(kdtEntry, this.COL_AIMCOST);
		this.kdtEntry.getColumn(this.COL_AIMCOST).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn(this.COL_AIMCOST).getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn(this.COL_AIMCOST).getStyleAttributes().setBackground(FDCTableHelper.lockColor);

		FDCHelper.formatTableNumber(kdtEntry, this.COL_OCCUREDCOST);
		this.kdtEntry.getColumn(this.COL_OCCUREDCOST).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn(this.COL_OCCUREDCOST).getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn(this.COL_OCCUREDCOST).getStyleAttributes().setBackground(FDCTableHelper.lockColor);

		FDCHelper.formatTableNumber(kdtEntry, this.COL_SPLITEDCOST);
		this.kdtEntry.getColumn(this.COL_SPLITEDCOST).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn(this.COL_SPLITEDCOST).getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn(this.COL_SPLITEDCOST).getStyleAttributes().setBackground(FDCTableHelper.lockColor);

		FDCHelper.formatTableNumber(kdtEntry, this.COL_DYNAMICCOST);
		this.kdtEntry.getColumn(this.COL_DYNAMICCOST).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn(this.COL_DYNAMICCOST).getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn(this.COL_DYNAMICCOST).getStyleAttributes().setBackground(FDCTableHelper.lockColor);

		FDCHelper.formatTableNumber(kdtEntry, this.COL_SUB_AMOUNT);
		this.kdtEntry.getColumn(this.COL_SUB_AMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn(this.COL_SUB_AMOUNT).getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn(this.COL_SUB_AMOUNT).getStyleAttributes().setBackground(FDCTableHelper.lockColor);

		this.kdtEntry.getColumn(this.COL_COSTACCOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		this.kdtEntry.getColumn(this.COL_COSTACCOUNT).getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn(this.COL_SUB_AMOUNT).getStyleAttributes().setBackground(FDCTableHelper.lockColor);

		this.kdtEntry.getColumn(this.COL_COSTACCOUNT_NAME).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		this.kdtEntry.getColumn(this.COL_COSTACCOUNT_NAME).getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn(this.COL_COSTACCOUNT_NAME).getStyleAttributes().setBackground(FDCTableHelper.lockColor);

		setFootEntryRow();
	}

	protected void initWorkButton() {
		super.initWorkButton();

		this.actionCopy.setEnabled(false);
		this.actionCopy.setVisible(false);
		this.actionPrintPreview.setEnabled(false);
		this.actionPrintPreview.setVisible(false);

		this.actionPrint.setEnabled(false);
		this.actionPrint.setVisible(false);
		this.actionPre.setEnabled(false);
		this.actionPre.setVisible(false);

		this.actionFirst.setEnabled(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setEnabled(false);
		this.actionNext.setVisible(false);

		this.actionLast.setVisible(false);
		this.actionLast.setEnabled(false);

		this.actionCancel.setEnabled(false);
		this.actionCancel.setVisible(false);

		this.actionCancelCancel.setEnabled(false);
		this.actionCancelCancel.setVisible(false);

		this.actionTraceDown.setEnabled(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setEnabled(false);
		this.actionTraceUp.setVisible(false);

		this.actionWorkFlowG.setVisible(false);
		this.actionWorkFlowG.setEnabled(false);

		this.actionAuditResult.setVisible(false);
		this.actionAuditResult.setEnabled(false);

		this.actionNextPerson.setEnabled(false);
		this.actionNextPerson.setVisible(false);
		this.actionCalculator.setEnabled(false);
		this.actionCalculator.setVisible(false);

		this.actionMultiapprove.setEnabled(false);
		this.actionMultiapprove.setVisible(false);
		this.actionCreateFrom.setEnabled(false);
		this.actionCreateFrom.setVisible(false);

		this.actionAuditResult.setEnabled(false);
		this.actionAuditResult.setVisible(false);

		this.actionAttachment.setEnabled(false);
		this.actionAttachment.setVisible(false);

		this.contCreator.setEnabled(false);
		this.prmtCreator.setEnabled(false);
		this.contCreateTime.setEnabled(false);
		this.pkCreateTime.setEditable(false);

		this.contAuditor.setEnabled(false);
		this.prmtAuditor.setEnabled(false);
		this.contAuditTime.setEnabled(false);
		this.pkAuditTime.setEnabled(false);

		this.actionAuditResult.setEnabled(false);
		this.actionAuditResult.setVisible(false);
	}

	/**
	 * 将编辑界面上的表格编辑事件封装成一个适配器
	 */
	protected EntryEditAdapter entryEditLs = new EntryEditAdapter();

	/**
	 * 内部类，实现的通用的表格编辑适配器
	 * 
	 * @author xiaobin_li
	 * 
	 */
	protected class EntryEditAdapter extends KDTEditAdapter {
		public void editStopped(KDTEditEvent evt) {
			try {
				kdtEntry_editStopped(evt);
			} catch (Exception e) {
				e.printStackTrace();
				InvitePreSplitEditUI.this.handleException(e);
			}
		}
	}

	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception 
	{
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();

		String colName  = kdtEntry.getColumnKey(colIndex);

		if(colName.equals(COL_PRESPLITAMOUNT))
		{
			IRow row = kdtEntry.getRow(rowIndex);

			BigDecimal aimCost = new BigDecimal("0");
			if(row.getCell(this.COL_AIMCOST).getValue() != null)
			{
				aimCost = new BigDecimal(row.getCell(this.COL_AIMCOST).getValue().toString());
			}

			BigDecimal occuredCost = new BigDecimal("0");
			if(row.getCell(this.COL_OCCUREDCOST).getValue() != null)
			{
				occuredCost = new BigDecimal(row.getCell(this.COL_OCCUREDCOST).getValue().toString());
			}

			BigDecimal splitedCost = new BigDecimal("0");
			if(row.getCell(this.COL_SPLITEDCOST).getValue() != null)
			{
				splitedCost = new BigDecimal(row.getCell(this.COL_SPLITEDCOST).getValue().toString());
			}

			BigDecimal dynamicCost = new BigDecimal("0");
			if(row.getCell(this.COL_DYNAMICCOST).getValue() != null)
			{
				dynamicCost = new BigDecimal(row.getCell(this.COL_DYNAMICCOST).getValue().toString());
			}

			BigDecimal splitingAmount = FDCHelper.ZERO;
			if(row.getCell(this.COL_PRESPLITAMOUNT) != null);
			{
				splitingAmount = FDCHelper.toBigDecimal(row.getCell(this.COL_PRESPLITAMOUNT).getValue());
			}
			List bigDecimalList = new ArrayList();

			bigDecimalList.add(dynamicCost);
			bigDecimalList.add(occuredCost);
			bigDecimalList.add(splitedCost);
			bigDecimalList.add(splitingAmount);

			BigDecimal subAmount = new BigDecimal("0");
			subAmount = superSubstraction(bigDecimalList);

			row.getCell(this.COL_SUB_AMOUNT).setValue(subAmount);

			BigDecimal sum = new BigDecimal("0");
			for(int i = 0; i < kdtEntry.getRowCount(); ++i)
			{
				IRow tmpRow = kdtEntry.getRow(i);
				if(tmpRow.getCell(this.COL_PRESPLITAMOUNT).getValue() != null)
				{
					BigDecimal tmp = new BigDecimal(tmpRow.getCell(this.COL_PRESPLITAMOUNT).getValue().toString());
					sum = sum.add(tmp);
				}
			}

			if(kdtEntry.getFootRow(0)!= null)
			{
				kdtEntry.getFootRow(0).getCell(this.COL_PRESPLITAMOUNT).setValue(sum);
			}

		}
	}

	public void actionImportCostAccount_actionPerformed(ActionEvent e)
	throws Exception {

		CostAccountCollection costAccountCols = null;

//		if(prmtCurProject.getValue() != null)
//		{
			curProject = (CurProjectInfo)prmtCurProject.getValue();
			
			UIContext uiContext = new UIContext(this); 
			uiContext.put("curProject",curProject);
			uiContext.put("isPreSplit",Boolean.valueOf(true));

			acctUI=UIFactory.createUIFactory(UIFactoryName.MODEL).create(
					com.kingdee.eas.fdc.basedata.client.CostSplitAcctUI.class.getName(), uiContext, null , null);       

			acctUI.show();
			IUIWindow uiWin=acctUI;

			if (((CostSplitAcctUI) uiWin.getUIObject()).isOk()) {	
				costAccountCols=((CostSplitAcctUI) uiWin.getUIObject()).getData();
			}else{
				return;
			}

			loadPreSplitEntry( costAccountCols );
//		}
//		else
//		{
//			if(prmtInviteProject.getValue() == null)
//			{
//				FDCMsgBox.showInfo(this, "请先选择招标立项，由招标立项自动带出所属项目。所属项目不为空时，才有成本科目。");
//				abort();
//			}
//			else
//			{
//				if(prmtCurProject.getValue() == null)
//				{
//					FDCMsgBox.showInfo(this, "所属项目不为空，没有对应的成本科目。");
//					abort();
//				}
//			}
//		}
	}

	private void loadPreSplitEntry(CostAccountCollection paramCostAccts) throws EASBizException, BOSException
	{
		Set costAcctSet = new HashSet();
		if(paramCostAccts != null)
		{
			Iterator iter = paramCostAccts.iterator();

			while(iter.hasNext())
			{
				CostAccountInfo accInfo = (CostAccountInfo)(iter.next());

				if(!accInfo.isIsLeaf())
				{
					continue ;
				}

				boolean isExist = false;

				for(int i = 0; i < kdtEntry.getRowCount(); ++i)
				{
					IRow row = kdtEntry.getRow(i);

					CostAccountInfo costAccInfo = (CostAccountInfo)(kdtEntry.getRow(i).getCell(this.COL_COSTACCOUNT).getValue());

					if(costAccInfo != null)
					{
						if(costAccInfo.getId().equals(accInfo.getId()))
						{
							isExist = true ;
							break;
						}
					}
				}

				if(!isExist)
				{
					costAcctSet.add(accInfo);
				}
			}
		}

		Map invitePreSplitEntryCols = InvitePreSplitCostAmountFacadeFactory.getRemoteInstance().getCostAccRelDatas(costAcctSet);

		InvitePreSplitEntryCollection preSplitEntry = (InvitePreSplitEntryCollection)(invitePreSplitEntryCols.get("INVITE_PRESPLIT_ENTRY"));

		if(preSplitEntry.size() <= 0)
		{
			Iterator costAcctSetIter = costAcctSet.iterator();

			while(costAcctSetIter.hasNext())
			{
				CostAccountInfo tmpInfo = (CostAccountInfo)costAcctSetIter.next();

				IRow row = kdtEntry.addRow();
				row.getCell(this.COL_COSTACCOUNT).setValue(tmpInfo);
				row.getCell(this.COL_COSTACCOUNT_NAME).setValue(tmpInfo.getName());

				row.getCell(this.COL_CURPROJECT).setValue(tmpInfo.getCurProject());
			}
		}
		else
		{
			Iterator preSplitEntryIter = preSplitEntry.iterator();
			while(preSplitEntryIter.hasNext())
			{
				InvitePreSplitEntryInfo entryInfo = (InvitePreSplitEntryInfo)preSplitEntryIter.next();

				IRow row = kdtEntry.addRow();

				row.getCell(this.COL_CURPROJECT).setValue(entryInfo.getCurProject());
				row.getCell(this.COL_COSTACCOUNT).setValue(entryInfo.getCostAccount());
				row.getCell(this.COL_COSTACCOUNT_NAME).setValue(entryInfo.getCostAccount().getName());

				BigDecimal aimCost = entryInfo.getAimCost();
				row.getCell(this.COL_AIMCOST).setValue(entryInfo.getAimCost());

				BigDecimal occuredCost = entryInfo.getOccuredCost();
				row.getCell(this.COL_OCCUREDCOST).setValue(entryInfo.getOccuredCost());

				BigDecimal splitedCost = entryInfo.getSplitedCost();
				row.getCell(this.COL_SPLITEDCOST).setValue(entryInfo.getSplitedCost());

				BigDecimal dynamicCost = entryInfo.getDynamicCost();
				row.getCell(this.COL_DYNAMICCOST).setValue(entryInfo.getDynamicCost());

				BigDecimal splitingAmount = new BigDecimal("0");
				if(row.getCell(this.COL_PRESPLITAMOUNT).getValue() != null);
				{
					splitingAmount = new BigDecimal(splitingAmount.toString());
				}
				List bigDecimalList = new ArrayList();

				bigDecimalList.add(dynamicCost);
				bigDecimalList.add(occuredCost);
				bigDecimalList.add(splitedCost);
				bigDecimalList.add(splitingAmount);

				BigDecimal subAmount = new BigDecimal("0");
				subAmount = superSubstraction(bigDecimalList);

				row.getCell(this.COL_SUB_AMOUNT).setValue(subAmount);
			}
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception 
	{
		super.verifyInput(e);
	}

	protected void verifyInputForSave() throws Exception {

		super.verifyInputForSave();

//		if(editData.getNumber()==null||editData.getNumber().trim().length()==0){
//			FDCMsgBox.showWarning(this,"单据编码不能为空");
//			abort();
//		}

		if(editData.getInviteProject() == null){
			FDCMsgBox.showWarning(this,"立项名称不能为空");
			abort();
		}

		if(editData.getPreSplitAmount() == null)
		{
			FDCMsgBox.showWarning(this,"预拆分金额不能为空");
			abort();
		}
		
		if(editData.getEntry() == null || editData.getEntry().size() <= 0)
		{
			FDCMsgBox.showWarning(this,"预拆分分录不能为空");
			abort();
		}

		for(int i = 0; i < kdtEntry.getRowCount(); ++i)
		{
			IRow tmpRow = kdtEntry.getRow(i);
			if(tmpRow.getCell(this.COL_SUB_AMOUNT).getValue() != null )
			{
				BigDecimal tmp = new BigDecimal(tmpRow.getCell(this.COL_SUB_AMOUNT).getValue().toString());

				if(tmp.compareTo(new BigDecimal("0"))< 0)
				{
					StringBuffer buffer = new StringBuffer();

					buffer.append("第");
					buffer.append(i+1);
					buffer.append("行的差额小于0");

//					FDCMsgBox.showWarning(this, buffer.toString());
//					abort();
				}
			}
		}

		if(editData.getPreSplitAmount() != null)
		{
			BigDecimal preSplit = editData.getPreSplitAmount();

			BigDecimal sum = new BigDecimal("0");
			for(int i = 0; i < kdtEntry.getRowCount(); ++i)
			{
				IRow tmpRow = kdtEntry.getRow(i);
				if(tmpRow.getCell(this.COL_PRESPLITAMOUNT).getValue() != null)
				{
					BigDecimal tmp = new BigDecimal(tmpRow.getCell(this.COL_PRESPLITAMOUNT).getValue().toString());
					sum = sum.add(tmp);
				}
			}

			if(kdtEntry.getFootRow(0)!= null)
			{
				kdtEntry.getFootRow(0).getCell(this.COL_PRESPLITAMOUNT).setValue(sum);
			}

			if(preSplit.compareTo(sum) != 0)
			{
				FDCMsgBox.showWarning(this,"分录预拆分金额之和不等于预拆分金额");
				abort();
			}
		}
		
		//一个招标立项只能有一个招标预拆分
		if(!check())
		{
			abort();
		}
		
		/**
		 * 加入编码规则后，不是新增显示时，此处editData.getNumber()为空
		 * 
		 */
	//	editData.setName(editData.getNumber());
	//	this.editData.setName("a"+System.currentTimeMillis());
		editData.setName(this.editData.getInviteProject().getName()+System.currentTimeMillis());
	}
	
	 public ICodingRuleManager getEncodingRule()
	    {
	    	
	    	ICodingRuleManager codingRuleManager = null;
			try {
				if(this.getUIContext().get("codingRuleManager")== null){
				codingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
				this.getUIContext().put("codingRuleManager", codingRuleManager);
				}else{
					
					codingRuleManager = (ICodingRuleManager) this.getUIContext().get("codingRuleManager");
				}
				
				
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return codingRuleManager;
	    }
	

	protected void verifyInputForSubmint() throws Exception {
		super.verifyInputForSubmint();

		
		 ICodingRuleManager codingManager =getEncodingRule();
		    
			if(this.editData != null ){
				
				boolean isUseCodingRule = codingManager.isExist(this.editData,SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
				if(!isUseCodingRule){
					
					if(editData.getNumber()==null||editData.getNumber().trim().length()==0){
						FDCMsgBox.showWarning(this,"单据编码不能为空");
						abort();
					}
				}
			}
		
		
		
		

		if(editData.getInviteProject() == null){
			FDCMsgBox.showWarning(this,"立项名称不能为空");
			abort();
		}

		if(editData.getPreSplitAmount() == null)
		{
			FDCMsgBox.showWarning(this,"预拆分金额不能为空");
			abort();
		}
		
		if(editData.getEntry() == null || editData.getEntry().size() <= 0)
		{
			FDCMsgBox.showWarning(this,"预拆分分录不能为空");
			abort();
		}

		boolean showWarn = false;
		for(int i = 0; i < kdtEntry.getRowCount(); ++i)
		{
			IRow tmpRow = kdtEntry.getRow(i);
			if(tmpRow.getCell(this.COL_SUB_AMOUNT).getValue() != null )
			{
				BigDecimal tmp = new BigDecimal(tmpRow.getCell(this.COL_SUB_AMOUNT).getValue().toString());

				if(tmp.compareTo(new BigDecimal("0"))< 0)
				{
					StringBuffer buffer = new StringBuffer();

					buffer.append("第");
					buffer.append(i+1);
					buffer.append("行的差额小于0");
					showWarn = true;
//					FDCMsgBox.showWarning(this, buffer.toString());
//					abort();
				}
			}
		}
		if(showWarn){
			if(FDCMsgBox.YES!=FDCMsgBox.showConfirm2(this, "存在差额小于0的行，是否继续？")){
				abort();
			}
		}

		if(editData.getPreSplitAmount() != null)
		{
			BigDecimal preSplit = editData.getPreSplitAmount();

			BigDecimal sum = new BigDecimal("0");
			for(int i = 0; i < kdtEntry.getRowCount(); ++i)
			{
				IRow tmpRow = kdtEntry.getRow(i);
				if(tmpRow.getCell(this.COL_PRESPLITAMOUNT).getValue() != null)
				{
					BigDecimal tmp = new BigDecimal(tmpRow.getCell(this.COL_PRESPLITAMOUNT).getValue().toString());
					sum = sum.add(tmp);
				}
			}

			if(kdtEntry.getFootRow(0)!= null)
			{
				kdtEntry.getFootRow(0).getCell(this.COL_PRESPLITAMOUNT).setValue(sum);
			}

			if(preSplit.compareTo(sum) != 0)
			{
				FDCMsgBox.showWarning(this,"分录预拆分金额之和不等于预拆分金额");
				abort();
			}
		}

		if(!check())
		{
			abort();
		}
		
		if( this.editData.getName() == null || this.editData.getName() == ""){
			editData.setName(editData.getInviteProject().getName()+System.currentTimeMillis());
		}
	}

	private boolean check() {
		boolean flag = true;
		
		String inviteProjectID = editData.getInviteProject().getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterNum = new FilterInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("inviteProject"));
		
		filterNum.appendFilterItem("inviteProject", inviteProjectID);
		view.setFilter(filterNum);
		
		try {
			InvitePreSplitCollection result = InvitePreSplitFactory.getRemoteInstance().getInvitePreSplitCollection(view);
			
			if (this.getOprtState().equals(OprtState.ADDNEW)) {
				if (result.size() > 0) {
					MsgBox.showWarning("该招标立项已经进行了招标预拆分，不能执行此操作");
					flag = false;
					return flag;
				}
			}
			if (this.getOprtState().equals(OprtState.EDIT)) {
				if (this.editData.getInviteProject().getId().toString().equalsIgnoreCase(
						inviteProjectID)) {
					if (result.size() > 1) {
						MsgBox.showWarning("该招标立项已经进行了招标预拆分，不能执行此操作");
						flag = false;
						return flag;
					}
				}
				else {
					if (result.size() > 0) {
						MsgBox.showWarning("该招标立项已经进行了招标预拆分，不能执行此操作");
						flag = false;
						return flag;
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	private void setInviteProjectFilter(SelectorEvent e) throws Exception
	{
		EntityViewInfo view = new EntityViewInfo();

		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("orgUnit.id"));
		view.getSelector().add(new SelectorItemInfo("orgUnit.longNumber"));

		view.getSelector().add(new SelectorItemInfo("inviteType.id"));
		view.getSelector().add(new SelectorItemInfo("inviteType.longNumber"));

		view.getSelector().add(new SelectorItemInfo("number"));
		view.getSelector().add(new SelectorItemInfo("name"));

		view.getSelector().add(new SelectorItemInfo("project.id"));
		view.getSelector().add(new SelectorItemInfo("project.number"));
		view.getSelector().add(new SelectorItemInfo("project.name"));

		view.getSelector().add(new SelectorItemInfo("inviteForm"));
		view.getSelector().add(new SelectorItemInfo("respPerson.number"));
		view.getSelector().add(new SelectorItemInfo("prjDate"));
		
		view.getSelector().add(new SelectorItemInfo("inviteProjectState"));
		view.getSelector().add(new SelectorItemInfo("inviteType.number"));
		view.getSelector().add(new SelectorItemInfo("inviteType.name"));
		view.getSelector().add(new SelectorItemInfo("inviteType.longNumber"));
		
//		view.getSelector().add(new SelectorItemInfo("creator.name"));
//		view.getSelector().add(new SelectorItemInfo("createTime"));
//		view.getSelector().add(new SelectorItemInfo("auditor.name"));
//		view.getSelector().add(new SelectorItemInfo("auditTime"));

		prmtInviteProject.setSelectorCollection(getInviteProjectNewSelector());

		FilterInfo filter = new FilterInfo();

		Set stateSet = new HashSet();
		stateSet.add(InviteProjectStateEnum.AUDITTED_VALUE);
		stateSet.add(InviteProjectStateEnum.FILEMAKING_VALUE);
		stateSet.add(InviteProjectStateEnum.ANSWERING_VALUE);
		stateSet.add(InviteProjectStateEnum.APPRAISING_VALUE);
		stateSet.add(InviteProjectStateEnum.FIXED_VALUE);
		//stateSet.add(InviteProjectStateEnum.SIGNEDCONTRACT_VALUE);
		//状态过滤
		filter.getFilterItems().add(new FilterItemInfo("inviteProjectState", stateSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE, CompareType.EQUALS));

		//选择当前组织下的招标立项
		if(orgUnitInfo != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", orgUnitInfo.getId().toString()));
		}

		//招标立项自身的过滤条件

		//招标类型过滤条件
		if(getSelectInviteType() != null && (getSelectInviteType() instanceof com.kingdee.eas.fdc.invite.InviteTypeInfo))
		{
			FilterInfo typeFilter = new FilterInfo();
			InviteTypeInfo typeInfo = (InviteTypeInfo)getSelectInviteType();
			BOSUuid id = typeInfo.getId();

			Set idSet = getInviteTypeIdSet(id);
			filter.getFilterItems().add(new FilterItemInfo("inviteType.id", idSet, CompareType.INCLUDE));

		}

		view.setFilter(filter);
		prmtInviteProject.setEntityViewInfo(view);


		if (prmtInviteProject.getSelector() != null && prmtInviteProject.getSelector() instanceof com.kingdee.eas.framework.client.ListUI) {
			((com.kingdee.eas.framework.client.ListUI)prmtInviteProject.getSelector()).setFilterForQuery(filter);
			((com.kingdee.eas.framework.client.ListUI)prmtInviteProject.getSelector()).onLoad();

		}else {
			prmtInviteProject.getEntityViewInfo().setFilter(filter);
			prmtInviteProject.getQueryAgent().resetRuntimeEntityView();
			prmtInviteProject.setRefresh(true);
		}
	}

	protected SelectorItemCollection getInviteProjectNewSelector()
	{
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("state"));

		sic.add(new SelectorItemInfo("project.id"));
		sic.add(new SelectorItemInfo("project.number"));
		sic.add(new SelectorItemInfo("project.name"));

		sic.add(new SelectorItemInfo("inviteForm"));
		sic.add(new SelectorItemInfo("respPerson.number"));
		sic.add(new SelectorItemInfo("prjDate"));

		sic.add(new SelectorItemInfo("inviteType.longNumber"));
		sic.add(new SelectorItemInfo("inviteType.id"));
		sic.add(new SelectorItemInfo("inviteType.number"));
		sic.add(new SelectorItemInfo("inviteType.name"));

		sic.add(new SelectorItemInfo("inviteProjectState"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		sic.add(new SelectorItemInfo("inviteMode.*"));
		
//		sic.add(new SelectorItemInfo("creator.name"));
//		sic.add(new SelectorItemInfo("createTime"));
//		sic.add(new SelectorItemInfo("auditor.name"));
//		sic.add(new SelectorItemInfo("auditTime"));

		return sic ;
	}
	
	private Set getInviteTypeIdSet(BOSUuid id) throws EASBizException, BOSException
	{
		Set idSet = new HashSet();
		IObjectPK pk = new ObjectUuidPK(id);
		InviteTypeInfo parentTypeInfo = InviteTypeFactory.getRemoteInstance().getInviteTypeInfo(pk);
		
		String longNumber = parentTypeInfo.getLongNumber();
		EntityViewInfo view = new EntityViewInfo();
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber+"!%", CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber));
		
		filter.setMaskString("#0 or #1");
		view.setFilter(filter);
		
		com.kingdee.eas.fdc.invite.InviteTypeCollection typeCols = InviteTypeFactory.getRemoteInstance().getInviteTypeCollection(view);
		
		Iterator iter = typeCols.iterator();
		while(iter.hasNext())
		{
			InviteTypeInfo tmp = (InviteTypeInfo)iter.next();
			idSet.add(tmp.getId().toString());
		}
		
		return idSet ;
	}
	private void setInvProjectRelationValues(DataChangeEvent eventObj) throws EASBizException, BOSException
	{
		if(eventObj.getNewValue() != null)
		{
			if(eventObj.getNewValue() instanceof InviteProjectInfo)
			{
				InviteProjectInfo invProjectInfo = (InviteProjectInfo)(eventObj.getNewValue());
				/*prmtCurProject.setValue(invProjectInfo.getProject());
				if(invProjectInfo.getInviteMode() != null){
					this.prmtInviteMode.setData(invProjectInfo.getInviteMode());
				}*/
				if(invProjectInfo.getOrgUnit() != null){
					this.txtInviteProjectOrg.setText(invProjectInfo.getOrgUnit().getName());
				}
			}
		}else{
			this.prmtInviteMode.setDataNoNotify(null);
			this.txtInviteProjectOrg.setText(null,false);
		}
	}

	protected boolean isShowAttachmentAction()
	{
		return false;
	}

	/**
	 * 获取所选的招标立项类型
	 * @return
	 */
	private InviteTypeInfo getSelectInviteType()
	{
		InviteTypeInfo typeInfo = null ;
		if(getUIContext().get("INVITE_TYPE")!= null && getUIContext().get("INVITE_TYPE") instanceof InviteTypeInfo)
		{

			typeInfo = (InviteTypeInfo)(getUIContext().get("INVITE_TYPE"));
		}

		return typeInfo ;
	}

	//获得目标成本、动态成本、已发生、未发生等数据
	private void fetchData(String prjId) throws BOSException,EASBizException{
		final FullDynamicCostMap fullDynamicCostMap = FDCCostRptFacadeFactory.getRemoteInstance().getFullDynamicCost(prjId, null);
		this.aimCostMap=fullDynamicCostMap.getAimCostMap();
		this.dyCostMap=fullDynamicCostMap.getDynamicCostMapp();

	}

	private BigDecimal superSubstraction(List substrList)
	{
		if(substrList.size() <= 0)
		{
			return new BigDecimal("0");
		}

		BigDecimal firstBig = (BigDecimal)substrList.get(0);
		if(firstBig == null)
			firstBig = new BigDecimal("0");
		BigDecimal subSum = new BigDecimal("0");
		for(int i = substrList.size()-1; i > 0; --i)
		{
			if(substrList.get(i) != null)
			{
				subSum = subSum.add((BigDecimal)substrList.get(i));
			}
		}
		if(subSum == null)
			subSum = new BigDecimal("0");
		firstBig = firstBig.subtract(subSum);

		return firstBig;
	}

	private void setFootEntryRow()
	{
		//设置合计行
		KDTFootManager footRowManager= kdtEntry.getFootManager();
		IRow footRow = null;
		if(footRowManager==null){

			footRowManager = new KDTFootManager(kdtEntry);
			footRowManager.addFootView();
			kdtEntry.setFootManager(footRowManager);

			footRow= footRowManager.addFootRow(0);
			footRow.setUserObject("FDC_PARAM_TOTALCOST");
			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));

			kdtEntry.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			kdtEntry.getIndexColumn().setWidth(60);
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);

			//设置到第一个可视行
			footRowManager.addIndexText(0, "合计");
		}else{
			footRow=kdtEntry.getFootRow(0);
			if(footRow.getUserObject()==null||!footRow.getUserObject().equals("FDC_PARAM_TOTALCOST")){
				footRow=kdtEntry.addFootRow(1);
			};
		}

		BigDecimal sum = new BigDecimal("0");
		for(int i = 0; i < kdtEntry.getRowCount(); ++i)
		{
			IRow row = kdtEntry.getRow(i);
			if(row.getCell(this.COL_PRESPLITAMOUNT).getValue() != null)
			{
				BigDecimal tmp = new BigDecimal(row.getCell(this.COL_PRESPLITAMOUNT).getValue().toString());
				sum = sum.add(tmp);
			}
		}

		if(footRow != null)
		{
			footRow.getCell(this.COL_PRESPLITAMOUNT).setValue(sum);
		}

	}

	public SelectorItemCollection getSelectors() {

		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("creator.*"));
		sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		sic.add(new SelectorItemInfo("number"));

		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("auditor.*"));
		sic.add(new SelectorItemInfo("orgUnit.*"));

		sic.add(new SelectorItemInfo("auditTime"));
		sic.add(new SelectorItemInfo("inviteProject.*"));
		sic.add(new SelectorItemInfo("preSplitAmount"));

		sic.add(new SelectorItemInfo("isContractSplit"));
		sic.add(new SelectorItemInfo("isAssContract"));
		sic.add(new SelectorItemInfo("entry.preSplitAmount"));

		sic.add(new SelectorItemInfo("entry.aimCost"));
		sic.add(new SelectorItemInfo("entry.occuredCost"));
		sic.add(new SelectorItemInfo("entry.splitedCost"));

		sic.add(new SelectorItemInfo("entry.dynamicCost"));
		sic.add(new SelectorItemInfo("entry.substractAmount"));
		sic.add(new SelectorItemInfo("entry.*"));

		sic.add(new SelectorItemInfo("entry.id"));
		sic.add(new SelectorItemInfo("entry.curProject.*"));
		sic.add(new SelectorItemInfo("entry.costAccount.*"));

		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("lastUpdateTime"));

		sic.add(new SelectorItemInfo("inviteProject.project.id"));
		sic.add(new SelectorItemInfo("inviteProject.project.number"));
		sic.add(new SelectorItemInfo("inviteProject.project.name"));
		sic.add(new SelectorItemInfo("inviteProject.inviteMode.*"));
		sic.add(new SelectorItemInfo("inviteProject.orgUnit.*"));

		sic.add(new SelectorItemInfo("state"));
		return sic;

	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		this.prmtCurProject.setValue(null);
		this.actionImportCostAccount.setEnabled(true);
		
		IRow footRow=kdtEntry.getFootRow(0);
		footRow.getCell(COL_PRESPLITAMOUNT).setValue(new BigDecimal("0"));
	}
	
	private boolean checkCanOperate()
	{
		boolean flag = true ;
		
		String orgId = editData.getOrgUnit().getId().toString();
		if (orgUnitInfo.getId().toString().equals(orgId)) {
			flag = true;
		} else {
			flag = false ;
		}

		return flag;
	}
	
	public void actionInviteExecuteInfo_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionInviteExecuteInfo_actionPerformed(e);
		InviteProjectInfo info = null;
		if(editData.getInviteProject() != null){
			info = editData.getInviteProject();
		}else if(prmtInviteProject.getData() != null){
				info = (InviteProjectInfo) prmtInviteProject.getData();	
		}
		if(info == null ){
			FDCMsgBox.showError("请先选择招标立项！");
			abort();
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		uiContext.put("INVITE_PROJECT", info.getId().toString());
		uiContext.put("LIST_UI","com.kingdee.eas.fdc.invite.client.InvitePreSplitEditUI");
		uiContext.put("INVITEPROJECT_NAME", null);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(InviteAllInformationUI.class.getName(), uiContext,
						null, OprtState.ADDNEW);
		uiWindow.show();
	}
}