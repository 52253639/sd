/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.CommissionApplyCollection;
import com.kingdee.eas.fdc.tenancy.CommissionApplyFactory;
import com.kingdee.eas.fdc.tenancy.CommissionApplyInfo;
import com.kingdee.eas.fdc.tenancy.IntentionCustomerInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class CommissionApplyEditUI extends AbstractCommissionApplyEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(CommissionApplyEditUI.class);
    public CommissionApplyEditUI() throws Exception
    {
        super();
    }
    protected void attachListeners() {
    	addDataChangeListener(this.prmtProject);
    	addDataChangeListener(this.txtCommissionAmount);
    	addDataChangeListener(this.txtTotalAmount);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.prmtProject);
		removeDataChangeListener(this.txtCommissionAmount);
		removeDataChangeListener(this.txtTotalAmount);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return CommissionApplyFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return null;
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	protected void initControl() throws EASBizException, BOSException {
		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPre.setVisible(false);
		this.menuView.setVisible(false);
		
		this.menuBiz.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionAddNew.setVisible(false);
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id",this.editData.getIntentionCustomer().getProject().getId().toString()));
		view.setFilter(filter);
		this.prmtProject.setEntityViewInfo(view);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
		} else {
			this.unLockUI();
		}
	}
	public void onLoad() throws Exception {
		super.onLoad();
		initControl();
	}
	public void storeFields(){
		super.storeFields();
	}
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		if(editData.getIntentionCustomer()!=null){
			UIContext uiContext = new UIContext(this);
			try {
				uiContext.put("ID", editData.getIntentionCustomer().getId().toString());
				IntentionCustomerEditUI	ui = (IntentionCustomerEditUI) UIFactoryHelper.initUIObject(IntentionCustomerEditUI.class.getName(), uiContext, null,OprtState.VIEW);
				this.kDScrollPane1.setViewportView(ui);
				this.kDScrollPane1.setKeyBoardControl(true);
				this.kDScrollPane1.setEnabled(false);
			} catch (UIException e) {
				e.printStackTrace();
			}
			try {
				CommissionApplyCollection col=CommissionApplyFactory.getRemoteInstance().getCommissionApplyCollection("select *,project.*,room.* from where IntentionCustomer.id='"+this.editData.getIntentionCustomer().getId().toString()+"' order by createtime");
				if(col.size()>0){
					if(this.editData.getId()!=null){
						if(!col.get(0).getId().equals(this.editData.getId())){
							this.prmtProject.setEnabled(false);
							this.prmtRoom.setEnabled(false);
							this.txtTotalAmount.setEnabled(false);
							this.txtCommissionAmount.setEnabled(false);
							this.txtRate.setEnabled(false);
							
							this.prmtProject.setValue(col.get(0).getProject());
							this.prmtRoom.setValue(col.get(0).getRoom());
							this.txtTotalAmount.setValue(col.get(0).getTotalAmount());
							this.txtCommissionAmount.setValue(col.get(0).getCommissionAmount());
							this.txtRate.setValue(col.get(0).getRate());
						}
					}else{
						this.prmtProject.setEnabled(false);
						this.prmtRoom.setEnabled(false);
						this.txtTotalAmount.setEnabled(false);
						this.txtCommissionAmount.setEnabled(false);
						this.txtRate.setEnabled(false);
						
						this.prmtProject.setValue(col.get(0).getProject());
						this.prmtRoom.setValue(col.get(0).getRoom());
						this.txtTotalAmount.setValue(col.get(0).getTotalAmount());
						this.txtCommissionAmount.setValue(col.get(0).getCommissionAmount());
						this.txtRate.setValue(col.get(0).getRate());
					}
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
			
		}
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
	protected void fetchInitData() throws Exception {
		
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel = super.getSelectors();
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("room.*");
    	sel.add("state");
    	sel.add("isPayed");
    	sel.add("intentionCustomer.*");
    	sel.add("sourceBillId");
		return sel;
	}
	protected IObjectValue createNewData() {
		CommissionApplyInfo info= new CommissionApplyInfo();
		info.setState(FDCBillStateEnum.SAVED);
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		info.setBizDate(now);
		info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		
		IntentionCustomerInfo cus = (IntentionCustomerInfo)this.getUIContext().get("cus");
    	info.setIntentionCustomer(cus);
		return info;
	}
	protected void verifyInputForSave() throws Exception{
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtProject);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtRoom);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtTotalAmount);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtCommissionAmount);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtRate);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtAmount);
		
		if(FDCHelper.add(this.txtAmount.getBigDecimalValue(), this.editData.getIntentionCustomer().getPayedAmount()).compareTo(this.txtCommissionAmount.getBigDecimalValue())>0){
			FDCMsgBox.showWarning(this,"累计支付金额超过佣金金额");
			SysUtil.abort();
		}
	}
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtProject);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtRoom);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtTotalAmount);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtCommissionAmount);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtRate);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtAmount);
		
		if(FDCHelper.add(this.txtAmount.getBigDecimalValue(), this.editData.getIntentionCustomer().getPayedAmount()).compareTo(this.txtCommissionAmount.getBigDecimalValue())>0){
			FDCMsgBox.showWarning(this,"累计支付金额超过佣金金额");
			SysUtil.abort();
		}
	}
	protected void prmtProject_dataChanged(DataChangeEvent e) throws Exception {
		if(this.prmtProject.getValue()!=null){
			this.prmtRoom.setEnabled(true);
			
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("building.sellProject.id",((SellProjectInfo)this.prmtProject.getValue()).getId().toString()));
			view.setFilter(filter);
			
			this.prmtRoom.setEntityViewInfo(view);
			
			if(this.prmtRoom.getValue()!=null){
				RoomInfo room=RoomFactory.getRemoteInstance().getRoomInfo("select building.sellProject.id from where id='"+((RoomInfo)this.prmtRoom.getValue()).getId().toString()+"'");
				if(!room.getBuilding().getSellProject().getId().toString().equals(((SellProjectInfo)this.prmtProject.getValue()).getId().toString())){
					this.prmtRoom.setValue(null);
				}
			}
		}else{
			this.prmtRoom.setEnabled(false);
		}
	}
	protected void txtCommissionAmount_dataChanged(DataChangeEvent e)throws Exception {
		this.txtRate.setValue(FDCHelper.divide(FDCHelper.multiply(this.txtCommissionAmount.getBigDecimalValue(), new BigDecimal(100)),this.txtTotalAmount.getBigDecimalValue(), 2, BigDecimal.ROUND_HALF_UP));
	}
	protected void txtTotalAmount_dataChanged(DataChangeEvent e)throws Exception {
		this.txtRate.setValue(FDCHelper.divide(FDCHelper.multiply(this.txtCommissionAmount.getBigDecimalValue(), new BigDecimal(100)),this.txtTotalAmount.getBigDecimalValue(), 2, BigDecimal.ROUND_HALF_UP));
	}
}