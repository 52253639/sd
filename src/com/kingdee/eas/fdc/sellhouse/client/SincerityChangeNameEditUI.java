/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeRecordEntryTwoInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class SincerityChangeNameEditUI extends 
		AbstractSincerityChangeNameEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(SincerityChangeNameEditUI.class);

	    
	protected UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
	    protected List customer=new ArrayList();
	    protected List newcustomer=new ArrayList();
	
	SincerityPurchaseInfo sinPur = null;
	
	
	public SincerityChangeNameEditUI() throws Exception {
		super();
		
		
	}
	
	public void storeFields() {
		super.storeFields();
	}
	
	
	public void onLoad() throws Exception {
		super.onLoad();
		kDLabelContainer1.setEnabled(false);
		// 为弹出界面设置标题
		setUITitle("排号更名");
		 if(null!= this.getUIContext().get("selectedID")){
	    	   try {
	    		   SelectorItemCollection selector = new SelectorItemCollection();
	    		   selector.add("transactionID");
	    		   selector.add("customer.*");
	    		   selector.add("customer.customer.*");
	    		   selector.add("customer.certificate.*");
	    		   selector.add("salesman.*");
	    		   
	    		   selector.add("appointmentPhone");
	    		   selector.add("appointmentPeople");
	    		   selector.add("cluesCus.*");
	    		   selector.add("cluesCus.sellProject.*");
	    		   selector.add("cusStr");
	    		   selector.add("sellProject.*");
	    		   selector.add("customerPhone");
	    		   selector.add("customerNames");
				 sinPur = (SincerityPurchaseInfo)SincerityPurchaseFactory.getRemoteInstance().
				   getSincerityPurchaseInfo(new ObjectUuidPK(BOSUuid.read(this.getUIContext().get("selectedID").toString())), selector);
			} catch (EASBizException e1) {
				// TODO Auto-generated catch block
				logger.warn("预约排号信息加载失败！"+e1.getMessage());
			} catch (BOSException e1) {
				// TODO Auto-generated catch block
				logger.warn("预约排号信息加载失败！"+e1.getMessage());
			} catch (UuidException e1) {
				// TODO Auto-generated catch block
				logger.warn("预约排号信息加载失败！"+e1.getMessage());
			}
			
	       }
		if(sinPur!=null && null!=this.sinPur.getCustomer()&&this.sinPur.getCustomer().size()>0){
			//原先客户
//			for(int i =0 ; i<this.sinPur.getCustomer().size() ; i++){
//				SHECustomerInfo sheCus = this.sinPur.getCustomer().get(i).getCustomer();
//				if(0==i){
//					
//					this.oldCus1.setText(sheCus.getName());
//					this.oldCus1.setUserObject(sheCus);
//				}else if(1==i){
//					this.oldCus2.setText(sheCus.getName());
//					this.oldCus2.setUserObject(sheCus);
//				}else if(2==i){
//					this.oldCus3.setText(sheCus.getName());
//					this.oldCus3.setUserObject(sheCus);
//				}
//				customer.add(sheCus);
//			}
//			this.contChooser.setEnabled(false);
//			this.txtChooser.setEnabled(false);
//			this.kDlinkCusName.setEnabled(false);
//			this.contChooseRoomPhone.setEnabled(false);
//			this.txtChooseRoomPhone.setEnabled(false);
			this.cluesPhone.setEnabled(false);
			this.txtCluesPhone.setEditable(false);
			
		}
		else{
			if(null!=sinPur.getCluesCus()){
//			this.kDlinkCusName.setText(sinPur.getCluesCus().getName());
//			this.kDlinkCusName.setUserObject(sinPur.getCluesCus());
			this.txtCluesPhone.setText(sinPur.getCluesCus().getPhone());
			}
			this.contChooser.setEnabled(true);
			this.txtChooser.setEnabled(true);
//			this.kDlinkCusName.setEnabled(true);
			this.contChooseRoomPhone.setEnabled(true);
			this.txtChooseRoomPhone.setEnabled(true);
			this.cluesPhone.setEnabled(false);
			this.txtCluesPhone.setEditable(false);
		}
		
		// 
		btnEdit.setEnabled(true);
		btnSave.setEnabled(true);
		
		if (getUIContext().get("selectedID") != null) {
			String selectedID = (String) getUIContext().get("selectedID");
			setDefaultFeilds(selectedID);
		}
		
		// 客户增加按当前用户是销售顾问过滤
		UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
		
		this.f7Operater.setValue(userInfo);
//		this.f7NewCustomer.setEntityViewInfo(CommerceHelper.getPermitCustomerView(
//				userInfo, MoneySysTypeEnum.SalehouseSys));
		loadCustomerEntry(this.sinPur);
		
		this.txtRemark.setMaxLength(255);
		
		if(sinPur.getSincerPriceEntrys().size()>0){
			txtChooser.setEnabled(false);
			txtChooseRoomPhone.setEnabled(false);
		}
	}
	public void loadFields(){
		super.loadFields();
    }
	
	private void setDefaultFeilds(String selectedID) throws Exception{
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("customer.id");
		sic.add("customer.name");
		sic.add("creator.name");
		
		SincerityPurchaseInfo purchaseInfo = SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(selectedID), sic);
		
		if(purchaseInfo != null){
			//原客户
//			f7OldCustomer.setValue(purchaseInfo.getCustomer());
			//操作人
//			f7Operater.setValue(purchaseInfo.getCreator());
			f7Operater.setValue(SysContext.getSysContext().getCurrentUserInfo());
		}
		
		
	}
	

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		if(sinPur.getSincerPriceEntrys().size()>0){
			 if(newcustomer==null||newcustomer.size()<=0){
				  FDCMsgBox.showWarning("客户不能为空！");
				  SysUtil.abort();
			  }
		}
		
		
	    //没有更名信息
		if(null!=newcustomer&& this.newcustomer.size()<=0&&((null==this.txtChooser.getText()|("").equals(this.txtChooser.getText()))&&(null==this.txtChooseRoomPhone.getText()||("").equals(this.txtChooseRoomPhone.getText())))){
			FDCMsgBox.showWarning("请填入更名信息！");
			SysUtil.abort();
		}
		
