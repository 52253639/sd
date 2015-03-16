/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.ShareModelEnum;
import com.kingdee.eas.fdc.sellhouse.ShareSellerCollection;
import com.kingdee.eas.fdc.sellhouse.ShareSellerFactory;
import com.kingdee.eas.fdc.sellhouse.ShareSellerInfo;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlatCollection;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlatFactory;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlatInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.tenancy.client.TenancyClientHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class CustomerShareUI extends AbstractCustomerShareUI
{
	private static final Logger logger = CoreUIObject.getLogger(CustomerShareUI.class);
	private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	private UserInfo info = SysContext.getSysContext().getCurrentUserInfo();
	/**
	 * output class constructor
	 */
	public CustomerShareUI() throws Exception
	{
		super();
	}
	
    //因为继承的editUI,所以必须重写父类方法为空，不重写该方法就提示传入编辑的参数ID为空
	protected void inOnload() throws Exception {
		// super.inOnload();
	}
	

	
	public void onLoad() throws Exception {
		initControl();
		initTblSharePerson();
		super.onLoad();		
		UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
		List idList = (List)this.getUIContext().get("idList");
		//如果是选择多个用户进行共享操作
		if(idList != null && idList.size()>0)
		{
			EntityViewInfo view = new EntityViewInfo();
			view.getSorter().add(new SorterItemInfo("number"));
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems()
			.add(
					new FilterItemInfo("id",FDCHelper.list2Set(idList), CompareType.INCLUDE));
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("salesman.*");
			view.getSelector().add("shareSellerList.*");
			view.getSelector().add("shareSellerList.seller.*");
			FDCCustomerCollection fdcCustomerColl = FDCCustomerFactory
			.getRemoteInstance().getFDCCustomerCollection(view);
			for(int i=0;i<fdcCustomerColl.size();i++)
			{
				FDCCustomerInfo fdcCustomerInfo = fdcCustomerColl.get(i);				
				this.tblCustomer.checkParsed();
				IRow row = this.tblCustomer.addRow();
				showFDCCustomerInfo(fdcCustomerInfo,row);
			}
		}else
		{
			//在编辑界面里进行共享操作，也就是只有一个用户
			String fdcCustomerID = (String)this.getUIContext().get("id");
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("salesman.*");
			sels.add("shareSellerList.*");
			sels.add("shareSellerList.seller.*");
			FDCCustomerInfo fdcCustomerInfo = FDCCustomerFactory
			.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(BOSUuid.read(fdcCustomerID)), sels);
			this.tblCustomer.checkParsed();
			IRow row = this.tblCustomer.addRow();
			showFDCCustomerInfo(fdcCustomerInfo,row);
		}
		this.f7SharePerson.setValue(userInfo);
		this.f7SharePerson.setEnabled(false);
		this.pkShareDate.setEnabled(false);
	}
	
	/**
     * 显示选择的客户信息
     */
	private void showFDCCustomerInfo(FDCCustomerInfo fdcCustomerInfo,IRow row)
	{
		row.setUserObject(fdcCustomerInfo);
		UserInfo userInfo = fdcCustomerInfo.getSalesman();
		row.getCell("seller").setValue(userInfo.getName());
		row.getCell("seller").getStyleAttributes().setLocked(true);
		row.getCell("customerName").setValue(fdcCustomerInfo.getName());
		row.getCell("customerName").getStyleAttributes().setLocked(true);
		row.getCell("customerNumber").setValue(fdcCustomerInfo.getNumber());
		row.getCell("customerNumber").getStyleAttributes().setLocked(true);
		row.getCell("sex").setValue(fdcCustomerInfo.getSex());
		row.getCell("sex").getStyleAttributes().setLocked(true);
		row.getCell("bookDate").setValue(fdcCustomerInfo.getCreateTime());
		row.getCell("bookDate").getStyleAttributes().setLocked(true);
		row.getCell("tradeTime").setValue(new Long(fdcCustomerInfo.getTradeTime()));
		row.getCell("tradeTime").getStyleAttributes().setLocked(true);
	}
	
	 /**
     * 隐藏多余按钮
     */
	private void initControl() {
		this.btnPrint.setEnabled(true);
		this.btnPrintPreview.setEnabled(true);
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.menuItemSave.setVisible(false);
		this.menuItemSubmit.setVisible(false);
		this.rMenuItemSubmit.setVisible(false);
		this.rMenuItemSubmitAndAddNew.setVisible(false);
		this.rMenuItemSubmitAndPrint.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.rMenuItemSubmitAndAddNew.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnSave.setVisible(false);
		this.btnSubmit.setVisible(false);
//		this.btnSubmit.setVisible(true);
//		this.btnSubmit.setEnabled(true);
		this.btnCopy.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnLast.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
	}
	
	/**
     * 初始化待共享销售顾问列表
     */
	private void initTblSharePerson() {
		this.tblSharePerson.checkParsed();
		this.tblSharePerson
		.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);	
		
		KDCheckBox chkBox = new KDCheckBox();
		ICellEditor checkBox = new KDTDefaultCellEditor(chkBox);
		this.tblSharePerson.getColumn("isAgainShare").setEditor(checkBox);
		
		chkBox = new KDCheckBox();
		checkBox = new KDTDefaultCellEditor(chkBox);
		this.tblSharePerson.getColumn("isUpdate").setEditor(checkBox);
		
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}
	
	protected IObjectValue createNewData() {
		return null;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return FDCCustomerFactory.getRemoteInstance();
	}
	
	//
	public static void showUI(IUIObject ui,List idList) throws EASBizException, BOSException
	{
		UIContext uiContext = new UIContext(ui);
		uiContext.put("idList",idList);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(CustomerShareUI.class.getName(), uiContext, null, STATUS_VIEW);
		uiWindow.show();
	}
	
	public static void showEditUI(IUIObject ui,String id) throws EASBizException, BOSException
	{
		UIContext uiContext = new UIContext(ui);
		uiContext.put("id",id);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(CustomerShareUI.class.getName(), uiContext, null, STATUS_VIEW);
		uiWindow.show();
	}
	
	//增加要共享的营销单元
	protected void btnAddUnit_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddUnit_actionPerformed(e);
        KDCommonPromptDialog dlg = new KDCommonPromptDialog();
        
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.fdc.tenancy.app.MarketingUnitQuery")));
		dlg.setEnabledMultiSelection(true);
        dlg.show();
        
        Object[] object = (Object[])dlg.getData();
		if(object !=null && object.length>0)
        {
        	 IRow row = null;
             List list = new ArrayList();
             for(int j=0;j<tblSharePerson.getRowCount();j++)
         	{
             	IRow row2 = tblSharePerson.getRow(j);
             	ShareModelEnum shareModel = (ShareModelEnum)row2.getCell("shareModel").getValue();
             	if(ShareModelEnum.shareMarket.equals(shareModel))
             	{
             		MarketingUnitInfo marketInfo = (MarketingUnitInfo)row2.getCell("shareObject").getUserObject();
         			if(marketInfo!=null)
         			{
         				list.add(marketInfo.getId().toString());
         			}
             	}
     			
         	}
             boolean boo = false;
             for(int i=0;i<object.length;i++)
             {
            	 MarketingUnitInfo marketInfo = (MarketingUnitInfo)object[i];
             	if(TenancyClientHelper.isInclude(marketInfo.getId().toString(),list))
             	{
             		MsgBox.showInfo("该营销单元已经存在不要重复添加！");
             		boo = true;
             		return;
             	}
//             	else
//             	{
//             	this.tblSharePerson.checkParsed();
//             	row = this.tblSharePerson.addRow(i);
//             	//显示销售组织信息
//             	showTblShareMarket(row,marketInfo);
//             	}
             }if(boo==false)
             {
            	 for(int i=0;i<object.length;i++)
                 {
                	 MarketingUnitInfo marketInfo = (MarketingUnitInfo)object[i];
                		this.tblSharePerson.checkParsed();
                     	row = this.tblSharePerson.addRow(i);
                     	//显示销售组织信息
                     	showTblShareMarket(row,marketInfo);
                 }
             }
        }
	}
	
	//增加要共享的销售组织
	protected void btnAddOrgUnit_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnAddOrgUnit_actionPerformed(e);
		KDCommonPromptDialog dlg = new KDCommonPromptDialog();
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.basedata.org.app.FullOrgUnitQuery")));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("partSale.isBizUnit","true"));
		view.setFilter(filter);
		dlg.setEntityViewInfo(view);
		dlg.setTitle("销售组织查询");
		dlg.setName("aaa");
		//设置多选
		dlg.setEnabledMultiSelection(true);
		dlg.show();
		Object[] object = (Object[])dlg.getData();
		if(object !=null && object.length>0)
        {
        	 IRow row = null;
             List list = new ArrayList();
             for(int j=0;j<tblSharePerson.getRowCount();j++)
         	{
             	IRow row2 = tblSharePerson.getRow(j);
             	ShareModelEnum shareModel = (ShareModelEnum)row2.getCell("shareModel").getValue();
             	if(shareModel.equals(ShareModelEnum.shareOrgUnit))
             	{
             		FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo)row2.getCell("shareObject").getUserObject();
         			if(orgUnitInfo!=null)
         			{
         				list.add(orgUnitInfo.getId().toString());
         			}
             	}		
         	}
             boolean boo = false;
             for(int i=0;i<object.length;i++)
             {
            	 FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo)object[i];
             	if(TenancyClientHelper.isInclude(orgUnitInfo.getId().toString(),list))
             	{
             		MsgBox.showInfo("该销售组织已经存在不要重复添加！");
             		boo = true;
             		return;
             	}
