/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.Icon;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.bibench.platform.ui.util.MsgBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.client.helper.OrgHelper;
import com.kingdee.eas.base.permission.util.ToolUtils;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.client.f7.OUUnionF7;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.CostAcctOrgAssignCollection;
import com.kingdee.eas.fdc.aimcost.CostAcctOrgAssignFactory;
import com.kingdee.eas.fdc.aimcost.CostAcctOrgAssignInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFacade;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.sun.rmi.rmid.ExecPermission;

/**
 * 首创售前Demo output class name
 */
public class CostAcctAssignUI extends AbstractCostAcctAssignUI {
	private static final Logger logger = CoreUIObject.getLogger(CostAcctAssignUI.class);

	/**
	 * output class constructor
	 */
	public CostAcctAssignUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output btnAddAll_actionPerformed method
	 */
	protected void btnAddAll_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.btnAddAll_actionPerformed(e);
		assignedMap.clear();
		assignedMap.putAll(acctNumberMap);
		fillAssignTable();
	}

	/**
	 * output btnAdd_actionPerformed method
	 */
	protected void btnAdd_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.btnAdd_actionPerformed(e);
		IRow row = KDTableUtil.getSelectedRow(tblAcct);
		CostAccountInfo info = (CostAccountInfo) row.getUserObject();
		addCostAcctToAssignedMap(info.getId().toString());
		fillAssignTable();
	}

	/**
	 * output btnDelete_actionPerformed method
	 */
	protected void btnDelete_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.btnDelete_actionPerformed(e);
		IRow row = KDTableUtil.getSelectedRow(tblAssignAcct);
		CostAccountInfo info = (CostAccountInfo) row.getUserObject();
		deleteCostAcctToAssignedMap(info.getId().toString());
		fillAssignTable();
	}

	/**
	 * output btnDeleteAll_actionPerformed method
	 */
	protected void btnDeleteAll_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.btnDeleteAll_actionPerformed(e);
		assignedMap.clear();
		fillAssignTable();
	}

	private void initOrgF7() throws EASBizException, BOSException {

		OUUnionF7 orgF7 = new OUUnionF7(this);
		IObjectPK curUserPK = ToolUtils.getCurrentUserPK();
		OrgHelper.setF7ByUser(orgF7, curUserPK);

		orgF7.disableTheOUWhichIsOnlyAdmin();
		orgF7.setMultiSelect(false);

		prmtOrg.setSelector(orgF7);
		prmtOrg.setEditable(false);
	}

	protected void initWorkButton() {
		super.initWorkButton();

		this.btnAdd.setText(null);
		Icon addIcon = EASResource.getIcon("imgTbtn_move_right");
		this.btnAdd.setIcon(addIcon);
		Icon addAllIcon = EASResource.getIcon("imgTbtn_moveall_right");
		this.btnAddAll.setIcon(addAllIcon);
		Icon deleteIcon = EASResource.getIcon("imgTbtn_move_left");
		this.btnDelete.setIcon(deleteIcon);
		Icon deleteAllIcon = EASResource.getIcon("imgTbtn_moveall_left");
		this.btnDeleteAll.setIcon(deleteAllIcon);
		this.btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
		setButtonDefaultStyl(btnSave);
	}

	protected void prmtProject_willShow(SelectorEvent e) throws Exception {

		prmtProject.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = prmtProject.getEntityViewInfo();
		if (view == null) {
			view = new EntityViewInfo();
		}
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("CU.id", SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		// prmtProject
		prmtProject.setEntityViewInfo(view);

	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		/*
		 * if(tblAssignAcct.getRowCount()<=0){ return; }
		 */
		String prjId = FDCHelper.getF7Id(prmtProject);
		String orgId = FDCHelper.getF7Id(prmtOrg);
		if (prjId == null || orgId == null) {
			return;
		}
		FullOrgUnitInfo orgUnit = new FullOrgUnitInfo();
		orgUnit.setId(BOSUuid.read(orgId));
		CostAcctOrgAssignCollection col = new CostAcctOrgAssignCollection();
		for (int i = 0; i < tblAssignAcct.getRowCount(); i++) {
			IRow row = tblAssignAcct.getRow(i);
			if (row.getUserObject() instanceof CostAccountInfo) {
				CostAccountInfo acct = (CostAccountInfo) row.getUserObject();
				if (acct == null || !acct.isIsLeaf()) {
					continue;
				}
				CostAcctOrgAssignInfo info = new CostAcctOrgAssignInfo();
				info.setCostAccount(acct);
				info.setObjectId(prjId);
				info.setOrgUnit(orgUnit);
				col.add(info);
			}
		}
		if (col.size() <= 0) {
			CostAcctOrgAssignInfo info = new CostAcctOrgAssignInfo();
			info.setCostAccount(null);
			info.setObjectId(prjId);
			info.setOrgUnit(orgUnit);
			col.add(info);
		}
		CostAcctOrgAssignFactory.getRemoteInstance().submitCollection(col);
		FDCMsgBox.showInfo(this, "保存成功!");
	}

	public void onLoad() throws Exception {
		super.onLoad();
		prmtProject.setEnabledMultiSelection(false);
		prmtProject.setDisplayFormat("$name$");
		prmtProject.setEditFormat("$number$");
		prmtProject.setCommitFormat("$number$");
		initOrgF7();
		initCtrlistener();
	}

	public void loadFields() {
		if (prmtProject.getValue() == null || prmtOrg.getValue() == null) {
			return;
		}

		String prjId = FDCHelper.getF7Id(prmtProject);
		String orgId = FDCHelper.getF7Id(prmtOrg);
		assignedMap.clear();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fcostAccountId from T_AIM_CostAcctOrgAssign where fobjectId=? and forgunitId=?");
		builder.addParam(prjId);
		builder.addParam(orgId);
		try {
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				String acctId = rowSet.getString("fcostAccountId");
				addCostAcctToAssignedMap(acctId);
			}
		} catch (Exception e) {
			handUIException(e);
		}

		fillAssignTable();

	}

	private void fillAssignTable() {
		tblAssignAcct.removeRows();
		tblAssignAcct.getTreeColumn().setDepth(0);
		if (assignedMap.size() <= 0) {
			return;
		}
		int dep = 0;
		for (Iterator iter = assignedMap.keySet().iterator(); iter.hasNext();) {
			CostAccountInfo acct = (CostAccountInfo) assignedMap.get(iter.next());
			if (acct == null) {
				continue;
			}
			if (acct.getLevel() > dep) {
				dep = acct.getLevel();
			}
		}
		tblAssignAcct.getTreeColumn().setDepth(dep);
		for (Iterator iter = assignedMap.keySet().iterator(); iter.hasNext();) {
			CostAccountInfo acct = (CostAccountInfo) assignedMap.get(iter.next());
			if (acct == null) {
				continue;
			}
			IRow row = tblAssignAcct.addRow();
			row.setUserObject(acct);
			row.setTreeLevel(acct.getLevel() - 1);
			row.getCell("id").setValue(acct.getId().toString());
			row.getCell("number").setValue(acct.getLongNumber().replace('!', '.'));
			row.getCell("name").setValue(acct.getName());
		}
	}

	private Map assignedMap = new TreeMap();
	private Map acctMap = new HashMap();
	private Map acctNumberMap = new HashMap();

	public void loadAcctFields() {
		tblAcct.removeRows();
		acctMap.clear();
		acctNumberMap.clear();
		String prjId = FDCHelper.getF7Id(prmtProject);
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("curProject.id", prjId);
		view.getFilter().appendFilterItem("isEnabled", Boolean.TRUE);
		view.getSorter().add(new SorterItemInfo("longNumber"));
		view.getSelector().add("*");
		try {
			CostAccountCollection accts = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
			int dep = 0;
			for (int i = 0; i < accts.size(); i++) {
				CostAccountInfo costAcct = accts.get(i);
				if (costAcct != null && costAcct.getLevel() > dep) {
					dep = costAcct.getLevel();
				}
			}
			tblAcct.getTreeColumn().setDepth(dep);
			for (int i = 0; i < accts.size(); i++) {
				CostAccountInfo costAcct = accts.get(i);
				acctNumberMap.put(costAcct.getLongNumber(), costAcct);
				acctMap.put(costAcct.getId().toString(), costAcct);
				IRow row = tblAcct.addRow();
				row.setTreeLevel(costAcct.getLevel() - 1);
				System.out.println(costAcct.getLongNumber() + ":\t" + costAcct.getLevel());
				row.setUserObject(costAcct);
				row.getCell("id").setValue(costAcct.getId().toString());
				row.getCell("number").setValue(costAcct.getLongNumber().replace('!', '.'));
				row.getCell("name").setValue(costAcct.getName());
			}
		} catch (Exception e) {
			handUIException(e);
		}
	}

	private void initCtrlistener() {
		prmtProject.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				loadAcctFields();
			};
		});
		prmtOrg.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				loadFields();
			}
		});
	}

	private void addCostAcctToAssignedMap(String acctId) {
		CostAccountInfo acct = (CostAccountInfo) acctMap.get(acctId);
		if (acct == null) {
			return;
		}
		/*
		 * // assignedMap.put(acct.getLongNumber(), acct); //添加科目上级 String
		 * []splits=acct.getLongNumber().split("!"); String last=""; for(int
		 * i=0;i<splits.length;i++){ if(i==0){ last=splits[i]; }else{
		 * last=last+"!"+splits[i]; } assignedMap.put(last,
		 * acctNumberMap.get(last)); }
		 */
		// 将它的下级也加入
		for (Iterator iter = acctNumberMap.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			if (key.startsWith(acct.getLongNumber() + "!") || acct.getLongNumber().startsWith(key + "!")) {
				assignedMap.put(key, acctNumberMap.get(key));
			}
		}
		assignedMap.put(acct.getLongNumber(), acct);
	}

	private void deleteCostAcctToAssignedMap(String acctId) {
		CostAccountInfo acct = (CostAccountInfo) acctMap.get(acctId);
		if (acct == null) {
			return;
		}
		// 检查有没有同级的,如果有则只删除本科目及下级科目,或者删除所有科目
		String[] splits = acct.getLongNumber().split("!");
		String last = "";
		for (int i = 0; i < splits.length; i++) {
			if (i == 0) {
				last = splits[i];
			} else {
				last = last + "!" + splits[i];
			}
			assignedMap.remove(last);
		}
		// 删除它的下级
		if (!acct.isIsLeaf()) {
			Map tmp=new TreeMap();
			tmp.putAll(assignedMap);
			for (Iterator iter = tmp.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				if (key.startsWith(acct.getLongNumber() + "!")) {
					assignedMap.remove(key);
				}
			}
		}
		// 做一次同步,同步回来被删除但是还有下级的上级
		Map tmp=new TreeMap();
		tmp.putAll(assignedMap);
		for (Iterator iter = tmp.values().iterator(); iter.hasNext();) {
			String id = (((CostAccountInfo) iter.next()).getId()).toString();
			addCostAcctToAssignedMap(id);
		}
	}
}