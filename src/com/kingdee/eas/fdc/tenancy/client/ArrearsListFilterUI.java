/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
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
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class ArrearsListFilterUI extends AbstractArrearsListFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ArrearsListFilterUI.class);
    
    private static final long serialVersionUID = 3439972579405265650L;

	public static final String KEY_BillBeginDate = "billBeginDate";

	public static final String KEY_BillEndDate = "billEndDate";

	public static final String KEY_F7Customer = "f7Customer";

	public static final String KEY_Deposit = "deposit";

	public static final String KEY_Rent = "rent";

	public static final String KEY_Expenses = "expenses";

	public static final String KEY_AppBeginDate = "appBeginDate";

	public static final String KEY_AppEndDate = "appEndDate";

	public static final String KEY_ArrearageDay = "arrearageDay";

	public static final String KEY_ALL = "all";

	public static final String KEY_DelayAndNo = "delayAndNo";

	public static final String KEY_DelayAndYes = "delayAndYes";

	public static final String KEY_NoDelay = "noDelay";
	
	public static final String KEY_OtherPay="otherPay";
	
	public static final String KEY_Contract="id";
	public boolean isOnLoad = false;

	public ArrearsListFilterUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.setUITitle("客户收款明细");
//		this.setArrearageEnable();
		this.setOnLoad(true);
		EntityViewInfo view =new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled","1"));
		view.setFilter(filter);
		f7Customer.setEntityViewInfo(view);
//		f7Customer.setEntityViewInfo(CommerceHelper.getPermitCustomerView(
//				SysContext.getSysContext().getCurrentUserInfo(),
//				MoneySysTypeEnum.TenancySys));
		SpinnerNumberModel count = new SpinnerNumberModel(0, -9999, 9999, 1);
//		arrearageDay.setModel(count);
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

		param.add(KEY_Deposit, this.cbDeposit.isSelected());
		param.add(KEY_Rent, this.cbRent.isSelected());
		param.add(KEY_Expenses, this.cbExpenses.isSelected());

		param.add(KEY_AppBeginDate, (Date) this.dpAppBeginDate.getValue());
		param.add(KEY_AppEndDate, (Date) this.dpAppEndDate.getValue());

//		param.add(KEY_ArrearageDay, this.arrearageDay.getIntegerVlaue());
//
//		param.add(KEY_ALL, this.all.isSelected());
//		param.add(KEY_DelayAndNo, this.delayAndNo.isSelected());
//		param.add(KEY_DelayAndYes, this.delayAndYes.isSelected());
//		param.add(KEY_NoDelay, this.noDelay.isSelected());

		param.add(KEY_OtherPay, this.cbOtherPay.isSelected());
		if (this.contract.getValue() != null) {
			param.add(KEY_Contract, ((TenancyBillInfo) this.contract
					.getValue()).getId().toString());
		}
		return param.getCustomerParams();
	}

	public void setCustomerParams(CustomerParams cp) {
		if (cp == null)
			return;
		FDCCustomerParams para = new FDCCustomerParams(cp);

		this.dpBillBeginDate.setValue(para.getDate(KEY_BillBeginDate));

		this.dpBillEndDate.setValue(para.getDate(KEY_BillEndDate));

		this.dpAppBeginDate.setValue(para.getDate(KEY_AppBeginDate));

		this.dpAppEndDate.setValue(para.getDate(KEY_AppEndDate));

//		this.arrearageDay.setValue(new Integer(para.getInt(KEY_ArrearageDay)));

		this.cbDeposit.setSelected(para.getBoolean(KEY_Deposit));
		this.cbRent.setSelected(para.getBoolean(KEY_Rent));
		this.cbExpenses.setSelected(para.getBoolean(KEY_Expenses));

//		this.all.setSelected(para.getBoolean(KEY_ALL));
//		this.delayAndNo.setSelected(para.getBoolean(KEY_DelayAndNo));
//		this.delayAndYes.setSelected(para.getBoolean(KEY_DelayAndYes));
//		this.noDelay.setSelected(para.getBoolean(KEY_NoDelay));

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

		super.setCustomerParams(para.getCustomerParams());
	}

	public boolean verify() {
		if (this.dpBillBeginDate.getValue() != null
				&& this.dpBillEndDate.getValue() != null) {
			if (DateTimeUtils.dayAfter((Date) this.dpBillBeginDate.getValue(),
					(Date) this.dpBillEndDate.getValue())) {
				MsgBox.showWarning("业务开始日期不能大于业务结束日期！");
				return false;
			}
		}
		if (this.dpAppBeginDate.getValue() != null
				&& this.dpAppEndDate.getValue() != null) {
			if (DateTimeUtils.dayAfter((Date) this.dpAppBeginDate.getValue(),
					(Date) this.dpAppEndDate.getValue())) {
				MsgBox.showWarning("应收开始日期不能大于应收结束日期！");
				return false;
			}
		}
		return true;
	}

	/**
	 * 返回款项类型
	 * 
	 * @return
	 */
	public MoneyTypeEnum[] getMoneyTypeEnum(FDCCustomerParams para) {
		MoneyTypeEnum money[] = new MoneyTypeEnum[9];

		boolean deposit = para.getBoolean(KEY_Deposit);
		boolean rent = para.getBoolean(KEY_Rent);
		boolean expenses = para.getBoolean(KEY_Expenses);
		boolean otherPay = para.getBoolean(KEY_OtherPay);
		
		if (deposit) {
			money[0] = MoneyTypeEnum.DepositAmount;
		}
		if (rent) {
			money[1] = MoneyTypeEnum.RentAmount;
		}
		if (expenses) {
			money[2] = MoneyTypeEnum.PeriodicityAmount;
		}
		
		if (otherPay) {
			money[3] = MoneyTypeEnum.LateFee;
			money[4] = MoneyTypeEnum.CommissionCharge;
			money[5] = MoneyTypeEnum.FitmentAmount;
			money[6] = MoneyTypeEnum.ElseAmount;
			money[7] = MoneyTypeEnum.ReplaceFee;
			money[8] = MoneyTypeEnum.BreachFee;
		}
		return money;
	}

	/**
	 * 应付开始日期
	 * 
	 * @return
	 */
	public Date getAppBeginDate(FDCCustomerParams para) {
		Date date = null;
		date = para.getDate(KEY_AppBeginDate);

		return date;
	}

	/**
	 * 返回欠款天数
	 * 
	 * @param para
	 * @return
	 */
	public int getArrearageDay(FDCCustomerParams para) {
		int temp = para.getInt(KEY_ArrearageDay);
		return temp;
	}

	/**
	 * 应付结束日期
	 * 
	 * @param para
	 * @return
	 */
	public Date getAppEndDate(FDCCustomerParams para) {
		Date date = null;
		date = para.getDate(KEY_AppEndDate);

		return date;
	}

	
	/**
	 * 返回查询的欠款类型
	 * 
	 * @param para
	 * @return
	 */
	public String getQueryType(FDCCustomerParams para) {
		String type = null;

		if (para.getBoolean(KEY_ALL))
			type = KEY_ALL;
		else if (para.getBoolean(KEY_DelayAndNo))
			type = KEY_DelayAndNo;
		else if (para.getBoolean(KEY_DelayAndYes))
			type = KEY_DelayAndYes;
		else if (para.getBoolean(KEY_NoDelay))
			type = KEY_NoDelay;

		return type;
	}

	public boolean isOnLoad() {
		return isOnLoad;
	}

	public void setOnLoad(boolean isOnLoad) {
		this.isOnLoad = isOnLoad;
	}

