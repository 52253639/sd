/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.assistant.PeriodFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceTypeEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class SaleFinancialCheckupFilterUI extends AbstractSaleFinancialCheckupFilterUI {

	private static final Logger logger = CoreUIObject.getLogger(SaleFinancialCheckupFilterUI.class);
	private static final String BEGINDATE = "beginDate";
	private static final String ENDDATE = "endDate";
	private static final String PERIODID = "periodId";
	private Date beginDate = null;
	private Date endDate = null;
	private String periodId = null;

	/**
	 * output class constructor
	 */
	public SaleFinancialCheckupFilterUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		OrgUnitInfo companyInfo = SysContext.getSysContext().getCurrentFIUnit(); // 当前组织
		PeriodInfo companyCurrentPeriod = SystemStatusCtrolUtils.getCurrentPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
        if(companyCurrentPeriod!=null){
        	this.f7Balance.setValue(companyCurrentPeriod);
        }		
	}

	protected void f7Balance_dataChanged(DataChangeEvent e) throws Exception {
		super.f7Balance_dataChanged(e);
		PeriodInfo info = (PeriodInfo) this.f7Balance.getData();
		String id = info.getId().toString();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		info = PeriodFactory.getRemoteInstance().getPeriodInfo(new ObjectUuidPK(BOSUuid.read(id)), sel);
		if (info != null) {
			beginDate = info.getBeginDate();
			endDate = info.getEndDate();
			periodId = info.getId().toString();
		} else {
			MsgBox.showInfo("您还没选择月结期间！");
			return;
		}
	}

	public FilterInfo getFilterInfo() {
		FilterInfo filter = new FilterInfo();
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		filter.getFilterItems().add(new FilterItemInfo(BEGINDATE, beginDate));
		filter.getFilterItems().add(new FilterItemInfo(ENDDATE, endDate));
		filter.getFilterItems().add(new FilterItemInfo(PERIODID, periodId));

		return filter;
	}

	public Date getBeginDate(FDCCustomerParams para) {
		Date date = null;
		String begin = para.getString(BEGINDATE);

		try {
			date = DateTimeUtils.parseDate(begin, "yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	public Date getEndDate(FDCCustomerParams para) {
		Date date = null;
		String end = para.getString(ENDDATE);

		try {
			date = DateTimeUtils.parseDate(end, "yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();

		Object obj = this.f7Balance.getValue();
		if (obj instanceof PeriodInfo) {
			PeriodInfo info = (PeriodInfo) obj;
			param.add(BEGINDATE, info.getBeginDate().toString());
			param.add(ENDDATE, info.getEndDate().toString());

		}

		return param.getCustomerParams();
	}

	private void initControlDate() {
		SpinnerNumberModel yearMoFrom = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
	}

	public void setCustomerParams(CustomerParams cp) {
		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);
		this.initControlDate();

		super.setCustomerParams(para.getCustomerParams());
	}

	public boolean verify() {
		if (this.f7Balance.getValue() == null) {
			MsgBox.showWarning("请选择月结期间！");
			return false;
		}
		return true;
	}

}