/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCStringHelper;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.ChequeCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.IReceiptInvoiceFacade;
import com.kingdee.eas.fdc.sellhouse.InvoiceFactory;
import com.kingdee.eas.fdc.sellhouse.InvoiceInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiptInvoiceFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class ChangeInvoiceUI extends AbstractChangeInvoiceUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChangeInvoiceUI.class);
    
    public ChangeInvoiceUI() throws Exception
    {
        super();
    }

	// 票据ID
	ChequeTypeEnum invType =null;
	//MarketDisplaySetting setting = new MarketDisplaySetting();


	public void loadFields() {
		super.loadFields();
	}

	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (getUIContext().get("invIDs") != null) {
			setDefaultFeilds();
		}
		if(getUIContext().get("invType")!=null){
			String invTypeStr = (String)getUIContext().get("invType");
			if(invTypeStr!=null){
				if(invTypeStr.equals("收据"))
					invType = ChequeTypeEnum.receipt;
				else if(invTypeStr.equals("发票"))
					invType = ChequeTypeEnum.invoice;
			}
		}
		actionConfirm.setEnabled(true);
		actionCancel.setEnabled(true);

		contPrmt.setVisible(true);
		contTxt.setVisible(false);		
		
/*		if (setting.getMarkInvoice() == 32) { 		非票据统一管理的情况下，换票没有什么意义啊
			contPrmt.setVisible(true);
			contTxt.setVisible(false);
		} else {
			contPrmt.setVisible(false);
			contTxt.setVisible(true);
		}*/
		
		EntityViewInfo view = new EntityViewInfo();
		
		FilterInfo filter = new FilterInfo();
		if(ChequeTypeEnum.invoice.equals(invType)){
			filter.getFilterItems().add(new FilterItemInfo("cheque.chequeType", ChequeTypeEnum.INVOICE_VALUE));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("cheque.chequeType", ChequeTypeEnum.RECEIPT_VALUE));
		}
		filter.getFilterItems().add(new FilterItemInfo("status", new Integer(ChequeStatusEnum.PICKED_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("cheque.keepOrgUnit.id", SysContext.getSysContext().getCurrentOrgUnit().getId()));
		filter.getFilterItems().add(new FilterItemInfo("cheque.keeper.id", SysContext.getSysContext().getCurrentUserInfo().getId()));
		
/*		String spIdsStr = (String)getUIContext().get("SellProIdsStr");
		if(spIdsStr!=null && !spIdsStr.equals(""))*/
			
			
		view.setFilter(filter);
		prmtNewInvoice.setEntityViewInfo(view);
		prmtNewInvoice.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ChequeDetailEntryQuery");
		SHEHelper.setTextFormat(tntAmount);
		
		this.setUITitle("开换票据");
	}

	/**
	 * output actionConfirm_actionPerformed
	 */
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		verifyDate();
		
		ArrayList invIDs = (ArrayList) getUIContext().get("invIDs");
		if(invIDs==null) return;		

		String  oldInvoice = null;
		String chequePK = null;
		String newInvoice = null;
		// 原发票号
		if (txtOldInvoice.getText() != null) {
			oldInvoice = txtOldInvoice.getText();
		}
		// 新发票号
		if (txtNewInvoice.getText() != null
				&& !txtNewInvoice.getText().trim().equals("")) {
			newInvoice = txtNewInvoice.getText();
		} else if (prmtNewInvoice.getValue() != null) {
			chequePK = ((ChequeDetailEntryInfo) prmtNewInvoice.getValue()).getId()
					.toString();
			newInvoice = ((ChequeDetailEntryInfo) prmtNewInvoice.getValue())
					.getNumber();
		}
		String desc = this.txtDescription.getText();
		//旧票据ID，新票据ID
		ChequeDetailEntryFactory.getRemoteInstance().changeInvoice(invIDs, chequePK,desc);
		if(ChequeTypeEnum.invoice.equals(invType)){
			if (FDCStringHelper.isEmpty(oldInvoice)) {
				MsgBox.showInfo("开发票成功，发票号为" + newInvoice);
			} else {
				MsgBox.showInfo("成功将发票" + oldInvoice + "换为" + newInvoice);
			}
		}else{
			if (FDCStringHelper.isEmpty(oldInvoice)) {
				MsgBox.showInfo("开收据成功，收据号为" + newInvoice);
			} else {
				MsgBox.showInfo("成功将收据" + oldInvoice + "换为" + newInvoice);
			}
		}
		getUIWindow().close();
	
	}

	/**
	 * 取消
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		getUIWindow().close();
	}

	/**
	 * 校验
	 */
	private void verifyDate() {
		if ((txtNewInvoice.getText() == null || txtNewInvoice.getText().trim()
				.equals(""))
				&& prmtNewInvoice.getValue() == null) {
			MsgBox.showWarning(this, "必须填写票据号才可换票!");
			SysUtil.abort();
		}
	}

	/**
	 * 从收款单ID读取页面默认值
	 * 
	 * @param invID
	 * @param isPrmtSet
	 * @throws Exception
	 */
	private void setDefaultFeilds() throws Exception {
		ArrayList invIDs = (ArrayList) getUIContext().get("invIDs");
		if(invIDs==null || invIDs.size()==0) return;
		
		String idsStr = "";
		for (int i = 0; i < invIDs.size(); i++) {
			idsStr += ",'"+ invIDs.get(i) +"'";
		}
		if(!idsStr.equals("")) idsStr = idsStr.substring(1);
		
		prmtRoom.setEnabledMultiSelection(true);
		prmtCustomer.setEnabledMultiSelection(true);
		
		
		ChequeDetailEntryCollection entryColl = ChequeDetailEntryFactory.getRemoteInstance()
				.getChequeDetailEntryCollection("select id,number,writtenOffTime,amount,room.id,room.name,room.number " +
						",customerEntry.*,customerEntry.customer.* where id in ("+ idsStr +") ");
		if(entryColl.size()==0) return;
		//客户必须一致，房间必须一致
		RoomInfo oldRoomInfo = null;
		String oldCustIdsStr = null;
		String oldCustNamseStr = "";
		String oldInvoceNumbers = "";
		BigDecimal oldAmount = new BigDecimal("0");
		for (int i = 0; i < entryColl.size(); i++) {
			ChequeDetailEntryInfo info = entryColl.get(i);
			
			if(i==0)
				oldRoomInfo = info.getRoom();
			else if( (info.getRoom()==null && oldRoomInfo!=null) || (info.getRoom()!=null && oldRoomInfo ==null)   
					|| (info.getRoom()!=null && oldRoomInfo!=null && !info.getRoom().getId().equals(oldRoomInfo.getId()))	) {
				FDCMsgBox.showWarning("多张旧票据的房间不一致，请检查！");
				this.abort();
			}

			String newCustIdsStr = "";
			for (int j = 0; j < info.getCustomerEntry().size(); j++) {
				newCustIdsStr += ","+info.getCustomerEntry().get(j).getCustomer().getId().toString();
				oldCustNamseStr += ","+info.getCustomerEntry().get(j).getCustomer().getName();
			}				
			if(!newCustIdsStr.equals(""))  newCustIdsStr = newCustIdsStr.substring(1);
			if(oldCustIdsStr==null) {
				oldCustIdsStr = newCustIdsStr;
			}else if(!oldCustIdsStr.equals(newCustIdsStr)){
				FDCMsgBox.showWarning("多张旧票据的客户不一致，请检查！");
				this.abort();
			}	
			
			oldInvoceNumbers += "," + entryColl.get(i).getNumber();
			oldAmount = oldAmount.add(entryColl.get(i).getAmount());
		}
		
		// 房间
		prmtRoom.setValue(entryColl.get(0).getRoom());
		// 客户
		ChequeCustomerEntryCollection  customerColl = entryColl.get(0).getCustomerEntry();
		Object[] object = new Object[customerColl.size()];
		for(int i = 0 ; i <customerColl.size();i++){
			object[i] = customerColl.get(i).getCustomer();
		}
		prmtCustomer.setValue(object);
		// 原发票号
		txtOldInvoice.setText(oldInvoceNumbers.equals("")?"":oldInvoceNumbers.substring(1));
		// 原发票日期
		pkDate.setValue(entryColl.get(0).getWrittenOffTime());
		// 金额
		tntAmount.setValue(oldAmount);	
		
	}

	

}