package com.kingdee.eas.fdc.contract.client;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.aimcost.DynamicCostMap;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractBillEditDataProvider extends FDCBillDataProvider {
	
	private static final Logger logger = Logger.getLogger(ContractBillEditDataProvider.class);
	private String bailId = ""; // 履约保证金的ID

	public ContractBillEditDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	}

	public void setBailId(String bailId) {
		this.bailId = bailId;
	}

	private KDTable tblCost = new KDTable();

	public static void insertRows(IRowSet iRowSet, ResultSetMetaData metas, IRowSet copy, int insertRowCount) {
		try {
			for (int i = 0; i < insertRowCount; i++) {
				iRowSet.moveToInsertRow();
				for (int j = 1; j <= metas.getColumnCount(); j++) {
					iRowSet.updateObject(metas.getColumnName(j), copy.getObject(metas.getColumnName(j)));
				}
				iRowSet.insertRow();
			}

		} catch (SQLException e) {
			logger.debug(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void initTable() {
		tblCost = new KDTable();
		tblCost.setName("tblCost");
		IRow headRow = tblCost.addHeadRow();

		IColumn column = tblCost.addColumn();
		column.setKey("acctNumber");
		headRow.getCell("acctNumber").setValue("acctNumber");
		column = tblCost.addColumn();
		column.setKey("acctName");
		headRow.getCell("acctName").setValue("acctName");
		column = tblCost.addColumn();
		column.setKey("aimCost");
		headRow.getCell("aimCost").setValue("aimCost");
		column = tblCost.addColumn();
		column.setKey("hasHappen");
		headRow.getCell("hasHappen").setValue("hasHappen");
		column = tblCost.addColumn();
		column.setKey("intendingHappen");
		headRow.getCell("intendingHappen").setValue("intendingHappen");
		column = tblCost.addColumn();
		column.setKey("dynamicCost");
		headRow.getCell("dynamicCost").setValue("dynamicCost");

		column = tblCost.addColumn();
		column.setKey("chayi");
		headRow.getCell("chayi").setValue("chayi");

		column = tblCost.addColumn();
		column.setKey("conSplit");
		headRow.getCell("conSplit").setValue("conSplit");
	}

	private ContractCostSplitEntryCollection getCostSplitEntrys(String contractId, String prjId) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("costAccount.id");
		view.getSelector().add("costAccount.longNumber");
		view.getSelector().add("costAccount.name");
		view.getSelector().add("amount");
		filter.appendFilterItem("parent.contractBill.id", contractId);
		filter.appendFilterItem("costAccount.curProject.id", prjId);
		filter.appendFilterItem("costAccount.isLeaf", Boolean.TRUE);
		filter.appendFilterItem("splitType", CostSplitTypeEnum.PRODSPLIT_VALUE);
		filter.appendFilterItem("isLeaf", Boolean.FALSE);
		filter.appendFilterItem("isLeaf", Boolean.TRUE);
		filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		filter.setMaskString("#0 and #1 and #2 and ((#3 and #4) or #5) and #6");
		view.getSorter().add(new SorterItemInfo("costAccount.longNumber"));
		ContractCostSplitEntryCollection splitEntrys = ContractCostSplitEntryFactory.getRemoteInstance().getContractCostSplitEntryCollection(view);
		return splitEntrys;
	}
	
	public IRowSet getContractEntryInfo(BOSQueryDataSource ds) {
		IRowSet iRow = super.getMainBillRowSet(ds);
		String contractId = "";
		try {
			iRow.next();
			contractId = iRow.getString("id");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK(
					"com.kingdee.eas.fdc.contract.app.ContractBillEntryPrintQuery"));
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", contractId.toString(), CompareType.EQUALS));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			iRowSet = exec.executeQuery();
			iRowSet.beforeFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iRowSet;
	}
	
	public IRowSet getContractPayItem(BOSQueryDataSource ds) {
		IRowSet iRow = super.getMainBillRowSet(ds);
		String contractId = "";
		try {
			iRow.next();
			contractId = iRow.getString("id");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory
					.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractPayItemPrintQuery"));
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId.toString(), CompareType.EQUALS));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			iRowSet = exec.executeQuery();
			iRowSet.beforeFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iRowSet;
	}
	
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		if (ds.getID().equalsIgnoreCase("ContractPayItemPrintQuery")) {
			// 合同付款事项
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK(
						"com.kingdee.eas.fdc.contract.app.ContractPayItemPrintQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("contractbill.id", billId));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}

			return iRowSet;
		} else if (ds.getID().equalsIgnoreCase("ContractBailPrintQuery")) {
			// 合同履约保证金及返还部分
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractBailPrintQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", bailId));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}

			return iRowSet;
		}else if(ds.getID().endsWith("ContractPayItemPrintQuery")){
			return getContractPayItem(ds);
			
		}else if(ds.getID().endsWith("ContractBillEntryPrintQuery")){
			return getContractEntryInfo(ds);
		}else if (ds.getID().equalsIgnoreCase("AttachmentQuery")) {
			// 合同履约保证金及返还部分
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.basedata.app.AttachmentQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("boAttchAsso.boID",billId));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}

			return iRowSet;
		}
		
		return getMainBillRowSet(ds);
	}

	public IRowSet getMainBillRowSet(BOSQueryDataSource ds) {

		IRowSet iRowSet = super.getMainBillRowSet(ds);
		try {
			initTable();
			// 本次拆分成本
			Map splitEntrysMap = new HashMap();

			iRowSet.beforeFirst();
			ResultSetMetaData metas = iRowSet.getMetaData();

			Map objs = new HashMap();
			if (iRowSet.next()) {
				String contractId = iRowSet.getString("id");

				/*
				 * 备份每一行数据
				 */
				for (int i = 1; i < metas.getColumnCount() + 1; i++) {
					if (metas.getColumnName(i) != null)
						objs.put(metas.getColumnName(i), iRowSet.getObject(metas.getColumnName(i)));
				}
				IRowSet iRowSetCopy = iRowSet.createCopy();

				String selectObjId = iRowSet.getString("curProject.id");
				DynamicCostMap dynamicCostMap = FDCCostRptFacadeFactory.getRemoteInstance().getDynamicCost(selectObjId, null, true);

				/***************************************************************
				 * 取合同的动态成本信息
				 */
				if (dynamicCostMap != null) {
					Map acctMap = new HashMap();
					// String contractId =
					// (String)getUIContext().get(UIContext.ID);
					// 获取当前合同的拆分科目
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().add("costAccount.id");
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id", contractId));
					filter.getFilterItems().add(new FilterItemInfo("parent.isInvalid", new Integer(0)));
					view.setFilter(filter);
					ContractCostSplitEntryCollection col = ContractCostSplitEntryFactory.getRemoteInstance().getContractCostSplitEntryCollection(view);
					for (Iterator iter = col.iterator(); iter.hasNext();) {
						ContractCostSplitEntryInfo element = (ContractCostSplitEntryInfo) iter.next();

						String costAcctId = element.getCostAccount().getId().toString();
						if (acctMap.containsKey(costAcctId)) {
							continue;
						}
						acctMap.put(costAcctId, element);
					}
					ContractCostHelper helper = null;
					CurProjectInfo curProject = new CurProjectInfo();
					curProject.setId(BOSUuid.read(selectObjId));

					// 使用新接口 by sxhong 2009-06-11 09:13:59

					helper = new ContractCostHelper(tblCost, contractId);
					helper.fillCostTable();

					// 更新其中本次拆分成本

					ContractCostSplitEntryCollection splitEntrys = getCostSplitEntrys(contractId, selectObjId);
					for (Iterator it = splitEntrys.iterator(); it.hasNext();) {
						ContractCostSplitEntryInfo info = (ContractCostSplitEntryInfo) it.next();
						String langNumber = info.getCostAccount().getLongNumber().replace('!', '.');
						splitEntrysMap.put(langNumber, info.getAmount());
					}
					/***************************************************************
					 * 根据取出的成本信息，新增相应的行数到结果集中
					 */
					int insertRowCount = tblCost.getBody().size();

					insertRows(iRowSet, metas, iRowSetCopy, insertRowCount - 1);

				}
			}

			iRowSet.beforeFirst();
			int iRow = 0;
			while (iRowSet.next()) {
				// 更新动态成本信息
				IRow row = tblCost.getRow(iRow);
				iRow++;
				if (row != null) {
					String longNumber = "";
					for (int i = 0; i < tblCost.getColumnCount(); i++) {
						String key = tblCost.getColumn(i).getKey();
						Object value = row.getCell(key).getValue();
						if (key.equals("acctNumber")) {
							longNumber = value.toString();
						}
						iRowSet.updateObject("dynamicInfo." + key, value);
					}
					iRowSet.updateObject("dynamicInfo.thisTimeSplit", splitEntrysMap.get(longNumber));
				}
			}
			iRowSet.beforeFirst();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return iRowSet;
	}

}
