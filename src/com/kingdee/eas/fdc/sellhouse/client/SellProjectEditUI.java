/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.ArchivesEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ArchivesEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ArchivesTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BaseDataPropertyEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.InvestorHouseInfo;
import com.kingdee.eas.fdc.sellhouse.MarketBaseDataList;
import com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ScopeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectResourceEnum;
import com.kingdee.eas.fdc.sellhouse.ShareOrgUnitCollection;
import com.kingdee.eas.fdc.sellhouse.ShareOrgUnitInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaCollection;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectFactory;
import com.kingdee.eas.fdc.tenancy.client.TenancyClientHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SellProjectEditUI extends AbstractSellProjectEditUI {
	private static final Logger logger = CoreUIObject
	.getLogger(SellProjectEditUI.class);
	
	UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	
	FullOrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
	
	
	/**
	 * output class constructor
	 */
	public SellProjectEditUI() throws Exception {
		super();
	}
	
	public void onShow() throws Exception {
		super.onShow();
		if (this.txtNumber.isEnabled()) {
			this.txtNumber.requestFocus();
		} else {
			this.txtName.requestFocus();
		}
	}
	
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		
		if (STATUS_VIEW.equals(this.getOprtState())) {
			this.btnAddSubarea.setEnabled(false);
			this.btnRemoveSubarea.setEnabled(false);
			this.btnAddOrgUnit.setEnabled(false);
			this.btnDelOrgUnit.setEnabled(false);
		}else if(STATUS_ADDNEW.equals(this.getOprtState())){
			this.txtNumber.setEnabled(true);
			this.txtNumber.setEditable(true);
			this.txtName.setEnabled(true);
			this.txtName.setEditable(true);
			this.btnAddSubarea.setEnabled(true);
			this.btnRemoveSubarea.setEnabled(true);
			this.btnAddOrgUnit.setEnabled(true);
			this.btnDelOrgUnit.setEnabled(true);
		}
	}

	protected void setNumberTextEnabled()
	{
		
		if (getNumberCtrl() != null)
		{
			CostCenterOrgUnitInfo currentCostUnit = SysContext.getSysContext()
			.getCurrentCostUnit();
			if (currentCostUnit != null)
			{
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(
						this.editData, currentCostUnit.getId().toString());
				
				getNumberCtrl().setEnabled(isAllowModify);
				getNumberCtrl().setEditable(isAllowModify);
			}
		}
	}
	
	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.txtNumber.setRequired(true);
		this.contProject.setVisible(true);
		this.contInvestorHouse.setVisible(false);
		
		/* TODO 59上面暂不作组织的过滤
		 FullOrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		 //FullOrgUnitInfo org = (FullOrgUnitInfo) this.getUIContext().get("org");
		  EntityViewInfo view = new EntityViewInfo();
		  FilterInfo filter = new FilterInfo();
		  view.setFilter(filter);
		  //如果当前不是财务中心，取是财务的上级组织
		   while(true){
		   if(!org.isIsCompanyOrgUnit() && org.isIsLeaf()){
		   org = org.getParent();
		   continue;
		   }
		   break;
		   }
		   filter.getFilterItems().add(
		   new FilterItemInfo("fullOrgUnit.id", org.getId().toString()));
		   //		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		    this.f7Project.setEntityViewInfo(view);
		    */
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		
		this.tblSubarea.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		//this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		
		this.txtTerraArea.setRemoveingZeroInDispaly(false);
		this.txtTerraArea.setRemoveingZeroInEdit(false);
		this.txtTerraArea.setPrecision(2);
		this.txtTerraArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtTerraArea.setSupportedEmpty(true);
		this.txtCubageRate.setRemoveingZeroInDispaly(false);
		this.txtCubageRate.setRemoveingZeroInEdit(false);
		this.txtCubageRate.setPrecision(2);
		this.txtCubageRate.setHorizontalAlignment(JTextField.RIGHT);
		this.txtCubageRate.setSupportedEmpty(true);
		
		this.txtFrameProperty.setMaxLength(80);
		
		/* 亿达5.9售楼改动 ?	删除租售项目里,的建筑性质，采用界面隐藏即可 20081223
		 view = new EntityViewInfo();
		 filter = new FilterInfo();
		 view.setFilter(filter);
		 filter.getFilterItems()
		 .add(new FilterItemInfo("level", new Integer(1)));
		 this.f7BuildingProperty.setEntityViewInfo(view);
		 this.f7BuildingProperty.setRequired(true);
		 */
		
		super.onLoad();
	}
	
	public void loadFields() {
		super.loadFields();
		SHEHelper.setNumberEnabled(editData,getOprtState(),txtNumber);
		SellProjectInfo sellProject = (SellProjectInfo) this.editData;
		this.txtName.setText(sellProject.getName());
		this.txtNumber.setText(sellProject.getNumber());
		this.comboProjecResource.setSelectedItem(sellProject.getProjectResource());
		this.f7Project.setValue(sellProject.getProject());
		this.f7InvestorHouse.setValue(sellProject.getInvestorHouse());
		this.txtTerraNumber.setText(sellProject.getTerraNumber());
		this.txtTerraArea.setValue(sellProject.getTerraArea());
		this.txtTerraLicence.setText(sellProject.getTerraLicence());
		this.kdpTermBegin.setValue(sellProject.getTermBegin());
		this.kdpTermEnd.setValue(sellProject.getTermEnd());
		this.txtTerraUse.setText(sellProject.getTerraUse());
		this.f7BuildingProperty.setValue(sellProject.getBuildingProperty());
		this.txtFrameProperty.setText(sellProject.getFrameProperty());
		this.txtSideEntironment.setText(sellProject.getSideEntironment());
		this.txtCubageRate.setValue(sellProject.getCubageRate());
		this.txtRealtyPaperNumber.setText(sellProject.getRealtyPaperNumber());
		this.txtPresellLicence.setText(sellProject.getPresellLicence());
		this.txtExportLicence.setText(sellProject.getExportLicence());
		this.txtProAdress.setText(sellProject.getProAddress());
		
		this.chkIsForSHE.setSelected(sellProject.isIsForSHE());
		this.chkIsForTen.setSelected(sellProject.isIsForTen());
		this.chkIsForPPM.setSelected(sellProject.isIsForPPM());
		
		this.tblSubarea.removeRows();
		this.tblSubarea.checkParsed();
		this.tblSubarea.setUserObject(null);
		SubareaCollection subarea = sellProject.getSubarea();
		for (int i = 0; i < subarea.size(); i++) {
			SubareaInfo info = subarea.get(i);
			IRow row = tblSubarea.addRow();
			row.setUserObject(info);
			row.getCell("number").setValue(info.getNumber());
			row.getCell("name").setValue(info.getName());
		}
		this.tblShareOrgUnit.removeRows();
		this.tblShareOrgUnit.checkParsed();
		this.tblShareOrgUnit.setUserObject(null);
		ShareOrgUnitCollection orgColl = sellProject.getOrgUnitShareList();
		for(int i=0;i<orgColl.size();i++)
		{
			ShareOrgUnitInfo orgUnitInfo = orgColl.get(i);			
		
			IRow row = tblShareOrgUnit.addRow(i);
			showLoadOrgUnit(row,orgUnitInfo);
//			row.setUserObject(orgUnitInfo);
//			row.getCell("orgUnit").setValue(orgUnitInfo.getOrgUnit().getName());
//			row.getCell("orgUnit").getStyleAttributes().setLocked(true);
//			row.getCell("operationPerson").setValue(orgUnitInfo.getOperationPerson().getName());
//			row.getCell("operationPerson").getStyleAttributes().setLocked(true);
//			row.getCell("shareDate").setValue(orgUnitInfo.getShareDate());
//			row.getCell("shareDate").getStyleAttributes().setLocked(true);
//			row.getCell("createOrgUnit").setValue(orgUnitInfo.getCreateOrgUnit().getName());
//			row.getCell("createOrgUnit").getStyleAttributes().setLocked(true);
		}

		
		if (this.editData.getId() != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", this.editData.getId()
							.toString()));
			try {
				if (BuildingFactory.getRemoteInstance().exists(filter)) {
					this.txtName.setEnabled(false);
					this.tblSubarea.getStyleAttributes().setLocked(true);
					this.f7BuildingProperty.setEnabled(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		SellProjectInfo sellProject = (SellProjectInfo) this.editData;
		sellProject.setIsForSHE(this.chkIsForSHE.isSelected());
		sellProject.setIsForTen(this.chkIsForTen.isSelected());
		sellProject.setIsForPPM(this.chkIsForPPM.isSelected());
		SellProjectResourceEnum projectResource = (SellProjectResourceEnum)this.comboProjecResource.getSelectedItem();
		sellProject.setProjectResource(projectResource);
		sellProject.setInvestorHouse((InvestorHouseInfo)this.f7InvestorHouse.getValue());
		sellProject.setNumber(this.txtNumber.getText());
		sellProject.setName(this.txtName.getText());
		sellProject.setProject((CurProjectInfo) this.f7Project.getValue());
		sellProject.setTerraNumber(this.txtTerraNumber.getText());
		sellProject.setTerraArea(this.txtTerraArea.getBigDecimalValue());
		sellProject.setTerraLicence(this.txtTerraLicence.getText());
		sellProject.setTermBegin((Date) this.kdpTermBegin.getValue());
		sellProject.setTermEnd((Date) this.kdpTermEnd.getValue());
		sellProject.setTerraUse(this.txtTerraUse.getText());
		sellProject
		.setBuildingProperty((BuildingPropertyInfo) this.f7BuildingProperty
				.getValue());
		sellProject.setFrameProperty(this.txtFrameProperty.getText());
		sellProject.setSideEntironment(this.txtSideEntironment.getText());
		sellProject.setCubageRate(this.txtCubageRate.getBigDecimalValue());
		sellProject.setRealtyPaperNumber(this.txtRealtyPaperNumber.getText());
		sellProject.setPresellLicence(this.txtPresellLicence.getText());
		sellProject.setExportLicence(this.txtExportLicence.getText());
		sellProject.setProAddress(this.txtProAdress.getText());
		sellProject.getSubarea().clear();
		for (int i = 0; i < tblSubarea.getRowCount(); i++) {
			IRow row = tblSubarea.getRow(i);
			SubareaInfo subarea = (SubareaInfo) row.getUserObject();
			subarea.setNumber((String) row.getCell("number").getValue());
			subarea.setName((String) row.getCell("name").getValue());
			sellProject.getSubarea().add(subarea);
		}
		sellProject.getOrgUnitShareList().clear();
		for(int i=0;i<tblShareOrgUnit.getRowCount();i++)
		{
			IRow row = tblShareOrgUnit.getRow(i);
			ShareOrgUnitInfo orgUnitInfo = (ShareOrgUnitInfo)row.getUserObject();
			orgUnitInfo.setOrgUnit((FullOrgUnitInfo)row.getCell("orgUnit").getUserObject());
			orgUnitInfo.setOperationPerson((UserInfo)row.getCell("operationPerson").getUserObject());
			orgUnitInfo.setShareDate((Date)row.getCell("shareDate").getValue());
			orgUnitInfo.setCreateOrgUnit((FullOrgUnitInfo)row.getCell("createOrgUnit").getUserObject());
			sellProject.getOrgUnitShareList().add(orgUnitInfo);
		}			
	}
	
	public boolean isModify() {
		boolean isModify = super.isModify();
		if (this.tblSubarea.getUserObject() != null) {
			isModify = true;
		}
		for (int i = 0; i < tblSubarea.getRowCount(); i++) {
			IRow row = this.tblSubarea.getRow(i);
			SubareaInfo subarea = (SubareaInfo) row.getUserObject();
			if (row.getCell("number").getValue() == null) {
				isModify = true;
			} else {
				if (!subarea.getNumber().equals(
						row.getCell("number").getValue())) {
					isModify = true;
				}
			}
			if (row.getCell("name").getValue() == null) {
				isModify = true;
			} else {
				if (!subarea.getName().equals(row.getCell("name").getValue())) {
					isModify = true;
				}
			}
		}
		return isModify;
	}
	
	protected IObjectValue createNewData() {
		SellProjectInfo sellProjectInfo = new SellProjectInfo();
		sellProjectInfo.setOrgUnit(SysContext.getSysContext()
				.getCurrentOrgUnit().castToFullOrgUnitInfo());
		sellProjectInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		sellProjectInfo.setProjectResource(SellProjectResourceEnum.DEVELOPER);
		return sellProjectInfo;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return SellProjectFactory.getRemoteInstance();
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		Date termBegin = (Date) this.kdpTermBegin.getValue();
		Date termEnd = (Date) this.kdpTermEnd.getValue();
		if (termBegin != null && termEnd != null) {
			if (!termEnd.after(termBegin)) {
				this.txtTotalYear.setText(null);
				MsgBox.showError("结束日期必须大于开始日期");
				abort();
			}
		}
		
		if (StringUtils.isEmpty(this.txtName.getText())) {
			MsgBox.showInfo("销售项目名称必须录入！");
			abort();
		}
		if (this.txtNumber.isEditable() && this.txtNumber.isEnabled()) {
			if (StringUtils.isEmpty(this.txtNumber.getText())) {
				MsgBox.showInfo("销售项目编码必须录入！");
				abort();
			}
		}
		
		if(!this.chkIsForSHE.isSelected() && !this.chkIsForTen.isSelected() && !this.chkIsForPPM.isSelected()){
			MsgBox.showInfo(this, "售楼,租赁,物业属性至少选一项!");
			this.abort();
		}
		
//		if (this.f7BuildingProperty.getValue() == null) {
//		MsgBox.showInfo("建筑性质必须录入！");
//		abort();
//		}
		
		if(this.txtSideEntironment.getText().length()>300)
		{
			MsgBox.showInfo("周边环境信息录入不能超过300字符！");
			abort();
		}
		Map numberMap = new HashMap();
		Map nameMap = new HashMap();
		for (int i = 0; i < this.tblSubarea.getRowCount(); i++) {
			IRow row = this.tblSubarea.getRow(i);
			if (row.getCell("number").getValue() == null) {
				MsgBox.showInfo("分区编码不能为空！");
				this.kDTabbedPane1.setSelectedIndex(1);
				tblSubarea.getSelectManager().select(row.getRowIndex(), 0);
				abort();
			} else {
				String number = (String)row.getCell("number").getValue();
				if(number.length() > 80){
					MsgBox.showInfo("分区编码长度不能大于80！");
					this.abort();
				}
				
				if (numberMap.containsKey(row.getCell("number").getValue())) {
					MsgBox.showInfo("分区编码不能重复！");
					this.kDTabbedPane1.setSelectedIndex(1);
					abort();
				} else {
					numberMap.put(row.getCell("number").getValue(), row
							.getCell("number").getValue());
				}
			}
			if (row.getCell("name").getValue() == null) {
				MsgBox.showInfo("分区名称不能为空！");
				this.kDTabbedPane1.setSelectedIndex(1);
				tblSubarea.getSelectManager().select(row.getRowIndex(), 1);
				abort();
			} else {
				String name = (String)row.getCell("name").getValue();
				if(name.length() > 80){
					MsgBox.showInfo("分区名称长度不能大于80！");
					this.abort();
				}
				
				if (nameMap.containsKey(row.getCell("name").getValue())) {
					MsgBox.showInfo("分区名称不能重复！");
					this.kDTabbedPane1.setSelectedIndex(1);
					abort();
				} else {
					nameMap.put(row.getCell("name").getValue(), row.getCell(
					"name").getValue());
				}
			}
		}
	}
	
	protected void btnAddSubarea_actionPerformed(ActionEvent e)
	throws Exception {
		int index = this.tblSubarea.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			index = this.tblSubarea.getRowCount() - 1;
		}
		IRow row = this.tblSubarea.addRow(index + 1);
		row.setUserObject(new SubareaInfo());
		row.getStyleAttributes().setLocked(false);
		this.tblSubarea.setUserObject("isEdited");
	}
	
	protected void btnDeleteSubarea_actionPerformed(ActionEvent e)
	throws Exception {
		int index = this.tblSubarea.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		IRow curRow = this.tblSubarea.getRow(index);
		if(curRow!=null) {
			//要删除的分区如果被使用则不能被删除
			SubareaInfo subAreaInfo = (SubareaInfo)curRow.getUserObject();
			if(subAreaInfo!=null && subAreaInfo.getId()!=null) {
				if(BuildingFactory.getRemoteInstance().exists("where subArea.id = '"+subAreaInfo.getId()+"'")) {
					FDCMsgBox.showInfo("该分区下已建立楼栋，不允许删除！");
					return;
				}
			}
			
			tblSubarea.removeRow(index);
			this.tblSubarea.setUserObject("isEdited");
		}

	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add("*");
		selectors.add("subarea.*");
		selectors.add("project.*");
		selectors.add("orgUnit.*");
		selectors.add("orgUnitShareList.*");
		selectors.add("orgUnitShareList.orgUnit.*");
		selectors.add("orgUnitShareList.operationPerson.*");
		selectors.add("orgUnitShareList.createOrgUnit.*");
		selectors.add("buildingProperty.*");
		selectors.add("investorHouse.*");
		return selectors;
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		
		this.f7BuildingProperty.setEnabled(true);
		this.txtNumber.setEnabled(true);
		this.txtNumber.setEditable(true);
		this.txtName.setEnabled(true);
		this.txtName.setEditable(true);
		this.txtNumber.requestFocus();
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		SellProjectInfo sellProject = (SellProjectInfo) this.editData;
//		
//		//在新建项目的时候默认把所有档案写入项目档案分录
//		//找出项目原来的档案，如果原来存在就不重新保存
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProject.getId().toString()));
//		view.setFilter(filter);
//		ProjectArchivesEntryCollection projectOldArchColl = ProjectArchivesEntryFactory.getRemoteInstance().getProjectArchivesEntryCollection(view);
////		ProjectArchivesEntryCollection projectArchColl = new ProjectArchivesEntryCollection();
//		
//		FDCSQLBuilder builder = new FDCSQLBuilder();
//		builder.appendSql("select * from t_she_projectDataBase ");
//		IRowSet projectSet = builder.executeQuery();
//		while(projectSet.next())
//		{
//			boolean isInclude = false;
//			ProjectArchivesEntryInfo projectArchInfo = new ProjectArchivesEntryInfo();
//			String name = projectSet.getString("fname_l2");
//			if(projectOldArchColl.size()>0)
//			{
//				for(int i=0;i<projectOldArchColl.size();i++)
//				{
//					ProjectArchivesEntryInfo projectOldArchInfo = projectOldArchColl.get(i);
//					if(name.equals(projectOldArchInfo.getArchivesName()))
//					{
//						isInclude = true;
//						break;
//					}
//				}
//				
//			}
//			if(!isInclude)
//			{
//				projectArchInfo.setArchivesName(name);
//				projectArchInfo.setName(projectSet.getString("FTable"));
//				projectArchInfo.setScopeType(ScopeTypeEnum.getEnum((String)projectSet.getString("FDefScopeType")));
//				projectArchInfo.setBaseDataProperty(BaseDataPropertyEnum.getEnum((String)projectSet.getString("FDefBaseDataPro")));
//				projectArchInfo.setArchivesType(ArchivesTypeEnum.getEnum((String)projectSet.getString("FArchives")));
//				projectOldArchColl.add(projectArchInfo);
//			}		
//		}
//		sellProject.getProjectArchEntrys().clear();
//		sellProject.getProjectArchEntrys().addCollection(projectOldArchColl);
		super.actionSubmit_actionPerformed(e);
		
		this.f7BuildingProperty.setEnabled(true);
		this.txtNumber.setEnabled(true);
		this.txtNumber.setEditable(true);
		this.txtName.setEnabled(true);
		this.txtName.setEditable(true);
		this.txtNumber.requestFocus();
		
	}
	
	protected void kdpTermBegin_dataChanged(DataChangeEvent e) throws Exception {
		super.kdpTermBegin_dataChanged(e);
		calTotalYear();
	}
	
	protected void kdpTermEnd_dataChanged(DataChangeEvent e) throws Exception {
		super.kdpTermEnd_dataChanged(e);
		calTotalYear();
	}
	
	private void calTotalYear() {
		Date termBegin = (Date) this.kdpTermBegin.getValue();
		Date termEnd = (Date) this.kdpTermEnd.getValue();
		if (termBegin == null || termEnd == null) {
			this.txtTotalYear.setText(null);
			return;
		}
		if (!termEnd.after(termBegin)) {
			this.txtTotalYear.setText(null);
			return;
		}
		this.txtTotalYear.setText((termEnd.getYear() - termBegin.getYear())
				+ "");
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		String id = this.editData.getId().toString();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", id));
		this.btnAddOrgUnit.setEnabled(true);
		this.btnDelOrgUnit.setEnabled(true);
		this.btnAddSubarea.setEnabled(true);
		this.btnRemoveSubarea.setEnabled(true);
		if (BuildingFactory.getRemoteInstance().exists(filter)) {
			this.f7BuildingProperty.setEnabled(false);
			this.txtNumber.setEnabled(false);
			this.txtName.setEnabled(false);
		} else {
			this.txtNumber.setEnabled(true);
			this.f7BuildingProperty.setEnabled(true);	
		}
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String id = this.editData.getId().toString();
		if (id == null) {
			return;
		}
		if (id != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", id));
			if (BuildingFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已经建立楼栋信息,不能删除!");
				return;
			}
		}
		super.actionRemove_actionPerformed(e);
	}
	
	protected void btnAddOrgUnit_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddOrgUnit_actionPerformed(e);
		KDCommonPromptDialog dlg = new KDCommonPromptDialog();
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.basedata.org.app.FullOrgUnitQuery")));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("partSale.isBizUnit","true"));
		view.setFilter(filter);
		dlg.setEntityViewInfo(view);
		//设置多选
		dlg.setEnabledMultiSelection(true);
		dlg.show();
		Object[] object = (Object[])dlg.getData();
		if(object!=null && object.length>0)
		{
			IRow row = null;
			List list = new ArrayList();
			for(int i=0;i<tblShareOrgUnit.getRowCount();i++)
			{
				IRow row2 = tblShareOrgUnit.getRow(i);
				FullOrgUnitInfo orgInfo = (FullOrgUnitInfo)row2.getCell("orgUnit").getUserObject();
				if(orgInfo!=null)
				{
					list.add(orgInfo.getId().toString());
				}
			}
			for(int j=0;j<object.length;j++)
			{
				FullOrgUnitInfo info = (FullOrgUnitInfo)object[j];
				if(org.getId().toString().equals(info.getId().toString()))
				{
					MsgBox.showInfo(info.getName()+"是当前登陆组织!");
					return;
				}
				else if(TenancyClientHelper.isInclude(info.getId().toString(),list))
				{
					MsgBox.showInfo(info.getName()+"已经存在不要重复添加！");
					return;
				}else
				{
					SellProjectInfo sellInfo  = (SellProjectInfo)this.editData;
					if(sellInfo.getOrgUnit().getId().toString().equals(info.getId().toString())) {
						MsgBox.showInfo(info.getName()+"组是该项目的创建组织，不用共享！");
						return;				
					}
					
					this.tblShareOrgUnit.checkParsed();
					row = this.tblShareOrgUnit.addRow(j);
					ShareOrgUnitInfo orgUnitInfo = new ShareOrgUnitInfo();
					orgUnitInfo.setOrgUnit(info);
					orgUnitInfo.setOperationPerson(userInfo);
					if(STATUS_ADDNEW.equals(getOprtState()))
					{
						orgUnitInfo.setCreateOrgUnit(org);
					}else
					{
						orgUnitInfo.setCreateOrgUnit(sellInfo.getOrgUnit());
					}
					
					orgUnitInfo.setShareDate(FDCSQLFacadeFactory.getRemoteInstance()
							.getServerTime());
					//显示共享组织信息
					showShareOrgUnit(row,orgUnitInfo);
				}
			}
		}
	}
	
	private void showShareOrgUnit(IRow row,ShareOrgUnitInfo orgUnitInfo)
	{
		row.setUserObject(orgUnitInfo);
		row.getCell("orgUnit").setValue(orgUnitInfo.getOrgUnit().getName());
		row.getCell("orgUnit").setUserObject(orgUnitInfo.getOrgUnit());
		row.getCell("orgUnit").getStyleAttributes().setLocked(true);
		row.getCell("operationPerson").setValue(orgUnitInfo.getOperationPerson().getName());
		row.getCell("operationPerson").setUserObject(orgUnitInfo.getOperationPerson());
		row.getCell("operationPerson").getStyleAttributes().setLocked(true);
		row.getCell("shareDate").setValue(orgUnitInfo.getShareDate());
		row.getCell("shareDate").getStyleAttributes().setLocked(true);
		row.getCell("createOrgUnit").setValue(org.getName());
		row.getCell("createOrgUnit").setUserObject(org);
		row.getCell("createOrgUnit").getStyleAttributes().setLocked(true);
	}
	
	private void showLoadOrgUnit(IRow row,ShareOrgUnitInfo orgUnitInfo)
	{
		row.setUserObject(orgUnitInfo);
		row.getCell("orgUnit").setValue(orgUnitInfo.getOrgUnit().getName());
		row.getCell("orgUnit").setUserObject(orgUnitInfo.getOrgUnit());
		row.getCell("orgUnit").getStyleAttributes().setLocked(true);
		row.getCell("operationPerson").setValue(orgUnitInfo.getOperationPerson().getName());
		row.getCell("operationPerson").setUserObject(orgUnitInfo.getOperationPerson());
		row.getCell("operationPerson").getStyleAttributes().setLocked(true);
		row.getCell("shareDate").setValue(orgUnitInfo.getShareDate());
		row.getCell("shareDate").getStyleAttributes().setLocked(true);
		row.getCell("createOrgUnit").setValue(orgUnitInfo.getCreateOrgUnit().getName());
		row.getCell("createOrgUnit").setUserObject(orgUnitInfo.getCreateOrgUnit());
		row.getCell("createOrgUnit").getStyleAttributes().setLocked(true);
	}
	
	
	protected void comboProjecResource_itemStateChanged(ItemEvent e) throws Exception {
		super.comboProjecResource_itemStateChanged(e);
		if(SellProjectResourceEnum.DEVELOPER.equals((SellProjectResourceEnum)this.comboProjecResource.getSelectedItem()))
		{
			this.contProject.setVisible(true);
			this.contInvestorHouse.setVisible(false);
			this.f7InvestorHouse.setValue(null);
		}else if(SellProjectResourceEnum.INVESTOR.equals((SellProjectResourceEnum)this.comboProjecResource.getSelectedItem()))
		{
			this.contInvestorHouse.setVisible(true);
			this.contProject.setVisible(false);
			this.f7Project.setValue(null);
		}
		else if(SellProjectResourceEnum.CONSIGN.equals((SellProjectResourceEnum)this.comboProjecResource.getSelectedItem()))
		{
			this.contInvestorHouse.setVisible(false);
			this.contProject.setVisible(false);
			this.f7Project.setValue(null);
			this.f7InvestorHouse.setValue(null);
		}
	}
	
	protected void btnDelOrgUnit_actionPerformed(ActionEvent e) throws Exception {
		super.btnDelOrgUnit_actionPerformed(e);
		int activeRowIndex = this.tblShareOrgUnit.getSelectManager()
		.getActiveRowIndex();
		if (activeRowIndex == -1) {
			MsgBox.showInfo("请选择行");
			return;
		}
		ShareOrgUnitInfo orgUnitInfo = (ShareOrgUnitInfo)this.tblShareOrgUnit.getRow(activeRowIndex).getUserObject();
		if(this.editData.getId()!=null)
		{
			String projectID = this.editData.getId().toString();
			boolean isUsed = MarketingUnitSellProjectFactory.getRemoteInstance().exists(
					"where head.orgUnit.id='"+orgUnitInfo.getOrgUnit().getId().toString()+"' and sellProject='"+projectID+"'");
			if(isUsed)	{
				MsgBox.showInfo("该共享组织已引用项目，不能取消组织共享");
				return;
			}			
			this.tblShareOrgUnit.removeRow(activeRowIndex);
		}else
		{
			this.tblShareOrgUnit.removeRow(activeRowIndex);
		}		
	}
	
	public static boolean isInclude(String str,Set set)
	{
		boolean result = false;
		Iterator iter = set.iterator();
		while(iter.hasNext())
		{
			String string = (String)iter.next();
			if(str.equals(string))
			{
				result =true;
				return result;
			}
		}
		return result;
	}
	
	protected FDCDataBaseInfo getEditData() {
		return null;
	}
	
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	
	protected KDBizMultiLangBox getNameCtrl() {
		return null;
	}
}