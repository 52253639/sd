/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.CostClosePeriodFacadeFactory;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProjectCostCloseUI extends AbstractProjectCostCloseUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectCostCloseUI.class);
    private boolean isOk = false;

    /**
     * output class constructor
     */
    public ProjectCostCloseUI() throws Exception
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

    /**
     * output actionAdd_actionPerformed
     */
    public void actionAdd_actionPerformed(ActionEvent e) throws Exception
    {
    	TreePath[] tps = treeOrigin.getSelectionPaths();
		if (tps == null)
			return;

		for (int i = 0; i < tps.length; i++) {
			TreePath tp = tps[i];
			addPath(tp);
		}

		KDTreeNode root = (KDTreeNode) treeSelected.getModel().getRoot();
		treeSelected.expandAllNodes(true, root);
		super.actionAdd_actionPerformed(e);
    }

    /**
     * output actionAddAll_actionPerformed method
     */
    public void actionAddAll_actionPerformed(ActionEvent e) throws Exception
    {
    	treeOrigin.setSelectionRow(0);
    	 actionAdd_actionPerformed( e);
    }

    /**
     * output actionDelAll_actionPerformed method
     */
    public void actionDelAll_actionPerformed(ActionEvent e) throws Exception
    {
    	treeSelected.setSelectionRow(0);
    	actionDelete_actionPerformed(e);
    }

    
    /**
	 * @param tp
	 */
	private void addPath(TreePath tp) {
		DefaultKingdeeTreeNode selectedNode = (DefaultKingdeeTreeNode) tp
				.getLastPathComponent();
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
			if ((temp = findNode(node, (DefaultKingdeeTreeNode) treeSelected
					.getModel().getRoot())) == null)
				addTo(toNode, tp, j);
			else
				toNode = temp;
		}
	}
	
	private DefaultKingdeeTreeNode findNode(DefaultKingdeeTreeNode source,
			DefaultKingdeeTreeNode dest) {
		if (source == null || dest == null)
			return null;

		if (source.getUserObject() != null && dest.getUserObject() != null
				&& source.getUserObject().equals(dest.getUserObject()))
			return dest;

		for (int i = 0; i < dest.getChildCount(); i++) {
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) dest
					.getChildAt(i);
			if ((child = findNode(source, child)) != null)
				return child;
		}
		return null;
	}

	private void addTo(DefaultKingdeeTreeNode toNode, TreePath tp, int from) {

		DefaultKingdeeTreeNode root = null;
		while (from < tp.getPathCount()) {
			DefaultKingdeeTreeNode oriChild = ((DefaultKingdeeTreeNode) tp
					.getPathComponent(from));
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) oriChild
					.clone();

			if (from == tp.getPathCount() - 1) {
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
    
    /**
     * output actionDelete_actionPerformed
     */
    public void actionDelete_actionPerformed(ActionEvent e) throws Exception
    {
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
     * output actionConfirm_actionPerformed
     */
    public void actionConfirm_actionPerformed(ActionEvent e) throws Exception
    {
	    try{
	    	btnConfirm.setEnabled(false);
	    	TreeModel treeModel = treeSelected.getModel();
	    	DefaultKingdeeTreeNode root=(DefaultKingdeeTreeNode) treeModel.getRoot();
	    	if(root.getChildCount()==0){
	    		MsgBox.showError(this, "未选定工程项目！");
	    		SysUtil.abort();
	    	}
	    	DefaultKingdeeTreeNode newRoot=(DefaultKingdeeTreeNode) ((DefaultKingdeeTreeNode)root.getChildAt(0)).clone();
	    	this.cloneTree(newRoot,(DefaultKingdeeTreeNode) root.getChildAt(0));
	    	TreeModel newTree=new KingdeeTreeModel(newRoot);
	    	List projectIdList = new ArrayList();
	    	if(newTree!=null){
	    		addPrjCollByNode(projectIdList, (DefaultKingdeeTreeNode) newTree
	    				.getRoot());
	    	}
	    	if(rbClosePeriod.isSelected()){
	    		CostClosePeriodFacadeFactory.getRemoteInstance().checkCostSplit(projectIdList);
	    		String result = CostClosePeriodFacadeFactory.getRemoteInstance().traceCostClose(projectIdList);
	    		txtResult.setText(result);
	    	}else if(rbFrozenPeriod.isSelected()){
	    		CostClosePeriodFacadeFactory.getRemoteInstance().frozenProject(projectIdList);
	    		txtResult.setText("所选工程项目单据已冻结。");
	    	}else if(rbUnClosePeriod.isSelected()){
	//    		MsgBox.showInfo(this, "暂不提供此功能，待月结完善之后再提供！");
	//    		SysUtil.abort();
	    		String result = CostClosePeriodFacadeFactory.getRemoteInstance().antiCostClose(projectIdList);
	    		txtResult.setText(result);
	    	}else{
	    		MsgBox.showError(this, "请选定操作！");
	    		SysUtil.abort();
	    	}
	    }finally{
	    	btnConfirm.setEnabled(true);
    	}
    }
    
    private void addPrjCollByNode(List idList,DefaultKingdeeTreeNode node){
    	if (node.getUserObject() instanceof CurProjectInfo && node.isLeaf()) {
    		CurProjectInfo column = (CurProjectInfo) node.getUserObject();
			idList.add(column.getId().toString());
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			this.addPrjCollByNode(idList, (DefaultKingdeeTreeNode) node
					.getChildAt(i));
		}
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
        setConfirm(false);
//		disposeUIWindow();
		super.destroyWindow();
    }
    
    public void setConfirm(boolean isOk) {
		this.isOk = isOk;
//		disposeUIWindow();
		super.destroyWindow();
	}
    
    public void onLoad() throws Exception {
    	if(!SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()){
    		MsgBox.showError(this, "当前公司为虚体，不允许使用该功能。");
			SysUtil.abort();
    	}
    	if(!FDCUtils.IsInCorporation(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString())){
			MsgBox.showWarning(this, "此财务组织未启用成本月结！");
			SysUtil.abort();
		}
    	super.onLoad();
    	
    	this.actionAddAll.setEnabled(true);
    	this.actionDelAll.setEnabled(true);
    	
    	ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		projectTreeBuilder.build(this, treeOrigin, actionOnLoad);
		treeOrigin.setShowsRootHandles(true);
		
		KingdeeTreeModel treeModel = (KingdeeTreeModel) treeSelected.getModel();

		if (treeModel.getRoot() != null) {
			KDTreeNode root2 = new KDTreeNode("");
			treeModel.setRoot(root2);
			treeSelected.setRootVisible(false);
			treeSelected.setShowsRootHandles(true);
		}	
		rbClosePeriod.setSelected(true);
		
		initCtrlListener();
    }
    
    protected void rbClosePeriod_stateChanged(ChangeEvent e) throws Exception {
    	super.rbClosePeriod_stateChanged(e);
    	if(rbClosePeriod.isSelected()){
    		txtDescription.setText(EASResource
    				.getString(
    						"com.kingdee.eas.fdc.finance.client.FinanceResource",
    						"costClose"));
    		txtResult.setText(null);
    	}
    }
    
    protected void rbFrozenPeriod_stateChanged(ChangeEvent e) throws Exception {
    	super.rbFrozenPeriod_stateChanged(e);
    	if(rbFrozenPeriod.isSelected()){
    		txtDescription.setText(EASResource
    				.getString(
    						"com.kingdee.eas.fdc.finance.client.FinanceResource",
    						"costFrozen"));
    		txtResult.setText(null);
    	}
    }
    
    protected void rbUnClosePeriod_stateChanged(ChangeEvent e) throws Exception {
    	super.rbUnClosePeriod_stateChanged(e);
    	if(rbUnClosePeriod.isSelected()){
    		txtDescription.setText(EASResource
    				.getString(
    						"com.kingdee.eas.fdc.finance.client.FinanceResource",
    						"costUnClose"));
    		txtResult.setText(null);
    	}
    }
    
	/**
	 * 处理鼠标双击事件
	 */
	private void initCtrlListener(){
		MouseAdapter mouseAdapter = new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
					if(e.getSource().equals(treeOrigin)){
						try {
							actionAdd_actionPerformed(null);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					
						
					}else if(e.getSource().equals(treeSelected)){
						try {
							actionDelete_actionPerformed(null);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					
						
					}
					
				}
			}
		};
		treeSelected.addMouseListener(mouseAdapter);
		treeOrigin.addMouseListener(mouseAdapter);
	}

}