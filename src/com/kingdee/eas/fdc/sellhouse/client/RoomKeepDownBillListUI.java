/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;

import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.propertymgmt.IPPMBill;
import com.kingdee.eas.fdc.propertymgmt.client.PPMHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.IRoomKeepDownBill;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillFactory;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillInfo;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBizEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.fm.common.DateHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
/**
 * output class name
 */
public class RoomKeepDownBillListUI extends AbstractRoomKeepDownBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomKeepDownBillListUI.class);
    protected boolean isSaleHouseOrg= FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
    
//    SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    
    Map orgMap = FDCSysContext.getInstance().getOrgMap();
    Date currentDate = FDCHelper.getCurrentDate();
    /**
     * output class constructor
     */
    public RoomKeepDownBillListUI() throws Exception
    {
        super();
       
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    public void loadFields()
    {
        super.loadFields();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	initTree();
		this.treeMain.setSelectionRow(0);
//		if(this.tblMain.getRowCount()>=0){
////		verifyKeepDown();
//		}
		initBtnState();
		if(!isSaleHouseOrg){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.btnAudit.setEnabled(false);
			this.btnCancelKeepDown.setEnabled(false);
			this.btnTransPrePurchase.setEnabled(false);
			this.btnTransPurchase.setEnabled(false);
			this.btnTransSign.setEnabled(false);
		
		}else{
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
		}
		this.actionEdit.setEnabled(true);
//		this.actionAddNew.setEnabled(false);
//		this.actionEdit.setEnabled(false);
//		this.actionRemove.setEnabled(false);
		//3个按钮
		this.btnTransPrePurchase.setIcon(EASResource.getIcon("imgTbtn_post"));
		this.btnTransPurchase.setIcon(EASResource.getIcon("imgTbtn_stafferbatch"));
		this.btnTransSign.setIcon(EASResource.getIcon("imgTbtn_initialize"));
		this.btnAudit.setText("审批");
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    }
    public void refresh (ActionEvent e)throws Exception {
    	this.tblMain.removeRows();
    }
    public void initBtnState() throws Exception {
    	this.btnAudit.setEnabled(true);
    	this.btnAudit.setVisible(true);
    	this.actionAudit.setEnabled(true);
    	this.actionAudit.setVisible(true);
    }
    
    public void verifyKeepDown(){
    	Date currentDate = FDCHelper.getCurrentDate();
    	
    
    	for(int i =0 ; i<this.tblMain.getRowCount();i++){
    		IRow row = this.tblMain.getRow(i);
    		BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("state").getValue();
    		if(RoomKeepDownBizEnum.CANCELKEEPDOWN_VALUE.equals(state.getValue())){
    		Date bizDate = (Date)row.getCell("bizDate").getValue();
    		int keepDownDay = ((Integer)row.getCell("keepDate").getValue()).intValue();
    		int dayDiff=0;
    		if(currentDate.after(bizDate)){
    		
    			dayDiff = getDaysBetween(transForCalendarDate(currentDate),transForCalendarDate(bizDate));
    		
    		
    			if(dayDiff>keepDownDay)
    			{
    				this.tblMain.getRow(i).getStyleAttributes().setBackground(Color.RED);
    			}
    		}
    		}
    	}
    	
    }
    
    
    protected void initListener() {
		super.initListener();

		/**
		 * 做数据的处理来优化性能 by renliang at 2011-3-23
		 */
		this.tblMain.getDataRequestManager().addDataFillListener(
				new KDTDataFillListener() {
					public void afterDataFill(KDTDataRequestEvent e) {
						tblMain_afterDataFill(e);
					}
				});
	}

	private void tblMain_afterDataFill(KDTDataRequestEvent e) {

		// 取得当前页的第一行
		int sr = e.getFirstRow();
		if (e.getFirstRow() > 0) {
			sr = sr - 1;
		}
		// 取得当前页的最后一行
		int lr = e.getLastRow();

		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (int i = sr; i <= lr; i++) {
			IRow row = tblMain.getRow(i);
			if (row == null) {
				continue;
			}
			BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("state").getValue();
    		if(!RoomKeepDownBizEnum.CANCELKEEPDOWN_VALUE.equals(state.getValue())){
			Date bizDate = (Date)row.getCell("bizDate").getValue();
    		int keepDownDay = ((Integer)row.getCell("keepDate").getValue()).intValue();
    		int dayDiff=0;
    		if(currentDate.after(bizDate)){
    		
    			dayDiff = getDaysBetween(transForCalendarDate(currentDate),transForCalendarDate(bizDate));
    		
    		
    			if(dayDiff>keepDownDay)
    			{
    				this.tblMain.getRow(i).getStyleAttributes().setBackground(Color.PINK);
    			}
    		
    		}else if(keepDownDay == 0){
    			this.tblMain.getRow(i).getStyleAttributes().setBackground(Color.PINK);
    		}
    		}
		}
	}
    public Calendar transForCalendarDate(Date date){
    	
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	return cal;
    }
    public int getDaysBetween (Calendar d1, Calendar d2){
        if (d1.after(d2)){
            java.util.Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2){
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }



protected void initTree() throws Exception {
	this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
	this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
}
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof OrgUnitInfo) {
			OrgUnitInfo orgUnit = (OrgUnitInfo) node.getUserObject();
			uiContext.put(FDCConstants.FDC_INIT_ORGUNIT, orgUnit.getId());
			
		} else if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
				uiContext.put(FDCConstants.FDC_INIT_PROJECT, sellProject);
			
		}
	}
    

