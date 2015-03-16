package com.kingdee.eas.fdc.market.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.market.AreaSetInfo;
import com.kingdee.eas.fdc.market.MonthPlanEntryInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class MonthFactValueFacadeControllerBean extends AbstractMonthFactValueFacadeControllerBean
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.app.MonthFactValueFacadeControllerBean");
    
    private FDCSQLBuilder sqlBuild = new FDCSQLBuilder();
    
    private String sellProjectID = "";
    
    protected IRowSet _getFactValue(Context ctx, HashMap map)throws BOSException, EASBizException
    {
    	ArrayList list = new ArrayList();
		IRowSet set = null;
    	//开始查询数据
    	HashMap returnMap = new HashMap();
    	sqlBuild.appendSql("/*dialect*/"+map.get("sql").toString());
	    set = sqlBuild.executeQuery(ctx);
        return set;
    }
	
	public void getProValue(String ValueBreakId) throws BOSException{
		
	}
    
	//传入参数值为valueBreakInfoId
	protected IRowSet _getLastValue(Context ctx, String projectId) throws BOSException, EASBizException {
		String valueBreakInfoId = projectId;
		IRowSet set = null;
		FDCSQLBuilder sqlBuild = new FDCSQLBuilder();
		StringBuffer bf = new StringBuffer();
		bf.append("select sum(famount) amount,fyear year from T_MAR_valuebreakentry ");
		bf.append("where fheadid = '"+valueBreakInfoId+"' and fyear != 0 group by fyear order by fyear");
		sqlBuild.appendSql(bf.toString());
		set = sqlBuild.executeQuery(ctx);
		return set;
	}

	protected IRowSet _getYearValue(Context ctx, String ProjectId, String year) throws BOSException, EASBizException {
		String valueBreakInfoId = "";
		IRowSet set_1 = null;
		FDCSQLBuilder sqlBuild_1 = new FDCSQLBuilder();
		StringBuffer bf_1 = new StringBuffer();
		bf_1.append("select fid from t_mar_valueBreak where fsellprojectid = '"+ProjectId+"' ");
		bf_1.append("and fedityear = '"+year+"' and fversiontype = '20' order by fcreatetime desc");
		sqlBuild_1.appendSql(bf_1.toString());
		set_1 = sqlBuild_1.executeQuery(ctx);
		
		try {
			while(set_1.next()){
				if(valueBreakInfoId.equals("")){
					valueBreakInfoId = set_1.getString("fid");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(valueBreakInfoId.equals("")){
			return null;
		}
		else{
			IRowSet set = null;
			FDCSQLBuilder sqlBuild = new FDCSQLBuilder();
			StringBuffer bf = new StringBuffer();
			bf.append("select sum(famount) amount,fyear year from T_MAR_valuebreakentry ");
			bf.append("where fheadid = '"+valueBreakInfoId+"' and fyear != 0 group by fyear order by fyear");
			sqlBuild.appendSql(bf.toString());
			set = sqlBuild.executeQuery(ctx);
			return set;
		}
	}
}