package com.domee;

import java.io.IOException;
import java.util.LinkedList;

import com.domee.adapter.DMFriendAdapter;
import com.domee.manager.DMAccountsManager;
import com.domee.model.FriendsResult;
import com.domee.model.User;
import com.domee.utils.GsonUtil;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.net.RequestListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class DMAtFriendActivity extends BaseActivity {

	private ListView listView;
	
	//title_user_timeline
	private ImageButton tbBack;
	private TextView tbTitle;
	
	private DMFriendAdapter adapter;
	private int cursor;
	
	public static void show(Activity activity) {
		Intent intent = new Intent(activity, DMAtFriendActivity.class);
		activity.startActivityForResult(intent, 0);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setTheme(R.style.CustomTheme);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.ac_user_timeline);     
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
        
        adapter = new DMFriendAdapter(this, imageLoader, options, animateFirstListener);
        
        //title_bar控件处理
        tbBack = (ImageButton)findViewById(R.id.tbBack);
        tbTitle = (TextView)findViewById(R.id.tbTitle);
        tbBack.setOnClickListener(new UtBtnListener());
        tbTitle.setText("选择好友");
        
        this.loadMore();
        listView = (ListView) findViewById(R.id.utListView);
        listView.setAdapter(adapter);
	}
	
	public void loadMore() {
		FriendsRequestListener listener = new FriendsRequestListener();
		friendshipsAPI.friends(DMAccountsManager.getCurUser().getId(), 20, cursor, false, listener);
	}
	
	public class FriendsRequestListener implements RequestListener {

		@Override
		public void onComplete(String arg0) {
			// TODO Auto-generated method stub
			FriendsResult fr = GsonUtil.gson2Friends(arg0);
			adapter.setUserList(fr.getUsers());
		}

		@Override
		public void onError(WeiboException arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onIOException(IOException arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class UtBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tbBack:
				finish();
				break;
			default:
				break;
			}
		}
	}
}
