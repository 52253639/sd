
--修改新收款单描述字段的长度
alter table t_bdc_fdcreceivingbill alter fdescription nvarchar(255);

--
--增加导入标示字段
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='fisimport' and KSQL_COL_TABNAME='t_bdc_fdcreceivingbillentry')
alter table t_bdc_fdcreceivingbillentry add fisimport int;

--增加原库中的ID字段
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='folddbid' and KSQL_COL_TABNAME='t_bdc_fdcreceivingbillentry')
alter table t_bdc_fdcreceivingbillentry add folddbid varchar(44);

--增加原库中的headId字段
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='folddbheadid' and KSQL_COL_TABNAME='t_bdc_fdcreceivingbillentry')
alter table t_bdc_fdcreceivingbillentry add folddbheadid varchar(44);

--增加原库中的旧收款明细ID字段
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='folddbpaylistid' and KSQL_COL_TABNAME='t_bdc_fdcreceivingbillentry')
alter table t_bdc_fdcreceivingbillentry add folddbpaylistid varchar(44);

--增加导入标示字段
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='fisimport' and KSQL_COL_TABNAME='T_BDC_FDCReceivingBill')
alter table T_BDC_FDCReceivingBill add fisimport int;

--增加原ID字段
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='folddbid' and KSQL_COL_TABNAME='T_BDC_FDCReceivingBill')
alter table T_BDC_FDCReceivingBill add folddbid varchar(44);

--新收款单客户分录表增加是否导入的标识字段
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='fisimport' and KSQL_COL_TABNAME='T_BDC_RevFDCCustomerEntry')
alter table T_BDC_RevFDCCustomerEntry add fisimport int;

--新收款单客户分录表增加原表ID字段
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='folddbid' and KSQL_COL_TABNAME='T_BDC_RevFDCCustomerEntry')
alter table T_BDC_RevFDCCustomerEntry add folddbid varchar(44);

--新收款单客户分录表增加原表HEADID字段
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='folddbheadid' and KSQL_COL_TABNAME='T_BDC_RevFDCCustomerEntry')
alter table T_BDC_RevFDCCustomerEntry add folddbheadid varchar(44);

--增加导入标示字段
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='fisimport' and KSQL_COL_TABNAME='T_SHE_PurchasePayListEntry')
alter table T_SHE_PurchasePayListEntry add fisimport int;

--补差应收明细增加导入标记字段
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='fisimport' and KSQL_COL_TABNAME='T_SHE_AreaCompensateRevList')
alter table T_SHE_AreaCompensateRevList add fisimport int;

If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='fisimport' and KSQL_COL_TABNAME='T_SHE_CusRevList')
alter table T_SHE_CusRevList add fisimport int;

--来源分录增加导入标示字段
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='fisimport' and KSQL_COL_TABNAME='T_BDC_TransferSourceEntry')
alter table T_BDC_TransferSourceEntry add fisimport int;

--有些旧数据不完整，为以后备查，存储对应的原红单分录的ID
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_NAME ='folddbSettlemententryid' and KSQL_COL_TABNAME='T_BDC_TransferSourceEntry')
alter table T_BDC_TransferSourceEntry add folddbSettlemententryid varchar(44);
------------



--将单据类型空的纪录设置为收款类型
update t_she_fdcreceivebill set FBillTypeEnum='gathering' where fbilltypeenum is null and FMoneySysType='SalehouseSys';

--将收款对象为空的纪录，收款对象默认为认购收款
update t_she_fdcreceivebill set FGathering='purchase' where FGathering is null and FMoneySysType='SalehouseSys';

------------------------------------  纯粹售楼收款单的升级  -----------------------------------------

