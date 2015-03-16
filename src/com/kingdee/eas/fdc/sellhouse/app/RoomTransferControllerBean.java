package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
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
import java.math.BigDecimal;

import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.GeneralAsstActTypeFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerCollection;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.sellhouse.RoomTransferFactory;
import com.kingdee.eas.fdc.sellhouse.RoomTransferInfo;
import com.kingdee.eas.fdc.sellhouse.RoomTransferCollection;
import com.kingdee.eas.fdc.sellhouse.RoomTransferStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;

public class RoomTransferControllerBean extends AbstractRoomTransferControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomTransferControllerBean");

	protected void _transfer(Context ctx, List value) throws BOSException,EASBizException {
		for(int i=0;i<value.size();i++){
			Map detailValue=(Map) value.get(i);
			Date transferDate=(Date) detailValue.get("transferDate");
			String roomName=(String) detailValue.get("room.name");
			String sysCustomerId=(String) detailValue.get("sysCustomer.id");
			String productTypeId=(String) detailValue.get("productType.id");
			BigDecimal contractTotalAmount=(BigDecimal) detailValue.get("contractTotalAmount");
			BigDecimal buildArea=(BigDecimal) detailValue.get("buildArea");
			String billId=(String) detailValue.get("bill.id");
			String sellProjectId=(String)detailValue.get("sellProject.id");
			RoomTransferInfo info=new RoomTransferInfo();
			info.setBizDate(transferDate);
			if(sysCustomerId!=null){
				CustomerCollection cusCol=CustomerFactory.getLocalInstance(ctx).getCustomerCollection("select * from where id='"+sysCustomerId+"'");
				if(cusCol.size()>0){
					info.setCustomer(cusCol.get(0));
				}
			}
			if(roomName!=null&&!"".equals(roomName)){
				GeneralAsstActTypeCollection aaCol=GeneralAsstActTypeFactory.getLocalInstance(ctx).getGeneralAsstActTypeCollection("select * from where name='"+roomName+"' or description='"+roomName+"'");
				if(aaCol.size()>0){
					info.setRoom(aaCol.get(0));
				}
			}
			if(productTypeId!=null){
				GeneralAsstActTypeCollection aaCol=GeneralAsstActTypeFactory.getLocalInstance(ctx).getGeneralAsstActTypeCollection("select * from where id='"+productTypeId+"'");
				if(aaCol.size()>0){
					info.setProductType(aaCol.get(0));
				}
			}
			if(sellProjectId!=null){
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("id");
				sels.add("companyOrgUnit.id");
				SellProjectInfo sp=SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo(new ObjectUuidPK(sellProjectId),sels);
				info.setCompany(sp.getCompanyOrgUnit());
				
				SellProjectInfo psp=getParentSellProject(ctx, sp);
				if(psp!=null&&psp.getProjectBase()!=null){
					CurProjectCollection curProjectCol=CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection("select * from where projectBase.projectBase.id='"+psp.getProjectBase().getId().toString()+"'");
					if(curProjectCol.size()>0){
						info.setCurProject(curProjectCol.get(0));
					}
				}
			}
			info.setContractTotalAmount(contractTotalAmount);
			info.setBuildArea(buildArea);
			
			info.setSourceBillId(billId);
			
			info.setBizState(RoomTransferStateEnum.HASTRANSFER);
			
			RoomTransferFactory.getLocalInstance(ctx).addnew(info);
		}
	}
	public static SellProjectInfo getParentSellProject(Context ctx,SellProjectInfo sellProject) throws EASBizException, BOSException{
		if(sellProject==null) return null;
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("parent.id");
		sels.add("projectBase.id");
		sellProject=SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo(new ObjectUuidPK(sellProject.getId()),sels);
		if(sellProject.getParent()!=null){
			return getParentSellProject(ctx,sellProject.getParent());
		}else{
			return sellProject;
		}
	}
	protected void _unTransfer(Context ctx, List id) throws BOSException,EASBizException {
		for(int i=0;i<id.size();i++){
			RoomTransferFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(id.get(i).toString()));
		}
	}
}