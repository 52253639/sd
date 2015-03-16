package com.kingdee.eas.fdc.basecrm.app;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ObjectNotFoundException;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.AssociationTypeEnum;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.CreateWayEnum;
import com.kingdee.eas.fdc.basecrm.CustomerChangeLogFactory;
import com.kingdee.eas.fdc.basecrm.CustomerChangeLogInfo;
import com.kingdee.eas.fdc.basecrm.CustomerTypeEnum;
import com.kingdee.eas.fdc.basecrm.FDCBaseCustomerInfo;
import com.kingdee.eas.fdc.basecrm.FDCBaseLinkManCollection;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataFactory;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerFactory;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerCollection;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerFactory;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureCollection;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureFactory;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureInfo;
import com.kingdee.eas.fdc.basecrm.IPartCustomerInfo;
import com.kingdee.eas.fdc.basecrm.OrgCustomerLinkManFactory;
import com.kingdee.eas.fdc.basecrm.OrgCustomerLinkManInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.IPropertyContainer;
import com.kingdee.util.NumericExceptionSubItem;

public class FDCOrgCustomerControllerBean extends AbstractFDCOrgCustomerControllerBean
{
    private static final String[] EXCEPT_FIELDS = new String[]{"number", "createUnit","creator","createTime","belongUnit"};
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basecrm.app.FDCOrgCustomerControllerBean");
    
    protected void _cancel(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
    	FDCOrgCustomerInfo info = (FDCOrgCustomerInfo) model;
    	info.setIsEnabled(false);
    	
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("isEnabled");
    	_updatePartial(ctx, model, sels);
    }
    
    protected void _cancelCancel(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
    	FDCOrgCustomerInfo info = (FDCOrgCustomerInfo) model;
    	info.setIsEnabled(true);
    	
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("isEnabled");
    	_updatePartial(ctx, model, sels);
    }
    