--将售楼收款单插入到中间表(客户分录未维护,分单分录未维护)
select b.fid b_fid,b.fcreatorid b_fcreatorid,b.fcreatetime b_fcreatetime,b.flastupdateuserid b_flastupdateuserid,b.flastupdatetime b_flastupdatetime,b.fcontrolunitid b_fcontrolunitid,b.fnumber b_fnumber,b.fbizdate b_fbizdate,b.fdescription b_fdescription,b.fauditorid b_fauditorid,b.fcompanyid b_fcompanyid,b.fcurrencyid b_fcurrencyid,b.fauditdate b_fauditdate,b.fcashierid b_fcashierid,b.ffivouchered b_ffivouchered,b.fbillstatus b_fbillstatus,b.factrecamt b_factrecamt,b.fpayertypeid b_fpayertypeid,b.fpayerid b_fpayerid,b.fpayernumber b_fpayernumber,b.fpayername b_fpayername,b.famount b_famount,b.frecbilltypeid b_frecbilltypeid,b.ffdcreceivebillid b_ffdcreceivebillid,'b-c' as 华丽分割线2,c.fid c_fid,c.fcreatorid c_fcreatorid,c.fcreatetime c_fcreatetime,c.flastupdateuserid  c_flastupdateuserid,c.flastupdatetime c_flastupdatetime,c.fcontrolunitid c_fcontrolunitid ,c.froomid  c_froomid,c.fsellprojectid c_fsellprojectid ,c.finvoiceid c_finvoiceid,c.FReceiptNumber  c_FReceiptNumber,c.fchequeid c_fchequeid,c.fmoneysystype c_fmoneysystype,c.ftenancycontractid  c_ftenancycontractid,c.fbilltypeenum c_fbilltypeenum,c.FGathering c_FGathering,c.FPurchaseID c_FPurchaseID, c.FSettlementBillID c_FSettlementBillID,c.FSinPurchaseID c_FSinPurchaseID,c.FSinObligateID c_FSinObligateID,c.FAsstActTypeID c_FAsstActTypeID,c.FIsBlankOut c_FIsBlankOut,c.FAdjustSrcBillID c_FAdjustSrcBillID,c.FReceiptState c_FReceiptState,c.FPrintCount c_FPrintCount,c.FSettleMentType c_FSettleMentType 
into t_tmp_revgaizao_s 
from t_cas_receivingbill b 
inner join t_she_fdcreceivebill c on b.ffdcreceivebillid=c.fid 
where FMoneySysType is null or fmoneysystype='SalehouseSys';

--将售楼收款单分录插入到中间表(来源分录未维护)
select a.fid a_fid,a.FAmount a_famount,a.FSettlement a_FSettlement,a.FSettlementTypeID a_FSettlementTypeID,a.FAccountID a_FAccountID,a.FBankNumber a_fbanknumber,a.FReceivingBillID a_FReceivingBillID,a.FRevAccountBankID a_FRevAccountBankID,a.FMoneyDefineID a_FMoneyDefineID,a.FPayListId a_FPayListId,a.FOppSubjectID a_FOppSubjectID,a.FCounteractAmount a_FCounteractAmount,a.fseq a_fseq,a.FFCounteractId a_FFCounteractId,a.FCanCounteractAmount a_FCanCounteractAmount,a.FIsPartial a_fispartial,'a-b' as 华丽分割线1,b.fid b_fid,b.fcreatorid b_fcreatorid,b.fcreatetime b_fcreatetime,b.flastupdateuserid b_flastupdateuserid,b.flastupdatetime b_flastupdatetime,b.fcontrolunitid b_fcontrolunitid,b.fnumber b_fnumber,b.fbizdate b_fbizdate,b.fdescription b_fdescription,b.fauditorid b_fauditorid,b.fcompanyid b_fcompanyid,b.fcurrencyid b_fcurrencyid,b.fauditdate b_fauditdate,b.fcashierid b_fcashierid,b.ffivouchered b_ffivouchered,b.fbillstatus b_fbillstatus,b.factrecamt b_factrecamt,b.fpayertypeid b_fpayertypeid,b.fpayerid b_fpayerid,b.fpayernumber b_fpayernumber,b.fpayername b_fpayername,b.famount b_famount,b.frecbilltypeid b_frecbilltypeid,b.ffdcreceivebillid b_ffdcreceivebillid,'b-c' as 华丽分割线2,c.fid c_fid,c.fcreatorid c_fcreatorid,c.fcreatetime c_fcreatetime,c.flastupdateuserid  c_flastupdateuserid,c.flastupdatetime c_flastupdatetime,c.fcontrolunitid c_fcontrolunitid ,c.froomid  c_froomid,c.fsellprojectid c_fsellprojectid ,c.finvoiceid c_finvoiceid,c.FReceiptNumber  c_FReceiptNumber,c.fchequeid c_fchequeid,c.fmoneysystype c_fmoneysystype,c.ftenancycontractid  c_ftenancycontractid,c.fbilltypeenum c_fbilltypeenum,c.FGathering c_FGathering,c.FPurchaseID c_FPurchaseID, c.FSettlementBillID c_FSettlementBillID,c.FSinPurchaseID c_FSinPurchaseID,c.FSinObligateID c_FSinObligateID,c.FAsstActTypeID c_FAsstActTypeID,c.FIsBlankOut c_FIsBlankOut,c.FAdjustSrcBillID c_FAdjustSrcBillID,c.FReceiptState c_FReceiptState,c.FPrintCount c_FPrintCount,c.FSettleMentType c_FSettleMentType 
into t_tmp_revgaizao_se 
from t_she_fdcreceivebillentry a 
inner join t_cas_receivingbill b on a.freceivingbillid=b.fid 
inner join t_she_fdcreceivebill c on b.ffdcreceivebillid=c.fid 
where FMoneySysType is null or fmoneysystype='SalehouseSys';

