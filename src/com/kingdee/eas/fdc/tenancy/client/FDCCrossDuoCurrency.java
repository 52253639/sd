package com.kingdee.eas.fdc.tenancy.client;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.data.impl.ICrossPrintDataProvider;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.tenancy.TENRevHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;

public class FDCCrossDuoCurrency implements ICrossPrintDataProvider{
	private static Logger log = Logger.getLogger(FDCCrossDuoCurrency.class);
	static public final String[] col = new String[] {"moneyDefine.number","moneyDefine.name","settlementNumber","revAccount.name",
		"revAccount.number","revAccountBank.name","revAccountBank.number","bankNumber","revAmount","oppAccount.name","oppAccount.number",
		"settlementType.name","settlementType.number","revListId","revListType","appAmount","actRevAmount","hasTransferredAmount",
		"hasRefundmentAmount","hasAdjustedAmount","sourceEntries.fromRevListId","sourceEntries.fromRevListType","sourceEntries.amount",
		"roomName","startDate","endDate","term","head.id","id"};
	private Set ids = null;
	private String tdType = null;
    private IMetaDataPK qpk = null;
    private String templatePath=null;
    private boolean ishasNext=true;
    public FDCCrossDuoCurrency(List id,IMetaDataPK qpk,String tdType) {
        this.ids = new HashSet(id);;
        this.qpk = qpk;
        this.tdType = tdType;
    }

    public FDCCrossDuoCurrency(List id,IMetaDataPK qpk,String templatePath,String tdType) {
        this.ids = new HashSet(id);
        this.qpk = qpk;
        this.templatePath=templatePath;
        this.tdType = tdType;
    }

	public boolean hasNext() {
		if(ishasNext){
			ishasNext=false;
			return true;
		}
		else{
			return false;
		}
	}

