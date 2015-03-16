/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ProjectReceivableListUI extends AbstractProjectReceivableListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectReceivableListUI.class);
    
    /**
     * output class constructor
     */
    public ProjectReceivableListUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	 initTree();
    	super.onLoad();
		
		
	}

	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    protected void initTree() throws Exception
	{
//    	this.treeMain.setModel(SHEHelper.getSellProjectTree(actionOnLoad, MoneySysTypeEnum.SalehouseSys));
    	this.treeMain.setModel(FDCTreeHelper.getUnitTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
//    	this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
    }
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

}