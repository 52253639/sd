package com.kingdee.eas.fdc.tenancy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fm.common.client.FMClientHelper;
import com.kingdee.jdbc.rowset.IRowSet;

public class TenancyProvider implements BOSQueryDelegate {
	private Set set = null;
	private IObjectPK currentPK = null;
	private int databaseNumber = 3;
	private String templatePath = "";
	 

	public TenancyProvider(List list) {
		set = new HashSet(list); 
	}

	public IRowSet execute(BOSQueryDataSource ds) {
		// TODO Auto-generated method stub
		IRowSet iRowSet = null;
		IQueryExecutor exec = QueryExecutorFactory
				.getRemoteInstance(new MetaDataPK(
						"com.kingdee.eas.fdc.tenancy.app.TenancyBillTDQuery"));
		exec.option().isAutoTranslateEnum = true;
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", set, CompareType.INCLUDE));
		ev.setFilter(filter);


		IObjectPK l=new ObjectStringPK((String)set.iterator().next());
		BigDecimal appAmount = new BigDecimal("0");
		BigDecimal actAmount = new BigDecimal("0");
		BigDecimal unAmount = new BigDecimal("0");
		java.sql.Date appPayDate=null;
		java.sql.Date startDate=null;
		java.sql.Date endDate=null;
		Set se=new HashSet();
		try {
			TenancyBillInfo tenacy=TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(l);
			TenancyRoomEntryCollection cols=tenacy.getTenancyRoomList();
			for(int i=0;i<cols.size();i++){
				TenancyRoomEntryInfo iii=cols.get(i);
				se.add(iii.getId());
			}
			BigDecimal b1=tenacy.getDealTotalRent()==null?new BigDecimal("0"):tenacy.getDealTotalRent();
			BigDecimal b2=tenacy.getDepositAmount()==null?new BigDecimal("0"):tenacy.getDepositAmount();
			BigDecimal b3=tenacy.getLateFeeAmount()==null?new BigDecimal("0"):tenacy.getLateFeeAmount();
			appAmount=b1.add(b2).add(b3);
			appPayDate=tenacy.getAppPayDate()==null?null:new java.sql.Date(tenacy.getAppPayDate().getTime());
			 startDate=tenacy.getStartDate()==null?null:new java.sql.Date(tenacy.getStartDate().getTime());
			 endDate=tenacy.getEndDate()==null?null:new java.sql.Date(tenacy.getEndDate().getTime());
			
			
		} catch (EASBizException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (BOSException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		EntityViewInfo ev1 = new EntityViewInfo();
		FilterInfo filter1 = new FilterInfo();
	
		filter1.getFilterItems().add(
				new FilterItemInfo("tenRoom.id", se, CompareType.INCLUDE));
		//TenancyBillInfo bill=
		ev1.setFilter(filter1);


		TenancyRoomPayListEntryCollection c=null;
		try {
			 c=TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryCollection(ev1);
			 for(int i=0;i<c.size();i++){
				 TenancyRoomPayListEntryInfo is=c.get(i);
					appPayDate=is.getAppPayDate()==null?null:new java.sql.Date(is.getAppPayDate().getTime());
					 startDate=is.getStartDate()==null?null:new java.sql.Date(is.getStartDate().getTime());
					 endDate=is.getEndDate()==null?null:new java.sql.Date(is.getEndDate().getTime());
					 BigDecimal aa= is.getActAmount()==null?new BigDecimal("0"):is.getActAmount();
					 actAmount = actAmount.add(aa);
			 }
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList param = ds.getParams(); 
		 
		try {
			FMClientHelper.addDataFilter(filter, param);
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exec.setObjectView(ev);
		try {
			iRowSet = exec.executeQuery();

			String fh = "";	 
           
			while (iRowSet.next()) {
				String roomNumber = iRowSet.getString("room.number");// 房号
				fh = fh + roomNumber + ",";// 房间号 以','隔开显示
			}
			unAmount = appAmount.subtract(actAmount);
			iRowSet = exec.executeQuery();
			while (iRowSet.next()){
			iRowSet.updateBigDecimal("roomPayList.appAmount", appAmount);
			iRowSet.updateBigDecimal("roomPayList.actAmount", actAmount);
			iRowSet.updateBigDecimal("unAmount", unAmount);
			Date d=SHEHelper.getServerDate2();  
			  java.sql.Date   a   =   new java.sql.Date(d.getTime());   
			  iRowSet.updateDate("roomPayList.appPayDate", appPayDate);
			  iRowSet.updateDate("roomPayList.startDate", startDate);
			  iRowSet.updateDate("roomPayList.endDate", endDate);
			iRowSet.updateDate("printDate",a);// 打印日期
			iRowSet.updateString("room.number", fh);
			}
			iRowSet.beforeFirst();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return iRowSet;

	}

}
