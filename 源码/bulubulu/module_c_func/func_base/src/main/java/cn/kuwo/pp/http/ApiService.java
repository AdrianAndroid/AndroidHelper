package cn.kuwo.pp.http;


import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import cn.kuwo.networker.Urls;
import cn.kuwo.pp.http.bean.AppUidBean;
import cn.kuwo.pp.http.bean.BaseHttpResult;
import cn.kuwo.pp.http.bean.BaseListData;
import cn.kuwo.pp.http.bean.GuideBean;
import cn.kuwo.pp.http.bean.MatchCountBean;
import cn.kuwo.pp.http.bean.PlayLogInfo;
import cn.kuwo.pp.http.bean.QiniuToken;
import cn.kuwo.pp.http.bean.QuestionAnswerModel;
import cn.kuwo.pp.http.bean.QuestionModel;
import cn.kuwo.pp.http.bean.UserCommonBean;
import cn.kuwo.pp.http.bean.UserOnlineBean;
import cn.kuwo.pp.http.bean.UserTrendBean;
import cn.kuwo.pp.http.bean.comment.CommentBean;
import cn.kuwo.pp.http.bean.systemmessage.SystemMessageResult;
import cn.kuwo.pp.http.bean.topic.TopicItemBean;
import cn.kuwo.pp.http.bean.user.BindMobileParam;
import cn.kuwo.pp.http.bean.user.LoginParam;
import cn.kuwo.pp.http.bean.user.LoginResult;
import cn.kuwo.pp.http.bean.user.UpdateProfileParam;
import cn.kuwo.pp.http.bean.user.UploadHeadResult;
import cn.kuwo.pp.http.bean.user.UserSigResult;
import cn.kuwo.pp.http.bean.voice.VoiceInfo;
import cn.kuwo.pp.manager.FriendList.FriendListItem;
import cn.kuwo.pp.manager.FriendList.FriendListManager;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by shihc on 2016/10/31.
 * 接口声明
 */

public interface ApiService {

//    @GET
//    Observable<ResponseBody> executeGet(
//            @Url String url,
//            @QueryMap Map<String, String> maps);
//
//
//    @POST
//    Observable<ResponseBody> executePost(
//            @Url String url,
//            @QueryMap Map<String, String> maps);
//
//    @POST("{url}")
//    Observable<ResponseBody> uploadFiles(
//            @Path("url") String url,
//            @Path("headers") Map<String, String> headers,
//            @Part("filename") String description,
//            @PartMap() Map<String, RequestBody> maps);
//
//    @Streaming
//    @GET
//    @Headers({"addBaseParameter: false", "encrypt: false"})
//    Observable<ResponseBody> downloadFile(@Url String fileUrl);

    //=================================================配置接口===========================================================

//    @GET(ConstantUrls.URL_CONFIG)
//    @Headers({"addBaseParameter: false", "encrypt: false"})
//    Observable<BaseHttpResult<JsonObject>> getConfig(@Query("sn") String sn);

    //=================================================登录相关接口===========================================================

//    /**
//     * 获取usersig
//     *
//     * @return
//     */
//    @GET(ConstantUrls.USER_GET_SIG)
//    @Headers({"encrypt: true"})
//    Observable<BaseHttpResult<UserSigResult>> getUserSig();

    /**
     * 发送验证码
     *
     * @param mobile 手机号
     * @param type   验证码类型，1-登录，2-绑定
     * @return
     */
    @GET(ConstantUrls.USER_SEND_SMS)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> sendSms(@Path("mobile") String mobile, @Query("type") int type);

    /**
     * 登录接口
     *
     * @param loginParam 登录参数
     * @return
     */
    @POST(ConstantUrls.USER_LOGIN)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<LoginResult>> login(@Body LoginParam loginParam);

    /**
     * 绑定/解绑手机号/第三方
     *
     * @param uid             用户id
     * @param bindMobileParam 参数
     * @return
     */
    @PUT(ConstantUrls.USER_BIND)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> bind(@Path("uid") String uid, @Query("token") String token, @Body BindMobileParam bindMobileParam);

    /**
     * 更新用户信息
     *
     * @param param
     * @param token
     * @param uid
     * @return
     */
    @PUT(ConstantUrls.USER_UPDATE_PROFILE)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<LoginResult>> updateProfile(@Path("uid") String uid, @Query("token") String token, @Body UpdateProfileParam param);

    /**
     * 上传图片
     *
     * @param uid
     * @param type 1-头像，2-背景图
     * @param file
     * @return
     */
    @Multipart
    @POST(ConstantUrls.USER_UPLOAD_PIC)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<UploadHeadResult>> uploadPic(@Path("uid") String uid, @Query("type") String type,
                                                           @Part List<MultipartBody.Part> file);

