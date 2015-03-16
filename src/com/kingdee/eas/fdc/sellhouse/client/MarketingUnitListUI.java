/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.UserType;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientCtrler;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlCollection;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlFactory;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MarketingUnitListUI extends AbstractMarketingUnitListUI {
	private static final Logger logger = CoreUIObject.getLogger(MarketingUnitListUI.class);
	private Map cancelMap =new HashMap();
	private Map  orgMap = FDCSysContext.getInstance().getOrgMap();
	
	public MarketingUnitListUI() throws Exception {
		super();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MarketingUnitFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return MarketingUnitEditUI.class.getName();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	private void initTree() throws Exception {
		DefaultKingdeeTreeNode oldSelected = null;
		Object objTree = this.treeMain.getLastSelectedPathComponent();
		if (objTree != null) {
			oldSelected = (DefaultKingdeeTreeNode) objTree;
		}
		this.treeMain.setModel(SHEMarketingunitHelper.getMarketTree(actionOnLoad, false));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		if (oldSelected != null)
			this.treeMain.setSelectionNode(oldSelected);
		else
			this.treeMain.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());

	}

	public void onLoad() throws Exception {
		super.onLoad();
		//OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
		this.muControlQuery = new EntityViewInfo();
		this.tblControl.getStyleAttributes().setLocked(true);
		this.tblControl.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		initTree();
		
		String[] fields=new String[this.tblControl.getColumnCount()];
		for(int i=0;i<this.tblControl.getColumnCount();i++){
			fields[i]=this.tblControl.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(this.tblControl,fields);
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		TreePath newPath = e.getNewLeadSelectionPath();
		if (newPath == null)
			return;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) newPath.getLastPathComponent();
		if (node == null) {
			return;
		}
		String  orgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo orgStrInfo = (OrgStructureInfo) node.getUserObject();
			if(orgId.equals(orgStrInfo.getUnit().getId().toString())) {//存在多级组织时 点击的节点是当前组织才能添加
					this.btnAddUser.setEnabled(true);
					this.btnDeleteUser.setEnabled(true);
					String id = orgStrInfo.getUnit().getId().toString();
					if (orgMap.containsKey(id)) {// 售楼组织节点
						this.actionAddNew.setEnabled(true);
						this.actionEdit.setEnabled(true);
						this.actionRemove.setEnabled(true);
					} else {
						this.actionAddNew.setEnabled(false);
						this.actionEdit.setEnabled(false);
						this.actionRemove.setEnabled(false);
					}
				
			}else {
				this.btnAddUser.setEnabled(false);
				this.btnDeleteUser.setEnabled(false);
				this.actionAddNew.setEnabled(false);
				this.actionEdit.setEnabled(false);
				this.actionRemove.setEnabled(false);
			}
			this.getUIContext().put("OrgUnitInfo", SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
			this.getUIContext().put("MarketingUnitInfo", null);
			this.tblControl.removeRows();
		} else if (node.getUserObject() instanceof MarketingUnitInfo) {
			String id = ((MarketingUnitInfo)node.getUserObject()).getOrgUnit().getId().toString();
			if(orgId.equals(id)) {//是登陆组织节点才有效
				if (orgMap.containsKey(id)) {// 售楼组织节点
					this.actionAddNew.setEnabled(true);
					this.actionEdit.setEnabled(true);
					this.actionRemove.setEnabled(true);
				} else {
					this.actionAddNew.setEnabled(false);
					this.actionEdit.setEnabled(false);
					this.actionRemove.setEnabled(false);
				}
			}else {
				this.actionAddNew.setEnabled(false);
				this.actionEdit.setEnabled(false);
				this.actionRemove.setEnabled(false);
			}
			this.btnAddUser.setEnabled(false);
			this.btnDeleteUser.setEnabled(false);
			MarketingUnitInfo muInfo = (MarketingUnitInfo) node.getUserObject();
			this.getUIContext().put("MarketingUnitInfo", muInfo);
//			DefaultKingdeeTreeNode orgStrNode = (DefaultKingdeeTreeNode) FDCTreeHelper.getAllObjectIdMap(
//					(TreeNode) this.treeMain.getModel().getRoot(), "OrgStructure").get(
//					muInfo.getOrgUnit().getId().toString());
			this.getUIContext().put("OrgUnitInfo", muInfo.getOrgUnit());
		}
		this.execQuery();
	}

	public void actionAddControler_actionPerformed(ActionEvent e)
			throws Exception {	
		super.actionAddControler_actionPerformed(e);
		

		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
		if (proNode != null && proNode.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo orgStrInfo = (OrgStructureInfo) proNode.getUserObject();
			FullOrgUnitInfo orgUnitInfo=FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(orgStrInfo.getUnit().getId()));
			UIContext uiContext = new UIContext(this);
			uiContext.put("OrgUnitInfo", orgUnitInfo);
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isDelete", Boolean.FALSE));
			filter.getFilterItems().add(new FilterItemInfo("isLocked", Boolean.FALSE));
			filter.getFilterItems().add(new FilterItemInfo("isForbidden", Boolean.FALSE));
			filter.getFilterItems().add(new FilterItemInfo("type", new Integer(UserType.PERSON_VALUE)));
			filter.getFilterItems().add(new FilterItemInfo("type",new Integer(UserType.OTHER_VALUE)) );
			filter.setMaskString("#0 and #1 and #2 and (#3 or #4)");
			view.setFilter(filter);
			KDCommonPromptDialog dlg = CommerceHelper.getANewCommonDialog(this,"com.kingdee.eas.base.permission.app.F7UserQuery", true, view);
			dlg.show();
			Object[] objects = (Object[])dlg.getData();
			if(objects!=null){
				//售楼组织下营销团队中 已存在的系统用户，则不允许增加为营销管控人员
				Set idset = new HashSet();
				for(int i = 0;i<objects.length;i++){
					UserInfo user = (UserInfo)objects[i];
					idset.add(user.getId().toString());
				}
				EntityViewInfo mView = new EntityViewInfo();
				FilterInfo mFilter = new FilterInfo();
				mView.setFilter(mFilter);
				mFilter.getFilterItems().add(new FilterItemInfo("member.id",idset,CompareType.INCLUDE));
				mFilter.getFilterItems().add(new FilterItemInfo("head.orgUnit",orgUnitInfo.getId().toString()));
				mFilter.getFilterItems().add(new FilterItemInfo("head.isEnabled",new Boolean(true)));
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add("member.*");
				mView.setSelector(sel);
				MarketingUnitMemberCollection memeColl = MarketingUnitMemberFactory.getRemoteInstance().getMarketingUnitMemberCollection(mView);
				StringBuffer sb = new StringBuffer();
					if(memeColl.size()>0){
						Map temp  = new TreeMap();
						for(int i = 0 ; i <memeColl.size();i++){
							UserInfo user = memeColl.get(i).getMember();
							if(!temp.containsKey(user.getId().toString())){
								if(i>0){
									sb.append(",");
								}
								temp.put(user.getId().toString(),new Integer(i));
								sb.append(memeColl.get(i).getMember().getName());
							}
						
						}
					MsgBox.showWarning(this,"所选用户: "+sb.toString()+" 在当前售楼组织下的营销团队中 已存在！");
					return;
				}
				for(int i = 0;i<objects.length;i++){
					MarketUnitControlInfo mcInfo = new MarketUnitControlInfo();
					UserInfo user = (UserInfo)objects[i];
					mcInfo.setControler(user);
					mcInfo.setOrgUnit(orgUnitInfo);
					mcInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
					mcInfo.setCreateTime(new Timestamp(new Date().getTime()));
					MarketUnitControlFactory.getRemoteInstance().addnew(mcInfo);
				}			
			}
			
			this.tblControl.refresh();
		}
	
	}
	public void actionDeleteControler_actionPerformed(ActionEvent e)
			throws Exception {
		if ((this.tblControl.getSelectManager().size() == 0)){
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
			return;
		}
		if (MsgBox.showConfirm2("确认要删除该管控用户吗？") == MsgBox.OK) {
			KDTSelectManager selectManager = this.tblControl.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			Set indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (this.tblControl.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
					return;
				}
				for (int i = top; i <= bottom; i++) {
					indexSet.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
			if (indexArr == null){
				return;
			}
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				String idStr = (String) this.tblControl.getRow(rowIndex).getCell("id").getValue();
				MarketUnitControlFactory.getRemoteInstance().delete(new ObjectUuidPK(BOSUuid.read(idStr)));
				this.tblControl.removeRow(rowIndex);
			}
			if (this.tblControl.getRow(0) != null){
				this.tblControl.getSelectManager().select(0, 0);
			}
			this.tblControl.refresh();
		}
	}
	protected void btnAddUser_actionPerformed(ActionEvent e) throws Exception {}

	protected void btnDeleteUser_actionPerformed(ActionEvent e) throws Exception {

	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {

		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();

		if (proNode == null)
			proNode = (DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot();
		String orgUnitIdStr = "";
		String orgUnitIdStr2 = "'nullnull'";
		FilterInfo muFilter = null;
		if (proNode.getUserObject() instanceof OrgStructureInfo) {
			Map orgStrMap = FDCTreeHelper.getAllObjectIdMap(proNode, "OrgStructure");
			orgUnitIdStr = FDCTreeHelper.getStringFromSet(orgStrMap.keySet());
			orgUnitIdStr2 = orgUnitIdStr;
		} else if (proNode.getUserObject() instanceof MarketingUnitInfo) {
			MarketingUnitInfo muInfo = (MarketingUnitInfo) proNode.getUserObject();
			orgUnitIdStr = "'"+muInfo.getOrgUnit().getId().toString()+"'";
			//获取该节点的所有下级节点的ID
			Map  idSetMap = SHEMarketingunitHelper.getAllObjectIdMap(proNode, "MarketingUnit");
			String idString = SHEMarketingunitHelper.getStringFromSet(idSetMap.keySet());
			muFilter = new FilterInfo();
			muFilter.getFilterItems().add(new FilterItemInfo("id",idString,CompareType.INNER));
		}

		FilterInfo filter = new FilterInfo();
		if (queryPK.equals(this.mainQueryPK)) {
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", orgUnitIdStr, CompareType.INNER));
			if (muFilter != null) {
				try {
					filter.mergeFilter(muFilter, "AND");
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		} 
		else if (queryPK.equals(this.muControlQueryPK)) {
			viewInfo = (EntityViewInfo) this.muControlQuery.clone();
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", orgUnitIdStr2, CompareType.INNER));
		}

		if (viewInfo.getFilter() == null)
			viewInfo.setFilter(filter);
		else {
			try {
				viewInfo.getFilter().mergeFilter(filter, "AND");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		initTree();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String idKey = this.getSelectedKeyValue();
		if (idKey == null)
			return;
		if (MarketingUnitFactory.getRemoteInstance().exists("where parent.id = '" + idKey + "'")) {
			MsgBox.showInfo("非明细营销单元节点不能直接删除！");
			return;
		}
		if (MarketingUnitFactory.getRemoteInstance().exists("where isEnabled = 1 and id = '" + idKey + "'")) {
			MsgBox.showInfo("处于启用状态的营销单元节点不能直接删除！");
			return;
		}
		
		super.actionRemove_actionPerformed(e);
		initTree();
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		setIsEnable(true);
	}
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		setIsEnable(false);
	}
	
	protected void setIsEnable(boolean flag) throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
		String id = tblMain.getRow(activeRowIndex).getCell("id").getValue().toString().trim();
		String message = null;
		if(!flag){
				//禁用时下级是否禁用
			Map idNodeMap = SHEMarketingunitHelper.getAllObjectIdMap((DefaultKingdeeTreeNode)this.treeMain.getModel().getRoot(), "MarketingUnit");
			DefaultKingdeeTreeNode currNode = (DefaultKingdeeTreeNode)idNodeMap.get(id);
			Map resutMap = SHEMarketingunitHelper.getAllObjectIdMap(currNode, "MarketingUnit");
			if(resutMap.keySet().size()>1){
				int result = FDCMsgBox.showConfirm2("操作禁用的营销团队有下级团队，禁用后下级营销团队也会被禁用，是否继续？");
				if(result ==2){
					this.abort();
				}else{
					
/* 					String param = FDCTreeHelper.getStringFromSet(resutMap.keySet());
					FDCSQLBuilder build = new FDCSQLBuilder();
					String sql = "update t_she_marketingunit set fisEnabled = 0 where fid in ("+param +")";
					build.appendSql(sql);
					build.executeUpdate();*/

					
					Iterator keySet = resutMap.keySet().iterator(); 
					while(keySet.hasNext()){
						String idStr = (String)keySet.next();
						MarketingUnitInfo info = new MarketingUnitInfo();
						info.setId(BOSUuid.read(idStr));
						info.setIsEnabled(flag);
						SelectorItemCollection sic = new SelectorItemCollection();
						sic.add(new SelectorItemInfo("isEnabled"));
						getBizInterface().updatePartial(info, sic);
					}
				}
			} else {//沒有下級就禁用本身
				MarketingUnitInfo info = new MarketingUnitInfo();
				info.setId(BOSUuid.read(id));
				info.setIsEnabled(flag);
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("isEnabled"));
				getBizInterface().updatePartial(info, sic);
			}
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK");
		} else { //启用时 如果上级没有启用则不能启用
			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("*");
			sel.add("parent.*");
			sel.add("sellProject.*");
			sel.add("member.*");
			MarketingUnitInfo muInfo = MarketingUnitFactory.getRemoteInstance().getMarketingUnitInfo(new ObjectUuidPK(id),sel);
			if(muInfo.getParent()!=null){
				if(!muInfo.getParent().isIsEnabled()){
					FDCMsgBox.showWarning("营销团队的上级没有启用，下级不能启用！");
					this.abort();
				}
			}
			//效验在同一售楼组织内，不允许管理相同项目的营销团队成员重复,同时也不能是该组织的管控人员
			MarketingUnitFactory.getRemoteInstance().checkMemeberOfSameSellPorject(muInfo);
			
			MarketingUnitInfo info = new MarketingUnitInfo();
			info.setId(BOSUuid.read(id));
			info.setIsEnabled(flag);
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("isEnabled"));
			getBizInterface().updatePartial(info, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK");
		}
		setMessageText(message);
		showMessage();
		tblMain.refresh();
		loadFields();
		initTree();
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}
	
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)	throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(activeRowIndex);
		if(row!=null){
			if(((Boolean)row.getCell("isEnable").getValue()).booleanValue()){
				this.btnCancelCancel.setEnabled(false);
				this.btnCancel.setEnabled(true);
				this.btnRemove.setEnabled(false);
				this.btnEdit.setEnabled(false);
			}else{
				this.btnCancelCancel.setEnabled(true);
				this.btnCancel.setEnabled(false);
				this.btnRemove.setEnabled(true);
				this.btnEdit.setEnabled(true);
			}
		}
	}
	
	
	protected String[] getLocateNames() {
		 String[] locateNames = new String[6];
	        locateNames[0] = IFWEntityStruct.dataBase_Number;
	        locateNames[1] = IFWEntityStruct.dataBase_Name;
	        locateNames[2] = "creator.name";
	        locateNames[3] = "createTime";
	        locateNames[4] ="lastUpdateUser.name";
	        locateNames[5] = "lastUpdateTime";
	        return locateNames;
	}
	
	protected void refresh(ActionEvent e) throws Exception {
		super.refresh(e);
		initTree();
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put("isCanCancel", isCanCancel());
		uiContext.putAll(cancelMap);
	}
	
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		//能否在编辑界面能禁用
		super.actionView_actionPerformed(e);
	}

	private Boolean isCanCancel() {
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0){
			return Boolean.TRUE;
		}	
		String id = tblMain.getRow(activeRowIndex).getCell("id").getValue().toString().trim();
		//禁用时下级是否禁用
		Map idNodeMap = SHEMarketingunitHelper.getAllObjectIdMap((DefaultKingdeeTreeNode)this.treeMain.getModel().getRoot(), "MarketingUnit");
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)idNodeMap.get(id);
		Map resutMap = SHEMarketingunitHelper.getAllObjectIdMap(root, "MarketingUnit");
		cancelMap.clear();
		if(resutMap.keySet().size()>1){
			cancelMap.put("cancelMap", resutMap);
			return  Boolean.FALSE;
		}
		
		return  Boolean.TRUE;
	}
	
}