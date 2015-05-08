/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
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
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportCollection;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportFactory;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class ProjectWeekReportListUI extends AbstractProjectWeekReportListUI {
	private static final Logger logger = CoreUIObject.getLogger(ProjectWeekReportListUI.class);

	/**
	 * output class constructor
	 */
	public ProjectWeekReportListUI() throws Exception {
		super();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	public SelectorItemCollection getSelectors() {
		return super.getSelectors();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProjectWeekReportFactory.getRemoteInstance();
	}

	// protected String getEditUIName() {
	// /* TODO 自动生成方法存根 */
	// return ViewProjectWeekReportUI.class.getName();
	// }
	/*
	 * 初始化表格格式 尽量使用元数据设置，这样就可以很容易的支持DEP二次开发 除非不能使用元数据设置的，才需要在这里实现
	 */
	protected void initTable() {
		super.initTable();
		this.tblMain.removeRows(false);

		if (getCurProject() != null) {
			CurProjectInfo info = getCurProject();
			String infoId = info.getId().toString().trim();
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add(new SelectorItemInfo("*"));
			view.getSelector().add(new SelectorItemInfo("creator.*"));
			
			SorterItemCollection sic = new SorterItemCollection();
			SorterItemInfo sf = new SorterItemInfo("year");
			sf.setSortType(SortType.DESCEND);
			sic.add(sf);
			sf = new SorterItemInfo("week");
			sf.setSortType(SortType.DESCEND);
			sic.add(sf);

			view.setSorter(sic);
			
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("project.id", infoId));
			if (getProjectSpecialInfo() != null) {
				ProjectSpecialInfo projectSpecialInfo = getProjectSpecialInfo();
				filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", projectSpecialInfo.getId()));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null, CompareType.EQUALS));
			}
			view.setFilter(filter);
			try {
				ProjectWeekReportCollection collection = ProjectWeekReportFactory.getRemoteInstance().getProjectWeekReportCollection(view);
				if (collection.size() > 0) {
					for (int i = 0; i < collection.size(); i++) {
						ProjectWeekReportInfo projectWeekReportInfo = collection.get(i);
						IRow row = tblMain.addRow();
						row.getCell("state").setValue(projectWeekReportInfo.getState());
						row.getCell("year").setValue(new Integer(projectWeekReportInfo.getYear()));
						row.getCell("week").setValue(new Integer(projectWeekReportInfo.getWeek()));
						row.getCell("startDate").setValue(projectWeekReportInfo.getStartDate());
						row.getCell("endDate").setValue(projectWeekReportInfo.getEndDate());
						row.getCell("creator.name").setValue(projectWeekReportInfo.getCreator().getName());
						row.getCell("createTime").setValue(projectWeekReportInfo.getCreateTime());
						row.getCell("project.id").setValue(projectWeekReportInfo.getProject().getId());
						row.getCell("id").setValue(projectWeekReportInfo.getId());
					}
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * @description
	 * @author 杜红明
	 * @createDate 2011-10-18
	 * @version EAS7.0
	 * @see
	 */

	protected void execQuery() {
		// TODO Auto-generated method stub
		super.execQuery();
	}

	/**
	 * @description
	 * @author 杜红明
	 * @createDate 2011-10-18
	 * @version EAS7.0
	 * @see
	 */

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		// TODO Auto-generated method stub
		SorterItemCollection sic = new SorterItemCollection();
		SorterItemInfo sf = new SorterItemInfo("year");
		sf.setSortType(SortType.DESCEND);
		sic.add(sf);
		sf = new SorterItemInfo("week");
		sf.setSortType(SortType.DESCEND);
		sic.add(sf);
		FilterInfo filter = new FilterInfo();
		CurProjectInfo project = getCurProject();
		if(project != null){
			filter.getFilterItems().add(new FilterItemInfo("project.id",project.getId().toString()));
		}
		ProjectSpecialInfo projectSpecial = getProjectSpecialInfo();
		if(projectSpecial != null){
			filter.getFilterItems().add(new FilterItemInfo("projectSpecial",projectSpecial.getId().toString()));
		}
		viewInfo.setFilter(filter);
		viewInfo.setSorter(sic);
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	public ProjectSpecialInfo getProjectSpecialInfo() {
		ProjectSpecialInfo projectSpecialInfo = new ProjectSpecialInfo();
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof ProjectWeekReportEditUI) {
			ProjectWeekReportEditUI editUI = (ProjectWeekReportEditUI) getUIContext().get("Owner");
			projectSpecialInfo = editUI.projectSpecialInfo;
		}
		return projectSpecialInfo;
	}

	/**
	 * @description 得到工程项目
	 * @author 车忠伟
	 * @createDate 2011-8-31
	 * @version EAS7.0
	 * @see
	 */
	public CurProjectInfo getCurProject() {
		CurProjectInfo curProjectInfo = new CurProjectInfo();
		if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof ProjectWeekReportEditUI) {
			ProjectWeekReportEditUI editUI = (ProjectWeekReportEditUI) getUIContext().get("Owner");
			curProjectInfo = editUI.curProjectInfo;
		}

		return curProjectInfo;
	}

	/*
	 * 初始化界面的button
	 * 
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillListUI#initWorkButton()
	 */
	protected void initWorkButton() {
		super.initWorkButton();
		btnCancel.setTextIconDisStyle(ITextIconDisplayStyle.BOTH_TEXT_ICON);
		btnCancelCancel.setTextIconDisStyle(ITextIconDisplayStyle.BOTH_TEXT_ICON);
		this.btnAddNew.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		this.btnCopyTo.setVisible(false);
		this.btnDelVoucher.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnLocate.setVisible(false);
		this.btnMultiapprove.setVisible(false);
		this.btnNextPerson.setVisible(false);
		this.btnPageSetup.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.btnQuery.setVisible(false);
		this.btnQueryScheme.setVisible(false);
		this.btnRefresh.setVisible(false);
		this.separatorFW3.setVisible(false);
		this.btnTraceDown.setVisible(false);
		this.btnTraceUp.setVisible(false);
		this.btnWorkFlowG.setVisible(false);
		this.btnWorkFlowList.setVisible(false);
		this.btnView.setVisible(false);
		this.btnCreateTo.setVisible(false);
		btnAuditResult.setVisible(false);
	}

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
		tblMain.checkParsed();
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {

			return false;
		}
		IRow row = tblMain.getRow(rowIndex);
		String id = row.getCell(getKeyFieldName()).getValue().toString();
		if (isRunningWorkflow(id)) {

			return true;
		}
		return false;
	}

	/**
	 * @description 删除
	 * @author 车忠伟
	 * @createDate 2011-8-31
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
			ProjectWeekReportFactory.getRemoteInstance().delete(pk);
			FDCMsgBox.showWarning("删除成功");
		}
	}

	public String reportId;

	/**
	 * 查看
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {

		checkSelected();
		int selectRows[] = KDTableUtil.getSelectedRows(tblMain);
		if (selectRows.length > 0) {
			IRow row = tblMain.getRow(selectRows[0]);
			String reportId = row.getCell("id").getValue().toString().trim();
			this.reportId = reportId;
			ProjectWeekReportListUI weekReportListUI = this;
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, reportId);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = uiFactory.create(ProjectWeekReportEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();

		}

		// super.actionView_actionPerformed(e);
	}

	/*						
		  */
	// protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
	// if (e.getClickCount() == 2) {
	// int i = this.tblMain.getSelectManager().getActiveRowIndex();
	// if (i >=0) {
	//				
	// }
	// }
	// }
	public void storeFields() {
		super.storeFields();
	}

	protected boolean isNeedfetchInitData() throws Exception {
		return false;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		btnAuditResult.setVisible(false);
		this.btnRemove.setEnabled(true);
		this.setUITitle("查看历史周报告列表");
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);// 默认选中第一行

	}

	protected String getEditUIName() {
		/* TODO 自动生成方法存根 */
		return ProjectWeekReportEditUI.class.getName();
	}
}