    @GET(ConstantUrls.USER_MINE_TREND_LIST)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<UserTrendBean>>> getMyTrendList(@Query("uid") String uid, @Query("pn") Integer pn, @Query("rn") Integer rn);

    @GET(ConstantUrls.USER_OTHER_TREND_LIST)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<UserTrendBean>>> getUserTrendList(@Query("uid") String uid, @Query("oid") String oid, @Query("pn") Integer pn, @Query("rn") Integer rn);

    @GET(ConstantUrls.USER_TREND_DELETE)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> deleteTrend(@Query("uid") String uid, @Query("tid") String tid);

    //=================================================视频相关接口===========================================================

    /**
     * 酷我后台获取七牛上传需用的token
     */
    @GET(ConstantUrls.URL_GET_QINIU_TOKEN)
    @Headers({"encrypt: true", Urls.HEADER_RELEASE})
    Observable<BaseHttpResult<QiniuToken>> requestQiniuToken();

    /**
     * 推荐的音频是否喜欢
     *
     * @param listenUid 音频的用户id
     * @param vid       收听的音频
     * @param like      1喜欢，2屏蔽
     * @return
     */
    @GET(ConstantUrls.URL_GET_LIKE_URL)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> sendLikeOpt(@Query("voiceUid") String listenUid, @Query("vid") String vid, @Query("like") String like);

    /**
     * 获取推荐音频内容列表
     *
     * @param card：喜欢的音频类型
     * @param sex：0女，1男    不传的时候，男女比例1:2返回
     * @return
     */
    @GET(ConstantUrls.URL_GET_RECOMMEND_VOICES)
    @Headers({"encrypt: true", Urls.HEADER_RELEASE})
    Observable<BaseHttpResult<BaseListData<VoiceInfo>>> getRecommendVoices(@Query("card") String card, @Query("sex") String sex);

    @GET(ConstantUrls.URL_GET_PLAY_VOICE)
    Observable<BaseHttpResult<PlayLogInfo>> sendPlayVoiceEvent(@Query("vid") String vid, @Query("voiceUid") String voiceUid);

