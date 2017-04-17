package top.linjia.wizarposapp.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import top.linjia.wizarposapp.beans.GoodsType;

public class GoodsCategoeySort {
    List<GoodsType.ResultBean> resultdata;
    int m,n,k;
    public GoodsCategoeySort(){
        this.resultdata=new ArrayList<>();
    }
    public List<GoodsType.ResultBean> sort(List<GoodsType.ResultBean> data){
        resultdata.add(0,null);
        resultdata.add(1,null);
        resultdata.add(2,null);
        for(int i=0;i<data.size();i++){
            if(data.get(i).getCname().equals("最新上架")){
                resultdata.set(0,data.get(i));
                m=i;
            }else if(data.get(i).getCname().equals("热销品")){
                resultdata.set(1,data.get(i));
                n=i;
            }else if(data.get(i).getCname().equals("组合促销")){
                resultdata.set(2,data.get(i));
                k=i;
            }
        }
        data.remove(k);
        if(m>data.size()){
            data.remove(m-1);
        }else{
            data.remove(m);
        }if(n>data.size()){
            data.remove(n-2);
        }else{
            data.remove(n);
        }
        resultdata.addAll(data);
        Log.i("test","resultData的长度是："+resultdata.size());
        return resultdata;
    }
}
