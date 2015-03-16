package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Component;
import java.awt.Window;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.TemplateMeasureEntryCollection;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryInfo;
import com.kingdee.eas.util.client.ComponentUtil;
import com.kingdee.eas.util.client.ExceptionHandler;

public class TemplateMeasureCostPromptBox extends KDCommonPromptDialog {
	protected IUIWindow classDlg = null;
	
	protected IUIObject owner;
	private String orgId=null;
	private String productId=null;
	private String acctLongNumber=null;
	private String index=null;
	private String projectTypeId=null;
	private boolean isPrice=false;
	private boolean isAimMeasure=true;
	private String projectID = null;
	private ProductTypeInfo productTypeInfo = null;
	
	
	public TemplateMeasureCostPromptBox(IUIObject owner,boolean isAimMeasure,String orgId){
		this.owner=owner;
		this.orgId=orgId;
		this.isAimMeasure=isAimMeasure;
	}
	public TemplateMeasureCostPromptBox(IUIObject owner,boolean isAimMeasure,String orgId,String productId,String acctLongNumber,String index,String projectTypeId,boolean isPrice) {
		this.owner=owner;
		this.orgId=orgId;
		this.productId=productId;
		this.acctLongNumber=acctLongNumber;
		this.index=index;
		this.projectTypeId=projectTypeId;
		this.isPrice=isPrice;
		this.isAimMeasure=isAimMeasure;
		
	}
	
	public TemplateMeasureCostPromptBox(IUIObject owner, boolean isAimMeasure,String orgId, String project,ProductTypeInfo productTypeInfo) {
		this.owner=owner;
		this.orgId=orgId;
		this.isAimMeasure=isAimMeasure;
		this.projectID = project;
		this.productTypeInfo = productTypeInfo;
		
	}
	public void setProjectTypeID(String projectTypeId){
		this.projectTypeId=projectTypeId;
	}
	
	public void setIndex(String index){
		this.index=index;
	}
	public void setProductId(String productId){
		this.productId=productId;
	}
	public void setAcctLongNumber(String acctLongNumber){
		this.acctLongNumber=acctLongNumber;
	}
	public void setIsPrice(boolean isPrice){
		this.isPrice=isPrice;
	}
	public void setOrgId(String orgId){
		this.orgId=orgId;
	}
	
	public void setUITitle(boolean isPrice){
		if(isPrice){
			((TemplateMeasureCostF7UI)classDlg.getUIObject()).setUITitle("单价");
		}else{
			((TemplateMeasureCostF7UI)classDlg.getUIObject()).setUITitle("系数");
		}
		
	}
	public void show() {
		Map context = getUIContext();
		IUIFactory uiFactory = null;
		try {
			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			classDlg = uiFactory.create("com.kingdee.eas.fdc.aimcost.client.TemplateMeasureCostF7UI", context,null,null,WinStyle.SHOW_ALL);
			setUITitle(isPrice);
//			((Window)classDlg);
			((JDialog)classDlg).setResizable(false);
			classDlg.show();
		} catch (BOSException ex) {
			ExceptionHandler.handle(this, ex);
			return;
		}
	}
	
	/**
	 * 弹出选框关闭后，返回是否取消
	 * 
	 * @return
	 */
	public boolean isCanceled()
	{
		return ((TemplateMeasureCostF7UI)classDlg.getUIObject()).isCancel();
	}
	
	public Object getData(){
		Object object = ((TemplateMeasureCostF7UI)classDlg.getUIObject()).getData();
		if(object instanceof TemplateMeasureEntryCollection){
			TemplateMeasureEntryCollection obj=(TemplateMeasureEntryCollection)object;
			if(obj!=null&&obj.size()>0){
				BigDecimal objs[]=new BigDecimal[obj.size()];
				for(int i=0;i<obj.size();i++){
					if(isPrice){
						BigDecimal price = obj.get(i).getPrice();
						if(price!=null){
							objs[i]=price.setScale(2);
						}
					}else{
						BigDecimal coefficient = obj.get(i).getCoefficient();
						if(coefficient!=null){
							objs[i]=coefficient.setScale(2);
						}
					}
				}
				return objs;
			}else{
				return null;
			}
		} else {
			if(object!=null&&!object.getClass().isArray()){
				object = new Object[]{object};
			}
			return object;
		}
	}
	
	private HashMap getUIContext(){
		HashMap context=new HashMap();
		context.put("orgUnit.id", orgId);
		context.put("product.id", productId);
		context.put("costAccount.longNumber", acctLongNumber);
		context.put("index", index);
		context.put("projectType.id", projectTypeId);
		context.put("project", projectID);
		context.put("Owner", ComponentUtil.getRootComponent((Component) owner));
		context.put("myOwner", owner);
		context.put("isPrice", Boolean.valueOf(isPrice));
		context.put("isAimMeasure", Boolean.valueOf(isAimMeasure));
		context.put("productTypeInfo", productTypeInfo);
		return context;
	}
}