	public IRowSet execute(BOSQueryDataSource ds)
	{
		Variant paramVal = null;
		ArrayList ps = ds.getParams();
		IRowSet iRowSet = null;
		if (ps.size() > 0) {
			DSParam param = (DSParam) ps.get(0);
			paramVal = param.getValue();
		}
		if("receivePrint".equals(tdType))
		{
			if ("MAINBILL".equals(ds.getID().toUpperCase()))// 假设主数据源名称为mainbill
			{
				// 返加主数据源的结果集
				iRowSet = getMainBillRowSet(ds);
			} else if ("BILLENTRY".equals(ds.getID().toUpperCase())) {
				iRowSet =  getOtherSubRowSet(ds);
			} 
		}else if("moneyDefine".equals(tdType))
		{
			if ("MAINBILL".equals(ds.getID().toUpperCase()))
			{
				// 返加主数据源的结果集
				iRowSet = getMainBillRowSet(ds);
			} else if ("BILLENTRY".equals(ds.getID().toUpperCase())) {
				iRowSet =  getOtherSubRowSet2(ds);
			} 
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
			filter.getFilterItems().add(new FilterItemInfo("id", ids, CompareType.INCLUDE));
			ev.setFilter(filter);
			exec.setObjectView(ev);

			iRowSet = exec.executeQuery();
			while(iRowSet.next())
			{
				String id = iRowSet.getString("id");
				SelectorItemCollection sel=new SelectorItemCollection();
				sel.add("obligateObj.name");
				sel.add("obligateObj.number");
				FDCReceivingBillInfo info = FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillInfo(new ObjectUuidPK(id), sel);
				if(info.getObligateObj()!=null)
				{
					String sincerObligateName = info.getObligateObj().getName();
					String sincerObligateNumber = info.getObligateObj().getNumber();
					iRowSet.updateString("sincerObliateName", sincerObligateName);
					iRowSet.updateString("sincerObliateNumber", sincerObligateNumber);
				}
			}
			iRowSet.beforeFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iRowSet;
	}
	
	// 查询收款单分录数据(按收款单套打)
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
		IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.tenancy.app.TENReceivingBillEntryTDQuery"));
		exec.option().isAutoTranslateEnum = true;
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("head.id", ids, CompareType.INCLUDE));
		ev.setFilter(filter);
		exec.setObjectView(ev);
		iRowSet = exec.executeQuery();
		while (iRowSet.next()) {
			String revListId = iRowSet.getString("revListId");
			BizEnumValueInfo ob= (BizEnumValueInfo)iRowSet.getObject("revListType");
			String stra = (String)ob.getValue();
			RevListTypeEnum revListType = RevListTypeEnum.getEnum(stra);
			ICoreBase iface = TENRevHelper.getRevListBizInterface(null, revListType);
			IRevListInfo revList = (IRevListInfo) iface.getValue(new ObjectUuidPK(revListId));
			if(revList instanceof TenancyRoomPayListEntryInfo)
			{				
				TenancyRoomPayListEntryInfo payList = (TenancyRoomPayListEntryInfo)revList;
				SelectorItemCollection sel=new SelectorItemCollection();
				sel.add("tenRoom.room.name");
				sel.add("tenRoom.room.number");
				sel.add("tenRoom.roomLongNum");
				sel.add("leaseSeq");
				sel.add("startDate");
				sel.add("endDate");
				sel.add("freeDays");
				sel.add("remissionAmount");
				payList = TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryInfo(new ObjectUuidPK(payList.getId()), sel);
				iRowSet.updateString("roomName", payList.getTenRoom().getRoomLongNum());
				iRowSet.updateDate("startDate", (java.sql.Date)payList.getStartDate());
				iRowSet.updateDate("endDate", (java.sql.Date) payList.getEndDate());
				iRowSet.updateInt("term", payList.getLeaseSeq());
			}else{
				iRowSet.updateString("roomName",null);
				iRowSet.updateDate("startDate",null);
				iRowSet.updateDate("endDate",null);
				iRowSet.updateInt("term",0);
			}
			BigDecimal appAmount = revList.getAppAmount();
			BigDecimal actRevAmount = revList.getActRevAmount();
			BigDecimal hasTransferredAmount = revList.getHasTransferredAmount();
			BigDecimal hasRefundmentAmount = revList.getHasRefundmentAmount();
			BigDecimal hasAdjustedAmount = revList.getHasAdjustedAmount();
			BigDecimal revAmount = actRevAmount.subtract(hasTransferredAmount).subtract(hasRefundmentAmount).subtract(hasAdjustedAmount);
			iRowSet.updateBigDecimal("appAmount", appAmount);
			iRowSet.updateBigDecimal("actRevAmount", revAmount);
			iRowSet.updateBigDecimal("hasTransferredAmount", hasTransferredAmount);
			iRowSet.updateBigDecimal("hasRefundmentAmount", hasRefundmentAmount);
			iRowSet.updateBigDecimal("hasAdjustedAmount", hasAdjustedAmount);
		}
		iRowSet.beforeFirst();
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return iRowSet;
	}
	
	// 查询收款单分录数据(按款项套打)
	public IRowSet getOtherSubRowSet2(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		int colCount = col.length;
		DynamicRowSet row = null;
		try {
		row = new DynamicRowSet(colCount);			
		for (int i = 0; i < colCount; i++) {
			ColInfo ci = new ColInfo();
			ci.colType = Types.VARCHAR;
			ci.columnName = col[i];
			ci.nullable = 1;
			row.setColInfo(i + 1, ci);
		}
		String roomName = "";
		Date startDate = new Date();
		Date endDate = new Date();
		int term = 0;
		row.beforeFirst();
		Map map = new HashMap();
		List list = new ArrayList();
		IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.tenancy.app.TENReceivingBillEntryTDQuery"));
		exec.option().isAutoTranslateEnum = true;
		EntityViewInfo ev = new EntityViewInfo();
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("moneyDefine.name");
		sic.add("moneyDefine.number");
		sic.add("settlementNumber");
		sic.add("bankNumber");
		sic.add("revAmount");
		sic.add("revListId");
		sic.add("revListType");
		sic.add("revAccount.name");
		sic.add("revAccount.number");
		sic.add("revAccountBank.name");
		sic.add("revAccountBank.number");
		sic.add("oppAccount.name");
		sic.add("oppAccount.number");
		sic.add("settlementType.name");
		sic.add("settlementType.number");
		sic.add("sourceEntries.fromRevListId");
		sic.add("sourceEntries.fromRevListType");
		sic.add("sourceEntries.amount");
		sic.add("head.id");
		sic.add("id");		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("head.id", ids, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.id", templatePath));
		ev.setFilter(filter);
		exec.setObjectView(ev);
		iRowSet = exec.executeQuery();
		while (iRowSet.next()) {
			String revListId = iRowSet.getString("revListId");
			BizEnumValueInfo ob= (BizEnumValueInfo)iRowSet.getObject("revListType");
			String stra = (String)ob.getValue();
			RevListTypeEnum revListType = RevListTypeEnum.getEnum(stra);
			ICoreBase iface = TENRevHelper.getRevListBizInterface(null, revListType);
			IRevListInfo revList = (IRevListInfo) iface.getValue(new ObjectUuidPK(revListId));
			StringBuffer key = new StringBuffer();
			String moneyDefineID = revList.getMoneyDefine().getId().toString();
			if(revList instanceof TenancyRoomPayListEntryInfo)
			{				
				TenancyRoomPayListEntryInfo payList = (TenancyRoomPayListEntryInfo)revList;
				SelectorItemCollection sel=new SelectorItemCollection();
				sel.add("tenRoom.room.name");
				sel.add("tenRoom.room.number");
				sel.add("tenRoom.roomLongNum");
				sel.add("leaseSeq");
				sel.add("startDate");
				sel.add("endDate");
				sel.add("freeDays");
				sel.add("remissionAmount");
				payList = TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryInfo(new ObjectUuidPK(payList.getId()), sel);
				String roomID = payList.getTenRoom().getId().toString();
				int seq = payList.getLeaseSeq();
				key.append(moneyDefineID).append(roomID).append(seq);
				roomName = payList.getTenRoom().getRoomLongNum();
				startDate = payList.getStartDate();
				endDate = payList.getEndDate();
				term = payList.getLeaseSeq();
			}else
			{
				//其他应收和诚意预留收款全都没有租期和房间的概念，所以只要款项相同就全部合并
				key.append(moneyDefineID);
				startDate = null;
				endDate = null;
			}
			//结算方式
			String settlementTypeName = iRowSet.getString("settlementType.name");
			String settlementTypeNumber = iRowSet.getString("settlementType.number");
			String moneyDefineName = iRowSet.getString("moneyDefine.name");;
			String moneyDefineNumber = iRowSet.getString("moneyDefine.number");
			String settlementNumber = iRowSet.getString("settlementNumber");
			String revAccountName = iRowSet.getString("revAccount.name");
			String revAccountNumber = iRowSet.getString("revAccount.number");
			String bankNumber = iRowSet.getString("bankNumber");
			BigDecimal revAmount = iRowSet.getBigDecimal("revAmount");
			String oppAccountName = iRowSet.getString("oppAccount.name");
			String oppAccountNnumber = iRowSet.getString("oppAccount.number");
			String fromRevListId = iRowSet.getString("sourceEntries.fromRevListId");
			String fromRevListType = iRowSet.getString("sourceEntries.fromRevListType");
			String headId = iRowSet.getString("head.id");
			String id = iRowSet.getString("id");
			BigDecimal appAmount = revList.getAppAmount();
			BigDecimal actRevAmount = revList.getActRevAmount();
			BigDecimal hasTransferredAmount = revList.getHasTransferredAmount();
			BigDecimal hasRefundmentAmount = revList.getHasRefundmentAmount();
			BigDecimal hasAdjustedAmount = revList.getHasAdjustedAmount();
			BigDecimal recrevAmount = actRevAmount.subtract(hasTransferredAmount).subtract(hasRefundmentAmount).subtract(hasAdjustedAmount);
			if(map.get(key.toString())==null)
			{
				Object[] obj = new Object[colCount];
				obj[0] = moneyDefineID;
				obj[1] = moneyDefineName;
				obj[2] = moneyDefineNumber;
				obj[3] = settlementNumber;
				obj[4] = revAccountName;
				obj[5] = revAccountNumber;			
				obj[6] = bankNumber;
				obj[7] = revAmount;
				obj[8] = oppAccountName;
				obj[9] = oppAccountNnumber;			
				obj[10] = fromRevListId;
				obj[11] = fromRevListType;
				obj[12] = headId;
				obj[13] = id;
				obj[14] = appAmount;
				obj[15] = actRevAmount;
				obj[16] = hasTransferredAmount;
				obj[17] = hasRefundmentAmount;
				obj[18] = hasAdjustedAmount;
				obj[19] = recrevAmount;
				obj[20] = roomName;
				obj[21] = startDate;
				obj[22] = endDate;
				obj[23] = new Integer(term);
				obj[24] = settlementTypeName;
				obj[25] = settlementTypeNumber;
				list.add(obj);
				map.put(key.toString(), obj);
			}else if(map.get(key.toString())!=null)
			{
				Object[] obj = (Object[])map.get(key.toString());
				obj[0] = moneyDefineID;
				obj[1] = moneyDefineName;
				obj[2] = moneyDefineNumber;
				obj[3] = settlementNumber;
				obj[4] = revAccountName;
				obj[5] = revAccountNumber;			
				obj[6] = bankNumber;
				obj[7] = ((BigDecimal)obj[7]).add(revAmount);
				obj[8] = oppAccountName;
				obj[9] = oppAccountNnumber;			
				obj[10] = fromRevListId;
				obj[11] = fromRevListType;
				obj[12] = headId;
				obj[13] = id;
				//只有房间款项和租期相同的收款明细才进行合并，也就是只有当同一款项用不同结算方式收款的时候收款金额才需要合并
				//其他应收已收金额等都不需要合并
				obj[14] = appAmount;
				obj[15] = actRevAmount;
				obj[16] = hasTransferredAmount;
				obj[17] = hasRefundmentAmount;
				obj[18] = hasAdjustedAmount;
				obj[19] = recrevAmount;
				obj[20] = roomName;
				obj[21] = startDate;
				obj[22] = endDate;
				obj[23] = new Integer(term);
				obj[24] = settlementTypeName;
				obj[25] = settlementTypeNumber;
			}
		}
		for(int i=0;i<list.size();i++)
		{
			Object[] object = (Object[])list.get(i);
			row.moveToInsertRow();
			row.updateString("moneyDefine.name",(String)object[1]);
			row.updateString("moneyDefine.number",(String)object[2]);
			row.updateString("settlementNumber",(String)object[3]);
			row.updateString("revAccount.name",(String)object[4]);
			row.updateString("revAccount.number",(String)object[5]);
			row.updateString("bankNumber",(String)object[6]);
			
			row.updateBigDecimal("revAmount",(BigDecimal)object[7]);
			row.updateString("oppAccount.name",(String)object[8]);
			row.updateString("oppAccount.number",(String)object[9]);
			
			row.updateString("sourceEntries.fromRevListId",(String)object[10]);
			row.updateString("sourceEntries.fromRevListType",(String)object[11]);
			row.updateString("head.id",(String)object[12]);
			
			row.updateString("id",(String)object[13]);
			row.updateString("appAmount",printStringHelper(object[14]));
			
			row.updateBigDecimal("actRevAmount",(BigDecimal)object[15]);
			row.updateBigDecimal("hasTransferredAmount",(BigDecimal)object[16]);
			row.updateBigDecimal("hasRefundmentAmount",(BigDecimal)object[17]);
			row.updateBigDecimal("hasAdjustedAmount",(BigDecimal)object[18]);
			
			row.updateString("roomName", (String)object[20]);
			if(object[21]==null && object[22]==null)
			{
				row.updateString("startDate", "");
				row.updateString("endDate", "");
			}else
			{
				row.updateDate("startDate", (java.sql.Date)object[21]);
				row.updateDate("endDate", (java.sql.Date)object[22]);
			}			
			row.updateInt("term", ((Integer)object[23]).intValue());
			row.updateString("settlementType.name",(String)object[24]);
			row.updateString("settlementType.number",(String)object[25]);
			row.insertRow();
		}
		row.beforeFirst();
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return row;
	}
	
	public static String printStringHelper(Object o) {
		if (o == null)
			return "";
		else if(o instanceof BigDecimal){
			if(FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(o))==0)
				return "";
			else
				return String.valueOf(((BigDecimal)o).setScale(2,BigDecimal.ROUND_HALF_UP));
		}
			return String.valueOf(o);
	}	
}
