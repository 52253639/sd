package com.kingdee.eas.fdc.sellhouse.app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchBooksCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchBooksInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchMaterialsCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchStepCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryTwoCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryTwoInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo;
import com.kingdee.util.NumericExceptionSubItem;

public class RoomPropertyBatchControllerBean extends AbstractRoomPropertyBatchControllerBean
{
	private static Logger logger =
		Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomPropertyBatchControllerBean");

	protected IObjectPK _save(Context ctx, IObjectValue model)
	throws BOSException, EASBizException {

		//先存批量产权
		RoomPropertyBatchInfo batchInfo = (RoomPropertyBatchInfo)model;
		if(batchInfo.getId() == null)
		{
			batchInfo.setId(BOSUuid.create(batchInfo.getBOSType()));
		}

		RoomPropertyBatchBooksCollection baseBooksCols = batchInfo.getBook();

		List updateRoomId = new ArrayList();
		//然后存产权方案
		RoomPropertyBatchBooksCollection saveBatchBookCols = new RoomPropertyBatchBooksCollection();
		for(int index = 0; index < baseBooksCols.size(); ++index)
		{
			RoomPropertyBatchBooksInfo tmpBatchBookInfo = baseBooksCols.get(index);
			if(tmpBatchBookInfo.getBook().getRoom() != null)
			{
				updateRoomId.add(tmpBatchBookInfo.getBook().getRoom().getId().toString());
			}

			//根据批量产权的步骤和资料获取即将保存的产权
			IObjectPK tmpBookPK = getProBookInfoByBatchInfoPk(ctx, tmpBatchBookInfo, batchInfo);

			//重新构造批量产权的产权分录
			RoomPropertyBatchBooksInfo saveBatchBookInfo = new RoomPropertyBatchBooksInfo();
			RoomPropertyBookInfo saveBookInfo = new RoomPropertyBookInfo();
			saveBookInfo.setId(BOSUuid.read(tmpBookPK.toString()));
			saveBatchBookInfo.setBook(saveBookInfo);
			saveBatchBookInfo.setParent(batchInfo);
			saveBatchBookCols.add(saveBatchBookInfo);
		}
		batchInfo.getBook().clear();
		batchInfo.getBook().addCollection(saveBatchBookCols);

		IObjectPK pk = super._save(ctx, model);

		//更新房间的的办理状态
		for(int index = 0; index < updateRoomId.size(); ++index)
		{
			String tmpRoomId = String.valueOf(updateRoomId.get(index));
			RoomInfo tmpRoom = new RoomInfo();
			tmpRoom.setId(BOSUuid.read(tmpRoomId));
			tmpRoom.setRoomBookState(batchInfo.getBookState());

			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("roomBookState");
			RoomFactory.getLocalInstance(ctx).updatePartial(tmpRoom, sic);

		}

		return pk;
	}

	private IObjectPK getProBookInfoByBatchInfoPk(Context ctx, RoomPropertyBatchBooksInfo parambatchBooksEntryInfo, 
			RoomPropertyBatchInfo paramBatchInfo) throws EASBizException, BOSException
			{
		//除了两个分录和方案属性，其他属性值都在客户端已经赋值了
		RoomPropertyBookInfo resultInfo = parambatchBooksEntryInfo.getBook();

		if(resultInfo.getId() == null)
		{
			resultInfo.setId(BOSUuid.create(resultInfo.getBOSType()));
		}
		resultInfo.setBatch(paramBatchInfo);
		resultInfo.setPropertyDoScheme(paramBatchInfo.getScheme());

		/**
		 * 如果该房间有存在的产权则将其删除，然后重新新增一个新的产权
		 */

		//删除已经存在的房间
		RoomInfo room = resultInfo.getRoom();
		if(room != null && room.getId() != null)
		{
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));

