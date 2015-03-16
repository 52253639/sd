package com.kingdee.eas.fdc.tenancy.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class CustomerTenancyAreaChangeFacadeControllerBean extends AbstractCustomerTenancyAreaChangeFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.CustomerTenancyAreaChangeFacadeControllerBean");
    protected Map _getValueMap(Context ctx, Map ID)throws BOSException {
    	//定义一个映射键值对，面积所处状态
    	Map valueMap = new HashMap();
    	valueMap.put("All", "all"); 
    	valueMap.put("NewTenancy", "新租"); 
    	valueMap.put("EnlargeTenancy", "扩租"); 
    	valueMap.put("ReduceTenancy", "缩租"); 
    	valueMap.put("QuitTenancy", "退租"); 
    	
    	List list = new ArrayList();
    	Date beginDate = null;
    	Date endDate = null;
    	Map listMap = new HashMap();
    	boolean flag = false;
    	int k = 0;
    	String projectName = "-1";
		String customer  = "-1";
		String building = "-1";
		String contractNumber = "-1";
		String room = "-1";
		BigDecimal area = new BigDecimal(0);
		BigDecimal oldarea = new BigDecimal(0);
		BigDecimal newarea = new BigDecimal(0);
		BigDecimal netarea= new BigDecimal(0);
		Date start = null;
		Date end = null;
    	String oldprojectName = "-1";
		String oldcustomer  = "-1";
		String oldcontractNumber = "-1";
		String newbuilding = null;
		String newcontractNumber = null;
		String newroom = null;
		String newstratroom = "";
		String newendroom = "";
		String customerid = "";
		String cpstateid = "";
    	if(ID.get("sellProject") instanceof List ){
    		list  = (List)ID.get("sellProject");
    	}
    	if(ID.get("beginDate") instanceof Date ){
    		beginDate  = (Date)ID.get("beginDate");
    	}
    	if(ID.get("endDate") instanceof Date ){
    		endDate = (Date)ID.get("endDate");
    	}
    	if(ID.get("f7Customer") instanceof String){
    		customerid = (String)ID.get("f7Customer");
    	}
    	if(ID.get("cpState") instanceof String){
    		cpstateid = (String)ID.get("cpState");
    	}
    	if(list!=null){
    		for(int i=0;i<list.size();i++){
    			if(list.get(i) instanceof SellProjectInfo){
    				SellProjectInfo sellProjectInfo = (SellProjectInfo)list.get(i);
	    			String sellProjectID = sellProjectInfo.getId().toString();
			    	StringBuffer sb  = new StringBuffer();
			    	sb.append("SELECT distinct SellProject.fname_l2,TENANCYBILL.fnumber,");
			    	sb.append("TENANCYBILL.FTenCustomerDes,TENANCYBILL.FTenRoomsDes,Building.fname_l2,");
			    	sb.append("Room.fname_l2,Room.FTenancyArea,TENANCYBILL.FStartDate,TENANCYBILL.FEndDate ");
			    	sb.append("FROM T_TEN_TenancyBill AS TENANCYBILL ");
			    	sb.append("left outer join T_SHE_SellProject as SellProject ");
			    	sb.append("on SellProject.fid  = TENANCYBILL.FSellProjectID ");
			    	sb.append("left outer join T_TEN_TenancyRoomEntry as TenancyRoomEntry ");
			    	sb.append("on TENANCYBILL.fid  = TenancyRoomEntry.FTenancyID ");
			    	sb.append("left outer join T_TEN_TenancyCustomerEntry as TenancyCustomerEntry ");
			    	sb.append("on TENANCYBILL.fid  = TenancyCustomerEntry.FTenancyBillID ");
			    	sb.append("inner join T_SHE_FDCCustomer as FDCCustomer ");
			    	sb.append("on  TenancyCustomerEntry.FFdcCustomerID= FDCCustomer.fid ");
			    	sb.append("left outer join T_SHE_Room as Room ");
			    	sb.append("on TenancyRoomEntry.FRoomID = Room.fid ");
			    	sb.append("left outer join T_SHE_Building as Building ");
			    	sb.append("on Room.FBuildingID = Building.fid ");
			    	sb.append("LEFT OUTER JOIN T_PM_User AS TENANCYADVISER ");
			    	sb.append("ON TENANCYBILL.FTenancyAdviserID = TENANCYADVISER.FID ");
			    	sb.append("LEFT OUTER JOIN T_TEN_Agency AS AGENCY ");
			    	sb.append("ON TENANCYBILL.FAgencyID = AGENCY.FID ");
			    	sb.append("LEFT OUTER JOIN T_TEN_TenancyBill AS OLDTENANCYBILL ");
			    	sb.append("ON TENANCYBILL.FOldTenancyBillID = OLDTENANCYBILL.FID ");
			    	sb.append("LEFT OUTER JOIN T_PM_User AS CREATOR ");
			    	sb.append("ON TENANCYBILL.FCreatorID = CREATOR.FID ");
			    	sb.append("where TENANCYBILL.FTenancyState !='Saved' and TENANCYBILL.FTenancyState !='Submitted' ");
			    	sb.append("and TENANCYBILL.FTenancyState !='Auditing' and  SellProject.fid = '");
			    	sb.append(sellProjectID).append("' ");			    	
			    	if(customerid!=null&&(!customerid.equals(""))){
			    		sb.append("and FDCCustomer.fid = '").append(customerid).append("' ");
			    	}
			    	sb.append("order by SellProject.fname_l2,TENANCYBILL.FTenCustomerDes,TENANCYBILL.fnumber");
			    	IRowSet  rs = DbUtil.executeQuery(ctx,sb.toString());
			    	try {
						while(rs.next()){
							//获取当前记录数
							projectName = rs.getString(1)!=null?rs.getString(1):"";
							customer  = rs.getString(3)!=null?rs.getString(3):"";
							building = rs.getString(5)!=null?rs.getString(5):"";
							room = rs.getString(6)!=null?rs.getString(6):"";
							contractNumber = rs.getString(2)!=null?rs.getString(2):"";
							start  = rs.getDate(8)!=null?rs.getDate(8):new Date();
							end  = rs.getDate(9)!=null?rs.getDate(9):new Date();
							area = rs.getBigDecimal(7)!=null?rs.getBigDecimal(7):new BigDecimal(0);
							//当前数据与前一条数据进行比较，如果相同（工程名称和客户）
							// 则进行融合
							if (oldprojectName.equals(projectName)
									&& oldcustomer.equals(customer)) {
								if (newroom.indexOf(room) == -1) {
									newroom = newroom + "," + room;
								}
								if (start.before(beginDate)) {
									if(newstratroom.indexOf(room) == -1) {
										newstratroom = newstratroom + "," + room;
										oldarea = oldarea.add(area);
									}
								}
								if(((!start.before(beginDate))&&(start.before(endDate)))||
										((!end.before(beginDate))&&(end.before(endDate)))){
									if(newendroom.indexOf(room) == -1) {
										newendroom = newendroom + "," + room;
										newarea = newarea.add(area);
									}
								}

								if (!oldcontractNumber.equals(contractNumber)) {
									newcontractNumber = newcontractNumber + ","
											+ contractNumber;
									oldcontractNumber = contractNumber;
								}
								if(newbuilding.indexOf(building)==-1){
									newbuilding =  newbuilding+","+building;
								}
							}else{
 								if(newroom==null){
									newroom = room;
									if(start.before(beginDate)){
										newstratroom = room;
										oldarea = oldarea.add(area);
									}
									if(((!start.before(beginDate))&&(start.before(endDate)))||
											((!end.before(beginDate))&&(end.before(endDate)))){
										newendroom = room;
										newarea = newarea.add(area);
									}
								}
								if(newcontractNumber==null){
									newcontractNumber = contractNumber;
								}
								if(newbuilding==null){
									newbuilding = building;
								}
								if(flag){
									String tenancystate = "";
									if(oldarea.compareTo(new BigDecimal(0))==0){
										tenancystate = "新租";
									}else{
										if(oldarea.compareTo(newarea)==-1){
											tenancystate = "扩租";
										}else{
											if(newarea.compareTo(new BigDecimal(0))==0){
												tenancystate = "退租";
											}else{
												tenancystate = "缩租";
											}
										}
									}
									if(valueMap.get(cpstateid).equals(tenancystate)||valueMap.get(cpstateid).equals("all")){
										Map map = new HashMap();
										netarea = newarea.subtract(oldarea);
										map.put("projectName",oldprojectName);
										map.put("customer", oldcustomer);
										map.put("building", newbuilding);
										map.put("contractNumber", newcontractNumber);
										map.put("room", newroom);
										map.put("oldarea", oldarea);
										map.put("nowarea", newarea);
										map.put("netArea",netarea);
										map.put("state", tenancystate);
										listMap.put(Integer.toString(k++), map);
									}
									oldarea = new BigDecimal(0);
									newarea = new BigDecimal(0);
									newroom = room;
									newcontractNumber = contractNumber;
									newbuilding = building;
									if(start.before(beginDate)){
										newstratroom = room;
										oldarea = oldarea.add(area);
									}
									if(((!start.before(beginDate))&&(start.before(endDate)))||
											((!end.before(beginDate))&&(end.before(endDate)))){
										newendroom = room;
										newarea = newarea.add(area);
									}
								}
								oldprojectName = projectName;
								oldcustomer = customer;
								oldcontractNumber = contractNumber;
								flag = true;
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
    			}
    		}
    		//结尾处理,因为列表中存的是对比记录的前一条，固最后一条记录需要特别处理
    		if(newbuilding!=null||newcontractNumber!=null||newroom!=null){
	    		String tenancystate = "";
	    		if(oldarea.compareTo(new BigDecimal(0))==0){
					tenancystate = "新租";
				}else{
					if(oldarea.compareTo(newarea)==-1){
						tenancystate = "扩租";
					}else{
						if(newarea.compareTo(new BigDecimal(0))==0){
							tenancystate = "退租";
						}else{
							tenancystate = "缩租";
						}
					}
				}
				if(valueMap.get(cpstateid).equals(tenancystate)||valueMap.get(cpstateid).equals("all")){
					Map map = new HashMap();
		    		netarea = newarea.subtract(oldarea);
					map.put("projectName",oldprojectName);
					map.put("customer", oldcustomer);
					map.put("building", newbuilding);
					map.put("room", newroom);
					map.put("oldarea", oldarea);
					map.put("nowarea", newarea);
					map.put("netArea",netarea);
					map.put("state", tenancystate);
					listMap.put(Integer.toString(k++), map);
				}
    		}
    	}
        return listMap;
    }
}