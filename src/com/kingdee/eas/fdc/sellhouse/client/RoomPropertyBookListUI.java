/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.BatchManageCollection;
import com.kingdee.eas.fdc.sellhouse.BatchManageFactory;
import com.kingdee.eas.fdc.sellhouse.BatchManageInfo;
import com.kingdee.eas.fdc.sellhouse.BatchManageSourceEnum;
import com.kingdee.eas.fdc.sellhouse.BatchRoomEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BatchRoomEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BatchRoomEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.BusinessTypeNameEnum;
import com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomPropertyBookListUI extends AbstractRoomPropertyBookListUI {
	private static final Logger logger = CoreUIObject.getLogger(RoomPropertyBookListUI.class);

	private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	// 批次表中的所有下标
	private final String batch_id = "id";
	private final String batch_orgUnit = "orgUnit";
	private final String batch_project = "project";
	private final String batch_batch = "batch";
	private final String batch_doState = "doState";
	private final String batch_curStep = "curStep";
	private final String batch_bookState = "bookState";
	private final String batch_trancator = "trancator";

	private final String batch_updateDate = "updateDate";
	private final String batch_orgUnitLongNumber = "orgUnitId";
	private final String batch_projectId = "projectId";

	// 主表表中的所有下标
	private final String main_id = "id";
	private final String main_orgUnit = "orgUnit.name";
	private final String main_sellProject = "sellProject.name";

	private final String main_subArea = "subarea.name";
	private final String main_build = "building.name";
	private final String main_roomUnit = "room.unit";

	private final String main_number = "room";
	private final String main_roomNo = "roomNo";
	private final String main_batch = "batch.number";

	private final String main_scheme = "propertyDoScheme.name";
	private final String main_bookNum = "roomPropertyBook.number";
	private final String main_bookState = "roomBookState";

	private final String main_step = "step";
	private final String main_bookTranDate = "roomPropertyBook.transactDate";
	private final String main_customer = "purchase.customerNames";

	private final String main_customerPhone = "purchase.customerPhones";
	private final String main_customerCard = "purchase.customerIDCards";
	private final String main_propertyState = "propertyState";

	private final String main_transactor = "transactor.name";
	private final String main_buildId = "building.id";
	private final String main_bookId = "roomPropertyBook.id";

	private final String main_sellProjectId = "sellProjectId";
	private final String main_schemeId = "scheme.id";
	private final String main_orgUnitLongNumber = "orgUnit.longNumber";

	private final String main_batchId = "batch.id";

	public RoomPropertyBookListUI() throws Exception {
		super();
	}

	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
		 IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
	        if(row == null){
	        	return;
	        }
	        try{
	        	RoomPropertyBookInfo info = RoomPropertyBookFactory.getRemoteInstance().getRoomPropertyBookInfo("select id,room.roomBookState where id='"+row.getCell("id").getValue().toString()+"'");
	        	if(info!=null && info.getRoom()!=null && info.getRoom().getRoomBookState().equals(RoomBookStateEnum.BOOKED)){
	        		this.actionBookAlert.setEnabled(false);
	        	}else{
	        		this.actionBookAlert.setEnabled(true);
	        	}
	        }catch(BOSException ex){
	        	logger.error(ex.getMessage());
	        }catch(Exception ex){
	        	logger.error(ex.getMessage());
	        }
	}
	
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
	}

	/*protected Map getAllShowData(Object selectedNodeObj)
			throws EASBizException, BOSException {
		Map allShowDatas = new HashMap();

		Map paramMap = new HashMap();
		if (selectedNodeObj != null) {
			paramMap.put("orgUnitLongNumber", SysContext.getSysContext()
					.getCurrentOrgUnit().getLongNumber());

			// 获取用户ID
			paramMap.put("userInfo", SysContext.getSysContext()
					.getCurrentUserInfo());
			allShowDatas = RoomPropertyBookFacadeFactory.getRemoteInstance()
					.getMutilRoomPropertyCollection(selectedNodeObj, paramMap);
		}
		return allShowDatas;
	}*/

	/*protected void loadAllShowData(Map allShowData) {
		tblMain.removeRows();
		tblBatch.removeRows();

		if (allShowData == null)
			return;

		if (allShowData.containsKey("BatchBook")
				&& allShowData.get("BatchBook") != null) {
			loadBatchData((RoomPropertyBatchCollection) allShowData
					.get("BatchBook"));
		}
		if (allShowData.containsKey("MutilBook")
				&& allShowData.get("MutilBook") != null) {
			loadBookData((List) allShowData.get("MutilBook"));
		}

		if (tblBatch.getRowCount() > 0) {
			tblBatch.getSelectManager().select(0, 0);
		}

		if (tblMain.getRowCount() > 0) {
			tblMain.getSelectManager().select(0, 0);
		}
	}*/

	/*protected void loadBatchData(RoomPropertyBatchCollection batchBookList) {
		tblBatch.removeRows();

		for (int index = 0; index < batchBookList.size(); ++index) {
			RoomPropertyBatchInfo batchData = batchBookList.get(index);
			IRow tmpRow = tblBatch.addRow();

			tmpRow.getCell(batch_orgUnit).setValue(
					batchData.getOrgUnit().getName());
			tmpRow.getCell(batch_project).setValue(
					batchData.getSellProject().getName());
			tmpRow.getCell(batch_batch).setValue(batchData.getNumber());

			tmpRow.getCell(batch_doState).setValue(batchData.getBookState());
			tmpRow.getCell(batch_curStep).setValue(batchData.getCurStep());
			tmpRow.getCell(batch_bookState).setValue(
					batchData.getPropertyState());
			tmpRow.getCell(batch_trancator).setValue(
					batchData.getTransactor().getName());

			tmpRow.getCell(batch_updateDate)
					.setValue(batchData.getUpdateDate());
			tmpRow.getCell(batch_orgUnitLongNumber).setValue(
					batchData.getOrgUnit().getLongNumber());
			tmpRow.getCell(batch_projectId).setValue(
					batchData.getSellProject().getId());

			tmpRow.getCell(batch_id).setValue(batchData.getId());
		}
	}*/

	/*protected void loadBookData(List mutilBookList) {
		tblMain.removeRows();

		String[] bookIndexArray = new String[] { main_id, main_orgUnit,
				main_sellProject, main_subArea, main_build, main_roomUnit,
				main_number, main_roomNo, main_batch, main_scheme,
				main_bookNum, main_bookState, main_step, main_bookTranDate,
				main_customer, main_customerPhone, main_customerCard,
				main_propertyState, main_transactor, main_buildId, main_bookId,
				main_sellProjectId, main_schemeId, main_orgUnitLongNumber,
				main_batchId };

		for (int index = 0; index < mutilBookList.size(); ++index) {
			Map bookData = (Map) mutilBookList.get(index);
			IRow tmpRow = tblMain.addRow();

			for (int bookIndex = 0; bookIndex < bookIndexArray.length; ++bookIndex) {
				if (bookData.containsKey(bookIndexArray[bookIndex])) {
					if (tmpRow.getCell(bookIndexArray[bookIndex]) != null) {
						tmpRow.getCell(bookIndexArray[bookIndex]).setValue(
								bookData.get(bookIndexArray[bookIndex]));
					}
				}
			}
		}
	}*/

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		this.f7RoomSelect.setValue(null);
		this.f7Customer.setValue(null);
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() instanceof OrgStructureInfo) {
			this.tblBatch.removeRows();
			this.tblMain.removeRows();
			
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionQuery.setEnabled(false);
			this.actionAddBatch.setEnabled(false);
			this.actionBookAlert.setEnabled(false);
			this.actionBatchBook.setEnabled(false);
			
			return;
		}
		
		if (FDCSysContext.getInstance().checkIsSHEOrg()){
			if(this.kDTabbedPane1.getSelectedIndex() == 1){
				this.actionEdit.setEnabled(true);
				this.actionRemove.setEnabled(true);
				this.actionQuery.setEnabled(true);
				this.actionBatchBook.setEnabled(true);
			}
			this.actionAddBatch.setEnabled(true);
			this.actionBookAlert.setEnabled(true);
		}
		
		this.initF7RoomSelect();
		
		this.execQuery();
		
		this.fillBatchTab();

		// 根据所选的节点不同显示不同的列
		/*if (node.getUserObject() instanceof Integer) {

		} 
		else if (node.getUserObject() instanceof BuildingUnitInfo) {
			
			 * if (saleOrg.isIsBizUnit()) {
			 * FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[]
			 * { actionEdit, actionBatchBook, actionRemove }, true); }
			 

			tblMain.getColumn("orgUnit.name").getStyleAttributes().setHided(
					true);
			tblMain.getColumn("sellProject.name").getStyleAttributes()
					.setHided(true);
			tblMain.getColumn("subarea.name").getStyleAttributes().setHided(
					true);
			tblMain.getColumn("building.name").getStyleAttributes().setHided(
					true);
		} else if (node.getUserObject() instanceof BuildingInfo) {

			// BuildingInfo building = (BuildingInfo) node.getUserObject();
			
			 * if
			 * (!CodingTypeEnum.UnitFloorNum.equals(building.getCodingType())) {
			 * if (saleOrg.isIsBizUnit())
			 * FDCClientHelper.setActionEnableAndNotSetVisible( new ItemAction[]
			 * { actionEdit, actionBatchBook, actionRemove }, true); } else {
			 * FDCClientHelper.setActionEnableAndNotSetVisible( new ItemAction[]
			 * { actionEdit, actionBatchBook, actionRemove }, false); }
			 

			tblMain.getColumn("orgUnit.name").getStyleAttributes().setHided(
					true);
			tblMain.getColumn("sellProject.name").getStyleAttributes()
					.setHided(true);
			tblMain.getColumn("subarea.name").getStyleAttributes().setHided(
					true);
			tblMain.getColumn("building.name").getStyleAttributes().setHided(
					false);
		} else if (node.getUserObject() instanceof SellProjectInfo) {
			tblMain.getColumn("orgUnit.name").getStyleAttributes().setHided(
					true);
			tblMain.getColumn("sellProject.name").getStyleAttributes()
					.setHided(false);
			tblMain.getColumn("subarea.name").getStyleAttributes().setHided(
					false);
			tblMain.getColumn("building.name").getStyleAttributes().setHided(
					false);
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			tblMain.getColumn("orgUnit.name").getStyleAttributes().setHided(
					false);
			tblMain.getColumn("sellProject.name").getStyleAttributes()
					.setHided(false);
			tblMain.getColumn("subarea.name").getStyleAttributes().setHided(
					false);
			tblMain.getColumn("building.name").getStyleAttributes().setHided(
					false);
		} else if (node.getUserObject() instanceof SubareaInfo) {
			tblMain.getColumn("orgUnit.name").getStyleAttributes().setHided(
					true);
			tblMain.getColumn("sellProject.name").getStyleAttributes()
					.setHided(true);
			tblMain.getColumn("subarea.name").getStyleAttributes().setHided(
					false);
			tblMain.getColumn("building.name").getStyleAttributes().setHided(
					false);
		} else {
			
			 * FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[]
			 * { actionEdit, actionBatchBook, actionRemove }, false);
			 
		}*/

		/*// 如果所选节点不为空，则调用facade获取所有的所需要的数据
		Map allShowData = getAllShowData(node.getUserObject());

		// 将所得数据加载到表格中
		loadAllShowData(allShowData);*/
	}
	
	/**
	 * 填充批次页签数据
	 * @throws BOSException 
	 */
	private void fillBatchTab() throws BOSException{
		this.tblBatch.removeRows();
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		SellProjectInfo sellProject = (SellProjectInfo)node.getUserObject();
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("sellProject.id");
		view.getSelector().add("sellProject.name");
		view.getSelector().add("transactor.name");
		view.getSelector().add("creator.name");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("source", BatchManageSourceEnum.PROPERTY_VALUE));
		view.setFilter(filter);
		
		BatchManageCollection batchManageCol = BatchManageFactory.getRemoteInstance().getBatchManageCollection(view);
		if(batchManageCol!=null && batchManageCol.size()>0){
			for(int i=0; i<batchManageCol.size(); i++){
				BatchManageInfo batchInfo = batchManageCol.get(i);
				IRow row = this.tblBatch.addRow();
				row.getCell("id").setValue(batchInfo.getId().toString());
				row.getCell("projectId").setValue(batchInfo.getSellProject().getId().toString());
				row.getCell("project").setValue(batchInfo.getSellProject().getName());
				row.getCell("batch").setValue(batchInfo.getNumber());
				row.getCell("transactor").setValue(batchInfo.getTransactor().getName());
				row.getCell("creator").setValue(batchInfo.getCreator().getName());
				row.getCell("createTime").setValue(batchInfo.getCreateTime());
			}
		}
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		viewInfo = (EntityViewInfo) mainQuery.clone();

		// 合并查询条件
		try {
			//加上销售项目查询条件
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			FilterInfo filter = new FilterInfo();
			
			if (node.getUserObject() instanceof SellProjectInfo) { // 销售项目
				SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("room.building.sellProject.id", sellProject.getId().toString()));
			}
			else{
				filter.getFilterItems().add(new FilterItemInfo("id", "null"));
			}
			
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			super.handUIException(e);
		}
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	private void search() throws BOSException{
		HashMap batchManageMap = new HashMap();
		
		RoomInfo qryRoom = null;
		SHECustomerInfo qryCustomer = null;
		if(this.f7RoomSelect.getValue() != null){
			qryRoom = (RoomInfo)this.f7RoomSelect.getValue();
		}
		if(this.f7Customer.getValue() != null){
			qryCustomer = (SHECustomerInfo)this.f7Customer.getValue();
		}
		
		if(qryRoom==null && qryCustomer==null){
			FDCMsgBox.showInfo("请输入查询条件");
			SysUtil.abort();
		}
		
		//根据条件获取产权单据中的批次id
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("batchManage.*");
		view.getSelector().add("batchManage.sellProject.id");
		view.getSelector().add("batchManage.sellProject.name");
		view.getSelector().add("batchManage.transactor.name");
		view.getSelector().add("batchManage.creator.name");
		
		FilterInfo roomPropertyfilter = new FilterInfo();
		roomPropertyfilter.getFilterItems().add(new FilterItemInfo("batchManage.source", BatchManageSourceEnum.PROPERTY_VALUE));
		
		if(qryRoom != null){  //添加房间限制条件
			roomPropertyfilter.getFilterItems().add(new FilterItemInfo("room.id", qryRoom.getId().toString()));
		}
		if(qryCustomer != null){  //根据客户查询签约单的客户分录，获取签约单id，添加签约单限制条件
			EntityViewInfo signView = new EntityViewInfo();
			signView.getSelector().add("head.id");
			
			FilterInfo signFilter = new FilterInfo();
			signFilter.getFilterItems().add(new FilterItemInfo("customer.id", qryCustomer.getId().toString()));
			
			SignCustomerEntryCollection signCustomerCol = SignCustomerEntryFactory.getRemoteInstance()
				.getSignCustomerEntryCollection(signView);
			if(signCustomerCol!=null && signCustomerCol.size()>0){
				HashSet signIdSet = new HashSet();
				for(int i=0; i<signCustomerCol.size(); i++){
					signIdSet.add(signCustomerCol.get(i).getHead().getId().toString());
				}
				
				roomPropertyfilter.getFilterItems().add(new FilterItemInfo("sign.id", signIdSet, CompareType.INCLUDE));
			}
		}
		
		view.setFilter(roomPropertyfilter);
		
		//从产权单据中得到批次map
		RoomPropertyBookCollection roomPropertyBookCol = RoomPropertyBookFactory.getRemoteInstance()
			.getRoomPropertyBookCollection(view);
		if(roomPropertyBookCol!=null && roomPropertyBookCol.size()>0){
			for(int i=0; i<roomPropertyBookCol.size(); i++){
				BatchManageInfo batchManage = roomPropertyBookCol.get(i).getBatchManage();
				if(batchManage != null){
					batchManageMap.put(batchManage.getId().toString(), batchManage);
				}
			}
		}
		
		//填充批次表
		this.tblBatch.removeRows();
		if(batchManageMap.size() > 0){
			Iterator batchIterator = batchManageMap.values().iterator();
			while(batchIterator.hasNext()){
				BatchManageInfo batchInfo = (BatchManageInfo)batchIterator.next();
				IRow row = this.tblBatch.addRow();
				row.getCell("id").setValue(batchInfo.getId().toString());
				row.getCell("projectId").setValue(batchInfo.getSellProject().getId().toString());
				row.getCell("project").setValue(batchInfo.getSellProject().getName());
				row.getCell("batch").setValue(batchInfo.getNumber());
				row.getCell("transactor").setValue(batchInfo.getTransactor().getName());
				row.getCell("creator").setValue(batchInfo.getCreator().getName());
				row.getCell("createTime").setValue(batchInfo.getCreateTime());
			}
		}
	}
	protected void btnSearch_actionPerformed(ActionEvent e) throws Exception {
		search();
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		this.setPreSelecteRow();
	}

	protected String getKeyFieldName() {
		return super.getKeyFieldName();
	}

	protected String getSelectedKeyValue() {
		String keyValue;
		keyValue = super.getSelectedKeyValue();

		if (keyValue == null || keyValue.length() <= 0) {
			String costBillID = getSelectedCostBillID();
			if (costBillID == null) {
				return null;
			}
		}

		return keyValue;
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		
		if (kDTabbedPane1.getSelectedIndex() == 0) {
			IRow selectedRow = this.tblBatch.getRow(this.tblBatch.getSelectManager().getActiveRowIndex());
			
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if (node == null || node.getUserObject() instanceof OrgStructureInfo) {
				FDCMsgBox.showInfo("请选择项目");
				SysUtil.abort();
			}
			String batchId = String.valueOf(selectedRow.getCell("id").getValue());
			UIContext uiContext = new UIContext(this);
			uiContext.put("sellProject", (SellProjectInfo)node.getUserObject());
			uiContext.put("source", "roomProperty");
			uiContext.put(UIContext.ID, batchId);

			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(BatchRoomManageUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
		else {
			super.actionView_actionPerformed(e);
		}
	}

	public static String getRes(String resName) {
		return EASResource.getString(
				"com.kingdee.eas.fdc.sellhouse.SellHouseResource", resName);
	}

	protected String getSelectedCostBillID() {
		int selectIndex = -1;

		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);

		if (selectRows.length > 0) {
			int rowIndex = selectRows[0];
			IRow row = tblMain.getRow(rowIndex);
			if (row == null) {
				return null;
			}

			selectIndex = rowIndex;
			ICell cell = row.getCell("id");

			if (cell == null) {
				MsgBox.showError(EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Error_KeyField_Fail"));
				SysUtil.abort();
			}

			Object keyValue = cell.getValue();

			if (keyValue != null) {
				return keyValue.toString();
			}
		}

		return null;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		BizEnumValueInfo propState = (BizEnumValueInfo)this.tblMain.getRow(rowIndex).getCell("propState").getValue();
		if(propState!=null && AFMortgagedStateEnum.STOPTRANSACT.getName().equals(propState.getName())){
			FDCMsgBox.showInfo("当前状态下的单据不能修改");
			SysUtil.abort();
		}
		
		super.actionEdit_actionPerformed(e);

		// 仅仅实现编辑数据完成后定位到原来编辑的数据行上
		this.setPreSelecteRow();
	}

	protected void checkBeforeRemove() throws EASBizException, BOSException,
			Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);

		for (int i = 0, size = selectRows.length; i < size; i++) {
			int rowIndex = selectRows[i];
			IRow row = tblMain.getRow(rowIndex);
			if (row == null) {
				MsgBox.showInfo(this, "没有选择行！");
				SysUtil.abort();
			}
			Object valueCom = row.getCell("id").getValue();
			if (valueCom == null) {
				StringBuffer buffer = new StringBuffer();
				buffer.append(row.getCell("number").getValue());
				buffer.append("房间未进行产权登记，不能删除");
				MsgBox.showWarning(this, buffer.toString());
				abort();

			}
		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();

		if(this.kDTabbedPane1.getSelectedIndex() == 0){  //当前页签为批次页签
			return;
		}

		List ids = new ArrayList();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		for (int index = 0; index < selectRows.length; ++index) {
			ids.add(tblMain.getRow(selectRows[index]).getCell("id").getValue());
		}
		if (ids != null && ids.size() > 0) {
			if (MsgBox.showConfirm2New(this, "是否确定手工终止办理？") == MsgBox.YES) {
				for (int i = 0; i < ids.size(); i++) {
					//获取产权对象
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add(new SelectorItemInfo("*"));
					selector.add(new SelectorItemInfo("room.id"));
					selector.add(new SelectorItemInfo("promiseDate"));
					selector.add(new SelectorItemInfo("actualFinishDate"));
					selector.add(new SelectorItemInfo("batchManage.id"));
					RoomPropertyBookInfo bookInfo = RoomPropertyBookFactory.getRemoteInstance()
						.getRoomPropertyBookInfo(new ObjectUuidPK(ids.get(i).toString()));
					
					//更新产权状态
					bookInfo.setPropState(AFMortgagedStateEnum.STOPTRANSACT);
					RoomPropertyBookFactory.getRemoteInstance().update(new ObjectUuidPK(ids.get(i).toString()), bookInfo);
					
					//更新房间产权状态
					bookInfo.getRoom().setRoomBookState(RoomBookStateEnum.NOTBOOKED);
					SelectorItemCollection roomSelector = new SelectorItemCollection();
					selector.add("roomBookState");
					RoomFactory.getRemoteInstance().updatePartial(bookInfo.getRoom(), roomSelector);
					
					//将批次对应的房间分录置为无效
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("batchManage.source", BatchManageSourceEnum.Property));
					filter.getFilterItems().add(new FilterItemInfo("room.id", bookInfo.getRoom().getId()));
					filter.getFilterItems().add(new FilterItemInfo("batchManage.id", bookInfo.getBatchManage().getId()));
					filter.getFilterItems().add(new FilterItemInfo("isValid", Boolean.TRUE));
					
					view.setFilter(filter);
					BatchRoomEntryCollection batchRoomCol = BatchRoomEntryFactory.getRemoteInstance()
						.getBatchRoomEntryCollection(view);
					
					if(batchRoomCol!=null && !batchRoomCol.isEmpty()){
						BatchRoomEntryInfo batchRoomEntry = batchRoomCol.get(0);
						batchRoomEntry.setIsValid(false);
						
						SelectorItemCollection updateSelector = new SelectorItemCollection();
						updateSelector.add("isValid");
						BatchRoomEntryFactory.getRemoteInstance().updatePartial(batchRoomEntry, updateSelector);
					}
					
					//更新业务总览中的对应的业务
					this.deleteTransactionBiz(bookInfo);
				}
				FDCClientUtils.showOprtOK(this);
				refreshList();
			}
		}
	}
	
	/**
	 * 删除业务总览中对应的服务
	 * @param roomLoan
	 */
	private void deleteTransactionBiz(RoomPropertyBookInfo bookInfo){
		try {
			SHEManageHelper.updateTransactionOverView(null, bookInfo.getRoom(), SHEManageHelper.INTEREST,
					bookInfo.getPromiseFinishDate(), bookInfo.getActualFinishDate(), true);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void actionBookAlert_actionPerformed(ActionEvent e) throws Exception {
		super.actionBookAlert_actionPerformed(e);
		UIContext uiContext = new UIContext(this);
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(PropertyNoticeUI.class.getName(), uiContext,
						null, OprtState.ADDNEW);
		uiWindow.show();
	}

	public void actionBatchBook_actionPerformed(ActionEvent e) throws Exception {
		if (kDTabbedPane1.getSelectedComponent().equals(kdPaneBatch)) {
			if (tblBatch.getRowCount() == 0
					|| tblBatch.getSelectManager().size() == 0) {
				MsgBox.showWarning(this, EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Msg_MustSelected"));
				SysUtil.abort();
			}

			/*// 修改当前组织下的产权
			if (tblBatch.getSelectManager().getActiveRowIndex() >= 0) {
				IRow tmpRow = tblBatch.getRow(tblBatch.getSelectManager()
						.getActiveRowIndex());
				String curOrgLongNumber = SysContext.getSysContext()
						.getCurrentOrgUnit().getLongNumber();
				if (!curOrgLongNumber.equals(tmpRow.getCell(
						batch_orgUnitLongNumber).getValue())) {
					MsgBox.showWarning("所选项不属于当前组织，不能执行此操作！");
					abort();
				}
			}*/

			doSelectedTblBatch();
		} else if (kDTabbedPane1.getSelectedComponent().equals(kdPaneBook)) {
			if (tblMain.getRowCount() == 0
					|| tblMain.getSelectManager().size() == 0) {
				MsgBox.showWarning(this, EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Msg_MustSelected"));
				SysUtil.abort();
			}

			/*// 修改当前组织下的产权
			if (tblMain.getSelectManager().getActiveRowIndex() >= 0) {
				IRow tmpRow = tblMain.getRow(tblMain.getSelectManager()
						.getActiveRowIndex());
				String curOrgLongNumber = SysContext.getSysContext()
						.getCurrentOrgUnit().getLongNumber();
				if (!curOrgLongNumber.equals(tmpRow.getCell(
						main_orgUnitLongNumber).getValue())) {
					MsgBox.showWarning("所选项不属于当前组织，不能执行此操作！");
					abort();
				}
			}*/

			doSelectedTblMain();
		}
	}

	protected void doSelectedTblBatch() throws UIException {
		int rowIndex = tblBatch.getSelectManager().getActiveRowIndex();
		if (rowIndex < 0)
			return;
		IRow selectRow = tblBatch.getRow(rowIndex);

		/*String orgUnitLongNumber = String.valueOf(selectRow.getCell(batch_orgUnitLongNumber).getValue());
		String prjId = String.valueOf(selectRow.getCell(batch_projectId)
				.getValue());*/

		IRow row = tblBatch.getRow(rowIndex);
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, row.getCell("id").getValue());
		/*uiContext.put("OrgUnitId", orgUnitLongNumber);
		uiContext.put("ProjectId", prjId);*/

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(RoomPropertyBatchEditUI.class.getName(), uiContext,
						null, OprtState.EDIT);

		uiWindow.show();
	}

	protected void doSelectedTblMain() throws BOSException {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);

		if (selectRows.length <= 0)
			return;

		IRow firstRow = tblMain.getRow(selectRows[0]);
		String schemeId = String.valueOf(firstRow.getCell("scheme.id").getValue());
		String batchId = String.valueOf(firstRow.getCell("batch.id").getValue());
		String sellProjectId = String.valueOf(firstRow.getCell("sellProjectId").getValue());

		//先检查第一行
		BizEnumValueInfo propState = (BizEnumValueInfo)firstRow.getCell("propState").getValue();
		if(propState!=null && AFMortgagedStateEnum.STOPTRANSACT.getName().equals(propState.getName())){
			FDCMsgBox.showInfo("手工终止的单据不能修改");
			SysUtil.abort();
		}

		for (int rowIndex = 1; rowIndex < selectRows.length; ++rowIndex) {
			IRow tmpRow = tblMain.getRow(selectRows[rowIndex]);
			String tmpSchemeId = String.valueOf(tmpRow.getCell(main_schemeId).getValue());

			BizEnumValueInfo rowPropState = (BizEnumValueInfo)tmpRow.getCell("propState").getValue();
			if(rowPropState!=null && AFMortgagedStateEnum.STOPTRANSACT.getName().equals(rowPropState.getName())){
				FDCMsgBox.showInfo("手工终止的单据不能修改");
				SysUtil.abort();
			}

			if(batchId!=null){
				if(tmpRow.getCell(main_batchId).getValue() == null 
					|| !batchId.equals(tmpRow.getCell(main_batchId).getValue().toString())){
					MsgBox.showWarning("所选项的批次不一致，不能执行批量产权办理！");
					abort();
				}
			}
			
			if (!tmpSchemeId.equals(schemeId)) {
				MsgBox.showWarning("所选项的产权方案不一致，不能执行批量产权办理！");
				abort();
			}
		}

		List roomBookIdList = new ArrayList();
		for (int rowIndex = 0; rowIndex < selectRows.length; ++rowIndex) {
			IRow tmpRow = tblMain.getRow(selectRows[rowIndex]);
			if (tmpRow.getCell(main_id).getValue() != null) {
				roomBookIdList.add(String.valueOf(tmpRow.getCell(main_id).getValue()));
			}
		}

		UIContext batchUIContext = new UIContext(this);
		batchUIContext.put(UIContext.ID, null);
		batchUIContext.put("sellProjectId", sellProjectId);
		batchUIContext.put("roomBookIdList", roomBookIdList);
		if(schemeId!=null && !schemeId.equals("null")){
			batchUIContext.put("schemeId", schemeId);
		}
		if(batchId!=null && !batchId.equals("null")){
			batchUIContext.put("batchId", batchId);
		}

		IUIWindow window = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(RoomPropertyBatchEditUI.class.getName(), batchUIContext, null, OprtState.ADDNEW);

		window.show();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	public void onLoad() throws Exception {
		super.onLoad();

		this.btnAddBatch.setIcon(EASResource.getIcon("imgTbtn_gathering"));
		this.btnBookAlert.setIcon(EASResource.getIcon("imgTbtn_closeinitialize"));
		
		this.tblBatch.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblBatch.getStyleAttributes().setLocked(true);
		this.tblBatch.checkParsed();
		
		SellProjectInfo sellProject=(SellProjectInfo) this.getUIContext().get("sellProject");
		if(sellProject!=null){
			treeMain.setVisible(false);
			pnlMain.setDividerLocation(0);
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot();
			DefaultKingdeeTreeNode node = SHEManageHelper.findNode(root, null, null ,sellProject);
			this.treeMain.setSelectionNode(node);
			RoomInfo room=(RoomInfo) this.getUIContext().get("room");
			if(room!=null){
				room=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(room.getId()));
			}
			this.f7RoomSelect.setValue(room);
			search();
		}
		
		this.initF7Customer();
		this.initF7RoomSelect();
	}

	protected String getEditUIName() {
		return RoomPropertyBookEditUI.class.getName();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected void refresh(ActionEvent e) throws Exception {
		treeMain_valueChanged(null);
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomPropertyBookFactory.getRemoteInstance();
	}

	protected void initWorkButton() {
		super.initWorkButton();

		FDCClientHelper.addSqlMenu(this, this.menuEdit);

		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew },
				false);
		/*
		 * if (!saleOrg.isIsBizUnit()) {
		 * FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] {
		 * actionEdit, actionRemove, actionBatchBook }, false); }
		 * actionEdit.setEnabled(true); btnEdit.setEnabled(true);
		 * actionBatchBook.setEnabled(true); btnBatchBook.setEnabled(true);
		 */
		this.actionLocate.setVisible(false);
		this.actionLocate.setEnabled(false);

		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
	}

	public void actionAddBatch_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddBatch_actionPerformed(e);
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() instanceof OrgStructureInfo) {
			FDCMsgBox.showInfo("请选择项目");
			SysUtil.abort();
		}
		
		UIContext bookContext = new UIContext(this);
		bookContext.put("sellProject", (SellProjectInfo)node.getUserObject());
		bookContext.put("source", "roomProperty");

		IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BatchRoomManageUI.class.getName(), 
				bookContext, null, OprtState.ADDNEW);
		window.show();
		
		refresh(null);
	}
	
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionExport);
		super.actionExport_actionPerformed(e);
	}

	public void actionExportData_actionPerformed(ActionEvent e)
			throws Exception {
		handlePermissionForItemAction(actionExportData);
		super.actionExportData_actionPerformed(e);
	}

	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		handlePermissionForItemAction(actionExportSelected);
		super.actionExportSelected_actionPerformed(e);

	}

	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionToExcel);
		super.actionToExcel_actionPerformed(e);
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew) || act.equals(actionEdit)
				|| (act.equals(actionView))) {
			/*
			 * 把当前选中的工程项目和合同类型传给EditUI
			 */
			/*if (getSelectedKeyValue() != null) {
				uiContext.put(UIContext.ID, getSelectedKeyValue());
				uiContext.put("roomId", getSelectedCostBillID());
			} else {
				uiContext.put(UIContext.ID, getSelectedCostBillID());
				uiContext.put("roomId", getSelectedCostBillID());
			}*/

			// 获取所选项的销售项目的Id，避免在编辑界面在取一次
			if (tblMain.getSelectManager().getActiveRowIndex() >= 0) {
				IRow tmpRow = tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex());
				uiContext.put("sellProjectId", String.valueOf(tmpRow.getCell(main_sellProjectId).getValue()));
			}

		}
	}

	protected boolean checkSelect() throws EASBizException, BOSException,
			Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		boolean isAllow = false;
		for (int i = 0, size = selectRows.length; i < size; i++) {
			int rowIndex = selectRows[i];
			IRow row = tblMain.getRow(rowIndex);
			if (row == null) {
				MsgBox.showInfo(this, "没有选择行！");
				SysUtil.abort();
			}
			Object valueCom = row.getCell("roomBookState").getValue();
			if (valueCom != null
					&& valueCom.toString().equals(
							RoomBookStateEnum.BOOKED.toString())) {
				isAllow = true;
				break;
			}
		}
		return isAllow;
	}
	
	protected void kDTabbedPane1_stateChanged(ChangeEvent e) throws Exception {
		super.kDTabbedPane1_stateChanged(e);
		if(this.kDTabbedPane1.getSelectedIndex() == 0){  //批次管理页签
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] { actionEdit, actionRemove,
					actionRefresh, actionQuery, actionBatchBook }, false);
		}
		else{  //产权管理页签
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] { actionEdit, actionRemove,
					actionRefresh, actionQuery, actionBatchBook }, true);
		}
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (!FDCSysContext.getInstance().checkIsSHEOrg() || node == null || node.getUserObject() instanceof OrgStructureInfo) {
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] {actionAddBatch, actionQuery,
					actionEdit, actionRemove, actionBatchBook }, false);
		}
	}

	protected void tblBatch_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			// modify to view when doubleClick row by Jacky 2005-1-7
			if (e.getType() == 0) {
				return;
			}

			/*// 修改当前组织下的产权
			if (tblBatch.getSelectManager().getActiveRowIndex() >= 0) {
				IRow tmpRow = tblBatch.getRow(tblBatch.getSelectManager()
						.getActiveRowIndex());
				String curOrgLongNumber = SysContext.getSysContext()
						.getCurrentOrgUnit().getLongNumber();
				if (!curOrgLongNumber.equals(tmpRow.getCell(
						batch_orgUnitLongNumber).getValue())) {
					MsgBox.showWarning("所选项不属于当前组织，不能执行此操作！");
					abort();
				}
			}*/

			int rowIndex = tblBatch.getSelectManager().getActiveRowIndex();
			if (rowIndex < 0)
				return;
			IRow selectRow = tblBatch.getRow(rowIndex);

