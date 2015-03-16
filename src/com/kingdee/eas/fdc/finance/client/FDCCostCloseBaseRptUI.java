/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.assistant.Period;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FDCCostCloseBaseRptUI extends AbstractFDCCostCloseBaseRptUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FDCCostCloseBaseRptUI.class);
	
	/**
	 * 当前期间
	 */
	protected String periodNumber = null;
	/**
	 * 工程项目当前期间
	 */
	protected Period period = null;
	/**
	 * 成本月结：true,财务成本月结false;
	 */
	protected boolean isCost = true;
	/**
	 * 是否成本中心
	 */
	protected boolean isCostCenter = false;
	/**
	 * 初始化数据
	 */
	protected Map initData = null;
	protected Map dataMap = new HashMap();
	/**
	 * 月结项目
	 */
	protected Object[][] item = null;
	/**
	 * 
	 */
	protected Map companyOrgUnitMap = null;
	/**
	 * 财务组织
	 */
	protected CompanyOrgUnitCollection companyOrgUnits = new CompanyOrgUnitCollection();
	/**
	 * 当前财务组织
	 */
	protected CompanyOrgUnitInfo companyOrgUnitInfo = null;
	/**
	 * 当前组织：财务或成本中心
	 */
	protected OrgUnitInfo orgUnitInfo = null;
	/**
	 * 财务组织ID集合
	 */
	protected Set companyOrgUnitIdSet = null;
	/**
	 * 工程项目
	 */
	protected CurProjectCollection projects = null;
	/**
	 * 工程项目ID
	 */
	protected Set prjIdSet = null;
	
	/**
	 * output class constructor
	 */
	public FDCCostCloseBaseRptUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if(e.getClickCount()!=2){
			return;
		}
		if(isClose()){
			return;
		}
		IRow row = getDetailTable().getRow(e.getRowIndex());
		String projectId = getProjectId(row);
		String item = (String)getItem(row);
		if(projectId==null || item==null){
			return;
		}
		
		Map uiContxt = new HashMap();
		uiContxt.put("isCost", Boolean.valueOf(isCost));
		uiContxt.put("projectId", projectId);
		uiContxt.put("item", item);
		uiContxt.put("periodNumber", periodNumber);
		uiContxt.put(UIContext.OWNER,this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(CostCloseDetailUI.class.getName(), uiContxt, dataObjects,
				OprtState.VIEW);

		uiWindow.show();

//		super.tblMain_tableClicked(e);
	}
	
	/**
	 * output comboCostCloseSort_itemStateChanged method
	 */
	protected void comboCostCloseSort_itemStateChanged(
			java.awt.event.ItemEvent e) throws Exception {
		super.comboCostCloseSort_itemStateChanged(e);
		if(comboCostCloseSort.getSelectedIndex()==1){
			Calendar curCal = Calendar.getInstance();
			int year = curCal.get(Calendar.YEAR);
			int month = curCal.get(Calendar.MONTH);
			spiYear.setValue(new Integer(year));
			spiMonth.setValue(new Integer(month+1));
			this.spiMonth.setEnabled(false);
			this.spiYear.setEnabled(false);
		}else{
			this.spiMonth.setEnabled(true);
			this.spiYear.setEnabled(true);
		}
		getDetailTable().removeRows();
//		fillTable();
	}

	protected void spiMonth_stateChanged(ChangeEvent e) throws Exception {
		super.spiMonth_stateChanged(e);
		setPeriodNumber();
		getDetailTable().removeRows();
//		fetchData();
//		fillTable();
	}

	protected void spiYear_stateChanged(ChangeEvent e) throws Exception {
		super.spiYear_stateChanged(e);
		setPeriodNumber();
		getDetailTable().removeRows();
//		fetchData();
//		fillTable();
	}

	/**
	 * output prmtCompany_dataChanged method
	 */
	protected void prmtCompany_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		if(e.getOldValue()==e.getNewValue()){
			return;
		}
		if(e.getNewValue()!=null){
			prmtProject.setEnabled(true);
			CompanyOrgUnitInfo selectedCompany = (CompanyOrgUnitInfo)prmtCompany.getValue();
			EntityViewInfo view = prmtProject.getEntityViewInfo();
			if(view==null){
				view = new EntityViewInfo();
			}
			FilterInfo filter = view.getFilter();
			if(filter==null){
				filter = new FilterInfo();
			}
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",selectedCompany.getId().toString()));
			view.setFilter(filter);
			prmtProject.setEntityViewInfo(view);
			orgUnitInfo = (OrgUnitInfo) e.getNewValue();
			initOrgUnit(orgUnitInfo);
			initProjects(false, null);
//			fetchData();
//			fillTable();
			
		}else{
			prmtProject.setEnabled(false);
			prmtProject.setValue(null);
		}
		getDetailTable().removeRows();
	}

	/**
	 * output prmtProject_dataChanged method
	 */
	protected void prmtProject_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		
		if(e.getNewValue()==null){
			return;
		}
		CurProjectInfo info = (CurProjectInfo)e.getNewValue();
		if(!info.isIsLeaf()){
			//后续可能需要支持非明细
			MsgBox.showWarning(this, "请选择明细工程项目!");
			prmtProject.setValue(null);
			SysUtil.abort();
		}
		initProjects(false,info);
		getDetailTable().removeRows();
