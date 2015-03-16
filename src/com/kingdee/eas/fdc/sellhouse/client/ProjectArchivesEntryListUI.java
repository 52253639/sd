/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.sellhouse.BaseDataPropertyEnum;
import com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryInfo;
import com.kingdee.eas.framework.*;

public class ProjectArchivesEntryListUI extends AbstractProjectArchivesEntryListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectArchivesEntryListUI.class);
    
    public ProjectArchivesEntryListUI() throws Exception
    {
        super();
    }
    
    protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getBaseDataTree(this.actionOnLoad,null));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	initTree();
		this.treeMain.setSelectionRow(0);
		this.btnRangeEnactment.setVisible(true);
    }
    
    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	super.treeMain_valueChanged(e);
    }

    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
        DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
        FilterInfo filter = new FilterInfo();
       if(node.getUserObject() instanceof ProjectArchivesEntryInfo)
       {
    	   ProjectArchivesEntryInfo projectArchInfo = (ProjectArchivesEntryInfo)node.getUserObject();
    	   if(BaseDataPropertyEnum.ProjectControl.equals(projectArchInfo.getBaseDataProperty()))
    	   {
    		   projectArchInfo.getName();
    	   }
       }
    }

	protected ICoreBase getBizInterface() throws Exception {
		return ProjectArchivesEntryFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}

}