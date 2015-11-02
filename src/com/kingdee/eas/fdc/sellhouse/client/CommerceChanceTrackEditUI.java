/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.RegionInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.ChannelTypeInfo;
import com.kingdee.eas.fdc.market.client.ChannelTypeListUI;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum;
import com.kingdee.eas.fdc.sellhouse.InteractionTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TrackEventEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CommerceChanceTrackEditUI extends AbstractCommerceChanceTrackEditUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1518623663454304497L;

	private static final Logger logger = CoreUIObject.getLogger(CommerceChanceTrackEditUI.class);
    
    protected UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
    
    /**
     * output class constructor
     */
    public CommerceChanceTrackEditUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	initControl();
    	initCommerceChanceStage();
//    	initSaleMans();
    	
    	FilterInfo filterInfoLevel = SHEHelper.getDefaultFilterForTree();
		filterInfoLevel.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		filterInfoLevel.getFilterItems().add(
				new FilterItemInfo("type.id",
						CRMConstants.COMMERCECHANCE_LEVEL_ID,
						CompareType.EQUALS));
		if (this.editData.getSellProject() != null) {
			filterInfoLevel.getFilterItems().add(
					new FilterItemInfo("sellProject.id", this.editData
							.getSellProject().getId().toString(),
							CompareType.EQUALS));
		} else {
			if (this.getUIContext().get("sellProject") != null) {
				SellProjectInfo sell = (SellProjectInfo) this.getUIContext()
						.get("sellProject");
				filterInfoLevel.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sell.getId()
								.toString(), CompareType.EQUALS));
			}
		}
		filterInfoLevel.getFilterItems().add(
				new FilterItemInfo("sellProject.id", null, CompareType.EQUALS));
		filterInfoLevel.setMaskString("#0 and #1 and #2 and (#3 or #4)");
		
		String queryInfoLevel = "com.kingdee.eas.fdc.sellhouse.app.CommerceAssistantForSHEQuery";
		SHEHelper.initF7(this.prmtCommerceLevel, queryInfoLevel, filterInfoLevel);
		
		this.pkTrackDate.setTimeEnabled(true);
		this.txtTrackContent.setMaxLength(500);
		
		this.actionAddNew.setVisible(false);
		this.prmtSaleMan.setEnabled(false);
    }
	private void initSaleMans() throws EASBizException, BOSException {
		if (this.getUIContext().get("sellProject") != null) {
			SellProjectInfo  info = (SellProjectInfo)this.getUIContext().get("sellProject");
			EntityViewInfo view= NewCommerceHelper.getPermitSalemanView(info,false);
			this.prmtSaleMan.setEntityViewInfo(view);	
		}
	}

	private SellProjectInfo getParentSellProject(SellProjectInfo sellProject){
		if(sellProject==null) return null;
		if(sellProject.getParent()!=null){
			return getParentSellProject(sellProject.getParent());
		}else{
			return sellProject;
		}
	}
    public void initCommerceChanceStage() throws Exception{
		// 商机阶段
    	FilterInfo filterInfoState = SHEHelper.getDefaultFilterForTree();
		filterInfoState.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE,
						CompareType.EQUALS));
		filterInfoState.getFilterItems().add(
				new FilterItemInfo("type.id", CRMConstants.HOPE_STAGE_ID,
						CompareType.EQUALS));
