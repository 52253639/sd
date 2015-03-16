/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;

/**
 * output class name
 */
public class FDCBudgetAcctExecFilterUI extends AbstractFDCBudgetAcctExecFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCBudgetAcctExecFilterUI.class);
    
    /**
     * output class constructor
     */
    public FDCBudgetAcctExecFilterUI() throws Exception
    {
        super();
    }
    public void storeFields()
    {
        super.storeFields();
    }
    public void onLoad() throws Exception {
		SpinnerNumberModel model=new SpinnerNumberModel();
		model.setMinimum(new Integer(1900));
		model.setMaximum(new Integer(3000));
		model.setStepSize(new Integer(1));
		this.spYear.setModel(model);
		model=new SpinnerNumberModel();
		model.setMinimum(new Integer(1));
		model.setMaximum(new Integer(12));
		model.setStepSize(new Integer(1));
		this.spMonth.setModel(model);
		this.spMonth.setRequired(true);
		this.spMonth.setEditable(true);
		this.spMonth.setEnabled(true);
		this.spYear.setRequired(true);
		this.spYear.setEditable(true);
		this.spYear.setEnabled(true);
		this.prmtProject.setRequired(true);
		EntityViewInfo view=prmtProject.getEntityViewInfo();
		if(view==null){
			view=new EntityViewInfo();
		}
		if(view.getFilter()==null){
			view.setFilter(new FilterInfo());
		}
		FilterInfo filter=new FilterInfo();
		String  ctrlUnitId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		if(! ctrlUnitId.equals(OrgConstants.DEF_CU_ID)){//当在集团下时可查看所有
			filter.appendFilterItem("CU.id", ctrlUnitId);
		}
//		filter.appendFilterItem("CU.id", SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
		view.getFilter().mergeFilter(filter, "and");
		prmtProject.setEntityViewInfo(view);
    	super.onLoad();
    	clear();
    }
    public void clear() {
    	super.clear();
    	FDCBudgetPeriodInfo period=FDCBudgetPeriodInfo.getCurrentPeriod(false);
    	this.spYear.setValue(new Integer(period.getYear()));
    	this.spMonth.setValue(new Integer(period.getMonth()));
    }
    public FilterInfo getFilterInfo() {
    	FDCCustomerParams fdc=new FDCCustomerParams(getCustomerParams());
//    	FilterInfo filter=new FilterInfo();
    	FilterInfo filter = super.getFilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id",fdc.get("prjId")));
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id",fdc.get("prjId")));
    	filter.getFilterItems().add(new FilterItemInfo("fdcPeriod.year",fdc.getInteger("year")));
    	filter.getFilterItems().add(new FilterItemInfo("fdcPeriod.month",fdc.getInteger("month")));

//    	return super.getFilterInfo();
    	return filter;
    }
    public boolean verify() {
    	FDCClientVerifyHelper.verifyRequire(this);
    	return super.verify();
    }
    public void setCustomerParams(CustomerParams cp) {
    	FDCCustomerParams fdc=new FDCCustomerParams(cp);
    	String prjId=fdc.get("prjId");
    	int year=fdc.getInt("year");
    	int month=fdc.getInt("month");
		try {
			CurProjectInfo info = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(prjId));
			prmtProject.setValue(info);
			this.spYear.setValue(new Integer(year));
			this.spMonth.setValue(new Integer(month));
		} catch (Exception e) {
			handUIException(e);
		}
    	
    	super.setCustomerParams(cp);
    }
    public CustomerParams getCustomerParams() {
    	FDCCustomerParams fdc=new FDCCustomerParams();
    	fdc.add("prjId", FDCHelper.getF7Id(prmtProject));
    	fdc.add("year", (Integer)this.spYear.getValue());
    	fdc.add("month", (Integer)this.spMonth.getValue());
    	return fdc.getCustomerParams();
    }

}