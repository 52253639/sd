/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.commonquery.SorterRuleEnum;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.CRMChequeFactory;
import com.kingdee.eas.fdc.sellhouse.CRMChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeSellProjectEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeSellProjectEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CRMChequeEditUI extends AbstractCRMChequeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(CRMChequeEditUI.class);
    private SellProjectInfo sellproject = null;
    /**
     * output class constructor
     */
    public CRMChequeEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
		this.editData.getSellProjectEntry().clear();
		Object[] sellProjectArray = (Object[])this.F7SellProject.getValue();
		StringBuffer sbNumbers = new StringBuffer();
		StringBuffer sbNames = new StringBuffer();
		if(sellProjectArray!=null && sellProjectArray.length >1 && sellProjectArray[0]!=null){
			for(int i = 0 ; i < sellProjectArray.length ; i ++){
				ChequeSellProjectEntryInfo info = new ChequeSellProjectEntryInfo();
				SellProjectInfo sellInfo = (SellProjectInfo)sellProjectArray[i];
				sbNumbers.append(","+sellInfo.getNumber());
				sbNames.append(","+sellInfo.getName());
				info.setSellProject(sellInfo);
				info.setCRMCheque(this.editData);
				this.editData.getSellProjectEntry().add(info);
			}
		}
		this.editData.setSellProjectNumbers(sbNumbers.toString().replaceFirst(",", ""));
		this.editData.setSellProjectNames(sbNames.toString().replaceFirst(",", ""));    	
    	
    	
    	super.storeFields();
    }
    

	public void loadFields() {
    	super.loadFields();
    	loadChequeDetailEntry(this.editData.getChequeDetailEntry());
    	
    }
    
    private void loadSellProject() {
    	ChequeSellProjectEntryCollection  chequeSellProject = this.editData.getSellProjectEntry();
    	Object[] spArray  = new Object[chequeSellProject.size()];
     	for(int i = 0 ; i< chequeSellProject.size();i++){
     		ChequeSellProjectEntryInfo info = chequeSellProject.get(i);
     		spArray[i] = info.getSellProject();
    	}
    	this.F7SellProject.setValue(spArray);
	}

	public void onLoad() throws Exception {
    	super.onLoad();
    	sellproject = (SellProjectInfo)this.getUIContext().get("sellProject");
    	this.kdtblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);  //.ROW_SELECT
    	initF7Customer();
    	loadSellProject();
    }
    
    private void initF7Customer() {
		
	}

	public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic =  super.getSelectors();
    	 sic.add(new SelectorItemInfo("creator.*"));
         sic.add(new SelectorItemInfo("createTime"));
         sic.add(new SelectorItemInfo("resume"));
         sic.add(new SelectorItemInfo("chequeType"));
         sic.add(new SelectorItemInfo("currency.*"));
         sic.add(new SelectorItemInfo("keeper.*"));
         sic.add(new SelectorItemInfo("keepOrgUnit.*"));
         sic.add(new SelectorItemInfo("limitAmount"));
         sic.add(new SelectorItemInfo("numberOfCheque"));
         sic.add(new SelectorItemInfo("serialNumber"));
         sic.add(new SelectorItemInfo("codeRule"));
         sic.add(new SelectorItemInfo("chequeBathNumber"));
         sic.add("chequeDetailEntry.*");
         sic.add("chequeDetailEntry.writtenOffer.*");
         sic.add("chequeDetailEntry.picker.*");
         sic.add("sellProjectEntry.*");
         sic.add("sellProjectEntry.sellProject.*");
         return sic;
    }
    
    private void loadChequeDetailEntry(ChequeDetailEntryCollection chequeDetailEntryColl){
    	this.kdtblMain.checkParsed();
    	this.kdtblMain.removeRows();
    	CRMChequeInfo  chequeInfo = this.editData;
    	CRMHelper.sortCollection(chequeDetailEntryColl, "number", true);
    	for(int i = 0 ; i <chequeDetailEntryColl.size();i++){
    		ChequeDetailEntryInfo  info = chequeDetailEntryColl.get(i);
    		IRow row = kdtblMain.addRow();
    		row.getCell("batchOfCheque").setValue(chequeInfo.getChequeBathNumber());
    		row.getCell("Number").setValue(info.getNumber());
    		row.getCell("chequeState").setValue(info.getStatus());
    		row.getCell("chequeType").setValue(chequeInfo.getChequeType());
    		row.getCell("resume").setValue(info.getDes());
    		row.getCell("amount").setValue(info.getAmount());
    		row.getCell("CAmount").setValue(info.getCapitalization());
    		row.getCell("currency").setValue(chequeInfo.getCurrency());
    		row.getCell("writerOffer").setValue(info.getWrittenOffer());
    		row.getCell("writerOfferDate").setValue(info.getWrittenOffTime());
    		row.getCell("keeper").setValue(chequeInfo.getKeeper());
    		row.getCell("keeperOrg").setValue(chequeInfo.getKeepOrgUnit());
    		row.getCell("verifyState").setValue(info.getVerifyStatus());
    		row.getCell("verifyOrg").setValue(info.getVerifyOrgUnit());
    		row.getCell("verifyDate").setValue(info.getVerifyTime());
    		row.getCell("verifier").setValue(info.getVerifier());
    		row.getCell("id").setValue(info.getId());
    		row.getCell("picker").setValue(info.getPicker());
    		row.getCell("pickDate").setValue(info.getPikeDate());
    	}
    }
    
    protected void kdtblMain_tableClicked(KDTMouseEvent e) throws Exception {
    	
    	 if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
    		 UIContext uiContext = new UIContext(this);
             uiContext.put(UIContext.ID , getSelectedKeyValue("id"));
             IUIWindow uiWindow =null;
             if(!ChequeStatusEnum.Booked.getAlias().equals(getSelectedKeyValue("chequeState"))&&!ChequeStatusEnum.Picked.getAlias().equals(getSelectedKeyValue("chequeState"))){
            	   uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
                  create(MakeInvoiceEditUI.class.getName() , uiContext , null ,OprtState.VIEW);
         	}else {
         		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
                 create(MakeInvoiceEditUI.class.getName() , uiContext , null ,OprtState.EDIT);
         	}
             uiWindow.show();
    	 }
    }
    
    /**
     * 开票
     */
    public void actionMakeInvoice_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	 UIContext uiContext = new UIContext(this);
		if (!ChequeStatusEnum.Picked.getAlias().equals(getSelectedKeyValue("chequeState"))) {
			FDCMsgBox.showInfo(this, "已领用的票据才能开票！");
			this.abort();
		}
		uiContext.put("id", getSelectedKeyValue("id"));
		uiContext.put("sellProject", sellproject);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(MakeInvoiceEditUI.class.getName(),
				uiContext, null, OprtState.ADDNEW);

		uiWindow.show();
		setChequeState(this.editData.getId().toString());
    }
    
   /**
    * 领用
    */
    public void actionPick_actionPerformed(ActionEvent e) throws Exception {
    	
    	UIContext uiContext = new UIContext(this);
    	uiContext.put("chequeid",this.editData.getId().toString());
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ChequePickEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		//为了更新 只能查一遍了
		setChequeState(this.editData.getId().toString());
		
    }
    
    /**
     * 回收
     */
    public void actionReback_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	if (!ChequeStatusEnum.WrittenOff.getAlias().equals(getSelectedKeyValue("chequeState"))) {
			FDCMsgBox.showInfo(this, "已填开的票据才能回收！");
			this.abort();
		}
    	
    	ChequeDetailEntryFactory.getRemoteInstance().reBack(getSelectedKeyValue("id"));
    	setChequeState(this.editData.getId().toString());
    }
    private void setChequeState(String id) throws BOSException {
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new  FilterInfo();
    	view.setFilter(filter);
    	SorterItemCollection sort = new SorterItemCollection();
    	sort.add(new SorterItemInfo("number"));
    	view.setSorter(sort);
    	filter.getFilterItems().add(new FilterItemInfo("cheque.id",id));
    	SelectorItemCollection selector = new SelectorItemCollection();
    	selector.add(new SelectorItemInfo("*"));
    	selector.add(new SelectorItemInfo("writtenOffer.*"));
    	selector.add(new SelectorItemInfo("picker.*"));
    	selector.add(new SelectorItemInfo("*"));
    	view.setSelector(selector);
    	ChequeDetailEntryCollection coll = ChequeDetailEntryFactory.getRemoteInstance().getChequeDetailEntryCollection(view);
    	loadChequeDetailEntry(coll);
	}

	/**
     * 作废
     */
    public void actionInvalid_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	if (!ChequeStatusEnum.WrittenOff.getAlias().equals(getSelectedKeyValue("chequeState"))) {
			FDCMsgBox.showInfo(this, "已填开的票据才能作废！");
			this.abort();
		}
    	ChequeDetailEntryFactory.getRemoteInstance().invalid(getSelectedKeyValue("id"));
    	setChequeState(this.editData.getId().toString());
    }
    /**
     * 取消开票
     */
    public void actionCancelInvoice_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	if (!ChequeStatusEnum.WrittenOff.getAlias().equals(getSelectedKeyValue("chequeState"))) {
			FDCMsgBox.showInfo(this, "已填开的票据才能取消开票！");
			this.abort();
		}
    	ChequeDetailEntryFactory.getRemoteInstance().cancelInvoice(getSelectedKeyValue("id"));
    	setChequeState( this.editData.getId().toString());
    }
    /**
     * 核销
     */
    public void actionVC_actionPerformed(ActionEvent e) throws Exception {
//    	checkSelected();
    	UIContext uiContext = new UIContext(this);
//		if (!ChequeStatusEnum.WrittenOff.getAlias().equals(getSelectedKeyValue("chequeState"))) {
//			FDCMsgBox.showInfo(this, "已填开的票据才能核销！");
//			this.abort();
//		}
//		ChequeDetailEntryInfo info = ChequeDetailEntryFactory.getRemoteInstance().getChequeDetailEntryInfo(new ObjectUuidPK(getSelectedKeyValue("id")));
    	
    	//已填开的票据才会被核销
		uiContext.put("chequeid", this.editData.getId().toString());
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ChequeVCEditUI.class.getName(),
				uiContext, null, OprtState.ADDNEW);

		uiWindow.show();
		setChequeState( this.editData.getId().toString());
    }
    
    /**
     * 换票
     */
    public void actionChangeInvoice_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
    	
    	ArrayList ids = new ArrayList();
        int[] selectRows = KDTableUtil.getSelectedRows(this.kdtblMain);
        for(int i=0;i<selectRows.length;i++) {
        	IRow curRow = this.kdtblMain.getRow(selectRows[i]);
        	String cqStateStr = ((Object)curRow.getCell("chequeState").getValue()).toString();
    		if (!ChequeStatusEnum.WrittenOff.getAlias().equals(cqStateStr)) {
    			FDCMsgBox.showInfo(this, "已填开的票据才能换票！");
    			this.abort();
    		}
    		String idStr = curRow.getCell("id").getValue().toString();
    		ids.add(idStr);
        }
    	
