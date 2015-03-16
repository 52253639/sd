package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;

import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.invite.InviteClarifyInfo;
import com.kingdee.eas.fdc.invite.InviteContentFactory;
import com.kingdee.eas.fdc.invite.InviteDocumentsCollection;
import com.kingdee.eas.fdc.invite.InviteDocumentsFactory;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractContentFactory;
import com.kingdee.eas.fdc.invite.InviteDocumentsInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;

public class InviteDocumentsControllerBean extends AbstractInviteDocumentsControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.InviteDocumentsControllerBean");
    
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
    	FilterInfo filter=new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("parent",pk.toString()+"BASE"));
//		InviteContentFactory.getLocalInstance(ctx).delete(filter);
		
		filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent",pk.toString()));
		InviteContentFactory.getLocalInstance(ctx).delete(filter);
		
		ContractContentFactory.getLocalInstance(ctx).delete(filter);
		
		SelectorItemCollection sel = new SelectorItemCollection();
	   	sel.add("agreementID");
		InviteDocumentsInfo info=InviteDocumentsFactory.getLocalInstance(ctx).getInviteDocumentsInfo(pk, sel);
		if(info.getAgreementID()!=null){
			deleteAttachment(ctx,info.getAgreementID());
		}
		deleteAttachment(ctx,pk.toString());
		super._delete(ctx, pk);
	}
    
    protected void deleteAttachment(Context ctx,String id) throws BOSException, EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , id));
		view.setFilter(filter);
		BoAttchAssoCollection col=BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(view);
		if(col.size()>0){
			for(int i=0;i<col.size();i++){
				EntityViewInfo attview=new EntityViewInfo();
				FilterInfo attfilter = new FilterInfo();
				
				attfilter.getFilterItems().add(new FilterItemInfo("attachment.id" , col.get(i).getAttachment().getId().toString()));
				attview.setFilter(attfilter);
				BoAttchAssoCollection attcol=BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(attview);
				if(attcol.size()==1){
					BizobjectFacadeFactory.getLocalInstance(ctx).delTempAttachment(id);
				}else if(attcol.size()>1){
					BoAttchAssoFactory.getLocalInstance(ctx).delete(filter);
				}
			}
		}
	}
}