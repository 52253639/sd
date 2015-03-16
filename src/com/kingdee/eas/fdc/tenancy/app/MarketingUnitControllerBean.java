package com.kingdee.eas.fdc.tenancy.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.MutableTreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
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
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellOrderCollection;
import com.kingdee.eas.fdc.sellhouse.SellOrderFactory;
import com.kingdee.eas.fdc.sellhouse.SellOrderInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaCollection;
import com.kingdee.eas.fdc.sellhouse.SubareaFactory;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.tenancy.MarketUnitControlCollection;
import com.kingdee.eas.fdc.tenancy.MarketUnitControlFactory;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlatCollection;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlatFactory;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlatInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.LocaleUtils;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.UuidException;

public class MarketingUnitControllerBean extends AbstractMarketingUnitControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.MarketingUnitControllerBean");

    
    // 获取当前的组织 (不能获取当前的销售组织，因为销售组织的上下级关系和营销单元里存储的组织的关系是不一致的)
    private OrgUnitInfo getCurrentOrgUnit(Context ctx)  throws BOSException, ContractException{
    	OrgUnitInfo oUInfo = ContextUtil.getCurrentOrgUnit(ctx,OrgType.Sale);
		if (oUInfo == null) {
			throw new ContractException(new NumericExceptionSubItem("100","无法获取到当前组织信息！"));
		}
		return oUInfo;
    }
    
    //获取指定组织下的所有子公司id集合 (包含自己)
    private Set getLeafCompanyIdSet(Context ctx,OrgUnitInfo unitInfo) throws BOSException, SQLException {
    	Set companyIdSet = new HashSet();
    	if(unitInfo==null) return companyIdSet;
    		
    	StringBuffer sql = new StringBuffer();
        sql.append("select fid from  t_org_baseUnit where ")
           .append(" (FNumber = '"+unitInfo.getNumber()+"'")
           .append(" or FLongNumber like '"+unitInfo.getLongNumber()+"!%') ")
           .append(" and FisLeaf =1 ");
        IRowSet rs = DbUtil.executeQuery(ctx,sql.toString());
        while (rs.next()) {
        	companyIdSet.add(rs.getString("fid"));
        }
        return companyIdSet;
    }
    
   
    /**
     * 允许查看销售人员 id集合
     * 指定的营销顾问在当前所在的组织下所能看到的销售顾问
     * (区分管控情况和非管控两种情况)
     */
	protected Set _getPermitSaleManIdSet(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException {
		Set retSet = new HashSet();		
		OrgUnitInfo orgUnit = getCurrentOrgUnit(ctx);	
		String parentNumberStr = "'"+orgUnit.getNumber()+"'"; //上级组织编码，包含自己
		String[] parentNumbers = orgUnit.getLongNumber().split("!");
		if(parentNumbers.length>1) {
			for(int i=0;i<(parentNumbers.length-1);i++) {
				parentNumberStr += ",'"+ parentNumbers[i] +"'";
			}
		}
		MarketUnitControlCollection muControlColl = MarketUnitControlFactory.getLocalInstance(ctx).getMarketUnitControlCollection(
				"select orgUnit.id,orgUnit.number,orgUnit.longNumber where controler.id ='"+currSaleMan.getId().toString()+"' and orgUnit.number in ("+parentNumberStr+") ");
		if(muControlColl.size()>0) {	//如果当前组织或下属组织是当前成员管控范围内，则可以访问管控组织下的所有营销成员
			String whereSql = "";
			for(int i=0;i<muControlColl.size();i++) {
				String tempNumber = muControlColl.get(i).getOrgUnit().getNumber();
				String tempLongNumber = muControlColl.get(i).getOrgUnit().getLongNumber();
				whereSql += "and (head.orgUnit.number = '"+tempNumber+"' or head.orgUnit.longNumber like '"+tempLongNumber+"!%')";
			}
			whereSql = whereSql.replaceFirst("and", "");			
			MarketingUnitMemberCollection muMemberColl = MarketingUnitMemberFactory.getLocalInstance(ctx).getMarketingUnitMemberCollection(
										"select member.id  where "+whereSql);
			for(int i=0;i<muMemberColl.size();i++) {
				if(muMemberColl.get(i).getMember()!=null)
					retSet.add(muMemberColl.get(i).getMember().getId().toString());
			}
		}else{
			MarketUnitPlatCollection muPlatColl = MarketUnitPlatFactory.getLocalInstance(ctx).getMarketUnitPlatCollection(
									"select member.id,dutyPerson.id where orgUnit.Number = '"+orgUnit.getNumber()+"' " +
											"or orgUnit.longNumber like '"+orgUnit.getLongNumber()+"!%' " ); 
			for(int i=0;i<muPlatColl.size();i++) {	//" and dutyPerson.id = '"+currSaleMan.getId().toString()+"' "
				UserInfo userInfo = muPlatColl.get(i).getMember(); 
				if(userInfo!=null) {	
					UserInfo dutyUser = muPlatColl.get(i).getDutyPerson();
					if(dutyUser!=null && dutyUser.getId().toString().equals(currSaleMan.getId().toString()))
						retSet.add(userInfo.getId().toString());
				}
			}
		}
		//retSet.add(currSaleMan.getId().toString());
		return retSet;
	}

	
	protected String _getPermitSaleManIdSql(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException {		
		if(currSaleMan==null) return "select FMemberID from T_TEN_MarketUnitPlat where fid='nullnull'";
		
		OrgUnitInfo orgUnit = getCurrentOrgUnit(ctx);	
		String parentNumberStr = "'"+orgUnit.getNumber()+"'"; //上级组织编码，包含自己
		String[] parentNumbers = orgUnit.getLongNumber().split("!");
		if(parentNumbers.length>1) {
			for(int i=0;i<(parentNumbers.length-1);i++) {
				parentNumberStr += ",'"+ parentNumbers[i] +"'";
			}
		}
		MarketUnitControlCollection muControlColl = MarketUnitControlFactory.getLocalInstance(ctx).getMarketUnitControlCollection(
				"select orgUnit.id,orgUnit.number,orgUnit.longNumber where controler.id ='"+currSaleMan.getId().toString()+"' and orgUnit.number in ("+parentNumberStr+") ");
		if(muControlColl.size()>0) {	//如果当前组织或下属组织是当前成员管控范围内，则可以访问管控组织下的所有营销成员
			String whereSql = "";
			for(int i=0;i<muControlColl.size();i++) {
				String tempNumber = muControlColl.get(i).getOrgUnit().getNumber();
				String tempLongNumber = muControlColl.get(i).getOrgUnit().getLongNumber();
				whereSql += "and (orgUnit.Fnumber = '"+tempNumber+"' or orgUnit.FlongNumber like '"+tempLongNumber+"!%')";
			}
			whereSql = whereSql.replaceFirst("and", "");			
			
			String retSql = "select muMember.FMemberID from T_TEN_MarketingUnitMember muMember " +
					"inner join T_TEN_MarketingUnit marUnit on muMember.FheadId=marUnit.Fid " + 
					"inner join T_ORG_BaseUnit orgUnit on marUnit.ForgUnitId = orgUnit.Fid " +					
					"where "+whereSql;
			return retSql;
		}else{
			String retSql = "select muPlat.FMemberID from T_TEN_MarketUnitPlat muPlat " +
					"inner join T_ORG_BaseUnit orgUnit on muPlat.ForgUnitId = orgUnit.Fid " +
					"where (orgUnit.FlongNumber like '"+orgUnit.getLongNumber()+"!%' or orgUnit.FNumber = '"+orgUnit.getNumber()+"' ) " +
					"and FDutyPersonID = '"+currSaleMan.getId().toString()+"' ";
			return retSql;
		}
	}
	
	
	
	
	
	/**
	 * 所允许看到的项目 id集合
	 * 指定营销人员所能看到的项目集合
	 * 区分管控和非管控两种情况
	 */
	protected Set _getPermitSellProjectIdSet(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException {
		Set idSet = new HashSet();
		String permitSql = _getPermitSellProjectIdSql(ctx,currSaleMan);
		
		FDCSQLBuilder sqlBuild = new FDCSQLBuilder(ctx);
		sqlBuild.appendSql(permitSql);
		IRowSet rowSet = sqlBuild.executeQuery();
		try {
			while(rowSet.next()){
				idSet.add(rowSet.getString("FSellProjectID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idSet;
	}

	
	
	protected String _getPermitSellProjectIdSql(Context ctx, UserInfo currSaleMan) throws BOSException, EASBizException {
		if(currSaleMan==null) return "select FSellProjectID from T_TEN_MarketingUnitSellProject where fid = 'nullnulll'";		

		MarketUnitControlCollection muControlColl = MarketUnitControlFactory.getLocalInstance(ctx).getMarketUnitControlCollection(
				"select orgUnit.longNumber where controler.id ='"+currSaleMan.getId().toString()+"' ");
		//管控的组织
		String controlMuSql = "";
		for(int i=0;i<muControlColl.size();i++) {
			String tempLongNumber = muControlColl.get(i).getOrgUnit().getLongNumber();
			controlMuSql += " or (orgUnit.FlongNumber like '"+tempLongNumber+"%') ";
		}
		
		OrgUnitInfo orgUnit = getCurrentOrgUnit(ctx);  
		//所在营销单元里的项目  + 管控的营销单元的项目
		String muIdSql = "select marUnit.Fid from T_TEN_MarketingUnit marUnit " +
						"inner join T_ORG_BaseUnit orgUnit on marUnit.ForgUnitId = orgUnit.Fid " +
						"left outer join T_TEN_MarketingUnitMember muMember on marUnit.Fid = muMember.FHeadID " +
						"where (muMember.FMemberID = '"+currSaleMan.getId().toString()+"' and orgUnit.FlongNumber like '"+orgUnit.getLongNumber()+"%')  ";
			  muIdSql += controlMuSql;
		String retSql = "select FSellProjectID from T_TEN_MarketingUnitSellProject where fHeadId in ("+muIdSql+")";
		return retSql;
	}
	
	
	
	private String getIdStrFromSet(Set idSet) {
		String idStr = "'null'";
		if(idSet!=null) {
			Iterator iter = idSet.iterator();
			while(iter.hasNext()) {
				idStr += ",'" + (String)iter.next()+"'";
			}
		}
		return idStr;
	}
	

	/**
	 * 根据客户名称和电话在自己的客户里查找
	 * 客户范围是自己有权限查看的，而且必须和名称匹配 (名称不能为空，电话可以为空)
	 */
	protected Set _getCustomerIdSetByNameAndPhone(Context ctx, UserInfo saleMan , String customerName, String customerPhone) throws BOSException, EASBizException {
		Set customerIdSet = new HashSet();
		if(customerName==null || customerName.trim().equals("")) return customerIdSet;			

		//有权访问的销售顾问的客户 + 共享给这些销售顾问的客户 
		String los = LocaleUtils.getLocaleString(ctx.getLocale());
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("select fid from T_SHE_FDCCustomer where (fSalesmanId in ("+this._getPermitSaleManIdSql(ctx,saleMan)+") ");
		sqlBuff.append("or fid in ("+getPermitShareCustomerIdStr(ctx,saleMan)+") ) ");
		sqlBuff.append("and fname_"+los+" = '"+ customerName.trim() +"'  ");
		sqlBuff.append(" and fphone like '%"+customerPhone.trim()+"%' ");
    	try {
	        IRowSet rs = DbUtil.executeQuery(ctx , sqlBuff.toString());
	        while (rs.next()) {
	        	customerIdSet.add(rs.getString("fid"));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		return customerIdSet;
	}

	/**
	 * 作为负责任所在的营销单元集合(当前所在组织下的)
	 */
	protected MarketingUnitCollection _getMarketUnitCollByDutySaleMan(Context ctx, UserInfo saleMan) throws BOSException, EASBizException {
		String idComStr = "";
		try{
			OrgUnitInfo orgUnit = getCurrentOrgUnit(ctx);  
			Set sonCompanyIds = this.getLeafCompanyIdSet(ctx,orgUnit);
			idComStr = this.getIdStrFromSet(sonCompanyIds);
		}catch(SQLException e){
			throw new BOSException("查询子公司异常！");
		}
			
		MarketingUnitCollection muColl = new MarketingUnitCollection();
		MarketingUnitMemberCollection muMemberColl = MarketingUnitMemberFactory.getLocalInstance(ctx).getMarketingUnitMemberCollection(
				"select head.id,head.name,head.number where head.orgUnit.id in ("+idComStr+") and member.id = '"+saleMan.getId().toString()+"' and isDuty =1 ");
		for(int i=0;i<muMemberColl.size();i++) {
			muColl.add(muMemberColl.get(i).getHead());
		}
		
		return muColl;
	}

	/**
	 * 作为成员任所在的营销单元集合(当前所在组织下的)
	 */
	protected MarketingUnitCollection _getMarketUnitCollByMemberSaleMan(Context ctx, UserInfo saleMan) throws BOSException, EASBizException {
		String idComStr = "";
		try{
			OrgUnitInfo orgUnit = getCurrentOrgUnit(ctx);  
			Set sonCompanyIds = this.getLeafCompanyIdSet(ctx,orgUnit);
			idComStr = this.getIdStrFromSet(sonCompanyIds);
		}catch(SQLException e){
			throw new BOSException("查询子公司异常！");
		}
			
		MarketingUnitCollection muColl = new MarketingUnitCollection();
		MarketingUnitMemberCollection muMemberColl = MarketingUnitMemberFactory.getLocalInstance(ctx).getMarketingUnitMemberCollection(
				"select head.id,head.name,head.number where head.orgUnit.id in ("+idComStr+") and member.id = '"+saleMan.getId().toString()+"' and isDuty =0 ");
		for(int i=0;i<muMemberColl.size();i++) {
			muColl.add(muMemberColl.get(i).getHead());
		}
		
		return muColl;
	}

	
	protected Set _getMarketUnitIdSetBySaleMan(Context ctx, UserInfo saleMan) throws BOSException, EASBizException {
		OrgUnitInfo saleOrgUnit = getCurrentOrgUnit(ctx);  
			
		MarketingUnitMemberCollection muMemberColl = MarketingUnitMemberFactory.getLocalInstance(ctx).getMarketingUnitMemberCollection(
				"select head.id where (head.orgUnit.number = '"+ saleOrgUnit.getNumber() +"' or head.orgUnit.longNumber like '"+saleOrgUnit.getLongNumber()+"!%') " +
						"and member.id = '"+saleMan.getId().toString()+"' ");
		Set idSet = new HashSet();
		for(int i=0;i<muMemberColl.size();i++)
			idSet.add(muMemberColl.get(i).getHead().getId().toString());
		return idSet;
	}

	
	
	/**
	 * 指定的ouId集合下的所有营销单元
	 */
	protected MarketingUnitCollection _getMarketUnitCollByOUIdSet(Context ctx, Set ouIdSet) throws BOSException, EASBizException {
		MarketingUnitCollection muColl = new MarketingUnitCollection();
		if(ouIdSet==null || ouIdSet.size()==0) return muColl;
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",ouIdSet,CompareType.INCLUDE));
		view.setFilter(filter);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("*"));
		view.setSelector(selector);
		return this.getMarketingUnitCollection(ctx,view);
	}

	


	/**
	 * 允许查看的客户id查询SQL
	 */
	protected String _getPermitFdcCustomerIdSql(Context ctx, UserInfo saleMan,boolean isInCludeDisEnable) throws BOSException, EASBizException {
		return this._getPermitFdcCustomerIdSql(ctx,saleMan,isInCludeDisEnable,true);	
	}

	/**
	 * 允许查看的客户id查询SQL(区分是否包含共享)	
	 * 获得所有允许查看的客户id查询SQL(区分是否包含共享)
	 */
	protected String _getPermitFdcCustomerIdSql(Context ctx, UserInfo saleMan,boolean isInCludeDisEnable, boolean isIncludeShared) throws BOSException, EASBizException {
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("select fid from T_SHE_FDCCustomer where (fSalesmanId in ("+this._getPermitSaleManIdSql(ctx,saleMan)+") ");
		if(isIncludeShared)
			sqlBuff.append("or fid in ("+getPermitShareCustomerIdStr(ctx,saleMan)+")");
		sqlBuff.append(")");
		if(!isInCludeDisEnable) 
			sqlBuff.append(" and fisEnabled = 1 ");
		return sqlBuff.toString();
	}
	
	
	private String getPermitShareCustomerIdStr(Context ctx,UserInfo userInfo) throws BOSException, EASBizException {
		if(userInfo==null) return "select fheadId from T_SHE_ShareSeller where fid='nullnull'";
		String idSqlString = "select fheadId from T_SHE_ShareSeller where FSellerID = '"+userInfo.getId().toString()+"'";
		String muIdStr = this.getIdStrFromSet(this._getMarketUnitIdSetBySaleMan(ctx,userInfo));
		if(!muIdStr.equals(""))
			idSqlString += " union select fheadId from T_SHE_ShareSeller where FMarketingUnitID in ("+muIdStr+")  ";
		SaleOrgUnitInfo saleUnitInfo = ContextUtil.getCurrentSaleUnit(ctx);
		if(saleUnitInfo!=null && saleUnitInfo.isIsBizUnit()) {
			idSqlString += " union select fheadId from T_SHE_ShareSeller where FOrgUnitID ='"+saleUnitInfo.getId().toString()+"'  ";
		}
		return idSqlString;
	}
	

	

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {		
    	ObjectUuidPK pk = (ObjectUuidPK)super._submit(ctx, model);
    	
    	if(getCountOfMuPlat(ctx)==0) {
    		MarketingUnitCollection muColl = MarketingUnitFactory.getLocalInstance(ctx).getMarketingUnitCollection("select id where level=1");
    		for(int i=0;i<muColl.size();i++){
    			dealForMarketUnitPlat(ctx,new ObjectUuidPK(muColl.get(i).getId()));
    		}
    	}else{
    		dealForMarketUnitPlat(ctx,pk);
    	}
    	
    	return pk;    	
	}	

    protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
    		EASBizException {    
    	super._delete(ctx, pk);
    	
    	MarketUnitPlatFactory.getLocalInstance(ctx).delete(" where marketUnit = '"+pk.toString()+"'");//全部删除
    }    

    private int getCountOfMuPlat(Context ctx) throws BOSException{
    	int countNum = 0;
		try {
			IRowSet	rs = DbUtil.executeQuery(ctx,"select count(*) from t_ten_marketUnitPlat");
			if(rs.next()) countNum = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return countNum; 
    }  
    
    //营销单元变动时（修改或删除）的处理 ： 把当前营销单元节点的信息保存到营销平视图中去
    //如果新增子营销单元，只根据新增的单元更新权限，那么父营销单元的权限未更新，因而要跟进新增单元的顶级营销单元才对
    private void dealForMarketUnitPlat(Context ctx,IObjectPK pk) throws BOSException, EASBizException {
    	
    	EntityViewInfo view = new EntityViewInfo();
    	SelectorItemCollection selector = new SelectorItemCollection();
    	selector.add(new SelectorItemInfo("id"));
    	selector.add(new SelectorItemInfo("number"));
    	selector.add(new SelectorItemInfo("longNumber"));
    	selector.add(new SelectorItemInfo("orgUnit"));
    	selector.add(new SelectorItemInfo("member.isDuty"));
    	selector.add(new SelectorItemInfo("member.member.id"));
    	view.setSelector(selector);
    	
		MarketingUnitInfo muInfo = MarketingUnitFactory.getLocalInstance(ctx).getMarketingUnitInfo(pk,selector);
		String allMuIds[] = muInfo.getLongNumber().split("!");
		String allMuIdStr = "";
		for(int i=0;i<allMuIds.length;i++)
			allMuIdStr += ",'" + allMuIds[i] + "'";
		MarketUnitPlatFactory.getLocalInstance(ctx).delete(" where marketUnit.number in ("+allMuIdStr.replaceFirst(",", "")+")");//全部删除 ，包含当前的以及上级的
		
		for(int iii=0;iii<allMuIds.length;iii++) { 
			MarketingUnitInfo muTopInfo = MarketingUnitFactory.getLocalInstance(ctx).getMarketingUnitInfo("where number = '"+allMuIds[iii]+"' ");  
			if(muTopInfo!=null) {
				CoreBaseCollection muPlatColl = new CoreBaseCollection();
	    		MarketingUnitMemberCollection memColl = muTopInfo.getMember();
	    		for(int i=0;i<memColl.size();i++) { 
	    			MarketingUnitMemberInfo memInfo = memColl.get(i);
	    			addToMarketUnitPlatColl(muPlatColl,memInfo.getMember(),memInfo.getMember(),muTopInfo,muTopInfo.getOrgUnit());
	    			if(memInfo.isIsDuty()) { //作为负责人
	    				MarketingUnitMemberCollection muMemberColl = MarketingUnitMemberFactory.getLocalInstance(ctx).getMarketingUnitMemberCollection(
	    							"select member.id where (head.number='"+muTopInfo.getNumber()+"' and isDuty =0 ) or head.longNumber like '"+muTopInfo.getLongNumber()+"!%') ");
	    				for(int j=0;j<muMemberColl.size();j++) {
	    					addToMarketUnitPlatColl(muPlatColl,memInfo.getMember(),muMemberColl.get(j).getMember(),muTopInfo,muTopInfo.getOrgUnit());
	    				}    				
	    			}
	    		}
	    		MarketUnitPlatFactory.getLocalInstance(ctx).addnew(muPlatColl);
			}
		}

    }
    
	
    private void addToMarketUnitPlatColl(CoreBaseCollection muPlatColl,UserInfo dutyInfo,UserInfo memInfo,
    		MarketingUnitInfo muInfo,FullOrgUnitInfo orgInfo) {
    	MarketUnitPlatInfo marPlat = new MarketUnitPlatInfo();
    	marPlat.setDutyPerson(dutyInfo);
    	marPlat.setMember(memInfo);
    	marPlat.setMarketUnit(muInfo);
    	marPlat.setOrgUnit(orgInfo);
    	muPlatColl.add(marPlat);
    }
    
    
    
    
    /**
     *isMuPerm=true 当前人的营销全下下看到的项目节点 包括共享给其它组织的 (多个相同的项目分别隶属于不同的组织下)
     *isMuPerm=false 跟营销权限去无关
     * SellProject 、SellOrder、Subarea、Building、BuildingUnit 可为空，为空代表到项目
     */
	protected ArrayList _getMuSellProTreeNodes(Context ctx, String detailNodeType, boolean isMuPerm) throws BOSException, EASBizException {
		ArrayList muSellProNodes = new ArrayList();
		
		

		//项目节点
		Map sellProNodeMap = new HashMap();	 //<id <MutableTreeNode>>  Orign原始的，Share共享的项目；此映射只存储原始的项目
		List shareSPNodeList = new ArrayList();		//属于共享的项目节点  ,为了解决共享的项目下挂与原始项目有相同子节点的问题
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		if(isMuPerm) {
			UserInfo currSaleMan = ContextUtil.getCurrentUserInfo(ctx);
			builder.appendSql("select * from (");
			builder.appendSql("select fid,fname_l2,fnumber,forgUnitid,fisForSHE,fisForTen,fisForPPM,'Orign' Soruce from t_she_sellProject " );
			builder.appendSql(" union ");
			builder.appendSql("select sellPro.fid,sellPro.fname_l2,sellPro.fnumber,shareOrg.forgUnitid,sellPro.fisForSHE,sellPro.fisForTen,sellPro.fisForPPM,'Share' Soruce from T_SHE_ShareOrgUnit shareOrg ");
			builder.appendSql("inner join t_she_sellProject sellPro on shareOrg.FHeadID = sellPro.fid ");
			builder.appendSql(") AAAA where AAAA.fid in ("+_getPermitSellProjectIdSql(ctx,currSaleMan)+") order by AAAA.fnumber asc " );			
		}else{
			builder.appendSql("select fid,fname_l2,fnumber,forgUnitid,fisForSHE,fisForTen,fisForPPM,'Orign' Soruce from t_she_sellProject " );
		}
		
		logger.debug("营销权限之能看到的权限SQL:"+builder.getSql());

		IRowSet tableSet = builder.executeQuery();
		try{
			while (tableSet.next()) {
				String FID = tableSet.getString("FID");
				String FName = tableSet.getString("fname_l2");
				String FNumber = tableSet.getString("fnumber");
				String ForgUnitId = tableSet.getString("forgUnitid");
				String Soruce = tableSet.getString("Soruce");
				SellProjectInfo thisInfo = new SellProjectInfo();
				thisInfo.setId(BOSUuid.read(FID));
				thisInfo.setName(FName);
				thisInfo.setNumber(FNumber);
				FullOrgUnitInfo orgUnitInfo = new FullOrgUnitInfo();
				orgUnitInfo.setId(BOSUuid.read(ForgUnitId));
				thisInfo.setOrgUnit(orgUnitInfo);
				thisInfo.setIsForSHE(tableSet.getBoolean("fisForSHE"));
				thisInfo.setIsForTen(tableSet.getBoolean("fisForTen"));
				thisInfo.setIsForPPM(tableSet.getBoolean("fisForPPM"));
				DefaultKingdeeTreeNode newNode = new DefaultKingdeeTreeNode(thisInfo.getName());
				newNode.setUserObject(thisInfo);
				if(newNode!=null) muSellProNodes.add(newNode);
				if(Soruce.equals("Share")) shareSPNodeList.add(newNode);
				
				if(!sellProNodeMap.containsKey(FID) || Soruce.equals("Orign"))   //不同组织的多个相同项目下挂节点的问题比较复杂，因此只处理原组织下的项目的情况
					sellProNodeMap.put(FID, newNode);						
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		if(detailNodeType==null || detailNodeType.trim().length()==0 || detailNodeType.trim().equalsIgnoreCase("SellProject"))
			return muSellProNodes;
		
		String sellProIdStr = getIdStrFromSet(sellProNodeMap.keySet());
		//盘次节点
		if(detailNodeType.trim().equalsIgnoreCase("SellOrder")) {
			SellOrderCollection sellOrders = SellOrderFactory.getLocalInstance(ctx).getSellOrderCollection(
						"select id,name,number,project.id where project.id in ("+ sellProIdStr +") and isEnabled =1 order by number");
			for(int i=0;i<sellOrders.size();i++) {
				SellOrderInfo orderInfo = sellOrders.get(i);
				MutableTreeNode newNode = new DefaultKingdeeTreeNode(orderInfo.getName());
				if(newNode!=null) {
					DefaultKingdeeTreeNode sellProNode = (DefaultKingdeeTreeNode)sellProNodeMap.get(orderInfo.getProject().getId().toString());
					orderInfo.setProject((SellProjectInfo)sellProNode.getUserObject());
					newNode.setUserObject(orderInfo);
					if(sellProNode!=null) sellProNode.add(newNode);
				}
			}		
			dealForTheSPBeforRetrun(shareSPNodeList,sellProNodeMap);
			return muSellProNodes;
		}
		
		//分区
		Map subAreaNodeMap = new HashMap();
		SubareaCollection subAreaColl = SubareaFactory.getLocalInstance(ctx).getSubareaCollection(
						"select id,name,number,sellProject.id where sellProject.id in ("+ sellProIdStr +") order by number");
		for(int i=0;i<subAreaColl.size();i++) {
			SubareaInfo thisInfo = subAreaColl.get(i);
			MutableTreeNode newNode = new DefaultKingdeeTreeNode(thisInfo.getName());
			subAreaNodeMap.put(thisInfo.getId().toString(), newNode);
			if(newNode!=null) {
				DefaultKingdeeTreeNode sellProNode = (DefaultKingdeeTreeNode)sellProNodeMap.get(thisInfo.getSellProject().getId().toString());
				thisInfo.setSellProject((SellProjectInfo)sellProNode.getUserObject());
				newNode.setUserObject(thisInfo);
				if(sellProNode!=null) sellProNode.add(newNode);
			}
		}
		if(detailNodeType.trim().equalsIgnoreCase("Subarea")) {
			dealForTheSPBeforRetrun(shareSPNodeList,sellProNodeMap);
			return muSellProNodes;
		}
		
		//搂栋
		Map buidNodeMap = new HashMap();
		builder.clear();
		builder.appendSql("select fid,fname_l2,fnumber,fsellProjectId,fsubareaId,fUnitCount,fCodingType,FFloorCount from t_she_Building where fsellProjectId in ("+ sellProIdStr + ") order by fnumber"  );
		tableSet = builder.executeQuery();
		try {
			while (tableSet.next()) {
				String FID = tableSet.getString("FID");
				String FName = tableSet.getString("fname_l2");
				String FNumber = tableSet.getString("fnumber");
				String FSellProjectId = tableSet.getString("fsellProjectId");
				String FSubareaId = tableSet.getString("fsubareaId");
				int fUnitCount = tableSet.getInt("fUnitCount");
				String fCodingType = tableSet.getString("fCodingType");	
				int FloorCount = tableSet.getInt("FFloorCount");
				BuildingInfo thisInfo = new BuildingInfo();
				thisInfo.setId(BOSUuid.read(FID));
				thisInfo.setName(FName);
				thisInfo.setNumber(FNumber);
				SellProjectInfo sellProInfo = new SellProjectInfo();
				sellProInfo.setId(BOSUuid.read(FSellProjectId));
				
				thisInfo.setSellProject(sellProInfo);
				if(FSubareaId!=null) {
					SubareaInfo subAreaInfo = new SubareaInfo();
					subAreaInfo.setId(BOSUuid.read(FSubareaId));
					thisInfo.setSubarea(subAreaInfo);
				}
				thisInfo.setUnitCount(fUnitCount);
				thisInfo.setFloorCount(FloorCount);
				thisInfo.setCodingType((CodingTypeEnum)CodingTypeEnum.getEnumMap().get(fCodingType));
				MutableTreeNode newNode = new DefaultKingdeeTreeNode(thisInfo.getName());
				buidNodeMap.put(FID, newNode);
				if(newNode!=null) {
					DefaultKingdeeTreeNode sellProNode = (DefaultKingdeeTreeNode)sellProNodeMap.get(thisInfo.getSellProject().getId().toString());
					thisInfo.setSellProject((SellProjectInfo)sellProNode.getUserObject());
					newNode.setUserObject(thisInfo);
					if(thisInfo.getSubarea()==null) {
						if(sellProNode!=null) sellProNode.add(newNode);
					}else{
						DefaultKingdeeTreeNode subAreaNode = (DefaultKingdeeTreeNode)subAreaNodeMap.get(thisInfo.getSubarea().getId().toString());
						/***
						 * 有些楼栋可能没有分区，需要空值判断
						 */
						if(subAreaNode!=null){
							thisInfo.setSubarea((SubareaInfo)subAreaNode.getUserObject());
							subAreaNode.add(newNode);
						}
					}
				}
			}
		} catch (UuidException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(detailNodeType.trim().equalsIgnoreCase("Building")) {
			dealForTheSPBeforRetrun(shareSPNodeList,sellProNodeMap);
			return muSellProNodes;
		}

		//单元
		Map buidUnitMap = new HashMap();
		BuildingUnitCollection buildUnitColl = BuildingUnitFactory.getLocalInstance(ctx).getBuildingUnitCollection(
					"select id,name,number,seq,building.id where building.id in ("+ getIdStrFromSet(buidNodeMap.keySet()) +") order by seq");
		for(int i=0;i<buildUnitColl.size();i++) {
			BuildingUnitInfo thisInfo = buildUnitColl.get(i);
			MutableTreeNode newNode = new DefaultKingdeeTreeNode(thisInfo.getName());
			//Add by zhiyuan_tang 2010/08/28  获取单元节点集合
			buidUnitMap.put(thisInfo.getId().toString(), newNode);
			if(newNode!=null) {
				DefaultKingdeeTreeNode buildNode = (DefaultKingdeeTreeNode)buidNodeMap.get(thisInfo.getBuilding().getId().toString());
				thisInfo.setBuilding((BuildingInfo)buildNode.getUserObject());
				newNode.setUserObject(thisInfo);
				if(buildNode!=null) buildNode.add(newNode);
			}
		}		
		if(detailNodeType.trim().equalsIgnoreCase("BuildingUnit")) {
			dealForTheSPBeforRetrun(shareSPNodeList,sellProNodeMap);
			return muSellProNodes;
		}
		
		//ADD by zhiyuan_tang 2010/08/28   构建房间树  Start
		//房间
//		Map roomMap = new HashMap();
		RoomCollection roomColl = null;
		//用楼栋来进行筛选
		roomColl = RoomFactory.getLocalInstance(ctx).getRoomCollection(
				"select id,roomPropNo,name,number,buildUnit.id where building.id in ("+ getIdStrFromSet(buidNodeMap.keySet()) +")  order by number");
		for(int i = 0; i < roomColl.size(); i++) {
			RoomInfo thisInfo = roomColl.get(i);
			MutableTreeNode newNode = new DefaultKingdeeTreeNode(thisInfo.getRoomPropNo());
			if(newNode != null) {
				//设置对应的楼栋
				DefaultKingdeeTreeNode buildingNode = (DefaultKingdeeTreeNode)buidNodeMap.get(thisInfo.getBuilding().getId().toString());
				thisInfo.setBuilding((BuildingInfo)buildingNode.getUserObject());
				newNode.setUserObject(thisInfo);
				if(thisInfo.getBuildUnit() == null) {
					//如果没有单元，则将节点做为楼栋的子节点
					buildingNode.add(newNode);
				} else {
					//如果有单元，则将节点做为单元的子节点
					DefaultKingdeeTreeNode buidingUnitNode = (DefaultKingdeeTreeNode)buidUnitMap.get(thisInfo.getBuildUnit().getId().toString());
					//有些房间可能没有单元，添加NULL值判断
					if (buidingUnitNode != null) {
						thisInfo.setBuildUnit((BuildingUnitInfo)buidingUnitNode.getUserObject());
						buidingUnitNode.add(newNode);
					}
				}
			}
		}
		
		if(detailNodeType.trim().equalsIgnoreCase("Room")) {
			dealForTheSPBeforRetrun(shareSPNodeList,sellProNodeMap);
			return muSellProNodes;
		}
		//ADD by zhiyuan_tang 2010/08/28   构建房间树  End

		dealForTheSPBeforRetrun(shareSPNodeList,sellProNodeMap);
		return muSellProNodes;
	}


	private void dealForTheSPBeforRetrun(List shareSPNodeList,Map sellProNodeMap){
		for(int i=0;i<shareSPNodeList.size();i++) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)shareSPNodeList.get(i);
			SellProjectInfo thisSpInfo = (SellProjectInfo)thisNode.getUserObject();
			DefaultKingdeeTreeNode orignSpNode = (DefaultKingdeeTreeNode)sellProNodeMap.get(thisSpInfo.getId().toString());
			if(orignSpNode!=null && !orignSpNode.isLeaf()) {
				cloneTree(thisNode,orignSpNode);
			}
		}
	}


	/**
	 * 克隆所选树节点，包含子节点
	 */
	private void cloneTree(DefaultKingdeeTreeNode newNode,	DefaultKingdeeTreeNode oriNode) {
		for (int i = 0; i < oriNode.getChildCount(); i++) {
			DefaultKingdeeTreeNode oriChild = (DefaultKingdeeTreeNode) oriNode.getChildAt(i);
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) oriChild.clone();
			newNode.add(child);
			cloneTree(child, oriChild);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

    
    
}