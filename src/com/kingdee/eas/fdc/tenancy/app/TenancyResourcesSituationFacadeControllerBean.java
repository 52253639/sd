package com.kingdee.eas.fdc.tenancy.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class TenancyResourcesSituationFacadeControllerBean extends AbstractTenancyResourcesSituationFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.TenancyResourcesSituationFacadeControllerBean");
    protected Map _getResult(Context ctx, Map param)throws BOSException    {
    	List list = new ArrayList();
    	Map listMap = new HashMap();
    	if(param.get("sellProject") instanceof List ){
    		list  = (List)param.get("sellProject");
    	}
    	if(list!=null){
    		for(int i=0,m=0;i<list.size();i++){
    			if(list.get(i) instanceof SellProjectInfo){
    				SellProjectInfo sellProjectInfo = (SellProjectInfo)list.get(i);
	    			String sellProjectID = sellProjectInfo.getId().toString();
	    			StringBuffer sql = new StringBuffer();
	    			sql.append("select BUILDING.fid,BUILDING.fname_l2 ");
	    			sql.append("FROM T_SHE_Building AS BUILDING ");
	    			sql.append("LEFT OUTER JOIN T_SHE_SellProject AS SELLPROJECT ");
	    			sql.append("ON BUILDING.FSellProjectID = SELLPROJECT.FID ");
	    			sql.append("where SELLPROJECT.FID = ? ");
	    			
//下面这个sql语句，where前后数据类型不一样，将字符串改为整形
//	    			String sql_type = "select fid,fname_l2 from T_FDC_ProductType where fisenabled = '1'";
	    			String sql_type = "select fid,fname_l2 from T_FDC_ProductType where fisenabled = 1";
	    			IRowSet rs_type  = DbUtil.executeQuery(ctx, sql_type);
	    			int rs_typecount = rs_type.size();
	    			String id[] = new String[rs_typecount];
	    			String name[] = new String[rs_typecount];
	    			try {
						for(int n = 0;rs_type.next();n++){
							id[n] = rs_type.getString(1);
							name[n] = rs_type.getString(2);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
	    			
	    			IRowSet rs  = DbUtil.executeQuery(ctx, sql.toString(), new String[]{sellProjectID});
	    			try {
						for (int j = 0; rs.next(); j++) {
							Map map = new HashMap();
							map.put("sellProjectName", sellProjectInfo.getName());
							map.put("buildingName", rs.getString(2));
							String sql1 = "select count(*),sum(ftenancyArea) from T_SHE_Room where FBuildingID = ?";
							String sql2 = "select sum(ftenancyArea) from T_SHE_Room " +
								"where (ftenancyState = 'NewTenancy' " +
								"or ftenancyState = 'ContinueTenancy' or ftenancyState = 'EnlargeTenancy') and FBuildingID = ?";
							IRowSet rs1  = DbUtil.executeQuery(ctx, sql1.toString(), new String[]{rs.getString(1)});
							IRowSet rs2  = DbUtil.executeQuery(ctx, sql2.toString(), new String[]{rs.getString(1)});
							BigDecimal allarea = new BigDecimal(0); 
							BigDecimal nousearea = new BigDecimal(0);
							BigDecimal proportionarea = new BigDecimal(0);
							if(rs1.next()){
								map.put("roomcount", rs1.getString(1));
								if(rs1.getBigDecimal(2)!=null){
									allarea = rs1.getBigDecimal(2);
								}
							}
							map.put("allarea", allarea.toString());
							if(rs2.next()){
								if(rs2.getBigDecimal(1)!=null){
									
									nousearea = rs2.getBigDecimal(1);
								}
							}
							map.put("nousearea", nousearea.toString());
							if((allarea.toString().equals("0"))||(nousearea.toString().equals("0"))){
							}else{
								proportionarea = nousearea.divide(allarea, 4,BigDecimal.ROUND_HALF_UP);
							}
							map.put("proportionarea", (proportionarea.divide(new BigDecimal(0.01),2,BigDecimal.ROUND_HALF_UP)).toString());
							map.put("type_count",Integer.toString(rs_typecount));
							//为了取名方便：参数规则如下：
							//"a1" == 计租面积总和
							//"a2" == 可租面积总和
							//"a3" == 保留面积总和
							//"a4" == 已租面积总和
							//"a5" == 空置面积总和
							//"a6" == 可租比例
							//"a7" == 保留比例
							//"a8" == 出租率
							//"a9" == 空置率
							//"a10" == 本类出租率
							for(int k = 0;k<rs_typecount;k++){
								Map map_type =  new HashMap();
								String productType  = id[k];
								String productTypeName = name[k];
								//本产品类计租面积总和
								String sql1_type = "select sum(ftenancyArea) " +
									"from T_SHE_Room where FBuildingID = ? and FProductTypeID = ?";
								//本产品类可租面积总和
								String sql2_type = "select sum(ftenancyArea)from T_SHE_Room where FBuildingID = ? " +
									"and FProductTypeID = ? and (ftenancyState = 'WaitTenancy' or ftenancyState = 'SincerObligate')";
								//本产品类保留面积总和
								String sql3_type = "select sum(ftenancyArea)from T_SHE_Room where FBuildingID = ? " +
								"and FProductTypeID = ? and ftenancyState = 'KeepTenancy'";
								//本产品类已租面积总和
								String sql4_type = "select sum(ftenancyArea)from T_SHE_Room where FBuildingID = ? " +
								"and FProductTypeID = ? and (ftenancyState = 'NewTenancy' " +
								"or ftenancyState = 'ContinueTenancy' or ftenancyState = 'EnlargeTenancy')";
								//本产品类空置面积总和
								String sql5_type = "select sum(ftenancyArea)from T_SHE_Room where FBuildingID = ? " +
								"and FProductTypeID = ? and (ftenancyState = 'UNTenancy' or ftenancyState = 'WaitTenancy'" +
								" or ftenancyState = 'KeepTenancy' or ftenancyState = 'SincerObligate' or ftenancyState is null )";
								
								IRowSet rs1_type  = DbUtil.executeQuery(ctx, sql1_type, new String[]{rs.getString(1),productType});
								IRowSet rs2_type  = DbUtil.executeQuery(ctx, sql2_type, new String[]{rs.getString(1),productType});
								IRowSet rs3_type  = DbUtil.executeQuery(ctx, sql3_type, new String[]{rs.getString(1),productType});
								IRowSet rs4_type  = DbUtil.executeQuery(ctx, sql4_type, new String[]{rs.getString(1),productType});
								IRowSet rs5_type  = DbUtil.executeQuery(ctx, sql5_type, new String[]{rs.getString(1),productType});
								BigDecimal a1 = new BigDecimal(0);
								BigDecimal a2 = new BigDecimal(0);
								BigDecimal a3 = new BigDecimal(0);
								BigDecimal a4 = new BigDecimal(0);
								BigDecimal a5 = new BigDecimal(0);
								BigDecimal a6 = new BigDecimal(0);
								BigDecimal a7 = new BigDecimal(0);
								BigDecimal a8 = new BigDecimal(0);
								BigDecimal a9 = new BigDecimal(0);
								BigDecimal a10 = new BigDecimal(0);
								if(rs1_type.next()){
									if(rs1_type.getBigDecimal(1)!=null){
										a1 = rs1_type.getBigDecimal(1);
									}
								}
								if(rs2_type.next()){
									if(rs2_type.getBigDecimal(1)!=null){
										a2 = rs2_type.getBigDecimal(1);
									}
								}
								if(rs3_type.next()){
									if(rs3_type.getBigDecimal(1)!=null){
										a3 = rs3_type.getBigDecimal(1);
									}
								}
								if(rs4_type.next()){
									if(rs4_type.getBigDecimal(1)!=null){
										a4 = rs4_type.getBigDecimal(1);
									}
								}
								if(rs5_type.next()){
									if(rs5_type.getBigDecimal(1)!=null){
										a5 = rs5_type.getBigDecimal(1);
									}
								}
								if((allarea.toString().equals("0"))||(a2.toString().equals("0"))){
								}else{
									a6 = a2.divide(allarea, 4,BigDecimal.ROUND_HALF_UP);
								}
								if((allarea.toString().equals("0"))||(a3.toString().equals("0"))){
								}else{
									a7 = a3.divide(allarea, 4,BigDecimal.ROUND_HALF_UP);
								}
								if((allarea.toString().equals("0"))||(a4.toString().equals("0"))){
								}else{
									a8 = a4.divide(allarea, 4,BigDecimal.ROUND_HALF_UP);
								}
								if((allarea.toString().equals("0"))||(a5.toString().equals("0"))){
								}else{
									a9 = a5.divide(allarea, 4,BigDecimal.ROUND_HALF_UP);
								}
								if((a1.toString().equals("0"))||(a4.toString().equals("0"))){
								}else{
									a10 = a4.divide(a1, 4,BigDecimal.ROUND_HALF_UP);
								}
								map_type.put("a1", a1.toString());
								map_type.put("a2", a2.toString());
								map_type.put("a3", a3.toString());
								map_type.put("a4", a4.toString());
								map_type.put("a5", a5.toString());
								map_type.put("a6", (a6.divide(new BigDecimal(0.01),2,BigDecimal.ROUND_HALF_UP)).toString());
								map_type.put("a7", (a7.divide(new BigDecimal(0.01),2,BigDecimal.ROUND_HALF_UP)).toString());
								map_type.put("a8", (a8.divide(new BigDecimal(0.01),2,BigDecimal.ROUND_HALF_UP)).toString());
								map_type.put("a9", (a9.divide(new BigDecimal(0.01),2,BigDecimal.ROUND_HALF_UP)).toString());
								map_type.put("a10",(a10.divide(new BigDecimal(0.01),2,BigDecimal.ROUND_HALF_UP)).toString());
								map_type.put("productTypeName", productTypeName);
								map.put("productType"+k, map_type);
							}
						listMap.put(String.valueOf(m), map);
						m = m+1;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
    		}
    		}
    	}
        return listMap;
    }
}