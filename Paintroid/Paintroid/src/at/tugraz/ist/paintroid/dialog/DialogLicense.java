/*    Catroid: An on-device graphical programming language for Android devices
 *    Copyright (C) 2010  Catroid development team
 *    (<http://code.google.com/p/catroid/wiki/Credits>)
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package at.tugraz.ist.paintroid.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import at.tugraz.ist.paintroid.R;

/**
 * The license dialog displays information about the gpl
 * 
 * Status: refactored 04.02.2011
 * @author PaintroidTeam
 * @version 6.0b
 */
public class DialogLicense extends Dialog implements OnClickListener{

	/**
	 * Constructor
	 * 
	 */
	public DialogLicense(Context context) {
		super(context);
		init();
	}

	/**
	 * Show the dialog
	 * 
	 */
	private void init(){
		
		setContentView(R.layout.dialog_license);
		setTitle(R.string.license_title);
		setCancelable(true);

		TextView text = (TextView) findViewById(R.id.license_tview_Text);
		text.setText(R.string.license_content); 

		Button button = (Button) findViewById(R.id.license_btn_Cancel);
		button.setText(R.string.license_cancel);
		button.setOnClickListener(this); 
   
		show();
	}
	
	/**
	 * Handles the onClick events
	 * 
	 * Closes the dialog if the cancel button
	 * was hit.
	 * 
	 */
	@Override
	public void onClick(View v) {
		
		// close dialog
		if (v.getId() == R.id.license_btn_Cancel)
			this.cancel();
	}
}