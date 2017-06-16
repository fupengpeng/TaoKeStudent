package com.taoke.taokestudent.model;

import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ListCallBack;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.entity.ExerciseChapter;
import com.taoke.taokestudent.entity.MicorLessonVideoBaseClassItem;
import com.taoke.taokestudent.entity.MicroDataTypeItem;
import com.taoke.taokestudent.entity.MicroExerciseQuestionListResponseData;
import com.taoke.taokestudent.entity.MicroLessonDetailListResponseData;
import com.taoke.taokestudent.entity.MicroLessonListResponseData;
import com.taoke.taokestudent.entity.MicroLessonVideoBaseClass;
import com.taoke.taokestudent.entity.net.MicroDataTypeResponse;
import com.taoke.taokestudent.entity.net.MicroDetailChargeVideoUrlResponse;
import com.taoke.taokestudent.entity.net.MicroExerciseChapterListResponse;
import com.taoke.taokestudent.entity.net.MicroExerciseQuestionListResponse;
import com.taoke.taokestudent.entity.net.MicroLessonDetailListResponse;
import com.taoke.taokestudent.entity.net.MicroLessonListResponse;
import com.taoke.taokestudent.entity.net.MicroLessonVideoBaseClassResponse;
import com.taoke.taokestudent.entity.net.PayWeiKeResponse;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;

import java.util.List;

/**
 * 微课视频业务
 */
public class MicroLessonVideoModel {
    /* 请求队列 */
    private static RequestQueue requestQueue = NoHttp.newRequestQueue(1);

    /**
     * 加载我的课程列表
     *
     * @param baseClass 当前分类
     * @param uid       用户ID
     * @param p         页码
     * @param callBack  回调
     */
    public void loadMyLessonList(MicroLessonVideoBaseClass baseClass, String uid, int p, final ObjectCallBack<MicroLessonListResponseData> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.MICRO_MY_LESSON_LIST, RequestMethod.GET);

        // 添加参数
        request.add("moKuaiId", baseClass.getMokuai());
        request.add("banBenId", baseClass.getBanben());
        request.add("uid", uid);
        request.add("p", p);

