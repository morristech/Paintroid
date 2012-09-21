package org.catrobat.paintroid.bitmap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.catrobat.paintroid.PaintroidApplication;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

public class PaintroidBitmapStorageFactory {

	private class PaintroidCustomBitmap {
		public Bitmap mBitmap = null;
		public String mDescription = null;

		public PaintroidCustomBitmap(String description, Bitmap createdBitmap) {
			mDescription = description;
			mBitmap = createdBitmap;
		}
	}

	private volatile HashMap<Integer, PaintroidCustomBitmap> mBitmapStorage = new HashMap<Integer, PaintroidCustomBitmap>();

	public Bitmap createNewBitmap(Bitmap bitmapSource, String bitmapDescription) {
		PaintroidCustomBitmap newPaintroidBitmap = new PaintroidCustomBitmap(
				bitmapDescription, Bitmap.createBitmap(bitmapSource));
		mBitmapStorage.put(mBitmapStorage.size(), newPaintroidBitmap);
		return newPaintroidBitmap.mBitmap;
	}

	// public Bitmap createNewBitmap(int bitmapWidth, int bitmapHeight,
	// Config bitmapConfig, String bitmapDescription) {
	// PaintroidCustomBitmap newPaintroidBitmap = new PaintroidCustomBitmap(
	// bitmapDescription, Bitmap.createBitmap(bitmapWidth,
	// bitmapHeight, bitmapConfig));
	// mBitmapStorage.put(mBitmapStorage.size(), newPaintroidBitmap);
	// return newPaintroidBitmap.mBitmap;
	// }

	// public void addBitmapToStorage(Bitmap bitmap, String bitmapDescription) {
	// mBitmapStorage.put(mBitmapStorage.size(), new PaintroidCustomBitmap(
	// bitmapDescription, bitmap));
	// }

	@SuppressLint("NewApi")
	synchronized public void clearAllBitmaps() {
		Log.i(PaintroidApplication.TAG, "recycling bitmaps ");
		Iterator<Entry<Integer, PaintroidCustomBitmap>> iterator = mBitmapStorage
				.entrySet().iterator();

		int totalBytesFound = 0;
		while (iterator.hasNext()) {
			Entry<Integer, PaintroidCustomBitmap> paintroidBitmapEntry = iterator
					.next();
			Bitmap image = paintroidBitmapEntry.getValue().mBitmap;
			if (image != null && image.isRecycled() == false) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
					totalBytesFound += image.getByteCount();
				}
				image.recycle();
				Log.i(PaintroidApplication.TAG, "recycling bitmap from "
						+ paintroidBitmapEntry.getValue().mDescription);
			}
			image = null;
		}
		Log.i(PaintroidApplication.TAG,
				"(Only on HONEYCOMB_MR1>= Version)Total freed bitmap bytes: "
						+ totalBytesFound + " ~MB:" + totalBytesFound
						/ 1048576.0);
		mBitmapStorage.clear();
	}

	@Override
	protected void finalize() throws Throwable {
		clearAllBitmaps();
		super.finalize();
	}
}
