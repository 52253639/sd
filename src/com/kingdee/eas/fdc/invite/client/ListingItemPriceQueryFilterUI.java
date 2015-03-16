/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IFormatter;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IFullOrgUnit;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.invite.HeadTypeFactory;
import com.kingdee.eas.fdc.invite.HeadTypeInfo;
import com.kingdee.eas.fdc.invite.IHeadType;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class ListingItemPriceQueryFilterUI extends AbstractListingItemPriceQueryFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ListingItemPriceQueryFilterUI.class);
    
    public static final String COMPANY_IDS = "companyids";
    public static final String HEADTYPE_ID = "headtypeid";
    public static final String ITEMNAME_KEY = "itemnamekey";
    public static final String ISHEADTYPENULL = "isheadtypenull";
    
    public static final String DATE_FROM = "datefrom";
    public static final String DATE_TO = "dateto";
    
//  是否使用成本月结,当前财务组织的期间
	private Date[] pkdate ;
    
    /**
     * output class constructor
     */
    public ListingItemPriceQueryFilterUI() throws Exception
    {
        super();
        pkDateFrom.setRequired(true);
		pkDateTo.setRequired(true);
		
		pkdate = FDCClientHelper.getCompanyCurrentDate(false);
    }
    public void onLoad() throws Exception{
    	initControl();
    	this.f7HeadType.requestFocus();
    }
    public boolean verify() {
		HeadTypeInfo headType = (HeadTypeInfo)f7HeadType.getValue();
		if (headType == null && StringUtils.isEmpty(this.kDTextFieldItemName.getText())) {
			MsgBox.showInfo(this,"当表头类型不选时，请录入名称的关键字过滤.");
			this.kDTextFieldItemName.requestFocus();
			return false;
		}
		if (this.f7Org.getValue() == null){
			MsgBox.showInfo(this,"必须选择组织.");
			this.f7Org.requestFocus();
			return false;
		}
		if (this.pkDateFrom.getValue() == null){
			MsgBox.showInfo(this,"开始日期必选.");
			this.pkDateFrom.requestFocus();
			return false;
		}
		if (this.pkDateTo.getValue() == null){
			MsgBox.showInfo(this,"结束日期必选.");
			this.pkDateTo.requestFocus();
			return false;
		}
		if (((Date)this.pkDateTo.getValue()).before((Date)this.pkDateFrom.getValue())){
			MsgBox.showInfo(this,"结束日期不能小于开始日期.");
			this.pkDateTo.requestFocus();
			return false;
		}
		return true;
	}
    static class newFormatter implements IFormatter{
			public String valueToString(Object o){
			String temp = o.toString();
				int start = temp.indexOf('[');
				int end = temp.indexOf(']');
				return start == 0 && end > 1 ? temp.substring(start + 1, end)
						: temp;
			};
			public void applyPattern(String pattern){};
    }
    private newFormatter myFormatter = new newFormatter();
    private void initControl() {
    	this.kDTextFieldItemName.setRequired(true);
		f7Org.setDisplayFormat("$name$");
		f7Org.setCommitFormat("$name$");
		f7Org.setEditFormat("$name$");
		f7Org.setRequired(true);
		f7Org.setEnabledMultiSelection(true);
		f7Org.setSelector(new CompanyTreeSelectF7(this));
		//设置格式，去掉多选择后，留下的中括号
		f7Org.setDisplayFormatter(myFormatter);
		f7Org.setEditFormatter(myFormatter);
		f7HeadType.setDisplayFormat("$name$");
		f7HeadType.setCommitFormat("$number$");
		f7HeadType.setEditFormat("$number$");
		f7HeadType.setRequired(true);
		this.pkDateFrom.setValue(pkdate[0]);
		this.pkDateTo.setValue(pkdate[1]);
		Vector v = new Vector();
    	FullOrgUnitInfo compInfo = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		if (compInfo != null) {
			v.add(compInfo);
			f7Org.setValue(v);
		}
		this.kDCheckBoxHeadTypeNull.setSelected(false);
		this.kDTextFieldItemName.setRequired(false);
		
	}
    public void clear(){
    	this.f7HeadType.setValue(null);
    	Vector v = new Vector();
    	FullOrgUnitInfo compInfo = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		if (compInfo != null) {
			v.add(compInfo);
			f7Org.setValue(v);
		}
    	this.kDTextFieldItemName.setText("");
    	this.pkDateFrom.setValue(pkdate[0]);
		this.pkDateTo.setValue(pkdate[1]);
		this.f7HeadType.setRequired(true);
		this.f7Org.setRequired(true);
		this.kDCheckBoxHeadTypeNull.setSelected(false);
		this.kDTextFieldItemName.setRequired(false);
    }
    
    public CustomerParams getCustomerParams(){
    	FDCCustomerParams param = new FDCCustomerParams();
    	Vector orgVector = (Vector)f7Org.getValue();
    	
    	String[] companyIds = new String[orgVector.size()] ;
    	for(int i=0;i<orgVector.size();i++){
    		FullOrgUnitInfo company = (FullOrgUnitInfo) orgVector.elementAt(i);
    		companyIds[i] = company.getId().toString();
    	}
    	param.add(COMPANY_IDS, companyIds);
    	
    	HeadTypeInfo headtype = (HeadTypeInfo)this.f7HeadType.getValue();
    	if(headtype != null)
    		param.add(HEADTYPE_ID,headtype.getId().toString());
    	else
    		param.add(HEADTYPE_ID,"");
    	
    	param.add(ITEMNAME_KEY,this.kDTextFieldItemName.getText());
    	
    	param.add(DATE_FROM,(Date)this.pkDateFrom.getValue());
    	param.add(DATE_TO,(Date)this.pkDateTo.getValue());
    	param.add(ISHEADTYPENULL,this.kDCheckBoxHeadTypeNull.isSelected());
    	
    	return param.getCustomerParams();
    }
    
    
    public void setCustomerParams(CustomerParams  cp){
    	if(cp == null) return;
    	
    	FDCCustomerParams para = new FDCCustomerParams(cp);
    	
    	try {
    		String[] companyIds = para.getStringArray(COMPANY_IDS);
        	HashSet idSet = new HashSet();
	    	if(companyIds != null){
	    		for(int i=0;i<companyIds.length;i++){
	    			idSet.add(companyIds[i]);
	    		}
	    		EntityViewInfo orgView = new EntityViewInfo();
	    		FilterInfo orgFilter = new FilterInfo();
	    		orgFilter.getFilterItems().add(
	    				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
	    		orgView.setFilter(orgFilter);
	    		IFullOrgUnit iComp;
				
					iComp = FullOrgUnitFactory.getRemoteInstance();
					FullOrgUnitCollection Comps = iComp
					.getFullOrgUnitCollection(orgView);
					Vector v = new Vector();
					
					for(int i=0;i<Comps.size();i++){
						v.add(Comps.get(i));
					}
					
					f7Org.setValue(v);
	    	}
	    	String headTypeId = para.getString(HEADTYPE_ID);
	    	if(!headTypeId.equals(""))
			{
	    		HeadTypeInfo info = HeadTypeFactory.getRemoteInstance().getHeadTypeInfo("select * where id='"+headTypeId+"'");
	    		this.f7HeadType.setValue(info);
			}
	    	else{
	    		this.f7HeadType.setValue(null);
	    	}
			
			
			this.kDTextFieldItemName.setText(para.getString(ITEMNAME_KEY));
			this.pkDateFrom.setValue(para.getDate(DATE_FROM));
			this.pkDateTo.setValue(para.getDate(DATE_TO));
			this.kDCheckBoxHeadTypeNull.setSelected(para.getBoolean(ISHEADTYPENULL));
			kDCheckBox1_actionPerformed(null);
			
			
    	} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
    }
    public void f7HeadType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e){
        //write your code here
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    public Set getOrgIdSet(){
    	Set idSet = new HashSet();
    	if(this.f7Org.getValue()!=null){
    		Vector orgVector = (Vector)this.f7Org.getValue();
        	Object[] orgs = orgVector.toArray();
        	for (int i = 0; i < orgs.length; i++) {
    			FullOrgUnitInfo company = (FullOrgUnitInfo) orgs[i];
    			idSet.add(company.getId().toString());
    		}
    	}
		return idSet;
    }
    
    public FilterInfo getFilterInfo()
	{
    	filterInfo = new FilterInfo();
    	
    	HeadTypeInfo headType = (HeadTypeInfo) this.f7HeadType.getValue();
		
		
		filterInfo.getFilterItems().add(
				new FilterItemInfo("head.item.orgUnit.id", getOrgIdSet(),
						CompareType.INCLUDE));
		if(headType != null){
			filterInfo.getFilterItems().add(
					new FilterItemInfo("head.item.headType.id", headType.getId()));
		}
		filterInfo.getFilterItems().add(
				new FilterItemInfo("head.date", DateTimeUtils
						.truncateDate((Date) this.pkDateFrom.getValue()),
						CompareType.GREATER_EQUALS));
		Date endDate = (Date) this.pkDateTo.getValue();
		Calendar cal = new GregorianCalendar();
		cal.setTime(endDate);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
		filterInfo.getFilterItems().add(
				new FilterItemInfo("head.date", DateTimeUtils.truncateDate(cal
						.getTime()), CompareType.LESS));
		String itemName = this.kDTextFieldItemName.getText();
		if (!StringUtils.isEmpty(itemName)) {
			filterInfo.getFilterItems().add(
					new FilterItemInfo("head.item.name", "%" + itemName + "%",
							CompareType.LIKE));
		}
		return this.filterInfo;
	}
    
    /**
     * output kDCheckBox1_actionPerformed method
     */
    protected void kDCheckBox1_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	
		if(this.kDCheckBoxHeadTypeNull.isSelected()){
			
			this.f7HeadType.setRequired(false);
	    	this.f7HeadType.setValue(null);
			this.kDTextFieldItemName.setRequired(true);
			this.kDTextFieldItemName.requestFocus();
		}
		else{
			this.f7HeadType.setRequired(true);	    	
			this.kDTextFieldItemName.setRequired(false);
			//控件设置了非必选以后,颜色没有改变
			//需要获取一下焦点,才有作用,变态的问题
			this.kDTextFieldItemName.requestFocus();
			this.f7HeadType.requestFocus();
		}
		
    }

}