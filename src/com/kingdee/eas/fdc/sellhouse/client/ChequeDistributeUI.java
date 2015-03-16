/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.ChequeCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ChequeDistributeUI extends AbstractChequeDistributeUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChequeDistributeUI.class);
    private static final String IDS = "ids";
    
    private  String[] updateIds=null;//待分配的票据ID
    /**
     * output class constructor
     */
    public ChequeDistributeUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
        super.onLoad();
        updateIds=(String[]) this.getUIContext().get(IDS);
        EntityViewInfo view = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.getSetByArray(updateIds), CompareType.INCLUDE));
        view.setFilter(filter);
        ChequeCollection coll = ChequeFactory.getRemoteInstance().getChequeCollection(view);
        
        this.f7NewKeeper.setRequired(true);
    	this.f7NewKeepOrgUnit.setRequired(true);
    	this.txtToDistributeCheques.setEditable(false);
    	
    	StringBuffer sb = new StringBuffer();
    	for(int i=0; i<coll.size(); i++){
    		if(i != 0){
    			sb.append("，");
    		}
    		ChequeInfo cheque = coll.get(i);
    		sb.append(cheque.getNumber());
    	}
    	this.txtToDistributeCheques.setText(sb.toString());
    	
    	this.actionAddNew.setVisible(false);
    	this.actionEdit.setVisible(false);
    	this.actionSave.setVisible(false);
    	this.actionSubmit.setVisible(false);
    	this.actionCopy.setVisible(false);
    	this.actionRemove.setVisible(false);
    	this.actionFirst.setVisible(false);
    	this.actionPre.setVisible(false);
    	this.actionNext.setVisible(false);
    	this.actionLast.setVisible(false);
//    	this.actionPrint.setVisible(false);
//    	this.actionPrintPreview.setVisible(false);
    	this.actionCancel.setVisible(false);
    	this.actionCancelCancel.setVisible(false);
    }
    
	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}
	
	protected void btnYes_actionPerformed(ActionEvent e) throws Exception {
		super.btnYes_actionPerformed(e);
		
		Object objUser = this.f7NewKeeper.getValue();
		Object objOrg = this.f7NewKeepOrgUnit.getValue();
		if(objUser == null){
			MsgBox.showInfo(this, "新保管人不能为空");
			return;
		}
		if(objOrg == null){
			MsgBox.showInfo(this, "新保管组织不能为空");
			return;
		}
		
		if (objUser instanceof UserInfo && objOrg instanceof OrgUnitInfo) {
			UserInfo user = (UserInfo) objUser;
			OrgUnitInfo org = (OrgUnitInfo) objOrg;
			ChequeFactory.getRemoteInstance().distribute(updateIds, org.getId().toString(), user.getId().toString());
			MsgBox.showInfo(this, "分配成功!");//分配成功!
			destroyWindow();
		}
	}
	
	protected void btnNo_actionPerformed(ActionEvent e) throws Exception {
		super.btnNo_actionPerformed(e);
		destroyWindow();
	}
	
	protected void checkIsOUSealUp() throws Exception {
	}
	
    /**
     * 根据id显示窗体
     */
    public static boolean showDialogWindows(IUIObject ui, String[] ids) throws UIException {
        UIContext uiContext = new UIContext(ui);
        if(FDCHelper.isEmpty(ids)){
            SysUtil.abort();
        }
        
        uiContext.put(IDS, ids);
        // 创建UI对象并显示
        IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ChequeDistributeUI.class.getName(),uiContext,null,"ADDNEW");
        uiWindow.show();
        
        return true;
    }
}