/*		if (!ChequeStatusEnum.WrittenOff.getAlias().equals(getSelectedKeyValue("chequeState"))) {
			FDCMsgBox.showInfo(this, "已填开的票据才能换票！");
			this.abort();
		}*/
        
    	UIContext uiContext = new UIContext(this);
		uiContext.put("invIDs", ids);
		
/*    	ChequeSellProjectEntryCollection  chequeSellProject = this.editData.getSellProjectEntry();
    	String spIdsStr = "";
    	for(int i = 0 ; i< chequeSellProject.size();i++){
     		ChequeSellProjectEntryInfo info = chequeSellProject.get(i);
     		spIdsStr += ",'"+info.getSellProject().getId()+"'";
    	}
    	if(!spIdsStr.equals("")) spIdsStr = spIdsStr.substring(1);
		uiContext.put("SellProIdsStr", spIdsStr);*/
		
		
		//传一个类型过去 区分是发票还是收据
		uiContext.put("invType", getSelectedKeyValue("chequeType"));
		uiContext.put(UIContext.OWNER, this);

		IUIWindow uiWindow = null;
		// 弹出模式
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				ChangeInvoiceUI.class.getName(), uiContext, null, OprtState.ADDNEW);

		// 开始展现UI
		uiWindow.show();
		setChequeState(this.editData.getId().toString());
    }
    
    
    public void checkSelected()
    {
        if (kdtblMain.getRowCount() == 0 || kdtblMain.getSelectManager().size() == 0)
        {
            MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
            SysUtil.abort();
        }
    }
    
    private int selectIndex = -1;
    protected String getSelectedKeyValue(String keyFiledName)
    {
        int[] selectRows = KDTableUtil.getSelectedRows(this.kdtblMain);
        selectIndex=-1;
        if (selectRows.length > 0)
        {
        	selectIndex=selectRows[0];
        }
        return ListUiHelper.getSelectedKeyValue(selectRows,this.kdtblMain,keyFiledName);
    }
}