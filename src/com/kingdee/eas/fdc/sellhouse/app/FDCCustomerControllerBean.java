package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

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
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoCollection;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerGroupDetailInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.master.cssp.UsedStatusEnum;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.LinkmanEntryCollection;
import com.kingdee.eas.fdc.sellhouse.LinkmanEntryFactory;
import com.kingdee.eas.fdc.sellhouse.LinkmanEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SellHouseException;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;
import com.kingdee.util.UuidException;

public class FDCCustomerControllerBean extends
		AbstractFDCCustomerControllerBean {
	private static final Integer STATUS_NORMAL = new Integer(0);// 一般跟进状态

	private static final Integer STATUS_IMPORTANT_TRACK = new Integer(1);// 重点跟进状态

	private static final Integer NORMAL = new Integer(1);// 未被废弃的

	private static final Integer BLANKOUTED = new Integer(0);// 被废弃的

	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.FDCCustomerControllerBean");

	private static final String DEFAULT_SHE_CSSP_GROUP_ID = "Mvoi4gEaEADgAB6EwKgTBXolaaI=";// 预设售楼客户类型的UUID

	// 默认采用编码。
	protected String _getLogInfo(Context ctx, IObjectPK pk)
			throws BOSException, EASBizException {
		FDCCustomerInfo info = (FDCCustomerInfo) super._getValue(ctx, pk);
		String retValue = "";
		if (info.getNumber() != null) {
			retValue = info.getNumber();
			if (info.getName() != null) {
				retValue = retValue + " " + info.getName();
			}
		}
		return retValue;
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		DataBaseCodeRuleHelper.handleIntermitNumber(ctx, (DataBaseInfo)model);
		IObjectPK pk = super._submit(ctx, model);
		
		//反写联系人 --zhicheng_jin 090615
		FDCCustomerInfo cus = (FDCCustomerInfo) model;
		LinkmanEntryCollection linkmans = cus.getLinkmanList();
		for(int i=0; i<linkmans.size(); i++){
			LinkmanEntryInfo linkman = linkmans.get(i);
			FDCCustomerInfo linkCus;
			//linkCus = linkman.getFdcCustomer();
			LinkmanEntryInfo recLinkman = new LinkmanEntryInfo();
			recLinkman.setFdcCustomer(cus);
			recLinkman.setRelation(linkman.getRelation());
			recLinkman.setPersonName(linkman.getPersonName());
			recLinkman.setSex(linkman.getSex());
			recLinkman.setPhone(linkman.getPhone());
			recLinkman.setDescription(linkman.getDescription());
			//recLinkman.setHead(linkCus);
			if(cus.getId() != null){
				FilterInfo filter = new FilterInfo();
				//filter.getFilterItems().add(new FilterItemInfo("head.id", linkCus.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id", cus.getId().toString()));
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
				LinkmanEntryCollection tmpLinks = LinkmanEntryFactory.getLocalInstance(ctx).getLinkmanEntryCollection(view);
				
				if(tmpLinks.isEmpty()){
				}else if(tmpLinks.size() == 1){
					recLinkman.setId(tmpLinks.get(0).getId());
				}else{
					logger.error("联系人重复. cusNum:" + cus.getNumber());
					recLinkman.setId(tmpLinks.get(0).getId());
				}
			}
			LinkmanEntryFactory.getLocalInstance(ctx).submit(recLinkman);
		}
		//--
		
		return pk;
	}
	
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		return super._save(ctx, model);
	}
	
	protected void _update(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		super._update(ctx, pk, model);
		
		FDCCustomerInfo fdcCustomer = getFDCCusByPK(ctx, pk);
		CustomerInfo customer = fdcCustomer.getSysCustomer();
		if(customer == null){
			return;
		}
		updateSysCustomer(ctx, fdcCustomer, customer);
	}

	private FDCCustomerInfo getFDCCusByPK(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		SelectorItemCollection collection = new SelectorItemCollection();
		collection.add("*");
		collection.add("country.*");
		collection.add("province.*");
		collection.add("city.*");
		collection.add("region.*");
		collection.add("sysCustomer.*");
		FDCCustomerInfo fdcCustomer = (FDCCustomerInfo) this.getValue(ctx,
				pk, collection);
		return fdcCustomer;
	}
	
	private void updateSysCustomer(Context ctx, FDCCustomerInfo fdcCustomer, CustomerInfo customer) throws BOSException, SellHouseException, EASBizException {
		setSysCustomerValue(ctx, fdcCustomer, customer);
		
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("number");
		selCol.add("name");
		selCol.add("address");
		selCol.add("country");
		selCol.add("province");
		selCol.add("city");
		selCol.add("region");
		selCol.add("description");
		selCol.add("groupInfo");
		CustomerFactory.getLocalInstance(ctx).updatePartial(customer, selCol);
	}

	private void setSysCustomerValue(Context ctx, FDCCustomerInfo fdcCustomer, CustomerInfo customer) throws BOSException, EASBizException {
		customer.setNumber("SHE-" + fdcCustomer.getNumber());
		customer.setName(fdcCustomer.getName());
		customer.setAddress(fdcCustomer.getMailAddress());
		customer.setCountry(fdcCustomer.getCountry());
		customer.setProvince(fdcCustomer.getProvince());
		customer.setCity(fdcCustomer.getCity());
		customer.setRegion(fdcCustomer.getRegion());
		customer.setDescription(fdcCustomer.getDescription());

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		Set name=new HashSet();
		name.add("售楼客户");
		name.add("租赁客户");
		filter.getFilterItems().add(new FilterItemInfo("name",name,CompareType.INCLUDE));
		//---需要在集团下存在售楼客户分录，修改BUG309599
		filter.getFilterItems().add(new FilterItemInfo("CU.id", OrgConstants.DEF_CU_ID));
		//-----------------
		
		CSSPGroupCollection sheGroupCol = CSSPGroupFactory
				.getLocalInstance(ctx).getCSSPGroupCollection(view);
		if (sheGroupCol.size() != 1) {//不存在或则存在多个，均不合理，给出提示
			throw new SellHouseException(SellHouseException.NOCUSTOMERCLASSIFY);
		}
		CSSPGroupInfo groupInfo = sheGroupCol.get(0);
		customer.setBrowseGroup(groupInfo);
		CustomerGroupDetailInfo info = new CustomerGroupDetailInfo();
		info.setCustomerGroup(groupInfo);
		info.setCustomerGroupFullName(groupInfo.getName());
		info.setCustomerGroupStandard(groupInfo.getGroupStandard());
		customer.getCustomerGroupDetails().add(info);
		customer.setUsedStatus(UsedStatusEnum.APPROVED);
		//同步房地产客户到系统客户的时候带上财务组织 xin_wang 2010.09.10
		if(customer.getId()!=null){
			EntityViewInfo view1 = new EntityViewInfo();
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("customer",customer.getId()));
			SelectorItemCollection coll = new SelectorItemCollection();
			coll.add("companyOrgUnit.*");
			coll.add("id");
			coll.add("customer");
			coll.add("CU");
			view1.setFilter(filter1);
			view1.setSelector(coll);
			CustomerCompanyInfoCollection customercoll = CustomerCompanyInfoFactory.getLocalInstance(ctx).getCustomerCompanyInfoCollection(view1);
			CustomerCompanyInfoInfo cinfo = customercoll.get(0);
			if(cinfo==null){
				cinfo = new CustomerCompanyInfoInfo();
				cinfo.setCompanyOrgUnit(ContextUtil.getCurrentFIUnit(ctx));
				cinfo.setCustomer(customer);
				cinfo.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
				CustomerCompanyInfoFactory.getLocalInstance(ctx).addnew(cinfo);
			}
		}
	}
	
	private void updateSysCustomer(Context ctx, FDCCustomerInfo fdcCustomer, CustomerInfo customer,List list) throws BOSException, SellHouseException, EASBizException {
		setSysCustomerValue(ctx, fdcCustomer, customer,list);
		
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("number");
		selCol.add("name");
		selCol.add("address");
		selCol.add("country");
		selCol.add("province");
		selCol.add("city");
		selCol.add("region");
		selCol.add("description");
		selCol.add("groupInfo");
		CustomerFactory.getLocalInstance(ctx).updatePartial(customer, selCol);
	}
	
	private void setSysCustomerValue(Context ctx, FDCCustomerInfo fdcCustomer, CustomerInfo customer,List list) throws BOSException, EASBizException {
		customer.setNumber("SHE-" + fdcCustomer.getNumber());
		customer.setName(fdcCustomer.getName());
		customer.setAddress(fdcCustomer.getMailAddress());
		customer.setCountry(fdcCustomer.getCountry());
		customer.setProvince(fdcCustomer.getProvince());
		customer.setCity(fdcCustomer.getCity());
		customer.setRegion(fdcCustomer.getRegion());
		customer.setDescription(fdcCustomer.getDescription());

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.list2Set(list),CompareType.INCLUDE));
		//filter.getFilterItems().add(new FilterItemInfo("name", "售楼客户"));
		//---需要在集团下存在售楼客户分录，修改BUG309599
		//filter.getFilterItems().add(new FilterItemInfo("CU.id", OrgConstants.DEF_CU_ID));
		//-----------------
		
		CSSPGroupCollection sheGroupCol = CSSPGroupFactory
				.getLocalInstance(ctx).getCSSPGroupCollection(view);