//		IChangeNameFacade biz = ChangeNameFacadeFactory.getRemoteInstance();
		
		String selectedID = null;
		if (getUIContext().get("selectedID") != null) {
			selectedID = (String) getUIContext().get("selectedID");
		}
//		//得到预约排号单客户信息
//		EntityViewInfo view = new EntityViewInfo ();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("head",selectedID));
//		view.setFilter(filter);
//		SincerityPurchaseCustomerEntryCollection sincerityPurchaseCustomerEntryCol = SincerityPurchaseCustomerEntryFactory.getRemoteInstance().getSincerityPurchaseCustomerEntryCollection(view);
//		String newCustomerID = ((FDCCustomerInfo)f7NewCustomer.getValue()).getId().toString();
		
//		biz.changeName(selectedID, newCustomerID);
		ChangeRecordEntryTwoInfo cretInfo = new ChangeRecordEntryTwoInfo();
		SincerityPurchaseInfo spInfo = sinPur;
		spInfo.setId(BOSUuid.read(selectedID));
		cretInfo.setHead(spInfo);
		
//		SincerityPurchaseCustomerEntryCollection sheCusCol = new SincerityPurchaseCustomerEntryCollection();
//		
//		Iterator ite = this.newcustomer.iterator();
//		while(ite.hasNext()){
//			SincerityPurchaseCustomerEntryInfo entry=new SincerityPurchaseCustomerEntryInfo();
//			SHEManageHelper.setCustomerListEntry(entry, ((TranCustomerEntryInfo)ite.next()));
//			sheCusCol.add(entry);
//		}
		cretInfo.setChangeDate((Date)pkChangeDate.getValue());
		//更名人
		cretInfo.setOperator(SysContext.getSysContext().getCurrentUserInfo());
		cretInfo.setRemark(txtRemark.getText());
		boolean flag = ChangeRecordEntryTwoFactory.getRemoteInstance().changeRecordSave(spInfo, this.txtChooser.getText(), this.txtChooseRoomPhone.getText(),newcustomer,cretInfo);
		