//		fetchData();
//		fillTable();
		
	}
	protected void prmtProject_willShow(SelectorEvent e) throws Exception {
	}
	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}
	
	public void onLoad() throws Exception {
//		if(!FDCUtils.IsFinacial(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString())){
//			MsgBox.showWarning(this, "此财务组织未启用财务成本一体化！");
//			SysUtil.abort();
//		}
		super.onLoad();
		initControl();
		initTable(); 
		if (isCost) {
			item = new Object[][] { { "合同", "合同全部完全拆分", "con" },
					{ "合同变更", "变更全部完全拆分", "change" },
					{ "结算", "结算全部完全拆分", "settle" },
					{ "工程实际投入", "工程实际投入全部完全拆分", "workload" },
					{ "无文本合同", "无文本合同全部完全审批", "conWithout" } };
		} else {
			item = new Object[][] { { "付款单", "付款全部完全拆分", "paySplit" },
					{ "无文本合同付款拆分", "无文本合同全部完全拆分", "payNoTextSplit" },
					{ "待处理合同", "事项处理完毕", "invalidCon" },
					{ "待处理无文本合同", "事项处理完毕", "invalidConWithout" } };
		}
		initOrgUnit(null);
		initPrmtCompany();
		initPrmtProject();

		initProjects(isCostCenter,null);
//		period = FDCUtils.getCurrentPeriod(ctx, projectId, isCost)
		setPeriodNumber();
//		fetchData();
		getDetailTable().checkParsed();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
//				initTable();
			}
		});
