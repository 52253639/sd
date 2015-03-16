package com.kingdee.eas.fdc.basecrm.app;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerCollection;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoCollection;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerGroupDetailInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.master.cssp.StandardTypeEnum;
import com.kingdee.eas.basedata.master.cssp.UsedStatusEnum;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureCollection;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureFactory;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureInfo;
import com.kingdee.eas.framework.DeletedStatusEnum;
import com.kingdee.eas.util.app.ContextUtil;

public class FDCMainCustomerControllerBean extends AbstractFDCMainCustomerControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basecrm.app.FDCMainCustomerControllerBean");

    protected void _cancel(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
    	FDCMainCustomerInfo info = (FDCMainCustomerInfo) model;
    	info.setIsEnabled(false);
    	
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("isEnabled");
    	_updatePartial(ctx, model, sels);
    }
    
    protected void _cancelCancel(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
    	FDCMainCustomerInfo info = (FDCMainCustomerInfo) model;
    	info.setIsEnabled(true);
    	
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("isEnabled");
    	_updatePartial(ctx, model, sels);
    }
    
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	IObjectPK pk = super._submit(ctx, model);
    	FDCMainCustomerInfo mainCus = (FDCMainCustomerInfo) model;
    	mainCus.setId(BOSUuid.read(pk.toString()));
      	mainCus.setLastUpdateUnit(ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo());
