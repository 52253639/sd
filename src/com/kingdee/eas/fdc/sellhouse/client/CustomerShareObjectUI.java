/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.ShareSellerCollection;
import com.kingdee.eas.fdc.sellhouse.ShareSellerFactory;
import com.kingdee.eas.fdc.sellhouse.ShareModelEnum;
import com.kingdee.eas.fdc.sellhouse.ShareSellerInfo;
import com.kingdee.eas.fdc.tenancy.IMarketingUnit;
import com.kingdee.eas.fdc.tenancy.IMarketingUnitMember;
import com.kingdee.eas.fdc.tenancy.MarketingUnitCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CustomerShareObjectUI extends AbstractCustomerShareObjectUI {

	private static final Logger logger = CoreUIObject.getLogger(CustomerShareObjectUI.class);

	private Object filterUI;
	
	MarketDisplaySetting setting = new MarketDisplaySetting();

	public void onLoad() throws Exception {
		super.onLoad();
		initControl();
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
		this.actionExeQuery_actionPerformed(e);
	}

	/**
	 * output class constructor
	 */
	public CustomerShareObjectUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getKeyFieldName() {

		return "id";

	}

	public void actionExeQuery_actionPerformed(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
		this.tblMain.checkParsed();  
//		if (this.txtMobile.getText().length() > 0) {
			if (this.txtMobile.getText().length() < setting.getMobileNumber()
					&& this.kDphone.getText().length() <= 0 ) {
				MsgBox.showConfirm2("移动电话位数不得少于"+(setting.getMobileNumber())+"位！");
				this.abort(); 
			} 
//		}
		if (this.kDphone.getText().length() > 0) {
			if (this.kDphone.getText().length() < setting.getPhoneNumber()) {
				MsgBox.showConfirm2("固定电话位数不得少于"+(setting.getPhoneNumber())+"位！");
				this.abort();
			} 
		} 
		super.actionExeQuery_actionPerformed(e);
		FDCCustomerCollection csCol = null; 
		EntityViewInfo evi = new EntityViewInfo();

		evi.getSelector().add("*");
		evi.getSelector().add("project.*");
		evi.getSelector().add("shareSellerList.*");
		evi.getSelector().add("shareSellerList.seller.*");
		evi.getSelector().add("shareSellerList.marketingUnit.*");
		evi.getSelector().add("shareSellerList.orgUnit.*");
		evi.getSelector().add("salesman.*");

		evi.getSorter().add(new SorterItemInfo("id"));
		evi.getSorter().add(new SorterItemInfo("shareSellerList.shareModel"));

		FilterInfo filterInfo = new FilterInfo();
	//  客户名称
		if (this.txtCustomer.getText().length() > 0) {
			filterInfo.getFilterItems().add(new FilterItemInfo("name", this.txtCustomer.getText()));
		}
		// 移动电话
		if (this.txtMobile.getText().length() > 0) {
			filterInfo.getFilterItems().add(new FilterItemInfo("phone", "%" + this.txtMobile.getText() + "%", CompareType.LIKE));
		}

		// 证件号码
		if (this.txtFixedPhone.getText().length() > 0) {
			filterInfo.getFilterItems().add(new FilterItemInfo("certificateNumber", this.txtFixedPhone.getText(), CompareType.EQUALS));
		}
		//固定电话
		if (this.kDphone.getText().length() > 0) {
			filterInfo.getFilterItems().add(new FilterItemInfo("tel", "%" + this.kDphone.getText() + "%", CompareType.LIKE));
			filterInfo.getFilterItems().add(new FilterItemInfo("fax", "%" + this.kDphone.getText() + "%", CompareType.LIKE));
			filterInfo.getFilterItems().add(new FilterItemInfo("officeTel", "%" + this.kDphone.getText() + "%", CompareType.LIKE));
			filterInfo.getFilterItems().add(new FilterItemInfo("phone2", "%" + this.kDphone.getText() + "%", CompareType.LIKE));
		}
		if (filterInfo.getFilterItems().size() < 1) {
			MsgBox.showConfirm2("请至少输入一个条件！");
			this.abort(); 
		}
		if(filterInfo.getFilterItems().size()==5)filterInfo.setMaskString(" #0 and ( #1 or #2 or #3 or #4  )");
		else if (filterInfo.getFilterItems().size()==6)filterInfo.setMaskString(" #0 and #1 and ( #2 or #3 or #4 or #5  ) ");
		else if (filterInfo.getFilterItems().size()==7)filterInfo.setMaskString(" #0 and #1 and #2 and ( #3 or #4 or #5 or #6  ) ");
		else if(filterInfo.getFilterItems().size()==4 )filterInfo.setMaskString(" #0 or #1 or #2 or #3 ");
		evi.setFilter(filterInfo);
		try {
			csCol = FDCCustomerFactory.getRemoteInstance().getFDCCustomerCollection(evi);
		} catch (BOSException ex) {
			handleException(ex);
		}
		if (!csCol.isEmpty()) {
			int k = 0;
			int kk = 0;
			String customerId = "";
			for (int i = 0; i < csCol.size(); i++) {
				kk++;
				FDCCustomerInfo info = csCol.get(i);
				if (info == null) {
					continue;
				}
				if (!info.isEmpty()) {
					IRow row = this.tblMain.addRow();
					if (customerId != "" && (!customerId.equals(info.getId().toString()))) {
						k = kk - 1;
					}
					customerId = info.getId().toString();
					row.getCell("name").setValue(info);
					if (info.getProject() != null) {
						row.getCell("proName").setValue(info.getProject().getName());
					} else {
						row.getCell("proName").setValue(null);
					}
					row.getCell("shareModel").setValue("所属顾问");
					//登记日期
					row.getCell("dengjiDate").setValue(info.getCreateTime());
					//lastTrackDate 最新更进日期
					row.getCell("lastTrackDate").setValue(info.getLastTrackDate());
					if (info.getSalesman() != null) {
						row.getCell("sellerName").setValue(info.getSalesman());
					} else {
						row.getCell("sellerName").setValue(null);
					}
					row.getCell("description").setValue(info.getDescription());
					row.getCell("id").setValue(info.getId());
					if (info.getShareSellerList().size() > 0) {
						for (int j = 0; j < info.getShareSellerList().size(); j++) {
							ShareSellerInfo sellInfo = info.getShareSellerList().get(j);
							if (sellInfo == null) {
								continue;
							}
							if (!sellInfo.isEmpty()) {
								kk++;
								row = this.tblMain.addRow();
								row.getCell("name").setValue(info);
								if (info.getProject() != null) {
									row.getCell("proName").setValue(info.getProject().getName());
								} else {
									row.getCell("proName").setValue(null);
								}
								if (ShareModelEnum.shareMarket.equals(sellInfo.getShareModel())) {
									row.getCell("shareModel").setValue(ShareModelEnum.shareMarket);
									row.getCell("dengjiDate").setValue(info.getCreateTime());
									row.getCell("lastTrackDate").setValue(info.getLastTrackDate());
									row.getCell("sellerName").setValue(sellInfo.getMarketingUnit());
								} else if (ShareModelEnum.shareOrgUnit.equals(sellInfo.getShareModel())) {
									row.getCell("shareModel").setValue(ShareModelEnum.shareOrgUnit);
									row.getCell("dengjiDate").setValue(info.getCreateTime());
									row.getCell("lastTrackDate").setValue(info.getLastTrackDate());
									row.getCell("sellerName").setValue(sellInfo.getOrgUnit());
								} else if (ShareModelEnum.sharePerson.equals(sellInfo.getShareModel())) {
									row.getCell("shareModel").setValue(ShareModelEnum.sharePerson);
									row.getCell("dengjiDate").setValue(info.getCreateTime());
									row.getCell("lastTrackDate").setValue(info.getLastTrackDate());
									row.getCell("sellerName").setValue(sellInfo.getSeller());
								} else {
									row.getCell("shareModel").setValue(null);
									row.getCell("dengjiDate").setValue(info.getCreateTime());
									row.getCell("lastTrackDate").setValue(info.getLastTrackDate());
									row.getCell("sellerName").setValue(null);
								}
								row.getCell("description").setValue(sellInfo.getDescription());
								row.getCell("id").setValue(info.getId());
							}
						}
					}
				}
				KDTMergeManager mm = this.tblMain.getMergeManager();
				mm.mergeBlock(k, 0, kk - 1, 0, KDTMergeManager.SPECIFY_MERGE);
				mm.mergeBlock(k, 1, kk - 1, 1, KDTMergeManager.SPECIFY_MERGE);
			}
		}
		initControl();
	}

	protected ICoreBase getBizInterface() throws Exception {

		return CustomerFactory.getRemoteInstance();
	}

	protected String getEditUIName() {

		return CustomerShareObjectUI.class.getName();

}
	
	
	public void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		
			// 双击
			if(e.getClickCount()==2){
				if(setting.getGxcheck()!=32){
					return;
				}
				Map map=this.getUserMarktingUnit();
				e.getRowIndex();
				IRow row = tblMain.getRow(e.getRowIndex());
				FDCCustomerInfo info=(FDCCustomerInfo) row.getCell("name").getValue();//客户名称
				UserInfo userInfo = info.getSalesman();//销售员
				String str=userInfo.getId().toString();
				for (int j = 0; j < info.getShareSellerList().size(); j++) {
					ShareSellerInfo sellInfo = info.getShareSellerList().get(j);//共享销售顾问
					UserInfo seller = sellInfo.getSeller();//销售顾问
					if(seller!=null){
						if("".equals(str))str=seller.getId().toString();	
						else str=str+","+seller.getId().toString();
					}
				}
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("member",str,CompareType.INCLUDE));
				view.setFilter(filter);
				SelectorItemCollection coll=new SelectorItemCollection();
				coll.add("*");
				coll.add("head.*");
				coll.add("head.parent.*");
				coll.add("head.member.*");
				view.setSelector(coll);
//				IMarketingUnit unitServ=MarketingUnitFactory.getRemoteInstance();
//				MarketingUnitCollection unitColl = unitServ.getMarketingUnitCollection(view);
				IMarketingUnitMember memberServ=MarketingUnitMemberFactory.getRemoteInstance();
				MarketingUnitMemberCollection memberColl = memberServ.getMarketingUnitMemberCollection(view);
				
				for(int i=0;i<memberColl.size();i++){//共享的置业顾问
					MarketingUnitInfo unitInfo = memberColl.get(i).getHead();
//					当前用户是否处于置业顾问所属营销组织
					if(map.containsKey(unitInfo.getId().toString())){
						MsgBox.showInfo("所选客户与当前操作员同属于一个营销单元，如需共享请找营销单元负责人！");
						return;
					}
					if(unitInfo.getParent()!=null){
						boolean b=checkIsMarketingUnit(unitInfo.getParent(),map);
						if(b){
							MsgBox.showInfo("所选客户与当前操作员同属于一个营销单元，如需共享请找营销单元负责人！");
							return;
						}
					}
				}
				int index=MsgBox.showConfirm2("是否将该客户共享给当前系统操作员！");
				if(index==0){
					ShareSellerInfo item=new ShareSellerInfo();
//					item.setId(BOSUuid.create(item.getBOSType()));
					item.setHead(info);
					item.setSeller(SysContext.getSysContext().getCurrentUserInfo());
					item.setShareModel(ShareModelEnum.sharePerson);
					item.setShareDate(new Date());
					item.setIsAgainShare(true);
					item.setIsUpdate(true);
					UserInfo uInfo = (UserInfo) row.getCell("sellerName").getValue();
					item.setOperationPerson(uInfo);
					item.setDescription("系统自动共享");
//					FullOrgUnitInfo orgInfo=FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo("select * where id='"
//							+SysContext.getSysContext().getCurrentSaleUnit().getId().toString()+"'");
//					item.setOrgUnit(orgInfo);
//					MarketingUnitInfo marketingUnitInfo=(MarketingUnitInfo) this.getUIContext().get("marketingInfo");
//					item.setMarketingUnit(marketingUnitInfo);
					info.getShareSellerList().add(item);
					FDCCustomerFactory.getRemoteInstance().update(new ObjectUuidPK(info.getId()), info);
//					ShareSellerFactory.getRemoteInstance().addnew(item);
					this.getUIWindow().close();  
				}
				
				
			}
