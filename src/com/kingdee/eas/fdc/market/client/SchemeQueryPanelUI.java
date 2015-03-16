/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.util.Calendar;
import java.util.Date;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.market.ThemeEnum;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class SchemeQueryPanelUI extends AbstractSchemeQueryPanelUI {
	private static final Logger logger = CoreUIObject.getLogger(SchemeQueryPanelUI.class);

	/**
	 * output class constructor
	 */
	public SchemeQueryPanelUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * 加载时。。加入下拉框Items，设置日期数字框的model，赋初始值
	 */
	public void onLoad() throws Exception {
		super.onLoad();
		state.addItem("");
		state.addItem(ThemeEnum.UnStarted);
		state.addItem(ThemeEnum.Underway);
		state.addItem(ThemeEnum.Finish);
		state.addItem(ThemeEnum.Canceled);
		SpinnerNumberModel startMonthModel = new SpinnerNumberModel(6, 1, 12, 1);
		SpinnerNumberModel endMonthModel = new SpinnerNumberModel(6, 1, 12, 1);
		SpinnerNumberModel startYearModel = new SpinnerNumberModel(2012, 1990, 3000, 1);
		SpinnerNumberModel endYearModel = new SpinnerNumberModel(2012, 1990, 3000, 1);
		this.startYear.setModel(startYearModel);
		this.startMonth.setModel(startMonthModel);
		Date now = SysUtil.getAppServerTime(null);
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		startYear.setValue(new Integer(cal.get(Calendar.YEAR)));
		startMonth.setValue(new Integer(cal.get(Calendar.MONTH) + 1));

		this.endYear.setModel(endYearModel);
		this.endMonth.setModel(endMonthModel);
		endYear.setValue(new Integer(cal.get(Calendar.YEAR)));
		endMonth.setValue(new Integer(cal.get(Calendar.MONTH) + 1));
	}

	public FilterInfo getFilterInfo() {
		FilterInfo filter = new FilterInfo();

		// 主题
		if (this.theme.getText() != null && !this.theme.getText().trim().equals("")) {
			filter.getFilterItems().add(new FilterItemInfo("EnterprisePlanEntry.theme", "%" + this.theme.getText().trim() + "%", CompareType.LIKE));
		}
		// 主题状态
		if (this.state.getSelectedItem() instanceof ThemeEnum) {
			filter.getFilterItems().add(new FilterItemInfo("EnterprisePlanEntry.state", ((ThemeEnum) this.state.getSelectedItem()).getValue(), CompareType.EQUALS));
		}
		// 开始时间
		int startYear =((Integer)this.startYear.getValue()).intValue();
		int endYear=((Integer)this.endYear.getValue()).intValue();
		int startMonth =((Integer)this.startMonth.getValue()).intValue();
		int endMonth =((Integer)this.endMonth.getValue()).intValue();
		filter.getFilterItems().add(new FilterItemInfo("planYear", String.valueOf(startYear), CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("planYear", String.valueOf(endYear), CompareType.LESS_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("planMonth", String.valueOf(startMonth), CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("planMonth", String.valueOf(endMonth), CompareType.LESS_EQUALS));
		
		
		// //开始日期
		// if(this.startYear.getValue() instanceof Integer
		// &&this.startMonth.getValue() instanceof Integer){
		// int year=((Integer)this.startYear.getValue()).intValue();
		// int month=((Integer)this.startMonth.getValue()).intValue();
		// SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// String dateStringToParse = year+"-"+month+"-1";
		// try {
		// java.util.Date date = bartDateFormat.parse(dateStringToParse);
		// java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		// filter.getFilterItems().add(new
		// FilterItemInfo("startDate",sqlDate,CompareType.GREATER_EQUALS));
		// }
		// catch (Exception ex) {
		// System.out.println(ex.getMessage());
		// }
		//
		// }
		// //结束日期
		// if(this.endYear.getValue() instanceof Integer
		// &&this.endMonth.getValue() instanceof Integer){
		// int year=((Integer)this.endYear.getValue()).intValue();
		// int month=((Integer)this.endMonth.getValue()).intValue()+1;
		// SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// String dateStringToParse = year+"-"+month+"-1";
		// try {
		// java.util.Date date = bartDateFormat.parse(dateStringToParse);
		// java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		// filter.getFilterItems().add(new
		// FilterItemInfo("startDate",sqlDate,CompareType.LESS));
		// }
		// catch (Exception ex) {
		// System.out.println(ex.getMessage());
		// }
		//	    
		// }
		return filter;
	}
}