--插入到新收款单分录中
--TODO：FRevListType未维护(可根据与各种应收明细连表来更新)。FHeadID(后续脚本已处理)。FRoomID未维护(租赁那边才会用到,可不维护)。转款来源未维护
insert into t_bdc_fdcreceivingbillentry (fid,FRevAmount,FRevLocAmount,FSettlementTypeID,FSettlementNumber,FRevAccountID,FOppAccountID,FMoneyDefineID,FRevAccountBankID,FBankNumber,FRevListId,fseq,fisimport,fheadid,folddbid,folddbheadid,folddbpaylistid) 
select newbosid('26EEC414') as fid,a_famount as frevamount,a_famount as frevlocamount,a_fsettlementtypeid as fsettlementtypeid,a_fsettlement as fsettlementnumber,a_faccountid as frevaccountid,a_foppsubjectid as foppaccountid,a_FMoneyDefineID as FMoneyDefineID,a_frevaccountbankid as FRevAccountBankID,a_fbanknumber as FBankNumber,a_fpaylistid as FRevListId,a_fseq as fseq,2 as fisimport,'tnnd' as fheadid,a_fid as folddbid,a_freceivingbillid as folddbheadid,a_fpaylistid as folddbpaylistid from t_tmp_revgaizao_se where (c_FBillTypeEnum is null or c_FBillTypeEnum!='settlement');

--插入到新收款单中
--TODO：FRevBizType(有部分记录的值未维护上,可根据与各种业务单据表连表来更新)。收据发票未维护(确定票据)。FOrgUnitID未维护(可不维护)。FState未维护(可暂不维护)。FTenancyUserID未维护(需特殊维护)。客户分录(后面SQL已处理)。
insert into T_BDC_FDCReceivingBill (fid,FCompanyID,FSellProjectID,FCurrencyID,FExchangeRate,FReceiptNumber,FtenancyObjID,FCustomerID,FBillStatus,ffiVouchered,FOriginalAmount,FAmount,FAuditTime,FBookedDate,fnumber,fbizdate,fdescription,fauditorid,fcreatorid,fcreatetime,flastupdateuserid,flastupdatetime,fcontrolunitid,folddbid,fisimport,FpurchaseObjID,FsincerityObjID,FRoomID,frevbilltype,frevbiztype,FReceiptID,FInvoiceID,FReceiptState) 
select newbosid('F12182FE') as fid,b_fcompanyid as FCompanyID,c_fsellprojectid as FSellProjectID,b_fcurrencyid as FCurrencyID,to_number(1) as FExchangeRate,c_FReceiptNumber as FReceiptNumber,c_FTenancyContractID as FtenancyObjID,b_fpayerid as FCustomerID,b_fbillstatus as FBillStatus,b_ffivouchered as ffiVouchered,b_factrecamt as FOriginalAmount, b_factrecamt as FAmount,b_fauditdate as FAuditTime,b_fcreatetime as FBookedDate,b_fnumber as fnumber,b_fbizdate as fbizdate,b_fdescription as fdescription,b_fauditorid as fauditorid,b_fcreatorid as fcreatorid,b_fcreatetime as fcreatetime,b_flastupdateuserid as flastupdateuserid,b_flastupdatetime as flastupdatetime,b_fcontrolunitid as fcontrolunitid,b_fid as folddbid,2 as fisimport,c_fpurchaseid as FpurchaseObjID,c_FSinPurchaseID as FsincerityObjID,c_FRoomID as froomid,(case c_FBillTypeEnum when 'gathering' then 'gathering' when 'refundment' then 'refundment' when 'transfer' then 'transfer' when 'adjust' then 'adjust' else null end) as frevbilltype,(case c_FGathering when 'SinPurchase' then 'sincerity' when 'SaleRoom' then 'purchase' when 'CustomerRev' then 'customer' else 'purchase' end) as frevbiztype,c_fchequeid as FReceiptID,c_finvoiceid as FInvoiceID,c_FReceiptState as FReceiptState from t_tmp_revgaizao_s where (c_FBillTypeEnum is null or c_FBillTypeEnum!='settlement');

--设置导入的收款单分录的HeadID为对应的收款单ID
update t_bdc_fdcreceivingbillentry set fheadid=
(select top 1 a.fid from T_BDC_FDCReceivingBill a where a.folddbid=t_bdc_fdcreceivingbillentry.folddbheadid)
where fisimport=2 and exists (select a.fid from T_BDC_FDCReceivingBill a where a.folddbid=t_bdc_fdcreceivingbillentry.folddbheadid);

--将收款客户插入到新收款单客户分录中(FHeadID 未维护,下条语句维护)
insert into T_BDC_RevFDCCustomerEntry (fid,FHeadID,FFdcCustomerID,folddbid,folddbheadid,fisimport) 
select newbosid('0F394DB6') as fid,'hehe' as fheadid,c.fid as FFdcCustomerID,a.fid as folddbid,a.fheadid as folddbheadid,2 as fisimport from T_SHE_CustomerEntry a 
inner join t_tmp_revgaizao_s b on a.fheadid=b.c_fid 
inner join T_SHE_FDCCustomer c on a.FCustomerId=c.FSysCustomerID
where b.c_FBillTypeEnum!='settlement';

