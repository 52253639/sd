package com.kingdee.eas.fdc.finance.client;

import java.math.BigDecimal;
import java.util.Map;

import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

public class PaymentClientUtils {
	
	
	public static String checkPaymentSplitState(String billId, Map param) throws Exception{
		String msg = "付款单还未进行拆分，请先通过“拆分”按钮或者付款拆分序时簿拆分功能进行拆分，否则不能提交";
		if(billId==null){
			msg="请先保存单据,并完全拆分后提交";
		}else{
			boolean isClosed = true;//关闭了发票归第一张
			FDCSQLBuilder builder=new FDCSQLBuilder();
			IRowSet rs;
			
			boolean isSimpleInvoice = Boolean.valueOf(param.get("isSimpleInvoice").toString()).booleanValue();
			boolean isSimpleFinancial = Boolean.valueOf(param.get("isSimpleFinancial").toString()).booleanValue();
			boolean isSimpleFinancialExtend = Boolean.valueOf(param.get("isSimpleFinancialExtend").toString()).booleanValue();
			boolean isFinancial = Boolean.valueOf(param.get("isFinancial").toString()).booleanValue();;
			boolean isSeparate = Boolean.valueOf(param.get("isSeparate").toString()).booleanValue();
			//判断是否还有比当前付款单更早的了,如果存在则表明当前单据不是第一张
			builder.appendSql("select 1 from T_CAS_PaymentBill a,T_CAS_PaymentBill b where a.ffdcPayReqID=b.ffdcPayReqID and b.fid=? and a.fcreateTime<b.fcreateTime");
			builder.addParam(billId);
			if (builder.isExist()) {
				isClosed = false ;
			}
			builder.clear();
			builder.appendSql("select pb.flocalamount famount,pb.fprojectpriceincontract fprojectpriceincontract,pr.finvoiceamt finvoiceamt,con.fiscostsplit fiscostsplit from t_cas_paymentbill pb ");
			builder.appendSql("inner join t_con_payrequestbill pr on pr.fid=pb.ffdcpayreqid ");
			builder.appendSql("inner join t_con_contractbill con on con.fid=pb.fcontractbillid ");
			builder.appendSql("where pb.fid=? ");
			builder.addParam(billId);
			builder.appendSql("union ");
			builder.appendSql("select pb.flocalamount famount,pb.fprojectpriceincontract fprojectpriceincontract,pr.finvoiceamt finvoiceamt,con.fiscostsplit fiscostsplit from t_cas_paymentbill pb ");
			builder.appendSql("inner join t_con_payrequestbill pr on pr.fid=pb.ffdcpayreqid ");
			builder.appendSql("inner join t_con_contractwithouttext con on con.fid=pb.fcontractbillid ");
			builder.appendSql("where pb.fid=? ");
			builder.addParam(billId);
			
			rs=builder.executeQuery();
			BigDecimal amount = FDCHelper.ZERO;
			BigDecimal amtInContract = FDCHelper.ZERO;
			BigDecimal invoiceAmt = FDCHelper.ZERO;
			boolean isCostSplit = true;
			if(rs!=null&&rs.size()==1){
				rs.next();
				amount = FDCHelper.toBigDecimal(rs.getBigDecimal("famount"),2);
				amtInContract = FDCHelper.toBigDecimal(rs.getBigDecimal("fprojectpriceincontract"),2);
				invoiceAmt = isClosed?FDCHelper.toBigDecimal(rs.getBigDecimal("finvoiceamt"),2):FDCHelper.ZERO;
				isCostSplit = rs.getBoolean("fiscostsplit");
				
			}
			
			String tableentry = isCostSplit?"t_fnc_paymentsplitentry":"t_fnc_paymentnocostsplitentry";
			String tablehead =isCostSplit?"t_fnc_paymentsplit":"t_fnc_paymentnocostsplit";
			builder.clear();
			builder.appendSql("select sum(e.finvoiceamt) finvoiceamt,sum(e.fpayedamt) fpayedamt,h.fisconwithouttext fisnotext from "+tableentry+" e ");
			builder.appendSql("inner join "+tablehead+" h on h.fid=e.fparentid ");
			builder.appendSql("where h.fpaymentbillid=? and e.fisleaf=1 and h.fisinvalid=0 ");
			builder.addParam(billId);
			builder.appendSql("group by h.fisconwithouttext ");//只有一张单据
			rs = builder.executeQuery();
			if (rs != null && rs.size() == 1) {
				rs.next();
				boolean isNoText = rs.getBoolean("fisnotext");
				BigDecimal splitPay = FDCHelper.toBigDecimal(rs.getBigDecimal("fpayedamt"),2);
				BigDecimal splitInv = FDCHelper.toBigDecimal(rs.getBigDecimal("finvoiceamt"),2);
				//未启用简单发票，未启用工程量分享，不处理发票
				if(!isSimpleInvoice && !isSeparate){
					invoiceAmt = FDCHelper.ZERO;
					splitInv = FDCHelper.ZERO;
				}
				//无文本发票不用处理，直接相等
				if(isNoText){
					splitInv = splitPay;
				}
				if(isSimpleFinancial){
					if(!isNoText&&isSimpleFinancialExtend){
						//简单，非无文本，简单扩展，金额为合同内工程款
						amount = amtInContract;
					}else{
						//
					}
				}else if(isFinancial){
					//复杂模式及其它情况处理的是合同内工程款
					amount = amtInContract;
				} else{
					amount = amtInContract;
				}
				
				
				if (amount.compareTo(
						splitPay) != 0
						|| invoiceAmt
								.compareTo(splitInv) != 0) {
					msg = "付款单还未进行拆分，请先通过“拆分”按钮或者付款拆分序时簿拆分功能进行拆分，否则不能提交";
				} else {
					msg = "";
				}
			} 
			
		}
		
		return msg;
	}
	
	
}
