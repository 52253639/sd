package com.kingdee.eas.fdc.aimcost.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK; //import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean; //import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import java.math.BigDecimal;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.Result;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.aimcost.CostSplitCollection;
import com.kingdee.eas.fdc.aimcost.CostSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostSplitFactory;
import com.kingdee.eas.framework.app.CoreBaseControllerBean;
import com.kingdee.eas.fdc.aimcost.CostSplitInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class CostSplitControllerBean extends AbstractCostSplitControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.aimcost.app.CostSplitControllerBean");

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
	}

	protected void _audit(Context ctx, List idList) throws BOSException, EASBizException {
	}

	protected void _unAudit(Context ctx, List idList) throws BOSException, EASBizException {
	}

	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		return super._save(ctx, model);
	}

	protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		super._save(ctx, pk, model);
	}

	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		if(true) return super._addnew(ctx, model);
		//ORMap还快一些,白改了 
		CostSplitInfo info = (CostSplitInfo) model;
		if (info.getId() == null) {
			info.setId(BOSUuid.create(info.getBOSType()));
		}
		String headSql = "INSERT INTO T_AIM_CostSplit (FSplitBillId, FCostBillID, FIsInvalid, FCostBillType, FID) VALUES (?, ?, ?, ?, ?)";
		String entrySql = "INSERT INTO T_AIM_CostSplitEntry (FAmount, FProdAmount, FCostAccountID, FParentID, FIsProduct, FID) VALUES (?, ?, ?, ?, ?, ?)";
		List headList = new ArrayList();
		List entryList = new ArrayList();
		String FSplitBillId=info.getSplitBillId()!=null?info.getSplitBillId().toString():null; 
		String FCostBillID=info.getCostBillId()!=null?info.getCostBillId().toString():null;
		Boolean FIsInvalid=Boolean.valueOf(info.isIsInvalid()); 
		String FCostBillType=info.getCostBillType()!=null?info.getCostBillType().getValue():null; 
		String FID=info.getId().toString();	
		headList.add(Arrays.asList(new Object[]{
				FSplitBillId, 
				FCostBillID, 
				FIsInvalid, 
				FCostBillType, 
				FID	
		}));
		for(Iterator iter=info.getEntrys().iterator();iter.hasNext();){
			CostSplitEntryInfo entry=(CostSplitEntryInfo)iter.next();
			BigDecimal FAmount=entry.getAmount();
			BigDecimal FProdAmount=entry.getProdAmount();
			String FCostAccountID=entry.getCostAccount()==null?null:(entry.getCostAccount().getId()==null?null:entry.getCostAccount().getId().toString());
			String FParentID=FID;
			Boolean FIsProduct=Boolean.valueOf(entry.isIsProduct());
			String FEntryId=entry.getId()!=null?entry.getId().toString():BOSUuid.create(entry.getBOSType()).toString();
			entryList.add(Arrays.asList(new Object[]{
					FAmount, FProdAmount, FCostAccountID, FParentID, FIsProduct, FEntryId
			}));
		}
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.executeBatch(headSql, headList);
		builder.executeBatch(entrySql, entryList);
		return new ObjectUuidPK(info.getId().toString());
	}
	protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		if(pk==null){
			throw new BOSException("pk cannt be null");
		}
		CostSplitInfo info=(CostSplitInfo)model;
		info.setId(BOSUuid.read(pk.toString()));
		_addnew(ctx, model);
	}
	
	//用于批量添加
	protected Result _save(Context ctx, IObjectCollection colls) throws BOSException, EASBizException {
		if(colls==null||colls.size()==0){
			return null;
		}
		String headSql = "INSERT INTO T_AIM_CostSplit (FSplitBillId, FCostBillID, FIsInvalid, FCostBillType, FID) VALUES (?, ?, ?, ?, ?)";
		String entrySql = "INSERT INTO T_AIM_CostSplitEntry (FAmount, FProdAmount, FCostAccountID, FParentID, FIsProduct, FID) VALUES (?, ?, ?, ?, ?, ?)";
		List headList = new ArrayList();
		List entryList = new ArrayList();
		Set deleteSet=new HashSet();
		for(Iterator headIter=colls.iterator();headIter.hasNext();){
			CostSplitInfo info = (CostSplitInfo) headIter.next();
			if (info.getId() == null) {
				info.setId(BOSUuid.create(info.getBOSType()));
			}else{
				deleteSet.add(info.getId().toString());
			}
			String FSplitBillId=info.getSplitBillId()!=null?info.getSplitBillId().toString():null; 
			String FCostBillID=info.getCostBillId()!=null?info.getCostBillId().toString():null;
			Boolean FIsInvalid=Boolean.valueOf(info.isIsInvalid()); 
			String FCostBillType=info.getCostBillType()!=null?info.getCostBillType().getValue():null; 
			String FID=info.getId().toString();	
			headList.add(Arrays.asList(new Object[]{
					FSplitBillId, 
					FCostBillID, 
					FIsInvalid, 
					FCostBillType, 
					FID	
			}));
			for(Iterator iter=info.getEntrys().iterator();iter.hasNext();){
				CostSplitEntryInfo entry=(CostSplitEntryInfo)iter.next();
				BigDecimal FAmount=entry.getAmount();
				BigDecimal FProdAmount=entry.getProdAmount();
				String FCostAccountID=entry.getCostAccount()==null?null:(entry.getCostAccount().getId()==null?null:entry.getCostAccount().getId().toString());
				String FParentID=FID;
				Boolean FIsProduct=Boolean.valueOf(entry.isIsProduct());
				String FEntryId=entry.getId()!=null?entry.getId().toString():BOSUuid.create(entry.getBOSType()).toString();
				entryList.add(Arrays.asList(new Object[]{
						FAmount, FProdAmount, FCostAccountID, FParentID, FIsProduct, FEntryId
				}));
			}
		}
		if(deleteSet.size()>0){
			FilterInfo filter=new  FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",deleteSet,CompareType.INCLUDE));
			CostSplitFactory.getLocalInstance(ctx).delete(filter);
		}
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.executeBatch(headSql, headList);
		builder.executeBatch(entrySql, entryList);
		return null;
	}
}