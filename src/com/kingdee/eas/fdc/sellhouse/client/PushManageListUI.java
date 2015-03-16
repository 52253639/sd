/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.IDisplayRule;
import com.kingdee.eas.fdc.sellhouse.PlanisphereInfo;
import com.kingdee.eas.fdc.sellhouse.PlanisphereTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PushManageCollection;
import com.kingdee.eas.fdc.sellhouse.PushManageFactory;
import com.kingdee.eas.fdc.sellhouse.PushManageHisEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PushManageHisEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PushManageHisEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PushManageInfo;
import com.kingdee.eas.fdc.sellhouse.PushManageStateEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiptTypeStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class PushManageListUI extends AbstractPushManageListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(PushManageListUI.class);
	RoomDisplaySetting setting = new RoomDisplaySetting();
	boolean hasOnLoadFlag = false;// 用来判断是否首次进入
	private Map map = FDCSysContext.getInstance().getOrgMap();// 用来判断是否为售楼组织
	public static int initCount = 0; // 未推盘
	public static int pushCount = 0; // 已推盘

	/**
	 * output class constructor
	 */
	public PushManageListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		hasOnLoadFlag = true;
		PushManageSetViewUI.insertUIToScrollPanel(this.kDScrollPane1);
		this.btnPush.setIcon(EASResource.getIcon("imgTbtn_choosein"));
		this.menuItemPush.setIcon(EASResource.getIcon("imgTbtn_choosein"));
		this.btnPull.setIcon(EASResource.getIcon("imgTbtn_chooseout"));
		this.menuItemPull.setIcon(EASResource.getIcon("imgTbtn_chooseout"));
		this.tblMainPushHisEntry.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);

		// 监听，点击“推盘日志”时查询
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (tabbedPane.getSelectedIndex() != 0) {
					try {
						treeMain_valueChanged(null);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		// 非售楼组织，按钮不可用
		if (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString())) {
			this.actionPush.setEnabled(false);
			this.actionPull.setEnabled(false);
		}
	}

	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
		query();
		this.tblMainPushHisEntry.removeRows();
	}

	/**
	 * 初始化树
	 */
	protected void initTree() throws Exception {
		this.tblMainRoom.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		this.treeMain.setModel(FDCTreeHelper.getUnitTreeForSHE(
				this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandOnLevel(5);// 打开界面时，最多展示到第5级节点
		// 设置为多选，进行推盘或撤盘
		this.tblMainRoom.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_CELL_SELECT);
	}

	/**
	 * 选择左树，查询
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		query();
		refresh(null);
	}

	/**
	 *查询推盘日志
	 * 
	 * @throws Exception
	 */
	private void query() throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		FilterInfo filter = new FilterInfo();
		if (node != null) {
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo sellProject = (SellProjectInfo) node
						.getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sellProject
								.getId().toString()));
			} else if (node.getUserObject() instanceof BuildingInfo) {
				BuildingInfo building = (BuildingInfo) node.getUserObject();
				String buildingId = building.getId().toString();
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.buildings.id",
								buildingId));

			} else if (node.getUserObject() instanceof BuildingUnitInfo) {
				BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
						.getParent()).getUserObject();
				String buildingId = building.getId().toString();
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.buildings.id",
								buildingId));
			} else if (node.getUserObject() instanceof SubareaInfo) {
				SellProjectInfo sellProject = (SellProjectInfo) ((DefaultKingdeeTreeNode) node
						.getParent()).getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sellProject
								.getId().toString()));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}
		}

		// getDialog带有过滤信息
		if (this.getDialog() != null) {
			filter.mergeFilter(this.getDialog().getCommonFilter(), "AND");
		}
		this.mainQuery.setFilter(filter);
		this.tblMain.removeRows();
		this.tblMainPushHisEntry.removeRows();
	}

	/**
	 * 如果双击,也查看日志
	 */
	public void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			String id=this.tblMain.getRow(e.getRowIndex()).getCell("id").getValue().toString();
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", id);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PushSelectSellOrderUI.class.getName(), uiContext, null, OprtState.EDIT);
			uiWindow.show();
			query();
		}
	}

	/**
	 * 选择推盘记录，查看日志
	 */
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		this.tblMainPushHisEntry.removeRows();
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row != null) {
			String id = (String) row.getCell("id").getValue();
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("room.*");
			sic.add("pushDate");
			sic.add("pullDate");
			sic.add("puller.id");
			sic.add("puller.name");
			sic.add("orgUnit.id");
			sic.add("orgUnit.name");
			sic.add("openDate");
			view.setSelector(sic);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("head.id", id));
			view.setFilter(filter);

			// 排序
			SorterItemCollection sorts = new SorterItemCollection();
			SorterItemInfo sort = new SorterItemInfo("room.name");
			sorts.add(sort);
			view.setSorter(sorts);

			// 设置日期格式
			tblMainPushHisEntry.getColumn("pushDate").getStyleAttributes()
					.setNumberFormat("yyyy-MM-dd");
			tblMainPushHisEntry.getColumn("pullDate").getStyleAttributes()
					.setNumberFormat("yyyy-MM-dd");
			tblMainPushHisEntry.getColumn("openDate").getStyleAttributes()
			.setNumberFormat("yyyy-MM-dd");

			// 得到推盘日志，加入到界面Table中
			PushManageHisEntryCollection hisEntrys = PushManageHisEntryFactory
					.getRemoteInstance().getPushManageHisEntryCollection(view);
			if (hisEntrys != null && hisEntrys.size() > 0) {
				for (int i = 0; i < hisEntrys.size(); i++) {
					PushManageHisEntryInfo hisEntryInfo = hisEntrys.get(i);
					IRow addRow = tblMainPushHisEntry.addRow();
					tblMainPushHisEntry.setEditable(false);
					if (hisEntryInfo.getRoom() != null) {
						addRow.getCell("name").setValue(
								hisEntryInfo.getRoom().getName());
					} else {
						addRow.getCell("name").setValue("");
					}
					addRow.getCell("openDate").setValue(hisEntryInfo.getOpenDate());
					addRow.getCell("pushDate").setValue(
							hisEntryInfo.getPushDate());
					addRow.getCell("pullDate").setValue(
							hisEntryInfo.getPullDate());
					if (hisEntryInfo.getPuller() != null) {
						addRow.getCell("puller").setValue(
								hisEntryInfo.getPuller().getName());
					} else {
						addRow.getCell("puller").setValue("");
					}
					if (hisEntryInfo.getOrgUnit() != null) {
						addRow.getCell("orgUnitName").setValue(
								hisEntryInfo.getOrgUnit().getName());
					} else {
						addRow.getCell("orgUnitName").setValue("");
					}
				}
			}
		}
	}

	// 推盘图表
	protected void refresh(ActionEvent e) throws BOSException {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (!hasOnLoadFlag) { // 防止第一次打开时默认选择根节点导致房间数量过大，报10000行错误
			this.tblMainRoom.removeColumns();
			this.tblMainRoom.removeHeadRows();
			this.tblMainRoom.removeRows();
			return;
		}
		// 由于平面图树是在单元树的基础上增加平面图节点生成的，会对单元树的depth产生影响,并影响房间表头的显示，
		// 因此需把所选节点克隆一份，并除去所有的平面图节点，然后再传递进去构建房间列表
		if (node.getUserObject() instanceof PlanisphereInfo) {
			// 显示平面图
			PlanisphereInfo phInfo = (PlanisphereInfo) node.getUserObject();
			if (phInfo.getPtype().equals(PlanisphereTypeEnum.PicBuildPlane)) {
				this.kDScrollPane1.setVisible(true);
			} else {
				this.kDScrollPane1.setVisible(false);
			}
			SHEHelper.showPlanisphereTable(this.tblMainRoom, phInfo, setting);
		} else {
			this.kDScrollPane1.setVisible(true);
			DefaultKingdeeTreeNode newNode = (DefaultKingdeeTreeNode) node
					.clone();
			CommerceHelper.cloneTree(newNode, node);
			newNode.setParent((DefaultKingdeeTreeNode) node.getParent()); // 调用接口时会查询其父节点的对象
			CommerceHelper.removePlanisphereNode(newNode);

			fillRoomTableByNode(this.tblMainRoom, newNode,
					MoneySysTypeEnum.SalehouseSys, null, null, setting, null);

		}
		setSellStateCountByColor();
	}

	/**
	 * 设置每个单元格的颜色 未推盘：灰色；已推盘：白色
	 * 
	 * @throws BOSException
	 */
	private void setSellStateCountByColor() throws BOSException {
		initCount = 0;
		pushCount = 0;
		RoomDisplaySetting setting = new RoomDisplaySetting();
		Set roomId=new HashSet();
		for (int j = 0; j < this.tblMainRoom.getRowCount(); j++) {
			IRow row = this.tblMainRoom.getRow(j);
			for (int i = 0; i < this.tblMainRoom.getColumnCount(); i++) {
				if(row.getCell(i).getUserObject()!=null&&row.getCell(i).getUserObject() instanceof RoomInfo){
					RoomInfo room=(RoomInfo) row.getCell(i).getUserObject();
					if(roomId.contains(room.getId())){
						continue;
					}else{
						roomId.add(room.getId());
					}
					ICell cell = row.getCell(i);
					if (cell != null) {
						if (setting.getBaseRoomSetting().getInitColor().equals(
								cell.getStyleAttributes().getBackground())) {
							initCount++;
						} else if (setting.getBaseRoomSetting()
								.getOnShowColor().equals(
										cell.getStyleAttributes()
												.getBackground())) {
							pushCount++;
						}
					}
				}
			}
		}
		PushManageSetViewUI.insertUIToScrollPanel(this.kDScrollPane1);
	}

	/**
	 * 推盘操作
	 * 
	 */
	public void actionPush_actionPerformed(ActionEvent e) throws Exception {
		KDTSelectBlock selectBlock = null;
		Set pushRoomIds = new HashSet();
		UIContext uiContext = new UIContext(this);

		// 获取选择块的总个数
		int size = tblMainRoom.getSelectManager().size();
		if (size == 0) {
			MsgBox.showInfo("请选择房间！");
			return;
		}

		if (isPush(size, selectBlock)) {
			for (int i = 0; i < size; i++) {
				selectBlock = tblMainRoom.getSelectManager().get(i);// 第i个块的总个数
				if (selectBlock != null) {
					int top = selectBlock.getTop();
					int bottom = selectBlock.getBottom();
					int left = selectBlock.getLeft();
					int right = selectBlock.getRight();
					for (int j = top; j <= bottom; j++) {
						for (int k = left; k <= right; k++) {
							ICell cell = this.tblMainRoom.getCell(j, k); // 第i,
																			// j个cell
							RoomInfo room = (RoomInfo) cell.getUserObject();
							if(room==null) continue;
							if(!room.getBuilding().isIsGetCertificated()){
								FDCMsgBox.showWarning(this,"楼栋未取得销售许可证，禁止推盘！");
								SysUtil.abort();
							}
							if(room.getStandardTotalAmount() == null || room.getStandardTotalAmount().compareTo(FDCHelper.ZERO) == 0){
								FDCMsgBox.showWarning(this,"房间"+room.getName()+"未定价，禁止推盘！");
								SysUtil.abort();
							}
							if(!room.isIsPre()){
								FDCMsgBox.showWarning(this,"房间"+room.getName()+"没有预售复核！");
								SysUtil.abort();
			        		}
							if (room != null) {
								pushRoomIds.add(room.getId().toString());
								// 创建UI对象并打开盘次页面
								uiContext.put("sellOrder", room.getSellOrder());
							}
						}
					}
				}
			}
			if(pushRoomIds.size()==0){
				FDCMsgBox.showWarning(this,"请选择房间！");
				SysUtil.abort();
			}
			uiContext.put("pushRoomIds", pushRoomIds);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(PushSelectSellOrderUI.class.getName(), uiContext,
							null, "ADDNEW");
			uiWindow.show();
		}
		refresh(null);
		tblMain_tableSelectChanged(null);
	}

	/**
	 * 撤盘，查询已有的推盘日志，进行更新
	 * 
	 * @param room
	 * @param total
	 * @throws Exception
	 */
	public void pullSave(Set quitRooms) throws Exception {
		// 查推盘记录的ID
		if (quitRooms != null && quitRooms.size() > 0) {
			for (Iterator itor = quitRooms.iterator(); itor.hasNext();) {
				RoomInfo room = (RoomInfo) itor.next();
				EntityViewInfo entryView = new EntityViewInfo();
				SelectorItemCollection sicEntry = new SelectorItemCollection();
				entryView.setSelector(sicEntry);
				FilterInfo entryFilter = new FilterInfo();
				entryFilter.getFilterItems().add(
						new FilterItemInfo("room.id", room.getId().toString()));
				entryView.setFilter(entryFilter);

				PushManageHisEntryCollection pushEntry = PushManageHisEntryFactory
						.getRemoteInstance().getPushManageHisEntryCollection(
								entryView);
				if (pushEntry != null && pushEntry.size() > 0) {
					if (pushEntry.get(0) != null) {
						String pushId = pushEntry.get(0).getHead().getId()
								.toString();

						// 根据ID查推盘记录
						EntityViewInfo pushView = new EntityViewInfo();
						SelectorItemCollection sicPush = new SelectorItemCollection();
						sicPush.add("id");
						sicPush.add("pullTotal");
						sicPush.add("puller");
						sicPush.add("pullDate");
						sicPush.add("his.id");
						sicPush.add("his.pullDate");
						sicPush.add("his.puller");
						sicPush.add("his.pushManageState");
						sicPush.add("his.orgUnit");
						pushView.setSelector(sicPush);

						// 按日期倒排序
						SorterItemCollection sorts = new SorterItemCollection();
						SorterItemInfo sort = new SorterItemInfo("pushDate");
						sort.setSortType(SortType.DESCEND);
						sorts.add(sort);
						pushView.setSorter(sorts);

						FilterInfo filter = new FilterInfo();
						filter.getFilterItems().add(
								new FilterItemInfo("id", pushId));
						pushView.setFilter(filter);

						PushManageCollection push = PushManageFactory
								.getRemoteInstance().getPushManageCollection(
										pushView);

						if (push != null && push.size() > 0) {
							PushManageInfo pushInfo = push.get(0);// 取最新的一条推盘记录进行更新
							for (int i = 0; i < pushEntry.size(); i++) {
								PushManageHisEntryInfo hisEntryInfo = pushEntry
										.get(i);
								hisEntryInfo.setPullDate(new Date());
								hisEntryInfo.setPuller(SysContext
										.getSysContext().getCurrentUserInfo());
								hisEntryInfo
										.setPushManageState(PushManageStateEnum.PULL);
								hisEntryInfo.setOrgUnit(SysContext
										.getSysContext().getCurrentSaleUnit()
										.castToFullOrgUnitInfo());

								pushInfo.getHis().add(hisEntryInfo);

								pushInfo.setPullTotal(quitRooms.size());
								pushInfo.setPuller(SysContext.getSysContext()
										.getCurrentUserInfo());
								pushInfo.setPullDate(new Date());
							}
							PushManageFactory.getRemoteInstance()
									.updatePartial(pushInfo, sicPush);
						}
					}
				}
			}
		}
	}

	/**
	 * 撤盘操作
	 */
	public void actionPull_actionPerformed(ActionEvent e) throws Exception {
		KDTSelectBlock selectBlock = null;
		Set quitRoomIds = new HashSet();
		Set quitRooms = new HashSet();
		// 获取选择块的总个数
		int size = tblMainRoom.getSelectManager().size();
		if (size == 0) {
			MsgBox.showInfo("请选择房间！");
			return;
		}
		if (isPull(size, selectBlock)) {
			int pullTotal = 0;
			for (int i = 0; i < size; i++) {
				selectBlock = tblMainRoom.getSelectManager().get(i);// 第i个块的总个数
				if (selectBlock != null) {
					pullTotal += selectBlock.size();
					int top = selectBlock.getTop();
					int bottom = selectBlock.getBottom();
					int left = selectBlock.getLeft();
					int right = selectBlock.getRight();
					for (int j = top; j <= bottom; j++) {
						for (int k = left; k <= right; k++) {
							ICell cell = this.tblMainRoom.getCell(j, k);
							RoomInfo room = (RoomInfo) cell.getUserObject();
							if (room != null) {
								quitRoomIds.add(String.valueOf(room.getId()));
								quitRooms.add(room);
							}
						}
					}
				}
			}
			if(quitRooms.size()==0){
				FDCMsgBox.showWarning(this,"请选择房间！");
				SysUtil.abort();
			}
			pullSave(quitRooms);// 保存这些Room
			int result = MsgBox.showConfirm2New(null, "是否确认撤盘！");
			if (result != MsgBox.YES) {
				return;
			}
			PushManageFactory.getRemoteInstance().quitOrder(quitRoomIds);
			MsgBox.showInfo(this, "撤盘成功!");
		}
		refresh(null);
		tblMain_tableSelectChanged(null);
	}

	/**
	 * 判断能否推盘
	 * 
	 * @param block
	 * @param selectBlock
	 * @return
	 */
	public boolean isPush(int size, KDTSelectBlock selectBlock) {
		for (int blockIndex = 0; blockIndex < size; blockIndex++) {
			selectBlock = this.tblMainRoom.getSelectManager().get(blockIndex);
			if (selectBlock != null) {
				int top = selectBlock.getTop();
				int bottom = selectBlock.getBottom();
				int left = selectBlock.getLeft();
				int right = selectBlock.getRight();
				for (int i = top; i <= bottom; i++) {
					for (int j = left; j <= right; j++) {
						ICell cell = this.tblMainRoom.getCell(i, j);
						RoomInfo room = (RoomInfo) cell.getUserObject();
						if (room != null) {
							if (room.isIsPush()) {
								MsgBox.showInfo(this, "未推盘的房间才允许推盘");
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * 判断能否撤盘
	 * 
	 * @param block
	 * @param selectBlock
	 * @return
	 */
	public boolean isPull(int size, KDTSelectBlock selectBlock) {
		for (int blockIndex = 0; blockIndex < size; blockIndex++) {
			selectBlock = this.tblMainRoom.getSelectManager().get(blockIndex);
			if (selectBlock != null) {
				int top = selectBlock.getTop();
				int bottom = selectBlock.getBottom();
				int left = selectBlock.getLeft();
				int right = selectBlock.getRight();
				for (int i = top; i <= bottom; i++) {
					for (int j = left; j <= right; j++) {
						ICell cell = this.tblMainRoom.getCell(i, j);
						RoomInfo room = (RoomInfo) cell.getUserObject();
						if (room != null) {
							// 判断能否撤盘
							if (!room.isIsPush()) {
								MsgBox.showInfo(this, "已推盘的房间才允许撤盘");
								return false;
							} else if (!RoomSellStateEnum.OnShow.equals(room
									.getSellState())) {
								MsgBox.showInfo(this, "已经发生业务的房间不允许撤盘");
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PushManageFactory.getRemoteInstance();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected String getKeyFieldName() {
		return "id";
	}

	/**
	 * 以下是复制销控表的方法，稍作修改 SHEHelper.fillRoomTableByNode()
	 * 
	 * @param table
	 * @param root
	 * @param moneySysTypeEnum
	 * @param roomCollRestrict
	 * @param sellProjectRestrict
	 * @param setting
	 * @param panel
	 * @throws BOSException
	 */
	public void fillRoomTableByNode(KDTable table, DefaultMutableTreeNode root,
			MoneySysTypeEnum moneySysTypeEnum, RoomCollection roomCollRestrict,
			SellProjectInfo sellProjectRestrict, IDisplayRule setting,
			KDScrollPane panel) throws BOSException {
		if (root.getDepth() > 10) {
			MsgBox.showInfo("太多层组织了,不能查询!");
		}
		table.getStyleAttributes().setLocked(true);
		table.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.CENTER);
		table.getIndexColumn().getStyleAttributes().setHided(true);
		table.getStyleAttributes().setWrapText(true);
		table.setHorizonGridLineVisible(true);
		table.setVerticalGridLineVisible(true);
		table.setVerticalHeadGridLineVisible(true);
		table.setHorizonHeadGridLineVisible(true);
		table.removeColumns();
		table.removeHeadRows();
		table.removeRows();

		try {
			SHEHelper.setUnitSonNodeUpNode(root);
		} catch (Exception e2) {
			FDCMsgBox.showWarning("非显示单元节点加载失败！");
		}

		String roomFilterSql = "";
		if (sellProjectRestrict != null) { // 对房间所在的销售项目进行限制，只能显示该销售项目下的房间
			roomFilterSql += "and SellProject.fid = '"
					+ sellProjectRestrict.getId().toString() + "' ";
		}
		if (roomCollRestrict != null && roomCollRestrict.size() > 0) { // 如果对房间范围有所限制
			// ,
			// 只显示指定的房间
			String restirctRoomIdStr = "";
			for (int i = 0; i < roomCollRestrict.size(); i++)
				restirctRoomIdStr += ",'"
						+ roomCollRestrict.get(i).getId().toString() + "'";
			restirctRoomIdStr = restirctRoomIdStr.replaceFirst(",", "");
			roomFilterSql = "and Room.fid in (" + restirctRoomIdStr + ") ";
		}

		// 选择的节点变化时
		Object objectNode = root.getUserObject();
		if (objectNode instanceof BuildingUnitInfo) { // 单元对象
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) objectNode;
			roomFilterSql += "and BuildUnit.fid = '"
					+ buildUnit.getId().toString() + "'";
		} else if (objectNode instanceof BuildingInfo) { // 楼栋
			BuildingInfo building = (BuildingInfo) objectNode;
			roomFilterSql += "and Build.fid = '" + building.getId().toString()
					+ "'";
		} else if (objectNode instanceof SubareaInfo) { // 分区
			SubareaInfo subAreaInfo = (SubareaInfo) objectNode;
			roomFilterSql += "and SubArea.fid = '"
					+ subAreaInfo.getId().toString() + "'";
		} else if (objectNode instanceof SellProjectInfo) {
			SellProjectInfo sellProInfo = (SellProjectInfo) objectNode;
			roomFilterSql += "and SellProject.fid = '"
					+ sellProInfo.getId().toString() + "'";
		} else {// 广度查找所有的销售项目
			String sellProIdFilterStr = "'null'";
			Enumeration enumeration = root.breadthFirstEnumeration();
			while (enumeration.hasMoreElements()) {
				DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumeration
						.nextElement();
				if (element.getUserObject() instanceof SellProjectInfo) {
					SellProjectInfo tempSellProInfo = (SellProjectInfo) element
							.getUserObject();
					sellProIdFilterStr += ",'"
							+ tempSellProInfo.getId().toString() + "'";
				}
			}
			roomFilterSql += "and SellProject.fid in (" + sellProIdFilterStr
					+ ")";
		}

		RoomCollection rooms = new RoomCollection();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		// 增加房间收款状态字段在最后。by zgy 2010-12-17
		builder
				.appendSql("select Build.fIsGetCertificated,room.FIsPre,Room.fstandardTotalAmount,Room.fSub,Room.fid,Room.fname_l2,Room.fnumber,Room.fdisplayName,Room.funit,Room.ffloor,Room.fserialNumber,Room.fendSerialNumber,Room.FISMortagaged,"
						+ "Room.fsellState,Room.fisForSHE,Room.fisForTen,Room.fisForPPM,Room.ftenancyState,Room.fbuildingId,Build.fsellProjectId,Room.FReceiptTypeState,Room.FIsPush from t_she_room Room  ");
		builder
				.appendSql("inner join t_she_building Build on Room.fbuildingId = Build.fid ");
		builder
				.appendSql("inner join t_she_sellProject SellProject on Build.fsellProjectId = SellProject.fid ");
		builder
				.appendSql("left outer join t_she_buildingUnit BuildUnit on Room.fbuildUnitId = BuildUnit.fid ");
		builder
				.appendSql("left outer join t_she_subarea SubArea on Build.fsubareaId = SubArea.fid ");
		if (!roomFilterSql.equals("")) {
			builder.appendSql("where " + roomFilterSql.replaceFirst("and", ""));
		}

		try {
			IRowSet rs = builder.executeQuery();
			while (rs.next()) {
				RoomInfo newInfo = new RoomInfo();
				String fid = rs.getString("fid");
				String fname = rs.getString("fname_l2");
				String fnumber = rs.getString("fnumber");
				String fdisplayName = rs.getString("fdisplayName");
				newInfo.setDisplayName(fdisplayName);
				newInfo.setId(BOSUuid.read(fid));
				newInfo.setName(fname);
				newInfo.setNumber(fnumber);
				int funit = rs.getInt("funit");
				newInfo.setUnit(funit);
				int ffloor = rs.getInt("ffloor");
				newInfo.setFloor(ffloor);
				int fserialNumber = rs.getInt("fserialNumber");
				newInfo.setSerialNumber(fserialNumber);
				int fendSerialNumber = rs.getInt("fendSerialNumber");
				newInfo.setEndSerialNumber(fendSerialNumber);
				newInfo.setIsMortagaged(rs.getBoolean("FISMortagaged"));
				String fsellState = rs.getString("fsellState");
				if (fsellState != null) {
					if (fsellState.equals("Onshow"))
						fsellState = "OnShow"; // 名称和值不一样的情况
					newInfo.setSellState((RoomSellStateEnum) RoomSellStateEnum
							.getEnumMap().get(fsellState));
				}
				String ReceiptTypeState = rs.getString("FReceiptTypeState");
				if (ReceiptTypeState != null) {
					newInfo
							.setReceiptTypeState((ReceiptTypeStateEnum) ReceiptTypeStateEnum
									.getEnumMap().get(ReceiptTypeState));
				}
				newInfo.setIsForSHE(rs.getBoolean("fisForSHE"));
				newInfo.setIsForTen(rs.getBoolean("fisForTen"));
				newInfo.setIsForPPM(rs.getBoolean("fisForPPM"));
				String ftenancyState = rs.getString("ftenancyState");
				newInfo.setIsPush(rs.getBoolean("FIsPush"));
				if (ftenancyState != null) {
					if (ftenancyState.equals("UNTenancy"))
						ftenancyState = "unTenancy"; // 名称和值不一样的情况
					else if (ftenancyState.equals("WaitTenancy"))
						ftenancyState = "waitTenancy";
					else if (ftenancyState.equals("KeepTenancy"))
						ftenancyState = "keepTenancy";
					else if (ftenancyState.equals("NewTenancy"))
						ftenancyState = "newTenancy";
					else if (ftenancyState.equals("ContinueTenancy"))
						ftenancyState = "continueTenancy";
					else if (ftenancyState.equals("EnlargeTenancy"))
						ftenancyState = "enlargeTenancy";
					else if (ftenancyState.equals("SincerObligate"))
						ftenancyState = "sincerObligate";
					newInfo.setTenancyState((TenancyStateEnum) TenancyStateEnum
							.getEnumMap().get(ftenancyState));
				}
				String fbuildingId = rs.getString("fbuildingId");
				if (fbuildingId != null) {
					BuildingInfo tempBuild = new BuildingInfo();
					tempBuild.setId(BOSUuid.read(fbuildingId));
					String fsellProjectId = rs.getString("fsellProjectId");
					if (fsellProjectId != null) {
						SellProjectInfo tempSellPro = new SellProjectInfo();
						tempSellPro.setId(BOSUuid.read(fsellProjectId));
						tempBuild.setSellProject(tempSellPro);
						tempBuild.setIsGetCertificated(rs.getBoolean("fIsGetCertificated"));
					}
					newInfo.setBuilding(tempBuild);
				}
				newInfo.setSub(rs.getString("fSub"));
				newInfo.setStandardTotalAmount(rs.getBigDecimal("fstandardTotalAmount"));
				newInfo.setIsPre(rs.getBoolean("FIsPre"));
				rooms.add(newInfo);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			SysUtil.abort();
		}

		if (rooms.size() > 0 && moneySysTypeEnum == null) {
			/*
			 * //显示房间所属的项目来源都是一个，所以项目属性取随便一间的 ---- 晕，暂时跟着你错下去--不这么做那应该咋做
			 * 
			 * SellProjectResourceEnum projectResenum =
			 * rooms.get(0).getBuilding().getSellProject().getProjectResource();
			 * RoomDisplaySetViewUI.insertUIToScrollPanel(panel,projectResenum);
			 */
			try {
				BuildingInfo buildInfo = BuildingFactory.getRemoteInstance()
						.getBuildingInfo(
								"select sellProject.ProjectResource where id='"
										+ rooms.get(0).getBuilding().getId()
												.toString() + "'");
				RoomDisplaySetViewUI.insertUIToScrollPanel(panel, buildInfo
						.getSellProject().getProjectResource());
			} catch (EASBizException e) {
				e.printStackTrace();
				SysUtil.abort();
			}
		}
		if (rooms.size() > 0) {
			Map buildingMaxFloor = new HashMap(); // 楼栋id和最大楼层的映射
			Map buildingMinFloor = new HashMap();
			Map buildingMaxNum = new HashMap(); // 楼栋id和房间的最大序列号的映射
			Map buildingMinNum = new HashMap();
			for (int i = 0; i < rooms.size(); i++) {
				RoomInfo room = rooms.get(i);
				// int unitNum =
				// room.getBuildUnit()==null?0:room.getBuildUnit().getSeq();
				int unitNum = room.getUnit();
				String key = room.getBuilding().getId().toString() + unitNum;
				Integer maxFloor = (Integer) buildingMaxFloor.get(key);
				Integer minFloor = (Integer) buildingMinFloor.get(key);
				Integer maxNum = (Integer) buildingMaxNum.get(key);
				Integer minNum = (Integer) buildingMinNum.get(key);
				if (minFloor == null || room.getFloor() < minFloor.intValue()) {
					buildingMinFloor.put(key, new Integer(room.getFloor()));
				}
				if (maxFloor == null || room.getFloor() > maxFloor.intValue()) {
					buildingMaxFloor.put(key, new Integer(room.getFloor()));
				}
				if (minNum == null
						|| room.getSerialNumber() < minNum.intValue()) {
					buildingMinNum
							.put(key, new Integer(room.getSerialNumber()));
				}
				if (maxNum == null
						|| room.getEndSerialNumber() > maxNum.intValue()) {
					buildingMaxNum.put(key, new Integer(room
							.getEndSerialNumber()));
				}
			}
			IColumn column = table.addColumn();
			column.setKey("floor");
			column.setWidth(30);
			Enumeration enumeration = root.depthFirstEnumeration();
			boolean haveUnit = false;
			while (enumeration.hasMoreElements()) {
				DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumeration
						.nextElement();
				Object object = element.getUserObject();
				String key = null;
				if (object instanceof Integer) { // 已替换为单元对象 --- 暂时保留
					int unit = ((Integer) object).intValue();
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) element
							.getParent();
					BuildingInfo building = (BuildingInfo) parent
							.getUserObject();
					key = building.getId().toString() + unit;
					haveUnit = true;
				} else if (object instanceof BuildingUnitInfo) {
					int unit = ((BuildingUnitInfo) object).getSeq();
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) element
							.getParent();
					BuildingInfo building = (BuildingInfo) parent
							.getUserObject();
					key = building.getId().toString() + unit;
					haveUnit = true;
				} else if (object instanceof BuildingInfo) {
					BuildingInfo building = (BuildingInfo) object;
					key = building.getId().toString() + 0;
				}
				Integer minNum = (Integer) buildingMinNum.get(key);
				Integer maxNum = (Integer) buildingMaxNum.get(key);
				if (minNum == null || maxNum == null) {
					continue;
				}
				for (int i = minNum.intValue(); i <= maxNum.intValue(); i++) { // 构建表体
					column = table.addColumn();
					column.setKey(key + i);
					column.setWidth(setting.getRoomWidth());
				}
			}
			int minRow = 1;
			int maxRow = 0;
			Collection collection = buildingMaxFloor.values();
			for (Iterator iter = collection.iterator(); iter.hasNext();) {
				Integer num = (Integer) iter.next();
				if (num.intValue() > maxRow)
					maxRow = num.intValue();
			}
			// 加上对最小楼层，即是 地下楼层的支持， 搞不懂 上面 为什么用这种方法 去求最大楼层，但是既然用了这么久，我也仿照这个去求最小楼层
			// by laiquan_luo
			Collection tempColl = buildingMinFloor.values();
			for (Iterator iter = tempColl.iterator(); iter.hasNext();) {
				Integer num = (Integer) iter.next();
				if (num.intValue() < minRow)
					minRow = num.intValue();
			}

			for (int i = minRow; i <= maxRow; i++) {

				IRow row = table.addRow();
				row.setUserObject(new Integer(i));

				row.setHeight(setting.getRoomHeight());

				int currFloor = maxRow - i + minRow;
				row.getCell("floor").setValue(new Integer(currFloor) + "层");
				if (currFloor == 0)
					row.getStyleAttributes().setHided(true); // 0层隐藏
			}
			/**
			 * 查询出所有 房间状态为待售，且有对应的认购单。 预定申请、预定复核、认购申请、认购审批中、认购审批这5种状态的认购单
			 */

			FDCSQLBuilder purChBuilder = new FDCSQLBuilder();
			purChBuilder
					.appendSql(" select Room.fid,Room.fname_l2,Room.fnumber,Room.fdisplayName,Room.funit,Room.ffloor,Room.fserialNumber,Room.fendSerialNumber, "
							+ " Room.fsellState,Room.fisForSHE,Room.fisForTen,Room.fisForPPM,Room.ftenancyState,Room.fbuildingId,Build.fsellProjectId ");
			purChBuilder
					.appendSql(" from  t_she_purchase purchase left join t_she_room Room  on purchase.froomid = room.fid ");
			purChBuilder
					.appendSql(" inner join t_she_building Build on Room.fbuildingId = Build.fid  ");
			purChBuilder
					.appendSql(" inner join t_she_sellProject SellProject on Build.fsellProjectId = SellProject.fid  ");
			purChBuilder
					.appendSql(" left outer join t_she_buildingUnit BuildUnit on Room.fbuildUnitId = BuildUnit.fid  ");
			purChBuilder
					.appendSql(" left outer join t_she_subarea SubArea on Build.fsubareaId = SubArea.fid  ");
			purChBuilder
					.appendSql(" where purchase.FPurchaseState in ('PrePurchaseApply','PrePurchaseCheck','PurchaseApply','PurchaseAuditing','PurchaseAudit') ");
			purChBuilder
					.appendSql(" and Room.fsellState not in('SincerPurchase') ");

			Set roomPurSet = new HashSet();
			if (!roomFilterSql.equals("")) {
				purChBuilder.appendSql(roomFilterSql);
			}
			IRowSet rsPur = purChBuilder.executeQuery();
			try {
				while (rsPur.next()) {
					roomPurSet.add(rsPur.getString("fid"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				SysUtil.abort();
			}

			try {
				fillRoomTable(table, rooms, setting, moneySysTypeEnum, panel,
						roomPurSet);
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				logger.warn("房间图像加载失败" + e.getMessage());
			}
			// 代码优化 xin_wang 2011.03.31
			String text[] = null;
			if (haveUnit) {
				text = new String[] { "编号", "单元", "楼栋", "分区", "项目", "组织",
						"组织1", "组织2", "组织3", "组织4", "组织5", "组织6" }; // 对于多级组织在名称上区分开
			} else {
				text = new String[] { "编号", "楼栋", "分区", "项目", "组织", "组织1",
						"组织2", "组织3", "组织4", "组织5", "组织6" };
			}
			for (int i = 0; i < root.getDepth() + 1; i++) { // 构建表头
				IRow row = table.addHeadRow(0);
				row.getCell(0).setValue(text[i]);
			}

			int count = 1;
			if (root.getChildCount() == 0) {
				String key = null;
				if (objectNode instanceof BuildingInfo) {
					key = ((BuildingInfo) objectNode).getId().toString() + 0;
				} else if (objectNode instanceof Integer) { // 已替换为单元对象 -- 暂时保留
					int unit = ((Integer) objectNode).intValue();
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) root
							.getParent();
					BuildingInfo building = (BuildingInfo) parent
							.getUserObject();
					key = building.getId().toString() + unit;
				} else if (objectNode instanceof BuildingUnitInfo) {
					int unit = ((BuildingUnitInfo) objectNode).getSeq();
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) root
							.getParent();
					BuildingInfo building = (BuildingInfo) parent
							.getUserObject();
					key = building.getId().toString() + unit;
				} else {
					return;
				}
				Integer minNum = (Integer) buildingMinNum.get(key);
				Integer maxNum = (Integer) buildingMaxNum.get(key);
				if (minNum == null || maxNum == null) {
					return;
				}
				for (int i = minNum.intValue(); i <= maxNum.intValue(); i++) {
					table.getHeadRow(0).getCell(i + 1 - minNum.intValue())
							.setValue(i + "号");
				}
			} else {
				for (int i = 0; i < root.getChildCount(); i++) {
					count += setTableHeadByNode(table,
							(DefaultMutableTreeNode) root.getChildAt(i), count,
							buildingMinNum, buildingMaxNum);
				}
			}
		}

		SHEHelper.delNodeFromTree(root);
	}

	private void fillRoomTable(KDTable table, RoomCollection rooms,
			IDisplayRule setting, MoneySysTypeEnum moneySysTypeEnum,
			KDScrollPane panel11, Set roomPurSet) throws BOSException,
			EASBizException {
		// 解决附属房产对应的编码问题
		Map roomAttachMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select roomEntry.fheadId,room.fnumber from t_she_RoomAttachmentEntry roomEntry inner join t_she_room Room on roomEntry.froomId = Room.fid ");
		try {
			IRowSet rs = builder.executeQuery();
			while (rs.next()) {
				String froomId = rs.getString("fheadId");
				String fnumber = rs.getString("fnumber");
				if (roomAttachMap.containsKey(froomId)) {
					String numberStr = (String) roomAttachMap.get(froomId);
					roomAttachMap.put(froomId, numberStr + "," + fnumber);
				} else {
					roomAttachMap.put(froomId, fnumber);
				}
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
			SysUtil.abort();
		} catch (SQLException e) {
			e.printStackTrace();
			SysUtil.abort();
		}

		Integer minRow = (Integer) table.getRow(0).getUserObject();
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			String buildingId = room.getBuilding().getId().toString();
			// int unitNum =
			// room.getBuildUnit()==null?0:room.getBuildUnit().getSeq();
			int unitNum = room.getUnit();
			String key = buildingId + unitNum;
			for (int j = room.getSerialNumber(); j <= room.getEndSerialNumber(); j++) {
				ICell cell = table.getCell(table.getRowCount()
						+ minRow.intValue() - 1 - room.getFloor(), key + j);
				if (cell == null)
					continue;
				cell.getStyleAttributes().setFont(setting.getFont());
				cell.getStyleAttributes().setFontColor(setting.getFrontColor());
				String text = "";
				Object object = room.getDisplayName();
				// Object object = room.get(displayField);
				// if (displayField.equals("houseProperty")) {
				// object = room.getHouseProperty();
				// }
				if (object != null) {
					if (object instanceof BigDecimal) {
						text += ((BigDecimal) object).setScale(2,
								BigDecimal.ROUND_HALF_UP);
					} else {
						text += object;
					}
				}
				if (cell.getUserObject() != null) {
					// log.info("定义房间重叠,房间代码:" + text+" ; "+ key +
					// ";floor="+room.getFloor() + ";fUnit="+room.getUnit() );
					MsgBox.showError("定义房间重叠,房间代码:" + text);
					SysUtil.abort();
				}

				if (setting.getAttachDisType() == 0
						|| setting.getAttachDisType() == 1) {
					if (room.getId() != null) { // 若是创建房间时，这里可能为空
						String attachNumbers = (String) roomAttachMap.get(room
								.getId().toString());
						if (attachNumbers != null) {
							if (setting.getAttachDisType() == 0)
								text += "\n绑定"
										+ attachNumbers.split(",").length
										+ "个房产";
							else
								text += "\n绑定:" + attachNumbers;
						}
					}
				} else if (setting.getAttachDisType() == 2) {

				}
				boolean isPush = room.isIsPush();
				RoomDisplaySetting roomSetting = new RoomDisplaySetting();
				cell.getStyleAttributes().setBackground(
						getRoomColorByState(isPush, roomSetting));

				cell.setValue(text);
				cell.setUserObject(room);
			}
			if (room.getSerialNumber() != room.getEndSerialNumber()) {
				int columnOff = table.getColumnIndex(key
						+ room.getSerialNumber());
				table.getMergeManager().mergeBlock(
						table.getRowCount() + minRow.intValue() - 1
								- room.getFloor(),
						columnOff,
						table.getRowCount() + minRow.intValue() - 1
								- room.getFloor(),
						columnOff + room.getEndSerialNumber()
								- room.getSerialNumber());
			}
			if (room.getSub() != null && room.getSub().indexOf(",") > 0) {
				String[] sub = room.getSub().split(",");
				int columnOff = table.getColumnIndex(key
						+ room.getSerialNumber());
				boolean isRow = true;
				int merger = sub.length;
				if (!sub[0].split(";")[0].equals(sub[1].split(";")[0])) {
					isRow = false;
				}
				if (!isRow) {
					if (room.getFloor() < 0) {
						table.getMergeManager().mergeBlock(
								table.getRowCount() + minRow.intValue() - 1
										- room.getFloor() - merger,
								columnOff,
								table.getRowCount() + minRow.intValue() - 1
										- room.getFloor(), columnOff);
					} else {
						table.getMergeManager().mergeBlock(
								table.getRowCount() + minRow.intValue() - 1
										- room.getFloor() - merger + 1,
								columnOff,
								table.getRowCount() + minRow.intValue() - 1
										- room.getFloor(), columnOff);
					}
				}
			}
		}

		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			if (roomPurSet.contains(String.valueOf(room.getId()))) {
				String buildingId = room.getBuilding().getId().toString();
				int unitNum = room.getUnit();
				String key = buildingId + unitNum;
				for (int j = room.getSerialNumber(); j <= room
						.getEndSerialNumber(); j++) {
					ICell cell = table.getCell(table.getRowCount()
							+ minRow.intValue() - 1 - room.getFloor(), key + j);
					if (cell == null)
						continue;
					cell.getStyleAttributes().setBackground(Color.GRAY);
				}
			}
		}

		for (int i = 0; i < table.getRowCount(); i++) {
			for (int j = 0; j < table.getColumnCount(); j++) {
				ICell cell = table.getRow(i).getCell(j);
				if (cell.getUserObject() == null) {
					if (j == 0) {
						cell.getStyleAttributes().setBackground(
								new Color(184, 204, 221));
					} else {
						cell.getStyleAttributes().setBackground(Color.GRAY);
					}
				}
			}

		}
	}

	public Color getRoomColorByState(boolean isPush,
			RoomDisplaySetting roomSetting) {
		Color retColor = Color.WHITE;
		// 获取房间的项目,初始化房间状态颜色。根据售楼设置参数认购业务以实际收款为准是否勾选。
		// 当勾选情况下，按原业务逻辑不变，不存在，认购收款状态，都为认购状态 by zgy 2010-12-15
		if (!isPush) {
			retColor = roomSetting.getBaseRoomSetting().getInitColor();
		}else{
			retColor=roomSetting.getBaseRoomSetting().getOnShowColor();
		}
		return retColor;
	}

	private int setTableHeadByNode(KDTable table, DefaultMutableTreeNode node,
			int colIndex, Map buildingMinNum, Map buildingMaxNum) {
		int childRowIndex = colIndex;

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) node
					.getChildAt(i);
			childRowIndex += setTableHeadByNode(table, child, childRowIndex,
					buildingMinNum, buildingMaxNum);
		}

		int currHeadRowNum = -1;
		Object object = node.getUserObject();
		if (object instanceof Integer) // 已替换为单元对象 -- 暂时保留
			currHeadRowNum = getHeadRowNumByName(table, "单元");
		else if (object instanceof BuildingUnitInfo)
			currHeadRowNum = getHeadRowNumByName(table, "单元");
		else if (object instanceof BuildingInfo)
			currHeadRowNum = getHeadRowNumByName(table, "楼栋");
		else if (object instanceof SubareaInfo)
			currHeadRowNum = getHeadRowNumByName(table, "分区");
		else if (object instanceof SellProjectInfo)
			currHeadRowNum = getHeadRowNumByName(table, "项目");
		else if (object instanceof OrgStructureInfo) {
			// 当前组织的headCell名称是　组织呢，还是组织１，组织２...?
			String osName = "组织";
			boolean haveUnit = false;
			if (getHeadRowNumByName(table, "单元") > 0)
				haveUnit = true;
			if (haveUnit) {
				if (node.getDepth() - 4 > 0)
					osName += (node.getDepth() - 4);
			} else {
				if (node.getDepth() - 3 > 0)
					osName += (node.getDepth() - 3);
			}
			currHeadRowNum = getHeadRowNumByName(table, osName);
		}

		if (node.isLeaf()) { // 如果叶节点为楼栋或单元时填充编号列，并返回所包含的列数
			String key = null;
			if (object instanceof BuildingInfo) {
				key = ((BuildingInfo) object).getId().toString() + 0;
			} else if (object instanceof Integer) { // 已替换为单元对象 -- 暂时保留
				int unit = ((Integer) object).intValue();
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node
						.getParent();
				BuildingInfo building = (BuildingInfo) parent.getUserObject();
				key = building.getId().toString() + unit;
			} else if (object instanceof BuildingUnitInfo) {
				int unit = ((BuildingUnitInfo) object).getSeq();
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node
						.getParent();
				BuildingInfo building = (BuildingInfo) parent.getUserObject();
				key = building.getId().toString() + unit;
			} else {
				return 0;
			}
			Integer minNum = (Integer) buildingMinNum.get(key);
			Integer maxNum = (Integer) buildingMaxNum.get(key);
			if (minNum == null || maxNum == null) {
				return 0;
			}

			for (int i = minNum.intValue(); i <= maxNum.intValue(); i++) { // 表头中最大行号必定为
				// '编号'行
				table.getHeadRow(table.getHeadRowCount() - 1).getCell(
						i + colIndex - minNum.intValue()).setValue(i + "号");
			}

			if (currHeadRowNum >= 0 && currHeadRowNum < table.getHeadRowCount()) {
				table.getHeadRow(currHeadRowNum).getCell(colIndex).setValue(
						object);
				table.getHeadMergeManager().mergeBlock(currHeadRowNum,
						colIndex, currHeadRowNum,
						colIndex + maxNum.intValue() - minNum.intValue());

				if (object instanceof BuildingInfo) { // 楼栋下无单元,但head中有单元行的情况;
					// 楼栋上无分区，但head中有分区行的情况
					int unitHeadRow = getHeadRowNumByName(table, "单元");
					if (unitHeadRow >= 0)
						table.getHeadMergeManager().mergeBlock(
								unitHeadRow,
								colIndex,
								unitHeadRow,
								colIndex + maxNum.intValue()
										- minNum.intValue());
					if (!(node.getParent() instanceof SubareaInfo)) {
						int subAreaHeadRow = getHeadRowNumByName(table, "分区");
						if (subAreaHeadRow >= 0)
							table.getHeadMergeManager().mergeBlock(
									subAreaHeadRow,
									colIndex,
									subAreaHeadRow,
									colIndex + maxNum.intValue()
											- minNum.intValue());
					}
				}
			}

			return maxNum.intValue() - minNum.intValue() + 1;
		} else {
			if (currHeadRowNum >= 0) {
				for (int i = 0; i < childRowIndex - colIndex; i++) {
					table.getHeadRow(currHeadRowNum).getCell(colIndex + i)
							.setValue(object);
				}
				table.getHeadMergeManager().mergeBlock(currHeadRowNum,
						colIndex, currHeadRowNum, childRowIndex - 1);
			}
			return childRowIndex - colIndex;
		}

	}

	private int getHeadRowNumByName(KDTable table, String cellName) {
		int headRowNumber = -1;
		String allNameString = "编号,单元,楼栋,分区,项目,组织,组织1,组织2,组织3,组织4,组织5,组织6,";
		if (cellName == null || allNameString.indexOf(cellName) < 0)
			return headRowNumber;

		for (int i = 0; i < table.getHeadRowCount(); i++) {
			IRow headRow = table.getHeadRow(i);
			Object cellObject = headRow.getCell(0).getValue();
			if (cellObject != null && cellObject instanceof String) {
				String cellString = (String) cellObject;
				if (cellString.equals(cellName))
					return i;
			}
		}
		return headRowNumber;
	}
}