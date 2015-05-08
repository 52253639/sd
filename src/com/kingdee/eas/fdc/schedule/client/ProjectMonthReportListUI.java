package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.ITextIconDisplayStyle;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportFactory;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * 项目月度报告 列表界面
 */
public class ProjectMonthReportListUI extends AbstractProjectMonthReportListUI {
	private static final Logger logger = CoreUIObject.getLogger(ProjectMonthReportListUI.class);

	/**
	 * output class constructor
	 */
	public ProjectMonthReportListUI() throws Exception {
		super();
	}


	protected ICoreBase getBizInterface() throws Exception {
		return ProjectMonthReportFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return ProjectMonthReportEditUI.class.getName();
	}

	/*
	 * 初始化表格格式
	 */
	protected void initTable() {
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		SorterItemCollection sic = new SorterItemCollection();
		SorterItemInfo sf = new SorterItemInfo("year");
		sf.setSortType(SortType.DESCEND);
		sic.add(sf);
		sf = new SorterItemInfo("month");
		sf.setSortType(SortType.DESCEND);
		sic.add(sf);

		viewInfo.setSorter(sic);
		FilterInfo filter = new FilterInfo();
		if (getUIContext().get("curprojectInfo") != null) {
			CurProjectInfo prj = (CurProjectInfo) getUIContext().get("curprojectInfo");
			filter.getFilterItems().add(new FilterItemInfo("project.id", prj.getId().toString()));
		}

		if (getUIContext().get("projectSpecial") != null) {
			ProjectSpecialInfo projectSpecial = (ProjectSpecialInfo) getUIContext().get("projectSpecial");
			filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", projectSpecial.getId().toString()));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null));
		}
		viewInfo.setFilter(filter);
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	/*
	 * 初始化界面的button
	 */
	protected void initWorkButton() {
		super.initWorkButton();
		btnCancel.setTextIconDisStyle(ITextIconDisplayStyle.BOTH_TEXT_ICON);
		btnCancelCancel.setTextIconDisStyle(ITextIconDisplayStyle.BOTH_TEXT_ICON);
		this.btnRemove.setEnabled(true);
		this.btnAttachment.setVisible(false);
	}

	public String reportId;
	
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int selectRows[] = KDTableUtil.getSelectedRows(tblMain);
		if (selectRows.length > 0) {
			IRow row = tblMain.getRow(selectRows[0]);
			String reportId = row.getCell("id").getValue().toString().trim();
			this.reportId = reportId;
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, reportId);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = uiFactory.create(ProjectMonthReportEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}

	/**
	 * 描述：判断单据是否在工作流中 client 创建时间：2009-1-8
	 * <p>
	 */
	public static boolean isRunningWorkflow(String objId) throws BOSException {
		boolean hasWorkflow = false;
		IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
		ProcessInstInfo[] procInsts = service2.getProcessInstanceByHoldedObjectId(objId);
		for (int i = 0, n = procInsts.length; i < n; i++) {
			if ("open.running".equals(procInsts[i].getState())) {
				hasWorkflow = true;
				break;
			}
		}
		return hasWorkflow;
	}

	/**
	 * 判断单据是否进入工作流
	 * 
	 * @param
	 * @author 李建波
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws UuidException
	 */
	public boolean isEnterWorkFlow() throws EASBizException, BOSException, UuidException {

		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {

			return false;
		}
		IRow row = tblMain.getRow(rowIndex);
		String id = (String) row.getCell(getKeyFieldName()).getValue();
		if (isRunningWorkflow(id)) {

			return true;
		}
		return false;
	}

	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-9-28
	 * @version EAS7.0
	 * @see
	 */

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		tblMain.checkParsed();
		int rows[] = KDTableUtil.getSelectedRows(tblMain);
		if (rows.length <= 0) {
			FDCMsgBox.showWarning("请选择删除行");
			SysUtil.abort();
		}

		IObjectPK[] pk = new IObjectPK[rows.length];
		for (int i = rows.length; i > 0; i--) {
			IRow row = tblMain.getRow(rows[i - 1]);
			if (null != row.getCell("state").getValue()) {
				if (row.getCell("state").getValue().toString().trim().equals(FDCBillStateEnum.AUDITTED.toString())
						|| row.getCell("state").getValue().toString().trim().equals(FDCBillStateEnum.AUDITTING.toString())) {
					FDCMsgBox.showWarning("已审批或审批中的单据不能被删除！");
					SysUtil.abort();
				}
			}
			if (isEnterWorkFlow()) {
				MsgBox.showWarning(this, "单据已经进入工作流，不能进行删除操作！");
				SysUtil.abort();
			}
			String reportId = row.getCell("id").getValue().toString();
			pk[i - 1] = new ObjectUuidPK(reportId);
			tblMain.removeRow(rows[i - 1]);
		}

		if (pk.length > 0) {
			ProjectMonthReportFactory.getRemoteInstance().delete(pk);
			FDCMsgBox.showWarning("删除成功");
		}
	}

	public void onShow() throws Exception {
		this.btnAuditResult.setVisible(false);
		super.onShow();
	}

	protected boolean isNeedfetchInitData() throws Exception {
		return false;
	}
}