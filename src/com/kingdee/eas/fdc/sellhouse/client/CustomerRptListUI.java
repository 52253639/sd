/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCBaseCustomerInfo;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerFactory;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basecrm.client.CusClientHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCCustomerEditUI;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basecrm.client.ImportFDCOrgCustomerUI;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basecrm.client.ShareCustomerUI;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceSrcEnum;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlCollection;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SharePropertyCollection;
import com.kingdee.eas.fdc.sellhouse.SharePropertyFactory;
import com.kingdee.eas.fdc.sellhouse.SharePropertyInfo;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.formula.SellHouseHelper;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CustomerRptListUI extends AbstractCustomerRptListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CustomerRptListUI.class);
	private Map map = FDCSysContext.getInstance().getOrgMap();
	boolean existNumberRule = CommerceHelper
			.isExistNumberRule(new SHECustomerInfo());
    private boolean isOrgData = false;//是否是组织客户的资料
    private boolean isPublic = false;//是否是公共客户，如果是公有的则只有负责人可以进行转交
    private boolean isPrivateValue = true;//判断读取的数据是公有还是私有
    private boolean isShowAll=true;
    protected Map permit=new HashMap();
    protected boolean isControl=SHEManageHelper.isControl(null, SysContext.getSysContext().getCurrentUserInfo());
	/**
	 * output class constructor
	 */
	public CustomerRptListUI() throws Exception {
		super();
	}
	protected String[] getLocateNames() {
		String[] locateNames = new String[2];
		locateNames[0] = "name";
		locateNames[1] = "phone";
		return locateNames;
	}
	public void onLoad() throws Exception {
		this.actionQuery.setEnabled(false);
		super.onLoad();
		if (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.btnMerge.setEnabled(false);
			this.actionAddToSysCustomer.setEnabled(false);
			this.actionImport.setEnabled(false);
		}else {
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionImport.setEnabled(true);
		}
		
		btnStatus(true);

		this.btnCommerceChance.setIcon(EASResource
				.getIcon("imgTbtn_synchronization"));
		this.btnTrack.setIcon(EASResource.getIcon("imgTbtn_follow"));
		this.btnQuestionPaper.setIcon(EASResource
				.getIcon("imgTbtn_addcredence"));
		this.btnInsider.setIcon(EASResource.getIcon("imgTbtn_upenumnew"));
		this.btnChangeName.setIcon(EASResource.getIcon("imgTbtn_rename"));
		this.btnUpdate.setIcon(EASResource.getIcon("imgIcon_update"));
		this.btnShare.setIcon(EASResource.getIcon("imgTbtn_sealup"));
		this.btnDeliver.setIcon(EASResource.getIcon("imgTbtn_deliverto"));
		this.btnMerge.setIcon(EASResource.getIcon("imgTbtn_merge"));
		this.btnAddToSysCustomer.setIcon(EASResource.getIcon("imgTbtn_synchronization"));
		initTree();

		tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat(
				"yyyy-MM-dd");
		tblMain.getColumn("lastUpdateTime").getStyleAttributes()
				.setNumberFormat("yyyy-MM-dd");

		this.btnImport.setVisible(true);
		this.btnAttachment.setVisible(true);
		this.MenuItemAttachment.setVisible(true);
		this.btnInsider.setVisible(false);
		this.btnMerge.setVisible(false);
		menuItemCommerceChance.setIcon(EASResource.getIcon("imgTbtn_synchronization"));
		menuItemTrack.setIcon(EASResource.getIcon("imgTbtn_follow"));
		menuItemQuestion.setIcon(EASResource.getIcon("imgTbtn_addcredence"));
		menuItemChangeName.setIcon(EASResource.getIcon("imgTbtn_rename"));
		menuItemUpdate.setIcon(EASResource.getIcon("imgIcon_update"));
		menuItemShare.setIcon(EASResource.getIcon("imgTbtn_sealup"));
		menuItemDeliver.setIcon(EASResource.getIcon("imgTbtn_deliverto"));
		menuItemMerge.setIcon(EASResource.getIcon("imgTbtn_merge"));
		menuItemImport.setIcon(EASResource.getIcon("imgTbtn_importexcel"));
		
		this.actionTrack.setEnabled(true);
		this.btnShowAll.setIcon(EASResource.getIcon("imgTbtn_monadismpostil"));
		
		this.actionQuery.setEnabled(true);
		
		this.actionUpdate.setVisible(false);
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(CRMTreeHelper.getSellProjectTree(actionOnLoad,false));
//		this.tblMain.getDataRequestManager().setDataRequestMode(
//				KDTDataRequestManager.REAL_MODE);
		if(this.treeMain.getRowCount()>0){
			treeMain.setSelectionRow(1); 
		}else{
			treeMain.setSelectionRow(0); 
		}
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(orgNode != null && orgNode.getUserObject() != null){
			if(orgNode.getUserObject() instanceof OrgStructureInfo) {
				this.actionAddNew.setEnabled(false);
				this.btnCusTransform.setEnabled(false);
				this.actionImport.setEnabled(false);
				if(!isMarketUnitControlUser()){
					this.tblMain.removeRows(false);
					isOrgData = false;
					return;
				}else{
					mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "SHECustomerQuery");
					isOrgData = true;
				}
			}else{
				if (map.containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
					this.actionAddNew.setEnabled(true);
					this.actionImport.setEnabled(true);
					this.btnCusTransform.setEnabled(true);
					this.actionEdit.setEnabled(true);
					this.actionRemove.setEnabled(true);
				}
				mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "SHECustomerQuery");
				isOrgData = false;
			}
		}
		this.refresh(null);
	}

	/**
	 * 开启自定义过滤界面
	 * 
	 */
	protected boolean initDefaultFilter() {
		return true;// 默认不弹出
	}

	/**
	 * 初始化过滤界面---
	 */
    private CommonQueryDialog queryDlg = null;
	protected CommonQueryDialog initCommonQueryDialog() { //覆盖方法
		if (queryDlg != null)
			return queryDlg;
		try {
			queryDlg = super.initCommonQueryDialog();
			queryDlg.setOwner(this);
			queryDlg.setParentUIClassName(getClass().getName());
			queryDlg.setQueryObjectPK(mainQueryPK); //mainQueryPK 决定自定义过滤的字段
			queryDlg.setHeight(400);
			queryDlg.setWidth(600);
			queryDlg.addUserPanel(getFilterUI());//加入过滤界面
			queryDlg.setTitle("客户台账过滤查询");
			queryDlg.setShowSorter(true);
			queryDlg.setUiObject(this);
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}
		return queryDlg;
	}
	/**
	 * 得到过滤界面
	 */
	CustomerRptFilterUI filterUI;
	public CustomerRptFilterUI getFilterUI() throws Exception {
		if (filterUI == null)
			filterUI = new CustomerRptFilterUI();
		return filterUI;
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			FilterInfo filter = new FilterInfo();
			if (orgNode != null) {
				if (orgNode.getUserObject() != null&& orgNode.getUserObject() instanceof OrgStructureInfo) {
					filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("id", "null"));
					this.actionAddNew.setEnabled(false);
					this.actionEdit.setEnabled(false);
					this.actionRemove.setEnabled(false);
				} else if (orgNode.getUserObject() != null&& orgNode.getUserObject() instanceof SellProjectInfo) {
					SellProjectInfo sellProject = (SellProjectInfo) orgNode.getUserObject();
//					Set idRow = new HashSet();
//					idRow = getShareCustomerIdSet(sellProject);
//					idRow = getCustomerId(sellProject);
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
//					if (idRow != null && idRow.size() > 0) {
//					filter.getFilterItems().add(
//							new FilterItemInfo("id", idRow,
//									CompareType.INNER));
//					}else{
//						filter.getFilterItems().add(
//								new FilterItemInfo("id", null,
//										CompareType.EQUALS));
//					}
					if(!isShowAll){
						filter.getFilterItems().add(new FilterItemInfo("id", "select fcustomerid from T_SHE_CommerceChance where fcommerceSrc !='transaction'",CompareType.NOTINNER));
					}
					if(this.btnCusTransform.getText().equals("查看私有客户")){
						filter.getFilterItems().add(new FilterItemInfo("isPublic", Boolean.TRUE));
						filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id",getPublicSql(null,sellProject),CompareType.INNER));
					}else{
						if(!isControl){
							Set saleMan=SHEManageHelper.getPermitSaleManSet(sellProject,permit);
							filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id", saleMan,CompareType.INNER));
						}
						filter.getFilterItems().add(new FilterItemInfo("isPublic", Boolean.FALSE));
					}
				}
			}else{
				filter.getFilterItems().add(new FilterItemInfo("id", "null"));
			}
			viewInfo = (EntityViewInfo) mainQuery.clone();
			
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "AND");
			} else {
				viewInfo.setFilter(filter);
			}

		} catch (Exception e) {
			this.handleException(e);
		}
		
		this.tblMain.getColumn("customerType").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("code").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("simpleCode").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("identity.name").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("firstLinkman").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("email").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("bookedAddress").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("enterpriceProperty.name").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("industry.name").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("isInsider").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("corporate").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("corporateTel").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("description").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("isEnabled").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("isChooseRoom").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("createUnit.name").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("creator.name").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("lastUpdateUnit.name").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("lastUpdateUser.name").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("createWay").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("sellProject.name").getStyleAttributes().setHided(true);
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	private String getPublicSql(UserInfo currSaleMan,SellProjectInfo spInfo)throws BOSException, EASBizException {

		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		if(currSaleMan==null) currSaleMan = SysContext.getSysContext().getCurrentUserInfo();
	
		StringBuffer bufferSql = new StringBuffer();
		bufferSql.append("select fcontrolerid fmemberid from T_SHE_MarketUnitControl where forgUnitId = '"+orgUnit.getId()+"' and fcontrolerid = '"+currSaleMan.getId()+"' ");
		bufferSql.append(" union ");
		bufferSql.append("select distinct(fmemberid) from (");
		bufferSql.append("select MuSp.fsellProjectId,MuMember.fmemberid ");//当作为管控人员时：取当前及下级组织下所有启用的团队中的所有成员
		bufferSql.append("from T_SHE_MarketingUnitMember MuMember ");
		bufferSql.append("left join T_SHE_MarketingUnit MuUnit on MuUnit.fid =MuMember.fheadId ");
		bufferSql.append("left join T_SHE_MarketingUnitSellProject MuSp on MuSp.fheadid = MuUnit.fid ");
		bufferSql.append("where MuUnit.FIsEnabled = 1 and MuUnit.forgUnitId = '"+orgUnit.getId()+"' ");
		bufferSql.append("and exists (select * from T_SHE_MarketUnitControl where forgUnitId = '"+orgUnit.getId()+"' and fcontrolerid = '"+currSaleMan.getId()+"') ");
		bufferSql.append(" union ");
		bufferSql.append("select * from (");
		bufferSql.append("select MuSp.fsellProjectId,MuMember.fmemberid  ");//非管控人员的情况下：1作为负责人：当前组织下的负责的启用团队中的所有成员
		bufferSql.append("from T_SHE_MarketingUnitMember MuMember ");
		bufferSql.append("left join T_SHE_MarketingUnit MuUnit on MuUnit.fid =MuMember.fheadId ");
		bufferSql.append("left join T_SHE_MarketingUnitSellProject MuSp on MuSp.fheadid = MuUnit.fid ");
		bufferSql.append("where MuMember.FHeadID in ( ");
		bufferSql.append("select MuMember.fheadid from T_SHE_MarketingUnitMember MuMember ");
		bufferSql.append("left join T_SHE_MarketingUnit MuUnit on MuUnit.fid =MuMember.fheadId ");
		bufferSql.append("where MuMember.fmemberid = '"+currSaleMan.getId()+"' and MuUnit.forgUnitId = '"+orgUnit.getId()+"' ");
		bufferSql.append("and MuMember.faccessionDate <= getDate() and (MuMember.fdimissionDate is null or MuMember.fdimissionDate >= getDate())  )   ");
		bufferSql.append(") NotControler  where not exists ( ");
		bufferSql.append("select * from T_SHE_MarketUnitControl where forgUnitId = '"+orgUnit.getId()+"' and fcontrolerid = '"+currSaleMan.getId()+"')");
		bufferSql.append(") AllMuUserId where fsellProjectId = '"+spInfo.getId()+"'");
		return bufferSql.toString();
	}
	private Set getShareCustomerIdSet(SellProjectInfo sellProject) throws EASBizException, BOSException{
		Set id = new HashSet();
		
		Set saleMan=SHEManageHelper.getPermitSaleManSet(sellProject,permit);
		
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("customer.id");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("user.id", saleMan, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("customer.sellProject.id", sellProject.getId().toString()));
		view.setFilter(filter);
		view.setSelector(sel);
		
		SharePropertyCollection shareProColl = SharePropertyFactory.getRemoteInstance().getSharePropertyCollection(view);
		for(int i = 0;i<shareProColl.size();i++){
			SharePropertyInfo info=(SharePropertyInfo)shareProColl.get(i);
			if(info.getCustomer()!= null){
				id.add(info.getCustomer().getId().toString());
			}
		}
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id", saleMan,CompareType.INCLUDE));
		sel=new SelectorItemCollection();
		sel.add("id");
		view.setFilter(filter);
		view.setSelector(sel);
		

		SHECustomerCollection customerColl = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
		for(int i = 0;i<customerColl.size();i++){
			id.add(customerColl.get(i).getId().toString());
		}
		return id;
	}
	/**
	 * 查询满足条件的记录 当前置业顾问、所选项目、共享到当前置业顾问、共享到当前项目
	 * 
	 * @param sellProjectInfo
	 * @return
	 * @throws BOSException
	 * @throws EASBizException 
	 */
	private Set getCustomerId(SellProjectInfo sellProjectInfo)
			throws BOSException, EASBizException {
		Set rs = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
//		if(isPrivateValue){
		//	UserInfo propertyConsultant = SysContext.getSysContext().getCurrentUserInfo();
			Set propertyConsultantSet = this.getUserIdSet(sellProjectInfo);//登录人能看到的id集合
			
			
			//共享置业顾问是当前置业顾问的
	//		if(!isDutyMember()){
	//			filter.getFilterItems().add(new FilterItemInfo("user.id", propertyConsultant.getId(), CompareType.EQUALS));
	//		}else{
				filter.getFilterItems().add(new FilterItemInfo("user.id", propertyConsultantSet, CompareType.INCLUDE));
	//		}
			view.setFilter(filter);
			SharePropertyCollection shareProColl = SharePropertyFactory.getRemoteInstance().getSharePropertyCollection(view);
			Set propertyCustomerSet = new HashSet();
			if(shareProColl != null && shareProColl.size() > 0){
				for(int i = 0;i<shareProColl.size();i++){
					SharePropertyInfo  info = (SharePropertyInfo)shareProColl.get(i);
					if(info.getCustomer() != null){
						propertyCustomerSet.add(info.getCustomer().getId());
					}
				}
			}
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", propertyCustomerSet, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectInfo.getId(), CompareType.EQUALS));
			view.setFilter(filter);
			SHECustomerCollection colls = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
			if(colls != null && colls.size() > 0){
				for(int i = 0;i<colls.size();i++){
					rs.add(colls.get(i).getId());
				}
			}
			
			
			//共享项目的
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectInfo.getId(), CompareType.EQUALS));
			view.setFilter(filter);
			ShareSellProjectCollection projectColl = ShareSellProjectFactory.getRemoteInstance().getShareSellProjectCollection(view);
			Set projectCustomerSet = new HashSet();
			if(projectColl != null && projectColl.size() > 0){
				for(int i = 0;i<projectColl.size();i++){
					ShareSellProjectInfo info = (ShareSellProjectInfo)projectColl.get(i);
					if(info.getCustomer() != null){
					    projectCustomerSet.add(info.getCustomer().getId());
					}
				}
			}
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", projectCustomerSet, CompareType.INCLUDE));
	//		if(!isDutyMember()){
	//			filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id", propertyConsultant.getId(), CompareType.EQUALS));
	//		}else{
				filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id", propertyConsultantSet, CompareType.INCLUDE));
	//		}
			view.setFilter(filter);
			SHECustomerCollection coll = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
			if(projectColl != null && coll.size() > 0){
				for(int i = 0;i<coll.size();i++){
					rs.add(coll.get(i).getId());
				}
			}
			
			//当前置业顾问，当前项目的
			view = new EntityViewInfo();
			filter = new FilterInfo();
	//		if(!isDutyMember()){
	//			filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id", propertyConsultant.getId(), CompareType.EQUALS));
	//		}else{
				filter.getFilterItems().add(new FilterItemInfo("propertyConsultant.id", propertyConsultantSet, CompareType.INCLUDE));
	//		}
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectInfo.getId(), CompareType.EQUALS));
			view.setFilter(filter);
			SHECustomerCollection customerColl = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
			if(customerColl != null && customerColl.size() > 0){
				for(int i = 0;i<customerColl.size();i++){
					rs.add(customerColl.get(i).getId());
				}
			}
			return rs;
