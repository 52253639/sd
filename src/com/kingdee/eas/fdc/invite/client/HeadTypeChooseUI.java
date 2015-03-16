/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.HeadColumnCollection;
import com.kingdee.eas.fdc.invite.HeadColumnFactory;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.HeadTypeFactory;
import com.kingdee.eas.fdc.invite.HeadTypeInfo;
import com.kingdee.eas.fdc.invite.IHeadColumn;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class HeadTypeChooseUI extends AbstractHeadTypeChooseUI {
	private static final Logger logger = CoreUIObject
			.getLogger(HeadTypeChooseUI.class);

	private static final Color canntSelectColor = new Color(0xFEFED3);

//	private FilterInfo filter = null;

//	private HeadColumnCollection cac = new HeadColumnCollection();

	private boolean isOk = false; // add by jelon

	private HeadColumnCollection cacheHeadColumnCollection = new HeadColumnCollection();

	private final static String commonSorterRes = "com.kingdee.eas.base.commonquery.client.CommonSorterPanel";

	private static final String resSystem = EASResource.getString(
			"com.kingdee.eas.fdc.invite.FDCInviteResource", "system");
	/**
	 * output class constructor
	 */
	public HeadTypeChooseUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output btnConfirm_actionPerformed method
	 */
	protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		setConfirm(true);
	}

	/**
	 * output btnCancel2_actionPerformed method
	 */
	protected void btnCancel2_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		setConfirm(false);
		disposeUIWindow();
		cacheHeadColumnCollection.clear();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		TreeModel treeModel = FDCClientHelper.createDataTree(HeadTypeFactory
				.getRemoteInstance(), null, "全部类型");
		treeHeadType.setModel(treeModel);
		treeHeadType.setSelectionRow(0);
		
