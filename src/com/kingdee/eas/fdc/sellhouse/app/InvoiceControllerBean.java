package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.ChequeFactory;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;
import com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum;
import com.kingdee.eas.fdc.sellhouse.InvoiceInfo;
import com.kingdee.eas.framework.Result;

public class InvoiceControllerBean extends AbstractInvoiceControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.InvoiceControllerBean");
    
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
    	InvoiceInfo invoice = this.getInvoiceInfo(ctx, pk);
    	if(invoice.getCheque() != null){
    		ChequeInfo cheque = ChequeFactory.getLocalInstance(ctx).getChequeInfo(new ObjectUuidPK(invoice.getCheque().getId()));
    		if(cheque != null){
        		cheque.setStatus(ChequeStatusEnum.Cancelled);
        		SelectorItemCollection sels = new SelectorItemCollection();
        		sels.add("status");
    			ChequeFactory.getLocalInstance(ctx).updatePartial(cheque, sels);
        	}	
    	}
    	super._delete(ctx, pk);
    }
    protected IObjectPK _submit(Context ctx, IObjectValue model)
    		throws BOSException, EASBizException {
    	return super._submit(ctx, model);
    }
    protected Result _submit(Context ctx, IObjectCollection colls)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	return super._submit(ctx, colls);
    }
    protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
    		throws BOSException, EASBizException {
    	// TODO Auto-generated method stub
    	super._submit(ctx, pk, model);
    }
    
}