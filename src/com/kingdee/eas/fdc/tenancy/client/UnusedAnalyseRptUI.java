/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class UnusedAnalyseRptUI extends AbstractUnusedAnalyseRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(UnusedAnalyseRptUI.class);
    
    /**
     * output class constructor
     */
    public UnusedAnalyseRptUI() throws Exception
    {
        super();
    }

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getBuildingTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
	
	protected CommonQueryDialog initCommonQueryDialog() 
	{
		CommonQueryDialog dialog = super.initCommonQueryDialog();
		try 
		{
			dialog.setShowFilter(false);
			dialog.setShowSorter(false);
		}
		catch (Exception e) 
		{
			handUIException(e);
		}
		return dialog;
	}
	
	protected String[] getLocateNames()
	 {
	     String[] locateNames = new String[2];
	     locateNames[0] = "roomNumber";
	     locateNames[1] = IFWEntityStruct.dataBase_Name;
	     return locateNames;
	}
    
	public void onLoad() throws Exception {
		super.onLoad();
		this.initTree();
		
		this.tblMain.getHeadMergeManager().mergeBlock(0, 0, 1, this.tblMain.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);		
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

}