//		// 在选择“表头设置”界面中，如果本表头类型中的供应商自定义清单勾选则，
//		// 在这个界面中的对应的类型名称后增加“（供应商自定义清单）”。
//		DefaultKingdeeTreeNode rootTreeNode = new DefaultKingdeeTreeNode("全部类型");
//		if(rootTreeNode.getChildCount() > 0){
//			for (int i = 0; i < rootTreeNode.getChildCount(); i++) {
//				DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) rootTreeNode.getChildAt(i);
//				if(node.getChildCount() > 0){
//					for(int childCount = 0; childCount < node.getChildCount(); childCount++){
//						
//					}
//				}else{
//					HeadTypeInfo headTypeInfo = (HeadTypeInfo) node.getUserObject();
//					if(headTypeInfo.isIsDefined()){
//						headTypeInfo.setName(headTypeInfo.getName() + "（供应商自定义清单）");
//						rootTreeNode.setUserObject(headTypeInfo);
//					}
//				}
//			}
//		}
		
		
		

		JButton btnSelectAll = contHeadType.add(actionAllSelect);
		JButton btnDelAll = contHeadType.add(actionNoneSelect);
		btnSelectAll.setIcon(EASResource.getIcon("imgTbtn_selectall"));
		btnSelectAll.setToolTipText(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "allSelect"));
		btnSelectAll.setText(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "allSelect"));
		btnSelectAll.setSize(22, 19);
		btnDelAll.setIcon(EASResource.getIcon("imgTbtn_deleteall"));
		btnDelAll.setToolTipText(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "noneSelect"));
		btnDelAll.setText(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "noneSelect"));
		btnDelAll.setSize(22, 19);

		JButton btnMoveUp = contSelected.add(actionMoveUp);
		JButton btnMoveDown = contSelected.add(actionMoveDown);
		btnMoveUp.setIcon(EASResource.getIcon("imgTbtn_movetop"));
		btnMoveUp.setToolTipText(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "moveUp"));
		btnMoveUp.setText(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "moveUp"));
		btnMoveUp.setSize(22, 19);
		btnMoveDown.setIcon(EASResource.getIcon("imgTbtn_movedown"));
		btnMoveDown.setToolTipText(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "moveDown"));
		btnMoveDown.setText(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "moveDown"));
		btnMoveDown.setSize(22, 19);
		tblHeadColumn.setColumnMoveable(true);
		tblSelected.setColumnMoveable(true);
		tblHeadColumn.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		tblSelected.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		tblHeadColumn.checkParsed();// table解析!
		tblSelected.checkParsed();// table解析!
	}
	
	
	

	/**
	 * output actionAllSelect_actionPerformed
	 */
	public void actionAllSelect_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 0; i < tblHeadColumn.getRowCount(); i++) {
			tblHeadColumn.getRow(i).getCell("selected").setValue(
					Boolean.valueOf(true));
		}
	}

	/**
	 * output actionNoneSelect_actionPerformed
	 */
	public void actionNoneSelect_actionPerformed(ActionEvent e)
			throws Exception {
		for (int i = 0; i < tblHeadColumn.getRowCount(); i++) {
			HeadColumnInfo headColumnInfo = (HeadColumnInfo) tblHeadColumn
					.getRow(i).getUserObject();
			if (!(headColumnInfo.getDescription().equals(resSystem)))
				tblHeadColumn.getRow(i).getCell("selected").setValue(
						Boolean.valueOf(false));
		}
	}

	protected void treeHeadType_valueChanged(TreeSelectionEvent e)
			throws Exception {
		treeSelectChange();
		tblSelected.removeRows();
	}

	private void treeSelectChange() throws Exception {
		/*
		 * 表头类型树
		 */
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeHeadType
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		// 缓存已经选择了的成本科目
		cacheHeadColumnCollection.addCollection(getSelectHeadColumns());

		String headTypeId = null;
		if (node.getUserObject() instanceof HeadTypeInfo) {
			HeadTypeInfo headTypeInfo = (HeadTypeInfo) node.getUserObject();
			headTypeId = headTypeInfo.getId().toString();
		} else {
			tblHeadColumn.removeRows();
			return;
		}

		HeadColumnCollection colls = getHeadColumnCollection(headTypeId);
		tblHeadColumn.getColumn("selected").setWidth(50);
		// getTable().getColumn("number").setWidth(130);
		tblHeadColumn.getColumn("name").setWidth(130);
		loadHeadColumnsInTable(tblHeadColumn, colls);

//		 for (int i = 0, count = tblHeadColumn.getRowCount(); cacheHeadColumnCollection
//				.size() > 0
//				&& i < count; i++) {
//			IRow row = tblHeadColumn.getRow(i);
//			HeadColumnInfo cai = (HeadColumnInfo) row.getUserObject();
//			if (cacheHeadColumnCollection.contains(cai)) {
//				// row.getCell("select").setValue(Boolean.TRUE);
//				select(i, false, true);
//			}
//		}
		if (tblHeadColumn.getRowCount() > 0) {
			tblHeadColumn.getSelectManager().select(0, 0);
		}
	}

	private HeadColumnCollection getHeadColumnCollection(String headTypeId)
			throws BOSException {
		if (StringUtils.isEmpty(headTypeId))
			return null;

		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("headType.id", headTypeId));
