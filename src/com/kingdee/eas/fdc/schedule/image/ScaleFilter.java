package com.kingdee.eas.fdc.schedule.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class ScaleFilter {

	private int width;
	private int height;

    /**
     * Construct a ScaleFilter.
     */
	public ScaleFilter() {
		width = 78;
		height = 42;
	}
    
	public BufferedImage filter( BufferedImage src, BufferedImage dst, double scale){
		width = (int)((double)src.getWidth() * scale);
		height = (int) ((double)src.getHeight() * scale);
		if ( dst == null ) {
			ColorModel dstCM = src.getColorModel();
			dst = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster( width, height ), dstCM.isAlphaPremultiplied(), null);
		}

		Image scaleImage = src.getScaledInstance( width, height, Image.SCALE_AREA_AVERAGING );
		Graphics2D g = dst.createGraphics();
		g.drawImage( scaleImage, 0, 0, width, height, null );
		g.dispose();

        return dst;
	}

    public BufferedImage filter( BufferedImage src, BufferedImage dst ) {
    	double imgD = 0.0;		
		imgD = (double)src.getWidth()/(double)src.getHeight();
		double standardWidth = 78;
		double standardHeight = 42;
		double standardD = standardWidth / standardHeight;
		if(imgD<standardD){
			height = 42;
			width = (int)(imgD * 42);
		}else{
			width = 78;
			height = (int)(standardWidth/imgD);
		}
		if ( dst == null ) {
			ColorModel dstCM = src.getColorModel();
			dst = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster( (int)standardWidth, (int)standardHeight ), dstCM.isAlphaPremultiplied(), null);
		}

		Image scaleImage = src.getScaledInstance( width, height, Image.SCALE_AREA_AVERAGING );
		Graphics2D g = dst.createGraphics();
		g.drawImage( scaleImage, ((int)standardWidth-width)/2, ((int)standardHeight-height)/2, width, height, null );
		g.dispose();

        return dst;
    }

	public String toString() {
		return "Distort/Scale";
	}
}
