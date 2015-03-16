--这个脚本不要轻易执行啊！！！
--drop table t_tmp_revgaizao_s;

--drop table t_tmp_revgaizao_se;

select count(*) from t_bdc_fdcreceivingbillentry;
--delete from t_bdc_fdcreceivingbillentry where fisimport=2;


select count(*) from t_bdc_fdcreceivingbill;
--delete from t_bdc_fdcreceivingbill where fisimport=2;


select count(*) from T_BDC_RevFDCCustomerEntry where fisimport=2;
--delete from T_BDC_RevFDCCustomerEntry where fisimport=2;


select count(*) from t_tmp_revgaizao_spe;
--这个不能轻易drop啊。备份的数据
--以下3句，务必确认 t_tmp_revgaizao_spe 中有数据。务必小心
--delete from T_SHE_SincerReceiveEntry;
--insert into T_SHE_SincerReceiveEntry select * from t_tmp_revgaizao_spe;
--drop table t_tmp_revgaizao_spe;

select count(*) from t_tmp_revgaizao_spure;
--delete from T_SHE_PurchasePayListEntry;
--insert into T_SHE_PurchasePayListEntry select * from t_tmp_revgaizao_spure;
--drop table t_tmp_revgaizao_spure;

select count(*) from t_tmp_revgaizao_spuroe;
--delete from T_SHE_PurchaseElsePayListEntry;
--insert into T_SHE_PurchaseElsePayListEntry select * from t_tmp_revgaizao_spuroe;
--drop table t_tmp_revgaizao_spuroe;
--
select * from T_SHE_PurchasePayListEntry where fisimport=2;
--delete from T_SHE_PurchasePayListEntry where fisimport=2;


select * from T_SHE_AreaCompensateRevList where fisimport=2
--delete from T_SHE_AreaCompensateRevList where fisimport=2;


select * from T_BDC_TransferSourceEntry where fisimport=2;
--delete from T_BDC_TransferSourceEntry where fisimport=2;

select * from T_SHE_CusRevList where fisimport=2;
--delete from T_SHE_CusRevList where fisimport=2;

select count(*) from t_tmp_bot_skdgz_bat_s;
--drop table t_tmp_bot_skdgz_bat_s;