--设置导入的收款单客户分录的HeadId为对应收款单的ID(测试库中执行后还有几条数据未更新上,不知道是不是脏数据所致)
select m.fid as eid,a.fid hid 
into t_tmp_revcushead_bat 
from t_bdc_revfdccustomerentry m 
inner join t_tmp_revgaizao_s b on m.folddbheadid=b.c_fid 
inner join t_bdc_fdcreceivingbill a on a.folddbid=b.b_fid;

update t_bdc_revfdccustomerentry set fheadid=
(select top 1 a.hid from t_tmp_revcushead_bat a where a.eid=t_bdc_revfdccustomerentry.fid )
where  exists(select top 1 a.hid from t_tmp_revcushead_bat a where a.eid=t_bdc_revfdccustomerentry.fid) 
and fisimport=2;

--收款明细类型的值更新,areaCompensate未处理(后续脚本处理),fdcCustomerRev未处理
update t_bdc_fdcreceivingbillentry set frevlisttype='sincerityPur' where fisimport=2 and frevlisttype is null and frevlistid in (select fid from T_SHE_SincerReceiveEntry);
update t_bdc_fdcreceivingbillentry set frevlisttype='purchaseRev' where fisimport=2 and frevlisttype is null and frevlistid in (select fid from T_SHE_PurchasePayListEntry);
update t_bdc_fdcreceivingbillentry set frevlisttype='purElseRev' where fisimport=2 and frevlisttype is null and frevlistid in (select fid from T_SHE_PurchaseElsePayListEntry);

------------------------------------------------------------------------------------------------------------


------------------------------------------------------------------------------------------------------------
---------------------------------------  以下是对应收明细的升级  -------------------------------------------
------------------------------------------------------------------------------------------------------------


-----------------------------------------  诚意认购应收明细  -----------------------------------------------

--备份诚意认购应收明细表的数据到t_tmp_revgaizao_spe
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_TABNAME='t_tmp_revgaizao_spe')
select *  
into t_tmp_revgaizao_spe 
from T_SHE_SincerReceiveEntry;

--备份认购单明细的数据到t_tmp_revgaizao_spure
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_TABNAME='t_tmp_revgaizao_spure')
select *  
into t_tmp_revgaizao_spure 
from T_SHE_PurchasePayListEntry where fisimport is null;

--备份认购单其他明细的数据到t_tmp_revgaizao_spuroe
If not exists (select * from KSQL_USERCOLUMNS where KSQL_COL_TABNAME='t_tmp_revgaizao_spuroe')
select *  
into t_tmp_revgaizao_spuroe 
from T_SHE_PurchaseElsePayListEntry;

--更新诚意认购应收明细的类型为sincerityPur
update T_SHE_SincerReceiveEntry set FrevMoneyType='sincerityPur';
--更新诚意认购应收明细的退款类型为AppRevRefundment
update T_SHE_SincerReceiveEntry set FrefundmentMoneyType='AppRevRefundment';
--设置为可以超收
update T_SHE_SincerReceiveEntry set FisCanRevBeyond=1 where FisCanRevBeyond is null;
--设置为非费用
update T_SHE_SincerReceiveEntry set FfeeMoneyType='NotFee';

--诚意认购这边，实收金额的含义和新模式下的已收金额字段含义相同
update T_SHE_SincerReceiveEntry set factRevAmount=factAmount where factRevAmount is null;
--诚意金都是剩余金额可退的
update T_SHE_SincerReceiveEntry set FIsRemainCanRefundment=1 where FIsRemainCanRefundment is null;

--根据退款单将对应的已退金额更新到对应的记录上
--这条可以看出，更新补丁后，必须即刻进行此数据升级，升级完成了才能继续使用.一旦在新收款单中做了相关业务，此升级语句会导致数据错误
update T_SHE_SincerReceiveEntry set fhasRefundmentAmount=
(select top 1 abs(refAmount) from (select a_fpaylistid paylistid,sum(case when a_famount is null then 0 else a_famount end) refamount from t_tmp_revgaizao_se 
where (c_FMoneySysType is null or c_FMoneySysType='SalehouseSys')
and c_FBillTypeEnum='refundment' and c_FSinPurchaseID is not null 
group by a_fpaylistid) t where t.paylistid=T_SHE_SincerReceiveEntry.fid) 
where exists (select top 1 abs(refAmount) from (select a_fpaylistid paylistid,sum(case when a_famount is null then 0 else a_famount end) refamount from t_tmp_revgaizao_se 
where (c_FMoneySysType is null or c_FMoneySysType='SalehouseSys')
and c_FBillTypeEnum='refundment' and c_FSinPurchaseID is not null 
group by a_fpaylistid) t where t.paylistid=T_SHE_SincerReceiveEntry.fid);

