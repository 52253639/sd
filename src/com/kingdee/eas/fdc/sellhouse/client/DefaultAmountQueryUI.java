/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatEntryInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerCollection;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerFactory;
import com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.fm.common.DateHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 欠款业务查询界面
 */
public class DefaultAmountQueryUI extends AbstractDefaultAmountQueryUI
{
    private static final Logger logger = CoreUIObject.getLogger(DefaultAmountQueryUI.class);
    
    /**
     * by..... wancheng
     */
    public DefaultAmountQueryUI() throws Exception
    {
        super();
    }
    protected SellProjectInfo sellProject = null;
    public void onLoad() throws Exception {
		super.onLoad();
		/**设置数字控件精度*/
		Object[] obj = {txtArgDay,txtargDayTo};
		FDCHelper.setComponentPrecision(obj,0);
		this.actionAddNew.setVisible(false);
		this.actionAbout.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionCalculator.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.prmtProject.setEnabled(false);
		this.actionExitCurrent.setVisible(false);
		this.actionExitCurrent.setEnabled(false);
		if (( this.getUIContext().get("sellProject") != null) && ( this.getUIContext().get("sellProject") != "")) {
			SellProjectInfo projectInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(this.getUIContext().get("sellProject").toString()));
			this.prmtProject.setValue(projectInfo);
			sellProject = projectInfo;
		}
		sign.setSelected(true);
		setRoomFilter();
		
