/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Component;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.Currency;
import java.util.Date;

import javax.swing.JComponent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.CRMChequeCollection;
import com.kingdee.eas.fdc.sellhouse.CRMChequeFactory;
import com.kingdee.eas.fdc.sellhouse.CRMChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeSellProjectEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeSellProjectEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CodeGenerater;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.VerifyStatusEnum;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CRMChequeBookInUI extends AbstractCRMChequeBookInUI
{
    private static final Logger logger = CoreUIObject.getLogger(CRMChequeBookInUI.class);
    
    /**
     * output class constructor
     */
    public CRMChequeBookInUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
    	storeSellProject();
    	super.storeFields();
    }
    private void storeSellProject() {
    	ChequeSellProjectEntryCollection chequeSellProject =  this.editData.getSellProjectEntry();
    	chequeSellProject.clear();
    	this.editData.setSellProjectNumbers("");
    	Object[] object = (Object[])this.F7SellProject.getValue();
    	if(object==null || object[0]==null)return;
    	String spNumbers = "";
    	String spNames = "";
    	for(int i = 0 ; i<object.length;i++){
    		SellProjectInfo spInfo = (SellProjectInfo)object[i];
    		ChequeSellProjectEntryInfo info = new ChequeSellProjectEntryInfo();
    		info.setCRMCheque(editData);
    		info.setSellProject(spInfo);
    		chequeSellProject.add(info);
    		spNumbers += ","+spInfo.getNumber();
    		spNames += ","+spInfo.getName();
    	}
    	if(!spNumbers.equals("")) spNumbers = spNumbers.substring(1);
    	if(!spNames.equals("")) spNames = spNames.substring(1);
    	this.editData.setSellProjectNumbers(spNumbers);
    	this.editData.setSellProjectNames(spNames);
	}
    public void onLoad() throws Exception {
    	super.onLoad();
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);    	
    	
    	initF7SellProject();
    	
    	
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    }
	private void initF7SellProject() {
		this.F7SellProject.setEnabledMultiSelection(true);
		this.F7SellProject.setEditable(true);
		this.F7SellProject.setCommitFormat("$name$");
		this.F7SellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");
		try {
			this.F7SellProject.setEntityViewInfo(NewCommerceHelper.getPermitProjectView());
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
	}

	protected IObjectValue createNewData() {
		//this.F7SellProject.setValue(null);
		CRMChequeInfo chequeInfo = new CRMChequeInfo();
		chequeInfo.setChequeType(ChequeTypeEnum.receipt);
		chequeInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		chequeInfo.setCreateTime(new Timestamp(new Date().getTime()));
		chequeInfo.setKeepOrgUnit(SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo());
		chequeInfo.setKeeper(SysContext.getSysContext().getCurrentUserInfo());
//		chequeInfo.setCurrency(SysContext.getSysContext().getCurrentFIUnit());
		try{
	    	CompanyOrgUnitInfo tempCompany = SysContext.getSysContext().getCurrentFIUnit();
			CompanyOrgUnitInfo company = GlUtils.getCurrentCompany(null,tempCompany.getId().toString(),null,false);
			chequeInfo.setCurrency(company.getBaseCurrency());	
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}				
		
		SellProjectInfo spInfo = (SellProjectInfo)this.getUIContext().get("sellProject");
		if(spInfo!=null) this.F7SellProject.setValue(spInfo);
		
		return  chequeInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		
		return CRMChequeFactory.getRemoteInstance();
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyInput(e);// 校验
		// 批量添加空白票据
		doAddBatch();
//		// 显示成功信息
//		setMessageText("登记成功");
//		showMessage();
		super.actionSubmit_actionPerformed(e);
		this.setOprtState(OprtState.VIEW);
	}
	
	private void doAddBatch() throws Exception, BOSException, EASBizException {
		CRMChequeInfo  chequeInfo = this.editData;
		chequeInfo.setChequeType((ChequeTypeEnum) this.comboChequeType.getSelectedItem());
		chequeInfo.setLimitAmount(this.txtLimitAmount.getBigDecimalValue());
		chequeInfo.setCurrency((CurrencyInfo) this.f7Currency.getValue());
		ChequeSellProjectEntryCollection csEntryColl = chequeInfo.getSellProjectEntry();
		csEntryColl.clear();
		Object[] sellProjectArray = (Object[])this.F7SellProject.getValue();
		StringBuffer sbNumbers = new StringBuffer();
		StringBuffer sbNames = new StringBuffer();
		if(sellProjectArray!=null && sellProjectArray.length >1 && sellProjectArray[0]!=null){
			for(int i = 0 ; i < sellProjectArray.length ; i ++){
				ChequeSellProjectEntryInfo info = new ChequeSellProjectEntryInfo();
				SellProjectInfo sellInfo = (SellProjectInfo)sellProjectArray[i];
				sbNumbers.append(","+sellInfo.getNumber());
				sbNames.append(","+sellInfo.getName());
				info.setSellProject(sellInfo);
				info.setCRMCheque(chequeInfo);
				csEntryColl.add(info);
			}
		}
		chequeInfo.setSellProjectNumbers(sbNumbers.toString().replaceFirst(",", ""));
		chequeInfo.setSellProjectNames(sbNames.toString().replaceFirst(",", ""));
		ChequeDetailEntryCollection collection = chequeInfo.getChequeDetailEntry();
		collection.clear();
		String codeRule = this.txtCodeRule.getText();// 编码规则，由界面传入
		int count = Integer.parseInt(this.spinCount.getValue().toString());// 张数，由界面传入
		int beginIndex = Integer.parseInt(this.spinStartNum.getValue().toString());// 起始流水号，由界面传入
		// 设置编码规则、张数、起始流水号
		CodeGenerater codeGen = new CodeGenerater();
		codeGen.setBeginIndex(beginIndex);
		codeGen.setCount(count);
		codeGen.setCodeRule(codeRule);
		for (int i = 0; i < count; i++) {
			ChequeDetailEntryInfo item = new ChequeDetailEntryInfo();
			item.setStatus(ChequeStatusEnum.Booked);
			item.setVerifyStatus(VerifyStatusEnum.NotVerified);
			item.setNumber(codeGen.nextNumber());// 根据编码规则、张数、起始流水号生成支票编码
			collection.add(item);
		}

	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		// 编码规则不能为空
		this.verifyEmpty(this, txtChequeBatch, "票据批次不能为空！");
		this.verifyEmpty(this, f7Currency, "币别不能为空！");
		this.verifyEmpty(this, txtCodeRule, "编码规则不能为空！");
		this.verifyEmpty(this, spinCount, "张数不能为空！");
		this.verifyEmpty(this, spinStartNum, "起始流水号不能为空！");
		// 编码规则必有一个*号
		if (this.txtCodeRule.getText() != null
				&& this.txtCodeRule.getText().indexOf("*") < 0) {
			MsgBox.showInfo(this, "编码规则必有一个*号！");
			this.abort();
		}
		
		if(((Integer)this.spinCount.getValue()).intValue()<1){
			FDCMsgBox.showInfo(this, "张数必须大于1！");
			SysUtil.abort();
		}
		this.verifyEmpty(this, f7Keeper, "保管人不能为空");
		String number = this.txtCodeRule.getText()+ this.spinCount.getIntegerVlaue();
		if (number.length() > 80) {
			MsgBox.showInfo(this, "支票编码规则长度+张数长度不能超过80个字符");
			SysUtil.abort();
		}
		 checkIsDump();
	}
	
	private void verifyEmpty(CoreUIObject inUI, JComponent com,String msg) {
		Object content = null;
		if(com instanceof KDBizPromptBox){
			KDBizPromptBox promtBox = (KDBizPromptBox)com;
			content  = promtBox.getValue();
		} else if(com instanceof KDSpinner){
			KDSpinner spinner = (KDSpinner)com;
			 content = spinner.getIntegerVlaue();
		} else if(com instanceof KDTextField){
			KDTextField text = (KDTextField)com;
			content = text.getText().trim();
			if(content.equals("")){
				content = null;
			}
		}
		if (content == null) {
			com.requestFocus(true);
			MsgBox.showWarning(inUI, msg);
			SysUtil.abort();
		}
	}
	
	/**
	 * 判断是否有重复编码的票据
	 * */
	private void checkIsDump() throws BOSException, EASBizException {
		String codeRule = this.txtCodeRule.getText();// 编码规则，由界面传入
		int count = Integer.parseInt(this.spinCount.getValue().toString());// 张数，由界面传入
		int beginIndex = Integer.parseInt(this.spinStartNum.getValue().toString());// 起始流水号，由界面传入

		CodeGenerater codeGen = new CodeGenerater();
		codeGen.setBeginIndex(beginIndex);
		codeGen.setCount(count);
		codeGen.setCodeRule(codeRule);
		String[] numbers = new String[count];

		for (int i = 0; i < count; i++) {
			numbers[i] = codeGen.nextNumber();
		}

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", FDCHelper.getSetByArray(numbers),CompareType.INCLUDE));

		if(ChequeFactory.getRemoteInstance().exists(filter)){
			MsgBox.showInfo(this, "存在重复的票据编号");
			this.abort();
		}
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel = super.getSelectors();
		sel.add("sellProjectEntry.*");
		sel.add("sellProjectEntry.sellProject.*");
		return sel;
	}
	
}