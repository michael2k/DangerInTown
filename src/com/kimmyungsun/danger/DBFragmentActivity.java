package com.kimmyungsun.danger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.ypyproductions.dialog.utils.IDialogFragmentListener;
import com.ypyproductions.net.task.IDBConstantURL;
import com.ypyproductions.utils.DBLog;
import com.ypyproductions.utils.ResolutionUtils;

/**
 * 
 * @author  :DOBAO
 * @Email   :dotrungbao@gmail.com
 * @Skype   :baopfiev_k50
 * @Phone   :+84983028786
 * @Date    :May 5, 2013
 * @project :IOnAuctions
 * @Package :com.dobao.net.activity
 */

public class DBFragmentActivity extends FragmentActivity implements IDBConstantURL, IDialogFragmentListener {
	
	public static final String TAG = DBFragmentActivity.class.getSimpleName();
	private Dialog  mProgressDialog;

	private int screenWidth;
	private int screenHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFormat(PixelFormat.RGBA_8888);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		this.createProgressDialog();
		
		int[] mRes=ResolutionUtils.getDeviceResolution(this);
		if(mRes!=null && mRes.length==2){
			screenWidth=mRes[0];
			screenHeight=mRes[1];
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showDialogFragment(DIALOG_QUIT_APPLICATION);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void showDialogFragment(int idDialog) {
		FragmentManager mFragmentManager = getSupportFragmentManager();
		switch (idDialog) {
		case DIALOG_LOSE_CONNECTION:
			createWarningDialog(DIALOG_LOSE_CONNECTION, R.string.title_warning, R.string.info_lose_internet).show(mFragmentManager, "DIALOG_LOSE_CONNECTION");
			break;
		case DIALOG_EMPTY:
			createWarningDialog(DIALOG_EMPTY, R.string.title_warning, R.string.info_empty).show(mFragmentManager, "DIALOG_EMPTY");
			break;
		case DIALOG_QUIT_APPLICATION:
			createQuitDialog().show(mFragmentManager, "DIALOG_QUIT_APPLICATION");
			break;
		case DIALOG_SEVER_ERROR:
			createWarningDialog(DIALOG_SEVER_ERROR, R.string.title_warning, R.string.info_server_error).show(mFragmentManager, "DIALOG_SEVER_ERROR");
			break;
		default:
			break;
		}
	}

	public DialogFragment createWarningDialog(int idDialog, int titleId, int messageId) {
		DBAlertFragment mDAlertFragment = DBAlertFragment.newInstance(idDialog, android.R.drawable.ic_dialog_alert, titleId, android.R.string.ok, messageId);
		return mDAlertFragment;
	}

	private DialogFragment createQuitDialog() {
		int mTitleId = R.string.title_confirm;
		int mYesId = R.string.title_yes;
		int mNoId = R.string.title_no;
		int iconId = R.drawable.ic_launcher;
		int messageId = R.string.quit_message;

		DBAlertFragment mDAlertFragment = DBAlertFragment.newInstance(DIALOG_QUIT_APPLICATION, iconId, mTitleId, mYesId, mNoId, messageId);
		return mDAlertFragment;

	}

	private void createProgressDialog() {
		this.mProgressDialog = new Dialog(this);
		this.mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.mProgressDialog.setContentView(R.layout.item_progress_bar);
		this.mProgressDialog.setCancelable(false);
		this.mProgressDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					return true;
				}
				return false;
			}
		});
		
		
	}

	public void showProgressDialog() {
		if (mProgressDialog != null) {
			TextView mTvMessage = (TextView) mProgressDialog.findViewById(R.id.tv_message);
			mTvMessage.setText(R.string.loading);
			if(!mProgressDialog.isShowing()){
				mProgressDialog.show();
			}
		}
	}
	public void showProgressDialog(int messageId) {
		DBLog.d(TAG, "============>mProgressDialog="+mProgressDialog);
		if (mProgressDialog != null) {
			TextView mTvMessage = (TextView) mProgressDialog.findViewById(R.id.tv_message);
			mTvMessage.setText(messageId);
			if(!mProgressDialog.isShowing()){
				mProgressDialog.show();
			}
		}
	}
	public void showProgressDialog(String message) {
		if (mProgressDialog != null) {
			TextView mTvMessage = (TextView) mProgressDialog.findViewById(R.id.tv_message);
			mTvMessage.setText(message);
			if(!mProgressDialog.isShowing()){
				mProgressDialog.show();
			}
		}
	}

	public void dimissProgressDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	@Override
	public void doPositiveClick(int idDialog) {
		switch (idDialog) {
		case DIALOG_QUIT_APPLICATION:
			onDestroyData();
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void doNegativeClick(int idDialog) {

	}
	
	public void onDestroyData(){
		
	}
	
	public void showMenu(int resMenuId, int resId, PopupMenu.OnMenuItemClickListener mOnClick) {
		View mView = findViewById(resMenuId);
		PopupMenu popup = new PopupMenu(this, mView);
		try {
			Field[] fields = popup.getClass().getDeclaredFields();
			for (Field field : fields) {
				if ("mPopup".equals(field.getName())) {
					field.setAccessible(true);
					Object menuPopupHelper = field.get(popup);
					Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
					Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
					setForceIcons.invoke(menuPopupHelper, true);
					break;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		MenuInflater inflater = popup.getMenuInflater();
		inflater.inflate(resId, popup.getMenu());
		popup.setOnMenuItemClickListener(mOnClick);
		popup.show();
	}

}
