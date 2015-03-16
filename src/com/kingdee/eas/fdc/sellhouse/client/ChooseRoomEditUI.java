/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomCollection;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomCusEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomFactory;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomInfo;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomStateEnum;
import com.kingdee.eas.fdc.sellhouse.CluesManageFactory;
import com.kingdee.eas.fdc.sellhouse.IChooseRoom;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo;
import com.kingdee.eas.fdc.tenancy.client.TenancyBillDataProvider;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
import com.kingdee.util.UuidException;
import com.lowagie.text.List;

/**
 * output class name
 */
public class ChooseRoomEditUI extends AbstractChooseRoomEditUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6616637158396759525L;
	private static final Logger logger = CoreUIObject.getLogger(ChooseRoomEditUI.class);
	Map orgMap = FDCSysContext.getInstance().getOrgMap();
	ChooseRoomInfo chooseRoom = null;;
    /**
     * output class constructor
     */
    public ChooseRoomEditUI() throws Exception
    {
        super();
    }
    
 
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
    	if(OprtState.VIEW.equals(this.getOprtState())){
    		try {
				this.editData  = ((IChooseRoom)this.getBizInterface()).getChooseRoomInfo(this.getUIContext().get(UIContext.ID).toString());
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				logger.warn("编辑状态下加载选房信息失败！"+e.getMessage());
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				logger.warn("编辑状态下加载选房信息失败！"+e.getMessage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.warn("编辑状态下加载选房信息失败！"+e.getMessage());
			}
    		
    	}
        super.storeFields();
    	if (null != this.getUIContext().get(UIContext.ID)) {
        this.editData.setId(BOSUuid.read(this.getUIContext().get(UIContext.ID).toString()));
    	}
    	if(null!=this.getUIContext().get("room")){
    	RoomInfo room=null;
		try {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("building.sellProject.orgUnit.*");
			selector.add("building.*");
			selector.add("building.sellProject.*");
			selector.add("*");
			room = (RoomInfo)RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(this.getUIContext().get("room").toString())),selector);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			logger.warn("房间信息加载失败！"+e.getMessage());
			
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			logger.warn("房间信息加载失败！"+e.getMessage());
		} catch (UuidException e) {
			// TODO Auto-generated catch block
			logger.warn("房间信息加载失败！"+e.getMessage());
		}
		SellProjectInfo sellPro = null;
		FullOrgUnitInfo orgU = null;
		sellPro = room.getBuilding().getSellProject();
		orgU = room.getBuilding().getSellProject().getOrgUnit();
		sellPro.setOrgUnit(orgU);
    	this.editData.getRoom().getBuilding().setSellProject(sellPro);
    	}
    	
    	//预设初始
		String cusStr = "";
		this.editData.setCusStr(cusStr);
		this.editData.getCustomerEntry().clear();
		if(this.prmtCustomerEntry.getValue() instanceof Object[]){
			Object[] sheCol = ((Object[])this.prmtCustomerEntry.getValue());
			if(null!=this.prmtCustomerEntry.getValue()
					&&sheCol.length>0){
				
				for(int i = 0 ; i<sheCol.length;i++ ){
					ChooseRoomCusEntryInfo choosRoomCustomer = new ChooseRoomCusEntryInfo();
					SHECustomerInfo sheCustomer = (SHECustomerInfo)sheCol[i];
					sheCustomer.setIsChooseRoom(true);
					choosRoomCustomer.setCustomer(sheCustomer);
		
				this.editData.getCustomerEntry().add(choosRoomCustomer);
				//维护客户名称字段
				 cusStr = this.editData.getCusStr()+sheCustomer.getName()+";";
				this.editData.setCusStr(cusStr);
				}
				
			}
		}
		else if(this.prmtCustomerEntry.getValue() instanceof SHECustomerCollection){
			SHECustomerCollection sheCol = ((SHECustomerCollection)this.prmtCustomerEntry.getValue());
			if(null!=this.prmtCustomerEntry.getValue()
					&&sheCol.size()>0){
				
				for(int i = 0 ; i<sheCol.size();i++ ){
					ChooseRoomCusEntryInfo choosRoomCustomer = new ChooseRoomCusEntryInfo();
					SHECustomerInfo sheCustomer = (SHECustomerInfo)sheCol.get(i);
					sheCustomer.setIsChooseRoom(true);
					choosRoomCustomer.setCustomer(sheCustomer);
		
					this.editData.getCustomerEntry().add(choosRoomCustomer);
					//维护客户名称字段
					cusStr = this.editData.getCusStr()+sheCustomer.getName()+";";
					this.editData.setCusStr(cusStr);
				}
				
			}
		}
    }
    
    
    public void loadFields()
    {
        super.loadFields();
        if(OprtState.VIEW.equals(this.getOprtState())){
//        	int i = this.editData.getChooseRoomNo();
//        	if(i >=0){
//				if(i <10){
//				this.txtNo.setText("0"+i);
//				}else{
//					this.txtNo.setText(String.valueOf(i));
//				}
//			}else{
//				this.txtNo.setText("01");
//			}
		
        	
        	SHECustomerCollection sheCusCol = new SHECustomerCollection();
    		if(null!=this.editData.getCustomerEntry()&&this.editData.getCustomerEntry().size()>0){
    			for(int j = 0 ; j<this.editData.getCustomerEntry().size();j++){
    				sheCusCol.add(this.editData.getCustomerEntry().get(j).getCustomer());
    			}
    			this.prmtCustomerEntry.setValue(sheCusCol.toArray());
    		}
        }
    }
    public void onLoad() throws Exception {
    	

    	this.btnCancelChooseRoom.setVisible(false);
    	KDMenuItem menuItem1 = new KDMenuItem();
		menuItem1.setAction(this.actionPrintChooseRoomInform);
		menuItem1.setText("打印选房通知书");
    	this.btnButtonCol.addAssistMenuItem(menuItem1);
    	KDMenuItem menuItem2 = new KDMenuItem();
		menuItem2.setAction(this.actionTransPrePurchase);
		menuItem2.setText("转预定");
    	this.btnButtonCol.addAssistMenuItem(menuItem2);
    	KDMenuItem menuItem3 = new KDMenuItem();
		menuItem3.setAction(this.actionTransPurchase);
		menuItem3.setText("转认购");
    	this.btnButtonCol.addAssistMenuItem(menuItem3);
    	KDMenuItem menuItem4 = new KDMenuItem();
		menuItem4.setAction(this.actionTransSign);
		menuItem4.setText("转签约");
    	this.btnButtonCol.addAssistMenuItem(menuItem4);
//    	initF7();
		super.onLoad();
		//项目下选房排号
		this.txtNo.setEditable(true);
		RoomInfo room=null;
		SelectorItemCollection selecRoom = new SelectorItemCollection();
		selecRoom.add("building.sellProject.*");
		selecRoom.add("*");
		try {
			room = (RoomInfo)RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(this.getUIContext().get("room").toString())),selecRoom);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			logger.warn("房间信息加载失败！"+e.getMessage());
			
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			logger.warn("房间信息加载失败！"+e.getMessage());
		} catch (UuidException e) {
			// TODO Auto-generated catch block
			logger.warn("房间信息加载失败！"+e.getMessage());
		}
		if(OprtState.ADDNEW.equals(this.getOprtState())){
	        	
	        
//	    		EntityViewInfo view = new EntityViewInfo();
//	    		FilterInfo filter = new FilterInfo();
//	    		SelectorItemCollection selec = new SelectorItemCollection();
//	    		selec.add("chooseRoomNo");
//	    		filter.getFilterItems().add(new FilterItemInfo("project.id",room.getBuilding().getSellProject().getId()));
//	    		view.setFilter(filter);
//	    		SorterItemInfo sortInfo = new SorterItemInfo("chooseRoomNo");
//				 sortInfo.setSortType(SortType.DESCEND);
//				 view.getSorter().add(sortInfo);
//	    		
//	    		ChooseRoomCollection chooseRoomCol = ((IChooseRoom)this.getBizInterface()).getChooseRoomCollection(view);
//	    		if(null!=chooseRoomCol&&chooseRoomCol.size()>0 ){
//	    			if(chooseRoomCol.get(0).getChooseRoomNo()>=0){
//	    				if(chooseRoomCol.get(0).getChooseRoomNo()<10){
//	    				this.txtNo.setText("0"+(chooseRoomCol.get(0).getChooseRoomNo()+1));
//	    				}else{
//	    					this.txtNo.setText(String.valueOf(chooseRoomCol.get(0).getChooseRoomNo()+1));
//	    				}
//	    			}else{
//	    				this.txtNo.setText("01");
//	    			}
//	    		}else{
//	    			this.txtNo.setText("01");
//	    		}
	    		
	    		
	    		//选房日期
//	    		this.prmtSincerityPurchase.setEntityViewInfo(NewCommerceHelper.
//	    				getPermitCustomerView(room.getBuilding().getSellProject(), (UserInfo)this.prmtSalesMan.getValue(), false, true));
	    		this.pkBizDate.setValue(new Date());
	    		EntityViewInfo view = new EntityViewInfo();
	    	FilterInfo filterInfo =new  FilterInfo();
	    	//过滤 
	    	if(null!= this.prmtSalesMan.getValue()){
	    		filterInfo.getFilterItems().add(new FilterItemInfo("propertyConsultant.id",((UserInfo) this.prmtSalesMan.getValue()).getId().toString()));
//	    		filterInfo.getFilterItems().add(new FilterItemInfo("creator.id",((UserInfo) this.prmtSalesMan.getValue()).getId().toString()));
//	    		filterInfo.getFilterItems().add(new FilterItemInfo("lastUpdateUser.id",((UserInfo) this.prmtSalesMan.getValue()).getId().toString()));
	    		
//				filterInfo.setMaskString("#0 or #1 or #2");
	    	}
	    	view.setFilter(filterInfo);
	    	this.prmtSincerityPurchase.setEntityViewInfo(view);
//	    		NewCommerceHelper.getPermitCustomerView((UserInfo)this.prmtSalesMan.getValue(),true);
	        }
		
		if (null != this.getUIContext().get(UIContext.ID)) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("chooseRoomStateEnum");
			chooseRoom = ((IChooseRoom) getBizInterface()).getChooseRoomInfo(new ObjectUuidPK(BOSUuid.read(this.getUIContext().get(UIContext.ID).toString())), selector);
		}
		
		
		
		//选房人，选房电话状态
		verifySinPurAndCustomer();
		
		//不用销售组织判断,改为售楼组织
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
	
			this.actionAffirm.setVisible(false);
			this.actionAffirm.setEnabled(false);

			this.actionCancelChooseRoom.setVisible(true);
			this.actionCancelChooseRoom.setEnabled(true);

			this.actionPrintChooseRoomInform.setEnabled(true);

			this.actionTransPurchase.setEnabled(false);

			this.actionTransSign.setEnabled(false);

			this.actionTransPrePurchase.setEnabled(false);
		}
		this.prmtSalesMan.setEntityViewInfo(NewCommerceHelper.getPermitSalemanView(room.getBuilding().getSellProject(),false));
