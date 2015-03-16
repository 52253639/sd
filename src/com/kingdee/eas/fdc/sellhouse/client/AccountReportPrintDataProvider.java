package com.kingdee.eas.fdc.sellhouse.client;
/***
 * by ....wancheng
 * */
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.impl.ICrossPrintDataProvider;
import com.kingdee.eas.fdc.sellhouse.AccountReportFacadeFactory;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;

public class AccountReportPrintDataProvider  implements ICrossPrintDataProvider {
	
	public  AccountReportPrintDataProvider(List id) {
		 ids = null;
	     ishasNext = true;
	     ids = new HashSet(id);
	}
	public boolean hasNext()
	{
	     if(ishasNext)
	        {
	            ishasNext = false;
	            return true;
	        } else
	        {
	            return false;
	        }
	}

	public IRowSet execute(BOSQueryDataSource ds) 
	{
		 IRowSet iRowSet = null;
	     DynamicRowSet row = null;
	     try
	     {
	    	 row = new DynamicRowSet(col.length);
             for(int i = 0; i < col.length; i++)
             {
                 ColInfo ci = new ColInfo();
                 ci.colType = 12;
                 ci.columnName = col[i];
                 ci.nullable = 1;
				 row.setColInfo(i + 1, ci);
             }
				for(iRowSet = AccountReportFacadeFactory.getRemoteInstance().getPrintData(ids);iRowSet.next(); row.insertRow())
				  {
					row.moveToInsertRow();
					String id = iRowSet.getString("楼号");
					if(id!=null){
						row.updateString("id", id);//房间
					}
					String customerNames = iRowSet.getString("客户");
					if(customerNames!=null){
						row.updateString("customerNames", customerNames);//客户
					}
					String saleManNames = iRowSet.getString("置业顾问");
					if(saleManNames!=null){
						row.updateString("saleManNames", saleManNames);//置业顾问
					}
					Date FBusAdscriptionDate = iRowSet.getDate("签约日期");
					if(FBusAdscriptionDate!=null){
						row.updateDate("bizDate", FBusAdscriptionDate);//签约日期
					}
					String  payName = iRowSet.getString("款项名称");
					if(payName!=null){
						row.updateString("payType.name", payName);//付款方案
					}
					BigDecimal FAppAmount = iRowSet.getBigDecimal("应收款");
					if(FAppAmount!=null){
						row.updateBigDecimal("signPayListEntry.appAmount", FAppAmount);//应收金额
					}
					BigDecimal notFAppAmount = iRowSet.getBigDecimal("未收款");
					if(notFAppAmount!=null){
						row.updateBigDecimal("signPayListEntry.actRevAmount", notFAppAmount);//未收金额
					}
					BigDecimal defaultAmount = iRowSet.getBigDecimal("违约金");
					if(defaultAmount!=null){
						row.updateBigDecimal("loanAmount", defaultAmount);//违约金
					} 
					int day = iRowSet.getInt("违约天数");
					row.updateInt("room.unit", day);//违约天数
					
					Date appDate = iRowSet.getDate("应缴日期");
					if(appDate!=null){
						row.updateDate("signPayListEntry.appDate", appDate);//应缴日期	
					}
					Date lastDate = iRowSet.getDate("最晚回款日期");
					if(lastDate!=null){
						row.updateDate("joinInDate", lastDate);//最晚日期	
					}
					row.updateBigDecimal("accFundAmount", defaultAmount.add(notFAppAmount));//共计
					Date   now =new   Date(System.currentTimeMillis()); //当前系统时间
					row.updateDate("onRecordDate", now);//签约日期
					}
				} catch (BOSException e) {
					e.printStackTrace();
				} catch (SQLException e) {
				}
		return row;
	 }
	 static Class _mthclass$(String x0) throws Throwable
	    {
	        try
	        {
	            return Class.forName(x0);
	        }
	        catch(ClassNotFoundException x1)
	        {
	            throw (new NoClassDefFoundError()).initCause(x1);
	        }
	    }
	private static Logger logger;
	private Set ids;
	private boolean ishasNext;
	public static final String col[] = {
	        "id", "saleManNames", "contractTotalAmount", "bizDate", "signPayListEntry.appAmount", "signPayListEntry.appDate", 
	        "signPayListEntry.actRevAmount", "room.unit", "room.floor", "room.serialNumber", "LoanBank.name", "sellProject.name", 
	        "payType.name","customerNames","onRecordDate","joinInDate","loanAmount","accFundAmount","fitmentPrice"};

	static 
	{
	      logger = Logger.getLogger(com.kingdee.eas.fdc.sellhouse.client.AccountReportPrintDataProvider.class);
	}

}
