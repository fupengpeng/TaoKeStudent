package com.taoke.taokestudent.model;

import android.graphics.Bitmap;
import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.taoke.taokestudent.common.Consts;
import com.taoke.taokestudent.common.ListCallBack;
import com.taoke.taokestudent.common.ObjectCallBack;
import com.taoke.taokestudent.entity.ConnectionAccount;
import com.taoke.taokestudent.entity.Order;
import com.taoke.taokestudent.entity.User;
import com.taoke.taokestudent.entity.net.CreateOrderResponse;
import com.taoke.taokestudent.entity.net.LoadConnectionAccountResponse;
import com.taoke.taokestudent.entity.net.LoadUserInfoByUidResponse;
import com.taoke.taokestudent.entity.net.LoginResponse;
import com.taoke.taokestudent.entity.net.RegisterResponse;
import com.taoke.taokestudent.entity.net.SendValidateCodeResponse;
import com.taoke.taokestudent.entity.net.UpdateUserInfoResponse;
import com.taoke.taokestudent.util.LogUtils;
import com.yolanda.nohttp.BasicBinary;
import com.yolanda.nohttp.BitmapBinary;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnUploadListener;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 用户信息业务
 */
public class UserModel {
    /* 请求队列 */
    private static RequestQueue requestQueue = NoHttp.newRequestQueue(1);

    public UserModel() {
    }

    /**
     * 解除绑定家长账号
     *
     * @param uid         uid
     * @param phoneNumber 手机号
     * @param password    密码
     * @param callBack    回调
     */
    public void relieveAccount(String uid, String phoneNumber, String password, final ObjectCallBack<String> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.PERSONAL_CENTER_RELIEVE_CONNECTION_ACCOUNT, RequestMethod.POST);

