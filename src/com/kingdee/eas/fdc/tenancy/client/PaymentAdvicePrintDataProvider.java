package com.kingdee.eas.fdc.tenancy.client;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.data.impl.ICrossPrintDataProvider;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.tenancy.PaymentAdvicePrintFacadeFactory;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;

public class PaymentAdvicePrintDataProvider implements ICrossPrintDataProvider {
	private static Logger logger = Logger.getLogger(PaymentAdvicePrintDataProvider.class);
	static public final String[] col = new String[] {"id","startdate","enddate","appdate","appamount", 
		"actamount","contractname","roomname","projectname","customer","moneyname","balance","tarea"};
	static public final String[] col1 = new String[] {"comment"};
	private Set ids = null;
	public PaymentAdvicePrintDataProvider(List id) {
        this.ids = new HashSet(id);;
        
    }
	private boolean ishasNext=true;
	public boolean hasNext() {
			if(ishasNext){
				ishasNext=false;
				return true;
			}else{
				return false;
			}
	}
	
	/**
	 * 取得合同 用户对照数据
	 * @return
	 */
	private List getCC(){
		FDCSQLBuilder zfsqlBuilder = new FDCSQLBuilder();
		zfsqlBuilder.appendSql(" select  tenancyBill.fid as billID,tenancyCustomerEntry.ffdccustomerid as customerID ,fdccustomer.fname_l2 as customer ")
		.appendSql("  from T_TEN_TenancyCustomerEntry tenancyCustomerEntry  ")
		.appendSql("  join T_TEN_TenancyBill tenancyBill        on tenancyBill.fid = tenancyCustomerEntry.ftenancybillid ")
		.appendSql("  left join T_SHE_FDCCustomer fdccustomer   on fdccustomer.fid = tenancyCustomerEntry.ffdccustomerid ")
		.appendSql(" left join T_SHE_SellProject SellProject on  tenancyBill.FSellProjectID =SellProject.Fid ");
		IRowSet zfrs=null;
		try {
			zfrs = zfsqlBuilder.executeQuery();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			logger.debug(e);
		}
		List ccList = new ArrayList();
		try {
			Map cc=null; ;
			while(zfrs.next()){
				cc = new HashMap();
				String billId =zfrs.getString("billId");
				String customername =zfrs.getString("customer");
				cc.put("billId", billId);
				cc.put("customername", customername);
				ccList.add(cc);
			}
		} catch (SQLException e) {
			logger.debug(e);
		}
		return ccList;
		
	}
	public IRowSet execute(BOSQueryDataSource ds)
	{
		IRowSet iRowSet = null;
		DynamicRowSet row = null;
		try {
			// 假设主数据源名称为mainbill
			if ("MAINBILL".equals(ds.getID().toUpperCase())){
				// 返加主数据源的结果集
					row = new DynamicRowSet(col.length);			
					for (int i = 0; i < col.length; i++) {
						ColInfo ci = new ColInfo();
						ci.colType = Types.VARCHAR;
						ci.columnName = col[i];
						ci.nullable = 1;
						row.setColInfo(i + 1, ci);
					}
				
					iRowSet = PaymentAdvicePrintFacadeFactory.getRemoteInstance().getPrintData(ids);
//					List resultList = new ArrayList();
					while(iRowSet.next()){
						String contractname = iRowSet.getString("contractname");
						
						String roomname ="";
						if(iRowSet.getString("contractname")!=null){
							if(iRowSet.getString("contractname").equals(iRowSet.getString("roomname"))){
								roomname="";
							}else{
								roomname= iRowSet.getString("roomname");
							}
						}else{
							roomname= iRowSet.getString("roomname");
						}
						String projectname = iRowSet.getString("projectname");
						
						//处理客户显示问题
						String customer = "";
						Map cc =null; 
						String contractid =iRowSet.getString("contractid");
						int i=0;
						List ccList =this.getCC();
						for(Iterator iter = ccList.iterator();iter.hasNext();){
							cc=(HashMap)iter.next();
							if(cc.containsKey("billId")&&cc.get("billId")!=null){
								String billId=cc.get("billId").toString();
								if(billId.equals(contractid)){
									if(i==0){
										customer=customer+cc.get("customername").toString();
										i=1;
									}else{
										customer=customer+","+cc.get("customername").toString();
									}
								}
							}
						}
						String moneyname = iRowSet.getString("moneyname");
						Date startdate =null;
						if(iRowSet.getDate("startdate")!=null){
							 startdate =iRowSet.getDate("startdate");
						}
						Date enddate =null;
						if(iRowSet.getDate("startdate")!=null){
							enddate =iRowSet.getDate("enddate");
						}
						Date appdate =iRowSet.getDate("appdate");
						BigDecimal appamount =FDCHelper.divide(iRowSet.getBigDecimal("appamount"), new BigDecimal("1"));
						
						BigDecimal actamount1=null;
						if(iRowSet.getBigDecimal("actamount")==null){
							actamount1=new BigDecimal("0");
						}else{
							actamount1 = iRowSet.getBigDecimal("actamount");
						}
						BigDecimal tamount=null;
						if(iRowSet.getBigDecimal("tamount")==null){
							tamount=new BigDecimal("0");
						}else{
							tamount = iRowSet.getBigDecimal("tamount");
						}
						BigDecimal ramount=null;
						if(iRowSet.getBigDecimal("ramount")==null){
							ramount=new BigDecimal("0");
						}else{
							ramount = iRowSet.getBigDecimal("ramount");
						}
						BigDecimal aamount=null;
						if(iRowSet.getBigDecimal("aamount")==null){
							aamount=new BigDecimal("0");
						}else{
							aamount = iRowSet.getBigDecimal("aamount");
						}
						BigDecimal tarea=null;
						if(iRowSet.getBigDecimal("tarea")!=null){
							if(iRowSet.getBigDecimal("tarea").compareTo(new BigDecimal("-1"))!=0){
								tarea=iRowSet.getBigDecimal("tarea");
							}else{
								tarea=new BigDecimal("0");
							}
						}else{
							tarea=new BigDecimal("0");
						}
						tarea=FDCHelper.divide(tarea, new BigDecimal("1"));
						//实收金额=已收金额-已退-已转-已调
						BigDecimal actamount =actamount1.subtract(tamount).subtract(ramount).subtract(aamount);
						actamount = FDCHelper.divide(actamount, new BigDecimal("1"));
						BigDecimal balance =(FDCHelper.subtract(appamount, actamount));
						String id = iRowSet.getString("id");
						
										
						row.moveToInsertRow();
						row.updateString("contractname",contractname);
						row.updateString("roomname",roomname);
						row.updateString("projectname",projectname);
						row.updateString("customer",customer);
						row.updateString("moneyname",moneyname);
						
						row.updateString("startdate",startdate==null?"":startdate.toString());
						row.updateString("enddate",enddate==null?"":enddate.toString());
						row.updateDate("appdate",appdate);
						row.updateBigDecimal("tarea", tarea);
						if(actamount == null){
							actamount = FDCHelper.ZERO;
						}
						if(appamount == null){
							appamount = FDCHelper.ZERO;
						}
						row.updateBigDecimal("appamount",appamount);
						row.updateBigDecimal("actamount",actamount);
						row.updateBigDecimal("balance",balance);
						row.updateString("id",id);
						row.insertRow();
					}
									
					
				
//			} else if ("COMMENT".equals(ds.getID().toUpperCase())) {
//				row = new DynamicRowSet(col1.length);	
//				for (int i = 0; i < col1.length; i++) {
//					ColInfo ci = new ColInfo();
//					ci.colType = Types.VARCHAR;
//					ci.columnName = col1[i];
//					ci.nullable = 1;
//					row.setColInfo(i + 1, ci);
//				}
//				iRowSet =  PaymentAdvicePrintFacadeFactory.getRemoteInstance().getComment();
//				while(iRowSet.next()){
//					row.moveToInsertRow();
//					String comment = iRowSet.getString("comment");
//					row.updateString("comment",comment);
//					row.insertRow();
//				}
//			}
			}
		} catch (BOSException e) {
				 e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return row;
	}
}