//		ChangeNameOldCustomerEntryInfo colOldInfo = null;
//		ChangeNameNewCustomerEntryInfo colNewInfo = null;
//		String newCusStr = "";
//		//当旧客户为空,填入的预约人信息生成新的线索客户，客户信息成为新客户
//		
//			for(int i = 0 ; i< spInfo.getCustomer().size() ; i++){
//				colOldInfo = new ChangeNameOldCustomerEntryInfo();
//				colOldInfo.setCustomer(spInfo.getCustomer().get(i).getCustomer());
//				//旧客户
//				cretInfo.getOldCustomer().add(colOldInfo);
//			}
//				//得到旧客户字段
//				cretInfo.setOldCusStr(spInfo.getCustomerNames());
//				//得到旧线索客户
//				if(null!=spInfo.getCluesCus()&&this.txtChooser.isEnabled()==true&&this.txtChooseRoomPhone.isEditable()==true){
//					cretInfo.setCluesCus(spInfo.getCluesCus());
//				}
//				
//				
//				
//				if(null!=newcustomer&& this.newcustomer.size()>0){
//					//先情况在更新
//					spInfo.getCustomer().clear();
//					Iterator it = this.newcustomer.iterator();
//					while(it.hasNext()){
//						colNewInfo = new ChangeNameNewCustomerEntryInfo();
//						SHECustomerInfo sheCus = ((TranCustomerEntryInfo)it.next()).getCustomer();
//						colNewInfo.setCustomer(sheCus);
//						colNewInfo.setParent(cretInfo);
//						//新客户
//						IObjectPK srNewId = ChangeNameNewCustomerEntryFactory.getRemoteInstance().submit(colNewInfo);
//						colNewInfo.setId((BOSUuid)srNewId.getKeyValue(null));
//					
//						cretInfo.getNewCustomer().add(colNewInfo);
//						newCusStr = this.sinPur.getCustomerNames();
//						//更新到预约单
//						
//						SincerityPurchaseCustomerEntryInfo cusEntryInfo = new SincerityPurchaseCustomerEntryInfo();
//						cusEntryInfo.setCustomer(sheCus);
//						cusEntryInfo.setHead(spInfo);
//						IObjectPK srId = SincerityPurchaseCustomerEntryFactory.getRemoteInstance().submit(cusEntryInfo);
//						cusEntryInfo.setId((BOSUuid)srId.getKeyValue(null));
//						spInfo.getCustomer().add(cusEntryInfo);
//					}
//					//得到新客户字段
//					cretInfo.setNewCusStr(newCusStr);
//					//预约单更新新客户字段
//					
//					spInfo.setCusStr(newCusStr);
//				}
//			
//				
//				
//		if(this.txtChooser.isEnabled()==true&&this.txtChooseRoomPhone.isEditable()==true){
//				//生成新线索客户
//				if(null!=this.txtChooser.getText()&&(!("").equals(this.txtChooser.getText()))&&null!=this.txtChooseRoomPhone.getText()&&(!("").equals(this.txtChooseRoomPhone.getText()))){//都不为null
//					CluesManageInfo newCluesMa = new CluesManageInfo();
//					newCluesMa.setName(this.txtChooser.getText());
//					newCluesMa.setNumber(this.txtChooser.getText()+this.txtChooseRoomPhone.getText());
//					newCluesMa.setPhone(this.txtChooseRoomPhone.getText());
//					newCluesMa.setSource(CluesSourceEnum.VISIT);
//					newCluesMa.setSellProject(spInfo.getCluesCus().getSellProject());
//					newCluesMa.setPropertyConsultant(spInfo.getSalesman());
//					spInfo.setAppointmentPeople(this.txtChooser.getText());
//					spInfo.setAppointmentPhone(this.txtChooseRoomPhone.getText());
//					IObjectPK newCluId = CluesManageFactory.getRemoteInstance().submit(newCluesMa);
//					newCluesMa.setId((BOSUuid)newCluId.getKeyValue(null));
//					spInfo.setCluesCus(newCluesMa);
//				}
//		}	
//		
//		
//	
////		//当旧客户不为空.客户信息成为新客户
////		else if(null!=spInfo.getCustomer()&&spInfo.getCustomer().size()>0){
////			
////			
////			
////		}
//		
//		
//		SelectorItemCollection sel = new SelectorItemCollection ();
////		sel.add("customer.customer.*");
//		sel.add("customer.*");
//		sel.add("cluesCus.*");
//		sel.add("appointmentPhone");
//		sel.add("appointmentPeople");
//		sel.add("customerPhones");
//		sel.add("customerNames");
//		sel.add("cusStr");
//		//更新预约单
//		SincerityPurchaseFactory.getRemoteInstance().updatePartial(spInfo,sel);
//		
//	
//		cretInfo.getOldCustomer().add(new ChangeNameOldCustomerEntryInfo());//(FDCCustomerInfo)f7OldCustomer.getValue());
//		
////		cretInfo.setOldCustomer((FDCCustomerInfo)f7OldCustomer.getValue());
////		cretInfo.setNewCustomer((FDCCustomerInfo)f7NewCustomer.getValue());
//		cretInfo.setChangeDate((Date)pkChangeDate.getValue());
//		//更名人
//		cretInfo.setOperator(SysContext.getSysContext().getCurrentUserInfo());
//		cretInfo.setRemark(txtRemark.getText());
////		cretInfo.setOperator((UserInfo)f7Operater.getValue());
//		
//		cretInfo.setHead(spInfo);
//		IObjectPK changeRecordId =ChangeRecordEntryTwoFactory.getRemoteInstance().submit(cretInfo);
//		if(null==changeRecordId){
//			FDCMsgBox.showError("更名失败！");
//			this.destroyWindow();
//		}
//		cretInfo.setId(BOSUuid.read(changeRecordId.getKeyValue(null).toString()));
//		
//		//找到后删除之前客户信息
//		for(int s = 0 ; s< sincerityPurchaseCustomerEntryCol.size() ; s++){
//			SincerityPurchaseCustomerEntryFactory.getRemoteInstance().delete(new ObjectUuidPK(sincerityPurchaseCustomerEntryCol.get(s).getId().toString()));
//		}
	
		
		
		//更成后，更新预约单
		// 将新客户写到诚意认购记录，"诚意更名"界面自动关闭
		if(!flag){
			FDCMsgBox.showError("更名失败");

		}
		this.destroyWindow();
	}

	protected IObjectValue createNewData() {
		return null;
	}


	protected ICoreBase getBizInterface() throws Exception {
		return ChangeRecordEntryTwoFactory.getRemoteInstance();
	}
	
	  /**
     * output oldCus1_mouseClicked method
     */
    protected void oldCus1_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
