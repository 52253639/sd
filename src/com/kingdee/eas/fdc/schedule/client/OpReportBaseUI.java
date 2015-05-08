/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.ProjectReportFacadeFactory;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 进度报告基类界面
 */
public abstract class OpReportBaseUI extends AbstractOpReportBaseUI {
	private static final Logger logger = CoreUIObject.getLogger(OpReportBaseUI.class);
	
	private boolean isNeedRefresh = false;
	
	public boolean isNeedRefresh() {
		return isNeedRefresh;
	}

	public void setNeedRefresh(boolean isNeedRefresh) {
		this.isNeedRefresh = isNeedRefresh;
	}

	public OpReportBaseUI() throws Exception {
		super();
	}

	/*
	 * 获取相关值 子类重载
	 */
	protected void fetchInitData() throws Exception {

	}

	/*
	 * 获取相关值 调用服务器端方法 1、获取本周的任务，及任务的完成情况 2、获取下周的任务 无论是月度报告，还是周报告，都可调用此方法
	 * 请将获取的值，生成ProjectWeekReportEntryInfo，放入到editData中
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#fetchInitData()
	 */
	protected Map fetchData() throws Exception {
		return ProjectReportFacadeFactory.getRemoteInstance().getPeriodTask(getMainFilterParam());
	}

	/*
	 * 设置相应的过滤条件
	 */
	protected abstract Map getMainFilterParam() throws Exception;

	public void storeFields() {
		super.storeFields();
		storeLineFields();
	}

	public void loadFields() {
		super.loadFields();
		loadLineFields();
	}

	public void loadLineFields() {
		loadThisTableFields();
		loadNextTableFields();
	}

	/***
	 * 将对象中的数据，显示到本期表格中
	 */
	public abstract void loadThisTableFields();

	/***
	 * 将对象中的数据，显示到下期表格中
	 */
	public abstract void loadNextTableFields();

	public void storeLineFields() {
		storeThisTableFields();
		storeNextTableFields();
	}

	/***
	 * 将本期表格中的数据，同步存储到对象中
	 */
	public abstract void storeThisTableFields();

	/***
	 * 将下期表格中的数据，同步存储到对象中
	 */
	public abstract void storeNextTableFields();

	public boolean destroyWindow() {
		 try {
			if ((getUIContext().get("Owner")) instanceof FDCScheduleBaseEditUI && isNeedRefresh) {
				FDCScheduleBaseEditUI ui = (FDCScheduleBaseEditUI) getUIContext().get("Owner");
				ui.refresh();

			}
				
		} catch (Exception e) {
			// 此处异常必须吃掉，可能诱发异常的一种情况，就是前面界面已经关闭，而此处去做刷新，会报异常。
			// 解决方法：把项目周月报改模式对话框
			logger.error("主专项计划界面已关闭。无法刷新界面");
			// handUIException(e);
		}
		return super.destroyWindow();
	}

	/**
	 * 得到项目工程
	 * 
	 * @return
	 */
	protected CurProjectInfo getCurProject() {
		CurProjectInfo curProjectInfo = null;
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof MainScheduleExecuteUI) {
			MainScheduleExecuteUI scheduleExecuteUI = (MainScheduleExecuteUI) getUIContext().get("Owner");
			curProjectInfo = scheduleExecuteUI.curprojectInfo;
			return curProjectInfo;
		} else if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof SpecialScheduleExecuteUI) {
			SpecialScheduleExecuteUI specialScheduleExecuteUI = (SpecialScheduleExecuteUI) getUIContext().get("Owner");
			curProjectInfo = specialScheduleExecuteUI.curprojectInfo;
		}
		
		if (curProjectInfo == null) {
			curProjectInfo = editData.getProject();
		}
	
		return curProjectInfo;
	}
	
	/**
	 * 根据周报的ID查询工程项目的ID，适用于界面未传入工程项目ID的情况
	 */
	public String getCurProjectIDByEditDataID() throws BOSException, EASBizException, SQLException {
		String editDataId = getUIContext().get("ID").toString();
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select fprojectid from " + getReportTableName() + " where fid=?  ");
		if (editDataId != null) {
			sql.addParam(editDataId);
		} else {
			sql.addParam(" ");
		}

		IRowSet rs = sql.executeQuery();
		String curProjectID = "";
		while (rs.next()) {
			curProjectID = rs.getString(1);
		}
		return curProjectID;
	}
	
	protected abstract String getReportTableName();

	/**
	 * 尝试用界面传参、或editDataID的方式来取得工程项目的ID
	 * 以适用各种特殊情况
	 * @Author：owen_wen
	 * @CreateTime：2013-6-27
	 */
	protected String getCurProjectIDForAll() throws EASBizException, BOSException, SQLException {
		if (getCurProject() != null) // 界面传参有值
			return getCurProject().getId().toString();
		else
			return getCurProjectIDByEditDataID();
	}
}