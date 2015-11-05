package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import cmri.utils.configuration.ConfigManager;
import cmri.utils.dao.RedisHandler;
import cmri.utils.lang.RandomHelper;
import com.cloopen.rest.sdk.CCPRestSDK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by zhuyin on 11/5/15.
 */
public class SMSController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    CCPRestSDK getServer(){
        CCPRestSDK restAPI = new CCPRestSDK();
        restAPI.init("sandboxapp.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
        restAPI.setAccount("8a48b55150b23c5b0150b2aca80502c4", "1136173d0065415c8abff00f53550682");// 初始化主帐号和主帐号TOKEN
        restAPI.setAppId("8a48b55150d5879c0150d589d49f002b");// 初始化应用ID
        return restAPI;
    }

    String generateCode(){
        return String.valueOf(RandomHelper.rand(1001,9999));
    }
    public static String getVerifyCodeId(Long phoneNum){
        return "sms_verifyCode_"+phoneNum;
    }
    public ResponseMessage sendSMS(Long phoneNum, String sig){
        String code = generateCode();
        RedisHandler.instance().set(getVerifyCodeId(phoneNum), code, ConfigManager.getInteger("sms.verifyCode.expireMinutes"));
        HashMap<String, Object> result = getServer().sendTemplateSMS(String.valueOf(phoneNum), ConfigManager.get("sms.template.id"),new String[]{code,ConfigManager.get("sms.verifyCode.expireMinutes", "3")});
        LOG.trace("SDKTestSendTemplateSMS result=" + result);
        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                LOG.trace(key + " = " + object);
            }
        }else{
            //异常返回输出错误码和错误信息
            throw new RuntimeException("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
        }
        return new ResponseMessage();
    }
}
