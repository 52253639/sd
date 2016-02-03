/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.app.DbUtil;

/**
 * output class name
 */
public class ModifyCustomerNameUI extends AbstractModifyCustomerNameUI
{
    private MarketDisplaySetting setting = new MarketDisplaySetting();
	
	private static final Logger logger = CoreUIObject.getLogger(ModifyCustomerNameUI.class);
	
	private static boolean isUpdateName = true;

	/**
     * output class constructor
     */
    public ModifyCustomerNameUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	this.txtName.setMaxLength(80);
    	String actionCommand = (String) this.getUIContext().get("ActionCommand");
    	if(actionCommand!=null) {
    		if(actionCommand.equals("modifyName")) {
    			this.contName.setVisible(true);
    			this.contPhone.setVisible(false);
    			this.setUITitle("客户姓名修改");
    			isUpdateName = true;
    		}else if(actionCommand.equals("modifyPhone")) {
    			this.contName.setVisible(false);
    			this.contPhone.setVisible(true);
    			this.setUITitle("移动电话修改");
    			isUpdateName = false;
    		}    		
    	}
    }
    
	protected IObjectValue createNewData() {
		return new FDCCustomerInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCCustomerFactory.getRemoteInstance();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		/*
		 * if (!FDCCustomerHelper.verifyInputNotNull(this.txtName, "姓名不能为空！") ||
		 * !FDCCustomerHelper.verifyInputNotNull(this.txtPhone, "移动电话不能为空！")) {
		 * this.abort(); }
		 */

		if (isUpdateName) {
			if (!FDCCustomerHelper.verifyInputNotNull(this.txtName, "姓名不能为空！")) {
				this.abort();
			}
		} else {
			if (!FDCCustomerHelper.verifyInputNotNull(this.txtPhone,
					"移动电话不能为空！")) {
				this.abort();
			}
		}
		
		FDCCustomerInfo otherInfo = (FDCCustomerInfo) FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(this.editData.getId())).clone();
		otherInfo.setPhone(this.txtPhone.getText().trim());
		otherInfo.setName(this.txtName.getText().trim());
		//检查用户
		Map rMap = FDCCustomerFactory.getRemoteInstance().verifySave(otherInfo,false);
		
		otherInfo = null;