//	
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof OrgStructureInfo){
			this.actionAddNew.setEnabled(false);
		}else if(node.getUserObject() instanceof SellProjectInfo){
			if(SHEManageHelper.isSellProjectHasChild((SellProjectInfo) node.getUserObject())){
				this.actionAddNew.setEnabled(false);
			}else if(isSaleHouseOrg){
				this.actionAddNew.setEnabled(true);
			}
		}else{
			if(isSaleHouseOrg){
				this.actionAddNew.setEnabled(true);
			}
		}
		this.refresh(null);
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		/*try {
			FilterInfo roomFilter = getRoomFilter();
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(roomFilter, "and");
			} else {
				viewInfo.setFilter(roomFilter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.getQueryExecutor(queryPK, viewInfo);*/
//		//不用销售组织判断,改为售楼组织
//		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
//		String idStr =null;
//		if(null!= orgUnit.getId()){
//			idStr = orgUnit.getId().toString();
//		}
//		if(null==orgMap.get(idStr)){
//			this.actionAddNew.setEnabled(false);
//			this.actionEdit.setEnabled(false);
//			this.actionRemove.setEnabled(false);
//		
//		}else{
//			this.actionAddNew.setEnabled(true);
//			this.actionEdit.setEnabled(true);
//			this.actionRemove.setEnabled(true);
//		}
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(node==null) node = (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
		
		try	{
			FilterInfo filter = new FilterInfo();
			if(node!=null){
				if (node.getUserObject() instanceof SellProjectInfo)		{
					SellProjectInfo spInfo = (SellProjectInfo)node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("project.id", spInfo.getId().toString()));
				}
				if (node.getUserObject() instanceof OrgStructureInfo)		{
					OrgStructureInfo osInfo = (OrgStructureInfo)node.getUserObject();
					if(osInfo.getId()!=null){
						filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", osInfo.getId().toString()));
					}else{
						filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",null));
					}
					
				}
			}
			
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		} catch (Exception e)
		{
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected KDTree getTreeCtrl() {
		return this.treeMain;
	}

//	protected String getTreeType() {
//		return "sellProject";
//	}

    	


    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	int index = this.tblMain.getSelectManager().getActiveRowIndex();
	
	if (index < 0) {
		FDCMsgBox.showWarning("请选择预留单！");
		SysUtil.abort();
	} else {
		if (null != this.tblMain.getRow(index)) {
			String idStr = this.tblMain.getRow(index).getCell("id").getValue().toString();
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("bizState");
			RoomKeepDownBillInfo roomkeepdown = (RoomKeepDownBillInfo) ((IRoomKeepDownBill) this.getBizInterface()).getRoomKeepDownBillInfo(new ObjectUuidPK(BOSUuid.read(idStr)), sels);
			if (null == idStr || "".equals(idStr)) {
				FDCMsgBox.showWarning("无此数据！");
				SysUtil.abort();
			} else {
				if (RoomKeepDownBizEnum.Auditted.equals(roomkeepdown.getBizState())){
					
						FDCMsgBox.showWarning("已审批单据不能修改!");
						SysUtil.abort();
				} else if(RoomKeepDownBizEnum.CancelKeepDown.equals(roomkeepdown.getBizState())){
					FDCMsgBox.showWarning("取消预留单据不能修改!");
					SysUtil.abort();
				}
				 super.actionEdit_actionPerformed(e);
				 this.refresh(e);
			}
		}
		
	}
    	
       
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	int index = this.tblMain.getSelectManager().getActiveRowIndex();
		
		if (index < 0) {
			FDCMsgBox.showWarning("请选择预留单！");
			SysUtil.abort();
		} else {
			if (null != this.tblMain.getRow(index)) {
				String idStr = this.tblMain.getRow(index).getCell("id").getValue().toString();
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("bizState");
				RoomKeepDownBillInfo roomkeepdown = (RoomKeepDownBillInfo) ((IRoomKeepDownBill) this.getBizInterface()).getRoomKeepDownBillInfo(new ObjectUuidPK(BOSUuid.read(idStr)), sels);
				if (null == idStr || "".equals(idStr)) {
					FDCMsgBox.showWarning("无此数据！");
					SysUtil.abort();
				} else {
					if (RoomKeepDownBizEnum.Submitted.equals(roomkeepdown.getBizState())){
						super.actionRemove_actionPerformed(e);
						 this.refresh(e);
					} else {
						FDCMsgBox.showWarning("单据状态不适合删除！");
						SysUtil.abort();
					}
				}
			}
			
		}
	
		  
    }
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		
		if (index < 0) {
			FDCMsgBox.showWarning("请选择预留单！");
			SysUtil.abort();
		} else {
			if (null != this.tblMain.getRow(index)) {
				String idStr = this.tblMain.getRow(index).getCell("id").getValue().toString();
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("bizState");
				RoomKeepDownBillInfo roomkeepdown = (RoomKeepDownBillInfo) ((IRoomKeepDownBill) this.getBizInterface()).getRoomKeepDownBillInfo(new ObjectUuidPK(BOSUuid.read(idStr)), sels);
				if (null == idStr || "".equals(idStr)) {
					FDCMsgBox.showWarning("无此数据！");
					SysUtil.abort();
				} else {
					 SelectorItemCollection selc = new SelectorItemCollection();
					  selc.add("bizState");
					 RoomKeepDownBillInfo roomkeepDown = ((IRoomKeepDownBill)getBizInterface()).
					 getRoomKeepDownBillInfo(new ObjectUuidPK(idStr),selc);
					  
					  
					 //这里写的比较恶心回来改
					  if(RoomKeepDownBizEnum.Submitted.equals(roomkeepDown.getBizState())){
						
						  roomkeepDown.setId(BOSUuid.read(idStr));
						  roomkeepDown.setBizState(RoomKeepDownBizEnum.Auditted);
					  
					  	
					    	((IRoomKeepDownBill)this.getBillInterface()).audit(BOSUuid.read(idStr));
					    	this.getBillInterface().updatePartial(roomkeepDown, selc);
					  } else {
						FDCMsgBox.showWarning("您所选择的单据不适合做审批操作！");
						SysUtil.abort();
					}
				}
			}
			
		}
		super.actionAudit_actionPerformed(e);
		this.refresh(e);
	}
    
    /**
	 * output 审批取消预留
	 */
	public void actionCancelKeepDown_actionPerformed(ActionEvent e) throws Exception {
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
		if (index < 0) {
			FDCMsgBox.showWarning("请选择预留单！");
			SysUtil.abort();
		} else {
			if (null != this.tblMain.getRow(index)) {
				
				
				String idStr = this.tblMain.getRow(index).getCell("id").getValue().toString();
				FDCClientUtils.checkBillInWorkflow(this, idStr);
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("bizState");
				sels.add("room");
				sels.add("room.*");
				RoomKeepDownBillInfo roomkeepdown = (RoomKeepDownBillInfo) ((IRoomKeepDownBill) this.getBizInterface()).getRoomKeepDownBillInfo(new ObjectUuidPK(BOSUuid.read(idStr)), sels);
				// 校验审批状态
				if (!(RoomKeepDownBizEnum.Submitted.equals(roomkeepdown.getBizState()))&&!(RoomKeepDownBizEnum.Auditted.equals(roomkeepdown.getBizState()))) {
					FDCMsgBox.showWarning("只有提交和审批状态下的单据可取消预留！");
					SysUtil.abort();
				}
				if (null == idStr || "".equals(idStr)) {
					FDCMsgBox.showWarning("无此数据！");
					SysUtil.abort();
				} else {
					if(null!=roomkeepdown.getRoom()){
						if (RoomSellStateEnum.KeepDown.equals(roomkeepdown.getRoom().getSellState())) {// 校验是否预留状态房屋 by tim_gao
							((IRoomKeepDownBill) this.getBizInterface()).cancelKeepDown(roomkeepdown.getRoom().getId().toString(),idStr);
						} else {
						FDCMsgBox.showWarning("房间不是预留状态！");
						SysUtil.abort();
						}
					}else{
						((IRoomKeepDownBill) this.getBizInterface()).cancelKeepDown(null,idStr);
					}

				}
			}
			
		}
		super.actionCancelKeepDown_actionPerformed(e);
		this.refresh(e);
	}
    /**
     * output 审批转预定
     */

    /**
     * output actionPrePurchase_actionPerformed method
     */
    public void actionPrePurchase_actionPerformed(ActionEvent e) throws Exception
    {	
    	
    	this.checkSelected();
    	int index = this.tblMain.getSelectManager().getActiveRowIndex();
    	String id = this.tblMain.getRow(index).getCell("id").getValue().toString();
    	RoomKeepDownBillInfo info = (RoomKeepDownBillInfo)((IRoomKeepDownBill) this.getBizInterface()).getRoomKeepDownBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
		if(!RoomKeepDownBizEnum.CancelKeepDown.equals(info.getBizState())){
			FDCMsgBox.showWarning("只有取消预留状态下的单据才能做此操作！");
			SysUtil.abort();
		}
    	
    	List customer=new ArrayList();
		for(int i=0;i<info.getCustomerEntry().size();i++){
			customer.add(info.getCustomerEntry().get(i).getCustomer());
		}
		SHEManageHelper.toTransactionBill(BOSUuid.read(id),info.getRoom().getId(), this,PrePurchaseManageEditUI.class.getName(),customer);
    }
    /**
	 * output 审批转认购
	 */
    
    public void actionPurchase_actionPerformed(ActionEvent e) throws Exception
    {
    	this.checkSelected();
    	int index = this.tblMain.getSelectManager().getActiveRowIndex();
    	String id = this.tblMain.getRow(index).getCell("id").getValue().toString();
    	RoomKeepDownBillInfo info = (RoomKeepDownBillInfo)((IRoomKeepDownBill) this.getBizInterface()).getRoomKeepDownBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
    	if(!RoomKeepDownBizEnum.CancelKeepDown.equals(info.getBizState())){
			FDCMsgBox.showWarning("只有取消预留状态下的单据才能做此操作！");
			SysUtil.abort();
		}
    	List customer=new ArrayList();
		for(int i=0;i<info.getCustomerEntry().size();i++){
			customer.add(info.getCustomerEntry().get(i).getCustomer());
		}
		SHEManageHelper.toTransactionBill(BOSUuid.read(id),info.getRoom().getId(), this,PurchaseManageEditUI.class.getName(),customer);
	}
    
	
    /**
     * output 审批转签约
     */
    public void actionTransSign_actionPerformed(ActionEvent e) throws Exception
    {	
    	this.checkSelected();
    	int index = this.tblMain.getSelectManager().getActiveRowIndex();
    	String id = this.tblMain.getRow(index).getCell("id").getValue().toString();
    	RoomKeepDownBillInfo info = (RoomKeepDownBillInfo)((IRoomKeepDownBill) this.getBizInterface()).getRoomKeepDownBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
    	if(!RoomKeepDownBizEnum.CancelKeepDown.equals(info.getBizState())){
			FDCMsgBox.showWarning("只有取消预留状态下的单据才能做此操作！");
			SysUtil.abort();
		}
    	List customer=new ArrayList();
		for(int i=0;i<info.getCustomerEntry().size();i++){
			customer.add(info.getCustomerEntry().get(i).getCustomer());
		}
		SHEManageHelper.toTransactionBill(BOSUuid.read(id),info.getRoom().getId(), this,SignManageEditUI.class.getName(),customer);
    }
    
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return RoomKeepDownBillFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return RoomKeepDownBillEditUI.class.getName();
	}

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
//        verifyKeepDown();
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
	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
		int index = this.tblMain.getSelectManager().getActiveRowIndex();
    	String id = this.tblMain.getRow(index).getCell("id").getValue().toString();
    	RoomKeepDownBillInfo info = (RoomKeepDownBillInfo)((IRoomKeepDownBill) this.getBizInterface()).getRoomKeepDownBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
    	if(isSaleHouseOrg){
    		if(RoomKeepDownBizEnum.CancelKeepDown.equals(info.getBizState())){
    			this.btnTransPrePurchase.setEnabled(true);
    			this.btnTransPurchase.setEnabled(true);
    			this.btnTransSign.setEnabled(true);
    			this.actionEdit.setEnabled(false);
    			this.actionRemove.setEnabled(false);
    			this.actionAudit.setEnabled(false);
    			this.actionCancelKeepDown.setEnabled(false);
    		}else if(RoomKeepDownBizEnum.Submitted.equals(info.getBizState())){
    			this.btnTransPrePurchase.setEnabled(false);
    			this.btnTransPurchase.setEnabled(false);
    			this.btnTransSign.setEnabled(false);
    			this.actionEdit.setEnabled(true);
    			this.actionRemove.setEnabled(true);
    			this.actionAudit.setEnabled(true);
    			this.actionCancelKeepDown.setEnabled(true);
    		}else if(RoomKeepDownBizEnum.Auditted.equals(info.getBizState())){
    			this.btnTransPrePurchase.setEnabled(false);
    			this.btnTransPurchase.setEnabled(false);
    			this.btnTransSign.setEnabled(false);
    			this.actionEdit.setEnabled(false);
    			this.actionRemove.setEnabled(false);
    			this.actionAudit.setEnabled(false);
    			this.actionCancelKeepDown.setEnabled(true);
    		}
    	}else{
    		this.btnTransPrePurchase.setEnabled(false);
			this.btnTransPurchase.setEnabled(false);
			this.btnTransSign.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionCancelKeepDown.setEnabled(false);
		}
	}
}