--更新已转金额
update T_SHE_SincerReceiveEntry set FHasTransferredAmount=
(select top 1 abs(refAmount) from (select a_fpaylistid paylistid,sum(case when a_famount is null then 0 else a_famount end) refamount from t_tmp_revgaizao_se 
where (c_FMoneySysType is null or c_FMoneySysType='SalehouseSys')
and c_FBillTypeEnum='settlement' 
group by a_fpaylistid) t where t.paylistid=T_SHE_SincerReceiveEntry.fid) 
where exists (select top 1 abs(refAmount) from (select a_fpaylistid paylistid,sum(case when a_famount is null then 0 else a_famount end) refamount from t_tmp_revgaizao_se 
where (c_FMoneySysType is null or c_FMoneySysType='SalehouseSys')
and c_FBillTypeEnum='settlement' 
group by a_fpaylistid) t where t.paylistid=T_SHE_SincerReceiveEntry.fid);

------------------------------------------------------------------------------------------------------------



------------------------------------------------ 预订金的升级 ------------------------------------------

--预订金是增加到应收明细中
insert into T_SHE_PurchasePayListEntry (fid,fheadid,fseq,FMoneyDefineID,FactRevAmount,fisimport,FHasRefundmentAmount,FLimitAmount,fiscanrevbeyond) 
select newbosid('8B4211A8') as fid,a.c_fpurchaseid as fheadid,-1 as fseq,a.a_fmoneydefineid as fmoneydefineid,sum(case when a.a_famount is null then 0 else a.a_famount end) as FactRevAmount,2 as fisimport,sum(case when a.a_FCounteractAmount is null then 0 else a.a_FCounteractAmount end) as FHasRefundmentAmount,sum((case when a.a_FCounteractAmount is null then 0 else a.a_FCounteractAmount end)+(case when a.a_FCanCounteractAmount is null then 0 else a.a_FCanCounteractAmount end)) as FLimitAmount,1 as fiscanrevbeyond from t_tmp_revgaizao_se a 
inner join t_she_moneydefine b on a.a_FMONEYDEFINEID=b.fid 
where fmoneytype='PreconcertMoney' and a.c_FBillTypeEnum!='refundment' and a.c_FBillTypeEnum!='settlement' and a.c_FBillTypeEnum!='adjust' group by a.c_fpurchaseid,a.a_fmoneydefineid;

--将新增的收款明细ID更新到收款单分录上
--更新新增的预订金明细ID到对应收款单分录上
update T_BDC_FDCReceivingBillEntry set FRevListId=
(select top 1 a.fid from T_SHE_PurchasePayListEntry a 
inner join t_bdc_fdcreceivingbill b on a.fheadid=b.FpurchaseObjID 
inner join t_she_moneydefine c on a.fmoneydefineid=c.fid 
where a.fisimport=2 and c.FMoneyType='PreconcertMoney' and T_BDC_FDCReceivingBillEntry.FHeadID=b.fid and T_BDC_FDCReceivingBillEntry.fmoneydefineid=a.fmoneydefineid)
where fisimport=2 and exists (select top 1 a.fid from T_SHE_PurchasePayListEntry a 
inner join t_bdc_fdcreceivingbill b on a.fheadid=b.FpurchaseObjID 
inner join t_she_moneydefine c on a.fmoneydefineid=c.fid 
where a.fisimport=2 and c.FMoneyType='PreconcertMoney' and T_BDC_FDCReceivingBillEntry.FHeadID=b.fid and T_BDC_FDCReceivingBillEntry.fmoneydefineid=a.fmoneydefineid);

--对于已审批的认购单,预订金肯定已全部转出到计划性款项上,更新其已转金额
update T_SHE_PurchasePayListEntry set FhasTransferredAmount=FactRevAmount
where fid in (select a.fid from T_SHE_PurchasePayListEntry a 
inner join t_she_purchase b on a.fheadid=b.fid 
where a.fseq=-1 and a.fisimport=2 and b.faudittime is not null);

--TODO 预订金的可退（这几项不维护也可，退房结算，换房结算不会取这里的值）

--预订退房退款的才有已退金额.根据预定金的退款单更新预订金应收明细的已退金额
--如果现场有2种或以上的预定金类型的收款,这里更新就会有问题 TODO
update T_SHE_PurchasePayListEntry set FHasRefundmentAmount=
(select abs(m.FactRevAmount) from (select a.c_fpurchaseid as fheadid,sum(case when a.a_famount is null then 0 else a.a_famount end) as FactRevAmount from t_tmp_revgaizao_se a 
inner join t_she_moneydefine b on a.a_FMONEYDEFINEID=b.fid 
where fmoneytype='PreconcertMoney' and a.c_FBillTypeEnum='refundment' group by a.c_fpurchaseid,a.a_fmoneydefineid) m where m.fheadid=T_SHE_PurchasePayListEntry.fheadid)
where fisimport=2 and exists (select m.FactRevAmount from (select a.c_fpurchaseid as fheadid,sum(case when a.a_famount is null then 0 else a.a_famount end) as FactRevAmount from t_tmp_revgaizao_se a 
inner join t_she_moneydefine b on a.a_FMONEYDEFINEID=b.fid 
where fmoneytype='PreconcertMoney' and a.c_FBillTypeEnum='refundment' group by a.c_fpurchaseid,a.a_fmoneydefineid) m where m.fheadid=T_SHE_PurchasePayListEntry.fheadid);

