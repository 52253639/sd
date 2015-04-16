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
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
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
		CtrlUnitInfo cu = new CtrlUnitInfo();
		cu.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
    	if(cus == null){
    		CustomerCollection col=CustomerFactory.getLocalInstance(ctx).getCustomerCollection("select * from where number='"+mainCus.getCertificateNumber()+"'");
    		if(col.size()==0){
    			cus = new CustomerInfo();
        		cus.setId(BOSUuid.create(CustomerInfo.getBosType()));
        		cus.setCU(cu);
        		cus.setAdminCU(cu);
        		
    			setSysCustomerValue(ctx, mainCus, cus);
    			
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
    			cus.setBrowseGroup(groupInfo);
    			
    			CustomerGroupDetailInfo Gdinfo = new CustomerGroupDetailInfo();
    			Gdinfo.setCustomerGroup(groupInfo);
    			Gdinfo.setCustomerGroupFullName(groupInfo.getName());
    			Gdinfo.setCustomerGroupStandard(groupInfo.getGroupStandard());
    			cus.getCustomerGroupDetails().add(Gdinfo);
    			
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
    				cus.getCustomerGroupDetails().add(baseGdinfo);
    			}

    			CustomerFactory.getLocalInstance(ctx).addnew(cus);
    			
    			CustomerCompanyInfoInfo info = new CustomerCompanyInfoInfo();
				info.setCompanyOrgUnit((OrgUnitInfo) cu.cast(CompanyOrgUnitInfo.class));
				info.setCustomer(cus);
				info.setCU(cu);
				CustomerCompanyInfoFactory.getLocalInstance(ctx).addnew(info);
    		}else{
    			cus=col.get(0);
    			if(cus.getSimpleName()!=null&&cus.getSimpleName().indexOf(ContextUtil.getCurrentFIUnit(ctx).getName())<0){
    				cus.setSimpleName(cus.getSimpleName()+ContextUtil.getCurrentFIUnit(ctx).getName()+";");
    			}else{
    				cus.setSimpleName(ContextUtil.getCurrentFIUnit(ctx).getName()+";");
    			}
    			SelectorItemCollection selCol = new SelectorItemCollection();
        		selCol.add("simpleName");
        		selCol.add("adminCU");
        		selCol.add("usedStatus");
        		CustomerFactory.getLocalInstance(ctx).updatePartial(cus, selCol);
			}
			mainCus.setSysCustomer(cus);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("sysCustomer");
			this.updatePartial(ctx, mainCus, sels);
    	}else{
    		updateSysCustomer(ctx, mainCus, cus);//已经同步过
    	}
    	if(!cus.getAdminCU().getId().toString().equals(cu.getId().toString())){
    		String assignId=cus.getAdminCU().getId().toString();
    		SelectorItemCollection selCol = new SelectorItemCollection();
    		selCol.add("CU");
    		selCol.add("adminCU");
    		selCol.add("usedStatus");
    		cus.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
    		cus.setAdminCU(ContextUtil.getCurrentCtrlUnit(ctx));
    		cus.setUsedStatus(UsedStatusEnum.UNAPPROVE);
    		CustomerFactory.getLocalInstance(ctx).updatePartial(cus, selCol);
    		
    		cus.getCustomerGroupDetails().clear();
    		
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
			cus.setBrowseGroup(groupInfo);
			
			CustomerGroupDetailInfo Gdinfo = new CustomerGroupDetailInfo();
			Gdinfo.setCustomerGroup(groupInfo);
			Gdinfo.setCustomerGroupFullName(groupInfo.getName());
			Gdinfo.setCustomerGroupStandard(groupInfo.getGroupStandard());
			cus.getCustomerGroupDetails().add(Gdinfo);
			
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
				cus.getCustomerGroupDetails().add(baseGdinfo);
			}
    		CustomerFactory.getLocalInstance(ctx).submit(cus);
    		
    		cus.setCU(cu);
    		cus.setAdminCU(cu);
    		cus.setUsedStatus(UsedStatusEnum.APPROVED);
    		selCol = new SelectorItemCollection();
    		selCol.add("CU");
    		selCol.add("adminCU");
    		selCol.add("usedStatus");
    		CustomerFactory.getLocalInstance(ctx).updatePartial(cus, selCol);
    		
    		CustomerCompanyInfoInfo info = new CustomerCompanyInfoInfo();
			info.setCompanyOrgUnit((OrgUnitInfo) cu.cast(CompanyOrgUnitInfo.class));
			info.setCustomer(cus);
			info.setCU(cu);
			CustomerCompanyInfoFactory.getLocalInstance(ctx).addnew(info);
			
			CustomerFactory.getLocalInstance(ctx).assign(new ObjectUuidPK(OrgConstants.DEF_CU_ID), new ObjectUuidPK(cus.getId()), new ObjectUuidPK(assignId));
    	}
    	FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("customer.id",cus.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("companyOrgUnit.id",ContextUtil.getCurrentFIUnit(ctx).getId().toString()));
		if(!CustomerCompanyInfoFactory.getLocalInstance(ctx).exists(filter)){
			CustomerFactory.getLocalInstance(ctx).assign(new ObjectUuidPK(OrgConstants.DEF_CU_ID), new ObjectUuidPK(cus.getId()), new ObjectUuidPK(ContextUtil.getCurrentFIUnit(ctx).getId().toString()));
		}
//		
//    	//获得客户管理组织所属的CU
//    	Set cuIds = getCusMgeCu(ctx);
//    	for(Iterator itor = cuIds.iterator(); itor.hasNext(); ){
//    		String cuId = (String) itor.next();
//    		CustomerFactory.getLocalInstance(ctx).assign(new ObjectUuidPK(OrgConstants.DEF_CU_ID), new ObjectUuidPK(cus.getId()), new ObjectUuidPK(cuId));
//    	}
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
		selCol.add("simpleName");
		CustomerFactory.getLocalInstance(ctx).updatePartial(customer, selCol);
	}
    
	private void setSysCustomerValue(Context ctx, FDCMainCustomerInfo fdcCustomer, CustomerInfo customer) throws BOSException, EASBizException {
		String tempNumber = fdcCustomer.getNumber();
		if(tempNumber != null && !tempNumber.equals("")){
			if(tempNumber.length() >40){
				tempNumber = tempNumber.substring(0, 40);
			}
		}
		customer.setNumber(fdcCustomer.getCertificateNumber());
		customer.setName(fdcCustomer.getName());
		customer.setAddress(fdcCustomer.getMailAddress());
		customer.setCountry(fdcCustomer.getCountry());
		customer.setProvince(fdcCustomer.getProvince());
		customer.setCity(fdcCustomer.getCity());
		customer.setRegion(fdcCustomer.getRegion());
		customer.setDescription(fdcCustomer.getDescription());
		
		customer.setVersion(1);
		customer.setUsedStatus(UsedStatusEnum.APPROVED);
		if(customer.getSimpleName()!=null&&customer.getSimpleName().indexOf(ContextUtil.getCurrentFIUnit(ctx).getName())<0){
			customer.setSimpleName(customer.getSimpleName()+ContextUtil.getCurrentFIUnit(ctx).getName()+";");
		}else{
			customer.setSimpleName(ContextUtil.getCurrentFIUnit(ctx).getName()+";");
		}
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