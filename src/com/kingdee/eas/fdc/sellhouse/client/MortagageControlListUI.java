/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.MortagageControlFactory;
import com.kingdee.eas.fdc.sellhouse.MortagageControlInfo;
import com.kingdee.eas.fdc.sellhouse.MortagageEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * @auther qinyouzhen,售楼优化 V 7.0.3版
 * @date 2011-06-15 抵押控制
 */
public class MortagageControlListUI extends AbstractMortagageControlListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(MortagageControlListUI.class);

	private Map map = FDCSysContext.getInstance().getOrgMap();// 用于判断是否售楼组织

	/**
	 * output class constructor
	 */
	public MortagageControlListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		initButtonState();
		tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);// 多选
		treeMain.setSelectionRow(0); // 默认选第一行
	}

	/**
	 * 初始化,构建树
	 * 
	 * @throws Exception
	 */
	protected void initTree() throws Exception {
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(
				this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.CELL_SELECT);
	}

	/**
	 * 初始化Button
	 */
	private void initButtonState() {
		actionAddNew.setEnabled(false);
		actionCreateTo.setVisible(false);
		actionTraceUp.setVisible(false);
		actionTraceDown.setVisible(false);
		actionLocate.setVisible(false);
		actionAttachment.setVisible(false);
		actionCopyTo.setVisible(false);
		actionAudit.setEnabled(true);
		actionUnAudit.setEnabled(true);

		// 非售楼组织按钮不可用
		if (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString())) {
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
			btnAntiMortagage.setEnabled(false);
			btnAudit.setEnabled(false);
			btnUnAudit.setEnabled(false);
		}
		menuWorkFlow.setVisible(false);
	}

	/**
	 * 选取树节点时查询
	 */
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			if (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit()
					.getId().toString())) {
				FDCClientHelper.setActionEnableAndNotSetVisible(
						new ItemAction[] { actionAddNew, actionEdit,
								actionRemove }, true);
				TreePath path = this.treeMain.getSelectionPath();
				if (path == null) {
					return;
				}
				DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path
						.getLastPathComponent();
				if (treenode.getUserObject() != null
						&& treenode.getUserObject() instanceof SellProjectInfo) {
					SellProjectInfo sellProjectInfo = (SellProjectInfo) treenode
							.getUserObject();
					if (sellProjectInfo != null) {
						getUIContext().put("sellProject", sellProjectInfo);
					}
				}
			} else {
				FDCClientHelper.setActionEnableAndNotSetVisible(
						new ItemAction[] { actionAddNew, actionEdit,
								actionRemove }, false);
			}
		}
		this.execQuery();
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
					.getLastSelectedPathComponent();
			if (node == null) {
				return null;
			}
			FilterInfo filter = new FilterInfo();
			String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper
					.getAllObjectIdMap(node, "SellProject").keySet());

			if (allSpIdStr.trim().length() == 0) {
				allSpIdStr = "'nullnull'";
			}
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", allSpIdStr,
							CompareType.INNER));
			if (node.getUserObject() instanceof SellProjectInfo) {
				if (map.containsKey(SysContext.getSysContext()
						.getCurrentOrgUnit().getId().toString())) {
					this.actionAddNew.setEnabled(true);
				}
			}
			viewInfo = (EntityViewInfo) mainQuery.clone();
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "AND");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			this.handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			Object object = node.getUserObject();
			if (object != null && object instanceof SellProjectInfo) {
				uiContext.put("sellProjectInfo", object);
			}
		} else {
			MsgBox.showInfo("请选择节点!");
			SysUtil.abort();
		}
		super.prepareUIContext(uiContext, e);
	}

	/**
	 * 新增
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * 解除抵押
	 */
	public void actionAntiMortagage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAntiMortagage_actionPerformed(e);
		checkSelected();
		MortagageControlInfo info = getSelectInfo();
		boolean antiMortagageResult = MortagageControlFactory
				.getRemoteInstance().antiMortagage(info);
		if (antiMortagageResult) {
			MsgBox.showInfo("解除抵押成功");
			this.refresh(null);
		} else {
			MsgBox.showInfo("单据状态不符合要求");
		}
	}

	/**
	 * 审批
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		MortagageControlInfo info = getSelectInfo();
		boolean auditResult = MortagageControlFactory.getRemoteInstance()
				.audit(info);
		if (auditResult) {
			MsgBox.showInfo("单据审批成功");
			refreshList();
		} else {
			MsgBox.showInfo("单据状态不符合要求");
		}
	}

	/**
	 * 反审批
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		MortagageControlInfo info = getSelectInfo();
		boolean unAuditResult = MortagageControlFactory.getRemoteInstance()
				.unAudit(info);
		if (unAuditResult) {
			MsgBox.showInfo("单据反审批成功");
			refreshList();
		} else {
			MsgBox.showInfo("单据状态不符合要求");
		}
	}

	/**
	 * 监听每一笔记录是否可用,从而设置按钮是否可用
	 */
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		MortagageControlInfo info = getSelectInfo();
		if (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString())) {
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
			btnAntiMortagage.setEnabled(false);
			btnAudit.setEnabled(false);
			btnUnAudit.setEnabled(false);
		} else {
			if (info.getMortagageState().equals(MortagageEnum.SUBMITTED)) {// 已提交
				this.btnAntiMortagage.setEnabled(true);
				this.btnAudit.setEnabled(true);
				this.btnUnAudit.setEnabled(false);
			} else if (info.getMortagageState().equals(MortagageEnum.AUDITTING)) {// 审批中
				this.btnAntiMortagage.setEnabled(false);
				this.btnAudit.setEnabled(true);
				this.btnUnAudit.setEnabled(true);
			} else if (info.getMortagageState().equals(MortagageEnum.AUDITTED)) {// 已审批
				this.btnAntiMortagage.setEnabled(true);
				this.btnAudit.setEnabled(false);
				this.btnUnAudit.setEnabled(true);
			} else if (info.getMortagageState().equals(
					MortagageEnum.ANTIMORTAGAGE)) {// 解除抵押
				this.btnAntiMortagage.setEnabled(false);
				this.btnAudit.setEnabled(false);
				this.btnUnAudit.setEnabled(false);
			} else {// 保存
				this.btnAntiMortagage.setEnabled(false);
				this.btnAudit.setEnabled(false);
				this.btnUnAudit.setEnabled(false);
			}
		}
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionView_actionPerformed(e);
		this.refresh(null);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		VerifyAuditOrUnAuditById("修改");
		super.actionEdit_actionPerformed(e);
		this.refresh(null);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		VerifyAuditOrUnAuditById("删除");
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * 判断记录是否审批或解除抵押,修改和删除时调用
	 * 
	 * @param words
	 * @param selectID
	 * @return
	 * @throws Exception
	 */
	public void VerifyAuditOrUnAuditById(String words) throws Exception {
		String id = "";
		int activeRowIndex = this.tblMain.getSelectManager()
				.getActiveRowIndex();
		IRow row = tblMain.getRow(activeRowIndex);
		if (row == null) {
			return;
		}
		id = row.getCell("id").getValue().toString();
		if (id != null && !id.equals("")) {
			MortagageControlInfo info = MortagageControlFactory
					.getRemoteInstance().getMortagageControlInfo(
							"select id,mortagageState where id='" + id + "'");
			if (info.getMortagageState().equals(MortagageEnum.AUDITTED)) {
				MsgBox.showWarning("本单据已经审批，不能" + words + "!");
				this.abort();
			} else if (info.getMortagageState().equals(MortagageEnum.AUDITTING)) {
				MsgBox.showWarning("本单据在审批中，不能" + words + "!");
				this.abort();
			} else if (info.getMortagageState().equals(
					MortagageEnum.ANTIMORTAGAGE)) {
				MsgBox.showWarning("本单据已经解除抵押，不能" + words + "!");
				this.abort();
			}
		}
	}

	// 返回选中的行
	private MortagageControlInfo getSelectInfo() throws EASBizException,
			BOSException {
		MortagageControlInfo info = MortagageControlFactory.getRemoteInstance()
				.getMortagageControlInfo(new ObjectUuidPK(getSelsectRowId()));
		return info;
	}

	// 获取选 中的行ID
	public String getSelsectRowId() {
		IRow selectRow = KDTableUtil.getSelectedRow(tblMain);
		return selectRow.getCell("id").getValue().toString();
	}

	// 先注释掉，用于合并房间的名称，之前是在编辑界面能够选择多个房间，现在的需求是只选一个房间
	// /**
	// * 查询,合并多个房间Room
	// */
	// protected void execQuery() {
	// super.execQuery();
	// Set ids = new HashSet();
	// for (int i = 0; i < tblMain.getRowCount(); i++) {
	// String id = (String) tblMain.getRow(i).getCell("id").getValue();
	// ids.add(id);
	// }
	// if (ids != null && ids.size() > 0) {
	// Map roomStr = getRoomString(ids);
	// for (int i = 0; i < tblMain.getRowCount(); i++) {
	// IRow row = tblMain.getRow(i);
	// String id = (String) row.getCell("id").getValue();
	// if (roomStr.containsKey(id)) {
	// row.getCell("roomName").setValue(roomStr.get(id));
	// }
	// }
	// }
	// }
	//
	// /**
	// * 根据ids 查询它的Room,放到Map中
	// *
	// * @param ids
	// * @return
	// */
	// private Map getRoomString(Set ids) {
	// Map roomStr = new HashMap();
	// FDCSQLBuilder builder = new FDCSQLBuilder();
	// try {
	// builder
	// .appendSql(
	// " select room.fName_l2 as name,mortagage.fId as id from T_SHE_Room as room "
	// +
	// "inner join T_SHE_MortagageControlEntry as mortagageEntry on mortagageEntry.fRoomId = room.fid "
	// +
	// "inner join T_SHE_MortagageControl as mortagage on mortagage.fid = mortagageEntry.fHeadId "
	// + "and mortagage.fid in ");
	// builder.appendSql(FMHelper.setTran2String(ids));
	// IRowSet rs = builder.executeQuery();
	// while (rs.next()) {
	// String id = rs.getString("id");
	// String roomName = rs.getString("name");
	// if (roomStr.containsKey(id)) {
	// String oldRoomName = (String) roomStr.get(id);
	// roomStr.put(id, oldRoomName + "," + roomName);
	// } else {
	// roomStr.put(id, roomName);
	// }
	// }
	// } catch (BOSException e) {
	// e.printStackTrace();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// return roomStr;
	// }

	protected ICoreBase getBizInterface() throws Exception {
		return MortagageControlFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		return new MortagageControlInfo();
	}

	protected String getEditUIName() {
		return MortagageControlEditUI.class.getName();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}
}