--插入时limitAmount为正确，上条FHasRefundmentAmount正确，此条做整合
update T_SHE_PurchasePayListEntry set flimitamount=(case when flimitamount is null then 0 else flimitamount end)+(case when FHasRefundmentAmount is null then 0 else FHasRefundmentAmount end) where fisimport=2;

--TODO 生成预订金转款的转款单



-------------------------------------  认购单计划性款项应收明细的升级  ------------------------------------
--设置应收日期，应收金额，实收日期，已退金额，可退金额，已转金额 字段到新字段上
update T_SHE_PurchasePayListEntry set fappDate=fapDate where fisimport is null and fappdate is null;
update T_SHE_PurchasePayListEntry set fappAmount=fapAmount where fisimport is null and fappAmount is null;
update T_SHE_PurchasePayListEntry set factRevDate=factPayDate where fisimport is null and factRevDate is null;
update T_SHE_PurchasePayListEntry set fhasRefundmentAmount=frefundmentAmount where fisimport is null and fhasRefundmentAmount is null;
update T_SHE_PurchasePayListEntry set fhasTransferredAmount=fhasRemitAmount where fisimport is null and fhasTransferredAmount is null;

--限制金额为 旧可退金额+旧已退金额
update T_SHE_PurchasePayListEntry set flimitAmount=TO_DECIMAL(isnull(fcanRefundmentAmount,0))+TO_DECIMAL(isnull(frefundmentAmount,0)) where fisimport is null and flimitAmount is null;

--实收金额字段，需要用旧实收金额+旧已退金额获得
update T_SHE_PurchasePayListEntry set factRevAmount=TO_DECIMAL(isnull(factPayAmount,0))+TO_DECIMAL(isnull(frefundmentAmount,0)) where fisimport is null and factRevAmount is null;

--TODO 划入(实际划入)费用金额,(实际已转)暂定款金额（这些都是换房结算用到，不影响流程的前提下，可不处理）


-------------------------------------  认购单其他应收明细的升级  ------------------------------------
update T_SHE_PurchaseElsePayListEntry set fappDate=fapDate where fappdate is null;
update T_SHE_PurchaseElsePayListEntry set fappAmount=fapAmount where fappAmount is null;
update T_SHE_PurchaseElsePayListEntry set factRevDate=factPayDate where factRevDate is null;
update T_SHE_PurchaseElsePayListEntry set fhasRefundmentAmount=frefundmentAmount where fhasRefundmentAmount is null;

--限制金额为 旧可退金额+旧已退金额
update T_SHE_PurchaseElsePayListEntry set flimitAmount=TO_DECIMAL(isnull(fcanRefundmentAmount,0))+TO_DECIMAL(isnull(frefundmentAmount,0)) where flimitAmount is null;

--实收金额字段，需要用旧实收金额+旧已退金额获得
update T_SHE_PurchaseElsePayListEntry set factRevAmount=TO_DECIMAL(isnull(factPayAmount,0))+TO_DECIMAL(isnull(frefundmentAmount,0)) where factRevAmount is null;

-------------------------------------   补差款应收明细的升级 ------------------------------------

--根据补差单生成补差应收明细
insert into T_SHE_AreaCompensateRevList (fid,fheadid,fmoneydefineid,fRefundmentMoneyType,fAppAmount,fLimitAmount,fRevMoneyType,fisimport)
select newbosid('179EB1A2') as fid,a.fid as fheadid,(select top 1 fid from t_she_moneydefine where FMoneyType='CompensateAmount' and fsystype='SalehouseSys') as fmoneydefineid,(case when a.FCompensateAmount<0 then 'AppRefundment' else 'AppRevRefundment' end) as fRefundmentMoneyType,(case when a.FCompensateAmount is null then 0 else a.FCompensateAmount end) as fapprevamount,abs(case when a.FCompensateAmount is null then 0 else a.FCompensateAmount end) as flimitamount,'AppRev' as frevmoneytype,2 as fisimport 
from T_SHE_RoomAreaCompensate a 
where a.FCompensateState in ('COMAUDITTED','COMRECEIVED');