//             	else
//             	{
//             	this.tblSharePerson.checkParsed();
//             	row = this.tblSharePerson.addRow(i);
//             	//显示销售组织信息
//             	showTblShareOrgUnit(row,orgUnitInfo);
//             	}
             }
             if(boo==false)
             {
            	 for(int i=0;i<object.length;i++)
                 {
                	 FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo)object[i];
                	 this.tblSharePerson.checkParsed();
                  	 row = this.tblSharePerson.addRow(i);
                  	 //显示销售组织信息
                  	 showTblShareOrgUnit(row,orgUnitInfo);
                 }
             }
        }
	}
	
	/**
     * 增加要共享的营销顾问
     */
	protected void btnAddSharePerson_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddSharePerson_actionPerformed(e);
		KDCommonPromptDialog dlg = new KDCommonPromptDialog();
		
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.F7UserQuery")));
		
		
		//设置多选
		dlg.setEnabledMultiSelection(true);
        dlg.show();
        Object[] object = (Object[])dlg.getData();
        if(object !=null && object.length>0)
        {
        	 IRow row = null;
             List list = new ArrayList();
             for(int j=0;j<tblSharePerson.getRowCount();j++)
         	{
             	IRow row2 = tblSharePerson.getRow(j);
             	ShareModelEnum shareModel = (ShareModelEnum)row2.getCell("shareModel").getValue();
             	if(ShareModelEnum.sharePerson.equals(shareModel))
             	{
             		UserInfo shareSellerInfo = (UserInfo)row2.getCell("shareObject").getUserObject();
         			if(shareSellerInfo!=null)
         			{
         				list.add(shareSellerInfo.getId().toString());
         			}
             	}    			
         	}
             boolean boo = false;
             for(int i=0;i<object.length;i++)
             {
             	UserInfo userInfo = (UserInfo)object[i];
             	if(TenancyClientHelper.isInclude(userInfo.getId().toString(),list))
             	{
             		MsgBox.showInfo("该营销顾问已经存在不要重复添加！");
             		boo = true;
             		return;
             	}
//             	else
//             	{
//             	this.tblSharePerson.checkParsed();
//             	row = this.tblSharePerson.addRow(i);
//             	//显示营销顾问信息
//             	showTblSharePerson(row,userInfo);
//             	}
             }if(boo==false)
             {
            	 for(int i=0;i<object.length;i++)
                 {
                 	UserInfo userInfo = (UserInfo)object[i];
            	    this.tblSharePerson.checkParsed();
              	    row = this.tblSharePerson.addRow(i);
              	    //显示营销顾问信息
                 	showTblSharePerson(row,userInfo);
                 }
             }
        }
	}
	
	private void showTblShareMarket(IRow row,MarketingUnitInfo marketInfo)
	{
		row.getCell("shareModel").setValue(ShareModelEnum.shareMarket);
		row.getCell("shareModel").getStyleAttributes().setLocked(true);
		row.getCell("shareObject").setValue(marketInfo.getName());
		row.getCell("shareObject").setUserObject(marketInfo);
		row.getCell("shareObject").getStyleAttributes().setLocked(true);
		row.getCell("operationPerson").setValue(info.getName());
		row.getCell("operationPerson").setUserObject(info);
		row.getCell("operationPerson").getStyleAttributes().setLocked(true);
		row.getCell("isAgainShare").setValue(new Boolean(false));
		row.getCell("isUpdate").setValue(new Boolean(false));
		row.getCell("isAgainShare").getStyleAttributes().setLocked(true);
		//row.getCell("isUpdate").getStyleAttributes().setLocked(true);
		ShareSellerInfo shareSellerInfo = new ShareSellerInfo();
		row.setUserObject(shareSellerInfo);
	}
	
	private void showTblShareOrgUnit(IRow row,FullOrgUnitInfo orgUnitInfo)
	{
		row.getCell("shareModel").setValue(ShareModelEnum.shareOrgUnit);
		row.getCell("shareModel").getStyleAttributes().setLocked(true);
		row.getCell("shareObject").setValue(orgUnitInfo.getName());
		row.getCell("shareObject").setUserObject(orgUnitInfo);
		row.getCell("shareObject").getStyleAttributes().setLocked(true);
		row.getCell("operationPerson").setValue(info.getName());
		row.getCell("operationPerson").setUserObject(info);
		row.getCell("operationPerson").getStyleAttributes().setLocked(true);
		row.getCell("isAgainShare").setValue(new Boolean(false));
		row.getCell("isUpdate").setValue(new Boolean(false));
		row.getCell("isAgainShare").getStyleAttributes().setLocked(true);
		//row.getCell("isUpdate").getStyleAttributes().setLocked(true);
		ShareSellerInfo shareSellerInfo = new ShareSellerInfo();
		row.setUserObject(shareSellerInfo);
	}
	
	/**
     * 显示营销顾问信息
     */
	private void showTblSharePerson(IRow row,UserInfo userInfo)
	{
		//显示名称，值设在userObject中
		row.getCell("shareModel").setValue(ShareModelEnum.sharePerson);
		row.getCell("shareModel").getStyleAttributes().setLocked(true);
		row.getCell("shareObject").setValue(userInfo.getName());
		row.getCell("shareObject").setUserObject(userInfo);
		row.getCell("shareObject").getStyleAttributes().setLocked(true);
		row.getCell("operationPerson").setValue(info.getName());
		row.getCell("operationPerson").setUserObject(info);
		row.getCell("operationPerson").getStyleAttributes().setLocked(true);
		row.getCell("isAgainShare").setValue(new Boolean(false));
		row.getCell("isUpdate").setValue(new Boolean(false));
		ShareSellerInfo shareSellerInfo = new ShareSellerInfo();
		row.setUserObject(shareSellerInfo);
	}
	
	/**
     * 删除共享销售顾问记录
     */
	protected void btnDeleteSharePerson_actionPerformed(ActionEvent e) throws Exception {
		super.btnDeleteSharePerson_actionPerformed(e);
		int activeRowIndex = this.tblSharePerson.getSelectManager()
		.getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.tblSharePerson.getRowCount();
		}
		this.tblSharePerson.removeRow(activeRowIndex);
	}

	/**
     * 删除客户记录
     */
	protected void btnDelShareCustomer_actionPerformed(ActionEvent e) throws Exception {
		super.btnDelShareCustomer_actionPerformed(e);
		int activeRowIndex = this.tblCustomer.getSelectManager()
		.getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.tblCustomer.getRowCount();
		}
		this.tblCustomer.removeRow(activeRowIndex);
	}
	
	/**
     * 提交结果
     */
	protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {
		verifyInput(e);
		List idList = (List)this.getUIContext().get("idList");
		//SHEHelper.customerShare(idList);
		//如果有多个客户
		if(idList!= null && idList.size()>0)
		{
			EntityViewInfo view = new EntityViewInfo();
			view.getSorter().add(new SorterItemInfo("number"));
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems()
			.add(
					new FilterItemInfo("id",FDCHelper.list2Set(idList), CompareType.INCLUDE));
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("salesman.*");
			view.getSelector().add("shareSellerList.*");
			view.getSelector().add("shareSellerList.seller.*");
			view.getSelector().add("shareSellerList.marketingUnit.*");
			view.getSelector().add("shareSellerList.operationPerson.*");
			view.getSelector().add("shareSellerList.orgUnit.*");
			view.getSelector().add("linkmanList.*");
			FDCCustomerCollection fdcCustomerColl = FDCCustomerFactory
			.getRemoteInstance().getFDCCustomerCollection(view);
			for(int i=0;i<fdcCustomerColl.size();i++)
			{
				FDCCustomerInfo fdcCustomerInfo = fdcCustomerColl.get(i);
				ShareSellerCollection shareSellerColl = fdcCustomerInfo.getShareSellerList();
				//个人共享方式列表
				List oldShareList = new ArrayList();
				//营销单元共享方式列表
				List marketUnitList = new ArrayList();
				//组织共享方式列表
				List orgUnitList = new ArrayList();
				//找出该客户原有的共享销售顾问
				for(int m=0;m<shareSellerColl.size();m++)
				{
					ShareSellerInfo shareSellerInfo = shareSellerColl.get(m);
					if(ShareModelEnum.sharePerson.equals(shareSellerInfo.getShareModel()))
					{
						oldShareList.add(shareSellerInfo);
					}else if(ShareModelEnum.shareMarket.equals(shareSellerInfo.getShareModel()))
					{
						marketUnitList.add(shareSellerInfo);
					}else if(ShareModelEnum.shareOrgUnit.equals(shareSellerInfo.getShareModel()))
					{
						orgUnitList.add(shareSellerInfo);
					}
				}
				shareSellerColl.clear();
				List tblShareList = new ArrayList();
				//得到先添加的共享销售顾问
				for(int j=0;j<tblSharePerson.getRowCount();j++)
				{
					IRow row = tblSharePerson.getRow(j);
					ShareSellerInfo shareSellerInfo = (ShareSellerInfo)row.getUserObject();
					ShareModelEnum shareModel = (ShareModelEnum)row.getCell("shareModel").getValue();
					shareSellerInfo.setShareModel(shareModel);
					if(ShareModelEnum.sharePerson.equals(shareModel))
					{
						UserInfo info = (UserInfo)row.getCell("shareObject").getUserObject();
						shareSellerInfo.setSeller(info);
						if(info.getId().toString().equals(fdcCustomerInfo.getSalesman().getId().toString()))
						{
							MsgBox.showInfo("请重新选择共享销售顾问，"+info.getName()+"是当前销售顾问");
							return;
						}
					}else if(ShareModelEnum.shareMarket.equals(shareModel))
					{
						MarketingUnitInfo marketInfo = (MarketingUnitInfo)row.getCell("shareObject").getUserObject();
						shareSellerInfo.setMarketingUnit(marketInfo);
					}else if(ShareModelEnum.shareOrgUnit.equals(shareModel))
					{
						FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo)row.getCell("shareObject").getUserObject();
						shareSellerInfo.setOrgUnit(orgUnitInfo);
					}
					shareSellerInfo.setIsAgainShare(((Boolean)row.getCell("isAgainShare").getValue()).booleanValue());
					
					shareSellerInfo.setIsUpdate(((Boolean)row.getCell("isUpdate").getValue()).booleanValue());
					shareSellerInfo.setOperationPerson((UserInfo)this.f7SharePerson.getValue());
					shareSellerInfo.setShareDate((Date)this.pkShareDate.getValue());
					shareSellerInfo.setDescription((String)row.getCell("descrption").getValue());
					tblShareList.add(shareSellerInfo);
				}
				//如果客户原有的共享对象现在也有，那么查看共享操作人是否同一个人，如果是那么删除原来的那条记录，增加现在的。
			    //如果不是，增加现在的原来的也不能删除。如果原来没有，就把这条记录增加上去，原来的也不能删除
				Set set = SHEHelper.differentSet(tblShareList,oldShareList,marketUnitList,orgUnitList);
				Iterator iterator = set.iterator();
				while(iterator.hasNext())
				{
					ShareSellerInfo shareSellerInfo = (ShareSellerInfo)iterator.next();
					fdcCustomerInfo.getShareSellerList().add(shareSellerInfo);
				}
				FDCCustomerFactory.getRemoteInstance().submit(fdcCustomerInfo);
			}
		}else
		{
			//如果是单个客户
			String fdcCustomerID = (String)this.getUIContext().get("id");
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("salesman.*");
			sels.add("shareSellerList.*");
			sels.add("shareSellerList.seller.*");
			sels.add("shareSellerList.marketingUnit.*");
			sels.add("shareSellerList.operationPerson.*");
			sels.add("shareSellerList.orgUnit.*");
			sels.add("linkmanList.*");
			FDCCustomerInfo fdcCustomerInfo = FDCCustomerFactory
			.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(BOSUuid.read(fdcCustomerID)), sels);
			ShareSellerCollection shareSellerColl = fdcCustomerInfo.getShareSellerList();
			List oldShareList = new ArrayList();
			List marketUnitList = new ArrayList();
			List orgUnitList = new ArrayList();
			for(int m=0;m<shareSellerColl.size();m++)
			{
				ShareSellerInfo shareSellerInfo = shareSellerColl.get(m);
				if(ShareModelEnum.sharePerson.equals(shareSellerInfo.getShareModel()))
				{
					oldShareList.add(shareSellerInfo);
				}else if(ShareModelEnum.shareMarket.equals(shareSellerInfo.getShareModel()))
				{
					marketUnitList.add(shareSellerInfo);
				}else if(ShareModelEnum.shareOrgUnit.equals(shareSellerInfo.getShareModel()))
				{
					orgUnitList.add(shareSellerInfo);
				}
			}
			shareSellerColl.clear();
			List tblShareList = new ArrayList();
			for(int j=0;j<tblSharePerson.getRowCount();j++)
			{
				IRow row = tblSharePerson.getRow(j);
				ShareSellerInfo shareSellerInfo = (ShareSellerInfo)row.getUserObject();
				ShareModelEnum shareModel = (ShareModelEnum)row.getCell("shareModel").getValue();
				shareSellerInfo.setShareModel(shareModel);
				if(ShareModelEnum.sharePerson.equals(shareModel))
				{
				   UserInfo info = (UserInfo)row.getCell("shareObject").getUserObject();
				   if(info.getId().toString().equals(fdcCustomerInfo.getSalesman().getId().toString()))
				   {
					  MsgBox.showInfo("请重新选择共享销售顾问，"+info.getName()+"是当前销售顾问");
					  return;
				   }			
				    shareSellerInfo.setSeller(info);
				}else if(ShareModelEnum.shareMarket.equals(shareModel))
				{
					MarketingUnitInfo marketInfo = (MarketingUnitInfo)row.getCell("shareObject").getUserObject();
					shareSellerInfo.setMarketingUnit(marketInfo);
				}else if(ShareModelEnum.shareOrgUnit.equals(shareModel))
				{
					FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo)row.getCell("shareObject").getUserObject();
					shareSellerInfo.setOrgUnit(orgUnitInfo);
				}
				shareSellerInfo.setIsAgainShare(((Boolean)row.getCell("isAgainShare").getValue()).booleanValue());
				
				shareSellerInfo.setIsUpdate(((Boolean)row.getCell("isUpdate").getValue()).booleanValue());
				shareSellerInfo.setOperationPerson((UserInfo)this.f7SharePerson.getValue());
				shareSellerInfo.setShareDate((Date)this.pkShareDate.getValue());
				shareSellerInfo.setDescription((String)row.getCell("descrption").getValue());
				tblShareList.add(shareSellerInfo);
			}
			
			Set set = SHEHelper.differentSet(tblShareList,oldShareList,marketUnitList,orgUnitList);
			Iterator iterator = set.iterator();
			while(iterator.hasNext())
			{
				ShareSellerInfo shareSellerInfo = (ShareSellerInfo)iterator.next();
				fdcCustomerInfo.getShareSellerList().add(shareSellerInfo);
			}
			FDCCustomerFactory.getRemoteInstance().submit(fdcCustomerInfo);
		}
		this.destroyWindow();
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new ShareSellerInfo().getBOSType());
	}	
	
	protected void btnCancell_actionPerformed(ActionEvent e) throws Exception {
		this.destroyWindow();
	}
	
	/**
     * 单元格事件，解决单选框鼠标单击不能选中问题
     */
	protected void tblSharePerson_activeCellChanged(KDTActiveCellEvent e) throws Exception {
		super.tblSharePerson_activeCellChanged(e);
		TenancyClientHelper.tableName_activeCellChanged(e,this.tblSharePerson);
	}
	
	/**
     * 验证
     */
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(this.tblSharePerson.getRowCount()==0)
		{
			MsgBox.showInfo("共享对象不能为空!");
			this.abort();
		}if(this.tblCustomer.getRowCount()==0)
		{
			MsgBox.showInfo("待共享客户不能为空！");
			this.abort();
		}if(this.tblSharePerson.getRowCount()>0)
		{
			for(int i=0;i<tblSharePerson.getRowCount();i++)
			{
				IRow row = tblSharePerson.getRow(i);
				if(row.getCell("descrption").getValue()!=null && ((String)row.getCell("descrption").getValue()).length()>255)
				{
					MsgBox.showInfo("第"+(i+1)+"行说明长度不能超过255!");
					this.abort();
				}
			}
			
		}
	}
	
	
	//当前登录用户对指定客户是否有共享的权限
	public static boolean hasSharePermission(FDCCustomerInfo customerInfo) throws EASBizException, BOSException, UuidException {
		if(customerInfo==null) return false;		
		return hasSharePermission(customerInfo.getId().toString());
	}
	
	public static boolean hasSharePermission(String customerId) throws EASBizException, BOSException, UuidException {
		if(customerId==null) return false;
		
		UserInfo currUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		if(FDCCustomerFactory.getRemoteInstance().exists("where id='"+customerId+"' and salesman.id='"+currUserInfo.getId().toString()+"' "))
			return true;  //当前登录人员的自己的客户
		
		boolean hasPermission = false;
		//当前组织下，作为指定客户所属的顾问的负责人，且拥有业务操作权限
		hasPermission = SHEHelper.hasOperatorPermission(customerId);
		if(hasPermission) return true;
			
		//是  对该客户的有再共享权的共享顾问 
		hasPermission = ShareSellerFactory.getRemoteInstance().exists("where head.id='"+ customerId +"'  and seller.id='"+ currUserInfo.getId().toString() +"'" +
				"and isAgainShare=1 and shareModel = '"+ShareModelEnum.SHAREPERSON_VALUE+"' ");
		if(hasPermission) return true;
		//是   对该客户的有再共享权的共享顾问 的 负责人且有操作权限
		SaleOrgUnitInfo saleOrg = SysContext.getSysContext().getCurrentSaleUnit();
		String filterSql = "select FSellerID from T_SHE_ShareSeller where FHeadID='"+customerId+"' and FIsAgainShare=1 and FShareModel='sharePerson' ";
		MarketUnitPlatCollection muPlatColl = MarketUnitPlatFactory.getRemoteInstance().getMarketUnitPlatCollection("select dutyPerson.id,marketUnit.id " +
				"where orgUnit.id='"+ saleOrg.getId().toString() +"' and dutyPerson.id='"+currUserInfo.getId().toString()+"' and member.id in ("+ filterSql +") ");
		for(int i=0;i<muPlatColl.size();i++) {
			MarketUnitPlatInfo muPlatInfo = muPlatColl.get(i);
			boolean existFlag = MarketingUnitMemberFactory.getRemoteInstance().exists("where head.id='"+muPlatInfo.getMarketUnit().getId().toString()+"' " +
					"and isDuty=1 and isOperation=1 and member.id='"+muPlatInfo.getDutyPerson().getId().toString()+"'");
			if(existFlag) return true;
		}
		
		return false;
	}
	
	
}