//		if(this.orgUnit!=null){
//			filterInfoState.getFilterItems().add(
//					new FilterItemInfo("orgUnit.id",this.orgUnit.getId().toString(),
//							CompareType.EQUALS));
//			filterInfoState.getFilterItems().add(
//					new FilterItemInfo("orgUnit.id",null,
//							CompareType.EQUALS));
//		}
		if (this.editData.getSellProject() != null) {
			filterInfoState.getFilterItems().add(
					new FilterItemInfo("sellProject.id", this.editData
							.getSellProject().getId().toString(),
							CompareType.EQUALS));
		} else {
			if (this.getUIContext().get("sellProject") != null) {
				SellProjectInfo sell = (SellProjectInfo) this.getUIContext()
						.get("sellProject");
				filterInfoState.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sell.getId()
								.toString(), CompareType.EQUALS));
			}
		}
		
		filterInfoState.getFilterItems().add(
				new FilterItemInfo("sellProject.id", null, CompareType.EQUALS));
		String filterStr = "'"+CRMConstants.COMMERCECHANCE_STAGE_PAIHAO+"'" +
		","+"'"+CRMConstants.COMMERCECHANCE_STAGE_BOOKING+"'" +
				","+"'"+CRMConstants.COMMERCECHANCE_STAGE_PURCHASE+"'" +
						","+"'"+CRMConstants.COMMERCECHANCE_STAGE_SIGN+"'" +
								","+"'"+CRMConstants.COMMERCECHANCE_STAGE_QUITROOM+"'" +
										","+"'"+CRMConstants.COMMERCECHANCE_STAGE_CHANGENAME+"'";
		filterInfoState.getFilterItems().add(
				new FilterItemInfo("id", filterStr, CompareType.NOTINNER));
		
		filterInfoState.setMaskString("#0 and #1 and  #2 and (#3 or #4) and #5 ");
		
		String queryInfoState = "com.kingdee.eas.fdc.sellhouse.app.CommerceAssistantForStageQuery";
		SHEHelper.initF7(this.prmtCommerceChanceStage, queryInfoState,
				filterInfoState);
		//wyh 设置商机与商机阶段
//		if (this.getUIContext().get("commerceChance") != null) {
//			CommerceChanceInfo ccni = (CommerceChanceInfo)this.getUIContext().get("commerceChance");
//			this.prmtCommerceChance.setValue(ccni);
//		}
//		if (this.getUIContext().get("commerceChanceStage") != null) {
//			this.prmtCommerceChanceStage.setValue(this.getUIContext().get("commerceChanceStage"));
//		}
		this.prmtCommerceChance.setEnabled(false);
		
		//渠道分类
		EntityViewInfo evi= new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("statrusing",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("CU.id",SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()));
		evi.setFilter(filter);
		
		HashMap ctx=new HashMap();
		ctx.put("EntityViewInfo", evi);
		ctx.put("EnableMultiSelection", new Boolean(false));
		ctx.put("HasCUDefaultFilter", new Boolean(true));
//		ctx.put("bizUIOwner", SwingUtilities.getWindowAncestor((Component)this));
		