//		String phone = this.txtPhone.getText().trim();
//		//只有在移动电话变化后才进行重复验证
//		if(!phone.equals(this.editData.getPhone())){
//			if(!FDCCustomerHelper.verifyPhone(phone, this.editData.getId().toString())){
//				this.abort();
//			}
//		}
		if(FDCCustomerHelper.verifyPhoneAndName(rMap,this)){
//			this.txtName.setText(this.editData.getName());
//			this.txtPhone.setText(this.editData.getPhone());
			abort();
		}
		
		

		this.editData.setName(this.txtName.getText());
		this.editData.setPhone(this.txtPhone.getText());
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("name");
		sels.add("phone");
		//---避免使用submit zhicheng_jin 090626
		getBizInterface().updatePartial(this.editData, sels);
		initOldData(this.editData);
		
		updatePurchaseCustomerInfo(this.editData);
		//添加对租赁合同ListUI的客户信息的更新 xin_wang 2010.12.15
		
		if(this.editData.getId()!=null)FDCCustomerFactory.getRemoteInstance().updateTenancyBill(this.editData.getId().toString());

		((ListUI)this.getUIContext().get("ListUI")).refreshList();
		destroyWindow();
	}

	/**
	 * 修改客户名称或电话号码后同步认购单上的客户电话和名称字段信息 、 证件号码
	 * custInfo 不为空时针对某个客户的认购单更新，否则针对整个认购单更新
	 */
	public static void updatePurchaseCustomerInfo(FDCCustomerInfo custInfo) throws BOSException, EASBizException{
		
		String executeSql = "select head.*, customer.name, customer.phone,customer.tel, customer.certificateNumber ";
		if(custInfo!=null) executeSql += "where head.id in (select fheadId from t_she_purchasecustomerinfo where FCustomerID = '"+custInfo.getId().toString()+"') " ;		
		
		PurchaseCustomerInfoCollection purCustColl = PurchaseCustomerInfoFactory.getRemoteInstance().getPurchaseCustomerInfoCollection(executeSql);
		Map purMap = new HashMap();
		for(int i=0;i<purCustColl.size();i++) {
			PurchaseCustomerInfoInfo purCusInfo = purCustColl.get(i);
			PurchaseInfo purInfo = purCusInfo.getHead(); 
			if(!purMap.containsKey(purInfo.getId().toString())) {
				purInfo.setCustomerNames(purCusInfo.getCustomer().getName()+";");
				purInfo.setCustomerPhones(purCusInfo.getCustomer().getPhone()+","+(purCusInfo.getCustomer().getTel()==null?" ":purCusInfo.getCustomer().getTel())+";");
				purInfo.setCustomerIDCards(purCusInfo.getCustomer().getCertificateNumber()+";");
				purMap.put(purInfo.getId().toString(), purInfo);
			}else{
				PurchaseInfo existInfo = (PurchaseInfo)purMap.get(purInfo.getId().toString());
				existInfo.setCustomerNames(existInfo.getCustomerNames() + purCusInfo.getCustomer().getName()+";");
				existInfo.setCustomerPhones(existInfo.getCustomerPhones() + purCusInfo.getCustomer().getPhone()+","+(purCusInfo.getCustomer().getTel()==null?" ":purCusInfo.getCustomer().getTel())+";");
				existInfo.setCustomerIDCards(existInfo.getCustomerIDCards() + purCusInfo.getCustomer().getCertificateNumber()+";");
				purMap.put(purInfo.getId().toString(), existInfo);
			}
		}
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("customerNames");
		sels.add("customerPhones");
		sels.add("customerIDCards");
		Iterator iter = purMap.keySet().iterator();
		while(iter.hasNext()) {
			PurchaseInfo purInfo = (PurchaseInfo)purMap.get(iter.next());
			PurchaseFactory.getRemoteInstance().updatePartial(purInfo, sels);
		}
		
		
		
	

		
	}
	
	/**
	 * 已经抽取工具类
	 * @param rMap
	 * @deprecated 
	 * @see FDCCustomerHelper.verifyPhoneAndName(Map rMap,CoreUI ui)
	 * @author yuanjun_lan
	 */
	
	public void verifyPhoneAndName(Map rMap){
		
		if((rMap.containsKey("dupNameMsg")&&setting.getNameRepeat()==0)&&(rMap.containsKey("dupPhoneMsg")&&setting.getPhoneRepeat()==0)){
			//两个都强制不保存，合并显示
			FDCMsgBox.showConfirm3a(this, "不能保存，请查看详细信息", (String)rMap.get("dupNameMsg")+"\n"+(String)rMap.get("dupPhoneMsg"));
			abort();//强制不保存
		}
		
		if(rMap.containsKey("dupNameMsg")){
			if(setting.getNameRepeat()==2)
				{
					//不提示，直接保存
				}else if(setting.getNameRepeat()==0){
					FDCMsgBox.showConfirm3a(this, "不能保存，请查看详细信息", (String)rMap.get("dupNameMsg"));
					abort();//强制不保存
				}else if(setting.getNameRepeat()==1){
					//给出提示，客户选择保存;
					if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(this, "存在重复的信息，请查看详细信息，确定是否保存？", (String)rMap.get("dupNameMsg"))){
						abort();
					}
				}else{
					abort();
				}
			}
			
			if(rMap.containsKey("dupPhoneMsg")){
				if(setting.getPhoneRepeat()==2)
				{
					//不提示，直接保存
				}else if(setting.getPhoneRepeat()==0){
					FDCMsgBox.showConfirm3a(this, "不能保存，请查看详细信息", (String)rMap.get("dupPhoneMsg"));
					abort();//强制不保存
				}else if(setting.getPhoneRepeat()==1){
					//给出提示，客户选择保存;
					if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(this, "存在重复的信息，请查看详细信息，确定是否保存？", (String)rMap.get("dupPhoneMsg"))){
						abort();
					}
				}else{
					abort();
				}
			}
			if(rMap.containsKey("dupNameAndPhoneMsg")){
				if(setting.getNameAndPhoneRepeat() == 2){
					
				}else if(setting.getNameAndPhoneRepeat()==0){
					FDCMsgBox.showConfirm3a(this, "不能保存，请查看详细信息", (String)rMap.get("dupNameAndPhoneMsg"));
					abort();//强制不保存
				}else if(setting.getNameAndPhoneRepeat()==1){
					//给出提示，客户选择保存;
					if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(this, "存在重复的信息，请查看详细信息，确定是否保存？", (String)rMap.get("dupNameAndPhoneMsg"))){
						abort();
					}
				}else{
					abort();
				}
			}
	}
	
	
	
	
	
}