//		if (sheGroupCol.size() != 1) {//不存在或则存在多个，均不合理，给出提示
//			throw new SellHouseException(SellHouseException.NOCUSTOMERCLASSIFY);
//		}
		for(int i=0;i<sheGroupCol.size();i++)
		{
			CSSPGroupInfo groupInfo = sheGroupCol.get(i);
			customer.setBrowseGroup(groupInfo);
			CustomerGroupDetailInfo info = new CustomerGroupDetailInfo();
			info.setCustomerGroup(groupInfo);
			info.setCustomerGroupFullName(groupInfo.getName());
			info.setCustomerGroupStandard(groupInfo.getGroupStandard());
			customer.getCustomerGroupDetails().add(info);
			customer.setUsedStatus(UsedStatusEnum.APPROVED);
		}
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
	}
	
	protected void _addToSysCustomer(Context ctx, String id,List list) throws EASBizException, BOSException, UuidException
	              {
		FDCCustomerInfo fdcCustomer = getFDCCusByPK(ctx, new ObjectUuidPK(BOSUuid.read(id)));
		//这里有可能系统客户已经被删除，但关联id还在，故要增加判断名称是否存在
		if (fdcCustomer.getSysCustomer() != null && fdcCustomer.getSysCustomer().getName()!=null) {
			CustomerInfo customer = fdcCustomer.getSysCustomer();
			updateSysCustomer(ctx, fdcCustomer, customer,list);
		}else{
			CustomerInfo customer = new CustomerInfo();
			customer.setId(BOSUuid.create(CustomerInfo.getBosType()));
			setSysCustomerValue(ctx, fdcCustomer, customer);
			fdcCustomer.setSysCustomer(customer);
			CustomerFactory.getLocalInstance(ctx).addnew(customer);
			
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("sysCustomer");
			this.updatePartial(ctx, fdcCustomer, sels);
		}
	}
	
	protected void _addToSysCustomer(Context ctx, String id)
			throws BOSException, EASBizException {
		FDCCustomerInfo fdcCustomer = getFDCCusByPK(ctx, new ObjectUuidPK(BOSUuid.read(id)));
		if (fdcCustomer.getSysCustomer() != null && fdcCustomer.getSysCustomer().getName()!=null) {
			CustomerInfo customer = fdcCustomer.getSysCustomer();
			updateSysCustomer(ctx, fdcCustomer, customer);//已经同步过
		}else{
			CustomerInfo customer = new CustomerInfo();
			customer.setId(BOSUuid.create(CustomerInfo.getBosType()));
			customer.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
			setSysCustomerValue(ctx, fdcCustomer, customer);
			fdcCustomer.setSysCustomer(customer);
			CustomerFactory.getLocalInstance(ctx).addnew(customer);
			
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("sysCustomer");
			this.updatePartial(ctx, fdcCustomer, sels);
		}
	}

	// 废弃客户
	protected void _blankOut(Context ctx, List idList) throws BOSException {
		String updateNumberSql = "update T_SHE_FDCCUSTOMER set FIsEnabled=?  where FID=? ";
		Object[] params = new Object[2];
		params[0] = BLANKOUTED;

		for (int i = 0; i < idList.size(); i++) {
			params[1] = idList.get(i);
			DbUtil.execute(ctx, updateNumberSql, params);
		}
	}

	// 捡回客户
	protected void _pickUp(Context ctx, List idList) throws BOSException {
		String updateNumberSql = "update T_SHE_FDCCUSTOMER set FIsEnabled=?  where FID=? ";
		Object[] params = new Object[2];
		params[0] = NORMAL;
		for (int i = 0; i < idList.size(); i++) {
			params[1] = idList.get(i);
			DbUtil.execute(ctx, updateNumberSql, params);
		}
	}

	protected void _modifyName(Context ctx, IObjectValue fdccustomer)
			throws BOSException {
		// 由EditUI自动实现了,不用到该实现

	}

	// 标记为重点跟进
	protected void _signImportantTrack(Context ctx, List idList)
			throws BOSException {
		String updateNumberSql = "update T_SHE_FDCCUSTOMER set FIsImportantTrack=?  where FID=? ";
		Object[] params = new Object[2];
		params[0] = STATUS_IMPORTANT_TRACK;
		for (int i = 0; i < idList.size(); i++) {
			params[1] = idList.get(i);
			DbUtil.execute(ctx, updateNumberSql, params);
		}
	}

	// 反标记重点跟进
	protected void _cancelImportantTrack(Context ctx, List idList)
			throws BOSException {
		String updateNumberSql = "update T_SHE_FDCCUSTOMER set FIsImportantTrack=?  where FID=? ";
		Object[] params = new Object[2];
		params[0] = STATUS_NORMAL;
		for (int i = 0; i < idList.size(); i++) {
			params[1] = idList.get(i);
			DbUtil.execute(ctx, updateNumberSql, params);
		}
	}

	// 客户转接
	protected void _switchTo(Context ctx, List idList, String salesmanId)
			throws BOSException {
		if (idList == null || idList.isEmpty()) {
			logger.warn("选中项为空，客户端逻辑判断有误，请检查。");
			return;
		}
		StringBuffer sb = new StringBuffer(
				"update T_SHE_FDCCUSTOMER set FSalesmanID=? where FID in (");
		for (int i = 0; i < idList.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append("'");
			sb.append(idList.get(i));
			sb.append("'");
		}
		sb.append(")");
		logger.debug("sql:" + sb.toString());
		DbUtil.execute(ctx, sb.toString(), new Object[] { salesmanId });
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		//如果客户同步了系统客户，同时将系统客户删除
		//TODO 是否系统客户参与收款的情况
		delSysCustomerByFDCCustomer(ctx, pk);
		DataBaseCodeRuleHelper.recycleNumber(ctx, (DataBaseInfo) _getValue(ctx, pk));
		super._delete(ctx, pk);
	}
	
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		for(int i=0; i<arrayPK.length; i++){
			delSysCustomerByFDCCustomer(ctx, arrayPK[i]);
			DataBaseCodeRuleHelper.recycleNumber(ctx, (DataBaseInfo) _getValue(ctx, arrayPK[i]));
		}
		super._delete(ctx, arrayPK);
	}
	
	private void delSysCustomerByFDCCustomer(Context ctx, IObjectPK pk) throws BOSException, EASBizException{
		FDCCustomerInfo fdcCustomer = getFDCCusByPK(ctx, pk);
		if (fdcCustomer.getSysCustomer() != null) {
			String id = fdcCustomer.getSysCustomer().getId().toString();
			String sql = "delete from T_BD_Customer where fid=?";
			DbUtil.execute(ctx, sql, new Object[] { id });
		}		
	}
	
	protected Map _verifySave(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		Map returnMap = new HashMap();
		StringBuffer errorMsg = null;
		FDCCustomerInfo customer = (FDCCustomerInfo)model;
//		MarketDisplaySetting setting = new MarketDisplaySetting();
//		boolean isSingle = setting.getCheckedRadioButton().equals("kDRadioSingle")?true:false;
//		if(isSingle){
			errorMsg = new StringBuffer();
			/***
			 * 1.判断重复的姓名
			 */
			if(StringUtils.isEmpty(customer.getName())){
				errorMsg.append("您输入的客户名称为空！\n");
			}else{
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("name", customer.getName()));
				filter.getFilterItems().add(new FilterItemInfo("project.id", customer.getProject().getId().toString()));
				//如果是修改,则ID不为空，排除自己
				if(customer.getId()!=null)
					filter.getFilterItems().add(new FilterItemInfo("id", customer.getId().toString(),CompareType.NOTEQUALS));
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add(new SelectorItemInfo("name"));
				sel.add(new SelectorItemInfo("lastTrackDate"));
				sel.add(new SelectorItemInfo("createTime"));
			    sel.add(new SelectorItemInfo("salesman.name"));
		    	EntityViewInfo view = new EntityViewInfo();
				view.getSelector().addObjectCollection(sel);
				view.setFilter(filter);
				FDCCustomerCollection cuses = getFDCCustomerCollection(ctx,view);
				if(cuses!=null&&cuses.size()>0){
					errorMsg.append("存在重复的客户名称，相关客户: \n");
					errorMsg.append("客户\t销售顾问\t登记日期\t最后跟进日期 \n");
					for(Iterator it = cuses.iterator();it.hasNext();){
						FDCCustomerInfo info = (FDCCustomerInfo)it.next();
						errorMsg.append(info.getName());
						errorMsg.append("\t");
						errorMsg.append(info.getSalesman().getName());
						errorMsg.append("\t");
						if(info.getCreateTime()!=null)
							errorMsg.append(FDCDateHelper.formatDate2(info.getCreateTime()));
						errorMsg.append("\t");
						if(info.getLastTrackDate()!=null)
							errorMsg.append(FDCDateHelper.formatDate2(info.getLastTrackDate()));
						errorMsg.append("\n");
					}
					
				}
			}
			if(errorMsg.length()>0)
				returnMap.put("dupNameMsg", errorMsg.toString());
			/***
			 * 2.判断重复的电话号码
			 */
			errorMsg = new StringBuffer();
			String phone = customer.getPhone();
			if(StringUtils.isEmpty(phone)){
				errorMsg.append("您输入的客户联系电话为空！\n");
			}else{
				String regEx="[\\d]{1,}[\\d|;|；]{0,}";
		        boolean result=Pattern.compile(regEx).matcher(phone).matches();
		        if(!result){
		        	errorMsg.append("联系电话只能输入数字,多个电话号码之间用;分隔!\n");
		        	/***
		        	 * 此种情况，不允许保存,无须再判断，直接抛出异常
		        	 */
		        	throw new EASBizException(new NumericExceptionSubItem("000","联系电话只能输入数字,多个电话号码之间用;分隔!"));
		        }else{
		        	FilterInfo filter = getPhonesFilter(customer);
		        	FilterInfo tmp = new FilterInfo();
		        	tmp.getFilterItems().add(new FilterItemInfo("project.id", customer.getProject().getId().toString()));
					filter.mergeFilter(tmp, "and");
		        	SelectorItemCollection sel = new SelectorItemCollection();
					sel.add(new SelectorItemInfo("name"));
					sel.add(new SelectorItemInfo("lastTrackDate"));
					sel.add(new SelectorItemInfo("createTime"));
				    sel.add(new SelectorItemInfo("salesman.name"));
			    	EntityViewInfo view = new EntityViewInfo();
					view.getSelector().addObjectCollection(sel);
					view.setFilter(filter);
					FDCCustomerCollection cuses = getFDCCustomerCollection(ctx,view);
					if(cuses!=null&&cuses.size()>0){
						errorMsg.append("存在重复的联系电话，相关客户: \n");
						errorMsg.append("客户\t销售顾问\t登记日期\t最后跟进日期 \n");
						for(Iterator it = cuses.iterator();it.hasNext();){
							FDCCustomerInfo info = (FDCCustomerInfo)it.next();
							errorMsg.append(info.getName());
							errorMsg.append("\t");
							errorMsg.append(info.getSalesman().getName());
							errorMsg.append("\t");
							if(info.getCreateTime()!=null)
								errorMsg.append(FDCDateHelper.formatDate2(info.getCreateTime()));
							errorMsg.append("\t");
							if(info.getLastTrackDate()!=null)
								errorMsg.append(FDCDateHelper.formatDate2(info.getLastTrackDate()));
							errorMsg.append("\n");
						}
					}
		        }
			}
			if(errorMsg.length()>0)
				returnMap.put("dupPhoneMsg", errorMsg.toString());