//    	if(null==this.oldCus1.getText()||("").equals(this.oldCus1.getText())){
//    		return;
//    	}
//    	UIContext uiContext = new UIContext(this);
//     	
// 		uiContext.put(UIContext.INIT_DATAOBJECT, new CustomerRptEditUI());
// 		
// 		EntityViewInfo view=CommerceHelper.getPermitCustomerView(user, MoneySysTypeEnum.SalehouseSys);
// 	
// 		uiContext.put("view", view);
// 		
// 		uiContext.put(UIContext.ID,((SHECustomerInfo)this.oldCus1.getUserObject()).getId());
// 		
// 		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
// 				CustomerRptEditUI.class.getName(), uiContext, null, "VIEW");
// 		uiWindow.show();
    	SHEManageHelper.openCustomerInformation(this, (SHECustomerInfo)this.oldCus1.getUserObject());
    }

    /**
     * output oldCus2_mouseClicked method
     */
    protected void oldCus2_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
//    	if(null==this.oldCus2.getText()||("").equals(this.oldCus2.getText())){
//    		return;
//    	}
//    	 UIContext uiContext = new UIContext(this);
//     	
// 		uiContext.put(UIContext.INIT_DATAOBJECT, new CustomerRptEditUI());
// 		
// 		EntityViewInfo view=CommerceHelper.getPermitCustomerView(user, MoneySysTypeEnum.SalehouseSys);
// 	
// 		uiContext.put("view", view);
// 		
// 		uiContext.put(UIContext.ID,((SHECustomerInfo)this.oldCus2.getUserObject()).getId());
// 		
// 		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
// 				CustomerRptEditUI.class.getName(), uiContext, null, "VIEW");
// 		uiWindow.show();
    	SHEManageHelper.openCustomerInformation(this, (SHECustomerInfo)this.oldCus2.getUserObject());
    }

    /**
     * output oldCus3_mouseClicked method
     */
    protected void oldCus3_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
