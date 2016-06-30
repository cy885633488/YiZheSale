/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ucai.yizhesale.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.EMError;
import com.easemob.chat.EMChatManager;

import cn.ucai.yizhesale.R;
import cn.ucai.yizhesale.YiZheSaleApplication;

import com.easemob.exceptions.EaseMobException;

/**
 * 注册页
 * 
 */
public class RegisterActivity extends BaseActivity {
	private EditText iphoneNumEditText;
	private EditText yzmEditText;
	private EditText passwordEditText;
    private EditText tjrIphoneNumEdiText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(cn.ucai.yizhesale.R.layout.activity_register);
        initView();
	}

    private void initView() {
        iphoneNumEditText = (EditText) findViewById(cn.ucai.yizhesale.R.id.iphoneNum);
        yzmEditText = (EditText) findViewById(R.id.yzm);
        passwordEditText = (EditText) findViewById(cn.ucai.yizhesale.R.id.register_password);
        tjrIphoneNumEdiText = (EditText) findViewById(cn.ucai.yizhesale.R.id.tjr_iphoneNum);
    }

    /**
	 * 注册
	 * 
	 * @param view
	 */
	public void register(View view) {
		final String iphoneNum = iphoneNumEditText.getText().toString().trim();
        final String yzm = yzmEditText.getText().toString().trim();
		final String password = passwordEditText.getText().toString().trim();
		if (TextUtils.isEmpty(iphoneNum)) {
			Toast.makeText(this, getResources().getString(R.string.Iphone_number_cannot_be_empty), Toast.LENGTH_SHORT).show();
            iphoneNumEditText.requestFocus();
			return;
		} else if (!iphoneNum.matches("^[1][3-8]+\\d{9}")){
            iphoneNumEditText.requestFocus();
            iphoneNumEditText.setError(getResources().getString(R.string.Iphone_number_bhf));
        } else if (TextUtils.isEmpty(yzm)) {
            Toast.makeText(this, getResources().getString(R.string.Yzm_cannot_be_empty), Toast.LENGTH_SHORT).show();
            yzmEditText.requestFocus();
            return;
        } else if (TextUtils.isEmpty(password)) {
			Toast.makeText(this, getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
			passwordEditText.requestFocus();
			return;
		}

		if (!TextUtils.isEmpty(iphoneNum) && !TextUtils.isEmpty(yzm) && !TextUtils.isEmpty(password)) {
			final ProgressDialog pd = new ProgressDialog(this);
			pd.setMessage(getResources().getString(R.string.Is_the_registered));
			pd.show();

			new Thread(new Runnable() {
				public void run() {
					try {
						// 调用sdk注册方法
						EMChatManager.getInstance().createAccountOnServer(iphoneNum, password);
						runOnUiThread(new Runnable() {
							public void run() {
								if (!RegisterActivity.this.isFinishing())
									pd.dismiss();
								// 保存用户名(手机号)
								YiZheSaleApplication.getInstance().setUserName(iphoneNum);
								Toast.makeText(getApplicationContext(),
										getResources().getString(R.string.Registered_successfully),
										Toast.LENGTH_SHORT).show();
								finish();
							}
						});
					} catch (final EaseMobException e) {
						runOnUiThread(new Runnable() {
							public void run() {
								if (!RegisterActivity.this.isFinishing())
									pd.dismiss();
								int errorCode=e.getErrorCode();
								if(errorCode==EMError.NONETWORK_ERROR){
									Toast.makeText(getApplicationContext(),
											getResources().getString(R.string.network_anomalies),
											Toast.LENGTH_SHORT).show();
								}else if(errorCode == EMError.USER_ALREADY_EXISTS){
									Toast.makeText(getApplicationContext(),
											getResources().getString(R.string.User_already_exists),
											Toast.LENGTH_SHORT).show();
								}else if(errorCode == EMError.UNAUTHORIZED){
									Toast.makeText(getApplicationContext(),
											getResources().getString(R.string.registration_failed_without_permission),
											Toast.LENGTH_SHORT).show();
								}else if(errorCode == EMError.ILLEGAL_USER_NAME){
								    Toast.makeText(getApplicationContext(),
											getResources().getString(R.string.illegal_user_name),
											Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(getApplicationContext(),
											getResources().getString(R.string.Registration_failed) + e.getMessage(),
											Toast.LENGTH_SHORT).show();
								}
							}
						});
					}
				}
			}).start();
		}
	}
}