    /**
     * 上传录制音频的pcm数据到后台进行分析
     *
     * @param params 音频pcm数据
     * @return
     */
    @Multipart
    @POST(ConstantUrls.URL_POST_JUDGE_VOICE)
    @Headers({"encrypt: false", Urls.HEADER_RELEASE})
    Observable<BaseHttpResult<VoiceInfo>> postVoiceForJudge(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST(ConstantUrls.URL_POST_REPORT_CHAT)
    @Headers({"addBaseParameter: false", "encrypt: false", Urls.HEADER_RELEASE})
    Observable<BaseHttpResult<Object>> postReportChat(@PartMap Map<String, RequestBody> params);


    /**
     * 获取用户音频信息
     *
     * @param uid 用户id
     * @return
     */
    @GET(ConstantUrls.URL_GET_USER_DETIAL)
    @Headers({"encrypt: true", Urls.HEADER_RELEASE})
    Observable<BaseHttpResult<VoiceInfo>> getUserDetail(@Query("voiceUid") String uid);

    @GET(ConstantUrls.URL_GET_GUIDE_LIST)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<GuideBean>>> getGuideList();

    @GET(ConstantUrls.URL_GET_SAVE_VOICE)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<VoiceInfo>> saveVoiceInfo(@Query("voiceUid") String uid);

    /**
     * 搜集通讯录信息
     *
     * @return
     */
    @POST(ConstantUrls.URL_CONTACT_PHONE)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> uploadContact(@Path("uid") String uid, @Body JsonObject contactBeans);

    @GET(ConstantUrls.URL_GET_REPORT)
    @Headers(Urls.HEADER_RELEASE)
    Observable<ResponseBody> sendReportReason(@Query("digest") String digest, @Query("digestId") String itemId, @Query("reason") String reason);

    @POST(ConstantUrls.URL_GET_APPUID)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<AppUidBean>> getAppUid(); //生成appuid接口


    //测测看题目
    @GET(ConstantUrls.URL_GET_QUESTION_LIST)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<QuestionModel>>> getQuestionList(@Query("uid") String uid, @Query("rn") Integer rn);

    //话题题目获取 rn 每页数量 pn页码数
    @GET(ConstantUrls.URL_GET_QUESTION_LIST)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<QuestionModel>>> getQuestionList(@Query("uid") String uid, @Query("rn") Integer rn, @Query("topicid") String topicid);

    // 获取话题下题目 // pagenum=1 rangenumber
    @GET(ConstantUrls.URL_topic_quesions)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<QuestionModel>>> getTopicQuestions(@Query("uid") String uid, @Query("topicid") String topicid, @Query("pn") Integer pn, @Query("rn") Integer rn);

    // 话题题目答题
    @GET(ConstantUrls.URL_topic_answer)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<QuestionAnswerModel>> getTopicAnswer(@Query("uid") String uid, @Query("qid") String qid, @Query("answer") String answer, @Query("topicid") String topicid);

    @GET(ConstantUrls.URL_GET_QUESTION_ANSWER)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<QuestionAnswerModel>> getQuestionAnswer(@Query("qid") String qid, @Query("answer") String answer);

    // 获取题目信息
    @GET(ConstantUrls.URL_GET_QUESTION_DETAIL)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<QuestionModel>>> getQuestionDetail(@Query("id") String qid);

    @GET(ConstantUrls.URL_GET_QUESTION_PASS)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> passQuestion(@Query("uid") String uid, @Query("qid") String qid);

    @GET(ConstantUrls.URL_GET_QUESTION_LIMIT)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<MatchCountBean>> getMatchLimit();

    @GET(ConstantUrls.URL_GET_QUESTION_SYNC)
    Observable<BaseHttpResult<Object>> syncMatchCount(@Query("num") int num, @Query("cnt") int cnt);

    @GET(ConstantUrls.URL_GET_CHAT_QUESTION)//获取题目
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<QuestionModel>>> getChatQuestion(@Query("oid") String uid, @Query("rn") Integer rn);

    //动态
    @GET(ConstantUrls.URL_GET_HOT_TREND_LIST)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<UserTrendBean>>> getHotTrendList(@Query("pn") Integer pn, @Query("rn") Integer rn);

    @GET(ConstantUrls.URL_GET_FAVOR_TREND_LIST)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<UserTrendBean>>> getFavorTrendList(@Query("pn") Integer pn, @Query("rn") Integer rn, @Query("uid") String uid);

    @GET(ConstantUrls.URL_GET_LATEST_TREND_LIST)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<UserTrendBean>>> getLatestTrendList(@Query("pn") Integer pn, @Query("rn") Integer rn);

    @GET(ConstantUrls.URL_GET_TOPIC_LEAST_TREND_LIST)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<UserTrendBean>>> getTopicLatestTrendList(@Query("uid") String uid, @Query("topicid") String topicid, @Query("pn") Integer pn, @Query("rn") Integer rn);

    @GET(ConstantUrls.URL_GET_TOPIC_HOT_TREND_LIST)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<UserTrendBean>>> getTopicHotTrendList(@Query("uid") String uid, @Query("topicid") String topicid, @Query("pn") Integer pn, @Query("rn") Integer rn);


    @Multipart
    @POST(ConstantUrls.URL_POST_BUILD_QUESTION)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<UserTrendBean>> publishQuestion(@Query("uid") String uid, @Query("topids") String topids, @Part List<MultipartBody.Part> file);

    @Multipart
    @POST(ConstantUrls.URL_POST_REPORT_ACTION)//用户行为数据上报
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> reportUserAction(@Part List<MultipartBody.Part> file);

    @GET(ConstantUrls.URL_GET_MATCH_USERS)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<VoiceInfo>>> matchUsers(@Query("uid") String uid);

    @Multipart
    @POST(ConstantUrls.URL_POST_USER_TREND)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<UserTrendBean>> publishUserTrend(@Query("uid") String uid, @Query("topids") String topids, @Part List<MultipartBody.Part> file);

    @GET(ConstantUrls.URL_GET_LIKE_TREND)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> likeTrend(@Query("uid") String uid, @Query("type") String type, @Query("tid") String tid);

    @GET(ConstantUrls.URL_GET_UNLIKE_TREND)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> unlikeTrend(@Query("uid") String uid, @Query("type") String type, @Query("tid") String tid);

    //话题
    @GET(ConstantUrls.URL_GET_TOPIC_HOT_LIST)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<TopicItemBean>>> getHotTopicList(@Query("uid") String uid, @Query("pn") Integer pn, @Query("rn") Integer rn);

    @GET(ConstantUrls.URL_GET_TOPIC_SUB_LIST)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<TopicItemBean>>> getSubTopicList(@Query("uid") String uid, @Query("pn") Integer pn, @Query("rn") Integer rn);

    @POST(ConstantUrls.URL_POST_ADD_TOPIC)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<TopicItemBean>> addTopic(@Query("uid") String uid, @Query("topname") String topname);

    @GET(ConstantUrls.URL_POST_SEARCH_TOPIC)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<TopicItemBean>>> searchTopic(@Query("uid") String uid, @Query("topname") String topname, @Query("pn") Integer pn, @Query("rn") Integer rn);

    @GET(ConstantUrls.URL_POST_SUB_TOPIC)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> subscribeTopic(@Query("uid") String uid, @Query("topicid") String topicid);

    @GET(ConstantUrls.URL_POST_Cancel_SUB_TOPIC)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> cancelSubscribeTopic(@Query("uid") String uid, @Query("topicid") String topicid);

    @GET(ConstantUrls.URL_GET_TOPIC_DETAIL)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<TopicItemBean>> getTopicDetail(@Query("uid") String uid, @Query("topicid") String topicid);

    //评论
    @POST(ConstantUrls.URL_POST_COMMENT_ADD)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> sendComment(@Path("source") int source, @Path("sourceId") String sourceId, @Query("uid") String uid, @Query("token") String token, @Body JsonObject content);

    @GET(ConstantUrls.URL_POST_COMMENT_FETCH)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<CommentBean>> getComments(@Path("source") int source, @Path("sourceId") String sourceId, @Query("type") int type, @Query("pn") int pn);

    @POST(ConstantUrls.URL_POST_COMMENT_LIKE)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> likeComment(@Path("source") int source, @Path("sourceId") String sourceId, @Query("uid") String uid, @Query("token") String token, @Body JsonObject commentId);

    @POST(ConstantUrls.URL_POST_COMMENT_UNLIKE)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> unLikeComment(@Path("source") int source, @Path("sourceId") String sourceId, @Query("uid") String uid, @Query("token") String token, @Body JsonObject commentId);

    // 好友关系

    @GET(ConstantUrls.URL_FRIEND_STATUS)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<FriendListManager.StatusBean>> friendListStatus(@Query("uid") String uid, @Query("oid") String oid);

    @GET(ConstantUrls.URL_FRIEND_FOLLOW)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> friendListFollow(@Query("uid") String uid, @Query("oid") String oid);

    @GET(ConstantUrls.URL_FRIEND_CANCEL)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> friendListCancel(@Query("uid") String uid, @Query("oid") String oid);

    @GET(ConstantUrls.URL_FRIEND_FOLLOWED)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<FriendListItem>>> friendListFollowed(@Query("uid") String uid, @Query("pn") Integer pn, @Query("rn") Integer rn);

    @GET(ConstantUrls.URL_FRIEND_MINE)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<FriendListItem>>> friendListMine(@Query("uid") String uid, @Query("pn") Integer pn, @Query("rn") Integer rn);

    @GET(ConstantUrls.URL_FRIEND_BOTH)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<FriendListItem>>> friendListBoth(@Query("uid") String uid, @Query("pn") Integer pn, @Query("rn") Integer rn);

    @GET(ConstantUrls.URL_FRIEND_STRANGER_LIST)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<FriendListItem>>> strangerList(@Query("pn") Integer pn, @Query("rn") Integer rn);

    @GET(ConstantUrls.URL_FRIEND_STRANGER_ADD)//添加邂逅
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> strangerAdd(@Query("oid") String oid);

    @GET(ConstantUrls.URL_FRIEND_STRANGER_DEL)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> strangerDel(@Query("oid") String oid);

    @GET(ConstantUrls.URL_MATCH_VALUE)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Number>> friendMatchValue(@Query("oid") String oid);//获取匹配度接口

    @GET(ConstantUrls.URL_USER_ONLINE)//返回的用户状态，目前支持的状态有：
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<UserOnlineBean>>> queryUserOnline(@Query("oids") String oids);

    @GET(ConstantUrls.URL_USER_COMMON)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<UserCommonBean>> friendCommonData(@Query("oid") String oid);

    // 积分
    @GET(ConstantUrls.URL_POINTS_INCR)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> pointsIncrease(@Query("uid") String uid, @Query("type") int type);

    @GET(ConstantUrls.URL_POINTS_USE)
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> pointsUse(@Query("uid") String uid, @Query("oid") String oid, @Query("type") int type);

    //系统消息
    @GET(ConstantUrls.URL_SYSTEM_MESSAGE_ALL)//获取消息
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<BaseListData<SystemMessageResult>>> updateSystemMessage(@Query("pn") Integer pn, @Query("rn") Integer rn);

    @Multipart
    @POST(ConstantUrls.URL_SYSTEM_MESSAGE_REPLY)//回复消息
    @Headers(Urls.HEADER_RELEASE)
    Observable<BaseHttpResult<Object>> userReplyMessage(@Query("type") int type, @Part List<MultipartBody.Part> body);
}
