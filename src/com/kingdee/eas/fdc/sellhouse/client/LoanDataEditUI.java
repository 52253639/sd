/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.sellhouse.LoanDataEntryInfo;
import com.kingdee.eas.fdc.sellhouse.LoanDataFactory;
import com.kingdee.eas.fdc.sellhouse.LoanDataInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.DeletedStatusEnum;
import com.kingdee.eas.framework.EffectedStatusEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class LoanDataEditUI extends AbstractLoanDataEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(LoanDataEditUI.class);

	private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	/**
	 * output class constructor
	 */
	public LoanDataEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields msbethod
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected IObjectValue createNewData() {
		LoanDataInfo objectValue = new LoanDataInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
				.get("sellProject");
		objectValue.setSellProject(sellProject);
		objectValue.setIsEnable(true);
		objectValue.setUsedAmount(FDCHelper.ZERO);
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return LoanDataFactory.getRemoteInstance();
	}

	public static String getRes(String resName) {
		return EASResource.getString(
				"com.kingdee.eas.fdc.sellhouse.SellHouseResource", resName);
	}

	private void initUI() throws Exception {
		// 分录增、删按钮调整至分录上方
		JButton btnAddRuleNew = ctnInterestRate.add(actionAddLine);
		JButton btnDelRuleNew = ctnInterestRate.add(actionRemoveLine);
		btnAddRuleNew.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnAddRuleNew.setToolTipText(getRes("sell001"));
		btnAddRuleNew.setText(getRes("sell001"));
		btnAddRuleNew.setSize(22, 19);
		btnDelRuleNew.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		btnDelRuleNew.setToolTipText(getRes("sell002"));
		btnDelRuleNew.setText(getRes("sell002"));
		btnDelRuleNew.setSize(22, 19);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initUI();
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew,
				actionAttachment,actionPrint, actionPrintPreview,
				actionWorkFlowG, actionInsertLine, actionCopy, actionPre,
				actionNext, actionFirst, actionLast, actionTraceDown,
				actionTraceUp, actionCreateFrom, actionSubmit,actionMultiapprove,
				actionNextPerson,actionAuditResult}, false);
		if (!saleOrg.isIsBizUnit()) {
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] {
					actionAddNew, actionEdit, actionRemove, actionAddLine,
					actionRemoveLine }, false);
		}
		btnAddLine.setVisible(false);
		btnRemoveLine.setVisible(false);
		
		initOldData(editData);
		
	}

	public void onShow() throws Exception {
		super.onShow();

		txtDepositAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtDepositAmount.setMinimumValue(FDCHelper.ZERO);
		txtDepositAmount.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
		txtLoanLine.setHorizontalAlignment(JTextField.RIGHT);
		txtLoanLine.setMinimumValue(FDCHelper.ZERO);
		txtLoanLine.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
		txtUsedAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtUsedAmount.setPrecision(2);
		txtUsedAmount.setMinimumValue(FDCHelper.ZERO);
		txtUsedAmount.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
		txtLoanFixedYear.setMinimumValue(FDCHelper.ZERO);
		txtLoanFixedYear.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
		txtDepositAmount.setRemoveingZeroInDispaly(false);
		txtDepositAmount.setPrecision(2);
		txtLoanLine.setRemoveingZeroInDispaly(false);
		txtUsedAmount.setRemoveingZeroInDispaly(false);

		getDetailTable().checkParsed();
		ICellEditor cEditor = this.getDetailTable().getColumn("interestRate")
				.getEditor();
		if (cEditor != null) {
			KDFormattedTextField amtTextField = (KDFormattedTextField) cEditor
					.getComponent();
			amtTextField.setPrecision(2);
			amtTextField.setDataType(1);
			amtTextField.setRequired(true);
			amtTextField.setHorizontalAlignment(JTextField.RIGHT);
			amtTextField.setMaximumValue(FDCHelper.MAX_VALUE);
			amtTextField.setMinimumValue(FDCHelper.ZERO);
		} else {
			KDFormattedTextField amtTextField = new KDFormattedTextField();
			amtTextField.setPrecision(2);
			amtTextField.setDataType(1);
			amtTextField.setRequired(true);
			amtTextField.setHorizontalAlignment(JTextField.RIGHT);
			amtTextField.setMaximumValue(FDCHelper.MAX_VALUE);
			amtTextField.setMinimumValue(FDCHelper.ZERO);
			this.getDetailTable().getColumn("interestRate").setEditor(
					new KDTDefaultCellEditor(amtTextField));
		}

		ICellEditor eEditor = this.getDetailTable().getColumn("fixedYear")
				.getEditor();
		if (eEditor != null) {
			KDFormattedTextField amtTextField = (KDFormattedTextField) eEditor
					.getComponent();
			amtTextField.setPrecision(2);
			amtTextField.setDataType(0);
			amtTextField.setRequired(true);
			amtTextField.setHorizontalAlignment(JTextField.RIGHT);
			amtTextField.setMaximumValue(FDCHelper.MAX_VALUE);
			amtTextField.setMinimumValue(FDCHelper.ONE);
		} else {
			KDFormattedTextField amtTextField = new KDFormattedTextField();
			amtTextField.setPrecision(2);
			amtTextField.setDataType(0);
			amtTextField.setRequired(true);
			amtTextField.setHorizontalAlignment(JTextField.RIGHT);
			amtTextField.setMaximumValue(FDCHelper.MAX_VALUE);
			amtTextField.setMinimumValue(FDCHelper.ONE);
			this.getDetailTable().getColumn("fixedYear").setEditor(
					new KDTDefaultCellEditor(amtTextField));
		}
	}

	protected KDTable getDetailTable() {
		return kdtEntrys;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		LoanDataEntryInfo objectValue = new LoanDataEntryInfo();
		return objectValue;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("编码必须录入！");
			abort();
		 }
		if (StringUtils.isEmpty(this.txtName.getText())) {
			MsgBox.showInfo("名称必须录入！");
			abort();
		 }
		if (this.prmtBank.getValue() == null) {
			MsgBox.showInfo("按揭银行不能为空！");
			abort();
		 }
		FDCClientVerifyHelper.verifyInput(this, getDetailTable(),
				"interestRate");
		FDCClientVerifyHelper.verifyUIControlEmpty(this);
		if (getDetailTable().getRowCount() < 1) {
			MsgBox.showError(this, "按揭利率设置不能为空！");
			SysUtil.abort();
		}
	}

	public void loadFields() {
		super.loadFields();
		SHEHelper.setNumberEnabled(editData,getOprtState(),txtNumber);
		
		if (editData != null && editData.getId() != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("loanLoanData.id", editData.getId()
							.toString()));
			try {
				if (SHEPayTypeFactory.getRemoteInstance().exists(filter)) {
					txtName.setEnabled(false);
					txtNumber.setEnabled(false);
					prmtBank.setEnabled(false);
					prmtCurrency.setEnabled(false);
					prmtSellProject.setEnabled(false);
				}
				filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("afLoanData.id", editData.getId()
								.toString()));
				if (SHEPayTypeFactory.getRemoteInstance().exists(filter)) {
					txtName.setEnabled(false);
					txtNumber.setEnabled(false);
					prmtBank.setEnabled(false);
					prmtCurrency.setEnabled(false);
					prmtSellProject.setEnabled(false);
				}
			} catch (EASBizException e) {
				logger.error(e);
			} catch (BOSException e) {
				logger.error(e);
			}

			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filterPurchase = new FilterInfo();
			filterPurchase.getFilterItems().add(
					new FilterItemInfo("payType.loanLoanData.id", editData
							.getId().toString()));
			filter.getFilterItems().add(
					new FilterItemInfo("purchaseState",
							PurchaseStateEnum.ChangeRoomBlankOut,
							CompareType.NOTEQUALS));
			filter.getFilterItems()
					.add(
							new FilterItemInfo("purchaseState",
									PurchaseStateEnum.ManualBlankOut,
									CompareType.NOTEQUALS));
			filter.getFilterItems()
					.add(
							new FilterItemInfo("purchaseState",
									PurchaseStateEnum.NoPayBlankOut,
									CompareType.NOTEQUALS));
			view.setFilter(filterPurchase);
			BigDecimal totalAmt = FDCHelper.ZERO;
			try {
				PurchaseCollection purColl = PurchaseFactory
						.getRemoteInstance().getPurchaseCollection(view);
				for (Iterator it = purColl.iterator(); it.hasNext();) {
					PurchaseInfo info = (PurchaseInfo) it.next();
					if (info.getLoanAmount() != null) {
						totalAmt = totalAmt.add(FDCHelper.toBigDecimal(info
								.getLoanAmount()));
					}
				}
				txtUsedAmount.setValue(totalAmt);
			} catch (BOSException e) {
				logger.error(e);
			}
		}
	}

	protected void getAttachMentItem(KDTable table) {
	}

	public KDMenuItem getAttachMenuItem(KDTable table) {
		return null;
	}
	
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception 
	{
		super.actionExitCurrent_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.getId()!=null) {
			if(LoanDataFactory.getRemoteInstance().exists("where isEnable =1 and id='"+this.editData.getId().toString()+"'")) {
				MsgBox.showInfo("该条记录已启用，不允许修改！");
				return;
			}
		}
		
		super.actionEdit_actionPerformed(e);
		FDCClientHelper.setActionEnable(actionAttachment, false);
	}
	public void actionSave_actionPerformed(ActionEvent e) throws Exception{
		
		super.actionSave_actionPerformed(e);
		
	}
    protected OrgType getMainBizOrgType()
    {
        return OrgType.Sale;
    }   
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSubmit_actionPerformed(e);
    }
    
    /**
     * @author xiaoao_liu
     * @date 2010-10-25
     * @description 重写父类，设置附件管理不可见
     */
    
    protected boolean isShowAttachmentAction()
    {
        return false;
    }
    /**
     * @author tim_gao
     * @date 2010-10-25
     * @description 已经启用的记录，不能删除
     */
    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.editData.getId()!=null) {
//			if(LoanDataFactory.getRemoteInstance().exists("where isEnable =1 and id='"+this.editData.getId().toString()+"'")) {
//				MsgBox.showInfo("该条记录已启用，不允许删除！");
//				return;
//			}
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",this.getEditData().getId().toString(),CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("isEnable",Boolean.valueOf(true),CompareType.EQUALS));
			if(LoanDataFactory.getRemoteInstance().exists(filter)){
				MsgBox.showInfo("该条记录已启用，不允许删除！");
				this.abort();
			}
			super.actionEdit_actionPerformed(e);
			FDCClientHelper.setActionEnable(actionAttachment, false);
		}
    	
    }
}