//    	if(null==this.oldCus3.getText()||("").equals(this.oldCus3.getText())){
//    		return;
//    	}
//    	UIContext uiContext = new UIContext(this);
//     	
// 		uiContext.put(UIContext.INIT_DATAOBJECT, new CustomerRptEditUI());
// 		
// 		EntityViewInfo view=CommerceHelper.getPermitCustomerView(user, MoneySysTypeEnum.SalehouseSys);
// 	
// 		uiContext.put("view", view);
// 		
// 		uiContext.put(UIContext.ID,((SHECustomerInfo)this.oldCus3.getUserObject()).getId());
// 		
// 		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
// 				CustomerRptEditUI.class.getName(), uiContext, null, "VIEW");
// 		uiWindow.show();
    	SHEManageHelper.openCustomerInformation(this, (SHECustomerInfo)this.oldCus3.getUserObject());
    }
    /**
     * output newCus5_mouseClicked method
     */
    protected void newCus5_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    	SHEManageHelper.openCustomerInformation(this, (SHECustomerInfo)this.newCus5.getUserObject());
    }

    /**
     * output newCus4_mouseClicked method
     */
    protected void newCus4_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    	SHEManageHelper.openCustomerInformation(this, (SHECustomerInfo)this.newCus4.getUserObject());
    }

    /**
     * output newCus3_mouseClicked method
     */
    protected void newCus3_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
//    	if(null==this.newCus3.getText()||("").equals(this.newCus3.getText())){
//    		return;
//    	}
//    	UIContext uiContext = new UIContext(this);
//     	
// 		uiContext.put(UIContext.INIT_DATAOBJECT, new CustomerRptEditUI());
// 		
// 		EntityViewInfo view=CommerceHelper.getPermitCustomerView(user, MoneySysTypeEnum.SalehouseSys);
// 	
// 		uiContext.put("view", view);
// 		
// 		uiContext.put(UIContext.ID,((SHECustomerInfo)this.newCus3.getUserObject()).getId());
// 		
// 		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
// 				CustomerRptEditUI.class.getName(), uiContext, null, "VIEW");
// 		uiWindow.show();
    	SHEManageHelper.openCustomerInformation(this, (SHECustomerInfo)this.newCus3.getUserObject());
    }

    /**
     * output newCus2_mouseClicked method
     */
    protected void newCus2_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
