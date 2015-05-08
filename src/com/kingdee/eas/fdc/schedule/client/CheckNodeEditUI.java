/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.CheckNodeFactory;
import com.kingdee.eas.fdc.schedule.CheckNodeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class CheckNodeEditUI extends AbstractCheckNodeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(CheckNodeEditUI.class);
    /**
     * output class constructor
     */
    public CheckNodeEditUI() throws Exception
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
		checkNumber();
        super.actionSubmit_actionPerformed(e);
    }
    
    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
        onLoad();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.txtName.setMaxLength(80);
//    	this.txtName.setDefaultLangItemData(getLocale());
    	this.txtDescription.setMaxLength(200);
    	this.txtNumber.setMaxLength(2);
    	this.btnCancel.setVisible(false);
    	this.btnCancelCancel.setVisible(false);
    }    
    
    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	this.btnSave.setEnabled(true);
        super.actionEdit_actionPerformed(e);
        onLoad();
    }

	protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return txtName;
	}

	
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	protected void showSubmitSuccess() {
		setMessageText(getClassAlise() + " " + EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Submit_OK"));
		setNextMessageText(getClassAlise() + " " + EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Edit"));
		setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
		setIsShowTextOnly(false);
		showMessage();
	}

	/** 主要是为了修改保存后的的提示 **/
	protected void showSaveSuccess() {
		setMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Save_OK"));
		setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
		setShowMessagePolicy(0);
		setIsShowTextOnly(false);
		showMessage();
	}
	
	private void checkNumber() {
		// 校验格式是不是数字
		String number = this.txtNumber.getText().trim();
		if (StringUtils.isEmpty(number)) {
			FDCMsgBox.showInfo("编码不能为空！");
			abort();
		}
		Pattern tDouble = Pattern.compile("([0-9]{1,20})");
		if (!tDouble.matcher(number).matches()) {
			FDCMsgBox.showInfo("编码只能输入数字！");
			abort();
		}
//		int currNumber = Integer.parseInt(number);
//		if (alreadyExistNumber.contains(currNumber)) {
//			FDCMsgBox.showInfo("编码已经存在！");
//			abort();
//		}
	}
	
	protected IObjectValue createNewData() {
		CheckNodeInfo checkNodeInfo = new CheckNodeInfo();
		//这个在loadfields里搞的。
		try {
			checkNodeInfo.setNumber(getNewNumber());
		} catch (BOSException e) {
			e.printStackTrace();
			abort();
		} catch (SQLException e) {
			e.printStackTrace();
			abort();
		}

		checkNodeInfo.setIsEnabled(isEnabled);
		return checkNodeInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CheckNodeFactory.getRemoteInstance();
	}
	
	/**
	 * 自动生成编码
	 * */
	private String getNewNumber() throws BOSException, SQLException{
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select top 1 fnumber ");
		sql.appendSql(" from T_SCH_CheckNode ORDER BY fnumber desc");
		IRowSet rs = sql.executeQuery();
		String currMaxNumber = null;
		while (rs.next()) {
			currMaxNumber= rs.getString("fnumber");
		}
		if (StringUtils.isEmpty(currMaxNumber)) {
			return "01";
		}
		String currNumber = null;
        currNumber = String.valueOf(Integer.parseInt(currMaxNumber) + 1);
        if (Integer.parseInt(currMaxNumber) < 9) {
			currNumber = "0" + currNumber;
		}
		return currNumber;
	}
}