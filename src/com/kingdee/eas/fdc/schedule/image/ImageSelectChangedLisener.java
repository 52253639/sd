package com.kingdee.eas.fdc.schedule.image;

import java.awt.image.BufferedImage;

public interface ImageSelectChangedLisener {
	BufferedImage getOriginalImg(int i);
	void imageSelectChanged(int i);
}