//		this.prmtSalesMan.setValue(SysContext.getSysContext().getCurrentUserInfo());
		
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filterInfo =new  FilterInfo();
//	//过滤 
//	if(null!= this.prmtSalesMan.getValue()){
//		filterInfo.getFilterItems().add(new FilterItemInfo("propertyConsultant.id",((UserInfo) this.prmtSalesMan.getValue()).getId().toString()));
//		filterInfo.getFilterItems().add(new FilterItemInfo("creator.id",((UserInfo) this.prmtSalesMan.getValue()).getId().toString()));
//		filterInfo.getFilterItems().add(new FilterItemInfo("lastUpdateUser.id",((UserInfo) this.prmtSalesMan.getValue()).getId().toString()));
//	
//		filterInfo.setMaskString("#0 or #1 or #2");
//	}
//	view.setFilter(filterInfo);
		this.prmtCustomerEntry.setEntityViewInfo(NewCommerceHelper.getPermitCustomerView(SHEManageHelper.getParentSellProject(null,room.getBuilding().getSellProject()),SysContext.getSysContext().getCurrentUserInfo()));
		
		if (null != chooseRoom) {
			if (OprtState.VIEW.equals(this.getOprtState()) && ChooseRoomStateEnum.Affirm.equals(chooseRoom.getChooseRoomStateEnum())) {
			
			
//			
				
				this.actionAffirm.setVisible(false);
				this.actionAffirm.setEnabled(false);
				this.actionSubmit.setVisible(false);
				this.actionSubmit.setEnabled(false);

			
				this.actionCancelChooseRoom.setVisible(true);
				this.actionCancelChooseRoom.setEnabled(true);

			
				this.actionPrintChooseRoomInform.setVisible(true);
				this.actionPrintChooseRoomInform.setEnabled(true);

		
				this.actionTransPurchase.setVisible(true);
				this.actionTransPurchase.setEnabled(true);

			
				this.actionTransSign.setVisible(true);
				this.actionTransSign.setEnabled(true);

			
				this.actionTransPrePurchase.setVisible(true);
				this.actionTransPrePurchase.setEnabled(true);
			}
		} else if (OprtState.ADDNEW.equals(this.getOprtState())) {
		
			this.actionAffirm.setVisible(true);
			this.actionAffirm.setEnabled(true);

			this.actionCancelChooseRoom.setVisible(false);
			this.actionCancelChooseRoom.setEnabled(false);

			this.actionPrintChooseRoomInform.setEnabled(false);

			this.actionTransPurchase.setEnabled(false);

			this.actionTransSign.setEnabled(false);
			this.actionTransPrePurchase.setEnabled(false);
		} else {
		
			this.actionAffirm.setVisible(false);
			this.actionAffirm.setEnabled(false);
			this.actionCancelChooseRoom.setVisible(true);
			this.actionCancelChooseRoom.setEnabled(true);

			this.actionPrintChooseRoomInform.setEnabled(true);

			this.actionTransPurchase.setEnabled(false);
			this.actionTransSign.setEnabled(false);

			this.actionTransPrePurchase.setEnabled(false);
		}
		
		//跟据房屋过滤预约单
		
		if(	null!=this.getUIContext().get("room")){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("room.id",this.getUIContext().get("room").toString()));
			view.setFilter(filter);
