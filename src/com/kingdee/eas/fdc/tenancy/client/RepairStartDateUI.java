/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

public class RepairStartDateUI extends AbstractRepairStartDateUI
{
    private static final Logger logger = CoreUIObject.getLogger(RepairStartDateUI.class);

    public RepairStartDateUI() throws Exception
    {
        super();
    }
    
    protected void inOnload() throws Exception {
		// super.inOnload();
	}
    
    /**
     * 隐藏多余按钮
     */
	private void initControl() {
		this.btnPrint.setEnabled(true);
		this.btnPrintPreview.setEnabled(true);
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.menuItemSave.setVisible(false);
		this.menuItemSubmit.setVisible(false);
		this.rMenuItemSubmit.setVisible(false);
		this.rMenuItemSubmitAndAddNew.setVisible(false);
		this.rMenuItemSubmitAndPrint.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.rMenuItemSubmitAndAddNew.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnSave.setVisible(false);
		this.btnSubmit.setVisible(false);
		this.btnCopy.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnLast.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		TenancyBillInfo tenancyBill = (TenancyBillInfo)this.getUIContext().get("tenancyBill");
		setFirstLeaseEndDateVisible(tenancyBill.getFirstLeaseType());
	}
	
	public static void showUI(IUIObject ui,TenancyBillInfo tenancyBill) throws EASBizException, BOSException
	{
		UIContext uiContext = new UIContext(ui);
		uiContext.put("tenancyBill",tenancyBill);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(RepairStartDateUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
	}
	
	protected void comboFirstLeaseType_itemStateChanged(ItemEvent e) throws Exception {
		setFirstLeaseEndDateVisible((FirstLeaseTypeEnum) this.comboFirstLeaseType.getSelectedItem());
	}

	private void setFirstLeaseEndDateVisible(FirstLeaseTypeEnum type) {
		if(FirstLeaseTypeEnum.UserDefined.equals(type)){
			this.contFirstLeaseEndDate.setVisible(true);
		}else{
			this.contFirstLeaseEndDate.setVisible(false);
		}
	}
	
	protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {
		TenancyBillInfo tenancyBill = (TenancyBillInfo)this.getUIContext().get("tenancyBill");
//		tenancyBill = TenancyHelper.getTenancyBillInfo(tenancyBill.getId().toString());
		Date startDateLimit = tenancyBill.getStartDateLimit();
		Date repairStartDate = new Date();
		if(this.pkRepairStartDate.getValue()==null)
		{
			MsgBox.showInfo("租赁开始日期不能为空!");
			this.abort();
		}else
		{
			repairStartDate = (Date)this.pkRepairStartDate.getValue();
			if(startDateLimit.before(repairStartDate))
			{
				MsgBox.showInfo("租赁开始日期不能晚于起始日期期限!");
				this.abort();
			}
			
		}
		Date firstLeaseDate = new Date();
		if(FirstLeaseTypeEnum.UserDefined.equals((FirstLeaseTypeEnum)this.comboFirstLeaseType.getSelectedItem()))
		{
			firstLeaseDate = (Date)this.pkFirstLeaseEndDate.getValue();
			if(this.pkFirstLeaseEndDate.getValue()==null)
			{
				MsgBox.showInfo("首租结束日期不能为空!");
				this.abort();
			}
		}
		FirstLeaseTypeEnum firstLease = (FirstLeaseTypeEnum)this.comboFirstLeaseType.getSelectedItem();
		TenancyBillFactory.getRemoteInstance().repairStartDate(tenancyBill, repairStartDate, firstLease, firstLeaseDate);
		this.destroyWindow();
	}

	protected void btnNoConfirm_actionPerformed(ActionEvent e) throws Exception {
		this.destroyWindow();
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

}