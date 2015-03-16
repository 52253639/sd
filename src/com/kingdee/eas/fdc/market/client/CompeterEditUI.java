package com.kingdee.eas.fdc.market.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.market.CompetePriceRecordInfo;
import com.kingdee.eas.fdc.market.CompeterFactory;
import com.kingdee.eas.fdc.market.CompeterInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.RentTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class CompeterEditUI extends AbstractCompeterEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(CompeterEditUI.class);
    
    /**
     * output class constructor
     */
    public CompeterEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	protected ICoreBase getBizInterface() throws Exception {
		return CompeterFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		return new CompeterInfo(); 
	}
	
	protected KDTable getDetailTable() {
		return this.tblPriceRecordList;
	}
	
	protected IObjectValue createNewDetailData(KDTable table) {
		return new CompetePriceRecordInfo();
	}
	
	
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
	}
	
	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionInsertLine_actionPerformed(e);
	}
	
	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemoveLine_actionPerformed(e);
	}
	
	
	protected void beforeStoreFields(ActionEvent e) throws Exception {   
		super.beforeStoreFields(e);
		
		StringBuffer buff = new StringBuffer();
		if(StringUtils.isEmpty(this.txtNumber.getText()))
			buff.append("编码不能为空!\n");
		if(StringUtils.isEmpty(this.txtName.getText()))
			buff.append("名称不能为空!\n");
		if(this.comboSysType.getSelectedItem()==null)
			buff.append("所属系统不能为空!\n");
		if(!buff.toString().equals("")) {
			MsgBox.showWarning(buff.toString());
			this.abort();
		}
		MoneySysTypeEnum sysType = (MoneySysTypeEnum)this.comboSysType.getSelectedItem();
		RentTypeEnum lastRentType = null;
		BigDecimal lastAvagePrice = null;
		Date lastInvestDate = null;
		for(int i=0;i<this.tblPriceRecordList.getRowCount();i++) {
			IRow row = this.tblPriceRecordList.getRow(i);
			Date thisInvestDate = (Date)row.getCell("investDate").getValue(); 
			if(thisInvestDate==null) 
				buff.append("分录中行"+(i+1)+"的调查日期不能为空!\n");
			RentTypeEnum thisRentType = (RentTypeEnum)row.getCell("rendtype").getValue();
			if(sysType.equals(MoneySysTypeEnum.TenancySys)) {
				if(thisRentType==null)
				buff.append("分录中行"+(i+1)+"的租金类型不能为空!\n");
			}
			BigDecimal thisAveragePrice = (BigDecimal)row.getCell("lastAveragePrice").getValue();
			if(thisAveragePrice==null)
				buff.append("分录中行"+(i+1)+"的最新均价不能为空!\n");
			
			if(lastInvestDate==null || (thisInvestDate!=null && thisInvestDate.after(lastInvestDate))) {
				lastInvestDate = thisInvestDate;
				lastRentType = thisRentType;
				lastAvagePrice = thisAveragePrice;
			}
			String thisFavourableType = (String)row.getCell("favourableType").getValue();
			if(thisFavourableType!=null && !"".equals(thisFavourableType) && thisFavourableType.length()>80)
				buff.append("分录中行"+(i+1)+"的优惠方式长度不能大于80!\n");
			String thisDescription = (String)row.getCell("description").getValue();
			if(thisDescription!=null && !"".equals(thisDescription) && thisDescription.length()>80)
				buff.append("分录中行"+(i+1)+"的备注长度不能大于80!\n");
		}
		
		if(!buff.toString().equals("")) {
			MsgBox.showWarning(buff.toString());
			this.abort();
		}
		
		if(sysType.equals(MoneySysTypeEnum.TenancySys)) {
			this.editData.setRendtype(lastRentType);
			this.editData.setLastAveragePrice(lastAvagePrice);
		}else if(sysType.equals(MoneySysTypeEnum.SalehouseSys)){
			this.editData.setRendtype(null);
			this.editData.setLastAveragePrice(lastAvagePrice);
		}
		
	}
	
	
	
	
	
	protected void attachListeners() {
		// TODO 自动生成方法存根		
	}

	protected void detachListeners() {
		// TODO 自动生成方法存根		
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}



	public void onLoad() throws Exception {
		super.onLoad();
		
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if(!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}		
		
		//设置隐藏菜单和工具按钮
		this.menuBiz.setVisible(false);
		this.menuWorkflow.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionSubmitOption.setVisible(false);
		this.btnWorkFlowG.setVisible(false);
		this.btnAudit.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		
		this.actionAttachment.setVisible(false);		
		this.btnAuditResult.setVisible(false);
		this.btnMultiapprove.setVisible(false);
		this.btnNextPerson.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		
		this.txtName.setRequired(true);
		
		
		//初始化分录表中的控件  investDate  rendtype lastAveragePrice  	favourableType	description
		this.tblPriceRecordList.getColumn("investDate").setEditor(CommerceHelper.getKDDatePickerEditor());
		this.tblPriceRecordList.getColumn("investDate").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.tblPriceRecordList.getColumn("rendtype").setEditor(CommerceHelper.getKDComboBoxEditor(RentTypeEnum.getEnumList()));
		this.tblPriceRecordList.getColumn("lastAveragePrice").setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
		this.tblPriceRecordList.getColumn("lastAveragePrice").getStyleAttributes().setNumberFormat("%r-[ ]0.2n");
		this.tblPriceRecordList.getColumn("lastAveragePrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		//控制优惠方式，备注的最大长度为80
		KDTextField txtField = new KDTextField();
		txtField.setMaxLength(80);
    	KDTDefaultCellEditor remarkEditor = new KDTDefaultCellEditor(txtField);
    	this.tblPriceRecordList.getColumn("favourableType").setEditor(remarkEditor);
    	this.tblPriceRecordList.getColumn("description").setEditor(remarkEditor);
		this.txtDescription.setMaxLength(80);
		this.txtAdvantage.setMaxLength(80);
		this.txtDisAdvantage.setMaxLength(80);
	}
	
    
	
	
	protected void comboSysType_itemStateChanged(ItemEvent e) throws Exception {
		super.comboSysType_itemStateChanged(e);
		MoneySysTypeEnum sysType = (MoneySysTypeEnum)e.getItem();
		if(sysType.equals(MoneySysTypeEnum.TenancySys)){
			this.tblPriceRecordList.getColumn("rendtype").getStyleAttributes().setHided(false);
		}else{
			this.editData.setRendtype(null);
			this.tblPriceRecordList.getColumn("rendtype").getStyleAttributes().setHided(true);
		}
	}	
		
	






}