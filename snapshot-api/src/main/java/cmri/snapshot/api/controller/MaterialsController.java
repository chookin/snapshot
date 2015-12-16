package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebMvcConfig;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.utils.lang.JsonHelper;
import cmri.utils.lang.MapAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuyin on 12/15/15.
 */
@RestController
@RequestMapping("/materials")
public class MaterialsController {
    /**
     * 该方法的签名不需要传入uid参数
     */
    @RequestMapping(value = "homepages", method = RequestMethod.GET)
    public ResponseMessage homepages(){
        List<String> items = new ArrayList<>();
        for(int i = 1;i<7;++i) {
            items.add(getJpgUrl("homepage-" + i));
        }
        return new ResponseMessage()
                .set("items", JsonHelper.toJson(items));
    }
    @RequestMapping(value = "personalHomepages", method = RequestMethod.GET)
    public ResponseMessage personalHomepages(){
        List<String> items = new ArrayList<>();
        for(int i = 1;i<7;++i) {
            items.add(getJpgUrl("personal-homepage-" + i));
        }
        return new ResponseMessage()
                .set("items", JsonHelper.toJson(items));
    }
    @RequestMapping(value = "categories", method = RequestMethod.GET)
    public ResponseMessage categories(){
        List<Map<String,String>> items = new ArrayList<>();
        items.add(new MapAdapter<>("url", getJpgUrl("c-couples")).put("title", "情侣写真").get());
        items.add(new MapAdapter<>("url", getJpgUrl("c-graduation")).put("title", "毕业纪念").get());
        items.add(new MapAdapter<>("url", getJpgUrl("c-group")).put("title", "团体拍摄").get());
        items.add(new MapAdapter<>("url", getJpgUrl("c-japanese")).put("title", "日系").get());
        items.add(new MapAdapter<>("url", getJpgUrl("c-personal")).put("title", "个人写真").get());
        items.add(new MapAdapter<>("url", getJpgUrl("c-pet")).put("title", "宠物摄影").get());
        items.add(new MapAdapter<>("url", getJpgUrl("c-pregnant")).put("title", "孕妇周记").get());
        items.add(new MapAdapter<>("url", getJpgUrl("c-travel")).put("title", "旅游跟拍").get());
        return new ResponseMessage()
                .set("items", JsonHelper.toJson(items));
    }
    @RequestMapping(value = "groupShotDetails", method = RequestMethod.GET)
    public ResponseMessage groupShotDetails(){
        List<String> names = new ArrayList<>();
        names.add("我是公主");
        names.add("相亲一家");
        names.add("两小无猜");
        List<Map<String,String>> items = new ArrayList<>();
        for(int i = 1;i<4;++i) {
            items.add(new MapAdapter<>("name",getJpgUrl("group-shot-" + i)).put("title", names.get(i-1)).get());
        }
        return new ResponseMessage()
                .set("items", JsonHelper.toJson(items));
    }

    static String getJpgUrl(String material){
        return getUrl(material+".jpg");
    }
    static String getUrl(String material){
        String path = "resources/"+material;
        return WebMvcConfig.getUrl(path);
    }

}
