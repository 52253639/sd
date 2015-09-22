package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import java.text.DateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public class SaleScheduleOnLoadDetailReportFacadeControllerBean extends AbstractSaleScheduleOnLoadDetailReportFacadeControllerBean
{
  private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.SaleScheduleOnLoadDetailReportFacadeControllerBean");

  protected RptParams _init(Context ctx, RptParams params) throws BOSException, EASBizException
  {
    RptParams pp = new RptParams();
    return pp;
  }
  private void initColoum(RptTableHeader header, RptTableColumn col, String name, int width, boolean isHide) {
    col = new RptTableColumn(name);
    col.setWidth(width);
    col.setHided(isHide);
    header.addColumn(col);
  }

  protected RptParams _createTempTable(Context ctx, RptParams params) throws BOSException, EASBizException {
    RptTableHeader header = new RptTableHeader();
    RptTableColumn col = null;
    initColoum(header, col, "id", 150, true);
    initColoum(header, col, "sellProject", 150, false);
    initColoum(header, col, "room", 150, false);
    initColoum(header, col, "customer", 150, false);
    initColoum(header, col, "bizDate", 100, false);
    initColoum(header, col, "contractTotalAmount", 120, false);
    initColoum(header, col, "onLoadAmount", 150, false);
    initColoum(header, col, "rate", 70, false);
    initColoum(header, col, "saleMans", 100, false);
    initColoum(header, col, "moneyDefine", 100, false);
    initColoum(header, col, "appRevAmount", 150, false);
    header.setLabels(new Object[][] { 
      { 
      "Id", "项目", "房间", "客户","签约日期","合同总价","在途金额","在途率","置业顾问","收款款项","收款金额"} }, 
      true);
    params.setObject("header", header);
    return params;
  }
  protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException {
    Date toDate = (Date)params.getObject("toDate");
    String orgId = (String)params.getObject("orgId");
    Boolean type = (Boolean)params.getObject("type");
    StringBuffer sb = new StringBuffer();
    sb.append(" select sign.fid id,sp.fname_l2 sellProject,room.fname_l2 room,sign.fcustomerNames customer,sign.fbizDate bizDate,sign.fcontractTotalAmount contractTotalAmount,sign.fcontractTotalAmount-isnull(sumRevBill.amount,0) onLoadAmount,(sign.fcontractTotalAmount-isnull(sumRevBill.amount,0))*100/sign.fcontractTotalAmount rate,sign.FSaleManNames saleMans,md.fname_l2 moneyDefine,isnull(entry.famount,0)+isnull(entry.frevAmount,0) appRevAmount from t_she_signManage sign left join t_org_baseUnit org on org.fid=sign.forgUnitId");
    sb.append(" left join t_she_sellProject sp on sp.fid=sign.fsellProjectid left join t_she_room room on room.fid=sign.froomId");
    sb.append(" left join T_BDC_SHERevBill revBill on revBill.frelatetransId=sign.ftransactionId left join T_BDC_SHERevBillEntry entry on entry.fparentid=revBill.fid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId");
    sb.append(" left join (select sum(isnull(entry.famount,0)+isnull(entry.frevAmount,0)) amount,revBill.frelateTransId billId from T_BDC_SHERevBill revBill left join T_BDC_SHERevBillEntry entry on entry.fparentid=revBill.fid left join t_she_moneyDefine md on md.fid=entry.fmoneyDefineId");
	sb.append(" where revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount') group by revBill.frelateTransId)sumRevBill on sumRevBill.billId=sign.ftransactionId");
    sb.append(" where ((revBill.fstate in('2SUBMITTED','4AUDITTED') and md.fmoneyType in('FisrtAmount','HouseAmount','LoanAmount','AccFundAmount'))or revBill.fid is null)  and sign.fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit')");
    sb.append(" and NOT EXISTS (select tt.fnewId from t_she_changeManage tt where tt.fstate in('2SUBMITTED','3AUDITTING') and sign.fid=tt.fnewId )");
    if (type.booleanValue())
      sb.append(" and EXISTS (select t1.fid from t_she_signManage t1 left join t_she_signPayListEntry t2 on t2.fheadid=t1.fid left join t_she_moneyDefine md on md.fid=t2.fmoneyDefineId where t1.fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit') and md.fmoneyType in('LoanAmount','AccFundAmount') and sign.fid=t1.fid )");
    else {
      sb.append(" and NOT EXISTS (select t1.fid from t_she_signManage t1 left join t_she_signPayListEntry t2 on t2.fheadid=t1.fid left join t_she_moneyDefine md on md.fid=t2.fmoneyDefineId where t1.fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit') and md.fmoneyType in('LoanAmount','AccFundAmount') and sign.fid=t1.fid )");
    }
    if (toDate != null) {
      sb.append(" and sign.fbusAdscriptionDate<{ts '" + FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(toDate)) + "'}");
    }
    if (orgId != null) {
      sb.append(" and org.fid ='" + orgId + "'");
    }
    sb.append(" order by sp.flongNumber,room.fnumber");
    RptRowSet rowSet = executeQuery(sb.toString(), null, ctx);
    params.setObject("rowset", rowSet);
    return params;
  }
}