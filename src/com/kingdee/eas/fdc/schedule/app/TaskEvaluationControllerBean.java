package com.kingdee.eas.fdc.schedule.app;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

public class TaskEvaluationControllerBean extends AbstractTaskEvaluationControllerBean
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5492234191326746306L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.schedule.app.TaskEvaluationControllerBean");

	/**
    * 
    * 
    * 描述：				任务评价设置查询
    *		
    * @author			周航建
    * @createDate		2010-11-14
    * @param			type
    * @return			Set
    *
    * @version		    EAS7.0	
    * (non-Javadoc)
    * @see com.kingdee.eas.fdc.schedule.app.AbstractTaskEvaluationControllerBean#_getDataBase(com.kingdee.bos.Context, java.lang.String)
    */
	protected Set _getDataBase(Context ctx, String type) throws BOSException,EASBizException {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.FEvaluationResult_l2,t.FevaluationPass,t.FNumber,t.FDescription_l2 ,t.FID, t.FControlUnitID ");
		sql.append("FROM T_SCH_TaskEvaluation AS t where FEvaluationType=? ");
		
		FDCSQLBuilder build=new FDCSQLBuilder(ctx);
		build.appendSql(sql.toString());
		if(null != type && !"".equals(type)){
			build.appendParam(type.toString().trim());
		}
		
		Set set=new HashSet();
		
		
		try {
			String testSql = build.getTestSql();
			testSql = testSql.substring(0,testSql.indexOf("?"));
			build.setPrepareStatementSql(testSql);
			IRowSet rowSet=build.executeQuery();
			while(rowSet.next())
			{
				Map map=new HashMap();
				map.put("Eresult", rowSet.getString("FEvaluationResult_l2"));
				map.put("Enumber", rowSet.getString("Fnumber"));
				map.put("Epass", rowSet.getString("FevaluationPass"));
				map.put("Edescription", rowSet.getString("Fdescription_l2"));
				map.put("Eid", rowSet.getString("fid"));
				map.put("ECUID", rowSet.getString("fcontrolunitid"));
				set.add(map);
				  
			}
		} catch (Exception e) {
			logger.getName();
			throw new BOSException(e);
		}
		return set;
	}

	
	 protected void _checkNumberDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
     
	 }

	 
	 /**
	  * 
	  * 
	  * 描述：			任务评价设置引用删除查询
	  *		
	  * @author			周航建
	  * @createDate		2010-11-14
	  * @param			引用评价设置id  <type> 
	  * @return			Set
	  *
	  * @version		    EAS7.0	
	  * (non-Javadoc)
	  * @see com.kingdee.eas.fdc.schedule.app.AbstractTaskEvaluationControllerBean#_quoteDelete(com.kingdee.bos.Context, java.lang.String)
	  */
	protected boolean _quoteDelete(Context ctx, String taskId)
			throws BOSException, EASBizException {
		boolean flag = false;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select FEvaluationResult from T_SCH_TaskEvaluationBill where FEvaluationResult in(?) ");
		builder.addParam(taskId);
		
		IRowSet set = builder.executeQuery(ctx);
		if(set != null && set.size() > 0)
		{
			flag = true;
		}
		
		return flag;
	}


	/**
	 * 
	 * 
	 * 描述：			对任务评价时：类型为进度评价同步修改任务和进度汇报数据,任务进度为100%,状态为已完成 或 进度为99%,未完成
	 *		
	 * @author			周航建
	 * @createDate		2011-9-5
	 * @param			任务srcId
	 * @return			
	 *
	 * @version		    EAS7.0	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.fdc.schedule.app.AbstractTaskEvaluationControllerBean#_updateSchedule(com.kingdee.bos.Context, java.lang.String)
	 */
	protected void _updateSchedule(Context ctx, String srcId,
			BigDecimal complete, boolean isComplete) throws BOSException,
			EASBizException {
		
		String t_task_sql = " update T_SCH_FDCScheduleTask set FComplete = ? where FSrcId = ? ";
		String t_prog_sql = " update t_sch_SchTaskProgressReport set FIsComplete = ? , FCompletePrecent=? where FrelateTaskId= ? ";
		
		FDCSQLBuilder taskBuild = new FDCSQLBuilder(ctx);
		taskBuild.appendSql(t_task_sql);
		taskBuild.addParam(complete);
		taskBuild.addParam(srcId);
		taskBuild.getTestSql();
		FDCSQLBuilder progBuild = new FDCSQLBuilder(ctx);
		progBuild.appendSql(t_prog_sql);
		if(isComplete){
			progBuild.addParam(Boolean.TRUE);
		}else{
			progBuild.addParam(Boolean.FALSE);
		}
		progBuild.addParam(complete);
		progBuild.addParam(srcId);
		
		progBuild.getTestSql();
		try {
			//Do BuildSQL
			taskBuild.executeUpdate(ctx);
			progBuild.executeUpdate(ctx);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		
	}

	 
	


}