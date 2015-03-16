/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FDCCustomerFullFilterUI extends AbstractFDCCustomerFullFilterUI
{
	private UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();

  
	protected ListUI listUI;
	
	protected ItemAction actionListOnLoad;
    /**
     * output class constructor
     */
    public FDCCustomerFullFilterUI() throws Exception
    {
        super();
    }

	public FDCCustomerFullFilterUI(ListUI listUI, ItemAction actionListOnLoad)throws Exception {
		super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
}
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
	public void onLoad() throws Exception {
		super.onLoad();
	}
    
	public void clear() {
		//this.contProject.setVisible(false);
		//this.f7Project.setData(null);
		//this.contCustomerType.setVisible(false);
		//this.boxCustomerType.setSelectedItem(null);

		this.chkAllCustomer.setSelected(true);
		this.dpBookedDate.setEnabled(!this.chkAllCustomer.isSelected());
		this.dpToBookedDate.setEnabled(!this.chkAllCustomer.isSelected());
		this.chkBlankOut.setSelected(false);
		
		this.comboCustomerType.setSelectedItem(CustomerTypeEnum.EnterpriceCustomer);
	}
	
	public FilterInfo getFilterInfo() {
		//FilterInfo filter = super.getFilterInfo();
		
//		SellProjectInfo sellProject = (SellProjectInfo)this.f7Project.getValue();
//		if(sellProject!=null)
//			filter.getFilterItems().add(new FilterItemInfo("project.id",sellProject.getId().toString()));
//		CustomerTypeEnum cusType = (CustomerTypeEnum)this.boxCustomerType.getSelectedItem();
//		if(cusType!=null)
//			filter.getFilterItems().add(new FilterItemInfo("customerType",cusType));
		
		FilterInfo filter = new FilterInfo(); 
		if(!this.chkAllCustomer.isSelected()) {
			Date beginDate = (Date)this.dpBookedDate.getValue();
			Date endDate = (Date)this.dpToBookedDate.getValue();
			filter.getFilterItems().add(new FilterItemInfo("createTime",beginDate,CompareType.GREATER));
			filter.getFilterItems().add(new FilterItemInfo("createTime",TenancyHelper.addCalendar(endDate, Calendar.DATE, 1),CompareType.LESS));
		}
		
		if(this.chkImportant.isSelected()) {
			filter.getFilterItems().add(new FilterItemInfo("isImportantTrack",Boolean.TRUE));
		}
		
		if(!this.chkBlankOut.isSelected())
			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		
		CustomerTypeEnum customerType = (CustomerTypeEnum) this.comboCustomerType.getSelectedItem();
		filter.getFilterItems().add(new FilterItemInfo("customerType", customerType.getValue()));
		
		return filter;
	}
	          
	public boolean verify() {
		Date beginDate = (Date)this.dpBookedDate.getValue();
		Date endDate = (Date)this.dpToBookedDate.getValue();
		
		if(!this.chkAllCustomer.isSelected()) {
			if(beginDate==null || endDate==null) {
				MsgBox.showWarning(this, "时间范围必须选定!");
				return false;
			}
			if(beginDate.after(endDate)){
				MsgBox.showWarning(this, "时间范围不合逻辑!");
				return false;
			}
			
		}
		
		return true;
	}
	public void setCustomerParams(CustomerParams cp) {
		if (cp == null)
			return;
		FDCCustomerParams para = new FDCCustomerParams(cp);
		if(para.getBoolean("chkShare"))
		{
			this.chkShare.setSelected(true);
		}
		super.setCustomerParams(para.getCustomerParams());
	}
	
	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();
		if(this.chkShare.isSelected())
		{
			param.add("chkShare",true);
		}
		return param.getCustomerParams();
	}

	protected void chkAllCustomer_actionPerformed(ActionEvent e) throws Exception {
		super.chkAllCustomer_actionPerformed(e);
		
		
		this.dpBookedDate.setEnabled(!this.chkAllCustomer.isSelected());
		this.dpToBookedDate.setEnabled(!this.chkAllCustomer.isSelected());
		
		
	}
    
	public CustomerTypeEnum getCustomerType(){
		return (CustomerTypeEnum) this.comboCustomerType.getSelectedItem();
	}
	
	
	
	

    
    

}