        // 添加参数
        request.add("uid", uid);
        request.add("username", phoneNumber);
        request.add("password", password);

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
                RegisterResponse registerResponse = JSON.parseObject(result, RegisterResponse.class);
                // 判断是否成功
                if (registerResponse.getCode() == 0) { // 成功
                    callBack.onSuccess(registerResponse.getInfo());
                } else { // 失败
                    Exception e = new Exception(registerResponse.getInfo());
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
     * 绑定家长账号
     *
     * @param uid         uid
     * @param phoneNumber 手机号
     * @param password    密码
     * @param callBack    回调
     */
    public void bindAccount(String uid, String phoneNumber, String password, final ObjectCallBack<String> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.PERSONAL_CENTER_BIND_CONNECTION_ACCOUNT, RequestMethod.POST);

        // 添加参数
        request.add("uid", uid);
        request.add("username", phoneNumber);
        request.add("password", password);

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
                RegisterResponse registerResponse = JSON.parseObject(result, RegisterResponse.class);
                // 判断是否成功
                if (registerResponse.getCode() == 0) { // 成功
                    callBack.onSuccess(registerResponse.getInfo());
                } else { // 失败
                    Exception e = new Exception(registerResponse.getInfo());
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
     * 获取家长列表
     *
     * @param uid      uid
     * @param callBack 回调
     */
    public void loadConnectionAccountList(String uid, final ListCallBack<ConnectionAccount> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.PERSONAL_CENTER_LOAD_CONNECTION_ACCOUNT_LIST, RequestMethod.GET);

        // 添加参数
        request.add("uid", uid);

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
                LoadConnectionAccountResponse connectionAccountResponse = JSON.parseObject(result, LoadConnectionAccountResponse.class);
                // 判断是否成功
                if (connectionAccountResponse.getCode() == 0) { // 成功
                    callBack.onSuccess(connectionAccountResponse.getData());
                } else { // 失败
                    Exception e = new Exception(connectionAccountResponse.getInfo());
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
     * 充值-生成支付订单
     *
     * @param uid      uid
     * @param increase 充值金额
     * @param type     充值方式
     * @param callBack 回调
     */
    public void createOrder(String uid, String increase, String type, final ObjectCallBack<Order> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.PERSONAL_CENTER_RECHARGE_CREATE_ORDER, RequestMethod.POST);

        // 添加参数
        request.add("uid", uid);
        request.add("increase", increase);
        request.add("type", type);

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
                CreateOrderResponse createOrderResponse = JSON.parseObject(result, CreateOrderResponse.class);
                // 判断是否成功
                if (createOrderResponse.getCode() == 0) { // 成功
                    callBack.onSuccess(createOrderResponse.getData());
                } else { // 失败
                    Exception e = new Exception(createOrderResponse.getInfo());
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
     * 修改密码
     *
     * @param uid      uid
     * @param oldPwd   原密码
     * @param newPwd   新密码
     * @param callBack 回调
     */
    public void updatePassword(String uid, String oldPwd, String newPwd, final ObjectCallBack<String> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.PERSONAL_CENTER_UPDATE_PASSWORD, RequestMethod.POST);

        // 添加参数
        request.add("uid", uid);
        request.add("oldpassword", oldPwd);
        request.add("newpassword", newPwd);

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
                UpdateUserInfoResponse updateUserInfoResponse = JSON.parseObject(result, UpdateUserInfoResponse.class);
                // 判断是否成功
                if (updateUserInfoResponse.getCode() == 0) { // 成功
                    callBack.onSuccess(updateUserInfoResponse.getInfo());
                } else { // 失败
                    Exception e = new Exception(updateUserInfoResponse.getInfo());
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
     * 通过Base32将Bitmap转换成Base64字符串
     *
     * @param bit
     * @return
     */
    private String Bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    /**
     * 修改用户信息
     *
     * @param user     用户信息
     * @param callBack 回调
     */
    public void updateUserInfo(User user, final ObjectCallBack<String> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.PERSONAL_CENTER_UPDATE_USER_INFO, RequestMethod.POST);

        // 添加参数
        request.add("uid", user.getUid());
        request.add("nickname", user.getNickname());
        request.add("realname", user.getRealname());
        request.add("sex", user.getSex());
        request.add("banji", user.getBanji());

        if (user.getHeadImage() != null) {
            String filename = user.getUid() + ("" + System.currentTimeMillis()).substring(6) + ".jpeg";
            request.add("head_image", Bitmap2StrByBase64(user.getHeadImage()));
            request.add("filename", filename);
            user.setHeadImage(null);
        }

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
                UpdateUserInfoResponse updateUserInfoResponse = JSON.parseObject(result, UpdateUserInfoResponse.class);
                // 判断是否成功
                if (updateUserInfoResponse.getCode() == 0) { // 成功
                    callBack.onSuccess(updateUserInfoResponse.getInfo());
                } else { // 失败
                    Exception e = new Exception(updateUserInfoResponse.getInfo());
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
     * 根据UID获取用户信息
     *
     * @param uid      uid
     * @param callBack 回调
     */
    public void loadUserInfoByUid(final String uid, final ObjectCallBack<User> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.PERSONAL_CENTER_LOAD_USER_INFO_BY_UID, RequestMethod.POST);

        // 添加参数
        request.add("uid", uid);

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
                LoadUserInfoByUidResponse loadUserInfoByUidResponse = JSON.parseObject(result, LoadUserInfoByUidResponse.class);
                // 判断是否成功
                if (loadUserInfoByUidResponse.getCode() == 0) { // 成功
                    User user = loadUserInfoByUidResponse.getData();
                    user.setUid(uid);
                    callBack.onSuccess(user);
                } else { // 失败
                    Exception e = new Exception(loadUserInfoByUidResponse.getInfo());
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
     * 登录
     *
     * @param account  账号
     * @param password 密码
     * @param callBack 回调
     */
    public void login(String account, String password, final ObjectCallBack<User> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.PERSONAL_CENTER_LOGIN, RequestMethod.POST);

        // 添加参数
        request.add("tel", account);
        request.add("password", password);

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
                LoginResponse loginResponse = JSON.parseObject(result, LoginResponse.class);
                // 判断是否成功
                if (loginResponse.getCode() == 0) { // 成功
                    // 根据UID获取用户信息
                    loadUserInfoByUid(loginResponse.getInfo(), callBack);
                } else { // 失败
                    Exception e = new Exception(loginResponse.getInfo());
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
     * 注册
     *
     * @param phoneNumber 手机号
     * @param password    密码
     * @param callBack    回调
     */
    public void register(String phoneNumber, String password, final ObjectCallBack<String> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.PERSONAL_CENTER_REGISTER, RequestMethod.POST);

        // 添加参数
        request.add("tel", phoneNumber);
        request.add("password", password);

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
                RegisterResponse registerResponse = JSON.parseObject(result, RegisterResponse.class);
                // 判断是否成功
                if (registerResponse.getCode() == 0) { // 成功
                    callBack.onSuccess(registerResponse.getInfo());
                } else { // 失败
                    Exception e = new Exception(registerResponse.getInfo());
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
     * 发送验证码
     *
     * @param phoneNumber 手机号
     * @param callBack    回调
     */
    public void sendValidateCode(String phoneNumber, final ObjectCallBack<String> callBack) {
        // 创建String请求
        Request<String> request = NoHttp.createStringRequest(Consts.NetUrl.PERSONAL_CENTER_SEND_VALIDATE_CODE, RequestMethod.POST);

        // 添加参数
        request.add("mobile", phoneNumber);

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
                SendValidateCodeResponse sendValidateCodeResponse = JSON.parseObject(result, SendValidateCodeResponse.class);
                // 判断是否成功
                if (sendValidateCodeResponse.getCode() == 0) { // 成功
                    callBack.onSuccess(sendValidateCodeResponse.getInfo());
                } else { // 失败
                    Exception e = new Exception(sendValidateCodeResponse.getInfo());
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

}