//			view.getSelector().add("*");
//			view.getSelector().add("customer.customer.*");
//			view.getSelector().add("customer.*");
			this.prmtSincerityPurchase.setEntityViewInfo(view);
		}else{
			FDCMsgBox.showError("未获得房间ID信息,请选择房间！");
			SysUtil.abort();
		}
    }
    public void onShow() throws Exception {
    	super.onShow();
    	if (null != chooseRoom) {
			if (OprtState.VIEW.equals(this.getOprtState()) && ChooseRoomStateEnum.Affirm.equals(chooseRoom.getChooseRoomStateEnum())) {
			
				//置灰
				this.txtNumber.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.txtNumber.setEnabled(false);
				this.txtChooser.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				
				this.txtChooser.setEnabled(false);
				this.prmtCustomerEntry.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.prmtCustomerEntry.setEnabled(false);
				this.txtLinkPhone.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.txtLinkPhone.setEnabled(false);
				this.prmtSincerityPurchase.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.prmtSincerityPurchase.setEnabled(false);
				this.prmtSalesMan.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.prmtSalesMan.setEnabled(false);
			}
    	}
    }
	
//    public void initF7(){
////    	SelectorItemCollection sel = new SelectorItemCollection();
////    	sel.add("salesman.*");
////    	sel.add("*");
////    	sel.add("customer.customer.*");
////    	sel.add("customer.id");
////    	sel.add("customer.customer.*");
////    	EntityViewInfo view = new EntityViewInfo();
////    	FilterInfo filterInfo =new  FilterInfo();
////    	//过滤 
////    	if(null!= this.prmtSalesMan.getValue()){
////    		filterInfo.getFilterItems().add(new FilterItemInfo("propertyConsultant.id",((UserInfo) this.prmtSalesMan.getValue()).getId().toString()));
////    		filterInfo.getFilterItems().add(new FilterItemInfo("creator.id",((UserInfo) this.prmtSalesMan.getValue()).getId().toString()));
////    		filterInfo.getFilterItems().add(new FilterItemInfo("lastUpdateUser.id",((UserInfo) this.prmtSalesMan.getValue()).getId().toString()));
////    		
////			filterInfo.setMaskString("#0 or #1 or #2");
////    	}
////    	view.setFilter(filterInfo);
////    	filterInfo.getFilterItems().add(new FilterItemInfo("",""));
//    	
//    	this.prmtSincerityPurchase.setEntityViewInfo(NewCommerceHelper.getPermitCustomerView(, (UserInfo)this.prmtSalesMan.getValue(), false, true));
//    }
    
    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
    SelectorItemCollection selector = 	super.getSelectors();
    selector.add("room");
    selector.add("chooseRoomStateEnum");
    selector.add("*");
    selector.add("customerEntry.*");
    selector.add("customerEntry.customer.*");
    selector.add("cluesCus.*");
    return selector;
    }

    /**
     * output btnAddLine_actionPerformed method
     */
    protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnAddLine_actionPerformed(e);
    }

    /**
     * output menuItemEnterToNextRow_itemStateChanged method
     */
    protected void menuItemEnterToNextRow_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.menuItemEnterToNextRow_itemStateChanged(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {

        
        super.actionSubmit_actionPerformed(e);
//        if(OprtState.ADDNEW.equals(this.getOprtState())){
//        	
//        	this.btnWCancelChooseRoom.setVisible(true);
//        	this.btnWCancelChooseRoom.setEnabled(true);
//        	this.btnWCancelChooseRoom.setVisible(true);
//        	this.btnWCancelChooseRoom.setEnabled(true);
//        	
//        	
//        	this.btnWAffirm.setVisible(false);
//        	this.btnWAffirm.setEnabled(false);
//        	this.actionAffirm.setVisible(false);
//        	this.actionAffirm.setEnabled(false);
//        	
//        	this.btnWCancelChooseRoom.setVisible(true);
//        	this.btnWCancelChooseRoom.setEnabled(true);
//        	this.actionCancelChooseRoom.setVisible(true);
//        	this.actionCancelChooseRoom.setEnabled(true);
//        	
//        	
//        	this.btnWPrintInform.setEnabled(true);
//        
//        	this.actionPrintChooseRoomInform.setEnabled(true);
//        	
//        	
//        	this.btnWTransPurchase.setEnabled(true);
//        	
//        	this.actionTransPurchase.setEnabled(true);
//        	
//        
//        	this.btnWTransSign.setEnabled(true);
//        
//        	this.actionTransSign.setEnabled(true);
//        	
//        
//        	this.btnTransPrePurchase.setEnabled(true);
//        
//        	this.actionTransPrePurchase.setEnabled(true);
//        }
       
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddLine_actionPerformed(e);
    }

    /**
     * output actionCopyLine_actionPerformed
     */
    public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyLine_actionPerformed(e);
    }

    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionInsertLine_actionPerformed(e);
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoveLine_actionPerformed(e);
    }

    /**
     * output actionCreateFrom_actionPerformed
     */
    public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateFrom_actionPerformed(e);
    }

    /**
     * output actionCopyFrom_actionPerformed
     */
    public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyFrom_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionViewSubmitProccess_actionPerformed
     */
    public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSubmitProccess_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionSendingMessage_actionPerformed
     */
    public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendingMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actionViewSignature_actionPerformed
     */
    public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSignature_actionPerformed(e);
    }

    /**
     * output actionSendMail_actionPerformed
     */
    public void actionSendMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMail_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
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

    /**
     * output actionAttamentCtrl_actionPerformed
     */
    public void actionAttamentCtrl_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttamentCtrl_actionPerformed(e);
    }

    /**
     * output actionCancelChooseRoom_actionPerformed
     */
    public void actionCancelChooseRoom_actionPerformed(ActionEvent e) throws Exception
    {	if(!(ChooseRoomStateEnum.Affirm.equals(this.editData.getChooseRoomStateEnum()))){
    		FDCMsgBox.showWarning("请选择选房确认状态下的单据，进行取消选房操作!");
    		SysUtil.abort();
    	}
        super.actionCancelChooseRoom_actionPerformed(e);
        if(null!=this.editData.getId()){
        	((IChooseRoom)this.getBizInterface()).cancelChooseRoom(this.editData.getId().toString());
        	FDCMsgBox.showWarning("已成功取消选房！");
        }else{
        	FDCMsgBox.showWarning("不存在本单据!");
        	SysUtil.abort();
        }
       this.editData=null;
//       this.storeFields();
       this.initOldData(this.editData);
       super.actionAddNew_actionPerformed(e);
//     
//       this.destroyWindow();
    }
 
    /**
     * output actionPrintChooseRoomInform_actionPerformed
     */
    public void actionPrintChooseRoomInform_actionPerformed(ActionEvent e) throws Exception
    {
    	ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
			"cantPrint"));
			return;
		}
		ChooseRoomDataProvider data = new ChooseRoomDataProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
    }

    /**
     * output actionTransPrePurchase_actionPerformed
     */
    public void actionTransPrePurchase_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.editData.getId()!=null){
    		ArrayList customer=new ArrayList();
    		for(int i=0;i<this.editData.getCustomerEntry().size();i++){
    			customer.add(this.editData.getCustomerEntry().get(i).getCustomer());
    		}
    		SHEManageHelper.toTransactionBill(this.editData.getId(),this.editData.getRoom().getId(), this,PrePurchaseManageEditUI.class.getName(), customer);
    	}
