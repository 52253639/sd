/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.CostSplitAcctUI;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * cassiel 2010-07-09
 */
public class PrjActualInputDetailFilterUI extends AbstractPrjActualInputDetailFilterUI
{
	CostAccountCollection tempAccts = null;
	String [] costAccountIds = null;
	String [] costAccountLgNums = null;
	
	private static boolean filterByPeriod = true;
	
	private static boolean filterByPrj = true;
	
	private static final Logger logger = CoreUIObject.getLogger(PrjActualInputDetailFilterUI.class);
	private IUIWindow acctUI = null;
	/**
	 * output class constructor
	 */
	public PrjActualInputDetailFilterUI() throws Exception {
		super();
		if(true){
			//取当前日期而不是公司的期间日期
			pkdate=new Date[1];
			pkdate[0]=new Date();
			return;
		}
		pkdate = FDCClientHelper.getCompanyCurrentDate();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	private static final String MONTH_TO = "monthTo";

	private static final String MONTH_FROM = "monthFrom";

	private static final String YEAR_TO = "yearTo";

	private static final String YEAR_FROM = "yearFrom";

	private static final String PROJECT_IDS = "projectIds";
	
	private static final String COSTACCOUNT_MAPS="costAccountMaps";

	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	protected ItemAction actionListOnLoad;

	private boolean isLoaded;

	protected ListUI listUI;
	
	private Date[] pkdate ;

	/**
	 * output class constructor
	 */
	public void clear() {
		this.bizProject.setValue(null);
		initDataStatus();
	}
	protected void btnCostAccount_actionPerformed(ActionEvent e) throws Exception {
		//必须先选择工程项目 
		if(this.bizProject.getValue()==null){
			FDCMsgBox.showInfo("请先选择工程项目");
			SysUtil.abort();
		}
		UIContext uiContext = new UIContext(this);
		Object o = bizProject.getValue();
		
		if (o != null && o instanceof CurProjectInfo) {
			uiContext.put("curProject", (CurProjectInfo) o);
		}
		
		// 选择科目
		acctUI = UIFactory.createUIFactory(UIFactoryName.MODEL).create(com.kingdee.eas.fdc.basedata.client.CostSplitAcctUI.class.getName(), uiContext, null, null);
		acctUI.show();
		
		IUIWindow uiWin = acctUI;
		CostAccountCollection accts = null;
		if (((CostSplitAcctUI) uiWin.getUIObject()).isOk()) {
			accts = ((CostSplitAcctUI) uiWin.getUIObject()).getData();
			tempAccts = accts;
			StringBuffer sb = new StringBuffer("");
			if(tempAccts!=null&&tempAccts.size()>0){
				for (int i = 0; i < accts.size(); i++) {
					CostAccountInfo costAccount = (CostAccountInfo) accts.get(i);
//				if (!costAccount.getCurProject().isIsLeaf()) {
//					MsgBox.showWarning("必须选择明细工程项目下的成本科目！");
//					SysUtil.abort();
//				}
					sb.append(costAccount.getLongNumber());
					sb.append(";");
					this.txtCostAccount.setText(sb.toString().replace('!', '.'));
				}
				filterByPrj = false;
			}else{
				this.txtCostAccount.setText("");
				filterByPrj = true;
			}
		} else {
			return;
		}
	}
	protected void txtCostAccount_focusLost(FocusEvent e) throws Exception {
		
	}
	protected void kdCheNotFilterByDate_stateChanged(ChangeEvent e) throws Exception {
		if(this.kdCheNotFilterByDate.getSelected()==16){
			filterByPeriod = true;
			this.spiYearFrom.setEnabled(true);
			this.spiYearTo.setEnabled(true);
			this.spiMonthFrom.setEnabled(true);
			this.spiMonthTo.setEnabled(true);
		}else{
			filterByPeriod = false;
			this.spiYearFrom.setEnabled(false);
			this.spiYearTo.setEnabled(false);
			this.spiMonthFrom.setEnabled(false);
			this.spiMonthTo.setEnabled(false);
		}
	}
	protected void btnProjectSelect_actionPerformed(ActionEvent e) throws Exception {
		this.bizProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
		if (!OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext()
				.getCurrentOrgUnit().getId().toString())) {
			EntityViewInfo evi = bizProject.getEntityViewInfo();
			if (evi == null) {
				evi = new EntityViewInfo();
				bizProject.setEntityViewInfo(evi);
			}
			
			FilterInfo filter = evi.getFilter();
			if (filter == null) {
				filter = new FilterInfo();
				evi.setFilter(filter);
			}
			String curOrgId = SysContext.getSysContext().getCurrentCtrlUnit()
					.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("CU.id", curOrgId));
			Set authorizedOrgs = FDCUtils.getAuthorizedOrgs(null);
			//权限组织
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",authorizedOrgs,CompareType.INCLUDE));
			//过滤财务组织的工程项目，CU下有多个财务组织数据太多
			//实体财务取其工程项目，虚体则取其下属财务组织工程项目
			BOSUuid companyId = SysContext.getSysContext().getCurrentFIUnit().getId();
			Map companyOrgUnitMap = FDCClientUtils.getChildrenCompanyOrgUnitInfos(companyId);
			if(companyOrgUnitMap.size()>0){
				filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", new HashSet(companyOrgUnitMap.keySet()),CompareType.INCLUDE));
			}
		}
		this.bizProject.setEditable(false);
		this.bizProject.setRequired(true);
	}
	private void initDataStatus(){
		
		Date curDate = pkdate[0];
		int year = curDate.getYear()+1900;
		int month = curDate.getMonth()+1;
		Date preDate=FDCDateHelper.getPreMonth(curDate);
		int startYear=preDate.getYear()+1900;
		int startMonth=preDate.getMonth()+1;
//		初始化时间控件
		SpinnerNumberModel yearMo1 = new SpinnerNumberModel(startYear, 1900, 2100, 1);
		spiYearFrom.setModel(yearMo1);
		SpinnerNumberModel yearMo2 = new SpinnerNumberModel(year, 1900, 2100, 1);
		spiYearTo.setModel(yearMo2);
		this.spiMonthFrom.setVisible(true);
		this.spiMonthTo.setVisible(true);
		SpinnerNumberModel monthMo1 = new SpinnerNumberModel(startMonth, 1, 12, 1);
		spiMonthFrom.setModel(monthMo1);
		SpinnerNumberModel monthMo2 = new SpinnerNumberModel(month, 1, 12, 1);
		spiMonthTo.setModel(monthMo2);
	}
	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());

		FilterInfo filter = new FilterInfo();

		if (para.isNotNull(PROJECT_IDS)) {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", FDCHelper.getSetByArray(para.getStringArray(PROJECT_IDS)), CompareType.INCLUDE));
		} else {//如果没有选择工程项目，给出提示
			MsgBox.showError(this, "请选择工程项目");
		}
		//科目ID
		if (para.isNotNull("costAccounts")) {
			filter.getFilterItems().add(new FilterItemInfo("costAccount.id", FDCHelper.getSetByArray(para.getStringArray("costAccounts")), CompareType.INCLUDE));
		}
		//科目Number
		if (para.isNotNull("costAccountLgNums")) {
			filter.getFilterItems().add(new FilterItemInfo("costAccount.number", FDCHelper.getSetByArray(para.getStringArray("costAccountLgNums")), CompareType.INCLUDE));
		}
		//开始会计期间
		if (para.isNotNull("beginQueryDate")) {
			filter.getFilterItems().add(new FilterItemInfo("beginQueryDate", para.getDate("beginQueryDate"), CompareType.GREATER_EQUALS));
		}
		//结束会计期间
		if (para.isNotNull("endQueryDate")) {
			filter.getFilterItems().add(new FilterItemInfo("endQueryDate", para.getDate("endQueryDate"), CompareType.LESS));
		}
		//不按会计期间查询
		if (para.isNotNull("filterByPeriod")) {
			String filterPeriod = para.getBoolean("filterByPeriod")==true?"1":"0";
			filter.getFilterItems().add(new FilterItemInfo("filterByPeriod", filterPeriod, CompareType.EQUALS));
		}
		//按工程查询
		if (para.isNotNull("filterByPrj")) {
			String filterPeriod = para.getBoolean("filterByPrj")==true?"1":"0";
			filter.getFilterItems().add(new FilterItemInfo("filterByPrj", filterPeriod, CompareType.EQUALS));
		}
		
		return filter;
	}

	private Date getBeginQueryDate(FDCCustomerParams para) {
		Date date = null;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
		cal.set(Calendar.MONTH, para.getInt(MONTH_FROM));
		cal.set(Calendar.DATE, 0);
		date = cal.getTime();
		return DateTimeUtils.truncateDate(date);
	}

	private Date getEndQueryDate(FDCCustomerParams para) {
		Date date = null;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, para.getInt(YEAR_TO));
		cal.set(Calendar.MONTH, para.getInt(MONTH_TO));
		cal.set(Calendar.DATE, 0);
		date = cal.getTime();
		return DateTimeUtils.truncateDate(date);
	}
	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();

		if(this.bizProject.getValue()!=null){
			param.add(PROJECT_IDS,((CurProjectInfo)this.bizProject.getValue()).getId().toString());
		}else{
			//给出提示
		}
		param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue()).intValue());
		param.add(YEAR_TO, ((Integer) this.spiYearTo.getValue()).intValue());
		param.add(MONTH_FROM, ((Integer) this.spiMonthFrom.getValue()).intValue());
		param.add(MONTH_TO, ((Integer) this.spiMonthTo.getValue()).intValue());
		param.add("beginQueryDate", this.getBeginQueryDate(param));
		param.add("endQueryDate", this.getEndQueryDate(param));
		if(this.kdCheNotFilterByDate.getSelected()==32){
			filterByPeriod = false;
		}else{
			filterByPeriod = true;
		}
		param.add("filterByPeriod", filterByPeriod);
		
		if(tempAccts!=null && tempAccts.size()>0){
//			filterByPrj = false;
			costAccountIds = new String [tempAccts.size()];
			costAccountLgNums = new String [tempAccts.size()];
			for(int i = 0;i<tempAccts.size();i++){
				CostAccountInfo costAccount = tempAccts.get(i);
				costAccountIds[i] = costAccount.getId().toString(); 
				costAccountLgNums[i] = costAccount.getLongNumber();
			}
			param.add("costAccounts",costAccountIds);
			param.add("costAccountLgNums", costAccountLgNums);
		}else{
//			filterByPrj = true;
		}

		param.add("filterByPrj", filterByPrj);
		return param.getCustomerParams();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		if (!isLoaded) {
			this.clear();
		}
		isLoaded = true;
		initDataStatus();
		//工程项目UI
		setProjectSelectedUI();
	}
	private void setProjectSelectedUI() throws Exception {
		this.btnProjectSelect.setVisible(false);
		this.bizProject.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
		EntityViewInfo evi = bizProject.getEntityViewInfo();
		FilterInfo filter = null;
		if (evi == null) {
			evi = new EntityViewInfo();
			bizProject.setEntityViewInfo(evi);
			filter = evi.getFilter();
			if (filter == null) {
				filter = new FilterInfo();
				evi.setFilter(filter);
			}
		}
		if (!OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext()
				.getCurrentOrgUnit().getId().toString())) {
			String curOrgId = SysContext.getSysContext().getCurrentCtrlUnit()
					.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("CU.id", curOrgId));
			filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		}
		Set authorizedOrgs = FDCUtils.getAuthorizedOrgs(null);
		//权限组织
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",authorizedOrgs,CompareType.INCLUDE));
		//过滤财务组织的工程项目，CU下有多个财务组织数据太多
		//实体财务取其工程项目，虚体则取其下属财务组织工程项目
		BOSUuid companyId = SysContext.getSysContext().getCurrentFIUnit().getId();
		Map companyOrgUnitMap = FDCClientUtils.getChildrenCompanyOrgUnitInfos(companyId);
		if(companyOrgUnitMap.size()>0){
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", new HashSet(companyOrgUnitMap.keySet()),CompareType.INCLUDE));
		}
		this.bizProject.setEditable(false);
		this.bizProject.setRequired(true);
	}

	public void setCustomerParams(CustomerParams cp) {

		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);
		if(para.getString(PROJECT_IDS)!=null){
			try {
				this.bizProject.setValue(CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(para.getString(PROJECT_IDS))));
			} catch (EASBizException e) {
				logger.error(e.getMessage(), e);
			} catch (BOSException e) {		
				logger.error(e.getMessage(), e);
			}
		}
		
		//成本科目ID
		if (para.isNotNull("costAccounts")) {
			costAccountIds = para.getStringArray("costAccounts");
		}
		
		//成本科目
		if (para.isNotNull("costAccountLgNums")) {
			StringBuffer sbuffer = new StringBuffer();
			String[] costArray = para.getStringArray("costAccountLgNums");
			costAccountLgNums = costArray;
			for (int i = 0; i < costArray.length; i++) {
				if (i > 0) {
					sbuffer.append(";");
				}
				sbuffer.append(costArray[i]);
			}
			this.txtCostAccount.setText(sbuffer.toString().replace('!', '.'));
		}
		
		//不按期间查询
		if (para.isNotNull("filterByPeriod")) {
			if (para.getBoolean("filterByPeriod")) {
				this.kdCheNotFilterByDate.setSelected(false);
			} else {
				this.kdCheNotFilterByDate.setSelected(true);
			}
			
		}
		
		//按工程查询
		if (para.isNotNull("filterByPrj")) {
			filterByPrj = para.getBoolean("filterByPrj");
		}
		this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
		this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
		this.spiMonthFrom.setValue(new Integer(para.getInt(MONTH_FROM)));
		this.spiMonthTo.setValue(new Integer(para.getInt(MONTH_TO)));

		super.setCustomerParams(para.getCustomerParams());
	}

	public boolean verify() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		if (para.isNotNull("endQueryDate") && para.isNotNull("beginQueryDate")) {
			if (this.kdCheNotFilterByDate.getSelected()==16&&para.getDate("endQueryDate").before(para.getDate("beginQueryDate"))) {
				MsgBox.showWarning(this, EASResource.getString(resourcePath, "DateBoundErrer"));
				return false;
			}
		}
		if(this.bizProject.getValue()==null){
			MsgBox.showWarning(this, EASResource.getString(resourcePath, "SelectCurProj"));
			return false;
		}
		return true;
	}
    public HashMap getResult() throws Exception {
        HashMap result = new HashMap();
        if(this.bizProject.getValue()!=null){
        	result.put(PROJECT_IDS,((CurProjectInfo)this.bizProject.getValue()).getId().toString());
		}else{
			//给出提示
		}
        //如果选择了特定的成本科目， 查询数据便不按工程项目来过滤成本科目进而查出数据了，直接使用特定的成本科目
        result.put("filterByPrj", new Boolean(filterByPrj));
        result.put("costAccounts", costAccountIds);
        result.put("costAccountLgNums", costAccountLgNums);
    	result.put(YEAR_FROM, (this.spiYearFrom.getValue()).toString());
    	result.put(YEAR_TO, (this.spiYearTo.getValue()).toString());
    	result.put(MONTH_FROM, (this.spiMonthFrom.getValue()).toString());
    	result.put(MONTH_TO, (this.spiMonthTo.getValue()).toString());
    	if(this.kdCheNotFilterByDate.getSelected()==32){
    		filterByPeriod = false;
    	}else{
    		filterByPeriod = true;
    	}
    	result.put("filterByPeriod",new Boolean(filterByPeriod) );
        return result;
    }
}