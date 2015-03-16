package com.kingdee.eas.fdc.invite;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.common.client.SysContext;
import com.sun.xml.messaging.saaj.util.ByteInputStream;

public class AssessSetting implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1293436601656286004L;

	private Font lowFont = null;

	private Font highFont = null;

	private Color lowColor = Color.BLUE;

	private Color highColor = Color.RED;

	private BigDecimal allowHigh = new BigDecimal("110");

	private BigDecimal allowLow = new BigDecimal("90");

	// 基准
	private QuoteStandEnum stand = QuoteStandEnum.BidderMinPrice;

	// 投标人显示
	private QuotePeopleDisplayEnum bidderDisplay = QuotePeopleDisplayEnum.BidderFullName;

	// 对应招标清单
	private NewListingInfo listingInfo = null;

	public AssessSetting() {

	}

	public AssessSetting(NewListingInfo inviteListingInfo) {
		// 载入数据库设置
		listingInfo = inviteListingInfo;
		AssessSetting assessSetting = null;
		EntityViewInfo entityInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("inviteListing.id", listingInfo.getId().toString());
		filterInfo.appendFilterItem("creator.id", SysContext.getSysContext().getCurrentUserInfo().getId().toString());
		entityInfo.setFilter(filterInfo);
		QuotingPriceSetCollection priceSetCollection;
		try {
			priceSetCollection = QuotingPriceSetFactory.getRemoteInstance().getQuotingPriceSetCollection(entityInfo);
			if (priceSetCollection.size() > 0) {
				byte[] b = priceSetCollection.get(0).getValue();
				// ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(
				// b);
				// ObjectInputStream inputStream = new ObjectInputStream(
				// arrayInputStream);
				ObjectInputStream objectInputStream = new ObjectInputStream(new ByteInputStream(b, 0,b.length));
				// Object obj = objectInputStream.readObject();

				assessSetting = (AssessSetting) objectInputStream.readObject();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (assessSetting != null) {
			this.lowFont = assessSetting.lowFont;
			this.highFont = assessSetting.highFont;
			this.lowColor = assessSetting.lowColor;
			this.highColor = assessSetting.highColor;
			this.allowHigh = assessSetting.allowHigh;
			this.allowLow = assessSetting.allowLow;
			this.bidderDisplay = assessSetting.bidderDisplay;
			this.stand = assessSetting.stand;
			this.listingInfo = assessSetting.listingInfo;
		}

	}

	public boolean save() {
		try {
			if (listingInfo == null) {
				return false;
			}
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
			objectOutputStream.writeObject(this);
			byte[] b = arrayOutputStream.toByteArray();

			//
			EntityViewInfo entityInfo = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.appendFilterItem("inviteListing.id", listingInfo.getId().toString());
			filterInfo.appendFilterItem("creator.id", SysContext.getSysContext().getCurrentUserInfo().getId().toString());
			entityInfo.setFilter(filterInfo);
			QuotingPriceSetCollection priceSetCollection = QuotingPriceSetFactory.getRemoteInstance().getQuotingPriceSetCollection(entityInfo);
			if (priceSetCollection.size() > 0) {
				QuotingPriceSetInfo gatherInfo = priceSetCollection.get(0);
				gatherInfo.setValue(b);
				QuotingPriceSetFactory.getRemoteInstance().update(new ObjectUuidPK(gatherInfo.getId()), gatherInfo);
			} else {
				QuotingPriceSetInfo gatherInfo = new QuotingPriceSetInfo();
				gatherInfo.setInviteListing(listingInfo);
				gatherInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
				gatherInfo.setValue(b);
				QuotingPriceSetFactory.getRemoteInstance().addnew(gatherInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public BigDecimal getAllowHigh() {
		return allowHigh;
	}

	public void setAllowHigh(BigDecimal allowHigh) {
		this.allowHigh = allowHigh;
	}

	public BigDecimal getAllowLow() {
		return allowLow;
	}

	public void setAllowLow(BigDecimal allowLow) {
		this.allowLow = allowLow;
	}

	public QuoteStandEnum getStand() {
		return stand;
	}

	public QuotePeopleDisplayEnum getBidderDisplay() {
		return bidderDisplay;
	}

	public void setBidderDisplay(QuotePeopleDisplayEnum bidderDisplay) {
		this.bidderDisplay = bidderDisplay;
	}

	public Font getHighFont() {
		return highFont;
	}

	public void setHighFont(Font highFont) {
		this.highFont = highFont;
	}

	public Font getLowFont() {
		return lowFont;
	}

	public void setLowFont(Font lowFont) {
		this.lowFont = lowFont;
	}

	public Color getHighColor() {
		return highColor;
	}

	public void setHighColor(Color highColor) {
		this.highColor = highColor;
	}

	public Color getLowColor() {
		return lowColor;
	}

	public void setLowColor(Color lowColor) {
		this.lowColor = lowColor;
	}

	public void setStand(QuoteStandEnum stand) {
		this.stand = stand;
	}

}
