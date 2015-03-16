/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
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
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.AdapterLogInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceStatusEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.ShareModelEnum;
import com.kingdee.eas.fdc.sellhouse.ShareSellerCollection;
import com.kingdee.eas.fdc.sellhouse.ShareSellerFactory;
import com.kingdee.eas.fdc.sellhouse.ShareSellerInfo;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlat;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlatCollection;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlatFactory;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlatInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberFactory;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class CustomerAdapterUI extends AbstractCustomerAdapterUI
{
	private static final Logger logger = CoreUIObject.getLogger(CustomerAdapterUI.class);
	
	/**
	 * output class constructor
	 */
	public CustomerAdapterUI() throws Exception
	{
		super();
	}
	
//	因为继承的editUI,所以必须重写父类方法为空，不重写该方法就提示传入编辑的参数ID为空
	protected void inOnload() throws Exception {
		// super.inOnload();
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
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
	
	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
		List idList = (List)this.getUIContext().get("idList");
//		如果是选择多个用户进行转接操作
		if(idList !=null && idList.size()>0)
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
			FDCCustomerCollection fdcCustomerColl = FDCCustomerFactory
			.getRemoteInstance().getFDCCustomerCollection(view);
			for(int i=0;i<fdcCustomerColl.size();i++)
			{
				FDCCustomerInfo fdcCustomerInfo = fdcCustomerColl.get(i);
				ShareSellerCollection shareSellerColl = fdcCustomerInfo.getShareSellerList();
				for(int j=0;j<shareSellerColl.size();j++)
				{
					
				}
				this.tblCustomer.checkParsed();
				IRow row = this.tblCustomer.addRow();
				showFDCCustomerInfo(fdcCustomerInfo,row);
			}
		}else
		{
			//在编辑界面里进行转接操作,也就是只有一个用户
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
		this.f7AdapterPerson.setValue(userInfo);
		this.f7AdapterPerson.setEnabled(false);
		this.chkIsAdapterInter.setSelected(true);
		this.chkAdapterFunction.setSelected(true);
	}
	
	/**
     * 显示选择的客户信息
     */
	private void showFDCCustomerInfo(FDCCustomerInfo fdcCustomerInfo,IRow row)
	{
		row.setUserObject(fdcCustomerInfo);
		UserInfo userInfo = fdcCustomerInfo.getSalesman();
		row.getCell("oldSeller").setValue(userInfo.getName());
		row.getCell("oldSeller").getStyleAttributes().setLocked(true);
		row.getCell("customerName").setValue(fdcCustomerInfo.getName());
		row.getCell("customerName").getStyleAttributes().setLocked(true);
		row.getCell("customerNumber").setValue(fdcCustomerInfo.getNumber());
		row.getCell("customerNumber").getStyleAttributes().setLocked(true);
		row.getCell("sex").setValue(fdcCustomerInfo.getSex());
		row.getCell("sex").getStyleAttributes().setLocked(true);
		row.getCell("bookedDate").setValue(fdcCustomerInfo.getCreateTime());
		row.getCell("bookedDate").getStyleAttributes().setLocked(true);
		row.getCell("tradeTime").setValue(new Long(fdcCustomerInfo.getTradeTime()));
		row.getCell("tradeTime").getStyleAttributes().setLocked(true);
	}
	
	public static void showUI(IUIObject ui,List idList) throws EASBizException, BOSException
	{
		UIContext uiContext = new UIContext(ui);
		uiContext.put("idList",idList);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(CustomerAdapterUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
	}
	
	public static void showEditUI(IUIObject ui,String id) throws EASBizException, BOSException
	{
		UIContext uiContext = new UIContext(ui);
		uiContext.put("id",id);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(CustomerAdapterUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
	}
	
	protected IObjectValue createNewData() {
		return null;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return FDCCustomerFactory.getRemoteInstance();
	}
	
	/**
     * 删除客户记录
     */
	protected void btnDeleteCustomer_actionPerformed(ActionEvent e) throws Exception {
		super.btnDeleteCustomer_actionPerformed(e);
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
		UserInfo userInfo = (UserInfo)this.f7NewSeller.getValue();
		if(userInfo==null)
		{
			MsgBox.showInfo("新所属营销顾问不能为空!");
			this.abort();
		}
		if(this.tblCustomer.getRowCount()==0)
		{
			MsgBox.showInfo("至少选择一个需要转接的客户!");
			return;
		}
		//如果有多个客户
		List idList = (List)this.getUIContext().get("idList");
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
			view.getSelector().add("linkmanList.*");
			view.getSelector().add("adapterLogList.*");
			view.getSelector().add("adapterLogList.beforeSeller.*");
			view.getSelector().add("adapterLogList.afterSeller.*");
			view.getSelector().add("adapterLogList.operationPerson.*");
			view.getSelector().add("shareSellerList.*");
			view.getSelector().add("shareSellerList.seller.*");
			view.getSelector().add("shareSellerList.marketingUnit.*");
			view.getSelector().add("shareSellerList.orgUnit.*");
			FDCCustomerCollection fdcCustomerColl = FDCCustomerFactory
			.getRemoteInstance().getFDCCustomerCollection(view);
			//找出客户原来的销售顾问和现在的比较
			for(int i=0;i<fdcCustomerColl.size();i++)
			{
				FDCCustomerInfo fdcCustomerInfo = fdcCustomerColl.get(i);
				String customerID = fdcCustomerInfo.getId().toString();
				ShareSellerCollection shareSellColl = null;
				UserInfo salesman = fdcCustomerInfo.getSalesman();;
				if(userInfo.getId().toString().equals(salesman.getId().toString()))
				{
					MsgBox.showInfo("新营销顾问不能和原顾问相同!");
					this.abort();
				}else
				{
					fdcCustomerInfo.setSalesman(userInfo);
					AdapterLogInfo adapterInfo = new AdapterLogInfo();
					adapterInfo.setBeforeSeller(salesman);
					adapterInfo.setAfterSeller(userInfo);
					adapterInfo.setOperationPerson((UserInfo)this.f7AdapterPerson.getValue());
					adapterInfo.setAdapterDate((Date)this.pkAdapterDate.getValue());
					adapterInfo.setIsAdapterFunction(this.chkAdapterFunction.isSelected());
					adapterInfo.setIsAdapterInter(this.chkIsAdapterInter.isSelected());
					adapterInfo.setIsEndAdapter(this.chkIsEndAdapter.isSelected());
					adapterInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
					fdcCustomerInfo.getAdapterLogList().add(adapterInfo);
					FDCCustomerFactory.getRemoteInstance().submit(fdcCustomerInfo);
					shareSellColl = fdcCustomerInfo.getShareSellerList();
					for(int j=0;j<shareSellColl.size();j++)
					{
						ShareSellerInfo shareSellerInfo = shareSellColl.get(j);
						ShareModelEnum shareModel = shareSellerInfo.getShareModel();
						if(ShareModelEnum.sharePerson.equals(shareModel))
						{
							if(userInfo.getId().toString().equals(shareSellerInfo.getSeller().getId().toString()))
							{
								ShareSellerFactory.getRemoteInstance().delete(new ObjectUuidPK(shareSellerInfo.getId()));
							}
						}
						
					}
					adapterCommer(customerID,userInfo);
				}
			}
		}else
		{
			String fdcCustomerID = (String)this.getUIContext().get("id");
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("salesman.*");
			sels.add("linkmanList.*");
			sels.add("shareSellerList.*");
			sels.add("shareSellerList.seller.*");
			sels.add("shareSellerList.marketingUnit.*");
			sels.add("shareSellerList.orgUnit.*");
			sels.add("adapterLogList.*");
			sels.add("adapterLogList.beforeSeller.*");
			sels.add("adapterLogList.afterSeller.*");
			sels.add("adapterLogList.operationPerson.*");
			FDCCustomerInfo fdcCustomerInfo = FDCCustomerFactory
			.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(BOSUuid.read(fdcCustomerID)), sels);
			UserInfo salesman = fdcCustomerInfo.getSalesman();;
			ShareSellerCollection shareSellColl = null;
			if(userInfo.getId().toString().equals(salesman.getId().toString()))
			{
				MsgBox.showInfo("新营销顾问不能和原顾问相同!");
				this.abort();
			}
			fdcCustomerInfo.setSalesman(userInfo);
			AdapterLogInfo adapterInfo = new AdapterLogInfo();
			adapterInfo.setBeforeSeller(salesman);
			adapterInfo.setAfterSeller(userInfo);
			adapterInfo.setOperationPerson((UserInfo)this.f7AdapterPerson.getValue());
			adapterInfo.setAdapterDate((Date)this.pkAdapterDate.getValue());
			adapterInfo.setIsAdapterFunction(this.chkAdapterFunction.isSelected());
			adapterInfo.setIsAdapterInter(this.chkIsAdapterInter.isSelected());
			adapterInfo.setIsEndAdapter(this.chkIsEndAdapter.isSelected());
			adapterInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			fdcCustomerInfo.getAdapterLogList().add(adapterInfo);
			FDCCustomerFactory.getRemoteInstance().submit(fdcCustomerInfo);
			shareSellColl = fdcCustomerInfo.getShareSellerList();
			for(int j=0;j<shareSellColl.size();j++)
			{
				ShareSellerInfo shareSellerInfo = shareSellColl.get(j);
				ShareModelEnum shareModel = shareSellerInfo.getShareModel();
				if(ShareModelEnum.sharePerson.equals(shareModel))
				{
					if(userInfo.getId().toString().equals(shareSellerInfo.getSeller().getId().toString()))
					{
						ShareSellerFactory.getRemoteInstance().delete(new ObjectUuidPK(shareSellerInfo.getId()));
					}
				}			
			}
			adapterCommer(fdcCustomerID,userInfo);
//			List list = new ArrayList();
//			list.add(fdcCustomerID);
//			FDCCustomerFactory.getRemoteInstance().switchTo(list,userInfo.getId().toString());
		}
		
		this.destroyWindow();
	}
	
	//转接商机
	private void adapterCommer(String customerID,UserInfo userInfo) throws Exception
	{
		if(this.chkIsAdapterInter.isSelected())
		{
			EntityViewInfo viewInfo = new EntityViewInfo();
			FilterInfo filter2 = new FilterInfo();
			filter2.getFilterItems()
			.add(
					new FilterItemInfo("fdcCustomer.id",customerID,CompareType.EQUALS));
			filter2.getFilterItems()
			.add(
					new FilterItemInfo("commerceStatus",CommerceStatusEnum.Intent,CompareType.EQUALS));
			viewInfo.setFilter(filter2);
			CoreBaseCollection coll = new CoreBaseCollection();
			
			CommerceChanceCollection comColl = CommerceChanceFactory.getRemoteInstance().getCommerceChanceCollection(viewInfo);
			for(int j=0;j<comColl.size();j++)
			{
			     CommerceChanceInfo comInfo = (CommerceChanceInfo)comColl.get(j);
			     comInfo.setSaleMan(userInfo);
			     coll.add(comInfo);
			}
			CommerceChanceFactory.getRemoteInstance().update(coll);
		}
		if(this.chkIsEndAdapter.isSelected())
		{
			EntityViewInfo viewInfo = new EntityViewInfo();
			FilterInfo filter2 = new FilterInfo();
			filter2.getFilterItems()
			.add(
					new FilterItemInfo("fdcCustomer.id",customerID,CompareType.EQUALS));
			filter2.getFilterItems()
			.add(
					new FilterItemInfo("commerceStatus",CommerceStatusEnum.Finish,CompareType.EQUALS));
			viewInfo.setFilter(filter2);
			CoreBaseCollection coll = new CoreBaseCollection();
			
			CommerceChanceCollection comColl = CommerceChanceFactory.getRemoteInstance().getCommerceChanceCollection(viewInfo);
			for(int j=0;j<comColl.size();j++)
			{
			     CommerceChanceInfo comInfo = (CommerceChanceInfo)comColl.get(j);
			     comInfo.setSaleMan(userInfo);
			     coll.add(comInfo);
			}
			CommerceChanceFactory.getRemoteInstance().update(coll);
		}if(this.chkAdapterFunction.isSelected())
		{
			EntityViewInfo viewInfo = new EntityViewInfo();
			FilterInfo filter2 = new FilterInfo();
			filter2.getFilterItems()
			.add(
					new FilterItemInfo("fdcCustomer.id",customerID,CompareType.EQUALS));
			filter2.getFilterItems()
			.add(
					new FilterItemInfo("commerceStatus",CommerceStatusEnum.Finish,CompareType.NOTEQUALS));
			filter2.getFilterItems()
			.add(
					new FilterItemInfo("commerceStatus",CommerceStatusEnum.Intent,CompareType.NOTEQUALS));
			viewInfo.setFilter(filter2);
			CoreBaseCollection coll = new CoreBaseCollection();
			
			CommerceChanceCollection comColl = CommerceChanceFactory.getRemoteInstance().getCommerceChanceCollection(viewInfo);
			for(int j=0;j<comColl.size();j++)
			{
			     CommerceChanceInfo comInfo = (CommerceChanceInfo)comColl.get(j);
			     comInfo.setSaleMan(userInfo);
			     coll.add(comInfo);
			}
			CommerceChanceFactory.getRemoteInstance().update(coll);
		}
	}
	protected void btnCancell_actionPerformed(ActionEvent e) throws Exception {
		this.destroyWindow();
	}
	
	
	//当前登录用户对指定客户是否有转接的权限
	public static boolean hasAdpaterPermission(FDCCustomerInfo customerInfo) throws EASBizException, BOSException, UuidException {
		if(customerInfo==null) return false;		
		return hasAdpaterPermission(customerInfo.getId().toString());
	}
	
	public static boolean hasAdpaterPermission(String customerId) throws EASBizException, BOSException, UuidException {
		if(customerId==null) return false;
		
		UserInfo currUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		if(FDCCustomerFactory.getRemoteInstance().exists("where id='"+customerId+"' and salesman.id='"+currUserInfo.getId().toString()+"' "))
			return true;  //当前登录人员的自己的客户
		
		//作为该客户的顾问的负责人，且拥有业务操作权限
		return SHEHelper.hasOperatorPermission(customerId);
	}
	
	
	
	
	
	
}