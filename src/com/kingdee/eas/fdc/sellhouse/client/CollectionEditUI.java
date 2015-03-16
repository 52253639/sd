/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.bo.BusinessObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.service.formula.api.IVarInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.forewarn.ForewarnMessageInfo;
import com.kingdee.eas.base.forewarn.ForewarnSource;
import com.kingdee.eas.base.forewarn.MetadataType;
import com.kingdee.eas.base.forewarn.client.ForewarnUtil;
import com.kingdee.eas.base.forewarn.client.FormulaDialog;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.CRMFormulaDialog;
import com.kingdee.eas.fdc.basecrm.client.FormulaHelper;
import com.kingdee.eas.fdc.basecrm.client.FormulaVarInfo;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.sellhouse.CalculateTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CollectionCollection;
import com.kingdee.eas.fdc.sellhouse.CollectionFactory;
import com.kingdee.eas.fdc.sellhouse.CollectionInfo;
import com.kingdee.eas.fdc.sellhouse.FactorEnum;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.HoldEnum;
import com.kingdee.eas.fdc.sellhouse.IntegerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.OperateEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.formula.CollectionUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CollectionEditUI extends AbstractCollectionEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(CollectionEditUI.class);
    
    /**
     * output class constructor
     */
    public CollectionEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.actionCopy.setVisible(true);
    	
    	if(OprtState.ADDNEW.equals(this.getOprtState()))
		{
    		this.comboCalculateType.setSelectedItem(CalculateTypeEnum.GENERAL);
        	this.comboIntegerType.setSelectedItem(IntegerTypeEnum.ROUND);
        	this.comboFactor.setSelectedItem(FactorEnum.DEALTOTALALMOUNT);
        	this.comboOperate.setSelectedItem(OperateEnum.MULTIPLY);
        	this.comboHold.setSelectedItem(HoldEnum.YUAN);
        	this.txtFixedAmount.setText("0.00");
        	this.txtComparaValue.setText("0.00");
        	this.setUITitle("代收费用设置-新增");
        	this.btnScript.setEnabled(true);
    		this.btnClear.setEnabled(true);
		}
    	if(OprtState.EDIT.equals(this.getOprtState())){
    		this.setUITitle("代收费用设置-修改");
    		this.btnScript.setEnabled(true);
    		this.btnClear.setEnabled(true);
    	}
    	if(OprtState.VIEW.equals(this.getOprtState())){
    		this.setUITitle("代收费用设置-查看");
    		this.btnScript.setEnabled(false);
    		this.btnClear.setEnabled(false);
    	}
    	this.txtDescription.setMaxLength(255);
    	this.txtComparaValue.setPrecision(4);
    	this.txtComparaValue.setRequired(true);
		this.txtFixedAmount.setMaximumNumber(new BigDecimal("9999999999999"));
		
		this.prmtProject.setEntityViewInfo(NewCommerceHelper.getPermitProjectView());
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
    	 super.storeFields();
    }
    
   		
    public void actionEdit_actionPerformed(java.awt.event.ActionEvent e) throws Exception{
    	if (this.editData.getId() != null) {
			if (CollectionFactory.getRemoteInstance().exists(
					"where isEnabled =1 and id='"
							+ this.editData.getId().toString() + "'")) {
				MsgBox.showInfo("该条记录已启用，不允许修改！");
				return;
			}
//				FilterInfo filter = new FilterInfo();
//				filter.getFilterItems().add(new FilterItemInfo("sheCollection", this.editData.getId().toString()));
//				if (PurchaseElsePayListEntryFactory.getRemoteInstance().exists(filter)) {
//					MsgBox.showInfo("该记录已经使用,不能修改!");
//					return;
//				}
		}
    	super.actionEdit_actionPerformed(e);
    	this.setUITitle("代收费用设置-修改");
    	this.btnScript.setEnabled(true);
		this.btnClear.setEnabled(true);
  }
    
    public void btnScript_actionPerformed(java.awt.event.ActionEvent e) {

    	CRMFormulaDialog formulaDialog = 
			new CRMFormulaDialog((KDDialog)this.getUIWindow(),
				EASResource.getString(ForewarnSource.FOREWARN_SOURCE_NAME,"formulaSysTitle"),true);
                
    	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    	formulaDialog.setSize((int)(d.width*0.8), (int)(d.height*0.8));
    	
		FormulaVarInfo[] varInfos = new FormulaVarInfo[2]; 
		varInfos[0] = new FormulaVarInfo("房间","",MetadataType.ENTITY,"Room","com.kingdee.eas.fdc.sellhouse.app.Room"); 
		//varInfos[1] = new FormulaVarInfo("认购单","",MetadataType.ENTITY,"PurchaseManager","com.kingdee.eas.fdc.sellhouse.app.PurchaseManage");
		varInfos[1] = new FormulaVarInfo("成交总价","",null,"DealTotalAmount","java.math.BigDecimal"); 
		
		String formulaText = this.txtsStdFormulaScript.getText();
		formulaText = FormulaHelper.processObjectScript(varInfos,formulaText);		
		formulaDialog.setFormula(formulaText);     
		
		BusinessObjectInfo[] bisObjs = new BusinessObjectInfo[1];
		bisObjs[0] = MetaDataLoaderFactory.getRemoteMetaDataLoader().getEntity(new MetaDataPK(varInfos[0].getType()));
		//bisObjs[1] = MetaDataLoaderFactory.getRemoteMetaDataLoader().getEntity(new MetaDataPK(varInfos[1].getType()));
		formulaDialog.setMetaDataObjs(bisObjs);

		formulaDialog.getAddButton().setVisible(false);
		formulaDialog.getDelButton().setVisible(false);
		formulaDialog.show();
		if(formulaDialog.isConfirm())	{
			String formula = formulaDialog.getFormula();
			String formulaAlias = formulaDialog.getFormulaAliasNoResultStr();
			//IVarInfo[] varInfos = formulaDialog.getVarInfos(formulaDialog.getFormula());
			
    		this.txtFormulaDescription.setText(formulaAlias);
    		this.txtsStdFormulaScript.setText(formula);
		}
    	
	}

    public void actionClear_actionPerformed(ActionEvent e) throws Exception
    {
    	this.txtFormulaDescription.setText("");
    }

	
	protected IObjectValue createNewData() {
		CollectionInfo objectValue = new CollectionInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
				.get("sellProject");
		objectValue.setProject(sellProject);
		objectValue.setIsEnabled(false);
		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
//		objectValue.setFixedAmount(FDCHelper.ZERO);
		return objectValue;
//		return new CollectionInfo();
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("CU.*");
		return sic;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		
		return CollectionFactory.getRemoteInstance();
	}
	
	public void comboCalculateType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception{
		super.comboCalculateType_itemStateChanged(e);
		CalculateTypeEnum calculateType = (CalculateTypeEnum)this.comboCalculateType.getSelectedItem();
	      if(CalculateTypeEnum.GENERAL.equals(calculateType)){
	    	  this.contFixedAmount.setVisible(false);
	    	  this.contHold.setVisible(true);
	    	  this.contIntegerType.setVisible(true);
	    	  this.contFormulaDescription.setVisible(false);
	    	  this.btnClear.setVisible(false);
	    	  this.btnScript.setVisible(false);
	    	  this.kDContainer1.setVisible(true);
	    	  
	      }else if(CalculateTypeEnum.PARMAMNENT.equals(calculateType)){
	    	  this.contFixedAmount.setVisible(true);
	    	  this.contHold.setVisible(false);
	    	  this.contIntegerType.setVisible(false);
	    	  this.contFormulaDescription.setVisible(false);
	    	  this.btnClear.setVisible(false);
	    	  this.btnScript.setVisible(false);
	    	  this.kDContainer1.setVisible(false);
	      }else if(CalculateTypeEnum.PATULOUS.equals(calculateType)){
	    	  this.contFixedAmount.setVisible(false);
	    	  this.contHold.setVisible(true);
	    	  this.contIntegerType.setVisible(true);
	    	  this.contFormulaDescription.setVisible(true);
	    	  this.btnClear.setVisible(true);
	    	  this.btnScript.setVisible(true);
	    	  this.kDContainer1.setVisible(false);
	    	  this.btnClear.setEnabled(true);
	    	  this.btnScript.setEnabled(true);
	      }
	      
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(this.txtNumber.getText()==null  || this.txtNumber.getText().trim().equals("")){
			MsgBox.showInfo("编码不能为空！");
			this.abort();
		}		
	
		if(this.prmtMoneyName.getValue()==null ){
			MsgBox.showInfo("款项名称不能为空！");
			this.abort();
		}
		
		if(this.prmtProject.getValue()==null ){
			MsgBox.showInfo("项目不能为空！");
			this.abort();
		}		
		
//        if(this.editData.getMoneyName() != this.prmtMoneyName.getValue()){
//        	checkMonyeType();
//        }
		
		CalculateTypeEnum calculateType = (CalculateTypeEnum)this.comboCalculateType.getSelectedItem();
	      if(CalculateTypeEnum.GENERAL.equals(calculateType)){
	    	 BigDecimal comValue = (BigDecimal)this.txtComparaValue.getBigDecimalValue();
	    	 if(comValue==null) {
	 			MsgBox.showInfo("常规模式下，因素值必须录入！");
				this.abort();	    		 
	    	 }
	      }
		
		
		super.actionSubmit_actionPerformed(e);
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		
	}

	public void checkMonyeType() throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		MoneyDefineInfo moneyDefine = (MoneyDefineInfo) this.prmtMoneyName
				.getValue();
		SellProjectInfo sellProject = (SellProjectInfo) this.prmtProject
				.getValue();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("moneyName.id", moneyDefine.getId()));
		filter.getFilterItems().add(
				new FilterItemInfo("project.id", sellProject.getId()));
		view.getSelector().add("id");
		view.getSelector().add("number");
		view.getSelector().add("name");
		view.getSelector().add("moneyName.*");
		CollectionInfo collection = new CollectionInfo();
		CollectionCollection payList1 = CollectionFactory.getRemoteInstance()
				.getCollectionCollection(view);
		if (payList1.size() != 0) {
			MsgBox.showInfo("款项名称已近存在！");
			this.abort();
		}
	}

	
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
		this.prmtProject.setEnabled(true);
		
  	  	this.btnClear.setEnabled(true);
  	  	this.btnScript.setEnabled(true);
  	  SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
  	
	}
	
	
	
}