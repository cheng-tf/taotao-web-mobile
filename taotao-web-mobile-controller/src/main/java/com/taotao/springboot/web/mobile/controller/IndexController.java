package com.taotao.springboot.web.mobile.controller;

import com.taotao.springboot.content.common.utils.JacksonUtils;
import com.taotao.springboot.content.domain.pojo.TbContent;
import com.taotao.springboot.content.export.ContentResource;
import com.taotao.springboot.web.mobile.domain.vo.AD1Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: IndexController</p>
 * <p>Description: 首页展示Controller</p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-05-06 13:08</p>
 * @author ChengTengfei
 * @version 1.0
 */
@Controller
public class IndexController {

    //@Reference
    @Autowired
    private ContentResource contentResource;

    @Value("${AD1_CATEGORY_ID}")
    private Long AD1_CATEGORY_ID;
    @Value("${AD1_WIDTH}")
    private Integer AD1_WIDTH;
    @Value("${AD1_WIDTH_B}")
    private Integer AD1_WIDTH_B;
    @Value("${AD1_HEIGHT}")
    private Integer AD1_HEIGHT;
    @Value("${AD1_HEIGHT_B}")
    private Integer AD1_HEIGHT_B;

    @RequestMapping("/index")
    public String showIndex(Model model) {
        // 根据categoryId查询轮播图内容列表
        List<TbContent> contentList = contentResource.getContentByCid(AD1_CATEGORY_ID);
        // 把列表转换为Ad1Node列表
        List<AD1Node> ad1NodeList = new ArrayList<>();
        for (TbContent content : contentList) {
            AD1Node node = new AD1Node();
            node.setAlt(content.getTitle());
            node.setHeight(AD1_HEIGHT);
            node.setHeightB(AD1_HEIGHT_B);
            node.setWidth(AD1_WIDTH);
            node.setWidthB(AD1_WIDTH_B);
            node.setSrc(content.getPic());
            node.setSrcB(content.getPic2());
            node.setHref(content.getUrl());
            // 添加到节点列表
            ad1NodeList.add(node);
        }
        // 将列表转换为JSON数据
        String ad1Json = JacksonUtils.objectToJson(ad1NodeList);
        // 把JSON数据传递给页面
        model.addAttribute("ad1", ad1Json);
        return "index";
    }

}