//		fillTable();
		
		actionAddNew.setEnabled(false);
		actionEdit.setEnabled(false);
		actionView.setEnabled(false);
		actionRemove.setEnabled(false);
		actionAttachment.setEnabled(false);
		actionLocate.setEnabled(false);
		actionQuery.setEnabled(false);
		
		actionAddNew.setVisible(false);
		actionEdit.setVisible(false);
		actionView.setVisible(false);
		actionRemove.setVisible(false);
		menuEdit.setVisible(false);
		menuBiz.setVisible(false);
		actionAttachment.setVisible(false);
		actionLocate.setVisible(false);
		actionQuery.setVisible(false);
	}

	private void initPrmtProject() throws EASBizException, BOSException{
		// TODO 明细工程项目，
		EntityViewInfo view =prmtProject.getEntityViewInfo();
		if(view==null){
			view = new EntityViewInfo();
		}
		FilterInfo filter = view.getFilter();
		if(filter==null){
			filter=new FilterInfo();
		}
		Set authorizedOrgs = FDCUtils.getAuthorizedOrgs(null);
		//权限组织
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",authorizedOrgs,CompareType.INCLUDE));
		//TODO 一体化组织过滤?
		prmtProject.setEntityViewInfo(view);
		if(isCostCenter){
			prmtProject.setEnabled(true);
		}else{
			prmtProject.setEnabled(false);
		}
	}

	private void initPrmtCompany() throws EASBizException, BOSException {
		EntityViewInfo view =prmtCompany.getEntityViewInfo();
		if(view==null){
			view = new EntityViewInfo();
		}
		FilterInfo filter = view.getFilter();
		if(filter==null){
			filter=new FilterInfo();
		}
		filter.getFilterItems().add(new FilterItemInfo("longNumber",orgUnitInfo.getLongNumber()+"%",CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("isBizUnit",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isSealUp",Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("isFreeze",Boolean.FALSE));
		Set authorizedOrgs = FDCUtils.getAuthorizedOrgs(null);
		//权限组织
		filter.getFilterItems().add(new FilterItemInfo("id",authorizedOrgs,CompareType.INCLUDE));
		//TODO 一体化组织过滤?
		view.setFilter(filter);
		prmtCompany.setEntityViewInfo(view);
		if(isCostCenter){
			prmtCompany.setValue(companyOrgUnitInfo);
			prmtCompany.setEnabled(false);
		}
		
	}

	private void initTable() {
		getDetailTable().getHeadMergeManager().setMergeMode(
				KDTMergeManager.FREE_MERGE);
		getDetailTable().getMergeManager().mergeBlock(0, 0, getDetailTable().getRowCount()-1, 2,
				KDTMergeManager.FREE_MERGE);
		getDetailTable().getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		getDetailTable().getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		getDetailTable().getColumn("project").setWidth(200);
		getDetailTable().getColumn("standard").setWidth(160);
		getDetailTable().getColumn("itemCount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		getDetailTable().getColumn("thisCount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		getDetailTable().getColumn("save").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		getDetailTable().getColumn("submit").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		getDetailTable().getColumn("audit").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		getDetailTable().getColumn("noSplit").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		getDetailTable().getColumn("partSplit").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		getDetailTable().getColumn("allSplit").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
	}

	private void initControl() {
		Calendar curCal = Calendar.getInstance();
		int year = curCal.get(Calendar.YEAR);
		int month = curCal.get(Calendar.MONTH);

		SpinnerNumberModel yearMo = new SpinnerNumberModel(year, 1990, 2099, 1);
		this.spiYear.setModel(yearMo);
		SpinnerNumberModel monthMo = new SpinnerNumberModel(month + 1, 1, 12, 1);
		spiMonth.setModel(monthMo);

	}

	private void fillTable() throws Exception {
		setToPeriodData();
		IRow row = null;
		getDetailTable().removeRows();
		getDetailTable().checkParsed();
		for(int i=0;i<projects.size();i++){
			CurProjectInfo info = projects.get(i);
			String orgUnitId = info.getFullOrgUnit().getId().toString();
			CompanyOrgUnitInfo company = (CompanyOrgUnitInfo)companyOrgUnitMap.get(orgUnitId);
			for(int j=0;j<item.length;j++){
				row = getDetailTable().addRow();
				row.getCell("company").setValue(company.getName());
				row.getCell("project").setValue(info.getDisplayName());
				row.getCell("item").setValue(item[j][0]);
				row.getCell("standard").setValue(item[j][1]);
				row.getCell("project.id").setValue(info.getId().toString());
				loadRow(row);
			}
		}
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				initTable();
//			}
//		});
		initTable(); 
	}

	protected void loadRow(IRow row) {
		//TODO
	}

	private void fetchData() throws Exception {
		Map param = new HashMap();
		param.put("periodNumber", periodNumber);
		param.put("isCostCenter", Boolean.valueOf(isCostCenter));
		param.put("prjIdSet", prjIdSet);
		initData = FDCCostRptFacadeFactory.getRemoteInstance()
				.getCostCloseData(isCost, param);
	}

	private void initOrgUnit(OrgUnitInfo selectedOrg) throws Exception {
		if (selectedOrg == null) {
			orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit();
		}
		if(orgUnitInfo.isIsCompanyOrgUnit()){
			companyOrgUnitMap = FDCClientUtils.getChildrenCompanyOrgUnitInfos(orgUnitInfo.getId());
		}else if(orgUnitInfo.isIsCostOrgUnit()){
			isCostCenter = true;
			companyOrgUnitInfo = SysContext
			.getSysContext().getCurrentFIUnit();
			companyOrgUnitMap = new HashMap();
			companyOrgUnitMap.put(companyOrgUnitInfo.getId().toString(), companyOrgUnitInfo);
		}
	}
	private void initProjects(boolean isCostCenter, CurProjectInfo curProjectInfo) throws Exception{
		Map retMap = null;
		if(isCostCenter){
			retMap = FDCHelper.getPrjInfosByCostCenter(null, orgUnitInfo.getId().toString());
		}else{
			retMap = FDCClientUtils.getPrjInfosByCompany(new HashSet(companyOrgUnitMap.keySet()));
		}
		projects = (CurProjectCollection)retMap.get("projects");
		prjIdSet=(Set)retMap.get("prjIdSet");
		if(curProjectInfo!=null){
			projects = new CurProjectCollection();
			prjIdSet = new HashSet();
			projects.add(curProjectInfo);
			prjIdSet.add(curProjectInfo.getId().toString());
		}
	}

	protected KDTable getDetailTable() {
		return tblMain;
	}
	
	public boolean destroyWindow() {
		boolean value = super.destroyWindow();
		clear();
		return value;
	}

	private void clear() {
		if(initData!=null){
			initData.clear();
			initData = null;
		}
		if(companyOrgUnitMap!=null){
			companyOrgUnitMap.clear();
			companyOrgUnitMap = null;
		}
		if(companyOrgUnitIdSet!=null){
			companyOrgUnitIdSet.clear();
			companyOrgUnitIdSet = null;
		}
		if(prjIdSet!=null){
			prjIdSet.clear();
			prjIdSet = null;
		}
		if(companyOrgUnits!=null){
			companyOrgUnits.clear();
			companyOrgUnits = null;
		}
		if(projects!=null){
			projects.clear();
			projects = null;
		}
		companyOrgUnitInfo = null;
		orgUnitInfo = null;
		periodNumber = null;
	}
	
	protected String getProjectId(IRow row){
		Object object = row.getCell("project.id").getValue();
		return object.toString();
	}
	
	protected Object getItem(IRow row){
		//元数据加个列就没这个必要了
		Object object = row.getCell("item").getValue();
		for(int i=0;i<item.length;i++){
			for(int j=0;j<3;j++){
				Object temp = item[i][0];
				if(temp.equals(object)){
					return item[i][2];
				}
			}
		}
		return null;
	}
	
	protected Map getData() {
		if(isClose()){
			return (Map)initData.get("costClose");
		}else{
			return (Map)initData.get("costUnClose");
		}
	}
	private void setPeriodNumber(){
		String year = spiYear.getValue().toString();
		String month= spiMonth.getValue().toString();
		if(Integer.parseInt(month)<10){
			month="0"+month;
		}
		periodNumber = year+month;
		String title = spiMonth.getValue().toString();
		if(isCost){
			title+=getRes("costCloseRpt");
		}else{
			title+=getRes("financeCostCloseRpt");
		}
		ctnCostClose.setTitle(title);
	}
	
	protected String getKey(IRow row){
		return periodNumber+getProjectId(row)+getItem(row);
	}
	
	//变量与方法哪个性能高?
	protected boolean isClose(){
		return comboCostCloseSort.getSelectedIndex()==0;
	}
	
	protected Object[] getStatisticsData(IRow row){
		return (Object[])dataMap.get(getKey(row));
	}
	
	protected int setIndex(String key){
		return 0;
	}
	
	protected void setToPeriodData(){
		dataMap.clear();
		for(Iterator iter = getData().keySet().iterator();iter.hasNext();){
			String  key = (String)iter.next();
			String thisKey = periodNumber+key.substring(6,key.length());
			Object[] statistics = (Object[])getData().get(key);
			Object[] addStatistics = (Object[])dataMap.get(thisKey);
			String number = key.substring(0, 6);
			int period = Integer.parseInt(number);
			int thisPeriod = Integer.parseInt(periodNumber);
			if(period-thisPeriod>0){
				continue;
			}
			if(isClose()){
				if(addStatistics==null){
					addStatistics=new Object[3];
				}
				addStatistics[0] = FDCHelper.add(addStatistics[0], statistics[0]);
				if(periodNumber.equals(number)){
					addStatistics[1] = FDCHelper.add(addStatistics[1], statistics[1]);
				}
				addStatistics[2] = FDCHelper.add(addStatistics[2], statistics[0]);
			}else{
				if(addStatistics==null){
					addStatistics=new Object[6];
				}
				addStatistics[0] = FDCHelper.add(addStatistics[0], statistics[0]);
				addStatistics[1] = FDCHelper.add(addStatistics[1], statistics[1]);
				addStatistics[2] = FDCHelper.add(addStatistics[2], statistics[2]);
				addStatistics[3] = FDCHelper.add(addStatistics[3], statistics[3]);
				addStatistics[4] = FDCHelper.add(addStatistics[4], statistics[4]);
				addStatistics[5] = FDCHelper.add(addStatistics[5], statistics[5]);
				
			}
			dataMap.put(thisKey, addStatistics);
		}
		dataMap.size();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return CostCloseDetailUI.class.getName();
	}
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
	
	protected String getKeyFieldName() {
		return "project.id";
	}
	protected void refresh(ActionEvent e) throws Exception {
		super.refresh(e);
		fetchData();
		getDetailTable().checkParsed();
		fillTable();
	}
	public void actionSearch_actionPerformed(ActionEvent e) throws Exception {
		fetchData();
		getDetailTable().checkParsed();
		fillTable();
	}
	public static String getRes(String resName) {
		return EASResource
				.getString(
						"com.kingdee.eas.fdc.finance.client.FinanceResource",
						resName);
	} 
}