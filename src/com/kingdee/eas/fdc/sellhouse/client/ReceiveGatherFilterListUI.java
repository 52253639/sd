/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basecrm.client.SHERevBillEditUI;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.sellhouse.GatherTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ReceiveGatherFilterListUI extends AbstractReceiveGatherFilterListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ReceiveGatherFilterListUI.class);
	
    boolean boo = false;
    
    public ReceiveGatherFilterListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();   	
    	initControl();
    	initTable();
    	this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblMain.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
			public void editValueChanged(KDTEditEvent e)
			{
//				tblMain_editValueChanged(e);
			}			
        });
    }
    
    private void tblMain_editValueChanged(KDTEditEvent e) {
    	int colIndex = e.getColIndex();
		String colKey = this.tblMain.getColumnKey(colIndex);		
        
        if("isSelect".equals(colKey))
        {
        	IRow row = this.tblMain.getRow(e.getRowIndex());  
            String id= (String)row.getCell("id").getValue();
            boolean boo = ((Boolean)e.getValue()).booleanValue();
            for(int i=0;i<tblRevEntry.getRowCount();i++)
        	{
        		IRow row2 = tblRevEntry.getRow(i);
        		String headID = (String)row2.getCell("headID").getValue();
        		if(headID.equals(id))
        		{
        			row2.getCell("isSelect").setValue(new Boolean(boo));
        		}
        	}
        }
	}
    protected void initControl()
    {
    	this.actionAttachment.setVisible(false);
    	this.actionAddNew.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionView.setVisible(false);
		this.menuBar.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuFile.setVisible(false);
		this.menuHelp.setVisible(false);
		this.MenuService.setVisible(false);
		this.actionCondition.setVisible(true);
		this.actionCondition.setEnabled(true);
    }
    
    protected void initTable()
    {
    	this.tblMain.checkParsed();
    	this.tblRevEntry.checkParsed();
    	this.tblMain.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);	
    	this.tblRevEntry.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);	
    	KDCheckBox chkBox = new KDCheckBox();
		ICellEditor checkBox = new KDTDefaultCellEditor(chkBox);
