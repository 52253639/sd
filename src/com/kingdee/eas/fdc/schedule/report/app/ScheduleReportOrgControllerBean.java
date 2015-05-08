package com.kingdee.eas.fdc.schedule.report.app;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.DataAccessException;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ScheduleReportOrgControllerBean extends AbstractScheduleReportOrgControllerBean {

	private static final long serialVersionUID = -3529373685586717754L;
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.schedule.app.report.ScheduleReportOrgControllerBean");

	/**
	 * @see com.kingdee.eas.fdc.schedule.app.report.AbstractScheduleReportOrgControllerBean#_updateScheduleReportData(com.kingdee.bos.Context, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void _updateScheduleReportData(Context ctx, List scheduleReportCollection, List deleteScheduleReportDataList) throws BOSException, EASBizException {
		
		if (deleteScheduleReportDataList != null && deleteScheduleReportDataList.size() > 0) {
			ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) deleteScheduleReportDataList.get(0);
			IORMappingDAO scheduleDao = ORMappingDAO.getInstance(org.getBOSType(), ctx, getConnection(ctx));
			batchDelete(deleteScheduleReportDataList, scheduleDao);
		}
		
		if (scheduleReportCollection != null && scheduleReportCollection.size() > 0) {
			ScheduleReportOrgInfo org = (ScheduleReportOrgInfo) scheduleReportCollection.get(0);
			IORMappingDAO scheduleDao = ORMappingDAO.getInstance(org.getBOSType(), ctx, getConnection(ctx));
			batchUpdate(scheduleReportCollection, scheduleDao);
		}
		
	}

	/**
	 * 描述：执行批处理请求，先删除，后添加
	 * @param updateDataList					修改后的数据集
	 * @param deleteScheduleReportDataList		已经在界面上删除的数据，但之前是在数据库中存在的
	 * @param dao
	 * @throws DataAccessException
	 * @Author：adward_huang
	 * @CreateTime：2012-10-16
	 */
	@SuppressWarnings("unchecked")
	private void batchUpdate(List updateDataList, IORMappingDAO dao) throws DataAccessException {
		int size = updateDataList.size();
		if (size > 0) {
			ScheduleReportOrgInfo org = null;
			// 1.将修改数据删除
			for (int i = 0; i < size; i++) {
				org = (ScheduleReportOrgInfo) updateDataList.get(i);
				dao.deleteBatch(new ObjectUuidPK(org.getId()));
			}
			// 2.只添加最后修改的数据
			dao.executeBatch();
			for (int i = 0; i < size; i++) {
				org = (ScheduleReportOrgInfo) updateDataList.get(i);
				dao.addNewBatch(org);
			}
			dao.executeBatch();
		}
	}
	
	/**
	 * 描述：执行批处理请求，先删除，后添加
	 * @param updateDataList					修改后的数据集
	 * @param deleteScheduleReportDataList		已经在界面上删除的数据，但之前是在数据库中存在的
	 * @param dao
	 * @throws DataAccessException
	 * @Author：adward_huang
	 * @CreateTime：2012-10-16
	 */
	@SuppressWarnings("unchecked")
	private void batchDelete(List deleteScheduleReportDataList,IORMappingDAO dao) throws DataAccessException {
		int size = deleteScheduleReportDataList.size();
		if (size > 0) {
			ScheduleReportOrgInfo org = null;
			// 2.将在界面上删除的信息在数据库中同步删除
			for (int i = 0; i < deleteScheduleReportDataList.size(); i++) {
				org = (ScheduleReportOrgInfo) deleteScheduleReportDataList.get(i);
				dao.deleteBatch(new ObjectUuidPK(org.getId()));
			}
			dao.executeBatch();
		}
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.app.report.AbstractScheduleReportOrgControllerBean#_checkDefaultGroupOrg(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectValue)
	 */
	@Override
	protected boolean _checkDefaultGroupOrg(Context ctx) throws BOSException, EASBizException {

		// 判断集团节点是否存在
		String sql = "select fid from T_SCH_ScheduleReportOrg ts where ts.frelateorgid = ?";
		IRowSet rowSet = DbUtil.executeQuery(ctx, sql.toString(), new String[] { FDCScheduleConstant.PROJECT_TREE_NODE_GROUP_CURRENT_ORG_ID });
		boolean exist = false;
		try {
			if (rowSet.next()) {
				exist = true;
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new BOSException("Check Schedule Report Data -Group Node SQLException", e);
		}
		return exist;
	}


}