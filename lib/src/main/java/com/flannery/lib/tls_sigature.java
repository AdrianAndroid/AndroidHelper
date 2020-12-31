//package com.flannery.lib;
//
//import java.io.CharArrayReader;
//import java.io.IOException;
//import java.io.Reader;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.Security;
//import java.nio.charset.Charset;
//
//import java.security.Signature;
//import java.util.zip.DataFormatException;
//import java.util.zip.Deflater;
//import java.util.zip.Inflater;
//
////import org.bouncycastle.util.encoders.Base64;
////import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
////import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
////import org.bouncycastle.jce.provider.BouncyCastleProvider;
////import org.bouncycastle.openssl.PEMParser;
////import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
////import org.bouncycastle.util.Arrays;
////import org.json.JSONObject;
////
////import com.tls.base64_url.base64_url;
//
//public class tls_sigature {
//    public static class GenTLSSignatureResult
//    {
//        public String errMessage;
//        public String urlSig;
//        public int expireTime;
//        public int initTime;
//        public GenTLSSignatureResult()
//        {
//            errMessage = "";
//            urlSig = "";
//        }
//    }
//
//    public static class CheckTLSSignatureResult
//    {
//        public String errMessage;
//        public boolean verifyResult;
//        public int expireTime;
//        public int initTime;
//        public CheckTLSSignatureResult()
//        {
//            errMessage = "";
//            verifyResult = false;
//        }
//    }
//
//    /**
//     * 生成 tls 票据
//     * @param expire 有效期，单位是秒，推荐一个月
//     * @param appid3rd 填写与 sdkAppid 一致字符串形式的值
//     * @param sdkappid 应用的 appid
//     * @param identifier 用户 id
//     * @param accountType 创建应用后在配置页面上展示的 acctype
//     * @param priKeyContent 生成 tls 票据使用的私钥内容
//     * @return 如果出错，GenTLSSignatureResult 中的 urlSig为空，errMsg 为出错信息，成功返回有效的票据
//     */
//    @Deprecated
//    public static GenTLSSignatureResult GenTLSSignature(long expire,
//                                                        String appid3rd, long sdkappid, String identifier,
//                                                        long accountType, String priKeyContent) {
//
//        GenTLSSignatureResult result = new GenTLSSignatureResult();
//
//        Security.addProvider(new BouncyCastleProvider());
//        Reader reader = new CharArrayReader(priKeyContent.toCharArray());
//        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
//        PEMParser parser = new PEMParser(reader);
//        PrivateKey privKeyStruct;
//        try{
//            Object obj = parser.readObject();
//            parser.close();
//            privKeyStruct = converter.getPrivateKey((PrivateKeyInfo) obj);
//        } catch (IOException e) {
//            result.errMessage = "read pem error:" + e.getMessage();
//            return result;
//        }
//
//        //Create Json string and serialization String
//        String jsonString = "{"
//                + "\"TLS.account_type\":\"" + accountType +"\","
//                + "\"TLS.identifier\":\"" + identifier +"\","
//                + "\"TLS.appid_at_3rd\":\"" + appid3rd +"\","
//                + "\"TLS.sdk_appid\":\"" + sdkappid +"\","
//                + "\"TLS.expire_after\":\"" + expire +"\""
//                + "}";
//        String time = String.valueOf(System.currentTimeMillis()/1000);
//        String SerialString = "TLS.appid_at_3rd:" + appid3rd + "\n"
//                + "TLS.account_type:" + accountType + "\n"
//                + "TLS.identifier:" + identifier + "\n"
//                + "TLS.sdk_appid:" + sdkappid + "\n"
//                + "TLS.time:" + time + "\n"
//                + "TLS.expire_after:" + expire +"\n";
//        try{
//            //Create Signature by SerialString
//            Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
//            signature.initSign(privKeyStruct);
//            signature.update(SerialString.getBytes(Charset.forName("UTF-8")));
//            byte[] signatureBytes = signature.sign();
//
//            String sigTLS = Base64.toBase64String(signatureBytes);
//
//            //Add TlsSig to jsonString
//            JSONObject jsonObject= new JSONObject(jsonString);
//            jsonObject.put("TLS.sig", sigTLS);
//            jsonObject.put("TLS.time", time);
//            jsonString = jsonObject.toString();
//
//            //compression
//            Deflater compresser = new Deflater();
//            compresser.setInput(jsonString.getBytes(Charset.forName("UTF-8")));
//
//            compresser.finish();
//            byte [] compressBytes = new byte [512];
//            int compressBytesLength = compresser.deflate(compressBytes);
//            compresser.end();
//
//            result.urlSig = new String(base64_url.base64EncodeUrl(Arrays.copyOfRange(compressBytes,0,compressBytesLength)));
//        } catch(Exception e) {
//            e.printStackTrace();
//            result.errMessage = e.getMessage();
//        }
//
//        return result;
//    }
//
//    /**
//     * 校验 tls 票据
//     * @param sig 返回 tls 票据
//     * @param appid3rd 填写与 sdkAppid 一致的字符串形式的值
//     * @param sdkappid 应的 appid
//     * @param identifier 用户 id
//     * @param accountType 创建应用后在配置页面上展示的 acctype
//     * @param  pubKeyContent 用于校验 tls 票据的公钥内容，但是需要先将公钥文件转换为 java 原生 api 使用的格式，下面是推荐的命令
//     *         openssl pkcs8 -topk8 -in ec_key.pem -outform PEM -out p8_priv.pem -nocrypt
//     * @return 如果出错 CheckTLSSignatureResult 中的 verifyResult 为 false，错误信息在 errMsg，校验成功为 true
//     */
//    @Deprecated
//    public static CheckTLSSignatureResult CheckTLSSignature(String sig, String appid3rd, long sdkappid,
//                                                            String identifier, long accountType,
//                                                            String pubKeyContent) {
//        CheckTLSSignatureResult result = new CheckTLSSignatureResult();
//        Security.addProvider(new BouncyCastleProvider());
//
//        byte [] compressBytes = base64_url.base64DecodeUrl(sig.getBytes(Charset.forName("UTF-8")));
//
//        //Decompression
//        Inflater decompression =  new Inflater();
//        decompression.setInput(compressBytes, 0, compressBytes.length);
//        byte [] decompressBytes = new byte [1024];
//        int decompressLength;
//        try {
//            decompressLength = decompression.inflate(decompressBytes);
//        } catch (DataFormatException e){
//            result.errMessage = "uncompress data error:" + e.getMessage();
//            return  result;
//        }
//        decompression.end();
//
//        String jsonString = new String(Arrays.copyOfRange(decompressBytes, 0, decompressLength));
//
//        //Get TLS.Sig from json
//        JSONObject jsonObject= new JSONObject(jsonString);
//        String sigTLS = jsonObject.getString("TLS.sig");
//
//        //debase64 TLS.Sig to get serailString
//        byte[] signatureBytes = Base64.decode(sigTLS.getBytes(Charset.forName("UTF-8")));
//
//        try{
//
//            String sigTime = jsonObject.getString("TLS.time");
//            String sigExpire = jsonObject.getString("TLS.expire_after");
//
//            //checkTime
//            if( System.currentTimeMillis()/1000 - Long.parseLong(sigTime) > Long.parseLong(sigExpire))
//            {
//                result.errMessage = new String("TLS sig is out of date ");
//                System.out.println("Timeout");
//                return result;
//            }
//
//            //Get Serial String from json
//            String SerialString = "TLS.appid_at_3rd:" + appid3rd + "\n"
//                    + "TLS.account_type:" + accountType + "\n"
//                    + "TLS.identifier:" + identifier + "\n"
//                    + "TLS.sdk_appid:" + sdkappid + "\n"
//                    + "TLS.time:" + sigTime + "\n"
//                    + "TLS.expire_after:" + sigExpire + "\n";
//
//            Reader reader = new CharArrayReader(pubKeyContent.toCharArray());
//            PEMParser  parser = new PEMParser(reader);
//            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
//            Object obj = parser.readObject();
//            parser.close();
//            PublicKey pubKeyStruct  = converter.getPublicKey((SubjectPublicKeyInfo) obj);
//
//            Signature signature = Signature.getInstance("SHA256withECDSA","BC");
//            signature.initVerify(pubKeyStruct);
//            signature.update(SerialString.getBytes(Charset.forName("UTF-8")));
//            result.verifyResult = signature.verify(signatureBytes);
//        } catch(Exception e) {
//            e.printStackTrace();
//            result.errMessage = "Failed in checking sig";
//        }
//
//        return result;
//    }
//
//    /**
//     * 生成 tls 票据，精简参数列表，有效期默认为 180 天
//     * @param skdAppid 应用的 sdkappid
//     * @param identifier 用户 id
//     * @param priKeyContent 私钥文件内容
//     * @return GenTLSSignatureResult
//     */
//    public static GenTLSSignatureResult GenTLSSignatureEx(
//            long skdAppid,
//            String identifier,
//            String priKeyContent) {
//        return GenTLSSignatureEx(skdAppid, identifier, priKeyContent, 3600*24*180);
//    }
//
//    /**
//     * 生成 tls 票据，精简参数列表
//     * @param skdAppid 应用的 sdkappid
//     * @param identifier 用户 id
//     * @param priKeyContent 私钥文件内容
//     * @param expire 有效期，以秒为单位，推荐时长一个月
//     * @return GenTLSSignatureResult
//     */
//    public static GenTLSSignatureResult GenTLSSignatureEx(
//            long skdAppid,
//            String identifier,
//            String priKeyContent,
//            long expire) {
//        return GenTLSSignature(expire, "0", skdAppid, identifier, 0, priKeyContent);
//    }
//
//    public static CheckTLSSignatureResult CheckTLSSignatureEx(
//            String sig,
//            long sdkappid,
//            String identifier,
//            String publicKey) throws DataFormatException {
//
//        CheckTLSSignatureResult result = new CheckTLSSignatureResult();
//        Security.addProvider(new BouncyCastleProvider());
//
//        byte [] compressBytes = base64_url.base64DecodeUrl(sig.getBytes(Charset.forName("UTF-8")));
//
//        //Decompression
//        Inflater decompression =  new Inflater();
//        decompression.setInput(compressBytes, 0, compressBytes.length);
//        byte[] decompressBytes = new byte[1024];
//        int decompressLength = decompression.inflate(decompressBytes);
//        decompression.end();
//
//        String jsonString = new String(Arrays.copyOfRange(decompressBytes, 0, decompressLength));
//
//        //Get TLS.Sig from json
//        JSONObject jsonObject= new JSONObject(jsonString);
//        String sigTLS = jsonObject.getString("TLS.sig");
//
//        //debase64 TLS.Sig to get serailString
//        byte[] signatureBytes = Base64.decode(sigTLS.getBytes(Charset.forName("UTF-8")));
//
//        try {
//            String strSdkappid = jsonObject.getString("TLS.sdk_appid");
//            String sigTime = jsonObject.getString("TLS.time");
//            String sigExpire = jsonObject.getString("TLS.expire_after");
//
//            if (Integer.parseInt(strSdkappid) != sdkappid)
//            {
//                result.errMessage = new String(	"sdkappid "
//                        + strSdkappid
//                        + " in tls sig not equal sdkappid "
//                        + sdkappid
//                        + " in request");
//                return result;
//            }
//
//            if ( System.currentTimeMillis()/1000 - Long.parseLong(sigTime) > Long.parseLong(sigExpire)) {
//                result.errMessage = new String("TLS sig is out of date");
//                return result;
//            }
//
//            //Get Serial String from json
//            String SerialString = "TLS.appid_at_3rd:" + 0 + "\n"
//                    + "TLS.account_type:" + 0 + "\n"
//                    + "TLS.identifier:" + identifier + "\n"
//                    + "TLS.sdk_appid:" + sdkappid + "\n"
//                    + "TLS.time:" + sigTime + "\n"
//                    + "TLS.expire_after:" + sigExpire + "\n";
//
//            Reader reader = new CharArrayReader(publicKey.toCharArray());
//            PEMParser  parser = new PEMParser(reader);
//            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
//            Object obj = parser.readObject();
//            parser.close();
//            PublicKey pubKeyStruct  = converter.getPublicKey((SubjectPublicKeyInfo) obj);
//
//            Signature signature = Signature.getInstance("SHA256withECDSA","BC");
//            signature.initVerify(pubKeyStruct);
//            signature.update(SerialString.getBytes(Charset.forName("UTF-8")));
//            boolean bool = signature.verify(signatureBytes);
//            result.expireTime = Integer.parseInt(sigExpire);
//            result.initTime = Integer.parseInt(sigTime);
//            result.verifyResult = bool;
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//            result.errMessage = "Failed in checking sig";
//        }
//
//        return result;
//    }
//
//    public static GenTLSSignatureResult genSig(
//            long sdkappid,
//            String identifier,
//            String priKey) {
//        // 默认 180 天
//        return GenTLSSignature(24*3600*180, "0", sdkappid, identifier, 0, priKey);
//    }
//
//    public static GenTLSSignatureResult genSig(
//            long sdkappid,
//            String identifier,
//            int expire,
//            String priKey) {
//        return GenTLSSignature(expire, "0", sdkappid, identifier, 0, priKey);
//    }
//}
