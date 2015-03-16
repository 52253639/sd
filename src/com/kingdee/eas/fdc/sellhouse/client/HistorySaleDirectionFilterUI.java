/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.util.HashSet;
import java.util.Set;

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
import com.kingdee.eas.basedata.assistant.PeriodFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceTypeEnum;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class HistorySaleDirectionFilterUI extends AbstractHistorySaleDirectionFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(HistorySaleDirectionFilterUI.class);
    
    /**
     * output class constructor
     */
    public HistorySaleDirectionFilterUI() throws Exception
    {
        super();
    }
    
	private static final String PERIOD_ID = "periodId";
//	private static final String PERIOD_NAME = "periodName";
//	private static final String PERIOD_NUMBER = "periodNumber";

	public void clear() {
		this.f7Period.setValue(null);
	}

	//保存方案的过滤条件时会调用， 是只有查询方案中的过滤条件不为空时，有默认情况下才可以不打开过滤界面
	public FilterInfo getFilterInfo() {
		FilterInfo filter = super.getFilterInfo();
	
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		PeriodInfo period = this.getPeriod(para);
		
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("periodId", period == null ? null : period.getId().toString()));
		
		return filter;
	}
	
	public PeriodInfo getPeriod(FDCCustomerParams para){
		String periodId = para.getString(PERIOD_ID);
		if(periodId == null){
			return null;
		}
		PeriodInfo period = null;
		try {
			period = PeriodFactory.getRemoteInstance().getPeriodInfo(new ObjectUuidPK(periodId));
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}

		return period;
	}

	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();
		PeriodInfo period = (PeriodInfo) this.f7Period.getValue();
		param.add(PERIOD_ID, period == null ? null : period.getId().toString());

		return param.getCustomerParams();
	}

	public void onLoad() throws Exception {
		this.f7Period.setRequired(true);
		super.onLoad();
		Set periodIDSet = new HashSet();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select FPeriodID from t_she_salebalance where FOperateType = '"+ SaleBalanceTypeEnum.BALANCE_VALUE +"'");
		IRowSet countSet = builder.executeQuery();
		while (countSet.next()) {
			String periodID = countSet.getString("FPeriodID");
			if (periodID != null) {
				periodIDSet.add(periodID);
			}
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", periodIDSet, CompareType.INCLUDE));
		view.setFilter(filter);
		f7Period.setEntityViewInfo(view);
	}

	//设置过滤面板上的参数
	public void setCustomerParams(CustomerParams cp) {
		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);
		if (para.getString(PERIOD_ID) != null) {
			String perId = (String)para.getString(PERIOD_ID);
			PeriodInfo perInfo = null;
			try {
				perInfo = PeriodFactory.getRemoteInstance().getPeriodInfo(new ObjectUuidPK(BOSUuid.read(perId)));
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (UuidException e) {
				e.printStackTrace();
			}
			this.f7Period.setValue(perInfo);
		}
		super.setCustomerParams(para.getCustomerParams());
	}

	public boolean verify() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		PeriodInfo period = this.getPeriod(para);
		if(period == null){
			MsgBox.showWarning(this, "期间不能为空!");
			return false;
		}
		return true;
	}

}