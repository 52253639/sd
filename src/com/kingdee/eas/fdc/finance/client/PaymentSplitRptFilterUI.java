/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
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
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.TreeSelectDialog;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class PaymentSplitRptFilterUI extends AbstractPaymentSplitRptFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(PaymentSplitRptFilterUI.class);
    
    /**
     * output class constructor
     */
    public PaymentSplitRptFilterUI() throws Exception
    {
        super();
        //取当前日期而不是公司的期间日期
		pkdate=new Date[1];
		pkdate[0]=new Date();
    }
    /**
     * output btnProjectSelectSelect_actionPerformed method
     */
    protected void btnProjectSelectSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnProjectSelectSelect_actionPerformed(e);
    }
    private static final String IS_DEALWITH_PERIOD = "isDealWithPeriod";
    
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
//		Date preDate=FDCDateHelper.getPreMonth(curDate);
//		int startYear=preDate.getYear()+1900;
//		int startMonth=preDate.getMonth()+1;
		//初始化时间控件
		SpinnerNumberModel yearMo1 = new SpinnerNumberModel(year, 1900, 2100, 1);
		spiYearFrom.setModel(yearMo1);
		SpinnerNumberModel yearMo2 = new SpinnerNumberModel(year, 1900, 2100, 1);
		spiYearTo.setModel(yearMo2);
		this.spiMonthFrom.setVisible(true);
		this.spiMonthTo.setVisible(true);
		SpinnerNumberModel monthMo1 = new SpinnerNumberModel(month, 1, 12, 1);
		spiMonthFrom.setModel(monthMo1);
		SpinnerNumberModel monthMo2 = new SpinnerNumberModel(month, 1, 12, 1);
		spiMonthTo.setModel(monthMo2);
	}
	/**
	 * 方案filter
	 */
	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());

		FilterInfo filter = new FilterInfo();

		if (para.isNotNull(PROJECT_IDS)) {
			filter.getFilterItems().add(new FilterItemInfo("curProject", FDCHelper.getSetByArray(para.getStringArray(PROJECT_IDS)), CompareType.INCLUDE));
		} else {
			MsgBox.showError(this, "请选择明细工程项目");
		}
		if (para.isNotNull(YEAR_FROM)) {
			filter.getFilterItems().add(new FilterItemInfo(YEAR_FROM,  this.spiYearFrom.getValue()));
		} else {
			MsgBox.showError(this, "请选择开始期间年");
		}
		if (para.isNotNull(YEAR_TO)) {
			filter.getFilterItems().add(new FilterItemInfo(YEAR_TO, spiYearTo.getValue()));
		} else {
			MsgBox.showError(this, "请选择截至期间年");
		}
		if (para.isNotNull(MONTH_FROM)) {
			filter.getFilterItems().add(new FilterItemInfo(MONTH_FROM, spiMonthFrom.getValue()));
		} else {
			MsgBox.showError(this, "请选择开始期间月");
		}
		if (para.isNotNull(MONTH_TO)) {
			filter.getFilterItems().add(new FilterItemInfo(MONTH_TO, spiMonthTo.getValue()));
		} else {
			MsgBox.showError(this, "请选择截至期间月");
		}
		filter.getFilterItems().add(new FilterItemInfo(IS_DEALWITH_PERIOD, Boolean.valueOf(chkPeriod.isSelected())));
		return filter;
	}

	private Date getBeginPeriod(FDCCustomerParams para) {
		Date date = null;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
		cal.set(Calendar.MONTH, para.getInt(MONTH_FROM));
		cal.set(Calendar.DATE, 0);
		date = cal.getTime();
		return DateTimeUtils.truncateDate(date);
	}

	private Date getEndPeriod(FDCCustomerParams para) {
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
		param.add("beginPeriod", this.getBeginPeriod(param));
		param.add("endPeriod", this.getEndPeriod(param));
		param.add(IS_DEALWITH_PERIOD, this.chkPeriod.isSelected());
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
			Set authorizedOrgs = FDCUtils.getAuthorizedOrgs(null);
			//权限组织
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",authorizedOrgs,CompareType.INCLUDE));
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
		this.chkPeriod.setSelected(para.getBoolean(IS_DEALWITH_PERIOD));
		setSelected();
		super.setCustomerParams(para.getCustomerParams());
	}

	public boolean verify() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		//当不按期间查询时，不校验证期间  by hpw 2010.11.17
		if (!para.getBoolean(IS_DEALWITH_PERIOD)&&para.isNotNull("endPeriod") && para.isNotNull("beginPeriod")) {
			if (para.getDate("endPeriod").before(para.getDate("beginPeriod"))) {
				MsgBox.showWarning(this, EASResource.getString(resourcePath, "DateBoundErrer"));
				return false;
			}
		}
		if(this.bizProject.getValue()==null){
			MsgBox.showWarning(this, EASResource.getString(resourcePath, "SelectCurProj"));
			return false;
		}
		if(!((CurProjectInfo)bizProject.getValue()).isIsLeaf()){
			FDCMsgBox.showWarning(this,"请选择明细工程项目!");
			return false;
		}
		return true;
	}
	
	protected void chkPeriod_stateChanged(ChangeEvent e) throws Exception {
		setSelected();
	}
    protected void chkPeriod_mouseClicked(MouseEvent e) throws Exception {
    	
    }
    private void setSelected( ){
    	boolean isPeriod = chkPeriod.isSelected();
    	if(isPeriod){
    		spiYearFrom.setEnabled(false);
    		spiMonthFrom.setEnabled(false);
    		spiYearTo.setEnabled(false);
    		spiMonthTo.setEnabled(false);
    	}else{
    		spiYearFrom.setEnabled(true);
    		spiMonthFrom.setEnabled(true);
    		spiYearTo.setEnabled(true);
    		spiMonthTo.setEnabled(true);
    	}
    }
}