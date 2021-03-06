package cn.ucai.yizhesale.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.ucai.yizhesale.YiZheSaleApplication;
import cn.ucai.yizhesale.I;
//import cn.ucai.yizhesale.bean.CartBean;
//import cn.ucai.yizhesale.bean.GoodDetailsBean;
//import cn.ucai.yizhesale.task.UpdateCartTask;

/**
 * Created by clawpo on 16/3/28.
 */
public class Utils {
    public static String getPackageName(Context context){
        return context.getPackageName();
    }
    
    public static void showToast(Context context,String text,int time){
        Toast.makeText(context,text,time).show();
    }
    
    public static void showToast(Context context,int  strId,int time){
        Toast.makeText(context, strId, time).show();
    }

    /**
     * 将数组转换为ArrayList集合
     * @param ary
     * @return
     */
    public static <T> ArrayList<T> array2List(T[] ary){
        List<T> list = Arrays.asList(ary);
        ArrayList<T> arrayList=new ArrayList<T>(list);
        return arrayList;
    }

    /**
     * 添加新的数组元素：数组扩容
     * @param array：数组
     * @param t：添加的数组元素
     * @return：返回添加后的数组
     */
    public static <T> T[] add(T[] array,T t){
        array=Arrays.copyOf(array, array.length+1);
        array[array.length-1]=t;
        return array;
    }

    public static String getResourceString(Context context, int msg){
        if(msg<=0) return null;
        String msgStr = msg+"";
        msgStr = I.MSG_PREFIX_MSG + msgStr;
        int resId = context.getResources().getIdentifier(msgStr, "string", context.getPackageName());
        return context.getResources().getString(resId);
    }

    public static int px2dp(Context context,int px){
        int density = (int) context.getResources().getDisplayMetrics().density;
        return px/density;
    }

    public static int dp2px(Context context,int dp){
        int density = (int) context.getResources().getDisplayMetrics().density;
        return dp*density;
    }

//    public static int sumCartCount(){
//        Log.i("main","sumcount=");
//        int count = 0;
//        ArrayList<CartBean> cartList = FuLiCenterApplication.getInstance().getCartList();
//        Log.i("main","cartList="+cartList.size());
//        for (CartBean cart:cartList){
//            count += cart.getCount();
//            Log.i("main","sumcount="+count);
//        }
//        return count;
//    }

//    public static void delCart(Context context,GoodDetailsBean good) {
//        ArrayList<CartBean> cartList = FuLiCenterApplication.getInstance().getCartList();
//        boolean isExist = false;
//        CartBean mCart = null;
//        for (int i=0;i<cartList.size();i++){
//            CartBean cart = cartList.get(i);
//            if (cart.getGoodsId()==good.getGoodsId()){
//                cart.setCount(cart.getCount()-1);
//                isExist = true;
//                mCart = cart;
//            }
//        }
//        if (isExist){
//            new UpdateCartTask(context,mCart).execute();
//        }
//    }
//
//    public static void addCart(Context context,GoodDetailsBean good) {
//        ArrayList<CartBean> cartList = FuLiCenterApplication.getInstance().getCartList();
//        boolean isExist = false;
//        CartBean mCart = null;
//        for (int i=0;i<cartList.size();i++){
//            CartBean cart = cartList.get(i);
//            if (cart.getGoodsId()==good.getGoodsId()){
//                cart.setCount(cart.getCount()+1);
//                isExist = true;
//                mCart = cart;
//            }
//        }
//        if (!isExist){
//            mCart = new CartBean(0,FuLiCenterApplication.getInstance().getUserName(),
//                    good.getGoodsId(),1,true);
//        }
//        new UpdateCartTask(context,mCart).execute();
//    }
}