//		ChannelTypeListUI ctlistUI=new ChannelTypeListUI();
//		ctlistUI.setF7Use(true, ctx);
//		ctlistUI.getUIToolBar().setVisible(false);
//		ctlistUI.getUIMenuBar().setVisible(false);
//		if((Window)SwingUtilities.getWindowAncestor(ctlistUI)!=null){
//			((Window)SwingUtilities.getWindowAncestor(ctlistUI)).setAlwaysOnTop(true);
//		}
//		this.prmtClassify.setSelector(ctlistUI);
		this.prmtClassify.setQueryInfo("com.kingdee.eas.fdc.market.app.ChannelTypeQuery");	
		this.prmtClassify.setDisplayFormat("$name$");		
        this.prmtClassify.setEditFormat("$number$");		
	    this.prmtClassify.setCommitFormat("$number$");
	    this.prmtClassify.setEntityViewInfo(evi);
    }
    
    public void loadFields() {
    	detachListeners();
    	super.loadFields();
    	
    	CommerceChanceTrackInfo info = (CommerceChanceTrackInfo) this.editData;
    	if(OprtState.ADDNEW.equals(this.getOprtState())){
    		this.prmtSellProject.setValue(this.getUIContext().get("sellProject"));
        	this.pkTrackDate.setValue(info.getTrackDate());
        	this.prmtSaleMan.setValue(user);
    	} else {
    		if(this.getUIContext().get("sellProject")!=null){
    			this.prmtSellProject.setValue(this.getUIContext().get("sellProject"));
    		}else{
    			this.prmtSellProject.setValue(this.editData.getSellProject());
    		}
    		
    		this.prmtCommerceChance.setValue(info.getCommerceChance());
    		this.pkTrackDate.setValue(info.getTrackDate());
    		this.comboInteractionType.setSelectedItem(info.getInteractionType());
    		this.comboTrackEvent.setSelectedItem(info.getTrackEvent());
    		this.txtTrackContent.setText(info.getTrackContent());
    		this.prmtCommerceLevel.setValue(info.getCommerceLevel());
    	}
    	try {
			loadCommerceChanceF7();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		attachListeners();
    }
    private void loadCommerceChanceF7() throws EASBizException, BOSException{
    	SellProjectInfo sellproject=(SellProjectInfo) this.getUIContext().get("sellProject");
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(sellproject!=null){
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellproject.getId()));
		}
		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("saleMan.id", NewCommerceHelper.getPermitSaleManIdSql(sellproject,user),CompareType.INNER));
		filter.getFilterItems().add(new FilterItemInfo("status", CommerceChangeNewStatusEnum.ACTIVE_VALUE));
		view.setFilter(filter);
		this.prmtCommerceChance.setEntityViewInfo(view);
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    protected IObjectValue createNewData() {
    	CommerceChanceTrackInfo info = new CommerceChanceTrackInfo();
    	info.setOrgUnit(SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo());
    	try {
			info.setBizDate(FDCCommonServerHelper.getServerTimeStamp());
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		SellProjectInfo sellproject=(SellProjectInfo) this.getUIContext().get("sellProject");
		/**
		 * 添加商机 by renliang at 2011-7-11
		 */
		if(this.getUIContext().get("SelectCommerceChance")!=null){
			CommerceChanceInfo chance = (CommerceChanceInfo)this.getUIContext().get("SelectCommerceChance");
			info.setCommerceChance(chance);
			CommerceChanceTrackInfo lastTrackInfo=null;
			try {
				lastTrackInfo = SHEManageHelper.getLastCommerceChanceTrack(null,chance);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if(lastTrackInfo!=null){
				info.setClassify(lastTrackInfo.getClassify());
				info.setCommerceLevel(lastTrackInfo.getCommerceLevel());
				info.setCommerceChanceStage(lastTrackInfo.getCommerceChanceStage());
			}
		}

		if(this.getUIContext().get("HashMap") != null){
			HashMap hp = (HashMap)this.getUIContext().get("HashMap");
			//最近一次的商机跟进级别的值
			if(hp.get("lastTrackChanceLevel") != null){
				info.setCommerceLevel((CommerceChanceAssistantInfo)hp.get("lastTrackChanceLevel"));
			}
			//最近一次的媒体渠道
			if(hp.get("lastClassify") != null){
				info.setClassify((ChannelTypeInfo)hp.get("lastClassify"));
			}
		}
		info.setSellProject(sellproject);
		Date date = new Date();
		info.setTrackDate(date);
		info.setSaleMan(user);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
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
    	this.actionPrint.setVisible(true);
    	this.actionPrint.setEnabled(true);
    	this.actionPrintPreview.setVisible(true);
    	this.actionPrintPreview.setEnabled(true);
    	if(OprtState.ADDNEW.equals(this.getOprtState())){
    		this.comboInteractionType.setSelectedIndex(0);
    	}
    	this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		
		this.txtTrackContent.setRequired(true);
    }
    
    
    protected String prepareNumberForAddnew (ICodingRuleManager iCodingRuleManager,FDCBillInfo info,String orgId,String costerId,String bindingProperty)throws Exception{

		String number = null;
		FilterInfo filter = null;
		int i=0;
		do {
			//如果编码重复重新取编码
			if(bindingProperty!=null){
				number = iCodingRuleManager.getNumber(editData, orgId, bindingProperty, "");
			}else{
				number = iCodingRuleManager.getNumber(editData, orgId);
			}
			
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("number", number));
			filter.getFilterItems()
					.add(new FilterItemInfo("orgUnit.id", costerId));
			if (info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),
								CompareType.NOTEQUALS));
			}
			i++;
		}while (((IFDCBill)getBizInterface()).exists(filter) && i<1000);
		
		return number;
	}
    
    public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("sellProject.*"));
        sic.add(new SelectorItemInfo("commerceChance.*"));
        sic.add(new SelectorItemInfo("trackDate"));
        sic.add(new SelectorItemInfo("interactionType"));
        sic.add(new SelectorItemInfo("trackEvent"));
        sic.add(new SelectorItemInfo("saleMan.*"));
        sic.add(new SelectorItemInfo("trackContent"));
        sic.add(new SelectorItemInfo("commerceChanceStage.*"));
        sic.add(new SelectorItemInfo("classify.*"));
        sic.add(new SelectorItemInfo("commerceLevel.*"));
        sic.add("CU.*");
		return sic;
    }
    
     /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	verifySave();
    	//当用户新增时候统一进行保存 wyh
    	if(this.getUIContext().get("HashMap") != null){
			HashMap hp = (HashMap)this.getUIContext().get("HashMap");
			String key = "track_"+hp.get("track_num");
			hp.put(key, fillEditData());
			hp.put("lastTrackChanceLevel", (CommerceChanceAssistantInfo)this.prmtCommerceLevel.getValue());
			hp.put("lastClassify", (ChannelTypeInfo)this.prmtClassify.getValue());
			hp.put("lastCommerceChanceStage", (CommerceChanceAssistantInfo)this.prmtCommerceChanceStage.getValue());
			hp.put("trackDate", this.pkTrackDate.getValue());
			disposeUIWindow();
			return;
		}else{
			KDBizPromptBox prmtLevel=(KDBizPromptBox)this.getUIContext().get("prmtLevel");
			if(prmtLevel!=null){
				prmtLevel.setValue(this.prmtCommerceLevel.getValue());
			}
			KDBizPromptBox prmtClassify=(KDBizPromptBox)this.getUIContext().get("prmtClassify");
			if(prmtLevel!=null){
				prmtClassify.setValue(this.prmtClassify.getValue());
			}
			KDBizPromptBox prmtCommerceChanceStage=(KDBizPromptBox)this.getUIContext().get("prmtCommerceChanceStage");
			if(prmtLevel!=null){
				prmtCommerceChanceStage.setValue(this.prmtCommerceChanceStage.getValue());
			}
		}
    	super.actionSave_actionPerformed(e);
    	this.actionAttachment.setVisible(false);
    	
    	if(editData.getCommerceChance()!=null){
    		SelectorItemCollection sic = new SelectorItemCollection();
            sic.add(new SelectorItemInfo("newLevel"));
            sic.add(new SelectorItemInfo("classify"));
            sic.add(new SelectorItemInfo("commerceChanceStage"));
            sic.add(new SelectorItemInfo("trackDate"));
            sic.add(new SelectorItemInfo("trackContent"));
            SelectorItemCollection updatesic = new SelectorItemCollection();
            updatesic.add("classify.*");
    		CommerceChanceTrackInfo	lastTrackInfo = SHEManageHelper.getLastCommerceChanceTrack(null,editData.getCommerceChance());
    		if(lastTrackInfo!=null){
    			String filterStr = "'"+CRMConstants.COMMERCECHANCE_STAGE_PAIHAO+"'" +
				","+"'"+CRMConstants.COMMERCECHANCE_STAGE_BOOKING+"'" +
						","+"'"+CRMConstants.COMMERCECHANCE_STAGE_PURCHASE+"'" +
								","+"'"+CRMConstants.COMMERCECHANCE_STAGE_SIGN+"'" +
										","+"'"+CRMConstants.COMMERCECHANCE_STAGE_QUITROOM+"'" +
												","+"'"+CRMConstants.COMMERCECHANCE_STAGE_CHANGENAME+"'";
    			
    			editData.getCommerceChance().setNewLevel(lastTrackInfo.getCommerceLevel());
    			editData.getCommerceChance().setClassify(lastTrackInfo.getClassify());
    			if(!(editData.getCommerceChance().getCommerceChanceStage()!=null&&filterStr.indexOf(editData.getCommerceChance().getCommerceChanceStage().getId().toString())>0)){
    				editData.getCommerceChance().setCommerceChanceStage(lastTrackInfo.getCommerceChanceStage());
    			}
    			editData.getCommerceChance().setTrackDate(lastTrackInfo.getTrackDate());
    			editData.getCommerceChance().setTrackContent(lastTrackInfo.getTrackContent());
    			CommerceChanceFactory.getRemoteInstance().updatePartial(editData.getCommerceChance(), sic);
    			
    			
    			
    			CommerceChanceTrackCollection col=CommerceChanceTrackFactory.getRemoteInstance().getCommerceChanceTrackCollection("select * from where commerceChance.id='"+editData.getCommerceChance().getId().toString()+"' and commerceChanceStage.id in("+filterStr+") and classify.id is null");
    			for(int i=0;i<col.size();i++){
    				col.get(i).setClassify(lastTrackInfo.getClassify());
    				CommerceChanceTrackFactory.getRemoteInstance().updatePartial(col.get(i), updatesic);
    			}
    			
    			if(editData.getCommerceChance().getCustomer()!=null){
    				sic = new SelectorItemCollection();
                    sic.add(new SelectorItemInfo("trackDate"));
                    sic.add(new SelectorItemInfo("commerceLevel"));
                    editData.getCommerceChance().getCustomer().setTrackDate(lastTrackInfo.getTrackDate());
                    editData.getCommerceChance().getCustomer().setCommerceLevel(lastTrackInfo.getCommerceLevel());
                    SHECustomerFactory.getRemoteInstance().updatePartial(editData.getCommerceChance().getCustomer(), sic);
    			}
    		}
    	}
    }
    
    public void verifySave() throws BOSException, EASBizException {
    	SellProjectInfo sellProject = (SellProjectInfo)this.prmtSellProject.getValue();
    	if(sellProject == null){
    		MsgBox.showWarning(this,"项目名称不能为空！");
			this.abort();
    	}
    	CommerceChanceInfo info = (CommerceChanceInfo) this.prmtCommerceChance.getValue();
    	if( info == null){
    		MsgBox.showWarning(this,"客户商机不能为空！");
			this.abort();
    	}
    	String str_number = this.txtNumber.getText();
    	if("".equals(str_number) || str_number == null){
    		MsgBox.showWarning(this,"跟进编码不能为空！");
			this.abort();
    	}
    	
    	Date date = (Date)this.pkTrackDate.getValue();
    	if(date == null){
    		MsgBox.showWarning(this,"跟进日期不能为空！");
			this.abort();
    	}
    	InteractionTypeEnum interactionType = (InteractionTypeEnum)this.comboInteractionType.getSelectedItem();
    	if(interactionType == null){
    		MsgBox.showWarning(this,"交互方式不能为空！");
			this.abort();
    	}
    	TrackEventEnum trackEvent = (TrackEventEnum)this.comboTrackEvent.getSelectedItem();
    	UserInfo user = (UserInfo)this.prmtSaleMan.getValue();
    	if(user == null){
    		MsgBox.showWarning(this,"置业顾问不能为空！");
			this.abort();
    	}
    	if(this.prmtClassify.getValue() == null){
    		FDCMsgBox.showWarning(this,"媒体渠道不能为空！");
			this.abort();
    	}
    	FDCClientVerifyHelper.verifyEmpty(this, this.prmtCommerceLevel);
    	FDCClientVerifyHelper.verifyEmpty(this, this.txtTrackContent);
    	if(this.txtTrackContent.getText().length()<5){
    		FDCMsgBox.showWarning(this,"跟进内容至少5个字！");
			this.abort();
    	}
	}
    
	//填充数据 wyh
	public CommerceChanceTrackInfo fillEditData(){
		this.editData.setSellProject((SellProjectInfo)this.prmtSellProject.getValue());
		this.editData.setNumber(this.txtNumber.getText());
		this.editData.setClassify((ChannelTypeInfo)this.prmtClassify.getValue());
		this.editData.setInteractionType((InteractionTypeEnum)this.comboInteractionType.getSelectedItem());
		this.editData.setTrackContent(this.txtTrackContent.getText());
		this.editData.setTrackEvent((TrackEventEnum)this.comboTrackEvent.getSelectedItem());
		this.editData.setCommerceChance((CommerceChanceInfo)this.prmtCommerceChance.getValue());
		this.editData.setCommerceChanceStage((CommerceChanceAssistantInfo)this.prmtCommerceChanceStage.getValue());
		this.editData.setCommerceLevel((CommerceChanceAssistantInfo)this.prmtCommerceLevel.getValue());
		this.editData.setTrackDate((Date)this.pkTrackDate.getValue());
		return (CommerceChanceTrackInfo)editData;
	}

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
    	if (this.editData == null || editData.getId() == null) {
			MsgBox.showWarning(this, "请提交后再打印!");
			SysUtil.abort();
		}
		String receId = this.editData.getId().toString();
		if (receId != null && receId.length() > 0) {
			List idList = new ArrayList();
			idList.add(receId);
			CommerceChanceTrackDataProvider data = new CommerceChanceTrackDataProvider(
					idList,
					new MetaDataPK(
							"com.kingdee.eas.fdc.sellhouse.app.CommerceChanceTrackForPrintQuery"));
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.print("/bim/fdc/sellhouse/customerManage", data,
					javax.swing.SwingUtilities.getWindowAncestor(this));
			super.actionPrint_actionPerformed(e);
		}
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
    	if (this.editData == null || editData.getId() == null) {
			MsgBox.showWarning(this, "请提交后再打印!");
			SysUtil.abort();
		}
		String receId = this.editData.getId().toString();
		if (receId != null && receId.length() > 0) {
			List idList = new ArrayList();
			idList.add(receId);
			CommerceChanceTrackDataProvider data = new CommerceChanceTrackDataProvider(
					idList,
					new MetaDataPK(
							"com.kingdee.eas.fdc.sellhouse.app.CommerceChanceTrackForPrintQuery"));
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.printPreview("/bim/fdc/sellhouse/customerManage", data,
					javax.swing.SwingUtilities.getWindowAncestor(this));
			super.actionPrintPreview_actionPerformed(e);
		}
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
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
        super.actionEdit_actionPerformed(e);
        this.actionAttachment.setVisible(false);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddLine_actionPerformed(e);
    }

    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionInsertLine_actionPerformed(e);
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoveLine_actionPerformed(e);
    }

    /**
     * output actionCreateFrom_actionPerformed
     */
    public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateFrom_actionPerformed(e);
    }

    /**
     * output actionCopyFrom_actionPerformed
     */
    public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyFrom_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionViewSubmitProccess_actionPerformed
     */
    public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSubmitProccess_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionSendingMessage_actionPerformed
     */
    public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendingMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actionViewSignature_actionPerformed
     */
    public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSignature_actionPerformed(e);
    }

    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }

    /**
     * output actionAttamentCtrl_actionPerformed
     */
    public void actionAttamentCtrl_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttamentCtrl_actionPerformed(e);
    }

	protected void attachListeners() {
		addDataChangeListener(comboInteractionType);
	}

	protected void detachListeners() {
		removeDataChangeListener(comboInteractionType);
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

	protected void comboInteractionType_itemStateChanged(ItemEvent e) throws Exception {
		if(InteractionTypeEnum.INTERVIEW.equals(this.comboInteractionType.getSelectedItem())){
			this.prmtCommerceChanceStage.setValue(CommerceChanceAssistantFactory.getRemoteInstance().getCommerceChanceAssistantInfo("select * from where name='来访'"));
		}else{
			FilterInfo filter = new FilterInfo();
			if(this.prmtCommerceChance.getValue()!=null){
				filter.getFilterItems().add(new FilterItemInfo("commerceChance.id",((CommerceChanceInfo)this.prmtCommerceChance.getValue()).getId().toString()));
			}
			filter.getFilterItems().add(new FilterItemInfo("interactionType",InteractionTypeEnum.INTERVIEW_VALUE));
			
			if(CommerceChanceTrackFactory.getRemoteInstance().exists(filter)){
				this.prmtCommerceChanceStage.setValue(CommerceChanceAssistantFactory.getRemoteInstance().getCommerceChanceAssistantInfo("select * from where name='来访'"));
			}else{
				this.prmtCommerceChanceStage.setValue(CommerceChanceAssistantFactory.getRemoteInstance().getCommerceChanceAssistantInfo("select * from where name='来电'"));
			}
		}
	}
}