    //获取所有客户维护组织
    private Map getCusMgeMap(Context ctx) throws BOSException{
    	Map cusMgeMap = new HashMap();
    	EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("unit.id");
		sel.add("unit.isOUSealUp");
		sel.add("tree.id");
		sel.add("unit.longNumber");
		sel.add("unit.name");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("custMangageUnit",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("unit.isOUSealUp",Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("isHis",Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("tree.id",OrgConstants.SALE_VIEW_ID));
		view.setFilter(filter);
		view.setSelector(sel);
		FDCOrgStructureCollection orgColl = FDCOrgStructureFactory.getLocalInstance(ctx).getFDCOrgStructureCollection(view);
		for(int i = 0 ; i < orgColl.size() ; i++){
			FDCOrgStructureInfo orgInfo = orgColl.get(i);
			if(orgInfo.getUnit()!=null){
				cusMgeMap.put(orgInfo.getUnit().getId().toString(),orgInfo.getUnit());
			}
		}
		return cusMgeMap;
    }
    
   
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	FDCOrgCustomerInfo cus = (FDCOrgCustomerInfo) model;
    	IObjectPK pk = innerSubmit(ctx, cus, true);
    	beforeOperate(ctx,cus);
    	FullOrgUnitInfo belongUnit = cus.getBelongUnit();
    	belongUnit = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo(new ObjectUuidPK(belongUnit.getId()));
    	Map cusMaps = getCusMgeMap(ctx);
    	List parentCusOrgs = getParentOrgs(cusMaps, belongUnit);
    	if(parentCusOrgs.isEmpty()){
    		return pk;
    	}
    	Set ids = new HashSet();
    	for(int i=0; i<parentCusOrgs.size(); i++){
    		ids.add(((OrgUnitInfo)parentCusOrgs.get(i)).getId().toString());
    	}
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("belongUnit.id", ids, CompareType.INCLUDE));
    	filter.getFilterItems().add(new FilterItemInfo("mainCustomer.id", cus.getMainCustomer().getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("id", cus.getId().toString(), CompareType.NOTEQUALS));
    	view.setFilter(filter);
		FDCOrgCustomerCollection col = getFDCOrgCustomerCollection(ctx, view);
    	
		FDCOrgCustomerCollection toAdded = new FDCOrgCustomerCollection();
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		OrgUnitInfo orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
		if (orgUnit == null){
			orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
		}
		for(int i=0; i<parentCusOrgs.size(); i++){
			OrgUnitInfo org = (OrgUnitInfo)parentCusOrgs.get(i);
			boolean exist = false;
			for(int j=0; j<col.size(); j++){
				FDCOrgCustomerInfo orgCus = col.get(j);
				if(orgCus.getBelongUnit() != null){
					if(orgCus.getBelongUnit().getId().toString().equals(org.getId().toString())){
						exist = true;
						CreateWayEnum createWay = orgCus.getCreateWay();
						CRMHelper.changeObjectValue(cus, orgCus, true, EXCEPT_FIELDS);
						orgCus.setCreateWay(createWay);
						break;
					}
				}
			}
			
			if(!exist){
				FDCOrgCustomerInfo orgCus = (FDCOrgCustomerInfo) cus.clone();
				orgCus.setId(null);
				orgCus.setBelongUnit(org.castToFullOrgUnitInfo());
				orgCus.setCreateWay(CreateWayEnum.CHILDGENERATE);
				orgCus.getLinkMan().clear();
				String retNumber = iCodingRuleManager.getNumber(orgCus, orgUnit.getId().toString());
				orgCus.setNumber(retNumber);
				toAdded.add(orgCus);
			}
    	}
    	
		col.addCollection(toAdded);
		for(int i=0; i<col.size(); i++){
			innerSubmit(ctx, col.get(i), false);
		}
    	return pk;
    }

    private IObjectPK innerSubmit(Context ctx, FDCOrgCustomerInfo cus, boolean toMain) throws EASBizException, BOSException{
    	cus.setLastUpdateUnit(ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo());
    	FDCMainCustomerInfo mainCus = cus.getMainCustomer();
    	if(toMain){
    		if(mainCus == null){
        		mainCus = new FDCMainCustomerInfo();
        		mainCus.setId(BOSUuid.create(mainCus.getBOSType()));
        		CRMHelper.changeObjectValue((IPropertyContainer) cus.clone(), mainCus, false);
        		        		
        		cus.setMainCustomer(mainCus);
        		
        	}else{
        		//TODO 补充信息到房地产主客户上
        		mainCus = FDCMainCustomerFactory.getLocalInstance(ctx).getFDCMainCustomerInfo(new ObjectUuidPK(mainCus.getId()));
        		BOSUuid uuid = mainCus.getId();
        		//TODO 创建信息不应该更新
        		CRMHelper.changeObjectValue((IPropertyContainer) cus.clone(), mainCus, true, EXCEPT_FIELDS);
        		mainCus.setId(uuid);
        	}
        	
        	FDCMainCustomerFactory.getLocalInstance(ctx).submit(mainCus);
    	}
    	
    	//修改情况下，
//    	if(cus.getId() != null){
//    		try{
//    			FDCOrgCustomerInfo oldCus = getFDCOrgCustomerInfo(ctx, new ObjectUuidPK(cus.getId()));
//    			CoreBaseCollection logs = getCustomerChangeLogs(oldCus, cus, ctx);
//        		if(!logs.isEmpty()){
//        			for(int i=0; i<logs.size(); i++){
//        				CustomerChangeLogInfo log = (CustomerChangeLogInfo) logs.get(i);
//        				log.setOrgCustomer(oldCus);
//        			}
//        			
//        			CustomerChangeLogFactory.getLocalInstance(ctx).addnew(logs);
//        		}
//    		}catch(ObjectNotFoundException e){
//    			logger.debug(e);
//    		}
//    	}
    	
    	return super._submit(ctx, cus);
    }
    
    /**
     * 重载修改的方法，调用时添加变更记录
     */
    protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
    		throws BOSException, EASBizException {
//     	FDCOrgCustomerInfo cus = new FDCOrgCustomerInfo();
//    	OrgCustomerLinkManInfo oclmi = new OrgCustomerLinkManInfo();
//    	cus.putAll(model); 
//    	oclmi.putAll(model);
    	FDCOrgCustomerInfo cus = (FDCOrgCustomerInfo) model;
    	if(cus.getId() != null){
    		try{
    			FDCOrgCustomerInfo oldCus = getFDCOrgCustomerInfo(ctx, new ObjectUuidPK(cus.getId()));
    			CoreBaseCollection logs = getCustomerChangeLogs(oldCus, cus, ctx);
        		if(!logs.isEmpty()){
        			for(int i=0; i<logs.size(); i++){
        				CustomerChangeLogInfo log = (CustomerChangeLogInfo) logs.get(i);
        				log.setOrgCustomer(oldCus);
        			}
        			
        			CustomerChangeLogFactory.getLocalInstance(ctx).addnew(logs);
        		}
    		}catch(ObjectNotFoundException e){
    			logger.debug(e);
    		}
    	}
    	super._update(ctx, pk, model);
    }
    
    /**
     * 保存首先联系人或企业法人时，同步到它们对应的企业客户
     * @param ctx
     * @param set
     * @param str1
     * @param str2
     * @param isLinkMan
     * @throws BOSException
     * @throws EASBizException
     */
    protected void _updateEnterpriceCustomer(Context ctx, Set set, String str1,String str2,boolean isLinkMan) throws BOSException, EASBizException
    {
    	Iterator it = set.iterator();
		while(it.hasNext()){
			String id = it.next().toString();
			FDCOrgCustomerInfo oldEnterpriceInfo = FDCOrgCustomerFactory.getLocalInstance(ctx).getFDCOrgCustomerInfo(new ObjectStringPK(id));
			String oldPhone = oldEnterpriceInfo.getPhone();
			String oldTel = oldEnterpriceInfo.getTel();
			String oldCorporateTel = oldEnterpriceInfo.getCorporateTel();
			
			FDCOrgCustomerInfo enterpriceInfo = oldEnterpriceInfo;
			if(isLinkMan){
				enterpriceInfo.setPhone(str1);
				enterpriceInfo.setTel(str2);
				this.updateEnterpriceCusLog(ctx, oldPhone, str1, "移动电话", enterpriceInfo);
				this.updateEnterpriceCusLog(ctx, oldTel, str2, "住宅电话", enterpriceInfo);
			}else{
				enterpriceInfo.setCorporate(str1);
				enterpriceInfo.setCorporateTel(str2);
				this.updateEnterpriceCusLog(ctx, oldCorporateTel, str2, "住宅电话", enterpriceInfo);
				
			}
			this.update(ctx, new ObjectStringPK(id), enterpriceInfo);
		}
    }
    
    private void updateEnterpriceCusLog(Context ctx,String str1,String str2,String type,FDCOrgCustomerInfo info) throws EASBizException, BOSException{
    	CoreBaseCollection changes = new CoreBaseCollection();
		this.getChangeLog(ctx, changes, str1,str2, type);
		if(!changes.isEmpty()){
			for(int i=0; i<changes.size(); i++){
				CustomerChangeLogInfo log = (CustomerChangeLogInfo) changes.get(i);
				log.setOrgCustomer(info);
			}
			CustomerChangeLogFactory.getLocalInstance(ctx).addnew(changes);
		}
    }
    private List getParentOrgs(Map cusMaps, FullOrgUnitInfo belongUnit) {
    	List list = new ArrayList();
    	Collection col = cusMaps.values();
    	String bLo = belongUnit.getLongNumber();
    	for(Iterator itor = col.iterator(); itor.hasNext(); ){
    		OrgUnitInfo org = (OrgUnitInfo) itor.next();
    		String lo = org.getLongNumber();
    		if(bLo.startsWith(lo) && !bLo.equals(lo)){
    			list.add(org);
    		}
    	}
    	
    	return list;
	}

	/**
	 * 判断被修改的字段
	 * @param ctx 
	 * 
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @throws Exception
	 */
	private CoreBaseCollection getCustomerChangeLogs(FDCBaseCustomerInfo oldCus, FDCBaseCustomerInfo cus, Context ctx) throws EASBizException, BOSException{
		CoreBaseCollection changes = new CoreBaseCollection();
		Date now = new Date();
		Timestamp time = new Timestamp(now.getTime());
		UserInfo user = ContextUtil.getCurrentUserInfo(ctx);
		FullOrgUnitInfo org = ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo();
		this.getChangeLog(ctx, changes, oldCus.getName(), cus.getName(), "客户名称");
		this.getChangeLog(ctx, changes, oldCus.getOfficeTel(), cus.getOfficeTel(), "办公电话");
		this.getChangeLog(ctx, changes, oldCus.getPhone(), cus.getPhone(), "移动电话");
		this.getChangeLog(ctx, changes, oldCus.getTel(), cus.getTel(), "住宅电话");
		this.getChangeLog(ctx, changes, oldCus.getFax(), cus.getFax(), "传真");
		this.getChangeLog(ctx, changes, oldCus.getCertificateNumber(), cus.getCertificateNumber(), "证件号码");

		// 客户身份F7,特别处理
		if ((oldCus.getIdentity() != null && oldCus.getIdentity().getId() != null) || (cus.getIdentity() != null && cus.getIdentity().getId() != null)) {
			String oldField = "";
			String newField = "";
			if (oldCus.getIdentity() == null) {
				oldField = "";
			} else {
				FDCCusBaseDataInfo oldInfo = FDCCusBaseDataFactory.getLocalInstance(ctx).getFDCCusBaseDataInfo(new ObjectUuidPK(oldCus.getIdentity().getId()));
				oldField = oldInfo.getName();
			}
			
			if (cus.getIdentity() == null) {
				newField = "";
			} else {
				FDCCusBaseDataInfo newInfo = FDCCusBaseDataFactory.getLocalInstance(ctx).getFDCCusBaseDataInfo(new ObjectUuidPK(cus.getIdentity().getId()));
				newField = newInfo.getName();
			}
			if ((oldField != null && !oldField.equals("")) || (newField != null && !newField.equals(""))) {
				if (oldField == null) {
					oldField = "";
				}
				if (newField == null) {
					newField = "";
				}
			if (!oldField.equals(newField)) {
				CustomerChangeLogInfo info = new CustomerChangeLogInfo();
				info.setOldField("客户身份：" + oldField);
				info.setNewField("客户身份：" + newField);
				info.setCreateTime(time);
				info.setCreator(user);
				info.setOrgUnit(org);
				changes.add(info);
			}
			}
		}

		boolean isEn = CustomerTypeEnum.ENTERPRICECUSTOMER.equals(oldCus.getCustomerType());
		this.getChangeLog(ctx, changes, oldCus.getMailAddress(), cus.getMailAddress(), isEn ? "公司地址" : "通信地址");
		this.getChangeLog(ctx, changes, oldCus.getBookedAddress(), cus.getBookedAddress(), isEn ? "注册地址" : "证件地址");

		// 企业客户，另外多关注三个字段的变更
		if (isEn) {
			this.getChangeLog(ctx, changes, oldCus.getFirstLinkman(), cus.getFirstLinkman(), "首选联系人");
			this.getChangeLog(ctx, changes, oldCus.getCorporate(), cus.getCorporate(), "公司法人");
			this.getChangeLog(ctx, changes, oldCus.getCorporateTel(), cus.getCorporateTel(), "公司法人移动电话");
		}
		return changes;
	}
    
	private void getChangeLog(Context ctx, CoreBaseCollection changes, String oldField, String newField, String columnName) {
		Date now = new Date();
		Timestamp time = new Timestamp(now.getTime());
		UserInfo user = ContextUtil.getCurrentUserInfo(ctx);
		FullOrgUnitInfo org = ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo();

		if ((oldField != null && !oldField.equals("")) || (newField != null && !newField.equals(""))) {
			if (oldField == null) {
				oldField = "";
			}
			if (newField == null) {
				newField = "";
			}
			if (!oldField.equals(newField)) {
				CustomerChangeLogInfo info = new CustomerChangeLogInfo();
				info.setOldField(columnName + "：" + oldField);
				info.setNewField(columnName + "：" + newField);
				info.setCreateTime(time);
				info.setCreator(user);
				info.setOrgUnit(org);
				changes.add(info);
			}
		}
	}
	
    /**
     * @deprecated
     * */
	private void in_mergeCustomer(Context ctx, IObjectCollection srcCuses, IObjectValue toCus) throws BOSException, EASBizException {
		if(true) return;
		
		IPartCustomerInfo toCusPC = (IPartCustomerInfo) toCus;
		FDCMainCustomerInfo toMainCus = toCusPC.getMainCustomer();
		
		String toCusId = toCusPC.get("id").toString();
		String toMainCusId = toMainCus.getId().toString();
		
		Set srcCusIds = new HashSet();
		Set srcMainCusIds = new HashSet();
		for(int i=0; i<srcCuses.size(); i++){
			IPartCustomerInfo pC = (IPartCustomerInfo) srcCuses.getObject(i);
			FDCMainCustomerInfo mainCus = pC.getMainCustomer();
			
			String cusId = pC.get("id").toString();
			String mainCusId = mainCus.getId().toString();
			
			if(cusId.equals(toCusId)){
				continue;
			}
			
			srcCusIds.add(cusId);
			srcMainCusIds.add(mainCusId);			
		}
		
		IObjectPK[] srcCusPks = new ObjectUuidPK[srcCusIds.size()];
		int tmpi = 0;
		for(Iterator itor = srcCusIds.iterator(); itor.hasNext(); ){
			srcCusPks[tmpi] = new ObjectUuidPK((String)itor.next());
			tmpi++;
		}
		
		IObjectPK[] srcMainCusPks = new ObjectUuidPK[srcMainCusIds.size()];
		tmpi = 0;
		for(Iterator itor = srcMainCusIds.iterator(); itor.hasNext(); ){
			srcMainCusPks[tmpi] = new ObjectUuidPK((String)itor.next());
			tmpi++;
		}
		
		//1.TODO 将业务关联到srcCusPks的全部修改为toCusId
		
		//2.合并partCustomer
		String sql1 = "update T_BDC_FDCORGCUSTOMER set fmaincustomerid=? where Fmaincustomerid=?";
		String sql2 = "update T_SHE_SHECUSTOMER set fmaincustomerid=? where Fmaincustomerid=?";
		
		Object[] params = new Object[2];
		params[0] = toCusId;
		for(Iterator itor = srcCusIds.iterator(); itor.hasNext(); ){
			String id = (String) itor.next();
			params[1] = id;
			DbUtil.execute(ctx, sql1, params);
			DbUtil.execute(ctx, sql2, params);
		}
		
		FDCMainCustomerFactory.getLocalInstance(ctx).delete(srcMainCusPks);
		if(toCus instanceof FDCOrgCustomerInfo){
			FDCOrgCustomerFactory.getLocalInstance(ctx).delete(srcCusPks);
		}else if(toCus instanceof SHECustomerInfo){
			SHECustomerFactory.getLocalInstance(ctx).delete(srcCusPks);
		}else{
			//TODO ...
		}
	}

	protected void _changeCusName(Context ctx, String cusId, String newName) throws BOSException, EASBizException {
		FDCOrgCustomerInfo orgCus = getFDCOrgCustomerInfo(ctx, new ObjectUuidPK(cusId));
		
		orgCus.setName(newName);
		
		_submit(ctx, orgCus);
//		String sql1 = "update T_BDC_FDCMainCustomer set fname_l2=? where fid=?";		
//		Object[] params = new Object[]{newName, cusId};
//		DbUtil.execute(ctx, sql1, params);
//		
//		String sql2 = "update T_BDC_FDCORGCUSTOMER set fname_l2=? where fmainCustomerid=?";
//		DbUtil.execute(ctx, sql2, params);
	}

	protected void _shareCustomer(Context ctx, List cusIds, String shareOrgId) throws BOSException, EASBizException {
		//获得当前销售组织和上级销售组织
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SelectorItemCollection sels= new SelectorItemCollection();
		sels.add("*");
		view.setSelector(sels);
		filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.list2Set(cusIds), CompareType.INCLUDE));
		view.setFilter(filter);
		FDCOrgCustomerCollection srcOrgCuses = FDCOrgCustomerFactory.getLocalInstance(ctx).getFDCOrgCustomerCollection(view);
		