--设置款项为补差的明细类型为补差类型
update t_bdc_fdcreceivingbillentry set frevlisttype='areaCompensate' where fisimport=2 and fmoneydefineid in (select fid from t_she_moneydefine where fmoneytype='CompensateAmount' and fsystype='SalehouseSys');

--设置款项为补差的收款单分录的revListId
update t_bdc_fdcreceivingbillentry set frevlistid=
(select top 1 g.fid from t_bdc_fdcreceivingbill f 
inner join t_she_room b on f.froomid=b.fid 
inner join t_she_purchase c on b.flastpurchaseid=c.fid 
inner join T_SHE_RoomAreaCompensate e on e.froomid=b.fid 
inner join T_SHE_AreaCompensateRevList g on g.fheadid=e.fid 
where t_bdc_fdcreceivingbillentry.fmoneydefineid in (select fid from t_she_moneydefine where fmoneytype='CompensateAmount' and fsystype='SalehouseSys') and f.fisimport=2 and t_bdc_fdcreceivingbillentry.fheadid=f.fid)
where fisimport=2 and exists (select top 1 g.fid from t_bdc_fdcreceivingbill f 
inner join t_she_room b on f.froomid=b.fid 
inner join t_she_purchase c on b.flastpurchaseid=c.fid 
inner join T_SHE_RoomAreaCompensate e on e.froomid=b.fid 
inner join T_SHE_AreaCompensateRevList g on g.fheadid=e.fid 
where t_bdc_fdcreceivingbillentry.fmoneydefineid in (select fid from t_she_moneydefine where fmoneytype='CompensateAmount' and fsystype='SalehouseSys') and f.fisimport=2 and t_bdc_fdcreceivingbillentry.fheadid=f.fid);

--汇总收款单的金额到补差明细的已收金额上
update T_SHE_AreaCompensateRevList set FActRevAmount=(
select sum(case when FRevAmount is null then 0 else FRevAmount end) 
from t_bdc_fdcreceivingbillentry a 
inner join t_she_moneydefine b on a.fmoneydefineid=b.fid 
where b.fmoneytype='CompensateAmount' and b.fsystype='SalehouseSys'
group by a.FRevListId having a.frevlistid=T_SHE_AreaCompensateRevList.fid
)where fisimport=2 and exists (
select a.FRevListId 
from t_bdc_fdcreceivingbillentry a 
inner join t_she_moneydefine b on a.fmoneydefineid=b.fid 
where b.fmoneytype='CompensateAmount' and b.fsystype='SalehouseSys'
group by a.FRevListId having a.frevlistid=T_SHE_AreaCompensateRevList.fid);

--负的收款需要更新已退金额字段
update T_SHE_AreaCompensateRevList set FHasRefundmentAmount=abs(FActRevAmount) where fisimport=2 and FActRevAmount<0;

--将负的补差的收款单的单据类型修改为退款
update t_bdc_fdcreceivingbill set FRevBillType='refundment' where fid in (
select c.fid from t_bdc_fdcreceivingbillentry a 
inner join t_she_moneydefine b on a.fmoneydefineid=b.fid 
inner join t_bdc_fdcreceivingbill c on a.fheadid=c.fid 
where a.fisimport=2 and b.fmoneytype='CompensateAmount' and b.fsystype='SalehouseSys' and c.FRevBillType='gathering' and a.FRevAmount<0
);

-----------------------------------  换房结算及客户收款明细的升级  ---------------------------------

insert into T_SHE_CusRevList (fid,FFdcCustomerID,fmoneydefineid,FactRevAmount,fisimport,FHasRefundmentAmount,FLimitAmount) 
select newbosid('C64ADF7B') as fid,b.fid as FFdcCustomerID,a.a_fmoneydefineid as fmoneydefineid,sum(case when a.a_famount is null then 0 else a.a_famount end) as FactRevAmount,2 as fisimport,sum(case when a.a_FCounteractAmount is null then 0 else a.a_FCounteractAmount end) as FHasRefundmentAmount,sum((case when a.a_FCounteractAmount is null then 0 else a.a_FCounteractAmount end)+(case when a.a_FCanCounteractAmount is null then 0 else a.a_FCanCounteractAmount end)) as FLimitAmount from t_tmp_revgaizao_se a 
left join t_she_fdccustomer b on a.b_fpayerid=b.fsyscustomerid 
inner join t_she_moneydefine c on a.a_FMONEYDEFINEID=c.fid 
where fmoneytype='PreMoney' and a.c_FBillTypeEnum!='refundment' and a.c_FBillTypeEnum!='settlement' and a.c_FBillTypeEnum!='adjust' and c_FGathering='CustomerRev'
group by b.fid,a.a_fmoneydefineid;

--TODO 更新暂定款收款单的revlistid


--转款来源分录的处理

--TODO 待整理
--来源分录需要待预收明细ID等都设置好后再进行



