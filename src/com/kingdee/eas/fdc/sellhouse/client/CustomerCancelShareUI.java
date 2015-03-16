/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.ShareModelEnum;
import com.kingdee.eas.fdc.sellhouse.ShareSellerCollection;
import com.kingdee.eas.fdc.sellhouse.ShareSellerInfo;
import com.kingdee.eas.fdc.tenancy.client.TenancyClientHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CustomerCancelShareUI extends AbstractCustomerCancelShareUI
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerCancelShareUI.class);
    
    UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
    
    /**
     * output class constructor
     */
    public CustomerCancelShareUI() throws Exception
    {
        super();
    }
    
    //重写父类方法为空，不重写该方法就提示传入编辑的参数ID为空
    protected void inOnload() throws Exception {
		 //super.inOnload();
	}

    public void onLoad() throws Exception {
    	initControl();
    	initTblSharePerson();
    	String fdcCustomerID = (String)this.getUIContext().get("id");
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("salesman.*");
		sels.add("shareSellerList.*");
		sels.add("shareSellerList.seller.*");
		sels.add("shareSellerList.marketingUnit.*");
		sels.add("shareSellerList.orgUnit.*");
		sels.add("shareSellerList.operationPerson.*");
		FDCCustomerInfo fdcCustomerInfo = FDCCustomerFactory
		.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(BOSUuid.read(fdcCustomerID)), sels);
		ShareSellerCollection shareColl = fdcCustomerInfo.getShareSellerList();
		//找到客户对应的共享人员
		for(int i=0;i<shareColl.size();i++)
		{
			ShareSellerInfo shareInfo = shareColl.get(i);
			this.tblSharePerson.checkParsed();
			IRow row = this.tblSharePerson.addRow();
			//显示共享人员信息
			showShareSellerInfo(shareInfo,row);
		}
    	super.onLoad();
    }
    
    /**
     * 显示共享人员信息
     */
    private void showShareSellerInfo(ShareSellerInfo shareInfo,IRow row)
    {
    	row.setUserObject(shareInfo);
    	row.getCell("isCancel").setValue(new Boolean(false));
    	ShareModelEnum shareModel = (ShareModelEnum)shareInfo.getShareModel();
    	row.getCell("shareModel").setValue(shareModel);
    	if(ShareModelEnum.sharePerson.equals(shareModel))
    	{
    		row.getCell("shareObject").setValue(shareInfo.getSeller().getName());
        	row.getCell("shareObject").getStyleAttributes().setLocked(true);
    	}else if(ShareModelEnum.shareMarket.equals(shareModel))
    	{
    		row.getCell("shareObject").setValue(shareInfo.getMarketingUnit().getName());
    		row.getCell("shareObject").getStyleAttributes().setLocked(true);
    	}else if(ShareModelEnum.shareOrgUnit.equals(shareModel))
    	{
    		row.getCell("shareObject").setValue(shareInfo.getOrgUnit().getName());
    		row.getCell("shareObject").getStyleAttributes().setLocked(true);
    	}
    	
    	row.getCell("operationPerson").setValue(shareInfo.getOperationPerson().getName());
    	row.getCell("operationPerson").getStyleAttributes().setLocked(true);
    	row.getCell("isAgainShare").setValue(new Boolean(shareInfo.isIsAgainShare()));
    	row.getCell("isAgainShare").getStyleAttributes().setLocked(true);
    	row.getCell("isUpdate").setValue(new Boolean(shareInfo.isIsUpdate()));
    	row.getCell("isUpdate").getStyleAttributes().setLocked(true);
    	row.getCell("description").setValue(shareInfo.getDescription());
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
     * 初始化共享人列表
     */
    private void initTblSharePerson()
    {
    	this.tblSharePerson.checkParsed();
		this.tblSharePerson
		.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		
		KDCheckBox chkBox = new KDCheckBox();
		ICellEditor checkBox = new KDTDefaultCellEditor(chkBox);
		this.tblSharePerson.getColumn("isCancel").setEditor(checkBox);
    }
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * 单元格事件，解决单选框鼠标单击不能选中问题
     */
    protected void tblSharePerson_activeCellChanged(KDTActiveCellEvent e) throws Exception {
    	super.tblSharePerson_activeCellChanged(e);
    	TenancyClientHelper.tableName_activeCellChanged(e,this.tblSharePerson);
    }

    public static void showEditUI(IUIObject ui,String id) throws EASBizException, BOSException
	{
		UIContext uiContext = new UIContext(ui);
		uiContext.put("id",id);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
		.create(CustomerCancelShareUI.class.getName(), uiContext, null, STATUS_VIEW);
		uiWindow.show();
	}
    
    /**
     * 确定事件
     */
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnConfirm_actionPerformed(e);
     	String fdcCustomerID = (String)this.getUIContext().get("id");
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("salesman.*");
		sels.add("shareSellerList.*");
		sels.add("shareSellerList.seller.*");
		sels.add("shareSellerList.marketingUnit.*");
		sels.add("shareSellerList.orgUnit.*");
		sels.add("linkmanList.*");
		FDCCustomerInfo fdcCustomerInfo = FDCCustomerFactory
		.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(BOSUuid.read(fdcCustomerID)), sels);
		UserInfo salesman = fdcCustomerInfo.getSalesman();
		ShareSellerCollection shareSellerColl = fdcCustomerInfo.getShareSellerList();
		shareSellerColl.clear();
		//如果用户是客户所属营销顾问，可以取消任意行
		if(userInfo.getId().toString().equals(salesman.getId().toString()))
		{
			for(int i=0;i<tblSharePerson.getRowCount();i++)
			{
				IRow row = tblSharePerson.getRow(i);
				//没被选中的才是需要保存的
				if(!((Boolean)row.getCell("isCancel").getValue()).booleanValue())
				{
					ShareSellerInfo shareInfo = (ShareSellerInfo)row.getUserObject();
					
					fdcCustomerInfo.getShareSellerList().add(shareInfo);
				}
			}
			FDCCustomerFactory.getRemoteInstance().submit(fdcCustomerInfo);
		}else
		{
			//?如果非所属营销顾问只有该行的共享操作人是自己才可以取消.
			for(int i=0;i<tblSharePerson.getRowCount();i++)
			{
				IRow row = tblSharePerson.getRow(i);
				if(((Boolean)row.getCell("isCancel").getValue()).booleanValue())
				{
					ShareSellerInfo shareInfo = (ShareSellerInfo)row.getUserObject();
					if(!userInfo.getId().toString().equals(shareInfo.getOperationPerson().getId().toString()))
					{
						MsgBox.showInfo("非所属销售顾问只能取消共享操作人是当前用户的客户!");
						this.abort();
					}
				}else
				{
					ShareSellerInfo shareInfo = (ShareSellerInfo)row.getUserObject();
					fdcCustomerInfo.getShareSellerList().add(shareInfo);
				}
			}
			FDCCustomerFactory.getRemoteInstance().submit(fdcCustomerInfo);
		}
		this.destroyWindow();
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
    }

    /**
     * output btnCancell_actionPerformed method
     */
    protected void btnCancell_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    { 	
    	this.destroyWindow();
    }

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

}