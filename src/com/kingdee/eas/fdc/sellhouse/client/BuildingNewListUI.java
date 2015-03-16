/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.BanBasedataEntryFactory;
import com.kingdee.eas.fdc.basedata.BanBasedataEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ContractBillEditAuditDateUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.sellhouse.BanBasedataEntryListFactory;
import com.kingdee.eas.fdc.sellhouse.BanBasedataEntryListInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDesFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.UpdateDataFacadeFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class BuildingNewListUI extends AbstractBuildingNewListUI {
	private static final Logger logger = CoreUIObject
	.getLogger(BuildingNewListUI.class);
	
//	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	Map orgMap = FDCSysContext.getInstance().getOrgMap();
	
	protected void initTree() throws Exception {
//		this.treeMain.setModel(FDCTreeHelper.getSubareaTree(this.actionOnLoad,null));
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,null));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
	
	/**
	 * output class constructor
	 */
	public BuildingNewListUI() throws Exception {
		super();
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	
	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
	throws Exception {
		super.tblMain_tableClicked(e);
	}
	
	/**
	 * 检测 2009.10.06 做了 楼栋的  楼层分录之后 ，是否 进行过 数据升级，进行了的话，就不做处理。
	 */
	private void updateData()
	{
		try
		{
			boolean debug = UpdateDataFacadeFactory.getRemoteInstance().isNeedUpdateBuildingFloorForRoom();
			if(debug)
			{
				int i = MsgBox.showConfirm2("该楼栋实体在 2009年10月6日，新增加了 楼栋分录，需进行数据升级操作，若已进行过升级还出现此对话框，请联系相关人员处理。" +
						"需要 20 分钟左右的时间，请耐心等待！是否进行升级操作？");
				if(i == 0)
				{
				
					SwingUtilities.invokeLater(new Runnable()
					{

						public void run()
						{
							LongTimeDialog longTimeDialog = UITools.getDialog(BuildingNewListUI.this);
							longTimeDialog.setLongTimeTask(new ILongTimeTask()
							{

								public void afterExec(Object arg0)	throws Exception
								{
									UITools.showMsg(BuildingNewListUI.this, "数据升级成功", false);
									
								}

								public Object exec() throws Exception
								{
									UpdateDataFacadeFactory.getRemoteInstance().updateBuildingFloorForRoom();
									return "";
								}
								
							});
							longTimeDialog.show();
							
						}
						
					});
				
			     }
				else
				{
					this.abort();
				}
			}
			
			
		} catch (EASBizException e)
		{
			e.printStackTrace();
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
	}
	
	public void onLoad() throws Exception {
		
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
//		this.tblMain.getColumn("floorCount").getStyleAttributes()
//		.setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblMain.getColumn("floorCount").getStyleAttributes()
//		.setNumberFormat(FDCHelper.getNumberFtm(0));
//		this.tblMain.getColumn("buildingHeight").getStyleAttributes()
//		.setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblMain.getColumn("buildingHeight").getStyleAttributes()
//		.setNumberFormat(FDCHelper.getNumberFtm(2));
//		this.tblMain.getColumn("buildingTerraArea").getStyleAttributes()
//		.setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblMain.getColumn("buildingTerraArea").getStyleAttributes()
//		.setNumberFormat(FDCHelper.getNumberFtm(2));
//		this.tblMain.getColumn("unitCount").getStyleAttributes()
//		.setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblMain.getColumn("unitCount").getStyleAttributes()
//		.setNumberFormat(FDCHelper.getNumberFtm(0));
		
		String formatString = "yyyy-MM-dd";
		this.tblMain.getColumn("createTime").getStyleAttributes()
				.setNumberFormat(formatString);
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		

//		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
//		if (!saleOrg.isIsBizUnit()) {
//			this.actionAddNew.setEnabled(false);
//			this.actionEdit.setEnabled(false);
//			this.actionRemove.setEnabled(false);
//		}
		//不用销售组织判断,改为售楼组织
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		//09年10月份增加的楼层表,需要做数据升级.但此实现对性能影响较大.
		//考虑到09年10月前上线的客户肯定都打了后面的补丁,即都已经升过级了.将升级代码注释处理.
//		this.updateData();
		this.btnRelateBanBaseData.setIcon(EASResource.getIcon("imgTbtn_rename"));
		
		if(SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("POMadmin")){
			KDWorkButton btnUpdate=new KDWorkButton();
			btnUpdate.setText("数据升级（分录）");
			this.toolBar.add(btnUpdate);
			btnUpdate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
		                beforeActionPerformed(e);
		                try {
		                	btnUpdate_actionPerformed(e);
		                } catch (Exception exc) {
		                    handUIException(exc);
		                } finally {
		                    afterActionPerformed(e);
		                }
		            }
		        });
		}
	}
	protected void btnUpdate_actionPerformed(ActionEvent e) throws Exception {
		if (FDCMsgBox.showConfirm2(this,"是否数据升级？") != MsgBox.OK) {
			return;
		}
		int m=0;
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select fid,fbanBasedataEntryId from T_SHE_Building where FDescription_l2 is null and fbanBasedataEntryId is not null");
		IRowSet rs = sqlBuilder.executeQuery();
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("description");
		sel.add("banBasedataEntry");
		while(rs.next()){
			String id=rs.getString("fid");
			BuildingInfo info=BuildingFactory.getRemoteInstance().getBuildingInfo(new ObjectUuidPK(id),sel);

			BanBasedataEntryListInfo entryInfo=new BanBasedataEntryListInfo();
			entryInfo.setHead(info);
			BanBasedataEntryInfo banBasedataEntry=BanBasedataEntryFactory.getRemoteInstance().getBanBasedataEntryInfo(new ObjectUuidPK(rs.getString("fbanBasedataEntryId")));
			entryInfo.setBanBasedataEntry(banBasedataEntry);
			BanBasedataEntryListFactory.getRemoteInstance().addnew(entryInfo);
		
			info.setDescription(banBasedataEntry.getBanNumber());
			info.setBanBasedataEntry(null);
			BuildingFactory.getRemoteInstance().updatePartial(info, sel);
		}
	}
	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
	throws Exception {
	}
	
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
//		prepareUIContext((UIContext)this.getUIWindow(),this.getActionEvent());
		this.actionAddNew.setEnabled(false);
		if (node == null) {
			return;
		}
		
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", sellProject.getId()
							.toString()));
			// filter.getFilterItems().add(new FilterItemInfo("subarea.id",
			// null));
		
			//不用销售组织判断,改为售楼组织
			FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
			String idStr =null;
			if(null!= orgUnit.getId()){
				idStr = orgUnit.getId().toString();
			}
			if(null!=orgMap.get(idStr)){
				FilterInfo filterParent = new FilterInfo();
				filterParent.getFilterItems().add(new FilterItemInfo("parent.id",sellProject.getId()
						.toString()));
				if(node.getChildCount()<=0&&!SellProjectFactory.getRemoteInstance().exists(filterParent)){ //末节点才能新增新增
					this.actionAddNew.setEnabled(true);
				}else{
					this.actionAddNew.setEnabled(false);
				}
			}else{
				this.actionAddNew.setEnabled(false);
			}