--连接条件：setleID。红冲单的对方科目和蓝单的收款科目相同。金额相反。
insert into T_BDC_TransferSourceEntry (fid,FHeadId,FFromRevListId,FFromRevListType,FAmount,fisimport,folddbSettlemententryid,ffrommoneydefineid)
select newbosid('DCBE7750') as fid,d.fid as fheadid,c.frevlistid as ffromrevlistid,'tnnd' as ffromrevlisttype,abs(case when b.a_famount is null then 0 else b.a_famount end) as famount,2 as fisimport,b.a_fid as folddbSettlemententryid,b.a_fmoneydefineid from t_tmp_revgaizao_se a 
inner join t_tmp_revgaizao_se b on a.c_FSettlementBillID=b.b_fid and a.a_FAccountID=b.a_FOppSubjectID and a.a_FAmount is not null and b.a_famount is not null and a.a_famount+b.a_famount=0 
left join t_bdc_fdcreceivingbillentry c on b.a_ffcounteractid=c.folddbid and c.fisimport=2  
inner join t_bdc_fdcreceivingbillentry d on a.a_fid=d.folddbid and d.fisimport=2
where b.c_FBillTypeEnum='settlement';


--------------------------------------------
update t_bdc_fdcreceivingbillentry set frevlisttype='sincerityPur' where fisimport=2 and frevlistid in (select fid from T_SHE_SincerReceiveEntry);
update t_bdc_fdcreceivingbillentry set frevlisttype='purchaseRev' where fisimport=2 and frevlistid in (select fid from T_SHE_PurchasePayListEntry);
update t_bdc_fdcreceivingbillentry set frevlisttype='purElseRev' where fisimport=2 and frevlistid in (select fid from T_SHE_PurchaseElsePayListEntry);
update t_bdc_fdcreceivingbillentry set frevlisttype='fdcCustomerRev' where fisimport=2 and frevlistid in (select fid from T_she_cusrevlist);
update t_bdc_fdcreceivingbillentry set frevlisttype='areaCompensate' where fisimport=2 and frevlistid in (select fid from T_SHE_AreaCompensateRevList);


update T_BDC_TransferSourceEntry set FFromRevListType='sincerityPur' where fisimport=2 and FFromRevListId in (select fid from T_SHE_SincerReceiveEntry);
update T_BDC_TransferSourceEntry set FFromRevListType='purchaseRev' where fisimport=2 and FFromRevListId in (select fid from T_SHE_PurchasePayListEntry);
update T_BDC_TransferSourceEntry set FFromRevListType='purElseRev' where fisimport=2 and FFromRevListId in (select fid from T_SHE_PurchaseElsePayListEntry);
update T_BDC_TransferSourceEntry set FFromRevListType='fdcCustomerRev' where fisimport=2 and FFromRevListId in (select fid from T_she_cusrevlist);
update T_BDC_TransferSourceEntry set FFromRevListType='areaCompensate' where fisimport=2 and FFromRevListId in (select fid from T_SHE_AreaCompensateRevList);

--补差的收款的收款业务类型修改为面积补差
update t_bdc_fdcreceivingbill set FRevBizType='areaCompensate' where fid in 
(select fheadid from t_bdc_fdcreceivingbillentry a 
inner join t_she_moneydefine b on a.fmoneydefineid=b.fid 
where b.FMoneyType='CompensateAmount' and a.fisimport=2);

--原换房结算生成的收款单，未记录收款金额，需要在升级上来的新收款单上汇总分录进行设置
update t_bdc_fdcreceivingbill set FAmount=(select sum(a.frevamount) from t_bdc_fdcreceivingbillentry a where a.fheadid=t_bdc_fdcreceivingbill.fid) 
where fisimport=2 and FRevBillType='transfer' and (case when FAmount is null then 0 else FAmount end)=0;

update t_bdc_fdcreceivingbill set FOriginalAmount=famount where fisimport=2 and FRevBillType='transfer' and (case when FOriginalAmount is null then 0 else FOriginalAmount end)=0;

--备份原收款单的BOTP纪录(售楼的)
select b.fid b_fid,a.fid a_fid,a.fsrcobjectid,a.fdestobjectid 
into t_tmp_bot_skdgz_bat_s 
from T_BOT_Relation a 
inner join t_bdc_fdcreceivingbill b on a.fsrcobjectid=b.folddbid 
where a.fsrcentityid='FA44FD5B' and a.fdestentityid='2652E01E' and b.fisimport=2;

--更新BOTP纪录(售楼的)
update T_BOT_Relation set fsrcobjectid=
(select a.fid from t_bdc_fdcreceivingbill a where a.fisimport=2 and t_bot_relation.fsrcobjectid=a.folddbid)
where fsrcentityid='FA44FD5B' and fdestentityid='2652E01E' and exists (select a.fid from t_bdc_fdcreceivingbill a where a.fisimport=2 and t_bot_relation.fsrcobjectid=a.folddbid)
