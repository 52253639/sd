/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 违约金明细查询界面
 */
public class DefaultAmountMangerEditUI extends AbstractDefaultAmountMangerEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(DefaultAmountMangerEditUI.class);
    
    /**
     * by..... wancheng
     */
    public DefaultAmountMangerEditUI() throws Exception
    {
        super();
    }

	public void onLoad() throws Exception 
	{
		kdtEntry.checkParsed();
		kdtEntry.getColumn("actDate").getStyleAttributes().setHided(true);
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
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionAudit.setVisible(false);
		
		Object[] obj = {txtContractAmount,txtArgAmount,txtRefDeAmount,txtSubDeAmount,txtCarryAmount};
		FDCHelper.setComponentPrecision(obj,2);
		
		CRMClientHelper.changeTableNumberFormat(kdtEntry, new String[]{"appAmount","actAmount","argAmount","referAmount"});
	}

    public void loadFields()
    {
		super.loadFields();
	}

	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * 关联销售单
     */
    public void actionRelevance_actionPerformed(ActionEvent e) throws Exception {
		String id = this.editData.getTransaction().getBillId().toString();
		UIContext context = new UIContext(this);
		context.put("ID",id);
		UIFactory.createUIFactory(UIFactoryName.MODEL).create(SignManageEditUI.class.getName(),context,null,OprtState.VIEW).show();
	}
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData == null || editData.getId() == null) {
			MsgBox.showWarning(this, "请提交后再打印!");
			SysUtil.abort();
		}
		String receId = this.editData.getId().toString();
		if (receId != null && receId.length() > 0) {
			DefaultAmountMangerPrintDataProvider data = new DefaultAmountMangerPrintDataProvider(
					receId,
					new MetaDataPK(
							"com.kingdee.eas.fdc.sellhouse.app.DefaultAmountMangerQuery"));
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.print("/bim/fdc/sellhouse/default", data,
					javax.swing.SwingUtilities.getWindowAncestor(this));
			super.actionPrint_actionPerformed(e);
		}
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)throws Exception {
		if (this.editData == null || editData.getId() == null) {
			MsgBox.showWarning(this, "请提交后再打印!");
			SysUtil.abort();
		}
		String receId = this.editData.getId().toString();
		if (receId != null && receId.length() > 0) {
			DefaultAmountMangerPrintDataProvider data = new DefaultAmountMangerPrintDataProvider(
					receId,
					new MetaDataPK(
							"com.kingdee.eas.fdc.sellhouse.app.DefaultAmountMangerQuery"));
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.print("/bim/fdc/sellhouse/default", data,
					javax.swing.SwingUtilities.getWindowAncestor(this));
			super.actionPrintPreview_actionPerformed(e);
		}
		
	}
	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}

	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return DefaultAmountMangerFactory.getRemoteInstance();
	}
}