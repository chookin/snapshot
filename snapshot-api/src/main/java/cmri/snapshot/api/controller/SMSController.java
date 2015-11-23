package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import cmri.utils.configuration.ConfigManager;
import cmri.utils.dao.RedisHandler;
import cmri.utils.exception.AuthException;
import cmri.utils.lang.RandomHelper;
import com.cloopen.rest.sdk.CCPRestSDK;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuyin on 11/5/15.
 */
@RestController
@RequestMapping("/sms")
public class SMSController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    String generateCode(){
        // int类型的最大值的常量可取的值为 2的31次方-1。理论上最大值是：2147483647
        int number = ConfigManager.getInt("sms.authCode.number");
        Validate.isTrue(number>= 3 && number <= 9, "'sms.authCode.number' should be lte 9 and gte 2");// 需要是不大于9且不小于2的数字
        int min = (int) Math.pow(10, number - 1);
        int max = (int) Math.pow(10, number);
        return String.valueOf(RandomHelper.rand(min, max));
    }

    public static String getAuthCodeId(long phoneNum){
        return "sms_authCode_"+phoneNum;
    }

    /**
     * 发送手机验证码
     */
    @RequestMapping(value = "/authCode/send", method = RequestMethod.POST)
    public ResponseMessage sendAuthCode(Long phoneNum){
        String code = generateCode();
        int expireMinutes = ConfigManager.getInt("sms.authCode.expireMinutes");
        sendAuthCode(phoneNum, code, expireMinutes);
        RedisHandler.instance().set(getAuthCodeId(phoneNum), code, expireMinutes * 60);
        return new ResponseMessage();
    }
    CCPRestSDK getServer(){
        CCPRestSDK restAPI = new CCPRestSDK();
        restAPI.init(ConfigManager.get("sms.server.ip"), ConfigManager.get("sms.server.port"));// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
        restAPI.setAccount(ConfigManager.get("sms.account.sid"), ConfigManager.get("sms.auth.token"));// 初始化主帐号和主帐号TOKEN
        restAPI.setAppId(ConfigManager.get("sms.app.id"));// 初始化应用ID
        return restAPI;
    }
    void sendAuthCode(long phoneNum, String code, int expireMinutes){
        LOG.info("send auth code " + code + " for "+phoneNum);
        HashMap<String, Object> result = getServer().sendTemplateSMS(
                String.valueOf(phoneNum), // 短信接收手机号码集合,用英文逗号分开,如 '13810001000,13810011001',最多一次发送100个
                ConfigManager.get("sms.template.id"), // 模板Id,如使用测试模板，模板id为1，如使用自己创建的模板，则使用自己创建的短信模板id即可
                new String[]{code, String.valueOf(expireMinutes)} // 内容数据，需定义成数组方式，如模板中有两个参数，定义方式为array（'3456','123'）
        );
        // HashMap<String, Object> result = getServer().sendTemplateSMS("13426198753", "1", new String[]{"6532", "5"}); // 免费开发测试使用的模板
        LOG.trace("SDKTestSendTemplateSMS result=" + result);
        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            for(Map.Entry<String, Object> entry: data.entrySet()){
                LOG.trace(entry.getKey() + "=" + entry.getValue());
            }
        }else{
            //异常返回输出错误码和错误信息
            throw new AuthException("Fail to send message, error code:" + result.get("statusCode") +" msg:"+result.get("statusMsg"));
        }
    }
}
