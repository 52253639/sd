package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.UuidException;

public class AimCostHandler {
	
	public static final String resourcePath = "com.kingdee.eas.fdc.aimcost.client.AimCostResource";

	public AimCostInfo aimCost;

	public AimCostVersionHandler versionHandler;

	public BigDecimal buildArea;

	public BigDecimal sellArea;

	private Map aimCostEntryMap = new HashMap();

	private TreeModel costAcctTree;

	private HashMap acctMap = new HashMap();

	public AimCostHandler(String objectId, String versionNumber)
			throws Exception {
		this.versionHandler = new AimCostVersionHandler(objectId);
		versionHandler.verifyVersion(versionNumber);
		if (versionNumber == null) {
			versionNumber = versionHandler.getLastVersion();
		}
		BOSObjectType bosType = BOSUuid.read(objectId).getType();
		FilterInfo acctFilter = new FilterInfo();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			acctFilter.getFilterItems().add(
					new FilterItemInfo("curProject.id", objectId));
		} else {
			acctFilter.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.id", objectId));
		}
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		accreditSet=AcctAccreditHelper.handAcctAccreditFilter(null, objectId, acctFilter);
		costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory
				.getRemoteInstance(), acctFilter);
		this.initAcct(acctFilter);
		EntityViewInfo view = getEntityView(objectId, versionNumber);
		AimCostCollection aimCostCollection = AimCostFactory
				.getRemoteInstance().getAimCostCollection(view);
		if (aimCostCollection.size() > 0) {
			this.aimCost = aimCostCollection.get(0);
			CostEntryCollection costEntrys = aimCost.getCostEntry();
			for (int i = 0; i < costEntrys.size(); i++) {
				CostEntryInfo info = costEntrys.get(i);
				CostAccountInfo costAccount = info.getCostAccount();
				if(costAccount==null||costAccount.getId()==null){
					continue;
				}
				if (aimCostEntryMap.containsKey(costAccount.getId().toString())) {
					CostEntryCollection coll = (CostEntryCollection) aimCostEntryMap
							.get(costAccount.getId().toString());
					coll.add(info);
				} else {
					CostEntryCollection newColl = new CostEntryCollection();
					newColl.add(info);
					aimCostEntryMap
							.put(costAccount.getId().toString(), newColl);
				}
			}
			for (int i = 1; i < aimCostCollection.size(); i++) {
				AimCostInfo deleteInfo = aimCostCollection.get(i);
				deleteInfo.setVersionNumber("0.0");
				AimCostFactory.getRemoteInstance().update(
						new ObjectUuidPK(deleteInfo.getId()), deleteInfo);
			}
		} else {
			this.aimCost = new AimCostInfo();
			this.aimCost.setVersionName("");
			this.aimCost.setIsLastVersion(true);
//			this.aimCost.setRecenseDate(DateTimeUtils.truncateDate(new Date()));
			this.aimCost.setVersionNumber(versionHandler.getFirstVersion());
			this.aimCost.setOrgOrProId(objectId);
		}
	}

	private EntityViewInfo getEntityView(String objectId, String versionNumber) {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("versionNumber", versionNumber));
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("orgOrProId", objectId));
		view.getSorter().add(new SorterItemInfo("id"));
		view.getSelector().add("*");
		view.getSelector().add("creator.name");
		view.getSelector().add("auditor.name");
		view.getSelector().add("lastUpdateUser.name");
		view.getSelector().add("costEntry.*");
		view.getSelector().add("costEntry.product.*");
		view.getSelector().add("measureStage.id");
		view.getSelector().add("measureStage.name");
		view.getSelector().add("measureStage.number");
		return view;
	}

	private void initAcct(FilterInfo acctFilter) throws BOSException {
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("longNumber"));
		sel.add(new SelectorItemInfo("fullOrgUnit.longNumber"));
		sel.add(new SelectorItemInfo("curProject.longNumber"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit.longNumber"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(acctFilter);
		CostAccountCollection accts = CostAccountFactory.getRemoteInstance()
				.getCostAccountCollection(view);
		for (int i = 0; i < accts.size(); i++) {
			CostAccountInfo info = accts.get(i);
			this.acctMap.put(info.getId().toString(), info);
		}
	}

	public void fillTable(KDTable table) throws Exception {
		table.removeRows();
		table.setUserObject(null);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree
				.getRoot();
		Enumeration childrens = root.depthFirstEnumeration();
		int maxLevel = 0;
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens
					.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		table.getTreeColumn().setDepth(maxLevel + 1);
		for (int i = 0; i < root.getChildCount(); i++) {
			fillNode(table, (DefaultMutableTreeNode) root.getChildAt(i));
		}
	}

	private void fillNode(KDTable table, DefaultMutableTreeNode node)
			throws BOSException, SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			MsgBox.showError("成本科目级别太多!");
			return;
		}
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		String longNumber = costAcct.getLongNumber();
		longNumber = longNumber.replace('!', '.');
		row.getCell("acctNumber").setValue(longNumber);
		row.getCell("acctName").setValue(costAcct.getName());
		if (node.isLeaf()) {
			setCostSumRow(row, costAcct);
			CostEntryCollection coll = (CostEntryCollection) aimCostEntryMap
					.get(costAcct.getId().toString());
			if (coll != null && coll.size() > 0) {
				if(coll.size()==1){
					CostEntryInfo info = coll.get(0);
					IRow entryRow = row;
					entryRow.setUserObject(info);
					loadRow(entryRow);
					setDetailAcctRow(row);
					row.getCell("acctName").setValue(costAcct.getName());
					row.getCell("acctNumber").setUserObject(costAcct);
				}else{
					row.getStyleAttributes().setLocked(true);
					row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
					row.getCell("acctNumber").setUserObject(costAcct);
					for (int i = 0; i < coll.size(); i++) {
						CostEntryInfo info = coll.get(i);
						IRow entryRow = table.addRow();
						entryRow.setTreeLevel(node.getLevel());
						entryRow.setUserObject(info);
						loadRow(entryRow);
					}
				}
			}else{
				CostEntryInfo info = new CostEntryInfo();
				info.setCostAccount(costAcct);
				row.setUserObject(info);
				setDetailAcctRow(row);
				row.getCell("acctNumber").setUserObject(costAcct);
			}
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node
						.getChildAt(i));
			}
			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			row.getCell("acctNumber").setUserObject(costAcct);
		}
	}

	private void setCostSumRow(IRow row, CostAccountInfo costAcct)
			throws EASBizException, BOSException, SQLException {
		//万科要求去掉
		if(true) return;
		CostAccountInfo acct = (CostAccountInfo) this.acctMap.get(costAcct
				.getId().toString());
		String fullNumber = "";
		if (acct.getCurProject() != null) {
			fullNumber = acct.getCurProject().getFullOrgUnit().getLongNumber()
					+ "!" + acct.getCurProject().getLongNumber();
		} else {
			fullNumber = acct.getFullOrgUnit().getLongNumber();
		}

		String longNumber = acct.getLongNumber();
		StringBuffer sql = new StringBuffer();
		sql
				.append("select sum(FCostAmount) sumCostAmount from T_Aim_CostEntry entry inner join T_Aim_AimCost head "
						+ "on entry.FHeadId=head.FId and FISLastVersion=1 inner join ");
		sql.append(FDCHelper.getFullAcctSql()).append(
				" on entry.FCostAccountId=costAcct.fid ");
		sql.append(" where (costAcct.FLongNumber = '").append(longNumber)
				.append("'").append(" or costAcct.FLongNumber like '").append(
						longNumber).append("!%' ").append(
						" or costAcct.FLongNumber like '%!").append(longNumber)
				.append("!%') ");
		sql
				.append("and (costAcct.FullNumber like '")
				.append(fullNumber)
				.append("!%' ")
				.append(" or costAcct.FullNumber like '%!")
				.append(fullNumber)
				.append(
						"!%') And costAcct.FIsLeaf=1 And costAcct.isleafProject=1");
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(sql.toString())
				.executeSQL();
		BigDecimal sum = FDCHelper.ZERO;
		while (rs.next()) {
			if (rs.getBigDecimal("sumCostAmount") != null) {
				sum = rs.getBigDecimal("sumCostAmount");
			}
		}
		row.getCell("sumAimCost").setValue(sum);
	}

	public void loadRow(IRow row) {
		CostEntryInfo info = (CostEntryInfo) row.getUserObject();
		row.getCell("acctName").setValue(info.getEntryName());
		BigDecimal workload = info.getWorkload();
		if (workload != null && workload.compareTo(FDCHelper.ZERO) == 0) {
			workload = null;
		}
		row.getCell("workload").setValue(workload);
		row.getCell("unit").setValue(info.getUnit());
		BigDecimal price = info.getPrice();
		if (price != null && price.compareTo(FDCHelper.ZERO) == 0) {
			price = null;
		}
		row.getCell("price").setValue(price);
		row.getCell("aimCost").setValue(info.getCostAmount());
		if (workload != null && price != null) {
			row.getCell("aimCost").getStyleAttributes().setLocked(true);
		}
		if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
			BigDecimal buildPart =FDCNumberHelper.divide(info.getCostAmount(), this.buildArea); 
				/*info.getCostAmount().divide(this.buildArea,
					2, BigDecimal.ROUND_HALF_UP).setScale(2,
					BigDecimal.ROUND_HALF_UP);*/
			row.getCell("buildPart").setValue(buildPart);
		}
		if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
			BigDecimal sellPart = FDCNumberHelper.divide(info.getCostAmount(), this.sellArea);
			/*info.getCostAmount().divide(this.sellArea, 2,
					BigDecimal.ROUND_HALF_UP).setScale(2,
					BigDecimal.ROUND_HALF_UP);*/
			row.getCell("sellPart").setValue(sellPart);
		}
		row.getCell("product").setValue(info.getProduct());
		row.getCell("program").setValue(info.getProgram());
		row.getCell("desc").setValue(info.getDesc());
		row.getCell("changeReason").setValue(info.getChangeReason());
		row.getCell("description").setValue(info.getDescription());
	}

	public void storeRow(IRow row) {
		CostEntryInfo info = (CostEntryInfo) row.getUserObject();
		info.setEntryName((String) row.getCell("acctName").getValue());
		BigDecimal workload = (BigDecimal) row.getCell("workload").getValue();
		if (workload == null) {
			workload = FDCHelper.ZERO;
		}
		info.setWorkload(workload);
		info.setUnit((String) row.getCell("unit").getValue());
		BigDecimal price = (BigDecimal) row.getCell("price").getValue();
		if (price == null) {
			price = FDCHelper.ZERO;
		}
		info.setPrice(price);
		BigDecimal aimCostAmount = (BigDecimal) row.getCell("aimCost")
				.getValue();
		if (aimCostAmount == null) {
			aimCostAmount = FDCHelper.ZERO;
		}
		info.setCostAmount(aimCostAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
		info.setProduct((ProductTypeInfo) row.getCell("product").getValue());
		info.setProgram((String)row.getCell("program").getValue());
		info.setDesc((String)row.getCell("desc").getValue());
		info.setDesc((String)row.getCell("changeReason").getValue());
		info.setDescription((String) row.getCell("description").getValue());
	}

	public boolean isEditedRow(IRow row) {
		if (row.getUserObject() == null) {
			return false;
		}
		CostEntryInfo info = (CostEntryInfo) row.getUserObject();
		String[] compareyKeys = new String[] { "entryName", "workload", "unit",
				"price", "costAmount", "product", "program","desc","changeReason","description" };
		String[] columnKeys = new String[] { "acctName", "workload", "unit",
				"price", "aimCost", "product", "program","desc","changeReason","description" };
		for (int i = 0; i < compareyKeys.length; i++) {
			if (!FDCHelper.isEqual(info.get(compareyKeys[i]), row.getCell(
					columnKeys[i]).getValue())) {
				return true;
			}
		}
		return false;
	}

	public boolean isEditedTable(KDTable table) {
		if (table.getUserObject() != null) {
			return true;
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if(isDetailAcctRow(row)){
				if(isDetailAcctHasInput(row)){
					return true;
				}
			}else{
				if (row.getUserObject() != null) {
					if (this.isEditedRow(row))
						return true;
				}
			}
		}
		return false;
	}

	public void submitVersionData(String versionName, String description,
			Date recenseDate) throws EASBizException, BOSException {
		this.aimCost.setVersionName(versionName);
		this.aimCost.setDescription(description);
		this.aimCost.setRecenseDate(recenseDate);
		if (this.aimCost.getId() != null) {
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add(new SelectorItemInfo("versionName"));
			sels.add(new SelectorItemInfo("description"));
			//不改时间
//			sels.add(new SelectorItemInfo("recenseDate"));
			AimCostFactory.getRemoteInstance()
					.updatePartial(this.aimCost, sels);
		} else {
			IObjectPK objectPK = AimCostFactory.getRemoteInstance().addnew(
					this.aimCost);
			this.aimCost.setId(BOSUuid.read(objectPK.toString()));
		}
	}

	public void submitTableData(KDTable table) throws EASBizException,
			BOSException {
		if (!this.isEditedTable(table)) {
			return;
		}
//		this.aimCost.getCostEntry().clear();
		handleAimCostAccredit(aimCost);
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() != null) {
				CostEntryInfo info = (CostEntryInfo) row.getUserObject();
/*				if(isDetailAcctRow(row)&&FDCNumberHelper.toBigDecimal(info.getCostAmount()).signum()==0){
					continue;
				}*/
				this.storeRow(row);
				//0金额的分录不允许保存
				if(info!=null&&!FDCHelper.isNullZero(info.getCostAmount())){
					this.aimCost.getCostEntry().add(info);
				}
			}
		}
		AimCostFactory.getRemoteInstance().submit(aimCost);
		CacheServiceFactory.getInstance().discardType(new AimCostInfo().getBOSType());
		table.setUserObject(null);
	}

	public boolean recenseTableDate(KDTable table) throws EASBizException,
			BOSException {
		// 没有修改不修订
		if (!this.isEditedTable(table)) {
			return false;
		}
		// // 没有保存过,不修订,直接保存
		// if ((this.aimCost.getCostEntry() == null ||
		// this.aimCost.getCostEntry()
		// .size() == 0)
		// && this.versionHandler.getLastVersion().equals(
		// versionHandler.getFirstVersion())) {
		// this.submitTableData(table);
		// return false;
		// }
		final Date date = new Date();
		String nextVersion = AimCostVersionHandler
				.getNextVersion(this.versionHandler.getLastVersion());
		Map map = VersionInputUI.showEditUI(this.aimCost.getOrgOrProId(), null,
				nextVersion, null, null, date,aimCost.getMeasureStage());
		if (map.get("isConfirm") == null) {
			return false;
		}

/*		AimCostCollection aimCostCollection = AimCostFactory
				.getRemoteInstance().getAimCostCollection(
						"select id where orgOrProId ='"
								+ this.aimCost.getOrgOrProId()
								+ "' and isLastVersion =1");
		for (int i = 0; i < aimCostCollection.size(); i++) {
			AimCostInfo aim = aimCostCollection.get(i);
			aim.setIsLastVersion(false);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add(new SelectorItemInfo("isLastVersion"));
			AimCostFactory.getRemoteInstance().updatePartial(aim, sels);
		}*/
		this.aimCost.setId(BOSUuid.create(this.aimCost.getBOSType()));
		this.aimCost.setVersionNumber(nextVersion);
		this.aimCost.setVersionName((String) map.get("versionName"));
		this.aimCost.setRecenseDate(date);
		this.aimCost.setDescription((String) map.get("description"));
		this.aimCost.setAuditDate(null);
		this.aimCost.setAuditor(null);
		this.aimCost.setIsLastVersion(true);
//		this.aimCost.getCostEntry().clear();
		handleAimCostAccredit(aimCost);
		//处理修订
		this.aimCost.setBoolean("isAimCostRevise", true);
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() != null) {
				CostEntryInfo info = (CostEntryInfo) row.getUserObject();
				if(isDetailAcctRow(row)&&FDCNumberHelper.toBigDecimal(row.getCell("aimCost").getValue()).signum()==0){
					continue;
				}
				info.setBOSUuid("oldId", info.getId());
				info.setId(BOSUuid.create(info.getBOSType()));
				info.setNumber(i + "");
				this.storeRow(row);
				//0金额的分录不允许保存
				if(info!=null&&!FDCHelper.isNullZero(info.getCostAmount())){
					this.aimCost.getCostEntry().add(info);
				}
				this.aimCost.getCostEntry().add(info);
			}
		}
		IObjectPK pk = AimCostFactory.getRemoteInstance().submit(aimCost);
		aimCost.setId(BOSUuid.read(pk.toString()));
		return true;
	}

	public static String getResource(String resourceName) {
		return EASResource.getString(resourcePath, resourceName);
	}
	
	
    public boolean isDetailAcctRow(IRow row){
    	if(row!=null &&row.getCell("sumAimCostCap").getUserObject()!=null&&row.getCell("sumAimCostCap").getUserObject().equals("DetailInput")){
    		return true;
    	}
    	return false;
    }
    public void setDetailAcctRowNull(IRow row){
    	if(row!=null&&row.getCell("sumAimCostCap").getUserObject()!=null){
    		row.getCell("sumAimCostCap").setUserObject(null);
    	}
    }
    public void setDetailAcctRow(IRow row){
    	if(row!=null&&row.getCell("sumAimCostCap")!=null){
    		row.getCell("sumAimCostCap").setUserObject("DetailInput");
    	}
    	if(row!= null && row.getCell("acctName") != null)
    	{
    		row.getCell("acctName").getStyleAttributes().setLocked(true);
    		row.getCell("acctName").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	}
    	// BT484556 by hpw 201011.17
    	if(row!= null && row.getCell("acctNumber") != null)
    	{
    		row.getCell("acctNumber").getStyleAttributes().setLocked(true);
    		row.getCell("acctNumber").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	}
    }
    public void setDetailAcctHasInput(IRow row){
    	if(row!=null&&row.getCell("aimCostCap")!=null){
    		row.getCell("aimCostCap").setUserObject("HasInput");
    	}
    }
    public void setDetailAcctHasNotInput(IRow row){
    	if(row!=null&&row.getCell("aimCostCap")!=null){
    		row.getCell("aimCostCap").setUserObject(null);
//    		row.setUserObject(null);
    	}
    }
    public boolean isDetailAcctHasInput(IRow row){
    	if(row!=null &&row.getCell("aimCostCap").getUserObject()!=null&&row.getCell("aimCostCap").getUserObject().equals("HasInput")){
    		return true;
    	}
    	return false;
    }
    public void clearDetailAcctRow(KDTable table,IRow row){

    	row.getCell("sumAimCostCap").setUserObject(null);
    	row.setUserObject(null);
		for(int i=2;i<table.getColumnCount();i++){
			row.getCell(i).setValue(null);
		}
	
/*    	CostEntryInfo info = new CostEntryInfo();
    	info.setCostAccount((CostAccountInfo)row.getCell("acctNumber").getUserObject());
		row.setUserObject(info);
		setDetailAcctRow(row);*/
    }
    
    //保存的时候用于判断是否做科目删除
    private Set accreditSet=null;
    /**
     * 将原来清白所有分录数据的逻辑改成清白授权科目，这样未被授权没有显示的科目数据就不会在保存后被清除掉
     *  by sxhong 2009-01-05 14:04:41
     * @param aimCost
     * @throws BOSException
     */
    private void handleAimCostAccredit(AimCostInfo aimCost) throws BOSException{
    	if(!AcctAccreditHelper.hasUsed(null)||accreditSet==null||accreditSet.size()==0){
    		//不使用科目授权的情况下按照原来的方式进行数据清除
    		aimCost.getCostEntry().clear();
    		return;
    	}
    	for(int i=aimCost.getCostEntry().size()-1;i>=0;i--){
    		CostEntryInfo entry=aimCost.getCostEntry().get(i);
    		if(entry==null||entry.getCostAccount()==null){
    			continue;
    		}
    		//这部分重新从界面取值，所以先删除掉
    		if(accreditSet.contains(entry.getCostAccount().getId().toString())){
    			aimCost.getCostEntry().remove(entry);
    		}else{
    			entry.setId(null);
    		}
    	}
    }
}