//		this.filter = filter;

		IHeadColumn iHeadColumn = HeadColumnFactory.getRemoteInstance();
		EntityViewInfo evInfo = new EntityViewInfo();
		SelectorItemCollection selectors = evInfo.getSelector();
		selectors.add("id");
		selectors.add("*");
		selectors.add("headType.*");
		selectors.add("parent.*");
		evInfo.setFilter(filter);

		SorterItemInfo sii = new SorterItemInfo();
		sii.setPropertyName("longNumber");
		sii.setSortType(SortType.ASCEND);
		evInfo.getSorter().add(sii);
		HeadColumnCollection headColumnCollection = iHeadColumn
				.getHeadColumnCollection(evInfo);
		return headColumnCollection;
	}

	private void loadHeadColumnsInTable(KDTable tbl,
			HeadColumnCollection headColumnCollection) {
		tbl.removeRows();
		if (headColumnCollection == null || headColumnCollection.size() <= 0)
			return;

		boolean fTblView = "tblHeadColumn".equals(tbl.getName());
		IRow row;
		for (int i = 0, n = headColumnCollection.size(); i < n; i++) {
			final HeadColumnInfo headColumnInfo = headColumnCollection.get(i);
			row = tbl.addRow();
			loadHeadColRowData(headColumnInfo, row, fTblView);
		}
	}

	private void loadHeadColRowData(HeadColumnInfo headColumnInfo, IRow row,
			boolean fTblView) {
		if (row == null || headColumnInfo == null) {
			logger.error("rowNull: " + (row == null) + ", infoNull:"
					+ (headColumnInfo == null));
			return;
		}

		row.getStyleAttributes().setBackground(Color.white);
		row.getStyleAttributes().setHided(false);

		row.getCell("isQuoting").setValue(
				Boolean.valueOf(headColumnInfo.isIsQuoting()));
		if (fTblView) {
			if (headColumnInfo.getDescription() != null
					&& headColumnInfo.getDescription().equals(resSystem)) {
				row.getCell("selected").setValue(Boolean.TRUE);
				row.getCell("selected").getStyleAttributes().setLocked(true);
			} else {
				row.getCell("selected").setValue(Boolean.FALSE);
				row.getCell("selected").getStyleAttributes().setLocked(false);
			}
		} else {
			if (headColumnInfo.getProperty().equals(DescriptionEnum.Personal)
					|| headColumnInfo.getProperty().equals(
							DescriptionEnum.TotalPrice)) {
				row.getCell("isQuoting").getStyleAttributes().setLocked(false);
			} else {
				row.getCell("isQuoting").getStyleAttributes().setLocked(true);
			}
			if (headColumnInfo.get("quotingLock") != null) {
				row.getCell("isQuoting").getStyleAttributes().setLocked(
						headColumnInfo.getBoolean("quotingLock"));
				headColumnInfo.remove("quotingLock");
			}
			if (headColumnInfo.get("cellquoting") != null) {
				row.getCell("isQuoting").setValue(
						headColumnInfo.get("cellquoting"));
				headColumnInfo.remove("cellquoting");
			}
		}
		row.getCell("description")
				.setValue(headColumnInfo.getDescription());
		//row.getCell("id").setValue(headColumnInfo.getId().toString());
		row.setUserObject(headColumnInfo);

		final int level = headColumnInfo.getLevel();
		final boolean isLeaf = headColumnInfo.isIsLeaf();
		if (headColumnInfo.getName().equals("工程量")
				|| headColumnInfo.getName().equals("金额")) {
			row.getStyleAttributes().setHided(false);
		} else {
			if (level != 0) {
				row.getStyleAttributes().setHided(true);
			}
			if (!isLeaf) {
				row.getStyleAttributes().setBackground(canntSelectColor);
			}
		}
		row.getCell("name").setValue(headColumnInfo.getName());
		row.getCell("name").setValue(createNameNode(headColumnInfo));
	}

	private CellTreeNode createNameNode(HeadColumnInfo headColumnInfo)
	{
		CellTreeNode node = new CellTreeNode();
		node.setValue(headColumnInfo.getName());
		int level = headColumnInfo.getLevel();
		node.setTreeLevel(level);
		if (headColumnInfo.isIsLeaf() || headColumnInfo.getName().equals("工程量")
				|| headColumnInfo.getName().equals("金额")) {
			node.setHasChildren(false);
		} else {
			node.setHasChildren(true);
			node.setCollapse(true);// 是否只隐藏根结点
//			if (headColumnInfo.isIsLeaf()) {
//				node.setHasChildren(false);
//			} else {
//				node.setHasChildren(true);
//				node.setCollapse(true);// 是否只隐藏根结点
////				if (level == 0) {
////					node.addClickListener(new NodeClickListener() {
////						public void doClick(CellTreeNode source, ICell cell,
////								int type) {
//////							tblHeadColumn.revalidate();
////						}
////					});
////				}
//			}
		}

		return node;
	}

	public boolean destroyWindow() {
		setConfirm(false);
		return super.destroyWindow();
	}

	public boolean isOk() {
		return isOk;
	}

	public void setConfirm(boolean isOk) {
		this.isOk = isOk;
		disposeUIWindow();
	}

	public void select(int row, boolean old, boolean now) {
		if (old == now)
			return;
		tblHeadColumn.getCell(row, "selected").setValue(Boolean.valueOf(now));
		HeadColumnInfo acctSelect = (HeadColumnInfo) tblHeadColumn.getRow(row)
				.getUserObject();
		HeadColumnInfo acct = null;
		int level = acctSelect.getLevel();
		// 下级
		for (int i = row + 1; i < tblHeadColumn.getRowCount(); i++) {
			acct = (HeadColumnInfo) tblHeadColumn.getRow(i).getUserObject();
			if (acct.getLevel() > level) {
				tblHeadColumn.getCell(i, "selected")
						.setValue(Boolean.valueOf(now));

			} else {
				break;
			}
		}

		// 上级
		int parentLevel = level - 1;
		if (now) {
			for (int i = row - 1; i >= 0; i--) {
				if (parentLevel == 0) {
					break;
				}
				acct = (HeadColumnInfo) tblHeadColumn.getRow(i).getUserObject();
				if (acct.getLevel() == parentLevel) {
					ICell cell = tblHeadColumn.getCell(i, "selected");
					if (cell.getValue() != Boolean.TRUE) {
						cell.setValue(Boolean.TRUE);
						parentLevel--;
					} else {
						break;
					}

				}
			}
		} else {

			// 不选择,检查同级是否有选择的
			boolean hasSelected = false;
			// 下面的行
			for (int i = row + 1; i < tblHeadColumn.getRowCount(); i++) {
				acct = (HeadColumnInfo) tblHeadColumn.getRow(i).getUserObject();
				if (acct.getLevel() == level) {
					ICell cell = tblHeadColumn.getCell(i, "selected");
					if (cell.getValue() == Boolean.TRUE) {
						hasSelected = true;
						break;
					} else if (acct.getLevel() < level) {
						break;
					}
				}
			}
			// 上面的行

			if (!hasSelected) {
				for (int i = row - 1; i >= 0; i--) {
					acct = (HeadColumnInfo) tblHeadColumn.getRow(i)
							.getUserObject();
					if (acct.getLevel() == level) {
						ICell cell = tblHeadColumn.getCell(i, "selected");
						if (cell.getValue() == Boolean.TRUE) {
							hasSelected = true;
							break;
						}
					} else if (acct.getLevel() < level) {
						row = i;
						break;
					}
				}
			}

			if (!hasSelected) {

				// 设置父级
				parentLevel = level - 1;
				for (int j = row; j >= 0; j--) {
					if (parentLevel == 0) {
						break;
					}
					acct = (HeadColumnInfo) tblHeadColumn.getRow(j)
							.getUserObject();
					if (acct.getLevel() == parentLevel) {
						ICell cell = tblHeadColumn.getCell(j, "selected");
						cell.setValue(Boolean.FALSE);
						parentLevel--;
					}
				}

			}

		}

	}

	public HeadColumnCollection getSelectHeadColumns() {
		HeadColumnCollection c = new HeadColumnCollection();
		for (int i = 0, count = tblHeadColumn.getRowCount(); i < count; i++) {
			IRow row = tblHeadColumn.getRow(i);
			if (((Boolean) row.getCell("selected").getValue()).booleanValue()) {
				HeadColumnInfo info = (HeadColumnInfo) row.getUserObject();
				c.add(info);
			}
		}
		return c;
	}

	protected void tblHeadColumn_editValueChanged(KDTEditEvent e)
			throws Exception {
		super.tblHeadColumn_editValueChanged(e);
		if (e.getColIndex() == tblHeadColumn.getColumnIndex("selected")) {
			Boolean old = (Boolean) e.getOldValue();
			Boolean now = (Boolean) e.getValue();
			select(e.getRowIndex(), old.booleanValue(), now.booleanValue());
		}
	}

	public HeadColumnCollection getSortHeadColumns() {
		HeadColumnCollection c = new HeadColumnCollection();
		for (int i = 0, count = tblSelected.getRowCount(); i < count; i++) {
			IRow row = tblSelected.getRow(i);
			// if (((Boolean)
			// row.getCell("selected").getValue()).booleanValue()) {
			HeadColumnInfo cai = (HeadColumnInfo) row.getUserObject();
			if (((Boolean) row.getCell("isQuoting").getValue()).booleanValue()) {
				cai.setIsQuoting(true);
			} else
				cai.setIsQuoting(false);
			// 只返回明细表头
			if (cai.getProperty().equals(DescriptionEnum.ProjectAmtSum)
					|| cai.getProperty().equals(DescriptionEnum.AmountSum)) {
				c.add(cai);
			} else {
				if (cai.isIsLeaf()) {
					c.add(cai);
				}
			}
		}
		return c;
	}

	/**
	 * 删除已选择的表头列，其中系统预置的列不能删除
	 */
	protected void btnLeft_actionPerformed(ActionEvent e) throws Exception {
		int rowCount = tblSelected.getRowCount();
		if (rowCount == 0 || tblSelected.getSelectManager().size() == 0)
			return;

		int[] selectRows = KDTableUtil.getSelectedRows(tblSelected);
		int[] arrRemoveInd = new int[rowCount];
		for (int i = 0; i < rowCount; i++) {
			arrRemoveInd[i] = -1;
		}

		HeadColumnInfo colInfo = null;
		for (int i = 0; i < selectRows.length; i++) {
			colInfo = getSelectedColInfo(selectRows[i]);
			if (colInfo.getDescription() != null
					&& colInfo.getDescription().equals(resSystem))
				continue;

			arrRemoveInd[selectRows[i]] = selectRows[i];

			CellTreeNode node = getNameNode(selectRows[i]);
			if (node == null)
				continue;
			if (node.isHasChildren()) {
				for (int k = selectRows[i] + 1; k < rowCount; k++) {
					CellTreeNode subNode = getNameNode(k);
					if (subNode != null && subNode.getTreeLevel() == 0)
						break;

					colInfo = getSelectedColInfo(k);
					if (colInfo.getDescription() != null
							&& colInfo.getDescription().equals(resSystem))
						continue;
					arrRemoveInd[k] = k;
				}
			}
		}

		for (int i = arrRemoveInd.length - 1; i >= 0; i--) {
			if (arrRemoveInd[i] >= 0)
				tblSelected.removeRow(arrRemoveInd[i]);
		}
		tblSelected.reLayoutAndPaint();
	}

	private CellTreeNode getNameNode(int rowInd) {
		IRow row = tblSelected.getRow(rowInd);
		if (row == null)
			return null;

		CellTreeNode node = (CellTreeNode) row.getCell("name").getValue();
		return node;
	}

	/**
	 * output actionMoveUp_actionPerformed
	 */
	public void actionMoveUp_actionPerformed(ActionEvent e) throws Exception {
		int selectedRowIndex = this.tblSelected.getSelectManager()
				.getActiveRowIndex();

		if (selectedRowIndex == -1) {
			MsgBox.showWarning(EASResource.getString(commonSorterRes,
					"moveWarning"));
		} else if (selectedRowIndex == 0) {
			return;
		} else {
// 			moveUpRow();
			moveRow(true);
		}
	}

	/**
	 * output actionMoveDown_actionPerformed
	 */
	public void actionMoveDown_actionPerformed(ActionEvent e) throws Exception {
		int selectedRowIndex = this.tblSelected.getSelectManager()
				.getActiveRowIndex();

		if (selectedRowIndex == -1) {
			MsgBox.showWarning(EASResource.getString(commonSorterRes,
					"moveWarning"));
		} else if (selectedRowIndex == this.tblSelected.getRowCount() - 1) {
			return;
		} else {
//			moveDownRow();
			moveRow(false);
		}
	}

	protected void moveRow(boolean fUpDown) throws Exception {
		HeadColumnCollection currentColl = null;
		HeadColumnCollection modifyColl = null;
		int selectedRowIndex = this.tblSelected.getSelectManager()
				.getActiveRowIndex();
		int selectedColIndex = this.tblSelected.getSelectManager()
				.getActiveColumnIndex();
		if (selectedRowIndex < 0 || selectedColIndex < 0)
			return ;

		logger.debug("move," + (fUpDown ? "Up" : "Down") + " selectedRow:"
				+ selectedRowIndex);
		int rowDir = fUpDown ? -1 : 1;
		int selectedRowNewIndex = 0;
		int neighborRowNewIndex = 0;
		HeadColumnInfo currentRowData = getSelectedColInfo(selectedRowIndex);
		HeadColumnInfo modifyRowData = getSelectedColInfo(selectedRowIndex
				+ rowDir);
		if (isLeafCol(currentRowData)) {
			if ((fUpDown && modifyRowData.getLevel() == 0)
					|| (!fUpDown && isLeafCol(modifyRowData))) {
				selectedRowNewIndex = selectedRowIndex + rowDir;
				neighborRowNewIndex = selectedRowIndex;
				loadHeadColRowData(modifyRowData, tblSelected
						.getRow(neighborRowNewIndex), false);
				loadHeadColRowData(currentRowData, tblSelected
						.getRow(selectedRowNewIndex), false);
			} else {
				modifyColl = fUpDown ? getUpNeighborBlock(selectedRowIndex)
						: getDownNeighborBlock(selectedRowIndex);
				selectedRowNewIndex = selectedRowIndex + rowDir
						* modifyColl.size();
				neighborRowNewIndex = fUpDown ? selectedRowIndex
						- modifyColl.size() + 1 : selectedRowIndex;
				loadSelect(neighborRowNewIndex, modifyColl);
				loadHeadColRowData(currentRowData, tblSelected
						.getRow(selectedRowNewIndex), false);
			}
		} else {
			currentColl = getSelectBlock(selectedRowIndex);
			if (!fUpDown
					&& (selectedRowIndex + currentColl.size()) >= tblSelected
							.getRowCount()) {
				logger.warn("the block is the last area");
				return;
			}

			modifyRowData = fUpDown ? modifyRowData
					: getSelectedColInfo(selectedRowIndex + currentColl.size());
			if ((fUpDown && modifyRowData.getLevel() == 0)
					|| (!fUpDown && isLeafCol(modifyRowData))) {
				selectedRowNewIndex = selectedRowIndex + rowDir;
				neighborRowNewIndex = fUpDown ? selectedRowIndex
						+ currentColl.size() - 1 : selectedRowIndex;
				loadSelect(selectedRowNewIndex, currentColl);
				loadHeadColRowData(modifyRowData, tblSelected
						.getRow(neighborRowNewIndex), false);
			} else {
				modifyColl = fUpDown ? getUpNeighborBlock(selectedRowIndex)
						: getDownNeighborBlock(selectedRowIndex);
				selectedRowNewIndex = selectedRowIndex + rowDir
						* (modifyColl.size() + 1);
				neighborRowNewIndex = fUpDown ? selectedRowIndex
						+ currentColl.size() + modifyColl.size()
						: selectedRowIndex;
				loadSelect(neighborRowNewIndex, modifyColl);
				loadSelect(selectedRowNewIndex, currentColl);
			}
		}
		logger.debug(", selectNewRow:" + selectedRowNewIndex
				+ ", neighborRowNew:" + neighborRowNewIndex);

		tblSelected.getSelectManager().select(selectedRowNewIndex,
				selectedColIndex, KDTSelectManager.ROW_SELECT);
	}

	private boolean isLeafCol(HeadColumnInfo info) {
		return info.isIsLeaf() || info.getName().equals("工程量")
				|| info.getName().equals("金额");
	}

	private HeadColumnCollection getDownNeighborBlock(int selectedRowIndex) {
		HeadColumnInfo modifyRowData = getSelectedColInfo(selectedRowIndex + 1);
		HeadColumnCollection modifyColl = new HeadColumnCollection();
		modifyColl.add(modifyRowData);
		for (int i = selectedRowIndex + 2, n = tblSelected.getRowCount(); i < n; i++) {
			HeadColumnInfo modifyData = (HeadColumnInfo) tblSelected.getRow(i)
					.getUserObject();
			if (modifyData.getLevel() == 0) {
				break;
			}
			modifyColl.add(modifyData);
		}
		return modifyColl;
	}

	private HeadColumnCollection getUpNeighborBlock(int selectedRowIndex) {
		HeadColumnInfo modifyRowData = getSelectedColInfo(selectedRowIndex - 1);
		HeadColumnCollection modifyColl = new HeadColumnCollection();
		HeadColumnCollection tempColl = new HeadColumnCollection();
		tempColl.add(modifyRowData);
		for (int i = selectedRowIndex - 2; i >= 0; i--) {
			HeadColumnInfo modifyData = getSelectedColInfo(i);
			tempColl.add(modifyData);
			if (modifyData.getLevel() == 0) {
				tblSelected.getRow(i).setCollapse(false);
				break;
			}
		}
		for (int i = tempColl.size() - 1; i >= 0; i--) {
			modifyColl.add(tempColl.get(i));
		}
		return modifyColl;
	}

	//
	private HeadColumnCollection getSelectBlock(int selectedRowIndex) {
		HeadColumnInfo currentRowData = getSelectedColInfo(selectedRowIndex);
		HeadColumnCollection currentColl = new HeadColumnCollection();
		currentColl.add(currentRowData);
		int rowCount = tblSelected.getRowCount();
		for (int i = selectedRowIndex + 1; i < rowCount; i++) {
			HeadColumnInfo modifyData = getSelectedColInfo(i);
			if (modifyData.getLevel() == 0) {
				break;
			}
			currentColl.add(modifyData);
		}
		return currentColl;
	}

	private HeadColumnInfo getSelectedColInfo(int rowIndex) {
		IRow row = tblSelected.getRow(rowIndex);
		if (row == null) {
			logger.error("rowIndex invalid:" + rowIndex);
			return null;
		}

		HeadColumnInfo info = (HeadColumnInfo) row
				.getUserObject();
		Object oQuote = row.getCell("isQuoting")
				.getValue();
		boolean fLock = row.getCell("isQuoting")
				.getStyleAttributes().isLocked();

		info.put("cellquoting", (Boolean)oQuote);
		info.put("quotingLock", Boolean.valueOf(fLock));
		return info;
	}

	protected void btnRight_actionPerformed(ActionEvent e) throws Exception {
		HeadColumnCollection selected = getSelectHeadColumns();
		loadHeadColumnsInTable(tblSelected, selected);
	}

	private void loadSelect(int place, HeadColumnCollection selected)
			throws Exception {
		if (selected == null || selected.size() == 0)
			return;

		int count = selected.size();
		IRow row;
		for (int i = 0; i < count; i++) {
			final HeadColumnInfo headColumnInfo = selected.get(i);
			row = tblSelected.getRow(place + i);
			loadHeadColRowData(headColumnInfo, row, false);
		}
	}

	protected void tblSelected_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		int selectedRowIndex = this.tblSelected.getSelectManager()
				.getActiveRowIndex();
		if (selectedRowIndex < 0) {
			return;
		} else {
			HeadColumnInfo currentRowData = (HeadColumnInfo) tblSelected
					.getRow(selectedRowIndex).getUserObject();
			if (currentRowData.getLevel() == 0) {
				actionMoveDown.setEnabled(true);
				actionMoveUp.setEnabled(true);
			} else {
				actionMoveDown.setEnabled(false);
				actionMoveUp.setEnabled(false);
			}
		}
	}

}