/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.ProductDetialFactory;
import com.kingdee.eas.fdc.sellhouse.ProductDetialInfo;
import com.kingdee.eas.fdc.sellhouse.SellProject;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ProductDetailEditUI extends AbstractProductDetailEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProductDetailEditUI.class);
    
    private ItemAction[] hiddenAction = {this.actionCancel,this.actionCancelCancel,	this.actionSave,this.actionCopy,this.actionPrint
	,this.actionPrintPreview};
    /**
     * output class constructor
     */
    public ProductDetailEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        
        ProductDetialInfo pd = this.editData;
        //pd.setDescription(this.taDescription.getText());
        
        super.storeFields();
    }


	protected IObjectValue createNewData()
	{
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		ProductDetialInfo pd = new ProductDetialInfo();
		pd.setSellProject(sellProject);
		
		return pd;
	}
	
	protected void loadData() throws Exception
	{
		super.loadData();
		
		ProductDetialInfo pd = this.editData;
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		this.txtSellProjectName.setText(sellProject.getName());
		this.txtSellProjectNum.setText(sellProject.getNumber());
		//this.taDescription.setText(pd.getDescription());
		
		
		
	}
//	public SelectorItemCollection getSelectors()
//	{
//		SelectorItemCollection selItemColl =  super.getSelectors();
//		selItemColl.add("description");
//		
//		return selItemColl;
//	}
	public void onLoad() throws Exception
	{
		super.onLoad();
		
		FDCClientHelper.setActionVisible(this.hiddenAction,false);
		
		this.initOldData(this.editData);
		this.txtSellProjectNum.setMaxLength(80);
		this.txtSellProjectName.setMaxLength(80);
		this.txtNumber.setMaxLength(80);
		this.txtName.setMaxLength(80);
		this.taDescription.setMaxLength(80);
		
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return ProductDetialFactory.getRemoteInstance();
	}

}