//			String orgUnitLongNumber = String.valueOf(selectRow.getCell(batch_orgUnitLongNumber).getValue());
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if (node == null || node.getUserObject() instanceof OrgStructureInfo) {
				FDCMsgBox.showInfo("请选择项目");
				SysUtil.abort();
			}
			String batchId = String.valueOf(selectRow.getCell("id").getValue());
			UIContext uiContext = new UIContext(this);
			uiContext.put("sellProject", (SellProjectInfo)node.getUserObject());
			uiContext.put("source", "roomProperty");
			uiContext.put(UIContext.ID, batchId);
//			uiContext.put("OrgUnitId", orgUnitLongNumber);

			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(BatchRoomManageUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}

	private void initF7RoomSelect(){
		this.f7RoomSelect.setEditable(false);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || !(node.getUserObject() instanceof SellProjectInfo)) {  //非项目节点
			filter.getFilterItems().add(new FilterItemInfo("id", "null"));
		}
		else{
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("building.sellProject.id", sellProject.getId().toString()));
		}
		
		filter.getFilterItems().add(new FilterItemInfo("building.sellProject.isForSHE", "true"));  // 售楼属性
		filter.getFilterItems().add(new FilterItemInfo("sellState", RoomSellStateEnum.Sign.getValue()));  //签约状态
		view.setFilter(filter);

		this.f7RoomSelect.setEntityViewInfo(view);
	}
	
	private void initF7Customer() throws EASBizException, BOSException{
		this.f7Customer.setEditable(false);
	}
}