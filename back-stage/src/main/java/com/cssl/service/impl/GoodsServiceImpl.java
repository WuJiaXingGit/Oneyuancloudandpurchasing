package com.cssl.service.impl;

import cn.yiyuangou.pojo.Type;
import com.cssl.dao.GoodsDao;
import com.cssl.service.GoodsService;
import cn.yiyuangou.pojo.Goods;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shksart
 * @date 2018/12/28 -  19:34
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;


    @Override
    public Page<Goods> seleList(Map<String, Object> map) {
        PageHelper.startPage(Integer.valueOf(map.get("pageNum").toString()) ,5);
        return goodsDao.seleList(map);
    }

    @Override
    public List<Type> categoryList() {
        return goodsDao.categoryList();
    }

    @Override
    public int addGoods(Goods goods) {
        return goodsDao.addGoods(goods);
    }

    @Override
    public Goods seleGoodById(int gid) {
        return goodsDao.seleGoodById(gid);
    }

    @Override
    public int UpdaGoodById(Goods goods) {
        return goodsDao.UpdaGoodById(goods);
    }

    @Override
    public int DelGoodById(int id) {
        return goodsDao.DelGoodById(id);
    }


}
