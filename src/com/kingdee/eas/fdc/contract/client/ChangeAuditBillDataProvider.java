package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class ChangeAuditBillDataProvider extends FDCBillDataProvider {

	private Map dataMap = new HashMap();

	/** 合同变更发起 ID */
	private String billId = null;

	/** 当前单据下发单位合同ID集合 */
	private Set idSet = new HashSet();

	private Date createTime = null;
	private String spName="";

	public ChangeAuditBillDataProvider(String billId, IMetaDataPK mainQuery,String spName) {
		super(billId, mainQuery);
		this.billId = billId;
		this.spName=spName;
	}

	public IRowSet getMainBillRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = super.getMainBillRowSet(ds);
		try {
			iRowSet.beforeFirst();
			while (iRowSet.next()) {
				createTime = iRowSet.getDate("createTime");
				iRowSet.updateObject("specialtyType.name", spName);
			}
			iRowSet.beforeFirst();
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return iRowSet;
	}

	public IRowSet execute(BOSQueryDataSource ds) {
		return super.execute(ds);
	}

	/***************************************************************************
	 * 下发单位
	 * 
	 * @param ds
	 * @return
	 */
	private IRowSet getSub1RowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory
					.getRemoteInstance(new MetaDataPK(
							"com.kingdee.eas.fdc.contract.app.ChangeAuditBillPrintContractQuery"));
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", billId, CompareType.EQUALS));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			// System.out.println(exec.getSQL());
			iRowSet = exec.executeQuery();
			iRowSet.beforeFirst();
			while (iRowSet.next()) {
				String id = iRowSet.getString("contractBill.id");
				if (id != null) {
					idSet.add(iRowSet.getString("contractBill.id"));
				}
			}
			initContractChangeAmt(idSet, createTime);

			iRowSet.beforeFirst();
			while (iRowSet.next()) {
				String conId = iRowSet.getString("contractBill.id");
				iRowSet.updateObject("contractBill.allAmount", FDCHelper.toBigDecimal(dataMap
						.get(conId)));
				iRowSet.updateObject("contractBill.allOriAmount", FDCHelper.toBigDecimal(dataMap
						.get(conId + "ori")));
				iRowSet.updateObject("contractBill.percent", FDCHelper.toBigDecimal(dataMap.get(conId
						+ "percent")));
			}
			iRowSet.beforeFirst();

		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

		return iRowSet;
	}

	/***************************************************************************
	 * 变更内容
	 * 
	 * @param ds
	 * @return
	 */
	private IRowSet getSub2RowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.contract.app.ChangeAuditForPrintQuery"));
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", billId, CompareType.EQUALS));
			ev.setFilter(filter);
			//变更内容按Seq排序
			SorterItemCollection coll = new SorterItemCollection();
			coll.add(new SorterItemInfo("entrys.seq"));
			ev.setSorter(coll);
			exec.setObjectView(ev);
			// System.out.println(exec.getSQL());
			iRowSet = exec.executeQuery();
			iRowSet.beforeFirst();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

		return iRowSet;
	}

	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		if ("SUB1".equals(ds.getID().toUpperCase())) {
			return getSub1RowSet(ds);
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
				e.printStackTrace();
			}
			return iRowSet;
		} else {
			return getSub2RowSet(ds);
		}
	}

	/**
	 * 测算金额：结算金额为零时取预算金额
	 * 
	 * @param measureAmt
	 * @param balanceAmt
	 * @return
	 */
	private BigDecimal getBalanceAmount(BigDecimal measureAmt,
			BigDecimal balanceAmt) {
		if (FDCHelper.toBigDecimal(balanceAmt).compareTo(FDCHelper.ZERO) != 0) {
			return balanceAmt;
		} else if (FDCHelper.toBigDecimal(measureAmt).compareTo(FDCHelper.ZERO) != 0) {
			return measureAmt;
		}
		return null;
	}

	/**
	 * 合同累计签证本币原币金额，签证次数(不含本次)
	 * 
	 * @param idSet
	 * @param createTime
	 * 
	 * @param
	 * 
	 */
	private void initContractChangeAmt(Set idSet, Date createTime) {
		BigDecimal amt = FDCHelper.ZERO;
		BigDecimal oriAmt = FDCHelper.ZERO;

		ContractChangeBillCollection colls = null;

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("amount");
		view.getSelector().add("originalAmount");
		view.getSelector().add("balanceAmount");
		view.getSelector().add("oriBalanceAmount");
		view.getSelector().add("contractBill.id");
		view.getSelector().add("contractBill.amount");
		view.getSelector().add("contractBill.originalAmount");
		view.getSelector().add("changeAudit.id");
		view.setFilter(new FilterInfo());
		if (idSet != null && idSet.size() > 0) {
			view.getFilter().getFilterItems().add(
					new FilterItemInfo("contractBill.id", idSet,
							CompareType.INCLUDE));
		} 
		view.getFilter().getFilterItems().add(
				new FilterItemInfo("changeAudit.createTime", createTime,
						CompareType.LESS_EQUALS));
		FilterInfo curFilter = new FilterInfo();
		curFilter.getFilterItems().add(new FilterItemInfo("changeAudit.id", this.billId));
		try {
			view.getFilter().mergeFilter(curFilter, "or");
		} catch (BOSException e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
		}
//		 view.getFilter().getFilterItems().add(
//		 new FilterItemInfo("state", FDCBillStateEnum.VISA_VALUE));
		try {
			colls = ContractChangeBillFactory.getRemoteInstance()
					.getContractChangeBillCollection(view);
		} catch (BOSException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		if (colls == null || colls.size() == 0) {
			return;
		}

		for (int i = 0; i < colls.size(); i++) {
			BigDecimal amount = FDCHelper.ZERO;

			ContractChangeBillInfo info = colls.get(i);
			amt = getBalanceAmount(info.getAmount(), info.getBalanceAmount());
			oriAmt = getBalanceAmount(info.getOriginalAmount(), info
					.getOriBalanceAmount());
			if (info != null && info.getContractBill() != null
					&& info.getContractBill().getId() != null) {
				String conId = info.getContractBill().getId().toString();
				String conIdOri = conId + "ori";
				String conIdPer = conId + "per";
				if(this.billId.equals(info.getChangeAudit().getId().toString())){
					amt = info.getAmount();
					oriAmt = info.getOriginalAmount();
					dataMap.put(conIdPer, amt);
				}  else {
					// 合同累计签证金额本币合计
					if (dataMap.get(conId) != null) {
						BigDecimal allAmount = FDCHelper.toBigDecimal(dataMap
								.get(conId));
						allAmount = FDCHelper.add(allAmount, amt);
						dataMap.put(conId, setValue(allAmount));
					} else {
						dataMap.put(conId, setValue(amt));
					}
				}
				
				BigDecimal percent = FDCHelper.divide(FDCHelper.add(dataMap.get(conIdPer),
						dataMap.get(conId)),
						info.getContractBill().getAmount(), 5,
						BigDecimal.ROUND_HALF_UP);
				dataMap.put(conId + "percent", percent);
				if (dataMap.get(conIdOri) != null) {
					BigDecimal allOriAmount = FDCHelper.toBigDecimal(dataMap
							.get(conIdOri));
					allOriAmount = FDCHelper.add(allOriAmount, oriAmt);
					dataMap.put(conIdOri, setValue(allOriAmount));
				} else {
					dataMap.put(conIdOri, setValue(oriAmt));
				}
			}
		}

	}

	public static String setValue(Object o) {
		if (o == null)
			return "";
		else if (o instanceof BigDecimal) {
			if (o.equals(FDCHelper.ZERO))
				return "";
			else
				return String.valueOf(((BigDecimal) o).setScale(2,
						BigDecimal.ROUND_HALF_UP));
		}
		return String.valueOf(o);
	}
}
