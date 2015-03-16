package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.UserType;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MarketingUnitEditUI extends AbstractMarketingUnitEditUI
{
	private static final Logger logger = CoreUIObject.getLogger(MarketingUnitEditUI.class);
	

	public MarketingUnitEditUI() throws Exception
	{
		super();
	}


	protected IObjectValue createNewData() {
		this.tblDutyPerson.removeRows();
		
		MarketingUnitInfo newMuInfo = new MarketingUnitInfo();
		OrgStructureInfo orgStrInfo = (OrgStructureInfo)this.getUIContext().get("OrgStructureInfo");
		if(orgStrInfo!=null) newMuInfo.setOrgUnit(orgStrInfo.getUnit());
		newMuInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		newMuInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));

		MarketingUnitInfo parentMuInfo = (MarketingUnitInfo)this.getUIContext().get("MarketingUnitInfo");
		newMuInfo.setParent(parentMuInfo);
		if(parentMuInfo!=null && orgStrInfo==null) {
			newMuInfo.setOrgUnit(parentMuInfo.getOrgUnit());
		}
		
		return newMuInfo;
	}


	protected ICoreBase getBizInterface() throws Exception {
		return MarketingUnitFactory.getRemoteInstance();
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("isLeaf"));
		return sic;
	}
	
	
	public void onLoad() throws Exception {	
		this.tblSellroject.checkParsed();
		this.tblDutyPerson.checkParsed();
		this.tblMember.checkParsed();
		
		super.onLoad();
		
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);		
		
		MarketingUnitInfo parentMuInfo = this.editData.getParent();
		if(this.getOprtState().equals(OprtState.ADDNEW))
			parentMuInfo = (MarketingUnitInfo)this.getUIContext().get("MarketingUnitInfo");
		if(parentMuInfo!=null) {
			this.editData.setParent(parentMuInfo);
			this.prmtParentUnit.setValue(parentMuInfo);

			if(!this.getOprtState().equals(OprtState.VIEW)) {
				if(!parentMuInfo.isIsSellFunction())  	 this.chkIsSHE.setEnabled(false);  
				if(!parentMuInfo.isIsTenancyFunction())  this.chkIsTEN.setEnabled(false);  
				if(!parentMuInfo.isIsWuYeFunction())  	 this.chkIsPPM.setEnabled(false);	 
				if(!parentMuInfo.isIsMarketFunction())   this.chkIsMAR.setEnabled(false);   
				if(!parentMuInfo.isIsProjectFunction())  this.chkIsPRO.setEnabled(false);   
				if(!parentMuInfo.isIsInsideFunction())   this.chkIsINS.setEnabled(false);   
			}
		}
		
		if(!this.getOprtState().equals(OprtState.VIEW)) {
			if(!this.editData.isIsSellFunction())   this.tblMember.getColumn("isSHE").getStyleAttributes().setLocked(true);
			if(!this.editData.isIsTenancyFunction())   this.tblMember.getColumn("isTEN").getStyleAttributes().setLocked(true);
			if(!this.editData.isIsWuYeFunction())   this.tblMember.getColumn("isPPM").getStyleAttributes().setLocked(true);
			if(!this.editData.isIsMarketFunction())   this.tblMember.getColumn("isPRO").getStyleAttributes().setLocked(true);
			if(!this.editData.isIsProjectFunction())   this.tblMember.getColumn("isMAR").getStyleAttributes().setLocked(true);
			if(!this.editData.isIsInsideFunction())   this.tblMember.getColumn("isINS").getStyleAttributes().setLocked(true);
		}
		
		this.tblSellroject.getStyleAttributes().setLocked(true);
		this.tblSellroject.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		this.tblDutyPerson.getColumn("user").getStyleAttributes().setLocked(true);
		this.tblDutyPerson.getColumn("accessionDate").setEditor(CommerceHelper.getKDDatePickerEditor());	
		this.tblDutyPerson.getColumn("dimissionDate").getStyleAttributes().setLocked(true);
		this.tblDutyPerson.getColumn("dimissionDate").setEditor(CommerceHelper.getKDDatePickerEditor());
		this.tblDutyPerson.getColumn("takePercentage").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
		this.tblDutyPerson.getColumn("accessionDate").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		
		this.tblMember.getColumn("member").getStyleAttributes().setLocked(true);		
		this.tblMember.getColumn("accessionDate").setEditor(CommerceHelper.getKDDatePickerEditor());
		this.tblMember.getColumn("dimissionDate").getStyleAttributes().setLocked(true);
		this.tblMember.getColumn("dimissionDate").setEditor(CommerceHelper.getKDDatePickerEditor());
		this.tblMember.getColumn("accessionDate").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		
		if(this.getOprtState().equals(OprtState.VIEW)) {
			this.btnAddProject.setEnabled(false);
			this.btnDelSellProject.setEnabled(false);
			this.btnAddDutyPerson.setEnabled(false);
			this.btnDelDutyPerson.setEnabled(false);
			this.btnAddMember.setEnabled(false);
			this.btnDelMember.setEnabled(false);
		}
	}
	
	
	public void loadFields() {
		super.loadFields();
		
		this.tblDutyPerson.removeRows();
		for(int i=this.tblMember.getRowCount()-1;i>=0;i--) {
			IRow row = this.tblMember.getRow(i);
			MarketingUnitMemberInfo muMemberInfo = (MarketingUnitMemberInfo)row.getUserObject();
			if(muMemberInfo.isIsDuty()) {
				this.tblMember.removeRow(row.getRowIndex());
				this.editData.getMember().remove(muMemberInfo);
				
				IRow addRow = this.tblDutyPerson.addRow();
				addRow.setUserObject(muMemberInfo);
				addRow.getCell("user").setValue(muMemberInfo.getMember().getName());
				addRow.getCell("dutyPersonTitle").setValue(muMemberInfo.getDutyPersonTitle());
				addRow.getCell("accessionDate").setValue(muMemberInfo.getAccessionDate());
				addRow.getCell("dimissionDate").setValue(muMemberInfo.getDimissionDate());
				addRow.getCell("isUpdateMember").setValue(Boolean.valueOf(muMemberInfo.isIsUpdateMember()));
				addRow.getCell("isOperation").setValue(Boolean.valueOf(muMemberInfo.isIsOperation()));
				addRow.getCell("description").setValue(muMemberInfo.getDescription());
				addRow.getCell("isSharePercent").setValue(Boolean.valueOf(muMemberInfo.isIsSharePercent()));
				addRow.getCell("takePercentage").setValue(muMemberInfo.getTakePercentage());
			}
		}
		
	}
	
	
	
	protected void btnAddProject_actionPerformed(ActionEvent e)	throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
			
		boolean isShe = this.chkIsSHE.isSelected();
		boolean isTen = this.chkIsTEN.isSelected();
		boolean isPpm = this.chkIsPPM.isSelected();
		if(isShe || isTen || isPpm) {
			if(isShe) filter.getFilterItems().add(new FilterItemInfo("isForSHE",Boolean.valueOf(isShe)));
			if(isTen) filter.getFilterItems().add(new FilterItemInfo("isForTen",Boolean.valueOf(isTen)));
			if(isPpm) filter.getFilterItems().add(new FilterItemInfo("isForPPM",Boolean.valueOf(isPpm)));
		}		

		FilterInfo orgfilter = new FilterInfo();
		orgfilter.getFilterItems().add(new FilterItemInfo("orgUnit.id",this.editData.getOrgUnit().getId().toString()));
		FilterInfo shareFilter = new FilterInfo();  //项目共享给其他组织的 
		shareFilter.getFilterItems().add(new FilterItemInfo("orgUnitShareList.orgUnit.id",this.editData.getOrgUnit().getId().toString()));		
		orgfilter.mergeFilter(shareFilter, "OR");
		
		filter.mergeFilter(orgfilter, "AND");
		
		if(this.editData.getParent()!=null){
			String idSqlStr = "select FSellProjectID from T_TEN_MarketingUnitSellProject where FheadId = '"+this.editData.getParent().getId().toString()+"'";
			FilterInfo parentFilter = new FilterInfo();
			parentFilter.getFilterItems().add(new FilterItemInfo("id",idSqlStr,CompareType.INNER));
			filter.mergeFilter(parentFilter, "AND");
		}
		view.setFilter(filter);	
		KDCommonPromptDialog dlg = CommerceHelper.getANewCommonDialog(this,"com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery", true, view);		
		dlg.show();
		Object[] objects = (Object[])dlg.getData();
		if(objects!=null) {
			Set sellProIdSet = new HashSet();
			MarketingUnitSellProjectCollection  muSellProColl = this.editData.getSellProject();
			for(int i=0;i<muSellProColl.size();i++) {
				sellProIdSet.add(muSellProColl.get(i).getSellProject().getId().toString());
			}
			
			for(int i=0;i<objects.length;i++) {
				SellProjectInfo sellProInfo = (SellProjectInfo)objects[i];
				if(!sellProIdSet.contains(sellProInfo.getId().toString())) {
					MarketingUnitSellProjectInfo muSellProInfo = new MarketingUnitSellProjectInfo();
					muSellProInfo.setHead(this.editData);
					muSellProInfo.setSellProject(sellProInfo);
					this.editData.getSellProject().add(muSellProInfo);
					
					IRow addRow = this.tblSellroject.addRow();
					addRow.setUserObject(muSellProInfo);
					addRow.getCell("sellProject").setValue(sellProInfo);
					addRow.getCell("number").setValue(sellProInfo.getNumber());
					addRow.getCell("isForSHE").setValue(Boolean.valueOf(sellProInfo.isIsForSHE()));
					addRow.getCell("isForTEN").setValue(Boolean.valueOf(sellProInfo.isIsForTen()));
					addRow.getCell("isForPPM").setValue(Boolean.valueOf(sellProInfo.isIsForPPM()));
				}
			}
		}
	}	
	protected void btnDelSellProject_actionPerformed(ActionEvent e)	throws Exception {
		IRow delRow = KDTableUtil.getSelectedRow(this.tblSellroject);
		if(delRow!=null) {  
			this.tblSellroject.removeRow(delRow.getRowIndex());	
			this.editData.getSellProject().remove((MarketingUnitSellProjectInfo)delRow.getUserObject());
		}
	}
	
	protected void btnAddDutyPerson_actionPerformed(ActionEvent e)	throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isDelete", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("isLocked", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("isForbidden", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("type", new Integer(UserType.SYSTEM_VALUE), CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("type", new Integer(UserType.AUTHENTICATEADMIN_VALUE), CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("type", new Integer(70), CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("type",null, CompareType.ISNOT));
		view.setFilter(filter);		
		KDCommonPromptDialog dlg = CommerceHelper.getANewCommonDialog(this,"com.kingdee.eas.base.permission.app.F7UserQuery", true, view);		
		dlg.show();
		Object[] objects = (Object[])dlg.getData();
		if(objects!=null) {
			for(int i=0;i<objects.length;i++) {
				UserInfo userInfo = (UserInfo)objects[i];
				MarketingUnitMemberInfo muMemberInfo = new MarketingUnitMemberInfo();
				muMemberInfo.setHead(this.editData);
				muMemberInfo.setMember(userInfo);
				muMemberInfo.setIsDuty(true);
				muMemberInfo.setIsOperation(false);
				muMemberInfo.setIsUpdateMember(false);
				muMemberInfo.setIsSharePercent(true);
				muMemberInfo.setTakePercentage(new BigDecimal(100.00));
				
				IRow addRow = this.tblDutyPerson.addRow();
				addRow.setUserObject(muMemberInfo);
				addRow.getCell("user").setValue(userInfo.getName());
				addRow.getCell("dutyPersonTitle").setValue(muMemberInfo.getDutyPersonTitle());
				addRow.getCell("accessionDate").setValue(muMemberInfo.getAccessionDate());
				addRow.getCell("dimissionDate").setValue(muMemberInfo.getDimissionDate());
				addRow.getCell("isUpdateMember").setValue( Boolean.valueOf(muMemberInfo.isIsUpdateMember()));
				addRow.getCell("isOperation").setValue( Boolean.valueOf(muMemberInfo.isIsOperation()));
				addRow.getCell("description").setValue(muMemberInfo.getDescription());
				addRow.getCell("isSharePercent").setValue( Boolean.valueOf(muMemberInfo.isIsSharePercent()));
				addRow.getCell("takePercentage").setValue(muMemberInfo.getTakePercentage());
			}
			
		}
		
	}
	protected void btnDelDutyPerson_actionPerformed(ActionEvent e)	throws Exception {
		IRow delRow = KDTableUtil.getSelectedRow(this.tblDutyPerson);
		if(delRow!=null) {  
			this.tblDutyPerson.removeRow(delRow.getRowIndex());			
			this.editData.getMember().remove((MarketingUnitMemberInfo)delRow.getUserObject());
		}
	}
	
	protected void btnAddMember_actionPerformed(ActionEvent e) throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isDelete", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("isLocked", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("isForbidden", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("type", new Integer(UserType.SYSTEM_VALUE), CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("type", new Integer(UserType.AUTHENTICATEADMIN_VALUE), CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("type", new Integer(70), CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("type",null, CompareType.ISNOT));
		view.setFilter(filter);		
		KDCommonPromptDialog dlg = CommerceHelper.getANewCommonDialog(this,"com.kingdee.eas.base.permission.app.F7UserQuery", true, view);		
		dlg.show();
		Object[] objects = (Object[])dlg.getData();
		if(objects!=null) {
			for(int i=0;i<objects.length;i++) {
				UserInfo userInfo = (UserInfo)objects[i];
				MarketingUnitMemberInfo muMemberInfo = new MarketingUnitMemberInfo();
				muMemberInfo.setHead(this.editData);
				muMemberInfo.setMember(userInfo);
				muMemberInfo.setIsDuty(false);
				muMemberInfo.setIsOperation(false);
				muMemberInfo.setIsUpdateMember(false);
				muMemberInfo.setIsSharePercent(false);
				muMemberInfo.setTakePercentage(new BigDecimal(0.00));
				
				IRow addRow = this.tblMember.addRow();
				addRow.setUserObject(muMemberInfo);
				addRow.getCell("member").setValue(userInfo.getName());
				addRow.getCell("accessionDate").setValue(muMemberInfo.getAccessionDate());
				addRow.getCell("dimissionDate").setValue(muMemberInfo.getDimissionDate());
				addRow.getCell("isSHE").setValue( Boolean.valueOf(muMemberInfo.isIsSellFunction()));
				addRow.getCell("isTEN").setValue( Boolean.valueOf(muMemberInfo.isIsTenancyFunction()));
				addRow.getCell("isPPM").setValue( Boolean.valueOf(muMemberInfo.isIsWuYeFunction()));
				addRow.getCell("isMAR").setValue( Boolean.valueOf(muMemberInfo.isIsMarketFunction()));
				addRow.getCell("isPRO").setValue( Boolean.valueOf(muMemberInfo.isIsProjectFunction()));
				addRow.getCell("isINS").setValue( Boolean.valueOf(muMemberInfo.isIsInsideFunction()));
				
				this.editData.getMember().add(muMemberInfo);				
			}
		}
		
	}
	protected void btnDelMember_actionPerformed(ActionEvent e) throws Exception {
		IRow delRow = KDTableUtil.getSelectedRow(this.tblMember);
		if(delRow!=null) {  
			this.tblMember.removeRow(delRow.getRowIndex());	
			this.editData.getMember().remove((MarketingUnitMemberInfo)delRow.getUserObject());
		}
	}
	
	
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(MarketingUnitFactory.getRemoteInstance().exists("where parent.id = '"+this.editData.getId().toString()+"'")) {
			MsgBox.showInfo("非明细营销单元节点不能删除！");
			return;
		}
		
		super.actionRemove_actionPerformed(e);
	}
	
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		
		this.btnAddProject.setEnabled(true);
		this.btnDelSellProject.setEnabled(true);
		this.btnAddDutyPerson.setEnabled(true);
		this.btnDelDutyPerson.setEnabled(true);
		this.btnAddMember.setEnabled(true);
		this.btnDelMember.setEnabled(true);
	}
	
	
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		
		this.tblDutyPerson.getEditManager().stopEditing();
		this.tblMember.getEditManager().stopEditing();
		
		if(this.txtNumber.getText()==null || this.txtNumber.getText().length()==0) {
			MsgBox.showInfo("编码必须录入！");
			return;
		}
		if(this.txtName.getText()==null || this.txtName.getText().length()==0) {
			MsgBox.showInfo("名称必须录入！");
			return;
		}		
		
		if(this.editData.getSellProject().size()==0) {
			MsgBox.showInfo("项目信息的营销项目必须录入！");
			return;
		}

		if(this.tblDutyPerson.getRowCount()==0) {
			MsgBox.showInfo("负责人必须录入！");
			return;
		}		
		
		
		//编码不能重复  
		if(this.editData.getId()==null && MarketingUnitFactory.getRemoteInstance().exists("where number='"+this.txtNumber.getText().trim()+"'")) {
			MsgBox.showInfo("编码已存在，不能重复！");
			return;
		}
		
		boolean isShe = this.chkIsSHE.isSelected();
		boolean isTen = this.chkIsTEN.isSelected();
		boolean isPpm = this.chkIsPPM.isSelected();
		boolean isPro = this.chkIsPRO.isSelected();
		boolean isMar = this.chkIsMAR.isSelected();
		boolean isIns = this.chkIsINS.isSelected();
		if(!isShe && !isTen && !isPpm && !isPro && !isMar && !isIns) {
			MsgBox.showInfo("未选择营销职能！");
			return;
		}		
		
		if(isShe ||  isTen || isPpm) {
			MarketingUnitSellProjectCollection muSellProColl = this.editData.getSellProject();
			for(int i=0;i<muSellProColl.size();i++) {
				MarketingUnitSellProjectInfo muSellProInfo = muSellProColl.get(i);
				if(isShe && !muSellProInfo.getSellProject().isIsForSHE()) {
					MsgBox.showInfo("项目信息的第"+(i+1)+"行的项目无‘售楼属性’，与该营销单元的‘销售职能’不符！");
					return;
				}
				if(isTen && !muSellProInfo.getSellProject().isIsForTen()) {
					MsgBox.showInfo("项目信息的第"+(i+1)+"行的项目无‘租赁属性’，与该营销单元的‘租赁职能’不符！");
					return;
				}
				if(isPpm && !muSellProInfo.getSellProject().isIsForPPM()) {
					MsgBox.showInfo("项目信息的第"+(i+1)+"行的项目无‘物业属性’，与该营销单元的‘物业职能’不符！");
					return;
				}				
			}
		}		
		
		if(this.editData.getId()!=null && !this.editData.isIsLeaf()) {
			boolean hashChanged = false;
			MarketingUnitInfo muInfo = MarketingUnitFactory.getRemoteInstance().getMarketingUnitInfo(
					"select isSellFunction,isTenancyFunction,isWuYeFunction,isMarketFunction,isProjectFunction,isInsideFunction,sellProject.sellProject.id where id='"+this.editData.getId().toString()+"'");
			if(muInfo.isIsSellFunction() && !isShe)		 hashChanged = true;
			if(muInfo.isIsTenancyFunction() && !isTen)   hashChanged  =true;
			if(muInfo.isIsWuYeFunction() && !isPpm) 	 hashChanged  =true;
			if(muInfo.isIsProjectFunction() && !isPro)   hashChanged  =true;
			if(muInfo.isIsMarketFunction() && !isMar) 	 hashChanged  =true;
			if(muInfo.isIsInsideFunction() && !isIns) 	 hashChanged  =true;
			if(hashChanged) {
				MsgBox.showInfo("非明细营销单元的职能信息被下级依赖，只能增，不能减！");
				return;
			}
			
			MarketingUnitSellProjectCollection muSellProColl = this.editData.getSellProject();
			Set proIdSet = new HashSet();
			for(int i=0;i<muSellProColl.size();i++) {
				proIdSet.add(muSellProColl.get(i).getSellProject().getId().toString());
			}
			for(int i=0;i<muInfo.getSellProject().size();i++) {
				if(!proIdSet.contains(muInfo.getSellProject().get(i).getSellProject().getId().toString())) 
					hashChanged = true;
			}
			if(hashChanged) {
				MsgBox.showInfo("非明细营销单元的项目信息被下级依赖，只能增，不能减！");
				return;
			}
		}
		
		if(this.editData.getParent()!=null) {
			MarketingUnitInfo muInfo = MarketingUnitFactory.getRemoteInstance().getMarketingUnitInfo(
						"select sellProject.sellProject.id where id='"+this.editData.getParent().getId().toString()+"'");
			Set idSet = new HashSet();
			for(int i=0;i<muInfo.getSellProject().size();i++)
				idSet.add(muInfo.getSellProject().get(i).getSellProject().getId().toString());
			MarketingUnitSellProjectCollection muSellProColl = this.editData.getSellProject();
			for(int i=0;i<muSellProColl.size();i++) {
				MarketingUnitSellProjectInfo muSellProInfo = muSellProColl.get(i);
				if(!idSet.contains(muSellProInfo.getSellProject().getId().toString())) {
					MsgBox.showInfo("项目信息的第"+(i+1)+"行的项目超出了上级单元的项目范围！");
					return;
				}
			}
		}	
				
		Timestamp thisTime = FDCSQLFacadeFactory.getRemoteInstance().getServerTime();
		Date thisDate = new Date(thisTime.getTime());
		Map validUserMap = new HashMap();
		Map validMemberMap = new HashMap();
		BigDecimal allPercentage = new BigDecimal(0);
		for(int i=0;i<this.tblDutyPerson.getRowCount();i++) {
			IRow row = this.tblDutyPerson.getRow(i);
			MarketingUnitMemberInfo muMemberInfo = (MarketingUnitMemberInfo)row.getUserObject();
			Date accessionDate = (Date)row.getCell("accessionDate").getValue();
			Date dimissionDate = (Date)row.getCell("dimissionDate").getValue();
			if(accessionDate==null) {
				MsgBox.showInfo("负责人信息的第"+(i+1)+"行的上岗日期必须录入！");
				return;
			}
			muMemberInfo.setAccessionDate(accessionDate);
			String dutyPersonTitle = (String)row.getCell("dutyPersonTitle").getValue();
			muMemberInfo.setDutyPersonTitle(dutyPersonTitle);
			muMemberInfo.setIsSellFunction(isShe);
			muMemberInfo.setIsTenancyFunction(isTen);
			muMemberInfo.setIsWuYeFunction(isPpm);
			muMemberInfo.setIsProjectFunction(isPro);
			muMemberInfo.setIsMarketFunction(isMar);
			muMemberInfo.setIsInsideFunction(isIns);
			Boolean isUpdateMember = (Boolean)row.getCell("isUpdateMember").getValue();
			muMemberInfo.setIsUpdateMember(isUpdateMember.booleanValue());
			Boolean isOperation = (Boolean)row.getCell("isOperation").getValue();
			muMemberInfo.setIsOperation(isOperation.booleanValue());
			String description = (String)row.getCell("description").getValue();
			muMemberInfo.setDescription(description);
			Boolean isSharePercent = (Boolean)row.getCell("isSharePercent").getValue();
			muMemberInfo.setIsSharePercent(isSharePercent.booleanValue());
			
			BigDecimal takePercentage = (BigDecimal)row.getCell("takePercentage").getValue();
			if(takePercentage==null) takePercentage = new BigDecimal(0.00);
			muMemberInfo.setTakePercentage(takePercentage);
			if(isSharePercent.booleanValue())
				allPercentage =allPercentage.add(takePercentage);
			
			if(dimissionDate==null || dimissionDate.after(thisDate)) {
				if(!validUserMap.containsKey(muMemberInfo.getMember().getId().toString())) {
					validUserMap.put(muMemberInfo.getMember().getId().toString(),muMemberInfo.getMember());
				}else{
					UserInfo thisUser = (UserInfo)validUserMap.get(muMemberInfo.getMember().getId().toString());
					MsgBox.showInfo("有效的营销人员‘"+thisUser.getName()+"’存在多次，不符合要求，请检查！");
					return;
				}	
			}
				
		}
