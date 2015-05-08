package com.kingdee.eas.fdc.schedule.image;

import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;



public class GrayFilter implements BufferedImageOp, Cloneable{
	protected boolean canFilterIndexColorModel = false;
	public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel dstCM) {
        if ( dstCM == null )
            dstCM = src.getColorModel();
        return new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), dstCM.isAlphaPremultiplied(), null);
    }
    
    public Rectangle2D getBounds2D( BufferedImage src ) {
        return new Rectangle(0, 0, src.getWidth(), src.getHeight());
    }
    
    public Point2D getPoint2D( Point2D srcPt, Point2D dstPt ) {
        if ( dstPt == null )
            dstPt = new Point2D.Double();
        dstPt.setLocation( srcPt.getX(), srcPt.getY() );
        return dstPt;
    }

    public RenderingHints getRenderingHints() {
        return null;
    }

    public BufferedImage filter( BufferedImage src, BufferedImage dst ) {
    	if(src == null){
    		return null;
    	}
        int width = src.getWidth();
        int height = src.getHeight();
		int type = src.getType();
		WritableRaster srcRaster = src.getRaster();

        if ( dst == null )
            dst = createCompatibleDestImage( src, null );
		WritableRaster dstRaster = dst.getRaster();

        setDimensions( width, height);

		int[] inPixels = new int[width];
        for ( int y = 0; y < height; y++ ) {
			// We try to avoid calling getRGB on images as it causes them to become unmanaged, causing horrible performance problems.
			if ( type == BufferedImage.TYPE_INT_ARGB ) {
				srcRaster.getDataElements( 0, y, width, 1, inPixels );
				for ( int x = 0; x < width; x++ )
					inPixels[x] = filterRGB( x, y, inPixels[x] );
				dstRaster.setDataElements( 0, y, width, 1, inPixels );
			} else {
				src.getRGB( 0, y, width, 1, inPixels, 0, width );
				for ( int x = 0; x < width; x++ )
					inPixels[x] = filterRGB( x, y, inPixels[x] );
				dst.setRGB( 0, y, width, 1, inPixels, 0, width );
			}
        }

        return dst;
    }

	public void setDimensions(int width, int height) {
	}


	public GrayFilter() {
		canFilterIndexColorModel = true;
	}

	public int filterRGB(int x, int y, int rgb) {
		int a = rgb & 0xff000000;
		int r = (rgb >> 16) & 0xff;
		int g = (rgb >> 8) & 0xff;
		int b = rgb & 0xff;
		r = (r+255)/2;
		g = (g+255)/2;
		b = (b+255)/2;
		return a | (r << 16) | (g << 8) | b;
	}

	public String toString() {
		return "Colors/Gray Out";
	}
	/**
	 * A convenience method for getting ARGB pixels from an image. This tries to avoid the performance
	 * penalty of BufferedImage.getRGB unmanaging the image.
     * @param image   a BufferedImage object
     * @param x       the left edge of the pixel block
     * @param y       the right edge of the pixel block
     * @param width   the width of the pixel arry
     * @param height  the height of the pixel arry
     * @param pixels  the array to hold the returned pixels. May be null.
     * @return the pixels
     * @see #setRGB
     */
	public int[] getRGB( BufferedImage image, int x, int y, int width, int height, int[] pixels ) {
		int type = image.getType();
		if ( type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )
			return (int [])image.getRaster().getDataElements( x, y, width, height, pixels );
		return image.getRGB( x, y, width, height, pixels, 0, width );
    }

	/**
	 * A convenience method for setting ARGB pixels in an image. This tries to avoid the performance
	 * penalty of BufferedImage.setRGB unmanaging the image.
     * @param image   a BufferedImage object
     * @param x       the left edge of the pixel block
     * @param y       the right edge of the pixel block
     * @param width   the width of the pixel arry
     * @param height  the height of the pixel arry
     * @param pixels  the array of pixels to set
     * @see #getRGB
	 */
	public void setRGB( BufferedImage image, int x, int y, int width, int height, int[] pixels ) {
		int type = image.getType();
		if ( type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )
			image.getRaster().setDataElements( x, y, width, height, pixels );
		else
			image.setRGB( x, y, width, height, pixels, 0, width );
    }

	public Object clone() {
		try {
			return super.clone();
		}
		catch ( CloneNotSupportedException e ) {
			return null;
		}
	}

}
