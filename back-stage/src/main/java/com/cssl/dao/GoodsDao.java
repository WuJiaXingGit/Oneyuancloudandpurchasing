package com.cssl.dao;

import cn.yiyuangou.pojo.Goods;
import cn.yiyuangou.pojo.Type;
import com.github.pagehelper.Page;
import org.springframework.data.repository.query.Param;


import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shksart  mr yin
 * @date 2018/12/28 -  19:18
 */
public interface GoodsDao {

     //后台主页面查询
     Page<Goods> seleList(Map<String,Object>map);

     //（1类型查询）
     List<Type> categoryList();

     //（2添加）
     int addGoods(Goods goods);

     //3（批量增加）
     /*boolean batchAddGoods(List<Goods> goodsList);*/


     //SELECT * FROM goods WHERE goods_id=1

     Goods seleGoodById(@Param("gid") int gid);

     int UpdaGoodById(Goods goods);

     int DelGoodById(@Param("id") int  id);


}
