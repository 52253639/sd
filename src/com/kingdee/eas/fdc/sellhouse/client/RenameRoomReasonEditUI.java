/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.sellhouse.RenameRoomReasonFactory;
import com.kingdee.eas.fdc.sellhouse.RenameRoomReasonInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;

/**
 * output class name
 */
public class RenameRoomReasonEditUI extends AbstractRenameRoomReasonEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(RenameRoomReasonEditUI.class);
    
    /**
     * output class constructor
     */
    public RenameRoomReasonEditUI() throws Exception
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
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
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
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {	
    	//by tim_gao 2010-10-26
    	String selectID = this.editData.getId().toString();
    	String warnings= "修改";
    	if(outPutWarningSentanceAndVerifyCancelorCancelCancelByID(warnings,selectID)){//判断是否启用禁用
    		this.abort();
    	}
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {	
    	
    	//by tim_gao 2010-10-26
    	String selectID = this.editData.getId().toString();
    	String warnings= "删除";
    	if(outPutWarningSentanceAndVerifyCancelorCancelCancelByID(warnings,selectID)){//判断是否启用禁用
    		this.abort();
    	}
        super.actionRemove_actionPerformed(e);
    }

    protected FDCDataBaseInfo getEditData() {
    	RenameRoomReasonInfo reasonInfo = new RenameRoomReasonInfo();
		return reasonInfo;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return txtName;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected IObjectValue createNewData() {
		RenameRoomReasonInfo reasonInfo = new RenameRoomReasonInfo();
		reasonInfo.setIsEnabled(false);
		return reasonInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RenameRoomReasonFactory.getRemoteInstance();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		
		this.txtNumber.setMaxLength(80);
		this.txtName.setMaxLength(80);
		this.txtDescription.setMaxLength(255);
		
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
	}
	/**
     * @author tim_gao
     * @date 2010-10-26
     * @throws BOSException 
     * @throws EASBizException 
     * @description 输入ID和提醒语句判断是否启用
     */
    public boolean outPutWarningSentanceAndVerifyCancelorCancelCancelByID(String words,String selectID) throws EASBizException, BOSException{
    	boolean flag=false;
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("id",selectID));
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.valueOf(true)));//是否启用
    	if(RenameRoomReasonFactory.getRemoteInstance().exists(filter)){//判断记录是否存在
    		MsgBox.showWarning("本记录已经启用，不能"+words+"!");
    		flag=true;
    	}
		return flag;
    }
}