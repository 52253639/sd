/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitCollection;
import com.kingdee.eas.basedata.org.SaleOrgUnitFactory;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerCollection;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerFactory;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FDCCustomerListUI extends AbstractFDCCustomerListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCCustomerListUI.class);
    
    /**
     * output class constructor
     */
    public FDCCustomerListUI() throws Exception
    {
        super();
    }

	private void tblMain_afterDataFill(KDTDataRequestEvent e) {
//		int sr = e.getFirstRow();
//		if(e.getFirstRow() > 0){
//			sr = sr - 1;
//		}
//		
//		this.tblMain.getGroupManager().group(sr, e.getLastRow());
	}
    
    public void onLoad() throws Exception {
    	
    	this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener(){
			public void afterDataFill(KDTDataRequestEvent e) {
				tblMain_afterDataFill(e);
			}
		});
    	
    	super.onLoad();
    	this.actionAttachment.setVisible(true);
    	
    	this.treeMain.setModel(SHEHelper.getSaleOrgTree(this.actionOnLoad));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		//集团和为客户管理组织的销售组织节点加粗显著标记
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeMain.getModel().getRoot();
		setBold(root, FDCSysContext.getInstance().getCusMgeMap());
		
		this.treeMain.setSelectionRow(0);
		
		if (!FDCSysContext.getInstance().getCusMgeMap().containsKey(
				SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
			this.actionChangeCusName.setEnabled(false);
			this.actionAttachment.setEnabled(false);
			this.actionUpdateCus.setEnabled(false);
			this.actionShare.setEnabled(false);
			this.actionMerge.setEnabled(false);
			this.actionImport.setEnabled(false);
		}
		
		this.btnChangeCusName.setIcon(EASResource.getIcon("imgTbtn_rename"));
		this.btnUpdateCus.setIcon(EASResource.getIcon("imgIcon_update"));
		this.btnShare.setIcon(EASResource.getIcon("imgTbtn_sealup"));
		this.btnMerge.setIcon(EASResource.getIcon("imgTbtn_merge"));
		this.btnImport.setIcon(EASResource.getIcon("imgTbtn_importexcel"));
		
		this.menuItemChangeCusName.setIcon(EASResource.getIcon("imgTbtn_rename"));
		this.menuItemUpdateCus.setIcon(EASResource.getIcon("imgIcon_update"));
		this.menuItemShare.setIcon(EASResource.getIcon("imgTbtn_sealup"));
		this.menuItemMerge.setIcon(EASResource.getIcon("imgTbtn_merge"));
		this.menuItemImport.setIcon(EASResource.getIcon("imgTbtn_importexcel"));
    }    
    
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		this.tblMain.checkParsed();
		super.tblMain_tableSelectChanged(e);
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row != null) {
			Boolean isEnabled = (Boolean) row.getCell("isEnabled").getValue();

			if (isEnabled.booleanValue()) {// 如果是启用,禁用按钮可用,修改按钮不可用
				actionCancel.setEnabled(true);
				actionCancelCancel.setEnabled(false);
			} else {
				actionCancel.setEnabled(false);
				actionCancelCancel.setEnabled(true);
			}
			
			if (!FDCSysContext.getInstance().getCusMgeMap().containsKey(
					SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
				this.actionCancel.setEnabled(false);
				this.actionCancelCancel.setEnabled(false);
			}
		}
	}
    
    private void setBold(DefaultKingdeeTreeNode node, Map sheOrgMap){
    	Object obj = node.getUserObject();
    	if(obj != null  &&  obj instanceof OrgStructureInfo){
    		String orgId = ((OrgStructureInfo)obj).getUnit().getId().toString();
    		
    		if(OrgConstants.DEF_CU_ID.equals(orgId)  ||  sheOrgMap.containsKey(orgId)){
    			node.setTextBold(true);
    			node.setTextColor(Color.red);
    		}
    	}
    	
    	for(int i=0; i<node.getChildCount(); i++){
    		DefaultKingdeeTreeNode tmpNode = (DefaultKingdeeTreeNode) node.getChildAt(i);
    		
    		setBold(tmpNode, sheOrgMap);
    	}
    }
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			
			if (node != null) {
				String id = ((OrgStructureInfo) node.getUserObject()).getUnit().getId().toString();
				if(!FDCSysContext.getInstance().getCusMgeMap().containsKey(
						SysContext.getSysContext().getCurrentOrgUnit().getId().toString())){
					boolean isCurOrg = id.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
					this.actionAddNew.setEnabled(isCurOrg);
					this.actionEdit.setEnabled(isCurOrg);
					this.actionRemove.setEnabled(isCurOrg);
					this.actionUpdateCus.setEnabled(isCurOrg);
					this.actionMerge.setEnabled(isCurOrg);
					this.actionChangeCusName.setEnabled(isCurOrg);
					this.actionAttachment.setEnabled(isCurOrg);
				}
				
				Set idss = FDCSysContext.getInstance().getCusMgeMap().keySet();
				Set ids = new HashSet();
				for(Iterator itor = idss.iterator(); itor.hasNext(); ){
					ids.add(itor.next());
				}
				
				FilterInfo filter = new FilterInfo();
				if(ids.contains(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())){
//					filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
					filter.getFilterItems().add(new FilterItemInfo("belongUnit.id", id));
				}else{
					//获得其下一级的客户管理组织
					Set ts = getFirstSubCusOrgIds(ids, id);
					if(ts.isEmpty()){
						//下级组织没有客户管理组织的不显示客户记录
						filter.getFilterItems().add(new FilterItemInfo("belongUnit.id", id));
					}else{
						filter.getFilterItems().add(new FilterItemInfo("belongUnit.id", ts, CompareType.INCLUDE));
					}
				}

				viewInfo = (EntityViewInfo) this.mainQuery.clone();
				if (viewInfo.getFilter() != null) {
					viewInfo.getFilter().mergeFilter(filter, "and");
				} else {
					viewInfo.setFilter(filter);
				}
			}
		} catch (Exception e) {
			this.handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
    
    private Set getFirstSubCusOrgIds(Set ids, String id) throws EASBizException, BOSException {
    	SaleOrgUnitInfo saleOrg = SaleOrgUnitFactory.getRemoteInstance().getSaleOrgUnitInfo(new ObjectUuidPK(id));
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("id", ids, CompareType.INCLUDE));
    	filter.getFilterItems().add(new FilterItemInfo("longNumber", saleOrg.getLongNumber()+"%", CompareType.LIKE));
    	view.setFilter(filter);
    	SaleOrgUnitCollection ss = SaleOrgUnitFactory.getRemoteInstance().getSaleOrgUnitCollection(view);
    	
    	Set res = new HashSet();
    	Map map = new HashMap();
    	for(int i=0; i<ss.size(); i++){
    		SaleOrgUnitInfo org = ss.get(i);
    		String longNumber = org.getLongNumber();
    		List toRms = new ArrayList();
    		Set ts = map.keySet();
    		boolean isExcept = false;
    		for(Iterator itor = ts.iterator(); itor.hasNext(); ){
    			String longN = (String) itor.next();
    			if(longN.contains(longNumber)){
    				toRms.add(longN);
    			}else if(longNumber.contains(longN)){
    				isExcept = true;
    				break;
    			}
    		}
    		
    		for(int j=0;j<toRms.size(); j++){
    			map.remove(toRms.get(j));
    		}
    		
    		if(isExcept){
    			continue;
    		}
    		
    		map.put(org.getLongNumber(), org.getId().toString());
    	}
    	
    	for(Iterator itor = map.keySet().iterator(); itor.hasNext(); ){
    		res.add(map.get(itor.next()));
    	}
    	
		return res;
	}

	protected void refresh(ActionEvent e) throws Exception {
    	super.refresh(e);
    	if (!FDCSysContext.getInstance().getCusMgeMap().containsKey(
				SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
			this.actionAttachment.setEnabled(false);
			this.actionChangeCusName.setEnabled(false);
			this.actionUpdateCus.setEnabled(false);
			this.actionShare.setEnabled(false);
			this.actionMerge.setEnabled(false);
			this.actionImport.setEnabled(false);
		}
    }
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	this.refresh(null);
    }
    
	public void beforeActionPerformed(ActionEvent e) {
		if (actionAddNew.getClass().getName().equals(e.getActionCommand())
				|| actionEdit.getClass().getName().equals(e.getActionCommand())
				|| actionRemove.getClass().getName().equals(e.getActionCommand())
				|| actionCancel.getClass().getName().equals(e.getActionCommand())
				|| actionCancelCancel.getClass().getName().equals(e.getActionCommand())
				|| actionChangeCusName.getClass().getName().equals(e.getActionCommand())
				|| actionUpdateCus.getClass().getName().equals(e.getActionCommand())
				|| actionShare.getClass().getName().equals(e.getActionCommand())
				|| actionMerge.getClass().getName().equals(e.getActionCommand())
				|| actionImport.getClass().getName().equals(e.getActionCommand())) {

			if (!FDCSysContext.getInstance().getCusMgeMap().containsKey(
					SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
				MsgBox.showInfo(this, "非客户管理组织不能操作!");
				SysUtil.abort();
			}
		}
		super.beforeActionPerformed(e);
	}
	private FDCOrgCustomerInfo getSelectInfo() throws EASBizException,
			BOSException {
		String idStr = getSelsectRowId();
		if (idStr == null) {
			return null;
		}
		FDCOrgCustomerInfo info = FDCOrgCustomerFactory.getRemoteInstance()
				.getFDCOrgCustomerInfo(new ObjectUuidPK(getSelsectRowId()));
		return info;
	}

	// 获取选 中的行ID
	public String getSelsectRowId() {
		IRow selectRow = KDTableUtil.getSelectedRow(tblMain);
		if (selectRow.getCell("id") == null) {
			return null;
		}
		Object idObj = selectRow.getCell("id").getValue();
		if (idObj == null) {
			return null;
		}
		return idObj.toString();
	}
	/**
	 * 开启自定义过滤界面
	 * 
	 */
	protected boolean initDefaultFilter() {
		return true;// 默认弹出
	}

	/**
	 * 初始化过滤界面---
	 */
    private CommonQueryDialog queryDlg = null;
	protected CommonQueryDialog initCommonQueryDialog() { //覆盖方法
		if (queryDlg != null)
			return queryDlg;
		try {
			queryDlg = new CommonQueryDialog();
			queryDlg.setOwner((Component) getUIContext().get("Owner"));
			queryDlg.setParentUIClassName(getClass().getName());
			queryDlg.setQueryObjectPK(mainQueryPK); //mainQueryPK 决定自定义过滤的字段
			queryDlg.setHeight(350);
			queryDlg.setWidth(570);
			queryDlg.addUserPanel(getFilterUI());//加入过滤界面
			queryDlg.setTitle("客户资料滤界面");
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
	FDCCustomerFilterUI filterUI;
	public FDCCustomerFilterUI getFilterUI() throws Exception {
		if (filterUI == null)
			filterUI = new FDCCustomerFilterUI();
		return filterUI;
	}

   /**
    * 被下级客户管理组织或项目引用，不能禁用
    */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		
		FDCOrgCustomerInfo info = getSelectInfo();
		FDCMainCustomerInfo  mainInfo = info.getMainCustomer();
		String orgId = info.getBelongUnit().getId().toString();
		FullOrgUnitInfo orgInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(orgId));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longNumber", orgInfo.getLongNumber()+"%",CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("id", orgInfo.getId(),CompareType.NOTEQUALS));
		view.setFilter(filter);
		FullOrgUnitCollection orgColl = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection(view);
		if(orgColl != null && orgColl.size()>0){
			for(int i=0;i<orgColl.size();i++){
				FullOrgUnitInfo tempInfo = orgColl.get(i);
				view = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE,CompareType.EQUALS));
				filter.getFilterItems().add(new FilterItemInfo("belongUnit.id", tempInfo.getId(),CompareType.EQUALS));
				filter.getFilterItems().add(new FilterItemInfo("mainCustomer.id", mainInfo.getId(),CompareType.EQUALS));
				view.setFilter(filter);
				FDCOrgCustomerCollection coll = FDCOrgCustomerFactory.getRemoteInstance().getFDCOrgCustomerCollection(view);
				if(coll != null && coll.size()>0){
					MsgBox.showInfo(this, "被下级客户管理组织引用，不能作废！");
					this.abort();
				}
			}
		}
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("mainCustomer.id", mainInfo.getId(),CompareType.EQUALS));
		view.setFilter(filter);
		SHECustomerCollection sheColl = SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
		if(sheColl != null && sheColl.size()>0){
			MsgBox.showInfo(this, "被下级项目引用，不能作废！");
			this.abort();
		}
		super.actionCancel_actionPerformed(e);
		this.refresh(e);
	}
	
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		cancelCancel();
		this.refresh(e);
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return FDCOrgCustomerFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return FDCCustomerEditUI.class.getName();
	}

	public void actionChangeCusName_actionPerformed(ActionEvent e) throws Exception {
		List cusIds = getSelectedIdValues();
		if(cusIds == null  ||  cusIds.size() != 1){
			MsgBox.showInfo(this, "请选择一条记录操作!");
			this.abort();
		}
		
		FDCOrgCustomerInfo cus = FDCOrgCustomerFactory.getRemoteInstance().getFDCOrgCustomerInfo(new ObjectUuidPK((String)cusIds.get(0)));
		
		UIContext uiContext = new UIContext(this);
		uiContext.put("srcCus", cus);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ChangeCusNameUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();
		
		Map result = uiWindow.getUIObject().getUIContext();
		
		if(result.get("continue") == null  ||  !((Boolean)result.get("continue")).booleanValue()){
			return;
		}
		
		String newName = (String) result.get("newName");
		FDCOrgCustomerFactory.getRemoteInstance().changeCusName(cus.getId().toString(), newName);
		
		MsgBox.showInfo(this, "客户更名成功！");
		this.refreshList();
	}
	
	private boolean confirmUpdate() {
		if (MsgBox.isYes(MsgBox.showConfirm2(this, "是否确认更新?"))) {
			return true;
		} else {
			return false;
		}
	}
	public void actionUpdateCus_actionPerformed(ActionEvent e) throws Exception {
		List cusIds = getSelectedIdValues();
		if (confirmUpdate()) {
			FDCOrgCustomerFactory.getRemoteInstance().updateCustomerInfo(cusIds);
			MsgBox.showInfo(this, "客户更新成功！");
			this.refreshList();
		}
	}
	
	public void actionShare_actionPerformed(ActionEvent e) throws Exception {
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
//		uiContext.put("ID", adjustPriceBillId);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ShareCustomerUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();
		
		Map result = uiWindow.getUIObject().getUIContext();
		
		FullOrgUnitInfo org = (FullOrgUnitInfo) result.get("shareOrg");
		
		if(org == null){
			return ;
		}
		
		FDCOrgCustomerFactory.getRemoteInstance().shareCustomer(cusIds, org.getId().toString());
		
		MsgBox.showInfo(this, "客户共享成功！");
		this.refreshList();
	}
	
	public void actionMerge_actionPerformed(ActionEvent e) throws Exception {
		List cusIds = getSelectedIdValues();
		if(cusIds == null  ||  cusIds.size() < 2){
			MsgBox.showInfo(this, "请选择多条记录!");
			this.abort();
		}
		

		Set orgSet = this.getOrgUnitSet(cusIds);
//		EntityViewInfo view = new EntityViewInfo();
		EntityViewInfo view = CusClientHelper.getEntityView();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.list2Set(cusIds), CompareType.INCLUDE));
		view.setFilter(filter);
		FDCOrgCustomerCollection cuses = FDCOrgCustomerFactory.getRemoteInstance().getFDCOrgCustomerCollection(view);
		boolean isMerge = false;
		Set customerTypeSet = new HashSet();
		for(int i = 0;i < cuses.size(); i++){
			FDCOrgCustomerInfo orgCustomerInfo = cuses.get(i);
			if(!orgCustomerInfo.isIsEnabled()){
				MsgBox.showInfo(this, "已作废客户不可以合并！");
				return;
			}else{
				customerTypeSet.add(orgCustomerInfo.getCustomerType());
			}
    		String orgUnitId = orgCustomerInfo.getCreateUnit().getId().toString();
    		Iterator it = orgSet.iterator();
    		while(it.hasNext()){
    			String tempUnitId = it.next().toString();
    			if(orgUnitId.equals(tempUnitId)){
    				isMerge = true;
    				break;
    			}
    		}
    		if(!isMerge){
    			MsgBox.showInfo(this, "客户合并仅限同一组织客户合并！");
    		    return;
    		}
    		if(customerTypeSet != null && customerTypeSet.size() > 1){
    			MsgBox.showInfo(this, "客户合并仅限同一类型客户合并！");
    		    return;
    		}
		}
		
		IObjectValue cus = CusClientHelper.selectCustomer(this, cuses);
		if(cus == null){
			return;
		}
		FDCOrgCustomerFactory.getRemoteInstance().mergeCustomer(cusIds, (String) cus.get("id").toString());
		
		MsgBox.showInfo(this, "客户合并成功！");
		this.refreshList();
	}
	
	/**
	 * 以第1 条为基准，先查出第1条记录所属组织，再由这个组织LongNumber去查它的所有上下级，放到一个Set集合里
	 * 如果其他记录所属组织的id不在集合里，就提示不能合并
	 * @param cusIds
	 * @return
	 * @throws Exception
	 */
	private Set getOrgUnitSet(List cusIds) throws Exception{
		Set orgSet = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("createUnit.id");
		sic.add("createUnit.longNumber");
		filter.getFilterItems().add(new FilterItemInfo("id", cusIds.get(0).toString(), CompareType.EQUALS));
		view.setFilter(filter);
		view.setSelector(sic);
		FDCOrgCustomerInfo info = FDCOrgCustomerFactory.getRemoteInstance().getFDCOrgCustomerCollection(view).get(0);

		view = new EntityViewInfo();
		filter = new FilterInfo();
		String longNumber = info.getCreateUnit().getLongNumber();
		String numbers[] = longNumber.split("!");
		filter.getFilterItems().add(new FilterItemInfo("longNumber", "%"+longNumber+"%", CompareType.LIKE));
		for(int i =0; i<numbers.length;i++){
			FilterInfo parentFilter = new FilterInfo();
			parentFilter.getFilterItems().add(
					new FilterItemInfo("number",
							numbers[i]));
			parentFilter
					.mergeFilter(parentFilter, "OR");
			filter.mergeFilter(parentFilter, "OR");
		}
		view.setFilter(filter);
		FullOrgUnitCollection orgUnitColl = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection(view);
		
		if(orgUnitColl != null && orgUnitColl.size() > 0){
			for(int j=0;j<orgUnitColl.size();j++){
				orgSet.add(orgUnitColl.get(j).getId());
			}
		}
		return orgSet;
	}
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	private Map linkedCus;
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		if(linkedCus != null){
			linkedCus.clear();
		}
		
		FilterItemInfo scope = new FilterItemInfo("belongUnit.id", SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
		linkedCus = CusClientHelper.addNewCusBegin2(this, FDCOrgCustomerFactory.getRemoteInstance(), scope);
		if(linkedCus != null){
			Boolean isAbort = (Boolean) linkedCus.get("isAbort");
			if(isAbort != null  &&  isAbort.booleanValue()){
				this.abort();
			}
		}
		
		super.actionAddNew_actionPerformed(e);
	}
	
	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
//		uiContext.put("ID", adjustPriceBillId);
		uiContext.put("foSHE", Boolean.FALSE);//add by  youzhen,20110801. 用来判断是否是客户台账
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ImportFDCOrgCustomerUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();
		this.refreshList();
	}
	
	/**
	 * 删除
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List ids = getSelectedIdValues();
		if(CusClientHelper.beforeRemove(this,ids)){
			super.actionRemove_actionPerformed(e);
		}
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		if(linkedCus != null){
			uiContext.putAll(linkedCus);
			
			if(linkedCus.get("mainCus") == null){
				uiContext.remove("mainCus");
				this.getUIContext().remove("mainCus");
			}
		}
		
		uiContext.put("isCurOrg", new Boolean(this.actionAddNew.isEnabled()));
	}
	
    protected String getEditUIModal() {
    	return UIFactoryName.MODEL;
    }
	
}
