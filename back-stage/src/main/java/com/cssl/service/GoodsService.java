package com.cssl.service;

import cn.yiyuangou.pojo.Goods;
import cn.yiyuangou.pojo.Type;
import com.github.pagehelper.Page;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shksart
 * @date 2018/12/28 -  19:33
 */
public interface GoodsService {

    Page<Goods> seleList(Map<String,Object>map);

    List<Type> categoryList();

    int addGoods(Goods goods);

    Goods seleGoodById(@Param("gid") int gid);

    int UpdaGoodById(Goods goods);

    int DelGoodById(@Param("id") int  id);
}
