/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDComboBoxMultiColumnItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectReferedException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeReasonFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeReasonInfo;
import com.kingdee.eas.fdc.sellhouse.IChangeReason;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SwapRoomReasonFactory;
import com.kingdee.eas.fdc.sellhouse.SwapRoomReasonInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class ChangeReasonEditUI extends AbstractChangeReasonEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChangeReasonEditUI.class);
    protected boolean isSaleHouseOrg= FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
    protected FullOrgUnitInfo org=SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
    
    public ChangeReasonEditUI() throws Exception
    {
        super();
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

	protected IObjectValue createNewData() {
		ChangeReasonInfo info = new ChangeReasonInfo();
		info.setBizType((ChangeBizTypeEnum)this.getUIContext().get("bizType"));
		info.setIsEnabled(true);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		return info;
	}

	public void loadFields() {
		super.loadFields();
		this.comboBizType.addItem(new KDComboBoxMultiColumnItem(new String[] {editData.getBizType().getAlias()+"原因"}));
	}


	protected ICoreBase getBizInterface() throws Exception {
		return ChangeReasonFactory.getRemoteInstance();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		this.txtNumber.setMaxLength(80);
		this.txtName.setMaxLength(80);
		this.txtDescription.setMaxLength(255);
		if (!isSaleHouseOrg)
		{
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
		}else{
			if(OprtState.VIEW.equals(getOprtState())){
				this.btnAddNew.setEnabled(true);
				this.btnEdit.setEnabled(true);
				this.btnRemove.setEnabled(true);
			}
		}
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		
	}
	public SelectorItemCollection getSelectors(){
		
        SelectorItemCollection sic = super.getSelectors();
        sic.add("bizType");
        sic.add("orgUnit.*");
        sic.add("CU.*");
        return sic;
    }
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
	 	checkModified();
	 	btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);
		this.getNumberCtrl().requestFocus();
		addnew();
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		
	}
	private void addnew(){
		if(getUIContext().get(BOTPViewS) != null)
        {
            getUIContext().remove(BOTPViewS);
        }
        if(getUIContext().get(UIContext.INIT_DATAOBJECT) != null)
        {
            getUIContext().remove(UIContext.INIT_DATAOBJECT);
        }
        setSave(false);
        setOprtState(STATUS_ADDNEW);
        setDataObject(createNewData());
        applyDefaultValue(this.editData);
        getUILifeCycleHandler().fireOnCreateNewData(this,editData);
        setDataObject(this.editData);

        showMessageForStatus();

        unLockUI();
        loadFields();
        this.initOldData(editData);
        setDefaultFocused();
        this.appendFootRow(null);
    }
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String selectID = this.editData.getId().toString();
		
		if(!editData.getOrgUnit().getId().equals(org.getId())){
			FDCMsgBox.showWarning(this,"创建组织才能进行删除!");
			return;
		}
    	if(outPutWarningSentanceAndVerifyCancelorCancelCancelByID("删除",selectID)){
    		return;
	    }
    	try {
    		this.getBizInterface().isReferenced(new ObjectUuidPK(selectID));
		} catch (ObjectReferedException ex) {
			FDCMsgBox.showWarning(this,"已经发生业务不能删除!");
			return;
		}
    	IObjectPK pk = new ObjectUuidPK(this.editData.getId());
        if (confirmRemove())
        {
         	boolean bool=UtilRequest.isPrepare("ActionRemove",this);
         	if (bool)
         	{
         		prepareRomove(null).callHandler();
         	}
             removeByPK(pk);
         }
    }
	 
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	String selectID = this.editData.getId().toString();
    	if(outPutWarningSentanceAndVerifyCancelorCancelCancelByID("修改",selectID)){//判断是否启用禁用
    		return;
    	}
    	if(!editData.getOrgUnit().getId().equals(org.getId())){
			FDCMsgBox.showWarning(this,"创建组织才能进行修改!");
			return;
		}
    	try {
    		this.getBizInterface().isReferenced(new ObjectUuidPK(selectID));
		} catch (ObjectReferedException ex) {
			FDCMsgBox.showWarning(this,"已经发生业务不能修改!");
			return;
		}
    	
		this.getNumberCtrl().requestFocus();
        String tempOprState = getOprtState();
        setSave(false);
        this.setOprtState(STATUS_EDIT);
        IObjectValue val = (IObjectValue)getUIContext().get("CURRENT.VO") ;
        getUIContext().put("CURRENT.VO",null) ;
        try
        {
            setDataObject(val) ;
        }
        catch(Exception ex)
        {
            setOprtState(tempOprState);
            getUIContext().put("CURRENT.VO",val) ;
            throw ex;
        }

        unLockUI();

        showMessageForStatus();
        initDataStatus();
        setDefaultFocused();
	}
	 
	protected void setIsEnable(boolean flag) throws Exception {
		baseDataInfo = getEditData();
		baseDataInfo.setIsEnabled(flag);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("isEnabled"));
		String message = null;
		if (flag) {
			getBizInterface().updatePartial(baseDataInfo, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK");
		} else {
			getBizInterface().updatePartial(baseDataInfo, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK");
		}
		setMessageText(message);
		showMessage();

		setDataObject(getValue(new ObjectUuidPK(baseDataInfo.getId())));
		loadFields();
		setSave(true);
		setSaved(true);

		this.btnCancelCancel.setVisible(!flag);
		this.btnCancelCancel.setEnabled(!flag);
		this.btnCancel.setVisible(flag);
		this.btnCancel.setEnabled(flag);

	}
}