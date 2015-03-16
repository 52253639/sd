/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BillAdjustFactory;
import com.kingdee.eas.fdc.sellhouse.BillAdjustInfo;
import com.kingdee.eas.fdc.sellhouse.BillAdjustTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseFacade;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class BillAdjustEditUI extends AbstractBillAdjustEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(BillAdjustEditUI.class);
    
    public BillAdjustEditUI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
        super.storeFields();
        this.editData.setName(this.editData.getNumber());
    }

    public void onLoad() throws Exception {
		super.onLoad();
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		
		ItemAction[] hideAction = new ItemAction[] { this.actionAddLine, this.actionCopy, this.actionCopyFrom, this.actionCreateFrom, this.actionCreateTo, this.actionFirst, this.actionNext,
				this.actionRemoveLine, this.actionNextPerson, this.actionAuditResult, this.actionMultiapprove, this.actionAttachment, this.actionDelVoucher, this.actionLast, this.actionPre,
				this.actionSave, this.actionTraceDown, this.actionTraceUp, this.actionWorkFlowG, this.actionUnAudit, this.actionAddNew, this.actionRemove, this.actionInsertLine };
		FDCClientHelper.setActionVisible(hideAction, false);
		
		this.f7Creator.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		this.f7Auditor.setEnabled(false);
		this.pkAuditTime.setEnabled(false);
		this.prmtPurchase.setEnabled(false);
		
		this.txtNumber.setRequired(true);
		this.pkBizDate.setRequired(true);
	}
    
    public void loadFields() {
    	super.loadFields();
    }
    
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
    	
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSubmit_actionPerformed(e);
    	this.storeFields();
    	initOldData(this.editData);
    	this.destroyWindow();
    }
    
    protected IObjectValue createNewData() {
    	BillAdjustInfo billAdjust = new BillAdjustInfo();
    	
    	billAdjust.setBizDate(new Date());
    	billAdjust.setAdjustType(BillAdjustTypeEnum.Purchase);
    	billAdjust.setCreateTime(new Timestamp(new Date().getTime()));
    	billAdjust.setCreator(SysContext.getSysContext().getCurrentUserInfo());
    	billAdjust.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
    	billAdjust.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
    	billAdjust.setBookedDate(new Date());
    	
    	String purchaseId = (String) this.getUIContext().get("purchaseId");
    	if(purchaseId != null){
    		try {
				PurchaseInfo purchase = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(purchaseId));
				billAdjust.setPurchase(purchase);
    		} catch (EASBizException e) {
				handleException(e);
				this.abort();
			} catch (BOSException e) {
				handleException(e);
				this.abort();
			}
    	}
    	
    	return billAdjust;
    }
    
	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected ICoreBase getBizInterface() throws Exception {
		return BillAdjustFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

}