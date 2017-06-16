package com.taoke.taokestudent.model;

import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ListCallBack;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.entity.OnlineExerciseBaseClassItem;
import com.taoke.taokestudent.entity.OnlineExerciseChapter;
import com.taoke.taokestudent.entity.OnlineExerciseQuestion;
import com.taoke.taokestudent.entity.OnlineExerciseTestResultResponseData;
import com.taoke.taokestudent.entity.net.OnlineExerciseBaseClassResponse;
import com.taoke.taokestudent.entity.net.OnlineExerciseChapterListResponse;
import com.taoke.taokestudent.entity.net.OnlineExerciseQuestionListResponse;
import com.taoke.taokestudent.entity.net.OnlineExerciseSubmitResultResponse;
import com.taoke.taokestudent.entity.net.OnlineExerciseTestResultResponse;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;

import java.util.List;

/**
 * 在线做题业务
 */
public class OnlineExerciseModel {
    /* 请求队列 */
    private static RequestQueue requestQueue = NoHttp.newRequestQueue(1);

    public OnlineExerciseModel() {
    }

    /**
     * 答题报告
     *
     * @param recordId 答题记录
     * @param callBack 回调
     */
    public void getTestReport(String recordId, final ObjectCallBack<OnlineExerciseTestResultResponseData> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.ONLINE_EXERCISE_GET_RESULT, RequestMethod.POST);

        // 添加参数
        request.add("jilu_id", recordId);

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
                OnlineExerciseTestResultResponse testResultResponse = JSON.parseObject(result, OnlineExerciseTestResultResponse.class);
                // 判断是否成功
                if (testResultResponse.getCode() == 0) { // 成功
                    // 回调
                    callBack.onSuccess(testResultResponse.getData());
                } else { // 失败
                    Exception e = new Exception(testResultResponse.getInfo());
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
     * 上传答题结果
     *
     * @param uid        用户id
     * @param num        题目数量
     * @param startTime  答题开始时间
     * @param useTime    答题用时
     * @param questionId 题目id
     * @param userAnswer 用户答案
     * @param callBack   回调
     */
    public void submitResult(String uid, int num, long startTime, String useTime,
                             String questionId, String userAnswer, final ObjectCallBack<String> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.ONLINE_EXERCISE_SUBMIT_RESULT, RequestMethod.POST);

        // 添加参数
        request.add("uid", uid);
        request.add("num", num);
        request.add("start_time", startTime);
        request.add("use_time", useTime);
        request.add("question_id", questionId);
        request.add("user_answer", userAnswer);

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
                OnlineExerciseSubmitResultResponse submitResultResponse = JSON.parseObject(result, OnlineExerciseSubmitResultResponse.class);
                // 判断是否成功
                if (submitResultResponse.getCode() == 0) { // 成功
                    // 回调
                    callBack.onSuccess(submitResultResponse.getInfo());
                } else { // 失败
                    Exception e = new Exception(submitResultResponse.getInfo());
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
     * 获取章节
     *
     * @param fid      父ID
     * @param num      题目数量
     * @param callBack 回调
     */
    public void getQuestionList(String fid, int num, final ListCallBack<OnlineExerciseQuestion> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.ONLINE_EXERCISE_GET_QUESTION_LIST, RequestMethod.POST);

        // 添加参数
        request.add("fid", fid);
        request.add("num", num);

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
                OnlineExerciseQuestionListResponse listResponse = JSON.parseObject(result, OnlineExerciseQuestionListResponse.class);
                // 判断是否成功
                if (listResponse.getCode() == 0) { // 成功
                    // 回调
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
     * 获取章节
     *
     * @param fid      父ID
     * @param callBack 回调
     */
    public void getChapterList(String fid, final ListCallBack<OnlineExerciseChapter> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.ONLINE_EXERCISE_GET_CHAPTER, RequestMethod.POST);

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
                OnlineExerciseChapterListResponse listResponse = JSON.parseObject(result, OnlineExerciseChapterListResponse.class);
                // 判断是否成功
                if (listResponse.getCode() == 0) { // 成功
                    // 回调
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
     * 获取模块分类
     *
     * @param fid      父ID
     * @param callBack 回调
     */
    public void getMokuai(String fid, final ListCallBack<OnlineExerciseBaseClassItem> callBack) {
        // 查询本地数据库是否保存了数据
        List<OnlineExerciseBaseClassItem> list = OnlineExerciseBaseClassItem.find(OnlineExerciseBaseClassItem.class, "fid = ?", fid);
        if (list.size() > 0) {
            callBack.onSuccess(list);
            return;
        }

        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.ONLINE_EXERCISE_GET_MOKUAI, RequestMethod.POST);

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
                OnlineExerciseBaseClassResponse baseClassResponse = JSON.parseObject(result, OnlineExerciseBaseClassResponse.class);
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
    public void getBasicClass(String fid, String level, final ListCallBack<OnlineExerciseBaseClassItem> callBack) {
        // 查询本地数据库是否保存了数据
        List<OnlineExerciseBaseClassItem> list = OnlineExerciseBaseClassItem.find(OnlineExerciseBaseClassItem.class, "fid = ?", fid);
        if (list.size() > 0) {
            callBack.onSuccess(list);
            return;
        }

        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.ONLINE_EXERCISE_GET_BASIC_CLASS, RequestMethod.POST);

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
                OnlineExerciseBaseClassResponse baseClassResponse = JSON.parseObject(result, OnlineExerciseBaseClassResponse.class);
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
    public void saveToDB(final List<OnlineExerciseBaseClassItem> list) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                for (OnlineExerciseBaseClassItem item : list) {
                    item.save();
                }
                return null;
            }
        }.execute();
    }
}
