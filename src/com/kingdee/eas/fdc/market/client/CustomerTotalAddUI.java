/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordEnum;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.CommerceChanceEditUI;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.CustomerEditUI;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseEditUI;
import com.kingdee.eas.fdc.sellhouse.client.TrackRecordEditUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CustomerTotalAddUI extends AbstractCustomerTotalAddUI
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerTotalAddUI.class);
    
    
    CustomerEditUI customerUI = null;
    CommerceChanceEditUI commerceUI = null;
    TrackRecordEditUI trackUI = null;
    
	KDScrollPane custScrollPane = null;
	KDScrollPane commScrollPane = null;
    
    /**
     * output class constructor
     */
    public CustomerTotalAddUI() throws Exception
    {
        super();
    }

	protected IObjectValue createNewData() {
		return new FDCCustomerInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCCustomerFactory.getRemoteInstance();
	}


	public void onLoad() throws Exception {
		super.onLoad();
		
		
	}
	
	
	public void loadFields() {
		super.loadFields();
		
		loadKdTabPanel();
		
		this.actionEdit.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionAddNew.setVisible(false);
	}
    
    
	private void loadKdTabPanel() {			
		String actionCommand = (String)this.getUIContext().get("ActionCommand");
		
		try {
			if(custScrollPane!=null) this.kDTabbedPane.remove(custScrollPane);
			custScrollPane = new KDScrollPane();
			customerUI = (CustomerEditUI)UIFactoryHelper.initUIObject(CustomerEditUI.class.getName(), new UIContext(ui), null, "ADDNEW");
			trackUI = (TrackRecordEditUI)UIFactoryHelper.initUIObject(TrackRecordEditUI.class.getName(), new UIContext(ui), null, "ADDNEW");

			custScrollPane.setViewportView(customerUI);
			custScrollPane.setKeyBoardControl(true);
			
			this.kDTabbedPane.add(custScrollPane, "客户录入");
			
			if(actionCommand!=null && actionCommand.equals("Intency")) {
				if(commScrollPane!=null) this.kDTabbedPane.remove(commScrollPane);
				commScrollPane = new KDScrollPane();
				commerceUI = (CommerceChanceEditUI)UIFactoryHelper.initUIObject(CommerceChanceEditUI.class.getName(), new UIContext(ui), null, "ADDNEW");
				commScrollPane.setViewportView(commerceUI);
				commScrollPane.setKeyBoardControl(true);
				this.kDTabbedPane.add(commScrollPane, "商机录入");
				if(commerceUI!=null) {
					commerceUI.getCustomerPrmt().setRequired(false);
					commerceUI.getCustomerPrmt().setEnabled(false);
					commerceUI.getSellProjectPrmt().setRequired(false);
					commerceUI.getSellProjectPrmt().setEnabled(false);
					commerceUI.getSaleManPrmt().setEnabled(false);		
				}		
				this.removeDataChangeListener(this.commerceUI.getSellProjectPrmt());
				this.removeDataChangeListener(this.commerceUI.getSaleManPrmt());
				this.removeDataChangeListener(this.commerceUI.getCustomerPrmt());
			}
			
		} catch (UIException e) {
			e.printStackTrace();
		}

		
		
		if(trackUI!=null)  {
			trackUI.getCustomerPrmt().setRequired(false);
			trackUI.getCustomerPrmt().setEnabled(false);
			trackUI.getSellProjectPrmt().setRequired(false);
			trackUI.getSellProjectPrmt().setEnabled(false);
			removeDataChangeListener(trackUI.getCustomerPrmt());
			removeDataChangeListener(trackUI.getSellProjectPrmt());
			removeDataChangeListener(trackUI.getCommercePrmt());
			trackUI.getSysTypeCombo().setSelectedItem(MoneySysTypeEnum.SalehouseSys);
			
			if(actionCommand!=null) {
				if(actionCommand.equals("Clew")){
					trackUI.getTrackTypeCombo().setSelectedItem(TrackRecordTypeEnum.Clew);
				}else if(actionCommand.equals("Latency")) {
					trackUI.getTrackTypeCombo().setSelectedItem(TrackRecordTypeEnum.Latency);
				}else if(actionCommand.equals("Intency")) {
					trackUI.getTrackTypeCombo().setSelectedItem(TrackRecordTypeEnum.Intency);
				}	
				trackUI.getTrackResultCombo().setEnabled(false);	
				trackUI.getCommercePrmt().setEnabled(false);
				trackUI.getTrackTypeCombo().setEnabled(false);
			}

		}
		
		
		
		if(customerUI!=null)  {			
			KDTabbedPane custTabPane = customerUI.getKDTabbedPane();
			custTabPane.remove(customerUI.getTblTrackRecord());
			custTabPane.remove(customerUI.getTblShareSeller());
			custTabPane.remove(customerUI.getTblAdapterLog());

			custTabPane.addTab("跟进录入", trackUI);
			custTabPane.setSelectedIndex(1);
		}	
		
	}

	
	
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		FDCCustomerInfo thisCustomer = this.customerUI.getCustomerData();	
		thisCustomer.setId(BOSUuid.create((new FDCCustomerInfo()).getBOSType()));
	
		UserInfo saleMan = (UserInfo)this.customerUI.getSaleManPrmt().getValue();
		SellProjectInfo proInfo = (SellProjectInfo)this.customerUI.getSellProject().getValue();
		this.trackUI.getSellProjectPrmt().setValue(proInfo);
		this.trackUI.getTrackData().setSellProject(proInfo);
		this.trackUI.getSaleManPrmt().setValue(saleMan);
		this.trackUI.getTrackData().setSaleMan(saleMan);
		this.trackUI.getCustomerPrmt().setValue(thisCustomer);

		boolean addCommerce = false;
		String actionCommand = (String)this.getUIContext().get("ActionCommand");
		if(actionCommand!=null && actionCommand.equals("Intency")) {
			addCommerce = true;
		}	
		
		this.customerUI.vertifyInput();
		if(addCommerce) {
			this.commerceUI.getCommerceData().setId(BOSUuid.create((new CommerceChanceInfo()).getBOSType()));
			this.commerceUI.getSellProjectPrmt().setValue(proInfo);
			this.commerceUI.getCommerceData().setSellProject(proInfo);
			this.commerceUI.getSaleManPrmt().setValue(saleMan);
			this.commerceUI.getCommerceData().setSaleMan(saleMan);
			this.commerceUI.getCustomerPrmt().setValue(thisCustomer);
			this.commerceUI.getCommerceData().setFdcCustomer(thisCustomer);
			
			this.commerceUI.vertifyInput();
			this.trackUI.getCommercePrmt().setValue(this.commerceUI.getCommerceData());
			this.trackUI.getTrackData().setCommerceChance(this.commerceUI.getCommerceData());
			
			this.trackUI.getCommercePrmt().setValue(this.commerceUI.getCommerceData());
			this.trackUI.getTrackData().setCommerceChance(this.commerceUI.getCommerceData());
			
			this.commerceUI.actionSubmit_actionPerformed(e);   //保存商机数据		
		}
		this.trackUI.vertifyInput();
		
		//第一条跟进保存时反写客户资料中的跟进的接待方式
		ReceptionTypeInfo recepType = (ReceptionTypeInfo)this.trackUI.getReceptionTypePrmt().getValue();
		if(recepType!=null)	{
			this.customerUI.getCustomerData().setReceptionType(recepType);
		}		
		
		this.customerUI.actionSubmit_actionPerformed(e);	//保存客户
		
		this.trackUI.getCustomerPrmt().setValue(thisCustomer);	
		this.trackUI.getTrackData().setHead(thisCustomer);		
		
		this.trackUI.actionSave_actionPerformed(e);		//保存跟进数据 
		
		MsgBox.showInfo("新增成功!");
		
		//新增后显示跟进和商机ui中的数据会被清除掉 ，因而改为浏览新界面方式 		
		this.kDTabbedPane.remove(commScrollPane);
		this.customerUI.getKDTabbedPane().remove(trackUI);
		this.customerUI.getKDTabbedPane().addTab("跟进记录", customerUI.getTblTrackRecord());
		this.customerUI.initTrackRecordTable();
		this.customerUI.initCommerceChanceRecord("Sale");  
		this.customerUI.initCommerceChanceRecord("Tenancy");  
	
		this.customerUI.storeFields();
		this.customerUI.initOldData();
		this.actionSave.setEnabled(false);
		
		//this.actionExitCurrent_actionPerformed(e);
		//CommerceHelper.openTheUIWindow(this, CustomerEditUI.class.getName(), thisCustomer.getId().toString());
			

		
	}
	
	
    protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;	
  	
		if(com instanceof KDPromptBox){
			listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDPromptBox)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDFormattedTextField){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDFormattedTextField)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDDatePicker){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDDatePicker)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	} 
    	else if(com instanceof KDComboBox){
    		listeners = com.getListeners(ItemListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDComboBox)com).removeItemListener((ItemListener)listeners[i]);
    		}
    	} 		
    }
	
	
    public boolean destroyWindow() {
    	if(customerUI!=null)
    		customerUI.destroyWindow();
    	if(commerceUI!=null)
    		commerceUI.destroyWindow();
    	if(trackUI!=null)
    		trackUI.destroyWindow();
    	
    	return super.destroyWindow();
    }
    
    
    
    

}