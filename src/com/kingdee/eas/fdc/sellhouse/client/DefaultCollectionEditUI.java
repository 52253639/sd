/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.bo.BusinessObjectInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.forewarn.ForewarnSource;
import com.kingdee.eas.base.forewarn.MetadataType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basecrm.client.CRMFormulaDialog;
import com.kingdee.eas.fdc.basecrm.client.FormulaHelper;
import com.kingdee.eas.fdc.basecrm.client.FormulaVarInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.CalculateTypedefaultEnum;
import com.kingdee.eas.fdc.sellhouse.DefaultCollectionFactory;
import com.kingdee.eas.fdc.sellhouse.DefaultCollectionInfo;
import com.kingdee.eas.fdc.sellhouse.HoldEnum;
import com.kingdee.eas.fdc.sellhouse.IntegerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 违约金计算公式设置界面
 */
public class DefaultCollectionEditUI extends AbstractDefaultCollectionEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(DefaultCollectionEditUI.class);
    
    /**
     * by..... wancheng
     */
    public DefaultCollectionEditUI() throws Exception
    {
        super();
    }
    SellProjectInfo sellProjectInfo =null;
    public void onLoad() throws Exception 
	{
		super.onLoad();
		/**设置数字控件精度*/
		Object[] obj = {txtScale};
		FDCHelper.setComponentPrecision(obj,2);
		this.actionAddNew.setVisible(false);
		this.prmtProject.setEnabled(false);
		if (( this.getUIContext().get("sellProject") != null) && ( this.getUIContext().get("sellProject") != "")) {
			SellProjectInfo projectInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(this.getUIContext().get("sellProject").toString()));
			this.prmtProject.setValue(projectInfo);
			sellProjectInfo = projectInfo;
		}
		if(OprtState.VIEW.equals(this.getOprtState()))
		{
			CalculateTypedefaultEnum calculateType = (CalculateTypedefaultEnum)this.comboCalculateType.getSelectedItem();
    		if(CalculateTypedefaultEnum.PATULOUS.equals(calculateType)){
    			kDContainer1.setVisible(false);
    			txtScale.setVisible(false);
    			contScale.setVisible(false);
    			txtFormulaDescription.setVisible(true);
//    			txtFormulaDescription.setEnabled(true);
//    			contFormulaDescription.setEnabled(true);
    			contFormulaDescription.setVisible(true);
    			btnScript.setEnabled(false);
    			btnScript.setVisible(true);
    			btnEmpty.setEnabled(false);
    			btnEmpty.setVisible(true);
    			this.txtFormulaDescription.setEnabled(false);
    			this.contFormulaDescription.setEnabled(false);
    		}else if(CalculateTypedefaultEnum.GENERAL.equals(calculateType)){
    			kDContainer1.setVisible(true);
    			txtScale.setVisible(true);
    			contScale.setVisible(true);
    			txtFormulaDescription.setVisible(false);
    			txtFormulaDescription.setEnabled(false);
    			contFormulaDescription.setEnabled(false);
    			contFormulaDescription.setVisible(false);
    			btnScript.setEnabled(false);
    			btnScript.setVisible(false);
    			btnEmpty.setEnabled(false);
    			btnEmpty.setVisible(false);
    		}
		}
    	if(OprtState.EDIT.equals(this.getOprtState())){
    		CalculateTypedefaultEnum calculateType = (CalculateTypedefaultEnum)this.comboCalculateType.getSelectedItem();
    		if(CalculateTypedefaultEnum.PATULOUS.equals(calculateType)){
    			kDContainer1.setVisible(false);
    			txtScale.setVisible(false);
    			txtScale.setValue(null);
    			contScale.setVisible(false);
    			txtFormulaDescription.setVisible(true);
//    			txtFormulaDescription.setEnabled(true);
//    			contFormulaDescription.setEnabled(true);
    			contFormulaDescription.setVisible(true);
    			btnScript.setEnabled(true);
    			btnScript.setVisible(true);
    			btnEmpty.setEnabled(true);
    			btnEmpty.setVisible(true);
    			this.txtFormulaDescription.setEnabled(false);
    			this.contFormulaDescription.setEnabled(false);
    		}else if(CalculateTypedefaultEnum.GENERAL.equals(calculateType)){
    			kDContainer1.setVisible(true);
    			txtScale.setVisible(true);
    			contScale.setVisible(true);
    			txtFormulaDescription.setVisible(false);
    			txtFormulaDescription.setEnabled(false);
    			contFormulaDescription.setEnabled(false);
    			contFormulaDescription.setVisible(false);
    			btnScript.setEnabled(false);
    			btnScript.setVisible(false);
    			btnEmpty.setEnabled(false);
    			btnEmpty.setVisible(false);
    		}
    	}
    	if (( this.getUIContext().get("ID") != null) && ( this.getUIContext().get("ID") != "")) {
			DefaultCollectionInfo projectInfo = DefaultCollectionFactory.getRemoteInstance().getDefaultCollectionInfo(new ObjectUuidPK(this.getUIContext().get("ID").toString()));
			this.editData.setName(projectInfo.getName());
			
		}
    	this.actionCancel.setVisible(false);
    	this.actionCancelCancel.setVisible(false);
    	this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
	}

    public void loadFields()
    {
		super.loadFields();
	}
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
    	if(CalculateTypedefaultEnum.GENERAL.equals(this.comboCalculateType.getSelectedItem())&&this.txtScale.getValue()==null){
    		  MsgBox.showInfo("比例不能为空!");
		      SysUtil.abort();
    	}
    	
    }
	protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return txtName;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected IObjectValue createNewData() {
		DefaultCollectionInfo defaultInfo = new DefaultCollectionInfo();
		defaultInfo.setCalculateType(CalculateTypedefaultEnum.GENERAL);
		defaultInfo.setHold(HoldEnum.YUAN);
		defaultInfo.setIntegerType(IntegerTypeEnum.ROUND);
		defaultInfo.setProject(sellProjectInfo);
		return  defaultInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return DefaultCollectionFactory.getRemoteInstance();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
	}
	/**
     * 计算方式
     */
	protected void comboCalculateType_itemStateChanged(ItemEvent e)throws Exception {
		super.comboCalculateType_itemStateChanged(e);
		CalculateTypedefaultEnum calculateType = (CalculateTypedefaultEnum)this.comboCalculateType.getSelectedItem();
		if(CalculateTypedefaultEnum.PATULOUS.equals(calculateType)){
			kDContainer1.setVisible(false);
			txtScale.setVisible(false);
			txtScale.setValue(null);
			contScale.setVisible(false);
			txtFormulaDescription.setVisible(true);
//			txtFormulaDescription.setEnabled(true);
//			contFormulaDescription.setEnabled(true);
			contFormulaDescription.setVisible(true);
			btnScript.setEnabled(true);
			btnScript.setVisible(true);
			btnEmpty.setEnabled(true);
			btnEmpty.setVisible(true);
			this.txtFormulaDescription.setEnabled(false);
			this.contFormulaDescription.setEnabled(false);
		}else if(CalculateTypedefaultEnum.GENERAL.equals(calculateType)){
			kDContainer1.setVisible(true);
			txtScale.setVisible(true);
			contScale.setVisible(true);
			txtFormulaDescription.setVisible(false);
			txtFormulaDescription.setEnabled(false);
			contFormulaDescription.setEnabled(false);
			contFormulaDescription.setVisible(false);
			btnScript.setEnabled(false);
			btnScript.setVisible(false);
			btnEmpty.setEnabled(false);
			btnEmpty.setVisible(false);
			
			txtFormulaDescription.setText(null);
			
		}
	}
	/**
     * 脚本编辑
     */
	public void actionScript_actionPerformed(ActionEvent e) throws Exception {
		super.actionScript_actionPerformed(e);
		CRMFormulaDialog formulaDialog = 
			new CRMFormulaDialog((KDDialog)this.getUIWindow(),
				EASResource.getString(ForewarnSource.FOREWARN_SOURCE_NAME,"formulaSysTitle"),true);
                
    	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    	formulaDialog.setSize((int)(d.width*0.8), (int)(d.height*0.8));
    	
		FormulaVarInfo[] varInfos = new FormulaVarInfo[1]; 
		varInfos[0] = new FormulaVarInfo("违约金管理分录","",MetadataType.ENTITY,"DefaultAmountMangerEntry","com.kingdee.eas.fdc.sellhouse.app.DefaultAmountMangerEntry"); 
		//varInfos[1] = new FormulaVarInfo("认购单","",MetadataType.ENTITY,"PurchaseManager","com.kingdee.eas.fdc.sellhouse.app.PurchaseManage");
//		varInfos[1] = new FormulaVarInfo("成交总价","",null,"DealTotalAmount","java.math.BigDecimal"); 
		
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
	/**
     * 结果清空
     */
	public void actionEmpty_actionPerformed(ActionEvent e) throws Exception {
		super.actionEmpty_actionPerformed(e);
	}
	private void verify() {
		try {
			super.verify(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
     * 
     */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		
	}
	  /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
//        sic.add(new SelectorItemInfo("isEnabled"));
//        sic.add(new SelectorItemInfo("number"));
//        sic.add(new SelectorItemInfo("calculateType"));
//        sic.add(new SelectorItemInfo("hold"));
//        sic.add(new SelectorItemInfo("integerType"));
//        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("project.*"));
//        sic.add(new SelectorItemInfo("description"));
//        sic.add(new SelectorItemInfo("simpleName"));
//        sic.add(new SelectorItemInfo("scale"));
//        sic.add(new SelectorItemInfo("formulaDescription"));
        sic.add("*");
        return sic;
    }
}