//		if(allPercentage.compareTo(new BigDecimal(100))>0) {
//			MsgBox.showInfo("负责人信息中有‘间接提佣’的分单比例合计不能大于100！");
//			return;
//		}
		
		for(int i=0;i<this.tblMember.getRowCount();i++) {
			IRow row = this.tblMember.getRow(i);
			Date accessionDate = (Date)row.getCell("accessionDate").getValue();
			Date dimissionDate = (Date)row.getCell("dimissionDate").getValue();
			if(accessionDate==null) {
				MsgBox.showInfo("成员信息的第"+(i+1)+"行的上岗日期必须录入！");
				return;
			}			
			MarketingUnitMemberInfo muMemberInfo = (MarketingUnitMemberInfo)row.getUserObject();
			muMemberInfo.setAccessionDate(accessionDate);
			muMemberInfo.setDimissionDate(dimissionDate);
			muMemberInfo.setIsSellFunction(((Boolean)row.getCell("isSHE").getValue()).booleanValue());
			muMemberInfo.setIsTenancyFunction(((Boolean)row.getCell("isTEN").getValue()).booleanValue());
			muMemberInfo.setIsWuYeFunction(((Boolean)row.getCell("isPPM").getValue()).booleanValue());
			muMemberInfo.setIsProjectFunction(((Boolean)row.getCell("isPRO").getValue()).booleanValue());
			muMemberInfo.setIsMarketFunction(((Boolean)row.getCell("isMAR").getValue()).booleanValue());
			muMemberInfo.setIsInsideFunction(((Boolean)row.getCell("isINS").getValue()).booleanValue());
			if(dimissionDate==null || dimissionDate.after(thisDate))		 {
				if(!validUserMap.containsKey(muMemberInfo.getMember().getId().toString())) { 
					validUserMap.put(muMemberInfo.getMember().getId().toString(),muMemberInfo.getMember());
					validMemberMap.put(muMemberInfo.getMember().getId().toString(), muMemberInfo);
				}else{
					UserInfo thisUser = (UserInfo)validUserMap.get(muMemberInfo.getMember().getId().toString());
					MsgBox.showInfo("有效的营销人员‘"+thisUser.getName()+"’存在多次，不符合要求，请检查！");
					return;
				}	
			}
		}	
		
		//同一个人对同一个项目的同一职能只能存在一个营销单元中.负责人的职能相当于营销单元的全部只能
		//负责人不受这条限制控制
		MarketingUnitSellProjectCollection muSellProColl = this.editData.getSellProject();
		for(int i=0;i<muSellProColl.size();i++) {
			SellProjectInfo sellProInfo = muSellProColl.get(i).getSellProject();
			if(sellProInfo!=null)  {
				if(isShe)  {
					String errorMessage = getErrorMessageInfo(sellProInfo,"SHE",validMemberMap);
					if(!errorMessage.equals("")) {
						MsgBox.showInfo(errorMessage);
						return;
					}
				}
				if(isTen) {
					String errorMessage = getErrorMessageInfo(sellProInfo,"TEN",validMemberMap);
					if(!errorMessage.equals("")) {
						MsgBox.showInfo(errorMessage);
						return;
					}
				}
				if(isPpm){
					String errorMessage = getErrorMessageInfo(sellProInfo,"PPM",validMemberMap);
					if(!errorMessage.equals("")) {
						MsgBox.showInfo(errorMessage);
						return;
					}
				}
				if(isPro){
					String errorMessage = getErrorMessageInfo(sellProInfo,"PRO",validMemberMap);
					if(!errorMessage.equals("")) {
						MsgBox.showInfo(errorMessage);
						return;
					}
				}
				if(isMar){
					String errorMessage = getErrorMessageInfo(sellProInfo,"MAR",validMemberMap);
					if(!errorMessage.equals("")) {
						MsgBox.showInfo(errorMessage);
						return;
					}
				}
				if(isIns){
					String errorMessage = getErrorMessageInfo(sellProInfo,"INS",validMemberMap);
					if(!errorMessage.equals("")) {
						MsgBox.showInfo(errorMessage);
						return;
					}
				}
			}
		}
		
		
		
		for(int i=0;i<this.tblDutyPerson.getRowCount();i++) {
			IRow row = this.tblDutyPerson.getRow(i);
			MarketingUnitMemberInfo muMemberInfo = (MarketingUnitMemberInfo)row.getUserObject();
			this.editData.getMember().add(muMemberInfo);
			
			IRow addRow = this.tblMember.addRow();
			addRow.getStyleAttributes().setHided(true);
			addRow.setUserObject(muMemberInfo);
			addRow.getCell("member").setValue(muMemberInfo.getMember().getName());
			addRow.getCell("accessionDate").setValue(muMemberInfo.getAccessionDate());
			addRow.getCell("dimissionDate").setValue(muMemberInfo.getDimissionDate());
			addRow.getCell("isSHE").setValue( Boolean.valueOf(muMemberInfo.isIsSellFunction()));
			addRow.getCell("isTEN").setValue( Boolean.valueOf(muMemberInfo.isIsTenancyFunction()));
			addRow.getCell("isPPM").setValue( Boolean.valueOf(muMemberInfo.isIsWuYeFunction()));
			addRow.getCell("isMAR").setValue( Boolean.valueOf(muMemberInfo.isIsMarketFunction()));
			addRow.getCell("isPRO").setValue( Boolean.valueOf(muMemberInfo.isIsProjectFunction()));
			addRow.getCell("isINS").setValue( Boolean.valueOf(muMemberInfo.isIsInsideFunction()));
		}
		
		super.actionSubmit_actionPerformed(e);
	}
	
	
	
	private String  getErrorMessageInfo(SellProjectInfo sellProInfo,String propType,Map validMemberMap) throws BOSException, EASBizException {
		Map muMemMap = new HashMap();
		String propTitle = "";
		String proFilter = "";
		if(propType.equals("SHE")) {
			propTitle = "销售职能";
			proFilter = "FIsSellFunction =1 "; 
		}else if(propType.equals("TEN")) {
			propTitle = "租赁职能";
			proFilter = "FIsTenancyFunction =1 "; 
		}else if(propType.equals("PPM")) {
			propTitle = "物业职能";
			proFilter = "FIsWuYeFunction =1 "; 
		}else if(propType.equals("PRO")) {
			propTitle = "工程职能";
			proFilter = "FIsProjectFunction =1 "; 
		}else if(propType.equals("MAR")) {
			propTitle = "营销职能";
			proFilter = "FIsMarketFunction =1 "; 
		}else if(propType.equals("INS")) {
			propTitle = "会员职能";
			proFilter = "FIsInsideFunction =1 "; 
		}
		
		//包含某项目且拥有某职能的营销单元		
		String tempSql = "select distinct(marUnit.fid) from T_TEN_MarketingUnit marUnit " +
				" left outer join T_TEN_MarketingUnitSellProject muSellPro on marUnit.Fid = muSellPro.fHeadId " +
				" where muSellPro.FSellProjectID = '"+sellProInfo.getId().toString()+"' and marUnit."+proFilter;
		//对某项目有某营销职能的所有营销成员
		String userSql = "select FMemberID,FheadId from T_TEN_MarketingUnitMember where "+ proFilter +" and fHeadId in ("+tempSql+")";
		if(this.editData.getId()!=null) 	userSql += " and fHeadId != '"+ this.editData.getId().toString() +"'";
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(userSql);
		IRowSet tableSet = builder.executeQuery();
		try{
			while(tableSet.next()) {
				String FMemberID = tableSet.getString("FMemberID");
				String FheadId = tableSet.getString("FheadId");
				muMemMap.put(FMemberID,FheadId);
			}
		}catch(SQLException e){
			this.handleException(e);
			this.abort();
		}
		if(muMemMap.size()==0) return "";
		if(validMemberMap.size()==0) return "";
		String errorMessage = "";
		Iterator iter = validMemberMap.keySet().iterator();
		while(iter.hasNext())  {
			String userId = (String)iter.next();
			MarketingUnitMemberInfo memberInfo = (MarketingUnitMemberInfo)validMemberMap.get(userId);
			if((propType.equals("SHE") && memberInfo.isIsSellFunction()) || (propType.equals("TEN") && memberInfo.isIsTenancyFunction()) || (propType.equals("PPM") && memberInfo.isIsWuYeFunction()) 
					|| (propType.equals("PRO") && memberInfo.isIsProjectFunction()) || (propType.equals("MAR") && memberInfo.isIsMarketFunction()) || (propType.equals("INS") && memberInfo.isIsInsideFunction()) ) {
				if(muMemMap.containsKey(userId)) {				
					String muId = (String)muMemMap.get(userId);
					MarketingUnitInfo muInfo = MarketingUnitFactory.getRemoteInstance().getMarketingUnitInfo("select name where id='"+muId+"'");
					if(muInfo!=null) {
						errorMessage = "营销人员‘"+memberInfo.getMember().getName() + "’对项目‘"+sellProInfo.getName()+"’的‘"+propTitle+"’在营销单元‘"+muInfo.getName()+"’中已经存在，不能重复添加！";
						break;
					}
				}
			}
		}
		return errorMessage;
	}
	
	
	protected void chkIsSHE_stateChanged(ChangeEvent e) throws Exception {
		this.tblMember.getColumn("isSHE").getStyleAttributes().setLocked(!this.chkIsSHE.isSelected());
		if(!this.chkIsSHE.isSelected()){
			for(int i=0;i<this.tblMember.getRowCount();i++) {
				IRow row = this.tblMember.getRow(i);
				row.getCell("isSHE").setValue(Boolean.valueOf(false));
			}
		}
	}
	protected void chkIsTEN_stateChanged(ChangeEvent e) throws Exception {
		this.tblMember.getColumn("isTEN").getStyleAttributes().setLocked(!this.chkIsTEN.isSelected());
		if(!this.chkIsTEN.isSelected()){
			for(int i=0;i<this.tblMember.getRowCount();i++) {
				IRow row = this.tblMember.getRow(i);
				row.getCell("isTEN").setValue(Boolean.valueOf(false));
			}
		}
	}
	protected void chkIsPPM_stateChanged(ChangeEvent e) throws Exception {
		this.tblMember.getColumn("isPPM").getStyleAttributes().setLocked(!this.chkIsPPM.isSelected());
		if(!this.chkIsPPM.isSelected()){
			for(int i=0;i<this.tblMember.getRowCount();i++) {
				IRow row = this.tblMember.getRow(i);
				row.getCell("isPPM").setValue(Boolean.valueOf(false));
			}
		}		
	}
	protected void chkIsMAR_stateChanged(ChangeEvent e) throws Exception {
		this.tblMember.getColumn("isMAR").getStyleAttributes().setLocked(!this.chkIsMAR.isSelected());
		if(!this.chkIsMAR.isSelected()){
			for(int i=0;i<this.tblMember.getRowCount();i++) {
				IRow row = this.tblMember.getRow(i);
				row.getCell("isMAR").setValue(Boolean.valueOf(false));
			}
		}			
	}
	protected void chkIsPRO_stateChanged(ChangeEvent e) throws Exception {
		this.tblMember.getColumn("isPRO").getStyleAttributes().setLocked(!this.chkIsPRO.isSelected());
		if(!this.chkIsPRO.isSelected()){
			for(int i=0;i<this.tblMember.getRowCount();i++) {
				IRow row = this.tblMember.getRow(i);
				row.getCell("isPRO").setValue(Boolean.valueOf(false));
			}
		}		
	}
	protected void chkIsINS_stateChanged(ChangeEvent e) throws Exception {
		this.tblMember.getColumn("isINS").getStyleAttributes().setLocked(!this.chkIsINS.isSelected());
		if(!this.chkIsINS.isSelected()){
			for(int i=0;i<this.tblMember.getRowCount();i++) {
				IRow row = this.tblMember.getRow(i);
				row.getCell("isINS").setValue(Boolean.valueOf(false));
			}
		}		
	}
	

}