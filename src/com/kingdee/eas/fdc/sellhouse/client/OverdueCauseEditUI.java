/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.sellhouse.OverdueCauseFactory;
import com.kingdee.eas.fdc.sellhouse.OverdueCauseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class OverdueCauseEditUI extends AbstractOverdueCauseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(OverdueCauseEditUI.class);
    
    /**
     * output class constructor
     */
    public OverdueCauseEditUI() throws Exception
    {
        super();
    }


	public void onLoad() throws Exception {
		super.onLoad();
		if (getOprtState().equals(OprtState.ADDNEW) || getOprtState().equals(OprtState.EDIT)){
			actionAddNew.setVisible(false);
			this.chkIsEnabled.setSelected(true);
		}
		actionRemove.setVisible(false);
	}

	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    protected void verifyInput(ActionEvent e) throws Exception {
		/*编码是否为空*/ 
		KDTextField txtNumber = this.getNumberCtrl();
		if (txtNumber.getText() == null || txtNumber.getText().trim().length() < 1) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		}
		/*名字不能为空*/
		if(this.txtName.getText() == null){
			txtName.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}
	}
    /**
     * output actionPageSetup_actionPerformed
     */
    protected IObjectValue createNewData() {
    	OverdueCauseInfo overdueCauseInfo =new OverdueCauseInfo();
		return overdueCauseInfo;
	}
    /**
     * output actionPageSetup_actionPerformed
     */
	protected ICoreBase getBizInterface() throws Exception {
		return OverdueCauseFactory.getRemoteInstance();
	}


	protected FDCDataBaseInfo getEditData() {
		return editData;
	}


	protected KDBizMultiLangBox getNameCtrl() {
		return new KDBizMultiLangBox();
	}


	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		
        String tempOprState = getOprtState();
        setSave(false);
        setOprtState("EDIT");
        IObjectValue val = (IObjectValue)getUIContext().get("CURRENT.VO");
        getUIContext().put("CURRENT.VO", null);
        try
        {
            setDataObject(val);
        }
        catch(Exception ex)
        {
            setOprtState(tempOprState);
            getUIContext().put("CURRENT.VO", val);
            throw ex;
        }
        unLockUI();
        showMessageForStatus();
        initDataStatus();
        setDefaultFocused();
        this.getNumberCtrl().requestFocus();
     }
      public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		   if(getUIContext().get("BOTPViewStatus") != null)
	            getUIContext().remove("BOTPViewStatus");
	        if(getUIContext().get("InitDataObject") != null)
	            getUIContext().remove("InitDataObject");
	        setSave(false);
	        setOprtState("ADDNEW");
	        setDataObject(createNewData());
	        applyDefaultValue(editData);
	        getUILifeCycleHandler().fireOnCreateNewData(this, editData);
	        setDataObject(editData);
	        showMessageForStatus();
	        unLockUI();
	        loadFields();
	        initOldData(editData);
	        setDefaultFocused();
	        appendFootRow(null);
	
	  }
	

}