//		this.tblMain.getColumn("isSelect").setEditor(checkBox);
//		this.tblMain.getColumn("isSelect").getStyleAttributes().setHided(true);
		
		chkBox = new KDCheckBox();
		checkBox = new KDTDefaultCellEditor(chkBox);
		this.tblRevEntry.getColumn("isSelect").setEditor(checkBox);
    }     
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		if(queryPK.equals(this.mainQueryPK)){
			try {
				viewInfo = (EntityViewInfo) this.mainQuery.clone();
				FilterInfo filter = new FilterInfo();
				SellProjectInfo project = (SellProjectInfo)this.getUIContext().get("sellProject");
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", project.getId().toString()));			
				if (viewInfo.getFilter() != null) {
					viewInfo.getFilter().mergeFilter(filter, "and");
				} else {
					viewInfo.setFilter(filter);
				}
			} catch (Exception e) {
				handleException(e);
			}
		}     	
		IQueryExecutor  queryExe = super.getQueryExecutor(queryPK, viewInfo);
		queryExe.option().isAutoTranslateEnum = true;
		return queryExe;
	}
    
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
    	super.actionQuery_actionPerformed(e);	
    }   

    public void storeFields()
    {
        super.storeFields();
    }
    
    protected boolean isIgnoreCUFilter() {
    	return true;
    }
    
    protected ICoreBase getBizInterface() throws Exception {
    	return SHERevBillFactory.getRemoteInstance();
    }
    
    protected String[] getLocateNames() {
    	return super.getLocateNames();
    }
    
    protected  void fetchInitData() throws Exception{
    	
    }

	protected String getEditUIName() {
		return SHERevBillEditUI.class.getName();
	}
	
	protected void settblMainIsSelect()
	{
		for(int i=0;i<tblMain.getRowCount();i++)
		{
			IRow row = (IRow)tblMain.getRow(i);
//			row.getCell("isSelect").setValue(Boolean.valueOf(true));
//			row.getCell("isSelect").getStyleAttributes().setLocked(false);
//			row.getCell("isSelect").getStyleAttributes().setHided(true);
		}
	}
	
	public void actionCondition_actionPerformed(ActionEvent e) throws Exception {
		UIContext uicontext = new UIContext(this);
		uicontext.put("sellProject", this.getUIContext().get("sellProject"));
		uicontext.put("params", this.getUIContext().get("params"));
		uicontext.put("owner", this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ConditionGatherFilterUI.class.getName(), 
				uicontext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	
	public void updateTable(CustomerParams params){
		//用来判断用户是否打开了明细条件查询界面
		boo = true;
		FDCCustomerParams param  = new FDCCustomerParams(params);
		this.getUIContext().put("params", params);
		FilterInfo filter = new FilterInfo();
		Date startDate = param.getDate("bizStartDate");
		Date endDate = param.getDate("bizEndDate");		
//		String buildingID = param.getString("building");
//		String buildUnitID = param.getString("unit");
		String moneyDefineID = param.getString("moneyDefine");
		String settlementID = param.getString("settlement");
		String accountBankID = param.getString("accountBank");
		String bankNumber = param.getString("bankNumber");
		String revType = param.getString("revType");
		String gatherType = param.getString("gatherType");
		//已汇总的明细不允许再汇总
		filter.getFilterItems().add(new FilterItemInfo("isGathered",Boolean.TRUE,CompareType.NOTEQUALS));
		if(startDate!=null) 
		{
			filter.getFilterItems().add(new FilterItemInfo("parent.bizDate",startDate,CompareType.GREATER));		    
		}	
		if(endDate !=null)
		{
			endDate = FDCDateHelper.getNextDay(endDate);
			filter.getFilterItems().add(new FilterItemInfo("parent.bizDate",endDate,CompareType.LESS));
		}
//		if(buildingID!=null)
//		{
//			filter.getFilterItems().add(new FilterItemInfo("building.id",buildingID,CompareType.EQUALS));
//		}
//		if(buildUnitID!=null)
//		{
//			filter.getFilterItems().add(new FilterItemInfo("buildUnit.id",buildUnitID,CompareType.EQUALS));
//		}
		if(moneyDefineID!=null)
		{
			filter.getFilterItems().add(new FilterItemInfo("moneyDefine.id",moneyDefineID,CompareType.EQUALS));
		}
		if(settlementID!=null)
		{
			filter.getFilterItems().add(new FilterItemInfo("settlementType.id",settlementID,CompareType.EQUALS));
		}
		if(accountBankID!=null)
		{
			filter.getFilterItems().add(new FilterItemInfo("revAccountBank.id",accountBankID,CompareType.EQUALS));
		}
		if(!bankNumber.equals("") && bankNumber!=null)
		{
			filter.getFilterItems().add(new FilterItemInfo("revAccountNumber",bankNumber,CompareType.EQUALS));
		}
		if(!revType.equals("") && revType!=null)
		{
			if(RevBillTypeEnum.GATHERING_VALUE.equals(revType))
			{
				//如果收款单据类型为收款，则明细也只能是收款的明细
				this.comboBillType.setSelectedItem(RevBillTypeEnum.gathering);
				this.comboGatherType.setSelectedItem(GatherTypeEnum.ReceiveGather);
				filter.getFilterItems().add(new FilterItemInfo("parent.revBillType",RevBillTypeEnum.GATHERING_VALUE,CompareType.EQUALS));
			}else if(RevBillTypeEnum.REFUNDMENT_VALUE.equals(revType))
			{
				//如果收款单据类型为退款，则明细也只能是退款的明细
				this.comboBillType.setSelectedItem(RevBillTypeEnum.refundment);
				this.comboGatherType.setSelectedItem(GatherTypeEnum.RefumentGather);
				filter.getFilterItems().add(new FilterItemInfo("parent.revBillType",RevBillTypeEnum.REFUNDMENT_VALUE,CompareType.EQUALS));
			}
			if(RevBillTypeEnum.TRANSFER_VALUE.equals(revType))
			{
				//如果收款单据类型为转款，则明细根据汇总类型来确定
				this.comboBillType.setSelectedItem(RevBillTypeEnum.transfer);
				if(!gatherType.equals("") && gatherType!=null)
				{
					if(GatherTypeEnum.BILLGATHER_VALUE.equals(gatherType))
					{
						//汇总类型为单据汇总，则明细为该转款单对应的转款明细
						this.comboGatherType.setSelectedItem(GatherTypeEnum.BillGather);
						filter.getFilterItems().add(new FilterItemInfo("parent.revBillType",RevBillTypeEnum.TRANSFER_VALUE,CompareType.EQUALS));
						filter.getFilterItems().add(new FilterItemInfo("parent.id", getSelectIDSet(),CompareType.INCLUDE));
//						if(getSelectIDSet().size()>0)
//						{								
//							filter.getFilterItems().add(new FilterItemInfo("parent.id", getSelectIDSet(),CompareType.INCLUDE));								
//						}else
//						{
//							filter.getFilterItems().add(new FilterItemInfo("parent.id", null));
//						}
					}else if(GatherTypeEnum.RECEIVEGATHER_VALUE.equals(gatherType))
					{
						//如果汇总类型为按收款明细汇总，则需要找转款单生成的对应的收款单明细
						this.comboGatherType.setSelectedItem(GatherTypeEnum.ReceiveGather);
					try {
						if(getSelectTransID(RevBillTypeEnum.GATHERING_VALUE).size()>0)
						{								
								filter.getFilterItems().add(new FilterItemInfo("parent.id", getSelectTransID(RevBillTypeEnum.GATHERING_VALUE),CompareType.INCLUDE));								
						}else
						{
							filter.getFilterItems().add(new FilterItemInfo("parent.id", null));
						}
					} catch (BOSException e1) {
							e1.printStackTrace();
					}
					}else if(GatherTypeEnum.REFUMENTGATHER_VALUE.equals(gatherType))
					{
						//如果汇总类型为按退款明细汇总，则需要找转款单生成的对应的退款单明细
						this.comboGatherType.setSelectedItem(GatherTypeEnum.RefumentGather);
					try {
						if(getSelectTransID(RevBillTypeEnum.REFUNDMENT_VALUE).size()>0)
						{								
								filter.getFilterItems().add(new FilterItemInfo("parent.id", getSelectTransID(RevBillTypeEnum.REFUNDMENT_VALUE),CompareType.INCLUDE));								
						}else
						{
							filter.getFilterItems().add(new FilterItemInfo("parent.id", null));
						}
					} catch (BOSException e1) {
							e1.printStackTrace();
					}
					}												
				}
			}else
			{
				if(getSelectIDSet().size()>0)
				{								
					filter.getFilterItems().add(new FilterItemInfo("parent.id", getSelectIDSet(),CompareType.INCLUDE));								
				}else
				{
					filter.getFilterItems().add(new FilterItemInfo("parent.id", null));
				}
			}
			if (sHEReceiveBillEntryQuery == null) {
				setDataObject("sHEReceiveBillEntryQuery",new EntityViewInfo());
			}					
				sHEReceiveBillEntryQuery.setFilter(filter);
				tblRevEntry.removeRows();
				tblRevEntry.getSelectManager().setSelectMode(
						KDTSelectManager.ROW_SELECT);
				tblRevEntry.getStyleAttributes().setLocked(true);
				settblRevEntryIsSelect();
			}
	}
	
	protected void afterTableFillData(KDTDataRequestEvent e) {
//		settblMainIsSelect();		
		super.afterTableFillData(e);
		FilterInfo filter = new FilterInfo();
		
		if (sHEReceiveBillEntryQuery == null) {
			setDataObject("sHEReceiveBillEntryQuery",new EntityViewInfo());
		}	
		if(getSelectIDSet().size()>0)
		{								
				filter.getFilterItems().add(new FilterItemInfo("parent.id", getSelectIDSet(),CompareType.INCLUDE));								
		}else
		{
			filter.getFilterItems().add(new FilterItemInfo("parent.id", null));
		}
		filter.getFilterItems().add(new FilterItemInfo("isGathered",Boolean.TRUE,CompareType.NOTEQUALS));
		
		
		sHEReceiveBillEntryQuery.setFilter(filter);
		tblRevEntry.removeRows();
		tblRevEntry.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblRevEntry.getStyleAttributes().setLocked(true);
		settblRevEntryIsSelect();				
	}
	
	protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {
		super.btnConfirm_actionPerformed(e);
		if(tblRevEntry.getRowCount()==0)
		{
			MsgBox.showInfo("至少选中一条收付款明细!");
			this.abort();
		}
		UIContext uiContext = new UIContext(this);
		Set idSet = new HashSet();
		for(int i=0;i<tblRevEntry.getRowCount();i++)
		{
			IRow row = tblRevEntry.getRow(i);
			boolean boo = ((Boolean)row.getCell("isSelect").getValue()).booleanValue();
			if(boo)
			{
				String id = (String)row.getCell("id").getValue();
				idSet.add(id);
			}
		}
		this.getUIWindow().close();
		if(boo)
		{
			GatherTypeEnum gatherType = (GatherTypeEnum)this.comboGatherType.getSelectedItem();
			RevBillTypeEnum revBillType = (RevBillTypeEnum)this.comboBillType.getSelectedItem();
			uiContext.put("gatherType", gatherType);
			uiContext.put("revBillType", revBillType);
		}else
		{
			Set sheRevIDSet = this.getSelectEntryIDSet();
			if(sheRevIDSet.size()>0)
			{
				Set revTypeSet = new HashSet();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				view.getSelector().add("id");
				view.getSelector().add("revBillType");
				filter.getFilterItems().add(new FilterItemInfo("id",sheRevIDSet,CompareType.INCLUDE));
				view.setFilter(filter);
				SHERevBillCollection sheRevColl = SHERevBillFactory.getRemoteInstance().getSHERevBillCollection(view);
				for(int i=0;i<sheRevColl.size();i++)
				{
					SHERevBillInfo sheRevInfo = sheRevColl.get(i);
					RevBillTypeEnum revType = sheRevInfo.getRevBillType();
					revTypeSet.add(revType);				
				}
				if(revTypeSet.size()>1)
				{
					MsgBox.showInfo("不能同时汇总收款单据类型不同的收款单!");
					this.abort();
				}else
				{
					SHERevBillInfo sheRevInfo = sheRevColl.get(0);
					RevBillTypeEnum revType = sheRevInfo.getRevBillType();
					if(RevBillTypeEnum.gathering.equals(revType))
					{
						uiContext.put("gatherType", GatherTypeEnum.ReceiveGather);
						uiContext.put("revBillType",RevBillTypeEnum.gathering);
					}else if(RevBillTypeEnum.refundment.equals(revType))
					{
						uiContext.put("gatherType",GatherTypeEnum.RefumentGather);
						uiContext.put("revBillType", RevBillTypeEnum.refundment);
					}else if(RevBillTypeEnum.transfer.equals(revType))
					{
						uiContext.put("gatherType", GatherTypeEnum.BillGather);
						uiContext.put("revBillType",RevBillTypeEnum.transfer );
					}
				}
				
			}
		}
		
		
		uiContext.put("revEntryIDSet", idSet);
		uiContext.put("sellProject", this.getUIContext().get("sellProject"));
		
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow curDialog = uiFactory.create(ReceiveGatherEditUI.class.getName(),uiContext,null, OprtState.ADDNEW);
		curDialog.show();
	}
	
	protected void settblRevEntryIsSelect()
	{
		for(int i=0;i<tblRevEntry.getRowCount();i++)
		{
			IRow row = tblRevEntry.getRow(i);
			row.getCell("isSelect").setValue(Boolean.valueOf(true));
			row.getCell("isSelect").getStyleAttributes().setLocked(false);
		}
	}
	
	protected Set getSelectIDSet()
	{
		Set set = new HashSet();
		for(int i=0;i<tblMain.getRowCount();i++)
		{
			IRow row = (IRow)tblMain.getRow(i);
			String id = (String)row.getCell("id").getValue();
			set.add(id);
		}
		return set;
	}	
	
	//明细分录中选中的收款单ID的集合
	protected Set getSelectEntryIDSet()
	{
		Set set = new HashSet();
		for(int i=0;i<tblRevEntry.getRowCount();i++)
		{
			IRow row = (IRow)tblRevEntry.getRow(i);
			boolean isSelect = ((Boolean)row.getCell("isSelect").getValue()).booleanValue();
			if(isSelect)
			{
				String id = (String)row.getCell("headID").getValue();
				set.add(id);
			}			
		}
		return set;
	}
	
	//通过选中的转款单找到对应生成的收款单或退款单
	protected Set getSelectTransID(String strRevType) throws BOSException
	{
		Set set = new HashSet();
		Set revSet = new HashSet();
		Set refundSet = new HashSet();
		for(int i=0;i<tblMain.getRowCount();i++)
		{
			IRow row = (IRow)tblMain.getRow(i);
			BizEnumValueInfo revTypeEnum = (BizEnumValueInfo)row.getCell("sheReceiveType").getValue();
			String revType = (String)revTypeEnum.getValue();
//			String revType = (String)row.getCell("sheReceiveType").getValue();
			if(RevBillTypeEnum.TRANSFER_VALUE.equals(revType))
			{
				String id = (String)row.getCell("id").getValue();
				set.add(id);
			}			
		}
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sellColl = new SelectorItemCollection();
			sellColl.add("trsToGatherId");
			sellColl.add("trsToRefundId");
			sellColl.add("isTansCreate");
			sellColl.add("isGathered");
			view.setSelector(sellColl);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
			view.setFilter(filter);
			SHERevBillCollection sheBillColl = SHERevBillFactory.getRemoteInstance().getSHERevBillCollection(view);
			for(int i=0;i<sheBillColl.size();i++)
			{
				SHERevBillInfo sheRevBillInfo = sheBillColl.get(i);
				String trsToGatherId = sheRevBillInfo.getTrsToGatherId();
				String trsToRefundId = sheRevBillInfo.getTrsToRefundId();
				revSet.add(trsToGatherId);
				refundSet.add(trsToRefundId);
			}
			if(RevBillTypeEnum.GATHERING_VALUE.equals(strRevType))
			{
				return revSet;
			}else
			{
				return refundSet;
			}							
	}
}