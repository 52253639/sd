/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.invite.ColumnTypeEnum;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.HeadColumnCollection;
import com.kingdee.eas.fdc.invite.HeadColumnFactory;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class NewListColumnSettingUI extends AbstractNewListColumnSettingUI {
	private static final Logger logger = CoreUIObject
			.getLogger(NewListColumnSettingUI.class);

	private boolean isOk = false;

	/**
	 * output class constructor
	 */
	public NewListColumnSettingUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output txtName_keyReleased method
	 */
	protected void txtName_keyReleased(java.awt.event.KeyEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeColumn
				.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof HeadColumnInfo) {
			HeadColumnInfo col = (HeadColumnInfo) node.getUserObject();
			if (col == null) {
				return;
			}
			this.treeColumn.setNodeText(node, this.txtName.getText());
			col.setName(this.txtName.getText());
		}
	}

	/**
	 * output btnAddLeaf_actionPerformed method
	 */
	protected void btnAddLeaf_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeColumn
				.getLastSelectedPathComponent();
		if (node == null) {
			MsgBox.showWarning(this, "请先选中节点！");
			return;
		}
		HeadColumnCollection columnColl = (HeadColumnCollection) getUIContext()
				.get("columnColletion");
		if (columnColl.size() == 1 && node.isRoot()
				&& node.getChildCount() == 0) {
			DefaultKingdeeTreeNode addSumNode = new DefaultKingdeeTreeNode("");
			HeadColumnInfo columnSum = new HeadColumnInfo();
			columnSum.setId(BOSUuid.create(columnSum.getBOSType()));
			columnSum.setName("小计");
			columnSum.setIsQuoting(false);
			columnSum.setColumnType(ColumnTypeEnum.Amount);
			columnSum.setIsRefPrice(false);
			columnSum.setProperty(DescriptionEnum.ProjectAmtSum);
//			columnSum.setDescription(EASResource.getString(
//					"com.kingdee.eas.fdc.invite.FDCInviteResource", "system"));
			addSumNode.setUserObject(columnSum);
			this.treeColumn.addNodeInto(addSumNode, node);
		}
		DefaultKingdeeTreeNode addNode = new DefaultKingdeeTreeNode("");
		HeadColumnInfo column = new HeadColumnInfo();
		column.setName("");
		column.setId(BOSUuid.create(column.getBOSType()));
		column.setIsQuoting(false);
		column.setDescription(EASResource.getString(
				"com.kingdee.eas.fdc.invite.FDCInviteResource", "personal"));
		column.setColumnType(ColumnTypeEnum.Amount);
		column.setProperty(DescriptionEnum.ProjectAmt);
		column.setIsRefPrice(false);
		addNode.setUserObject(column);
		this.treeColumn.insertNodeInto(addNode, node, node.getChildCount() - 1);
		this.treeColumn.setSelectionNode(addNode);
		this.txtName.requestFocus();

	}

	/**
	 * output btnRemoveNode_actionPerformed method
	 */
	protected void btnRemoveNode_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeColumn
				.getLastSelectedPathComponent();
		if (node == null) {
			MsgBox.showWarning(this, "请先选中节点！");
			return;
		}
		DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode) node
				.getParent();
		this.treeColumn.removeNodeFromParent(node);
		if(parent.getChildCount()==1){
			this.treeColumn.removeAllChildrenFromParent(parent);
		}
		this.treeColumn.setSelectionNode(parent);
	}

	/**
	 * output btnConfirm_actionPerformed method
	 */
	protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		TreeModel model = this.treeColumn.getModel();
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) model.getRoot();
		Map nameMap=new HashMap();
		for (int i = 0; i < root.getChildCount(); i++) {
			DefaultKingdeeTreeNode child=(DefaultKingdeeTreeNode) root.getChildAt(i);
			HeadColumnInfo col=(HeadColumnInfo) child.getUserObject();
			if(nameMap.containsKey(col.getName())){
				MsgBox.showInfo("有重复列名!");
				return;
			}else{
				nameMap.put(col.getName(),col);
			}
		}
		setConfirm(true);
	}

	/**
	 * output btnCancel_actionPerformed method
	 */
	protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		setConfirm(false);
		disposeUIWindow();
	}

	public void setConfirm(boolean isOk) {
		this.isOk = isOk;
		disposeUIWindow();
	}

	/**
	 * output treeColumn_valueChanged method
	 */
	protected void treeColumn_valueChanged(
			javax.swing.event.TreeSelectionEvent e) throws Exception {
		super.treeColumn_valueChanged(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeColumn
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof HeadColumnInfo) {
			HeadColumnInfo col = (HeadColumnInfo) node.getUserObject();
			if (col == null) {
				this.txtName.setText(null);
				return;
			}
			this.txtName.setText(col.getName());
			this.txtName.setEnabled(true);
			if (col.isIsHasChild()) {
				btnAddLeaf.setEnabled(true);
				btnRemoveNode.setEnabled(true);
			} else {
				btnAddLeaf.setEnabled(false);
				btnRemoveNode.setEnabled(false);
			}
			if (col.getName().equals("小计")) {
				txtName.setEnabled(false);
				btnRemoveNode.setEnabled(false);
			} else {
				txtName.setEnabled(true);
				btnRemoveNode.setEnabled(true);
			}
		} else {
			this.txtName.setText(null);
			this.txtName.setEnabled(false);
			btnAddLeaf.setEnabled(true);
			btnRemoveNode.setEnabled(false);
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		HeadColumnCollection columnColl = (HeadColumnCollection) getUIContext()
				.get("columnColletion");
		HeadColumnCollection amountColl = (HeadColumnCollection) getUIContext()
		.get("amountColletion");
		if (amountColl.size() == 1) {			
			amountInfo = amountColl.get(0);
		} else {
			amountInfo = amountColl.get(0).getParent();
		}
		TreeModel model = getColumnTreeByColl(columnColl);
		treeColumn.setModel(model);
		this.txtName.setEnabled(false);
	}

	HeadColumnInfo parentInfo = new HeadColumnInfo();
	HeadColumnInfo amountInfo = new HeadColumnInfo();

	protected TreeModel getColumnTreeByColl(HeadColumnCollection columnColl) {
		DefaultKingdeeTreeNode rootTreeNode = null;
		if (columnColl.size() == 1) {
			rootTreeNode = new DefaultKingdeeTreeNode(columnColl.get(0)
					.getName());
			parentInfo = columnColl.get(0);
		} else {
			rootTreeNode = new DefaultKingdeeTreeNode("工程量");
			parentInfo = columnColl.get(0).getParent();
		}
		HashMap nodeMap = new HashMap();
		if (columnColl.size() > 1) {
			for (int i = 0; i < columnColl.size(); i++) {
				HeadColumnInfo column = (HeadColumnInfo) columnColl
						.getObject(i);
				DefaultKingdeeTreeNode subTreeNode = new DefaultKingdeeTreeNode(
						column);
				subTreeNode.setUserObject(column);
				HeadColumnInfo parentColumn = (HeadColumnInfo) column
						.getParent();
				// if (parentColumn == null) {
				this.addHeadSeqNode(rootTreeNode, subTreeNode);
				// } else {
				// if (nodeMap.containsKey(parentColumn.getId().toString())) {
				// DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)
				// nodeMap
				// .get(parentColumn.getId().toString());
				// this.addHeadSeqNode(node, subTreeNode);
				// }
				// }
				nodeMap.put(column.getId().toString(), subTreeNode);
			}
		}
		TreeModel treeModel = new DefaultTreeModel(rootTreeNode);
		return treeModel;
	}

	public void addHeadSeqNode(DefaultKingdeeTreeNode parent,
			DefaultKingdeeTreeNode child) {
		for (int i = 0; i < parent.getChildCount(); i++) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) parent
					.getChildAt(i);
			HeadColumnInfo column1 = (HeadColumnInfo) node.getUserObject();
			HeadColumnInfo column2 = (HeadColumnInfo) child.getUserObject();
			if (column1.getSeq() > column2.getSeq()) {
				int index = parent.getIndex(node);
				parent.insert(child, index);
				return;
			}
		}
		parent.add(child);
	}

	public HeadColumnCollection getHeadColumnCollection(boolean isSubmit) {
		HeadColumnCollection coll = new HeadColumnCollection();
		addHeadCollByNode(coll, (DefaultKingdeeTreeNode) treeColumn.getModel()
				.getRoot());
		return coll;
	}
	
	public HeadColumnCollection getAmountHeadColumnCollection() {
		HeadColumnCollection collAmount = new HeadColumnCollection();
		HeadColumnCollection coll = getHeadColumnCollection(false);	
		for (int i = 0, size = coll.size(); i < size; i++) {
			HeadColumnInfo temp = new HeadColumnInfo();
			temp.setId(BOSUuid.create(temp.getBOSType()));
			temp.setIsQuoting(false);
			temp.setIsRefPrice(false);
			temp.setIsLeaf(true);
			temp.setIsHasChild(false);
			temp.setId(BOSUuid.create(temp.getBOSType()));
			temp.setName(coll.get(i).getName());
			if(coll.get(i).getName().equals("小计"))
				temp.setProperty(DescriptionEnum.AmountSum);
			else
				temp.setProperty(DescriptionEnum.Amount);
			temp.setParent(amountInfo);
			temp.setDescription("submit");
			temp.setNumber(Integer.toString(i));
			temp.setLongNumber(amountInfo.getLongNumber() + "!"
					+ coll.get(i).getSeq());
			temp.setColumnType(ColumnTypeEnum.Amount);
			temp.setSeq(i);			
			collAmount.add(temp);
		}	
		return collAmount;
	}

	public void addHeadCollByNode(HeadColumnCollection col,
			DefaultKingdeeTreeNode node) {
		if (node.getUserObject() instanceof HeadColumnInfo && node.isLeaf()) {
			HeadColumnInfo column = (HeadColumnInfo) node.getUserObject();
			// HeadColumnInfo parentColumn = new HeadColumnInfo();
			column.setParent(parentInfo);
			DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode) node
					.getParent();
			int index = parent.getIndex(node);
			column.setSeq(index);
			column.setId(BOSUuid.create(column.getBOSType()));
			column.setDescription("submit");
			column.setNumber(Integer.toString(index));
			column.setLongNumber(parentInfo.getLongNumber() + "!"
					+ Integer.toString(index));
			column.setLevel(node.getLevel() - 1);
			column.setIsLeaf(node.isLeaf());			
			col.add(column);
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			this.addHeadCollByNode(col, (DefaultKingdeeTreeNode) node
					.getChildAt(i));
		}
	}

	public boolean isOk() {
		return isOk;
	}
}