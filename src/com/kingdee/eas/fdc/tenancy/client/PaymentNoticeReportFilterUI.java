/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;

/**
 * output class name
 */
public class PaymentNoticeReportFilterUI extends
		AbstractPaymentNoticeReportFilterUI {
	private static final Logger logger = CoreUIObject
			.getLogger(PaymentNoticeReportFilterUI.class);

	/**
	 * output class constructor
	 */
	public PaymentNoticeReportFilterUI() throws Exception {
		super();
	}

	public void clear() {
		Calendar cal = Calendar.getInstance();
		Date now = UIRuleUtil.now();
		cal.setTime(now);
		now.setDate(cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		this.pkDateFrom.setValue(now);
		now.setDate(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		this.pkDateTo.setValue(now);
		this.txtNote.setText("");
	}

    public void setCustomerParams(CustomerParams cp){   
		if (cp == null){
			return;
		}	
		FDCCustomerParams para = new FDCCustomerParams(cp);
		if(para.getDate("dateFrom")!=null)
			pkDateFrom.setValue(para.getDate("dateFrom"));
		if(para.getDate("dateTo")!=null)
			pkDateTo.setValue(para.getDate("dateTo"));
		if(para.getString("NOTE")!=null)
			this.txtNote.setText(para.getString("NOTE"));
		super.setCustomerParams(para.getCustomerParams());
    }
    
    public CustomerParams getCustomerParams(){
		FDCCustomerParams param = new FDCCustomerParams();
		param.add("dateFrom", pkDateFrom.getSqlDate());
		param.add("dateTo", pkDateTo.getSqlDate());
		param.add("NOTE",txtNote.getText());
		return param.getCustomerParams();
    }

	
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
//		Calendar cal = Calendar.getInstance();
//		Date now = UIRuleUtil.now();
//		cal.setTime(now);
//		now.setDate(cal.getActualMinimum(Calendar.DAY_OF_MONTH));
//		this.pkDateFrom.setValue(now);
//		now.setDate(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
//		this.pkDateTo.setValue(now);
	}

	public FilterInfo getFilterInfo() {
		FilterInfo fi = super.getFilterInfo();
		if (pkDateFrom != null) {
			fi.getFilterItems().add(
					new FilterItemInfo("dateFrom", pkDateFrom.getSqlDate(),
							CompareType.GREATER_EQUALS));
		}
		if (pkDateTo != null) {
			fi.getFilterItems().add(
					new FilterItemInfo("dateTo", pkDateTo.getSqlDate(),
							CompareType.LESS));
		}
		fi.getFilterItems()
				.add(
						new FilterItemInfo("NOTE", txtNote.getText(),
								CompareType.LESS));
		return fi;
	}
	
}