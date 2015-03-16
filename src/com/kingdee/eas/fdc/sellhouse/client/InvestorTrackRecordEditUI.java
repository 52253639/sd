/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.InvestorHouseFactory;
import com.kingdee.eas.fdc.sellhouse.InvestorHouseInfo;
import com.kingdee.eas.fdc.sellhouse.InvestorHouseTrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.InvestorHouseTrackRecordInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class InvestorTrackRecordEditUI extends AbstractInvestorTrackRecordEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InvestorTrackRecordEditUI.class);
    
    UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
    
//    protected void inOnload() throws Exception {
//    	super.inOnload();
//    }
    
    public InvestorTrackRecordEditUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	init();
    	this.prmtTrackPerson.setValue(userInfo);
    	this.prmtTrackPerson.setEnabled(false);
    	this.storeFields();
    }
    
    private void init()
    {
    	this.btnSave.setVisible(false);
    	this.btnAddNew.setVisible(false);
    	this.btnEdit.setVisible(false);
    	this.btnCopy.setVisible(false);
    	this.btnCancel.setVisible(false);
    	this.btnCancelCancel.setVisible(false);
    	this.btnPrint.setVisible(false);
    	this.btnPrintPreview.setVisible(false);
    	this.btnRemove.setVisible(false);
    	this.btnSubmit.setEnabled(true);
    }
    
    public void storeFields()
    {
        super.storeFields();
        InvestorHouseTrackRecordInfo info = this.editData;
        String trackRecordID = (String)this.getUIContext().get("id");
        try {
			InvestorHouseInfo investorInfo = InvestorHouseFactory.getRemoteInstance().getInvestorHouseInfo(new ObjectUuidPK(BOSUuid.read(trackRecordID)));
			 info.setHead(investorInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}  
    }

	protected IObjectValue createNewData() {
		InvestorHouseTrackRecordInfo trackRecordInfo = new InvestorHouseTrackRecordInfo();
		try {
			trackRecordInfo.setCreateTime(FDCSQLFacadeFactory.getRemoteInstance()
					.getServerTime());
			trackRecordInfo.setTrackDate(FDCSQLFacadeFactory.getRemoteInstance()
					.getServerTime());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		this.pkCreateTime.setEnabled(false);
		return trackRecordInfo;
	}

	public static void showEditUI(IUIObject ui,String id) throws EASBizException, BOSException
	{
		UIContext uiContext = new UIContext(ui);
		uiContext.put("id",id);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(InvestorTrackRecordEditUI.class.getName(), uiContext, null, STATUS_ADDNEW);
		uiWindow.show();
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return InvestorHouseTrackRecordFactory.getRemoteInstance();
	}

}