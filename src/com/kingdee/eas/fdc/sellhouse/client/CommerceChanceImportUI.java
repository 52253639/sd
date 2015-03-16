/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.AreaRequirementInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.BuyHouseReasonInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceIntentionEnum;
import com.kingdee.eas.fdc.sellhouse.CommerceLevelInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FirstPayProportionInfo;
import com.kingdee.eas.fdc.sellhouse.HopePitchInfo;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.HopedFloorInfo;
import com.kingdee.eas.fdc.sellhouse.HopedTotalPricesInfo;
import com.kingdee.eas.fdc.sellhouse.HopedUnitPriceInfo;
import com.kingdee.eas.fdc.sellhouse.ProductDetialInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFormInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SightRequirementInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;

import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CommerceChanceImportUI extends AbstractCommerceChanceImportUI
{
    private static final Logger logger = CoreUIObject.getLogger(CommerceChanceImportUI.class);
    
    private Map mapSaleMan = null; 
    private Map mapCommerceLevel = null;
    private Map mapIntentBuildingPro = null;
    private Map mapIntentProductType = null;
    private Map mapProductDeatil = null;
    private Map mapIntentDirection = null;
    private Map mapIntentArea = null;
    private Map mapIntentSight = null;
    private Map mapRoomForm = null;
    private Map mapIntentRoomType = null;
    private Map mapHopedUnitPrice = null;
    private Map mapHopedTotalPrices = null;
    private Map mapHopedFloor = null;
    private Map mapBuyHouseReason = null;
    private Map mapHopedPitch = null;
    private Map mapCommerceIntention = null;
    private Map mapFirstPayProportion = null;
    
    private boolean existNumberRule = CommerceHelper.isExistNumberRule(new CommerceChanceInfo());
    
    /**
     * output class constructor
     */
    public CommerceChanceImportUI() throws Exception
    {
        super();
    }

	protected String getEditUIName() {
		return CommerceChanceEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CommerceChanceFactory.getRemoteInstance();
	}


	public void onLoad() throws Exception {
		super.onLoad();
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);

		SellProjectInfo sellProInfo = (SellProjectInfo)this.getUIContext().get("sellProject");
		if(sellProInfo!=null)
			this.prmtSellProject.setValue(sellProInfo);
	
		this.prmtSellProject.setEntityViewInfo(CommerceHelper.getPermitProjectView());
		
		this.actionExcelImport.setEnabled(true);
		this.actionRemove.setEnabled(true);
		this.actionView.setVisible(false);
		this.actionRefresh.setVisible(false);
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.menuView.setVisible(false);
		
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
	}
	
	public void onShow() throws Exception {
		super.onShow();
		
     	int indexNum = this.tblMain.getColumn("importStatus").getColumnIndex();     	
     	this.tblMain.getViewManager().setFreezeView(-1, indexNum+1);
	}
	
	
	public void actionExcelImport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExcelImport_actionPerformed(e);
		
		SellProjectInfo sellProInfo = (SellProjectInfo)this.prmtSellProject.getValue();
		MoneySysTypeEnum sysType =  (MoneySysTypeEnum)this.comboSysType.getSelectedItem();
		
		if(sellProInfo==null || sysType==null) {
			MsgBox.showInfo("必须先选择'租售项目'和'所属系统'!");
			return;
		}
		
		initColumnEditor();  
		
		//清除所有数据
        int rowCount = tblMain.getRowCount();
		for(int i=0;i<rowCount;i++) {
		   tblMain.removeRow(0);
		}		
		
		String fileName = SHEHelper.showExcelSelectDlg(this);
		this.txtFilePath.setText(fileName);
		
		this.tblMain.getColumn("importStatus").getStyleAttributes().setHided(true);
		//	首付比例 firstPayProportion	 租赁时不能导入
		if(sysType.equals(MoneySysTypeEnum.TenancySys)) 
			this.tblMain.getColumn("firstPayProportion").getStyleAttributes().setHided(true);
		int excelNum = CommerceHelper.importExcelToTable(fileName, this.tblMain);       //对隐藏的列不写入,所以对不需要导入的列先隐藏再显示
		this.tblMain.getColumn("importStatus").getStyleAttributes().setHided(false);
		if(sysType.equals(MoneySysTypeEnum.TenancySys)) 
			this.tblMain.getColumn("firstPayProportion").getStyleAttributes().setHided(false);
		
		
		this.tblMain.setRowCount(excelNum);
		this.txtExcelCount.setText(String.valueOf(excelNum));
		
	
	    //遍历所有记录校验 
		for(int i=0;i<this.tblMain.getRowCount();i++)
			vertifyARowImportDate(i);
		
		
		setTheTextCount();
		
		
		this.actionExcelSave.setEnabled(true);
	}
	
	
	
	public void actionExcelSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionExcelSave_actionPerformed(e);
		//必须全部校验通过后才能保存
		String invalildCount = this.txtInvaluidCount.getText();
		if(invalildCount==null) return;
		if(invalildCount!=null && !invalildCount.equals("0")) {
			MsgBox.showInfo("还有"+invalildCount+"条未校验通过,必须全部校验通过后才能保存!");
			return;
		}
		
		
		for(int i=0;i<this.tblMain.getRowCount();i++) {
			IRow row = this.tblMain.getRow(i);
			String importStatus = (String)row.getCell("importStatus").getValue(); 
			//只针对校验通过的行进行保存 (已保存过的除外)
			if(importStatus!=null && importStatus.indexOf("校验通过")>=0){
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add(new SelectorItemInfo("saleMan"));
				selector.add(new SelectorItemInfo("fdcCustomer"));
				selector.add(new SelectorItemInfo("name"));
				selector.add(new SelectorItemInfo("phoneNumber"));
				selector.add(new SelectorItemInfo("commerceLevel"));
				selector.add(new SelectorItemInfo("commerceDate"));
				selector.add(new SelectorItemInfo("hopedBuildingProperty"));
				selector.add(new SelectorItemInfo("hopedProductType"));
				selector.add(new SelectorItemInfo("productDeatil"));
				selector.add(new SelectorItemInfo("hopedDirection"));
				selector.add(new SelectorItemInfo("hopedAreaRequirement"));
				selector.add(new SelectorItemInfo("hopedSightRequirement"));
				selector.add(new SelectorItemInfo("hopedRoomModelType"));
				selector.add(new SelectorItemInfo("roomForm"));
				selector.add(new SelectorItemInfo("hopedUnitPrice"));
				selector.add(new SelectorItemInfo("hopedTotalPrices"));
				selector.add(new SelectorItemInfo("hopedFloor"));
				selector.add(new SelectorItemInfo("buyHouseReason"));
				selector.add(new SelectorItemInfo("hopedPitch"));
				selector.add(new SelectorItemInfo("commerceIntention"));
				selector.add(new SelectorItemInfo("intendingDate"));
				selector.add(new SelectorItemInfo("intendingMoney"));
				selector.add(new SelectorItemInfo("firstPayProportion"));
				selector.add(new SelectorItemInfo("description"));
				selector.add(new SelectorItemInfo("bargainOnCondition"));
				CommerceChanceInfo thisInfo = (CommerceChanceInfo)row.getUserObject();
				if(thisInfo.getId()!=null) {  //修改
					try{				
						CommerceChanceFactory.getRemoteInstance().updatePartial(thisInfo,selector);
						row.getCell("importStatus").setValue("保存成功,修改数据");
					}catch(Exception ee){
						this.setRowStyleLockStatus(row,"保存失败,修改数据 \r\n"+ee.getMessage());				
					}
				} else {   //新增
					String number = thisInfo.getNumber();
					if(number==null || number.trim().equals("")) {
						if(existNumberRule) {
							number = CommerceHelper.getNumberByRule(new CommerceChanceInfo());
							thisInfo.setNumber(number);
							row.getCell("number").setValue(number);
						}
					}
					
					if(number!=null && !number.trim().equals("")) {					
					  try{
							if(thisInfo.getSysType()!=null) {
								thisInfo.setCommerceStatus(CommerceStatusEnum.Intent);
							}		
						  CommerceChanceFactory.getRemoteInstance().addnew(thisInfo); //IObjectPK ioPk = 
						row.getCell("importStatus").setValue("保存成功,新增数据");
					  }catch(Exception ee) {
						  this.setRowStyleLockStatus(row,"保存失败,新增数据 \r\n"+ee.getMessage());		
					  }
					}else{
						this.setRowStyleLockStatus(row,"保存失败,新增数据 \r\n"+"编码为空");	
					}
				}
			}
		}

	}
	
	
	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		super.tblMain_editStopped(e);
		
		int rowIndex = e.getRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
