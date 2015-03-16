package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InviteTimeConsultCollection;
import com.kingdee.eas.fdc.invite.InviteTimeConsultFactory;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordCollection;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordFactory;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.util.NumericExceptionSubItem;

public class SupplierInviteRecordControllerBean extends AbstractSupplierInviteRecordControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.SupplierInviteRecordControllerBean");
    
    protected IObjectPK _submit(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	SupplierInviteRecordInfo info=(SupplierInviteRecordInfo)model;
    	int times=this.getTimes(ctx, info,false);
    	info.setTimes(times);
    	IObjectPK pk = super._submit(ctx, info);
    	return pk;
    }

	protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		SupplierInviteRecordInfo info=(SupplierInviteRecordInfo)model;
    	int times=this.getTimes(ctx, info,false);
    	info.setTimes(times);
		super._save(ctx, pk, info);
	}

	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		SupplierInviteRecordInfo info=(SupplierInviteRecordInfo)model;
    	int times=this.getTimes(ctx, info,false);
    	info.setTimes(times);
		return super._save(ctx, info);
	}

	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		SupplierInviteRecordInfo info=(SupplierInviteRecordInfo)model;
    	int times=this.getTimes(ctx, info,false);
    	info.setTimes(times);
		super._submit(ctx, pk, info);
	}
    protected int getTimes(Context ctx,SupplierInviteRecordInfo info,boolean isDelete) throws BOSException, EASBizException{
		if(info.getInviteProject()!=null&&info.getSupplier()!=null){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("inviteProject.id", info.getInviteProject().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("supplier.id", info.getSupplier().getId().toString()));
			if(info.getId()!=null){
				filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString(),CompareType.NOTEQUALS));
			}
			if(!isDelete&&info.getCreateTime()!=null){
				filter.getFilterItems().add(new FilterItemInfo("createTime", info.getCreateTime(),CompareType.LESS));
			}
			view.setFilter(filter);
			SupplierInviteRecordCollection col=SupplierInviteRecordFactory.getLocalInstance(ctx).getSupplierInviteRecordCollection(view);
			if(!info.getInviteProject().isPaperIsComplete()){
				return col.size()+1;
			}else{
				view = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("inviteProject.id", info.getInviteProject().getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
				view.setFilter(filter);
				view.getSelector().add("times");
				InviteTimeConsultCollection timesCol=InviteTimeConsultFactory.getLocalInstance(ctx).getInviteTimeConsultCollection(view);
				int times=0;
				for(int i=0;i<timesCol.size();i++){
					if(timesCol.get(i).getTimes()>times){
						times=timesCol.get(i).getTimes();
					}
				}
				if(times+2<col.size()+1){
					throw new EASBizException(new NumericExceptionSubItem("100","最大回标次数为"+(times+2)+",请增加回标次数请示！"));
				}
			}
			return col.size()+1;
		}else{
			return 1;
		}
    }
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
    	SupplierInviteRecordInfo info=this.getSupplierInviteRecordInfo(ctx, pk);
    	int times=this.getTimes(ctx, info,true);
    	if(times!=info.getTimes()){
    		throw new EASBizException(new NumericExceptionSubItem("100","请先删除最新回标次数投标记录！"));
    	}
    	super._delete(ctx, pk);
    }
}