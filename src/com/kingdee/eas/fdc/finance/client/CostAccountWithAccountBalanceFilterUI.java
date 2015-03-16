/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class CostAccountWithAccountBalanceFilterUI extends AbstractCostAccountWithAccountBalanceFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostAccountWithAccountBalanceFilterUI.class);
    
    private Date[] pkdate ;
    public static final String YEAR = "periodYear";
    public static final String MONTH = "periodNumber";
    //工程项目
    public static final String PROJECT = "project";
    //成本中心
    public static final String COSTCENTER = "costCenter";
    public PeriodInfo curPeriod = null;
    /**
     * output class constructor
     */
    public CostAccountWithAccountBalanceFilterUI() throws Exception
    {
        super();
        pkdate = FDCClientHelper.getCompanyCurrentDate();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	public CustomerParams getCustomerParams() {
		// TODO 自动生成方法存根
		FDCCustomerParams param = new FDCCustomerParams();
		param.add(YEAR, ((Integer) spiYear.getValue()).intValue());
		param.add(MONTH, ((Integer) spiMonth.getValue()).intValue());
		Object value = prmtProject.getValue();
		if (value != null) {
			if (value instanceof Object[]) {
				Object[] objs = (Object[]) value;
				String[] ids = new String[objs.length];
				for (int i = 0; i < objs.length; i++) {
					ids[i] = (((CurProjectInfo) objs[i]).getId().toString());
				}
				param.add(PROJECT, ids);
			}else{
				String id = ((CurProjectInfo)value).getId().toString();
				param.add(PROJECT, new String[]{id});
			}
		}
		
		value = prmtCostCenter.getValue();
		if(value !=null){
			CostCenterOrgUnitInfo cost = (CostCenterOrgUnitInfo) value;
			param.add(COSTCENTER, cost.getId().toString());
		}
		
		return param.getCustomerParams();
	}

	public void onLoad() throws Exception {
		// TODO 自动生成方法存根
		super.onLoad();
		String orgUnitId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		EntityViewInfo view = new EntityViewInfo();		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",orgUnitId));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		view.setFilter(filter);
		prmtProject.setEntityViewInfo(view);
//		prmtProject.setEnabledMultiSelection(true);
		
		view = new EntityViewInfo();		
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
		String longNum = SysContext.getSysContext().getCurrentOrgUnit().getLongNumber();
		filter.getFilterItems().add(new FilterItemInfo("longNumber",longNum+"%",CompareType.LIKE));
		view.setFilter(filter);
		prmtCostCenter.setEntityViewInfo(view);
		
		Date curDate = pkdate[0];
		int year = curDate.getYear()+1900;
		int month = curDate.getMonth()+1;
		Calendar cal = new GregorianCalendar();
		cal.setTime(curDate);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
		Date preDate=cal.getTime();
		int startYear=preDate.getYear()+1900;
		int startMonth=preDate.getMonth()+1;
		SpinnerNumberModel yearM = new SpinnerNumberModel(startYear, 1900, 2100, 1);
		SpinnerNumberModel monthM = new SpinnerNumberModel(startMonth, 1, 12, 1);
		spiYear.setModel(yearM);
		spiMonth.setModel(monthM);
		spiYear.setValue(new Integer(startYear));
		spiMonth.setValue(new Integer(startMonth));
		
//		boolean isIncorporation = FDCUtils.IsInCorporation(null, SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo().getId().toString());
//		
//		if(!isIncorporation){
			CompanyOrgUnitInfo companyInfo = CompanyOrgUnitFactory.getRemoteInstance()
			.getCompanyOrgUnitInfo(new ObjectUuidPK(SysContext.getSysContext().getCurrentFIUnit().getId()),GlUtils.getCompanySic());
			curPeriod = FDCUtils.fetchPeriod(null, companyInfo.getAccountPeriodType().getId().toString(),pkdate[0] );
			
			if(curPeriod==null){
				curPeriod = PeriodUtils.getPeriodInfo(null ,new Date() ,companyInfo);
			}
//		}
	}

	public void setCustomerParams(CustomerParams cp) {
		// TODO 自动生成方法存根
		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);
		spiYear.setValue(new Integer(para.getInt(YEAR)));
		
		spiMonth.setValue(new Integer(para.getInt(MONTH)));
		if(para.get(PROJECT) != null){
			String[] prjId = para.getStringArray(PROJECT);
			Object[] prjs = new Object[prjId.length];
			CurProjectInfo info = null;
			for (int i = 0; i < prjId.length; i++) {
				try {
					info = CurProjectFactory.getRemoteInstance()
							.getCurProjectInfo(
									new ObjectUuidPK(BOSUuid.read(prjId[i])));
					prjs[i] = info;
				} catch (EASBizException e) {
					handUIException(e);
				} catch (BOSException e) {
					handUIException(e);
				} catch (UuidException e) {
					handUIException(e);
				}
			}
			prmtProject.setValue(prjs);
		}else{
			prmtProject.setValue(null);
		}
		
		if (para.get(COSTCENTER) != null) {
			String costId = para.getString(COSTCENTER);
			try {
				CostCenterOrgUnitInfo cost = CostCenterOrgUnitFactory
						.getRemoteInstance().getCostCenterOrgUnitInfo(
								new ObjectUuidPK(costId));
				prmtCostCenter.setValue(cost);
			} catch (EASBizException e) {
				handUIException(e);
			} catch (BOSException e) {
				handUIException(e);
			}
		}else{
			prmtCostCenter.setValue(null);
		}
		
	}

	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo(YEAR,spiYear.getValue()));
		filterInfo.getFilterItems().add(new FilterItemInfo(MONTH,spiMonth.getValue()));
		if (para.get(PROJECT) != null) {
			String[] prjIDs = para.getStringArray(PROJECT);
			// String prjId = (String)para.get(PROJECT);
			filterInfo.getFilterItems().add(
					new FilterItemInfo(PROJECT, FMHelper.Array2Set(prjIDs),
							CompareType.INCLUDE));
		}
		if (para.get(COSTCENTER) != null) {
			String costID = para.getString(COSTCENTER);
			try {
				filterInfo.getFilterItems().add(new FilterItemInfo(COSTCENTER,costID));
				Map prj = FDCHelper.getPrjInfosByCostCenter(null, costID);
				Set prjIDs = (Set) prj.get("prjIdSet");
				filterInfo.getFilterItems()
						.add(
								new FilterItemInfo(PROJECT, prjIDs,
										CompareType.INCLUDE));
			} catch (BOSException e) {
				handUIException(e);
			}
		}
		return filterInfo;
	}
	
	public Integer getPeriodYear(){
		return spiYear.getIntegerVlaue();
	}
	public Integer getPeriodNumber(){
		return spiMonth.getIntegerVlaue();
	}

	public boolean verify() {
		// TODO 自动生成方法存根
		String msg = "当前财务组织本期间还未进行期末结账，不能查看对帐表！";
		if(curPeriod.getPeriodYear() > getPeriodYear().intValue())
			return true;
		else if(curPeriod.getPeriodYear() == getPeriodYear().intValue()&&
				curPeriod.getPeriodNumber() > getPeriodNumber().intValue())
			return true;
		else{
			//R101104-183 月结前，可进行财务成本对帐数据核对
//			MsgBox.showInfo(this,msg);
//			SysUtils.abort();
		}	
		return true;
	}

}