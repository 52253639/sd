package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.sql.SQLException;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.InviteMonthPlanEntrysInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class InviteMonthPlanEditUICTEx extends InviteMonthPlanEditUI
{

	public InviteMonthPlanEditUICTEx() throws Exception
	{
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void loadFields()
	{
		// TODO Auto-generated method stub
		super.loadFields();  

		System.out.println("----------填充 实际中标日期--------------");

		System.out.println(kdtEntry.getRowCount());
		if (editData == null || editData.getId() == null)
		{
			return;
		}
		String strBillId = editData.getId().toString();
		for (int i = 0; i < kdtEntry.getRowCount(); i++)
		{

			if (kdtEntry.getRow(i) == null
					|| kdtEntry.getRow(i).getCell("actInviteForm") == null)
			{
				break;
			}

			// longnumber actResultAuditDate
			if (kdtEntry.getRow(i).getCell("actInviteForm").getValue() != null)
			{
				String isZhiJie = kdtEntry.getRow(i).getCell("actInviteForm")
						.getValue().toString();
				if ("直接委托".equals(isZhiJie))
				{
					System.out.println(isZhiJie);
					if (kdtEntry.getRow(i).getCell("longNumber").getValue() != null)
					{

						String strLongNum = kdtEntry.getRow(i).getCell(
								"longNumber").getValue().toString();
						String strSQL = " select distinct t1.fid, t12.fname_l2 as formName,t2.flongnumber , t17.faudittime "
								+ " from T_INV_InviteMonthPlan t1 "
								+ "   left join T_INV_InviteMonthPlanEntrys t2 on t1.fid = t2.fparentid "
								+ "   left join T_INV_InviteProjectEntries t4 on t4.FPROGRAMMINGCONTRACTID = t2.FProgrammingContractID "
								+ "   left join T_INV_InviteProject t5 on t5.fid = t4.fparentid  "
								+ "   left join T_INV_DirectAccepterResult  t17 on t5.fid = t17.finviteprojectid "
								+ "   left join T_INV_InviteForm t12 on t12.fid = t5.FInviteFormId     "
								+ " where t1.fid = '"
								+ strBillId
								+ "'  and t12.fname_l2 = '直接委托' and t2.flongnumber='"
								+ strLongNum + "'";

						FDCSQLBuilder builder = new FDCSQLBuilder();
						builder.clear();
						builder.appendSql(strSQL);
						IRowSet rs;
						try
						{
							rs = builder.executeQuery();
							while (rs.next())
							{
								Date date = rs.getDate("faudittime");
								kdtEntry.getRow(i)
										.getCell("actResultAuditDate")
										.setValue(date);
							}
						}
						catch (BOSException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (SQLException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			}

		}
		
		
		executeDateLogic();

	}

	@Override
	public void onLoad() throws Exception
	{
		// TODO Auto-generated method stub
		super.onLoad();
		System.out.println("----------扩展调整--------------");
		System.out.println("修改日期不能修改逻辑");
		executeDateLogic();    
	}

	private void executeDateLogic()
	{

		for (int i = 0; i < kdtEntry.getRowCount(); i++)
		{
			boolean leaf = true;
			IRow row = kdtEntry.getRow(i);
			InviteMonthPlanEntrysInfo entry = (InviteMonthPlanEntrysInfo) row
					.getUserObject();
			if (entry.getProgrammingContractID() != null)
			{
				String pcId = entry.getProgrammingContractID().toString();
				System.out.println(entry.getName());
				if (leafMap.get(pcId) != null
						&& !((Boolean) leafMap.get(pcId)).booleanValue())
					leaf = false;
			}
			if (leaf && row.getCell("actInviteForm").getValue() != null)
			{
				row.getCell("requiredInviteForm").getStyleAttributes()
						.setLocked(true);
				row.getCell("requiredInviteForm").getStyleAttributes()
						.setBackground(new Color(245, 245, 245));
			}
			if (row.getCell("actContractAuditDate").getValue() != null || !leaf)
			{
				//修改产品BUG,去掉判断
				//if (!leaf)
				//{
					row.getCell("indicator").getStyleAttributes().setLocked(
							true);
					row.getCell("requiredInviteForm").getStyleAttributes()
							.setLocked(true);
					row.getCell("paperDate").getStyleAttributes().setLocked(
							true);
					row.getCell("documentsAuditDate").getStyleAttributes()
							.setLocked(true);
					row.getCell("resultAuditDate").getStyleAttributes()
							.setLocked(true);
					row.getCell("contractAuditDate").getStyleAttributes()
							.setLocked(true);
					row.getCell("enterAuditDate").getStyleAttributes()
							.setLocked(true);
				//}
				row.getCell("indicator").getStyleAttributes().setBackground(
						new Color(245, 245, 245));
				row.getCell("requiredInviteForm").getStyleAttributes()
						.setBackground(new Color(245, 245, 245));
				row.getCell("paperDate").getStyleAttributes().setBackground(
						new Color(245, 245, 245));
				row.getCell("documentsAuditDate").getStyleAttributes()
						.setBackground(new Color(245, 245, 245));
				row.getCell("resultAuditDate").getStyleAttributes()
						.setBackground(new Color(245, 245, 245));
				row.getCell("contractAuditDate").getStyleAttributes()
						.setBackground(new Color(245, 245, 245));
				row.getCell("enterAuditDate").getStyleAttributes()
						.setBackground(new Color(245, 245, 245));
			}
			else if (row.getCell("actResultAuditDate").getValue() != null)
			{
				row.getCell("resultAuditDate").getStyleAttributes().setLocked(
						true);
				row.getCell("documentsAuditDate").getStyleAttributes()
						.setLocked(true);
				row.getCell("paperDate").getStyleAttributes().setLocked(true);
				row.getCell("resultAuditDate").getStyleAttributes()
						.setBackground(new Color(245, 245, 245));
				row.getCell("documentsAuditDate").getStyleAttributes()
						.setBackground(new Color(245, 245, 245));
				row.getCell("paperDate").getStyleAttributes().setBackground(
						new Color(245, 245, 245));
			}
			else if (row.getCell("actDocumentsAuditDate").getValue() != null)
			{
				row.getCell("documentsAuditDate").getStyleAttributes()
						.setLocked(true);
				row.getCell("paperDate").getStyleAttributes().setLocked(true);
				row.getCell("documentsAuditDate").getStyleAttributes()
						.setBackground(new Color(245, 245, 245));
				row.getCell("paperDate").getStyleAttributes().setBackground(
						new Color(245, 245, 245));
			}
			// try
			// {
			// setRequiredColor(row);
			// }
			// catch(EASBizException e)
			// {
			// e.printStackTrace();
			// }
			// catch(BOSException e)
			// {
			// e.printStackTrace();
			// }
		}

	}

}
