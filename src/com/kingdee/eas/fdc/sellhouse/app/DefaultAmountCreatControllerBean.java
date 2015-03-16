package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatEntryFactory;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatEntryInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerFactory;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.util.NumericExceptionSubItem;

public class DefaultAmountCreatControllerBean extends AbstractDefaultAmountCreatControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.DefaultAmountCreatControllerBean");
    
    protected void checkBill(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    }
    
    
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		return submitAction(ctx,model);
	}
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		submitAction(ctx,model);
		super._submit(ctx, pk, model);
	}
	
	private IObjectPK submitAction(Context ctx, IObjectValue model) throws EASBizException, BOSException{
		IObjectPK pk=null;
		Set isAddValue=new HashSet();
		DefaultAmountCreatInfo info=(DefaultAmountCreatInfo)model;
		info.setDefCalDate(FDCCommonServerHelper.getServerTime());
		for(int i=0;i<info.getParent().size();i++){
			DefaultAmountCreatEntryInfo entry=info.getParent().get(i);
			if(entry.getDefaultAmountMangerEntry()!=null){
				if(!isAddValue.contains(entry.getDefaultAmountMangerEntry().getHead().getId())){
					entry.getDefaultAmountMangerEntry().getHead().setDefCalDate(info.getDefCalDate());
					BigDecimal subDeAmounts=FDCHelper.ZERO;
					BigDecimal refDeAmount=FDCHelper.ZERO;
					for(int j=0;j<entry.getDefaultAmountMangerEntry().getHead().getEntry().size();j++){
						subDeAmounts=subDeAmounts.add(entry.getDefaultAmountMangerEntry().getHead().getEntry().get(j).getSubDeAmount());
						refDeAmount=refDeAmount.add(entry.getDefaultAmountMangerEntry().getHead().getEntry().get(j).getReferAmount());
					}
					entry.getDefaultAmountMangerEntry().getHead().setSubDeAmount(subDeAmounts);
					entry.getDefaultAmountMangerEntry().getHead().setRefDeAmount(refDeAmount);
					entry.getDefaultAmountMangerEntry().getHead().setCarryAmount(refDeAmount.subtract(subDeAmounts));
					DefaultAmountMangerFactory.getLocalInstance(ctx).submit(entry.getDefaultAmountMangerEntry().getHead());
					isAddValue.add(entry.getDefaultAmountMangerEntry().getHead().getId());
				}
			}
			if(entry.getTranBusinessOverView()!=null){
				entry.getTranBusinessOverView().setAppDate(info.getDefCalDate());
//				TranBusinessOverViewFactory.getLocalInstance(ctx).submit(entry.getTranBusinessOverView());	
			}
			if(entry.getSignPayListEntry()!=null){
				entry.getSignPayListEntry().setAppDate(info.getDefCalDate());
//				SignPayListEntryFactory.getLocalInstance(ctx).submit(entry.getSignPayListEntry());
			}
		}
		pk=super._submit(ctx, model);
		return pk;
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		DefaultAmountCreatInfo info=(DefaultAmountCreatInfo)getValue(ctx, pk);
		for(int i=0;i<info.getParent().size();i++){
			DefaultAmountCreatEntryInfo entry=info.getParent().get(i);
			if(entry.getTranBusinessOverView()!=null&&entry.getTranBusinessOverView().getMoneyDefine()!=null){
				TranBusinessOverViewInfo tran=TranBusinessOverViewFactory.getLocalInstance(ctx).getTranBusinessOverViewInfo(new ObjectUuidPK(entry.getTranBusinessOverView().getId()));
				if(tran.getActRevAmount().compareTo(FDCHelper.ZERO)>0){
					throw new EASBizException(new NumericExceptionSubItem("100","违约金已收款，不能进行删除操作！"));
				}
				TranBusinessOverViewFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(entry.getTranBusinessOverView().getId()));	
			}
			if(entry.getSignPayListEntry()!=null){
				SignPayListEntryFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(entry.getSignPayListEntry().getId()));
			}
			if(entry.getDefaultAmountMangerEntry()!=null){
				if(DefaultAmountMangerEntryFactory.getLocalInstance(ctx).exists(new ObjectUuidPK(entry.getDefaultAmountMangerEntry().getId()))){
					DefaultAmountMangerEntryInfo damentry=DefaultAmountMangerEntryFactory.getLocalInstance(ctx).getDefaultAmountMangerEntryInfo(new ObjectUuidPK(entry.getDefaultAmountMangerEntry().getId()));
					
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("head.defCalDate", info.getDefCalDate(),CompareType.LESS));
					filter.getFilterItems().add(new FilterItemInfo("tranOverView", damentry.getTranOverView().getId()));
					if(DefaultAmountMangerEntryFactory.getLocalInstance(ctx).exists(filter)){
						throw new EASBizException(new NumericExceptionSubItem("100","不是最新违约金生成记录，不能进行删除操作！"));
					}
					
					DefaultAmountMangerFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(damentry.getHead().getId()));
				}
			}
		}
		super._delete(ctx, pk);
	}
}