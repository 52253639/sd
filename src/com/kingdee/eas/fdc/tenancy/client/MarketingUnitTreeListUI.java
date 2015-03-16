/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;


import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class MarketingUnitTreeListUI extends AbstractMarketingUnitTreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketingUnitTreeListUI.class);
    
    /**
     * output class constructor
     */
    public MarketingUnitTreeListUI() throws Exception
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

    protected String getRootName() {
		return "营销单元树";
	}

    protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getMarketTree(this.actionOnLoad));	//
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}
   
    public void onLoad() throws Exception {
		super.onLoad();
		initTree();
	}

    
	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return UIFactoryName.MODEL;
	}

}