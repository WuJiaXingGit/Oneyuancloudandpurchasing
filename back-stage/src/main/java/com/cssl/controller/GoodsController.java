package com.cssl.controller;


import cn.yiyuangou.pojo.Type;
import com.cssl.service.GoodsService;
import cn.yiyuangou.pojo.Goods;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author shksart
 * @date 2018/12/28 -  19:35
 */
@Controller
@RequestMapping("/back-stage")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;



    //商品展示
    @RequestMapping("/productlist")
    public ModelAndView GoodsList(ModelAndView mv,@RequestParam Map<String,Object>map) {
        System.out.println("进入商品展示列表method...");
        if(map.get("create_time1")!=null && map.get("create_time1")!=""&& map.get("end_time1")!="" && map.get("end_time1")!=null){
            System.out.println("进入转换日期IF");
            java.sql.Date.valueOf(map.get("create_time1").toString());
            java.sql.Date.valueOf(map.get("create_time1").toString());
        }
        if(map.get("pageNum")==null){
            System.out.println("进入if");
            int pageNum=0;
            map.put("pageNum",pageNum);
            System.out.println(pageNum);
            System.out.println(map.get("name1"));
            System.out.println(map.get("create_time1"));
            System.out.println(map.get("end_time1"));
            //map.get("pageNum");  //第N页
            //map.get("pageSize"); //每页M条数
        }
        Page<Goods> pagegoodsList= goodsService.seleList(map);
        mv.addObject("goodsList",pagegoodsList);
        mv.setViewName("product-list");
        return  mv;
    }

    //初始化类别表
    @RequestMapping("/getType")
    public ModelAndView initLevel(ModelAndView mv){
        System.out.println("getTypeMethod...");
        //取出类别name
        List<Type> typeList= goodsService.categoryList();
        mv.addObject("typeList",typeList);
        mv.setViewName("product-add");
        return mv;
    }

    //商品添加
    @RequestMapping("/addProduct")
    public ModelAndView addGoods(ModelAndView mv,Goods goods,MultipartFile files) {
        System.out.println("addProductMethod...");
        System.out.println("从前天获取商品表的值为："+goods);
        System.out.println("上传的文件类型："+files.getContentType());
        String path="D:\\yiyuangou111\\OneYuanCloudAndPurchasing\\page\\src\\main\\resources\\static\\attachment\\image\\upload";
        //自定义规定上传文件的类型
        List<String> types= Arrays.asList("image/gif","image/jpeg","image/png");
        //判断用户是否上传img
        if(files!=null){
            //开始上传
            String Imgtype = files.getContentType();  //得到文件类型
            String Imgname = files.getOriginalFilename();  //得到文件名
            System.out.println(Imgname);
            if (types.contains(Imgtype)) {
                //判断上传图片名称可能产生同名，使用uuid区分
                String uuid = UUID.randomUUID().toString();
                System.out.println("UUID：" + uuid);
                File file=new File(path,uuid+Imgname);
                try {
                    files.transferTo(file);
                } catch (IOException e) {
                    mv.addObject("uploadMessage","上传失败!");
                    e.printStackTrace();
                }
                //给商品对象path赋值
                String savepath="/attachment/image/upload/";
                goods.setPath(savepath+uuid+Imgname);  //添加图片路径
                System.out.println("数据库保存的图片路径为："+savepath+uuid+Imgname);
                System.out.println("上传成功");
                mv.addObject("uploadMessage","上传成功!");
            }else if(!types.contains(Imgtype)){
                //保存失败信息前台渲染
                mv.addObject("uploadMessage","上传失败，请选择图片！");
            }

        }
        //给对象赋值并添加
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String create_time= sdf.format(new Date());
        goods.setCreate_time(java.sql.Date.valueOf(create_time));
        goods.setUpdate_time(java.sql.Date.valueOf(create_time));
        //剩余人数
        goods.setSurplus(goods.getHeadcount());
        //新商品以参与人数默认为0
        //期号 (新商品默认为第一期)
        goods.setGoodsIssue(1);
        int result= goodsService.addGoods(goods);
        if(result>0){
            System.out.println("添加商品成功");
            //保存个状态码前台用户判断是否添加成功
            //mv.addObject("addMess",true);
            //跳转到主页面
            mv.setViewName("redirect:/back-stage/productlist");
        }else{
            System.out.println("添加商品失败");
            //mv.addObject("addMess",false);
            mv.setViewName("product-add");
        }
        return mv;
    }

    //add action upda
    @RequestMapping("/cparam")
    public ModelAndView goodsById(int id,ModelAndView mv){
        Goods goods= goodsService.seleGoodById(id);
        List<Type> typeList= goodsService.categoryList();
        mv.addObject("typeList",typeList);
        System.out.println(goods);
        mv.addObject("goods",goods);
        mv.setViewName("product-upda");
        return mv;
    }

    //upda
    @RequestMapping("/upda")
    public ModelAndView UpdaGoods(ModelAndView mv,Goods goods,MultipartFile files){
        System.out.println("goods...");
        System.out.println("要修改对象的值为："+goods);
        if(files!=null){
            //已选择文件，判断文件类型是否为图片
            List<String> types=Arrays.asList("image/gif","image/jpeg","image/png");
            String path="D:\\yiyuangou111\\OneYuanCloudAndPurchasing\\page\\src\\main\\resources\\static\\attachment\\image\\upload";
            String filename=files.getOriginalFilename();
            if(types.contains( files.getContentType())){
                //为图片
                String uuid = UUID.randomUUID().toString();
                System.out.println("UUID：" + uuid);
                File file=new File(path,uuid+filename);
                try {
                    files.transferTo(file);
                    //更新path
                    String savepath="/attachment/image/upload/";
                    System.out.println("图片新Path:"+savepath+uuid+filename);
                    goods.setPath(savepath+uuid+filename);
                    //保存标记前台判断
                    mv.addObject("uploadMess",true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                //其他文件
                System.out.println("上传失败！");
                mv.addObject("uploadMess",false);
            }
        }
        goods.setUpdate_time(new Date());  //获取当前时间-》编辑时间
        //调用更新方法
        int result= goodsService.UpdaGoodById(goods);
        if(result>0){
            System.out.println("编辑商品成功");
            mv.addObject("updaMess",false);
            mv.setViewName("redirect:/back-stage/productlist");
        }else{
            System.out.println("编辑商品失败");
            mv.addObject("updaMess",false);
        }

        return mv;
    }

    //del
    @RequestMapping("/del")
    @ResponseBody
    public boolean DelGoods(@RequestParam int[] id){
        System.out.println("进入删除！");
        System.out.println("id:"+id);
        if(id!=null){
            for (int i = 0; i < id.length ; i++) {
                //防止传过来的null值而报错
                if(String.valueOf(id[i])!=null ){
                    if(goodsService.DelGoodById(id[i])>0){
                        System.out.println("删除的id为："+id[i]);
                        return true;
                    }
                }
            }
        }
        return false;
    }




}
