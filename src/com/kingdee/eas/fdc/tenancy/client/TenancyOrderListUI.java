/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.TenancyOrderFactory;
import com.kingdee.eas.fdc.tenancy.TenancyOrderInfo;
import com.kingdee.eas.fdc.tenancy.TenancyOrderRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyOrderRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TenancyOrderListUI extends AbstractTenancyOrderListUI
{
    private static final Logger logger = CoreUIObject.getLogger(TenancyOrderListUI.class);
    
    SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    
    /**
     * output class constructor
     */
    public TenancyOrderListUI() throws Exception
    {
        super();
    }
    
    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        //super.tblMain_tableSelectChanged(e);
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
       // super.treeMain_valueChanged(e);
    	DefaultKingdeeTreeNode deKdtreeNode = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
    	if(deKdtreeNode==null)
    		return;
    	
    	if(deKdtreeNode.getUserObject() instanceof SellProjectInfo) {
    		if(this.saleOrg.isIsBizUnit())
    			this.actionAddNew.setEnabled(true);    		
    	}else{
    		this.actionAddNew.setEnabled(false);
    	}
    	this.execQuery();
    }
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
    	viewInfo = (EntityViewInfo) this.mainQuery.clone();
    	
    	FilterInfo filter = new FilterInfo();
    	FilterItemInfo itemInfo = null; 
    	if(node.getUserObject() instanceof SellProjectInfo){
    		SellProjectInfo sellPro = (SellProjectInfo)node.getUserObject();
    		itemInfo = new FilterItemInfo("sellProject.id",sellPro.getId());
    	}else{
    		itemInfo = new FilterItemInfo("id",null);
    	}
    	filter.getFilterItems().add(itemInfo);
    	
    	if(viewInfo.getFilter()==null)
    		viewInfo.setFilter(filter);
    	else{
    		try{
    			viewInfo.getFilter().mergeFilter(filter,"and");
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    	
		return super.getQueryExecutor(queryPK, viewInfo); 
	}
    
    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        //super.actionRemove_actionPerformed(e);
    	String id = this.getSelectedKeyValue();
    	if(id==null) return;
    	
    	IObjectPK pk = new ObjectUuidPK(BOSUuid.read(id));
    	TenancyOrderInfo tenOrder = TenancyOrderFactory.getRemoteInstance().getTenancyOrderInfo(pk);
    	if(tenOrder.isIsEnabled()){
    		MsgBox.showError("批次已经执行,不能删除!");
    		return;
    	}
    	
    	if(this.confirmRemove()) {
    		this.Remove();
    	}

    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	String id = this.getSelectedKeyValue();
    	if(id==null) return;
    	
    	IObjectPK pk = new ObjectUuidPK(BOSUuid.read(id));
    	TenancyOrderInfo tenOrder = TenancyOrderFactory.getRemoteInstance().getTenancyOrderInfo(pk);
    	if(tenOrder.isIsEnabled()){
    		MsgBox.showError("批次已经执行,不能修改!");
    		return;
    	}
    	super.actionEdit_actionPerformed(e);
    }

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true,(TreeNode)this.treeMain.getModel().getRoot());
	}

	protected ICoreBase getBizInterface() throws Exception {
		//return super.getBizInterface();
		return TenancyOrderFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return TenancyOrderEditUI.class.getName();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node =  (DefaultKingdeeTreeNode)this.treeMain.getLastSelectedPathComponent();
		if(node==null)  return;
		
		if(node.getUserObject() instanceof SellProjectInfo){
			SellProjectInfo sellPro = (SellProjectInfo)node.getUserObject();
			uiContext.put("sellProject",sellPro);
		}else{
			MsgBox.showInfo("请选择具体的销售项目");
			this.abort();
		}

		super.prepareUIContext(uiContext, e);
	}

	public void actionSetEnable_actionPerformed(ActionEvent e) throws Exception {
		//super.actionSetEnable_actionPerformed(e);
		String id = this.getSelectedKeyValue();
		if(id==null)  return;
		List roomList = new ArrayList();
		IObjectPK opk = new ObjectUuidPK(BOSUuid.read(id));
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("*");
		selector.add("roomEntrys.room.*");	
		TenancyOrderInfo tenOrder = TenancyOrderFactory.getRemoteInstance().getTenancyOrderInfo(opk,selector);
		if(tenOrder.isIsEnabled()){
    		MsgBox.showError("批次已经执行,不能再执行!");
    		return;
    	}
		TenancyOrderRoomEntryCollection roomEntrys = tenOrder.getRoomEntrys();
		for(int i=0;i<roomEntrys.size();i++) {
			TenancyOrderRoomEntryInfo roomEntry = roomEntrys.get(i);
			RoomInfo room = roomEntry.getRoom();
			if(room!=null) {
				if(room.getTenancyState()==null || room.getTenancyState().equals(TenancyStateEnum.unTenancy))
					roomList.add(Arrays.asList(new Object[]{TenancyStateEnum.waitTenancy.getValue(),room.getId().toString()}));   //,tenOrder.getId().toString()
			}else{
				MsgBox.showError("房间已不存在:"+roomEntry.getRoomLongNumber()+"!");
			}
		}
		
		try{
			if(roomList.size()>0) {			
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.executeBatch("update T_SHE_ROOM set FTenancyState=? where FID=?",roomList);
				tenOrder.setIsEnabled(true);
				tenOrder.setExecuteDate(new Date());
				UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
				tenOrder.setExecuteUser(user);
				TenancyOrderFactory.getRemoteInstance().submit(tenOrder);		
				
				MsgBox.showInfo("执行成功!");
				this.refresh(e);
			}else{
				MsgBox.showInfo("执行成功!");
			}
		}catch(Exception ee){
			MsgBox.showError("执行失败!"+ee.getMessage());
			ee.printStackTrace();
		}

		
	}
	
	
	
	
	protected String getLongNumberFieldName() {
		return "number";
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		String formatString = "yyyy-MM-dd";
		this.tblMain.getColumn("lastUpdateTime").getStyleAttributes().setNumberFormat(formatString);
		
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		
	}
	
	
	
	
	
	
	
	
	
	

}