		Set mainIds = new HashSet();
		for(int i=0; i<srcOrgCuses.size(); i++){
			FDCOrgCustomerInfo srcCus = srcOrgCuses.get(i);
			if(srcCus.getMainCustomer() != null){
				mainIds.add(srcCus.getMainCustomer().getId().toString());
			}
		}
		
		view = new EntityViewInfo();
		sels= new SelectorItemCollection();
		sels.add("mainCustomer.id");
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("mainCustomer.id", mainIds, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("belongUnit.id", shareOrgId));
		view.setFilter(filter);
		view.setSelector(sels);
		
		Set exceptMinId = new HashSet();
		FDCOrgCustomerCollection ex = FDCOrgCustomerFactory.getLocalInstance(ctx).getFDCOrgCustomerCollection(view);
		for(int i=0; i<ex.size(); i++){
			FDCOrgCustomerInfo orgCus = ex.get(i);
			if(orgCus.getMainCustomer() != null){
				exceptMinId.add(orgCus.getMainCustomer().getId().toString());
			}
		}
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		OrgUnitInfo orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
		if (orgUnit == null)
			orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
		boolean existCodingRule = iCodingRuleManager.isExist(new FDCOrgCustomerInfo(), orgUnit.getId().toString());
		FullOrgUnitInfo tmp = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo(new ObjectUuidPK(shareOrgId));
		for(int i=0; i<srcOrgCuses.size(); i++){
			FDCOrgCustomerInfo srcCus = srcOrgCuses.get(i);
			
			FDCMainCustomerInfo srcMainCus = srcCus.getMainCustomer();
			if(srcMainCus != null  &&  exceptMinId.contains(srcMainCus.getId().toString())){
				continue;
			}
			
			FDCOrgCustomerInfo orgCus = (FDCOrgCustomerInfo) srcCus.clone();
			orgCus.getLinkMan().clear();
			orgCus.setId(BOSUuid.create(orgCus.getBOSType()));
			orgCus.setCreateWay(CreateWayEnum.UNITSHARE);
			
			orgCus.setBelongUnit(tmp);
			
			if(existCodingRule){
				String retNumber = iCodingRuleManager.getNumber(orgCus, orgUnit.getId().toString());
				orgCus.setNumber(retNumber);
			}else{
				throw new EASBizException(new NumericExceptionSubItem("100",orgCus+"的编码规则未配置，请先配置编码规则！" ));
//				String tempNumber = orgCus.getNumber();
//				if(tempNumber != null && !tempNumber.equals("")){
//					if(tempNumber.length() >40){
//						tempNumber = tempNumber.substring(0, 40);
//					}
//					
//				}
//				String number = "SHARE_" +tempNumber + "_" + System.currentTimeMillis();
//				orgCus.setNumber(number);
//				Timestamp time = new Timestamp(new Date().getTime());
//				String milliSecond = String.valueOf(time).substring(20, 23);
//				String timeStr = String.valueOf(time).replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "").substring(0,14)+milliSecond;
//				String number = String.valueOf(timeStr)+"001";
//				orgCus.setNumber(number);
			}
			
			FDCOrgCustomerFactory.getLocalInstance(ctx).submit(orgCus);	
		}
	}

	protected void _updateCustomerInfo(Context ctx, List ids) throws BOSException, EASBizException {
		if(ids.isEmpty()){
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.list2Set(ids), CompareType.INCLUDE));
		view.setFilter(filter);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("number");
		sels.add("name");
		sels.add("belongUnit.id");
		sels.add("belongUnit.number");
		sels.add("belongUnit.longNumber");
		view.setSelector(sels);
		FDCOrgCustomerCollection orgCuses = FDCOrgCustomerFactory.getLocalInstance(ctx).getFDCOrgCustomerCollection(view);
		
		if(orgCuses.isEmpty()){
			return;
		}
		
		//循环套循环……
		//根据当前所属组织（belongUnit）得到longNumber, 查出上级管理单元对应的客户资料，一同更新
		for(int i=0; i<orgCuses.size(); i++){
			FDCOrgCustomerInfo orgCus = orgCuses.get(i);
			FDCOrgCustomerCollection allOrgCuses = getAllOrgCustomerColl(ctx,orgCus);
			if(allOrgCuses.isEmpty()){
				return;
			}
			for(int j=0; j<allOrgCuses.size(); j++){
				FDCOrgCustomerInfo allOrgCus = allOrgCuses.get(j);
				FDCMainCustomerInfo allMainCus = allOrgCus.getMainCustomer();
				
				CRMHelper.changeObjectValue(allMainCus, allOrgCus, true, EXCEPT_FIELDS);
				if(allOrgCus.getId() != null){
		    		try{
		    			FDCOrgCustomerInfo oldCus = getFDCOrgCustomerInfo(ctx, new ObjectUuidPK(allOrgCus.getId()));
		    			CoreBaseCollection logs = getCustomerChangeLogs(oldCus, allOrgCus, ctx);
		        		if(!logs.isEmpty()){
		        			for(int k=0; k<logs.size(); k++){
		        				CustomerChangeLogInfo log = (CustomerChangeLogInfo) logs.get(k);
		        				log.setOrgCustomer(oldCus);
		        			}
		        			CustomerChangeLogFactory.getLocalInstance(ctx).addnew(logs);
		        		}
		    		}catch(ObjectNotFoundException e){
		    			logger.debug(e);
		    		}
		    	}
			}
			_updateBatchData(ctx, allOrgCuses);
		}
	}
	
	
	/**
	 * 得到上级管理单元
	 * @param ctx
	 * @param orgCus
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private FDCOrgCustomerCollection getAllOrgCustomerColl(Context ctx,FDCOrgCustomerInfo orgCus)throws BOSException, EASBizException{
		Set belongUnitIdSet = new HashSet();
		if(orgCus.getBelongUnit().isEmpty()){
			return null;
		}
		String longNumber = orgCus.getBelongUnit().getLongNumber();
		String numbers[] = longNumber.split("!");// 对所属组织的长编码进行拆分
		Set numberSet = new HashSet();
		for(int i = 0;i<numbers.length;i++){
			numberSet.add(numbers[i]);
		}
		
		if(numberSet.isEmpty()){
			return null;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number",numberSet, CompareType.INCLUDE));
		view.setFilter(filter);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("number");
		view.setSelector(sels);
		FullOrgUnitCollection tmp = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitCollection(view);
		if(tmp.isEmpty()){
			return null;
		}
		for(int j=0;j<tmp.size();j++){
			belongUnitIdSet.add(tmp.get(j).getId());
		}
		
		if(belongUnitIdSet.isEmpty()){
			return null;
		}
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("belongUnit.id", belongUnitIdSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("name", orgCus.getName(), CompareType.EQUALS));
		view.setFilter(filter);
		sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("mainCustomer.*");
		view.setSelector(sels);
		FDCOrgCustomerCollection allOrgCuses = FDCOrgCustomerFactory.getLocalInstance(ctx).getFDCOrgCustomerCollection(view);
		
		return allOrgCuses;
	}

	protected void _mergeCustomer(Context ctx, List srcIds, String toId) throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.list2Set(srcIds), CompareType.INCLUDE));
		view.setFilter(filter);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("mainCustomer.id");
		FDCOrgCustomerCollection orgCuses = getFDCOrgCustomerCollection(ctx, view);
		
		String toMainId = null;
		for(int i=0; i<orgCuses.size(); i++){
			FDCOrgCustomerInfo orgCus = orgCuses.get(i);
			if(toId.equals(orgCus.getId().toString())){
				toMainId = orgCus.getMainCustomer().getId().toString();
			}
		}
		
		String toCusId = toId;
		String toMainCusId = toMainId;
		
		Set srcCusIds = new HashSet();
		Set srcMainCusIds = new HashSet();
		for(int i=0; i<orgCuses.size(); i++){
			IPartCustomerInfo pC = (IPartCustomerInfo) orgCuses.getObject(i);
			FDCMainCustomerInfo mainCus = pC.getMainCustomer();
			
			String cusId = pC.get("id").toString();
			String mainCusId = mainCus.getId().toString();
			
			if(cusId.equals(toCusId)){
				continue;
			}
			srcCusIds.add(cusId);
			if(mainCusId.equals(toMainCusId)){
				continue;
			}
			srcMainCusIds.add(mainCusId);			
		}
		
		IObjectPK[] srcCusPks = new ObjectUuidPK[srcCusIds.size()];
		int tmpi = 0;
		for(Iterator itor = srcCusIds.iterator(); itor.hasNext(); ){
			srcCusPks[tmpi] = new ObjectUuidPK((String)itor.next());
			tmpi++;
		}
		
		//1.TODO 将业务关联到srcCusPks的全部修改为toCusId
		
		//2.合并partCustomer
		String sql1 = "update T_BDC_FDCORGCUSTOMER set fmaincustomerid=? where Fmaincustomerid=?";
		String sql2 = "update T_SHE_SHECUSTOMER set fmaincustomerid=? where Fmaincustomerid=?";
		
		Object[] params = new Object[2];
		params[0] = toMainCusId;
		for(Iterator itor = srcMainCusIds.iterator(); itor.hasNext(); ){
			String id = (String) itor.next();
			params[1] = id;
			DbUtil.execute(ctx, sql1, params);
			DbUtil.execute(ctx, sql2, params);
		}
		
		if(!srcMainCusIds.isEmpty()){
			IObjectPK[] srcMainCusPks = new ObjectUuidPK[srcMainCusIds.size()];
			tmpi = 0;
			for(Iterator itor = srcMainCusIds.iterator(); itor.hasNext(); ){
				srcMainCusPks[tmpi] = new ObjectUuidPK((String)itor.next());
				tmpi++;
			}
			FDCMainCustomerFactory.getLocalInstance(ctx).delete(srcMainCusPks);
		}
		FDCOrgCustomerFactory.getLocalInstance(ctx).delete(srcCusPks);

	}

	protected void _importCustomer(Context ctx, IObjectCollection res) throws BOSException, EASBizException {
		for(int i=0; i<res.size(); i++){
			FDCOrgCustomerInfo cus = (FDCOrgCustomerInfo) res.getObject(i);
			String name = cus.getName();
			String cerNum = cus.getCertificateNumber();
//			String phone = cus.getPhone();
//			String tel = cus.getTel();
//			String officeTel = cus.getOfficeTel();
//			String fax = cus.getFax();
			FullOrgUnitInfo belongUnit = cus.getBelongUnit();
			boolean isExist = false;
			//TODO  如果重复的，不导入.
			//重复的规则：姓名，（4个电话号码任一号码），证件号码 任意22组合重复（注意过滤空）,需要精确匹配
			//此处需要判断此组织下是否有重复的，如果有，continue;
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("belongUnit.id",belongUnit.getId()));//所属组织
			filter.getFilterItems().add(new FilterItemInfo("name",name));
			if(cerNum == null || cerNum.length() == 0){
				FilterInfo phoneFilter = this.getPhoneFilter(cus);
			    filter.mergeFilter(phoneFilter, "AND");
			}else{
				filter.getFilterItems().add(new FilterItemInfo("certificateNumber",cerNum));
				filter.mergeFilter(filter, "AND");
				isExist = FDCOrgCustomerFactory.getLocalInstance(ctx).exists(filter);
				if(isExist){
					throw new EASBizException(new NumericExceptionSubItem("100","客户资料不能重复！" ));
				}else{
					filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("belongUnit.id",belongUnit.getId()));//所属组织
					filter.getFilterItems().add(new FilterItemInfo("certificateNumber",cerNum));
					FilterInfo phoneFilter = this.getPhoneFilter(cus);
				    filter.mergeFilter(phoneFilter, "AND");
				}
			}
			
			isExist = FDCOrgCustomerFactory.getLocalInstance(ctx).exists(filter);
			if(isExist){
				throw new EASBizException(new NumericExceptionSubItem("100","客户资料不能重复！" ));
				//continue;
			}
			this._submit(ctx, res.getObject(i));
		}
	}
	
	/**
	 * 将四个电话加入判断条件中，用于导入，
	 * @author youzhen_qin, 20110801
	 * @param phone
	 * @param tel
	 * @param officeTel
	 * @param fax
	 * @return
	 */
	private FilterInfo getPhoneFilter(FDCOrgCustomerInfo cus){
		String phone = cus.getPhone();
		String tel = cus.getTel();
		String officeTel = cus.getOfficeTel();
		String fax = cus.getFax();
		FilterInfo phoneFilter = new FilterInfo();
		if(phone != null && !phone.equals("")){
			phoneFilter.getFilterItems().add(new FilterItemInfo("phone",phone));
			if(tel != null && !tel.equals("")){
				phoneFilter.getFilterItems().add(new FilterItemInfo("tel",tel));
				
				if(officeTel != null && !officeTel.equals("")){
					phoneFilter.getFilterItems().add(new FilterItemInfo("officeTel",officeTel));
					
					if(fax != null && !fax.equals("")){
						phoneFilter.getFilterItems().add(new FilterItemInfo("fax",fax));
						phoneFilter.setMaskString("#0 or #1 or #2 or #3");
					}else{
						phoneFilter.setMaskString("#0 or #1 or #2 ");
					}
				}else{
					phoneFilter.setMaskString("#0 or #1");
				}
			}
		}else{
			if(tel != null && !tel.equals("")){
				phoneFilter.getFilterItems().add(new FilterItemInfo("tel",tel));
				
				if(officeTel != null && !officeTel.equals("")){
					phoneFilter.getFilterItems().add(new FilterItemInfo("officeTel",officeTel));
					
					if(officeTel != null && !officeTel.equals("")){
						phoneFilter.getFilterItems().add(new FilterItemInfo("fax",fax));
						phoneFilter.setMaskString("#0 or #1 or #2");
					}else{
						phoneFilter.setMaskString("#0 or #1 ");
					}
				}
			}
		}
		return phoneFilter;
	}
	
	
	private FDCOrgCustomerInfo getNumberForInfo(Context ctx,FDCOrgCustomerInfo info){
		try {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
			OrgUnitInfo orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
			boolean existCodingRule = iCodingRuleManager.isExist(
					new FDCOrgCustomerInfo(), orgUnit.getId().toString());
			if (existCodingRule) {
				String retNumber = iCodingRuleManager.getNumber(info, orgUnit
						.getId().toString());
				info.setNumber(retNumber);
			}else {
				throw new EASBizException(new NumericExceptionSubItem("100",orgUnit+"的编码规则未配置，请先配置编码规则！" ));
//				String tempNumber = info.getNumber();
//				if(tempNumber != null && !tempNumber.equals("")){
//					if(tempNumber.length() >40){
//						tempNumber = tempNumber.substring(0, 40);
//					}
//				}
//				String number = orgUnit.getName() + "_" + tempNumber + "_" + System.currentTimeMillis();
//				info.setNumber(number);
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return info;
	}
	
	protected void _submitEnterpriceCustomer(Context ctx, IObjectValue model,
			String name, String phone) throws BOSException, EASBizException {
	    FDCOrgCustomerInfo info =  (FDCOrgCustomerInfo) model;
		FDCOrgCustomerInfo newInfo = new FDCOrgCustomerInfo();
		boolean isLegalPerson = false;
		newInfo = getNumberForInfo(ctx,newInfo);
		newInfo.setCustomerType(CustomerTypeEnum.PERSONALCUSTOMER);
		newInfo.setName(name);
		if(phone == null){
			isLegalPerson = false;
			newInfo.setPhone(info.getPhone());
		}else{
			isLegalPerson = true;
			newInfo.setPhone(phone);
		}
		
		Date now = new Date();
		Timestamp time = new Timestamp(now.getTime());
		UserInfo user = ContextUtil.getCurrentUserInfo(ctx);
		FullOrgUnitInfo org = ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo();
		
		newInfo.setTel(info.getTel());
		newInfo.setOfficeTel(info.getOfficeTel());
		newInfo.setOtherTel(info.getOtherTel());
		newInfo.setEmail(info.getEmail());
		newInfo.setMailAddress(info.getMailAddress());
		newInfo.setBookedAddress(info.getBookedAddress());
		newInfo.setPostalCode(info.getPostalCode());
		newInfo.setDescription(info.getDescription());
		newInfo.setBelongUnit(info.getBelongUnit());
		newInfo.setCreateTime(time);
		newInfo.setCreateUnit(org);
		newInfo.setCreator(user);
		newInfo.setIsEnabled(true);
		
		phone = newInfo.getPhone();
		String tel = newInfo.getTel();
		String officeTel = newInfo.getOfficeTel();
		String fax = newInfo.getFax();
		String certificateNumber = newInfo.getCertificateNumber();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("customerType", CustomerTypeEnum.PERSONALCUSTOMER));
		
		if (name != null && !name.equals("")) {
			filter.getFilterItems().add(
					new FilterItemInfo("name", name, CompareType.EQUALS));
			if (certificateNumber != null && !certificateNumber.equals("")) {
				filter.getFilterItems().add(
						new FilterItemInfo("certificateNumber",
								certificateNumber, CompareType.EQUALS));
			if (FDCOrgCustomerFactory.getLocalInstance(ctx).exists(filter)) {
//					MsgBox.showInfo("客户名称、证件号码已经存在！不能重复");
//					SysUtil.abort();
				throw new EASBizException(new NumericExceptionSubItem("100","该客户已经存在，请与主管沟通！" ));
				}else{
					if (phone != null && !phone.equals("")) {
						filter.getFilterItems().add(
								new FilterItemInfo("phone", phone,
										CompareType.EQUALS));
					}else if(tel != null && !tel.equals("")){
						filter.getFilterItems().add(
								new FilterItemInfo("tel", tel,
										CompareType.EQUALS));
					}else if(officeTel != null && !officeTel.equals("")){
						filter.getFilterItems().add(
								new FilterItemInfo("officeTel", officeTel,
										CompareType.EQUALS));
					}else if(fax != null && !fax.equals("")){
						filter.getFilterItems().add(
								new FilterItemInfo("fax", fax,
										CompareType.EQUALS));
					}
					if (FDCOrgCustomerFactory.getLocalInstance(ctx).exists(filter)) {
//						MsgBox.showInfo("客户名称、联系方式已经存在！不能重复");
//						SysUtil.abort();
						throw new EASBizException(new NumericExceptionSubItem("100","该客户已经存在，请与主管沟通！" ));
					}
				}
			}else{
				if (phone != null && !phone.equals("")) {
					filter.getFilterItems().add(
							new FilterItemInfo("phone", phone,
									CompareType.EQUALS));
				}else if(tel != null && !tel.equals("")){
					filter.getFilterItems().add(
							new FilterItemInfo("tel", tel,
									CompareType.EQUALS));
				}else if(officeTel != null && !officeTel.equals("")){
					filter.getFilterItems().add(
							new FilterItemInfo("officeTel", officeTel,
									CompareType.EQUALS));
				}else if(fax != null && !fax.equals("")){
					filter.getFilterItems().add(
							new FilterItemInfo("fax", fax,
									CompareType.EQUALS));
				}
				if (FDCOrgCustomerFactory.getLocalInstance(ctx).exists(filter)) {
					//MsgBox.showInfo("客户名称、联系方式已经存在！不能重复");
					//SysUtil.abort();
					throw new EASBizException(new NumericExceptionSubItem("100","该客户已经存在，请与主管沟通！" ));
				}
			}
			
			this._submit(ctx, newInfo);
			
			OrgCustomerLinkManInfo linkInfo = new OrgCustomerLinkManInfo();
			linkInfo.setAssociationType(AssociationTypeEnum.SYSTEM);
			linkInfo.setIsAssociation(true);
			if(!isLegalPerson){
				linkInfo.setRelation("企业职员");
			}else{
				linkInfo.setRelation("企业法人");
			}
			
			linkInfo.setName(name);
			filter = new FilterInfo();
			//filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("name", linkInfo.getName()));
			boolean isExist = OrgCustomerLinkManFactory.getLocalInstance(ctx).exists(filter);
			if(isExist){
				throw new EASBizException(new NumericExceptionSubItem("100","联系人名称不能重复！" ));
			}
			linkInfo.setPhone(newInfo.getPhone());
			linkInfo.setTel(newInfo.getTel());
			linkInfo.setOfficeTel(newInfo.getOfficeTel());
			linkInfo.setOtherTel(newInfo.getOtherTel());
			linkInfo.setEmail(newInfo.getEmail());
			linkInfo.setMailAddress(newInfo.getMailAddress());
			linkInfo.setBookedAddress(newInfo.getBookedAddress());
			linkInfo.setPostalCode(newInfo.getPostalCode());
			linkInfo.setDescription(newInfo.getDescription());
			linkInfo.setOrgCustomer(info);
			linkInfo.setNumber(newInfo.getNumber());
			linkInfo.setCreateTime(time);
			linkInfo.setCreator(user);
			OrgCustomerLinkManFactory.getLocalInstance(ctx).addnew(linkInfo);
		}
	}
	
	/**
	 * 验证名称和电话
	 * @param ctx
	 * @param cus
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void verifyNameAndPhone(Context ctx,FDCOrgCustomerInfo cus) throws BOSException, EASBizException{
		/*FilterInfo filter = new FilterInfo();
		Object obj = cus.getId();
		if(obj == null){
			return;
		}
		String id = obj.toString();
		filter.getFilterItems().add(
				new FilterItemInfo("id", id, CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("customerType", cus.getCustomerType()
						.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("belongUnit.id",cus.getBelongUnit().getId().toString()));
		
		
		String name = cus.getName();
    	String phone = cus.getPhone().trim();
    	String tel = "";
    	if(cus.getTel() != null){
    		 tel = cus.getTel().trim();
    	}
    	String officeTel = "";
    	if(cus.getOfficeTel() != null){
    		officeTel = cus.getOfficeTel().trim();
    	}
		String fax = "";
		if(cus.getFax() != null){
			fax = cus.getFax().trim();
		}
		filter.getFilterItems().add(new FilterItemInfo("name", name));
		
		if (phone != null && !phone.equals("")) {
			filter.getFilterItems().add(
					new FilterItemInfo("phone", phone));
		}else if(tel != null && !tel.equals("")){
			filter.getFilterItems().add(
					new FilterItemInfo("tel", tel));
		}else if(officeTel != null && !officeTel.equals("")){
			filter.getFilterItems().add(
					new FilterItemInfo("officeTel", officeTel));
		}else if(fax != null && !fax.equals("")){
			filter.getFilterItems().add(
					new FilterItemInfo("fax", fax));
		}
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selectcoll = new SelectorItemCollection();
		selectcoll.add(new SelectorItemInfo("id"));
		view.setSelector(selectcoll);
		view.setFilter(filter);
		IFDCOrgCustomer fdcFacad  = FDCOrgCustomerFactory.getLocalInstance(ctx);
		FDCOrgCustomerCollection coll = fdcFacad.getFDCOrgCustomerCollection(view);
		if(coll != null && coll.size()>0){
			throw new EASBizException(new NumericExceptionSubItem("100",cus.getBelongUnit()+"下客户名称、联系方式已经存在！不能重复" ));
		}
		*/
		
		String sql = "select * from T_BDC_FDCOrgCustomer where fid='"
				+ cus.getId().toString()
				+ "' and fisenabled=1 and fcustomertype='"
				+ cus.getCustomerType() + "' and fbelongunitid='"
				+ cus.getBelongUnit().getId().toString() + "'  and fname_l2='"
				+ cus.getName().toString() + "' " ;
				//"and fphone='" + cus.getPhone().toString() + "'";
		FDCSQLBuilder sqlbuild = new FDCSQLBuilder(ctx);
		sqlbuild.appendSql(sql);
		String phone = "";
		if(cus.getPhone() != null){
   		 phone = cus.getPhone().trim();
      	}
		String tel = "";
    	if(cus.getTel() != null){
    		 tel = cus.getTel().trim();
    	}
    	String officeTel = "";
    	if(cus.getOfficeTel() != null){
    		officeTel = cus.getOfficeTel().trim();
    	}
		String fax = "";
		if(cus.getFax() != null){
			fax = cus.getFax().trim();
		}
		String tempSql = getSql(phone,tel,officeTel,fax);
		sqlbuild.appendSql(" and ( "+tempSql+")");
		IRowSet rs = sqlbuild.executeQuery();
		try {
			if(rs.next()){
				throw new EASBizException(new NumericExceptionSubItem("100",cus.getBelongUnit()+"下客户名称、联系方式已经存在！不能重复" ));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getPhoneSql(String phone){
		String sql = "";
		if (phone != null && !phone.equals("")) {
			 sql = " fphone='" + phone.toString()+"' or ftel = '"+phone.toString()+"' or fofficeTel='"+phone.toString()+"' or ffax = '"+phone.toString()+"' ";
		}
		return sql;
	}
	
	private String getSql(String phone,String tel,String officeTel ,String fax){
		String sql1 = getPhoneSql(phone);
		String sql2 = getPhoneSql(tel);
		String sql3 = getPhoneSql(officeTel);
		String sql4 = getPhoneSql(fax);
		String tempSql = "";
		
		if(sql1 != null && !sql1.equals("")){
			if(sql2 != null && !sql2.equals("")){
				if(sql3 != null && !sql3.equals("")){
					if(sql4 != null && !sql4.equals("")){
						tempSql = sql1 +" or "+ sql2 +" or "+ sql3 +" or "+ sql4 ;
					}else{
						tempSql = sql1 +" or "+ sql2 +" or "+ sql3;
					}
				}else{
					tempSql = sql1 +" or "+ sql2 ;
				}
			}else{
				tempSql = sql1 ;
			}
		}else{
			if(sql2 != null && !sql2.equals("")){
				if(sql3 != null && !sql3.equals("")){
					if(sql4 != null && !sql4.equals("")){
						tempSql = sql2 +" or "+ sql3 +" or "+ sql4 ;
					}else{
						tempSql =  sql2 +" or "+ sql3;
					}
				}else{
					tempSql = sql2 ;
				}
			}else{
				if(sql3 != null && !sql3.equals("")){
					if(sql4 != null && !sql4.equals("")){
						tempSql =  sql3 +" or "+ sql4 ;
					}else{
						tempSql =  sql3;
					}
				}else{
					if(sql4 != null && !sql4.equals("")){
						tempSql = sql4 ;
					}
				}
			}
		}
		return tempSql;
	}
	
	/**
	 * 验证名称和证件号码
	 * @param ctx
	 * @param cus
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void verifyNameAndCertificateNum(Context ctx,FDCOrgCustomerInfo cus) throws BOSException, EASBizException{
		/*FilterInfo filter = new FilterInfo();
		Object obj = cus.getId();
		if(obj == null){
			return;
		}
		String id = obj.toString();
		filter.getFilterItems().add(
				new FilterItemInfo("id", id, CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("customerType", cus.getCustomerType()
						.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("belongUnit.id",cus.getBelongUnit().getId().toString()));
		
		String name = cus.getName();
		String certificateNumber = cus.getCertificateNumber();
		filter.getFilterItems().add(
				new FilterItemInfo("name", name));
		if (certificateNumber != null && !"".equals(certificateNumber)) {
			filter.getFilterItems().add(
					new FilterItemInfo("certificateNumber",certificateNumber));
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection selectcoll = new SelectorItemCollection();
			selectcoll.add(new SelectorItemInfo("id"));
			view.setSelector(selectcoll);
			view.setFilter(filter);
			FDCOrgCustomerCollection coll = FDCOrgCustomerFactory.getLocalInstance(ctx).getFDCOrgCustomerCollection(view);
			if(coll != null && coll.size()>0){
				throw new EASBizException(new NumericExceptionSubItem("100",cus.getBelongUnit()+"下客户名称、证件号码已经存在！不能重复" ));
			}
			*/
		
		String certificateNumber = cus.getCertificateNumber();
		if (certificateNumber != null && !"".equals(certificateNumber)) {
			String sql = "select * from T_BDC_FDCOrgCustomer where fid='"
				+ cus.getId().toString()
				+ "' and fisenabled=1 and fcustomertype='"
				+ cus.getCustomerType() + "' and fbelongunitid='"
				+ cus.getBelongUnit().getId().toString() + "'  and fname_l2='"
				+ cus.getName().toString() + "' and fcertificateNumber='" + certificateNumber + "'";
		
		FDCSQLBuilder sqlbuild = new FDCSQLBuilder(ctx);
		sqlbuild.appendSql(sql);
		IRowSet rs = sqlbuild.executeQuery();
		try {
			if(rs.next()){
				throw new EASBizException(new NumericExceptionSubItem("100",cus.getBelongUnit()+"下客户名称、证件号码已经存在！不能重复" ));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		
	} 
	
	/**
	 * 验证电话和证件号码
	 * @param ctx
	 * @param cus
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void verifyPhoneAndCertificateNum(Context ctx,FDCOrgCustomerInfo cus) throws BOSException, EASBizException{
		/*FilterInfo filter = new FilterInfo();
		Object obj = cus.getId();
		if(obj == null){
			return;
		}
		String id = obj.toString();
		filter.getFilterItems().add(
				new FilterItemInfo("id", id, CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("customerType", cus.getCustomerType()
						.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("belongUnit.id",cus.getBelongUnit().getId().toString()));
		
		String phone = cus.getPhone().trim();
		String tel = "";
    	if(cus.getTel() != null){
    		 tel = cus.getTel().trim();
    	}
    	String officeTel = "";
    	if(cus.getOfficeTel() != null){
    		officeTel = cus.getOfficeTel().trim();
    	}
		String fax = "";
		if(cus.getFax() != null){
			fax = cus.getFax().trim();
		}
		String certificateNumber = "";
		if(cus.getCertificateNumber() != null){
			certificateNumber = cus.getCertificateNumber();
		}
		if (phone != null && !phone.equals("")) {
			filter.getFilterItems().add(
					new FilterItemInfo("phone", phone));
		}else if(tel != null && !tel.equals("")){
			filter.getFilterItems().add(
					new FilterItemInfo("tel", tel));
		}else if(officeTel != null && !officeTel.equals("")){
			filter.getFilterItems().add(
					new FilterItemInfo("officeTel", officeTel));
		}else if(fax != null && !fax.equals("")){
			filter.getFilterItems().add(
					new FilterItemInfo("fax", fax));
		}
		if (certificateNumber != null && !"".equals(certificateNumber)) {
			filter.getFilterItems().add(
					new FilterItemInfo("certificateNumber",certificateNumber));
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection selectcoll = new SelectorItemCollection();
			selectcoll.add(new SelectorItemInfo("id"));
			view.setSelector(selectcoll);
			view.setFilter(filter);
			FDCOrgCustomerCollection coll = FDCOrgCustomerFactory.getLocalInstance(ctx).getFDCOrgCustomerCollection(view);
			if(coll != null && coll.size()>0){
				throw new EASBizException(new NumericExceptionSubItem("100",cus.getBelongUnit()+"下联系方式、证件号码已经存在！不能重复" ));
			}
		*/
		String certificateNumber = "";
		if(cus.getCertificateNumber() != null){
			certificateNumber = cus.getCertificateNumber();
		}
		if (certificateNumber != null && !"".equals(certificateNumber)) {
			String sql = "select * from T_BDC_FDCOrgCustomer where fid='"
				+ cus.getId().toString()
				+ "' and fisenabled=1 and fcustomertype='"
				+ cus.getCustomerType() + "' and fbelongunitid='"
				+ cus.getBelongUnit().getId().toString() + "' and fcertificateNumber='" + certificateNumber + "' ";
		
		FDCSQLBuilder sqlbuild = new FDCSQLBuilder(ctx);
		sqlbuild.appendSql(sql);
		String phone = "";
		if(cus.getPhone() != null){
   		 phone = cus.getPhone().trim();
      	}
		String tel = "";
    	if(cus.getTel() != null){
    		 tel = cus.getTel().trim();
    	}
    	String officeTel = "";
    	if(cus.getOfficeTel() != null){
    		officeTel = cus.getOfficeTel().trim();
    	}
		String fax = "";
		if(cus.getFax() != null){
			fax = cus.getFax().trim();
		}
		
		String tempSql = getSql(phone,tel,officeTel,fax);
		sqlbuild.appendSql(" and ( "+tempSql+")");
		IRowSet rs = sqlbuild.executeQuery();
		try {
			if(rs.next()){
				throw new EASBizException(new NumericExceptionSubItem("100",cus.getBelongUnit()+"下联系方式、证件号码已经存在！不能重复" ));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	} 
	
	/**
	 * 验证
	 * @param ctx
	 * @param cus
	 * @throws BOSException
	 * @throws EASBizException
	 */
	 private void beforeOperate(Context ctx,FDCOrgCustomerInfo cus)throws BOSException, EASBizException{
		 this.verifyNameAndPhone(ctx, cus);
		 this.verifyNameAndCertificateNum(ctx, cus);
		 this.verifyPhoneAndCertificateNum(ctx,cus);
	  }
		
}