        //将request加入requestQueue
        requestQueue.add(0, request, new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                // 服务器响应码。
                int responseCode = response.getHeaders().getResponseCode();
                // 这里一定要判断状态码，经测试，404错误时也走这个回调方法
                if (responseCode != 200) {
                    // 请求失败
                    Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                    callBack.onFail(e);
                    return;
                }

                // 响应结果。
                String result = response.get();
                // 解析
                MicroLessonListResponse listResponse = JSON.parseObject(result, MicroLessonListResponse.class);
                // 判断是否成功
                if (listResponse.getCode() == 0) { // 成功
                    callBack.onSuccess(listResponse.getData());
                } else { // 失败
                    Exception e = new Exception(listResponse.getInfo());
                    callBack.onFail(e);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                // 请求失败
                Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                callBack.onFail(e);
            }
        });
    }


    /**
     * 加载习题微课-课程详情-习题列表
     *
     * @param classId  课时Id
     * @param p        页码
     * @param callBack 回调
     */
    public void loadExerciseQuestionList(String classId, int p, final ObjectCallBack<MicroExerciseQuestionListResponseData> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.MICRO_EXERCISE_QUESTION_LIST, RequestMethod.GET);

        // 添加参数
        request.add("classId", classId);
        request.add("p", p);

        //将request加入requestQueue
        requestQueue.add(0, request, new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                // 服务器响应码。
                int responseCode = response.getHeaders().getResponseCode();
                // 这里一定要判断状态码，经测试，404错误时也走这个回调方法
                if (responseCode != 200) {
                    // 请求失败
                    Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                    callBack.onFail(e);
                    return;
                }

                // 响应结果。
                String result = response.get();
                // 解析
                MicroExerciseQuestionListResponse listResponse = JSON.parseObject(result, MicroExerciseQuestionListResponse.class);
                // 判断是否成功
                if (listResponse.getCode() == 0) { // 成功
                    callBack.onSuccess(listResponse.getData());
                } else { // 失败
                    Exception e = new Exception(listResponse.getInfo());
                    callBack.onFail(e);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                // 请求失败
                Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                callBack.onFail(e);
            }
        });
    }

    /**
     * 加载习题微课-课程详情-章节列表
     *
     * @param subjectId 课程ID
     * @param callBack  回调
     */
    public void loadExerciseChapterList(String subjectId, final ListCallBack<ExerciseChapter> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.MICRO_EXERCISE_CHAPTER_LIST, RequestMethod.GET);

        // 添加参数
        request.add("subjectId", subjectId);

        //将request加入requestQueue
        requestQueue.add(0, request, new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                // 服务器响应码。
                int responseCode = response.getHeaders().getResponseCode();
                // 这里一定要判断状态码，经测试，404错误时也走这个回调方法
                if (responseCode != 200) {
                    // 请求失败
                    Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                    callBack.onFail(e);
                    return;
                }

                // 响应结果。
                String result = response.get();
                // 解析
                MicroExerciseChapterListResponse listResponse = JSON.parseObject(result, MicroExerciseChapterListResponse.class);
                // 判断是否成功
                if (listResponse.getCode() == 0) { // 成功
                    callBack.onSuccess(listResponse.getData());
                } else { // 失败
                    Exception e = new Exception(listResponse.getInfo());
                    callBack.onFail(e);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                // 请求失败
                Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                callBack.onFail(e);
            }
        });
    }

    /**
     * 加载视频详情中的收费课程播放地址
     *
     * @param uid       用户id
     * @param subjectId 课程ID
     * @param subId     视频id
     * @param callBack  回调
     */
    public void loadDetailChargeLessonVideoURL(String uid, String subjectId, String subId, final ListCallBack<String> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.MICRO_LESSON_VIDEO_DETAIL_CHARGE_VIDEO_URL, RequestMethod.GET);

        // 添加参数
        request.add("uid", uid);
        request.add("subjectId", subjectId);
        request.add("subId", subId);


        //将request加入requestQueue
        requestQueue.add(0, request, new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                // 服务器响应码。
                int responseCode = response.getHeaders().getResponseCode();
                // 这里一定要判断状态码，经测试，404错误时也走这个回调方法
                if (responseCode != 200) {
                    // 请求失败
                    Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                    callBack.onFail(e);
                    return;
                }

                // 响应结果。
                String result = response.get();
                // 解析
                MicroDetailChargeVideoUrlResponse urlResponse = JSON.parseObject(result, MicroDetailChargeVideoUrlResponse.class);
                // 判断是否成功
                if (urlResponse.getCode() == 0) { // 成功
                    callBack.onSuccess(urlResponse.getData());
                } else { // 失败
                    Exception e = new Exception(urlResponse.getInfo());
                    callBack.onFail(e);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                // 请求失败
                Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                callBack.onFail(e);
            }
        });
    }

    /**
     * 加载视频详情中的收费课程列表
     *
     * @param subjectId 课程ID
     * @param p         页码
     * @param callBack  回调
     */
    public void loadDetailChargeLessonList(String subjectId, int p, final ObjectCallBack<MicroLessonDetailListResponseData> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.MICRO_LESSON_VIDEO_DETAIL_CHARGE, RequestMethod.GET);

        // 添加参数
        request.add("subjectId", subjectId);
        request.add("p", p);

        //将request加入requestQueue
        requestQueue.add(0, request, new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                // 服务器响应码。
                int responseCode = response.getHeaders().getResponseCode();
                // 这里一定要判断状态码，经测试，404错误时也走这个回调方法
                if (responseCode != 200) {
                    // 请求失败
                    Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                    callBack.onFail(e);
                    return;
                }

                // 响应结果。
                String result = response.get();
                // 解析
                MicroLessonDetailListResponse listResponse = JSON.parseObject(result, MicroLessonDetailListResponse.class);
                // 判断是否成功
                if (listResponse.getCode() == 0) { // 成功
                    callBack.onSuccess(listResponse.getData());
                } else { // 失败
                    Exception e = new Exception(listResponse.getInfo());
                    callBack.onFail(e);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                // 请求失败
                Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                callBack.onFail(e);
            }
        });
    }


    /**
     * 加载视频详情中的免费课程列表
     *
     * @param subjectId 课程ID
     * @param p         页码
     * @param callBack  回调
     */
    public void loadDetailFreeLessonList(String subjectId, int p, final ObjectCallBack<MicroLessonDetailListResponseData> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.MICRO_LESSON_VIDEO_DETAIL_FREE, RequestMethod.GET);

        // 添加参数
        request.add("subjectId", subjectId);
        request.add("p", p);

        //将request加入requestQueue
        requestQueue.add(0, request, new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                // 服务器响应码。
                int responseCode = response.getHeaders().getResponseCode();
                // 这里一定要判断状态码，经测试，404错误时也走这个回调方法
                if (responseCode != 200) {
                    // 请求失败
                    Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                    callBack.onFail(e);
                    return;
                }

                // 响应结果。
                String result = response.get();
                // 解析
                MicroLessonDetailListResponse listResponse = JSON.parseObject(result, MicroLessonDetailListResponse.class);
                // 判断是否成功
                if (listResponse.getCode() == 0) { // 成功
                    callBack.onSuccess(listResponse.getData());
                } else { // 失败
                    Exception e = new Exception(listResponse.getInfo());
                    callBack.onFail(e);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                // 请求失败
                Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                callBack.onFail(e);
            }
        });
    }


    /**
     * 购买课程
     *
     * @param uid      用户ID
     * @param tid      本次购买资源的id
     * @param callBack 回调
     */
    public void payWeiKe(String uid, String tid, final ObjectCallBack<String> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.MICRO_LESSON_VIDEO_PAY_WEIKE, RequestMethod.POST);

        // 添加参数
        request.add("uid", uid);
        request.add("tid", tid);

        //将request加入requestQueue
        requestQueue.add(0, request, new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                // 服务器响应码。
                int responseCode = response.getHeaders().getResponseCode();
                // 这里一定要判断状态码，经测试，404错误时也走这个回调方法
                if (responseCode != 200) {
                    // 请求失败
                    Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                    callBack.onFail(e);
                    return;
                }

                // 响应结果。
                String result = response.get();
                // 解析
                PayWeiKeResponse payWeiKeResponse = JSON.parseObject(result, PayWeiKeResponse.class);
                // 判断是否成功
                if (payWeiKeResponse.getCode() == 0) { // 成功
                    callBack.onSuccess(payWeiKeResponse.getInfo());
                } else { // 失败
                    Exception e = new Exception(payWeiKeResponse.getInfo());
                    callBack.onFail(e);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                // 请求失败
                Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                callBack.onFail(e);
            }
        });
    }


    /**
     * 加载课程列表
     *
     * @param baseClass 当前分类
     * @param uid       用户ID
     * @param p         页码
     * @param callBack  回调
     */
    public void loadLessonList(MicroLessonVideoBaseClass baseClass, String uid, int p, final ObjectCallBack<MicroLessonListResponseData> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.MICRO_LESSON_VIDEO_LESSON_LIST, RequestMethod.GET);

        // 添加参数
        request.add("moKuaiId", baseClass.getMokuai());
        request.add("banBenId", baseClass.getBanben());
        request.add("type", baseClass.getDataType());
        request.add("uid", uid);
        request.add("p", p);

        //将request加入requestQueue
        requestQueue.add(0, request, new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                // 服务器响应码。
                int responseCode = response.getHeaders().getResponseCode();
                // 这里一定要判断状态码，经测试，404错误时也走这个回调方法
                if (responseCode != 200) {
                    // 请求失败
                    Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                    callBack.onFail(e);
                    return;
                }

                // 响应结果。
                String result = response.get();
                // 解析
                MicroLessonListResponse listResponse = JSON.parseObject(result, MicroLessonListResponse.class);
                // 判断是否成功
                if (listResponse.getCode() == 0) { // 成功
                    callBack.onSuccess(listResponse.getData());
                } else { // 失败
                    Exception e = new Exception(listResponse.getInfo());
                    callBack.onFail(e);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                // 请求失败
                Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                callBack.onFail(e);
            }
        });
    }


    /**
     * 获取资源分类
     *
     * @param callBack 回调
     */
    public void getDataType(final ListCallBack<MicroDataTypeItem> callBack) {
        // 查询本地数据库是否保存了数据
        List<MicroDataTypeItem> list = MicroDataTypeItem.listAll(MicroDataTypeItem.class);
        if (list.size() > 0) {
            callBack.onSuccess(list);
            return;
        }

        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.MICRO_LESSON_VIDEO_GET_DATA_TYPE, RequestMethod.POST);

        //将request加入requestQueue
        requestQueue.add(0, request, new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                // 服务器响应码。
                int responseCode = response.getHeaders().getResponseCode();
                // 这里一定要判断状态码，经测试，404错误时也走这个回调方法
                if (responseCode != 200) {
                    // 请求失败
                    Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                    callBack.onFail(e);
                    return;
                }

                // 响应结果。
                String result = response.get();
                // 解析
                MicroDataTypeResponse dataTypeResponse = JSON.parseObject(result, MicroDataTypeResponse.class);
                // 判断是否成功
                if (dataTypeResponse.getCode() == 0) { // 成功
                    if (dataTypeResponse.getData() == null || dataTypeResponse.getData().size() < 1) {
                        Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                        callBack.onFail(e);
                        return;
                    }
                    // 保存到数据库
                    saveDataTypeToDB(dataTypeResponse.getData());
                    // 回调
                    callBack.onSuccess(dataTypeResponse.getData());
                } else { // 失败
                    Exception e = new Exception(dataTypeResponse.getInfo());
                    callBack.onFail(e);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                // 请求失败
                Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                callBack.onFail(e);
            }
        });
    }

    /**
     * 获取模块分类
     *
     * @param fid      父ID
     * @param callBack 回调
     */
    public void getMokuai(String fid, final ListCallBack<MicorLessonVideoBaseClassItem> callBack) {
        // 查询本地数据库是否保存了数据
        List<MicorLessonVideoBaseClassItem> list = MicorLessonVideoBaseClassItem.find(MicorLessonVideoBaseClassItem.class, "fid = ?", fid);
        if (list.size() > 0) {
            callBack.onSuccess(list);
            return;
        }

        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.MICRO_LESSON_VIDEO_GET_MOKUAI, RequestMethod.POST);

        // 添加参数
        request.add("fid", fid);

        //将request加入requestQueue
        requestQueue.add(0, request, new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                // 服务器响应码。
                int responseCode = response.getHeaders().getResponseCode();
                // 这里一定要判断状态码，经测试，404错误时也走这个回调方法
                if (responseCode != 200) {
                    // 请求失败
                    Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                    callBack.onFail(e);
                    return;
                }

                // 响应结果。
                String result = response.get();
                // 解析
                MicroLessonVideoBaseClassResponse baseClassResponse = JSON.parseObject(result, MicroLessonVideoBaseClassResponse.class);
                // 判断是否成功
                if (baseClassResponse.getCode() == 0) { // 成功
                    if (baseClassResponse.getData() == null || baseClassResponse.getData().size() < 1) {
                        Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                        callBack.onFail(e);
                        return;
                    }
                    // 保存到数据库
                    saveToDB(baseClassResponse.getData());
                    // 回调
                    callBack.onSuccess(baseClassResponse.getData());
                } else { // 失败
                    Exception e = new Exception(baseClassResponse.getInfo());
                    callBack.onFail(e);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                // 请求失败
                Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                callBack.onFail(e);
            }
        });
    }

    /**
     * 获取基础分类
     *
     * @param fid      父ID
     * @param level    分类级别
     * @param callBack 回调
     */
    public void getBasicClass(String fid, String level, final ListCallBack<MicorLessonVideoBaseClassItem> callBack) {
        // 查询本地数据库是否保存了数据
        List<MicorLessonVideoBaseClassItem> list = MicorLessonVideoBaseClassItem.find(MicorLessonVideoBaseClassItem.class, "fid = ?", fid);
        if (list.size() > 0) {
            callBack.onSuccess(list);
            return;
        }

        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.MICRO_LESSON_VIDEO_GET_BASIC_CLASS, RequestMethod.POST);

        // 添加参数
        request.add("fid", fid);
        request.add("level", level);

        //将request加入requestQueue
        requestQueue.add(0, request, new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                // 服务器响应码。
                int responseCode = response.getHeaders().getResponseCode();
                // 这里一定要判断状态码，经测试，404错误时也走这个回调方法
                if (responseCode != 200) {
                    // 请求失败
                    Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                    callBack.onFail(e);
                    return;
                }

                // 响应结果。
                String result = response.get();
                // 解析
                MicroLessonVideoBaseClassResponse baseClassResponse = JSON.parseObject(result, MicroLessonVideoBaseClassResponse.class);
                // 判断是否成功
                if (baseClassResponse.getCode() == 0) { // 成功
                    if (baseClassResponse.getData() == null || baseClassResponse.getData().size() < 1) {
                        Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                        callBack.onFail(e);
                        return;
                    }
                    // 保存到数据库
                    saveToDB(baseClassResponse.getData());
                    // 回调
                    callBack.onSuccess(baseClassResponse.getData());
                } else { // 失败
                    Exception e = new Exception(baseClassResponse.getInfo());
                    callBack.onFail(e);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                // 请求失败
                Exception e = new Exception(Consts.NET_REQUEST_EXCEPTION);
                callBack.onFail(e);
            }
        });
    }

    /**
     * 将基础分类保存到数据库
     *
     * @param list 基础分类数据
     */
    public void saveToDB(final List<MicorLessonVideoBaseClassItem> list) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                for (MicorLessonVideoBaseClassItem item : list) {
                    item.save();
                }
                return null;
            }
        }.execute();
    }

    /**
     * 将资源分类保存到数据库
     *
     * @param list 基础分类数据
     */
    public void saveDataTypeToDB(final List<MicroDataTypeItem> list) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                for (MicroDataTypeItem item : list) {
                    item.save();
                }
                return null;
            }
        }.execute();
    }
}
