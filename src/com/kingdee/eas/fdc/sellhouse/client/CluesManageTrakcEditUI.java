/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo;
import com.kingdee.eas.fdc.sellhouse.InteractionTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class CluesManageTrakcEditUI extends AbstractCluesManageTrakcEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(CluesManageTrakcEditUI.class);
    
    protected UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
    
    /**
     * output class constructor
     */
    public CluesManageTrakcEditUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	initControl();
    }
    
    public void loadFields() {
    	super.loadFields();
    	CommerceChanceTrackInfo info = (CommerceChanceTrackInfo) this.editData;
    	
    	if(this.getUIContext().get("commerceChance") == null){
 			getUIContext().put("commerceChance", editData.getCommerceChance());
 		}
 		if(this.getUIContext().get("sellProjectInfo")==null){
 			getUIContext().put("sellProjectInfo", editData.getSellProject());
 		}
 		
    	if(OprtState.ADDNEW.equals(this.getOprtState())){
    		this.prmtSellProject.setValue(this.getUIContext().get("sellProject"));
        	this.pkTrackDate.setValue(info.getTrackDate());
        	this.prmtSaleMan.setValue(user);
        	this.comboInteractionType.setSelectedItem(InteractionTypeEnum.TELEPHONE);
    	} else {
//    		this.prmtSellProject.setValue(this.getUIContext().get("sellProject"));
//    		this.pkTrackDate.setValue(info.getTrackDate());
//    		this.comboInteractionType.setSelectedItem(info.getInteractionType());
//    		this.txtTrackContent.setText(info.getTrackContent());
    	}
    }
    
    protected IObjectValue createNewData() {
    	CommerceChanceTrackInfo info = new CommerceChanceTrackInfo();
    	info.setOrgUnit(SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo());
    	try {
			info.setBizDate(FDCCommonServerHelper.getServerTimeStamp());
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		CluesManageInfo cluesManageInfo = (CluesManageInfo) this.getUIContext().get("cluesCustomer");
		info.setClues(cluesManageInfo);
		
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		info.setSellProject(sellProject);
		
		CommerceChanceInfo commerceChance = (CommerceChanceInfo) this.getUIContext().get("commerceChance");
		info.setCommerceChance(commerceChance);
		Date date = new Date();
		info.setTrackDate(date);
		info.setSaleMan(user);
    	return info;
    }
    
    protected void initControl(){
    	this.actionSubmit.setVisible(false);
    	this.actionCopy.setVisible(false);
    	this.actionRemove.setEnabled(true);
    	this.actionAttachment.setVisible(false);
    	this.actionFirst.setVisible(false);
    	this.actionPre.setVisible(false);
    	this.actionNext.setVisible(false);
    	this.actionLast.setVisible(false);
    	this.actionTraceDown.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.actionWorkFlowG.setVisible(false);
    	this.actionCreateFrom.setVisible(false);
    	this.actionAddLine.setVisible(false);
    	this.actionRemoveLine.setVisible(false);
    	this.actionInsertLine.setVisible(false);
    	this.actionAuditResult.setVisible(false);
    	this.actionMultiapprove.setVisible(false);
    	this.actionNextPerson.setVisible(false);
    	this.actionCalculator.setVisible(false);
    	this.actionAudit.setVisible(false);
    	this.actionAttachment.setVisible(false);
    	if(OprtState.ADDNEW.equals(this.getOprtState())){
    		this.comboInteractionType.setSelectedIndex(0);
    	}
    	
		try {
			EntityViewInfo view = NewCommerceHelper.getPermitSalemanView(editData.getSellProject(),null);
			this.prmtSaleMan.setEntityViewInfo(view);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
    }
    
    public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectorItemCollection = super.getSelectors();
		selectorItemCollection.add("*");
		selectorItemCollection.add("sellProject.*");
		selectorItemCollection.add("saleMan.*");
		selectorItemCollection.add("clues.*");
		return selectorItemCollection;
    }
    
	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CommerceChanceTrackFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected void showSaveSuccess()
    {
        setMessageText("¸ú½ø" + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Save_OK"));
        setNextMessageText("¸ú½ø" + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
        setShowMessagePolicy(0);
        setIsShowTextOnly(false);
        showMessage();
    }
}