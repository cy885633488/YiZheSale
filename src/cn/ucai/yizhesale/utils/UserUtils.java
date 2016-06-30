package cn.ucai.yizhesale.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.easemob.util.HanziToPinyin;
import com.squareup.picasso.Picasso;

import cn.ucai.yizhesale.Constant;
import cn.ucai.yizhesale.DemoHXSDKHelper;
import cn.ucai.yizhesale.YiZheSaleApplication;
import cn.ucai.yizhesale.I;
import cn.ucai.yizhesale.R;
import cn.ucai.yizhesale.applib.controller.HXSDKHelper;
import cn.ucai.yizhesale.data.RequestManager;
import cn.ucai.yizhesale.domain.EMUser;

public class UserUtils {
    /**
     * 根据username获取相应user，由于demo没有真实的用户数据，这里给的模拟的数据；
     * @param username
     * @return
     */
    public static EMUser getUserInfo(String username){
        EMUser user = ((DemoHXSDKHelper) HXSDKHelper.getInstance()).getContactList().get(username);
        if(user == null){
            user = new EMUser(username);
        }
            
        if(user != null){
            //demo没有这些数据，临时填充
        	if(TextUtils.isEmpty(user.getNick()))
        		user.setNick(username);
        }
        return user;
    }
    /**
     * 设置用户头像
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
    	EMUser user = getUserInfo(username);
        if(user != null && user.getAvatar() != null){
            Picasso.with(context).load(user.getAvatar()).placeholder(R.drawable.default_avatar).into(imageView);
        }else{
            Picasso.with(context).load(R.drawable.default_avatar).into(imageView);
        }
    }
    public static void setUserAvatar(String url,NetworkImageView networkImageView){
        if (url==null || url.isEmpty()) return;
        networkImageView.setDefaultImageResId(R.drawable.default_avatar);
        networkImageView.setImageUrl(url, RequestManager.getImageLoader());
        networkImageView.setErrorImageResId(R.drawable.default_avatar);
    }
    /**
     * 设置当前用户头像
     */
	public static void setCurrentUserAvatar(Context context, ImageView imageView) {
		EMUser user = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().getCurrentUserInfo();
		if (user != null && user.getAvatar() != null) {
			Picasso.with(context).load(user.getAvatar()).placeholder(R.drawable.default_avatar).into(imageView);
		} else {
			Picasso.with(context).load(R.drawable.default_avatar).into(imageView);
		}
	}
    /**
     * 设置用户昵称
     */
    public static void setUserNick(String username,TextView textView){
    	EMUser user = getUserInfo(username);
    	if(user != null){
    		textView.setText(user.getNick());
    	}else{
    		textView.setText(username);
    	}
    }
    /**
     * 设置当前用户昵称
     */
    public static void setCurrentUserNick(TextView textView){
    	EMUser user = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().getCurrentUserInfo();
    	if(textView != null){
    		textView.setText(user.getNick());
    	}
    }

    /**
	 * 保存或更新某个用户
	 * @param newUser
     */
	public static void saveUserInfo(EMUser newUser) {
		if (newUser == null || newUser.getUsername() == null) {
			return;
		}
		((DemoHXSDKHelper) HXSDKHelper.getInstance()).saveContact(newUser);
	}


    public static String getPinYinFromHanZi(String hanzi) {
        String pinyin = "";

        for(int i=0;i<hanzi.length();i++){
            String s = hanzi.substring(i,i+1);
            pinyin = pinyin + HanziToPinyin.getInstance()
                    .get(s).get(0).target.toLowerCase();
        }
        return pinyin;
    }

}