//		String importStatus = (String)row.getCell("importStatus").getValue();
//		if(importStatus!=null && importStatus.indexOf("保存成功")>=0)
//			return;
		
		
		vertifyARowImportDate(rowIndex);
		
		setTheTextCount();
	}
	
	
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		String key = this.getSelectedKeyValue();
		if(key==null) return;		
		
		super.tblMain_tableClicked(e);
	}
	
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		//super.actionRemove_actionPerformed(e);
	    checkSelected();
	    if (confirmRemove())
	    {
			int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
			for(int i=selectRows.length-1;i>-1;i--) {
				this.tblMain.removeRow(selectRows[i]);		
			}	
			
			setTheTextCount();
	    }	
	}
	
	
	
	/**
	 * 初始化单元格里的控件
	 */
	private void initColumnEditor(){ 
		
		//importStatus 导入状态  
		try{
			this.tblMain.getColumn("saleMan").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.base.permission.app.F7UserQuery",CommerceHelper.getPermitSalemanView()));  // 营销顾问
			this.tblMain.getColumn("saleMan").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
			this.tblMain.getColumn("fdcCustomer").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery",CommerceHelper.getPermitCustomerView()));  //客户名称	fdcCustomer
			this.tblMain.getColumn("fdcCustomer").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		}catch(Exception e){
			e.printStackTrace();
			this.abort();
		}
		//商机编码    number
		if(!existNumberRule)
			this.tblMain.getColumn("number").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		//商机名称	name
		//商机联系电话	phoneNumber
		
		this.tblMain.getColumn("commerceLevel").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.CommerceLevelQuery",null));  //商机级别
		this.tblMain.getColumn("commerceLevel").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		this.tblMain.getColumn("commerceDate").setEditor(CommerceHelper.getKDDatePickerEditor());    //商机日期
		this.tblMain.getColumn("commerceDate").getStyleAttributes().setNumberFormat("YYYY-MM-DD");
		this.tblMain.getColumn("commerceDate").getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		this.tblMain.getColumn("intentBuildingPro").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.BuildingPropertyQuery",null));  //意向建筑性质
		this.tblMain.getColumn("intentProductType").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery",null));  //意向产品类型
		
		//产品描述 需要根据销售项目过滤
		String sellProId = "null";
		SellProjectInfo sellProInfo = (SellProjectInfo)this.prmtSellProject.getValue();
		if(sellProInfo!=null)  sellProId = sellProInfo.getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",sellProId));
		view.setFilter(filter);
		this.tblMain.getColumn("productDeatil").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.ProductDetialQuery",view));  //
		
		this.tblMain.getColumn("intentDirection").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.HopedDirectionQuery",null));  //意向朝向
		this.tblMain.getColumn("intentArea").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.AreaRequirementQuery",null));  //面积需求
		this.tblMain.getColumn("intentSight").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.SightRequirementQuery",null));  //景观需求
		
		this.tblMain.getColumn("roomForm").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.RoomFormQuery",null));  //房屋形式
		
		this.tblMain.getColumn("intentRoomType").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.RoomModelTypeQuery",null));  //户型需求
		this.tblMain.getColumn("hopedUnitPrice").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.HopedUnitPriceQuery",null));  //意向单价		
		this.tblMain.getColumn("hopedTotalPrices").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.HopedTotalPricesQuery",null));  //意向总价	
		this.tblMain.getColumn("hopedFloor").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.HopedFloorQuery",null));  //意向楼层	
		this.tblMain.getColumn("buyHouseReason").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.BuyHouseReasonQuery",null));  //商机原因
		this.tblMain.getColumn("hopedPitch").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.HopePitchQuery",null));  //意向强度
		this.tblMain.getColumn("commerceIntention").setEditor(CommerceHelper.getKDComboBoxEditor(CommerceIntentionEnum.getEnumList()));  //置业目的
		this.tblMain.getColumn("intendingDate").setEditor(CommerceHelper.getKDDatePickerEditor());    //预计日期
		this.tblMain.getColumn("intendingDate").getStyleAttributes().setNumberFormat("YYYY-MM-DD");
		this.tblMain.getColumn("intendingMoney").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);  //预计成交额	
		this.tblMain.getColumn("intendingMoney").getStyleAttributes().setNumberFormat("%r-[ ]0.2n");
		this.tblMain.getColumn("firstPayProportion").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.FirstPayProportionQuery",null));  //首付比例	 租赁时不能导入	 					
		//描述			description							
		//成交条件.		bargainOnCondition
	}
	
	
	/**
	 * 针对某一行进行校验
	 * 	 * 必须导入的列:  营销顾问 客户名称  商机编码  商机名称 商机联系电话	商机级别	商机日期
	 * @param rowNum
	 */
	private void vertifyARowImportDate(int rowNum) {
		IRow row = this.tblMain.getRow(rowNum);
		if(row==null)  return;
		
		CommerceChanceInfo commerceInfo = (CommerceChanceInfo)row.getUserObject();
		if(commerceInfo==null) {
			commerceInfo = new CommerceChanceInfo();
			SellProjectInfo sellProInfo = (SellProjectInfo)this.prmtSellProject.getValue();
			MoneySysTypeEnum sysType =  (MoneySysTypeEnum)this.comboSysType.getSelectedItem();			
			if(sellProInfo!=null)  commerceInfo.setSellProject(sellProInfo);
			if(sysType!=null)	 commerceInfo.setSysType(sysType);				
		}	
		
		
		try{
			//营销顾问   mapSaleMan 
			if(mapSaleMan==null)  {
				UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
				mapSaleMan = CommerceHelper.getPermitUserMap(currentUserInfo);				
			}
		    Object seller = row.getCell("saleMan").getValue();  
	    	if(seller!=null) {
	    		if(seller instanceof String) {   			
	    			UserInfo thisInfo = (UserInfo)mapSaleMan.get(((String)seller).trim());
	    			if(thisInfo!=null){
	    				row.getCell("saleMan").setValue(thisInfo);    				
	    				commerceInfo.setSaleMan(thisInfo);
	    			}else{
	    				commerceInfo.setSaleMan(null);
	    				setRowStyleLockStatus(row,"'营销顾问'无法识别!\r\n");
	    				return;
	    			}        
	    		}else if(seller instanceof UserInfo){
	    			commerceInfo.setSaleMan((UserInfo)seller);    
	    		}
	    	}else{
	    		commerceInfo.setSaleMan(null);
				setRowStyleLockStatus(row,"'营销顾问'必须录入'!\r\n");
				return;
	    	}
	    	
	    	
	    	//客户名称 fdcCustomer
			//客户名称   --数量太大,直接查询     
			Object customer = row.getCell("fdcCustomer").getValue();
			if(customer!=null) {
				if(customer instanceof String)  {
					String tempPhone = (String)row.getCell("phoneNumber").getValue();	
					FDCCustomerCollection custColl = null;

					try {					
						EntityViewInfo view = new EntityViewInfo();
						FilterInfo filter = new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
						filter.getFilterItems().add(new FilterItemInfo("name",customer));
						if(tempPhone!=null && !tempPhone.trim().equals(""))
							filter.getFilterItems().add(new FilterItemInfo("phone","%"+tempPhone+"%",CompareType.LIKE));
						
						try {
							UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
							String sqlPerSalmanIdStr = CommerceHelper.getPermitSaleManIdSql(currentUserInfo);
							filter.getFilterItems().add(new FilterItemInfo("salesman.id",sqlPerSalmanIdStr,CompareType.INNER));
						} catch (EASBizException e) {
							e.printStackTrace();
							SysUtil.abort();
						}	
			
						view.setFilter(filter);
						custColl = FDCCustomerFactory.getRemoteInstance().getFDCCustomerCollection(view);
					} catch (BOSException e) {
						this.handUIException(e);
						this.abort();	
					}	
					
					if(custColl!=null && custColl.size()==1){
						row.getCell("fdcCustomer").setValue(custColl.get(0));
						commerceInfo.setFdcCustomer(custColl.get(0));
					}else{
						commerceInfo.setFdcCustomer(null);
						
						if(custColl.size()>1)
							setRowStyleLockStatus(row,"'客户名称'有重复，请指定!\r\n");
						else
							setRowStyleLockStatus(row,"'客户名称',无法识别!\r\n");
						return;
					}
				}else if(customer instanceof FDCCustomerInfo){
					FDCCustomerInfo thisInfo = (FDCCustomerInfo)customer;
					commerceInfo.setFdcCustomer(thisInfo);
					//by tim_gao 2011-11-12 根据导入客户加入手机号码
					if(((FDCCustomerInfo) customer).getPhone()!=null && !("").equals(((FDCCustomerInfo) customer).getPhone())){
						row.getCell("phoneNumber").setValue(((FDCCustomerInfo) customer).getPhone());
					}
				}
			}else{
				commerceInfo.setFdcCustomer(null);
				setRowStyleLockStatus(row,"'客户名称'必须录入!\r\n");
				return;
			}
	    	
	    	
			//商机编码  number
			String number = (String)row.getCell("number").getValue();
			if(number==null || number.trim().equals("")) {
				if(!existNumberRule)  {
					setRowStyleLockStatus(row,"'商机编码'不能为空!");
					return;
				}
				commerceInfo.setNumber(null);
			}else if(number.length()>80) {
					setRowStyleLockStatus(row,"'商机编码'字符过长>80!");
					return;
			}
			if(number!=null)				
				commerceInfo.setNumber(number.trim());
			
				
			//商机名称	name
			String comName = (String)row.getCell("name").getValue();
			if(comName==null || comName.trim().equals("")) {
				setRowStyleLockStatus(row,"'商机名称'不能为空!");
				return;
			}else if(comName.length()>80){
				setRowStyleLockStatus(row,"'商机名称'字符过长>80!");
				return;				
			}
			commerceInfo.setName(comName.trim());
			
			
			//商机联系电话	 phoneNumber
			String phoneNumber = (String)row.getCell("phoneNumber").getValue();
			if(phoneNumber==null || phoneNumber.trim().equals("")) {
				setRowStyleLockStatus(row,"'商机联系电话'不能为空!");
				return;
			}if (phoneNumber.length()>80) {
				setRowStyleLockStatus(row,"'商机联系电话'字符过长>80!");
				return;				
			}
			commerceInfo.setPhoneNumber(phoneNumber.trim());
			
		
			//商机级别	commerceLevel
			if(mapCommerceLevel==null) 
				mapCommerceLevel = CommerceHelper.getCommerceLevelMap(); 
		    Object commerceLevel = row.getCell("commerceLevel").getValue();   
	    	if(commerceLevel!=null) {
	    		if(commerceLevel instanceof String) {        			
	    			CommerceLevelInfo thisInfo = (CommerceLevelInfo)mapCommerceLevel.get(((String)commerceLevel).trim());
	    			if(thisInfo!=null){
	    				row.getCell("commerceLevel").setValue(thisInfo);
	    				commerceInfo.setCommerceLevel(thisInfo);
	    			}else{
	    				setRowStyleLockStatus(row,"'商机级别'无法识别!\r\n");
	    				return;
	    			}        
	    		}else if(commerceLevel instanceof CommerceLevelInfo){
	    			commerceInfo.setCommerceLevel((CommerceLevelInfo)commerceLevel);        			
	    		}
	    	}else{
				setRowStyleLockStatus(row,"'商机级别'必须录入!\r\n");
				return;
	    	}
	    
			
	    	//商机日期	commerceDate
	    	Object commerceDate  = row.getCell("commerceDate").getValue();	
	    	if(commerceDate!=null && commerceDate instanceof String){
	    		if(CommerceHelper.isDateFormat(commerceDate.toString(),"yyyy-MM-dd")) {
	    			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date thisDate = formatter.parse(commerceDate.toString());
						commerceInfo.setCommerceDate(thisDate);
	        			row.getCell("commerceDate").setValue(commerceDate);
					} catch (ParseException e) {
						//e.printStackTrace();
		    			setRowStyleLockStatus(row,"'商机日期'无法识别!\r\n");
		    			return;						
					}        			
	    		}else{
	    			setRowStyleLockStatus(row,"'商机日期'无法识别!\r\n");
	    			return;
	    		}
	    	}else if(commerceDate instanceof Date) {
	    		commerceInfo.setCommerceDate((Date)commerceDate);
	    	}else{
				setRowStyleLockStatus(row,"'商机日期'必须录入!\r\n");
				return;
	    	}			
		
	
	    	//意向建筑性质 	intentBuildingPro
			if(mapIntentBuildingPro==null) 
				mapIntentBuildingPro = CommerceHelper.getIntentBuildingPro(); 
		    Object intentBuildingPro = row.getCell("intentBuildingPro").getValue();   
	    	if(intentBuildingPro!=null) {
	    		if(intentBuildingPro instanceof String) {        			
	    			BuildingPropertyInfo thisInfo = (BuildingPropertyInfo)mapIntentBuildingPro.get(((String)intentBuildingPro).trim());
	    			if(thisInfo!=null){
	    				row.getCell("intentBuildingPro").setValue(thisInfo);
	    				commerceInfo.setHopedBuildingProperty(thisInfo);
	    			}else{
	    				setRowStyleLockStatus(row,"'意向建筑性质'无法识别!\r\n");
	    				return;
	    			}        
	    		}else if(intentBuildingPro instanceof BuildingPropertyInfo){
	    			commerceInfo.setHopedBuildingProperty((BuildingPropertyInfo)intentBuildingPro);        			
	    		}
	    	}else{
	    		commerceInfo.setHopedBuildingProperty(null);
	    	}
	    	
	    	
			//意向产品类型    intentProductType
			if(mapIntentProductType==null) 
				mapIntentProductType = CommerceHelper.getIntentProductType(); 
		    Object intentProductType = row.getCell("intentProductType").getValue();   
	    	if(intentProductType!=null) {
	    		if(intentProductType instanceof String) {        			
	    			ProductTypeInfo thisInfo = (ProductTypeInfo)mapIntentProductType.get(((String)intentProductType).trim());
	    			if(thisInfo!=null){
	    				row.getCell("intentProductType").setValue(thisInfo);
	    				commerceInfo.setHopedProductType(thisInfo);
	    			}else{
	    				setRowStyleLockStatus(row,"'意向产品类型'无法识别!\r\n");
	    				return;
	    			}        
	    		}else if(intentProductType instanceof ProductTypeInfo){
	    			commerceInfo.setHopedProductType((ProductTypeInfo)intentProductType);        			
	    		}
	    	}else{
	    		commerceInfo.setHopedProductType(null);
	    	}
	    	
	    	
	    	//产品描述   productDeatil
			if(mapProductDeatil==null)  {
		    	String sellProjectId = null;
		    	SellProjectInfo sellProInfo = (SellProjectInfo)this.prmtSellProject.getValue();
		    	if(sellProInfo!=null)  sellProjectId = sellProInfo.getId().toString();
				mapProductDeatil = CommerceHelper.getProductDeatil(sellProjectId); 			
			}		
		    Object productDeatil = row.getCell("productDeatil").getValue();   
	    	if(productDeatil!=null) {
	    		if(productDeatil instanceof String) {        			
	    			 ProductDetialInfo thisInfo = ( ProductDetialInfo)mapProductDeatil.get(((String)productDeatil).trim());
	    			if(thisInfo!=null){
	    				row.getCell("productDeatil").setValue(thisInfo);
	    				commerceInfo.setProductDetail(thisInfo);
	    			}else{
	    				setRowStyleLockStatus(row,"'产品描述'无法识别!\r\n");
	    				return;
	    			}        
	    		}else if(productDeatil instanceof ProductDetialInfo){
	    			commerceInfo.setProductDetail((ProductDetialInfo)productDeatil);        			
	    		}
	    	}else{
	    		commerceInfo.setProductDetail(null);
	    	}
	    		
	    	
	    	
	    	
	    	//意向朝向	intentDirection
			if(mapIntentDirection==null) 
				mapIntentDirection = CommerceHelper.getIntentDirection(); 
		    Object intentDirection = row.getCell("intentDirection").getValue();   
	    	if(intentDirection!=null) {
	    		if(intentDirection instanceof String) {        			
	    			HopedDirectionInfo thisInfo = (HopedDirectionInfo)mapIntentDirection.get(((String)intentDirection).trim());
	    			if(thisInfo!=null){
	    				row.getCell("intentDirection").setValue(thisInfo);
	    				commerceInfo.setHopedDirection(thisInfo);
	    			}else{
	    				setRowStyleLockStatus(row,"'意向朝向'无法识别!\r\n");
	    				return;
	    			}        
	    		}else if(intentDirection instanceof HopedDirectionInfo){
	    			commerceInfo.setHopedDirection((HopedDirectionInfo)intentDirection);        			
	    		}
	    	}else{
	    		commerceInfo.setHopedDirection(null);
	    	}
	    	
	    	
	    	//面积需求  intentArea
			if(mapIntentArea==null) 
				mapIntentArea = CommerceHelper.getIntentArea(); 
		    Object intentArea = row.getCell("intentArea").getValue();   
	    	if(intentArea!=null) {
	    		if(intentArea instanceof String) {        			
	    			AreaRequirementInfo thisInfo = (AreaRequirementInfo)mapIntentArea.get(((String)intentArea).trim());
	    			if(thisInfo!=null){
	    				row.getCell("intentArea").setValue(thisInfo);
	    				commerceInfo.setHopedAreaRequirement(thisInfo);
	    			}else{
	    				setRowStyleLockStatus(row,"'面积需求'无法识别!\r\n");
	    				return;
	    			}        
	    		}else if(intentArea instanceof AreaRequirementInfo){
	    			commerceInfo.setHopedAreaRequirement((AreaRequirementInfo)intentArea);        			
	    		}
	    	}else{
	    		commerceInfo.setHopedAreaRequirement(null);
	    	}
	    	
	    	
	    	//景观需求  intentSight
			if(mapIntentSight==null) 
				mapIntentSight = CommerceHelper.getIntentSight(); 
		    Object intentSight = row.getCell("intentSight").getValue();   
	    	if(intentSight!=null) {
	    		if(intentSight instanceof String) {        			
	    			SightRequirementInfo thisInfo = (SightRequirementInfo)mapIntentSight.get(((String)intentSight).trim());
	    			if(thisInfo!=null){
	    				row.getCell("intentSight").setValue(thisInfo);
	    				commerceInfo.setHopedSightRequirement(thisInfo);
	    			}else{
	    				setRowStyleLockStatus(row,"'景观需求'无法识别!\r\n");
	    				return;
	    			}        
	    		}else if(intentSight instanceof SightRequirementInfo){
	    			commerceInfo.setHopedSightRequirement((SightRequirementInfo)intentSight);        			
	    		}
	    	}else{
	    		commerceInfo.setHopedSightRequirement(null);
	    	}
			
	    	
	    	//房屋形式  roomForm
			if(mapRoomForm==null) 
				mapRoomForm = CommerceHelper.getRoomForm(); 
		    Object roomForm = row.getCell("roomForm").getValue();   
	    	if(roomForm!=null) {
	    		if(roomForm instanceof String) {        			
	    			RoomFormInfo thisInfo = (RoomFormInfo)mapRoomForm.get(((String)roomForm).trim());
	    			if(thisInfo!=null){
	    				row.getCell("roomForm").setValue(thisInfo);
	    				commerceInfo.setRoomForm(thisInfo);
	    			}else{
	    				setRowStyleLockStatus(row,"'房屋形式'无法识别!\r\n");
	    				return;
	    			}        
	    		}else if(roomForm instanceof RoomFormInfo){
	    			commerceInfo.setRoomForm((RoomFormInfo)roomForm);        			
	    		}
	    	}else{
	    		commerceInfo.setRoomForm(null);
	    	}
	    	
	    	
	    	//户型需求  intentRoomType
			if(mapIntentRoomType==null) 
				mapIntentRoomType = CommerceHelper.getIntentRoomType(); 
		    Object intentRoomType = row.getCell("intentRoomType").getValue();   
	    	if(intentRoomType!=null) {
	    		if(intentRoomType instanceof String) {        			
	    			RoomModelTypeInfo thisInfo = (RoomModelTypeInfo)mapIntentRoomType.get(((String)intentRoomType).trim());
	    			if(thisInfo!=null){
	    				row.getCell("intentRoomType").setValue(thisInfo);
	    				commerceInfo.setHopedRoomModelType(thisInfo);
	    			}else{
	    				setRowStyleLockStatus(row,"'户型需求'无法识别!\r\n");
	    				return;
	    			}        
	    		}else if(intentRoomType instanceof RoomModelTypeInfo){
	    			commerceInfo.setHopedRoomModelType((RoomModelTypeInfo)intentRoomType);        			
	    		}
	    	}else{
	    		commerceInfo.setHopedRoomModelType(null);
	    	}
	    	
	    	
	    	//意向单价  hopedUnitPrice
			if(mapHopedUnitPrice==null) 
				mapHopedUnitPrice = CommerceHelper.getHopedUnitPrice(); 
		    Object hopedUnitPrice = row.getCell("hopedUnitPrice").getValue();   
	    	if(hopedUnitPrice!=null) {
	    		if(hopedUnitPrice instanceof String) {        			
	    			HopedUnitPriceInfo thisInfo = (HopedUnitPriceInfo)mapHopedUnitPrice.get(((String)hopedUnitPrice).trim());
	    			if(thisInfo!=null){
	    				row.getCell("hopedUnitPrice").setValue(thisInfo);
	    				commerceInfo.setHopedUnitPrice(thisInfo);
	    			}else{
	    				setRowStyleLockStatus(row,"'意向单价'无法识别!\r\n");
	    				return;
	    			}        
	    		}else if(hopedUnitPrice instanceof HopedUnitPriceInfo){
	    			commerceInfo.setHopedUnitPrice((HopedUnitPriceInfo)hopedUnitPrice);        			
	    		}
	    	}else{
	    		commerceInfo.setHopedUnitPrice(null);
	    	}
	    	
	    	
	    	
	    	//意向总价  hopedTotalPrices
			if(mapHopedTotalPrices==null) 
				mapHopedTotalPrices = CommerceHelper.getHopedTotalPrices(); 
		    Object hopedTotalPrices = row.getCell("hopedTotalPrices").getValue();   
	    	if(hopedTotalPrices!=null) {
	    		if(hopedTotalPrices instanceof String) {        			
	    			HopedTotalPricesInfo thisInfo = (HopedTotalPricesInfo)mapHopedTotalPrices.get(((String)hopedTotalPrices).trim());
	    			if(thisInfo!=null){
	    				row.getCell("hopedTotalPrices").setValue(thisInfo);
	    				commerceInfo.setHopedTotalPrices(thisInfo);
	    			}else{
	    				setRowStyleLockStatus(row,"'意向总价'无法识别!\r\n");
	    				return;
	    			}        
	    		}else if(hopedTotalPrices instanceof HopedTotalPricesInfo){
	    			commerceInfo.setHopedTotalPrices((HopedTotalPricesInfo)hopedTotalPrices);        			
	    		}
	    	}else{
	    		commerceInfo.setHopedTotalPrices(null);
	    	}
	    	
	    	
	    	//意向楼层  hopedFloor
			if(mapHopedFloor==null) 
				mapHopedFloor = CommerceHelper.getHopedFloor(); 
		    Object hopedFloor = row.getCell("hopedFloor").getValue();   
	    	if(hopedFloor!=null) {
	    		if(hopedFloor instanceof String) {        			
	    			HopedFloorInfo thisInfo = (HopedFloorInfo)mapHopedFloor.get(((String)hopedFloor).trim());
	    			if(thisInfo!=null){
	    				row.getCell("hopedFloor").setValue(thisInfo);
	    				commerceInfo.setHopedFloor(thisInfo);
	    			}else{
	    				setRowStyleLockStatus(row,"'意向楼层'无法识别!\r\n");
	    				return;
	    			}        
	    		}else if(hopedFloor instanceof HopedFloorInfo){
	    			commerceInfo.setHopedFloor((HopedFloorInfo)hopedFloor);        			
	    		}
	    	}else{
	    		commerceInfo.setHopedFloor(null);
	    	}
	    	
	    	
	    	//商机原因  buyHouseReason
			if(mapBuyHouseReason==null) 
				mapBuyHouseReason = CommerceHelper.getBuyHouseReason(); 
		    Object buyHouseReason = row.getCell("buyHouseReason").getValue();   
	    	if(buyHouseReason!=null) {
	    		if(buyHouseReason instanceof String) {        			
	    			BuyHouseReasonInfo thisInfo = (BuyHouseReasonInfo)mapBuyHouseReason.get(((String)buyHouseReason).trim());
	    			if(thisInfo!=null){
	    				row.getCell("buyHouseReason").setValue(thisInfo);
	    				commerceInfo.setBuyHouseReason(thisInfo);
	    			}else{
	    				setRowStyleLockStatus(row,"'商机原因'无法识别!\r\n");
	    				return;
	    			}        
	    		}else if(buyHouseReason instanceof BuyHouseReasonInfo){
	    			commerceInfo.setBuyHouseReason((BuyHouseReasonInfo)buyHouseReason);        			
	    		}
	    	}else{
	    		commerceInfo.setBuyHouseReason(null);
	    	}
	    	
	    	
	    	//意向强度  hopedPitch
			if(mapHopedPitch==null) 
				mapHopedPitch = CommerceHelper.getHopePitch(); 
		    Object hopedPitch = row.getCell("hopedPitch").getValue();   
	    	if(hopedPitch!=null) {
	    		if(hopedPitch instanceof String) {        			
	    			HopePitchInfo thisInfo = (HopePitchInfo)mapHopedPitch.get(((String)hopedPitch).trim());
	    			if(thisInfo!=null){
	    				row.getCell("hopedPitch").setValue(thisInfo);
	    				commerceInfo.setHopedPitch(thisInfo);
	    			}else{
	    				setRowStyleLockStatus(row,"'意向强度'无法识别!\r\n");
	    				return;
	    			}        
	    		}else if(hopedPitch instanceof HopePitchInfo){
	    			commerceInfo.setHopedPitch((HopePitchInfo)hopedPitch);        			
	    		}
	    	}else{
	    		commerceInfo.setHopedPitch(null);
	    	}
	    	
	    	
	    	//置业目的   commerceIntention
	    	if(mapCommerceIntention==null)
	    		mapCommerceIntention = CommerceHelper.getCommerceIntentionMap();
	    	Object commerceIntention = row.getCell("commerceIntention").getValue();
	    	if(commerceIntention!=null) {
	    		if(commerceIntention instanceof String) {
	    			CommerceIntentionEnum thisEnum = (CommerceIntentionEnum)mapCommerceIntention.get(((String)commerceIntention).trim());
	    			if(thisEnum!=null){
	    				row.getCell("commerceIntention").setValue(thisEnum);
	    				commerceInfo.setCommerceIntention(thisEnum);
	    			}else{
	    				setRowStyleLockStatus(row,"'置业目的'无法识别!\r\n");
	    				return;
	    			}
	    		}else if(commerceIntention instanceof CommerceIntentionEnum) {
	    			commerceInfo.setCommerceIntention((CommerceIntentionEnum)commerceIntention);
	    		}    		
	    	}else{
	    		commerceInfo.setCommerceIntention(null);
	    	}
	    	
	    	
	    	
	    	//预计日期	commerceDate
	    	Object intendingDate  = row.getCell("intendingDate").getValue();	
	    	if(intendingDate!=null && intendingDate instanceof String){
	    		if(CommerceHelper.isDateFormat(intendingDate.toString(),"yyyy-MM-dd")) {
	    			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date thisDate = formatter.parse(intendingDate.toString());
						commerceInfo.setIntendingDate(thisDate);
	        			row.getCell("intendingDate").setValue(intendingDate);
					} catch (ParseException e) {
						//e.printStackTrace();
		    			setRowStyleLockStatus(row,"'预计日期'无法识别!\r\n");
		    			return;
					}        			
	    		}else{
	    			setRowStyleLockStatus(row,"'预计日期'无法识别!\r\n");
	    			return;
	    		}
	    	}else if(intendingDate instanceof Date) {
	    		commerceInfo.setIntendingDate((Date)intendingDate);
	    	}else{
	    		commerceInfo.setIntendingDate(null);
	    	}
	    	
	    	//预计成交额  intendingMoney
	    	String intendingMoney = (String)row.getCell("intendingMoney").getValue();
	    	if(intendingMoney!=null) {
	    		if(CommerceHelper.isNumber(intendingMoney))  {
		    		if(intendingMoney.length()>10) {
		    			setRowStyleLockStatus(row,"'预计成交额'数字太大!\r\n");
		    			return;
		    		}
	        		BigDecimal intentMoney = new BigDecimal(intendingMoney);
	        		commerceInfo.setIntendingMoney(intentMoney);
	    		}   
	    	}else{
	    		commerceInfo.setIntendingMoney(null);
	    	}
	    		
	    	
	    	//首付比例  firstPayProportion
			if(mapFirstPayProportion==null) 
				mapFirstPayProportion = CommerceHelper.getFirstPayProportion(); 
		    Object firstPayProportion = row.getCell("firstPayProportion").getValue();   
	    	if(firstPayProportion!=null) {
	    		if(firstPayProportion instanceof String) {        			
	    			FirstPayProportionInfo thisInfo = (FirstPayProportionInfo)mapFirstPayProportion.get(((String)firstPayProportion).trim());
	    			if(thisInfo!=null){
	    				row.getCell("firstPayProportion").setValue(thisInfo);
	    				commerceInfo.setFirstPayProportion(thisInfo);
	    			}else{
	    				setRowStyleLockStatus(row,"'首付比例'无法识别!\r\n");
	    				return;
	    			}        
	    		}else if(firstPayProportion instanceof FirstPayProportionInfo){
	    			commerceInfo.setFirstPayProportion((FirstPayProportionInfo)firstPayProportion);        			
	    		}
	    	}else{
	    		commerceInfo.setFirstPayProportion(null);
	    	}
	    	
	    	
	    	//描述	description
	    	String description = (String)row.getCell("description").getValue();
	    	if(description!=null) {	    		
	    		if(description.length()>80)  {
					setRowStyleLockStatus(row,"'描述'字符过长>80!\r\n");
					return;
	    		}
	    		commerceInfo.setDescription(description);
	    	}else{
	    		commerceInfo.setDescription(null);
	    	}
	    	
	    	
	    	//成交条件	bargainOnCondition
	    	String bargainOnCondition = (String)row.getCell("bargainOnCondition").getValue();
	    	if(bargainOnCondition!=null) {
	    		if(bargainOnCondition.length()>80)  {
					setRowStyleLockStatus(row,"'成交条件'字符过长>80!\r\n");
					return;
	    		}    		
	    		commerceInfo.setBargainOnCondition(bargainOnCondition);
	    	}else{
	    		commerceInfo.setBargainOnCondition(null);
	    	}
		}catch(BOSException e) {
			e.printStackTrace();
			this.abort();
		}
    	
    	
    	row.setUserObject(commerceInfo);
    	setRowStyleLockStatus(row,"校验通过");
	}
	
	
	private void setRowStyleLockStatus(IRow row,String str ) {
		if(str==null || str.trim().equals("")) return;
		
		if(str.indexOf("校验通过")>=0 || str.indexOf("保存成功")>=0) {
			row.getStyleAttributes().setBackground(Color.WHITE);
			row.getCell("importStatus").getStyleAttributes().setBackground(Color.WHITE);			
			if(str.indexOf("保存成功")>=0) 
			 row.getStyleAttributes().setLocked(true);
		}else{			
			row.getStyleAttributes().setLocked(false);
			
			row.getCell("importStatus").getStyleAttributes().setLocked(true);
			row.getCell("importStatus").getStyleAttributes().setBackground(Color.RED);
		}
		row.getCell("importStatus").setValue(str);			
	}
	
	
	/**
	 *设置不可导入条数 新增条数 和 修改条数
	 */
	private void setTheTextCount() {
		//Map modifyRowMap = new HashMap();  //要修改的行的映射
		Map valuidRowMap = new HashMap();  //可导入的行的映射
		
		int invalildCount = 0;
		for(int i=0;i<this.tblMain.getRowCount();i++) {
			IRow row = this.tblMain.getRow(i);			
			if(isHasErrorRow(row))
				invalildCount ++;
			else{
				String number = (String)row.getCell("number").getValue();				
				if(number!=null && !number.trim().equals("")) {
					number = number.trim();
					if(valuidRowMap.get(number)==null)  {
						valuidRowMap.put(number,row);
						row.getCell("importStatus").setValue("校验通过,新增数据");
					}else{                 //可能存在导入数据里的编码有重复的
						IRow dupRow = (IRow)valuidRowMap.get(number);
						this.setRowStyleLockStatus(row,"编码 和 行"+dupRow.getRowIndex()+1+ "的编码重复!");
					
					}					
				}				
			}
		}
		this.txtInvaluidCount.setText(String.valueOf(invalildCount));
		
		int modifyCount = 0;
		//只需要针对无错的行
		//若是修改 ，则一定存在编码 ，且编码在库中存在
		Set numberSet = new HashSet();
		numberSet.add("null");
		numberSet.addAll(valuidRowMap.keySet());		
		EntityViewInfo view = new EntityViewInfo(); 
		FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number",numberSet,CompareType.INCLUDE));
		view.setFilter(filter);
		try {
			CommerceChanceCollection trackColl = CommerceChanceFactory.getRemoteInstance().getCommerceChanceCollection(view);
			for(int i=0;i<trackColl.size();i++)  {
				CommerceChanceInfo thisInfo = trackColl.get(i);
				//找到要修改的行，并把id填充到该行
				IRow mdRow = (IRow)valuidRowMap.get(thisInfo.getNumber());
				mdRow.getCell("id").setValue(thisInfo.getId().toString());
				((CommerceChanceInfo)mdRow.getUserObject()).setId(thisInfo.getId());
				mdRow.getCell("importStatus").setValue("校验通过,修改数据");
			}		
			modifyCount = trackColl.size();
		} catch (BOSException e) {
			this.handUIException(e);
			this.abort();	
		}
		this.txtModifyCount.setText(String.valueOf(modifyCount));
		this.txtAddNewCount.setText(String.valueOf(this.tblMain.getRowCount()-invalildCount-modifyCount));
	}
	
	private boolean isHasErrorRow(IRow row) {
		boolean hasErrorRow = false;
		String importStatus = (String)row.getCell("importStatus").getValue();
		if(importStatus!=null && !importStatus.trim().equals("")) {
			if(importStatus.indexOf("校验通过")<0 && importStatus.indexOf("保存成功")<0)
				hasErrorRow = true;			
		}
		return hasErrorRow;
	}
	
	
	protected void prmtSellProject_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtSellProject_dataChanged(e);
		
		
		mapProductDeatil = null;		
	}
	
	public int getRowCountFromDB(){
		return -1;
	}

}