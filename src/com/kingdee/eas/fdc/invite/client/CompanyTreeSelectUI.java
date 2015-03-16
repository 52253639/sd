/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.uiframe.UIFrameResource;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.client.tree.KDTreeNode;

/**
 * output class name
 */
public class CompanyTreeSelectUI extends AbstractCompanyTreeSelectUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CompanyTreeSelectUI.class);
	
	private JDialog dialog = null;

	private boolean defualtModelIsTree = false;// 默认的选择模式是否树型

	/** 已选择树Model备份 */
	private TreeModel bakSelectedTreeModel = null;
	
	

	/**
	 * 描述：备份选择的树Model，以便在用户更改了选择，点Cancel之后恢复
	 */
	private void bakTreeSelected() {
		if (treeSelected.getModel().getRoot() != null) {
			KDTreeNode root2 = new KDTreeNode("");
			cloneTree(root2, (KDTreeNode) treeSelected.getModel().getRoot());
			bakSelectedTreeModel = new KingdeeTreeModel(root2);
		}
	}

	/**
	 * 
	 * 描述：点Cancel时，恢复修改前的Model
	 * 
	 */
	private void restoreTreeSelected() {
		// 用户取消操作，如果已有备份，则要恢复前一次的备份
		if (bakSelectedTreeModel != null) {
			
			java.util.List list = new ArrayList();
			popNode(list, (DefaultKingdeeTreeNode)bakSelectedTreeModel.getRoot());
			this.firePropertyChange("companyChanged", null, list.toArray());
			
			KDTreeNode root2 = new KDTreeNode("");
			// 克隆一个新的，以免修改后又影响备份的Model
			cloneTree(root2, (KDTreeNode) bakSelectedTreeModel.getRoot());
			treeSelected.setModel(new KingdeeTreeModel(root2));
			treeSelected.expandAllNodes(true, (TreeNode) treeSelected
					.getModel().getRoot());
			
			
			
		} else { // 没有备份，表明是第一次选择就取消，则恢复空白
			KDTreeNode root = new KDTreeNode("");
			((KingdeeTreeModel) treeSelected.getModel()).setRoot(root);
		}
	}

	/**
	 * 
	 * 描述：得到组织架构树Model
	 * 
	 * @return
	 * @throws EASBizException
	 * @author jxd 创建时间：2005-12-31
	 */
	protected TreeModel getTreeModel() throws EASBizException {
		// CtrlUnitInfo cuInfo =
		// SysContext.getSysContext().getCurrentCtrlUnit();
		// if (cuInfo == null) {
		// throw new OUException(OUException.CU_CAN_NOT_NULL);
		// }
		TreeModel orgTreeModel = null;
		try {
			// orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY, "",
			// cuInfo.getId().toString(), getListUI().getMetaDataPK(),
			// FMHelper.getActionPK(actionListOnLoad));
			// orgTreeModel =
			// NewOrgUtils.getTreeModel(OrgViewType.RESPONSECENTER, null, false,
			// false, false,cuInfo.getId().toString(), false, null, 0, null,
			// null);
			// String rootUnitID =
			// SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
			String rootUnitID = OrgConstants.SYS_CU_ID;

			orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY, null,
					false, true, false, rootUnitID, false, null, 0, null, null);

		} catch (Exception e) {
			handUIException(e);
		}
		return orgTreeModel;
	}

	/**
	 * output class constructor
	 */

	public CompanyTreeSelectUI() throws Exception {
		super();

		initUIContentLayout();
		// this.setBounds(new Rectangle(10, 10, 632, 446));

		if (getTreeModel() == null)
			return; // can't go here
		treeOrigin.setModel(getTreeModel());
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeOrigin
				.getModel().getRoot();
		treeOrigin.expandAllNodes(true, root);

		KingdeeTreeModel treeModel = (KingdeeTreeModel) treeSelected.getModel();

		if (treeModel.getRoot() != null) {
			KDTreeNode root2 = new KDTreeNode("");
			treeModel.setRoot(root2);
			treeSelected.setRootVisible(false);			
		}
		this.kDCheckBoxHasChildren.setVisible(true);
		setSelectModel();

	}

	/***************************************************************************
	 * 设置默认的选择模式
	 * 
	 * @param isTree
	 */
	public void setDefaultSelectModel(boolean isTree) {
		this.defualtModelIsTree = isTree;
	}

	private void setSelectModel() {
		if (defualtModelIsTree) {
			// 树型结构的选择
			this.kDTreeView2.setVisible(true);
			this.kDScrollPane1.setVisible(false);
			// this.kDCheckBoxHasChildren.setVisible(false);
			this.kDCheckBoxIsTreeModel.setSelected(true);
			this.kDCheckBoxHasChildren.setSelected(false);
			treeSelected.setShowsRootHandles(true);
		} else {
			// 非树型结构的选择
			this.kDTreeView2.setVisible(true);
			this.kDScrollPane1.setVisible(false);
			// this.kDCheckBoxHasChildren.setVisible(true);
			this.kDCheckBoxIsTreeModel.setSelected(false);
			this.kDCheckBoxHasChildren.setSelected(false);
			treeSelected.setShowsRootHandles(false);
		}
	}

	private void addTo(DefaultKingdeeTreeNode toNode, TreePath tp, int from) {

		DefaultKingdeeTreeNode root = null;
		while (from < tp.getPathCount()) {
			DefaultKingdeeTreeNode oriChild = ((DefaultKingdeeTreeNode) tp
					.getPathComponent(from));
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) oriChild
					.clone();

			if (from == tp.getPathCount() - 1) {
				if (this.kDCheckBoxHasChildren.isSelected())
					cloneTree(child, oriChild);
			}
			if (toNode == null) {
				root = (DefaultKingdeeTreeNode) ((KingdeeTreeModel) treeSelected
						.getModel()).getRoot();
				// root.add(child);
				((KingdeeTreeModel) treeSelected.getModel()).insertNodeInto(
						child, root, 0);
			} else {
				// 找到应该插入的位置
				DefaultKingdeeTreeNode sibling = oriChild, found = null;
				while ((sibling = (DefaultKingdeeTreeNode) sibling
						.getNextSibling()) != null) {
					found = findNode(sibling, toNode);
					if (found != null)
						break;
				}
				if (found != null)
					toNode.insert(child, toNode.getIndex(found));
				else
					toNode.add(child);

				((KingdeeTreeModel) treeSelected.getModel())
						.nodeStructureChanged(toNode);
			}
			toNode = child;
			from++;
		}
	}

	private void cloneTree(DefaultKingdeeTreeNode root,
			DefaultKingdeeTreeNode oriRoot) {
		for (int i = 0; i < oriRoot.getChildCount(); i++) {
			DefaultKingdeeTreeNode oriChild = (DefaultKingdeeTreeNode) oriRoot
					.getChildAt(i);
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) oriChild
					.clone();
			root.add(child);
			cloneTree(child, oriChild);
		}
	}

	private void addChildrenToList(DefaultKingdeeTreeNode oriRoot) {
		for (int i = 0; i < oriRoot.getChildCount(); i++) {
			DefaultKingdeeTreeNode oriChild = (DefaultKingdeeTreeNode) oriRoot
					.getChildAt(i);
			addNodeToList(oriChild);
			addChildrenToList(oriChild);
		}
	}

	private DefaultKingdeeTreeNode findNode(DefaultKingdeeTreeNode source,
			DefaultKingdeeTreeNode dest) {
		if (source == null || dest == null)
			return null;

		if (source.getUserObject().equals(dest.getUserObject()))
			return dest;

		for (int i = 0; i < dest.getChildCount(); i++) {
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) dest
					.getChildAt(i);
			if ((child = findNode(source, child)) != null)
				return child;
		}
		return null;
	}

	public void addNodeToList(DefaultKingdeeTreeNode selectedNode) {
//		OrgStructureInfo org = (OrgStructureInfo) node.getUserObject();
//		for (int i = 0; i < this.kDListSelectCompany.getElementCount(); i++) {
//			if (this.kDListSelectCompany.getElement(i).equals(org.getUnit()))
//				return;
//			else
//				continue;
//		}
//		this.kDListSelectCompany.addElement(org.getUnit());
		DefaultKingdeeTreeNode rightRoot = (DefaultKingdeeTreeNode) ((KingdeeTreeModel) treeSelected
				.getModel()).getRoot();
		for(int i=0;i<rightRoot.getChildCount();i++){
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode)rightRoot.getChildAt(i);
			if(selectedNode.getUserObject() instanceof OrgStructureInfo
					&& child.getUserObject() instanceof FullOrgUnitInfo){				
				BOSUuid id1 = ((OrgStructureInfo) selectedNode.getUserObject()).getUnit().getId();
				BOSUuid id2 = ((FullOrgUnitInfo)child.getUserObject()).getId();
				if(id1.equals(id2)){
					return ;
				}
			}
			
			if(child.getUserObject().equals(selectedNode.getUserObject()))
				return;
			else
				continue;
		}
		DefaultKingdeeTreeNode node = new DefaultKingdeeTreeNode(selectedNode.getUserObject(),
                null, false, false);
		((KingdeeTreeModel) treeSelected.getModel()).insertNodeInto(
				node, rightRoot, ((KingdeeTreeModel) treeSelected.getModel()).getChildCount(rightRoot));
	}

	public void actionAdd_actionPerformed(ActionEvent e) throws Exception {

		TreePath[] tps = treeOrigin.getSelectionPaths();
		if (tps == null)
			return;

		for (int i = 0; i < tps.length; i++) {
			TreePath tp = tps[i];

			DefaultKingdeeTreeNode selectedNode = (DefaultKingdeeTreeNode) tp
					.getLastPathComponent();
			
			if(this.kDCheckBoxIsTreeModel.isSelected()){
				//
				// 如果已经有该节点，先删除掉
				KDTreeNode rightRoot = (KDTreeNode) ((KingdeeTreeModel) treeSelected
						.getModel()).getRoot();
				DefaultKingdeeTreeNode findNode = findNode(selectedNode, rightRoot);
				if (findNode != null) {
					DefaultKingdeeTreeNode parent = (DefaultKingdeeTreeNode) findNode
							.getParent();
					findNode.removeFromParent();
					((KingdeeTreeModel) treeSelected.getModel())
							.nodeStructureChanged(parent);
				}

				DefaultKingdeeTreeNode toNode = null;
				for (int j = 0; j < tp.getPathCount(); j++) {
					// 从根开始搜索，如果没有，把整条TreePath加过去，如果有，裁减掉根，从下一个节点开始
					DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) tp
							.getPathComponent(j);
					DefaultKingdeeTreeNode temp = null;
					if ((temp = findNode(node,
							(DefaultKingdeeTreeNode) treeSelected.getModel()
									.getRoot())) == null)
						addTo(toNode, tp, j);
					else
						toNode = temp;
				}
			}
			else{
				// 将选择的node添加到kdlist
				addNodeToList(selectedNode);
				if (this.kDCheckBoxHasChildren.isSelected()) {
					addChildrenToList(selectedNode);
				}
			}
		}
		
		KDTreeNode root = (KDTreeNode) treeSelected.getModel().getRoot();
		if(this.kDCheckBoxIsTreeModel.isSelected())
			treeSelected.setRootVisible(false);
		treeSelected.expandAllNodes(true, root);
		super.actionAdd_actionPerformed(e);
	}

	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {

		// 恢复
		restoreTreeSelected();

		dialog.setVisible(false);
		super.actionCancel_actionPerformed(e);
	}

	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {

		TreeModel tree = (KingdeeTreeModel) treeSelected.getModel();
		KDTreeNode root = (KDTreeNode) tree.getRoot();
		// 允许不选公司
		// if (root.getChildCount() == 0) {
		// MsgBox.showInfo(this, EASResource.getString(resourcePath,
		// "noCompany"));
		// return;
		// }

		// DefaultMutableTreeNode simpleRoot = new DefaultMutableTreeNode();
		// DefaultMutableTreeNode bigNode = (DefaultMutableTreeNode)
		// root.children().getAllowsChildren().getChildAt(0);
		// boolean child = root.getAllowsChildren();

		// Object [] values = new Object[root.children().]
		java.util.List list = new ArrayList();
		popNode(list, root);
		this.firePropertyChange("companyChanged", null, list.toArray());
		dialog.setVisible(false);
		super.actionConfirm_actionPerformed(e);

		// 备份
		bakTreeSelected();
	}
	/***
	 * 已不使用
	 * @param list
	 * @deprecated
	 */
	private void popList(java.util.List list) {
		for (int i = 0; i < this.kDListSelectCompany.getElementCount(); i++) {
			list.add(this.kDListSelectCompany.getElement(i));
		}
	}

	private void popNode(java.util.List list, DefaultKingdeeTreeNode root) {
		for (Enumeration c = root.children(); c.hasMoreElements();) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) c
					.nextElement();
			if (node.isCheckBoxEnabled()) {
				// node.
				if(node.getUserObject() instanceof FullOrgUnitInfo){
					FullOrgUnitInfo org = (FullOrgUnitInfo) node.getUserObject();
					list.add(org);
				}
				else if(node.getUserObject() instanceof OrgStructureInfo){
					OrgStructureInfo org = (OrgStructureInfo) node.getUserObject();
					list.add(org.getUnit());
				}
				
			}
			popNode(list, node);
		}
	}

	public void actionDelete_actionPerformed(ActionEvent e) throws Exception {
		int[] selectIds = this.kDListSelectCompany.getSelectedIndices();
		for (int i = selectIds.length - 1; i >= 0; i--) {
			this.kDListSelectCompany.removeElementAt(selectIds[i]);

		}

		TreePath[] tps = treeSelected.getSelectionPaths();
		if (tps == null)
			return;
		for (int i = 0; i < tps.length; i++) {
			TreePath tp = tps[i];
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) tp
					.getLastPathComponent();
			while (node.getParent() != null
					&& node.getParent().getChildCount() == 1
					&& node.getParent().getParent() != null) {
				tp = tp.getParentPath();
				node = (DefaultKingdeeTreeNode) tp.getLastPathComponent();
			}
			if (node.getParent() != null
					&& node.getParent().getChildCount() == 1
					&& node.getParent().getParent() != null)
				node = (DefaultKingdeeTreeNode) node.getParent();

			((KingdeeTreeModel) treeSelected.getModel())
					.removeNodeFromParent(node);// .nodeStructureChanged(parent);
		}

		super.actionDelete_actionPerformed(e);
	}

	/**
	 * output kDCheckBoxIsTreeModel_actionPerformed method
	 */
	protected void kDCheckBoxIsTreeModel_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
		// 每次切换，都清空所有数据
		//this.kDListSelectCompany.removeAllElements();

		this.treeSelected.getModel().getRoot();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeSelected
				.getModel().getRoot();
		this.treeSelected.removeAllChildrenFromParent(node);
		// this.kDCheckBoxHasChildren.setSelected(true);
		//this.kDTreeView2
				//.setVisible(this.kDTreeView2.isVisible() ? false : true);
		// this.kDCheckBoxHasChildren.setVisible(this.kDCheckBoxHasChildren.isVisible()?false:true);
		//this.kDScrollPane1.setVisible(this.kDScrollPane1.isVisible() ? false
				//: true);
		// this.kDListSelectCompany.setVisible(this.kDListSelectCompany.isVisible()?false:true);

	}

	/**
	 * @param dialog
	 */
	public void setDialog(JDialog dialog) {
		this.dialog = dialog;
	}

	/**
	 * @param model
	 */
	public void setValue(TreeModel model) {
		this.treeSelected.setModel(model);
		bakTreeSelected();
	}

}