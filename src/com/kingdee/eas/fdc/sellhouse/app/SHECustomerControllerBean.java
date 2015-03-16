package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerGroupDetailInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.AssociationTypeEnum;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.CreateWayEnum;
import com.kingdee.eas.fdc.basecrm.CustomerChangeLogCollection;
import com.kingdee.eas.fdc.basecrm.CustomerChangeLogFactory;
import com.kingdee.eas.fdc.basecrm.CustomerChangeLogInfo;
import com.kingdee.eas.fdc.basecrm.CustomerTypeEnum;
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
import com.kingdee.eas.fdc.basecrm.client.CusClientHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo;
import com.kingdee.eas.fdc.sellhouse.CluesManageFactory;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.CluesStatusEnum;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChangeRoomFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChangeRoomInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerChangeReasonEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerChangeReasonFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerLinkManFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerLinkManInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SharePropertyCollection;
import com.kingdee.eas.fdc.sellhouse.SharePropertyInfo;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.ShareTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.IPropertyContainer;
import com.kingdee.util.NumericExceptionSubItem;

public class SHECustomerControllerBean extends
		AbstractSHECustomerControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.SHECustomerControllerBean");

	protected IObjectPK _addnew(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		SHECustomerInfo cus = (SHECustomerInfo) model;
		FDCOrgCustomerInfo orgCus = new FDCOrgCustomerInfo();
		
		String[] exceptFields = new String[]{"number","linkMan"};
		CRMHelper.changeObjectValue((IPropertyContainer) cus.clone(), orgCus,false,exceptFields);
		orgCus.getLinkMan().clear();
		
		orgCus.setId(BOSUuid.create(orgCus.getBOSType()));
		//orgCus.setId(null);
		orgCus.setCreateWay(CreateWayEnum.CHILDGENERATE);
		// 所属组织
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		
		FullOrgUnitInfo tmp = new FullOrgUnitInfo();
//		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
//		.getLocalInstance(ctx);
		OrgUnitInfo orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
		tmp = getBelongUnit(ctx, cus);
		if (tmp != null) {
			orgCus.setBelongUnit(tmp);
			String retNumber = iCodingRuleManager.getNumber(orgCus, orgUnit
					.getId().toString());
			orgCus.setNumber(retNumber);
			boolean isExistOnUnit = false;//上级组织是否存在
			//当前所属的客户管理组织，上级是否有相同的资料，如果有，则关联起来
			List ids = this.getBelongUnitList(ctx,cus);
			List belongIdList = new ArrayList();
			if(ids != null && ids.size()>0){
				for(int j=0;j<ids.size();j++){
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter =new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
					filter.getFilterItems().add(new FilterItemInfo("belongUnit.id", ids.get(j).toString()));
					filter.getFilterItems().add(new FilterItemInfo("customerType",orgCus.getCustomerType().getValue(),CompareType.EQUALS));
					filter.getFilterItems().add(new FilterItemInfo("name",orgCus.getName(),CompareType.EQUALS));
					filter.getFilterItems().add(new FilterItemInfo("phone",orgCus.getPhone(),CompareType.EQUALS));
//					if(orgCus.getCertificateNumber() != null && !orgCus.getCertificateNumber().trim().equals("")){
//						filter.getFilterItems().add(new FilterItemInfo("certificateNumber",orgCus.getCertificateNumber(),CompareType.EQUALS));
//					}
					
					view.setFilter(filter);
					FDCOrgCustomerCollection coll = FDCOrgCustomerFactory.getLocalInstance(ctx).getFDCOrgCustomerCollection(view);
					if(coll != null && coll.size() > 0){
						if(coll.get(0)!= null){
							orgCus.setMainCustomer(coll.get(0).getMainCustomer());
							cus.setMainCustomer(coll.get(0).getMainCustomer());//如果上级组织有记录，将该记录与MainCustomer关系
							isExistOnUnit = true;
							break;//默认该组织有记录，上级也会有记录。
						}
						if(isExistOnUnit){//如果上级组织有相同的记录，则更新之。
							FDCOrgCustomerFactory.getLocalInstance(ctx).update(new ObjectUuidPK(orgCus.getId()), orgCus);
						}
					}else{
						belongIdList.add(ids.get(j).toString());//将上级没有记录的belongUnit存住，新增之。
					}
				}
			}
			if(!isExistOnUnit){//如果上级组织没有相同的记录，则新增之。
				FDCOrgCustomerFactory.getLocalInstance(ctx).submit(orgCus);
			}else{
				if(belongIdList != null && belongIdList.size()>0){
					//for(int i=0;i<belongIdList.size();i++){
						FullOrgUnitInfo belongUnit = FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo(new ObjectUuidPK(belongIdList.get(0).toString()));
						FDCOrgCustomerInfo newOrgCusInfo = new FDCOrgCustomerInfo();
						CRMHelper.changeObjectValue(orgCus, newOrgCusInfo, true, EXCEPT_FIELDS);
						newOrgCusInfo.setBelongUnit(belongUnit);
						newOrgCusInfo.setId(BOSUuid.create(newOrgCusInfo.getBOSType()));
						String newNumber = iCodingRuleManager.getNumber(orgCus, belongUnit.getId().toString());
						newOrgCusInfo.setNumber(newNumber);
						FDCOrgCustomerFactory.getLocalInstance(ctx).submit(newOrgCusInfo);
					//}
				}
			}
		}
		if(cus.getClues() != null){//如果是线索转过来的，并且不是状态为空，保存时将线索置为‘转客户’状态
			CluesManageInfo cluesInfo = cus.getClues();
			if(cluesInfo.getCluesStatus() == null || cluesInfo.getCluesStatus().equals("")){
				cluesInfo.setCluesStatus(CluesStatusEnum.CUSTOMER);
				CluesManageFactory.getLocalInstance(ctx).submit(cluesInfo);
			}
		}
		cus.setPropertyConsultant(cus.getFirstConsultant());//首次接待顾问的值给置业顾问
		return super._addnew(ctx, cus);
	}
	
    protected void _checkNumberDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		
	}
    protected void _checkNameDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	
    }
  	private List getBelongUnitList(Context ctx,SHECustomerInfo cus)throws BOSException, EASBizException {
  		FullOrgUnitCollection coll = getBelongUnitColl(ctx,cus);
  		List idList = new ArrayList();
  		if (coll != null && coll.size() > 0) {
			for (int j = 0; j < coll.size(); j++) {// 遍历，查出当前组织中最近的一个客户组织
				FullOrgUnitInfo parentUnit = coll.get(j);
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
				filter.getFilterItems().add(new FilterItemInfo("unit.id",parentUnit.getId()));
				view.setFilter(filter);
				view.setSelector(sel);
				FDCOrgStructureCollection orgColl = FDCOrgStructureFactory.getLocalInstance(ctx).getFDCOrgStructureCollection(view);
				for(int i = 0 ; i < orgColl.size() ; i++){// 如果是客户管理组织,添加到集合中
					idList.add(orgColl.get(i).getUnit().getId().toString());
				}
			}
		}
		return idList;
	}

	private FullOrgUnitCollection getBelongUnitColl(Context ctx, SHECustomerInfo cus)
			throws BOSException, EASBizException {
		String longNumber = cus.getCreateUnit().getLongNumber();
		String numbers[] = longNumber.split("!");// 对长编码拆分
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		// sic.add("id");
		FilterInfo filter = new FilterInfo();

		// 排序
		SorterItemCollection sorts = new SorterItemCollection();
		SorterItemInfo sort = new SorterItemInfo("longNumber");
		sort.setSortType(SortType.DESCEND);
		sorts.add(sort);
		view.setSorter(sorts);

		// 所有上级的编码
		for (int i = 0; i < numbers.length; i++) {
			FilterInfo parentFilter = new FilterInfo();
			parentFilter.getFilterItems().add(
					new FilterItemInfo("number", numbers[i]));
			parentFilter.mergeFilter(parentFilter, "OR");
			filter.mergeFilter(parentFilter, "OR");
		}
		view.setSelector(sic);
		view.setFilter(filter);

		// 得到所有上级
		FullOrgUnitCollection coll = FullOrgUnitFactory.getLocalInstance(ctx)
				.getFullOrgUnitCollection(view);
		return coll;
	}
	
	private FullOrgUnitInfo getBelongUnit(Context ctx,SHECustomerInfo cus)throws BOSException, EASBizException {
		FullOrgUnitCollection coll = getBelongUnitColl(ctx,cus);
		FullOrgUnitInfo unitInfo = new FullOrgUnitInfo();
		
		Set cusOrgIds = getCusMgeMap(ctx).keySet();
		
		if (coll != null && coll.size() > 0) {
			for (int j = 0; j < coll.size(); j++) {// 遍历，查出当前组织中最近的一个客户组织
				FullOrgUnitInfo parentUnit = coll.get(j);
				if (cusOrgIds.contains(parentUnit.getId().toString())) {// 如果是销售组织，则取该组织
					return parentUnit;
				}
			}
		}
		return unitInfo;
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
	
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		SHECustomerInfo cus = (SHECustomerInfo) model;
		FDCMainCustomerInfo mainCus = cus.getMainCustomer();
		if (mainCus == null) {
			mainCus = new FDCMainCustomerInfo();
			CRMHelper.changeObjectValue((IPropertyContainer) cus.clone(),mainCus, true);
			mainCus.setId(BOSUuid.create(mainCus.getBOSType()));
			cus.setMainCustomer(mainCus);
		} else {
			// TODO 补充信息到房地产主客户上
			mainCus = FDCMainCustomerFactory.getLocalInstance(ctx)
					.getFDCMainCustomerInfo(new ObjectUuidPK(mainCus.getId()));
			BOSUuid uuid = mainCus.getId();
			// TODO 创建信息不应该更新
			CRMHelper.changeObjectValue((IPropertyContainer) cus.clone(),
					mainCus, true);
			mainCus.setId(uuid);
		}
		FDCMainCustomerFactory.getLocalInstance(ctx).submit(mainCus);
		
		//同步上级组织的资料
		this.updateUnitData(ctx,cus,mainCus);
		
		if (cus.getId() != null) {
			SHECustomerCollection shecc = SHECustomerFactory.getLocalInstance(ctx).getSHECustomerCollection("where id = '"+cus.getId()+"'");
			if(shecc.size() > 0){
				SHECustomerInfo oldCus = getSHECustomerInfo(ctx, new ObjectUuidPK(
						cus.getId()));
				CustomerChangeLogCollection changes = getCustomerChangeLogs(ctx,
						oldCus, cus);
				if (changes != null && changes.size() > 0) {
					for (int i = 0; i < changes.size(); i++) {
						CustomerChangeLogInfo change = (CustomerChangeLogInfo) changes
								.get(i);
						change.setSheCustomer(cus);
						CustomerChangeLogFactory.getLocalInstance(ctx).addnew(
								change);
					}
				}
			}
		}
		if(mainCus!=null){
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("sysCustomer.*");
			mainCus = FDCMainCustomerFactory.getLocalInstance(ctx).getFDCMainCustomerInfo(new ObjectUuidPK(mainCus.getId()),sel);
			if(mainCus.getSysCustomer()!=null){
				SelectorItemCollection updatesel=new SelectorItemCollection();
				CustomerInfo custom=CustomerFactory.getLocalInstance(ctx).getCustomerInfo(new ObjectUuidPK(mainCus.getSysCustomer().getId()));
				if(!custom.getNumber().equals(cus.getCertificateNumber())){
					FilterInfo filter=new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("number",cus.getCertificateNumber()));
					
					if(CustomerFactory.getLocalInstance(ctx).exists(filter)){
						throw new EASBizException(new NumericExceptionSubItem("100","已经存在编码为"+cus.getCertificateNumber()+"的客户主数据，禁止修改！"));
					}
					updatesel.add("number");
					custom.setNumber(cus.getCertificateNumber());
					CustomerFactory.getLocalInstance(ctx).updatePartial(custom, updatesel);
				}
				if(!custom.getName().equals(cus.getName())){
					updatesel.add("name");
					custom.setName(cus.getName());
					CustomerFactory.getLocalInstance(ctx).updatePartial(custom,updatesel);
				}
			}
		}
		return super._submit(ctx, model);
	}
	
	private void updateUnitData(Context ctx,SHECustomerInfo cus,FDCMainCustomerInfo mainCus) throws BOSException, EASBizException{
		if(mainCus == null){
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("mainCustomer.id", mainCus.getId(), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE, CompareType.EQUALS));
		view.setFilter(filter);
		
		FDCOrgCustomerCollection coll = FDCOrgCustomerFactory.getLocalInstance(ctx).getFDCOrgCustomerCollection(view);
		if(coll != null && coll.size() > 0){
			for(int i = 0;i<coll.size();i++){
				FDCOrgCustomerInfo orgInfo = coll.get(i);
				BOSUuid uuid = orgInfo.getId();
				CRMHelper.changeSheObjectValue((IPropertyContainer) cus.clone(),orgInfo, true);
				orgInfo.setId(uuid);
				//更新客户资料表里客户信息
				FDCOrgCustomerFactory.getLocalInstance(ctx).update(new ObjectUuidPK(uuid), orgInfo);
			}
		}
	}

	
	private CustomerChangeLogCollection getCustomerChangeLogs(Context ctx,
			SHECustomerInfo oldCus, SHECustomerInfo cus) throws EASBizException, BOSException {
		CustomerChangeLogCollection changes = new CustomerChangeLogCollection();
		Date now = new Date();
		Timestamp time = new Timestamp(now.getTime());
		UserInfo user = ContextUtil.getCurrentUserInfo(ctx);
		FullOrgUnitInfo org = ContextUtil.getCurrentOrgUnit(ctx)
				.castToFullOrgUnitInfo();
		this.getChangeLog(ctx, changes, oldCus.getName(), cus.getName(),"客户名称");
		this.getChangeLog(ctx, changes, oldCus.getPhone(), cus.getPhone(),"移动电话");
		this.getChangeLog(ctx, changes, oldCus.getOfficeTel(), cus.getOfficeTel(), "办公电话");
		this.getChangeLog(ctx, changes, oldCus.getTel(), cus.getTel(), "住宅电话");
		this.getChangeLog(ctx, changes, oldCus.getFax(), cus.getFax(), "传真");
		this.getChangeLog(ctx, changes, oldCus.getCertificateNumber(), cus
				.getCertificateNumber(), "证件号码");

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
		
		//首次接待顾问
		if ((oldCus.getPropertyConsultant() != null && oldCus.getPropertyConsultant().getId() != null) || (cus.getPropertyConsultant() != null && cus.getPropertyConsultant().getId() != null)) {
			String oldField = "";
			String newField = "";
			if (oldCus.getPropertyConsultant() == null) {
				oldField = "";
			} else {
				UserInfo oldInfo = UserFactory.getLocalInstance(ctx).getUserInfo(new ObjectUuidPK(oldCus.getPropertyConsultant().getId()));
				oldField = oldInfo.getName();
			}
			if (cus.getPropertyConsultant() == null) {
				newField = "";
			} else {
				UserInfo newInfo = UserFactory.getLocalInstance(ctx).getUserInfo(new ObjectUuidPK(cus.getPropertyConsultant().getId()));
				newField = newInfo.getName();
			}
			if ((oldField != null && !oldField.equals(""))|| (newField != null && !newField.equals(""))) {
				if (oldField == null) {
					oldField = "";
				}
				if (newField == null) {
					newField = "";
				}
				boolean isAddNew=true;
				if(oldCus.getPropertyConsultant()!=null&&cus.getPropertyConsultant()!=null
						&&oldCus.getPropertyConsultant().getId().toString().equals(cus.getPropertyConsultant().getId().toString())){
					isAddNew=false;
				}
				if (isAddNew) {
					CustomerChangeLogInfo info = new CustomerChangeLogInfo();
					info.setOldField("置业顾问：" + oldField);
					info.setNewField("置业顾问：" + newField);
					info.setCreateTime(time);
					info.setCreator(user);
					info.setOrgUnit(org);
					changes.add(info);
				}
			}
		}

		if (cus.getCustomerType().equals(CustomerTypeEnum.ENTERPRICECUSTOMER)) {
			this.getChangeLog(ctx, changes, oldCus.getMailAddress(), cus
					.getMailAddress(), "公司地址");
			this.getChangeLog(ctx, changes, oldCus.getBookedAddress(), cus
					.getBookedAddress(), "注册地址");
			// 企业客户，另外多关注三个字段的变更
			this.getChangeLog(ctx, changes, oldCus.getFirstLinkman(), cus
					.getFirstLinkman(), "首选联系人");
			this.getChangeLog(ctx, changes, oldCus.getCorporate(), cus
					.getCorporate(), "公司法人");
			this.getChangeLog(ctx, changes, oldCus.getCorporateTel(), cus
					.getCorporateTel(), "公司法人移动电话");
		} else {
			this.getChangeLog(ctx, changes, oldCus.getMailAddress(), cus
					.getMailAddress(), "通信地址");
			this.getChangeLog(ctx, changes, oldCus.getBookedAddress(), cus
					.getBookedAddress(), "证件地址");
		}
		return changes;
	}

	private void getChangeLog(Context ctx, CustomerChangeLogCollection changes,
			String oldField, String newField, String columnName) {
		Date now = new Date();
		Timestamp time = new Timestamp(now.getTime());
		UserInfo user = ContextUtil.getCurrentUserInfo(ctx);
		FullOrgUnitInfo org = ContextUtil.getCurrentOrgUnit(ctx)
				.castToFullOrgUnitInfo();
		if ((oldField != null && !oldField.equals(""))
				|| (newField != null && !newField.equals(""))) {
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
	 * 客户更新 从客户主线上取最新的数据,更新当前售楼客户
	 * @throws EASBizException 
	 */
	private static final String[] EXCEPT_FIELDS = new String[]{"number", "createUnit","creator","createTime","belongUnit"};
	protected void _updateData(Context ctx, List ids) throws BOSException, EASBizException {
		if(ids.isEmpty()){
			return;
		}
//		List orgIdList = getOrgId(ctx,ids);
//		FDCOrgCustomerFactory.getLocalInstance(ctx).updateCustomerInfo(orgIdList);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.list2Set(ids), CompareType.INCLUDE));
		view.setFilter(filter);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("*");
		view.setSelector(sels);
		SHECustomerCollection sheCuses = SHECustomerFactory.getLocalInstance(ctx).getSHECustomerCollection(view);
		
		if(sheCuses == null ||sheCuses.size() == 0){
			return;
		}
		
		for(int i = 0; i<sheCuses.size(); i++){
			SHECustomerInfo sheCustomer = sheCuses.get(i);
			FDCMainCustomerInfo mainCustomer = getFDCMainCusByPK(ctx,
					new ObjectUuidPK(ids.get(i).toString()));
			CRMHelper.changeObjectValue(mainCustomer, sheCustomer, true, EXCEPT_FIELDS);
			
			if (sheCustomer.getId() != null) {
				this.updateUnitData(ctx,sheCustomer,mainCustomer);//更新上级组织
				SHECustomerInfo oldCus = getSHECustomerInfo(ctx, new ObjectUuidPK(
						sheCustomer.getId()));
				CustomerChangeLogCollection changes = getCustomerChangeLogs(ctx,
						oldCus, sheCustomer);
				if (changes != null && changes.size() > 0) {
					for (int j = 0; j < changes.size(); j++) {
						CustomerChangeLogInfo change = (CustomerChangeLogInfo) changes
								.get(j);
						change.setSheCustomer(sheCustomer);
						CustomerChangeLogFactory.getLocalInstance(ctx).addnew(
								change);
					}
				}
			}
		}
		_updateBatchData(ctx, sheCuses);
		
	}

	/**
	 * 由pk查询客户主线
	 * 
	 * @param ctx
	 * @param pk
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private FDCMainCustomerInfo getFDCMainCusByPK(Context ctx, IObjectPK pk)
			throws BOSException, EASBizException {
		SelectorItemCollection collection = new SelectorItemCollection();
		collection.add("id");
		collection.add("name");
		collection.add("*");
		collection.add("mainCustomer.id");
		collection.add("mainCustomer.*");
		SHECustomerInfo sheCustomerInfo = (SHECustomerInfo) SHECustomerFactory.getLocalInstance(ctx).getSHECustomerInfo(pk, collection);
		return sheCustomerInfo.getMainCustomer();
	}
	
	/**
	 * 客户更名
	 */
	protected void _changeName(Context ctx, IObjectValue model, Map map)
			throws BOSException, EASBizException {
		SHECustomerInfo info = (SHECustomerInfo) model;
		String oldName = info.getName();
		String newName = map.get("newName").toString();
		SellProjectInfo sellProject = (SellProjectInfo)map.get("sellProject");
		if (info.getName().equals(newName)) {
			throw new EASBizException(new NumericExceptionSubItem("100",
					"新客户名称不能与原客户名称相同!"));
		}else{
			info.setName(newName);
			_submit(ctx,info);
//			beforeOperate(ctx,info,sellProject);
//			
//			CustomerChangeLogInfo change = new CustomerChangeLogInfo();
//			Date now = new Date();
//			Timestamp time = new Timestamp(now.getTime());
//			UserInfo user = ContextUtil.getCurrentUserInfo(ctx);
//			FullOrgUnitInfo org = ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo();
//			change.setCreateTime(time);
//			change.setCreator(user);
//			change.setOrgUnit(org);
//			change.setOldField("客户名称:"+oldName);
//			change.setNewField("客户名称:"+newName);
//			change.setSheCustomer(info);
//			CustomerChangeLogFactory.getLocalInstance(ctx).addnew(change);
//			
//			info.setLastUpdateTime(time);
//			info.setLastUpdateUnit(org);
//			info.setLastUpdateUser(user);
//			SelectorItemCollection updateName = new SelectorItemCollection();
//			updateName.add("name");
//			updateName.add("lastUpdateTime");
//			updateName.add("lastUpdateUser.id");
//			updateName.add("lastUpdateUnit.id");
//			SHECustomerFactory.getLocalInstance(ctx).updatePartial(info, updateName);
		}
	}
	
	/**
	 * 客户转交
	 */
	protected void _deliverCustomer(Context ctx, IObjectValue model, Map map)
			throws BOSException, EASBizException {
		SHECustomerInfo info = (SHECustomerInfo) model;
		UserInfo userInfo = (UserInfo) map.get("propertyConsultant");
		UserInfo deliverCustomer = info.getPropertyConsultant();
		//SharePropertyCollection sharePropertyColl = null;
		if (!info.isIsPublic()&&deliverCustomer != null && userInfo.getId().toString().equals(
				deliverCustomer.getId().toString())) {
			throw new EASBizException(new NumericExceptionSubItem("100",
					"转交的置业顾问不能和原置业顾问相同!"));
		} else {
			if ((deliverCustomer != null && deliverCustomer.getId() != null) || (userInfo != null && userInfo.getId() != null)) {
				Date now = new Date();
				Timestamp time = new Timestamp(now.getTime());
				UserInfo user = ContextUtil.getCurrentUserInfo(ctx);
				FullOrgUnitInfo org = ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo();
				
				String oldField = "";
				String newField = "";
				if (deliverCustomer == null) {
					oldField = "";
				} else {
					UserInfo oldInfo = UserFactory.getLocalInstance(ctx).getUserInfo(new ObjectUuidPK(deliverCustomer.getId()));
					oldField = oldInfo.getName();
				}
				if (userInfo == null) {
					newField = "";
				} else {
					UserInfo newInfo = UserFactory.getLocalInstance(ctx).getUserInfo(new ObjectUuidPK(userInfo.getId()));
					newField = newInfo.getName();
				}
				if ((oldField != null && !oldField.equals(""))|| (newField != null && !newField.equals(""))) {
					if (oldField == null) {
						oldField = "";
					}
					if (newField == null) {
						newField = "";
					}
					boolean isAddNew=true;
					if(deliverCustomer!=null&&userInfo!=null
							&&deliverCustomer.getId().toString().equals(userInfo.getId().toString())){
						isAddNew=false;
					}
					if(isAddNew){
						CustomerChangeLogInfo change = new CustomerChangeLogInfo();
						change.setOldField("置业顾问：" + oldField);
						change.setNewField("置业顾问：" + newField);
						change.setCreateTime(time);
						change.setCreator(user);
						change.setOrgUnit(org);
						change.setSheCustomer(info);
						CustomerChangeLogFactory.getLocalInstance(ctx).addnew(change);
					}
				}
			}
			
			info.setPropertyConsultant(userInfo);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("propertyConsultant.id"));
			SHECustomerFactory.getLocalInstance(ctx).updatePartial(info, selector);
//			SHECustomerFactory.getLocalInstance(ctx).submit(info);
//			sharePropertyColl = info.getShareProperty();
//			for (int j = 0; j < sharePropertyColl.size(); j++) {
//				SharePropertyInfo sharePropertyInfo = sharePropertyColl.get(j);
//				if (userInfo.getId().toString().equals(
//						sharePropertyInfo.getUser().getId().toString())) {
//					ShareSellerFactory.getLocalInstance(ctx).delete(
//							new ObjectUuidPK(sharePropertyInfo.getId()));
//				}
//			}
		}
	}

	/**
	 * 客户共享
	 */
	protected void _shareCustomer(Context ctx, IObjectCollection objectColl,
			Map map) throws BOSException, EASBizException {
		SHECustomerCollection customerColl = (SHECustomerCollection) objectColl;
		Map shareMap = (HashMap) map;

		if (customerColl != null && customerColl.size() > 0) {
			for (int i = 0; i < customerColl.size(); i++) {
				SHECustomerInfo info = customerColl.get(i);
				SharePropertyCollection sharePropertyColl = info
						.getShareProperty();// 共享置业顾问
				ShareSellProjectCollection shareProjectColl = info
						.getShareProject();// 共享项目
				List oldShareList = new ArrayList();
				if (shareMap.get("shareType").equals(ShareTypeEnum.USERSHARE)) {
					for (int j = 0; j < sharePropertyColl.size(); j++) {
						SharePropertyInfo sharePropertyInfo = sharePropertyColl
								.get(j);
						oldShareList.add(sharePropertyInfo);
					}
					sharePropertyColl.clear();
					List tblShareList = new ArrayList();
					Object[] propertyConsultant = (Object[]) shareMap
							.get("propertyConsultant");
					if (propertyConsultant != null
							&& propertyConsultant.length > 0) {
						for (int j = 0; j < propertyConsultant.length; j++) {
							UserInfo userInfo = (UserInfo) propertyConsultant[j];

							SharePropertyInfo sharePropertyInfo = new SharePropertyInfo();
							sharePropertyInfo
									.setOperationPerson((UserInfo) (SysContext
											.getSysContext()
											.getCurrentUserInfo()));
							sharePropertyInfo.setShareDate(new Date());
							sharePropertyInfo.setUser(userInfo);

							if (info.getPropertyConsultant() != null && userInfo.getId().toString().equals(
									info.getPropertyConsultant().getId().toString())) {
								throw new EASBizException(
										new NumericExceptionSubItem("100",
												"共享的置业顾问不能和原置业顾问相同!"));
							} else {
								tblShareList.add(sharePropertyInfo);
								Set set = differentSet(tblShareList,
										oldShareList, false);
								Iterator iterator = set.iterator();
								while (iterator.hasNext()) {
									SharePropertyInfo propertyInfo = (SharePropertyInfo) iterator
											.next();
									info.getShareProperty().add(propertyInfo);
								}
								SHECustomerFactory.getLocalInstance(ctx)
										.submit(info);
							}
						}
					}
				} else {
					for (int j = 0; j < shareProjectColl.size(); j++) {
						ShareSellProjectInfo shareProjectInfo = shareProjectColl
								.get(j);
						oldShareList.add(shareProjectInfo);
					}
					shareProjectColl.clear();
					List tblShareList = new ArrayList();
					Object[] project = (Object[]) shareMap.get("project");

					if (project != null && project.length > 0) {
						for (int j = 0; j < project.length; j++) {
							SellProjectInfo sellProjectInfo = (SellProjectInfo) project[j];

							ShareSellProjectInfo shareProjectInfo = new ShareSellProjectInfo();
							shareProjectInfo.setShareDate(new Date());
							shareProjectInfo.setSellProject(sellProjectInfo);

							if (sellProjectInfo.getId().toString().equals(
									info.getSellProject().getId().toString())) {
								throw new EASBizException(
										new NumericExceptionSubItem("100",
												"共享的项目不能和原项目相同!"));
							} else {
								tblShareList.add(shareProjectInfo);
								Set set = differentSet(tblShareList,
										oldShareList, true);
								Iterator iterator = set.iterator();
								while (iterator.hasNext()) {
									ShareSellProjectInfo projectInfo = (ShareSellProjectInfo) iterator
											.next();
									info.getShareProject().add(projectInfo);
								}
								SHECustomerFactory.getLocalInstance(ctx)
										.submit(info);
							}
						}
					}
				}
			}
		}
	}

	private Set differentSet(List tblShareList, List list, boolean isProject) {
		Set newSet = new HashSet();
		if (!isProject) {// 共享置业顾问
			if (list.size() == 0) {
				for (int i = 0; i < tblShareList.size(); i++) {
					SharePropertyInfo sharePropertyInfo = (SharePropertyInfo) tblShareList
							.get(i);
					newSet.add(sharePropertyInfo);
				}
			} else {
				for (int i = 0; i < tblShareList.size(); i++) {
					SharePropertyInfo sharePropertyInfo = (SharePropertyInfo) tblShareList
							.get(i);
					UserInfo userInfo = sharePropertyInfo.getUser();
					String userID = userInfo.getId().toString();

					if (list.size() > 0) {
						for (int j = 0; j < list.size(); j++) {
							SharePropertyInfo sharePropertyInfo2 = (SharePropertyInfo) list
									.get(j);
							UserInfo userInfo2 = sharePropertyInfo2.getUser();
							String userID2 = userInfo2.getId().toString();
							// 如果有相同的去除相同值，把新值加到set中，过滤重复值
							if (userID.equals(userID2)) {
								UserInfo operationPerson = sharePropertyInfo
										.getOperationPerson();
								UserInfo operationPerson2 = sharePropertyInfo2
										.getOperationPerson();
								// 同一个共享的销售顾问的共享操作人不能是一个，如果是一个后面得记录就把前面的那条覆盖
								// 也就是说如果有几个人同时把某客户都共享给同一个人的时候这样的可以的，
								// 而且这个人对该客户拥有的权限
								// 取最大权限，下面的共享团队和组织是一样的
								if(operationPerson != null && operationPerson2 != null){
									if (operationPerson.getId().toString().equals(
											operationPerson2.getId().toString())) {
										newSet.add(sharePropertyInfo);
										list.remove(sharePropertyInfo2);
										break;
									} 
								}
								else {
									newSet.add(sharePropertyInfo);
								}
							} else {
								newSet.add(sharePropertyInfo);
							}
						}
					} else {
						newSet.add(sharePropertyInfo);
					}
				}
				if (list.size() > 0) {
					for (int m = 0; m < list.size(); m++) {
						SharePropertyInfo sharePropertyInfo = (SharePropertyInfo) list
								.get(m);
						newSet.add(sharePropertyInfo);
					}
				}
			}
		} else {// 共享项目
			if (list.size() == 0) {
				for (int i = 0; i < tblShareList.size(); i++) {
					ShareSellProjectInfo shareProjectInfo = (ShareSellProjectInfo) tblShareList
							.get(i);
					newSet.add(shareProjectInfo);
				}
			} else {
				for (int i = 0; i < tblShareList.size(); i++) {
					ShareSellProjectInfo shareProjectInfo = (ShareSellProjectInfo) tblShareList
							.get(i);
					SellProjectInfo projectInfo = shareProjectInfo
							.getSellProject();
					String projectID = projectInfo.getId().toString();

					if (list.size() > 0) {
						for (int j = 0; j < list.size(); j++) {
							ShareSellProjectInfo shareProjectInfo2 = (ShareSellProjectInfo) list
									.get(j);
							SellProjectInfo projectInfo2 = shareProjectInfo2
									.getSellProject();
							String projectID2 = projectInfo2.getId().toString();
							if (projectID.equals(projectID2)) {
								UserInfo operationPerson = shareProjectInfo
										.getOperationPerson();
								UserInfo operationPerson2 = shareProjectInfo2
										.getOperationPerson();

								if (operationPerson.getId().toString().equals(
										operationPerson2.getId().toString())) {
									newSet.add(shareProjectInfo);
									list.remove(shareProjectInfo2);
									break;
								} else {
									newSet.add(shareProjectInfo);
								}
							} else {
								newSet.add(shareProjectInfo);
							}
						}
					} else {
						newSet.add(shareProjectInfo);
					}
				}
				if (list.size() > 0) {
					for (int m = 0; m < list.size(); m++) {
						ShareSellProjectInfo shareProjectInfo = (ShareSellProjectInfo) list
								.get(m);
						newSet.add(shareProjectInfo);
					}
				}
			}
		}
		return newSet;
	}

	/**
	 * 客户合并
	 */
	protected void _mergeCustomer(Context ctx, List srcIds, String toId)
			throws BOSException, EASBizException {
		srcIds.remove(toId);
		String sql2 = "update T_SHE_SHECUSTOMER set fmaincustomerid=? where Fmaincustomerid=?";
		IObjectPK[] srcMainCusPks = new ObjectUuidPK[srcIds.size()];
		Object[] params = new Object[2];
		params[0] = toId;
		for (int i = 0; i < srcIds.size(); i++) {
			String id = (String) srcIds.get(i);
			params[1] = id;
			DbUtil.execute(ctx, sql2, params);
			srcMainCusPks[i] = new ObjectUuidPK(id);
		}
		SHECustomerFactory.getLocalInstance(ctx).delete(srcMainCusPks);
	}

	/**
	 * 客户导入
	 */
	protected void _importCustomer(Context ctx, IObjectCollection res,IObjectValue sellProject)
			throws BOSException, EASBizException {
		for (int i = 0; i < res.size(); i++) {
			SHECustomerInfo cus = (SHECustomerInfo) res.getObject(i);
			SellProjectInfo sellProjectInfo = (SellProjectInfo)sellProject;
//			beforeOperate(ctx,cus,sellProjectInfo);
			cus.setSellProject(sellProjectInfo);
			cus.setPropertyConsultant(ContextUtil.getCurrentUserInfo(ctx));
			cus.setIsChooseRoom(false);
			cus.setIsInsider(false);
			cus.setLastUpdateUnit(ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo());
			this._submit(ctx, res.getObject(i));
//			this._addnew(ctx, res.getObject(i));
		}
	}
	
	/**
	 * 操作前验证客户台账资料是否已存在 
	 * @param ctx
	 * @param cus
	 * @param sellProjectInfo
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void beforeOperate(Context ctx,SHECustomerInfo cus,SellProjectInfo sellProjectInfo)throws BOSException, EASBizException{
		String cusName = cus.getName();
		String cusCertificateNum = cus.getCertificateNumber();
		String cusPhone = cus.getPhone();
		Map params = this.getCRMConstants(ctx);
		String rule = (String) params
				.get(CRMConstants.FDCSHE_PARAM_CUSREPEATRULE);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("customerType", cus.getCustomerType()
						.getValue()));
		int ci = 0;
		StringBuffer maskStr = new StringBuffer();
		maskStr.append("#");
		maskStr.append(ci++);
		maskStr.append(" and #");
		maskStr.append(ci++);
		if (CusClientHelper.CUSREPEATRULE_VALUE_NAME.equals(rule)) {
			addNameFilter(cusName, filter);
			maskStr.append(" and #");
			maskStr.append(ci++);
		} else if (CusClientHelper.CUSREPEATRULE_VALUE_TEL.equals(rule)) {
			addPhoneFilter(cusPhone, filter, true);
			ci = addPhoneMastStr(maskStr, ci);
		} else if (CusClientHelper.CUSREPEATRULE_VALUE_CER.equals(rule)) {
			addCerFilter(cusCertificateNum, filter);
			maskStr.append(" and #");
			maskStr.append(ci++);
		} else if (CusClientHelper.CUSREPEATRULE_VALUE_NAME_TEL
				.equals(rule)) {
			addNameFilter(cusName, filter);
			addPhoneFilter(cusPhone, filter, true);
			maskStr.append(" and #");
			maskStr.append(ci++);
			ci = addPhoneMastStr(maskStr, ci);
		} else if (CusClientHelper.CUSREPEATRULE_VALUE_NAME_CER
				.equals(rule)) {
			addNameFilter(cusName, filter);
			addCerFilter(cusCertificateNum, filter);
			maskStr.append(" and #");
			maskStr.append(ci++);
			maskStr.append(" and #");
			maskStr.append(ci++);
		} else if (CusClientHelper.CUSREPEATRULE_VALUE_TEL_CER.equals(rule)) {
			addCerFilter(cusCertificateNum, filter);
			addPhoneFilter(cusPhone, filter, true);
			maskStr.append(" and #");
			maskStr.append(ci++);
			ci = addPhoneMastStr(maskStr, ci);
		} else if (CusClientHelper.CUSREPEATRULE_VALUE_NAME_TEL_CER
				.equals(rule)) {
			addNameFilter(cusName, filter);
			addCerFilter(cusCertificateNum, filter);
			addPhoneFilter(cusPhone, filter, true);
			maskStr.append(" and #");
			maskStr.append(ci++);
			maskStr.append(" and #");
			maskStr.append(ci++);
			ci = addPhoneMastStr(maskStr, ci);
		}

		view.setFilter(filter);
		filter.setMaskString(maskStr.toString());
		FilterItemInfo scope = new FilterItemInfo();
		if(sellProjectInfo != null){
			 scope = new FilterItemInfo("sellProject.id",sellProjectInfo.getId().toString());
		}
		ICoreBase iface = SHECustomerFactory.getLocalInstance(ctx);
		if (iface != null && scope != null) {
			FilterInfo tmp = (FilterInfo) filter.clone();
			tmp.getFilterItems().add(scope);
			maskStr.append(" and #");
			maskStr.append(ci++);
			tmp.setMaskString(maskStr.toString());
			if (iface.exists(tmp)) {
				throw new EASBizException(new NumericExceptionSubItem(
						"100", "客户台账资料不能重复！"));
			}
		}
	}
	
	private  HashMap getCRMConstants(Context ctx){
		HashMap param = new HashMap();
		HashMap value=null;
		IObjectPK pk=new ObjectUuidPK(ContextUtil.getCurrentUserInfo(ctx).getId());
		param.put(CRMConstants.FDCSHE_PARAM_ISSHARECUS, pk);
		param.put(CRMConstants.FDCSHE_PARAM_REPEATCUSSHOWRULE_DIR, pk);
		param.put(CRMConstants.FDCSHE_PARAM_REPEATCUSSHOWRULE_START, pk);
		param.put(CRMConstants.FDCSHE_PARAM_REPEATCUSSHOWRULE_LEN, pk);
		param.put(CRMConstants.FDCSHE_PARAM_CUSREPEATRULE, pk);
		try {
			value = ParamControlFactory.getLocalInstance(ctx).getParamHashMap(param);
			if (value == null) {
				value = new HashMap();
				value.put(CRMConstants.FDCSHE_PARAM_ISSHARECUS, Boolean.TRUE);
				value.put(CRMConstants.FDCSHE_PARAM_REPEATCUSSHOWRULE_DIR, "0");
				value.put(CRMConstants.FDCSHE_PARAM_REPEATCUSSHOWRULE_START, new Integer(3));
				value.put(CRMConstants.FDCSHE_PARAM_REPEATCUSSHOWRULE_LEN, new Integer(7));
				value.put(CRMConstants.FDCSHE_PARAM_CUSREPEATRULE, "1");
			}
		}catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		return value;
	}

	/**
	 * @param cusName
	 * @param filter
	 */
	private static void addNameFilter(String cusName, FilterInfo filter) {
		filter.getFilterItems().add(new FilterItemInfo("name", cusName));
	}

	/**
	 * @param cusPhone
	 * @param filter
	 */
	private static void addPhoneFilter(String cusPhone, FilterInfo filter,
			boolean like) {
		if (like) {
			filter.getFilterItems().add(
					new FilterItemInfo("phone", "%" + cusPhone + "%",
							CompareType.LIKE));
			filter.getFilterItems().add(
					new FilterItemInfo("tel", "%" + cusPhone + "%",
							CompareType.LIKE));
			filter.getFilterItems().add(
					new FilterItemInfo("officeTel", "%" + cusPhone + "%",
							CompareType.LIKE));
			filter.getFilterItems().add(
					new FilterItemInfo("otherTel", "%" + cusPhone + "%",
							CompareType.LIKE));
			filter.getFilterItems().add(
					new FilterItemInfo("fax", "%" + cusPhone + "%",
							CompareType.LIKE));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("phone", cusPhone));
			filter.getFilterItems().add(new FilterItemInfo("tel", cusPhone));
			filter.getFilterItems().add(
					new FilterItemInfo("officeTel", cusPhone));
			filter.getFilterItems().add(
					new FilterItemInfo("otherTel", cusPhone));
			filter.getFilterItems().add(new FilterItemInfo("fax", cusPhone));
		}
	}

	private static int addPhoneMastStr(StringBuffer maskStr, int ci) {
		maskStr.append(" and (#");
		maskStr.append(ci++);
		maskStr.append(" or #");
		maskStr.append(ci++);
		maskStr.append(" or #");
		maskStr.append(ci++);
		maskStr.append(" or #");
		maskStr.append(ci++);
		maskStr.append(" or #");
		maskStr.append(ci++);
		maskStr.append(")");

		return ci;
	}

	/**
	 * @param cusCertificateNum
	 * @param filter
	 */
	private static void addCerFilter(String cusCertificateNum, FilterInfo filter) {
		if (cusCertificateNum == null || cusCertificateNum.length() == 0) {
			filter.getFilterItems().add(
					new FilterItemInfo("certificateNumber", "nullnull"));
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("certificateNumber", "%"
							+ cusCertificateNum + "%", CompareType.LIKE));
		}
	}

	private SHECustomerInfo getNumberForInfo(Context ctx,SHECustomerInfo info){
		try {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
			OrgUnitInfo orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
			boolean existCodingRule = iCodingRuleManager.isExist(
					new FDCOrgCustomerInfo(), orgUnit.getId().toString());
			if (existCodingRule) {
				String retNumber = iCodingRuleManager.getNumber(info, orgUnit
						.getId().toString());
				info.setNumber(retNumber);
			}else {
				Timestamp time = new Timestamp(new Date().getTime());
				String milliSecond = String.valueOf(time).substring(20, 23);
				String timeStr = String.valueOf(time).replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "").substring(0,14)+milliSecond;
				String number = String.valueOf(timeStr)+"001";
				info.setNumber(number);
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
		 SHECustomerInfo info =  (SHECustomerInfo) model;
		 SHECustomerInfo newInfo = new SHECustomerInfo();
		 FDCMainCustomerInfo mainCus =  info.getMainCustomer();
		 ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		 OrgUnitInfo orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
		 String retNumber = iCodingRuleManager.getNumber(new FDCOrgCustomerInfo(), orgUnit
					.getId().toString());
		 mainCus.setNumber(retNumber);
		 FDCMainCustomerFactory.getLocalInstance(ctx).submit(mainCus);
		 newInfo.setMainCustomer(mainCus);
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
			newInfo.setSellProject(info.getSellProject());
			newInfo.setCreateTime(time);
			newInfo.setCreateUnit(org);
			newInfo.setCreator(user);
			newInfo.setPropertyConsultant(user);
			newInfo.setLastUpdateUnit(info.getLastUpdateUnit());
			newInfo.setLastUpdateUser(info.getLastUpdateUser());
			newInfo.setIsEnabled(true);
			newInfo.setCreateWay(CreateWayEnum.HAND);
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
//			filter.getFilterItems().add(new FilterItemInfo("customerType", CustomerTypeEnum.PERSONALCUSTOMER));
			
			
			this.beforeOperate(ctx, newInfo, newInfo.getSellProject());
			this._addnew(ctx, newInfo);
				
				SHECustomerLinkManInfo linkInfo = new SHECustomerLinkManInfo();
				linkInfo.setAssociationType(AssociationTypeEnum.SYSTEM);
				linkInfo.setIsAssociation(true);
				if(!isLegalPerson){
					linkInfo.setRelation("企业职员");
				}else{
					linkInfo.setRelation("企业法人");
				}
				linkInfo.setName(name);
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
				filter.getFilterItems().add(new FilterItemInfo("name", linkInfo.getName()));
				boolean isExist = SHECustomerLinkManFactory.getLocalInstance(ctx).exists(filter);
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
				linkInfo.setSheCustomer(info);
				linkInfo.setNumber(newInfo.getNumber());
				linkInfo.setCreateTime(time);
				linkInfo.setCreator(user);
				SHECustomerLinkManFactory.getLocalInstance(ctx).addnew(linkInfo);
			}

	protected IObjectPK _submitAll(Context ctx, HashMap hashMap, IObjectValue editData) throws BOSException, EASBizException {
		IObjectPK pk = this._submit(ctx, editData);
		SHECustomerInfo sheci = (SHECustomerInfo)editData;
		sheci.setId(BOSUuid.read(pk.toString()));
		CommerceChanceInfo cci = (CommerceChanceInfo)hashMap.get("CommerceChance");
		cci.setCustomer(sheci);
		CommerceChanceFactory.getLocalInstance(ctx).submit(cci);
		if(hashMap != null){
			HashMap hp = hashMap;
			FDCCustomerInfo fdcci = new FDCCustomerInfo();
			fdcci.putAll((SHECustomerInfo)editData);
			//保存问卷
			if(hp.get("question_num")!=null){
				int num = Integer.parseInt(hp.get("question_num").toString());
				for(int i=0;i<num;i++){
					String key = "question_"+i;
					QuestionPaperAnswerInfo info = (QuestionPaperAnswerInfo)hp.get(key);
					//info.setCustomer(fdcci);
					QuestionPaperAnswerFactory.getLocalInstance(ctx).submit(info);
				}
			}
			//保存跟进
			if(hp.get("track_num")!=null){
				int num = Integer.parseInt(hp.get("track_num").toString());
				for(int i=0;i<num;i++){
					String key = "track_"+i;
					CommerceChanceTrackInfo info = (CommerceChanceTrackInfo)hp.get(key);
					CommerceChanceTrackFactory.getLocalInstance(ctx).submit(info);
				}
			}
			//保存意向房源
			if(hp.get("roomCollection")!=null){
				RoomCollection ccrc = (RoomCollection)hp.get("roomCollection");
				for(int i=0;i<ccrc.size();i++){
					RoomInfo info = ccrc.get(i);
					CommerceChangeRoomInfo ccri = new CommerceChangeRoomInfo();
					ccri.setRoom(info);
					ccri.setHead(cci);
					CommerceChangeRoomFactory.getLocalInstance(ctx).submit(ccri);
				}
			}
			SHECustomerChangeReasonFacadeFactory.getLocalInstance(ctx).addNewMessage(sheci.getSellProject().getOrgUnit(), SHECustomerChangeReasonEnum.ADDNEW_VALUE,editData, sheci.getPropertyConsultant());
		}
		return pk;
	}
}