//			if (saleOrg.isIsBizUnit()) {
//				this.actionAddNew.setEnabled(true);
//			}else{
//				this.actionAddNew.setEnabled(false);
//			}
		} else if (node.getUserObject() instanceof SubareaInfo) {
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode)node.getParent();
			SellProjectInfo sellProject = (SellProjectInfo)parentNode.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("subarea.id", subarea.getId().toString()));
			
		}else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
			this.actionAddNew.setEnabled(false);
		}
		
		
		this.mainQuery.setFilter(filter);
		this.tblMain.removeRows();
	}
	
	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof String) {
			MsgBox.showInfo("请选择具体销售项目！");
			this.abort();
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
			.getUserObject();
			uiContext.put("sellProject", sellProject);
			uiContext.put("subarea", null);
		} else if (node.getUserObject() instanceof SubareaInfo) {
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			
			SellProjectInfo sellProject = null;
			
			if(subarea.getSellProject()!=null && subarea.getSellProject().getId()!=null)
				try
			{
					sellProject = (SellProjectInfo) SellProjectFactory.getRemoteInstance().getValue(new ObjectUuidPK(BOSUuid.read(subarea.getSellProject().getId().toString())));
			}catch (Exception e1)
			{
				e1.printStackTrace();
			}
			uiContext.put("sellProject", sellProject);
			uiContext.put("subarea", subarea);
		}
		super.prepareUIContext(uiContext, e);
	}
	
	protected String getEditUIName() {
		
		return BuildingNewEditUI.class.getName() ;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return BuildingFactory.getRemoteInstance();
	}
	
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		if (id != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("building.id", id));
			if (RoomDesFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被房间定义使用,不能删除!");
				return;
			}
			if (RoomFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经被房间使用,不能删除!");
				return;
			}
		}
		checkSelected();
		if (confirmRemove()) {
//			prepareRemove(null).callHandler();
			Remove();
			refresh(e);
		}
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
			.getUserObject();
			CacheServiceFactory.getInstance().discardType(sellProject.getBOSType());
		}
		if(node.getUserObject() instanceof SubareaInfo)
		{
		  try
			{
			  SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			  SellProjectInfo sellProject = null;
			if(subarea.getSellProject()!=null && subarea.getSellProject().getId()!=null)
			{
					sellProject = (SellProjectInfo) SellProjectFactory.getRemoteInstance().getValue(new ObjectUuidPK(BOSUuid.read(subarea.getSellProject().getId().toString())));
			        CacheServiceFactory.getInstance().discardType(sellProject.getBOSType());
			}
			}catch (Exception e1)
			{
				this.handleException(e1);
			}
		}
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}
	
	/**
     * output actionCopy_actionPerformed method
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
    	int index = this.tblMain.getSelectManager().getActiveRowIndex();
    	if(index<0){
    		FDCMsgBox.showWarning("请选中行！");
    		SysUtil.abort();
    	}
    	IRow  rowCopy = this.tblMain.getRow(index);
    	
    	   UIContext uiContext = new UIContext(this);
           // UIContext uiContext = new
           // UIContext(getUIContext().get(UIContext.OWNER));
    	   DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
   		.getLastSelectedPathComponent();
   		if (node == null) {
   			return;
   		}
   		if (node.getUserObject() instanceof String) {
   			MsgBox.showInfo("请选择具体销售项目！");
   			this.abort();
   		}
   		if (node.getUserObject() instanceof SellProjectInfo) {
   			SellProjectInfo sellProject = (SellProjectInfo) node
   			.getUserObject();
   			uiContext.put("sellProject", sellProject);
   			uiContext.put("subarea", null);
   		} else if (node.getUserObject() instanceof SubareaInfo) {
   			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
   			
   			SellProjectInfo sellProject = null;
   			
   			if(subarea.getSellProject()!=null && subarea.getSellProject().getId()!=null)
   				try
   			{
   					sellProject = (SellProjectInfo) SellProjectFactory.getRemoteInstance().getValue(new ObjectUuidPK(BOSUuid.read(subarea.getSellProject().getId().toString())));
   			}catch (Exception e1)
   			{
   				e1.printStackTrace();
   			}
   			uiContext.put("sellProject", sellProject);
   			uiContext.put("subarea", subarea);
   		}
//    	   uiContext.put("sellProject", rowCopy.getCell("id").getValue().toString());
           uiContext.put("cId", rowCopy.getCell("id").getValue().toString());
           uiContext.put("COPY", Boolean.TRUE);
           IUIWindow uiWindow  = UIFactory.createUIFactory(UIFactoryName.MODEL).create(getEditUIName(), uiContext, null,
                       OprtState.ADDNEW);

         uiWindow.show();
         this.refresh(null);
    }
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo sellProject = (SellProjectInfo) node
						.getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sellProject
								.getId().toString(), CompareType.EQUALS));
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				String orgUnitIdStr = FDCTreeHelper
						.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node,
								"OrgStructure").keySet());
				if (orgUnitIdStr.trim().length() == 0) {
					orgUnitIdStr = "'nullnull'";
				}
				filter.getFilterItems().add(
						new FilterItemInfo("orgUnit.id", orgUnitIdStr,
								CompareType.INNER));
			}
		}
		// 合并查询条件
		viewInfo = (EntityViewInfo) mainQuery.clone();
		if(viewInfo.getFilter().getFilterItems().size()>0){
			FilterItemCollection coll = viewInfo.getFilter().getFilterItems();
			if(coll.get(0).toString().startsWith("id IS NULL")){
				coll.removeObject(0);
			}
		}
		try {
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	public void actionRelateBanBaseData_actionPerformed(ActionEvent e)throws Exception {
		checkSelected();
		List id=ContractClientUtils.getSelectedIdValues(this.tblMain, getKeyFieldName());
		if(id.size()>1){
			FDCMsgBox.showWarning(this,"请选择一条记录进行关联！");
			return;
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("list", ContractClientUtils.getSelectedIdValues(this.tblMain, getKeyFieldName()));
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BanBaseDataUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
		
		this.refresh(null);
	}
}