//      	_addToSysCustomer(ctx, mainCus);
    	return pk;
    }
    
	protected void _addToSysCustomer(Context ctx, IObjectValue mainCus1) throws BOSException, EASBizException {
		FDCMainCustomerInfo mainCus = (FDCMainCustomerInfo)mainCus1;
		CustomerInfo cus = mainCus.getSysCustomer();
    	
    	if(cus == null){
    		CustomerCollection col=CustomerFactory.getLocalInstance(ctx).getCustomerCollection("select * from where number='"+mainCus.getCertificateNumber()+"'");
    		CtrlUnitInfo cu = new CtrlUnitInfo();
			cu.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
    		if(col.size()==0){
    			cus = new CustomerInfo();
        		cus.setId(BOSUuid.create(CustomerInfo.getBosType()));
    			setSysCustomerValue(ctx, mainCus, cus);
    			
    			cus.setCU(cu);
    			CustomerFactory.getLocalInstance(ctx).addnew(cus);
    		}else{
    			cus=col.get(0);
    		}

			//提交的怎么都是取得当前CU，只好这样更新为集团。。
			cus.setCU(cu);
			SelectorItemCollection sels1 = new SelectorItemCollection();
			sels1.add("CU");
			CustomerFactory.getLocalInstance(ctx).updatePartial(cus, sels1);
			
			mainCus.setSysCustomer(cus);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("sysCustomer");
			this.updatePartial(ctx, mainCus, sels);
    	}else{
    		updateSysCustomer(ctx, mainCus, cus);//已经同步过
    	}
    	
    	//获得客户管理组织所属的CU
    	Set cuIds = getCusMgeCu(ctx);
    	for(Iterator itor = cuIds.iterator(); itor.hasNext(); ){
    		String cuId = (String) itor.next();
    		CustomerFactory.getLocalInstance(ctx).assign(new ObjectUuidPK(OrgConstants.DEF_CU_ID), new ObjectUuidPK(cus.getId()), new ObjectUuidPK(cuId));
    	}
	}

	private Set getCusMgeCu(Context ctx) throws BOSException {
		Set set = new HashSet();
		
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("unit.CU.id");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("custMangageUnit", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("unit.isOUSealUp", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("isHis", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("tree.id", OrgConstants.SALE_VIEW_ID));
		view.setFilter(filter);
		view.setSelector(sel);
		FDCOrgStructureCollection orgColl = FDCOrgStructureFactory.getLocalInstance(ctx).getFDCOrgStructureCollection(
				view);
		for (int i = 0; i < orgColl.size(); i++) {
			FDCOrgStructureInfo orgInfo = orgColl.get(i);
			if (orgInfo.getUnit() != null) {
				String id = orgInfo.getUnit().getCU().getId().toString();
				if(OrgConstants.DEF_CU_ID.equals(id)){
					continue;
				}
				set.add(id);
			}
		}
		return set;
	}
	
	private void updateSysCustomer(Context ctx, FDCMainCustomerInfo mainCus, CustomerInfo customer) throws BOSException, EASBizException {
		setSysCustomerValue(ctx, mainCus, customer);
		
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("number");
		selCol.add("name");
		selCol.add("address");
		selCol.add("country");
		selCol.add("province");
		selCol.add("city");
		selCol.add("region");
		selCol.add("description");
		selCol.add("CU");
		selCol.add("browseGroup");
		selCol.add("customerGroupDetails");
		CustomerFactory.getLocalInstance(ctx).updatePartial(customer, selCol);
	}
    
	private void setSysCustomerValue(Context ctx, FDCMainCustomerInfo fdcCustomer, CustomerInfo customer) throws BOSException, EASBizException {
		String tempNumber = fdcCustomer.getNumber();
		if(tempNumber != null && !tempNumber.equals("")){
			if(tempNumber.length() >40){
				tempNumber = tempNumber.substring(0, 40);
			}
		}
//		customer.setNumber("FDC-" + tempNumber + "-" + System.currentTimeMillis());
		customer.setNumber(fdcCustomer.getCertificateNumber());
		//customer.setNumber("FDC-" + fdcCustomer.getNumber() + "-" + System.currentTimeMillis());
		customer.setName(fdcCustomer.getName());
		customer.setAddress(fdcCustomer.getMailAddress());
		customer.setCountry(fdcCustomer.getCountry());
		customer.setProvince(fdcCustomer.getProvince());
		customer.setCity(fdcCustomer.getCity());
		customer.setRegion(fdcCustomer.getRegion());
		customer.setDescription(fdcCustomer.getDescription());
		CtrlUnitInfo cu = new CtrlUnitInfo();
		cu.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
		customer.setCU(cu);
		customer.setAdminCU(cu);
		
		CSSPGroupInfo groupInfo = null;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("name", "房地产客户"));
		filter.getFilterItems().add(new FilterItemInfo("CU.id", OrgConstants.DEF_CU_ID));
		
		CSSPGroupCollection sheGroupCol = CSSPGroupFactory.getLocalInstance(ctx).getCSSPGroupCollection(view);
		
		if(sheGroupCol.isEmpty()){
			CSSPGroupStandardInfo strd = new CSSPGroupStandardInfo();
			strd.setId(BOSUuid.create(strd.getBOSType()));
			strd.setNumber("fdccusGstrd");
			strd.setName("房地产客户分类标准");
			strd.setType(1);
			strd.setIsBasic(StandardTypeEnum.defaultStandard);
			strd.setCU(cu);
			
			CSSPGroupStandardFactory.getLocalInstance(ctx).addnew(strd);
			
			CSSPGroupInfo gr = new CSSPGroupInfo();
			gr.setDeletedStatus(DeletedStatusEnum.NORMAL);
			gr.setId(BOSUuid.create(gr.getBOSType()));
			gr.setNumber("fdccusG");
			gr.setName("房地产客户");
			gr.setCU(cu);
			gr.setGroupStandard(strd);
			
			CSSPGroupFactory.getLocalInstance(ctx).addnew(gr);
			
			groupInfo = gr;
		}else{
			groupInfo = sheGroupCol.get(0);
		}
		customer.setBrowseGroup(groupInfo);
		
		CustomerGroupDetailInfo Gdinfo = new CustomerGroupDetailInfo();
		Gdinfo.setCustomerGroup(groupInfo);
		Gdinfo.setCustomerGroupFullName(groupInfo.getName());
		Gdinfo.setCustomerGroupStandard(groupInfo.getGroupStandard());
		customer.getCustomerGroupDetails().add(Gdinfo);
		
		EntityViewInfo baseview = new EntityViewInfo();
		FilterInfo basefilter = new FilterInfo();
		baseview.setFilter(basefilter);
		basefilter.getFilterItems().add(new FilterItemInfo("number", "05.03"));
		basefilter.getFilterItems().add(new FilterItemInfo("name", "个人客户"));
		basefilter.getFilterItems().add(new FilterItemInfo("CU.id", OrgConstants.DEF_CU_ID));
		baseview.setFilter(basefilter);
		
		CSSPGroupCollection basesheGroupCol = CSSPGroupFactory.getLocalInstance(ctx).getCSSPGroupCollection(baseview);
		if(basesheGroupCol.size()>0){
			CustomerGroupDetailInfo baseGdinfo = new CustomerGroupDetailInfo();
			baseGdinfo.setCustomerGroup(basesheGroupCol.get(0));
			baseGdinfo.setCustomerGroupFullName(basesheGroupCol.get(0).getName());
			baseGdinfo.setCustomerGroupStandard(basesheGroupCol.get(0).getGroupStandard());
			customer.getCustomerGroupDetails().add(baseGdinfo);
		}
		customer.setVersion(1);
		customer.setUsedStatus(UsedStatusEnum.APPROVED);
		customer.setSimpleName(ContextUtil.getCurrentFIUnit(ctx).getName());

		//同步房地产客户到系统客户的时候带上财务组织 xin_wang 2010.09.10
		if(customer.getId()!=null){
			EntityViewInfo view1 = new EntityViewInfo();
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("customer",customer.getId()));
			SelectorItemCollection coll = new SelectorItemCollection();
			coll.add("companyOrgUnit.id");
			coll.add("id");
			coll.add("customer");
			coll.add("CU.*");
			view1.setFilter(filter1);
			view1.setSelector(coll);
			CustomerCompanyInfoCollection customercoll = CustomerCompanyInfoFactory.getLocalInstance(ctx).getCustomerCompanyInfoCollection(view1);
			//经过确认这个地方不只是取到一条数据，所以比较一下CU 确认是哪个CU改变了财务资料 xin_wang 2010.09.10
			CustomerCompanyInfoInfo info = null;
			if(customercoll.size()<1){
				if(info==null){
					info = new CustomerCompanyInfoInfo();
					info.setCompanyOrgUnit(ContextUtil.getCurrentFIUnit(ctx));
					info.setCustomer(customer);
					info.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
					CustomerCompanyInfoFactory.getLocalInstance(ctx).addnew(info);
				}
			}else{
				boolean flag =true;
				for(int i =0;i<customercoll.size();i++){//判断当前财务组织有没有对应系统客户
					CustomerCompanyInfoInfo info1 = customercoll.get(i);
					if(info1.getCompanyOrgUnit()!=null){
						if(!(ContextUtil.getCurrentFIUnit(ctx).getId().equals(info1.getCompanyOrgUnit().getId()))){
							continue;
						}else{
							flag=false;
							break;
						}
					}
				}
				if(flag){//当前财务组织没有对应系统客户,有为空的财务组织编码！更新。。。
//					for(int i =0;i<customercoll.size();i++){
						CustomerCompanyInfoInfo info1 = customercoll.get(0);
						info1.setCompanyOrgUnit(ContextUtil.getCurrentFIUnit(ctx));
						SelectorItemCollection selcol = new SelectorItemCollection();
						selcol.add("companyOrgUnit.id");
						CustomerCompanyInfoFactory.getLocalInstance(ctx).updatePartial(info1, selcol);
//						if(info1.getCompanyOrgUnit()==null){//有财务资料，但是财务组织编码为空，只需要更新财务组织编码,同是还需要FDC客户所产生的系统客户没有登陆用户所对应财务组织
//							info1.setCompanyOrgUnit(ContextUtil.getCurrentFIUnit(ctx));
//							SelectorItemCollection selcol = new SelectorItemCollection();
//							selcol.add("companyOrgUnit.id");
//							CustomerCompanyInfoFactory.getLocalInstance(ctx).updatePartial(info1, selcol);
//							flag=false;
//						}
//					}
				}
//				if(flag){//没有相同的财务组织客户，也没有财务组织为空的 那就新增条吧！
//					info = new CustomerCompanyInfoInfo();
//					info.setCompanyOrgUnit(ContextUtil.getCurrentFIUnit(ctx));
//					info.setCustomer(customer);
//					info.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
//					CustomerCompanyInfoFactory.getLocalInstance(ctx).addnew(info);
//				}
			}
		}
		
		/*
		//同步房地产客户到系统客户的时候带上销售组织
		if (customer.getId() != null) {
			EntityViewInfo view1 = new EntityViewInfo();
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("customer.id", customer.getId()));
			SelectorItemCollection coll = new SelectorItemCollection();
//			coll.add("saleOrgUnit.id");
			coll.add("id");
			coll.add("customer.id");
			view1.setFilter(filter1);
			view1.setSelector(coll);
			CustomerSaleInfoCollection customerSalecoll = CustomerSaleInfoFactory.getLocalInstance(ctx)
					.getCustomerSaleInfoCollection(view1);
			// 经过确认这个地方不只是取到一条数据，所以比较一下CU 确认是哪个CU改变了财务资料 xin_wang 2010.09.10
			CustomerSaleInfoInfo info = null;
			if (customerSalecoll.size() < 1) {
				if (info == null) {
					info = new CustomerSaleInfoInfo();
					info.setSaleOrgUnit(ContextUtil.getCurrentSaleUnit(ctx));
					info.setCustomer(customer);
					info.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
					CustomerCompanyInfoFactory.getLocalInstance(ctx).addnew(info);
				}
			} else {
				boolean flag =true;
				for(int i =0;i<customerSalecoll.size();i++){//判断当前销售组织有没有对应系统客户
					CustomerSaleInfoInfo info1 = customerSalecoll.get(i);
					if(info1.getSaleOrgUnit()!=null){
						if(!(ContextUtil.getCurrentSaleUnit(ctx).getId().equals(info1.getSaleOrgUnit().getId()))){
							continue;
						}else{
							flag=false;
							break;
						}
					}
				}
				if(flag){//当前财务组织没有对应系统客户,有为空的财务组织编码！更新。。。
					CustomerSaleInfoInfo info1 = customerSalecoll.get(0);
					info1.setSaleOrgUnit(ContextUtil.getCurrentSaleUnit(ctx));
					SelectorItemCollection selcol = new SelectorItemCollection();
					selcol.add("saleOrgUnit.id");
					CustomerSaleInfoFactory.getLocalInstance(ctx).updatePartial(info1, selcol);
				}
			}
		}
		*/
	}
	
	private FDCMainCustomerInfo getMainCusByPK(Context ctx, ObjectUuidPK pk) throws EASBizException, BOSException {
    	SelectorItemCollection collection = new SelectorItemCollection();
		collection.add("*");
		collection.add("country.*");
		collection.add("province.*");
		collection.add("city.*");
		collection.add("region.*");
		collection.add("sysCustomer.*");
		FDCMainCustomerInfo fdcCustomer = (FDCMainCustomerInfo) this.getValue(ctx, pk, collection);
		return fdcCustomer;
	}


}