//    	if(null==this.newCus2.getText()||("").equals(this.newCus2.getText())){
//    		return;
//    	}
//        UIContext uiContext = new UIContext(this);
//    	
//		uiContext.put(UIContext.INIT_DATAOBJECT, new CustomerRptEditUI());
//		
//		EntityViewInfo view=CommerceHelper.getPermitCustomerView(user, MoneySysTypeEnum.SalehouseSys);
//	
//		uiContext.put("view", view);
//		
//		uiContext.put(UIContext.ID,((SHECustomerInfo)this.newCus2.getUserObject()).getId());
//		
//		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
//				CustomerRptEditUI.class.getName(), uiContext, null, "VIEW");
//		uiWindow.show();
    	SHEManageHelper.openCustomerInformation(this, (SHECustomerInfo)this.newCus2.getUserObject());
    }
    /**
     * output oldCus4_mouseClicked method
     */
    protected void oldCus4_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    	SHEManageHelper.openCustomerInformation(this, (SHECustomerInfo)this.oldCus4.getUserObject());
    }

    /**
     * output oldCus5_mouseClicked method
     */
    protected void oldCus5_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    	SHEManageHelper.openCustomerInformation(this, (SHECustomerInfo)this.oldCus5.getUserObject());
    }

    /**
     * output newCus1_mouseClicked method
     */
    protected void newCus1_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
//    	if(null==this.newCus1.getText()||("").equals(this.newCus1.getText())){
//    		return;
//    	}
//    	UIContext uiContext = new UIContext(this);
//    	
//		uiContext.put(UIContext.INIT_DATAOBJECT, new CustomerRptEditUI());
//		
//		EntityViewInfo view=CommerceHelper.getPermitCustomerView(user, MoneySysTypeEnum.SalehouseSys);
//	
//		uiContext.put("view", view);
//		
//		uiContext.put(UIContext.ID,((SHECustomerInfo)this.newCus1.getUserObject()).getId());
//		
//		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
//				CustomerRptEditUI.class.getName(), uiContext, null, "VIEW");
//		uiWindow.show();
    	SHEManageHelper.openCustomerInformation(this, (SHECustomerInfo)this.newCus1.getUserObject());
    }
    
    public void clearCus(){
    	this.newcustomer = new ArrayList();
    	this.newCus1.setText(null);
    	this.newCus1.setUserObject(null);
    	this.newCus2.setText(null);
    	this.newCus2.setUserObject(null);
    	this.newCus3.setText(null);
    	this.newCus3.setUserObject(null);
    	this.newCus4.setText(null);
    	this.newCus4.setUserObject(null);
    	this.newCus5.setText(null);
    	this.newCus5.setUserObject(null);
    	
    }
    /**
     * output btnSelecCus_actionPerformed method
     */
    protected void btnSelecCus_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    
//    	clearCus();
//    	super.btnSelecCus_actionPerformed(e);
//    	UIContext uiContext = new UIContext(this);
//		uiContext.put(UIContext.INIT_DATAOBJECT, new BaseTransactionInfo());
//		
//		EntityViewInfo view=NewCommerceHelper.getPermitCustomerView(user);
//		if(0!=customer.size()){
//			uiContext.put("customer", customer);
//			
//		}
//		uiContext.put("salesMan", this.sinPur.getSalesman());
//		uiContext.put("view", view);
//		
//		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
//				TranCustomerSelectUI.class.getName(), uiContext, null, "VIEW");
//		uiWindow.show();
//		
//		Map cusContext = uiWindow.getUIObject().getUIContext();
//		
//		if(null==cusContext.get(TranCustomerSelectUI.SHE_FILTER_CUSTOMER)){
//			FDCMsgBox.showWarning("未选择到客户！");
//			SysUtil.abort();
//		}
//		this.newcustomer =(List) cusContext.get(TranCustomerSelectUI.SHE_FILTER_CUSTOMER);
		
		
		
