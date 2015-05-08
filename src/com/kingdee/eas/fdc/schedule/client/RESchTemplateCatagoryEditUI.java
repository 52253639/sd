/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateCatagoryFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateCatagoryInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class RESchTemplateCatagoryEditUI extends AbstractRESchTemplateCatagoryEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(RESchTemplateCatagoryEditUI.class);
    
    /**
     * output class constructor
     */
    public RESchTemplateCatagoryEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
		super.onLoad();
		this.actionAddNew.setVisible(false);
	}

	public void onShow() throws Exception {
		super.onShow();
		this.contParent.setEnabled(false);
		this.prmtParent.setEnabled(false);
		this.actionSave.setVisible(false);
		updateUI();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
        if (editData.getParent() != null) {
			editData.setTemplateType(editData.getParent().getTemplateType());
		}
    }

  

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
		super.actionSubmit_actionPerformed(e);
		
    }

    
    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
        this.prmtParent.setEnabled(false);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		RESchTemplateCatagoryInfo parent = editData.getParent();
		super.actionAddNew_actionPerformed(e);
		this.editData.setParent(parent);
		this.prmtParent.setData(parent);
		this.prmtParent.setEnabled(false);
	}

   

	protected FDCTreeBaseDataInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return txtName;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected IObjectValue createNewData() {
		RESchTemplateCatagoryInfo info = new RESchTemplateCatagoryInfo();
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RESchTemplateCatagoryFactory.getRemoteInstance();
	}
	
	 public void initUIToolBarLayout() {
		this.toolBar.add(btnAddNew);
		this.toolBar.add(btnEdit);
		this.toolBar.add(btnSave);
		this.toolBar.add(btnSubmit);
		this.toolBar.add(btnRemove);

	}

}