		prePurchase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setRoomFilter();
			}
		});
		
		purchase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setRoomFilter();
			}
		});
		
		sign.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setRoomFilter();
			}
		});
    }

    private void setRoomFilter(){
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
		
		Set inS = new HashSet();
		if(prePurchase.isSelected()){
			inS.add(RoomSellStateEnum.PREPURCHASE_VALUE);
		}
		if(purchase.isSelected()){
			inS.add(RoomSellStateEnum.PURCHASE_VALUE);
		}
		if(sign.isSelected()){
			inS.add(RoomSellStateEnum.SIGN_VALUE);
		}
		
		if(inS.isEmpty()){
			inS.add(RoomSellStateEnum.PREPURCHASE_VALUE);
			inS.add(RoomSellStateEnum.PURCHASE_VALUE);
			inS.add(RoomSellStateEnum.SIGN_VALUE);
		}
		
		filter.getFilterItems().add(new FilterItemInfo("sellState", inS, CompareType.INCLUDE));
		
		view.setFilter(filter);
		prmtRoom.setEntityViewInfo(view);
    }
    
    
	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
  protected List room=new ArrayList();
  protected List moneyDefine=new ArrayList();
    public void loadFields() 
    {
		super.loadFields();
	}

	/**
     * 查询按钮
     */
    public void actionArgQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionArgQuery_actionPerformed(e);
        if(sign.isSelected()==false&&prePurchase.isSelected()==false&&purchase.isSelected()==false){
        	 MsgBox.showInfo("请至少选择一种业务类型进行查询!");
		      SysUtil.abort();
        }
        StringBuffer sb = new StringBuffer();
        Object[] obj = (Object[])prmtRoom.getValue();
        if(obj!=null && obj.length>0){
        	for(int i=0;i<obj.length &&obj[i]!=null;i++){
            	if(i==0)
            		sb.append("'"+((RoomInfo)obj[i]).getId().toString()+"'");
            	else
            		sb.append(",'"+((RoomInfo)obj[i]).getId().toString()+"'");
            }
        }
        kDTable1.removeRows();//d
        
        StringBuffer sql = new StringBuffer();
        sql.append(" select zz.房间,zz.客户,zz.业务日期,zz.业务编号,zz.置业顾问,zz.合同总价,zz.联系电话,zz.应收日期,zz.应收金额,zz.实收日期,zz.实收金额,");
        sql.append(" zz.欠款金额,zz.款项名称,zz.欠款天数,zz.欠款总金额,zz.付款方案,zz.房间ID,zz.付款方案ID,zz.款项名称ID,zz.业务类型ID,zz.销售项目ID,zz.主线ID");
        sql.append(" from (");
        if(sign.isSelected()==true){
        	 sql.append(" select sg.房间,sg.房间ID,sg.客户,sg.业务日期,sg.主线ID,sg.业务编号,sg.置业顾问,sg.业务类型ID,sg.合同总价,sg.联系电话,sg.应收日期,");
             sql.append(" sg.应收金额,sg.实收日期,sg.实收金额,sg.欠款金额,sg.款项名称,sg.欠款天数,sg.付款方案,sg.付款方案ID,sg.欠款总金额,sg.款项名称ID,sg.销售项目ID");
             sql.append(" from (select room.fname_l2 as 房间,room.fid as 房间ID,");
             sql.append(" zx.FCustomerNames as 客户, zx.FTranDate as 业务日期,zx.fid as 主线ID,");
             sql.append(" sm.fnumber as 业务编号,sm.FSaleManNames as 置业顾问,sm.fid as 业务类型ID,");
             sql.append(" sm.FContractTotalAmount as 合同总价,sm.FCustomerPhone as 联系电话,");
             sql.append(" mx.FAppDate as 应收日期,mx.FAppAmount as 应收金额,mx.FActualFinishDate as 实收日期,mx.FBusinessName as 款项名称,");
             sql.append(" mx.FActRevAmount as 实收金额,(mx.FAppAmount-mx.FActRevAmount) as 欠款金额,");
             sql.append(" DATEDIFF(day,  mx.FAppDate, getdate()) as 欠款天数,");
             sql.append(" pt.fname_l2 as 付款方案,pt.fid as 付款方案ID,");
             sql.append(" tt.欠款金额 as 欠款总金额,md.fid as 款项名称ID,sp.fid as 销售项目ID");
             sql.append(" from   T_SHE_Transaction zx");
             sql.append(" left join  T_SHE_SignManage sm on sm.fid = zx.fbillid ");
             sql.append(" left join  T_SHE_SellProject sp on sp.fid = sm.FSellProjectID ");
             sql.append(" left join  T_SHE_SHEPayType pt on pt.fid = sm.FPayTypeID ");
             sql.append(" left join  T_SHE_Room room on room.fid = zx.froomid");
             sql.append(" left join  T_SHE_TranBusinessOverView mx on zx.fid =mx.fheadid ");
             sql.append(" left join (select sum(mm.FAppAmount) as 应收金额,sum( mm.FActRevAmount) as 实收金额, sum((mm.FAppAmount-mm.FActRevAmount)) as 欠款金额, zz.fid as 主线ID from T_SHE_Transaction zz  left join  T_SHE_TranBusinessOverView mm on zz.fid =mm.fheadid group by zz.fid )tt on tt.主线ID = zx.fid ");
             sql.append(" left join  T_SHE_MoneyDefine   md  on md.fid =mx.FMoneyDefineID");
             sql.append(" where (mx.FAppAmount-mx.FActRevAmount)>0 and ");
             sql.append(" sm.fBizState in ('SignApple','SignAuditing','SignAudit','ChangePirceAuditing','ChangeRoomAuditing','QuitRoomAuditing','ChangeNameAuditing')");
             sql.append(" and DATEDIFF(day,  mx.FAppDate, getdate())>0");
             sql.append(" order by zx.fid ) sg");
        }
        if(prePurchase.isSelected()==true){
        	if(sign.isSelected()==true){
        		sql.append(" UNION all ");
        	}
             sql.append(" select pre.房间,pre.房间ID,pre.客户,pre.业务日期,pre.主线ID,pre.业务编号,pre.置业顾问,pre.业务类型ID,pre.合同总价,pre.联系电话,pre.应收日期, ");
             sql.append(" pre.应收金额,pre.实收日期,pre.实收金额,pre.欠款金额,pre.款项名称,pre.欠款天数,pre.付款方案,pre.付款方案ID,pre.欠款总金额,pre.款项名称ID,pre.销售项目ID ");
             sql.append(" from ( select room.fname_l2 as 房间,room.fid as 房间ID,");
             sql.append(" zx.FCustomerNames as 客户, zx.FTranDate as 业务日期,zx.fid as 主线ID,");
             sql.append(" pm.fnumber as 业务编号,pm.FSaleManNames as 置业顾问,pm.fid as 业务类型ID,");
             sql.append(" pm.FContractTotalAmount as 合同总价,pm.FCustomerPhone as 联系电话, ");
             sql.append(" mx.FAppDate as 应收日期,mx.FAppAmount as 应收金额,mx.FActualFinishDate as 实收日期,mx.FBusinessName as 款项名称, ");
             sql.append(" mx.FActRevAmount as 实收金额,(mx.FAppAmount-mx.FActRevAmount) as 欠款金额,");
             sql.append(" DATEDIFF(day,  mx.FAppDate, getdate()) as 欠款天数, ");
             sql.append(" pt.fname_l2 as 付款方案,pt.fid as 付款方案ID,");
             sql.append(" tt.欠款金额 as 欠款总金额,md.fid as 款项名称ID,sp.fid as 销售项目ID");
             sql.append(" from   T_SHE_Transaction zx  ");
             sql.append(" left join  T_SHE_PrePurchaseManage pm on pm.fid = zx.fbillid ");
             sql.append(" left join  T_SHE_SellProject sp on sp.fid = pm.FSellProjectID ");
             sql.append(" left join  T_SHE_SHEPayType pt on pt.fid = pm.FPayTypeID ");
             sql.append("  join  T_SHE_Room room on room.fid = zx.froomid ");
             sql.append(" left join  T_SHE_TranBusinessOverView mx on zx.fid =mx.fheadid ");
             sql.append(" left join  (select sum(mm.FAppAmount) as 应收金额,sum( mm.FActRevAmount) as 实收金额, sum((mm.FAppAmount-mm.FActRevAmount)) as 欠款金额, zz.fid as 主线ID from      T_SHE_Transaction zz  left join  T_SHE_TranBusinessOverView mm on zz.fid =mm.fheadid group by zz.fid )tt on tt.主线ID = zx.fid ");
             sql.append(" left join  T_SHE_MoneyDefine   md  on md.fid =mx.FMoneyDefineID");
             sql.append(" where (mx.FAppAmount-mx.FActRevAmount)>0 and ");
             sql.append(" pm.fBizState in ('PreApple','PreAuditing''PreAudit','ChangePirceAuditing','ChangeRoomAuditing','QuitRoomAuditing','ChangeNameAuditing') ");
             sql.append(" and DATEDIFF(day,  mx.FAppDate, getdate())>0 ");
             sql.append(" order by zx.fid ) pre");
        }
       if(purchase.isSelected()==true){
    	   if(sign.isSelected()==true){
       		sql.append(" UNION all ");
           }else if(prePurchase.isSelected()==true&&purchase.isSelected()==true){
        	   sql.append(" UNION all ");
          }
           sql.append(" select pur.房间,pur.房间ID,pur.客户,pur.业务日期,pur.主线ID,pur.业务编号,pur.置业顾问,pur.业务类型ID,pur.合同总价,pur.联系电话,pur.应收日期, ");
           sql.append(" pur.应收金额,pur.实收日期,pur.实收金额,pur.欠款金额,pur.款项名称,pur.欠款天数,pur.付款方案,pur.付款方案ID,pur.欠款总金额,pur.款项名称ID,pur.销售项目ID ");
           sql.append(" from ( select room.fname_l2 as 房间,room.fid as 房间ID,");
           sql.append(" zx.FCustomerNames as 客户, zx.FTranDate as 业务日期,zx.fid as 主线ID,");
           sql.append(" cm.fnumber as 业务编号,cm.FSaleManNames as 置业顾问,cm.fid as 业务类型ID,");
           sql.append(" cm.FContractTotalAmount as 合同总价,cm.FCustomerPhone as 联系电话, ");
           sql.append(" mx.FAppDate as 应收日期,mx.FAppAmount as 应收金额,mx.FActualFinishDate as 实收日期,mx.FBusinessName as 款项名称, ");
           sql.append(" mx.FActRevAmount as 实收金额,(mx.FAppAmount-mx.FActRevAmount) as 欠款金额,");
           sql.append(" DATEDIFF(day,  mx.FAppDate, getdate()) as 欠款天数, ");
           sql.append(" pt.fname_l2 as 付款方案,pt.fid as 付款方案ID,");
           sql.append(" tt.欠款金额 as 欠款总金额, md.fid as 款项名称ID,sp.fid as 销售项目ID");
           sql.append(" from   T_SHE_Transaction zx  ");
           sql.append(" left join  T_SHE_PurchaseManage cm on cm.fid = zx.fbillid ");
           sql.append(" left join  T_SHE_SellProject sp on sp.fid = cm.FSellProjectID ");
           sql.append(" left join  T_SHE_SHEPayType pt on pt.fid = cm.FPayTypeID ");
           sql.append(" left join  T_SHE_Room room on room.fid = zx.froomid ");
           sql.append(" left join  T_SHE_TranBusinessOverView mx on zx.fid =mx.fheadid ");
           sql.append(" left join  (select sum(mm.FAppAmount) as 应收金额,sum( mm.FActRevAmount) as 实收金额, sum((mm.FAppAmount-mm.FActRevAmount)) as 欠款金额, zz.fid as 主线ID from      T_SHE_Transaction zz  left join  T_SHE_TranBusinessOverView mm on zz.fid =mm.fheadid group by zz.fid )tt on tt.主线ID = zx.fid ");
           sql.append(" left join  T_SHE_MoneyDefine   md  on md.fid =mx.FMoneyDefineID");
           sql.append(" where (mx.FAppAmount-mx.FActRevAmount)>0 and ");
           sql.append(" cm.fBizState in ('PurApple','PurAuditing''PurAudit','ChangePirceAuditing','ChangeRoomAuditing','QuitRoomAuditing','ChangeNameAuditing') ");
           sql.append(" and DATEDIFF(day,  mx.FAppDate, getdate())>0 ");
           sql.append(" order by zx.fid ) pur");
       }
        sql.append(" ) zz");
        sql.append(" where 1=1 and  zz.销售项目ID ='"+sellProject.getId().toString()+"'");
        if(obj!=null && obj.length>0 && obj[0]!=null)
        	sql.append(" and zz.房间ID in ("+sb.toString()+")");
        if(this.prmtMoneyDefine.getValue()!=null){
        	MoneyDefineInfo moeInfo = (MoneyDefineInfo)this.prmtMoneyDefine.getValue();
        	String  moneytId = (String)moeInfo.getId().toString();
        	sql.append(" and zz.款项名称ID in ('"+moneytId+"')");
        }
       if(pkBizDateFrom.getValue()!=null){
    	    Date from = (Date)pkBizDateFrom.getValue();
    		sql.append(" and zz.业务日期  > {ts '"+ DateHelper.getSQLBegin(DateHelper.getNextDay(from))+"'}");
		}
       if(pkBizDateTo.getValue()!=null){
    	   Date to = (Date)pkBizDateTo.getValue();
   		   sql.append(" and zz.业务日期  <= {ts '"+ DateHelper.getSQLBegin(DateHelper.getNextDay(to))+"'}");
		}
       if(txtArgDay.getValue()!=null){
    	    int dayFrom = ((BigDecimal)txtArgDay.getValue()).intValue();
     		sql.append(" and zz.欠款天数  > "+dayFrom+"");
      }
       if(txtargDayTo.getValue()!=null){
    	   int dayTo = ((BigDecimal)txtargDayTo.getValue()).intValue();
     		sql.append(" and zz.欠款天数  <= "+dayTo+"");
      }
       if(txtArgAmountFrom.getValue()!=null){
    	    BigDecimal AmountFrom = (BigDecimal)txtArgAmountFrom.getValue();
      		sql.append(" and zz.欠款总金额  > "+AmountFrom+"");
       }
       if(txtArgAmountTo.getValue()!=null){
   	    BigDecimal AmountTo = (BigDecimal)txtArgAmountTo.getValue();
     		sql.append(" and zz.欠款总金额  <= "+AmountTo+"");
       }
        FDCSQLBuilder db = new FDCSQLBuilder();
        db.appendSql(sql.toString());
        
        IRowSet rowset = db.executeQuery();
        Map t = new HashMap();
        while(rowset.next()){
        	String zxID = rowset.getString("主线ID");
        	DefaultAmountMangerInfo info = (DefaultAmountMangerInfo) t.get(zxID);
        	if(info == null){
        		 info = new DefaultAmountMangerInfo();
        		 info.setCustomerNames(rowset.getString("客户"));
        		 info.setTelephone(rowset.getString("联系电话"));
        		 info.setArgAmount(rowset.getBigDecimal("欠款总金额"));
        		 if(rowset.getString("付款方案ID")!=null){
        			 SHEPayTypeInfo payInfo = SHEPayTypeFactory.getRemoteInstance().getSHEPayTypeInfo(new ObjectUuidPK(rowset.getString("付款方案ID")));
            		 info.setPayment(payInfo);
        		 }
        		 info.setRemark(rowset.getString("业务编号"));
        		 String id = rowset.getString("业务类型ID");
        		 if(id!=null){
        			 Object billInfo = (Object) DynamicObjectFactory.getRemoteInstance().getValue(new ObjectUuidPK(id).getObjectType(),new ObjectUuidPK(id));
            		 if(billInfo instanceof SignManageInfo){ 
            			 info.setBusType("签约单");
            		 }
            		 if(billInfo instanceof PurchaseManageInfo){ 
            			 info.setBusType("认购单");
            		 }
            		 if(billInfo instanceof PrePurchaseManageInfo){ 
            			 info.setBusType("预定单");
            		 }
        		 }
        		 info.setContractAmount(rowset.getBigDecimal("合同总价"));
        		 info.setSaleManNames(rowset.getString("置业顾问"));
        		 info.setBizDate(rowset.getDate("业务日期"));
        		 RoomInfo roomInfo =RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(rowset.getString("房间ID")));
        		 info.setRoom(roomInfo);
        		 TransactionInfo tranInfo = TransactionFactory.getRemoteInstance().getTransactionInfo(new ObjectUuidPK(rowset.getString("主线ID")));
        		 info.setTransaction(tranInfo);
        		 SellProjectInfo sellInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(rowset.getString("销售项目ID")));
        		 info.setProject(sellInfo);
        		 info.setBillId(rowset.getString("业务类型ID"));
        		 info.setSubDeAmount(new BigDecimal(0.00));
        		 info.setId(BOSUuid.create(info.getBOSType()));
        		 info.setNumber(BOSUuid.create(info.getBOSType()).toString());
        		 info.setName(BOSUuid.create(info.getBOSType()).toString());
        	}
        	
        	 DefaultAmountMangerEntryInfo entryInfo = new DefaultAmountMangerEntryInfo();
        	 entryInfo.setHead(info);
        	 MoneyDefineInfo mdInfo =MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(rowset.getString("款项名称ID")));
        	 entryInfo.setMoneyDefine(mdInfo);
        	 entryInfo.setAppDate(rowset.getDate("应收日期"));
        	 entryInfo.setAppAmount(rowset.getBigDecimal("应收金额"));
        	 entryInfo.setActDate(rowset.getDate("实收日期"));
        	 entryInfo.setActAmount(rowset.getBigDecimal("实收金额"));
        	 entryInfo.setArgAmount(rowset.getBigDecimal("欠款金额"));
        	 entryInfo.setArgDays(rowset.getInt("欠款天数"));
			 info.getEntry().add(entryInfo);
			 t.put(zxID, info);
         }
        Set s = t.keySet();
        for(Iterator itor = s.iterator(); itor.hasNext(); ){
        	DefaultAmountMangerInfo deInfo = (DefaultAmountMangerInfo) t.get(itor.next());
        	IRow row = kDTable1.addRow();
        	kDTable1.getStyleAttributes().setLocked(true);
        	row.getCell("room").setValue(deInfo.getRoom().getName());
        	row.getCell("customerNames").setValue(deInfo.getCustomerNames());
        	row.getCell("telePhone").setValue(deInfo.getTelephone());
			row.getCell("bizDate").setValue(deInfo.getBizDate());
			row.getCell("number").setValue(deInfo.getRemark());
			row.getCell("contractAmount").setValue(deInfo.getContractAmount());
			row.getCell("argAmount").setValue(deInfo.getArgAmount());
			row.getCell("busType").setValue(deInfo.getBusType());
			row.getCell("saleManNames").setValue(deInfo.getSaleManNames());
			row.getCell("id").setValue(deInfo.getTransaction().getId());
			row.setUserObject(deInfo);
        	dams.add(deInfo);
        }
    }

	protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
		super.kDTable1_tableClicked(e);
		
	}
	protected ArrayList getSelectedIdValues()
	{
	    ArrayList selectList = new ArrayList();
	    int selectRows[] = KDTableUtil.getSelectedRows(kDTable1);
	    for(int i=0;i<selectRows.length;i++){
	    	String id= (String)kDTable1.getRow(selectRows[i]).getCell("id").getValue();
	    	selectList.add(id);
	    }
	    return selectList;
	}
	protected void attachListeners() {
		
	}

	protected void detachListeners() {
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		return DefaultAmountMangerFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return null;
	}
	DefaultAmountMangerCollection dams = new DefaultAmountMangerCollection();
	/**
     * 确认
     */
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception 
	{
		super.actionConfirm_actionPerformed(e);
//		getSelectedIdValues();
		int rowIndex = kDTable1.getSelectManager().getActiveRowIndex();
		if(rowIndex ==-1){
			  MsgBox.showInfo("请至少选择一条欠款明细!");
		      SysUtil.abort();
		}
		if(kDTable1.getRowCount()!=0){
			UIContext context = new UIContext(this);
			context.put("createInfo", setDefaultAmountCreatInfo());
			context.put("damColl", setAmountMangerCollection());
			context.put("SellProject" ,sellProject.getId());
		    UIFactory.createUIFactory(UIFactoryName.MODEL).create(DefaultAmountCreatEditUI.class.getName(),context,null,OprtState.ADDNEW).show();
		}
		else{
			  MsgBox.showInfo("至少有一条欠款明细才能确认!");
		      SysUtil.abort();
		}
	}
	public DefaultAmountMangerCollection setAmountMangerCollection(){
		DefaultAmountMangerCollection damColl = new DefaultAmountMangerCollection();
		int selectRows[] = KDTableUtil.getSelectedRows(kDTable1);
	    for(int i=0;i<selectRows.length;i++){
	    	damColl.add((DefaultAmountMangerInfo)kDTable1.getRow(selectRows[i]).getUserObject());
	    }
		return damColl;
	}
	public DefaultAmountCreatInfo setDefaultAmountCreatInfo() throws EASBizException, BOSException{
		DefaultAmountCreatInfo createInfo = new DefaultAmountCreatInfo();
		int selectRows[] = KDTableUtil.getSelectedRows(kDTable1);
		SellProjectInfo sellInfo = null;
	    for(int i=0;i<selectRows.length;i++){
	    	IRow row = kDTable1.getRow(selectRows[i]);
	    	DefaultAmountCreatEntryInfo entry = new DefaultAmountCreatEntryInfo();
	    	DefaultAmountMangerInfo amInfo = (DefaultAmountMangerInfo)kDTable1.getRow(selectRows[i]).getUserObject();
	    	sellInfo = amInfo.getProject();
	    	entry.setCustomerNames(amInfo.getCustomerNames());
	    	entry.setRoom(amInfo.getRoom());
	    	entry.setTelephone(amInfo.getTelephone());
	    	entry.setBizDate(amInfo.getBizDate());
	    	entry.setNumber(amInfo.getRemark());
	    	entry.setContractAmount(amInfo.getContractAmount());
	    	entry.setArgAmount(amInfo.getArgAmount());
	    	entry.setBusType(amInfo.getBusType());
	    	entry.setSaleManNames(amInfo.getSaleManNames());
	    	entry.setArgAmount(amInfo.getArgAmount());
	    	entry.setSubDeAmount(amInfo.getSubDeAmount());
	    	entry.setRemak(amInfo.getTransaction().getId().toString());
   		 	createInfo.getParent().add(entry);
	    }
	    createInfo.setProject(sellInfo);
		return createInfo;
	}
	/**
     * 取消
     */
	public void actionCancels_actionPerformed(ActionEvent e) throws Exception 
	{
//		super.actionCancels_actionPerformed(e);
//		kDTable1.removeRows();
		super.actionExitCurrent_actionPerformed(e);
	}
	protected  void fetchInitData() throws Exception{
		
	}
	 public SelectorItemCollection getSelectors() {
		 SelectorItemCollection sic = super.getSelectors();
		 sic.add("room.*");
		 sic.add("area.*");
	     return sic;
	}   

	protected void prmtRoom_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtRoom_dataChanged(e);

	}
	/**
     * 业务日期  从
     */
	protected void pkBizDateFrom_dataChanged(DataChangeEvent e)throws Exception {
		super.pkBizDateFrom_dataChanged(e);
		if(this.pkBizDateFrom.getValue()!=null&&this.pkBizDateTo.getValue()!=null){
			Calendar dateFrom = Calendar.getInstance();
			Calendar dateTo = Calendar.getInstance();
			dateFrom.setTime((Date)pkBizDateFrom.getValue());
			dateTo.setTime((Date)pkBizDateTo.getValue());
			if(dateFrom.compareTo(dateTo)>0){
				MsgBox.showInfo("前者日期应小于后者日期!");
				pkBizDateFrom.setValue(null);
				SysUtil.abort();
			}
		} 
		
	}
	/**
     * 业务日期  至
     */
	protected void pkBizDateTo_dataChanged(DataChangeEvent e) throws Exception {
		super.pkBizDateTo_dataChanged(e);
		if(this.pkBizDateFrom.getValue()!=null&&this.pkBizDateTo.getValue()!=null){
			Calendar dateFrom = Calendar.getInstance();
			Calendar dateTo = Calendar.getInstance();
			dateFrom.setTime((Date)pkBizDateFrom.getValue());
			dateTo.setTime((Date)pkBizDateTo.getValue());
			if(dateFrom.compareTo(dateTo)>0){
				MsgBox.showInfo("后者日期应大于前者日期!");
				pkBizDateTo.setValue(null);
				SysUtil.abort();
			}
		}
	}
	/**
     * 欠款金额 从  
     */
	protected void txtArgAmountFrom_dataChanged(DataChangeEvent e)throws Exception {
		super.txtArgAmountFrom_dataChanged(e);
		if(this.txtArgAmountFrom.getValue()!=null&&this.txtArgAmountTo.getValue()!=null){
			 BigDecimal AmountFrom = (BigDecimal)txtArgAmountFrom.getValue();
			 BigDecimal AmountTo = (BigDecimal)txtArgAmountTo.getValue();
			 if(AmountFrom.compareTo(AmountTo)>0){
				  MsgBox.showInfo("前者金额应小于后者金额!");
				  txtArgAmountFrom.setValue(null);
			      SysUtil.abort();
			 }
		}
	}
	/**
     * 欠款金额 至
     */
	protected void txtArgAmountTo_dataChanged(DataChangeEvent e)throws Exception {
		super.txtArgAmountTo_dataChanged(e);
		if(this.txtArgAmountFrom.getValue()!=null&&this.txtArgAmountTo.getValue()!=null){
			 BigDecimal AmountFrom = (BigDecimal)txtArgAmountFrom.getValue();
			 BigDecimal AmountTo = (BigDecimal)txtArgAmountTo.getValue();
			 if(AmountFrom.compareTo(AmountTo)>0){
				  MsgBox.showInfo("后者金额应大于前者金额!");
				  txtArgAmountTo.setValue(null);
			      SysUtil.abort();
			 }
		}
	}
	/**
     * 违约天数 从
     */
	protected void txtArgDay_dataChanged(DataChangeEvent e) throws Exception {
		super.txtArgDay_dataChanged(e);
		if(this.txtArgDay.getValue()!=null&&this.txtargDayTo.getValue()!=null){
			 int dayFrom = ((BigDecimal)txtArgDay.getValue()).intValue();
			 int dayTo= ((BigDecimal)txtargDayTo.getValue()).intValue();
			 if(dayFrom>dayTo){
				 MsgBox.showInfo("前者天数应小于后者天数!");
				 txtArgDay.setValue(null);
			      SysUtil.abort();
			 }
		}
	}
	/**
     * 违约天数 至
     */
	protected void txtargDayTo_dataChanged(DataChangeEvent e) throws Exception {
		super.txtargDayTo_dataChanged(e);
		if(this.txtArgDay.getValue()!=null&&this.txtargDayTo.getValue()!=null){
			 int dayFrom = ((BigDecimal)txtArgDay.getValue()).intValue();
			 int dayTo= ((BigDecimal)txtargDayTo.getValue()).intValue();
			 if(dayFrom>dayTo){
				 MsgBox.showInfo("后者天数应大于前者天数!");
				 txtargDayTo.setValue(null);
			      SysUtil.abort();
			 }
		}
	}

	/**
     * 
     */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		
	}
}