//		}else{
//			/**
//			 * 3、当参数为名称和电话重复时，校验
//			 */
//			errorMsg = new StringBuffer();
//			boolean isNameNull = false;
//			boolean isPhoneNull = false;
//			if(StringUtils.isEmpty(customer.getName())){
//				errorMsg.append("客户名称为空!");
//				isNameNull = true;
//			}
//			if(!StringUtils.isEmpty(customer.getPhone())){
//				String regEx="[\\d]{1,}[\\d|;|；]{0,}";
//		        boolean result=Pattern.compile(regEx).matcher(customer.getPhone()).matches();
//		        if(!result){
//		        	errorMsg.append("联系电话只能输入数字,多个电话号码之间用;分隔!\n");
//		        	/***
//		        	 * 此种情况，不允许保存,无须再判断，直接抛出异常
//		        	 */
//		        	throw new EASBizException(new NumericExceptionSubItem("000","联系电话只能输入数字,多个电话号码之间用;分隔!"));
//		        }
//			}else{
//				errorMsg.append("您输入的客户联系电话为空！\n");
//				isPhoneNull = true;
//			}
//			
//			if(!isNameNull && !isPhoneNull){
//				FilterInfo filter = getPhonesFilter(customer);
//				filter.getFilterItems().add(new FilterItemInfo("name",customer.getName()));
//	        	SelectorItemCollection sel = new SelectorItemCollection();
//				sel.add(new SelectorItemInfo("name"));
//				sel.add(new SelectorItemInfo("lastTrackDate"));
//				sel.add(new SelectorItemInfo("createTime"));
//			    sel.add(new SelectorItemInfo("salesman.name"));
//		    	EntityViewInfo view = new EntityViewInfo();
//				view.getSelector().addObjectCollection(sel);
//				view.setFilter(filter);
//				FDCCustomerCollection cuses = getFDCCustomerCollection(ctx,view);
//				if(cuses!=null&&cuses.size()>0){
//					errorMsg.append("存在重复的客户，相关客户: \n");
//					errorMsg.append("客户\t销售顾问\t登记日期\t最后跟进日期 \n");
//					for(Iterator it = cuses.iterator();it.hasNext();){
//						FDCCustomerInfo info = (FDCCustomerInfo)it.next();
//						errorMsg.append(info.getName());
//						errorMsg.append("\t");
//						errorMsg.append(info.getSalesman().getName());
//						errorMsg.append("\t");
//						if(info.getCreateTime()!=null)
//							errorMsg.append(FDCDateHelper.formatDate2(info.getCreateTime()));
//						errorMsg.append("\t");
//						if(info.getLastTrackDate()!=null)
//							errorMsg.append(FDCDateHelper.formatDate2(info.getLastTrackDate()));
//						errorMsg.append("\n");
//					}
//				}
//			}
//			if(errorMsg.length()>0){
//				returnMap.put("dupNameAndPhoneMsg", errorMsg.toString());
//			}
//		}
		return returnMap;
	}

	private FilterInfo getPhonesFilter(FDCCustomerInfo customer)
			throws EASBizException {
		String[] phones = customer.getPhone().split("[；|;]{1}");
		if(phones.length == 0){//用户只输入;这个符号会导致该情况
			throw new EASBizException(new NumericExceptionSubItem("000","联系电话只能输入数字,多个电话号码之间用;分隔!"));
		}
		FilterInfo filter = null;
		for(int i=0; i<phones.length; i++){
			if(phones[i].trim().equals("")){
				continue;
			}
			if(filter == null){
				filter = createFilterPerPhone(phones[i]);
			}else{
				FilterInfo tmp = createFilterPerPhone(phones[i]);
				try {
					filter.mergeFilter(tmp, "or");
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		if(customer.getId() != null){
			FilterInfo filter2 = new FilterInfo();
			filter2.getFilterItems().add(new FilterItemInfo("id", customer.getId().toString(), CompareType.NOTEQUALS));
			try {
				filter.mergeFilter(filter2, "and");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		return filter;
	}
	/**
	 * 根据一条电话号码构造查询Filter,表中电话号码是1个字段,多个号码之间用,或，分隔的
	 * 注:如果直接用like %phone% 会导致匹配不精确,如表中存在号码12345,用123去匹配就不准确了
	 * */
	private static FilterInfo createFilterPerPhone(String phone) {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("phone", phone, CompareType.EQUALS));
		
		mergeOr(filter, new FilterItemInfo("phone", "%;"+phone, CompareType.LIKE));
		mergeOr(filter, new FilterItemInfo("phone", phone+";%", CompareType.LIKE));
		mergeOr(filter, new FilterItemInfo("phone", "%;"+phone+";%", CompareType.LIKE));
		mergeOr(filter, new FilterItemInfo("phone", "%;"+phone+"；%", CompareType.LIKE));
		
		mergeOr(filter, new FilterItemInfo("phone", "%；"+phone, CompareType.LIKE));
		mergeOr(filter, new FilterItemInfo("phone", phone+"；%", CompareType.LIKE));
		mergeOr(filter, new FilterItemInfo("phone", "%；"+phone+"；%", CompareType.LIKE));
		mergeOr(filter, new FilterItemInfo("phone", "%；"+phone+";%", CompareType.LIKE));
		
		return filter;
	}
	
	private static void mergeOr(FilterInfo filter, FilterItemInfo info) {
		FilterInfo tmp = new FilterInfo();
		tmp.getFilterItems().add(info);
		try {
			filter.mergeFilter(tmp, "OR");
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量设置单据中的认购状态
	 * @author liang_ren969
	 * @date 2010-6-11
	 */
	protected void _setStatus(Context ctx, List idList) throws BOSException {
		 List paramList = new ArrayList();
		 
		  /**
		   * 将每一个id放入到一个List中，为批量更新做准备
		   * 没明白api中为什么这么处理
		   */
		  for (int i = 0; i < idList.size(); i++) {
			  paramList.add(Arrays.asList(((Object[])idList.get(i))));
		  }
		
		  StringBuffer buffer = new StringBuffer();
		  buffer.append("update t_she_fdccustomer ");
		  buffer.append(" set fissub = 0 where fid in");
		  buffer.append(" (select distinct ci.fcustomerid from T_SHE_Purchase p, T_SHE_PurchaseCustomerInfo ci");
		  buffer.append(" where ci.fheadid = p.fid ");
		  buffer.append(" and ci.fcustomerid = ?)");
		  buffer.append(" and fissub = 1");
		  
		  /**
		   * 得到update的sql语句
		   */
		  StringBuffer sb = new StringBuffer();
		  sb.append("update t_she_fdccustomer ");
		  sb.append(" set fissub = 1 where fid in");
		  sb.append(" (select distinct ci.fcustomerid from T_SHE_Purchase p, T_SHE_PurchaseCustomerInfo ci");
		  sb.append(" where ci.fheadid = p.fid and p.fpurchasestate in ('PurchaseAudit', 'PurchaseChange')");
		  sb.append(" and ci.fcustomerid = ?)");
		
		  
		try {
			
			/**
			 * 批量update认购状态
			 */
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.executeBatch(buffer.toString(), paramList);
			builder.executeBatch(sb.toString(), paramList);
			
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}

	protected Map _verifySave(Context ctx, IObjectValue model, boolean isSingle)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		Map returnMap = new HashMap();
		StringBuffer errorMsg = null;
		FDCCustomerInfo customer = (FDCCustomerInfo)model;
//		if(isSingle){
			errorMsg = new StringBuffer();
			/***
			 * 1.判断重复的姓名
			 */
			if(StringUtils.isEmpty(customer.getName())){
				errorMsg.append("您输入的客户名称为空！\n");
			}else{
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("name", customer.getName()));
				filter.getFilterItems().add(new FilterItemInfo("project.id", customer.getProject().getId().toString()));
				//如果是修改,则ID不为空，排除自己
				if(customer.getId()!=null)
					filter.getFilterItems().add(new FilterItemInfo("id", customer.getId().toString(),CompareType.NOTEQUALS));
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add(new SelectorItemInfo("name"));
				sel.add(new SelectorItemInfo("lastTrackDate"));
				sel.add(new SelectorItemInfo("createTime"));
			    sel.add(new SelectorItemInfo("salesman.name"));
		    	EntityViewInfo view = new EntityViewInfo();
				view.getSelector().addObjectCollection(sel);
				view.setFilter(filter);
				FDCCustomerCollection cuses = getFDCCustomerCollection(ctx,view);
				if(cuses!=null&&cuses.size()>0){
					errorMsg.append("存在重复的客户名称，相关客户: \n");
					errorMsg.append("客户\t销售顾问\t登记日期\t最后跟进日期 \n");
					for(Iterator it = cuses.iterator();it.hasNext();){
						FDCCustomerInfo info = (FDCCustomerInfo)it.next();
						errorMsg.append(info.getName());
						errorMsg.append("\t");
						errorMsg.append(info.getSalesman().getName());
						errorMsg.append("\t");
						if(info.getCreateTime()!=null)
							errorMsg.append(FDCDateHelper.formatDate2(info.getCreateTime()));
						errorMsg.append("\t");
						if(info.getLastTrackDate()!=null)
							errorMsg.append(FDCDateHelper.formatDate2(info.getLastTrackDate()));
						errorMsg.append("\n");
					}
					
				}
			}
			if(errorMsg.length()>0)
				returnMap.put("dupNameMsg", errorMsg.toString());
			/***
			 * 2.判断重复的电话号码
			 */
			errorMsg = new StringBuffer();
			String phone = customer.getPhone();
			if(StringUtils.isEmpty(phone)){
				errorMsg.append("您输入的客户联系电话为空！\n");
			}else{
				String regEx="[\\d]{1,}[\\d|;|；]{0,}";
		        boolean result=Pattern.compile(regEx).matcher(phone).matches();
		        if(!result){
		        	errorMsg.append("联系电话只能输入数字,多个电话号码之间用;分隔!\n");
		        	/***
		        	 * 此种情况，不允许保存,无须再判断，直接抛出异常
		        	 */
		        	throw new EASBizException(new NumericExceptionSubItem("000","联系电话只能输入数字,多个电话号码之间用;分隔!"));
		        }else{
		        	FilterInfo filter = getPhonesFilter(customer);
		        	FilterInfo tmp = new FilterInfo();
		        	tmp.getFilterItems().add(new FilterItemInfo("project.id", customer.getProject().getId().toString()));
					filter.mergeFilter(tmp, "and");
					
		        	SelectorItemCollection sel = new SelectorItemCollection();
					sel.add(new SelectorItemInfo("name"));
					sel.add(new SelectorItemInfo("lastTrackDate"));
					sel.add(new SelectorItemInfo("createTime"));
				    sel.add(new SelectorItemInfo("salesman.name"));
			    	EntityViewInfo view = new EntityViewInfo();
					view.getSelector().addObjectCollection(sel);
					view.setFilter(filter);
					FDCCustomerCollection cuses = getFDCCustomerCollection(ctx,view);
					if(cuses!=null&&cuses.size()>0){
						errorMsg.append("存在重复的联系电话，相关客户: \n");
						errorMsg.append("客户\t销售顾问\t登记日期\t最后跟进日期 \n");
						for(Iterator it = cuses.iterator();it.hasNext();){
							FDCCustomerInfo info = (FDCCustomerInfo)it.next();
							errorMsg.append(info.getName());
							errorMsg.append("\t");
							errorMsg.append(info.getSalesman().getName());
							errorMsg.append("\t");
							if(info.getCreateTime()!=null)
								errorMsg.append(FDCDateHelper.formatDate2(info.getCreateTime()));
							errorMsg.append("\t");
							if(info.getLastTrackDate()!=null)
								errorMsg.append(FDCDateHelper.formatDate2(info.getLastTrackDate()));
							errorMsg.append("\n");
						}
					}
		        }
			}
			if(errorMsg.length()>0)
				returnMap.put("dupPhoneMsg", errorMsg.toString());
//		}else{
			/**
			 * 3、当参数为名称和电话重复时，校验
			 */
			errorMsg = new StringBuffer();
			boolean isNameNull = false;
			boolean isPhoneNull = false;
			if(StringUtils.isEmpty(customer.getName())){
				errorMsg.append("客户名称为空!");
				isNameNull = true;
			}
			if(!StringUtils.isEmpty(customer.getPhone())){
				String regEx="[\\d]{1,}[\\d|;|；]{0,}";
		        boolean result=Pattern.compile(regEx).matcher(customer.getPhone()).matches();
		        if(!result){
		        	errorMsg.append("联系电话只能输入数字,多个电话号码之间用;分隔!\n");
		        	/***
		        	 * 此种情况，不允许保存,无须再判断，直接抛出异常
		        	 */
		        	throw new EASBizException(new NumericExceptionSubItem("000","联系电话只能输入数字,多个电话号码之间用;分隔!"));
		        }
			}else{
				errorMsg.append("您输入的客户联系电话为空！\n");
				isPhoneNull = true;
			}
			
			if(!isNameNull && !isPhoneNull){
				FilterInfo filter = getPhonesFilter(customer);
				FilterInfo nameFilter = new FilterInfo();
				nameFilter.getFilterItems().add(new FilterItemInfo("name",customer.getName()));
				filter.mergeFilter(nameFilter, "and");
				
				FilterInfo tmp = new FilterInfo();
	        	tmp.getFilterItems().add(new FilterItemInfo("project.id", customer.getProject().getId().toString()));
				filter.mergeFilter(tmp, "and");
				
	        	SelectorItemCollection sel = new SelectorItemCollection();
				sel.add(new SelectorItemInfo("name"));
				sel.add(new SelectorItemInfo("lastTrackDate"));
				sel.add(new SelectorItemInfo("createTime"));
			    sel.add(new SelectorItemInfo("salesman.name"));
		    	EntityViewInfo view = new EntityViewInfo();
				view.getSelector().addObjectCollection(sel);
				view.setFilter(filter);
				FDCCustomerCollection cuses = getFDCCustomerCollection(ctx,view);
				if(cuses!=null&&cuses.size()>0){
					errorMsg.append("存在重复的移动电话和客户名称，相关客户: \n");
					errorMsg.append("客户\t销售顾问\t登记日期\t最后跟进日期 \n");
					for(Iterator it = cuses.iterator();it.hasNext();){
						FDCCustomerInfo info = (FDCCustomerInfo)it.next();
						errorMsg.append(info.getName());
						errorMsg.append("\t");
						errorMsg.append(info.getSalesman().getName());
						errorMsg.append("\t");
						if(info.getCreateTime()!=null)
							errorMsg.append(FDCDateHelper.formatDate2(info.getCreateTime()));
						errorMsg.append("\t");
						if(info.getLastTrackDate()!=null)
							errorMsg.append(FDCDateHelper.formatDate2(info.getLastTrackDate()));
						errorMsg.append("\n");
					}
				}
			}
			if(errorMsg.length()>0){
				returnMap.put("dupNameAndPhoneMsg", errorMsg.toString());
			}
//		}
		return returnMap;
	}

	protected void _updateTenancyBill(Context ctx, String fdcCustID)
			throws BOSException {
		String executeSql = "select FTenancyBillID ";
		 executeSql += " from T_TEN_TenancyCustomerEntry where FFdcCustomerID = '"+fdcCustID+"' " ;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(executeSql);
		IRowSet row = builder.executeQuery();
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add("fdcCustomer.name");
		SelectorItemCollection tenColl = new SelectorItemCollection();
		tenColl.add("tenCustomerDes");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setSelector(coll);
		StringBuffer sb =null;
		try {
			while(row.next()){//这个地方到底怎么优化了？
				sb = new StringBuffer();
				String  tenancyid = (String)row.getObject("FTenancyBillID");
				filter.getFilterItems().add(new FilterItemInfo("tenancyBill",tenancyid));
				view.setFilter(filter);
				TenancyCustomerEntryCollection customerColl=  TenancyCustomerEntryFactory.getLocalInstance(ctx).getTenancyCustomerEntryCollection(view);
				for(int i = 0 ;i<customerColl.size();i++){
					TenancyCustomerEntryInfo info = customerColl.get(i);
					if(i<1){
						sb.append(info.getFdcCustomer().getName());
					}else{
						sb.append(",");
						sb.append(info.getFdcCustomer().getName());
					}
				}
				TenancyBillInfo tenancyBillInfo = TenancyBillFactory.getLocalInstance(ctx).getTenancyBillInfo(new ObjectUuidPK(tenancyid));
				tenancyBillInfo.setTenCustomerDes(sb.toString());
				TenancyBillFactory.getLocalInstance(ctx).updatePartial(tenancyBillInfo, tenColl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}