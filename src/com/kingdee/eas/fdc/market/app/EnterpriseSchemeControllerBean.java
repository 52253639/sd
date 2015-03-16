package com.kingdee.eas.fdc.market.app;

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
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.market.EnterprisePlanEntryCollection;
import com.kingdee.eas.fdc.market.EnterprisePlanEntryFactory;
import com.kingdee.eas.fdc.market.EnterprisePlanEntryInfo;
import com.kingdee.eas.fdc.market.EnterpriseSchemeEntryInfo;
import com.kingdee.eas.fdc.market.EnterpriseSchemeInfo;
import com.kingdee.eas.fdc.market.EnterpriseSellPlanCollection;
import com.kingdee.eas.fdc.market.EnterpriseSellPlanFactory;
import com.kingdee.eas.fdc.market.EnterpriseSellPlanInfo;
import com.kingdee.eas.fdc.market.IsFinishEnum;
import com.kingdee.eas.fdc.market.ThemeEnum;

public class EnterpriseSchemeControllerBean extends AbstractEnterpriseSchemeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.app.EnterpriseSchemeControllerBean");
    protected void checkNameDup(Context ctx, FDCBillInfo billInfo)
    		throws BOSException, EASBizException {
    }
    
    /**
     * 
     * 
     * 3."该企划实施"提交，状态自动反写至”企划计划“，特别关注”已取消“状态
     * 4.如果改主题内事项状态均为“未开始”，则主题状态"未开始"
     * 均为“已完成”，则主题状态“已完成”
     * 均为“已取消”，则主题状态“已取消”
     * “已完成”+“已取消”，则主题状态“已完成”
     * “已取消”+“未开始”，则主题状态“未开始”
     * 
     * */
	protected void updateEnterpriseState(Context ctx,IObjectValue model,boolean isDel) throws EASBizException, BOSException{
		EnterpriseSchemeInfo billInfo = (EnterpriseSchemeInfo) model;
		EnterpriseSellPlanCollection espc = new EnterpriseSellPlanCollection();
		SelectorItemCollection updateSel = new SelectorItemCollection();
		updateSel.add("state");
		updateSel.add("esEntryId");
		
		Set set_zhuti = new HashSet();
		
		
		//更新事项的状态
		for(int i=0;i<billInfo.getEntry().size();i++){
			EnterpriseSchemeEntryInfo entry=billInfo.getEntry().get(i);
			EnterpriseSellPlanInfo sellPlan=EnterpriseSellPlanFactory.getLocalInstance(ctx).getEnterpriseSellPlanInfo(new ObjectUuidPK(entry.getSellPlanID()));

			if(entry.getIsEnd().equals(IsFinishEnum.FINISH)){
				sellPlan.setState(ThemeEnum.Finish);
			}else if(entry.getIsEnd().equals(IsFinishEnum.CANCEL)){
				sellPlan.setState(ThemeEnum.Canceled);
			}else{
				sellPlan.setState(ThemeEnum.UnStarted);
			}
			sellPlan.setEsEntryId(entry.getId());
			EnterpriseSellPlanFactory.getLocalInstance(ctx).updatePartial(sellPlan, updateSel);
			espc.add(sellPlan);
			if(!set_zhuti.contains(sellPlan.getHead().getId())){
				set_zhuti.add(sellPlan.getHead().getId());
			}
		}
		//更新主题的状态
		Iterator it = set_zhuti.iterator();
		while(it.hasNext()){
			int unstrat = 0;
			int finish = 0;
			int cancel = 0;
			int count = 0;
			Object object = it.next();//主题ID
			if(object == null){
				continue;
			}
			for(int i=0;i<espc.size();i++){
				EnterpriseSellPlanInfo sellPlan = espc.get(i);
				if(object.equals(sellPlan.getHead().getId())){
					if(sellPlan.getState().getAlias().equals(IsFinishEnum.UNSTART.getAlias())){
						unstrat++;
					}else if(sellPlan.getState().getAlias().equals(IsFinishEnum.FINISH.getAlias())){
						finish++;
					}else if(sellPlan.getState().getAlias().equals(IsFinishEnum.CANCEL.getAlias())){
						cancel++;
					}
					count++;
				}
			}
			EnterprisePlanEntryInfo epei = new EnterprisePlanEntryInfo();
			epei.setId(BOSUuid.read(object.toString()));
			if(unstrat == count){
				epei.setState(ThemeEnum.UnStarted);
			}else if(finish == count){
				epei.setState(ThemeEnum.Finish);
			}else if(cancel == count){
				epei.setState(ThemeEnum.Canceled);
			}else if(cancel+finish == count){
				epei.setState(ThemeEnum.Finish);
			}else if(cancel+unstrat == count){
				epei.setState(ThemeEnum.UnStarted);
			}else{
				epei.setState(ThemeEnum.Underway);
			}
			EnterprisePlanEntryFactory.getLocalInstance(ctx).updatePartial(epei, updateSel);
		}
	}
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		super._submit(ctx, pk, model);
		updateEnterpriseState(ctx,model,false);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		IObjectPK pk= super._submit(ctx, model);
		updateEnterpriseState(ctx,model,false);
		return pk;
	}
	protected EnterprisePlanEntryCollection getEnterprisePlanEntry(Context ctx,Set sellPlan) throws BOSException{
		EntityViewInfo evInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("palnTheme.id",sellPlan,CompareType.INCLUDE));
		evInfo.setFilter(filter);
		
		EnterprisePlanEntryCollection col=EnterprisePlanEntryFactory.getLocalInstance(ctx).getEnterprisePlanEntryCollection(evInfo);
		
		return col;
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
//		EnterpriseSchemeInfo billInfo = (EnterpriseSchemeInfo) getValue(ctx,pk);
//		updateEnterpriseState(ctx,billInfo,true);
		super._delete(ctx, pk);
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
//		for (int i = 0; i < arrayPK.length; i++) {
//			EnterpriseSchemeInfo billInfo = (EnterpriseSchemeInfo) getValue(ctx,arrayPK[i]);
//			updateEnterpriseState(ctx,billInfo,true);
//		}
		super._delete(ctx, arrayPK);
	}
}