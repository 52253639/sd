/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class CustomerRentalFilterUI extends AbstractCustomerRentalFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerRentalFilterUI.class);
    public static final String KEY_BillBeginDate = "startDate";

	public static final String KEY_BillEndDate = "endDate";

	public static final String KEY_F7Customer = "customer";
	
	public static final String KEY_Contract="id";
	
	public static final String KEY_Year="year";
	
	public static final String KEY_Month="month";
	
	public boolean isOnLoad = false;
    /**
     * output class constructor
     */
    public CustomerRentalFilterUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
		super.onLoad();

		this.setOnLoad(true);
		EntityViewInfo view =new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled","1"));
		view.setFilter(filter);
		f7Customer.setEntityViewInfo(view);
		
		SpinnerNumberModel count = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.YEAR), 0, 9999, 1);
		year.setModel(count);
		
		SpinnerNumberModel count1 = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.MONTH)+1,1, 12, 1);
		month.setModel(count1);
	}

	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		FilterInfo filter = new FilterInfo();

		// 合同客户

		// 合同业务时间
		Date billBeginDate = para.getDate(KEY_BillBeginDate);
		if (billBeginDate != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("tenancyDate", billBeginDate,
							CompareType.GREATER_EQUALS));
		}
		Date billEndDate = para.getDate(KEY_BillEndDate);
		if (billEndDate != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("tenancyDate", FDCDateHelper.addDays(
							billEndDate, 1), CompareType.LESS));
		}
		
		String id = para.getString(KEY_Contract);
		if (id != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.EQUALS));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("id", null));
		}
		Integer year=para.getInteger(KEY_Year);
		
		if (year != null) {
			filter.getFilterItems().add(new FilterItemInfo("year", year, CompareType.EQUALS));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("year", null));
		}
		
		Integer month=para.getInteger(KEY_Month);
		
		if (month != null) {
			filter.getFilterItems().add(new FilterItemInfo("month", month, CompareType.EQUALS));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("month", null));
		}
		return filter;

	}

	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();

		param.add(KEY_BillBeginDate, (Date) this.dpBillBeginDate.getValue());
		param.add(KEY_BillEndDate, (Date) this.dpBillEndDate.getValue());

		if (this.f7Customer.getValue() != null) {
			param.add(KEY_F7Customer, ((FDCCustomerInfo) this.f7Customer
					.getValue()).getId().toString());
		}
		if (this.contract.getValue() != null) {
			param.add(KEY_Contract, ((TenancyBillInfo) this.contract
					.getValue()).getId().toString());
		}
		param.add(KEY_Year, this.year.getIntegerVlaue());
		param.add(KEY_Month, this.month.getIntegerVlaue());
		return param.getCustomerParams();
	}

	public void setCustomerParams(CustomerParams cp) {
		if (cp == null)
			return;
		FDCCustomerParams para = new FDCCustomerParams(cp);

		this.dpBillBeginDate.setValue(para.getDate(KEY_BillBeginDate));

		this.dpBillEndDate.setValue(para.getDate(KEY_BillEndDate));

		String customerId = para.getString(KEY_F7Customer);
		if (customerId != null) {
			try {
				FDCCustomerInfo info = FDCCustomerFactory.getRemoteInstance()
						.getFDCCustomerInfo(
								new ObjectUuidPK(BOSUuid.read(customerId)));
				this.f7Customer.setValue(info);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String contractId= para.getString(KEY_Contract);
		
		if (contractId != null) {
			try {
				TenancyBillInfo info = TenancyBillFactory.getRemoteInstance()
						.getTenancyBillInfo(
								new ObjectUuidPK(BOSUuid.read(contractId)));
				this.contract.setValue(info);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.year.setValue(new Integer(para.getInt(KEY_Month)));
		this.month.setValue(new Integer(para.getInt(KEY_Year)));
		super.setCustomerParams(para.getCustomerParams());
	}

	public boolean verify() {
		if (this.dpBillBeginDate.getValue() != null
				&& this.dpBillEndDate.getValue() != null) {
			if (DateTimeUtils.dayAfter((Date) this.dpBillBeginDate.getValue(),
					(Date) this.dpBillEndDate.getValue())) {
				MsgBox.showWarning("合同开始日期不能大于合同结束日期！");
				return false;
			}
		}
		
		return true;
	}

	public boolean isOnLoad() {
		return isOnLoad;
	}

	public void setOnLoad(boolean isOnLoad) {
		this.isOnLoad = isOnLoad;
	}
	public Map getParam() {

		Map param = new HashMap();

		param.put(KEY_BillBeginDate, dpBillBeginDate.getValue());
		param.put(KEY_BillEndDate, dpBillEndDate.getValue());

		if (this.f7Customer.getValue() != null) {
			param.put(KEY_F7Customer, ((FDCCustomerInfo) this.f7Customer
					.getValue()).getId().toString());
		}
		
		if (this.contract.getValue() != null) {
			param.put(KEY_Contract, ((TenancyBillInfo) this.contract
					.getValue()).getId().toString());
		}
		param.put(KEY_Year, this.year.getIntegerVlaue());
		param.put(KEY_Month, this.month.getIntegerVlaue());
		return param;
	}

}