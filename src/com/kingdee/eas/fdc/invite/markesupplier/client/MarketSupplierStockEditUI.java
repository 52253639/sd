/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitFactory;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherCollection;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherFactory;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockCollection;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockFactory;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockLinkPersonCollection;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockLinkPersonInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierAttachListEntryCollection;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierAttachListEntryInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierPersonEntryCollection;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierPersonEntryInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierServiceTypeCollection;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierServiceTypeInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierSplAreaEntryCollection;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierSplAreaEntryInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAreaFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAreaInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplServiceTypeFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplServiceTypeInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAttachListCollection;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAttachListFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAttachListInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierFileTypInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MarketSupplierStockEditUI extends AbstractMarketSupplierStockEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSupplierStockEditUI.class);
    protected Map listenersMap = new HashMap();
    /**
     * output class constructor
     */
    public MarketSupplierStockEditUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	initTable();
		super.onLoad();
		initF7();
		initControl();
		kDPanel3.setVisible(false);
		contSupplierBusinessMode.setVisible(false);
		kDTabbedPane1.add(panelBill, "评审");
	}
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sel=super.getSelectors();
    	sel.add("supplierServiceType.*");
    	sel.add("supplierServiceType.serviceType.*");
    	sel.add("supplierSplAreaEntry.*");
    	sel.add("supplierSplAreaEntry.fdcSplArea.*");
    	sel.add("supplierPersonEntry.*");
    	sel.add("supplierAttachListEntry.*");
    	sel.add("linkPerson.*");
    	sel.add("state");
    	sel.add("adminCU.*");
    	sel.add("sysSupplier.*");
    	sel.add("*");
		return sel;
	}
    public void storeFields()
    {
    	this.storeSupplierAttachList();
    	this.storeSupplierLinkPerson();
    	this.storeSupplierPerson();
    	this.storeSupplierServiceType();
    	this.storeSupplierSplArea();
        super.storeFields();
    }
    public void loadFields() {
    	detachListeners();
    	super.loadFields();
    	this.loadSupplierLinkPerson();
    	this.loadSupplierPerson();
    	this.loadSupplierServiceType();
    	this.loadSupplierSplArea();
    	this.loadSupplierAttachList();
    	attachListeners();
    	
    	if(!SysContext.getSysContext().getCurrentOrgUnit().isIsPurchaseOrgUnit()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
    }
    protected void initControl(){
    	this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		
    	this.actionSubmit.putValue("SmallIcon", EASResource.getIcon("imgTbtn_submit"));
    	this.actionSave.putValue("SmallIcon", EASResource.getIcon("imgTbtn_save"));
    	
    	this.actionCopy.setVisible(true);
    	this.actionCancel.setVisible(false);
    	this.actionCancelCancel.setVisible(false);
    	
    	this.menuView.setVisible(false);
    	
    	this.menuItemSave.setVisible(true);
    	this.menuItemSave.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
    	this.menuItemSubmit.setVisible(true);
    	this.menuItemSubmit.setAccelerator(KeyStroke.getKeyStroke("ctrl T"));
    	this.menuItemAddNew.setAccelerator(null);
    	
		if(!SupplierStateEnum.SAVE.equals(this.editData.getState())&&!SupplierStateEnum.SUBMIT.equals(this.editData.getState())){
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		if(SupplierStateEnum.SUBMIT.equals(this.editData.getState())){
			this.actionSave.setEnabled(false);
		}
		this.actionAddNew.setVisible(false);
		
		try {
			FDCHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		} catch (CodingRuleException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.prmtInviteType.setRequired(true);
		this.prmtVisibility.setRequired(true);
		this.kDTextField1.setRequired(true);
		this.txtEnterpriseMaster.setRequired(true);
		this.prmtSupplierFileType.setRequired(true);
		this.prmtSupplierSplAreaEntry.setRequired(true);
		this.prmtSupplierBusinessMode.setRequired(true);
		this.txtAbility.setRequired(true);
		this.txtBusinessNum.setRequired(true);
		
		if(this.txtRecommended.getText()!=null&&!this.txtRecommended.getText().trim().equals("")){
			this.txtRecommended.setEnabled(false);
		}
		if(!SysContext.getSysContext().getCurrentOrgUnit().isIsPurchaseOrgUnit()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCopy.setEnabled(false);
		}
		loadReviewGatherUI();
    }
    protected void loadReviewGatherUI(){
    	try {
			Map map=new HashMap();
			TreeMap treemap = new TreeMap();
			if(editData.getId()!=null){
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("supplier.id" , editData.getId()));
				view.setFilter(filter);
				view.getSelector().add(new SelectorItemInfo("id"));
				view.getSelector().add(new SelectorItemInfo("evaluationType.id"));
				view.getSelector().add(new SelectorItemInfo("evaluationType.number"));
		    	
				MarketSupplierReviewGatherCollection col= MarketSupplierReviewGatherFactory.getRemoteInstance().getMarketSupplierReviewGatherCollection(view);
				for(int i=0;i<col.size();i++){
					if(col.get(i).getEvaluationType()==null) continue;
					if(map.get(col.get(i).getEvaluationType().getId().toString())!=null){
						((Set)map.get(col.get(i).getEvaluationType().getId().toString())).add(col.get(i).getId().toString());
					}else{
						Set id=new HashSet();
						id.add(col.get(i).getId().toString());
						map.put(col.get(i).getEvaluationType().getId().toString(), id);
						treemap.put(col.get(i).getEvaluationType().getNumber(), col.get(i).getEvaluationType().getId().toString());
					}
				}
			}
			Iterator treeit = treemap.entrySet().iterator();
			while(treeit.hasNext()){
				Map.Entry entry = (Map.Entry) treeit.next();
				String typeId = (String)entry.getValue();
				Set idSet=(HashSet)map.get(typeId);
				KDScrollPane panel=new KDScrollPane();
				panel.setName(typeId);
				panel.setMinimumSize(new Dimension(1013,600));		
				panel.setPreferredSize(new Dimension(1013,600));
		        this.kDTabbedPane2.add(panel,MarketAccreditationTypeFactory.getRemoteInstance().getMarketAccreditationTypeInfo(new ObjectUuidPK(typeId)).getName());
		        
		        UIContext uiContext = new UIContext(this);
		        uiContext.put("IDSET", idSet);
				MarketSupplierReviewGatherListUI ui = (MarketSupplierReviewGatherListUI) UIFactoryHelper.initUIObject(MarketSupplierReviewGatherListUI.class.getName(), uiContext, null,OprtState.VIEW);
				panel.setViewportView(ui);
				panel.setKeyBoardControl(true);
				panel.setEnabled(false);
			}
//			Iterator it=map.entrySet().iterator();
//			while(it.hasNext()){
//				Map.Entry entry = (Map.Entry) it.next();
//				String typeId=entry.getKey().toString();
//				Set idSet=(HashSet)entry.getValue();
//				
//				KDScrollPane panel=new KDScrollPane();
//				panel.setName(typeId);
//				panel.setMinimumSize(new Dimension(1013,600));		
//				panel.setPreferredSize(new Dimension(1013,600));
//		        this.kDTabbedPane2.add(panel,MarketAccreditationTypeFactory.getRemoteInstance().getMarketAccreditationTypeInfo(new ObjectUuidPK(typeId)).getName());
//		        
//		        UIContext uiContext = new UIContext(this);
//		        uiContext.put("IDSET", idSet);
//				MarketSupplierReviewGatherListUI ui = (MarketSupplierReviewGatherListUI) UIFactoryHelper.initUIObject(MarketSupplierReviewGatherListUI.class.getName(), uiContext, null,OprtState.VIEW);
//				panel.setViewportView(ui);
//				panel.setKeyBoardControl(true);
//				panel.setEnabled(false);
//			}
		}catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
    public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if(oprtType.equals(OprtState.VIEW)){
			this.actionPersonAddLine.setEnabled(false);
			this.actionPersonInsertLine.setEnabled(false);
			this.actionPersonRemoveLine.setEnabled(false);
			this.actionLinkPersonAddLine.setEnabled(false);
			this.actionLinkPersonInsertLine.setEnabled(false);
			this.actionLinkPersonRemoveLine.setEnabled(false);
			this.actionAttachListAddLine.setEnabled(false);
			this.actionAttachListInsertLine.setEnabled(false);
			this.actionAttachListRemoveLine.setEnabled(false);
		}else{
			this.actionPersonAddLine.setEnabled(true);
			this.actionPersonInsertLine.setEnabled(true);
			this.actionPersonRemoveLine.setEnabled(true);
			this.actionLinkPersonAddLine.setEnabled(true);
			this.actionLinkPersonInsertLine.setEnabled(true);
			this.actionLinkPersonRemoveLine.setEnabled(true);
			this.actionAttachListAddLine.setEnabled(true);
			this.actionAttachListInsertLine.setEnabled(true);
			this.actionAttachListRemoveLine.setEnabled(true);
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		if(SupplierStateEnum.SUBMIT.equals(this.editData.getState())){
			this.actionSave.setEnabled(false);
		}
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		verifyInputForSave();
		super.actionSave_actionPerformed(e);
		FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
			public void run() {
				storeFields();
				initOldData(editData);
			}
		});
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyInputForSubmit();
		super.actionSubmit_actionPerformed(e);
		if(SupplierStateEnum.SUBMIT.equals(this.editData.getState())){
			this.actionSave.setEnabled(false);
		}
		FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
			public void run() {
				storeFields();
				initOldData(editData);
			}
		});
		
		FDCHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
	}
    protected void storeSupplierSplArea(){
    	editData.getSupplierSplAreaEntry().clear();
    	Object[] value=(Object[]) this.prmtSupplierSplAreaEntry.getValue();
    	String splArea="";
    	if(value!=null&&value.length>0){
    		for(int i=0;i<value.length;i++){
    			MarketSplAreaInfo info = (MarketSplAreaInfo) value[i];
    			if(info==null){continue;}
    			MarketSupplierStockSupplierSplAreaEntryInfo entry = new MarketSupplierStockSupplierSplAreaEntryInfo();
				entry.setFdcSplArea(info);
				splArea=splArea+info.getName()+";";
				editData.getSupplierSplAreaEntry().add(entry);
    		}
    	}
    	editData.setSplArea(splArea);
    }
    protected void storeSupplierServiceType(){
    	editData.getSupplierServiceType().clear();
    	Object[] value=(Object[]) this.prmtServiceType.getValue();
    	if(value!=null&&value.length>0){
    		for(int i=0;i<value.length;i++){
    			MarketSplServiceTypeInfo info = (MarketSplServiceTypeInfo) value[i];
    			MarketSupplierStockSupplierServiceTypeInfo entry = new MarketSupplierStockSupplierServiceTypeInfo();
				entry.setServiceType(info);
				editData.getSupplierServiceType().add(entry);
    		}
    	}
    }
    protected void storeSupplierLinkPerson(){
    	editData.getLinkPerson().clear();
    	for(int i=0;i<this.kdtLinkPerson.getRowCount();i++){
    		IRow row = this.kdtLinkPerson.getRow(i);
    		MarketSupplierStockLinkPersonInfo entry=new MarketSupplierStockLinkPersonInfo();
    		if(row.getUserObject()!=null){
    			entry=(MarketSupplierStockLinkPersonInfo) row.getUserObject();
    		}
    		entry.setSeq(i);
    		entry.setPersonName((String)row.getCell("personName").getValue());
    		entry.setPosition((String)row.getCell("position").getValue());
    		entry.setPhone((String)row.getCell("phone").getValue());
    		entry.setWorkPhone((String)row.getCell("workPhone").getValue());
    		entry.setPersonFax((String)row.getCell("personFax").getValue());
    		entry.setEmail((String)row.getCell("email").getValue());
			entry.setIsDefault(((Boolean)row.getCell("isDefault").getValue()).booleanValue());
    		editData.getLinkPerson().add(entry);
    	}
    }
    protected void storeSupplierPerson(){
    	editData.getSupplierPersonEntry().clear();
    	for(int i=0;i<this.kdtSupplierPerson.getRowCount();i++){
    		IRow row = this.kdtSupplierPerson.getRow(i);
    		MarketSupplierStockSupplierPersonEntryInfo entry=new MarketSupplierStockSupplierPersonEntryInfo();
    		if(row.getUserObject()!=null){
    			entry=(MarketSupplierStockSupplierPersonEntryInfo) row.getUserObject();
    		}
    		entry.setSeq(i);
    		entry.setNumber((String)row.getCell("number").getValue());
    		entry.setName((String)row.getCell("name").getValue());
    		if(UIRuleUtil.isNull(row.getCell("amount").getValue())){
    			entry.setAmount(0);
    		}else{
    			entry.setAmount(Integer.parseInt(row.getCell("amount").getValue().toString()));
    		}
    		editData.getSupplierPersonEntry().add(entry);
    	}
    		
    }
    protected void storeSupplierAttachList(){
    	editData.getSupplierAttachListEntry().clear();
    	for(int i=0;i<this.kdtSupplierAttachList.getRowCount();i++){
    		IRow row = this.kdtSupplierAttachList.getRow(i);
    		MarketSupplierStockSupplierAttachListEntryInfo entry=new MarketSupplierStockSupplierAttachListEntryInfo();
    		if(row.getUserObject()!=null){
    			entry=(MarketSupplierStockSupplierAttachListEntryInfo) row.getUserObject();
    		}
    		entry.setSeq(i);
    		entry.setNumber((String)row.getCell("number").getValue());
    		entry.setName((String)row.getCell("name").getValue());
    		editData.getSupplierAttachListEntry().add(entry);
    	}
    }
	protected void loadSupplierSplArea(){
		MarketSupplierStockSupplierSplAreaEntryCollection col=editData.getSupplierSplAreaEntry();
    	if(col.size()>0){
    		Object[] value=new Object[col.size()];
    		for(int i=0;i<col.size();i++) {
    			MarketSupplierStockSupplierSplAreaEntryInfo entry = col.get(i);
//    			MarketSupplierStockSupplierSplAreaEntryInfo entry = null;
//				try {
//					entry = MarketSupplierStockSupplierSplAreaEntryFactory.getRemoteInstance().getMarketSupplierStockSupplierSplAreaEntryInfo(new ObjectUuidPK(entry1.getId()));
//				} catch (EASBizException e) {
//					e.printStackTrace();
//				} catch (BOSException e) {
//					e.printStackTrace();
//				}
    			if(entry.getFdcSplArea()!=null)
					try {
						MarketSplAreaInfo info = MarketSplAreaFactory.getRemoteInstance().getMarketSplAreaInfo(new ObjectUuidPK(entry.getFdcSplArea().getId())) ;
						value[i]=info;
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
				else
    				value[i] = null;
    		}
    		this.prmtSupplierSplAreaEntry.setValue(value);
    	}else{
    		this.prmtSupplierSplAreaEntry.setValue(null);
    	}
    }
	protected void loadSupplierServiceType(){
		MarketSupplierStockSupplierServiceTypeCollection col=editData.getSupplierServiceType();
		if(col.size()>0){
    		Object[] value=new Object[col.size()];
    		for(int i=0;i<col.size();i++) {
    			MarketSupplierStockSupplierServiceTypeInfo entry = col.get(i);
//    			MarketSupplierStockSupplierServiceTypeInfo entry = null;
				try {
//					entry = MarketSupplierStockSupplierServiceTypeFactory.getRemoteInstance().getMarketSupplierStockSupplierServiceTypeInfo(new ObjectUuidPK(entry1.getId()));
					if(entry.getServiceType()!=null)
						value[i] = MarketSplServiceTypeFactory.getRemoteInstance().getMarketSplServiceTypeInfo(new ObjectUuidPK(entry.getServiceType().getId()));
					else
						value[i]=null;
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
    		}
    		this.prmtServiceType.setValue(value);
    	}else{
    		this.prmtServiceType.setValue(null);
    	}
	}
	protected void loadSupplierLinkPerson(){
		MarketSupplierStockLinkPersonCollection col=editData.getLinkPerson();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtLinkPerson.removeRows();
		for(int i=0;i<col.size();i++){
			MarketSupplierStockLinkPersonInfo entry=col.get(i);
//			MarketSupplierStockLinkPersonInfo entry = null;
//			try {
//				entry = MarketSupplierStockLinkPersonFactory.getRemoteInstance()
//				.getMarketSupplierStockLinkPersonInfo(new ObjectUuidPK(entry1.getId()));
//			} catch (EASBizException e) {
//				e.printStackTrace();
//			} catch (BOSException e) {
//				e.printStackTrace();
//			}
			IRow row=this.kdtLinkPerson.addRow();
			row.setUserObject(entry);
			row.getCell("personName").setValue(entry.getPersonName());
			row.getCell("position").setValue(entry.getPosition());
			row.getCell("phone").setValue(entry.getPhone());
			row.getCell("workPhone").setValue(entry.getWorkPhone());
			row.getCell("personFax").setValue(entry.getPersonFax());
			row.getCell("email").setValue(entry.getEmail());
			row.getCell("isDefault").setValue(Boolean.valueOf(entry.isIsDefault()));
		}
	}
	protected void loadSupplierPerson(){
		MarketSupplierStockSupplierPersonEntryCollection col=editData.getSupplierPersonEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtSupplierPerson.removeRows();
		for(int i=0;i<col.size();i++){
			MarketSupplierStockSupplierPersonEntryInfo entry=col.get(i);
//			MarketSupplierStockSupplierPersonEntryInfo entry = null;
//			try {
//				entry = MarketSupplierStockSupplierPersonEntryFactory.getRemoteInstance()
//				.getMarketSupplierStockSupplierPersonEntryInfo(new ObjectUuidPK(entry1.getId()));
//			} catch (EASBizException e) {
//				e.printStackTrace();
//			} catch (BOSException e) {
//				e.printStackTrace();
//			}
			IRow row=this.kdtSupplierPerson.addRow();
			row.setUserObject(entry);
			row.getCell("number").setValue(entry.getNumber());
			row.getCell("name").setValue(entry.getName());
			row.getCell("amount").setValue(Integer.valueOf(entry.getAmount()));
		}
	}
	protected void loadSupplierAttachList(){
		MarketSupplierStockSupplierAttachListEntryCollection col=editData.getSupplierAttachListEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtSupplierAttachList.removeRows();
		for(int i=0;i<col.size();i++){
			MarketSupplierStockSupplierAttachListEntryInfo entry=col.get(i);
			IRow row=this.kdtSupplierAttachList.addRow();
			row.setUserObject(entry);
			row.getCell("number").setValue(entry.getNumber());
			row.getCell("name").setValue(entry.getName());
			try {
				if(entry.getId()!=null){
					row.getCell("attachment").setValue(loadAttachment(entry.getId().toString()));
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}	
	protected void prmtProvince_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		ProvinceInfo pinfo = (ProvinceInfo) prmtProvince.getValue();
		if(pinfo!=null){
			filter.getFilterItems().add(new FilterItemInfo("province.id" , pinfo.getId().toString()));
		}
		view.setFilter(filter);
		this.prmtCity.setEntityViewInfo(view);
    }

    protected void prmtSupplierFileType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	 boolean isChanged = true;
		 isChanged = BizCollUtil.isF7ValueChanged(e);
         if(!isChanged){
        	 return;
         }
         MarketSupplierFileTypInfo fileType=(MarketSupplierFileTypInfo)this.prmtSupplierFileType.getValue();
         boolean isShowWarn=false;
         boolean isUpdate=false;
         if(this.kdtSupplierPerson.getRowCount()>0||this.kdtSupplierAttachList.getRowCount()>0){
        	 isShowWarn=true;
         }
         if(isShowWarn){
        	 if(FDCMsgBox.showConfirm2(this, "档案分类改变会覆盖附件清单数据，是否继续？")== FDCMsgBox.YES){
        		 isUpdate=true;
             }
         }else{
        	 isUpdate=true;
         }
         if(isUpdate){
        	 this.kdtSupplierPerson.removeRows();
        	 this.kdtSupplierAttachList.removeRows();
        	 
        	 if(fileType!=null){
        		 SorterItemCollection sort=new SorterItemCollection();
        		 sort.add(new SorterItemInfo("number"));
        		 EntityViewInfo view=new EntityViewInfo();
            	 FilterInfo filter = new FilterInfo();
                 filter.getFilterItems().add(new FilterItemInfo("supplierFileType.id" , fileType.getId().toString()));
                 filter.getFilterItems().add(new FilterItemInfo("isEnabled" , Boolean.TRUE));
                 view.setFilter(filter);
                 view.setSorter(sort);
//                 MarketSupplierPersonCollection col=MarketSupplierPersonFactory.getRemoteInstance().getMarketSupplierPersonCollection(view);
//                 for(int i=0;i<col.size();i++){
//                	 MarketSupplierPersonInfo sp=col.get(i);
//                	 IRow row=this.kdtSupplierPerson.addRow();
//                	 row.getCell("number").setValue(sp.getNumber());
//                	 row.getCell("name").setValue(sp.getName());
//                 }
                 MarketSupplierAttachListCollection alCol=MarketSupplierAttachListFactory.getRemoteInstance().getMarketSupplierAttachListCollection(view);
                 for(int i=0;i<alCol.size();i++){
                	 MarketSupplierAttachListInfo at=alCol.get(i);
                	 IRow row=this.kdtSupplierAttachList.addRow();
                	 MarketSupplierStockSupplierAttachListEntryInfo info=new MarketSupplierStockSupplierAttachListEntryInfo();
     				 info.setId(BOSUuid.create(info.getBOSType()));
     				 row.setUserObject(info);
                	 row.getCell("number").setValue(at.getNumber());
                	 row.getCell("name").setValue(at.getName());
                 }
        	 }
         }
    }
    protected void initF7(){
    	//服务区域
    	prmtSupplierSplAreaEntry.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.MarketSplAreaQuery");
    	//服务类型
    	prmtServiceType.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.MarketSplServiceTypeQuery");
    	//档案分类
    	prmtSupplierFileType.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.MarketSupplierFileTypQuery");
    	//经营模式
    	prmtSupplierBusinessMode.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.MarketSupplierBusinessModeQuery");
    	//资格等级
    	prmtQuaLevel.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.MarktQuaLevelQuery");
    	
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		view.setFilter(filter);
		this.prmtServiceType.setEntityViewInfo(view);
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("longNumber","6%",CompareType.LIKE));
		view.setFilter(filter);
		this.prmtInviteType.setEntityViewInfo(view);
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		view.setFilter(filter);
		this.prmtSupplierFileType.setEntityViewInfo(view);
		this.prmtSupplierBusinessMode.setEntityViewInfo(view);
		this.prmtSupplierSplAreaEntry.setEntityViewInfo(view);
		this.prmtVisibility.setEntityViewInfo(view);
    }
    protected void initWorkButton(ItemAction add,ItemAction insert,ItemAction remove,KDContainer container){
    	KDWorkButton btnAddRowinfo=new KDWorkButton();
    	KDWorkButton btnInsertRowinfo=new KDWorkButton();
    	KDWorkButton btnDeleteRowinfo=new KDWorkButton();
    	
    	add.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)container.add(add);
		btnAddRowinfo.setText(getResource("addRow"));
		btnAddRowinfo.setSize(new Dimension(140, 19));
		
		insert.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton)container.add(insert);
		btnInsertRowinfo.setText(getResource("insertRow"));
		btnInsertRowinfo.setSize(new Dimension(140, 19));
		
		remove.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton)container.add(remove);
		btnDeleteRowinfo.setText(getResource("deleteRow"));
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
    }
    protected void initTable(){
    	initWorkButton(this.actionPersonAddLine,this.actionPersonInsertLine,this.actionPersonRemoveLine,this.contSupplierPerson);
    	initWorkButton(this.actionLinkPersonAddLine,this.actionLinkPersonInsertLine,this.actionLinkPersonRemoveLine,this.contLinkPerson);
    	initWorkButton(this.actionAttachListAddLine,this.actionAttachListInsertLine,this.actionAttachListRemoveLine,this.contSupplierAttachList);
    	prmtServiceType.setEnabled(false);
    	KDTextField testField = new KDTextField();
    	testField.setMaxLength(80);
		KDTDefaultCellEditor editorSize = new KDTDefaultCellEditor(testField);
		kDTextField1.setMaxLength(80);
		KDTextField testField1 = new KDTextField();
    	testField.setMaxLength(255);
		KDTDefaultCellEditor editorSize1 = new KDTDefaultCellEditor(testField1);
		
		KDFormattedTextField peopleC = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		peopleC.setPrecision(0);
		peopleC.setDataVerifierType(12);
		KDTDefaultCellEditor peopleCount = new KDTDefaultCellEditor(peopleC);
		
    	this.kdtLinkPerson.checkParsed();
 	    this.kdtLinkPerson.getColumn("personName").setEditor(editorSize);
 		this.kdtLinkPerson.getColumn("position").setEditor(editorSize);
 		this.kdtLinkPerson.getColumn("phone").setEditor(editorSize);
 		this.kdtLinkPerson.getColumn("workPhone").setEditor(editorSize);
 		this.kdtLinkPerson.getColumn("personFax").setEditor(editorSize);
 		this.kdtLinkPerson.getColumn("email").setEditor(editorSize);
 		KDCheckBox isAutidor = new KDCheckBox();
 		isAutidor.setSelected(false);
 		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(isAutidor);
 		this.kdtLinkPerson.getColumn("isDefault").setEditor(editor);
 		
 		this.kdtSupplierPerson.checkParsed();
 		this.kdtSupplierPerson.getColumn("number").setEditor(editorSize);
  		this.kdtSupplierPerson.getColumn("name").setEditor(editorSize1);
  		this.kdtSupplierPerson.getColumn("amount").setEditor(peopleCount);
  		this.kdtSupplierPerson.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
 		
 		this.kdtSupplierAttachList.checkParsed();
 		this.kdtSupplierAttachList.getColumn("number").setEditor(editorSize);
  		this.kdtSupplierAttachList.getColumn("name").setEditor(editorSize1);
  		this.kdtSupplierAttachList.getColumn("attachment").getStyleAttributes().setLocked(true);
  		this.kdtSupplierAttachList.getColumn("attachment").getStyleAttributes().setFontColor(Color.BLUE);
  		this.kdtSupplierAttachList.getColumn("attachment").setWidth(400);
    }
    
	public void actionAttachListAddLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableAddRow(this.kdtSupplierAttachList);
	}
	public void actionAttachListInsertLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableInsertLine(this.kdtSupplierAttachList);
	}
	public void actionAttachListRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableDeleteRow(this.kdtSupplierAttachList);
	}
	public void actionLinkPersonAddLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableAddRow(this.kdtLinkPerson);
	}
	public void actionLinkPersonInsertLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableInsertLine(this.kdtLinkPerson);
	}
	public void actionLinkPersonRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableDeleteRow(this.kdtLinkPerson);
	}
	public void actionPersonAddLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableAddRow(this.kdtSupplierPerson);
	}
	public void actionPersonInsertLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableInsertLine(this.kdtSupplierPerson);
	}
	public void actionPersonRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableDeleteRow(this.kdtSupplierPerson);
	}
	protected void kdtLinkPerson_editStopped(KDTEditEvent e) throws Exception {
		int index = e.getRowIndex();
		int size = this.kdtLinkPerson.getRowCount();
	    IRow row = this.kdtLinkPerson.getRow(index);
	    Boolean b=(Boolean) row.getCell("isDefault").getValue();
	    /*
	     * 这里是合并手机与办公电话到一列中去,序时薄上要把手机和办公电话显示到一起.暂用这种方式做
	     */
	    for(int j = 0 ; j<size ; j++){
	    	if(null != this.kdtLinkPerson.getRow(j).getCell("contact").getValue()){
	    		this.kdtLinkPerson.getRow(j).getCell("contact").setValue(null);
	    	}
	    	if(null != this.kdtLinkPerson.getRow(j).getCell("phone").getValue()){
	    		if(null != this.kdtLinkPerson.getRow(j).getCell("workPhone").getValue()){
	    			this.kdtLinkPerson.getRow(j).getCell("contact").setValue(this.kdtLinkPerson.getRow(j).getCell("phone").getValue()+";"+this.kdtLinkPerson.getRow(j).getCell("workPhone").getValue());
	    		}else{
	    			this.kdtLinkPerson.getRow(j).getCell("contact").setValue(this.kdtLinkPerson.getRow(j).getCell("phone").getValue().toString());
	    		}
	    	}else if(null != this.kdtLinkPerson.getRow(j).getCell("workPhone").getValue()){
	    		this.kdtLinkPerson.getRow(j).getCell("contact").setValue(this.kdtLinkPerson.getRow(j).getCell("workPhone").getValue().toString());
	    	}else{
	    		continue;
	    	}
	    }
	    /*
	     * 处理只能勾选一个人为默认联系.新增第一行时为空 则返回
	     */
	    if(null == b ){
	    	return ;
	    }
	    if(b.booleanValue()){
			for( int i = 0 ; i< size ;i++){
				if(i ==index ){
					continue;
				}
				this.kdtLinkPerson.getRow(i).getCell("isDefault").setValue(Boolean.FALSE);
			}
	    }
	}
	protected void kdtSupplierAttachList_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2&&
				this.kdtSupplierAttachList.getColumnKey(e.getColIndex()).equals("attachment")) {
			if(((MarketSupplierStockSupplierAttachListEntryInfo)this.kdtSupplierAttachList.getRow(e.getRowIndex()).getUserObject()).getId()==null){return;};
			String id=((MarketSupplierStockSupplierAttachListEntryInfo)this.kdtSupplierAttachList.getRow(e.getRowIndex()).getUserObject()).getId().toString();
			AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
	        boolean isEdit = false;
	        if(OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState()))
	            isEdit = true;
	        AttachmentUIContextInfo info = new AttachmentUIContextInfo();
	        info.setBoID(id);
	        info.setEdit(isEdit);
	        String multi = (String)getUIContext().get("MultiapproveAttachment");
	        if(multi != null && multi.equals("true")){
	        	acm.showAttachmentListUIByBoIDNoAlready(this, info);
	        }else{
	        	acm.showAttachmentListUIByBoID(this, info);
	        }
	        this.kdtSupplierAttachList.getRow(e.getRowIndex()).getCell("attachment").setValue(loadAttachment(id));
		}
	}
	protected String loadAttachment(String id) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID", id));
		view.setFilter(filter);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("attachment.name");
		view.setSelector(sels);
		BoAttchAssoCollection col = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		String name="";
		for(int i=0;i<col.size();i++){
			name=name+col.get(i).getAttachment().getName()+" ";
		}
		return name;
	}


	protected void attachListeners() {
		addDataChangeListener(this.prmtSupplierFileType);
		addDataChangeListener(this.prmtProvince);
		addDataChangeListener(this.prmtSupplierSplAreaEntry);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.prmtSupplierFileType);
		removeDataChangeListener(this.prmtProvince);
		removeDataChangeListener(this.prmtSupplierSplAreaEntry);
	}
    protected void addDataChangeListener(JComponent com) {
    	EventListener[] listeners = (EventListener[] )listenersMap.get(com);
    	if(listeners!=null && listeners.length>0){
	    	if(com instanceof KDPromptBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDPromptBox)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDFormattedTextField){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDFormattedTextField)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDDatePicker){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDDatePicker)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	} else if(com instanceof KDComboBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDComboBox)com).addItemListener((ItemListener)listeners[i]);
	    		}
	    	}
    	}
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
    	}else if(com instanceof KDComboBox){
    		listeners = com.getListeners(ItemListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDComboBox)com).removeItemListener((ItemListener)listeners[i]);
    		}
    	} 
		if(listeners!=null && listeners.length>0){
			listenersMap.put(com,listeners );
		}
    }
    private void kdTableAddRow(KDTable table) {
		if(!getOprtState().equals(OprtState.VIEW)){
			IRow row=table.addRow();
			if(table.equals(this.kdtSupplierAttachList)){
				MarketSupplierStockSupplierAttachListEntryInfo info=new MarketSupplierStockSupplierAttachListEntryInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				row.setUserObject(info);
			}else if(table.equals(this.kdtLinkPerson)){
				MarketSupplierStockLinkPersonInfo info=new MarketSupplierStockLinkPersonInfo();
				row.setUserObject(info);
				if(this.kdtLinkPerson.getRowCount()==1){
					row.getCell("isDefault").setValue(Boolean.TRUE);
				}else{
					row.getCell("isDefault").setValue(Boolean.FALSE);
				}
			}
		}
			
	}
	private void kdTableInsertLine(KDTable table){
		if(!getOprtState().equals(OprtState.VIEW)){
			if(table == null)
	            return;
	        IRow row = null;
	        if(table.getSelectManager().size() > 0)
	        {
	            int top = table.getSelectManager().get().getTop();
	            if(isTableColumnSelected(table))
	                row = table.addRow();
	            else
	                row = table.addRow(top);
	        } else
	        {
	            row = table.addRow();
	        }
	        if(table.equals(this.kdtSupplierAttachList)&&row!=null){
	        	MarketSupplierStockSupplierAttachListEntryInfo info=new MarketSupplierStockSupplierAttachListEntryInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				row.setUserObject(info);
			}else if(table.equals(this.kdtLinkPerson)){
				MarketSupplierStockLinkPersonInfo info=new MarketSupplierStockLinkPersonInfo();
				row.setUserObject(info);
				if(this.kdtLinkPerson.getRowCount()==1){
					row.getCell("isDefault").setValue(Boolean.TRUE);
				}else{
					row.getCell("isDefault").setValue(Boolean.FALSE);
				}
			}
		}
	}
	protected final boolean isTableColumnSelected(KDTable table)
    {
        if(table.getSelectManager().size() > 0)
        {
            KDTSelectBlock block = table.getSelectManager().get();
            if(block.getMode() == 4 || block.getMode() == 8)
                return true;
        }
        return false;
    }
	private boolean confirmRemove(Component comp){
		return FDCMsgBox.isYes(FDCMsgBox.showConfirm2(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}
	private void kdTableDeleteRow(KDTable table) {
		if(!getOprtState().equals(OprtState.VIEW)){
	        if(table.getSelectManager().size() == 0 || isTableColumnSelected(table)){
	            FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	            return;
	        }
	        if(confirmRemove(this)){
	            int top = table.getSelectManager().get().getBeginRow();
	            int bottom = table.getSelectManager().get().getEndRow();
	            for(int i = top; i <= bottom; i++){
	                if(table.getRow(top) == null)
	                {
	                    FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	                    return;
	                }
	                if(table.equals(this.kdtSupplierAttachList)){
	                	try {
	                		deleteAttachment(((MarketSupplierStockSupplierAttachListEntryInfo)table.getRow(top).getUserObject()).getId().toString());
	                	} catch (BOSException e) {
							e.printStackTrace();
						} catch (EASBizException e) {
							e.printStackTrace();
						}
	                }
	                table.removeRow(top);
	            }
	        }
		}
	}
	protected void deleteAttachment(String id) throws BOSException, EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , id));
		view.setFilter(filter);
		BoAttchAssoCollection col=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		if(col.size()>0){
			for(int i=0;i<col.size();i++){
				EntityViewInfo attview=new EntityViewInfo();
				FilterInfo attfilter = new FilterInfo();
				
				attfilter.getFilterItems().add(new FilterItemInfo("attachment.id" , col.get(i).getAttachment().getId().toString()));
				attview.setFilter(attfilter);
				BoAttchAssoCollection attcol=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(attview);
				if(attcol.size()==1){
					BizobjectFacadeFactory.getRemoteInstance().delTempAttachment(id);
				}else if(attcol.size()>1){
					BoAttchAssoFactory.getRemoteInstance().delete(filter);
				}
			}
		}
	}
	private String getResource(String msg) {
		return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", msg);
	}
	protected void showSaveSuccess() {
		setMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Save_OK"));
	    setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
	    setShowMessagePolicy(0);
	    setIsShowTextOnly(false);
	    showMessage();
	}
	protected void showSubmitSuccess() {
		setMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Submit_OK"));
	    if(chkMenuItemSubmitAndAddNew.isSelected())
	        setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_AddNew"));
	    else
	    if(!chkMenuItemSubmitAndPrint.isSelected() && chkMenuItemSubmitAndAddNew.isSelected())
	        setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_AddNew"));
	    else
	        setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
	    setIsShowTextOnly(false);
	    setShowMessagePolicy(0);
	    showMessage();
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		verifyInputForSave();
		// 编码是否为空
		KDTextField txtNumber = this.getNumberCtrl();
		if (txtNumber.getText() == null || txtNumber.getText().trim().length() < 1) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		}
		// 名称是否为空
		KDBizMultiLangBox txtName = getNameCtrl();
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(txtName, getEditData(), "name");
		if (flag) {
			txtName.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}
	}
	
	protected void verifyInputForSubmit() throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		FDCClientVerifyHelper.verifyEmpty(this, getNameCtrl());
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtInviteType);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtPurchaseOrgUnit);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtEnterpriseMaster);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSupplierFileType);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSupplierSplAreaEntry);
//		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSupplierBusinessMode);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtBusinessNum);
//		FDCClientVerifyHelper.verifyEmpty(this, this.txtAbility);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtVisibility);
		FDCClientVerifyHelper.verifyEmpty(this, this.kDTextField1);
		boolean isDefault=true;
		for(int i=0;i<this.kdtLinkPerson.getRowCount();i++){
			IRow row=this.kdtLinkPerson.getRow(i);
			if(((Boolean)row.getCell("isDefault").getValue()).booleanValue()){
				isDefault=false;
				if(row.getCell("personName").getValue()==null||row.getCell("personName").getValue().toString().trim().equals("")){
					FDCMsgBox.showWarning(this,"授权联系人姓名不能为空！");
					SysUtil.abort();
				}
				if(row.getCell("phone").getValue()==null||row.getCell("phone").getValue().toString().trim().equals("")){
					FDCMsgBox.showWarning(this,"授权联系人手机不能为空！");
					SysUtil.abort();
				}
			}
		}
		if(isDefault){
			FDCMsgBox.showWarning(this,"授权联系人不能为空！");
			SysUtil.abort();
		}
		boolean isYValueAttact = true;
		boolean isSValueAttact = true;
		boolean isZValueAttact = true;
		for (int i = 0; i < kdtSupplierAttachList.getRowCount(); i++) {
			if((UIRuleUtil.getString(kdtSupplierAttachList.getCell(i, "name").getValue()).equals("营业执照"))
					&&UIRuleUtil.isNotNull(kdtSupplierAttachList.getCell(i, "attachment").getValue())&&
					!"".equals(UIRuleUtil.getString(kdtSupplierAttachList.getCell(i, "attachment").getValue()))){
				isYValueAttact = false;
			}if((UIRuleUtil.getString(kdtSupplierAttachList.getCell(i, "name").getValue()).equals("税务登记证"))
					&&UIRuleUtil.isNotNull(kdtSupplierAttachList.getCell(i, "attachment").getValue())&&
					!"".equals(UIRuleUtil.getString(kdtSupplierAttachList.getCell(i, "attachment").getValue()))){
				isSValueAttact = false;
			}if((UIRuleUtil.getString(kdtSupplierAttachList.getCell(i, "name").getValue()).equals("组织机构代码证"))
					&&UIRuleUtil.isNotNull(kdtSupplierAttachList.getCell(i, "attachment").getValue())&&
					!"".equals(UIRuleUtil.getString(kdtSupplierAttachList.getCell(i, "attachment").getValue()))){
				isZValueAttact = false;
			}
		}
		if(isYValueAttact||isSValueAttact||isZValueAttact){
			FDCMsgBox.showWarning(this,"附件清单必须要有营业执照、税务登记证、组织机构代码证的附件！");
			SysUtil.abort();
		}
	
	}
	
	protected void verifyInputForSave() throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		FDCClientVerifyHelper.verifyEmpty(this, getNameCtrl());
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtInviteType);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtPurchaseOrgUnit);
	}
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
    	super.actionCopy_actionPerformed(e);
    	FDCHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
    	this.txtStorageNumber.setText(null);
    	this.pkStorageDate.setValue(null);
    	
    	this.cbIsPass.setSelectedItem(null);
    	this.prmtCreator.setValue(null);
    	this.prmtAuditor.setValue(null);
    	this.pkCreateTime.setValue(null);
    	this.pkAuditDate.setValue(null);
    	this.kDTabbedPane2.removeAll();
    	
    	this.editData.setSysSupplier(null);
    	this.editData.setLevel(null);
    	this.editData.setGrade(null);
    	this.editData.put("isPass", null);
    	this.editData.put("sysSupplier", null);
    	this.editData.put("storageNumber", null);
    	this.editData.put("storageDate", null);
    	
    	for (int i = 0; i < editData.getSupplierAttachListEntry().size(); i++) {
    		MarketSupplierStockSupplierAttachListEntryInfo  info = editData.getSupplierAttachListEntry().get(i);
    		info.setId(BOSUuid.create(info.getBOSType()));
		}
    }
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return null;
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo objectValue = new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        MarketSupplierStockInfo info = new MarketSupplierStockInfo();
		try {
			info.setCreateTime(FDCCommonServerHelper.getServerTimeStamp());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if(getUIContext().get("type")!=null){
			info.setInviteType((InviteTypeInfo)getUIContext().get("type"));
		}
		if(getUIContext().get("org")!=null){
			try {
				PurchaseOrgUnitInfo orgUnitInfo = PurchaseOrgUnitFactory.getRemoteInstance().getPurchaseOrgUnitInfo(new ObjectUuidPK(((OrgStructureInfo) getUIContext().get("org")).getUnit().getId()));
				info.setPurchaseOrgUnit(orgUnitInfo);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		info.setHasCreatedSupplierBill(false);
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		info.setAdminCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return info;
    }
	protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}
	protected KDBizMultiLangBox getNameCtrl() {
		return this.txtName;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

}