//    	
//    	
//        super.actionTransPrePurchase_actionPerformed(e);
//        if(null!=this.editData.getId()){
//        	((IChooseRoom)this.getBizInterface()).updateTrans(this.editData.getId().toString(), ChooseRoomStateEnum.TransPrePur);
//        }else{
//        	FDCMsgBox.showWarning("不存在本单据!");
//        	SysUtil.abort();
//        }
       
    }

    /**
     * output actionTransPurchase_actionPerformed
     */
    public void actionTransPurchase_actionPerformed(ActionEvent e) throws Exception
    {
    	
    	if(this.editData.getId()!=null){
    		ArrayList customer=new ArrayList();
    		for(int i=0;i<this.editData.getCustomerEntry().size();i++){
    			customer.add(this.editData.getCustomerEntry().get(i).getCustomer());
    		}
    		SHEManageHelper.toTransactionBill(this.editData.getId(),this.editData.getRoom().getId(), this,PurchaseManageEditUI.class.getName(), customer);
    	}
//        super.actionTransPurchase_actionPerformed(e);
//        if(null!=this.editData.getId()){
//        	((IChooseRoom)this.getBizInterface()).updateTrans(this.editData.getId().toString(), ChooseRoomStateEnum.TransPurchase);
//        }else{
//        	FDCMsgBox.showWarning("不存在本单据!");
//        	SysUtil.abort();
//        }
    }

    /**
     * output actionTransSign_actionPerformed
     */
    public void actionTransSign_actionPerformed(ActionEvent e) throws Exception
    {
    	
    	if(this.editData.getId()!=null){
    		ArrayList customer=new ArrayList();
    		for(int i=0;i<this.editData.getCustomerEntry().size();i++){
    			customer.add(this.editData.getCustomerEntry().get(i).getCustomer());
    		}
    		SHEManageHelper.toTransactionBill(this.editData.getId(),this.editData.getRoom().getId(), this,SignManageEditUI.class.getName(), customer);
    	}
//        super.actionTransSign_actionPerformed(e);
//        if(null!=this.editData.getId()){
//        	((IChooseRoom)this.getBizInterface()).updateTrans(this.editData.getId().toString(), ChooseRoomStateEnum.TransSign);
//        }else{
//        	FDCMsgBox.showWarning("不存在本单据!");
//        	SysUtil.abort();
//        }
    }

	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return ChooseRoomFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return null;
	}

	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return this.txtNumber;
	}
	protected IObjectValue createNewData() {
		// TODO Auto-generated method stub
		
		ChooseRoomInfo chooseRoom = new ChooseRoomInfo();
		
		
		if(null!=this.getUIContext().get(UIContext.ID)){
			chooseRoom.setId(BOSUuid.read(this.getUIContext().get(UIContext.ID).toString()));
		}
		if(null!=this.getUIContext().get("room")){
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("*");
			selector.add("building.*");
			RoomInfo room = null;
			try {
				room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(this.getUIContext().get("room").toString())),selector);
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				logger.error("加载房间信息失败----------------------------------------");
				FDCMsgBox.showError("加载房间信息失败!");
				SysUtil.abort();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				logger.error("加载房间信息失败----------------------------------------");
				FDCMsgBox.showError("加载房间信息失败!");
				SysUtil.abort();
			} catch (UuidException e) {
				// TODO Auto-generated catch block
				logger.error("加载房间信息失败----------------------------------------");
				FDCMsgBox.showError("加载房间信息失败!");
				SysUtil.abort();
			}
			if(null!=room){
				chooseRoom.setRoom(room);
			}
			
		}else{
			FDCMsgBox.showError("未得到房间信息,请点击房间!");
			SysUtil.abort();
		}
		return chooseRoom;
	}
	
	protected String getTDFileName() {
		return "/bim/fdc/sellhouse/ChooseRoom";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.ChooseRoomPrintQuery");
		
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
//		if(null==this.prmtSalesMan.getValue()){
//			FDCMsgBox.showWarning("置业顾问不能为空！");
//			SysUtil.abort();
//		}
		
		if(null==this.pkBizDate.getValue()){
			FDCMsgBox.showWarning("业务日期不能为空！");
			SysUtil.abort();
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.txtChooseNo);
//		if(null==this.txtNumber.getText()||("").equals(this.txtNumber.getText())){
//			FDCMsgBox.showWarning("编码不能为空！");
//			SysUtil.abort();
//		}
		
//		if(null==this.txtNo.getText()||("").equals(this.txtNo.getText())){
//			FDCMsgBox.showWarning("选房单号不能为空！");
//			SysUtil.abort();
//		}
//		if(("00").equals(this.txtNo.getText())){
//			FDCMsgBox.showWarning("选房单号不能为00！");
//			SysUtil.abort();
//		}
		//选房人 ，联系电话的校验
//		if(true==this.txtChooser.required&&true==this.txtLinkPhone.required){
//			if(null==this.txtChooser.getText()||("").equals(this.txtChooser.getText())){
//				FDCMsgBox.showWarning("请填入选房人!");
//				SysUtil.abort();
//			}
//			if(null==this.txtLinkPhone.getText()||("").equals(this.txtLinkPhone.getText())){
//				FDCMsgBox.showWarning("请填入联系电话!");
//				SysUtil.abort();
//		}
			
			//这里输入移动电话，加0 什么的，故不做校验
//				else if (this.txtLinkPhone.getText().trim().length() <= 7) {
//				MsgBox.showInfo(this, "联系电话必须输入,且至少需要输入7位以上!");
//				this.abort();
//			}
//		}
		
		
		super.verifyInput(e);

	}
	

    /**
     * output prmtSincerityPurchase_dataChanged method
     */
    protected void prmtSincerityPurchase_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	if(null!=e.getNewValue()){
    			
    		SincerityPurchaseInfo sincerPur = (SincerityPurchaseInfo)this.prmtSincerityPurchase.getValue();
    		
    		SelectorItemCollection selector = new SelectorItemCollection();
    		selector.add("*");
    		selector.add("salesman.*");
    		selector.add("customer.*");
    		selector.add("customer.customer.*");
    		selector.add("cusStr");
    		SincerityPurchaseInfo tempSincerPur = SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(BOSUuid.read(sincerPur.getId().toString())), selector);
    		if(tempSincerPur!=null){
    			sincerPur = tempSincerPur;
    		}
    		SHECustomerCollection sheCusCol = new SHECustomerCollection();
    	
    		if(null!=sincerPur.getCustomer()&&sincerPur.getCustomer().size()>0){
    				for(int i = 0; i<sincerPur.getCustomer().size() ; i++){
    					SincerityPurchaseCustomerEntryInfo sinPurEntryInfo = sincerPur.getCustomer().get(i);
    					if(null!=sinPurEntryInfo.getCustomer()){
    						sheCusCol.add(sinPurEntryInfo.getCustomer());
    					}
    				}
    			}
    		
    		if(null!=sheCusCol&&sheCusCol.size()>0){
    			this.prmtCustomerEntry.setValue(sheCusCol.toArray());
    			this.prmtCustomerEntry.setText(tempSincerPur.getCustomerNames());
    		}
    			this.txtChooser.setText(sincerPur.getAppointmentPeople());
    			this.txtLinkPhone.setText(sincerPur.getAppointmentPhone());
    			this.prmtSalesMan.setValue(sincerPur.getSalesman());
    	}
    	verifySinPurAndCustomer();
    	super.prmtSincerityPurchase_dataChanged(e);
    }
    
    /**
     * output prmtCustomerEntry_dataChanged method
     */
    protected void prmtCustomerEntry_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    
    		if(this.prmtCustomerEntry.getValue() instanceof Object[]){
    			Object[] obj = (Object[])this.prmtCustomerEntry.getValue();
    			if(obj.length>5){
    				FDCMsgBox.showWarning("请最多选择5条客户！");
    				this.prmtCustomerEntry.setValue(null);
    				SysUtil.abort();
    			}
    			
    		}
    		else if(this.prmtCustomerEntry.getValue() instanceof SHECustomerCollection){
    			SHECustomerCollection cusCol = (SHECustomerCollection)this.prmtCustomerEntry.getValue();
    			if(cusCol.size()>3){
    				FDCMsgBox.showWarning("请最多选择3条客户！");
    				SysUtil.abort();
    				this.prmtCustomerEntry.setValue(null);
    			}
    		}
    	
    	
    	verifySinPurAndCustomer();
    }
    
    public void verifySinPurAndCustomer(){
    	Object[] cusObj =null;
    	SHECustomerCollection cusSHE = null;
    	if(this.prmtCustomerEntry.getValue() instanceof Object[]){
    		cusObj = (Object[])this.prmtCustomerEntry.getValue();
//    		 StringBuffer sb = new StringBuffer();
//    	        Object[] obj = (Object[])prmtCustomerEntry.getValue();
//    	        if(obj!=null && obj.length>0){
//    	        	for(int i=0;i<obj.length &&obj[i]!=null;i++){
//    	            	if(i==0)
//    	            		sb.append(""+((SHECustomerInfo)obj[i]).getPhone().toString()+"");
//    	            	else
//    	            		sb.append(";"+((SHECustomerInfo)obj[i]).getPhone().toString()+"");
//    	            }
//    	        }
//    	        this.txtLinkPhone.setText(sb.toString());
    	}else if(this.prmtCustomerEntry.getValue() instanceof SHECustomerCollection){
    		cusSHE = (SHECustomerCollection)this.prmtCustomerEntry.getValue();
    	}
    	if(null==this.prmtSincerityPurchase.getValue()&&
    			(null==cusSHE||cusSHE.size()<=0)&&(null==cusObj||(cusObj.length<=1&&cusObj[0]==null))){
    		this.prmtCustomerEntry.setValue(null);
//    		this.txtChooser.setRequired(true);
//    		this.txtLinkPhone.setRequired(true);
    	}else{
    		this.txtChooser.setRequired(false);
    		this.txtChooser.repaint();
    		
    		this.txtLinkPhone.setRequired(false);
    		this.txtLinkPhone.repaint();
    	}
    	
    	
    }
}