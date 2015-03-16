package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.sellhouse.CluesManageFactory;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.CluesStatusEnum;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SharePropertyCollection;
import com.kingdee.eas.fdc.sellhouse.SharePropertyFactory;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.ShareSellProjectFactory;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.util.app.DbUtil;

public class CommerceChanceControllerBean extends AbstractCommerceChanceControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.CommerceChanceControllerBean");

	protected boolean isUseName() {
		return false;
	}

	/**
	 * add by youzhen,20110720
	 * 线索客户转商机后，线索客户的状态变为 ‘转商机’
	 */
	protected IObjectPK _submit(Context ctx, IObjectValue model)
	throws BOSException, EASBizException {
		CommerceChanceInfo  info = (CommerceChanceInfo) model;
		
		if(info.getId()==null){
			info.setId(BOSUuid.create(info.getBOSType()));
		}
		
		if(info != null && info.getCluesCustomer() != null){
		    CluesManageInfo cluesInfo = info.getCluesCustomer();
		    cluesInfo.setCluesStatus(CluesStatusEnum.COMMERCECHANCE);
		    //wyh 无转商机，注销
			//CluesManageFactory.getLocalInstance(ctx).submit(cluesInfo);
			
			//变成真实客户
			SHECustomerInfo sheCustomerInfo = saveSHECustomer(ctx,cluesInfo,info);
			info.setCustomer(sheCustomerInfo);
		}
		return super._submit(ctx, info);
	}
	
	/**
	 * @author youzhen_qin 
	 * @date 20110720
	 * 将线索客户变成真实客户
	 * 将共享,商机,跟进,问卷转移到该客户下
	 * @param cluesInfo
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private SHECustomerInfo saveSHECustomer(Context ctx,CluesManageInfo cluesInfo,CommerceChanceInfo commerceChanceInfo)throws BOSException, EASBizException {
		if(cluesInfo != null){
		SHECustomerInfo sheCustomerInfo = new SHECustomerInfo();
		sheCustomerInfo = commerceChanceInfo.getCustomer();
			//将置业顾问转移
			SharePropertyCollection shareIds = SharePropertyFactory.getLocalInstance(ctx).getSharePropertyCollection(" select id  where property.id = '"+cluesInfo.getId()+"'");
			if(shareIds != null  && shareIds.size() > 0){
			try {
					String shareSql = "update T_SHE_ShareProperty set fcustomerid=? where fpropertyid=?";
					FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
					sqlBuilder.setPrepareStatementSql(shareSql);
					sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
				
					
					for (int i = 0; i < shareIds.size(); i++) {
						sqlBuilder.addParam(sheCustomerInfo.getId().toString());
						sqlBuilder.addParam(cluesInfo.getId().toString());
						sqlBuilder.addBatch();
					}
					sqlBuilder.executeBatch();
					
				} catch (BOSException ex) {
					logger.error(ex.getMessage() + "更新置业顾问失败!");
					throw new BOSException(ex.getMessage() + "更新置业顾问失败!");
				}
			}
			
			//将共享项目转移
			ShareSellProjectCollection sellProjectIds = ShareSellProjectFactory.getLocalInstance(ctx).getShareSellProjectCollection(" select id  where shareProject.id = '"+cluesInfo.getId()+"'");
			if(sellProjectIds != null  && sellProjectIds.size() > 0){
			try {
				String sellProjectSql = "update T_SHE_ShareSellProject set fcustomerid=? where FShareProjectID=?";
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
				sqlBuilder.setPrepareStatementSql(sellProjectSql);
				sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
				
				
				for (int i = 0; i < sellProjectIds.size(); i++) {
					sqlBuilder.addParam(sheCustomerInfo.getId().toString());
					sqlBuilder.addParam(cluesInfo.getId().toString());
					sqlBuilder.addBatch();
				}
				sqlBuilder.executeBatch();
			
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "更新项目共享失败!");
				throw new BOSException(ex.getMessage() + "更新项目共享失败!");
			}
			}
			
			//将问卷转移
			QuestionPaperAnswerCollection questionIds = QuestionPaperAnswerFactory.getLocalInstance(ctx).getQuestionPaperAnswerCollection(" select id  where cluesManage = '"+cluesInfo.getId()+"'");
			if(questionIds != null  && questionIds.size() > 0){
			try {
				String questionSql = "update T_MAR_QuestionPaperAnswer set fshecustomerid=? where FCluesManageID=?";
				FDCSQLBuilder sqlBuilder1 = new FDCSQLBuilder(ctx);
				sqlBuilder1.setPrepareStatementSql(questionSql);
				sqlBuilder1.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
				
				for (int i = 0; i < questionIds.size(); i++) {
					DbUtil.execute(ctx, questionSql, new Object[] {sheCustomerInfo.getId().toString(),cluesInfo.getId().toString()});
				}
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "更新问卷失败!");
				throw new BOSException(ex.getMessage() + "更新问卷失败!");
			}
			}
			//将跟进转移到客户的商机下
			CommerceChanceTrackCollection trackIds = CommerceChanceTrackFactory.getLocalInstance(ctx).getCommerceChanceTrackCollection(" select id  where clues.id = '"+cluesInfo.getId()+"'");
			if(trackIds != null  && trackIds.size() > 0){
			try {
				String trackSql = "update T_SHE_CommerceChanceTrack set FCommerceChanceID=? where FCluesID=?";
				FDCSQLBuilder sqlBuilder4 = new FDCSQLBuilder(ctx);
				
				sqlBuilder4.setPrepareStatementSql(trackSql);
				sqlBuilder4.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
				
				for (int i = 0; i < trackIds.size(); i++) {
					DbUtil.execute(ctx, trackSql, new Object[] {commerceChanceInfo.getId().toString(),cluesInfo.getId().toString()});
				}
			} catch (BOSException ex) {
				logger.error(ex.getMessage() + "更新跟进失败!");
				throw new BOSException(ex.getMessage() + "更新跟进失败!");
			}
		}
			return sheCustomerInfo;
    		
		}
		return null;
	}
	
	/**
	 * 删除商机时反与线索客户的状态，如果是“转商机”，则变为“转客户”，其他状态则不改变。
	 */
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		CommerceChanceInfo info=(CommerceChanceInfo)getValue(ctx, pk);
		if(info.getCluesCustomer() != null && info.getCluesCustomer().getId() != null){
			CluesManageInfo cluesInfo = info.getCluesCustomer();
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("cluesStatus");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", cluesInfo.getId()));
			view.setSelector(sic);
			view.setFilter(filter);
			CluesManageInfo tempInfo = CluesManageFactory.getLocalInstance(ctx).getCluesManageCollection(view).get(0);
			
			if(CluesStatusEnum.COMMERCECHANCE.equals(tempInfo.getCluesStatus())){
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("cluesStatus");
				cluesInfo.setCluesStatus(CluesStatusEnum.CUSTOMER);
				CluesManageFactory.getLocalInstance(ctx).updatePartial(cluesInfo, selector);
			}
		}
		super._delete(ctx, pk);
	}
    
	protected void _updateToTransaction(Context ctx,String type,String id) {
		SelectorItemCollection selector = new SelectorItemCollection();
		if(type.equals("toSign")){
			selector.add(new SelectorItemInfo("isToSign"));
		}else if(type.equals("toPre")){
			selector.add(new SelectorItemInfo("isToPre"));
		}else if(type.equals("toPur")){
			selector.add(new SelectorItemInfo("isToPur"));
		}
		selector.add(new SelectorItemInfo("status"));
		CommerceChanceInfo model = new CommerceChanceInfo();
		if(type.equals("toSign")){
			model.setIsToSign(true);
		}else if(type.equals("toPre")){
			model.setIsToPre(true);
		}else if(type.equals("toPur")){
			model.setIsToPur(true);
		}
		model.setStatus(CommerceChangeNewStatusEnum.TRANSACTION);
		model.setId(BOSUuid.read(id));
		try {
			CommerceChanceFactory.getLocalInstance(ctx).updatePartial(model, selector);
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("cluesCustomer.id");
			sic.add("cluesCustomer.number");
			sic.add("cluesCustomer.name");
			sic.add("cluesCustomer.cluesStatus");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", id));
			view.setSelector(sic);
			view.setFilter(filter);
			CluesManageInfo tempInfo = CommerceChanceFactory.getLocalInstance(ctx).getCommerceChanceCollection(view).get(0).getCluesCustomer();
			sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("cluesStatus"));
			if(type.equals("toSign")){
				tempInfo.setCluesStatus(CluesStatusEnum.SIGN);
			}else if(type.equals("toPre")){
				tempInfo.setCluesStatus(CluesStatusEnum.PREPURCHASE);
			}else if(type.equals("toPur")){
				tempInfo.setCluesStatus(CluesStatusEnum.PURCHASE);
			}
			CluesManageFactory.getLocalInstance(ctx).updatePartial(tempInfo, sic);
		} catch (EASBizException e) {
			logger.error("转交易失败!"+e.getMessage());
		} catch (BOSException e) {
			logger.error("转交易失败!"+e.getMessage());
		}
		
	}
}