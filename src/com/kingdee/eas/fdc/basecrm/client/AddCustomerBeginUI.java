/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CustomerTypeEnum;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureCollection;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureFactory;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerLinkManCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerLinkManFactory;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AddCustomerBeginUI extends AbstractAddCustomerBeginUI
{
    private static final Logger logger = CoreUIObject.getLogger(AddCustomerBeginUI.class);
    
    /**
     * output class constructor
     */
    public AddCustomerBeginUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.comboCusType.addItems(CustomerTypeEnum.getEnumList().toArray());
    	this.comboCusType.setRequired(true);
    	this.txtCusName.setRequired(true);
    	this.txtPhone.setRequired(true);
    }
    
    
    protected void btnOK_actionPerformed(ActionEvent e) throws Exception {
    	if(this.txtCusName.getText() == null  ||  this.txtCusName.getText().trim().equals("")){
    		MsgBox.showInfo(this, "客户姓名必须输入!");
    		this.abort();
    	}
    	
    	if(this.txtPhone.getText() == null  ||  this.txtPhone.getText().trim().length() < 7){
    		MsgBox.showInfo(this, "联系电话必须输入,且至少需要输入7位以上!");
    		this.abort();
    	}
    	//设置为导向增加客户,可以不用填问卷
    	if(this.getUIContext().get("addnewDerict") != null){
    		this.getUIContext().put("addnewDerict", this.getUIContext().get("addnewDerict")); 
    	}
    	else{
        	this.getUIContext().put("addnewDerict", "derict");    		
    	}
    	
    	this.getUIContext().put("customerType", this.comboCusType.getSelectedItem());
    	this.getUIContext().put("cusName", this.txtCusName.getText().trim());
    	this.getUIContext().put("cusPhone", this.txtPhone.getText().trim());
    	this.getUIContext().put("cusCertificateNum", this.txtCertificateNum.getText().trim());
    	
    	String isSHE = "";
    	if(this.getUIContext().get("isSHE") != null && !this.getUIContext().get("isSHE").equals("")){
    		isSHE =  this.getUIContext().get("isSHE").toString();
    	}
    	CusClientHelper.existCodingRule(isSHE);
    	//existCodingRule(isSHE);
    	
    	this.getUIContext().put("isContinue", Boolean.TRUE);
    	
    	EntityViewInfo view=new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
    	filter.getFilterItems().add(new FilterItemInfo("phone", this.txtPhone.getText().trim()));
    	filter.getFilterItems().add(new FilterItemInfo("tel", this.txtPhone.getText().trim()));
    	filter.getFilterItems().add(new FilterItemInfo("officeTel", this.txtPhone.getText().trim()));
    	filter.getFilterItems().add(new FilterItemInfo("fax", this.txtPhone.getText().trim()));
    	filter.setMaskString("#0 and (#1 OR #2 OR #3 OR #4)");
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("sellProject.name");
		sic.add("name");
		sic.add("propertyConsultant.name");
		view.setSelector(sic);
		view.setFilter(filter);
    	SHECustomerCollection col=SHECustomerFactory.getRemoteInstance().getSHECustomerCollection(view);
    	String warning="";
    	for(int i=0;i<col.size();i++){
    		warning=warning+"项目："+col.get(i).getSellProject().getName()+"；客户名称："+col.get(i).getName()+"；置业顾问："+col.get(i).getPropertyConsultant().getName()+"；\n";
    	}
    	if(col.size()>0){
    		FDCMsgBox.showDetailAndOK(this, "存在相同联系电话客户", warning, MsgBox.NO);
    	}
    	EntityViewInfo linkview=new EntityViewInfo();
    	FilterInfo linkfilter = new FilterInfo();
    	linkfilter.getFilterItems().add(new FilterItemInfo("sheCustomer.isEnabled", Boolean.TRUE));
    	linkfilter.getFilterItems().add(new FilterItemInfo("phone", this.txtPhone.getText().trim()));
    	linkfilter.getFilterItems().add(new FilterItemInfo("tel", this.txtPhone.getText().trim()));
    	linkfilter.getFilterItems().add(new FilterItemInfo("officeTel", this.txtPhone.getText().trim()));
    	linkfilter.getFilterItems().add(new FilterItemInfo("fax", this.txtPhone.getText().trim()));
    	linkfilter.setMaskString("#0 and (#1 OR #2 OR #3 OR #4)");
		SelectorItemCollection linksic=new SelectorItemCollection();
		linksic.add("sheCustomer.sellproject.name");
		linksic.add("sheCustomer.name");
		linksic.add("sheCustomer.propertyConsultant.name");
		linkview.setSelector(linksic);
		linkview.setFilter(linkfilter);
    	SHECustomerLinkManCollection linkcol=SHECustomerLinkManFactory.getRemoteInstance().getSHECustomerLinkManCollection(linkview);
    	String linkwarning="";
    	for(int i=0;i<linkcol.size();i++){
    		linkwarning=linkwarning+"项目："+linkcol.get(i).getSheCustomer().getSellProject().getName()+"；客户名称："+linkcol.get(i).getSheCustomer().getName()+"；置业顾问："+linkcol.get(i).getSheCustomer().getPropertyConsultant().getName()+"；\n";
    	}
    	if(linkcol.size()>0){
    		FDCMsgBox.showDetailAndOK(this, "存在相同联系电话联系人", linkwarning, MsgBox.NO);
    	}
    	destroyWindow();
    }

    protected void btnCancel_actionPerformed(ActionEvent e) throws Exception {
    	this.getUIContext().put("isContinue", Boolean.FALSE);
    	
    	destroyWindow();
    }
}