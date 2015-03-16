/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.sellhouse.OverdueCauseTypeEnum;
import com.kingdee.eas.fdc.sellhouse.OverdueDescribeFactory;
import com.kingdee.eas.fdc.sellhouse.OverdueDescribeInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class OverdueDescribeEditUI extends AbstractOverdueDescribeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(OverdueDescribeEditUI.class);
    
    /**
     * output class constructor
     */
    public OverdueDescribeEditUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
		super.onLoad();
		
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
		this.menuBiz.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCalculator.setVisible(false);
		this.actionAudit.setVisible(false);
		if (STATUS_ADDNEW.equals(this.oprtState)) {
			if (( this.getUIContext().get("tranID") != null) && ( this.getUIContext().get("tranID") != "")) {
				 String tranID =   (String) this.getUIContext().get("tranID");
				 this.txtTransOviewId.setText(tranID);
				 SelectorItemCollection sic = new SelectorItemCollection();
				 sic.add("loanBank.*");
				 SelectorItemCollection transic = new SelectorItemCollection();
				 transic.add("head.billId");
				 TranBusinessOverViewInfo tran=TranBusinessOverViewFactory.getRemoteInstance().getTranBusinessOverViewInfo(new ObjectUuidPK(tranID),transic);
				 SignManageInfo sign=SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(tran.getHead().getBillId()),sic);
				 this.prmtLoanBank.setValue(sign.getLoanBank());
			}
		}
		this.txtCauseDescribe.setMaxLength(500);
		this.txtResolve.setMaxLength(500);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("type", OverdueCauseTypeEnum.YSK_VALUE));
		view.setFilter(filter);
		this.prmtOverdueSort.setEntityViewInfo(view);
	}

	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return OverdueDescribeFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	protected IObjectValue createNewData() {
		OverdueDescribeInfo od=new OverdueDescribeInfo();
		od.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return od ;
	}
}