/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.sellhouse.RoomModelCollection;
import com.kingdee.eas.fdc.sellhouse.RoomModelFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataCollection;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataFactory;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class CommerceChanceFilterUI extends AbstractCommerceChanceFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(CommerceChanceFilterUI.class);
    public CommerceChanceFilterUI() throws Exception
    {
        super();
    }
    public void onLoad()throws Exception
    {
    	super.onLoad();
    	prmtStayArea.setValue(null);
    	prmtWorkArea.setValue(null);
    	prmtIntentionType.setValue(null);
    	this.pkCreateTimeFrom.setValue(null);
    	this.pkCreateTimeTo.setValue(null);
    	
    	String psql=NewCommerceHelper.getPermitProjectIdSql(null);
    	FilterInfo filter = new FilterInfo();
		EntityViewInfo evi = new EntityViewInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("group.name", "居住区域"));
		filter.getFilterItems().add(new FilterItemInfo("project.id",psql ,CompareType.INNER));
		filter.getFilterItems().add(new FilterItemInfo("project.id",null));
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", SysContext.getSysContext().getCurrentOrgUnit().getId().toString(),CompareType.EQUALS));
		
		filter.setMaskString("#0 and #1 and (#2 or (#3 and #4) )");
		
		evi.setFilter(filter);
		prmtStayArea.setEntityViewInfo(evi);
		prmtStayArea.setEnabledMultiSelection(true);
		prmtStayArea.setDisplayFormat("$name$");
		prmtStayArea.setEditFormat("$number$");
		prmtStayArea.setCommitFormat("$number$");
		
		evi = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("group.name", "工作区域"));
		filter.getFilterItems().add(new FilterItemInfo("project.id", psql,CompareType.INNER));
		filter.getFilterItems().add(new FilterItemInfo("project.id",null));
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", SysContext.getSysContext().getCurrentOrgUnit().getId().toString(),CompareType.EQUALS));
		
		filter.setMaskString("#0 and #1 and (#2 or (#3 and #4) )");
		evi.setFilter(filter);
		prmtWorkArea.setEntityViewInfo(evi);
		prmtWorkArea.setEnabledMultiSelection(true);
		prmtWorkArea.setDisplayFormat("$name$");
		prmtWorkArea.setEditFormat("$number$");
		prmtWorkArea.setCommitFormat("$number$");
		
		evi = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", psql,CompareType.INNER));
		evi.setFilter(filter);
		filter.setMaskString("#0");
		prmtIntentionType.setEntityViewInfo(evi);
		prmtIntentionType.setEnabledMultiSelection(true);
		prmtIntentionType.setDisplayFormat("$name$");
		prmtIntentionType.setEditFormat("$number$");
		prmtIntentionType.setCommitFormat("$number$");
    }

	public FilterInfo getFilterInfo()
	{
	    FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
	    FilterInfo filter = new FilterInfo();
	    String stayAreaString=para.getString("stayArea");
	    String workAreaString=para.getString("workArea");
	    String intentionTypeString=para.getString("intentionType");
	    int i=0;
	    if(stayAreaString != null){
	    	filter.getFilterItems().add(new FilterItemInfo("stayArea.id", stayAreaString, CompareType.INNER));
	    	i=i+1;
	    }
	    if(workAreaString != null){
	    	filter.getFilterItems().add(new FilterItemInfo("workArea.id", workAreaString, CompareType.INNER));
	    	i=i+1;
	    }
	    if(intentionTypeString != null){
	    	filter.getFilterItems().add(new FilterItemInfo("intentionType.id", intentionTypeString, CompareType.INNER));
	    	i=i+1;
	    }
	    if(i==2){
	    	filter.setMaskString("#0 or #1 ");
	    }else if(i==3){
	    	filter.setMaskString("#0 or #1 or #2");
	    }
	    FilterInfo timefilter = new FilterInfo();
	    if(this.pkCreateTimeTo.getValue()!=null||this.pkCreateTimeFrom.getValue()!=null){
	 	    getDateFilter(timefilter,this.pkCreateTimeTo,"createTime",CompareType.LESS_EQUALS);
	 		getDateFilter(timefilter,this.pkCreateTimeFrom,"createTime",CompareType.GREATER_EQUALS);
	    }
	    if(this.txtCustomer.getText()!=null&&!this.txtCustomer.getText().trim().equals("")){
	    	timefilter.getFilterItems().add(new FilterItemInfo("customer.name","%"+this.txtCustomer.getText().trim()+"%",CompareType.LIKE));
	    }
	    if(this.txtPhone.getText()!=null&&!this.txtPhone.getText().equals("")){
	    	timefilter.getFilterItems().add(new FilterItemInfo("phone","%"+this.txtPhone.getText().trim()+"%",CompareType.LIKE));
	    }
	    try {
 			filter.mergeFilter(timefilter, "and");
 		} catch (BOSException e) {
 			e.printStackTrace();
 		} 
	    return filter;
	}
	public static FilterInfo getDateFilter(FilterInfo filter, KDDatePicker pkEndBizDate, String filterItem, CompareType compareType) {
	    Date applyDate = (Date) pkEndBizDate.getValue();
	    if (applyDate != null ) {
		    Calendar calendar=null; 
		    calendar = Calendar.getInstance();
		    calendar.setTime(applyDate);
		    if(CompareType.GREATER_EQUALS.equals(compareType))
		    	calendar.add(Calendar.DATE, -1);
		    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),23, 59, 59);
		    filter.getFilterItems().add(new FilterItemInfo(filterItem,calendar.getTime(),compareType));
	    }
	    return filter;
    }
	public CustomerParams getCustomerParams()
	{
	    FDCCustomerParams param = new FDCCustomerParams();
	    if(prmtStayArea.getValue() != null){
	    	Object[] stayArea = (Object[])prmtStayArea.getValue();
	    	String stayAreaString="";
	    	for(int i=0;i<stayArea.length;i++){
	    		if(stayArea[i]==null) continue;
	    		if(i==0)
	    			stayAreaString+="'"+((SHECusAssistantDataInfo)stayArea[i]).getId().toString()+"'";
	    		else
	    			stayAreaString+=",'"+((SHECusAssistantDataInfo)stayArea[i]).getId().toString()+"'";
	    	}
	    	if(!stayAreaString.equals("")) param.add("stayArea", stayAreaString);
	    }
	    if(prmtWorkArea.getValue() != null){
	    	Object[] workArea = (Object[])prmtWorkArea.getValue();
	    	String workAreaString="";
	    	for(int i=0;i<workArea.length;i++){
	    		if(workArea[i]==null) continue;
	    		if(i==0)
	    			workAreaString+="'"+((SHECusAssistantDataInfo)workArea[i]).getId().toString()+"'";
	    		else
	    			workAreaString+=",'"+((SHECusAssistantDataInfo)workArea[i]).getId().toString()+"'";
	    	}
	    	if(!workAreaString.equals("")) param.add("workArea", workAreaString);
	    }
	    if(prmtIntentionType.getValue() != null){
	    	Object[] intentionType = (Object[])prmtIntentionType.getValue();
	    	String intentionTypeString="";
	    	for(int i=0;i<intentionType.length;i++){
	    		if(intentionType[i]==null) continue;
	    		if(i==0)
	    			intentionTypeString+="'"+((RoomModelInfo)intentionType[i]).getId().toString()+"'";
	    		else
	    			intentionTypeString+=",'"+((RoomModelInfo)intentionType[i]).getId().toString()+"'";
	    	}
	    	if(!intentionTypeString.equals("")) param.add("intentionType", intentionTypeString);
	    }
	    if(this.pkCreateTimeFrom.getValue()!=null){
	    	param.add("createTimeFrom",(Date)this.pkCreateTimeFrom.getValue());
	    }
	    if(this.pkCreateTimeTo.getValue()!=null){
	    	param.add("createTimeTo", (Date)this.pkCreateTimeTo.getValue());
	    }
	    if(this.txtCustomer.getText()!=null){
	    	param.add("customer", this.txtCustomer.getText().trim());
	    }
	    if(this.txtPhone.getText()!=null){
	    	param.add("phone", this.txtPhone.getText().trim());
	    }
	    return param.getCustomerParams();
	}
	
	public void setCustomerParams(CustomerParams cp)
	{
	    if(cp == null)
	        return;
	    FDCCustomerParams para = new FDCCustomerParams(cp);
	    String stayAreaString=para.getString("stayArea");
	    String workAreaString=para.getString("workArea");
	    String intentionTypeString=para.getString("intentionType");
	    if(stayAreaString != null){
			try {
				SHECusAssistantDataCollection col = SHECusAssistantDataFactory.getRemoteInstance().getSHECusAssistantDataCollection("select * from where id in("+stayAreaString+")");
				prmtStayArea.setValue(col.toArray());
			} catch (BOSException e) {
				e.printStackTrace();
			}
	    }
	    if(stayAreaString != null){
			try {
				SHECusAssistantDataCollection col = SHECusAssistantDataFactory.getRemoteInstance().getSHECusAssistantDataCollection("select * from where id in("+workAreaString+")");
				prmtWorkArea.setValue(col.toArray());
			} catch (BOSException e) {
				e.printStackTrace();
			}
	    }
	    if(stayAreaString != null){
			try {
				RoomModelCollection col = RoomModelFactory.getRemoteInstance().getRoomModelCollection("select * from where id in("+intentionTypeString+")");
				prmtIntentionType.setValue(col.toArray());
			} catch (BOSException e) {
				e.printStackTrace();
			}
	    }
	    if(para.get("createTimeFrom")!=null){
	    	this.pkCreateTimeFrom.setValue(para.getDate("createTimeFrom"));
	    }
	    if(para.get("createTimeTo")!=null){
	    	this.pkCreateTimeTo.setValue(para.getDate("createTimeTo"));
	    }
	    if(para.get("customer")!=null){
	    	this.txtCustomer.setText(para.getString("customer"));
	    }
	    if(para.get("phone")!=null){
	    	this.txtPhone.setText(para.getString("phone"));
	    }
	    super.setCustomerParams(para.getCustomerParams());
	}
}