			if(RoomPropertyBookFactory.getLocalInstance(ctx).exists(filter))
			{
				RoomPropertyBookFactory.getLocalInstance(ctx).delete(filter);
			}
		}


		//新增与房间对应的产权
		RoomPropertyBatchStepCollection stepCols = paramBatchInfo.getStep();
		RoomPropertyBookEntryCollection entryCols = new RoomPropertyBookEntryCollection();
		for(int index = 0; index < stepCols.size(); ++index)
		{
			RoomPropertyBookEntryInfo tmpEntryInfo = new RoomPropertyBookEntryInfo();

			tmpEntryInfo.setParent(resultInfo);
			tmpEntryInfo.setSeq(stepCols.get(index).getSeq());
			tmpEntryInfo.setNumber(stepCols.get(index).getNumber());

			tmpEntryInfo.setName(stepCols.get(index).getName());
			tmpEntryInfo.setIsFinish(stepCols.get(index).isIsFinish());
			tmpEntryInfo.setDescription(stepCols.get(index).getDescription());

			tmpEntryInfo.setProcessDate(stepCols.get(index).getDate());

			entryCols.add(tmpEntryInfo);
		}

		resultInfo.getEntry().clear();
		resultInfo.getEntry().addCollection(entryCols);


		RoomPropertyBatchMaterialsCollection matCols = paramBatchInfo.getMaterial();
		RoomPropertyBookEntryTwoCollection entryTwoCols = new RoomPropertyBookEntryTwoCollection();
		for(int index = 0; index < matCols.size(); ++index)
		{
			RoomPropertyBookEntryTwoInfo tmpEntryTwoInfo = new RoomPropertyBookEntryTwoInfo();

			tmpEntryTwoInfo.setParent(resultInfo);
			tmpEntryTwoInfo.setSeq(matCols.get(index).getSeq());
			tmpEntryTwoInfo.setNumber(matCols.get(index).getNumber());

			tmpEntryTwoInfo.setName(matCols.get(index).getName());
			tmpEntryTwoInfo.setIsFinish(matCols.get(index).isIsFinish());
			tmpEntryTwoInfo.setDescription(matCols.get(index).getDescription());

			tmpEntryTwoInfo.setProcessDate(matCols.get(index).getDate());

			entryTwoCols.add(tmpEntryTwoInfo);
		}
		resultInfo.getEntryTwo().clear();
		resultInfo.getEntryTwo().addCollection(entryTwoCols);

		return RoomPropertyBookFactory.getLocalInstance(ctx).save(resultInfo);
			}

	protected IObjectValue _getValue(Context ctx, IObjectPK pk,
			SelectorItemCollection selector) throws BOSException,
			EASBizException {
		IObjectValue batchInfo = super._getValue(ctx, pk, selector);
		RoomPropertyBatchInfo batchFullInfo = (RoomPropertyBatchInfo)batchInfo;
		//获取产权分录详细信息
		RoomPropertyBatchBooksCollection batchBookCols = batchFullInfo.getBook();

		RoomPropertyBatchBooksCollection batchBookFullCols = getBatchBookFullInformCols(ctx, batchBookCols, batchFullInfo);
		batchFullInfo.getBook().clear();
		batchFullInfo.getBook().addCollection(batchBookFullCols);
		return batchFullInfo;
	}

	private RoomPropertyBatchBooksCollection getBatchBookFullInformCols(Context ctx, RoomPropertyBatchBooksCollection batchBooks,
			RoomPropertyBatchInfo parent) throws BOSException
			{
		//根据分录中的book获取更加信息的book
		RoomPropertyBatchBooksCollection resultBooks = new RoomPropertyBatchBooksCollection();

		Set bookIdSet = new HashSet();
		for(int index = 0; index < batchBooks.size(); ++index)
		{
			bookIdSet.add(batchBooks.get(index).getBook().getId().toString());
		}

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("orgUnit.id");
		view.getSelector().add("orgUnit.number");
		view.getSelector().add("orgUnit.longNumber");
		view.getSelector().add("batch.id");
		view.getSelector().add("batch.number");

		view.getSelector().add("propertyDoScheme.*");
		view.getSelector().add("transactor.id");
		view.getSelector().add("transactor.number");
		view.getSelector().add("transactor.name");

		view.getSelector().add("room.*");

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", bookIdSet, CompareType.INCLUDE));
		view.setFilter(filter);

		RoomPropertyBookCollection bookCols = RoomPropertyBookFactory.getLocalInstance(ctx).getRoomPropertyBookCollection(view);

		for(int index = 0; index < batchBooks.size(); ++index)
		{
			RoomPropertyBatchBooksInfo tmpBatchBooksInfo = batchBooks.get(index);
			RoomPropertyBatchBooksInfo tmpBatchBooksSave = new RoomPropertyBatchBooksInfo();

			tmpBatchBooksSave.setId(tmpBatchBooksInfo.getId());
			tmpBatchBooksSave.setSeq(tmpBatchBooksInfo.getSeq());
			tmpBatchBooksSave.setBook(getRoomBookFullInfo(tmpBatchBooksInfo, bookCols));
			tmpBatchBooksSave.setParent(tmpBatchBooksInfo.getParent());

			resultBooks.add(tmpBatchBooksSave);
		}

		return resultBooks;
			}
	private RoomPropertyBookInfo getRoomBookFullInfo(RoomPropertyBatchBooksInfo batchBooksInfo, RoomPropertyBookCollection bookCols)
	{
		RoomPropertyBookInfo result = new RoomPropertyBookInfo();
		for(int index = 0; index < bookCols.size(); ++index)
		{
			if(bookCols.get(index).getId().equals(batchBooksInfo.getBook().getId()))
			{
				result = bookCols.get(index);
				break;
			}
		}
		return result;
	}

	protected void checkBill(Context ctx, IObjectValue model)
	throws BOSException, EASBizException {
		//办理批次重复检测
		RoomPropertyBatchInfo batchInfo = (RoomPropertyBatchInfo)model;

		String number = batchInfo.getNumber();
		String id = batchInfo.getId().toString();

		FilterInfo filterId = new FilterInfo();
		filterId.getFilterItems().add(new FilterItemInfo("id", id));
		if(RoomPropertyBatchFactory.getLocalInstance(ctx).exists(filterId))
		{
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			if(batchInfo.getSellProject() != null)
			{
				String sellPrjId = batchInfo.getSellProject().getId().toString();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellPrjId));

				if(RoomPropertyBatchFactory.getLocalInstance(ctx).exists(filter))
				{
					throw new EASBizException(new NumericExceptionSubItem("20", "办理批次重复，不能执行此操作！"));
				}
			}
		}
		else
		{
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			if(batchInfo.getSellProject() != null)
			{
				String sellPrjId = batchInfo.getSellProject().getId().toString();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellPrjId));

				if(RoomPropertyBatchFactory.getLocalInstance(ctx).exists(filter))
				{
					throw new EASBizException(new NumericExceptionSubItem("20", "办理批次重复，不能执行此操作！"));
				}
			}
		}
	}
	protected void setPropsForBill(Context ctx, FDCBillInfo billInfo)
	throws EASBizException, BOSException {
		// TODO Auto-generated method stub
		//super.setPropsForBill(ctx, billInfo);
	}
}