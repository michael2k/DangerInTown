package com.kimmyungsun.danger;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kimmyungsun.danger.constanst.IDangerConstants;
import com.kimmyungsun.danger.datamng.TotalDataManager;
import com.ypyproductions.net.task.DBTask;
import com.ypyproductions.net.task.IDBTaskListener;
import com.ypyproductions.utils.DBLog;

/**
 * SplashActivity
 * @author  :DOBAO
 * @Email   :dotrungbao@gmail.com
 * @Skype   :baopfiev_k50
 * @Phone   :+84983028786
 * @Date    :May 5, 2013
 * @project :IOnAuctions
 * @Package :com.auction.ionauctions
 */

public class SplashActivity extends Activity implements IDBTaskListener, IDangerConstants {
	
	public static final String TAG=SplashActivity.class.getSimpleName();
	
	private ProgressBar mProgressBar;
	private boolean isPressBack;
	private DBTask mDBTask;

	private Handler mHandler = new Handler();
//	private ArrayList<HomeSearchObject> mListHomeObjects;
	private TextView mTvCopyright;
//	private ArrayList<KeywordObject> mListKeywordObjects;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.splash);
		this.mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		this.mTvCopyright =(TextView) findViewById(R.id.tv_copyright);
		
//		this.mTvCopyright.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/champagne.ttf"));
		
		DBLog.setDebug(DEBUG);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mDBTask = new DBTask(SplashActivity.this);
				mDBTask.execute();
			}
		}, 2000);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacksAndMessages(null);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if(isPressBack){
				TotalDataManager.getInstance().onDestroy();
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onPreExcute() {
		this.mProgressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void onDoInBackground() {
//		String dataHome = IOUtils.readStringFromAssets(this, "homedata.dat");
//		String dataKeyword =IOUtils.readStringFromAssets(this, "types_search.dat");
//		
//		mListHomeObjects = JsonParsingUtils.parsingListHomeObjects(dataHome);
//		if(mListHomeObjects!=null && mListHomeObjects.size()>0){
//			HomeSearchObject mHomeSearchObject = new HomeSearchObject(TYPE_SEARCH_BY_TYPES,
//					getString(R.string.title_custom_search), "", "");
//			mListHomeObjects.add(0,mHomeSearchObject);
//			TotalDataManager.getInstance().setListHomeSearchObjects(mListHomeObjects);
//			if(mListHomeObjects.size()>=2){
//				mListHomeObjects.get(1).setSelected(true);
//			}
//		}
//		mListKeywordObjects = JsonParsingUtils.parsingListKeywordObjects(dataKeyword);
//		if(mListKeywordObjects!=null && mListKeywordObjects.size()>0){
//			TotalDataManager.getInstance().setListKeywordObjects(mListKeywordObjects);
//			if(!SettingManager.isInitProvider(this)){
//				for(KeywordObject mKeywordObject:mListKeywordObjects){
//					MySuggestionDAO.insertData(this, mKeywordObject);
//				}
//				SettingManager.setInitProvider(this, true);
//			}
//		}
//		
//		File mCacheFile = IOUtils.getDiskCacheDir(this, DIR_DATA);
//		if(!mCacheFile.exists()){
//			mCacheFile.mkdirs();
//		}
//		String dataFavorite = IOUtils.readLogFile(this, mCacheFile.getAbsolutePath(), FILE_FAVORITE_PLACES);
//		ArrayList<PlaceObject> listFavorites = JsonParsingUtils.parsingListFavoriteObjects(dataFavorite);
//		TotalDataManager.getInstance().setListFavoriteObjects(listFavorites);
	}
	
	@Override
	public void onPostExcute() {
//		if(mListHomeObjects==null || mListHomeObjects.size()==0){
//			Toast.makeText(this, R.string.info_parse_error, Toast.LENGTH_LONG).show();
//			return;
//		}
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.mProgressBar.setVisibility(View.INVISIBLE);
		Intent mIntent = new Intent(SplashActivity.this, DangerDataTest.class);
		mIntent.putExtra(KEY_START_FROM, START_FROM_SPLASH);
//		DirectionUtils.changeActivity(this, R.anim.slide_in_from_bottom, R.anim.slide_out_to_top, true, mIntent);
		startActivity(mIntent);
		finish();
	}
}