//		}
//		else{
//			//公有客户
//			view = new EntityViewInfo();
//			filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectInfo.getId(), CompareType.EQUALS));
//			view.setFilter(filter);
//			SHECustomerCollection customerColl = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
//			if(customerColl != null && customerColl.size() > 0){
//				for(int i = 0;i<customerColl.size();i++){
//					rs.add(customerColl.get(i).getId());
//				}
//			}
//			return rs;
//		}
	}

	private Map linkedCus;

	/**
	 * 新增
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		if (linkedCus != null) {
			linkedCus.clear();
		}
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			Object object = node.getUserObject();
			if (object != null && object instanceof SellProjectInfo) {
				SellProjectInfo sellProjectInfo = (SellProjectInfo) object;
				getUIContext().put("sellProject", sellProjectInfo);				
				linkedCus = CusClientHelper.addNewCusBegin(this, sellProjectInfo.getId().toString());
			}
			
			if(linkedCus != null){
				Boolean isAbort = (Boolean) linkedCus.get("isAbort");
				if(isAbort != null  &&  isAbort.booleanValue()){
					this.abort();
				}
			}
			
		} else {
			MsgBox.showInfo("请选择节点!");
			SysUtil.abort();
		}

		super.actionAddNew_actionPerformed(e);
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		if (linkedCus != null) {
			uiContext.putAll(linkedCus);

			if (linkedCus.get("mainCus") == null) {
				uiContext.remove("mainCus");
				this.getUIContext().remove("mainCus");
			}
		}
	}

	/**
	 * 修改
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
//		SHECustomerInfo info = getSelectInfo();
//		boolean result = info.isIsEnabled();
//		if (result) {
//			isEditOrDelete(OPERATION_EDIT, "修改!");
//		}
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * 删除
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
//		SHECustomerInfo info = getSelectInfo();
//		boolean result = info.isIsEnabled();
//		if (result) {
//			isEditOrDelete(OPERATION_EDIT, "删除!");
//		}
		super.actionRemove_actionPerformed(e);
	}

//	private void isEditOrDelete(String operation, String msg) {
//		if (operation.equals(OPERATION_EDIT)) {
//			FDCMsgBox.showWarning(this, "已经启用，不能" + msg);
//			this.abort();
//		} else if (operation.equals(OPERATION_DELETE)) {
//			FDCMsgBox.showWarning(this, "已经启用，不能" + msg);
//			this.abort();
//		}
//	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		
		List selsIds = getSelectedIdValues();
		for (int i = 0; i < selsIds.size(); i++) {
			String selId = (String) selsIds.get(i);

			SHECustomerInfo info = new SHECustomerInfo();
			info.setId(BOSUuid.read(selId));
			info.setIsEnabled(true);

			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isEnabled");
			SHECustomerFactory.getRemoteInstance().updatePartial(info, selector);
		}
		
		this.refresh(e);
		MsgBox.showInfo("捡回成功");
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List selsIds = getSelectedIdValues();
		for (int i = 0; i < selsIds.size(); i++) {
			String selId = (String) selsIds.get(i);

			SHECustomerInfo info = new SHECustomerInfo();
			info.setId(BOSUuid.read(selId));
			info.setIsEnabled(false);

			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("isEnabled");
			SHECustomerFactory.getRemoteInstance().updatePartial(info, selector);
		}
		
		this.refresh(e);
		MsgBox.showInfo("作废成功");
	}
	
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
//		if(isOrderForClickTableHead() && e.getType() == 0 && e.getButton() == 1 && e.getClickCount() == 1)
//	    {
//			 super.tblMain_tableClicked(e);
//	    }
//		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
//			IRow row = this.tblMain.getRow(e.getRowIndex());
//			Object idObj = row.getCell("id").getValue();
//			if (idObj == null) {
//				return;
//			}
//			String idStr = "";
//			if (idObj instanceof String) {
//				idStr = (String) idObj;
//			} else if (idObj instanceof BOSUuid) {
//				idStr = ((BOSUuid) idObj).toString();
//			}
//			if (!idStr.equals("")) {
//				try {
//					UIContext uiContext = new UIContext(this);
//					uiContext.put(UIContext.ID, idStr);
//					IUIWindow uiWindow = null;
//					if(isOrgData){
//						uiContext.put("isOrgData", "isOrgData");
//						uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(FDCCustomerEditUI.class.getName(), uiContext,null, "VIEW");
//					}else{
//						uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(CustomerRptEditUI.class.getName(), uiContext,null, "VIEW");
//					}
//					uiWindow.show();
//					this.execQuery();
//				} catch (UIException e1) {
//					logger.error(e1.getMessage() + "打开编辑界面失败！");
//				}
//
//			}
//		}
	}

	// 返回选中的行
	private FDCBaseCustomerInfo getSelectInfo() throws EASBizException,
			BOSException {
		List idList = this.getSelectedIdValues();
		if(idList==null){
			FDCMsgBox.showWarning(this,"请先选择行记录!");
		}
		if(idList.size()>0 && idList.get(0) != null){
			Object obj = idList.get(0);
			ObjectUuidPK  pk = new ObjectUuidPK(obj.toString());
			Object objValue = DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk);
			FDCBaseCustomerInfo info = (FDCBaseCustomerInfo)objValue;
			SHECustomerInfo cinfo = (SHECustomerInfo)objValue;
			if(cinfo.isIsPublic()){
				isPublic = true;
			}
	        return info;
		}else{
			return null;
		}
	}

	/**
	 * 商机
	 */
	public void actionCommerceChance_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		CommerceChanceCollection coll = getCommerceChance();
		if (coll != null && coll.size() > 0) {// 如果存在刚打开
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, coll.get(0).getId());
			uiContext.put(CommerceChangeNewEditUI.KEY_DESTORY_WINDOW, Boolean.TRUE);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(CommerceChangeNewEditUI.class.getName(), uiContext,
							null, OprtState.VIEW);
			uiWindow.show();
		} else {// 如果不存在则新增
			showEditUI(CommerceChangeNewEditUI.class.getName());
		}
	}
	
	/**
	 * 判断所选客户是否存在商机
	 * @return
	 * @throws Exception
	 */
	private CommerceChanceCollection getCommerceChance() throws Exception{
		
		List idList = this.getSelectedIdValues();
		if(idList==null){
			FDCMsgBox.showWarning(this,"请先选择行记录!");
		}
		String id = idList.get(0).toString();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("customer.id", id, CompareType.EQUALS));
		view.setSelector(sic);
		view.setFilter(filter);

		CommerceChanceCollection coll = CommerceChanceFactory
				.getRemoteInstance().getCommerceChanceCollection(view);
		
		return coll;
	}

	/**
	 * 跟进
	 * 
	 * @throws Exception
	 * */
	public void actionTrack_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		UIContext uiContext = new UIContext(this);
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("sellProject.*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", getSelectedKeyValue()));
		view.setSelector(sic);
		view.setFilter(filter);

		SHECustomerInfo info = SHECustomerFactory.getRemoteInstance()
				.getSHECustomerCollection(view).get(0);
		
		CommerceChanceCollection coll = getCommerceChance();
		CommerceChanceInfo commerceChance  = new CommerceChanceInfo();
		if (coll != null && coll.size() > 0) {
			commerceChance = (CommerceChanceInfo)coll.get(0);
		}else{
			FDCMsgBox.showWarning(this,"请先填写客户商机！");
			return;
		}
		
		uiContext.put("sheCustomer", info);
		uiContext.put("sellProject", info.getSellProject());
		uiContext.put("SelectCommerceChance", commerceChance);//商机
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
//		IUIWindow curDialog = uiFactory.create(CluesManageTrakcEditUI.class.getName(), uiContext, null,
//				OprtState.ADDNEW);
		IUIWindow curDialog = uiFactory.create(CommerceChanceTrackEditUI.class.getName(), uiContext, null,
				OprtState.ADDNEW);
		curDialog.show();
		
	}

	/**
	 * 填写问卷
	 */
	public void actionQuestionPaper_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		CommerceChanceCollection coll = getCommerceChance();
		if (coll.size()==0) {
			FDCMsgBox.showWarning(this,"请先填写客户商机！");
			return;
		}
		showEditUI(QuestionPaperAnswerEditUI.class.getName());
	}

	/**
	 * 入会
	 * 
	 * @throws Exception
	 * */
	public void actionInsider_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();

		super.actionInsider_actionPerformed(e);
	}

	/**
	 * 客户更名
	 */
	public void actionChangeName_actionPerformed(ActionEvent e)
			throws Exception {
		this.checkSelected();
		List idList = this.getSelectedIdValues();
		FDCBaseCustomerInfo info = this.getSelectInfo();
		if(info instanceof SHECustomerInfo){
			SHECustomerChangeNameEditUI.showUI(this, idList);
		} 
		this.refresh(e);
	}

	/**
	 * 客户更新
	 */
	public void actionUpdate_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		List cusIds = getSelectedIdValues();
		if (confirmUpdate()) {
			FDCBaseCustomerInfo info = this.getSelectInfo();
			if(info instanceof SHECustomerInfo){
				SHECustomerFactory.getRemoteInstance().updateData(cusIds);
			} else if(info instanceof FDCOrgCustomerInfo) {
				FDCOrgCustomerFactory.getRemoteInstance().updateCustomerInfo(cusIds);
			}
			
			MsgBox.showInfo(this, "客户更新成功！");
			this.refreshList();
		}
	}

	private boolean confirmUpdate() {
		if (MsgBox.isYes(MsgBox.showConfirm2(this, "是否确认更新?"))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 共享
	 */
	public void actionShare_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList = this.getSelectedIdValues();
		FDCBaseCustomerInfo info = this.getSelectInfo();
		SellProjectInfo sellProject = info.getSellProject();
		if(info instanceof SHECustomerInfo){
			if(info != null){
				CustomerRptShareEditUI.showUI(this, idList,sellProject);
			}
		} else if(info instanceof FDCOrgCustomerInfo) {
			List cusIds = getSelectedIdValues();
			if(cusIds == null  ||  cusIds.isEmpty()){
				MsgBox.showInfo(this, "请选择记录!");
				this.abort();			
			}
			
			int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
			for (int i = 0; i < selectRows.length; i++) {
				IRow row = tblMain.getRow(selectRows[i]);
				if(row.getCell("isEnabled") == null  ||  !((Boolean)row.getCell("isEnabled").getValue()).booleanValue()){
					MsgBox.showInfo(this, "作废的客户不允许共享！");
					return;
				}
			}
			UIContext uiContext = new UIContext(this);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ShareCustomerUI.class.getName(), uiContext, null, "ADDNEW");
			uiWindow.show();
			
			Map result = uiWindow.getUIObject().getUIContext();
			FullOrgUnitInfo org = (FullOrgUnitInfo) result.get("shareOrg");
			
			if(org == null){
				return ;
			}
			
			FDCOrgCustomerFactory.getRemoteInstance().shareCustomer(cusIds, org.getId().toString());
			MsgBox.showInfo(this, "客户共享成功！");
		}
		
		this.actionRefresh_actionPerformed(e);
	}

	/**
	 * 转交
	 * 
	 * @throws Exception
	 * */
	public void actionDeliver_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		List idList = this.getSelectedIdValues();
		FDCBaseCustomerInfo info = this.getSelectInfo();
		SellProjectInfo sellProject = info.getSellProject();
		if(info instanceof SHECustomerInfo){
			if(info != null){
				CustomerRptDeliverEditUI.showUI(this, idList,sellProject);
			}
		} 
		this.actionRefresh_actionPerformed(e);
	}

	/**
	 * 客户合并
	 */
	public void actionMerge_actionPerformed(ActionEvent e) throws Exception {
		List cusIds = getSelectedIdValues();
		if (cusIds == null || cusIds.size() < 2) {
			MsgBox.showInfo(this, "请选择多条记录!");
			return;
		}

		FDCBaseCustomerInfo tempInfo = this.getSelectInfo();
		if(tempInfo instanceof SHECustomerInfo){
			Set sellProjectSet = new HashSet();
			Set customerTypeSet = new HashSet();
			for(int i =0;i<cusIds.size();i++){ 
				SHECustomerInfo info = SHECustomerFactory.getRemoteInstance().getSHECustomerInfo(new ObjectUuidPK(cusIds.get(i).toString()));
				if(!info.isIsEnabled()){
					MsgBox.showInfo(this, "已作废客户不可以合并！");
					return;
				}else{
					customerTypeSet.add(info.getCustomerType());
					sellProjectSet.add(info.getSellProject());
				}
			}
			if(sellProjectSet != null && sellProjectSet.size() > 1){
				MsgBox.showInfo(this, "客户合并仅限同一项目客户合并！");
			    return;
			}
			if(customerTypeSet != null && customerTypeSet.size() > 1){
				MsgBox.showInfo(this, "客户合并仅限同一类型客户合并！");
			    return;
			}
			
			EntityViewInfo view = CusClientHelper.getEntityView();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", FDCHelper.list2Set(cusIds),
							CompareType.INCLUDE));
			view.setFilter(filter);
			SHECustomerCollection cuses = SHECustomerFactory.getRemoteInstance()
					.getSHECustomerCollection(view);
			IObjectValue cus = CusClientHelper.selectCustomer(this, cuses);
			if (cus == null) {
				return;
			}
			SHECustomerFactory.getRemoteInstance().mergeCustomer(cusIds,
					cus.get("id").toString());
		}
		MsgBox.showInfo(this, "客户合并成功！");
		this.refreshList();
	}

	/**
	 * 导入
	 */
	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("forSHE", Boolean.TRUE);//用来判断是否是客户台账
		 SellProjectInfo sellProjectInfo = null;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
        if (node != null) {
	     Object object = node.getUserObject();
	    if (object != null && object instanceof SellProjectInfo) {
		      sellProjectInfo = (SellProjectInfo) object;
	     }
        }
	    uiContext.put("sellProject", sellProjectInfo);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(ImportFDCOrgCustomerUI.class.getName(), uiContext,
						null, "ADDNEW");
		uiWindow.show();
		this.refreshList();
	}

	/**
	 * 打开各功能新增界面,供各功能调用
	 * 
	 * @param name
	 * @throws Exception
	 */
	public void showEditUI(String name) throws Exception {
		UIContext uiContext = new UIContext(this);
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("sellProject.*");
		sic.add("clues.*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", getSelectedKeyValue()));
		view.setSelector(sic);
		view.setFilter(filter);

		SHECustomerInfo info = SHECustomerFactory.getRemoteInstance()
				.getSHECustomerCollection(view).get(0);
		uiContext.put("sheCustomer", info);
		uiContext.put("sellProject", info.getSellProject());
		uiContext.put(CommerceChangeNewEditUI.KEY_DESTORY_WINDOW, Boolean.TRUE);
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow curDialog = uiFactory.create(name, uiContext, null,
				OprtState.ADDNEW);
		curDialog.show();
	}

	/**
	 * 监听每一笔记录是否可用,从而设置按钮是否可用
	 */
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		// ------------------
		FDCBaseCustomerInfo info = getSelectInfo();
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		boolean isDuty = SellHouseHelper.getPersonIsDuty(info.getSellProject().getId().toString(), user.getId().toString());
		if(isPublic){
//			if(isDuty){
//				btnDeliver.setEnabled(true);
//			}else{
//				btnDeliver.setEnabled(false);
//			}
			//所有人都能进行跟进
			btnDeliver.setEnabled(true);
		}
		// if (!saleOrg.isIsBizUnit()) {
		if(info instanceof SHECustomerInfo){
		if (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString())) {
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
			this.actionCancel.setEnabled(false);
			btnStatus(false);
		} else {
			if(info != null){
				if (info.isIsEnabled()) {
					this.actionCancelCancel.setEnabled(false);
					this.actionCancel.setEnabled(true);

				} else {
					this.actionCancelCancel.setEnabled(true);
					this.actionCancel.setEnabled(false);

				}
			}
//			btnStatus(true);
//			btnEdit.setEnabled(true);
//			btnRemove.setEnabled(true);
			/** wyh
			CommerceChanceCollection coll = getCommerceChance();
			if(coll != null && coll.size() > 0){
				this.actionTrack.setEnabled(true);
			}else{
				this.actionTrack.setEnabled(false);
			}
			*/
		}
		}else if(info instanceof FDCOrgCustomerInfo){
			btnStatus(false);
			this.actionShare.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionImport.setEnabled(false);
		}
	}

	public void btnStatus(boolean isEnabled) {
		this.actionCommerceChance.setEnabled(isEnabled);
		this.btnQuestionPaper.setEnabled(isEnabled);
		// this.actionInsider.setEnabled(isEnabled);
		this.actionChangeName.setEnabled(isEnabled);
		this.actionUpdate.setEnabled(isEnabled);
		this.actionShare.setEnabled(isEnabled);
		this.actionDeliver.setEnabled(isEnabled);
		this.actionMerge.setEnabled(isEnabled);
		// this.actionImport.setEnabled(isEnabled);
	}

	protected DataBaseInfo getBaseDataInfo() {
		return new SHECustomerInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SHECustomerFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return CustomerRptEditUI.class.getName();
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	public boolean isIgnoreRowCount() {
		return false;
	}
	protected void refresh(ActionEvent e) throws Exception{
		this.tblMain.removeRows();
	}
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
	/**
	 * 判断当前用户是否是营销管控人员
	 * @return
	 * @throws BOSException
	 */
	private boolean isMarketUnitControlUser() throws BOSException{
		UserInfo currentUser = SysContext.getSysContext().getCurrentUserInfo();
		FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo();
		MarketUnitControlCollection coll = MarketUnitControlFactory.getRemoteInstance().getMarketUnitControlCollection("where controler.id='"+currentUser.getId().toString()+"' and orgUnit.id = '"+orgUnit.getId().toString()+"'");
		if(coll != null && coll.size() > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 满足条件的置业顾问集合
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private Set getUserIdSet(SellProjectInfo sellProject)throws BOSException, EASBizException{
		Set idSet = new HashSet();
		EntityViewInfo view= NewCommerceHelper.getPermitSalemanView(sellProject,null);
		UserCollection userColl = UserFactory.getRemoteInstance().getUserCollection(view);
		if(userColl != null && userColl.size() > 0){
			for(int i=0;i<userColl.size();i++){
				idSet.add(userColl.get(i).getId());
			}
		}
		//添加自己
		UserInfo currentInfo = SysContext.getSysContext().getCurrentUserInfo();
		idSet.add(currentInfo.getId());
		return idSet;
	}

	
/**
 * 查看公共客户和私有客户的转换按钮功能
 * @author xiaoMin wang
 * 	
 */
	public void actionCusTansform_actionPerformed(ActionEvent e) throws Exception {
//		EntityViewInfo evInfo=new EntityViewInfo();
//		FilterInfo filter=new FilterInfo();
		if(this.btnCusTransform.getText().equals("查看公共客户")){
			//filter.getFilterItems().add(new FilterItemInfo("isPublic",new Boolean(true)));
			//evInfo.setFilter(filter);
			//mainQuery=evInfo;
			btnCusTransform.setText("查看私有客户");
			btnAddNew.setEnabled(false);
			btnRemove.setEnabled(false);
//			btnEdit.setEnabled(false);
			//btnCancel.setEnabled(false);
			//btnCancelCancel.setEnabled(false);
			btnChangeName.setEnabled(false);
			btnCommerceChance.setEnabled(false);
//			btnTrack.setEnabled(false);
			btnQuestionPaper.setEnabled(false);
			btnUpdate.setEnabled(false);
			btnShare.setEnabled(false);
			btnDeliver.setEnabled(true);
			btnMerge.setEnabled(false);
			btnImport.setEnabled(false);
			isPrivateValue = false;
			execQuery();
//			PublicCustomerFacadeFactory.getRemoteInstance().changeToPublicCustomer();

		}else if(this.btnCusTransform.getText().equals("查看私有客户")){
//		    filter.getFilterItems().add(new FilterItemInfo("isPublic",new Boolean(false)));//???????目前数据都是null
//		    filter.getFilterItems().add(new FilterItemInfo("isPublic",null));
//		    evInfo.setFilter(filter);
//		    mainQuery=evInfo;
			btnCusTransform.setText("查看公共客户");
			btnAddNew.setEnabled(true);
			btnRemove.setEnabled(true);
			btnEdit.setEnabled(true);
//			btnCancel.setEnabled(true);
//			btnCancelCancel.setEnabled(true);
			btnChangeName.setEnabled(true);
			btnCommerceChance.setEnabled(true);
			btnTrack.setEnabled(true);
			btnQuestionPaper.setEnabled(true);
			btnUpdate.setEnabled(true);
			btnShare.setEnabled(true);
			btnDeliver.setEnabled(true);
			btnMerge.setEnabled(true);
			btnImport.setEnabled(true);
			isPrivateValue = true;
	 		execQuery();
		}
		this.refresh(null);
	}

	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(orgNode!=null){
   	  	if(orgNode.getUserObject()instanceof OrgStructureInfo){
   	  		MsgBox.showInfo("请选择具体项目！");
   	  		return;
   	  		}
		}
    	 super.actionQuery_actionPerformed(e);
	 
	}	
	
	public void actionAddToSysCustomer_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				AddToSysCustomerUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
	protected void btnShowAll_actionPerformed(ActionEvent e) throws Exception {
		if(isShowAll){
			this.btnShowAll.setText("显示所有客户");
			isShowAll=false;
		}else{
			this.btnShowAll.setText("显示无商机客户");
			isShowAll=true;
		}
		this.refresh(null);
	}
}