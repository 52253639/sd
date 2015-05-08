package com.kingdee.eas.fdc.schedule.app;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.SCHHelper;
import com.kingdee.eas.fdc.schedule.ScheduleException;
import com.kingdee.jdbc.rowset.IRowSet;

public class WBSCodeRuleControllerBean extends AbstractWBSCodeRuleControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.WBSCodeRuleControllerBean");

	protected String _getNewNumber(Context ctx, int level, String parentID, String curProjectID)
			throws BOSException, ScheduleException {
		if(level == 0){
			throw new ScheduleException(ScheduleException.NUMLEVELISZERO);
		}
		String newNum = new String();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select ent.* from t_sch_wbscoderule head " +
				"	inner join t_sch_wbscoderuleentry ent on ent.fparentid=head.fid" +
				"	where (head.fisenabled=? and ent.flevel=?) or ent.flevel=0 order by ent.flevel");
		builder.addParam(Boolean.TRUE);
		builder.addParam(new Integer(level));
		IRowSet rowSet = builder.executeQuery();
		if(rowSet.size()<1){
			throw new ScheduleException(ScheduleException.NONUMLEVEL);
		}
		int length = 0;
		try {
			while(rowSet.next()){
				length = rowSet.getInt("flength");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ScheduleException(ScheduleException.NUMLEVELISZERO);
		}

		builder.clear();
		
		String sql = null;
		
		if(parentID != null){
			/**
			 * 取编码规则，不需要用flevel，不同的level间parent肯定不同
			 * and flevel=?
			 */
			
			sql = "select max(flongnumber) longnum from t_sch_fdcwbs where fcurprojectid=? and fparentid=? ";
		}else{
			sql = "select max(flongnumber) longnum from t_sch_fdcwbs where fcurprojectid=? and flevel=?";
		}
		builder.appendSql(sql);
//		builder.addParam(new Integer(length));
		builder.addParam(curProjectID);
		if(parentID != null){
			builder.addParam(parentID);
		}else{
			builder.addParam(new Integer(level));
		}
		rowSet = null;
		rowSet = builder.executeQuery();
		int maxNum = 1;
		try{
			while(rowSet.next()){
				newNum = rowSet.getString("longnum");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(newNum != null ){
			if(newNum.lastIndexOf("!")>0){
				maxNum = Integer.parseInt(newNum.substring(newNum.lastIndexOf("!")+1)) +1;
			}else{
				maxNum = Integer.parseInt(newNum)+1;
			}
		}
		if(Integer.toString(maxNum).length() > length){
			throw new ScheduleException(ScheduleException.NUMLEVELTOBIG);
		}
		newNum = SCHHelper.getFixLenNum(length, maxNum);
		return newNum;
	}

	protected String _createNewNumber(Context ctx, int level, int maxNum)
			throws BOSException, ScheduleException {
		String newNUmber = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select ent.* from t_sch_wbscoderule head " +
				"	inner join t_sch_wbscoderuleentry ent on ent.fparentid=head.fid" +
				"	where (head.fisenabled=? and ent.flevel=?) or ent.flevel=0 order by ent.flevel desc");
		builder.addParam(Boolean.TRUE);
		builder.addParam(new Integer(level));
		IRowSet rowSet = builder.executeQuery();
		if(rowSet.size()<1){
			throw new ScheduleException(ScheduleException.NONUMLEVEL);
		}
		int length = 0;
		try {
			while(rowSet.next()){
				length = rowSet.getInt("flength");
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ScheduleException(ScheduleException.NUMLEVELISZERO);
		}
		newNUmber = SCHHelper.getFixLenNum(length, maxNum);
		return newNUmber;
	}
}