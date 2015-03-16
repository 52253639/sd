package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum;
import com.kingdee.jdbc.rowset.IRowSet;

public class PurchaseBookPrintDataProvider extends FDCBillDataProvider {
	private static Logger log = Logger
			.getLogger(PurchaseBookPrintDataProvider.class);

	private IMetaDataPK qpk = null;
	private String id = null;
	private HashMap map = null;
	private HashMap map1 = null;

	public PurchaseBookPrintDataProvider(String id, IMetaDataPK qpk) {
		super(id, qpk);
		this.id = id;
		this.qpk = qpk;
	}

	public PurchaseBookPrintDataProvider(String id, IMetaDataPK qpk, HashMap map) {
		super(id, qpk);
		this.id = id;
		this.qpk = qpk;
		this.map = map;
	}
	

	public IRowSet execute(BOSQueryDataSource ds) {
		Variant paramVal = null;
		ArrayList ps = ds.getParams();
		IRowSet iRowSet = null;
		if (ps.size() > 0) {
			DSParam param = (DSParam) ps.get(0);
			paramVal = param.getValue();
		}
		if ("MAINBILL".equals(ds.getID().toUpperCase()))// 假设主数据源名称为mainbill
		{
			// 返加主数据源的结果集
			iRowSet = getMainBillRowSet(ds);
		} else if ("PAYLIST".equals(ds.getID().toUpperCase())) {
			return getOtherSubRowSet(ds);
		} else if ("FIRSTAMOUNT".equals(ds.getID().toUpperCase())) {
			return getFirstAmountRowSet(ds);
		} else if ("CUSTOMER".equals(ds.getID().toUpperCase())){  //by zengqiang_yang
			return getCustomerRowSet(ds);
		}
		// 如果套打的元数据大于2个执行下面的函数
		else {
			return getOtherSubRowSet(ds);
		}
		return iRowSet;
	}
	
	/**
	 * @author zengqiang_yang
	 * @param ds
	 * @return IRowSet
	 */
	public IRowSet getCustomerRowSet(BOSQueryDataSource ds){
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory
					.getRemoteInstance(new MetaDataPK(
							"com.kingdee.eas.fdc.sellhouse.app.PurchaseCustomerQuery"));
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			ev.getSorter().add(new SorterItemInfo("seq"));
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("head.id", id, CompareType.EQUALS));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			// System.out.println(exec.getSQL());
			iRowSet = exec.executeQuery();
			iRowSet.beforeFirst();
		} catch (BOSException e) {
			log.error(e);
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}

		return iRowSet;
	}

	public IRowSet getFirstAmountRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory
					.getRemoteInstance(new MetaDataPK(
							"com.kingdee.eas.fdc.sellhouse.app.FisrtAmountQuery"));
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			ev.getSorter().add(new SorterItemInfo("seq"));
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("head.id", id, CompareType.EQUALS));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			// System.out.println(exec.getSQL());
			iRowSet = exec.executeQuery();
			while (iRowSet.next()) {
				BigDecimal apAmount = iRowSet.getBigDecimal("appAmount");
				String upperApAmount = FDCClientHelper.getChineseFormat(
						apAmount, false);
				iRowSet.updateString("upperApAmount", upperApAmount);
			}
			iRowSet.beforeFirst();
		} catch (BOSException e) {
			log.error(e);
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}

		return iRowSet;
	}
	
	// 传入参数元数据的的PK
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory
					.getRemoteInstance(new MetaDataPK(
							"com.kingdee.eas.fdc.sellhouse.app.PayListQuery"));
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			ev.getSorter().add(new SorterItemInfo("seq"));
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("head.id", id, CompareType.EQUALS));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			// System.out.println(exec.getSQL());
			iRowSet = exec.executeQuery();
			while (iRowSet.next()) {
				BigDecimal apAmount = iRowSet.getBigDecimal("appAmount");
				String upperApAmount = FDCClientHelper.getChineseFormat(
						apAmount, false);
				iRowSet.updateString("upperApAmount", upperApAmount);
			}
			iRowSet.beforeFirst();
		} catch (BOSException e) {
			log.error(e);
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}

		return iRowSet;
	}

	// 查询主元数据
	public IRowSet getMainBillRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(qpk);
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.EQUALS));
			//因为客户信息不只一条，这样套打其他与订购关联的属性会重复打印，那就只把主客户过滤出来。
			filter.getFilterItems().add(new FilterItemInfo("customerInfo.seq", new Integer(0), CompareType.EQUALS));
			// filter.getFilterItems().add(new
			// FilterItemInfo("payListEntry.MONEYDEFINE.moneyType",
			// MoneyTypeEnum.EARNESTMONEY_VALUE, CompareType.EQUALS));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			// System.out.println(exec.getSQL());
			iRowSet = exec.executeQuery();
			RoomInfo room = (RoomInfo)map.get("room");
			BigDecimal standardTotalAmount = room.getStandardTotalAmount();
			BigDecimal roomArea = room.getRoomArea();
			BigDecimal buildingArea = room.getBuildingArea();
			String standardBuildingPrice = (standardTotalAmount.divide(buildingArea,2,BigDecimal.ROUND_HALF_UP)).toString();
			String standardRoomPrice = (standardTotalAmount.divide(roomArea,2,BigDecimal.ROUND_HALF_UP)).toString();
			ArrayList customerList = (ArrayList) map.get("customerList");
			FDCCustomerInfo customer = (FDCCustomerInfo) customerList.get(0);
			String mainCustomerName = customer.getName();
			String mainCustomerCard = customer.getCertificateNumber();
			String mainCustomerPhone = customer.getPhone();
			
			SpecialAgioEnum splType=(SpecialAgioEnum)map.get("splType");
			BigDecimal splAgio=(BigDecimal)map.get("splAgio");
			AgioParam agioParam=(AgioParam)map.get("currAgioParam");
