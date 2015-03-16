/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.IError;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDealAmountError;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class ErrorCheckListUI extends AbstractErrorCheckListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ErrorCheckListUI.class);
    
    /**
     * output class constructor
     */
    public ErrorCheckListUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	initSellProjectTree();
    	initErrorTree();
    	this.actionPurDistillUpdate.setEnabled(true);
    	this.actionPurAddMarket.setEnabled(true);
    	this.actionProjectDataBaseUpdate.setEnabled(true);
    }

	private void initErrorTree() {
//		this.treeViewError.setTitle("检查项");
		
		DefaultKingdeeTreeNode root = new DefaultKingdeeTreeNode("错误项");
		
		KDWorkButton btnCheck = new KDWorkButton();
		btnCheck.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				btnCheck_actionPerformed(e);
			}
		});
		btnCheck.setToolTipText("检查");
		btnCheck.setIcon(EASResource.getIcon("imgTbtn_sortstandard"));
		this.treeViewError.getControlPane().add(btnCheck);
		
		List errors = getErrorList();
		
		this.treeError.setShowCheckBox(true);
		root.setCheckBoxVisible(false);
		for(int i=0; i<errors.size(); i++){
			IError error = (IError) errors.get(i);
			KDTreeNode node = new KDTreeNode(error.getDes());
			node.setUserObject(error);
//			node.setCheckBoxVisible(true);
//			node.setCheckBoxEnabled(true);
			root.add(node);
		}
		
		this.treeError.setModel(new DefaultTreeModel(root));
		this.treeError.expandAllNodes(true, root);
	}
	
	protected void btnPurDistillUpdate_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnPurDistillUpdate_actionPerformed(e);
		PurchaseFactory.getRemoteInstance().purDistillUpdate();
	}

	protected void btnPurAddMarket_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnPurAddMarket_actionPerformed(e);
		PurchaseFactory.getRemoteInstance().purAddMarket();
	}
	
	protected void btnProjectDataBaseUpdate_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnProjectDataBaseUpdate_actionPerformed(e);
		SellProjectFactory.getRemoteInstance().allProjectDataUpdate();
	}
	
	protected void btnCheck_actionPerformed(ActionEvent e){
		
//		this.treeError.getSelectionRows();
		
		
		
		
		
		
	}
	
	private List getErrorList() {
		List errors = new ArrayList();
		//errors.add(new RoomDealAmountError());
		//errors.add(new RoomDealAmountError());
		//TODO 待添加
		
		return errors;
	}

	private void initSellProjectTree() throws Exception {
		this.treeSellProject.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.treeSellProject.expandAllNodes(true, (TreeNode) this.treeSellProject.getModel().getRoot());
	}
    
}