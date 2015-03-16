package com.kingdee.eas.fdc.invite.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.AppraiseResultEntryFactory;
import com.kingdee.eas.fdc.invite.AppraiseResultEntryInfo;
import com.kingdee.eas.fdc.invite.EffectivenessEnum;
import com.kingdee.eas.fdc.invite.InviteFixCollection;
import com.kingdee.eas.fdc.invite.InviteFixFactory;
import com.kingdee.eas.fdc.invite.InviteFixHeadCollection;
import com.kingdee.eas.fdc.invite.InviteFixHeadFactory;
import com.kingdee.eas.fdc.invite.InviteFixInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class TenderAccepterResultControllerBean extends AbstractTenderAccepterResultControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.TenderAccepterResultControllerBean");

    public HashMap getFixMap(Context ctx, BOSUuid inviteProjectId) throws BOSException, EASBizException {
    	return this._getFixMap(ctx, inviteProjectId);
    }
    
	@Override
	protected HashMap _getFixMap(Context ctx, BOSUuid inviteProjectId) throws BOSException, EASBizException {
		HashMap<String,InviteFixInfo > map = new HashMap<String, InviteFixInfo>();
		
		EntityViewInfo fixView = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("head.inviteProject.id");
		sic.add("*");
		sic.add("head.state");
		fixView.setSelector(sic);
		
		FilterInfo fixFilter = new FilterInfo();
		fixView.setFilter(fixFilter);
		fixFilter.getFilterItems().add(new FilterItemInfo("head.inviteProject.id",inviteProjectId.toString()));
		fixFilter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.AUDITTED_VALUE));
		
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo time = new SorterItemInfo("head.auditTime");
		time.setSortType(SortType.DESCEND);
		sorter.add(time);
		fixView.setSorter(sorter);
		
		InviteFixCollection fixCol=InviteFixFactory.getLocalInstance(ctx).getInviteFixCollection(fixView);
		for(int i=0; i<fixCol.size(); i++) {
			InviteFixInfo fix = fixCol.get(i);
			BOSUuid sid = fix.getSupplier().getId();
			if( sid !=null && map.get(sid.toString()) ==null ) {
				map.put(sid.toString(), fix);
			}
			
		}
		
		// TODO Auto-generated method stub
		return map;
	}
    
    

}