//			String agioDesString = SHEHelper.getAgioDes(agioParam.getAgios(),
//					splType,splAgio,agioParam.isToInteger(),agioParam.isBasePriceSell(),agioParam.getToIntegerType(), agioParam.getDigit());
			
			String mainCustomerGender = new String();
			if(customer.getSex()==null)
			{
				mainCustomerGender = null;
			}else
			{
				mainCustomerGender = customer.getSex().toString();
			}
//			String mainCustomerGender = customer.getSex().toString();
			String mainCustomerAddress = customer.getMailAddress();
			//所有客户信息长字段=客户名+身份证号
			String customersInfo = "";
			if (customerList.size() == 1) {
				customersInfo = mainCustomerName + "，" + mainCustomerCard + "。";
			} else {
				customersInfo = mainCustomerName + "，" + mainCustomerCard + "；";
			}
			for (int i = 1; i < customerList.size(); i++) {
				FDCCustomerInfo customerInfo = (FDCCustomerInfo) customerList
						.get(i);

				if (i == customerList.size() - 1) {
					customersInfo += customerInfo.getName() + "，"
							+ customerInfo.getCertificateNumber() + "。";
					break;
				}
				customersInfo += customerInfo.getName() + "，"
						+ customerInfo.getCertificateNumber() + "；";
			}
			
			BigDecimal apEarnestMoneyAmount = new BigDecimal(0);
			PurchasePayListEntryCollection apEarnestColl = PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryCollection(
					"select appAmount where head.id = '"+id+"' and moneyDefine.moneyType = '"+MoneyTypeEnum.EARNESTMONEY_VALUE+"'" );
			for(int i=0;i<apEarnestColl.size();i++) {
				apEarnestMoneyAmount = apEarnestMoneyAmount.add(apEarnestColl.get(i).getApAmount());
			}
			
			if (iRowSet.next()) {
				BigDecimal dealAmount = iRowSet.getBigDecimal("dealAmount");
				String upperDealAmount = FDCClientHelper.getChineseFormat(dealAmount, false);
				iRowSet.updateString("upperDealAmount", upperDealAmount);
				iRowSet.updateString("customer.name", mainCustomerName);
				iRowSet.updateString("customer.card", mainCustomerCard);
				iRowSet.updateString("customer.gender", mainCustomerGender);
				iRowSet.updateString("customer.phone", mainCustomerPhone);
				iRowSet.updateString("customer.address", mainCustomerAddress);
				iRowSet.updateString("customersInfo", customersInfo);
				iRowSet.updateString("standardBuildingPrice", standardBuildingPrice);
				iRowSet.updateString("standardRoomPrice", standardRoomPrice);
//				iRowSet.updateString("agioDesString", agioDesString);
				
				//应付定金金额：选择该字段，打印认购单上的定金金额。
				apEarnestMoneyAmount = apEarnestMoneyAmount.divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_UP);
				iRowSet.updateBigDecimal("apEarnestMoneyAmount", apEarnestMoneyAmount);
			}
			iRowSet.beforeFirst();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}

		return iRowSet;
	}
}