//			super.tblMain_tableClicked(e);
	}
	/**
	 * 判断当前用户是否与客户的共享置业顾问处于同一营销单元---上级
	 * @param userids
	 * @return
	 * @throws BOSException
	 */
	private boolean checkIsMarketingUnit(MarketingUnitInfo unitMember,Map userMarketingUnitMap) throws BOSException{
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.id",unitMember.getId().toString(),CompareType.EQUALS));
//		filter.getFilterItems().add(new FilterItemInfo("head.parent.id",unitMember.getId().toString(),CompareType.EQUALS));
//		filter.setMaskString("#0 or #1");
		view.setFilter(filter);
		SelectorItemCollection coll=new SelectorItemCollection();
		coll.add("*");
		coll.add("head.*");
		coll.add("head.parent.*");
		coll.add("head.member.*");
		view.setSelector(coll);
		IMarketingUnitMember memberServ=MarketingUnitMemberFactory.getRemoteInstance();
		MarketingUnitMemberCollection memberColl = memberServ.getMarketingUnitMemberCollection(view);
		for(int i=0;i<memberColl.size();i++){//共享的置业顾问
			MarketingUnitInfo unitInfo = memberColl.get(i).getHead();
			if(userMarketingUnitMap.containsKey(unitInfo.getId().toString())){
				return true;
			}
			if(unitInfo.getParent()!=null){
				return checkIsMarketingUnit(unitInfo.getParent(),userMarketingUnitMap);
			}
		}
		return false;
	}

	/**
	 * 获取当前用户所属营销单元，可有多个营销单元
	 * @return
	 * @throws BOSException 
	 */
	private Map getUserMarktingUnit() throws BOSException{
		UserInfo user=SysContext.getSysContext().getCurrentUserInfo();
		Map map=new HashMap();
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("member.id",user.getId().toString(),CompareType.EQUALS));
		view.setFilter(filter);
		SelectorItemCollection coll=new SelectorItemCollection();
		coll.add("*");
		coll.add("head.*");
		view.setSelector(coll);
		IMarketingUnitMember memberServ=MarketingUnitMemberFactory.getRemoteInstance();
		MarketingUnitMemberCollection memberColl = memberServ.getMarketingUnitMemberCollection(view);
		for(int i=0;i<memberColl.size();i++){//共享的置业顾问
			MarketingUnitInfo unitInfo = memberColl.get(i).getHead();
			map.put(unitInfo.getId().toString(), unitInfo);
		}
		return map;
	}

	/**
	 * 隐藏多余按钮
	 */
	private void initControl() {
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.btnRefresh.setVisible(true);
		this.btnRefresh.setEnabled(true);
		this.btnView.setVisible(false);
		this.btnQuery.setVisible(false);
		this.btnLocate.setVisible(false);
		this.kdwbQuery.setVisible(true);
		this.kdwbQuery.setEnabled(true);

		this.tblMain.setEnabled(false);
		this.txtCustomer.setFocusable(true);
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);

	}

	public int getRowCountFromDB(){
		return -1;
	}

}