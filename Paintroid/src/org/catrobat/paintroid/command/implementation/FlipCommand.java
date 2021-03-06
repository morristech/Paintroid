/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2012 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://www.catroid.org/catroid/licenseadditionalterm
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.paintroid.command.implementation;

import org.catrobat.paintroid.PaintroidApplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

public class FlipCommand extends BaseCommand {

	private FlipDirection mFlipDirection;

	public static enum FlipDirection {
		FLIP_HORIZONTAL, FLIP_VERTICAL
	};

	public FlipCommand(FlipDirection flipDirection) {
		mFlipDirection = flipDirection;
	}

	@Override
	public void run(Canvas canvas, Bitmap bitmap) {
		setChanged();
		notifyStatus(NOTIFY_STATES.COMMAND_STARTED);
		if (mFlipDirection == null) {
			setChanged();
			notifyStatus(NOTIFY_STATES.COMMAND_FAILED);
			return;
		}

		Matrix flipMatrix = new Matrix();

		switch (mFlipDirection) {
			case FLIP_HORIZONTAL:
				flipMatrix.setScale(1, -1);
				flipMatrix.postTranslate(0, bitmap.getHeight());
				Log.i(PaintroidApplication.TAG, "flip horizontal");
				break;
			case FLIP_VERTICAL:
				flipMatrix.setScale(-1, 1);
				flipMatrix.postTranslate(bitmap.getWidth(), 0);
				Log.i(PaintroidApplication.TAG, "flip vertical");
				break;
			default:
				setChanged();
				notifyStatus(NOTIFY_STATES.COMMAND_FAILED);
				return;
		}

		Bitmap flipBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
		Canvas flipCanvas = new Canvas(flipBitmap);

		flipCanvas.drawBitmap(bitmap, flipMatrix, new Paint());
		if (PaintroidApplication.DRAWING_SURFACE != null) {
			PaintroidApplication.DRAWING_SURFACE.setBitmap(flipBitmap);
		}

		setChanged();
		notifyStatus(NOTIFY_STATES.COMMAND_DONE);
	}
}