//	private void setArrearageEnable() {
//		if (this.all.isSelected()) {
//			this.arrearageDay.setValue(new Integer(0));
//			this.arrearageDay.setEnabled(false);
//		} else if (this.delayAndNo.isSelected()) {
//			this.arrearageDay.setEnabled(true);
//		} else if (this.delayAndYes.isSelected()) {
//			this.arrearageDay.setEnabled(true);
//		} else if (this.noDelay.isSelected()) {
//			this.arrearageDay.setValue(new Integer(0));
//			this.arrearageDay.setEnabled(false);
//		}
//
//	}

//	protected void all_actionPerformed(ActionEvent e) throws Exception {
//		this.setArrearageEnable();
//	}
//
//	protected void delayAndNo_actionPerformed(ActionEvent e) throws Exception {
//		this.setArrearageEnable();
//	}
//
//	protected void delayAndYes_actionPerformed(ActionEvent e) throws Exception {
//		this.setArrearageEnable();
//	}
//
//	protected void noDelay_actionPerformed(ActionEvent e) throws Exception {
//		this.setArrearageEnable();
//	}

	public Map getParam() {

		Map param = new HashMap();

		param.put(KEY_BillBeginDate, dpBillBeginDate.getValue());
		param.put(KEY_BillEndDate, dpBillEndDate.getValue());

		if (this.f7Customer.getValue() != null) {
			param.put(KEY_F7Customer, ((FDCCustomerInfo) this.f7Customer
					.getValue()).getId().toString());
		}

//		param.put(KEY_ALL, new Boolean(all.isSelected()));
//		param.put(KEY_DelayAndNo, new Boolean(delayAndNo.isSelected()));
//		param.put(KEY_DelayAndYes, new Boolean(delayAndYes.isSelected()));
//		param.put(KEY_NoDelay, new Boolean(noDelay.isSelected()));

//		if (arrearageDay.isEnabled()) {
//			param.put(KEY_ArrearageDay, arrearageDay.getIntegerVlaue());
//		}

		param.put(KEY_Deposit, new Boolean(cbDeposit.isSelected()));
		param.put(KEY_Rent, new Boolean(cbRent.isSelected()));
		param.put(KEY_Expenses, new Boolean(cbExpenses.isSelected()));

		param.put(KEY_AppBeginDate, dpAppBeginDate.getValue());
		param.put(KEY_AppEndDate, dpAppEndDate.getValue());
		
		if (this.contract.getValue() != null) {
			param.put(KEY_Contract, ((TenancyBillInfo) this.contract
					.getValue()).getId().toString());
		}
		
		param.put(KEY_OtherPay, new Boolean(cbOtherPay.isSelected()));
		
		return param;
	}

}