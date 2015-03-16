/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.monitor.client.ProcessRunningListUI;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BasePriceControlFactory;
import com.kingdee.eas.fdc.sellhouse.BasePriceControlInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.IBasePriceControl;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.workflow.IWorkflowUIEnhancement;
import com.kingdee.eas.framework.client.workflow.IWorkflowUISupport;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class BasePriceControlListUI extends AbstractBasePriceControlListUI {

	private static final long serialVersionUID = 3167811896048751605L;
	private static final Logger logger = CoreUIObject
			.getLogger(BasePriceControlListUI.class);
	private boolean isNew = false;
	private List roomMap = new ArrayList();
	private Map buildId = new HashMap();
	private boolean isFill = true;
	private boolean isAll = true;
	private boolean isKdpRoom = true;

	/**
	 * output class constructor
	 */
	public BasePriceControlListUI() throws Exception {
		super();
	}

	private boolean isSellOrg() {
		boolean res = false;
		try {
			FullOrgUnitInfo fullOrginfo = SysContext.getSysContext()
					.getCurrentOrgUnit().castToFullOrgUnitInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("sellHouseStrut", Boolean.TRUE,
							CompareType.EQUALS));
			if (fullOrginfo != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("unit.id", fullOrginfo.getId()
								.toString(), CompareType.EQUALS));
			}
			filter.getFilterItems().add(
					new FilterItemInfo("tree.id", OrgConstants.SALE_VIEW_ID,
							CompareType.EQUALS));
			res = FDCOrgStructureFactory.getRemoteInstance().exists(filter);
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "获得售楼组织失败!");
		}

		return res;
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {

		if (this.kdpMain.getSelectedComponent().getName().equals("kdpNewRoom")) {
			this.tblRoom.removeRows();
			List roomList = null;
			if (!"".equals(filterStr().trim())) {
				roomList = BasePriceControlFactory.getRemoteInstance()
						.getRoomInfoList(filterStr());

			}
			if (roomList != null && roomList.size() > 0) {
				for (int i = 0; i < roomList.size(); i++) {
					RoomInfo room = (RoomInfo) roomList.get(i);
					loadRoomInfo(room);
				}
			}
		} else {
			this.execQuery();
		}

	}

	public void TreeViewPrice_valueChanged(
			javax.swing.event.TreeSelectionEvent e) throws Exception {
		if (this.kdpMain.getSelectedComponent().getName().equals("kdpNewRoom")) {
			this.tblRoom.removeRows();
			List roomList = null;
			if (filterStr() != null && !"".equals(filterStr().trim())) {
				roomList = BasePriceControlFactory.getRemoteInstance()
						.getRoomInfoList(filterStr());
			}
			if (roomList != null && roomList.size() > 0) {
				for (int i = 0; i < roomList.size(); i++) {
					RoomInfo room = (RoomInfo) roomList.get(i);
					loadRoomInfo(room);
				}
			}
		} else {
			this.execQuery();
		}
	}

	private void loadRoomInfo(RoomInfo room) {
		if (room == null) {
			return;
		}
		this.tblRoom.checkParsed();
		int activeRowIndex = this.tblRoom.getSelectManager()
				.getActiveRowIndex();
		IRow row = null;
		if (activeRowIndex == -1) {
			row = this.tblRoom.addRow();
		} else {
			row = this.tblRoom.addRow(activeRowIndex + 1);
		}

		addNewRoom(row, room);
	}

	private void addNewRoom(IRow row, RoomInfo room) {
		if (room != null) {
			row.getCell("id").setValue(room.getId());
			if (room.getDisplayName() != null) {
				row.getCell("roomNumber").setValue(room.getDisplayName());
			}
			if (room.getRoomNo() != null) {
				row.getCell("number").setValue(room.getRoomNo());
			}
			if (room.getSellType() != null) {
				row.getCell("saleType").setValue(room.getSellType());
			}
			if (room.getStandardTotalAmount() != null) {
				row.getCell("stanardTotal").setValue(
						room.getStandardTotalAmount());
			}
			if (room.getBaseStandardPrice() != null) {
				row.getCell("stanardBasePrice").setValue(
						room.getBaseStandardPrice());
			}
			if (room.getBuildingArea() != null) {
				row.getCell("buildArea").setValue(room.getBuildingArea());
			}
			if (room.getBuildPrice() != null) {
				row.getCell("buildPrice").setValue(room.getBuildPrice());
			}
			if (room.getBaseBuildingPrice() != null) {
				row.getCell("buildBasePrice").setValue(
						room.getBaseBuildingPrice());
			}
			if (room.getRoomArea() != null) {
				row.getCell("roomArea").setValue(room.getRoomArea());
			}
			if (room.getRoomPrice() != null) {
				row.getCell("roomPrice").setValue(room.getRoomPrice());
			}
			if (room.getBaseRoomPrice() != null) {
				row.getCell("roomBasePrice").setValue(room.getBaseRoomPrice());
			}
			if (room.getBuilding() != null) {
				row.getCell("buildingId").setValue(room.getBuilding());
			}
			if(room.getSellState()!=null){
				row.getCell("sellState").setValue(room.getSellState());
			}

		}
	}

	protected void execQuery() {
		super.execQuery();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);

		if (this.kdpMain.getSelectedComponent().getName().equals("kdpNewRoom")) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.TreeViewPrice
					.getLastSelectedPathComponent();
			SellProjectInfo sellProject = null;
			uiContext.put("selectBuilding", null);
			if (node != null) {
				if (node.getUserObject() instanceof SellProjectInfo) {
					sellProject = (SellProjectInfo) node.getUserObject();
					if (this.kdpMain.getSelectedComponent().getName().equals(
							"kdpNewRoom")) {
						if (this.isFill) {
							int[] selectRows = KDTableUtil
									.getSelectedRows(this.tblRoom);
							if (selectRows.length > 0) {
								int dex = selectRows[0];
								IRow row = this.tblRoom.getRow(dex);
								if (row == null) {
									return;
								}

								BuildingInfo info = (BuildingInfo) row.getCell(
										"buildingId").getValue();
								if (info != null) {
									uiContext.put("selectBuilding", info
											.getId().toString());
								}
							}
						}
					}
				} else if (node.getUserObject() instanceof SubareaInfo) {
					SubareaInfo subArea = (SubareaInfo) node.getUserObject();
					sellProject = subArea.getSellProject();
					uiContext.put("selectBuilding", null);
				} else if (node.getUserObject() instanceof BuildingInfo) {
					BuildingInfo building = (BuildingInfo) node.getUserObject();
					if(building!=null){
						sellProject = getProjectInfoByBuilding(building);
					}
					if (this.kdpMain.getSelectedComponent().getName().equals(
							"kdpNewRoom")) {
						uiContext.put("selectBuilding", (BuildingInfo) node
								.getUserObject());

					}
				} else if (node.getUserObject() instanceof BuildingUnitInfo) {
					BuildingUnitInfo unit = (BuildingUnitInfo) node
							.getUserObject();
					if(unit.getBuilding()!=null){
							sellProject = getProjectInfoByBuilding(unit.getBuilding());
					}
					if (this.kdpMain.getSelectedComponent().getName().equals(
							"kdpNewRoom")) {
						uiContext.put("selectBuilding", unit.getBuilding());
					}
					uiContext.put("selectUnit", unit);
				} else if (node.getUserObject() instanceof OrgStructureInfo) {
					if (this.isNew) {
						FDCMsgBox.showWarning(this, "请先选择项目!");
						this.abort();
					}
				}

				uiContext.put("selectRoom", this.roomMap);
				uiContext.put("sellProject", sellProject);
				uiContext.put("selectBuild", this.buildId);
			}
		} else {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeMain
					.getLastSelectedPathComponent();
			SellProjectInfo sellProject = null;
			uiContext.put("selectBuilding", null);
			if (node != null) {
				if (node.getUserObject() instanceof SellProjectInfo) {
					sellProject = (SellProjectInfo) node.getUserObject();
					
				} else if (node.getUserObject() instanceof SubareaInfo) {
					SubareaInfo subArea = (SubareaInfo) node.getUserObject();
					sellProject = subArea.getSellProject();
					uiContext.put("selectBuilding", null);
				} else if (node.getUserObject() instanceof BuildingInfo) {
					sellProject = ((BuildingInfo) node.getUserObject())
							.getSellProject();
					
				} else if (node.getUserObject() instanceof BuildingUnitInfo) {
					BuildingUnitInfo unit = (BuildingUnitInfo) node
							.getUserObject();
					sellProject = unit.getBuilding().getSellProject();
					
				} else if (node.getUserObject() instanceof OrgStructureInfo) {
					if (this.isNew) {
						FDCMsgBox.showWarning(this, "请先选择项目!");
						this.abort();
					}
				}
				uiContext.put("sellProject", sellProject);
			}
		}

	}

	private SellProjectInfo getProjectInfoByBuilding(BuildingInfo building) {
		SellProjectInfo project = null;
		BuildingInfo info = null;
		try {
			info = BuildingFactory.getRemoteInstance().getBuildingInfo("select id,sellProject.id,sellProject.name,sellProject.number where id='"+building.getId().toString()+"'");
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		if(info!=null && info.getSellProject()!=null){
			project = info.getSellProject();
		}
		
		return project;
	}

	protected void initTree() throws Exception {
		if (this.kdpMain.getSelectedComponent().getName().equals("kdpNewRoom")) {
			//this.TreeViewPrice.setModel(FDCTreeHelper.getSellProjectTreeForSHE
			// (this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
			this.TreeViewPrice.setModel(FDCTreeHelper.getUnitTreeForSHE(
					actionOnLoad, MoneySysTypeEnum.SalehouseSys));
			this.TreeViewPrice.expandAllNodes(true,
					(TreeNode) this.TreeViewPrice.getModel().getRoot());
			this.TreeViewPrice
					.setSelectionNode((DefaultKingdeeTreeNode) this.TreeViewPrice
							.getModel().getRoot());
		} else {
			//this.treeMain.setModel(FDCTreeHelper.getUnitTreeForSHE(actionOnLoad
			// , MoneySysTypeEnum.SalehouseSys));
			this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(
					this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
			this.treeMain.setShowsRootHandles(true);
			this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain
					.getModel().getRoot());
			this.treeMain
					.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain
							.getModel().getRoot());
		}
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
		if (e.getType() == KDTStyleConstants.BODY_ROW
				&& e.getButton() == MouseEvent.BUTTON1
				&& e.getClickCount() == 2) {
			IRow row = this.tblMain.getRow(e.getRowIndex());
			Object idObj = row.getCell("id").getValue();
			if (idObj == null) {
				return;
			}
			String idStr = "";
			if (idObj instanceof String) {
				idStr = (String) idObj;
			} else if (idObj instanceof BOSUuid) {
				idStr = ((BOSUuid) idObj).toString();
			}
			if (!idStr.equals("")) {
				String uiClassName = "com.kingdee.eas.fdc.sellhouse.client.BasePriceControlEditUI";
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, idStr);
				try {
					IUIWindow uiWindow = UIFactory.createUIFactory(
							UIFactoryName.NEWTAB).create(uiClassName,
							uiContext, null, OprtState.VIEW);
					uiWindow.show();
				} catch (UIException e1) {
					logger.error(e1.getMessage() + "打开户型界面失败！");
				}

			}
		}

		// this.execQuery();
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return BasePriceControlEditUI.class.getName();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return BasePriceControlFactory.getRemoteInstance();
	}

	protected void refresh(ActionEvent e) throws Exception {
		if (this.kdpMain.getSelectedComponent().getName().equals("kdpNewRoom")) {
			this.tblRoom.removeRows();
			List roomList = BasePriceControlFactory.getRemoteInstance()
					.getRoomInfoList(filterStr());
			if (roomList != null && roomList.size() > 0) {
				for (int i = 0; i < roomList.size(); i++) {
					RoomInfo room = (RoomInfo) roomList.get(i);
					loadRoomInfo(room);
				}
			}
		} else {
			this.execQuery();
		}
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		
		FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			if (node.getUserObject() instanceof SellProjectInfo) {
				String allSpIdStr = FDCTreeHelper
						.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node,
								"SellProject").keySet());
				if (allSpIdStr.trim().length() == 0) {
					allSpIdStr = "'nullnull'";
				}

				filter.getFilterItems().add(
						new FilterItemInfo("project.id", allSpIdStr,
								CompareType.INNER));
				
			} else if (node.getUserObject() instanceof OrgStructureInfo) {
				String orgUnitIdStr = "'nullnull'";
				filter.getFilterItems().add(
						new FilterItemInfo("orgUnit.id", orgUnitIdStr,
								CompareType.INNER));
			} else if (node.getUserObject() instanceof BuildingInfo) {// 楼栋
				BuildingInfo building = (BuildingInfo) node.getUserObject();
				String buildingId = building.getId().toString();
				filter.getFilterItems().add(
						new FilterItemInfo("building.id", buildingId,
								CompareType.EQUALS));
			} else if (node.getUserObject() instanceof BuildingUnitInfo) {// 单元
				BuildingUnitInfo unit = (BuildingUnitInfo) node.getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("unit.id", unit.getId().toString(),
								CompareType.EQUALS));
			} else if (node.getUserObject() instanceof SubareaInfo) { // 分区
				SubareaInfo subarea = (SubareaInfo) node.getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("building.subarea.id", subarea
								.getId().toString(), CompareType.EQUALS));
			}
		}
		// 合并查询条件
		viewInfo = (EntityViewInfo) mainQuery.clone();
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

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
		if(getExistChild()>0){
			FDCMsgBox.showWarning(this,"非末级项目不能新增!");
			this.abort();
		}
		this.isNew = true;
		this.isFill = true;
		this.buildId.clear();
		this.roomMap.clear();
		if (this.kdpMain.getSelectedComponent().getName().equals("kdpNewRoom")) {
			int[] selectRows = KDTableUtil.getSelectedRows(this.tblRoom);
			if (selectRows.length > 0) {
				int comfim = MsgBox.NO;
				
				comfim = MsgBox.showConfirm2New(this, "已售房间是否参与底价控制？");
				
				if (comfim == MsgBox.NO) {
					this.isAll = false;
				}
				else {
					this.isAll = true;
				}
				for (int i = 0; i < selectRows.length; i++) {
					int select = selectRows[i];
					IRow row = this.tblRoom.getRow(select);
					if (row == null) {
						continue;
					}
					RoomSellStateEnum state = (RoomSellStateEnum)row.getCell("sellState").getValue();
					if(!this.isAll){
						if(!state.equals(RoomSellStateEnum.Init)
						&&!state.equals(RoomSellStateEnum.OnShow)
						&&!state.equals(RoomSellStateEnum.KeepDown)
						&&!state.equals(RoomSellStateEnum.SincerPurchase)
						){
							continue;
						}
					}
					RoomInfo room = new RoomInfo();
					if (row.getCell("id").getValue() != null) {
						room.setId(BOSUuid.read(row.getCell("id").getValue()
								.toString()));
					}
					if (row.getCell("roomNumber").getValue() != null) {
						room.setDisplayName(row.getCell("roomNumber")
								.getValue().toString());
					}
					if (row.getCell("number").getValue() != null) {
						room.setRoomNo(row.getCell("number").getValue()
								.toString());
					}
					if (row.getCell("saleType").getValue() != null) {
						room.setSellType((SellTypeEnum) row.getCell("saleType")
								.getValue());
					}
					if (row.getCell("stanardTotal").getValue() != null) {
						room.setStandardTotalAmount((BigDecimal) row.getCell(
								"stanardTotal").getValue());
					}
					if (row.getCell("buildArea").getValue() != null) {
						room.setBuildingArea((BigDecimal) row.getCell(
								"buildArea").getValue());
					}
					if (row.getCell("buildPrice").getValue() != null) {
						room.setBuildPrice((BigDecimal) row.getCell(
								"buildPrice").getValue());
					}
					if (row.getCell("roomArea").getValue() != null) {

						room.setRoomArea((BigDecimal) row.getCell("roomArea")
								.getValue());
					}
					if (row.getCell("roomPrice").getValue() != null) {
						room.setRoomPrice((BigDecimal) row.getCell("roomPrice")
								.getValue());
					}
					if (row.getCell("stanardBasePrice").getValue() != null) {
						room.setBaseStandardPrice((BigDecimal) row.getCell(
								"stanardBasePrice").getValue());
					}
					if (row.getCell("buildBasePrice").getValue() != null) {
						room.setBaseBuildingPrice((BigDecimal) row.getCell(
								"buildBasePrice").getValue());
					}
					if (row.getCell("roomBasePrice").getValue() != null) {
						room.setBaseRoomPrice((BigDecimal) row.getCell(
								"roomBasePrice").getValue());
					}

					if (row.getCell("buildingId").getValue() != null) {
						BuildingInfo build = (BuildingInfo) row.getCell(
								"buildingId").getValue();
						if (build != null) {
								if(!this.buildId.containsKey(build.getId())){
									this.buildId.put(build.getId().toString(), build.getId().toString());
								}
						}
					}
					this.roomMap.add(room);

				}
			} else {
				FDCMsgBox.showWarning(this, "请选择记录!");
				this.abort();
			}

		}
		super.actionAddNew_actionPerformed(e);

		this.execQuery();
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		IBasePriceControl iBase = BasePriceControlFactory.getRemoteInstance();
		BasePriceControlInfo info = iBase
				.getBasePriceControlInfo("select id,name,state,isExecuted where id='"
						+ id + "'");

		if (info != null) {
			if (info.isIsExecuted()) {
				FDCMsgBox.showWarning(this, "单据已经执行，不能修改!");
				this.abort();
			} else if (info.getState().equals(FDCBillStateEnum.AUDITTED)) {
				FDCMsgBox.showWarning(this, "单据已审批，不能修改!");
				this.abort();
			} else if (info.getState().equals(FDCBillStateEnum.AUDITTING)) {
				FDCMsgBox.showWarning(this, "单据审批中，不能修改!");
				this.abort();
			}
		}

		FDCClientUtils.checkBillInWorkflow(this, id);
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		IBasePriceControl iBase = BasePriceControlFactory.getRemoteInstance();
		BasePriceControlInfo info = iBase
				.getBasePriceControlInfo("select id,name,state,isExecuted where id='"
						+ id + "'");

		if (info != null) {
			if (info.isIsExecuted()) {
				FDCMsgBox.showWarning(this, "单据已经执行，不能删除!");
				this.abort();
			} else if (info.getState().equals(FDCBillStateEnum.AUDITTED)) {
				FDCMsgBox.showWarning(this, "单据已审批，不能删除!");
				this.abort();
			} else if (info.getState().equals(FDCBillStateEnum.AUDITTING)) {
				FDCMsgBox.showWarning(this, "单据审批中，不能删除!");
				this.abort();
			}
		}
		FDCClientUtils.checkBillInWorkflow(this, id);
		super.actionRemove_actionPerformed(e);
	}

	public String filterStr() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.TreeViewPrice
				.getLastSelectedPathComponent();
		if (node == null) {
			return null;
		}
		StringBuffer str = new StringBuffer(" ");

		if (node.getUserObject() instanceof BuildingUnitInfo) {// 单元
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node
					.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			str.append("and building.FID='" + buildingId + "' ");
			str.append("and buildingUnit.FID='" + buildUnit.getId().toString()
					+ "' ");
		} else if (node.getUserObject() instanceof BuildingInfo) {// 楼栋
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			String buildingId = building.getId().toString();
			str.append("and building.FID='" + buildingId + "' ");
		} else if (node.getUserObject() instanceof SubareaInfo) { // 分区
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			str.append("and subArea.FID='" + subarea.getId().toString() + "' ");
		} else if (node.getUserObject() instanceof SellProjectInfo) { // 销售项目
			
			String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper
					.getAllObjectIdMap(node, "SellProject").keySet());
			if (allSpIdStr.trim().length() == 0) {
				allSpIdStr = "'nullnull'";
			}
			
			str.append("and sellProject.FID in (" + allSpIdStr
					+ ") ");


		} else if (node.getUserObject() instanceof OrgStructureInfo) {
		}
		return str.toString();
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	public void kdpMain_stateChanged(javax.swing.event.ChangeEvent e)
			throws Exception {
		super.kdpMain_stateChanged(e);
		if (this.isSellOrg()) {
			boolean result = true;
			if (e.getSource() != null) {
				if (((KDTabbedPane) e.getSource()).getSelectedComponent()
						.getName().equals("kdpNewRoom")) {
					result = false;
					isKdpRoom = true;
				} else {
					result = true;
					isKdpRoom = false;
					this.execQuery();
				}
			}
			this.actionView.setEnabled(result);
			this.actionLocate.setEnabled(result);
			this.actionEdit.setEnabled(result);
			this.actionRemove.setEnabled(result);
			this.actionQuery.setEnabled(result);
			this.actionPrint.setEnabled(result);
			this.actionPrintPreview.setEnabled(result);
			this.actionAudit.setEnabled(result);
			this.actionExctue.setEnabled(result);
			this.actionWorkFlow.setEnabled(result);
		}

		this.initTree();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initTable();
		this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.btnWorkFolow.setIcon(EASResource.getIcon("imgTbtn_flowchart"));
		this.btnExctue.setIcon(EASResource.getIcon("imgTbtn_execute"));

		this.tblRoom.getStyleAttributes().setLocked(true);
	}

	private void initTable() {
		if (this.tblRoom.getColumn("stanardTotal") != null) {
			this.tblRoom.getColumn("stanardTotal").getStyleAttributes()
					.setNumberFormat(FDCClientHelper.KDTABLE_NUMBER_FTM);
		}

		if (this.tblRoom.getColumn("stanardBasePrice") != null) {
			this.tblRoom.getColumn("stanardBasePrice").getStyleAttributes()
					.setNumberFormat(FDCClientHelper.KDTABLE_NUMBER_FTM);
		}
		if (this.tblRoom.getColumn("buildArea") != null) {
			this.tblRoom.getColumn("buildArea").getStyleAttributes()
					.setNumberFormat(FDCClientHelper.KDTABLE_NUMBER_FTM);
		}

		if (this.tblRoom.getColumn("buildPrice") != null) {
			this.tblRoom.getColumn("buildPrice").getStyleAttributes()
					.setNumberFormat(FDCClientHelper.KDTABLE_NUMBER_FTM);
		}
		if (this.tblRoom.getColumn("buildBasePrice") != null) {
			this.tblRoom.getColumn("buildBasePrice").getStyleAttributes()
					.setNumberFormat(FDCClientHelper.KDTABLE_NUMBER_FTM);
		}
		if (this.tblRoom.getColumn("roomArea") != null) {
			this.tblRoom.getColumn("roomArea").getStyleAttributes()
					.setNumberFormat(FDCClientHelper.KDTABLE_NUMBER_FTM);
		}
		if (this.tblRoom.getColumn("roomPrice") != null) {
			this.tblRoom.getColumn("roomPrice").getStyleAttributes()
					.setNumberFormat(FDCClientHelper.KDTABLE_NUMBER_FTM);
		}
		if (this.tblRoom.getColumn("roomBasePrice") != null) {
			this.tblRoom.getColumn("roomBasePrice").getStyleAttributes()
					.setNumberFormat(FDCClientHelper.KDTABLE_NUMBER_FTM);
		}
		if (this.tblMain.getColumn("createTime") != null) {
			this.tblMain.getColumn("createTime").getStyleAttributes()
					.setNumberFormat("yyyy-mm-dd");
		}
	}

	public void onShow() throws Exception {
		super.onShow();
		this.actionView.setEnabled(false);
		this.actionLocate.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionQuery.setEnabled(false);
		this.actionPrint.setEnabled(false);
		this.actionPrintPreview.setEnabled(false);

		if (this.isSellOrg()) {
			this.actionAddNew.setEnabled(true);
		} else {
			this.actionAddNew.setEnabled(false);
		}

		if (this.tblRoom.getColumn("number") != null) {
			this.tblRoom.getColumn("number").getStyleAttributes()
					.setHided(true);
		}
	}

	public void actionWorkFlow_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String fieldName = getQueryFieldNameBindingWf();
		String id = (String) getSelectedFieldValues(fieldName).get(0);
		IEnactmentService service = EnactmentServiceFactory
				.createRemoteEnactService();
		ProcessInstInfo processInstInfo = null;
		ProcessInstInfo[] procInsts = service
				.getProcessInstanceByHoldedObjectId(id);
		for (int i = 0, n = procInsts.length; i < n; i++) {
			if (procInsts[i].getState().startsWith("open")) {
				processInstInfo = procInsts[i];
			}
		}
		if (processInstInfo == null) {
			// 如果没有运行时流程，判断是否有可以展现的流程图并展现
			procInsts = service.getAllProcessInstancesByBizobjId(id);
			if (procInsts == null || procInsts.length <= 0)
				MsgBox.showInfo(this, EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Msg_WFHasNotInstance"));
			else if (procInsts.length == 1) {
				showWorkflowDiagram(procInsts[0]);
			} else {
				UIContext uiContext = new UIContext(this);
				uiContext.put("procInsts", procInsts);
				String className = ProcessRunningListUI.class.getName();
				IUIWindow uiWindow = UIFactory.createUIFactory(
						UIFactoryName.MODEL).create(className, uiContext);
				uiWindow.show();
			}
		} else {
			showWorkflowDiagram(processInstInfo);
		}
	}

	public final String getQueryFieldNameBindingWf() {
		if (this instanceof IWorkflowUISupport) {
			IWorkflowUIEnhancement enhancement = ((IWorkflowUISupport) this)
					.getWorkflowUIEnhancement();
			return enhancement.getQueryFieldNameBindingWf(this);
		} else
			return getKeyFieldNameForWF();
	}

	public final ArrayList getSelectedFieldValues(String fieldName) {
		ArrayList list = new ArrayList();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		for (int i = 0; i < selectRows.length; i++) {
			ICell cell = tblMain.getRow(selectRows[i]).getCell(fieldName);
			if (cell == null) {
				MsgBox.showError(EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Error_KeyField_Fail"));
				SysUtil.abort();
			}
			if (cell.getValue() != null) {
				String id = cell.getValue().toString();
				if (!list.contains(id))
					list.add(id);
			}

		}
		return list;
	}

	private void showWorkflowDiagram(ProcessInstInfo processInstInfo)
			throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("id", processInstInfo.getProcInstId());
		uiContext.put("processInstInfo", processInstInfo);
		BasicWorkFlowMonitorPanel.Show(uiContext);
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		ArrayList idList = this.getSelectedIdValues();
		if (idList.size() <= 0) {
			FDCMsgBox.showWarning(this, "请选择记录!");
			this.abort();
		}

		IBasePriceControl iBase = BasePriceControlFactory.getRemoteInstance();
		BasePriceControlInfo info = iBase
				.getBasePriceControlInfo("select id,name,state where id='"
						+ idList.get(0).toString() + "'");
		if (info != null) {
			if (!info.getState().equals(FDCBillStateEnum.SUBMITTED)) {
				FDCMsgBox.showWarning(this, "单据状态不适合做审批操作，请检查!");
				this.abort();
			}
		}

		FDCClientUtils.checkBillInWorkflow(this, idList.get(0).toString());

		try {
			iBase.auditBasePrice(BOSUuid.read(idList.get(0).toString()));
			FDCMsgBox.showWarning(this, "审批成功!");
			loadFields();

		} catch (Exception ex) {
			logger.error(ex.getMessage() + "审批失败!");
		}

		this.execQuery();

	}

	public void actionExctue_actionPerformed(ActionEvent e) throws Exception {
		super.actionExctue_actionPerformed(e);
		ArrayList idList = this.getSelectedIdValues();
		if (idList.size() <= 0) {
			FDCMsgBox.showWarning(this, "请选择记录!");
			this.abort();
		}

		IBasePriceControl iBase = BasePriceControlFactory.getRemoteInstance();
		BasePriceControlInfo info = iBase
				.getBasePriceControlInfo("select id,name,state,isExecuted where id='"
						+ idList.get(0).toString() + "'");
		if (info != null) {
			if (!info.getState().equals(FDCBillStateEnum.AUDITTED)) {
				FDCMsgBox.showWarning(this, "不能执行，请检查单据状态!");
				this.abort();
			} else {
				if (info.isIsExecuted()) {
					FDCMsgBox.showWarning(this, "已经执行，请不要重复操作!");
					this.abort();
				}
			}
		}

		FDCClientUtils.checkBillInWorkflow(this, idList.get(0).toString());

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("isExecuted"));
		BasePriceControlInfo model = new BasePriceControlInfo();
		model.setId(BOSUuid.read(idList.get(0).toString()));
		model.setIsExecuted(true);
		try {
			iBase.updatePartial(model, selector);
			FDCMsgBox.showWarning(this, "执行成功!");
			this.abort();
		} catch (Exception ex) {
			logger.error(ex.getMessage() + "执行失败!");
		}

		this.execQuery();
	}
	
	private int getExistChild() {
		DefaultKingdeeTreeNode node = null;
		int number = 0;
		if(this.isKdpRoom){
			node = (DefaultKingdeeTreeNode) TreeViewPrice
			.getLastSelectedPathComponent();
			//SellProjectInfo info = ()node.getUserObject();
			if(node.getUserObject() instanceof OrgStructureInfo){
				number++;
			}else if(node.getUserObject() instanceof SellProjectInfo){
				SellProjectInfo info = (SellProjectInfo)node.getUserObject();
				Map map = FDCTreeHelper.getAllObjectIdMap(node, "SellProject");
				if(!map.isEmpty()){
					map.remove(info.getId().toString());
					if(!map.isEmpty()){
						number++;
					}
				}
			}
			
		}else{
			node = (DefaultKingdeeTreeNode) treeMain
			.getLastSelectedPathComponent();
			if (node != null) {
				number = node.getChildCount();
			}
		}
		return number;
	}
}