//		//页签变化
//		for(int i =0 ; i<newcustomer.size() ; i++){
//			if(0==i){
//				this.newCus1.setText(((SHECustomerInfo)newcustomer.get(i)).getName());
//				this.newCus1.setUserObject((SHECustomerInfo)newcustomer.get(i));
//			}else if(1==i){
//				this.newCus2.setText(((SHECustomerInfo)newcustomer.get(i)).getName());
//				this.newCus2.setUserObject((SHECustomerInfo)newcustomer.get(i));
//			}else if(2==i){
//				this.newCus3.setText(((SHECustomerInfo)newcustomer.get(i)).getName());
//				this.newCus3.setUserObject((SHECustomerInfo)newcustomer.get(i));
//			}
//			
//		}
    	this.newcustomer =SHEManageHelper.customerSelect(this,this.newcustomer,this.sinPur.getSellProject(),false,true);
    	storeCustomerEntry(sinPur);
    	this.loadNewCustomerEntry(sinPur);
    }
    protected void storeCustomerEntry(SincerityPurchaseInfo info) {
    	info.getCustomer().clear();
		setCustomerEntry(info,this.newcustomer);
	}
    protected void loadCustomerEntry(SincerityPurchaseInfo info){
		this.customer=new ArrayList();
		for(int i=0;i<info.getCustomer().size();i++){
			SincerityPurchaseCustomerEntryInfo entry=info.getCustomer().get(i);
			this.customer.add(entry);
		}
		SHEManageHelper.loadCustomer(oldCus1, oldCus2, oldCus3,oldCus4,oldCus5, this.customer, null, info);
		txtCluesPhone.setText(info.getCustomerPhone()); //add by shilei
		txtyuanChooser.setText(info.getAppointmentPeople());
	}
    protected void loadNewCustomerEntry(SincerityPurchaseInfo info){
		this.newcustomer=new ArrayList();
		for(int i=0;i<info.getCustomer().size();i++){
			SincerityPurchaseCustomerEntryInfo entry=info.getCustomer().get(i);
			this.newcustomer.add(entry);
		}
		SHEManageHelper.loadCustomer(newCus1, newCus2, newCus3,newCus4,newCus5, this.newcustomer, null, info);
		
		for(int i=0;i<this.newcustomer.size();i++){
			TranCustomerEntryInfo entry=(TranCustomerEntryInfo)this.newcustomer.get(i);
			if(entry.isIsMain()){
				this.txtChooser.setText(entry.getName());
				this.txtChooseRoomPhone.setText(entry.getPhone());
				this.txtChooser.setEnabled(false);
				this.txtChooseRoomPhone.setEnabled(false);
			}
		}
	}
    private void setCustomerEntry(SincerityPurchaseInfo sign,List customerList){
		String customerNames="";
		String customerPhone="";
		for(int i=0;i<customerList.size();i++){
			SincerityPurchaseCustomerEntryInfo info =new SincerityPurchaseCustomerEntryInfo();
			TranCustomerEntryInfo tranInfo=(TranCustomerEntryInfo)customerList.get(i);
			SHEManageHelper.setCustomerListEntry(info, tranInfo);
			
			String phone="";
			String mob="";
			String tel="";
			if(info.getPhone()!=null&&!info.getPhone().trim().equals("")){
				mob=info.getPhone()+"(M)";
			}
			if(info.getTel()!=null&&!info.getTel().trim().equals("")){
				tel=info.getTel()+"(T)";
			}
			if(!mob.equals("")&&!tel.equals("")){
				phone=mob+","+tel;
			}else if(!mob.equals("")){
				phone=mob;
			}else if(!tel.equals("")){
				phone=tel;
			}
			if(i==customerList.size()-1){
				customerNames=customerNames+info.getName();
				customerPhone=customerPhone+phone;
			}else{
				customerNames=customerNames+info.getName()+";";
				customerPhone=customerPhone+phone+";";
			}
			sign.getCustomer().add(info);
		}
		sign.setCustomerNames(customerNames);
		sign.setCustomerPhone(customerPhone);
	}
}