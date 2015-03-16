/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.SpinnerNumberModel;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.client.TreeSelectDialog;
import com.kingdee.eas.fi.gl.OtherContextConstant;
import com.kingdee.eas.fi.gl.client.InitClientHelp;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * 描述:项目动态成本分析表过滤页签
 * @author jackwang  date:2007-4-29 <p>
 * @version EAS5.3
 */
public class DynCostSnapShotFilterUI extends AbstractDynCostSnapShotFilterUI {
	private static final Logger logger = CoreUIObject.getLogger(DynCostSnapShotFilterUI.class);

	/**
	 * output class constructor
	 */
	public DynCostSnapShotFilterUI() throws Exception {
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

	/**
	 * output btnProjectSelect_actionPerformed method
	 */
	protected void btnProjectSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
//		if (this.projectSelectDlg == null) {
//			this.initProjectDlg(null);
//		}
//		Object object = projectSelectDlg.showDialog();
//		setProjectByTree(object);
//		super.btnProjectSelect_actionPerformed(e);
	}
//	private void initProjectDlg(String[] projectIds) throws Exception {
//		ProjectTreeBuilder builder = new ProjectTreeBuilder(true);
//		KDTree tree = new KDTree();
//		if (this.companySelectDlg != null
//				&& this.companySelectDlg.getSelectTree() != null) {
//			builder.buildByCostOrgTree(tree, this.companySelectDlg.getSelectTree());
//		} else {
//			builder.build(this.listUI, tree, this.actionListOnLoad);
//		}
//		TreeModel model = tree.getModel();
//		FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) model.getRoot(),
//				"isIsEnabled", Boolean.FALSE);
//		projectSelectDlg = new TreeSelectDialog(this, model, "getId,toString",
//				projectIds);
//	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private static final String MONTH_TO = "monthTo";

	private static final String MONTH_FROM = "monthFrom";

	private static final String YEAR_TO = "yearTo";

	private static final String YEAR_FROM = "yearFrom";

	private static final String PROJECT_IDS = "projectIds";

	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	protected ItemAction actionListOnLoad;

	private boolean isLoaded;

	protected ListUI listUI;

	private TreeSelectDialog projectSelectDlg;
	
	//是否使用成本月结,当前财务组织的期间
	private Date[] pkdate ;

	/**
	 * output class constructor
	 */
	public void clear() {
		this.bizProject.setValue(null);
		initDataStatus();
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
		//
		spiYearTo.setEnabled(false);//.setEditable(false);
		spiMonthTo.setEnabled(false);//.setEditable(false);
	}
	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());

		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("id", null, CompareType.NOTEQUALS));

		if (para.isNotNull(PROJECT_IDS)) {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", FDCHelper.getSetByArray(para.getStringArray(PROJECT_IDS)), CompareType.INCLUDE));
		} else {//如果没有选择工程项目，给出提示
			MsgBox.showError(this, "请选择工程项目");
//			filter.getFilterItems().add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		}
//
		if (getBeginQueryDate(para) != null) {
			filter.getFilterItems().add(new FilterItemInfo("snapShotDate", getBeginQueryDate(para), CompareType.GREATER_EQUALS));
		}
		if (getEndQueryDate(para) != null) {
			filter.getFilterItems().add(new FilterItemInfo("snapShotDate", FDCHelper.getNextDay(getEndQueryDate(para)), CompareType.LESS));
		}

		return filter;
	}

	private Date getBeginQueryDate(FDCCustomerParams para) {
		Date date = null;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
//		cal.set(Calendar.MONTH, para.getInt(MONTH_FROM) - 1);
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

//		String[] projIds = (String[]) this.txtProject.getUserObject();
//		if (!FDCHelper.isEmpty(projIds)) {
//			param.add(PROJECT_IDS, projIds);
//		}
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
		return param.getCustomerParams();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (!isLoaded) {
			this.clear();
		}
		isLoaded = true;
		initDataStatus();
		
		this.btnProjectSelect.setVisible(false);
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
		this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
		this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
		this.spiMonthFrom.setValue(new Integer(para.getInt(MONTH_FROM)));
		this.spiMonthTo.setValue(new Integer(para.getInt(MONTH_TO)));

		super.setCustomerParams(para.getCustomerParams());
	}

	public boolean verify() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		if (para.isNotNull("endQueryDate") && para.isNotNull("beginQueryDate")) {
			if (!para.getDate("endQueryDate").after(para.getDate("beginQueryDate"))) {
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
////////////////////////
        if(this.bizProject.getValue()!=null){
        	result.put(PROJECT_IDS,((CurProjectInfo)this.bizProject.getValue()).getId().toString());
		}else{
			//给出提示
		}
        result.put(YEAR_FROM, (this.spiYearFrom.getValue()).toString());
        result.put(YEAR_TO, (this.spiYearTo.getValue()).toString());
        result.put(MONTH_FROM, (this.spiMonthFrom.getValue()).toString());
        result.put(MONTH_TO, (this.spiMonthTo.getValue()).toString());
//        result.put("beginQueryDate", this.getBeginQueryDate(param));
//        result.put("endQueryDate", this.getEndQueryDate(param));
		
        ////////////////////////
//        result.put("isNumDateOrder", "asdfasdfsadfasdf");
        return result;
    }
}