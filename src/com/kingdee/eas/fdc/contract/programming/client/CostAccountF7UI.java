/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.mm.control.client.TableCellComparator;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class CostAccountF7UI extends AbstractCostAccountF7UI {
	private static final Logger logger = CoreUIObject.getLogger(CostAccountF7UI.class);
	private boolean isCancel = true;

	public boolean isCancel() {
		return isCancel;
	}

	public void setCancel(boolean isCancel) {
		this.isCancel = isCancel;
	}

	public CostAccountF7UI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		menuBar.setVisible(false);
		toolBar.setVisible(false);
		super.onLoad();
		btnConfirm.setEnabled(true);
		btnExit.setEnabled(true);
	}
	protected void btnSelect_actionPerformed(ActionEvent e) throws Exception {
		for(int i=0;i<this.tblMain.getRowCount();i++){
			IRow row=this.tblMain.getRow(i);
			String name = (String)row.getCell("name").getValue();
			String selectName=this.txtName.getText().trim();
			if(selectName.equals("")){
				row.getStyleAttributes().setHided(false);
			}else{
				if(name!=null&&name.indexOf(selectName)>=0){
					row.getStyleAttributes().setHided(false);
				}else{
					row.getStyleAttributes().setHided(true);
				}
			}
		}
	}

	private BigDecimal getAllContractAssign(CostAccountInfo caInfo,ProgrammingContractCollection pcCollection) {
		BigDecimal allContractAssign = FDCHelper.ZERO;
		for (int i = 0; i < pcCollection.size(); i++) {
			ProgrammingContractInfo programmingContractInfo = pcCollection.get(i);
			ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
			for (int j = 0; j < costEntries.size(); j++) {
				ProgrammingContracCostInfo pccInfo = costEntries.get(j);
				CostAccountInfo costAccountInfo = pccInfo.getCostAccount();
				if (costAccountInfo != null) {
					if(costAccountInfo.getLongNumber()!=null){
						if (costAccountInfo.getLongNumber().equals(caInfo.getLongNumber())) {
							BigDecimal contractAssign = pccInfo.getContractAssign();
							if (contractAssign == null) {
								contractAssign = FDCHelper.ZERO;
							}
							allContractAssign = allContractAssign.add(contractAssign);
						}
					}
				}
			}
		}
		return allContractAssign;
	}
	Map assignMap=new HashMap();
	Map goalCostMap=new HashMap();
	public void onShow() throws Exception {
		super.onShow();
		tblMain.addKDTMouseListener(new KDTSortManager(tblMain));
		tblMain.getSortMange().setSortAuto(false);
		
		List rows = this.tblMain.getBody().getRows();
        Collections.sort(rows, new TableCellComparator(tblMain.getColumnIndex("longNumber"), KDTSortManager.SORT_ASCEND));
		
    	int maxLevel = 0;
		int[] levelArray = new int[tblMain.getRowCount()];

		ProgrammingContractCollection pcCollection=(ProgrammingContractCollection) this.getUIContext().get("col");
		AimCostInfo aimCost=(AimCostInfo) this.getUIContext().get("aimCost");
		if(pcCollection!=null){
			for (int i = 0; i < pcCollection.size(); i++) {
				ProgrammingContractInfo programmingContractInfo = pcCollection.get(i);
				ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
				for (int j = 0; j < costEntries.size(); j++) {
					ProgrammingContracCostInfo pccInfo = costEntries.get(j);
					CostAccountInfo costAccountInfo = pccInfo.getCostAccount();
					if (costAccountInfo != null) {
						BigDecimal contractAssign = pccInfo.getContractAssign();
						if (contractAssign == null) {
							contractAssign = FDCHelper.ZERO;
						}
						if (assignMap.containsKey(costAccountInfo.getId().toString())) {
							BigDecimal allContractAssign = ((BigDecimal)assignMap.get(costAccountInfo.getId().toString())).add(contractAssign);
							assignMap.put(costAccountInfo.getId().toString(), allContractAssign);
						}else{
							assignMap.put(costAccountInfo.getId().toString(), contractAssign);
						}
					}
				}
			}
		}
		if(aimCost!=null){
			FDCSQLBuilder buider = new FDCSQLBuilder();
			buider.appendSql("select sum(costEntry.FCostAmount) FCostAmount,costAccount.fid id from T_AIM_CostEntry costEntry ");
			buider.appendSql("left join T_FDC_CostAccount costAccount on costEntry.FCostAccountID = costAccount.FID ");
			buider.appendSql("left join T_FDC_CurProject project on costAccount.FCurProject = project.FID ");
			buider.appendSql("where costEntry.FHeadID = '" + aimCost.getId().toString() + "' group by costAccount.fid");
			IRowSet rowSet;
			try {
				rowSet = buider.executeQuery();
				while(rowSet.next()) {
					goalCostMap.put(rowSet.getString("id"), rowSet.getBigDecimal("FCostAmount"));
				}
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
			row.setTreeLevel(level - 1);
			
			Object name =  row.getCell("name").getValue();
			if(name != null && name.toString().trim().length() > 0){
				String blank = setNameIndent(level);
				row.getCell("name").setValue(blank+name.toString().trim());
			}
			String id=row.getCell("id").getValue().toString();
			BigDecimal goalCost = (BigDecimal) goalCostMap.get(id);
			BigDecimal amount=(BigDecimal) assignMap.get(id);
			if(goalCost==null||goalCost.compareTo(FDCHelper.ZERO)==0||(amount!=null&&goalCost.compareTo(amount)==0)){
				row.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			}
		}

		for (int i = 0; i < tblMain.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}
		tblMain.getTreeColumn().setDepth(maxLevel);
		tblMain.getColumn("fullOrgUnit.name").getStyleAttributes().setHided(true);
		tblMain.getColumn("isEnabled").getStyleAttributes().setHided(true);
		tblMain.getColumn("level").getStyleAttributes().setHided(true);
		this.tblMain.setRefresh(true);
	}
	
	public void loadFields() {
    	super.loadFields();
	}
	
	private String setNameIndent(int level){
		StringBuffer blank = new StringBuffer("");
		for(int i = level ; i > 1 ; i--){
			blank.append("        ");
		}
		return blank.toString();
	}
	
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
		Map map = this.getUIContext();
		if(map.get("view") != null){
			EntityViewInfo entityView = (EntityViewInfo)map.get("view");
			EntityViewInfo viewInfo = this.getMainQuery();
			viewInfo.setFilter(entityView.getFilter());
		}
	}
	
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			if (e.getType() == 0) {
				return;
			} else {
				confirm();
				setCancel(false);
			}
		}
	}

	public CostAccountInfo[] getData() throws Exception {
		ArrayList IDList = getSelectedIdValues();
		CostAccountInfo [] info = new CostAccountInfo[IDList.size()];
		
		ProgrammingContractCollection pcCollection=(ProgrammingContractCollection) this.getUIContext().get("col");
		AimCostInfo aimCost=(AimCostInfo) this.getUIContext().get("aimCost");
		
		for(int i = 0 ; i < IDList.size() ; i++){
			CostAccountInfo costAccount = new CostAccountInfo();
			String id = IDList.get(i).toString();
			if (id == null)
				return null;
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("id");
			view.getSelector().add("name");
			view.getSelector().add("longNumber");
			view.getSelector().add("curProject.id");
			view.getSelector().add("curProject.displayName");
			view.getSelector().add("isLeaf");
			view.getSelector().add("CU.id");
			view.getSelector().add("curProject.longNumber");
			view.getSelector().add("isEnabled");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", id));
			view.setFilter(filter);
			costAccount = (CostAccountInfo) CostAccountFactory.getRemoteInstance().getCostAccountCollection(view).get(0);
			if (!costAccount.isIsLeaf()) {
				MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
				SysUtil.abort();
			}
			if(pcCollection!=null&&aimCost!=null){
				BigDecimal goalCost = (BigDecimal) goalCostMap.get(id);
				BigDecimal amount=(BigDecimal) assignMap.get(id);
				if(goalCost==null||goalCost.compareTo(FDCHelper.ZERO)==0||(amount!=null&&goalCost.compareTo(amount)==0)){
					MsgBox.showWarning(this,costAccount.getName()+"待分配金额为0，请选择其他成本科目！");
					SysUtil.abort();
				}
			}
			info[i] = costAccount;
		}
		disposeUIWindow();
		return info;
	}

	private void confirm() throws Exception {
		checkSelected();
		getData();
		setCancel(true);
	}

	public void checkSelected() {
		if (tblMain.getRowCount() == 0 || tblMain.getSelectManager().size() == 0 || tblMain.getSelectManager().getActiveRowIndex() < 0) {
			MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			SysUtil.abort();
		}
	}
	
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		confirm();
		setCancel(false);
	}
	
	public void actionExit_actionPerformed(ActionEvent e) throws Exception {
		destroyWindow();
		setCancel(true);
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}
}