package cmri.snapshot.api.validator;


import cmri.snapshot.api.domain.User;
import cmri.snapshot.api.repository.UserRepository;
import cmri.utils.lang.ValidateKit;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.AuthenticationException;

/**
 * Created by zhuyin on 11/2/15.
 */
@Service
public class UserSecureValidator {
    @Autowired
    private UserRepository userRepository;

    /**
     * Validate signature of the request.
     * @param user
     * @param date
     * @param time
     * @param sig
     * @return the real user info fetched from db.
     */
    public User validateSignature(User user, String date, Long time, String sig) throws AuthenticationException {
        Validate.notNull(sig, "arg signature is null");
        Validate.notNull(time, "arg time is null");
        ValidateKit.match("\\d{8}", date, "参数date的格式需为'yyyyMMdd'");
        User saved = userRepository.find(user);
        String key = computeKey(date, saved.getPassword());
        String mySig = hmacSha1(key, date, time);
        if(sig.equals(mySig)){
            return saved;
        }
        throw new AuthenticationException("签名校验失败");
    }

    /**
     * compute the key to generate hmacSha1.
     * @param date "yyyyMMdd" format.
     * @param password password was stored as md5.
     * @return hmacSha1 string.
     */
    String computeKey(String date, String password){
        return DigestUtils.md5Hex(date + password);
    }

    String hmacSha1(String key, String date, long time){
        String valueToDigest = date + time;
        return HmacUtils.hmacSha1Hex(key, valueToDigest);
    }

    static String myHmacSha1(String value, String key) {
        try {
            // Get an hmac_sha1 key from the raw key bytes
            byte[] keyBytes = key.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

            // Get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            // Compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(value.getBytes());

            // Convert raw bytes to Hex
            byte[] hexBytes = new Hex().encode(rawHmac);

            //  Covert array of Hex bytes to a String
            return new String(hexBytes, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
