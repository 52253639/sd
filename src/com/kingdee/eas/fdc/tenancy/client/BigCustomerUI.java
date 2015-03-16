/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class BigCustomerUI extends AbstractBigCustomerUI
{
    private static final Logger logger = CoreUIObject.getLogger(BigCustomerUI.class);
    public BigCustomerUI() throws Exception
    {
        super();
    }

    public void storeFields()
    {
        super.storeFields();
    }

    protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
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

    public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnQuery.setEnabled(true);
		this.actionAddNew.setVisible(false);
		this.menuEdit.setVisible(false);
	}
    
    protected String[] getLocateNames()
	 {
	     String[] locateNames = new String[2];
	     locateNames[0] = "customerName";
	     locateNames[1] = IFWEntityStruct.dataBase_Name;
	     return locateNames;
	}
    
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}
}